package xlsys.base.database.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import xlsys.base.XLSYS;
import xlsys.base.buffer.AbstractBuffer;
import xlsys.base.buffer.BufferManager;
import xlsys.base.database.ClientDataBase;
import xlsys.base.database.EnvDataBase;
import xlsys.base.database.IDataBase;
import xlsys.base.database.IEnvDataBase;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.dataset.DataSetCell;
import xlsys.base.dataset.DataSetColumn;
import xlsys.base.dataset.IDataSet;
import xlsys.base.dataset.IStorableDataSet;
import xlsys.base.model.IModel;
import xlsys.base.model.ITableModel;
import xlsys.base.model.ModelUtil;
import xlsys.base.session.Session;
import xlsys.base.session.SessionManager;
import xlsys.base.util.ObjectUtil;
import xlsys.base.util.SystemUtil;

/**
 * 数据库翻译类
 * @author Lewis
 *
 */
public class TranslateUtil extends AbstractBuffer
{
	public final static String DEFAULT_TABLENAME = "_default_table";
	
	private static Map<String, TranslateUtil> tuMap;
	
	private boolean forServer;
	private String language;
	private Integer tableCount;
	
	private TranslateUtil(String language, boolean forServer)
	{
		this.forServer = forServer;
		this.language = language;
		BufferManager.getInstance().registerBuffer(XLSYS.BUFFER_TRANSLATION_PREFIX+language, this);
	}
	
	public synchronized static TranslateUtil getInstance(boolean forServer)
	{
		return getInstance(SystemUtil.getUserLanguage(), forServer);
	}
	
	/**
	 * 获取一个翻译类的实例
	 * @return
	 */
	public synchronized static TranslateUtil getInstance(String language, boolean forServer)
	{
		if(tuMap==null) tuMap = new HashMap<String, TranslateUtil>();
		TranslateUtil tu = tuMap.get(language);
		if(tu==null)
		{
			tu = new TranslateUtil(language, forServer);
			tuMap.put(language, tu);
		}
		return tu;
	}
	
	/**
	 * 翻译一个字符串，该方法和getTranslateStr(dataBase, srcStr, DEFAULT_TABLENAME)等价
	 * @param dataBase
	 * @param srcStr
	 * @return
	 */
	public String getTranslateStr(IEnvDataBase dataBase, String srcStr)
	{
		return getTranslateStr(dataBase.getEnvId(), dataBase, srcStr);
	}
	
	public String getTranslateStr(int envId, IDataBase dataBase, String srcStr)
	{
		return getTranslateStr(envId, dataBase, srcStr, DEFAULT_TABLENAME);
	}
	
	public String getTranslateStr(IEnvDataBase dataBase, String srcStr, String tableNames)
	{
		return getTranslateStr(dataBase.getEnvId(), dataBase, srcStr, tableNames);
	}
	
	/**
	 * 翻译一个字符串
	 * @param dataBase 数据库连接
	 * @param srcStr 源字符串
	 * @param tableName 要翻译的字符串所在的表名
	 * @return
	 */
	public String getTranslateStr(int envId, IDataBase dataBase, String srcStr, String tableNames)
	{
		if(tableNames==null) tableNames = DEFAULT_TABLENAME;
		else if(!DEFAULT_TABLENAME.equals(tableNames)&&!tableNames.contains(DEFAULT_TABLENAME)) tableNames += XLSYS.KEY_CODE_SEPARATOR + DEFAULT_TABLENAME;
		String retStr = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(IDataBase.BUFFER_KEY_TABLE_NAME, tableNames);
		String bufferName = XLSYS.BUFFER_TRANSLATION_PREFIX+language;
		Map<String, String> tableMap = (Map<String, String>) getBufferData(envId, bufferName, paramMap);
		retStr = tableMap.get(srcStr);
		if(retStr==null) retStr = srcStr;
		return retStr;
	}
	
	public void translateDataSet(IEnvDataBase dataBase, IDataSet dataSet)
	{
		translateDataSet(dataBase.getEnvId(), dataBase, dataSet);
	}
	
	/**
	 * 翻译一个DataSet. 该方法等价于translateDataSet(dataBase, dataSet, DEFAULT_TABLENAME)
	 * @param dataBase
	 * @param dataSet
	 */
	public void translateDataSet(int envId, IDataBase dataBase, IDataSet dataSet)
	{
		String tableNames = DEFAULT_TABLENAME;
		if(dataSet instanceof IStorableDataSet)
		{
			String tableName = ((IStorableDataSet) dataSet).getTableName();
			if(tableName!=null) tableNames += tableName + XLSYS.KEY_CODE_SEPARATOR + tableNames;
		}
		translateDataSet(envId, dataBase, dataSet, tableNames);
	}
	
	public void translateDataSet(IEnvDataBase dataBase, IDataSet dataSet, String tableNames)
	{
		translateDataSet(dataBase.getEnvId(), dataBase, dataSet, tableNames);
	}
	
	/**
	 * 翻译一个DataSet. 该方法等价于translateDataSet(dataBase, dataSet, tableName, "name")
	 * @param dataBase
	 * @param dataSet
	 * @param tableName
	 */
	public void translateDataSet(int envId, IDataBase dataBase, IDataSet dataSet, String tableNames)
	{
		translateDataSet(envId, dataBase, dataSet, tableNames, "name");
	}
	
	public void translateDataSet(IEnvDataBase dataBase, IDataSet dataSet, String tableNames, String transCols)
	{
		translateDataSet(dataBase.getEnvId(), dataBase, dataSet, tableNames, transCols);
	}
	
	/**
	 * 翻译一个DataSet. 该方法等价于translateDataSet(dataBase, dataSet, tableName, transCols, XLSYS.KEY_CODE_SEPARATOR)
	 * @param dataBase
	 * @param dataSet
	 * @param tableName
	 * @param transCols
	 */
	public void translateDataSet(int envId, IDataBase dataBase, IDataSet dataSet, String tableNames, String transCols)
	{
		translateDataSet(envId, dataBase, dataSet, tableNames, transCols, XLSYS.KEY_CODE_SEPARATOR);
	}
	
	/**
	 * 翻译一个DataSet
	 * @param dataBase 数据库实例
	 * @param dataSet 要翻译的DataSet
	 * @param tableName 源内容所在的表名
	 * @param transCols 要翻译的DataSet的列，可为多个
	 * @param colSpliter transCols中包含多个列时，用来分隔不同列之间的字符串
	 */
	public void translateDataSet(int envId, IDataBase dataBase, IDataSet dataSet, String tableNames, String transCols, String colSpliter)
	{
		if(dataSet==null||dataSet.getRowCount()==0) return;
		if(tableNames==null) tableNames = DEFAULT_TABLENAME;
		else if(!DEFAULT_TABLENAME.equals(tableNames)&&!tableNames.contains(DEFAULT_TABLENAME)) tableNames += XLSYS.KEY_CODE_SEPARATOR + DEFAULT_TABLENAME;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(IDataBase.BUFFER_KEY_TABLE_NAME, tableNames);
		String bufferName = XLSYS.BUFFER_TRANSLATION_PREFIX+language;
		Map<String, String> tableMap = (Map<String, String>) getBufferData(envId, bufferName, paramMap);
		if(!tableMap.isEmpty())
		{
			String[] transColArr = transCols.split(colSpliter);
			for(String colName : transColArr)
			{
				DataSetColumn dsc = dataSet.getColumn(colName);
				if(dsc!=null&&dsc.getJavaClass().equals(String.class.getName()))
				{
					int colIndex = dataSet.getColumnIndex(colName);
					int rowCount = dataSet.getRowCount();
					for(int i=0;i<rowCount;++i)
					{
						DataSetCell dsCell = dataSet.getRow(i).getCell(colIndex);
						String oldValue = (String) dsCell.getContent();
						String newValue = null;
						if(oldValue!=null)
						{
							newValue = tableMap.get(oldValue);
							if(newValue!=null)
							{
								dsCell.setContent(newValue);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 翻译一个Model, 返回翻译后的拷贝model. 注意：本方法不会改变原始model的值
	 * @param dataBase
	 * @param model
	 * @return 翻译后的model
	 */
	public <T extends IModel> T translateModel(IEnvDataBase dataBase, T model)
	{
		return translateModel(dataBase.getEnvId(), dataBase, model);
	}
	
	/**
	 * 翻译一个Model列表, 返回翻译后的拷贝model列表. 注意：本方法不会改变原始model的值
	 * @param dataBase
	 * @param modelList
	 * @return 翻译后的model列表
	 */
	public <T extends IModel> List<T> translateModelList(IEnvDataBase dataBase, List<T> modelList)
	{
		return translateModelList(dataBase.getEnvId(), dataBase, modelList);
	}
	
	public <T extends IModel> T translateModel(int envId, IDataBase dataBase, T model)
	{
		List<T> srcModelList = new ArrayList<T>();
		srcModelList.add(model);
		List<T> translatedModelList = translateModelList(envId, dataBase, srcModelList);
		if(translatedModelList.isEmpty()) return null;
		return translatedModelList.get(0);
	}

	public <T extends IModel> List<T> translateModelList(int envId, IDataBase dataBase, List<T> modelList)
	{
		if(modelList==null||modelList.isEmpty()) return new ArrayList<T>();
		String tableNames = DEFAULT_TABLENAME;
		T firstModel = modelList.get(0);
		if(firstModel instanceof ITableModel)
		{
			String tableName = ((ITableModel) firstModel).getRefTableName();
			if(tableName!=null) tableNames += tableName + XLSYS.KEY_CODE_SEPARATOR + DEFAULT_TABLENAME;
		}
		return translateModelList(envId, dataBase, modelList, tableNames);
	}
	
	public <T extends IModel> T translateModel(IEnvDataBase dataBase, T model, String tableNames)
	{
		return translateModel(dataBase.getEnvId(), dataBase, model, tableNames);
	}
	
	public <T extends IModel> List<T> translateModelList(IEnvDataBase dataBase, List<T> modelList, String tableNames)
	{
		return translateModelList(dataBase.getEnvId(), dataBase, modelList, tableNames);
	}
	
	/**
	 * 翻译一个Model. 该方法等价于translateModel(dataBase, model, tableName, "name")
	 * @param dataBase
	 * @param model
	 * @param tableName
	 * @return
	 */
	public <T extends IModel> T translateModel(int envId, IDataBase dataBase, T model, String tableNames)
	{
		return translateModel(envId, dataBase, model, tableNames, "name");
	}
	
	/**
	 * 翻译一个Model列表. 该方法等价于translateModelList(dataBase, modelList, tableName, "name")
	 * @param dataBase
	 * @param modelList
	 * @param tableName
	 * @return
	 */
	public <T extends IModel> List<T> translateModelList(int envId, IDataBase dataBase, List<T> modelList, String tableNames)
	{
		return translateModelList(envId, dataBase, modelList, tableNames, "name");
	}
	
	public <T extends IModel> T translateModel(IEnvDataBase dataBase, T model, String tableNames, String transFields)
	{
		return translateModel(dataBase.getEnvId(), dataBase, model, tableNames, transFields);
	}
	
	public <T extends IModel> List<T> translateModelList(IEnvDataBase dataBase, List<T> modelList, String tableNames, String transFields)
	{
		return translateModelList(dataBase.getEnvId(), dataBase, modelList, tableNames, transFields);
	}
	
	/**
	 * 翻译一个Model. 该方法等价于translateModel(dataBase, model, tableName, transFields, XLSYS.KEY_CODE_SEPARATOR)
	 * @param dataBase
	 * @param model
	 * @param tableName
	 * @param transFields
	 * @return 
	 */
	public <T extends IModel> T translateModel(int envId, IDataBase dataBase, T model, String tableNames, String transFields)
	{
		return translateModel(envId, dataBase, model, tableNames, transFields, XLSYS.KEY_CODE_SEPARATOR);
	}
	
	/**
	 * 翻译一个Model列表. 该方法等价于translateModelList(dataBase, modelList, tableName, transFields, XLSYS.KEY_CODE_SEPARATOR)
	 * @param dataBase
	 * @param modelList
	 * @param tableName
	 * @param transFields
	 * @return 
	 */
	public <T extends IModel> List<T> translateModelList(int envId, IDataBase dataBase, List<T> modelList, String tableNames, String transFields)
	{
		return translateModelList(envId, dataBase, modelList, tableNames, transFields, XLSYS.KEY_CODE_SEPARATOR);
	}
	
	/**
	 * 翻译一个Model, 返回翻译后的拷贝model. 注意：本方法不会改变原始model的值
	 * @param dataBase 数据库实例
	 * @param model 要翻译的Model
	 * @param tableNames 源内容所在的表名,可为多个
	 * @param transFields 要翻译的Model属性，可为多个
	 * @param fieldSpliter transFields中包含多个属性时，用来分隔不同列之间的字符串
	 * @return 
	 * @return 翻译后的model
	 */
	public <T extends IModel> T translateModel(int envId, IDataBase dataBase, T model, String tableNames, String transFields, String fieldSpliter)
	{
		List<T> srcModelList = new ArrayList<T>();
		srcModelList.add(model);
		List<T> translatedModelList = translateModelList(envId, dataBase, srcModelList, tableNames, transFields, fieldSpliter);
		if(translatedModelList.isEmpty()) return null;
		return translatedModelList.get(0);
	}
	
	/**
	 * 翻译一个列表中的Model, 返回翻译后的拷贝Model列表. 注意：本方法不会改变原始model的值
	 * @param dataBase 数据库实例
	 * @param modelList 要翻译的Model列表
	 * @param tableNames 源内容所在的表名,可为多个
	 * @param transFields 要翻译的Model属性，可为多个
	 * @param fieldSpliter transFields中包含多个属性时，用来分隔不同列之间的字符串
	 * @return 
	 * @return 翻译后的model列表
	 */
	public <T extends IModel> List<T> translateModelList(int envId, IDataBase dataBase, List<T> modelList, String tableNames, String transFields, String fieldSpliter)
	{
		List<T> cloneModelList = new ArrayList<T>();
		if(modelList==null||modelList.isEmpty()) return cloneModelList;
		if(tableNames==null) tableNames = DEFAULT_TABLENAME;
		else if(!DEFAULT_TABLENAME.equals(tableNames)&&!tableNames.contains(DEFAULT_TABLENAME)) tableNames += XLSYS.KEY_CODE_SEPARATOR + DEFAULT_TABLENAME;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(IDataBase.BUFFER_KEY_TABLE_NAME, tableNames);
		String bufferName = XLSYS.BUFFER_TRANSLATION_PREFIX+language;
		Map<String, String> tableMap = (Map<String, String>) getBufferData(envId, bufferName, paramMap);
		for(T model : modelList)
		{
			T cloneModel = ModelUtil.cloneModel(model);
			cloneModelList.add(cloneModel);
		}
		if(!tableMap.isEmpty())
		{
			String[] transFieldArr = transFields.split(fieldSpliter);
			for(String fieldName : transFieldArr)
			{
				Field field = null;
				try
				{
					field = cloneModelList.get(0).getClass().getDeclaredField(fieldName);
					if(String.class.isAssignableFrom(field.getType()))
					{
						field.setAccessible(true);
						for(T cloneModel : cloneModelList)
						{
							String oldValue = ObjectUtil.objectToString(field.get(cloneModel));
							if(oldValue!=null)
							{
								String newValue = tableMap.get(oldValue);
								if(newValue!=null) field.set(cloneModel, newValue);
							}
						}
					}
				}
				catch(Exception e) {}
			}
		}
		return cloneModelList;
	}

	@Override
	public Serializable getStorageObject(int envId, String bufferName)
	{
		Serializable[] arr = new Serializable[2];
		arr[0] = (Serializable) getBufferMap(envId, bufferName);
		arr[1] = tableCount;
		return arr;
	}
	
	private IDataBase createDataBase(int envId)
	{
		IDataBase dataBase = null;
		try
		{
			if(forServer) dataBase = EnvDataBase.getInstance(envId);
			else
			{
				Session session = SessionManager.getInstance().getCurrentSession();
				if(session==null||ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID))!=envId) return null;
				dataBase = ClientDataBase.getInstance();
			}
		}
		catch(Exception e)
		{
			DBUtil.close(dataBase);
			dataBase = null;
		}
		return dataBase;
	}

	@Override
	public boolean isBufferComplete(int envId, String bufferName)
	{
		if(tableCount==null)
		{
			IDataBase dataBase = null;
			try
			{
				dataBase = createDataBase(envId);
				String selectSql = "select count(1) from (select distinct tablename from xlsys_translator where language=?)";
				ParamBean pb = new ParamBean(selectSql);
				pb.addParamGroup();
				pb.setParam(1, language);
				tableCount = ObjectUtil.objectToInt(dataBase.sqlSelectAsOneValue(pb));
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
		return getBufferMap(envId, bufferName).size()==tableCount.intValue();
	}

	@Override
	public void reloadDataDirectly(int envId, String bufferName, Map<String, Object> paramMap, boolean forceLoad)
	{
		String tableNames = null;
		if(paramMap!=null) tableNames = (String) paramMap.get(IDataBase.BUFFER_KEY_TABLE_NAME);
		Map<String, Map<String, String>> dictionaryMap = (Map<String, Map<String, String>>) getBufferMap(envId, bufferName);
		if(tableNames!=null)
		{
			String[] tableArr = tableNames.split(XLSYS.KEY_CODE_SEPARATOR);
			for(String tableName : tableArr) dictionaryMap.remove(tableName);
		}
		else
		{
			dictionaryMap.clear();
			tableCount = null;
		}
		if(forceLoad) getBufferData(envId, bufferName, paramMap);
	}

	@Override
	public boolean loadDataFromStorageObject(int envId, String bufferName, Serializable storageObj)
	{
		Serializable[] arr = (Serializable[]) storageObj;
		Map<String, Map<String, String>> dictionaryMap = (Map<String, Map<String, String>>) getBufferMap(envId, bufferName);
		synchronized(dictionaryMap)
		{
			dictionaryMap.clear();
			dictionaryMap.putAll((Map<? extends String, ? extends Map<String, String>>) arr[0]);
			tableCount = (Integer) arr[1];
		}
		return true;
	}
	
	private void initByParamBean(int envId, IDataBase dataBase, ParamBean pb) throws Exception
	{
		Map<String, Map<String, String>> dictionaryMap = (Map<String, Map<String, String>>) getBufferMap(envId, XLSYS.BUFFER_TRANSLATION_PREFIX+language);
		synchronized(dictionaryMap)
		{
			IDataSet dataSet = dataBase.sqlSelect(pb);
			for(int i=0;i<dataSet.getRowCount();++i)
			{
				String tablename = (String) dataSet.getValue(i, "tablename");
				String defaultname = (String) dataSet.getValue(i, "defaultname");
				String transname = (String) dataSet.getValue(i, "transname");
				Map<String, String> tablenameMap = dictionaryMap.get(tablename);
				if(tablenameMap==null)
				{
					tablenameMap = new HashMap<String, String>();
					dictionaryMap.put(tablename, tablenameMap);
				}
				if(transname!=null) tablenameMap.put(defaultname, transname);
			}
		}
	}

	@Override
	public Serializable doGetBufferData(int envId, String bufferName, Map<String, Object> paramMap)
	{
		Serializable ret = null;
		String tableNames = null;
		if(paramMap!=null) tableNames = (String) paramMap.get(IDataBase.BUFFER_KEY_TABLE_NAME);
		IDataBase dataBase = null;
		try
		{
			dataBase = createDataBase(envId);
			// <tablename, <defaultname, transname>>
			Map<String, Map<String, String>> dictionaryMap = (Map<String, Map<String, String>>) getBufferMap(envId, bufferName);
			synchronized(dictionaryMap)
			{
				// 翻译表情况特殊, 如果加载, 就为全加载, 不再分别加载
				if(tableCount==null||dictionaryMap.size()!=tableCount.intValue())
				{
					dictionaryMap.clear();
					ParamBean pb = new ParamBean("select * from xlsys_translator where language=?");
					pb.addParamGroup();
					pb.setParam(1, language);
					initByParamBean(envId, dataBase, pb);
					tableCount = dictionaryMap.size();
				}
				if(tableNames!=null)
				{
					String[] tbnmArr = tableNames.split(XLSYS.KEY_CODE_SEPARATOR);
					/*if(tableCount==null||dictionaryMap.size()<tableCount.intValue())
					{
						Set<String> needInitTbnm = new HashSet<String>();
						for(String tbnm : tbnmArr)
						{
							Map<String, String> tablenameMap = dictionaryMap.get(tbnm);
							if(tablenameMap==null) needInitTbnm.add(tbnm);
						}
						if(!needInitTbnm.isEmpty())
						{
							ParamBean pb = new ParamBean("select * from xlsys_translator where language=? and tablename=?");
							for(String tbnm : needInitTbnm)
							{
								pb.addParamGroup();
								pb.setParam(1, language);
								pb.setParam(2, tbnm);
							}
							initByParamBean(envId, dataBase, pb);
						}
					}*/
					Map<String, String> tableMap = new HashMap<String, String>();
					for(int i=tbnmArr.length-1;i>=0;--i)
					{
						String tableName = tbnmArr[i];
						Map<String, String> tempMap = dictionaryMap.get(tableName);
						if(tempMap!=null) tableMap.putAll(tempMap);
					}
					ret = (Serializable) tableMap;
				}
				else ret = (Serializable) dictionaryMap;
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
		return ret;
	}
}
