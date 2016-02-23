package xlsys.base.io.transport;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.database.ConnectionPool;
import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.dataset.IDataSet;
import xlsys.base.log.LogUtil;
import xlsys.base.session.Session;

public class TransportThread extends Thread
{
	private Session session;
	private String tsId;
	private Set<TransportListener> listenerSet;
	private String fromTable;
	private String toTable;
	private String fromSql;
	private IDataSet fromDataSet;
	private Set<String> toColSet;
	private int batchCount;
	private boolean cpSmCol;
	private int dbId;
	private int fromDsId;
	private int toDsId;
	private Map<String, String> fromToColMap;
	private Map<String, Serializable> globalVarMap;
	private Map<String, Serializable> threadVarMap;

	public TransportThread(Session session, String tsId, Set<TransportListener> listenerSet, String fromTable, String toTable, String fromSql, Set<String> toColSet, int batchCount, boolean cpSmCol, int dbId, int fromDsId, int toDsId, Map<String, String> fromToColMap, Map<String, Serializable> globalVarMap)
	{
		this.session = session;
		this.tsId = tsId;
		this.listenerSet = listenerSet;
		this.fromTable = fromTable;
		this.toTable = toTable;
		this.fromSql = fromSql;
		this.toColSet = toColSet;
		this.batchCount = batchCount;
		this.cpSmCol = cpSmCol;
		this.dbId = dbId;
		this.fromDsId = fromDsId;
		this.toDsId = toDsId;
		this.fromToColMap = fromToColMap;
		this.globalVarMap = globalVarMap;
		threadVarMap = new HashMap<String, Serializable>();
	}
	
	public TransportThread(Session session, String tsId, Set<TransportListener> listenerSet, String fromTable, String toTable, IDataSet fromDataSet, Set<String> toColSet, int batchCount, boolean cpSmCol, int dbId, int fromDsId, int toDsId, Map<String, String> fromToColMap, Map<String, Serializable> globalVarMap)
	{
		this.session = session;
		this.tsId = tsId;
		this.listenerSet = listenerSet;
		this.fromTable = fromTable;
		this.toTable = toTable;
		this.fromDataSet = fromDataSet;
		this.toColSet = toColSet;
		this.batchCount = batchCount;
		this.cpSmCol = cpSmCol;
		this.dbId = dbId;
		this.fromDsId = fromDsId;
		this.toDsId = toDsId;
		this.fromToColMap = fromToColMap;
		this.globalVarMap = globalVarMap;
		threadVarMap = new HashMap<String, Serializable>();
	}

	@Override
	public void run()
	{
		IDataBase dataBase = null;
		IDataBase fromDataBase = null;
		IDataBase toDataBase = null;
		boolean success = false;
		try
		{
			LogUtil.printlnInfo("Thread : " + getId() + " run");
			dataBase = ((ConnectionPool)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbId)).getNewDataBase();
			fromDataBase = ((ConnectionPool)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(fromDsId)).getNewDataBase();
			toDataBase = ((ConnectionPool)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(toDsId)).getNewDataBase();
			toDataBase.setAutoCommit(false);
			if(fromDataSet==null) fromDataSet = fromDataBase.sqlSelect(fromSql);
			// 开始事件
			TransportEvent event = new TransportEvent(this);
			event.session = session;
			event.tsId = tsId;
			event.fromDataSet = fromDataSet;
			event.batchCount = batchCount;
			event.cpSmCol = cpSmCol;
			event.dataBase = dataBase;
			event.fromDataBase = fromDataBase;
			event.toDataBase = toDataBase;
			event.fromToColMap = fromToColMap;
			event.globalVarMap = globalVarMap;
			event.threadVarMap = threadVarMap;
			fireThreadBegin(event);
			Transport.checkEvent(event);
			fromDataSet = event.fromDataSet;
			batchCount = event.batchCount;
			cpSmCol = event.cpSmCol;
			// 循环源数据集, 拿出数据, 写入目标表
			ExecuteBean insertBean = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_INSERT, toTable);
			ExecuteBean updateBean = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_UPDATE, toTable);
			ExecuteBean deleteBean = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_DELETE, toTable);
			int rowCount = fromDataSet.getRowCount();
			int colCount = fromDataSet.getColumnCount();
			for(int i=0;i<rowCount;++i)
			{
				Map<String, Serializable> fromDataMap = new HashMap<String, Serializable>();
				for(int j=0;j<colCount;++j) fromDataMap.put(fromDataSet.getColumnName(j), fromDataSet.getValue(i, j));
				// 判断是否需要当前这条记录
				event = new TransportEvent(this);
				event.session = session;
				event.fromTable = fromTable;
				event.toTable = toTable;
				event.dataBase = dataBase;
				event.fromDataBase = fromDataBase;
				event.toDataBase = toDataBase;
				event.globalVarMap = globalVarMap;
				event.threadVarMap = threadVarMap;
				event.fromDataMap = fromDataMap;
				Boolean needRow = fireThreadNeedRow(event);
				Transport.checkEvent(event);
				if(needRow==null) needRow = true;
				if(needRow)
				{
					// 获取处理方式
					event = new TransportEvent(this);
					event.session = session;
					event.tsId = tsId;
					event.fromTable = fromTable;
					event.toTable = toTable;
					event.dataBase = dataBase;
					event.fromDataBase = fromDataBase;
					event.toDataBase = toDataBase;
					event.globalVarMap = globalVarMap;
					event.threadVarMap = threadVarMap;
					event.fromDataMap = fromDataMap;
					Integer executeType = fireThreadExecuteType(event);
					Transport.checkEvent(event);
					if(executeType==null) executeType = ExecuteBean.EXECUTE_TYPE_INSERT;
					ExecuteBean usedBean = null;
					if(executeType==ExecuteBean.EXECUTE_TYPE_INSERT) usedBean = insertBean;
					else if(executeType==ExecuteBean.EXECUTE_TYPE_UPDATE) usedBean = updateBean;
					else if(executeType==ExecuteBean.EXECUTE_TYPE_DELETE) usedBean = deleteBean;
					if(usedBean!=null)
					{
						// 生成目标数据Map
						Map<String, Serializable> toDataMap = new HashMap<String, Serializable>();
						if(cpSmCol)
						{
							// 拷贝同名列到目标Map
							for(String fromCol : fromDataMap.keySet())
							{
								if(toColSet.contains(fromCol)) toDataMap.put(fromCol, fromDataMap.get(fromCol));
							}
						}
						// 根据列名映射关系写入值
						for(String fromCol : fromToColMap.keySet())
						{
							String toCol = fromToColMap.get(fromCol);
							if(toColSet.contains(toCol)) toDataMap.put(toCol, fromDataMap.get(fromCol));
						}
						// 触发传输前事件
						event = new TransportEvent(this);
						event.session = session;
						event.tsId = tsId;
						event.fromTable = fromTable;
						event.toTable = toTable;
						event.dataBase = dataBase;
						event.fromDataBase = fromDataBase;
						event.toDataBase = toDataBase;
						event.globalVarMap = globalVarMap;
						event.threadVarMap = threadVarMap;
						event.fromDataMap = fromDataMap;
						event.toDataMap = toDataMap;
						fireThreadBeforeTransport(event);
						Transport.checkEvent(event);
						toDataMap = event.toDataMap;
						usedBean.addData(toDataMap);
						// 触发传输后事件
						event = new TransportEvent(this);
						event.session = session;
						event.tsId = tsId;
						event.fromTable = fromTable;
						event.toTable = toTable;
						event.dataBase = dataBase;
						event.fromDataBase = fromDataBase;
						event.toDataBase = toDataBase;
						event.globalVarMap = globalVarMap;
						event.threadVarMap = threadVarMap;
						event.fromDataMap = fromDataMap;
						event.toDataMap = toDataMap;
						fireThreadAfterTransport(event);
						Transport.checkEvent(event);
					}
				}
				// 检查是否到达提交数量
				if((i+1)%batchCount==0)
				{
					if(insertBean.getDataList()!=null)
					{
						toDataBase.sqlExecute(insertBean);
						insertBean = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_INSERT, toTable);
					}
					if(updateBean.getDataList()!=null)
					{
						toDataBase.sqlExecute(updateBean);
						updateBean = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_UPDATE, toTable);
					}
					if(deleteBean.getDataList()!=null)
					{
						toDataBase.sqlExecute(deleteBean);
						deleteBean = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_DELETE, toTable);
					}
					if(!toDataBase.getAutoCommit()) toDataBase.commit();
				}
			}
			if(insertBean.getDataList()!=null) toDataBase.sqlExecute(insertBean);
			if(updateBean.getDataList()!=null) toDataBase.sqlExecute(updateBean);
			if(deleteBean.getDataList()!=null) toDataBase.sqlExecute(deleteBean);
			if(!toDataBase.getAutoCommit()) toDataBase.commit();
			// 触发结束后事件
			event = new TransportEvent(this);
			event.session = session;
			event.tsId = tsId;
			event.fromDataSet = fromDataSet;
			event.batchCount = batchCount;
			event.cpSmCol = cpSmCol;
			event.dataBase = dataBase;
			event.fromDataBase = fromDataBase;
			event.toDataBase = toDataBase;
			event.fromToColMap = fromToColMap;
			event.globalVarMap = globalVarMap;
			event.threadVarMap = threadVarMap;
			fireThreadEnd(event);
			Transport.checkEvent(event);
			success = true;
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
		}
		finally
		{
			DBUtil.close(dataBase);
			DBUtil.close(fromDataBase);
			DBUtil.close(toDataBase);
		}
		if(success) LogUtil.printlnInfo("Thread : " + getId() + " success");
		else LogUtil.printlnError("Thread : " + getId() + " fail with fromSql : " + fromSql);
	}
	
	private void fireThreadBegin(TransportEvent event)
	{
		try
		{
			for(TransportListener listener : listenerSet)
			{
				listener.threadBegin(event);
				if(event.interrupt) break;
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
		}
	}
	
	private Boolean fireThreadNeedRow(TransportEvent event)
	{
		Boolean needRow = true;
		try
		{
			for(TransportListener listener : listenerSet)
			{
				Boolean tempNeedRow = listener.threadNeedRow(event);
				if(tempNeedRow!=null) needRow = tempNeedRow;
				if(event.interrupt) break;
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
		}
		return needRow;
	}
	
	private Integer fireThreadExecuteType(TransportEvent event)
	{
		Integer executeType = null;
		try
		{
			for(TransportListener listener : listenerSet)
			{
				Integer tempExecuteType = listener.threadExecuteType(event);
				if(tempExecuteType!=null) executeType = tempExecuteType;
				if(event.interrupt) break;
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
		}
		return executeType;
	}
	
	private void fireThreadBeforeTransport(TransportEvent event)
	{
		try
		{
			for(TransportListener listener : listenerSet)
			{
				listener.threadBeforeTransport(event);
				if(event.interrupt) break;
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
		}
	}
	
	private void fireThreadAfterTransport(TransportEvent event)
	{
		try
		{
			for(TransportListener listener : listenerSet)
			{
				listener.threadAfterTransport(event);
				if(event.interrupt) break;
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
		}
	}
	
	private void fireThreadEnd(TransportEvent event)
	{
		try
		{
			for(TransportListener listener : listenerSet)
			{
				listener.threadEnd(event);
				if(event.interrupt) break;
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
		}
	}
}
