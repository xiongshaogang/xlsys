package xlsys.base.io.transfer.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;

import xlsys.base.XLSYS;
import xlsys.base.database.EnvDataBase;
import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.dataset.IDataSet;
import xlsys.base.io.pack.InnerPackage;
import xlsys.base.io.pack.XlsysPackage;
import xlsys.base.io.transfer.server.extra.ExtraCmdEvent;
import xlsys.base.io.transfer.server.extra.ExtraCmdListener;
import xlsys.base.io.transfer.server.extra.ExtraCmdProcessor;
import xlsys.base.io.transfer.server.extra.JSExtraCmdListener;
import xlsys.base.io.util.IOUtil;
import xlsys.base.log.LogUtil;
import xlsys.base.script.XlsysClassLoader;
import xlsys.base.script.XlsysCompiler;
import xlsys.base.session.Session;
import xlsys.base.task.XlsysScheduledExecutor;
import xlsys.base.util.ObjectUtil;
import xlsys.base.util.StringUtil;
import xlsys.base.util.SystemUtil;

/**
 * 标准的HttpServlet实现类，当使用Web形式来进行数据交互时，使用该类来进行数据交互处理
 * @author Lewis
 *
 */
public class XlsysServlet extends HttpServlet
{
	private static final long serialVersionUID = -5772354685874961574L;
	
	private static ServletServerTransfer servletServerTransfer;
	
	private static Map<Integer, Map<String, ExtraCmdProcessor>> extraMap;
	
	public XlsysServlet()
	{
		super();
		try
		{
			SystemUtil.systemInit();
			XlsysScheduledExecutor.startSchedule();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private synchronized static void initServletServerTransfer() throws Exception
	{
		if(servletServerTransfer==null)
		{
			servletServerTransfer = new ServletServerTransfer();
		}
	}
	
	private synchronized static Map<String, ExtraCmdProcessor> getExtraMap(int envId)
	{
		if(extraMap==null) extraMap = new HashMap<Integer, Map<String, ExtraCmdProcessor>>();
		if(!extraMap.containsKey(envId))
		{
			extraMap.put(envId, new HashMap<String, ExtraCmdProcessor>());
		}
		return extraMap.get(envId);
	}
	
	/**
	 * 虚拟提交(服务端调用).
	 * 模拟客户端提交命令字到服务端
	 * @param inPkg 传入的InnerPackage
	 * @return
	 * @throws Exception
	 */
	public static InnerPackage virtualPost(InnerPackage inPkg) throws Exception
	{
		initServletServerTransfer();
		return ServerTransfer.packageProcessor._process(inPkg);
	}
	
	/**
	 * 虚拟提交(服务端调用).
	 * 模拟客户端提交命令字到服务端
	 * @param session Session对象
	 * @param command 命令字
	 * @param inObj 传入的参数
	 * @return
	 * @throws Exception
	 */
	public static Serializable virtualPost(Session session, String command, Serializable inObj) throws Exception
	{
		InnerPackage inPkg = new InnerPackage(session);
		inPkg.setCommand(command);
		inPkg.setObj(inObj);
		InnerPackage outPkg = virtualPost(inPkg);
		return outPkg.getObj();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		try
		{
			String clientType = req.getHeader(XLSYS.CLIENT_TYPE);
			if(XLSYS.CLIENT_TYPE_WEB.equals(clientType))
			{
				doWebPost(req, resp);
			}
			else
			{
				String extraCommand = req.getParameter(XLSYS.EXTRA_COMMAND);
				if(extraCommand!=null)
				{
					doExtraPost(req, resp);
				}
				else
				{
					String contype = req.getContentType();
					if (contype.equals(ContentType.DEFAULT_BINARY.toString()))
					{
						doBinaryPost(req, resp);
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
	
	private void addJavaListenerToExtra(IDataBase dataBase, String javaListener, ExtraCmdProcessor ecp)
	{
		if(javaListener!=null)
		{
			// 添加Java监听
			String[] listeners = javaListener.split(XLSYS.COMMAND_SEPARATOR);
			for(String lsnStr : listeners)
			{
				// 尝试对监听进行加载
				try
				{
					// 获取listener的额外参数
					String paramStr = null;
					int qstIdx = lsnStr.indexOf(XLSYS.COMMAND_QUESTION);
					if(qstIdx>-1)
					{
						paramStr = lsnStr.substring(qstIdx+1);
						lsnStr = lsnStr.substring(0, qstIdx);
					}
					XlsysClassLoader xcl = XlsysClassLoader.getInstance();
					if(!xcl.containsClass(lsnStr))
					{
						// 获取java源代码,并编译
						String selectSql = "select javasource from xlsys_javaclass where classid=?"; //$NON-NLS-1$
						ParamBean pb = new ParamBean(selectSql);
						pb.addParamGroup();
						pb.setParam(1, lsnStr);
						String javaSource = ObjectUtil.objectToString(dataBase.sqlSelectAsOneValue(pb));
						if(javaSource!=null)
						{
							XlsysCompiler xlsysCompiler = XlsysCompiler.getInstance();
							xlsysCompiler.addSource(lsnStr, javaSource);
							xlsysCompiler.compile();
						}
					}
					Class<?> lsnClass = XlsysClassLoader.getInstance().loadClass(lsnStr);
					ExtraCmdListener listener = (ExtraCmdListener) lsnClass.newInstance();
					// 设置额外参数
					if(paramStr!=null)
					{
						String[] params = paramStr.split(XLSYS.COMMAND_AND);
						for(String param : params)
						{
							String[] prop = param.split(XLSYS.COMMAND_RELATION, 2);
							Field field = lsnClass.getField(prop[0]);
							Class<?> fieldClass = field.getType();
							Object fieldValue = ObjectUtil.objectCast(prop[1], fieldClass);
							field.set(listener, fieldValue);
						}
					}
					ecp.addListener(listener);
				}
				catch(Exception e)
				{
					LogUtil.printlnWarn(e);
				}
			}
		}
	}
	
	private void doWebPost(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		int length = req.getContentLength();
		InputStream is = null;
		try
		{
			is = req.getInputStream();
			byte[] b = IOUtil.readBytesFromInputStream(is, length);
			String str = new String(b, "UTF-8");
			Map<String, String> paramMap = StringUtil.getParamMap(str, "=", "&");
			String cmd = paramMap.get(XLSYS.WEB_COMMAND);
			String sessionStr = paramMap.get(XLSYS.WEB_SESSION);
			Session session = (Session) IOUtil.readJSONObject(sessionStr);
			String data = paramMap.get(XLSYS.WEB_DATA);
			Serializable inObj = (Serializable) IOUtil.readJSONObject(data);
			Serializable outObj = virtualPost(session, cmd, inObj);
			PrintWriter pw = resp.getWriter();
			pw.write(IOUtil.getJSONObjectStr(outObj));
		}
		finally
		{
			IOUtil.close(is);
		}
	}

	private void doExtraPost(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String extraCmd = req.getParameter(XLSYS.EXTRA_COMMAND);
		int envId = ObjectUtil.objectToInt(req.getParameter(XLSYS.EXTRA_ENVID));
		IDataBase dataBase = null;
		try
		{
			dataBase = EnvDataBase.getInstance(envId);
			dataBase.setAutoCommit(false);
			
			Map<String, ExtraCmdProcessor> procMap = getExtraMap(envId);
			synchronized(procMap)
			{
				if(!procMap.containsKey(extraCmd))
				{
					ExtraCmdProcessor ecp = new ExtraCmdProcessor(extraCmd);
					// 初始化该命令字对应的处理器
					String selectSql = "select * from xlsys_extracmd where extracmd=?";
					ParamBean pb = new ParamBean(selectSql);
					pb.addParamGroup();
					pb.setParam(1, extraCmd);
					IDataSet dataSet = dataBase.sqlSelect(pb);
					if(dataSet.getRowCount()>0)
					{
						// 添加Java监听
						String javaListener = ObjectUtil.objectToString(dataSet.getValue(0, "javalistener"));
						addJavaListenerToExtra(dataBase, javaListener, ecp);
						// 添加JS脚本监听
						String jsListener = ObjectUtil.objectToString(dataSet.getValue(0, "jslistener")); //$NON-NLS-1$
						if(jsListener!=null&&jsListener.length()>0)
						{
							ecp.addListener(new JSExtraCmdListener(jsListener));
						}
						// 添加默认的跳转路径
						ecp.setDefaultDispatchPath(ObjectUtil.objectToString(dataSet.getValue(0, "dispatchpath")));
					}
					procMap.put(extraCmd, ecp);
				}
			}
			ExtraCmdProcessor ecp = procMap.get(extraCmd);
			ExtraCmdEvent event = new ExtraCmdEvent(extraCmd);
			event.request = req;
			event.response = resp;
			event.dispatchPath = ecp.getDefaultDispatchPath();
			ecp.process(event);
			if(event.doit) dataBase.commit();
			else
			{
				if(event.errMsg instanceof Exception) throw (Exception)event.errMsg;
				else throw new Exception(ObjectUtil.objectToString(event.errMsg));
			}
		}
		catch (Exception e)
		{
			if(!dataBase.getAutoCommit()) dataBase.rollback();
			throw e;
		}
		finally
		{
			DBUtil.close(dataBase);
		}
	}

	private void doBinaryPost(HttpServletRequest req, HttpServletResponse resp)
			throws Exception
	{
		XlsysPackage respPkg = null;
		InputStream is = null;
		OutputStream os = null;
		byte seriMode = XLSYS.SERIALIZATION_MODE_JDK;
		try
		{
			is = req.getInputStream();
			// 读取序列化模式
			seriMode = (byte) is.read();
			// 读取包内容
			XlsysPackage reqPak = null;
			if(seriMode==XLSYS.SERIALIZATION_MODE_JDK)
			{
				reqPak = (XlsysPackage) IOUtil.readObject(is);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_INTERNAL)
			{
				reqPak = (XlsysPackage) IOUtil.readInternalObject(is);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_JSON)
			{
				reqPak = (XlsysPackage) IOUtil.readJSONObject(is);
			}
			if (reqPak != null)
			{
				initServletServerTransfer();
				respPkg = ServerTransfer.packageProcessor.process(reqPak, servletServerTransfer, seriMode);
			}
			resp.setContentType(ContentType.DEFAULT_BINARY.toString());
			os = resp.getOutputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// 写入序列化模式
			baos.write(seriMode);
			// 写入内容
			if(seriMode==XLSYS.SERIALIZATION_MODE_JDK)
			{
				IOUtil.writeObject(respPkg, baos);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_INTERNAL)
			{
				IOUtil.writeInternalObject(respPkg, baos);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_JSON)
			{
				IOUtil.writeJSONObject(respPkg, baos);
			}
			resp.setContentLength(baos.size());
			os.write(baos.toByteArray());
		}
		catch (Exception e)
		{
			LogUtil.printlnError(e);
			throw new ServletException(e);
		}
		finally
		{
			IOUtil.close(is);
			IOUtil.close(os);
		}
		
	}
}
