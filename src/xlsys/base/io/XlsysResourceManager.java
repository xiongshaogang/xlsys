package xlsys.base.io;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import xlsys.base.cfg.BaseConfig;
import xlsys.base.io.util.FileUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.util.ReflectUtil;

public abstract class XlsysResourceManager
{
	private static XlsysResourceManager manager;
	
	// <name, url>
	private Map<String ,String> nuMap;
	// <name, path>
	private Map<String ,String> npMap;
	// <url, name>
	private Map<String ,String> unMap;
	// <url, path>
	private Map<String ,String> upMap;
	// <path, name>
	private Map<String ,String> pnMap;
	// <path, url>
	private Map<String ,String> puMap;
	
	protected XlsysResourceManager()
	{
		nuMap = new HashMap<String, String>();
		npMap = new HashMap<String, String>();
		unMap = new HashMap<String, String>();
		upMap = new HashMap<String, String>();
		pnMap = new HashMap<String, String>();
		puMap = new HashMap<String, String>();
	}
	
	public synchronized static XlsysResourceManager getInstance()
	{
		if(manager==null)
		{
			try
			{
				XmlModel sysCfgXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
				XmlModel managerXm = sysCfgXm.getChild("Resource").getChild("Manager");
				manager = (XlsysResourceManager) ReflectUtil.getInstanceFromXm(managerXm, "className", "param", "field");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return manager;
	}
	
	/**
	 * 注册资源.
	 * @param resourceName 资源名称
	 * @param in 读入流
	 * @param resourceSuffix 资源后缀
	 * @throws Exception
	 */
	public synchronized void registResource(String resourceName, InputStream in, String resourceSuffix) throws Exception
	{
		registResource(resourceName, IOUtil.readBytesFromInputStream(in, -1), resourceSuffix);
	}
	
	/**
	 * 注册资源.
	 * @param resourceName 资源名称
	 * @param resourceBytes 资源二进制数据
	 * @param resourceSuffix 资源后缀
	 * @throws Exception
	 */
	public synchronized void registResource(String resourceName, byte[] resourceBytes, String resourceSuffix) throws Exception
	{
		registResource(resourceName, resourceBytes, resourceSuffix, FileUtil.getFileMd5(resourceBytes));
	}
	
	/**
	 * 注册资源.
	 * @param resourceName 资源名称
	 * @param resourceBytes 资源二进制数据
	 * @param resourceSuffix 资源后缀
	 * @param resourceMd5 资源MD5值
	 * @throws Exception
	 */
	public synchronized void registResource(String resourceName, byte[] resourceBytes, String resourceSuffix, String resourceMd5) throws Exception
	{
		String resourcePath = createResourcePath(resourceBytes, resourceSuffix, resourceMd5);
		String resourceUrl = createResourceUrl(resourcePath, resourceMd5);
		registResource(resourceName, resourceUrl, resourcePath);
	}
	
	/**
	 * 注册资源.
	 * @param resourceName 资源名称
	 * @param resourceFile 资源文件
	 * @throws Exception
	 */
	public synchronized void registResource(String resourceName, File resourceFile) throws Exception
	{
		registResource(resourceName, resourceFile, FileUtil.getFileMd5(FileUtil.getByteFromFile(resourceFile.getCanonicalPath())));
	}
	
	/**
	 * 注册资源.
	 * @param resourceName 资源名称
	 * @param resourceFile 资源文件
	 * @param resourceMd5 资源MD5值
	 * @throws Exception
	 */
	public synchronized void registResource(String resourceName, File resourceFile, String resourceMd5) throws Exception
	{
		String filePath = resourceFile.getCanonicalPath();
		String url = createResourceUrl(filePath, resourceMd5);
		registResource(resourceName, url, filePath);
	}
	
	/**
	 * 创建资源URL. 该方法中需要为资源创建一个唯一的URL. 使用该URL可直接访问到该资源.
	 * @param resourcePath 资源路径
	 * @param resourceMd5 资源MD5值
	 * @return
	 * @throws Exception
	 */
	protected abstract String createResourceUrl(String resourcePath, String resourceMd5) throws Exception;
	
	/**
	 * 注册资源.
	 * @param resourceName 资源名称
	 * @param resourceUrl 资源URL
	 * @param in 资源读入流
	 * @param resourceSuffix
	 * @throws Exception
	 */
	public synchronized void registResource(String resourceName, String resourceUrl, InputStream in, String resourceSuffix) throws Exception
	{
		registResource(resourceName, resourceUrl, IOUtil.readBytesFromInputStream(in, -1), resourceSuffix);
	}
	
	public synchronized void registResource(String resourceName, String resourceUrl, byte[] resourceBytes, String resourceSuffix) throws Exception
	{
		registResource(resourceName, resourceUrl, resourceBytes, resourceSuffix, FileUtil.getFileMd5(resourceBytes));
	}
	
	public synchronized void registResource(String resourceName, String resourceUrl, byte[] resourceBytes, String resourceSuffix, String resourceMd5) throws Exception
	{
		registResource(resourceName, resourceUrl, createResourcePath(resourceBytes, resourceSuffix, resourceMd5));
	}
	
	protected abstract String createResourcePath(byte[] resourceBytes, String resourceSuffix, String resourceMd5) throws Exception;
	
	public synchronized void registResource(String resourceName, String url, String filePath)
	{
		nuMap.put(resourceName, url);
		npMap.put(resourceName, filePath);
		unMap.put(url, resourceName);
		upMap.put(url, filePath);
		pnMap.put(filePath, resourceName);
		puMap.put(filePath, url);
	}
	
	public synchronized boolean containsResourceWithName(String resourceName)
	{
		return nuMap.containsKey(resourceName)||npMap.containsKey(resourceName);
	}
	
	public synchronized boolean containsResourceWithUrl(String resourceUrl)
	{
		return unMap.containsKey(resourceUrl)||upMap.containsKey(resourceUrl);
	}
	
	public synchronized boolean containsResourceWithPath(String resourcePath)
	{
		return pnMap.containsKey(resourcePath)||puMap.containsKey(resourcePath);
	}
	
	public synchronized String getResourceNameWithUrl(String resourceUrl)
	{
		return unMap.get(resourceUrl);
	}
	
	public synchronized String getResourceNameWithPath(String resourcePath)
	{
		return pnMap.get(resourcePath);
	}
	
	public synchronized String getResourceUrlWithName(String resourceName)
	{
		return nuMap.get(resourceName);
	}
	
	public synchronized String getResourceUrlWithPath(String resourcePath)
	{
		return puMap.get(resourcePath);
	}
	
	public synchronized String getResourcePathWithName(String resourceName)
	{
		return npMap.get(resourceName);
	}
	
	public synchronized String getResourcePathWithUrl(String resourceUrl)
	{
		return upMap.get(resourceUrl);
	}
}
