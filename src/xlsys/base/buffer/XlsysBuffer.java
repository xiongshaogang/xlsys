package xlsys.base.buffer;

import java.io.Serializable;
import java.util.Map;

/**
 * 标准缓冲对象接口
 * @author Lewis
 *
 */
public interface XlsysBuffer
{
	final static String LOCAL_STORAGE_DIR = "LOCAL_STORAGE";

	/**
	 * 获取指定缓冲名称的可序列化缓冲数据
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	Serializable getStorageObject(int envId, String bufferName);
	
	/**
	 * 返回指定缓冲名称的缓冲是否已经完整
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	boolean isBufferComplete(int envId, String bufferName);
	
	/**
	 * 根据传入的参数重新加载指定缓冲名的缓冲数据. 该方法不会读取本地存储数据.
	 * @param envId
	 * @param bufferName
	 * @param paramMap
	 * @param forceLoad 是否强制加载, 如果该参数为true, 则要求接口必须立刻加载数据(非延迟加载), 如果为false, 则接口可通过其他方式等需要时再加载数据(延迟加载)
	 */
	void reloadDataDirectly(int envId, String bufferName, Map<String, Object> paramMap, boolean forceLoad);
	
	/**
	 * 从存储对象中加载缓冲
	 * @param envId
	 * @param bufferName
	 * @param storageObj
	 * @return
	 */
	boolean loadDataFromStorageObject(int envId, String bufferName, Serializable storageObj);
	
	/**
	 * 获取缓冲数据具体实现方法
	 * @param envId
	 * @param bufferName
	 * @param paramMap
	 * @return
	 */
	Serializable doGetBufferData(int envId, String bufferName, Map<String, Object> paramMap);
	
	/**
	 * 获取缓冲数据
	 * @param envId
	 * @param bufferName
	 * @param paramMap
	 * @return
	 */
	default public Serializable getBufferData(int envId, String bufferName, Map<String, Object> paramMap)
	{
		return MXlsysBuffer._getBufferData(this, envId, bufferName, paramMap);
	}
	
	/**
	 * 更新指定缓冲名的当前缓冲版本号, 刷新后版本号为当前版本号+1
	 * @param envId
	 * @param bufferName
	 * @return
	 * @throws Exception 
	 */
	default int updateCurrentVersion(int envId, String bufferName)
	{
		return MXlsysBuffer._updateCurrentVersion(this, envId, bufferName, true);
	}
	
	/**
	 * 获取指定缓冲名的当前缓冲版本号
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	default int getCurrentVersion(int envId, String bufferName)
	{
		return MXlsysBuffer._getCurrentVersion(this, envId, bufferName, true);
	}
	
	/**
	 * 保存数据到本地存储. 在系统日志级别为DEBUG时, 总是返回false
	 * @param envId
	 * @param bufferName
	 * @param storageObject
	 * @return
	 */
	default boolean saveDataToLocalStorage(int envId, String bufferName, Serializable storageObject)
	{
		return MXlsysBuffer._saveDataToLocalStorage(this, envId, bufferName, storageObject);
	}
	
	/**
	 * 重新加载指定缓冲名的所有缓冲数据
	 * @param envId
	 * @param bufferName
	 * @throws Exception 
	 */
	default void reloadAllData(int envId, String bufferName)
	{
		MXlsysBuffer._reloadAllData(this, envId, bufferName);
	}
	
	/**
	 * 重新加载指定缓冲名的所有缓冲数据
	 * @param envId
	 * @param bufferName
	 * @param forceLoad 是否强制重载数据
	 * @throws Exception 
	 */
	default void reloadAllData(int envId, String bufferName, boolean forceLoad)
	{
		MXlsysBuffer._reloadAllData(this, envId, bufferName, forceLoad);
	}
	
	/**
	 * 根据传入的参数重新加载指定缓冲名的缓冲数据
	 * @param envId
	 * @param bufferName 缓冲名称
	 * @param paramMap 参数表
	 * @throws Exception 
	 */
	default void reloadData(int envId, String bufferName, Map<String, Object> paramMap)
	{
		MXlsysBuffer._reloadData(this, envId, bufferName, paramMap);
	}
	
	/**
	 * 根据传入的参数重新加载指定缓冲名的缓冲数据
	 * @param envId
	 * @param bufferName 缓冲名称
	 * @param paramMap 参数表
	 * @param forceLoad 是否强制重载数据
	 * @throws Exception 
	 */
	default void reloadData(int envId, String bufferName, Map<String, Object> paramMap, boolean forceLoad)
	{
		MXlsysBuffer._reloadData(this, envId, bufferName, paramMap, forceLoad);
	}
	
	/**
	 * 从本地存储中根据传入的参数加载指定缓冲名的缓冲数据. 在系统日志级别为DEBUG时, 总是返回false
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	default boolean reloadDataFromLocalStorage(int envId, String bufferName)
	{
		return MXlsysBuffer._reloadDataFromLocalStorage(this, envId, bufferName);
	}
	
	/**
	 * 获取指定缓冲名称对应的本地缓冲版本号. 在系统日志级别为DEBUG时, 总是返回-1.
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	default int getLocalStorageVersion(int envId, String bufferName)
	{
		return MXlsysBuffer._getLocalStorageVersion(this, envId, bufferName);
	}
}
