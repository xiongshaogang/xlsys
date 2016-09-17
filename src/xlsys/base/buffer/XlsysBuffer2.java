package xlsys.base.buffer;

import java.io.Serializable;
import java.util.Map;

/**
 * 标准缓冲对象接口
 * @author Lewis
 *
 */
public interface XlsysBuffer2
{
	public static final String BUFFER_KEY_ENVID = "_BUFFER_KEY_ENVID";
	
	/**
	 * 重新加载所有缓冲
	 */
	public void loadAllData();
	
	/**
	 * 重新加载缓冲
	 * @param paramMap 参数表
	 */
	public void loadData(Map<String, Serializable> paramMap);
}
