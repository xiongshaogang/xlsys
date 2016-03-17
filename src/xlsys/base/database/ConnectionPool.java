package xlsys.base.database;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;

import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.buffer.BufferManager;
import xlsys.base.buffer.BufferPool;
import xlsys.base.buffer.MutiLRUBufferPool;
import xlsys.base.buffer.XlsysBuffer;
import xlsys.base.database.util.DBUtil;
import xlsys.base.exception.AlreadyClosedException;
import xlsys.base.exception.ImpossibleSituationException;
import xlsys.base.exception.ParameterNotEnoughException;
import xlsys.base.log.LogUtil;
import xlsys.base.util.ObjectUtil;

/**
 * 数据库连接池抽象类，要实现数据库连接池必须继承该类
 * @author 刘旭东
 * 
 */
public abstract class ConnectionPool extends Thread implements XlsysBuffer
{
	public static final String BUFFER_KEY_TABLE_NAME = "_BUFFER_KEY_TABLE_NAME";
	
	private int id;
	private String description;
	private String dataSource;
	private String user;
	private String password;
	private int corePoolSize;
	private int maximumPoolSize;
	private int keepAliveTime;
	private int queueCapacity;
	private BufferPool<String, TableInfo> tableInfoBuffer;

	private boolean corePoolTimeOut; // 是否允许核心池使用超时关闭策略
	private boolean close;

	private Set<Connection> busyConSet;
	private Map<Connection, Long> freeConMap;
	private BlockingQueue<Integer> bq;

	protected ConnectionPool(int id, String description, String dataSource, String user, String password,
			int corePoolSize, int maximumPoolSize, int keepAliveTime,
			int queueCapacity) throws NoSuchMethodException, SecurityException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, DocumentException, ParameterNotEnoughException
	{
		BufferManager.getInstance().registerBuffer("_DB"+XLSYS.BUFFER_TABLE_INFO_PREFIX+id, this);
		corePoolTimeOut = false;
		close = false;
		this.id = id;
		this.description = description;
		this.dataSource = dataSource;
		this.user = user;
		this.password = password;
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maximumPoolSize;
		this.keepAliveTime = keepAliveTime;
		this.queueCapacity = queueCapacity;
		tableInfoBuffer = new MutiLRUBufferPool<String, TableInfo>();
		if (maximumPoolSize < corePoolSize)
			maximumPoolSize = corePoolSize;
		busyConSet = new HashSet<Connection>();
		freeConMap = new HashMap<Connection, Long>();
		if (queueCapacity == 0)
		{
			// 直接提交队列
			bq = new SynchronousQueue<Integer>();
		}
		else if (queueCapacity < 0)
		{
			// 无界等待队列
			bq = new LinkedBlockingQueue<Integer>();
		}
		else
		{
			// 有界等待队列
			bq = new ArrayBlockingQueue<Integer>(queueCapacity);
		}
	}

	/**
	 * 是否允许核心连接超时
	 * @param value
	 */
	public void allowCorePoolTimeOut(boolean value)
	{
		corePoolTimeOut = value;
	}

	/**
	 * 从池中获取可使用的DataBase
	 * @return 如果成功，返回DataBase实例，失败返回null
	 */
	private synchronized DataBase getDataBaseFromPool()
	{
		DataBase dataBase = null;
		Connection con = null;
		if (freeConMap.size() > 0)
		{
			// 如果还有空闲线程
			con = freeConMap.keySet().iterator().next();
			freeConMap.remove(con);
		}
		else
		{
			// 如果没有空闲线程
			if (busyConSet.size() < maximumPoolSize)
			{
				// 如果还没有达到线程池上限
				con = createConnection();
			}
		}
		if (con != null)
		{
			busyConSet.add(con);
			dataBase = newDataBase(con);
		}
		return dataBase;
	}
	
	/**
	 * 重置DataBase中连接
	 * @param dataBase
	 * @throws AlreadyClosedException
	 */
	protected synchronized void replaceConnection(DataBase dataBase) throws AlreadyClosedException
	{
		checkStatus();
		Connection con = dataBase.getConnection();
		try
		{
			if(con!=null&&!con.isClosed()) con.close();
		}
		catch(Exception e){}
		if(!dataBase.isClose())
		{
			busyConSet.remove(con);
			int retryMax = 5;
			int retryCount = 0;
			try
			{
				do
				{
					LogUtil.printlnWarn("Recreate " + dataBase.getDataBaseId() + " DataBase Connection : " + ++retryCount);
					con = createConnection();
				}
				while((con==null||con.isClosed())&&retryCount<=retryMax);
			}
			catch(SQLException e) {}
			busyConSet.add(con);
			dataBase.setConnection(con);
		}
	}

	/**
	 * 获取还未使用的DataBase
	 * @return
	 * @throws InterruptedException
	 * @throws AlreadyClosedException
	 */
	public DataBase getNewDataBase() throws InterruptedException,
			AlreadyClosedException
	{
		checkStatus();
		DataBase dataBase = null;
		do
		{
			dataBase = getDataBaseFromPool();
			if (dataBase == null)
			{
				// 如果连接池已经最大，则先将当前线程放入队列中,并挂起当前线程
				Integer forLock = new Integer(0);
				Exception e = new Exception("注意！！数据库连接池已经用完！");
				System.err.println(ObjectUtil.objectToString(e));
				if (bq.offer(forLock))
				{
					// 加入队列成功,则将当前线程挂起
					synchronized(forLock)
					{
						forLock.wait();
					}
				}
				else
				{
					// 如果队列已满
					throw new RejectedExecutionException("连接池等待队列已满");
				}
			}
		}
		while (dataBase == null);
		return dataBase;
	}

	/**
	 * 获取共享的DataBase。注意，该DataBase已经被其他线程所使用。危险方法，不建议使用
	 * @return
	 * @throws InterruptedException
	 * @throws AlreadyClosedException
	 */
	@Deprecated
	public synchronized DataBase getSharedDataBase()
			throws InterruptedException, AlreadyClosedException
	{
		checkStatus();
		DataBase dataBase = null;
		if (busyConSet.size() == 0)
		{
			dataBase = getNewDataBase();
		}
		else
		{
			Connection con = busyConSet.iterator().next();
			dataBase = newDataBase(con);
		}
		return dataBase;
	}

	/**
	 * 建立数据库连接
	 * @return
	 */
	protected abstract Connection createConnection();

	/**
	 * 使用传入的数据库连接来构造一个对应的DataBase
	 * @param con
	 * @return
	 */
	protected abstract DataBase newDataBase(Connection con);

	/**
	 * 关闭DataBase.该方法不会真的将数据库连接关闭，只是交回给数据库池去管理
	 * @param dataBase
	 * @throws ImpossibleSituationException
	 * @throws AlreadyClosedException
	 * @throws SQLException 
	 */
	protected synchronized void closeDataBase(DataBase dataBase)
			throws ImpossibleSituationException, AlreadyClosedException, SQLException
	{
		checkStatus();
		Connection con = dataBase.getConnection();
		if(con!=null) con.setAutoCommit(true);
		if (busyConSet.contains(con) && !freeConMap.containsKey(con))
		{
			// 回收连接资源
			busyConSet.remove(con);
			freeConMap.put(con, System.currentTimeMillis());
			if (bq.peek() != null)
			{
				// 如果队列中有等待获取连接的线程，则唤醒该线程
				Integer forLock = bq.poll();
				synchronized(forLock)
				{
					forLock.notify();
				}
			}
		}
		else
		{
			DBUtil.close(con);
/*			throw new ImpossibleSituationException(
					"不可能出现的情况，请检查程序 : 想要回收不属于该连接池的连接!");*/
		}
	}

	/**
	 * 关闭数据库连接
	 * @param con
	 * @param rollback
	 */
	private void closeConnection(Connection con, boolean rollback)
	{
		try
		{
			if (con != null)
			{
				if (rollback) con.rollback();
				else if(!con.getAutoCommit()) con.commit();
				con.close();
				LogUtil.printlnInfo("Connection has been closed : "+con);
			}
		}
		catch (SQLException e)
		{
			LogUtil.printlnError(e);
		}
	}

	/**
	 * 关闭数据库池，该方法与close(false)等价
	 */
	public void close()
	{
		close(false);
	}

	/**
	 * 关闭数据库池
	 * @param rollback 是否回滚
	 */
	public synchronized void close(boolean rollback)
	{
		if (close)
			return;
		for (Connection con : freeConMap.keySet())
		{
			closeConnection(con, rollback);
		}
		for (Connection con : busyConSet)
		{
			closeConnection(con, rollback);
		}
		close = true;
	}

	/**
	 * 返回当前池是否已关闭
	 * @return
	 */
	public boolean isClose()
	{
		return close;
	}

	/**
	 * 检查当前池是否已经关闭
	 * @throws AlreadyClosedException
	 */
	private void checkStatus() throws AlreadyClosedException
	{
		if (close)
			throw new AlreadyClosedException();
	}

	@Override
	public void run()
	{
		// 定期检查连接池状态
		if(!close)
		{
			synchronized (this)
			{
				long nowtime = System.currentTimeMillis();
				if(freeConMap.size() > corePoolSize || (corePoolTimeOut && freeConMap.size() > 0))
				{
					int needCloseCnt = 0;
					if(freeConMap.size() > corePoolSize && !corePoolTimeOut) needCloseCnt = freeConMap.size() - corePoolSize;
					else if(freeConMap.size() > 0) needCloseCnt = freeConMap.size();
					if(needCloseCnt>0)
					{
						List<Connection> toCloseConList = null;
						for(Connection con : freeConMap.keySet())
						{
							long freeAt = freeConMap.get(con);
							if (keepAliveTime <= 0 || (nowtime - freeAt) >= keepAliveTime * 1000)
							{
								if(toCloseConList==null) toCloseConList = new ArrayList<Connection>();
								toCloseConList.add(con);
								if(--needCloseCnt==0) break;
							}
						}
						if(toCloseConList!=null)
						{
							for(Connection con : toCloseConList)
							{
								freeConMap.remove(con);
								closeConnection(con, false);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 获取数据库ID
	 * @return
	 */
	public int getDataBaseId()
	{
		return id;
	}

	/**
	 * 获取数据库描述
	 * @return
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * 获取DataSource
	 * @return
	 */
	public String getDataSource()
	{
		return dataSource;
	}

	/**
	 * 获取用户名
	 * @return
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * 获取密码
	 * @return
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * 获取核心池大小
	 * @return
	 */
	public int getCorePoolSize()
	{
		return corePoolSize;
	}

	/**
	 * 获取最大池大小
	 * @return
	 */
	public int getMaximumPoolSize()
	{
		return maximumPoolSize;
	}

	/**
	 * 获取保持连接活动的时间
	 * @return
	 */
	public int getKeepAliveTime()
	{
		return keepAliveTime;
	}

	/**
	 * 获取最大队列长度
	 * @return
	 */
	public int getQueueCapacity()
	{
		return queueCapacity;
	}

	/**
	 * 获取核心池超时时间
	 * @return
	 */
	public boolean isCorePoolTimeOut()
	{
		return corePoolTimeOut;
	}

	/**
	 * 获取表信息缓冲池
	 * @return
	 */
	public BufferPool<String, TableInfo> getTableInfoBuffer()
	{
		return tableInfoBuffer;
	}
	
	public void loadAllData()
	{
		tableInfoBuffer.clear();
	}
	
	/**
	 * 重新加载缓冲
	 * @param paramMap 参数表
	 */
	public void loadData(Map<String, Serializable> paramMap)
	{
		if(paramMap!=null)
		{
			String tableName = ObjectUtil.objectToString(paramMap.get(BUFFER_KEY_TABLE_NAME));
			if(tableName!=null) tableInfoBuffer.remove(tableName);
			else loadAllData();
		}
	}
}
