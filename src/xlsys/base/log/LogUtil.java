package xlsys.base.log;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.script.XlsysClassLoader;

/**
 * 日志打印工具类
 * @author Lewis
 *
 */
public class LogUtil
{
	private static boolean hasDom4j = true;
	
	/**
	 * 使用系统定义的默认日志对象打印日志
	 * @param level 日志等级
	 * @param content 日志内容
	 * @return 成功返回true, 失败返回false
	 */
	public static boolean println(int level, Object content)
	{
		boolean success = false;
		if(hasDom4j)
		{
			try
			{
				// 查询有没有DocumentException类，如果没有，则说明没有使用config.xml说明文件，有可能是运行在android客户端中的程序
				XlsysClassLoader.getInstance().loadClass("org.dom4j.DocumentException");
				Log log = (Log) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_LOG).getInstance();
				log.println(level, content);
				success = true;
			}
			catch(Exception e)
			{
				hasDom4j = false;
				success = false;
			}
		}
		return success;
	}
	
	/**
	 * 等价于调用println(Log.LOG_LEVEL_INFO, content)
	 * @param content
	 * @return
	 */
	public static boolean printlnInfo(Object content)
	{
		return println(Log.LOG_LEVEL_INFO, content);
	}
	
	/**
	 * 等价于调用println(Log.LOG_LEVEL_WARN, content)
	 * @param content
	 * @return
	 */
	public static boolean printlnWarn(Object content)
	{
		return println(Log.LOG_LEVEL_WARN, content);
	}
	
	/**
	 * 等价于调用println(Log.LOG_LEVEL_ERROR, content)
	 * @param content
	 * @return
	 */
	public static boolean printlnError(Object content)
	{
		return println(Log.LOG_LEVEL_ERROR, content);
	}
	
	/**
	 * 等价于调用println(Log.LOG_LEVEL_FATAL, content)
	 * @param content
	 * @return
	 */
	public static boolean printlnFatal(Object content)
	{
		return println(Log.LOG_LEVEL_FATAL, content);
	}
}
