package xlsys.base.io.transport;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.database.ConnectionPool;
import xlsys.base.database.IDataBase;
import xlsys.base.database.TableInfo;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.dataset.DataSet;
import xlsys.base.dataset.DataSetColumn;
import xlsys.base.dataset.IDataSet;
import xlsys.base.env.Env;
import xlsys.base.log.LogUtil;
import xlsys.base.script.XlsysClassLoader;
import xlsys.base.script.XlsysCompiler;
import xlsys.base.session.Session;
import xlsys.base.util.ObjectUtil;

/**
 * 该类强制启用事务处理
 * @author Lewis
 *
 */
public class Transport
{
	private int totalThreadCount;
	private Session session;
	private int dbId;
	private String tsId;
	private int fromDsId;
	private int toDsId;
	private int threadCount;
	private int dataPerThread;
	private Map<String, Serializable> globalVarMap;

	public Transport(Session session, String tsRunId) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		this.session = session;
		int envId = ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
		Env env = (Env) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_ENV).getInstance(envId);
		int dbId = env.getDbIdByTableName("xlsys_transportrun");
		init(dbId, tsRunId);
	}
	
	public Transport(int dbId, String tsRunId)
	{
		init(dbId, tsRunId);
	}
	
	private void init(int dbId, String tsRunId)
	{
		this.dbId = dbId;
		IDataBase dataBase = null;
		try
		{
			globalVarMap = new HashMap<String, Serializable>();
			dataBase = ((ConnectionPool)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbId)).getNewDataBase();
			String selectSql = "select * from xlsys_transportrun where tsrunid=?";
			ParamBean pb = new ParamBean(selectSql);
			pb.addParamGroup();
			pb.setParam(1, tsRunId);
			IDataSet dataSet = dataBase.sqlSelect(pb);
			tsId = (String) dataSet.getValue(0, "tsid");
			fromDsId = ObjectUtil.objectToInt(dataSet.getValue(0, "fromdsid"));
			toDsId = ObjectUtil.objectToInt(dataSet.getValue(0, "todsid"));
			BigDecimal bTotalThreadCount = (BigDecimal) dataSet.getValue(0, "totalthreadcount");
			totalThreadCount = bTotalThreadCount==null?1:bTotalThreadCount.intValue();
			BigDecimal bThreadCount = (BigDecimal) dataSet.getValue(0, "threadcount");
			threadCount = bThreadCount==null?1:bThreadCount.intValue();
			BigDecimal bDataPerThread = (BigDecimal) dataSet.getValue(0, "dataPerThread");
			dataPerThread = bDataPerThread==null?-1:bDataPerThread.intValue();
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
		}
		finally
		{
			DBUtil.close(dataBase);
		}
	}
	
	public Exception transport()
	{
		Exception ret = null;
		IDataBase dataBase = null;
		IDataBase fromDataBase = null;
		IDataBase toDataBase = null;
		try
		{
			long beginTime = System.currentTimeMillis();
			dataBase = ((ConnectionPool)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbId)).getNewDataBase();
			fromDataBase = ((ConnectionPool)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(fromDsId)).getNewDataBase();
			toDataBase = ((ConnectionPool)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(toDsId)).getNewDataBase();
			// 查询需要执行的传输明细
			String selectSql = "select * from xlsys_transportdetail where tsid=? and active=1 order by idx";
			ParamBean pb = new ParamBean(selectSql);
			pb.addParamGroup();
			pb.setParam(1, tsId);
			IDataSet dataSet = dataBase.sqlSelect(pb);
			int rowCount = dataSet.getRowCount();
			for(int i=0;i<rowCount;++i)
			{
				transportDetail(dataSet, i, dataBase, fromDataBase, toDataBase);
			}
			long endTime = System.currentTimeMillis();
			LogUtil.printlnInfo("Total Time : " + (endTime-beginTime)/1000 +" seconds");
		}
		catch(Exception e)
		{
			ret = e;
		}
		finally
		{
			DBUtil.close(dataBase);
			DBUtil.close(fromDataBase);
			DBUtil.close(toDataBase);
		}
		return ret;
	}
		
	private void transportDetail(IDataSet rsDtDs, int rowAt, IDataBase dataBase, IDataBase fromDataBase, IDataBase toDataBase) throws Exception
	{
		String fromTable = (String) rsDtDs.getValue(rowAt, "fromtable");
		String toTable = (String) rsDtDs.getValue(rowAt, "totable");
		String fromSql = (String) rsDtDs.getValue(rowAt, "fromsql");
		String javaListener = (String) rsDtDs.getValue(rowAt, "javalistener");
		byte[] jsListener = (byte[]) rsDtDs.getValue(rowAt, "jslistener");
		BigDecimal bBatchCount = (BigDecimal) rsDtDs.getValue(rowAt, "batchcount");
		int batchCount = bBatchCount==null?2000:bBatchCount.intValue();
		BigDecimal bCpSmCol = (BigDecimal) rsDtDs.getValue(rowAt, "cpsmcol");
		if(bCpSmCol==null) bCpSmCol = BigDecimal.ONE;
		boolean cpSmCol = bCpSmCol.intValue()==1;
		// 查询字段映射明细
		Map<String, String> fromToColMap = new HashMap<String, String>();
		int tsDtIdx = ObjectUtil.objectToInt(rsDtDs.getValue(rowAt, "idx"));
		String selectSql = "select fromcolumn, tocolumn from xlsys_transportdtcolmap where tsid=? and tsdtidx=? order by idx";
		ParamBean pb = new ParamBean(selectSql);
		pb.addParamGroup();
		pb.setParam(1, tsId);
		pb.setParam(2, tsDtIdx);
		IDataSet tempDataSet = dataBase.sqlSelect(pb);
		int tempRowCount = tempDataSet.getRowCount();
		for(int j=0;j<tempRowCount;++j) fromToColMap.put((String)tempDataSet.getValue(j, 0), (String)tempDataSet.getValue(j, 1));
		// 初始化Listener
		Set<TransportListener> listenerSet = new LinkedHashSet<TransportListener>();
		initJavaListener(dataBase, javaListener, listenerSet);
		if(jsListener!=null&&jsListener.length>0)
		{
			TransportJsListener tsJsListener = new TransportJsListener(ObjectUtil.objectToString(jsListener));
			listenerSet.add(tsJsListener);
		}
		// 调用初始化事件
		TransportEvent event = new TransportEvent(this);
		event.session = session;
		event.tsId = tsId;
		event.fromTable = fromTable;
		event.toTable = toTable;
		event.fromSql = fromSql;
		event.batchCount = batchCount;
		event.cpSmCol = cpSmCol;
		event.threadCount = threadCount;
		event.dataPerThread = dataPerThread;
		event.dataBase = dataBase;
		event.fromDataBase = fromDataBase;
		event.toDataBase = toDataBase;
		event.fromToColMap = fromToColMap;
		event.globalVarMap = globalVarMap;
		fireBegin(listenerSet, event);
		checkEvent(event);
		fromTable = event.fromTable;
		toTable = event.toTable;
		fromSql = event.fromSql;
		batchCount = event.batchCount;
		cpSmCol = event.cpSmCol;
		TableInfo toTableInfo = toDataBase.getTableInfo(toTable);
		Set<String> toColSet = new HashSet<String>();
		for(DataSetColumn dsc : toTableInfo.getDataColumnList()) toColSet.add(dsc.getColumnName());
		List<TransportThread> threadList = new ArrayList<TransportThread>();
		if(event.fromDataSet==null)
		{
			// 获取源sql语句, 拆分后给予各个子线程
			if(fromSql==null) fromSql = "select * from " + fromTable;
			event = new TransportEvent(this);
			event.session = session;
			event.tsId = tsId;
			event.fromTable = fromTable;
			event.toTable = toTable;
			event.fromSql = fromSql;
			event.batchCount = batchCount;
			event.cpSmCol = cpSmCol;
			event.threadCount = threadCount;
			event.dataPerThread = dataPerThread;
			event.dataBase = dataBase;
			event.fromDataBase = fromDataBase;
			event.toDataBase = toDataBase;
			event.fromToColMap = fromToColMap;
			event.globalVarMap = globalVarMap;
			List<String> splitSqlList = fireSplitFromSqlForThread(listenerSet, event);
			if(splitSqlList==null)
			{
				// 拆分源Sql
				splitSqlList = new ArrayList<String>();
				ParamBean tempPb = new ParamBean(fromSql);
				int resultCount = fromDataBase.getResultCount(tempPb);
				int curDataPerThread = dataPerThread>0?dataPerThread:resultCount/threadCount;
				int subSqlCount = resultCount%curDataPerThread==0?resultCount/curDataPerThread:resultCount/curDataPerThread+1;
				Method m = fromDataBase.getClass().getDeclaredMethod("getSplitedParamBean", ParamBean.class, int.class, int.class);
				m.setAccessible(true);
				for(int i=0;i<subSqlCount;i++)
				{
					int beginNum = i*curDataPerThread;
					int endNum = i==(subSqlCount-1)?(resultCount-1):((i+1)*curDataPerThread-1);
					ParamBean splitedPb = (ParamBean) m.invoke(fromDataBase, tempPb, beginNum, endNum);
					splitSqlList.add(splitedPb.getSelectSql());
				}
			}
			for(String splitedfromSql : splitSqlList)
			{
				// 创建子线程对象
				TransportThread tsThread = new TransportThread(session, tsId, listenerSet, fromTable, toTable, splitedfromSql, toColSet, batchCount, cpSmCol, dbId, fromDsId, toDsId, fromToColMap, globalVarMap);
				threadList.add(tsThread);
			}
		}
		else
		{
			// 直接拆分源数据集到各个子线程
			int resultCount = event.fromDataSet.getRowCount();
			int curDataPerThread = dataPerThread>0?dataPerThread:resultCount/threadCount;
			int subThreadCount = resultCount%curDataPerThread==0?resultCount/curDataPerThread:resultCount/curDataPerThread+1;
			for(int i=0;i<subThreadCount;i++)
			{
				int beginRowAt = i*curDataPerThread;
				int endRowAt = i==(subThreadCount-1)?(resultCount-1):((i+1)*curDataPerThread-1);
				IDataSet subDataSet = new DataSet(event.fromDataSet.getColumns());
				for(int j=beginRowAt;j<=endRowAt;++j) subDataSet.insertRowAfterLast(event.fromDataSet.getRow(j));
				// 创建子线程对象
				TransportThread tsThread = new TransportThread(session, tsId, listenerSet, fromTable, toTable, subDataSet, toColSet, batchCount, cpSmCol, dbId, fromDsId, toDsId, fromToColMap, globalVarMap);
				threadList.add(tsThread);
			}
		}
		// 创建线程池来运行
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(totalThreadCount, totalThreadCount, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.AbortPolicy());
		for(TransportThread thread : threadList) threadPoolExecutor.execute(thread);
		threadPoolExecutor.shutdown();
		threadPoolExecutor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MINUTES);
		// 触发结束事件
		event = new TransportEvent(this);
		event.session = session;
		event.tsId = tsId;
		event.fromTable = fromTable;
		event.toTable = toTable;
		event.fromSql = fromSql;
		event.batchCount = batchCount;
		event.cpSmCol = cpSmCol;
		event.threadCount = threadCount;
		event.dataPerThread = dataPerThread;
		event.dataBase = dataBase;
		event.fromDataBase = fromDataBase;
		event.toDataBase = toDataBase;
		event.fromToColMap = fromToColMap;
		event.globalVarMap = globalVarMap;
		fireEnd(listenerSet, event);
		checkEvent(event);
	}
	
	protected static void checkEvent(TransportEvent event) throws Exception
	{
		if(!event.doit)
		{
			if(event.errMsg!=null)
			{
				if(event.errMsg instanceof Exception) throw (Exception) event.errMsg;
				else throw new Exception(ObjectUtil.objectToString(event.errMsg));
			}
			else throw new Exception();
		}
	}

	private void fireBegin(Set<TransportListener> listenerSet, TransportEvent event)
	{
		try
		{
			for(TransportListener listener : listenerSet)
			{
				listener.begin(event);
				if(event.interrupt) break;
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
		}
	}
	
	private List<String> fireSplitFromSqlForThread(Set<TransportListener> listenerSet, TransportEvent event)
	{
		List<String> splitSqlList = null;
		try
		{
			for(TransportListener listener : listenerSet)
			{
				List<String> tempSplitSqlList = listener.splitFromSqlForThread(event);
				if(tempSplitSqlList!=null) splitSqlList = tempSplitSqlList;
				if(event.interrupt) break;
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
		}
		return splitSqlList;
	}
	
	private void fireEnd(Set<TransportListener> listenerSet, TransportEvent event)
	{
		try
		{
			for(TransportListener listener : listenerSet)
			{
				listener.end(event);
				if(event.interrupt) break;
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
		}
	}
	
	private void initJavaListener(IDataBase dataBase, String javaListener, Set<TransportListener> listenerSet) throws Exception
	{
		if(javaListener!=null)
		{
			// 添加Java监听
			String[] listeners = javaListener.split(XLSYS.KEY_CODE_SEPARATOR);
			for(String lsnStr : listeners)
			{
				// 获取listener的额外参数
				String paramStr = null;
				int qstIdx = lsnStr.indexOf(XLSYS.PARAM_QUESTION);
				if(qstIdx>-1)
				{
					paramStr = lsnStr.substring(qstIdx+1);
					lsnStr = lsnStr.substring(0, qstIdx);
				}
				XlsysClassLoader xcl = XlsysClassLoader.getInstance();
				if(!xcl.containsClass(lsnStr))
				{
					// 获取java源代码
					String selectSql = "select javasource from xlsys_javaclass where classid=?"; //$NON-NLS-1$
					ParamBean pb = new ParamBean(selectSql);
					pb.addParamGroup();
					pb.setParam(1, lsnStr);
					String javaSource = ObjectUtil.objectToString(dataBase.sqlSelectAsOneValue(pb));
					if(javaSource!=null)
					{
						XlsysCompiler xlsysCompiler = XlsysCompiler.getInstance();
						xlsysCompiler.addSource(lsnStr, javaSource);
						xlsysCompiler.compile();
					}
				}
				Class<?> lsnClass = XlsysClassLoader.getInstance().loadClass(lsnStr);
				Constructor<?> c = lsnClass.getDeclaredConstructor();
				c.setAccessible(true);
				// 构造TransportListener
				TransportListener listener = (TransportListener) c.newInstance();
				// 设置额外参数
				if(paramStr!=null)
				{
					String[] params = paramStr.split(XLSYS.PARAM_AND);
					for(String param : params)
					{
						String[] prop = param.split(XLSYS.PARAM_RELATION, 2);
						Field field = lsnClass.getField(prop[0]);
						Class<?> fieldClass = field.getType();
						Object fieldValue = fieldClass.getConstructor(String.class).newInstance(prop[1]);
						field.set(listener, fieldValue);
					}
				}
				listenerSet.add(listener);	
			}
		}
	}
}
