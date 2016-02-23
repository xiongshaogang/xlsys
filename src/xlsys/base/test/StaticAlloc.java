package xlsys.base.test;

import java.util.ArrayList;
import java.util.List;

public class StaticAlloc
{
	private static StaticAlloc alloc;
	
	private long count;
	private long batch;
	
	private StaticAlloc()
	{
		// TODO 在这里可以访问数据库来初始化count和batch的值，这里简单的将count初始化成100, batch初始化成50
		count = 100;
		batch = 50;
		System.out.println("从数据库初始化分配器，初始值为"+count+",每分配"+batch+"个号更新一次数据库");
	}
	
	public synchronized long getNextCount()
	{
		long ret = count;
		if(++count%batch==0)
		{
			// TODO 提交数据库更新数据
			System.out.println("数据库的值更新成了"+count);
		}
		return ret;
	}
	
	protected long getCurrent()
	{
		return count;
	}
	
	/**
	 * 使用单例模式来获取分号器
	 * @return
	 */
	public synchronized static StaticAlloc getInstance()
	{
		if(alloc==null) alloc = new StaticAlloc();
		return alloc;
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		// 分号器测试，开始多个线程来同时获取编号
		Runnable task = new Runnable()
		{
			public void run()
			{
				// 获取100个编号
				for(int i=0;i<100;i++)
				{
					long nextId = StaticAlloc.getInstance().getNextCount();
				}
			}
		};
		// 开启10个线程来获取编号
		List<Thread> threadList = new ArrayList<Thread>();
		for(int i=0;i<10;i++)
		{
			Thread thread = new Thread(task);
			threadList.add(thread);
			thread.start();
		}
		// 等待所有线程结束
		for(Thread thread : threadList)
		{
			thread.join();
		}
		// 输出此时分配器的值
		System.out.println("此时count的值为:"+StaticAlloc.getInstance().getCurrent());
	}
}
