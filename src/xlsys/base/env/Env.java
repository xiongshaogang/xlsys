package xlsys.base.env;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import xlsys.base.model.IModel;

public class Env implements IModel
{
	private static final long serialVersionUID = -7304428833924137849L;
	
	private Integer envId;
	private String name;
	private List<EnvDetail> details;
	private transient Map<String, Integer> tableDbMap;
	private transient Set<Integer> dbIdSet;
	
	protected Env()
	{
		init();
	}
	
	public Env(Integer envId)
	{
		this.envId = envId;
		init();
	}
	
	private void init()
	{
		tableDbMap = new HashMap<String, Integer>();
		dbIdSet = new HashSet<Integer>();
	}
	
	public Integer getEnvId()
	{
		return envId;
	}
	public void setEnvId(Integer envId)
	{
		this.envId = envId;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void addDetail(EnvDetail detail)
	{
		if(details==null) details = new ArrayList<EnvDetail>();
		details.add(detail);
		dbIdSet.add(detail.getDbId());
	}

	public List<EnvDetail> getDetails()
	{
		return details;
	}

	public void setDetails(List<EnvDetail> details)
	{
		this.details = details;
		dbIdSet.clear();
		for(EnvDetail detail : details) dbIdSet.add(detail.getDbId());
	}
	
	public Set<Integer> getDbIdSet()
	{
		return dbIdSet;
	}

	/**
	 * 根据表名获取对应的数据库ID
	 * @param tableName
	 * @return 没有找到对应的数据库, 则返回-1
	 */
	public int getDbIdByTableName(String tableName)
	{
		int dbId = -1;
		if(details==null||tableName==null) return dbId;
		if(!tableDbMap.containsKey(tableName))
		{
			for(EnvDetail detail : details)
			{
				if(tableName.matches(detail.getTableRegex()))
				{
					dbId = detail.getDbId();
					tableDbMap.put(tableName, dbId);
					break;
				}
			}
		}
		else dbId = tableDbMap.get(tableName);
		return dbId;
	}
}
