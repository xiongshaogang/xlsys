package xlsys.base.log;

import xlsys.base.io.xml.XmlModel;

/**
 * 系统Log抽象类
 * @author Lewis
 *
 */
public abstract class Log
{
	/**
	 * 信息日志等级
	 */
	public final static int LOG_LEVEL_INFO = 40;
	/**
	 * 警告日志等级
	 */
	public final static int LOG_LEVEL_WARN = 30;
	/**
	 * 错误日志等级
	 */
	public final static int LOG_LEVEL_ERROR = 20;
	/**
	 * 致命错误日志等级
	 */
	public final static int LOG_LEVEL_FATAL = 10;
	/**
	 * 不输出日志等级
	 */
	public final static int LOG_LEVEL_NONE = 0;
	
	protected int logLevel;
	
	protected Log(int logLevel, XmlModel params)
	{
		this.logLevel = logLevel;
	}
	
	/**
	 * 使用指定日志等级写日志.
	 * @param level 日志等级
	 * @param content 日志内容
	 * @return 成功返回写入的字节数，失败返回-1
	 */
	public int print(int level, Object content)
	{
		if(level<=logLevel)
		{
			return write(level, content);
		}
		return 0;
	}
	
	/**
	 * 使用当前日志等级写日志.
	 * @param content 日志内容
	 * @return
	 */
	public int print(Object content)
	{
		return print(logLevel, content);
	}
	
	/**
	 * 使用{@link #LOG_LEVEL_INFO}等级写日志
	 * @param content 日志内容
	 * @return
	 */
	public int printInfo(Object content)
	{
		return print(LOG_LEVEL_INFO, content);
	}
	
	/**
	 * 使用{@link #LOG_LEVEL_}等级写日志
	 * @param content 日志内容
	 * @return
	 */
	public int printWarn(Object content)
	{
		return print(LOG_LEVEL_WARN, content);
	}
	
	/**
	 * 使用{@link #LOG_LEVEL_ERROR}等级写日志
	 * @param content 日志内容
	 * @return
	 */
	public int printError(Object content)
	{
		return print(LOG_LEVEL_ERROR, content);
	}
	
	/**
	 * 使用{@link #LOG_LEVEL_FATAL}等级写日志
	 * @param content 日志内容
	 * @return
	 */
	public int printFatal(Object content)
	{
		return print(LOG_LEVEL_FATAL, content);
	}
	
	/**
	 * 使用指定的日志等级写日志
	 * @param level 日志等级
	 * @param content 日志内容
	 * @return
	 */
	protected abstract int write(int level, Object content);
	
	/**
	 * 使用指定的日志等级写日志. 该方法包含一个换行符
	 * @param level 日志等级
	 * @param content 日志内容
	 * @return
	 */
	public int println(int level, Object content)
	{
		if(level<=logLevel)
		{
			return writeln(level, content);
		}
		return 0;
	}
	
	/**
	 * 使用当前日志等级写日志. 该方法包含一个换行符
	 * @param content 日志内容
	 * @return
	 */
	public int println(Object content)
	{
		return println(logLevel, content);
	}
	
	/**
	 * 使用{@link #LOG_LEVEL_INFO}等级写日志. 该方法包含一个换行符
	 * @param content 日志内容
	 * @return
	 */
	public int printlnInfo(Object content)
	{
		return println(LOG_LEVEL_INFO, content);
	}
	
	/**
	 * 使用{@link #LOG_LEVEL_WARN}等级写日志. 该方法包含一个换行符
	 * @param content 日志内容
	 * @return
	 */
	public int printlnWarn(Object content)
	{
		return println(LOG_LEVEL_WARN, content);
	}
	
	/**
	 * 使用{@link #LOG_LEVEL_ERROR}等级写日志. 该方法包含一个换行符
	 * @param content 日志内容
	 * @return
	 */
	public int printlnError(Object content)
	{
		return println(LOG_LEVEL_ERROR, content);
	}
	
	/**
	 * 使用{@link #LOG_LEVEL_FATAL}等级写日志. 该方法包含一个换行符
	 * @param content 日志内容
	 * @return
	 */
	public int printlnFatal(Object content)
	{
		return println(LOG_LEVEL_FATAL, content);
	}
	
	/**
	 * 使用指定的日志等级写日志. 该方法包含一个换行符
	 * @param level 日志等级
	 * @param content 日志内容
	 * @return
	 */
	protected int writeln(int level, Object content)
	{
		int ret = write(level, content);
		if(ret>=0)
		{
			ret += write(level, "\n");
		}
		return ret;
	}

	/**
	 * 设置当前的日志等级
	 * @param logLevel
	 */
	public void setLogLevel(int logLevel)
	{
		this.logLevel = logLevel;
	}

	/**
	 * 获取当前的日志等级
	 * @return
	 */
	public int getLogLevel()
	{
		return logLevel;
	}
	
	public abstract void close();
}
