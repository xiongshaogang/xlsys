package xlsys.base.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.buffer.BufferManager;
import xlsys.base.buffer.XlsysBuffer;
import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.bean.ISqlBean;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.dataset.DataSet;
import xlsys.base.dataset.IDataSet;
import xlsys.base.io.transfer.client.ClientTransfer;
import xlsys.base.session.Session;
import xlsys.base.session.SessionManager;
import xlsys.base.util.ObjectUtil;

/**
 * 客户端数据库交互类，在客户端使用，该类会通过客户端传输类把sql语句传递给服务端执行，并获取服务端的返回结果
 * @author Lewis
 *
 */
public class ClientDataBase implements IClientDataBase
{
	static ClientTransfer clientTransfer;
	
	private boolean autoCommit;
	private List<ISqlBean> transactionList;
	
	/**
	 * 获取客户端Database实例
	 * @return
	 */
	public synchronized static final ClientDataBase getInstance()
	{
		if(clientTransfer==null)
		{
			try
			{
				clientTransfer = (ClientTransfer) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_CLIENT_TRANSFER).getInstance();
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		return new ClientDataBase();
	}
	
	private ClientDataBase()
	{
		autoCommit = true;
		transactionList = new ArrayList<ISqlBean>();
	}
	
	@Override
	public Map<String, String> getAllTableBaseInfo() throws Exception
	{
		return (Map<String, String>) clientTransfer.post(XLSYS.COMMAND_DB_GET_ALL_TABLE_BASE_INFO);
	}

	@Override
	public TableInfo getTableInfo(String tableName) throws Exception
	{
		int envId = getEnvId();
		String bufferName = "_ENV"+XLSYS.BUFFER_TABLE_INFO_PREFIX+envId;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(IDataBase.BUFFER_KEY_TABLE_NAME, tableName);
		return (TableInfo) ClientTableInfoBuffer.getInstance(envId).getBufferData(envId, bufferName, paramMap);
	}

	@Override
	public int getResultCount(ParamBean paramBean) throws Exception
	{
		return (Integer) clientTransfer.post(XLSYS.COMMAND_DB_GET_RESULT_COUNT, paramBean);
	}
	
	@Override
	public Serializable sqlSelectAsOneValue(String selectSql) throws Exception
	{
		return sqlSelectAsOneValue(new ParamBean(selectSql));
	}

	@Override
	public Serializable sqlSelectAsOneValue(ParamBean paramBean) throws Exception
	{
		return clientTransfer.post(XLSYS.COMMAND_DB_SQL_SELECT_AS_ONE_VALUE, paramBean);
	}

	@Override
	public IDataSet sqlSelect(String selectSql) throws Exception
	{
		return sqlSelect(new ParamBean(selectSql));
	}

	@Override
	public IDataSet sqlSelect(ParamBean paramBean) throws Exception
	{
		return (DataSet) clientTransfer.post(XLSYS.COMMAND_DB_SQL_SELECT, paramBean);
	}
	
	@Override
	public IDataSet sqlSelect(ParamBean paramBean, int beginRowNum, int endRowNum) throws Exception
	{
		Object[] arr = new Object[3];
		arr[0] = paramBean;
		arr[1] = beginRowNum;
		arr[2] = endRowNum;
		return (DataSet) clientTransfer.post(XLSYS.COMMAND_DB_SQL_SELECT, arr);
	}
	
	@Override
	public IDataSet sqlSelect(ParamBean paramBean, Map<String, Integer> sortColMap, int beginRowNum, int endRowNum) throws Exception
	{
		Object[] arr = new Object[4];
		arr[0] = paramBean;
		arr[1] = sortColMap;
		arr[2] = beginRowNum;
		arr[3] = endRowNum;
		return (DataSet) clientTransfer.post(XLSYS.COMMAND_DB_SQL_SELECT, arr);
	}

	@Override
	public boolean sqlExecute(String executeSql) throws Exception
	{
		return sqlExecute(new ExecuteBean(executeSql));
	}

	@Override
	public boolean sqlExecute(ExecuteBean executeBean) throws Exception
	{
		if(autoCommit)
		{
			return (Boolean) clientTransfer.post(XLSYS.COMMAND_DB_SQL_EXECUTE, executeBean);
		}
		else
		{
			transactionList.add(executeBean);
		}
		return true;
	}

	@Override
	public boolean sqlExecute(List<? extends ISqlBean> sqlBeanList) throws Exception
	{
		if(autoCommit)
		{
			return (Boolean) clientTransfer.post(XLSYS.COMMAND_DB_SQL_EXECUTE, (Serializable) sqlBeanList);
		}
		else
		{
			transactionList.addAll(sqlBeanList);
		}
		return true;
	}

	@Override
	public boolean sqlExecute(ParamBean paramBean) throws Exception
	{
		if(autoCommit)
		{
			return (Boolean) clientTransfer.post(XLSYS.COMMAND_DB_SQL_EXECUTE, paramBean);
		}
		else
		{
			transactionList.add(paramBean);
		}
		return true;
	}

	@Override
	public String getLowerFunc(String srcExp)
	{
		return ObjectUtil.objectToString(clientTransfer.post(XLSYS.COMMAND_DB_FUNC_LOWER, srcExp));
	}

	@Override
	public String getUpperFunc(String srcExp)
	{
		return ObjectUtil.objectToString(clientTransfer.post(XLSYS.COMMAND_DB_FUNC_UPPER, srcExp));
	}
	
	@Override
	public String getSubStringFunc(String srcExp, int begin)
	{
		Object[] arr = new Object[2];
		arr[0] = srcExp;
		arr[1] = begin;
		return ObjectUtil.objectToString(clientTransfer.post(XLSYS.COMMAND_DB_FUNC_SUBSTRING, arr));
	}

	@Override
	public String getSubStringFunc(String srcExp, int begin, int end)
	{
		Object[] arr = new Object[3];
		arr[0] = srcExp;
		arr[1] = begin;
		arr[2] = end;
		return ObjectUtil.objectToString(clientTransfer.post(XLSYS.COMMAND_DB_FUNC_SUBSTRING, arr));
	}

	@Override
	public String getByteLengthFunc(String srcExp)
	{
		return ObjectUtil.objectToString(clientTransfer.post(XLSYS.COMMAND_DB_FUNC_BYTELENGTH, srcExp));
	}
	
	@Override
	public String getToDateFunc(Date date)
	{
		return ObjectUtil.objectToString(clientTransfer.post(XLSYS.COMMAND_DB_FUNC_TODATE, date));
	}
	
	@Override
	public int getDataBaseId()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void commit() throws Exception
	{
		if(!autoCommit&&transactionList.size()>0)
		{
			Serializable ret = clientTransfer.post(XLSYS.COMMAND_DB_SQL_EXECUTE, (Serializable) transactionList);
			if(ret instanceof Exception)
			{
				throw (Exception) ret;
			}
			else if(Boolean.TRUE.equals(ret)) transactionList.clear();
		}
	}

	@Override
	public void rollback() throws Exception
	{
		if(!autoCommit&&transactionList.size()>0)
		{
			transactionList.clear();
		}
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws Exception
	{
		if(autoCommit&&!this.autoCommit)
		{
			commit();
		}
		this.autoCommit = autoCommit;
	}

	@Override
	public boolean getAutoCommit() throws Exception
	{
		return autoCommit;
	}

	@Override
	public void close() throws Exception
	{
		if(!autoCommit) commit();
	}

	@Override
	public boolean isClose()
	{
		return false;
	}

	@Override
	public boolean disableConstraint(String tableName, String constraintName) throws Exception
	{
		Object[] arr = new Object[2];
		arr[0] = tableName;
		arr[1] = constraintName;
		return (Boolean) clientTransfer.post(XLSYS.COMMAND_DB_DISABLE_CONSTRAINT, arr);
	}

	@Override
	public boolean enableConstraint(String tableName, String constraintName) throws Exception
	{
		Object[] arr = new Object[2];
		arr[0] = tableName;
		arr[1] = constraintName;
		return (Boolean) clientTransfer.post(XLSYS.COMMAND_DB_ENABLE_CONSTRAINT, arr);
	}

	@Override
	public int getEnvId()
	{
		Session session = SessionManager.getInstance().getCurrentSession();
		return ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
	}
}

class ClientTableInfoBuffer implements XlsysBuffer
{
	private static Map<Integer, ClientTableInfoBuffer> ctibMap;
	private Map<String, TableInfo> tableInfoBuffer;
	private Integer tableCount;
	private int envId;
	
	private ClientTableInfoBuffer(int envId)
	{
		this.envId = envId;
		tableInfoBuffer = new HashMap<String, TableInfo>();
		BufferManager.getInstance().registerBuffer("_ENV"+XLSYS.BUFFER_TABLE_INFO_PREFIX+envId, this);
	}
	
	public static synchronized ClientTableInfoBuffer getInstance(int envId)
	{
		if(ctibMap==null) ctibMap = new HashMap<Integer, ClientTableInfoBuffer>();
		ClientTableInfoBuffer ctib = ctibMap.get(envId);
		if(ctib==null)
		{
			ctib = new ClientTableInfoBuffer(envId);
			ctibMap.put(envId, ctib);
		}
		return ctib;
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
		if(this.envId!=envId) return false;
		if(tableCount==null)
		{
			Session session = SessionManager.getInstance().getCurrentSession();
			if(session==null) return false;
			int curEnvId = ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
			if(curEnvId!=this.envId) return false;
			IClientDataBase dataBase = ClientDataBase.getInstance();
			try
			{
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
		if(paramMap!=null) tableName = (String) paramMap.get(IDataBase.BUFFER_KEY_TABLE_NAME);
		if(tableName!=null) tableInfoBuffer.remove(tableName);
		else
		{
			tableInfoBuffer.clear();
			tableCount = null;
		}
		if(forceLoad) this.getBufferData(envId, bufferName, paramMap);
	}
	
	@Override
	public boolean loadDataFromStorageObject(int envId, String bufferName, Serializable storageObj)
	{
		Serializable[] arr = (Serializable[]) storageObj;
		tableInfoBuffer = (Map<String, TableInfo>) arr[0];
		tableCount = (Integer) arr[1];
		return true;
	}

	@Override
	public Serializable doGetBufferData(int envId, String bufferName, Map<String, Object> paramMap)
	{
		if(envId!=this.envId) return null;
		Serializable ret = null;
		String tableName = null;
		if(paramMap==null) tableName = (String) paramMap.get(IDataBase.BUFFER_KEY_TABLE_NAME);
		if(tableName!=null)
		{
			ret = tableInfoBuffer.get(tableName);
			if(ret!=null) return ret;
		}
		else
		{
			ret = (Serializable) tableInfoBuffer;
			if(tableCount!=null&&tableInfoBuffer.size()==tableCount.intValue()) return ret;
			else ret = null;
		}
		if(ret==null)
		{
			Session session = SessionManager.getInstance().getCurrentSession();
			if(session==null) return ret;
			int curEnvId = ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
			if(curEnvId!=this.envId) return ret;
			ClientDataBase dataBase = ClientDataBase.getInstance();
			try
			{
				if(tableName!=null)
				{
					TableInfo tableInfo = (TableInfo) dataBase.clientTransfer.post(XLSYS.COMMAND_DB_GET_TABLE_INFO, tableName);
					tableInfoBuffer.put(tableName, tableInfo);
					ret = tableInfo;
				}
				else
				{
					Map<String, String> allTableInfo = (Map<String, String>) dataBase.clientTransfer.post(XLSYS.COMMAND_DB_GET_ALL_TABLE_BASE_INFO);
					for(String tempTable : allTableInfo.keySet())
					{
						TableInfo tableInfo = (TableInfo) dataBase.clientTransfer.post(XLSYS.COMMAND_DB_GET_TABLE_INFO, tempTable);
						tableInfoBuffer.put(tempTable, tableInfo);
					}
					ret = (Serializable) tableInfoBuffer;
				}
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
		return ret;
	}
}
