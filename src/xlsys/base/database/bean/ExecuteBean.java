package xlsys.base.database.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * 执行DDL语句时的传递参数封装类
 * @author Lewis
 *
 */
public class ExecuteBean implements Serializable, ISqlBean
{
	private static final long serialVersionUID = 7633304024150492015L;
	
	/**
	 * 插入类型的执行语句
	 */
	public final static int EXECUTE_TYPE_INSERT = 0;
	/**
	 * 更新类型的执行语句
	 */
	public final static int EXECUTE_TYPE_UPDATE = 1;
	/**
	 * 删除类型的执行语句
	 */
	public final static int EXECUTE_TYPE_DELETE = 2;
	/**
	 * 其他类型的执行语句
	 */
	public final static int EXECUTE_TYPE_OTHER = 9;
	
	private String executeSql;
	protected List<Map<String, Serializable>> dataList;
	/**
	 * 自定义update时使用的属性，key为要update的字段Map, value为update的条件字段Map
	 */
	protected Map<Map<String, Serializable>, Map<String, Serializable>> oldDataMaps;
	/**
	 * 生成sql语句后的参数顺序列表, 如果为带oldDataMap的update类型，则这里只存放update区域的参数列表
	 */
	protected LinkedHashSet<String> paramColSet;
	/**
	 * 带oldDataMap的update类型时此参数有效，存放where区域的参数列表
	 */
	protected LinkedHashSet<String> updateWhereColSet;
	protected int executeType;
	protected String tableName;
	
	/**
	 * 直接使用sql语句来构造一个ExecuteBean
	 * @param executeSql
	 */
	@Deprecated
	public ExecuteBean(String executeSql)
	{
		this.executeSql = executeSql;
		executeType = -1;
		String lowcaseSql = executeSql.toLowerCase().trim();
		if(lowcaseSql.startsWith("update "))
		{
			executeType = EXECUTE_TYPE_UPDATE;
			lowcaseSql = lowcaseSql.substring(7).trim();
			int idx = lowcaseSql.indexOf(' ');
			if(idx!=-1) tableName = lowcaseSql.substring(0, idx);
		}
		else if(lowcaseSql.startsWith("insert "))
		{
			executeType = EXECUTE_TYPE_INSERT;
			int idx = lowcaseSql.indexOf(" into ");
			lowcaseSql = lowcaseSql.substring(idx+6).trim();
			idx = lowcaseSql.indexOf(' ');
			if(idx!=-1) tableName = lowcaseSql.substring(0, idx);
			idx = tableName.indexOf('(');
			if(idx!=-1) tableName = tableName.substring(0, idx);
		}
		else if(lowcaseSql.startsWith("delete "))
		{
			executeType = EXECUTE_TYPE_DELETE;
			int idx = lowcaseSql.indexOf(" from ");
			lowcaseSql = lowcaseSql.substring(idx+6).trim();
			idx = lowcaseSql.indexOf(' ');
			if(idx!=-1) tableName = lowcaseSql.substring(0, idx);
			else tableName = lowcaseSql;
		}
		else
		{
			executeType = EXECUTE_TYPE_OTHER;
		}
	}
	
	/**
	 * 使用执行类型和表名来构造一个ExecuteBean
	 * @param executeType 必须是"EXECUTE_TYPE_"开头的执行类型之一
	 * @param tableName 表名
	 */
	public ExecuteBean(int executeType, String tableName)
	{
		this.executeType = executeType;
		this.tableName = tableName;
	}
	
	/**
	 * 将一个包含一条数据的Map放入当前对象中
	 * @param dataMap
	 */
	public void addData(Map<String, Serializable> dataMap)
	{
		if(dataList==null)
		{
			dataList = new ArrayList<Map<String, Serializable>>();
		}
		if(dataMap!=null)
		{
			dataList.add(dataMap);
		}
	}
	
	/**
	 * 将一个包含一条数据的Map和包含其对应的一条旧数据的Map放入当前对象中，注意旧数据的Map只有在执行类型为EXECUTE_TYPE_UPDATE时才会使用
	 * @param dataMap 要执行的数据
	 * @param oldDataMap 对应的旧数据
	 */
	public void addData(Map<String, Serializable> dataMap, Map<String, Serializable> oldDataMap)
	{
		if(dataList==null)
		{
			dataList = new ArrayList<Map<String, Serializable>>();
		}
		if(oldDataMap!=null&&oldDataMaps==null)
		{
			oldDataMaps = new HashMap<Map<String, Serializable>, Map<String, Serializable>>();
		}
		if(dataMap!=null)
		{
			dataList.add(dataMap);
			if(oldDataMap!=null) oldDataMaps.put(dataMap, oldDataMap);
		}
	}

	/**
	 * 获得执行类型
	 * @return
	 */
	public int getExecuteType()
	{
		return executeType;
	}

	/**
	 * 获取对应表名
	 * @return
	 */
	public String getTableName()
	{
		return tableName;
	}
	
	/**
	 * 设置可执行的sql
	 * @param executeSql
	 */
	public void setExecuteSql(String executeSql)
	{
		this.executeSql = executeSql;
	}

	/**
	 * 获取可执行的sql
	 * @return
	 */
	public String getExecuteSql()
	{
		return executeSql;
	}

	/**
	 * 获取要执行的数据列表
	 * @return
	 */
	public List<Map<String, Serializable>> getDataList()
	{
		return dataList;
	}

	/**
	 * 设置要执行的数据列表
	 * @param dataList
	 */
	public void setDataList(List<Map<String, Serializable>> dataList)
	{
		this.dataList = dataList;
	}

	/**
	 * 获取旧数据Map
	 * @return
	 */
	public Map<Map<String, Serializable>, Map<String, Serializable>> getOldDataMaps()
	{
		return oldDataMaps;
	}

	/**
	 * 设置旧数据Map
	 * @param oldDataMaps
	 */
	public void setOldDataMaps(Map<Map<String, Serializable>, Map<String, Serializable>> oldDataMaps)
	{
		this.oldDataMaps = oldDataMaps;
	}

	/**
	 * 获取参数列集合
	 * @return
	 */
	public LinkedHashSet<String> getParamColSet()
	{
		return paramColSet;
	}
	
	/**
	 * 设置参数列集合
	 * @param paramColSet
	 */
	public void setParamColSet(LinkedHashSet<String> paramColSet)
	{
		this.paramColSet = paramColSet;
	}

	/**
	 * 获取update时where语句后面的列集合
	 * @return
	 */
	public LinkedHashSet<String> getUpdateWhereColSet()
	{
		return updateWhereColSet;
	}

	/**
	 * 设置update时where语句后面的列集合
	 * @param updateWhereColSet
	 */
	public void setUpdateWhereColSet(LinkedHashSet<String> updateWhereColSet)
	{
		this.updateWhereColSet = updateWhereColSet;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataList == null) ? 0 : dataList.hashCode());
		result = prime * result
				+ ((executeSql == null) ? 0 : executeSql.hashCode());
		result = prime * result + executeType;
		result = prime * result
				+ ((oldDataMaps == null) ? 0 : oldDataMaps.hashCode());
		result = prime * result
				+ ((paramColSet == null) ? 0 : paramColSet.hashCode());
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
		result = prime
				* result
				+ ((updateWhereColSet == null) ? 0 : updateWhereColSet
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExecuteBean other = (ExecuteBean) obj;
		if (dataList == null)
		{
			if (other.dataList != null)
				return false;
		} else if (!dataList.equals(other.dataList))
			return false;
		if (executeSql == null)
		{
			if (other.executeSql != null)
				return false;
		} else if (!executeSql.equals(other.executeSql))
			return false;
		if (executeType != other.executeType)
			return false;
		if (oldDataMaps == null)
		{
			if (other.oldDataMaps != null)
				return false;
		} else if (!oldDataMaps.equals(other.oldDataMaps))
			return false;
		if (paramColSet == null)
		{
			if (other.paramColSet != null)
				return false;
		} else if (!paramColSet.equals(other.paramColSet))
			return false;
		if (tableName == null)
		{
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		if (updateWhereColSet == null)
		{
			if (other.updateWhereColSet != null)
				return false;
		} else if (!updateWhereColSet.equals(other.updateWhereColSet))
			return false;
		return true;
	}
}
