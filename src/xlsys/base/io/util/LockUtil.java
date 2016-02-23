package xlsys.base.io.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import xlsys.base.XLSYS;
import xlsys.base.cfg.BaseConfig;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.session.Session;
import xlsys.base.util.ObjectUtil;
import xlsys.base.util.StringUtil;

/**
 * 同步锁工具
 * @author Lewis
 *
 */
public class LockUtil
{
	/**
	 * 超时时间，超过该时间后自动释放锁，防止死锁
	 */
	private int lockTimeout;
	/**
	 * 锁计时容器，每个锁对应一个倒计时，时间到了自动释放锁
	 */
	private Map<String, Integer> lockMap;
	/**
	 * 获取锁队列容器，每个锁对应一个等待获取锁的列表
	 */
	private Map<String, List<Integer>> waitMap;
	private static LockUtil lockUtil;
	/**
	 * 互斥锁，用来做内部同步
	 */
	private ReentrantLock lock;
	private ScheduledExecutorService scheduler;
	private ScheduledFuture<?> scheduledFuture;
	
	private LockUtil() throws Exception
	{
		super();
		lockMap = new HashMap<String, Integer>();
		waitMap = new HashMap<String, List<Integer>>();
		scheduler = Executors.newScheduledThreadPool(1);
		scheduledFuture = scheduler.scheduleAtFixedRate(new LockUtilTimer(), 1, 1, TimeUnit.SECONDS);
		lock = new ReentrantLock();
		XmlModel sysCfgXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
		XmlModel luXm = sysCfgXm.getChild("LockUtil");
		lockTimeout = Integer.parseInt(luXm.getChild("lockTimeout").getText());
	}

	public synchronized static LockUtil getInstance() throws Exception
	{
		if(lockUtil==null) lockUtil = new LockUtil();
		return lockUtil;
	}

	public void shutdown(boolean interrupt)
	{
		if(scheduledFuture!=null)
		{
			scheduledFuture.cancel(interrupt);
			scheduledFuture = null;
		}
		if(scheduler!=null&&!scheduler.isShutdown())
		{
			if(interrupt) scheduler.shutdownNow();
			else scheduler.shutdown();
			scheduler = null;
		}
		lockUtil = null;
	}

	/**
	 * 尝试获取锁，非阻塞方法，不成功返回null, 成功则返回获取到的key
	 * @param session
	 * @param str
	 * @return
	 */
	public String tryLockKey(Session session, String ... str)
	{
		String[] newStr = new String[str.length+1];
		newStr[0] = ""+ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
		for(int i=0;i<str.length;i++)
		{
			newStr[i+1] = str[i];
		}
		return tryLockKey(newStr);
	}
	
	/**
	 * 尝试获取锁，非阻塞方法，不成功返回null, 成功则返回获取到的key
	 * @param str
	 * @return
	 * @throws InterruptedException 
	 */
	public String tryLockKey(String ... str)
	{
		String key = null;
		String md5 = StringUtil.getMD5String(str);
		lock.lock();
		try
		{
			if(!lockMap.containsKey(md5)) key = getLockKey(str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			lock.unlock();
		}
		return key;
	}
	
	/**
	 * 尝试获取锁，阻塞方法，获取不到则阻塞，直到获取到为止
	 * @param session
	 * @param str
	 * @return
	 * @throws InterruptedException
	 */
	public String getLockKey(Session session, String ... str) throws InterruptedException
	{
		String[] newStr = new String[str.length+1];
		newStr[0] = ""+ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
		for(int i=0;i<str.length;i++)
		{
			newStr[i+1] = str[i];
		}
		return getLockKey(newStr);
	}
	
	/**
	 * 尝试获取锁，阻塞方法，获取不到则阻塞，直到获取到为止
	 * @param str
	 * @return
	 * @throws InterruptedException
	 */
	public String getLockKey(String ... str) throws InterruptedException
	{
		String md5 = StringUtil.getMD5String(str);
		lock.lock();
		try
		{
			while(lockMap.containsKey(md5))
			{
				Integer forLock = new Integer(0);
				// 该md5还在被使用，将当前线程加入此md5值对应的等待队列
				List<Integer> list = waitMap.get(md5);
				if(list==null)
				{
					list = new LinkedList<Integer>();
					waitMap.put(md5, list);
				}
				list.add(forLock);
				// 挂起线程
				synchronized(forLock)
				{
					lock.unlock();
					forLock.wait();
					lock.lock();
				}
			}
			// 获得权限，获取该md5给该线程
			lockMap.put(md5, lockTimeout);
		}
		finally
		{
			lock.unlock();
		}
		return md5;
	}
	
	public void releaseKey(String key)
	{
		lock.lock();
		try
		{
			// 释放锁
			lockMap.remove(key);
			// 唤醒等待该锁的队列第一个元素
			List<Integer> waitList = waitMap.get(key);
			if(waitList!=null&&!waitList.isEmpty())
			{
				Integer forLock = waitList.get(0);
				waitList.remove(0);
				synchronized(forLock)
				{
					forLock.notify();
				}
			}
		}
		finally
		{
			lock.unlock();
		}
	}
	
	private class LockUtilTimer extends Thread
	{
		@Override
		public void run()
		{
			lock.lock();
			try
			{
				for(Entry<String, Integer> entry : lockMap.entrySet())
				{
					String key = entry.getKey();
					int timeRemain = entry.getValue();
					if(--timeRemain<=0) releaseKey(key);
					else lockMap.put(key, timeRemain);
				}
			}
			finally
			{
				lock.unlock();
			}
		}
	}
}