package xlsys.base.thread;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.dom4j.DocumentException;

import xlsys.base.cfg.BaseConfig;
import xlsys.base.io.xml.XmlModel;

/**
 * 系统线程池
 * @author Lewis
 *
 */
public class XlsysThreadPool
{
	private ThreadPoolExecutor threadPoolExecutor;
	
	private static XlsysThreadPool xlsysThreadPool;
	
	private XlsysThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,int capacity)
	{
		// 大于最大线程数时所使用的等待队列
		BlockingQueue<Runnable> bq = null;
		if(capacity==0)
		{
			// 直接提交队列
			bq = new SynchronousQueue<Runnable>();
		}
		else if(capacity<0)
		{
			// 无界等待队列
			bq = new LinkedBlockingQueue<Runnable>();
		}
		else
		{
			// 有界等待队列
			bq = new ArrayBlockingQueue<Runnable>(capacity);
		}
		// 初始化线程池，给定的超出线程池处理范围的程序为 : 被拒绝的执行处理程序，该程序会自动抛出RejectedExecutionException
		threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, bq, new ThreadPoolExecutor.AbortPolicy());
	}
	
	/**
	 * 获取一个线程池实例
	 * @return
	 * @throws DocumentException
	 */
	public static synchronized XlsysThreadPool getInstance() throws DocumentException
	{
		if(xlsysThreadPool==null)
		{
			XmlModel sysCfgXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
			XmlModel threadPoolXm = sysCfgXm.getChild("ThreadPool");
			int corePoolSize = Integer.parseInt(threadPoolXm.getChild("corePoolSize").getText());
			int maximumPoolSize = Integer.parseInt(threadPoolXm.getChild("maximumPoolSize").getText());
			if(maximumPoolSize<=0) maximumPoolSize = Integer.MAX_VALUE;
			int keepAliveTime = Integer.parseInt(threadPoolXm.getChild("keepAliveTime").getText());
			if(keepAliveTime<0) keepAliveTime = 0;
			int queueCapacity = Integer.parseInt(threadPoolXm.getChild("queueCapacity").getText());
			xlsysThreadPool = new XlsysThreadPool(corePoolSize, maximumPoolSize, keepAliveTime, queueCapacity);
		}
		return xlsysThreadPool;
	}
	
	/**
	 * 执行一个实现Runnable接口的任务
	 * @param command
	 */
	public void execute(Runnable command)
	{	
		try
		{
			threadPoolExecutor.execute(command);
		}
		catch(RejectedExecutionException e)
		{
			rejectedExecution(command);
		}
		
	}
	
	/**
	 * 关闭线程池
	 */
	public void shutdown()
	{
		threadPoolExecutor.shutdown();
	}
	
	/**
	 * 立刻关闭线程池
	 * @return
	 */
	public List<Runnable> shutdownNow() 
	{
		return threadPoolExecutor.shutdownNow();
	}
	
	/**
	 * 当线程被拒绝时调用此方法.
	 * 此事件一般发生在没有可使用的资源时
	 * @param command
	 */
	protected void rejectedExecution(Runnable command){}
}
