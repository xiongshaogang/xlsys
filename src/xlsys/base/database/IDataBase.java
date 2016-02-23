package xlsys.base.database;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.bean.ISqlBean;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.dataset.IDataSet;

/**
 * 数据库交互接口，所有的数据库交互类都必须实现此接口
 * @author Lewis
 *
 */
public interface IDataBase
{
	/**
	 * 获取当前的数据库ID
	 * @return
	 */
	public int getDataBaseId();
	
	/**
	 * 获取表信息
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public TableInfo getTableInfo(String tableName) throws Exception;
	
	/**
	 * 获取所有的表基本信息
	 * @return 返回表的基本信息映射.其中,key为表名,value为表注释
	 * @throws Exception
	 */
	public Map<String, String> getAllTableBaseInfo() throws Exception;
	
	/**
	 * 获取要查询的语句的返回行数
	 * @param paramBean
	 * @return
	 * @throws Exception
	 */
	public int getResultCount(ParamBean paramBean) throws Exception;
	
	/**
	 * 查询sql语句，返回第一行第一列的值
	 * @param selectSql
	 * @return
	 * @throws Exception
	 */
	public Serializable sqlSelectAsOneValue(String selectSql) throws Exception;
	
	/**
	 * 查询sql语句，返回第一行第一列的值
	 * @param paramBean
	 * @return
	 * @throws Exception
	 */
	public Serializable sqlSelectAsOneValue(ParamBean paramBean) throws Exception;
	
	/**
	 * 查询sql语句，返回数据集
	 * @param selectSql
	 * @return
	 * @throws Exception
	 */
	public IDataSet sqlSelect(String selectSql) throws Exception;
	
	/**
	 * 查询sql语句，返回数据集
	 * @param paramBean
	 * @return
	 * @throws Exception
	 */
	public IDataSet sqlSelect(ParamBean paramBean) throws Exception;
	
	/**
	 * 查询指定开始行号和结束行号的sql语句，返回数据集
	 * @param paramBean
	 * @param beginRowNum
	 * @param endRowNum
	 * @return
	 * @throws Exception
	 */
	public IDataSet sqlSelect(ParamBean paramBean, int beginRowNum, int endRowNum) throws Exception;
	
	/**
	 * 此方法先查询出所有的数据，再对查询后的数据进行排序，最后返回所需要的数据
	 * @param paramBean 查询Bean
	 * @param sortColMap 要排序的列及列排序类型Map, 列的排序类型详见IDataSet
	 * @param beginRowNum 要获得的开始行号,从0开始
	 * @param endRowNum 要获得的结束行号,从0开始
	 * @return
	 * @throws Exception
	 */
	public IDataSet sqlSelect(ParamBean paramBean, Map<String, Integer> sortColMap, int beginRowNum, int endRowNum) throws Exception;
	
	/**
	 * 执行sql语句
	 * @param executeSql
	 * @return
	 * @throws Exception
	 */
	public boolean sqlExecute(String executeSql) throws Exception;
	
	/**
	 * 执行sql语句
	 * @param executeBean
	 * @return
	 * @throws Exception
	 */
	public boolean sqlExecute(ExecuteBean executeBean) throws Exception;
	
	/**
	 * 执行sql语句列表
	 * @param sqlBeanList
	 * @return
	 * @throws Exception
	 */
	public boolean sqlExecute(List<? extends ISqlBean> sqlBeanList) throws Exception;
	
	/**
	 * 执行sql语句
	 * @param paramBean
	 * @return
	 * @throws Exception
	 */
	public boolean sqlExecute(ParamBean paramBean) throws Exception;
	
	/**
	 * 屏蔽约束
	 * @param tableName 表名
	 * @param constraintName 约束名称
	 * @return
	 * @throws Exception
	 */
	public boolean disableConstraint(String tableName, String constraintName) throws Exception;
	
	/**
	 * 启用约束
	 * @param tableName 表名
	 * @param constraintName 约束名称
	 * @return
	 * @throws Exception
	 */
	public boolean enableConstraint(String tableName, String constraintName) throws Exception;
	
	/**
	 * 获取转小写函数表达式
	 * @param srcExp 源表达式
	 * @return
	 */
	public String getLowerFunc(String srcExp);
	
	/**
	 * 获取转大小函数表达式
	 * @param srcExp 源表达式
	 * @return
	 */
	public String getUpperFunc(String srcExp);
	
	/**
	 * 获取字符串字节数长度函数表达式
	 * @param srcExp 源表达式
	 * @return
	 */
	public String getByteLengthFunc(String srcExp);
	
	/**
	 * 获取截取字符串的函数表达式
	 * @param srcExp 源表达式
	 * @param begin 起始索引(包括,从0开始)
	 * @return
	 */
	public String getSubStringFunc(String srcExp, int begin);
	
	/**
	 * 获取截取字符串的函数表达式
	 * @param srcExp 源表达式
	 * @param begin 起始索引(包括,从0开始)
	 * @param end 结束索引(不包括)
	 * @return
	 */
	public String getSubStringFunc(String srcExp, int begin, int end);
	
	/**
	 * 获取转换日期的函数表达式
	 * @param date Java中的Date对象
	 * @return
	 */
	public String getToDateFunc(Date date);
	
	/**
	 * 提交
	 * @throws Exception
	 */
	public void commit() throws Exception;
	
	/**
	 * 回滚
	 * @throws Exception
	 */
	public void rollback() throws Exception;
	
	/**
	 * 设置自动提交
	 * @param autoCommit
	 * @throws Exception
	 */
	public void setAutoCommit(boolean autoCommit) throws Exception;
	
	/**
	 * 获取是否自动提交
	 * @return
	 * @throws Exception
	 */
	public boolean getAutoCommit() throws Exception;
	
	/**
	 * 回收当前连接
	 * @throws Exception
	 */
	public void close() throws Exception;
	
	/**
	 * 当前连接是否已被回收
	 * @return
	 */
	public boolean isClose();
}
