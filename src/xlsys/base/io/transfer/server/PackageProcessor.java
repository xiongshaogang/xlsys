package xlsys.base.io.transfer.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.cfg.BaseConfig;
import xlsys.base.exception.NativeException;
import xlsys.base.exception.PermissionDeniedException;
import xlsys.base.io.pack.InnerPackage;
import xlsys.base.io.pack.XlsysPackage;
import xlsys.base.io.transfer.XlsysTransfer;
import xlsys.base.io.transfer.client.ClientTransfer;
import xlsys.base.io.transfer.client.ClientTransferFactory;
import xlsys.base.io.util.FileUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.log.LogUtil;
import xlsys.base.model.SystemInfoModel;
import xlsys.base.session.Session;
import xlsys.base.thread.XlsysThreadPool;
import xlsys.base.util.ObjectUtil;
import xlsys.base.util.ReflectUtil;

/**
 * 处理中心的抽象实现类，该类定义了接受->反馈的数据处理过程
 * @author Lewis
 *
 */
public abstract class PackageProcessor
{
	private PackageProcessor mainPackageProcessor;
	private List<PackageProcessor> extProcessorList;
	private static boolean isInit = false;
	private static ClientTransfer centerTransfer;
	private static Set<String> registerCmdSet;
	private static SystemInfoModel systemInfoModel;
	/**
	 * 在线用户列表.正常情况下只有主处理类中会使用此成员
	 * <li> key : session id
	 * <li> value : session相关信息
	 */
	protected Map<String, SessionInfo> aliveSessionMap;
	private int sessionAliveTime; // session保留时间
	protected long checkCycleTime; // 检查连接状态的周期时间(毫秒)
	
	private synchronized static void initCenterTransfer() throws Exception
	{
		if(!isInit)
		{
			isInit = true;
			XmlModel systemXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
			XmlModel centerServerXm = systemXm.getChild("CenterServer");
			if(centerServerXm!=null&&"on".equalsIgnoreCase(centerServerXm.getAttributeValue("switch")))
			{
				XmlModel transferXm = centerServerXm.getChild("Transfer");
				centerTransfer = ClientTransferFactory.getClientTransfer(transferXm);
				registerCmdSet = new HashSet<String>();
				XmlModel registerCommandXm = centerServerXm.getChild("RegisterCommand");
				List<XmlModel> cmdXmList = registerCommandXm.getChilds("cmd");
				for(XmlModel cmdXm : cmdXmList)
				{
					registerCmdSet.add((String) ReflectUtil.invoke(XLSYS.JAVA_STATIC_FIELD_PREFIX + cmdXm.getText()));
				}
			}
		}
	}
	
	/**
	 * 构造一个处理中心对象
	 * @throws Exception
	 */
	public PackageProcessor() throws Exception
	{
		initCenterTransfer();
	}
	
	private synchronized void initAliveSessionInfo()
	{
		if(aliveSessionMap==null)
		{
			aliveSessionMap = new HashMap<String, SessionInfo>();
			checkCycleTime = 1000;
			try
			{
				XmlModel serverXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SERVER_CONFIG);
				sessionAliveTime = ObjectUtil.objectToInt(serverXm.getChild("SessionAliveTime").getText()); 
			}
			catch (Exception e)
			{
				sessionAliveTime = 60;
				LogUtil.printlnWarn(e);
			}
			Runnable checkThread = new Runnable()
			{
				@Override
				public void run()
				{
					while(true)
					{
						long nowtime = System.currentTimeMillis();
						synchronized(aliveSessionMap)
						{
							Set<String> toBeRemovedSessionIdSet = null;
							for(SessionInfo sessionInfo : aliveSessionMap.values())
							{
								if((nowtime-sessionInfo.lastActiveTime)>(sessionAliveTime*1000))
								{
									if(toBeRemovedSessionIdSet==null) toBeRemovedSessionIdSet = new HashSet<String>();
									toBeRemovedSessionIdSet.add(sessionInfo.session.getSessionId());
								}
							}
							if(toBeRemovedSessionIdSet!=null)
							{
								for(String sessionId : toBeRemovedSessionIdSet)
								{
									aliveSessionMap.remove(sessionId);
								}
							}
						}
						try
						{
							Thread.sleep(checkCycleTime);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}
			};
			try
			{
				XlsysThreadPool.getInstance().execute(checkThread);
			}
			catch (Exception e)
			{
				LogUtil.printlnError(e);
			}
		}
	}
	
	protected InnerPackage _processWithPermission(InnerPackage innerInPackage) throws ClassNotFoundException, DocumentException, IOException, NativeException
	{
		// 检测合法性
		InnerPackage innerOutPackage = null;
		if(hasPermission(innerInPackage))
		{
			innerOutPackage = _process(innerInPackage);
		}
		else
		{
			innerOutPackage = new InnerPackage(innerInPackage.getSession());
			innerOutPackage.setCommand(XLSYS.COMMAND_PERMISSION_DENIED);
			innerOutPackage.setObj(new PermissionDeniedException(innerInPackage.getCommand()));
		}
		return innerOutPackage;
	}
	
	/**
	 * 处理一个内部包
	 * @param innerInPkg 要处理的内部包
	 * @return 带有结果信息的内部包
	 */
	private InnerPackage _process(InnerPackage innerInPkg)
	{
		InnerPackage innerOutPkg = null;
		if(mainPackageProcessor!=null)
		{
			// 如果存在主处理中心，则直接调用主处理中心的处理方法来处理
			innerOutPkg = mainPackageProcessor._process(innerInPkg);
		}
		else
		{
			keepSessionInfo(innerInPkg.getSession());
			LogUtil.printlnInfo(Thread.currentThread() + ": with command " + innerInPkg.getCommand());
			try
			{
				innerOutPkg = processWithRedirect(innerInPkg);
				List<PackageProcessor> extPPList = getExtProcessors();
				for(int i=0;i<extPPList.size()&&(innerOutPkg==null||innerOutPkg.getCommand()==null);i++)
				{
					PackageProcessor extProcessor = extPPList.get(i);
					innerOutPkg = extProcessor.processWithRedirect(innerInPkg);
				}
				if(innerOutPkg!=null&&innerOutPkg.getObj() instanceof Exception)
				{
					innerOutPkg.setCommand(XLSYS.COMMAND_ERROR);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				innerOutPkg = new InnerPackage(innerInPkg.getSession());
				innerOutPkg.setCommand(XLSYS.COMMAND_ERROR);
				innerOutPkg.setObj(e);
			}
		}
		return innerOutPkg;
	}
	
	private void keepSessionInfo(Session session)
	{
		initAliveSessionInfo();
		String sessionId = session.getSessionId();
		if(!XLSYS.SESSION_DEFAULT_ID.equals(sessionId))
		{
			SessionInfo sessionInfo = aliveSessionMap.get(sessionId);
			if(sessionInfo==null) sessionInfo = new SessionInfo();
			sessionInfo.session = session;
			sessionInfo.lastActiveTime = System.currentTimeMillis();
			aliveSessionMap.put(sessionId, sessionInfo);
		}
	}

	/**
	 * 处理一个传输包
	 * @param xlsysInPkg 要处理的传输包
	 * @param xlsysTransfer 传输类
	 * @param seriMode 序列化模式 .详见{@link XLSYS}
	 * @return 包含处理结果的传输包
	 * @throws Exception
	 */
	public final XlsysPackage process(XlsysPackage xlsysInPkg, XlsysTransfer xlsysTransfer, byte seriMode) throws Exception
	{
		InnerPackage innerInPackage = xlsysTransfer.getInnerPackage(xlsysInPkg);
		// 检测合法性
		InnerPackage innerOutPackage = _processWithPermission(innerInPackage);
		XlsysPackage xlsysOutPackage = xlsysTransfer.getXlsysPackage(innerOutPackage, seriMode);
		return xlsysOutPackage;
	}
	
	private boolean hasPermission(InnerPackage innerInPackage) throws DocumentException, ClassNotFoundException, IOException, NativeException
	{
		if(systemInfoModel==null)
		{
			XmlModel sysXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
			String licFilePath = sysXm.getChild("LicenseFile").getText();
			File file = new File(licFilePath).getCanonicalFile();
			if(file.exists())
			{
				systemInfoModel = SystemInfoModel.readOfficalSystemInfoModel(licFilePath);
			}
			else
			{
				SystemInfoModel current = SystemInfoModel.getCurrentSystemInfoModel();
				FileUtil.writeFile("systeminfo.xlsys", IOUtil.getObjectBytes(current));
				LogUtil.printlnInfo("System information generated at "+new File("systeminfo.xlsys").getAbsolutePath());
				return false;
			}
		}
		return systemInfoModel.hasPermission(innerInPackage.getCommand());
	}

	/**
	 * 使用重定向的方式处理内部包
	 * @param innerPackage 要处理的内部包
	 * @return
	 * @throws Exception
	 */
	protected InnerPackage processWithRedirect(InnerPackage innerPackage) throws Exception
	{
		InnerPackage innerOutPackage = null;
		if(centerTransfer!=null&&registerCmdSet!=null&&registerCmdSet.contains(innerPackage.getCommand()))
		{
			innerOutPackage = doProcessWithCenter(innerPackage);
		}
		else
		{
			innerOutPackage = doProcess(innerPackage);
		}
		return innerOutPackage;
	}
	
	/**
	 * 使用中心处理器来处理内部包
	 * @param innerPackage
	 * @return
	 * @throws Exception
	 */
	protected InnerPackage doProcessWithCenter(InnerPackage innerPackage) throws Exception
	{
		InnerPackage innerOutPackage = null;
		if(centerTransfer!=null)
		{
			innerOutPackage = centerTransfer.postInnerPackage(innerPackage);
		}
		return innerOutPackage;
	}
	
	/**
	 * 处理内部包
	 * @param innerPackage 要处理的内部包
	 * @return 包含处理结果的内部包
	 * @throws Exception
	 */
	protected abstract InnerPackage doProcess(InnerPackage innerPackage) throws Exception;
	
	/**
	 * 添加附加的处理中心
	 * @param extProcessor
	 */
	public void addExtProcessor(PackageProcessor extProcessor)
	{
		if(extProcessorList==null)
		{
			extProcessorList = new ArrayList<PackageProcessor>();
		}
		extProcessor.setMainPackageProcessor(this);
		extProcessorList.add(extProcessor);
	}
	
	/**
	 * 获取附加的处理中心列表
	 * @return
	 */
	public List<PackageProcessor> getExtProcessors()
	{
		return extProcessorList;
	}
	
	/**
	 * 设置主处理中心
	 * @param mainPackageProcessor
	 */
	public void setMainPackageProcessor(PackageProcessor mainPackageProcessor)
	{
		this.mainPackageProcessor = mainPackageProcessor;
	}
	
	/**
	 * 获取在线Session集合
	 * @return
	 */
	public Map<String, SessionInfo> getAliveSessionMap()
	{
		Map<String, SessionInfo> map = null;
		if(mainPackageProcessor!=null) map = mainPackageProcessor.getAliveSessionMap();
		else map = aliveSessionMap;
		return map;
	}

	public void setAliveSessionMap(Map<String, SessionInfo> aliveSessionMap)
	{
		this.aliveSessionMap = aliveSessionMap;
	}

	public class SessionInfo
	{
		public Session session;
		public long lastActiveTime;
	}
}
