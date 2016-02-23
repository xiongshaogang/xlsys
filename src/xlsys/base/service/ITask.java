package xlsys.base.service;

import java.util.Map;

public interface ITask
{
	/**
	 * 获取任务类型
	 * @return
	 */
	public int getTastType();
	
	/**
	 * 获取任务相关参数
	 * @return
	 */
	public Map<String, Object> getTaskParams();
}
