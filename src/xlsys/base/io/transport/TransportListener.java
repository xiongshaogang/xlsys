package xlsys.base.io.transport;

import java.util.EventListener;
import java.util.List;

import xlsys.base.database.bean.ExecuteBean;

public interface TransportListener extends EventListener
{
	/**
	 * 传输开始前事件, 可对传输参数进行初始化.
	 * <p> 该事件为全局事件.
	 * <li> in		event.session 当前Session(可能为null)
	 * <li> in		event.tsId 当前传输ID
	 * <li> in&out	event.fromTable 源表
	 * <li> in&out	event.toTable 目标表
	 * <li> in&out	event.fromSql 源Sql
	 * <li> out		event.fromDataSet 源数据集. 如果该参数返回不为null, 则会忽略fromSql来查询, 直接使用该数据集作为数据传输的源数据集
	 * <li> in&out	event.batchCount 批量提交数量
	 * <li> in&out	event.cpSmCol 是否拷贝同名列
	 * <li> in		event.threadCount 线程数量
	 * <li> in		event.dataPerThread 每个线程的数据处理量
	 * <li> in		event.dataBase 当前的数据库连接
	 * <li> in		event.fromDataBase 源数据库的连接
	 * <li> in		event.toDataBase 目标数据库的连接
	 * <li> in		event.fromToColMap 列名映射
	 * <li> in		event.globalVarMap 全局变量Map
	 * @param event
	 */
	public void begin(TransportEvent event);
	
	/**
	 * 拆分源Sql语句. 拆分后的每个Sql语句都将放入一个线程中去执行传输过程
	 * <p> 该事件为全局事件.
	 * <li> in		event.session 当前Session(可能为null)
	 * <li> in		event.tsId 当前传输ID
	 * <li> in		event.fromTable 源表
	 * <li> in		event.toTable 目标表
	 * <li> in		event.fromSql 源Sql
	 * <li> in		event.batchCount 批量提交数量
	 * <li> in		event.cpSmCol 是否拷贝同名列
	 * <li> in		event.threadCount 线程数量
	 * <li> in		event.dataPerThread 每个线程的数据处理量
	 * <li> in		event.dataBase 当前的数据库连接
	 * <li> in		event.fromDataBase 源数据库的连接
	 * <li> in		event.toDataBase 目标数据库的连接
	 * <li> in		event.fromToColMap 列名映射
	 * <li> in		event.globalVarMap 全局变量Map
	 * @param event
	 * @return
	 */
	public List<String> splitFromSqlForThread(TransportEvent event);
	
	/**
	 * 传输结束事件, 当前传输明细的所有线程结束后调用.
	 * <p> 该事件为全局事件.
	 * <li> in		event.session 当前Session(可能为null)
	 * <li> in		event.tsId 当前传输ID
	 * <li> in		event.fromTable 源表
	 * <li> in		event.toTable 目标表
	 * <li> in		event.fromSql 源Sql
	 * <li> in		event.batchCount 批量提交数量
	 * <li> in		event.cpSmCol 是否拷贝同名列
	 * <li> in		event.threadCount 线程数量
	 * <li> in		event.dataPerThread 每个线程的数据处理量
	 * <li> in		event.dataBase 当前的数据库连接
	 * <li> in		event.fromDataBase 源数据库的连接
	 * <li> in		event.toDataBase 目标数据库的连接
	 * <li> in		event.fromToColMap 列名映射
	 * <li> in		event.globalVarMap 全局变量Map
	 * @param event
	 */
	public void end(TransportEvent event);
	
	/**
	 * 线程传输开始事件.
	 * <p> 该事件为线程事件.
	 * <li> in		event.session 当前Session(可能为null)
	 * <li> in		event.tsId 当前传输ID
	 * <li> in&out	event.fromDataSet 源数据集
	 * <li> in&out	event.batchCount 批量提交数量
	 * <li> in&out	event.cpSmCol 是否拷贝同名列
	 * <li> in		event.dataBase 当前的数据库连接
	 * <li> in		event.fromDataBase 源数据库的连接
	 * <li> in		event.toDataBase 目标数据库的连接
	 * <li> in		event.fromToColMap 列名映射
	 * <li> in		event.globalVarMap 全局变量Map
	 * <li> in		event.threadVarMap 线程变量Map
	 * @param event
	 */
	public void threadBegin(TransportEvent event);
	
	/**
	 * 是否需要处理当前行
	 * <p> 该事件为线程事件
	 * <li> in		event.session 当前Session(可能为null)
	 * <li> in		event.tsId 当前传输ID
	 * <li> in		event.fromTable 源表
	 * <li> in		event.toTable 目标表
	 * <li> in		event.dataBase 当前的数据库连接
	 * <li> in		event.fromDataBase 源数据库的连接
	 * <li> in		event.toDataBase 目标数据库的连接
	 * <li> in		event.globalVarMap 全局变量Map
	 * <li> in		event.threadVarMap 线程变量Map
	 * <li> in		event.fromDataMap 当前读入的数据行
	 * @param event
	 */
	public Boolean threadNeedRow(TransportEvent event);
	
	/**
	 * 当前行的处理方式, 返回值可为参见{@link ExecuteBean}
	 * <p> 该事件为线程事件
	 * <li> in		event.session 当前Session(可能为null)
	 * <li> in		event.tsId 当前传输ID
	 * <li> in		event.fromTable 源表
	 * <li> in		event.toTable 目标表
	 * <li> in		event.dataBase 当前的数据库连接
	 * <li> in		event.fromDataBase 源数据库的连接
	 * <li> in		event.toDataBase 目标数据库的连接
	 * <li> in		event.globalVarMap 全局变量Map
	 * <li> in		event.threadVarMap 线程变量Map
	 * <li> in		event.fromDataMap 当前读入的数据行
	 * @param event
	 */
	public Integer threadExecuteType(TransportEvent event);
	
	/**
	 * 数据行传输前事件
	 * <p> 该事件为线程事件
	 * <li> in		event.session 当前Session(可能为null)
	 * <li> in		event.tsId 当前传输ID
	 * <li> in		event.fromTable 源表
	 * <li> in		event.toTable 目标表
	 * <li> in		event.dataBase 当前的数据库连接
	 * <li> in		event.fromDataBase 源数据库的连接
	 * <li> in		event.toDataBase 目标数据库的连接
	 * <li> in		event.globalVarMap 全局变量Map
	 * <li> in		event.threadVarMap 线程变量Map
	 * <li> in		event.fromDataMap 当前读入的数据行
	 * <li> in&out	event.toDataMap 当前要传输的数据行
	 * @param event
	 */
	public void threadBeforeTransport(TransportEvent event);
	
	/**
	 * 数据行传输后事件
	 * <p> 该事件为线程事件
	 * <li> in		event.session 当前Session(可能为null)
	 * <li> in		event.tsId 当前传输ID
	 * <li> in		event.fromTable 源表
	 * <li> in		event.toTable 目标表
	 * <li> in		event.dataBase 当前的数据库连接
	 * <li> in		event.fromDataBase 源数据库的连接
	 * <li> in		event.toDataBase 目标数据库的连接
	 * <li> in		event.globalVarMap 全局变量Map
	 * <li> in		event.threadVarMap 线程变量Map
	 * <li> in		event.fromDataMap 当前读入的数据行
	 * <li> in		event.toDataMap 当前要传输的数据行
	 * @param event
	 */
	public void threadAfterTransport(TransportEvent event);
	
	/**
	 * 线程传输结束事件.
	 * <p> 该事件为线程事件.
	 * <li> in		event.session 当前Session(可能为null)
	 * <li> in		event.tsId 当前传输ID
	 * <li> in		event.fromDataSet 源数据集
	 * <li> in		event.batchCount 批量提交数量
	 * <li> in		event.cpSmCol 是否拷贝同名列
	 * <li> in		event.dataBase 当前的数据库连接
	 * <li> in		event.fromDataBase 源数据库的连接
	 * <li> in		event.toDataBase 目标数据库的连接
	 * <li> in		event.fromToColMap 列名映射
	 * <li> in		event.globalVarMap 全局变量Map
	 * <li> in		event.threadVarMap 线程变量Map
	 * @param event
	 */
	public void threadEnd(TransportEvent event);
}
