package xlsys.base.script;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.Bundle;

import xlsys.base.XlsysBaseActivator;
import xlsys.base.cfg.BaseConfig;
import xlsys.base.io.util.IOUtil;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.log.LogUtil;

/**
 * 系统类加载器
 * @author Lewis
 *
 */
public class XlsysClassLoader extends URLClassLoader
{
	private static XlsysClassLoader xlsysClassLoader;
	private static Map<String, Class<?>> baseTypeClassMap;
	
	private Map<String, XlsysJavaFileObject> customsClasses;
	
	protected XlsysClassLoader()
	{
		super(new URL[0], Thread.currentThread().getContextClassLoader());
		init();
	}
	
	public void addURLs(URL[] urls)
	{
		for(URL url : urls)
		{
			addURL(url);
		}
	}

	public static synchronized Map<String, Class<?>> getBaseTypeClassMap()
	{
		if(baseTypeClassMap==null)
		{
			baseTypeClassMap = new HashMap<String, Class<?>>();
			baseTypeClassMap.put("int", int.class);
			baseTypeClassMap.put("byte", byte.class);
			baseTypeClassMap.put("char", char.class);
			baseTypeClassMap.put("short", short.class);
			baseTypeClassMap.put("long", long.class);
			baseTypeClassMap.put("float", float.class);
			baseTypeClassMap.put("double", double.class);
			baseTypeClassMap.put("boolean", boolean.class);
		}
		return baseTypeClassMap;
	}
	
	public static synchronized XlsysClassLoader getInstance()
	{
		if(xlsysClassLoader==null) xlsysClassLoader = new XlsysClassLoader();
		return xlsysClassLoader;
	}
	
	private void init()
	{
		if(customsClasses==null)
		{
			customsClasses = new Hashtable<String, XlsysJavaFileObject>();
			try
			{
				XmlModel sysCfgXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
				String javaHome = sysCfgXm.getChild("JAVA_HOME").getText();
				File file = new File(javaHome);
				if(file.exists()&&file.isDirectory())
				{
					addURL(new URL("file:"+File.separator+file.getAbsolutePath()+File.separator+"lib"+File.separator+"tools.jar"));
				}
			}
			catch(Exception e)
			{
				LogUtil.printlnError(e);
			}
		}
	}
	
	public boolean containsClass(String name)
	{
		boolean contains = false;
		Class<?> toFind = null;
		try
		{
			toFind = loadClass(name);
			if(toFind!=null) contains = true;
		}
		catch (ClassNotFoundException e)
		{
			contains = false;
		}
		return contains; 
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException
	{
		Class<?> toFind = null; 
		try
		{
			toFind = super.findClass(name);
		}
		catch(ClassNotFoundException e){}
		if(toFind==null)
		{
			XlsysJavaFileObject file = customsClasses.get(name);
			if(file!=null)
			{
				ByteArrayInputStream bais = (ByteArrayInputStream) file.openInputStream();
				byte[] b = new byte[bais.available()];
				try
				{
					bais.read(b);
				}
				catch (IOException e)
				{
					LogUtil.printlnError(e);
				}
				finally
				{
					IOUtil.close(bais);
				}
				if(b.length>0) toFind = defineClass(name, b, 0, b.length);
			}
		}
		if(toFind==null&&XlsysBaseActivator.getBundleContext()!=null)
		{
			// 从BundleContext对象中取得所有的bundles
			Bundle[] bundles = XlsysBaseActivator.getBundleContext().getBundles();
			// 调用每个bundle的classLoader来查找查找该类
			for(Bundle bundle : bundles)
			{
				if(bundle.getState()==Bundle.ACTIVE&&bundle.getSymbolicName().startsWith("xlsys."))
				{
					try
					{
						toFind = bundle.loadClass(name);
					}
					catch(Exception e){}
					if(toFind!=null) break;
				}
			}
		}
		if(toFind==null) throw new ClassNotFoundException("Cannot find class : "+name);
		return toFind;
	}
	
	public XlsysJavaFileObject putXlsysJavaFileObject(String baseName, CharSequence source) throws URISyntaxException
	{
		XlsysJavaFileObject xjfo = new XlsysJavaFileObject(baseName, source);
		customsClasses.put(baseName, xjfo);
		return xjfo;
	}
	
	public XlsysJavaFileObject putXlsysJavaFileObject(String baseName, byte[] binary) throws URISyntaxException, IOException
	{
		XlsysJavaFileObject xjfo = new XlsysJavaFileObject(baseName, binary);
		customsClasses.put(baseName, xjfo);
		return xjfo;
	}
	
	public XlsysJavaFileObject putXlsysJavaFileObject(String baseName, XlsysJavaFileObject xjfo)
	{
		customsClasses.put(baseName, xjfo);
		return xjfo;
	}
	
	public XlsysJavaFileObject getXlsysJavaFileObject(String baseName)
	{
		return customsClasses.get(baseName);
	}
	
	protected Map<String, XlsysJavaFileObject> getCustomsClasses()
	{
		return customsClasses;
	}
}
