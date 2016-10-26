package xlsys.base.database;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
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
import xlsys.base.buffer.MXlsysBuffer;
import xlsys.base.buffer.XlsysBuffer;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.dataset.IDataSet;
import xlsys.base.exception.AlreadyClosedException;
import xlsys.base.exception.ImpossibleSituationException;
import xlsys.base.exception.ParameterNotEnoughException;
import xlsys.base.io.util.LockUtil;
import xlsys.base.log.LogUtil;
import xlsys.base.session.Session;
import xlsys.base.util.ObjectUtil;

/**
 * 数据库连接池抽象类，要实现数据库连接池必须继承该类
 * @author 刘旭东
 * 
 */
public abstract class ConnectionPool extends Thread implements XlsysBuffer
{
	private int id;
	private String description;
	private String dataSource;
	private String user;
	private String password;
	private int corePoolSize;
	private int maximumPoolSize;
	private int keepAliveTime;
	private int queueCapacity;
	private Integer tableCount;
	private Map<String, TableInfo> tableInfoBuffer;

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
		tableInfoBuffer = new HashMap<String, TableInfo>();
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
				System.out.println("Connection has been closed : "+con);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
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
	
	public TableInfo getTableInfo(String tableName)
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(IDataBase.BUFFER_KEY_TABLE_NAME, tableName);
		return (TableInfo) this.getBufferData(0, "_DB"+XLSYS.BUFFER_TABLE_INFO_PREFIX+id, paramMap);
	}

	@Override
	public Serializable getStorageObject(int envId, String bufferName)
	{
		Serializable[] arr = new Serializable[2];
		arr[0] = (Serializable) tableInfoBuffer;
		arr[1] = tableCount;
		return arr;
	}

	@Override
	public boolean isBufferComplete(int envId, String bufferName)
	{
		if(tableCount==null)
		{
			IDataBase dataBase = null;
			try
			{
				dataBase = getNewDataBase();
				tableCount = dataBase.getAllTableBaseInfo().size();
			}
			catch(Exception e)
			{
				throw new RuntimeException(e);
			}
			finally
			{
				DBUtil.close(dataBase);
			}
		}
		return tableInfoBuffer.size()==tableCount.intValue();
	}

	@Override
	public void reloadDataDirectly(int envId, String bufferName, Map<String, Object> paramMap, boolean forceLoad)
	{
		String tableName = null;
		if(paramMap!=null) tableName = ObjectUtil.objectToString(paramMap.get(IDataBase.BUFFER_KEY_TABLE_NAME));
		synchronized(tableInfoBuffer)
		{
			if(tableName!=null) tableInfoBuffer.remove(tableName);
			else
			{
				tableInfoBuffer.clear();
				tableCount = null;
			}
			if(forceLoad) this.doGetBufferData(envId, bufferName, paramMap);
		}
	}
	
	@Override
	public boolean loadDataFromStorageObject(int envId, String bufferName, Serializable storageObj)
	{
		Serializable[] arr = (Serializable[]) storageObj;
		synchronized(tableInfoBuffer)
		{
			tableInfoBuffer = (Map<String, TableInfo>) arr[0];
			tableCount = (Integer) arr[1];
		}
		return true;
	}
	
	@Override
	public Serializable doGetBufferData(int envId, String bufferName, Map<String, Object> paramMap)
	{
		Serializable ret = null;
		String tableName = null;
		if(paramMap!=null) tableName = (String) paramMap.get(IDataBase.BUFFER_KEY_TABLE_NAME);
		synchronized(tableInfoBuffer)
		{
			if(tableName!=null)
			{
				TableInfo tableInfo = tableInfoBuffer.get(tableName);
				if(tableInfo==null)
				{
					DataBase dataBase = null;
					try
					{
						dataBase = this.getNewDataBase();
						tableInfo = dataBase.queryTableInfo(tableName);
						if(tableInfo.getPkColSet().isEmpty())
						{
							// 没有从数据库获取到表主键信息,有可能是数据库不支持,或者数据库用户对该表没有相应权限,尝试从额外表信息中获取
							String selectSql = "select etid.* from xlsys_exttableinfo eti, xlsys_exttableinfodetail etid where eti.tableid=etid.tableid and eti.tablename=? order by etid.idx";
							ParamBean pb = new ParamBean(selectSql);
							pb.addParamGroup();
							pb.setParam(1, tableName);
							IDataSet ds = dataBase.sqlSelect(pb);
							if(ds!=null)
							{
								int rowCount = ds.getRowCount();
								for(int i=0;i<rowCount;i++)
								{
									BigDecimal primarykey = (BigDecimal) ds.getValue(i, "primarykey");
									if(primarykey!=null&&primarykey.intValue()==1)
									{
										tableInfo.addPkCol((String)ds.getValue(i, "colname"));
									}
								}
							}
						}
						tableInfoBuffer.put(tableName, tableInfo);
					}
					catch(Exception e) { throw new RuntimeException(e); }
					finally { DBUtil.close(dataBase); }
				}
				ret = tableInfo;
			}
			else
			{
				IDataBase dataBase = null;
				try
				{
					dataBase = this.getNewDataBase();
					Map<String, String> allTableInfo = dataBase.getAllTableBaseInfo();
					if(tableCount==null||allTableInfo.size()!=tableCount.intValue())
					{
						tableCount = allTableInfo.size();
						for(String tempTable : allTableInfo.keySet()) tableInfoBuffer.put(tempTable, dataBase.getTableInfo(tempTable));
					}
				}
				catch(Exception e) { throw new RuntimeException(e); }
				finally { DBUtil.close(dataBase); }
				ret = (Serializable) tableInfoBuffer;
			}
		}
		return ret;
	}

	public Serializable getBufferData(int envId, String bufferName, Map<String, Object> paramMap)
	{
		return MXlsysBuffer._getBufferData(this, 0, bufferName, paramMap);
	}

	public int updateCurrentVersion(int envId, String bufferName)
	{
		int version = -1;
		synchronized(MXlsysBuffer.versionMap)
		{
			Session session = new Session(XLSYS.SESSION_DEFAULT_ID);
			session.setAttribute(XLSYS.SESSION_ENV_ID, 0);
			String lockKey = null;
			IDataBase dataBase = null;
			try
			{
				lockKey = LockUtil.getGlobalLock(session, bufferName);
				dataBase = this.getNewDataBase();
				version = MXlsysBuffer._updateCurrentVersionToDB(this, dataBase, 0, bufferName, false);
				// 存入当前版本号到缓存中
				Map<String, Integer> tempMap = MXlsysBuffer.versionMap.get(0);
				if(tempMap==null)
				{
					tempMap = new HashMap<String, Integer>();
					MXlsysBuffer.versionMap.put(0, tempMap);
				}
				tempMap.put(bufferName, version);
			}
			catch(Exception e)
			{
				throw new RuntimeException(e);
			}
			finally
			{
				DBUtil.close(dataBase);
				if(lockKey!=null) LockUtil.releaseGlobalLock(session, lockKey);
			}
		}
		return version;
	}
	
	private BigDecimal _getCurrentVersionFromDB(DataBase dataBase, int envId, String bufferName, boolean useGlobalLock)
	{
		BigDecimal version = null;
		Session session = new Session(XLSYS.SESSION_DEFAULT_ID);
		session.setAttribute(XLSYS.SESSION_ENV_ID, envId);
		String lockKey = null;
		try
		{
			if(useGlobalLock) lockKey = LockUtil.getGlobalLock(session, bufferName);
			String selectSql = "select version from xlsys_bufferinfo where buffername=?";
			ParamBean pb = new ParamBean(selectSql);
			pb.addParamGroup();
			pb.setParam(1, bufferName);
			IDataSet ds = dataBase.doSqlSelect(pb, false);
			if(ds.getRowCount()>0) version = (BigDecimal) ds.getValue(0, 0);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			if(lockKey!=null) LockUtil.releaseGlobalLock(session, lockKey);
		}
		return version;
	}

	public int getCurrentVersion(int envId, String bufferName)
	{
		int version = -1;
		synchronized(MXlsysBuffer.versionMap)
		{
			Session session = new Session(XLSYS.SESSION_DEFAULT_ID);
			session.setAttribute(XLSYS.SESSION_ENV_ID, 0);
			Map<String, Integer> tempMap = MXlsysBuffer.versionMap.get(0);
			if(tempMap==null)
			{
				tempMap = new HashMap<String, Integer>();
				MXlsysBuffer.versionMap.put(0, tempMap);
			}
			if(tempMap.containsKey(bufferName)) version = tempMap.get(bufferName);
			else
			{
				// 从数据库中读取版本号
				String lockKey = null;
				DataBase dataBase = null;
				try
				{
					lockKey = LockUtil.getGlobalLock(session, bufferName);
					dataBase = this.getNewDataBase();
					dataBase.setAutoCommit(false);
					BigDecimal dbBufferVersion = _getCurrentVersionFromDB(dataBase, 0, bufferName, false);
					if(dbBufferVersion!=null) version = dbBufferVersion.intValue();
					else
					{
						version = 0;
						// 插入数据到数据库中
						String insertSql = "insert into xlsys_bufferinfo(buffername, version) values(?, ?)";
						ParamBean pb = new ParamBean(insertSql);
						pb.addParamGroup();
						pb.setParam(1, bufferName);
						pb.setParam(2, version);
						dataBase.sqlExecute(pb);
					}
					dataBase.commit();
				}
				catch(Exception e)
				{
					DBUtil.rollback(dataBase);
					throw new RuntimeException(e);
				}
				finally
				{
					DBUtil.close(dataBase);
					if(lockKey!=null) LockUtil.releaseGlobalLock(session, lockKey);
				}
				if(version==-1) version = 0;
				tempMap.put(bufferName, version);
			}
		}
		return version;
	}
	
	public boolean saveDataToLocalStorage(int envId, String bufferName, Serializable storageObject)
	{
		return MXlsysBuffer._saveDataToLocalStorage(this, 0, bufferName, storageObject);
	}

	public void reloadAllData(int envId, String bufferName)
	{
		MXlsysBuffer._reloadAllData(this, 0, bufferName, false);
	}
	
	public void reloadAllData(int envId, String bufferName, boolean forceLoad)
	{
		MXlsysBuffer._reloadAllData(this, 0, bufferName, forceLoad);
	}

	public void reloadData(int envId, String bufferName, Map<String, Object> paramMap)
	{
		MXlsysBuffer._reloadData(this, 0, bufferName, paramMap);
	}
	
	public void reloadData(int envId, String bufferName, Map<String, Object> paramMap, boolean forceLoad)
	{
		MXlsysBuffer._reloadData(this, 0, bufferName, paramMap, forceLoad);
	}

	public boolean reloadDataFromLocalStorage(int envId, String bufferName)
	{
		return MXlsysBuffer._reloadDataFromLocalStorage(this, 0, bufferName);
	}

	public int getLocalStorageVersion(int envId, String bufferName)
	{
		return MXlsysBuffer._getLocalStorageVersion(this, 0, bufferName);
	}
}
