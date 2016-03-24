package xlsys.base.database;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.bean.ISqlBean;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.SqlUtil;
import xlsys.base.dataset.IDataSet;
import xlsys.base.env.Env;
import xlsys.base.log.LogUtil;

/**
 * 环境数据库连接类. 该类不会指定具体的数据库, 会根据要查询或者更新的表来动态连接对应的数据库
 * @author Lewis
 *
 */
public abstract class IEnvDataBase implements IDataBase
{
	private Env env;
	private int lastDbId;
	private Map<Integer, IDataBase> dataBaseMap;
	
	protected IEnvDataBase(int envId) throws Exception
	{
		env = getEnvModel(envId);
		lastDbId = -1;
		dataBaseMap = new HashMap<Integer, IDataBase>();
	}
	
	protected abstract Env getEnvModel(int envId) throws Exception;
	
	/**
	 * 获取当前的环境ID
	 * @return
	 */
	public int getEnvId()
	{
		return env.getEnvId();
	}
	
	/**
	 * 返回最近一次使用的数据库ID
	 * @return 如果还未使用过, 则返回-1
	 */
	@Override
	@Deprecated
	public int getDataBaseId()
	{
		return lastDbId;
	}
	
	/**
	 * 根据sql语句获取对应的数据库连接ID, 如果获取不到, 则使用最近一次使用的数据库连接
	 * @param sql
	 * @return
	 */
	private int getDbIdBySql(String sql)
	{
		Set<Integer> dbIdSet = env.getDbIdSet();
		int dbid = -1;
		if(dbIdSet.size()==1)
		{
			// 当只有一个数据库时, 直接返回
			dbid = dbIdSet.iterator().next();
		}
		else
		{
			// 解析sql语句, 获取对应的表名
			String tableName = SqlUtil.tryToGetTableName(sql);
			if(tableName==null) dbid = lastDbId;
			else dbid = env.getDbIdByTableName(tableName);
		}
		return dbid;
	}
	
	private IDataBase getDataBase(String tableName)
	{
		int dbid = env.getDbIdByTableName(tableName);
		return getDataBase(dbid);
	}
	
	private IDataBase getDataBase(int dbId)
	{
		return getDataBase(dbId, true);
	}
	
	private synchronized IDataBase getDataBase(int dbId, boolean changeLast)
	{
		if(dbId==-1) dbId = env.getDbIdSet().iterator().next();
		IDataBase dataBase = dataBaseMap.get(dbId);
		if(dataBase==null)
		{
			try
			{
				dataBase = getNewDataBase(dbId);
			}
			catch (Exception e)
			{
				LogUtil.printlnError(e);
			}
			dataBaseMap.put(dbId, dataBase);
		}
		if(changeLast) lastDbId = dbId;
		return dataBase;
	}
	
	/**
	 * 根据dbid获取新的数据库连接
	 * @param dbId
	 * @return
	 */
	protected abstract IDataBase getNewDataBase(int dbId) throws Exception;
	
	@Override
	public TableInfo getTableInfo(String tableName) throws Exception
	{
		IDataBase dataBase = getDataBase(tableName);
		return dataBase.getTableInfo(tableName);
	}
	
	/**
	 * 获取表信息
	 * @param tableName
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 * @throws Exception
	 */
	public TableInfo getTableInfo(String tableName, int dbid) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.getTableInfo(tableName);
	}
	
	@Override
	public Map<String, String> getAllTableBaseInfo() throws Exception
	{
		Map<String, String> allInfo = new LinkedHashMap<String, String>();
		for(int dbid : env.getDbIdSet())
		{
			IDataBase tempDataBase = getDataBase(dbid, false);
			allInfo.putAll(tempDataBase.getAllTableBaseInfo());
		}
		return allInfo;
	}

	/**
	 * 获取所有的表基本信息
	 * @param dbid 使用此数据库来获取结果
	 * @return 返回表的基本信息映射.其中,key为表名,value为表注释
	 * @throws Exception
	 */
	public Map<String, String> getAllTableBaseInfo(int dbid) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.getAllTableBaseInfo();
	}
	
	@Override
	public int getResultCount(ParamBean paramBean) throws Exception
	{
		int dbid = getDbIdBySql(paramBean.getSelectSql());
		return getResultCount(paramBean, dbid);
	}
	
	/**
	 * 获取要查询的语句的返回行数
	 * @param paramBean
	 * @param tableName 通过此表名确定要连接的数据库
	 * @return
	 * @throws Exception
	 */
	public int getResultCount(ParamBean paramBean, String tableName) throws Exception
	{
		return getResultCount(paramBean, env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 获取要查询的语句的返回行数
	 * @param paramBean
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 * @throws Exception
	 */
	public int getResultCount(ParamBean paramBean, int dbid) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.getResultCount(paramBean);
	}
	
	@Override
	public Serializable sqlSelectAsOneValue(String selectSql) throws Exception
	{
		int dbid = getDbIdBySql(selectSql);
		return sqlSelectAsOneValue(selectSql, dbid);
	}
	
	/**
	 * 查询sql语句，返回第一行第一列的值
	 * @param selectSql
	 * @param tableName 通过此表名确定要连接的数据库
	 * @return
	 * @throws Exception
	 */
	public Serializable sqlSelectAsOneValue(String selectSql, String tableName) throws Exception
	{
		return sqlSelectAsOneValue(selectSql, env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 查询sql语句，返回第一行第一列的值
	 * @param selectSql
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 * @throws Exception
	 */
	public Serializable sqlSelectAsOneValue(String selectSql, int dbid) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.sqlSelectAsOneValue(selectSql);
	}
	
	@Override
	public Serializable sqlSelectAsOneValue(ParamBean paramBean) throws Exception
	{
		int dbid = getDbIdBySql(paramBean.getSelectSql());
		return sqlSelectAsOneValue(paramBean, dbid);
	}
	
	/**
	 * 查询sql语句，返回第一行第一列的值
	 * @param paramBean
	 * @param tableName 通过此表名确定要连接的数据库
	 * @return
	 * @throws Exception
	 */
	public Serializable sqlSelectAsOneValue(ParamBean paramBean, String tableName) throws Exception
	{
		return sqlSelectAsOneValue(paramBean, env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 查询sql语句，返回第一行第一列的值
	 * @param paramBean
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 * @throws Exception
	 */
	public Serializable sqlSelectAsOneValue(ParamBean paramBean, int dbid) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.sqlSelectAsOneValue(paramBean);
	}

	@Override
	public IDataSet sqlSelect(String selectSql) throws Exception
	{
		int dbid = getDbIdBySql(selectSql);
		return sqlSelect(selectSql, dbid);
	}
	
	/**
	 * 查询sql语句，返回数据集
	 * @param selectSql
	 * @param tableName 通过此表名确定要连接的数据库
	 * @return
	 * @throws Exception
	 */
	public IDataSet sqlSelect(String selectSql, String tableName) throws Exception
	{
		return sqlSelect(selectSql, env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 查询sql语句，返回数据集
	 * @param selectSql
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 * @throws Exception
	 */
	public IDataSet sqlSelect(String selectSql, int dbid) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.sqlSelect(selectSql);
	}
	
	@Override
	public IDataSet sqlSelect(ParamBean paramBean) throws Exception
	{
		int dbid = getDbIdBySql(paramBean.getSelectSql());
		return sqlSelect(paramBean, dbid);
	}
	
	/**
	 * 查询sql语句，返回数据集
	 * @param paramBean
	 * @param tableName 通过此表名确定要连接的数据库
	 * @return
	 * @throws Exception
	 */
	public IDataSet sqlSelect(ParamBean paramBean, String tableName) throws Exception
	{
		return sqlSelect(paramBean, env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 查询sql语句，返回数据集
	 * @param paramBean
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 * @throws Exception
	 */
	public IDataSet sqlSelect(ParamBean paramBean, int dbid) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.sqlSelect(paramBean);
	}
	
	@Override
	public IDataSet sqlSelect(ParamBean paramBean, int beginRowNum, int endRowNum) throws Exception
	{
		int dbid = getDbIdBySql(paramBean.getSelectSql());
		return sqlSelect(paramBean, dbid, beginRowNum, endRowNum);
	}
	
	/**
	 * 查询指定开始行号和结束行号的sql语句，返回数据集
	 * @param paramBean
	 * @param tableName 通过此表名确定要连接的数据库
	 * @param beginRowNum
	 * @param endRowNum
	 * @return
	 * @throws Exception
	 */
	public IDataSet sqlSelect(ParamBean paramBean, String tableName, int beginRowNum, int endRowNum) throws Exception
	{
		return sqlSelect(paramBean, env.getDbIdByTableName(tableName), beginRowNum, endRowNum);
	}
	
	/**
	 * 查询指定开始行号和结束行号的sql语句，返回数据集
	 * @param paramBean
	 * @param dbid 使用此数据库来获取结果
	 * @param beginRowNum
	 * @param endRowNum
	 * @return
	 * @throws Exception
	 */
	public IDataSet sqlSelect(ParamBean paramBean, int dbid, int beginRowNum, int endRowNum) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.sqlSelect(paramBean, beginRowNum, endRowNum);
	}
	
	@Override
	public IDataSet sqlSelect(ParamBean paramBean, Map<String, Integer> sortColMap, int beginRowNum, int endRowNum) throws Exception
	{
		int dbid = getDbIdBySql(paramBean.getSelectSql());
		return sqlSelect(paramBean, dbid, sortColMap, beginRowNum, endRowNum);
	}
	
	/**
	 * 此方法先查询出所有的数据，再对查询后的数据进行排序，最后返回所需要的数据
	 * @param paramBean
	 * @param tableName 通过此表名确定要连接的数据库
	 * @param sortColMap
	 * @param beginRowNum
	 * @param endRowNum
	 * @return
	 * @throws Exception
	 */
	public IDataSet sqlSelect(ParamBean paramBean, String tableName, Map<String, Integer> sortColMap, int beginRowNum, int endRowNum) throws Exception
	{
		return sqlSelect(paramBean, env.getDbIdByTableName(tableName), sortColMap, beginRowNum, endRowNum);
	}
	
	/**
	 * 此方法先查询出所有的数据，再对查询后的数据进行排序，最后返回所需要的数据
	 * @param paramBean
	 * @param dbid 使用此数据库来获取结果
	 * @param sortColMap
	 * @param beginRowNum
	 * @param endRowNum
	 * @return
	 * @throws Exception
	 */
	public IDataSet sqlSelect(ParamBean paramBean, int dbid, Map<String, Integer> sortColMap, int beginRowNum, int endRowNum) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.sqlSelect(paramBean, sortColMap, beginRowNum, endRowNum);
	}
	
	@Override
	public boolean sqlExecute(String executeSql) throws Exception
	{
		int dbid = getDbIdBySql(executeSql);
		return sqlExecute(executeSql, dbid);
	}
	
	/**
	 * 执行sql语句
	 * @param executeSql
	 * @param tableName 通过此表名确定要连接的数据库
	 * @return
	 * @throws Exception
	 */
	public boolean sqlExecute(String executeSql, String tableName) throws Exception
	{
		return sqlExecute(executeSql, env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 执行sql语句
	 * @param executeSql
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 * @throws Exception
	 */
	public boolean sqlExecute(String executeSql, int dbid) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.sqlExecute(executeSql);
	}
	
	@Override
	public boolean sqlExecute(ExecuteBean executeBean) throws Exception
	{
		return sqlExecute(executeBean, env.getDbIdByTableName(executeBean.getTableName()));
	}
	
	/**
	 * 执行sql语句
	 * @param executeBean
	 * @param tableName 通过此表名确定要连接的数据库
	 * @return
	 * @throws Exception
	 */
	public boolean sqlExecute(ExecuteBean executeBean, String tableName) throws Exception
	{
		return sqlExecute(executeBean, env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 执行sql语句
	 * @param executeBean
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 * @throws Exception
	 */
	public boolean sqlExecute(ExecuteBean executeBean, int dbid) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.sqlExecute(executeBean);
	}
	
	/**
	 * 执行sql语句列表. 注意, 该方法会逐个执行每个sqlBean
	 * @param sqlBeanList
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean sqlExecute(List<? extends ISqlBean> sqlBeanList) throws Exception
	{
		if(sqlBeanList.size()==0) return true;
		boolean success = true;
		for(ISqlBean sqlBean : sqlBeanList)
		{
			if(sqlBean instanceof ExecuteBean) success &= sqlExecute((ExecuteBean)sqlBean);
			else if(sqlBean instanceof ParamBean) success &= sqlExecute((ParamBean)sqlBean);
			if(!success) break;
		}
		return success;
	}
	
	/**
	 * 执行sql语句列表. 注意:所有ISqlBean都将在一个数据库实例中执行
	 * @param sqlBeanList
	 * @param tableName 通过此表名确定要连接的数据库
	 * @return
	 * @throws Exception
	 */
	public boolean sqlExecute(List<? extends ISqlBean> sqlBeanList, String tableName) throws Exception
	{
		return sqlExecute(sqlBeanList, env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 执行sql语句列表. 注意:所有ISqlBean都将在一个数据库实例中执行
	 * @param sqlBeanList
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 * @throws Exception
	 */
	public boolean sqlExecute(List<? extends ISqlBean> sqlBeanList, int dbid) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.sqlExecute(sqlBeanList);
	}
	
	@Override
	public boolean sqlExecute(ParamBean paramBean) throws Exception
	{
		int dbid = getDbIdBySql(paramBean.getSelectSql());
		return sqlExecute(paramBean, dbid);
	}
	
	/**
	 * 执行sql语句
	 * @param paramBean
	 * @param tableName 通过此表名确定要连接的数据库
	 * @return
	 * @throws Exception
	 */
	public boolean sqlExecute(ParamBean paramBean, String tableName) throws Exception
	{
		return sqlExecute(paramBean, env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 执行sql语句
	 * @param paramBean
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 * @throws Exception
	 */
	public boolean sqlExecute(ParamBean paramBean, int dbid) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.sqlExecute(paramBean);
	}
	
	/**
	 * 屏蔽约束
	 * @param tableName 约束对应的表名. 也会通过此表名确定要连接的数据库
	 * @param constraintName
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean disableConstraint(String tableName, String constraintName) throws Exception
	{
		return disableConstraint(tableName, env.getDbIdByTableName(tableName), constraintName);
	}
	
	/**
	 * 屏蔽约束
	 * @param tableName
	 * @param dbid 使用此数据库来获取结果
	 * @param constraintName
	 * @return
	 * @throws Exception
	 */
	public boolean disableConstraint(String tableName, int dbid, String constraintName) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.disableConstraint(tableName, constraintName);
	}
	
	/**
	 * 启用约束
	 * @param tableName 约束对应的表名. 也会通过此表名确定要连接的数据库
	 * @param constraintName 约束名称
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean enableConstraint(String tableName, String constraintName) throws Exception
	{
		return enableConstraint(tableName, env.getDbIdByTableName(tableName), constraintName);
	}
	
	/**
	 * 启用约束
	 * @param tableName 表名
	 * @param dbid 使用此数据库来获取结果
	 * @param constraintName
	 * @return
	 * @throws Exception
	 */
	public boolean enableConstraint(String tableName, int dbid, String constraintName) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.enableConstraint(tableName, constraintName);
	}
	
	/**
	 * 获取转小写函数表达式. 注意:该方法会使用当前对象最后一次所访问的数据库实例来查询
	 * @param srcExp 源表达式
	 * @return
	 */
	@Override
	public String getLowerFunc(String srcExp)
	{
		return getLowerFunc(srcExp, lastDbId);
	}
	
	/**
	 * 获取转小写函数表达式
	 * @param srcExp
	 * @param tableName 通过此表名确定要连接的数据库
	 * @return
	 */
	public String getLowerFunc(String srcExp, String tableName)
	{
		return getLowerFunc(srcExp, env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 获取转小写函数表达式
	 * @param srcExp
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 */
	public String getLowerFunc(String srcExp, int dbid)
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.getLowerFunc(srcExp);
	}
	
	/**
	 * 获取转大小函数表达式. 注意:该方法会使用当前对象最后一次所访问的数据库实例来查询
	 * @param srcExp 源表达式
	 * @return
	 */
	@Override
	public String getUpperFunc(String srcExp)
	{
		return getUpperFunc(srcExp, lastDbId);
	}
	
	/**
	 * 获取转大小函数表达式
	 * @param srcExp
	 * @param tableName 通过此表名确定要连接的数据库
	 * @return
	 */
	public String getUpperFunc(String srcExp, String tableName)
	{
		return getUpperFunc(srcExp, env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 获取转大小函数表达式
	 * @param srcExp
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 */
	public String getUpperFunc(String srcExp, int dbid)
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.getUpperFunc(srcExp);
	}
	
	/**
	 * 获取字符串字节数长度函数表达式. 注意:该方法会使用当前对象最后一次所访问的数据库实例来查询
	 * @param srcExp 源表达式
	 * @return
	 */
	@Override
	public String getByteLengthFunc(String srcExp)
	{
		return getByteLengthFunc(srcExp, lastDbId);
	}
	
	/**
	 * 获取字符串字节数长度函数表达式
	 * @param srcExp
	 * @param tableName 通过此表名确定要连接的数据库
	 * @return
	 */
	public String getByteLengthFunc(String srcExp, String tableName)
	{
		return getByteLengthFunc(srcExp, env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 获取字符串字节数长度函数表达式
	 * @param srcExp
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 */
	public String getByteLengthFunc(String srcExp, int dbid)
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.getByteLengthFunc(srcExp);
	}
	
	/**
	 * 获取截取字符串的函数表达式. 注意:该方法会使用当前对象最后一次所访问的数据库实例来查询
	 * @param srcExp 源表达式
	 * @param begin 起始索引(包括,从0开始)
	 * @return
	 */
	@Override
	public String getSubStringFunc(String srcExp, int begin)
	{
		return getSubStringFunc(lastDbId, srcExp, begin);
	}
	
	/**
	 * 获取截取字符串的函数表达式
	 * @param srcExp
	 * @param tableName 通过此表名确定要连接的数据库
	 * @param begin
	 * @return
	 */
	public String getSubStringFunc(String srcExp, String tableName, int begin)
	{
		return getSubStringFunc(env.getDbIdByTableName(tableName), srcExp, begin);
	}
	
	/**
	 * 获取截取字符串的函数表达式
	 * @param dbid 使用此数据库来获取结果
	 * @param srcExp
	 * @param begin
	 * @return
	 */
	public String getSubStringFunc(int dbid, String srcExp, int begin)
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.getSubStringFunc(srcExp, begin);
	}
	
	/**
	 * 获取截取字符串的函数表达式. 注意:该方法会使用当前对象最后一次所访问的数据库实例来查询
	 * @param srcExp 源表达式
	 * @param begin 起始索引(包括,从0开始)
	 * @param end 结束索引(不包括)
	 * @return
	 */
	@Override
	public String getSubStringFunc(String srcExp, int begin, int end)
	{
		return getSubStringFunc(srcExp, lastDbId, begin, end);
	}
	
	/**
	 * 获取截取字符串的函数表达式
	 * @param srcExp
	 * @param tableName 通过此表名确定要连接的数据库
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getSubStringFunc(String srcExp, String tableName, int begin, int end)
	{
		return getSubStringFunc(srcExp, env.getDbIdByTableName(tableName), begin, end);
	}
	
	/**
	 * 获取截取字符串的函数表达式
	 * @param srcExp
	 * @param dbid 使用此数据库来获取结果
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getSubStringFunc(String srcExp, int dbid, int begin, int end)
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.getSubStringFunc(srcExp, begin, end);
	}
	
	/**
	 * 获取转换日期的函数表达式. 注意:该方法会使用当前对象最后一次所访问的数据库实例来查询
	 * @param date Java中的Date对象
	 * @return
	 */
	@Override
	public String getToDateFunc(Date date)
	{
		return getToDateFunc(date, lastDbId);
	}
	
	/**
	 * 获取转换日期的函数表达式
	 * @param date
	 * @param tableName 通过此表名确定要连接的数据库
	 * @return
	 */
	public String getToDateFunc(Date date, String tableName)
	{
		return getToDateFunc(date, env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 获取转换日期的函数表达式
	 * @param date
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 */
	public String getToDateFunc(Date date, int dbid)
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.getToDateFunc(date);
	}
	
	/**
	 * 提交. 注意:该方法会提交当前环境掌控下的所有数据库连接
	 * @throws Exception
	 */
	@Override
	public void commit() throws Exception
	{
		for(int dbid : dataBaseMap.keySet()) commit(dbid);
	}
	
	/**
	 * 提交
	 * @param tableName 通过此表名确定要连接的数据库
	 * @throws Exception
	 */
	public void commit(String tableName) throws Exception
	{
		commit(env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 提交
	 * @param dbid 使用此数据库来获取结果
	 * @throws Exception
	 */
	public void commit(int dbid) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		dataBase.commit();
	}
	
	/**
	 * 回滚. 注意:该方法会回滚当前环境掌控下的所有数据库连接
	 * @throws Exception
	 */
	@Override
	public void rollback() throws Exception
	{
		for(int dbid : dataBaseMap.keySet()) rollback(dbid);
	}
	
	/**
	 * 回滚.
	 * @param tableName 通过此表名确定要连接的数据库
	 * @throws Exception
	 */
	public void rollback(String tableName) throws Exception
	{
		rollback(env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 回滚.
	 * @param dbid 使用此数据库来获取结果
	 * @throws Exception
	 */
	public void rollback(int dbid) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		dataBase.rollback();
	}
	
	/**
	 * 设置自动提交. 注意:该方法会设置当前环境掌控下的所有数据库连接的提交状态
	 * @param autoCommit
	 * @throws Exception
	 */
	@Override
	public void setAutoCommit(boolean autoCommit) throws Exception
	{
		for(int dbid : env.getDbIdSet()) setAutoCommit(autoCommit, dbid);
	}
	
	/**
	 * 设置自动提交.
	 * @param autoCommit
	 * @param tableName 通过此表名确定要连接的数据库
	 * @throws Exception 
	 */
	public void setAutoCommit(boolean autoCommit, String tableName) throws Exception
	{
		setAutoCommit(autoCommit, env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 设置自动提交.
	 * @param autoCommit
	 * @param dbid 使用此数据库来获取结果
	 * @throws Exception
	 */
	public void setAutoCommit(boolean autoCommit, int dbid) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		dataBase.setAutoCommit(autoCommit);
	}
	
	/**
	 * 获取是否自动提交. 注意:该方法会使用当前对象最后一次所访问的数据库实例来进行操作
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean getAutoCommit() throws Exception
	{
		if(lastDbId==-1) return true;
		return getAutoCommit(lastDbId);
	}
	
	/**
	 * 获取是否自动提交.
	 * @param tableName 通过此表名确定要连接的数据库
	 * @return
	 * @throws Exception
	 */
	public boolean getAutoCommit(String tableName) throws Exception
	{
		return getAutoCommit(env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 获取是否自动提交.
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 * @throws Exception
	 */
	public boolean getAutoCommit(int dbid) throws Exception
	{
		IDataBase dataBase = getDataBase(dbid);
		return dataBase.getAutoCommit();
	}
	
	/**
	 * 回收当前连接. 注意:该方法会关闭当前环境掌控下的所有数据库连接
	 * @throws Exception
	 */
	@Override
	public void close() throws Exception
	{
		Integer[] dbids = dataBaseMap.keySet().toArray(new Integer[0]);
		for(int dbid : dbids) close(dbid);
	}
	
	/**
	 * 回收当前连接. 
	 * @param tableName 通过此表名确定要连接的数据库
	 * @throws Exception
	 */
	public void close(String tableName) throws Exception
	{
		close(env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 回收当前连接. 
	 * @param dbid 使用此数据库来获取结果
	 * @throws Exception
	 */
	public synchronized void close(int dbid) throws Exception
	{
		IDataBase dataBase = dataBaseMap.get(dbid);
		if(dataBase!=null)
		{
			dataBase.close();
			dataBaseMap.remove(dbid);
		}
	}
	
	/**
	 * 当前连接是否已被回收. 注意:该方法会使用当前对象最后一次所访问的数据库实例来进行操作
	 * @return
	 */
	@Override
	public boolean isClose()
	{
		return isClose(lastDbId);
	}
	
	/**
	 * 当前连接是否已被回收. 
	 * @param tableName 通过此表名确定要连接的数据库
	 * @return
	 */
	public boolean isClose(String tableName)
	{
		return isClose(env.getDbIdByTableName(tableName));
	}
	
	/**
	 * 当前连接是否已被回收. 
	 * @param dbid 使用此数据库来获取结果
	 * @return
	 */
	public synchronized boolean isClose(int dbid)
	{
		IDataBase dataBase = dataBaseMap.get(dbid);
		if(dataBase==null) return true;
		return dataBase.isClose();
	}
}
