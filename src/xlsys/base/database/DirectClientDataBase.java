package xlsys.base.database;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.buffer.MutiLRUBufferPool;
import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.bean.ISqlBean;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.dataset.DataSet;
import xlsys.base.dataset.IDataSet;
import xlsys.base.exception.ParameterNotEnoughException;
import xlsys.base.io.transfer.client.ClientTransfer;
import xlsys.base.util.ObjectUtil;

/**
 * 客户端数据库交互类，在客户端使用，该类会通过客户端传输类把sql语句传递给服务端执行，并获取服务端的返回结果
 * @author Lewis
 *
 */
public class DirectClientDataBase implements IClientDataBase
{
	private static ClientTransfer clientTransfer;
	private int dbId;
	private Map<String, TableInfo> tableInfoBuffer;
	
	private boolean autoCommit;
	private List<ISqlBean> transactionList;

	private DirectClientDataBase(int dbId) throws ParameterNotEnoughException
	{
		this.dbId = dbId;
		tableInfoBuffer = new MutiLRUBufferPool<String, TableInfo>();
	}
	
	/**
	 * 获取客户端Database实例
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws DocumentException
	 * @throws ParameterNotEnoughException
	 */
	public synchronized static final DirectClientDataBase getInstance(int dbId) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException, ParameterNotEnoughException
	{
		if(clientTransfer==null) clientTransfer = (ClientTransfer) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_CLIENT_TRANSFER).getInstance();
		return new DirectClientDataBase(dbId);
	}
	
	private DirectClientDataBase()
	{
		autoCommit = true;
		transactionList = new ArrayList<ISqlBean>();
	}
	
	@Override
	public Map<String, String> getAllTableBaseInfo() throws Exception
	{
		return (Map<String, String>) clientTransfer.post(XLSYS.COMMAND_DB_GET_ALL_TABLE_BASE_INFO, dbId);
	}

	@Override
	public TableInfo getTableInfo(String tableName) throws Exception
	{
		TableInfo tableInfo = tableInfoBuffer.get(tableName);
		if(tableInfo==null)
		{
			Object[] arr = new Object[2];
			arr[0] = dbId;
			arr[1] = tableName;
			tableInfo = (TableInfo) clientTransfer.post(XLSYS.COMMAND_DB_GET_TABLE_INFO, arr);
			tableInfoBuffer.put(tableName, tableInfo);
		}
		return tableInfo;
	}

	@Override
	public int getResultCount(ParamBean paramBean) throws Exception
	{
		Object[] arr = new Object[2];
		arr[0] = dbId;
		arr[1] = paramBean;
		return (Integer) clientTransfer.post(XLSYS.COMMAND_DB_GET_RESULT_COUNT, arr);
	}
	
	@Override
	public Serializable sqlSelectAsOneValue(String selectSql) throws Exception
	{
		return sqlSelectAsOneValue(new ParamBean(selectSql));
	}

	@Override
	public Serializable sqlSelectAsOneValue(ParamBean paramBean) throws Exception
	{
		Object[] arr = new Object[2];
		arr[0] = dbId;
		arr[1] = paramBean;
		return clientTransfer.post(XLSYS.COMMAND_DB_SQL_SELECT_AS_ONE_VALUE, arr);
	}

	@Override
	public DataSet sqlSelect(String selectSql) throws Exception
	{
		return sqlSelect(new ParamBean(selectSql));
	}

	@Override
	public DataSet sqlSelect(ParamBean paramBean) throws Exception
	{
		Object[] arr = new Object[2];
		arr[0] = dbId;
		arr[1] = paramBean;
		return (DataSet) clientTransfer.post(XLSYS.COMMAND_DB_SQL_SELECT, arr);
	}
	
	@Override
	public IDataSet sqlSelect(ParamBean paramBean, int beginRowNum, int endRowNum) throws Exception
	{
		Object[] arr = new Object[4];
		arr[0] = dbId;
		arr[1] = paramBean;
		arr[2] = beginRowNum;
		arr[3] = endRowNum;
		return (DataSet) clientTransfer.post(XLSYS.COMMAND_DB_SQL_SELECT, arr);
	}
	
	@Override
	public IDataSet sqlSelect(ParamBean paramBean, Map<String, Integer> sortColMap, int beginRowNum, int endRowNum) throws Exception
	{
		Object[] arr = new Object[5];
		arr[0] = dbId;
		arr[1] = paramBean;
		arr[2] = sortColMap;
		arr[3] = beginRowNum;
		arr[4] = endRowNum;
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
			Object[] arr = new Object[2];
			arr[0] = dbId;
			arr[1] = executeBean;
			return (Boolean) clientTransfer.post(XLSYS.COMMAND_DB_SQL_EXECUTE, arr);
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
			Object[] arr = new Object[2];
			arr[0] = dbId;
			arr[1] = sqlBeanList;
			return (Boolean) clientTransfer.post(XLSYS.COMMAND_DB_SQL_EXECUTE, arr);
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
			Object[] arr = new Object[2];
			arr[0] = dbId;
			arr[1] = paramBean;
			return (Boolean) clientTransfer.post(XLSYS.COMMAND_DB_SQL_EXECUTE, arr);
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
		Object[] arr = new Object[2];
		arr[0] = dbId;
		arr[1] = srcExp;
		return ObjectUtil.objectToString(clientTransfer.post(XLSYS.COMMAND_DB_FUNC_LOWER, arr));
	}

	@Override
	public String getUpperFunc(String srcExp)
	{
		Object[] arr = new Object[2];
		arr[0] = dbId;
		arr[1] = srcExp;
		return ObjectUtil.objectToString(clientTransfer.post(XLSYS.COMMAND_DB_FUNC_UPPER, arr));
	}
	
	@Override
	public String getSubStringFunc(String srcExp, int begin)
	{
		Object[] arr = new Object[3];
		arr[0] = dbId;
		arr[1] = srcExp;
		arr[2] = begin;
		return ObjectUtil.objectToString(clientTransfer.post(XLSYS.COMMAND_DB_FUNC_SUBSTRING, arr));
	}

	@Override
	public String getSubStringFunc(String srcExp, int begin, int end)
	{
		Object[] arr = new Object[4];
		arr[0] = dbId;
		arr[1] = srcExp;
		arr[2] = begin;
		arr[3] = end;
		return ObjectUtil.objectToString(clientTransfer.post(XLSYS.COMMAND_DB_FUNC_SUBSTRING, arr));
	}

	@Override
	public String getByteLengthFunc(String srcExp)
	{
		Object[] arr = new Object[2];
		arr[0] = dbId;
		arr[1] = srcExp;
		return ObjectUtil.objectToString(clientTransfer.post(XLSYS.COMMAND_DB_FUNC_BYTELENGTH, arr));
	}
	
	@Override
	public String getToDateFunc(Date date)
	{
		Object[] arr = new Object[2];
		arr[0] = dbId;
		arr[1] = date;
		return ObjectUtil.objectToString(clientTransfer.post(XLSYS.COMMAND_DB_FUNC_TODATE, arr));
	}
	
	@Override
	public int getDataBaseId()
	{
		return dbId;
	}

	@Override
	public void commit() throws Exception
	{
		if(!autoCommit&&transactionList.size()>0)
		{
			Object[] arr = new Object[2];
			arr[0] = dbId;
			arr[1] = transactionList;
			Serializable ret = clientTransfer.post(XLSYS.COMMAND_DB_SQL_EXECUTE, arr);
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
		Object[] arr = new Object[3];
		arr[0] = dbId;
		arr[1] = tableName;
		arr[2] = constraintName;
		return (Boolean) clientTransfer.post(XLSYS.COMMAND_DB_DISABLE_CONSTRAINT, arr);
	}

	@Override
	public boolean enableConstraint(String tableName, String constraintName) throws Exception
	{
		Object[] arr = new Object[3];
		arr[0] = dbId;
		arr[1] = tableName;
		arr[2] = constraintName;
		return (Boolean) clientTransfer.post(XLSYS.COMMAND_DB_ENABLE_CONSTRAINT, arr);
	}

	@Override
	public int getEnvId()
	{
		throw new UnsupportedOperationException();
	}
}
