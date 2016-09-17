package xlsys.base.buffer;

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
	 * @param envId 环境Id
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
	 * @param envId 环境Id
	 * @param bufferName 缓冲名称
	 */
	public final void reloadBuffer(int envId, String bufferName)
	{
		reloadBuffer(envId, bufferName, null);
	}
	
	/**
	 * 重载缓冲
	 * @param envId 环境Id
	 * @param bufferName 缓冲名称
	 * @param paramMap 要传入的参数
	 */
	public final void reloadBuffer(int envId, String bufferName, Map<String, Object> paramMap)
	{
		reloadBuffer(envId, bufferName, paramMap, false);
	}
	
	/**
	 * 重载缓冲
	 * @param envId 环境Id
	 * @param bufferName 缓冲名称
	 * @param paramMap 要传入的参数
	 * @param forceLoad 是否强制重载数据
	 */
	public final void reloadBuffer(int envId, String bufferName, Map<String, Object> paramMap, boolean forceLoad)
	{
		if(bufferName==null) return;
		XlsysBuffer buffer = bufferMap.get(bufferName);
		if(buffer==null) return;
		if(paramMap!=null) buffer.reloadData(envId, bufferName, paramMap, forceLoad);
		else buffer.reloadAllData(envId, bufferName, forceLoad);
	}
	
	/**
	 * 重载所有的注册缓冲
	 * @param envId 环境Id
	 */
	public final void reloadAllBuffer(int envId)
	{
		reloadAllBuffer(envId, false);
	}
	
	/**
	 * 重载所有的注册缓冲
	 * @param envId 环境Id
	 * @param forceLoad 是否强制重载数据
	 */
	public final void reloadAllBuffer(int envId, boolean forceLoad)
	{
		for(String bufferName : bufferMap.keySet())
		{
			reloadBuffer(envId, bufferName, null, forceLoad);
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
	
	/**
	 * 更新缓存版本, 返回更新后的缓存版本号
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	public final int updateBufferVersion(int envId, String bufferName)
	{
		XlsysBuffer buffer = bufferMap.get(bufferName);
		if(buffer==null) return -1;
		return buffer.updateCurrentVersion(envId, bufferName);
	}
	
	/**
	 * 获取缓存的当前版本号, 如果缓存没有注册, 则返回-1
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	public final int getBufferVersion(int envId, String bufferName)
	{
		XlsysBuffer buffer = bufferMap.get(bufferName);
		if(buffer==null) return -1;
		return buffer.getCurrentVersion(envId, bufferName);
	}
	
	/**
	 * 获取缓存的本地存储版本号, 如果缓存没有注册, 则返回-1
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	public final int getLocalBufferVersion(int envId, String bufferName)
	{
		XlsysBuffer buffer = bufferMap.get(bufferName);
		if(buffer==null) return -1;
		return buffer.getLocalStorageVersion(envId, bufferName);
	}
}
