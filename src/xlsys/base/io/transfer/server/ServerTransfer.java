package xlsys.base.io.transfer.server;

import java.util.List;
import java.util.Map;

import xlsys.base.cfg.BaseConfig;
import xlsys.base.io.transfer.XlsysTransfer;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.log.LogUtil;
import xlsys.base.script.XlsysClassLoader;
import xlsys.base.session.Session;

/**
 * 服务端传输抽象类
 * @author Lewis
 *
 */
public abstract class ServerTransfer extends XlsysTransfer implements Runnable
{
	// 服务端停止类型
	/**
	 * 停止接受新的请求，等待所有现有请求完成，释放资源
	 */
	public final static int SERVER_SHUTDOWN_COMMON = 0;
	/**
	 * 停止接受新的请求，直接终止现有请求，释放资源
	 */
	public final static int SERVER_SHUTDOWN_IMMEDIATE = 1;
	/**
	 * 停止接受新的请求，直接终止现有请求，不释放资源
	 */
	public final static int SERVER_SHUTDOWN_ABORT = 2;
	
	// 服务端状态
	/**
	 * 服务端正在运行
	 */
	public final static int SERVER_STATUS_RUNNING = 0;
	/**
	 * 服务端正在停止
	 */
	public final static int SERVER_STATUS_STOPING = 1;
	/**
	 * 服务端已经停止
	 */
	public final static int SERVER_STATUS_STOPED = 2;
	
	protected int serverStatus;
	protected static PackageProcessor packageProcessor;
	
	/**
	 * 构造一个服务端传输类
	 * @throws Exception
	 */
	public ServerTransfer() throws Exception
	{
		super();
		serverStatus = SERVER_STATUS_STOPED;
		initPackageProcessor();
	}
	
	protected void initPackageProcessor() throws Exception
	{
		if(packageProcessor==null)
		{
			XmlModel serverCfgXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SERVER_CONFIG);
			XmlModel ppXm = serverCfgXm.getChild("PackageProcessor");
			String ppClassName = ppXm.getChild("className").getText();
			packageProcessor = (PackageProcessor) XlsysClassLoader.getInstance().loadClass(ppClassName).newInstance();
			List<XmlModel> extProcessorXmList = ppXm.getChilds("extProcessor");
			if(extProcessorXmList!=null)
			{
				for(XmlModel extProcessorXm : extProcessorXmList)
				{
					packageProcessor.addExtProcessor((PackageProcessor) XlsysClassLoader.getInstance().loadClass(extProcessorXm.getText()).newInstance());
				}
			}
		}
	}
	
	public void run()
	{
		serverStatus = SERVER_STATUS_RUNNING;
		startup();
	}
	
	/**
	 * 启动服务端
	 */
	protected abstract void startup();
	
	/**
	 * 释放服务端资源
	 */
	protected abstract void release();
	
	/**
	 * 等待正在运行的请求
	 */
	protected abstract void waitForRunningRequest();
	
	/**
	 * 停止服务端
	 */
	protected abstract void stop();

	/**
	 * 判断服务端是否已经停止
	 * @return
	 */
	public boolean isStoped()
	{
		return serverStatus==SERVER_STATUS_STOPED;
	}
	
	/**
	 * 获取服务端状态
	 * @return
	 */
	public int getServerStatus()
	{
		return serverStatus;
	}
	
	/**
	 * 停止服务端
	 */
	public void shutdown()
	{
		shutdown(SERVER_SHUTDOWN_COMMON);
	}

	/**
	 * 停止服务端
	 * @param opt 服务端停止类型
	 */
	public void shutdown(int opt)
	{
		if(serverStatus==SERVER_STATUS_RUNNING)
		{
			serverStatus = SERVER_STATUS_STOPING;
			if(opt==SERVER_SHUTDOWN_COMMON)
			{
				waitForRunningRequest();
			}
			if(opt==SERVER_SHUTDOWN_COMMON||opt==SERVER_SHUTDOWN_IMMEDIATE)
			{
				release();
			}
			stop();
			serverStatus = SERVER_STATUS_STOPED;
		}
		else if(serverStatus==SERVER_STATUS_STOPING)
		{
			LogUtil.printlnWarn("This Server is already being stoped by another thread!");
		}
		else if(serverStatus==SERVER_STATUS_STOPED)
		{
			LogUtil.printlnWarn("This Server has been stoped!");
		}
	}
}
