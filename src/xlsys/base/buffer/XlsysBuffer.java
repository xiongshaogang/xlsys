package xlsys.base.buffer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.io.util.FileUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.log.LogUtil;
import xlsys.base.util.StringUtil;
import xlsys.base.util.SystemUtil;

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
		if(!isBufferComplete(envId, bufferName)) this.reloadDataFromLocalStorage(envId, bufferName);
		Serializable ret = doGetBufferData(envId, bufferName, paramMap);
		if(isBufferComplete(envId, bufferName))
		{
			Serializable storageObject = getStorageObject(envId, bufferName);
			saveDataToLocalStorage(envId, bufferName, storageObject);
		}
		return ret;
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
		return MXlsysBuffer.doUpdateCurrentVersion(envId, bufferName, true);
	}
	
	/**
	 * 获取指定缓冲名的当前缓冲版本号
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	default int getCurrentVersion(int envId, String bufferName)
	{
		return MXlsysBuffer.doGetCurrentVersion(envId, bufferName, true);
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
		if(SystemUtil.isDebug()) return false;
		boolean success = false;
		synchronized(this)
		{
			int version = getCurrentVersion(envId, bufferName);
			int localVersion = getLocalStorageVersion(envId, bufferName);
			if(localVersion>=version) return true;
			DataOutputStream dos = null;
			try
			{
				String storageDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance();
				storageDir += File.separator + LOCAL_STORAGE_DIR;
				storageDir = FileUtil.fixFilePath(storageDir);
				String fileName = envId + "_" + StringUtil.getMD5String(bufferName);
				String filePath = storageDir + File.separator + fileName;
				FileOutputStream fos = IOUtil.getFileOutputStream(filePath, false);
				dos = new DataOutputStream(fos);
				dos.writeInt(version);
				dos.write(IOUtil.getObjectBytes(storageObject));
				dos.flush();
				dos.close();
				dos = null;
				success = true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				success = false;
			}
			finally
			{
				IOUtil.close(dos);
			}
			LogUtil.printlnError("Save Buffer To Local " + (success?"success":"failed") + "! EnvId : " + envId + "  bufferName : " + bufferName);
		}
		return success;
	}
	
	/**
	 * 重新加载指定缓冲名的所有缓冲数据
	 * @param envId
	 * @param bufferName
	 * @throws Exception 
	 */
	default void reloadAllData(int envId, String bufferName)
	{
		reloadAllData(envId, bufferName, false);
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
		synchronized(this)
		{
			if(!reloadDataFromLocalStorage(envId, bufferName))
			{
				reloadDataDirectly(envId, bufferName, null, forceLoad);
				if(isBufferComplete(envId, bufferName))
				{
					Serializable storageObject = getStorageObject(envId, bufferName);
					saveDataToLocalStorage(envId, bufferName, storageObject);
				}
			}
		}
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
		reloadData(envId, bufferName, paramMap, false);
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
		synchronized(this)
		{
			if(paramMap==null) reloadAllData(envId, bufferName);
			else
			{
				reloadDataDirectly(envId, bufferName, paramMap, forceLoad);
				if(isBufferComplete(envId, bufferName))
				{
					Serializable storageObject = getStorageObject(envId, bufferName);
					saveDataToLocalStorage(envId, bufferName, storageObject);
				}
			}
		}
		
	}
	
	/**
	 * 从本地存储中根据传入的参数加载指定缓冲名的缓冲数据. 在系统日志级别为DEBUG时, 总是返回false
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	default boolean reloadDataFromLocalStorage(int envId, String bufferName)
	{
		if(SystemUtil.isDebug()) return false;
		boolean success = false;
		synchronized(this)
		{
			int curVersion = getCurrentVersion(envId, bufferName);
			if(curVersion<0) return false;
			int localVersion = getLocalStorageVersion(envId, bufferName);
			if(localVersion<0||curVersion>localVersion) return false;
			// 从本地存储中加载缓冲
			try
			{
				String storageDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance();
				storageDir += File.separator + LOCAL_STORAGE_DIR;
				storageDir = FileUtil.fixFilePath(storageDir);
				String fileName = envId + "_" + StringUtil.getMD5String(bufferName);
				String filePath = storageDir + File.separator + fileName;
				byte[] fileBytes = FileUtil.getByteFromFile(filePath);
				// 前四位是版本号
				byte[] objBytes = Arrays.copyOfRange(fileBytes, 4, fileBytes.length);
				Serializable storageObj = (Serializable) IOUtil.readObject(objBytes);
				success = loadDataFromStorageObject(envId, bufferName, storageObj);
				LogUtil.printlnError("Load Buffer From Local " + (success?"success":"failed") + "! EnvId : " + envId + "  bufferName : " + bufferName);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				success = false;
			}
		}
		return success;
	}
	
	/**
	 * 获取指定缓冲名称对应的本地缓冲版本号. 在系统日志级别为DEBUG时, 总是返回-1.
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	default int getLocalStorageVersion(int envId, String bufferName)
	{
		if(SystemUtil.isDebug()) return -1;
		int version = -1;
		synchronized(this)
		{
			DataInputStream dis = null;
			try
			{
				String storageDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance();
				storageDir += File.separator + LOCAL_STORAGE_DIR;
				storageDir = FileUtil.fixFilePath(storageDir);
				String fileName = envId + "_" + StringUtil.getMD5String(bufferName);
				String filePath = storageDir + File.separator + fileName;
				File file = new File(filePath);
				if(!file.exists()) return -1;
				FileInputStream fis = IOUtil.getFileInputStream(filePath);
				dis = new DataInputStream(fis);
				version = dis.readInt();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				version = -1;
			}
			finally
			{
				IOUtil.close(dis);
			}
		}
		return version;
	}
}
