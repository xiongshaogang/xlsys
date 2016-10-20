package xlsys.base.io.transfer.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
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
import xlsys.base.io.transfer.server.tpl.JSTplListener;
import xlsys.base.io.transfer.server.tpl.TplEvent;
import xlsys.base.io.transfer.server.tpl.TplListener;
import xlsys.base.io.transfer.server.tpl.TplProcessor;
import xlsys.base.io.util.FileUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.log.LogUtil;
import xlsys.base.script.XlsysClassLoader;
import xlsys.base.script.XlsysCompiler;
import xlsys.base.session.Session;
import xlsys.base.util.ObjectUtil;
import xlsys.base.util.StringUtil;

/**
 * 标准的HttpServlet实现类，当使用Web形式来进行数据交互时，使用该类来进行数据交互处理
 * @author Lewis
 *
 */
@WebServlet
@MultipartConfig
public class XlsysServlet extends AbstractServlet
{
	private static final long serialVersionUID = -5772354685874961574L;
	
	private static ServletServerTransfer servletServerTransfer;
	
	private static Map<Integer, Map<String, TplProcessor>> tplProcessorMap;
	
	public XlsysServlet()
	{
		super();
	}

	private synchronized static void initServletServerTransfer() throws Exception
	{
		if(servletServerTransfer==null)
		{
			servletServerTransfer = new ServletServerTransfer();
		}
	}
	
	private synchronized static Map<String, TplProcessor> getTplProcessorMap(int envId)
	{
		if(tplProcessorMap==null) tplProcessorMap = new HashMap<Integer, Map<String, TplProcessor>>();
		if(!tplProcessorMap.containsKey(envId))
		{
			tplProcessorMap.put(envId, new HashMap<String, TplProcessor>());
		}
		return tplProcessorMap.get(envId);
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
		
		return ServerTransfer.packageProcessor._processWithPermission(inPkg);
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
			// resp.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
			Integer platForm = ObjectUtil.objectToInt(req.getHeader(XLSYS.PLATFORM));
			if(platForm!=null)
			{
				if(platForm==XLSYS.PLATFORM_WEB) doWebPost(req, resp);
				else if(platForm==XLSYS.PLATFORM_TEMPLATE) doTemplatePost(req, resp);
				else doBinaryPost(req, resp);
			}
			else doBinaryPost(req, resp);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
	
	private void addJavaListenerToTemplate(IDataBase dataBase, String javaListener, TplProcessor ecp)
	{
		if(javaListener!=null)
		{
			// 添加Java监听
			String[] listeners = javaListener.split(XLSYS.KEY_CODE_SEPARATOR);
			for(String lsnStr : listeners)
			{
				// 尝试对监听进行加载
				try
				{
					// 获取listener的额外参数
					String paramStr = null;
					int qstIdx = lsnStr.indexOf(XLSYS.PARAM_QUESTION);
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
					TplListener listener = (TplListener) lsnClass.newInstance();
					// 设置额外参数
					if(paramStr!=null)
					{
						String[] params = paramStr.split(XLSYS.PARAM_AND);
						for(String param : params)
						{
							String[] prop = param.split(XLSYS.PARAM_RELATION, 2);
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
	
	private String decodeUrl(String src) throws UnsupportedEncodingException
	{
		if(src==null) return null;
		return URLDecoder.decode(src, "utf-8");
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
			String cmd = null;
			String sessionStr = null;
			String data = null;
			String retPkgStr = null;
			is = req.getInputStream();
			try
			{
				byte[] b = IOUtil.readBytesFromInputStream(is, length);
				String str = new String(b, "UTF-8");
				Map<String, String> paramMap = StringUtil.getParamMap(str, "=", "&");
				cmd = decodeUrl(paramMap.get(XLSYS.WEB_COMMAND));
				sessionStr = decodeUrl(paramMap.get(XLSYS.WEB_SESSION));
				data = decodeUrl(paramMap.get(XLSYS.WEB_DATA));
				retPkgStr = decodeUrl(paramMap.get(XLSYS.WEB_RETPKG));
			}
			catch(Exception e)
			{
				// 尝试使用Parameter直接获取值
				cmd = decodeUrl(req.getParameter(XLSYS.WEB_COMMAND));
				sessionStr = decodeUrl(req.getParameter(XLSYS.WEB_SESSION));
				data = decodeUrl(req.getParameter(XLSYS.WEB_DATA));
				retPkgStr = decodeUrl(req.getParameter(XLSYS.WEB_RETPKG));
				if(cmd==null) throw e;
			}
			Session session = (Session) IOUtil.readJSONObject(sessionStr);
			Serializable inObj = (Serializable) IOUtil.readJSONObject(data);
			boolean retPkg = ObjectUtil.objectToBoolean(retPkgStr);
			Serializable outObj = null;
			if(retPkg)
			{
				InnerPackage inPkg = new InnerPackage(session);
				inPkg.setCommand(cmd);
				inPkg.setObj(inObj);
				outObj = virtualPost(inPkg);
			}
			else outObj = virtualPost(session, cmd, inObj);
			PrintWriter pw = resp.getWriter();
			pw.write(IOUtil.getJSONObjectStr(outObj));
		}
		finally
		{
			IOUtil.close(is);
		}
	}

	private void doTemplatePost(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		int length = req.getContentLength();
		InputStream is = null;
		try
		{
			String cmd = null;
			String sessionStr = null;
			String data = null;
			String retPkgStr = null;
			String templateId = null;
			is = req.getInputStream();
			try
			{
				byte[] b = IOUtil.readBytesFromInputStream(is, length);
				String str = new String(b, "UTF-8");
				Map<String, String> paramMap = StringUtil.getParamMap(str, "=", "&");
				cmd = decodeUrl(paramMap.get(XLSYS.WEB_COMMAND));
				sessionStr = decodeUrl(paramMap.get(XLSYS.WEB_SESSION));
				data = decodeUrl(paramMap.get(XLSYS.WEB_DATA));
				retPkgStr = decodeUrl(paramMap.get(XLSYS.WEB_RETPKG));
				templateId = decodeUrl(paramMap.get(XLSYS.WEB_TEMPLATE_ID));
			}
			catch(Exception e)
			{
				// 尝试使用Parameter直接获取值
				cmd = decodeUrl(req.getParameter(XLSYS.WEB_COMMAND));
				sessionStr = decodeUrl(req.getParameter(XLSYS.WEB_SESSION));
				data = decodeUrl(req.getParameter(XLSYS.WEB_DATA));
				retPkgStr = decodeUrl(req.getParameter(XLSYS.WEB_RETPKG));
				templateId = decodeUrl(req.getParameter(XLSYS.WEB_TEMPLATE_ID));
				if(sessionStr==null) throw e;
			}
			Session session = (Session) IOUtil.readJSONObject(sessionStr);
			Serializable outObj = null;
			if(cmd!=null)
			{
				Serializable inObj = (Serializable) IOUtil.readJSONObject(data);
				boolean retPkg = ObjectUtil.objectToBoolean(retPkgStr);
				if(retPkg)
				{
					InnerPackage inPkg = new InnerPackage(session);
					inPkg.setCommand(cmd);
					inPkg.setObj(inObj);
					outObj = virtualPost(inPkg);
				}
				else outObj = virtualPost(session, cmd, inObj);
			}
			if(templateId==null||templateId.isEmpty())
			{
				// 如果没有模板Id, 则直接返回结果对象
				PrintWriter pw = resp.getWriter();
				pw.write(IOUtil.getJSONObjectStr(outObj));
			}
			else
			{
				// 如果存在模板Id, 则交给模板处理器处理
				doPostWithTemplateProcessor(req, resp, templateId, session, outObj);
			}
		}
		finally
		{
			IOUtil.close(is);
		}
	}

	private void doPostWithTemplateProcessor(HttpServletRequest req, HttpServletResponse resp, String templateId, Session session, Serializable outObj) throws Exception
	{
		int envId = ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
		Map<String, TplProcessor> procMap = getTplProcessorMap(envId);
		synchronized(procMap)
		{
			if(!procMap.containsKey(templateId))
			{
				TplProcessor ecp = new TplProcessor(templateId);
				IDataBase dataBase = null;
				try
				{
					dataBase = EnvDataBase.getInstance(envId);
					// 初始化该命令字对应的处理器
					String selectSql = "select * from xlsys_template where templateid=?";
					ParamBean pb = new ParamBean(selectSql);
					pb.addParamGroup();
					pb.setParam(1, templateId);
					IDataSet dataSet = dataBase.sqlSelect(pb);
					if(dataSet.getRowCount()>0)
					{
						// 获取模板
						byte[] template = (byte[]) dataSet.getValue(0, "template");
						if(template==null||template.length==0)
						{
							// 尝试从path中获取模板
							String templatePath = ObjectUtil.objectToString(dataSet.getValue(0, "templatepath"));
							if(templatePath!=null&&!templatePath.isEmpty())
							{
								String targetBundleId = ObjectUtil.objectToString(dataSet.getValue(0, "bundleid"));
								URL url = FileUtil.getBundleResourceUrl(templatePath, targetBundleId);
								if(url!=null)
								{
									InputStream is = null;
									try
									{
										is = url.openStream();
										template = IOUtil.readBytesFromInputStream(is, -1);
									}
									finally
									{
										IOUtil.close(is);
									}
								}
							}
						}
						if(template!=null&&template.length>0) ecp.setDefaultTemplate(new String(template, "utf-8"));
						// 添加默认的跳转路径
						ecp.setDefaultRedirectPath(ObjectUtil.objectToString(dataSet.getValue(0, "redirectPath")));
						// 添加Java监听
						String javaListener = ObjectUtil.objectToString(dataSet.getValue(0, "javalistener"));
						addJavaListenerToTemplate(dataBase, javaListener, ecp);
						// 添加JS脚本监听
						String jsListener = ObjectUtil.objectToString(dataSet.getValue(0, "jslistener"));
						if(jsListener!=null&&jsListener.length()>0) ecp.addListener(new JSTplListener(jsListener));
					}
					procMap.put(templateId, ecp);
				}
				finally
				{
					DBUtil.close(dataBase);
				}
			}
		}
		TplProcessor ecp = procMap.get(templateId);
		if(ecp==null) return;
		TplEvent event = new TplEvent(templateId);
		event.request = req;
		event.response = resp;
		event.fillObj = outObj;
		event.template = ecp.getDefaultTemplate();
		event.redirectPath = ecp.getDefaultRedirectPath();
		ecp.process(event);
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
