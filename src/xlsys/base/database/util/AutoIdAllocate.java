package xlsys.base.database.util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.buffer.BufferManager;
import xlsys.base.buffer.ModelBuffer;
import xlsys.base.database.ConnectionPool;
import xlsys.base.database.EnvDataBase;
import xlsys.base.database.IDataBase;
import xlsys.base.database.IEnvDataBase;
import xlsys.base.dataset.DataSetColumn;
import xlsys.base.exception.AllocateException;
import xlsys.base.log.LogUtil;

/**
 * 自动分配编码类
 * @author Lewis
 *
 */
public class AutoIdAllocate extends ModelBuffer
{
	private static AutoIdAllocate autoIdAllocate;
	
	private Set<String> supportedClass;
	/*
	 * Map<dbid,Map<sql,maxid>>
	 */
	// private Map<Integer, Map<String, Serializable>> idMap;
	
	/**
	 * 简单计数器
	 */
	private Map<String, Integer> sequenceMap;
	
	private AutoIdAllocate() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		// idMap = new HashMap<Integer, Map<String, Serializable>>();
		sequenceMap = new HashMap<String, Integer>();
		supportedClass = new HashSet<String>();
		supportedClass.add("java.lang.String");
		supportedClass.add("java.math.BigDecimal");
		BufferManager.getInstance().registerBuffer(XLSYS.BUFFER_AUTOIDALLOCATION, this);
	}
	
	public void setCurrentValueByKey(String key, Integer begin)
	{
		synchronized(sequenceMap)
		{
			sequenceMap.put(key, begin);
		}
	}
	
	public Integer getNextValueByKey(String key)
	{
		Integer curValue = null;
		synchronized(sequenceMap)
		{
			curValue = sequenceMap.get(key);
			if(curValue==null) curValue = 0;
			sequenceMap.put(key, ++curValue);
		}
		return curValue;
	}
	
	/**
	 * 等价于allocateId(dbid, tableName, columnName, null)
	 * @param dbid
	 * @param tableName
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	public Serializable allocateId(int dbid, String tableName, String columnName) throws Exception
	{
		return allocateId(dbid, tableName, columnName, XLSYS.CODELIKE_COMMON);
	}
	
	/**
	 * 等价于allocateId(dbid, tableName, columnName, codeLike, true)
	 * @param dbid
	 * @param tableName
	 * @param columnName
	 * @param codeLike
	 * @return
	 * @throws Exception
	 */
	public Serializable allocateId(int dbid, String tableName, String columnName, String codeLike) throws Exception
	{
		return allocateId(dbid, tableName, columnName, codeLike, true);
	}
	
	/**
	 * 等价于allocateId(dbid, tableName, columnName, codeLike, true, 1)
	 * @param dbid
	 * @param tableName
	 * @param columnName
	 * @param codeLike
	 * @param useCache
	 * @return
	 * @throws Exception
	 */
	public Serializable allocateId(int dbid, String tableName, String columnName, String codeLike, boolean useCache) throws Exception
	{
		return allocateId(dbid, tableName, columnName, codeLike, true, 1);
	}
	
	/**
	 * 自动分配编码
	 * @param dbid 要分配的编码所对应的数据库ID
	 * @param tableName 要分配编码的表名
	 * @param columnName 要分配编码的列名
	 * @param codeLike 要分配的编码所匹配的字符串形式，支持使用"_"来代替自动分配的部分
	 * @param useCache 是否使用缓存，如果使用缓存则在第二次分配相同codeLike的编码是不会再访问数据库查询当前的最大值
	 * @param step 每次分配所递增的量
	 * @return
	 * @throws Exception
	 */
	public Serializable allocateId(int envId, String tableName, String columnName, String codeLike, boolean useCache, int step) throws Exception
	{
		Serializable newId = null;
		IEnvDataBase database = null;
		try
		{
			database = EnvDataBase.getInstance(envId);
			DataSetColumn dsc = database.getTableInfo(tableName).getDataSetColumn(columnName);
			if(dsc==null) throw new AllocateException("Column is not exists : " + columnName);
			String javaClass = dsc.getJavaClass();
			if(!supportedClass.contains(javaClass)) throw new AllocateException("Unsupported type for allocation : " + javaClass);
			if("java.lang.String".equals(javaClass))
			{
				newId = allocateIdForString(database, tableName, columnName, codeLike, useCache, step);
			}
			else if("java.math.BigDecimal".equals(javaClass))
			{
				newId = allocateIdForBigDecimal(database, tableName, columnName, codeLike, useCache, step);
			}
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
			throw e;
		}
		finally
		{
			DBUtil.close(database);
		}
		return newId;
	}
	
	/**
	 * 为String类型的字段分配编码
	 * @param dataBase
	 * @param tableName
	 * @param columnName
	 * @param codeLike
	 * @param useCache
	 * @param step
	 * @return
	 * @throws Exception
	 */
	private Serializable allocateIdForString(IEnvDataBase dataBase, String tableName, String columnName, String codeLike, boolean useCache, int step) throws Exception
	{
		Serializable newId = null;
		String select_sql = "select max("+columnName+") from "+tableName;
		if(codeLike!=null)
		{
			select_sql += " where "+columnName+" like '"+codeLike+"'";
		}
		String maxId = null;
		Map<String, Serializable> idMap = (Map<String, Serializable>) getBufferMap(dataBase.getEnvId(), XLSYS.BUFFER_AUTOIDALLOCATION);
		synchronized(idMap)
		{
			if(useCache) maxId = (String) idMap.get(select_sql);
			if(maxId==null) maxId = (String) dataBase.sqlSelectAsOneValue(select_sql);
			if(maxId==null)
			{
				if(codeLike!=null) maxId = codeLike.replace('_', '0');
				else maxId = "0";
				newId = maxId;
			}
			if(newId==null)
			{
				if(codeLike==null) codeLike = maxId.replaceAll(".", "_");
				List<Integer> idxList = new ArrayList<Integer>();
				String valueStr = "";
				for(int i=0;i<codeLike.length();i++)
				{
					if(codeLike.charAt(i)=='_')
					{
						char c = maxId.charAt(i);
						if(c>='0'&&c<='9') valueStr += c;
						else valueStr += '0';
						idxList.add(i);
					}
				}
				int valueLen = valueStr.length();
				BigDecimal value = new BigDecimal(valueStr).add(new BigDecimal(step));
				valueStr = ""+value;
				if(valueStr.length()>valueLen) throw new AllocateException("Id already Max");
				while(valueStr.length()<valueLen) valueStr = 0 + valueStr;
				for(int i=0;i<idxList.size();i++)
				{
					int charAt = idxList.get(i);
					maxId = maxId.substring(0, charAt) + valueStr.charAt(i) + maxId.substring(charAt+1);
				}
				newId = maxId;
			}
			idMap.put(select_sql, maxId);
		}
		return newId;
	}

	/**
	 * 为数字类型的字段分配编码
	 * @param dataBase
	 * @param tableName
	 * @param columnName
	 * @param codeLike
	 * @param useCache
	 * @param step
	 * @return
	 * @throws Exception
	 */
	private Serializable allocateIdForBigDecimal(IEnvDataBase dataBase, String tableName, String columnName, String codeLike, boolean useCache, int step) throws Exception
	{
		Serializable newId = null;
		if(codeLike!=null&&!codeLike.matches("[0-9_]+"))
		{
			throw new AllocateException("The codeLike must consisted of 0-9 or '_' when allocate id of number type : " + codeLike);
		}
		String select_sql = "select max("+columnName+") from "+tableName;
		Long codeLikeMin = null;
		Long codeLikeMax = null;
		if(codeLike!=null)
		{
			codeLikeMin = Long.parseLong(codeLike.replace('_', '0'));
			codeLikeMax = Long.parseLong(codeLike.replace('_', '9'));
			select_sql += " where "+columnName+" between "+codeLikeMin+" and "+codeLikeMax;
		}
		BigDecimal maxId = null;
		Map<String, Serializable> idMap = (Map<String, Serializable>) getBufferMap(dataBase.getEnvId(), XLSYS.BUFFER_AUTOIDALLOCATION);
		synchronized(idMap)
		{
			if(useCache) maxId = (BigDecimal) idMap.get(select_sql);
			if(maxId==null) maxId = (BigDecimal) dataBase.sqlSelectAsOneValue(select_sql);
			if(maxId==null)
			{
				if(codeLikeMin!=null) maxId = new BigDecimal(""+codeLikeMin);
				else maxId = new BigDecimal("0");
				newId = maxId;
			}
			if(newId==null)
			{
				maxId = maxId.add(new BigDecimal(step));
				if(codeLike!=null&&!maxId.toString().startsWith(codeLike.replace("_", "")))
				{
					throw new AllocateException("Id already Max");
				}
				newId = maxId;
			}
			idMap.put(select_sql, maxId);
		}
		return newId;
	}
	
	/**
	 * 获取分配编码类的实例对象
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws DocumentException
	 */
	public synchronized static AutoIdAllocate getInstance() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		if(autoIdAllocate==null)
		{
			autoIdAllocate = new AutoIdAllocate();
		}
		return autoIdAllocate;
	}
}
