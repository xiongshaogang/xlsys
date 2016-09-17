package xlsys.base.database;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.bean.ISqlBean;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.dataset.IDataSet;
import xlsys.base.dataset.util.DataSetUtil;
import xlsys.base.exception.AlreadyClosedException;
import xlsys.base.script.XlsysClassLoader;

/**
 * 数据库交互抽象类，和数据库的直接交互类必须继承自该类
 * @author Lewis
 *
 */
public abstract class DataBase implements IDataBase
{
	private static Field[] typesFields = Types.class.getFields();
	
	protected ConnectionPool conPool; 
	protected Connection con;
	private boolean close;
	
	protected DataBase(ConnectionPool conPool, Connection con) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		this.conPool = conPool;
		this.con = con;
		close = false;
	}
	
	@Override
	public TableInfo getTableInfo(String tableName) throws Exception
	{
		Map<String, TableInfo> tableInfoBuffer = conPool.getTableInfoBuffer();
		TableInfo tableInfo = tableInfoBuffer.get(tableName);
		if(tableInfo==null)
		{
			synchronized(tableInfoBuffer)
			{
				tableInfo = tableInfoBuffer.get(tableName);
				if(tableInfo==null)
				{
					tableInfo = queryTableInfo(tableName);
					if(tableInfo.getPkColSet().isEmpty())
					{
						// 没有从数据库获取到表主键信息,有可能是数据库不支持,或者数据库用户对该表没有相应权限,尝试从额外表信息中获取
						String selectSql = "select etid.* from xlsys_exttableinfo eti, xlsys_exttableinfodetail etid where eti.tableid=etid.tableid and eti.tablename=? order by etid.idx";
						ParamBean pb = new ParamBean(selectSql);
						pb.addParamGroup();
						pb.setParam(1, tableName);
						IDataSet ds = sqlSelect(pb);
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
			}
		}
		return tableInfo;
	}
	
	@Override
	public int getDataBaseId()
	{
		return conPool.getDataBaseId();
	}
	
	/**
	 * 查询表信息的具体行为方法
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	protected abstract TableInfo queryTableInfo(String tableName) throws Exception;
	
	/**
	 * 直接获取数据库连接
	 * @return
	 */
	public final Connection getConnection()
	{
		return con;
	}
	
	@Override
	public void setAutoCommit(boolean autoCommit) throws Exception
	{
		checkStatus();
		con.setAutoCommit(autoCommit);
	}
	
	@Override
	public boolean getAutoCommit() throws Exception
	{
		checkStatus();
		return con.getAutoCommit();
	}
	
	@Override
	public void commit() throws Exception
	{
		checkStatus();
		con.commit();
	}
	
	@Override
	public void rollback() throws Exception
	{
		checkStatus();
		con.rollback();
	}
	
	@Override
	public final void close() throws Exception
	{
		conPool.closeDataBase(this);
		close = true;
		conPool = null;
		con = null;
	}
	
	/**
	 * 检查当前的数据库状态
	 * @throws Exception
	 */
	private void checkStatus() throws Exception
	{
		if(close) throw new AlreadyClosedException();
		ensureConnected();
	}
	
	@Override
	public Serializable sqlSelectAsOneValue(String selectSql) throws Exception
	{
		return sqlSelectAsOneValue(new ParamBean(selectSql));
	}
	
	@Override
	public Serializable sqlSelectAsOneValue(ParamBean paramBean) throws Exception
	{
		Serializable value = null;
		IDataSet ds = sqlSelect(paramBean);
		if(ds.getRowCount()>0)
		{
			value = ds.getValue(0, 0);
		}
		return value;
	}
	
	@Override
	public IDataSet sqlSelect(String selectSql) throws Exception
	{
		return sqlSelect(new ParamBean(selectSql));
	}
	
	@Override
	public IDataSet sqlSelect(ParamBean paramBean) throws Exception
	{
		checkStatus();
		return doSqlSelect(paramBean);
	}
	
	@Override
	public IDataSet sqlSelect(ParamBean paramBean, int beginRowNum, int endRowNum) throws Exception
	{
		ParamBean splitedParamBean = getSplitedParamBean(paramBean, beginRowNum, endRowNum);
		IDataSet dataSet = sqlSelect(splitedParamBean);
		afterSplitedSqlQueried(dataSet);
		return dataSet;
	}
	
	@Override
	public IDataSet sqlSelect(ParamBean paramBean, Map<String, Integer> sortColMap, int beginRowNum, int endRowNum) throws Exception
	{
		IDataSet dataSet = sqlSelect(paramBean);
		dataSet.setSortColumn(sortColMap);
		dataSet.sort();
		return DataSetUtil.subDataSet(dataSet, beginRowNum, endRowNum);
	}
	
	/**
	 * 确保数据库连接可使用
	 * @throws Exception
	 */
	protected void ensureConnected() throws Exception
	{
		if(con!=null&&!con.isValid(0))
		{
			conPool.replaceConnection(this);
		}
	}
	
	/**
	 * 查询sql语句的具体行为方法
	 * @param paramBean
	 * @return
	 * @throws Exception
	 */
	protected abstract IDataSet doSqlSelect(ParamBean paramBean) throws Exception;
	
	@Override
	public boolean sqlExecute(String executeSql) throws Exception
	{
		return sqlExecute(new ExecuteBean(executeSql));
	}
	
	@Override
	public boolean sqlExecute(ExecuteBean executeBean) throws Exception
	{
		List<ISqlBean> list = new ArrayList<ISqlBean>();
		list.add(executeBean);
		return sqlExecute(list);
	}
	
	@Override
	public boolean sqlExecute(ParamBean paramBean) throws Exception
	{
		List<ISqlBean> list = new ArrayList<ISqlBean>();
		list.add(paramBean);
		return sqlExecute(list);
	}
	
	@Override
	public boolean sqlExecute(List<? extends ISqlBean> sqlBeanList) throws Exception
	{
		checkStatus();
		return doSqlExecute(sqlBeanList);
	}

	/**
	 * 执行Sql语句的具体行为方法
	 * @param sqlBeanList
	 * @return
	 * @throws Exception
	 */
	protected abstract boolean doSqlExecute(List<? extends ISqlBean> sqlBeanList) throws Exception;
	
	@Override
	public int getResultCount(ParamBean paramBean) throws Exception
	{
		StringBuffer selectSql = new StringBuffer();
		selectSql.append("select count(1) from (").append(paramBean.getSelectSql()).append(") lxd_sub");
		ParamBean countBean = new ParamBean(selectSql.toString());
		countBean.setParamsList(paramBean.getParamsList());
		return ((BigDecimal)sqlSelectAsOneValue(countBean)).intValue();
	}
	
	/**
	 * 根据开始和结束行号获取拆分查询语句的具体行为方法
	 * @param paramBean
	 * @param beginRowNum
	 * @param endRowNum
	 * @return
	 * @throws Exception
	 */
	protected abstract ParamBean getSplitedParamBean(ParamBean paramBean, int beginRowNum, int endRowNum) throws Exception;
	
	/**
	 * 拆分数据查询后事件，用来进行一些后续处理行为，以得到正确的结果值
	 * @param dataSet
	 */
	protected abstract void afterSplitedSqlQueried(IDataSet dataSet);
	
	/**
	 * 通过Java类型获取对应的数据库类型
	 * @param javaClass
	 * @return
	 */
	protected abstract int getSqlTypeFromJavaClass(Class<?> javaClass);
	
	/**
	 * 通过Java类型获取对应的数据库类型
	 * @param className
	 * @return
	 * @throws ClassNotFoundException 
	 */
	protected int getSqlTypeFromClassName(String className) throws ClassNotFoundException
	{
		Class<?> javaClass = null;
		// 查看基本类型
		if(boolean.class.getName().equals(className)) javaClass = boolean.class;
		else if(byte.class.getName().equals(className)) javaClass = byte.class;
		else if(char.class.getName().equals(className)) javaClass = char.class;
		else if(short.class.getName().equals(className)) javaClass = short.class;
		else if(int.class.getName().equals(className)) javaClass = int.class;
		else if(long.class.getName().equals(className)) javaClass = long.class;
		else if(float.class.getName().equals(className)) javaClass = float.class;
		else if(double.class.getName().equals(className)) javaClass = double.class;
		else if(byte[].class.getName().equals(className)) javaClass = byte[].class;
		else javaClass = XlsysClassLoader.getInstance().loadClass(className);
		return getSqlTypeFromJavaClass(javaClass);
	}
	
	@Override
	public boolean isClose()
	{
		return close;
	}

	/**
	 * 设置数据库连接
	 * @param connection
	 */
	protected void setConnection(Connection connection)
	{
		this.con = connection;
	}
	
	/**
	 * 通过数据库字段的类型来获取对应的java.sql.Types的类型
	 * @param dbColumnType
	 * @return
	 */
	protected int getSqlTypeFromDBColumnType(String dbColumnType)
	{
		int type = Types.OTHER;
		type = getSqlTypeFromSpecialDBColumnType(dbColumnType);
		dbColumnType = dbColumnType.toUpperCase();
		if(type==Types.OTHER)
		{
			for(Field field : typesFields)
			{
				int modifiers = field.getModifiers();
				String fieldName = field.getName();
				if(field.getType()==int.class&&Modifier.isStatic(modifiers)&&Modifier.isFinal(modifiers)&&dbColumnType.equals(fieldName))
				{
					try
					{
						type = field.getInt(null);
					}
					catch(Exception e) {}
					break;
				}
			}
		}
		return type;
	}
	
	/**
	 * 通过特殊的数据库字段的类型来获取对应的java.sql.Types的类型
	 * @param dbColumnType
	 * @return
	 */
	protected abstract int getSqlTypeFromSpecialDBColumnType(String dbColumnType);
	
	/**
	 * 通过数据库字段的类型来获取对应的Java类型
	 * @param dbColumnType
	 * @return
	 */
	protected Class<?> getJavaClassFromDBColumnType(String dbColumnType)
	{
		int sqlType = getSqlTypeFromDBColumnType(dbColumnType);
		return getJavaClassFromSqlType(sqlType);
	}
	
	/**
	 * 根据java.sql.Types的类型获取对应的Java类型
	 * @param sqlType
	 * @return
	 */
	protected static Class<?> getJavaClassFromSqlType(int sqlType)
	{
		Class<?> type = null;
		if(sqlType==Types.ARRAY) type = byte[].class;
		else if(sqlType==Types.BIGINT) type = BigDecimal.class;
		else if(sqlType==Types.BINARY) type = byte[].class;
		else if(sqlType==Types.BIT) type = BigDecimal.class;
		else if(sqlType==Types.BLOB) type = byte[].class;
		else if(sqlType==Types.BOOLEAN) type = BigDecimal.class;
		else if(sqlType==Types.CHAR) type = String.class;
		else if(sqlType==Types.CLOB) type = byte[].class;
		else if(sqlType==Types.DATALINK) type = byte[].class;
		else if(sqlType==Types.DATE) type = Timestamp.class;
		else if(sqlType==Types.DECIMAL) type = BigDecimal.class;
		else if(sqlType==Types.DISTINCT) type = byte[].class;
		else if(sqlType==Types.DOUBLE) type = BigDecimal.class;
		else if(sqlType==Types.FLOAT) type = BigDecimal.class;
		else if(sqlType==Types.INTEGER) type = BigDecimal.class;
		else if(sqlType==Types.JAVA_OBJECT) type = byte[].class;
		else if(sqlType==Types.LONGNVARCHAR) type = String.class;
		else if(sqlType==Types.LONGVARBINARY) type = byte[].class;
		else if(sqlType==Types.LONGVARCHAR) type = String.class;
		else if(sqlType==Types.NCHAR) type = String.class;
		else if(sqlType==Types.NCLOB) type = byte[].class;
		else if(sqlType==Types.NULL) type = byte[].class;
		else if(sqlType==Types.NUMERIC) type = BigDecimal.class;
		else if(sqlType==Types.NVARCHAR) type = String.class;
		else if(sqlType==Types.OTHER) type = byte[].class;
		else if(sqlType==Types.REAL) type = BigDecimal.class;
		else if(sqlType==Types.REF) type = byte[].class;
		else if(sqlType==Types.ROWID) type = String.class;
		else if(sqlType==Types.SMALLINT) type = BigDecimal.class;
		else if(sqlType==Types.SQLXML) type = String.class;
		else if(sqlType==Types.STRUCT) type = String.class;
		else if(sqlType==Types.TIME) type = Timestamp.class;
		else if(sqlType==Types.TIMESTAMP) type = Timestamp.class;
		else if(sqlType==Types.TINYINT) type = BigDecimal.class;
		else if(sqlType==Types.VARBINARY) type = byte[].class;
		else if(sqlType==Types.VARCHAR) type = String.class;
		return type;
	}
	
	/**
	 * 
	 * @param sqlType
	 * @return
	 */
	protected static String getClassNameFromSqlType(int sqlType)
	{
		Class<?> javaClass = getJavaClassFromSqlType(sqlType);
		return javaClass.getName();
	}
}
