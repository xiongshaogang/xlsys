package xlsys.base.script;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardLocation;

import org.dom4j.DocumentException;

import xlsys.base.log.LogUtil;

/**
 * 系统Java文件管理器
 * @author Lewis
 *
 */
public class XlsysJavaFileManager extends
		ForwardingJavaFileManager<JavaFileManager>
{
	private XlsysClassLoader xlsysClassLoader;
	private JavaFileManager parent;

	protected XlsysJavaFileManager(JavaFileManager fileManager,
			XlsysClassLoader xlsysClassLoader) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		super(fileManager);
		parent = fileManager;
		this.xlsysClassLoader = xlsysClassLoader;
	}

	/**
	 * 获取准备读取的类文件
	 */
	@Override
	public FileObject getFileForInput(Location location, String packageName,
			String relativeName) throws IOException
	{
		FileObject o = super.getFileForInput(location, packageName, relativeName);
		if(o==null) o = xlsysClassLoader.getXlsysJavaFileObject(packageName+"."+relativeName);
		return o;
	}

	/**
	 * 获取准备写入的类文件
	 */
	@Override
	public JavaFileObject getJavaFileForOutput(Location location,
			String qualifiedName, Kind kind, FileObject outputFile)
			throws IOException
	{
		XlsysJavaFileObject file = null;
		try
		{
			file = new XlsysJavaFileObject(qualifiedName, kind);
			xlsysClassLoader.putXlsysJavaFileObject(qualifiedName, file);
		}
		catch (URISyntaxException e)
		{
			LogUtil.printlnError(e);
		}
		return file;
	}

	@Override
	public ClassLoader getClassLoader(JavaFileManager.Location location)
	{
		return xlsysClassLoader;
	}

	@Override
	public String inferBinaryName(Location loc, JavaFileObject file)
	{
		String result = null;
		if (file instanceof XlsysJavaFileObject)
			result = file.getName();
		else
			result = super.inferBinaryName(loc, file);
		return result;
	}

	@Override
	public Iterable<JavaFileObject> list(Location location, String packageName,
			Set<Kind> kinds, boolean recurse) throws IOException
	{
		Iterable<JavaFileObject> result = super.list(location, packageName, kinds, recurse);
		List<JavaFileObject> files = new ArrayList<JavaFileObject>();
		if (location == StandardLocation.CLASS_PATH && kinds.contains(JavaFileObject.Kind.CLASS))
		{
			for (XlsysJavaFileObject file : xlsysClassLoader.getCustomsClasses().values())
			{
				if (file.getKind() == Kind.CLASS && file.getName().startsWith(packageName))
				{
					files.add(file);
				}
			}
		}
		else if (location == StandardLocation.SOURCE_PATH && kinds.contains(JavaFileObject.Kind.SOURCE))
		{
			for (XlsysJavaFileObject file : xlsysClassLoader.getCustomsClasses().values())
			{
				if (file.getKind() == Kind.SOURCE && file.getName().startsWith(packageName))
				{
					files.add(file);
				}
			}
		}
		for (JavaFileObject file : result)
		{
			files.add(file);
		}
		return files;
	}

	public JavaFileManager getParent()
	{
		return parent;
	}

}
