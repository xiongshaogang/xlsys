package xlsys.base.env;

import xlsys.base.model.IModel;

public class EnvDetail implements IModel
{
	private static final long serialVersionUID = -2199344316296625818L;

	private Integer envId;
	private String tableRegex;
	private Integer dbId;
	private String name;
	
	protected EnvDetail() {}

	public EnvDetail(Integer envId, String tableRegex, Integer dbId)
	{
		this.envId = envId;
		this.tableRegex = tableRegex;
		this.dbId = dbId;
	}
	
	public Integer getEnvId()
	{
		return envId;
	}

	protected void setEnvId(Integer envId)
	{
		this.envId = envId;
	}

	public String getTableRegex()
	{
		return tableRegex;
	}

	protected void setTableRegex(String tableRegex)
	{
		this.tableRegex = tableRegex;
	}

	public Integer getDbId()
	{
		return dbId;
	}

	protected void setDbId(Integer dbId)
	{
		this.dbId = dbId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
