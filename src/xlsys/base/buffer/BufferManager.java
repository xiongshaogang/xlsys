package xlsys.base.buffer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存管理类，要使用此类管理里的缓存类必须实现XlsysBuffer接口
 * @author Lewis
 *
 */
public class BufferManager
{
	private static BufferManager manager;
	
	private Map<String, XlsysBuffer> bufferMap;
	
	private BufferManager() 
	{
		bufferMap = new HashMap<String, XlsysBuffer>();
	}
	
	/**
	 * 获取缓冲管理实例
	 * @return
	 */
	public synchronized static final BufferManager getInstance()
	{
		if(manager==null) manager = new BufferManager();
		return manager;
	}
	
	/**
	 * 注册缓冲类，只有注册了的缓冲类才能使用该类进行缓冲管理
	 * @param bufferName 缓冲名称
	 * @param buffer 缓冲类
	 */
	public synchronized final void registerBuffer(String bufferName, XlsysBuffer buffer)
	{
		if(bufferName!=null&&buffer!=null) bufferMap.put(bufferName, buffer);
	}
	
	/**
	 * 取消注册缓冲类
	 * @param bufferName
	 */
	public synchronized final void cancelRegisterBuffer(String bufferName)
	{
		if(bufferName!=null) bufferMap.remove(bufferName);
	}
	
	/**
	 * 查看指定的缓冲名称是否有注册
	 * @param bufferName
	 * @return
	 */
	public final boolean isRegistered(String bufferName)
	{
		return bufferMap.containsKey(bufferName);
	}
	
	/**
	 * 重载缓冲
	 * @param bufferName 缓冲名称
	 */
	public final void reloadBuffer(String bufferName)
	{
		reloadBuffer(bufferName, null);
	}
	
	/**
	 * 重载缓冲
	 * @param bufferName 缓冲名称
	 * @param paramMap 要传入的参数
	 */
	public final void reloadBuffer(String bufferName, Map<String, Serializable> paramMap)
	{
		if(bufferMap.containsKey(bufferName))
		{
			XlsysBuffer buffer = bufferMap.get(bufferName);
			if(paramMap!=null) buffer.loadData(paramMap);
			else buffer.loadAllData();
		}
	}
	
	/**
	 * 重载所有的注册缓冲
	 */
	public final void reloadAllBuffer()
	{
		for(String bufferName : bufferMap.keySet())
		{
			reloadBuffer(bufferName);
		}
	}
	
	/**
	 * 获取所有的已注册的缓存名称
	 * @return
	 */
	public final String[] getAllRegisteredBufferNames()
	{
		return bufferMap.keySet().toArray(new String[0]);
	}
}
