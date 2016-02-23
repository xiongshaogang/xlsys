package xlsys.base.service;

import java.util.HashMap;
import java.util.Map;

public class Task implements ITask
{
	private int taskType;
	private Map<String, Object> paramMap;
	
	public Task(int taskType)
	{
		this.taskType = taskType;
		paramMap = new HashMap<String, Object>();
	}
	
	@Override
	public int getTastType()
	{
		return taskType;
	}
	
	public void addParam(String name, Object value)
	{
		paramMap.put(name, value);
	}

	public Map<String, Object> getParamMap()
	{
		return paramMap;
	}

	@Override
	public Map<String, Object> getTaskParams()
	{
		return paramMap;
	}
}
