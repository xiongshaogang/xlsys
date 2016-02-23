package xlsys.base.webservice;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.xml.ws.Endpoint;

import org.dom4j.DocumentException;

import xlsys.base.cfg.BaseConfig;
import xlsys.base.exception.UnsupportedException;
import xlsys.base.io.ProcessStreamLoader;
import xlsys.base.io.util.FileUtil;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.log.LogUtil;
import xlsys.base.script.XlsysClassLoader;
import xlsys.base.script.XlsysCompiler;
import xlsys.base.util.ObjectUtil;
import xlsys.base.util.StringUtil;
import xlsys.base.util.SystemUtil;

/**
 * WebService发布类
 * 测试导入webservice, 可参考命令wsimport -d ./bin -s ./src -p test.client.ref http://localhost/xlsysrap/webservice/webservice/wstest?wsdl -B-XautoNameResolution
 * @author Lewis
 *
 */
public class WebServicePublisher
{
	public final static String WEBSERVICE_ROOT = "webservice";
	
	private static WebServicePublisher publisher;
	
	/**
	 * web根路径
	 */
	private String webRoot;
	/**
	 * 默认的发布类包全路径
	 */
	private String defaultPackage;
	/**
	 * JavaHome路径
	 */
	private String javaHome;
	
	private WebServicePublisher() throws DocumentException
	{
		// 从配置文件中读取信息并初始化
		XmlModel sysXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
		javaHome = sysXm.getChild("JAVA_HOME").getText().trim();
		XmlModel wsXm = sysXm.getChild("WebService");
		webRoot = wsXm.getChild("webRoot").getText();
		defaultPackage = wsXm.getChild("defaultPackage").getText();
		// 建立必要的文件夹
		String[] dirNames = new String[]{"src","bin","wsdl"};
		for(int i=0;i<dirNames.length;i++)
		{
			File temp = new File(WEBSERVICE_ROOT+File.separator+dirNames[i]);
			temp.mkdirs();
		}
	}
	
	/**
	 * 获取发布类实例
	 * @return
	 * @throws DocumentException
	 */
	public synchronized static WebServicePublisher getInstance() throws DocumentException
	{
		if(publisher==null)
		{
			publisher = new WebServicePublisher();
		}
		return publisher;
	}
	
	/**
	 * 将一个方法发布成webService方法. 该方法必须是static的, 并且返回值类型不能为接口类或抽象类，并且必须有个不带参的构造方法，包含属性.
	 * @param srcMethod 源方法
	 * @param publisherClassName 发布的类名称
	 * @param webServiceName 发布的webservice名称. e.g. 如果此参数为ws, 则发布后的webservice对应的url为http://localhost/xlsysrap/webservice/<B>ws</B>
	 * @throws Exception
	 */
	public void publisherMethod(Method srcMethod, String publisherClassName, String webServiceName) throws Exception
	{
		publisherMethod(srcMethod, publisherClassName, webServiceName, srcMethod.getName(), srcMethod.getName(), "result");
	}
	
	/**
	 * 将一个方法发布成webService方法. 该方法必须是static的, 并且返回值类型不能为接口类或抽象类，并且必须有个不带参的构造方法，包含属性.
	 * @param srcMethod 源方法
	 * @param publisherClassName 发布的类名称
	 * @param webServiceName 发布的webservice名称. e.g. 如果此参数为ws, 则发布后的webservice对应的url为http://localhost/xlsysrap/webservice/<B>ws</B>
	 * @param action 动作名称.
	 * @param operationName 生成方法的调用名称
	 * @param resultName 返回值的名称
	 * @throws Exception
	 */
	public void publisherMethod(Method srcMethod, String publisherClassName, String webServiceName, String action, String operationName, String resultName) throws Exception
	{
		publisherMethod(new Method[]{srcMethod}, publisherClassName, webServiceName, new String[]{action}, new String[]{operationName}, new String[]{resultName});
	}

	/**
	 * 将多个方法发布成webService方法, 并且放入同一个webservice类中. 这些方法必须是static的, 并且返回值类型不能为接口类或抽象类，并且必须有个不带参的构造方法，包含属性.
	 * @param srcMethods 源方法
	 * @param publisherClassName 发布的类名称
	 * @param webServiceName 发布的webservice名称. e.g. 如果此参数为ws, 则发布后的webservice对应的url为http://localhost/xlsysrap/webservice/<B>ws</B>
	 * @param actions 动作名称.
	 * @param operationNames 生成方法的调用名称
	 * @param resultNames 返回值的名称
	 * @throws Exception
	 */
	public void publisherMethod(Method[] srcMethods, String publisherClassName, String webServiceName, String[] actions, String[] operationNames, String[] resultNames) throws Exception
	{
		String classStr = generateClassStr(srcMethods, publisherClassName, actions, operationNames, resultNames);
		// 写入文件
		int lastPointIdx = publisherClassName.lastIndexOf('.');
		if(lastPointIdx==-1)
		{
			publisherClassName = defaultPackage + '.' + publisherClassName;
		}
		String filePath = WEBSERVICE_ROOT+File.separator+"src"+File.separator+StringUtil.transJavaPackToPath(publisherClassName)+".java";
		FileUtil.writeFile(filePath, classStr.getBytes("utf-8"));
		// 获取当前的classPath
		String classPathSeparator = ":";
		if(SystemUtil.getOsName().toLowerCase().contains("windows")) classPathSeparator = ";";
		String classPath = System.getProperty("java.class.path")+classPathSeparator+"./bin";
		// 编译Java文件
		List<String> cmdList = new ArrayList<String>();
		cmdList.add(javaHome+File.separator+"bin"+File.separator+"javac");
		cmdList.add("-d");
		cmdList.add("./bin");
		cmdList.add("-classpath");
		cmdList.add(classPath);
		cmdList.add("./src"+File.separator+StringUtil.transJavaPackToPath(publisherClassName)+".java");
		ProcessBuilder pb = new ProcessBuilder(cmdList);
		pb.directory(new File(WEBSERVICE_ROOT));
		Process process = pb.start();
		ProcessStreamLoader psl = new ProcessStreamLoader(process);
		psl.setSteamType(ProcessStreamLoader.PROCESS_ERRORSTREAM);
		psl.setClose(true);
		psl.start();
		process.waitFor();
		if(process.exitValue()==0)
		{
			// 编译成功, 生成wsdl描述文件
			cmdList.clear();
			cmdList.add(javaHome+File.separator+"bin"+File.separator+"wsgen");
			cmdList.add("-s");
			cmdList.add("./src");
			cmdList.add("-d");
			cmdList.add("./bin");
			cmdList.add("-r");
			cmdList.add("./wsdl");
			cmdList.add("-wsdl");
			cmdList.add(publisherClassName);
			cmdList.add("-classpath");
			cmdList.add(classPath);
			pb = new ProcessBuilder(cmdList);
			pb.directory(new File(WEBSERVICE_ROOT));
			process = pb.start();
			psl = new ProcessStreamLoader(process);
			psl.setSteamType(ProcessStreamLoader.PROCESS_ERRORSTREAM);
			psl.setClose(true);
			psl.start();
			process.waitFor();
			if(process.exitValue()==0)
			{
				// 生成描述文件成功, 发布WebService
				// 使用运行时方法编译该Java代码
				XlsysCompiler xlsysCompiler = XlsysCompiler.getInstance();
				xlsysCompiler.addSource(publisherClassName, classStr);
				//设置编译依赖的CLASSPATH。
				//setLocation的value为Iterable<? extends File>，只要将allReferences返回即可完成设置编译依赖.
				StandardJavaFileManager fileManager = (StandardJavaFileManager) xlsysCompiler.getJavaFileManager().getParent();
				List<File> classPathFiles = new ArrayList<File>();
				for(String path : classPath.split(classPathSeparator))
				{
					classPathFiles.add(new File(path));
				}
				fileManager.setLocation(StandardLocation.CLASS_PATH, classPathFiles);
				xlsysCompiler.compile();
				Class<?> publisherClass = XlsysClassLoader.getInstance().loadClass(publisherClassName);
				Endpoint.publish(webRoot+"/webservice/"+webServiceName, publisherClass.newInstance());
				LogUtil.printlnInfo("Web Service Published At : "+webRoot+"/webservice/"+webServiceName);
			}
		}
	}
	
	private String generateClassStr(Method[] srcMethod, String publisherClassName, String[] action, String[] operationName, String[] resultName) throws UnsupportedException
	{
		String packageName = null;
		String className = null;
		int lastPointIdx = publisherClassName.lastIndexOf('.');
		if(lastPointIdx==-1)
		{
			packageName = defaultPackage;
			className = publisherClassName;
			// 没有包名，则自动加上默认包名
			publisherClassName = defaultPackage + '.' + publisherClassName;
		}
		else
		{
			packageName = publisherClassName.substring(0, lastPointIdx);
			className = publisherClassName.substring(lastPointIdx+1);
		}
		// 生成Java类头
		StringBuilder sb = new StringBuilder();
		sb.append("package ").append(packageName).append(";\n");
		sb.append('\n');
		Set<String> importedClass = new HashSet<String>();
		for(int i=0;i<srcMethod.length;i++)
		{
			int modifiers = srcMethod[i].getModifiers();
			if(!Modifier.isStatic(modifiers)) throw new UnsupportedException("Can not publisher non-static method : " + srcMethod[i]);
			String tempClassName = srcMethod[i].getDeclaringClass().getName();
			if(!importedClass.contains(tempClassName))
			{
				importedClass.add(tempClassName);
				sb.append("import ").append(tempClassName).append(";\n");
			}
		}
		sb.append("import javax.jws.WebMethod;\n");
		sb.append("import javax.jws.WebParam;\n");
		sb.append("import javax.jws.WebResult;\n");
		sb.append("import javax.jws.WebService;\n");
		sb.append("import javax.jws.soap.SOAPBinding;\n");
		sb.append('\n');
		sb.append("@WebService(targetNamespace = \"").append(packageName).append("\")\n");
		sb.append("@SOAPBinding(style = SOAPBinding.Style.RPC)\n");
		sb.append('\n');
		sb.append("public class ").append(className).append('\n');
		sb.append("{\n");
		for(int i=0;i<srcMethod.length;i++)
		{
			// 生成Java方法头
			sb.append("\t@WebMethod(action=\"").append(action[i]).append("\",operationName=\"").append(operationName[i]).append("\",exclude=false)\n");
			sb.append("\t@WebResult(name=\"").append(resultName[i]).append("\")\n");
			sb.append("\tpublic ").append(srcMethod[i].getReturnType().getName()).append(' ').append(srcMethod[i].getName()).append('(');
			Class<?>[] paramTypes = srcMethod[i].getParameterTypes();
			for(int j=0;j<paramTypes.length;j++)
			{
				sb.append("@WebParam(name=\"avg").append(j).append("\")").append(paramTypes[j].getName()).append(" avg").append(j);
				if(j!=paramTypes.length-1) sb.append(", ");
			}
			sb.append(")\n");
			// 生成方法体
			sb.append("\t{\n");
			sb.append("\t\t");
			if(!"void".equals(srcMethod[i].getReturnType().getName())) sb.append("return ");
			sb.append(srcMethod[i].getDeclaringClass().getName()).append('.').append(srcMethod[i].getName()).append('(');
			for(int j=0;j<paramTypes.length;j++)
			{
				sb.append("avg").append(j);
				if(j!=paramTypes.length-1) sb.append(", ");
			}
			sb.append(");\n");
			sb.append("\t}\n");
			if(i!=srcMethod.length-1) sb.append('\n');
		}
		sb.append("}\n");
		return sb.toString();
	}
	
	public String getWebRoot()
	{
		return webRoot;
	}

	public String getDefaultPackage()
	{
		return defaultPackage;
	}
	
	public String getJavaHome()
	{
		return javaHome;
	}

	public static void main(String[] args) throws Exception
	{
		//System.out.println(System.getProperty("java.class.path"));
		//System.out.println(System.getProperty("java.library.path"));
		WebServicePublisher publisher = WebServicePublisher.getInstance();
		publisher.publisherMethod(ObjectUtil.class.getDeclaredMethod("objectToString", Object.class), "WSTest", "wstest", "o2Str", "objToString", "returnStr");
	}
}
