package xlsys.base.database.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import xlsys.base.XLSYS;
import xlsys.base.buffer.BufferManager;
import xlsys.base.buffer.ModelBuffer;
import xlsys.base.database.IDataBase;
import xlsys.base.database.IEnvDataBase;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.dataset.DataSetCell;
import xlsys.base.dataset.DataSetColumn;
import xlsys.base.dataset.IDataSet;
import xlsys.base.dataset.IStorableDataSet;

/**
 * 数据库翻译类
 * @author Lewis
 *
 */
public class TranslateUtil extends ModelBuffer
{
	public final static String DEFAULT_TABLENAME = "_default_table";
	
	private static TranslateUtil translateUtil;
	
	private TranslateUtil()
	{
		BufferManager.getInstance().registerBuffer(XLSYS.BUFFER_TRANSLATION, this);
	}
	
	/**
	 * 获取一个翻译类的实例
	 * @return
	 */
	public synchronized static TranslateUtil getInstance()
	{
		if(translateUtil==null) translateUtil = new TranslateUtil();
		return translateUtil;
	}
	
	private void init(int envId, IDataBase dataBase, String lan, String tbnms)
	{
		// <language, <tablename, <defaultname, transname>>>
		Map<String, Map<String, Map<String, String>>> dictionaryMap = (Map<String, Map<String, Map<String, String>>>) getBufferMap(envId, XLSYS.BUFFER_TRANSLATION);
		synchronized(dictionaryMap)
		{
			Set<String> needInitTbnm = new HashSet<String>();
			String[] tbnmArr = tbnms.split(XLSYS.CODE_NAME_SEPARATOR);
			Map<String, Map<String, String>> languageMap = dictionaryMap.get(lan);
			if(languageMap!=null&&!languageMap.isEmpty())
			{
				for(String tbnm : tbnmArr)
				{
					Map<String, String> tablenameMap = languageMap.get(tbnm);
					if(tablenameMap==null) needInitTbnm.add(tbnm);
				}
			}
			else
			{
				for(String tbnm : tbnmArr) needInitTbnm.add(tbnm);
			}
			if(!needInitTbnm.isEmpty())
			{
				try
				{
					StringBuffer selectSql = new StringBuffer("select language,tablename,defaultname,transname from xlsys_translator where language=? and tablename=?");
					ParamBean pb = new ParamBean(selectSql.toString());
					for(String tbnm : needInitTbnm)
					{
						pb.addParamGroup();
						if(lan!=null) pb.setParam(1, lan);
						if(tbnm!=null) pb.setParam(2, tbnm);
					}
					initByParamBean(envId, dataBase, pb);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private void initByParamBean(int envId, IDataBase dataBase, ParamBean pb)
	{
		Map<String, Map<String, Map<String, String>>> dictionaryMap = (Map<String, Map<String, Map<String, String>>>) getBufferMap(envId, XLSYS.BUFFER_TRANSLATION);
		synchronized(dictionaryMap)
		{
			try
			{
				IDataSet dataSet = dataBase.sqlSelect(pb);
				for(int i=0;i<dataSet.getRowCount();i++)
				{
					String language = (String) dataSet.getValue(i, "language");
					String tablename = (String) dataSet.getValue(i, "tablename");
					String defaultname = (String) dataSet.getValue(i, "defaultname");
					String transname = (String) dataSet.getValue(i, "transname");
					if(transname!=null)
					{
						Map<String, Map<String, String>> languageMap = dictionaryMap.get(language);
						if(languageMap==null)
						{
							languageMap = new HashMap<String, Map<String, String>>();
							dictionaryMap.put(language, languageMap);
						}
						Map<String, String> tablenameMap = languageMap.get(tablename);
						if(tablenameMap==null)
						{
							tablenameMap = new HashMap<String, String>();
							languageMap.put(tablename, tablenameMap);
						}
						tablenameMap.put(defaultname, transname);
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 翻译一个字符串，该方法和getTranslateStr(dataBase, srcStr, Locale.getDefault().getLanguage(), DEFAULT_TABLENAME)等价
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
		return getTranslateStr(envId, dataBase, srcStr, Locale.getDefault().getLanguage(), DEFAULT_TABLENAME);
	}
	
	/**
	 * 翻译一个字符串，该方法和getTranslateStr(dataBase, srcStr, language, DEFAULT_TABLENAME)等价
	 * @param dataBase
	 * @param srcStr
	 * @param language
	 * @return
	 */
	public String getTranslateStr(IEnvDataBase dataBase, String srcStr, String language)
	{
		return getTranslateStr(dataBase.getEnvId(), dataBase, srcStr, language);
	}
	
	public String getTranslateStr(int envId, IDataBase dataBase, String srcStr, String language)
	{
		return getTranslateStr(envId, dataBase, srcStr, language, DEFAULT_TABLENAME);
	}
	
	public String getTranslateStr(IEnvDataBase dataBase, String srcStr, String language, String tableName)
	{
		return getTranslateStr(dataBase.getEnvId(), dataBase, srcStr, language, tableName);
	}
	
	/**
	 * 翻译一个字符串
	 * @param dataBase 数据库连接
	 * @param srcStr 源字符串
	 * @param language 要翻译的语言
	 * @param tableName 要翻译的字符串所在的表名
	 * @return
	 */
	public String getTranslateStr(int envId, IDataBase dataBase, String srcStr, String language, String tableName)
	{
		String retStr = null;
		init(envId, dataBase, language, tableName);
		Map<String, Map<String, Map<String, String>>> dictionaryMap = (Map<String, Map<String, Map<String, String>>>) getBufferMap(envId, XLSYS.BUFFER_TRANSLATION);
		Map<String, Map<String, String>> tableMap = dictionaryMap.get(language);
		if(tableMap!=null)
		{
			Map<String, String> transMap = tableMap.get(tableName);
			if(transMap!=null)
			{
				retStr = transMap.get(srcStr);
			}
		}
		if(retStr==null) retStr = srcStr;
		return retStr;
	}

	public void translateDataSet(IEnvDataBase dataBase, IDataSet dataSet)
	{
		translateDataSet(dataBase.getEnvId(), dataBase, dataSet);
	}
	
	/**
	 * 翻译一个DataSet. 该方法等价于translateDataSet(dataBase, dataSet, Locale.getDefault().getLanguage())
	 * @param dataBase
	 * @param dataSet
	 */
	public void translateDataSet(int envId, IDataBase dataBase, IDataSet dataSet)
	{
		translateDataSet(envId, dataBase, dataSet, Locale.getDefault().getLanguage());
	}
	
	public void translateDataSet(IEnvDataBase dataBase, IDataSet dataSet, String language)
	{
		translateDataSet(dataBase.getEnvId(), dataBase, dataSet, language);
	}
	
	/**
	 * 翻译一个DataSet. 该方法等价于translateDataSet(dataBase, dataSet, language, DEFAULT_TABLENAME)
	 * @param dataBase
	 * @param dataSet
	 * @param language
	 */
	public void translateDataSet(int envId, IDataBase dataBase, IDataSet dataSet, String language)
	{
		String tableNames = DEFAULT_TABLENAME;
		if(dataSet instanceof IStorableDataSet)
		{
			String tableName = ((IStorableDataSet) dataSet).getTableName();
			if(tableName!=null) tableNames += XLSYS.CODE_NAME_SEPARATOR + tableName;
		}
		translateDataSet(envId, dataBase, dataSet, language, tableNames);
	}
	
	public void translateDataSet(IEnvDataBase dataBase, IDataSet dataSet, String language, String tableNames)
	{
		translateDataSet(dataBase.getEnvId(), dataBase, dataSet, language, tableNames);
	}
	
	/**
	 * 翻译一个DataSet. 该方法等价于translateDataSet(dataBase, dataSet, language, tableName, "name")
	 * @param dataBase
	 * @param dataSet
	 * @param language
	 * @param tableName
	 */
	public void translateDataSet(int envId, IDataBase dataBase, IDataSet dataSet, String language, String tableNames)
	{
		translateDataSet(envId, dataBase, dataSet, language, tableNames, "name");
	}
	
	public void translateDataSet(IEnvDataBase dataBase, IDataSet dataSet, String language, String tableNames, String transCols)
	{
		translateDataSet(dataBase.getEnvId(), dataBase, dataSet, language, tableNames, transCols);
	}
	
	/**
	 * 翻译一个DataSet. 该方法等价于translateDataSet(dataBase, dataSet, language, tableName, transCols, XLSYS.CODE_NAME_SEPARATOR)
	 * @param dataBase
	 * @param dataSet
	 * @param language
	 * @param tableName
	 * @param transCols
	 */
	public void translateDataSet(int envId, IDataBase dataBase, IDataSet dataSet, String language, String tableNames, String transCols)
	{
		translateDataSet(envId, dataBase, dataSet, language, tableNames, transCols, XLSYS.CODE_NAME_SEPARATOR);
	}
	
	/**
	 * 翻译一个DataSet
	 * @param dataBase 数据库实例
	 * @param dataSet 要翻译的DataSet
	 * @param language 要翻译的目标语言
	 * @param tableName 源内容所在的表名
	 * @param transCols 要翻译的DataSet的列，可为多个
	 * @param colSpliter transCols中包含多个列时，用来分隔不同列之间的字符串
	 */
	public void translateDataSet(int envId, IDataBase dataBase, IDataSet dataSet, String language, String tableNames, String transCols, String colSpliter)
	{
		if(tableNames==null) tableNames = DEFAULT_TABLENAME;
		init(envId, dataBase, language, tableNames);
		if(dataSet==null||dataSet.getRowCount()==0) return;
		Map<String, Map<String, Map<String, String>>> dictionaryMap = (Map<String, Map<String, Map<String, String>>>) getBufferMap(envId, XLSYS.BUFFER_TRANSLATION);
		Map<String, Map<String, String>> tablenameMap = dictionaryMap.get(language);
		if(tablenameMap!=null)
		{
			String[] tableArr = tableNames.split(XLSYS.CODE_NAME_SEPARATOR);
			Map<String, String> tableMap = new HashMap<String, String>();
			for(String tableName : tableArr)
			{
				Map<String, String> tempMap = tablenameMap.get(tableName);
				if(tempMap!=null) tableMap.putAll(tempMap);
			}
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
	}
}
