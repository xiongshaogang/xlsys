package xlsys.base.util;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Constructor;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import xlsys.base.LibraryLoader;
import xlsys.base.XLSYS;
import xlsys.base.log.Log;
import xlsys.base.log.LogFactory;

/**
 * 系统工具类
 * @author Lewis
 *
 */
public class SystemUtil
{
	private static final Runtime runtime = Runtime.getRuntime();
	
	private static boolean systemInited = false;
	
	/**
	 * 系统初始化
	 * @throws IOException 
	 */
	public synchronized static void systemInit() throws IOException
	{
		if(!systemInited)
		{
			// 尝试从虚拟机属性中查找固定配置路径，如果存在的话，使用固定配置路径，修正osgi中多个实例启动后的路径重复拼接问题
			String absolutelyConfigPath = System.getProperty(XLSYS.SYSTEM_PROPERTY_ABSOLUTELY_CONFIG_PATH);
			if(absolutelyConfigPath==null)
			{
				String configRootPath = System.getProperty(XLSYS.SYSTEM_PROPERTY_CONFIG_PATH);
				// 如果存在配置路径, 使用配置路径来读取配置文件
				if(configRootPath!=null)
				{
					String programDir = new File(configRootPath).getCanonicalPath();
					System.setProperty("user.dir", programDir);
					System.setProperty(XLSYS.SYSTEM_PROPERTY_ABSOLUTELY_CONFIG_PATH, programDir);
				}
			}
			
			LibraryLoader.loadLibrary();
		}
	}
	
	public static long getUsedMemory()
    {
        return runtime.totalMemory()-runtime.freeMemory();
    }
	
	private static void runGC() throws Exception
    {
        // It helps to call Runtime.gc()
        // using several method calls:
        for (int r = 0; r < 4; ++ r) _runGC ();
    }
	
	private static void _runGC() throws Exception
    {
        long usedMem1 = getUsedMemory();
        long usedMem2 = Long.MAX_VALUE;
        for(int i=0;(usedMem1<usedMem2)&&(i<500);i++)
        {
        	runtime.runFinalization();
        	runtime.gc();
            Thread.yield();
            usedMem2 = usedMem1;
            usedMem1 = getUsedMemory();
        }
    }
	
    public static int sizeof(Class<?> javaClass, Object ... param) throws Exception
    {
        // Warm up all classes/methods we will use
    	Class<?>[] paramClass = new Class<?>[param.length];
        for(int i=0;i<param.length;i++)
        {
        	paramClass[i] = param[i].getClass();
        }
        Constructor<?> c = javaClass.getDeclaredConstructor(paramClass);
        c.setAccessible(true);
        runGC ();
        getUsedMemory();
        // Array to keep strong references to allocated objects
        final int count = 100000;
        Object[] objects = new Object[count];
        
        long heap1 = 0;
        // Allocate count+1 objects, discard the first one
        
        for (int i=-1;i<count;i++)
        {
            Object object = null;
            
            // Instantiate your data here and assign it to object
            
            object = c.newInstance(param);
            if (i>=0) objects[i] = object;
            else
            {
                object = null; // Discard the warm up object
                runGC ();
                heap1 = getUsedMemory(); // Take a before heap snapshot
            }
        }
        runGC ();
        long heap2 = getUsedMemory(); // Take an after heap snapshot:
        
        final int size = Math.round (((float)(heap2 - heap1))/count);
        for (int i = 0; i < count; ++ i) objects [i] = null;
        objects = null;
        return size;
    }
    
    public static InetAddress[] getHostInet6Addresses() throws SocketException
    {
    	return getHostInetAddresses(Inet6Address.class);
    }
    
    public static InetAddress[] getHostInet4Addresses() throws SocketException
    {
    	return getHostInetAddresses(Inet4Address.class);
    }
    
    public static InetAddress[] getHostInetAddresses(Class<? extends InetAddress> inetAddressClass) throws SocketException
    {
    	Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
    	List<InetAddress> inetAddressList = new ArrayList<InetAddress>();
    	while(networkInterfaces.hasMoreElements())
    	{
    		NetworkInterface networkInterface = networkInterfaces.nextElement();
    		Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
    		while(inetAddresses.hasMoreElements())
    		{
    			InetAddress inetAddress = inetAddresses.nextElement();
    			if(inetAddressClass.isAssignableFrom(inetAddress.getClass()))
    			{
    				inetAddressList.add(inetAddress);
    			}
    		}
    	}
    	return inetAddressList.toArray(new InetAddress[inetAddressList.size()]);
    }
    
    /**
     * 返回系统当前日志级别是否为debug
     * @return
     */
    public static boolean isDebug()
	{
    	boolean flag = false;
    	try
    	{
    		String logLevelStr = System.getProperty(XLSYS.ENV_LOG_LEVEL);
        	if(logLevelStr==null)
        	{
        		LogFactory.getFactoryInstance();
        		logLevelStr = System.getProperty(XLSYS.ENV_LOG_LEVEL);
        	}
    		int logLevel = ObjectUtil.objectToInt(logLevelStr);
    		flag = logLevel>=Log.LOG_LEVEL_DEBUG;
    	}
    	catch(Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    	return flag;
	}
    
    /**
     * 返回当前操作系统的行分隔符
     * @return
     */
    public static String getLineSeparator()
    {
    	return System.getProperty("line.separator");
    }
    
    /**
     * 获取当前系统国别代码
     * @return
     */
    public static String getCountry()
    {
    	return System.getProperty("user.country");
    }
    
    /**
     * 获取临时目录
     * @return
     */
    public static String getIOTmpDir()
    {
    	return System.getProperty("java.io.tmpdir");
    }
    
    /**
     * 获取系统内部数据所使用的编码格式
     * @return
     */
    public static String getInternalEncoding()
    {
    	return System.getProperty("sun.jnu.encoding");
    }
    
    /**
     * 获取系统读写文件时所使用的编码格式
     * @return
     */
    public static String getFileEncoding()
    {
    	return System.getProperty("file.encoding");
    }
    
    /**
     * 获取操作系统名称
     * @return
     */
    public static String getOsName()
    {
    	return System.getProperty("os.name");
    }
    
    /**
     * 获取操作系统架构
     * @return
     */
    public static String getOsArch()
    {
    	return System.getProperty("os.arch");
    }
    
    /**
     * 获取操作系统当前版本
     * @return
     */
    public static String getOsVersion()
    {
    	return System.getProperty("os.version");
    }
    
    /**
     * 获取用户主目录
     * @return
     */
    public static String getUserHome()
    {
    	return System.getProperty("user.home");
    }
    
    /**
     * 获取用户当前工作路径
     * @return
     */
    public static String getUserDir()
    {
    	return System.getProperty("user.dir");
    }
    
    /**
     * 获取Java安装路径
     * @return
     */
    public static String getJavaHome()
    {
    	return System.getProperty("java.home");
    }
    
    /**
     * 获取Java版本
     * @return
     */
    public static String getJavaVersion()
    {
    	return System.getProperty("java.version");
    }
    
    /**
     * 获取用户当前语言
     * @return
     */
    public static String getUserLanguage()
    {
    	return System.getProperty("user.language");
    }
    
    /**
     * 获取CPU数量
     * @return
     */
    public static int getCpuCount()
    {
    	return Runtime.getRuntime().availableProcessors();
    }
    
    public static Set<byte[]> getAllMacAddress()
    {
    	Set<byte[]> macSet = new HashSet<byte[]>();
    	try
    	{
    		Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
    		while(networkInterfaces.hasMoreElements())
    		{
    			NetworkInterface networkInterface = networkInterfaces.nextElement();
    			byte[] bytes = networkInterface.getHardwareAddress();
    			if(bytes!=null&&bytes.length>0)
    			{
    				boolean contains = false;
    				for(byte[] b : macSet)
    				{
    					if(Arrays.equals(b, bytes))
    					{
    						contains = true;
    						break;
    					}
    				}
    				if(!contains) macSet.add(bytes);
    			}
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return macSet;
    }
    
    /**
     * 获取本机的所有Mac地址的字符串表示形式数组
     * @return
     */
    public static String[] getMacAddresses()
    {
    	Set<byte[]> macSet = getAllMacAddress();
    	String[] macArr = new String[macSet.size()];
    	int idx = 0;
    	for(byte[] bytes : macSet)
    	{
    		String mac = "";
			for(int i=0;i<bytes.length;i++)
			{
				mac += Integer.toHexString(0xFF&bytes[i]);
				if(i!=bytes.length-1) mac += ':';
			}
			macArr[idx++] = mac;
    	}
    	return macArr;
    }
    
    public static List<String> getJVMArgument()
    {
    	RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
		return runtimeMxBean.getInputArguments();
    }
    
    public static String[] getProgramArgument()
    {
    	return System.getProperty("sun.java.command").split(" +");
    }
    
    public static void main(String[] args) throws Exception
    {
    	System.out.println(sizeof(String.class, "1"));
    }
}
