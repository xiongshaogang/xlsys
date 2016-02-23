package xlsys.base.script;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.io.util.FileUtil;
import xlsys.base.log.LogUtil;

/**
 * 系统编译器，可动态编译Java源代码
 * @author Lewis
 *
 */
public class XlsysCompiler
{
	private static XlsysCompiler xlsysCompiler; 
	private JavaCompiler compiler;
	private DiagnosticCollector<JavaFileObject> diagnostics;
	private XlsysJavaFileManager javaFileManager;
	private List<XlsysJavaFileObject> compilationUnits;
	private XlsysClassLoader xlsysClassLoader;
	
	private XlsysCompiler() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		xlsysClassLoader = XlsysClassLoader.getInstance();
		Class<?> jtc = xlsysClassLoader.loadClass("com.sun.tools.javac.api.JavacTool");
		compiler = (JavaCompiler) jtc.newInstance();
		// JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		diagnostics = new DiagnosticCollector<JavaFileObject>();
		JavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		javaFileManager = new XlsysJavaFileManager(fileManager, xlsysClassLoader);
		compilationUnits = new ArrayList<XlsysJavaFileObject>();
	} 
	
	/**
	 * 获取一个编译类实例
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws DocumentException
	 */
	public static synchronized XlsysCompiler getInstance() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		if(xlsysCompiler==null) xlsysCompiler = new XlsysCompiler();
		return xlsysCompiler;
	}
	
	/**
	 * 添加 Java源代码
	 * @param className Java类全名称.包含包名和类名
	 * @param sourceFile 源文件
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public boolean addSource(String className, File sourceFile) throws URISyntaxException, IOException
	{
		boolean success = false;
		if(sourceFile!=null&&sourceFile.exists()&&sourceFile.isFile())
		{
			byte[] b = FileUtil.getByteFromFile(sourceFile.getAbsolutePath());
			success = addSource(className, new String(b));
		}
		return success;
	}
	
	/**
	 * 添加 Java源代码
	 * @param className Java类全名称.包含包名和类名
	 * @param source 源代码字符串
	 * @return
	 * @throws URISyntaxException
	 */
	public boolean addSource(String className, String source) throws URISyntaxException
	{
		boolean success = false;
		XlsysJavaFileObject xjfo = new XlsysJavaFileObject(className, source);
		compilationUnits.add(xjfo);
		success = true;
		return success;
	}
	
	/**
	 * 编译代码
	 * @param options 编译时传入的参数列表. 该参数为执行javac时的参数
	 * @return 编译成功返回true, 否则返回false
	 */
	public boolean compile(List<String> options)
	{
		boolean success = false;
		if(!compilationUnits.isEmpty())
		{
			CompilationTask task = compiler.getTask(null, javaFileManager, diagnostics, options, null, compilationUnits);
			Boolean result = task.call();
			if (result == null || !result.booleanValue())
			{
				for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics())
				{
					LogUtil.printlnError("Error on line " + diagnostic.getLineNumber() + " at "+diagnostic.getSource().getName()+" : " + diagnostic.getMessage(null));
				}
			}
			else
			{
				success = true;
			}
		}
		return success;
	}
	
	/**
	 * 编译代码
	 * @return 编译成功返回true, 否则返回false
	 */
	public boolean compile()
	{
		return compile(null);
	}
	
	/**
	 * 获取编译的错误信息
	 */
	public DiagnosticCollector<JavaFileObject> getDiagnostics()
	{
		return diagnostics;
	}

	/**
	 * 获取对应的Java文件管理器
	 * @return
	 */
	public XlsysJavaFileManager getJavaFileManager()
	{
		return javaFileManager;
	}
	
}
