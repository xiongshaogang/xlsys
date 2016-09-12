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
import xlsys.base.util.StringUtil;

/**
 * 标准缓冲对象接口
 * @author Lewis
 *
 */
public interface XlsysBuffer2
{
	static final String LOCAL_STORAGE_DIR = "LOCAL_STORAGE";
	
	public static final String BUFFER_KEY_ENVID = "_BUFFER_KEY_ENVID";

	/**
	 * 获取指定缓冲名的当前缓冲版本号
	 * @param bufferName
	 * @return
	 */
	public int getCurrentVersion(String bufferName);
	
	/**
	 * 获取指定缓冲名称的可序列化缓冲数据
	 * @param bufferName
	 * @return
	 */
	public Serializable getStorageObject(String bufferName);
	
	/**
	 * 返回指定缓冲名称的缓冲是否已经完整
	 * @param bufferName
	 * @return
	 */
	public boolean isBufferComplete(String bufferName);
	
	/**
	 * 根据传入的参数重新加载指定缓冲名的缓冲数据. 该方法不会读取本地存储数据.
	 * @param bufferName
	 * @param paramMap
	 */
	public void reloadDataDirectly(String bufferName, Map<String, Serializable> paramMap);
	
	/**
	 * 从存储对象中加载缓冲
	 * @param bufferName
	 * @param storageObj
	 * @param paramMap
	 * @return
	 */
	public boolean loadDataFromStorageObject(String bufferName, Serializable storageObj);
	
	/**
	 * 保存数据到本地存储
	 * @param bufferName
	 * @param storageObject
	 * @return
	 */
	default boolean saveDataToLocalStorage(String bufferName, Serializable storageObject)
	{
		boolean success = false;
		int version = getCurrentVersion(bufferName);
		DataOutputStream dos = null;
		try
		{
			String storageDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance();
			storageDir += File.separator + LOCAL_STORAGE_DIR;
			storageDir = FileUtil.fixFilePath(storageDir);
			String fileName = StringUtil.getMD5String(bufferName);
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
		return success;
	}
	
	/**
	 * 重新加载指定缓冲名的所有缓冲数据
	 * @param bufferName
	 */
	default void reloadAllData(String bufferName)
	{
		if(!reloadDataFromLocalStorage(bufferName))
		{
			reloadDataDirectly(bufferName, null);
			if(isBufferComplete(bufferName))
			{
				Serializable storageObject = getStorageObject(bufferName);
				saveDataToLocalStorage(bufferName, storageObject);
			}
		}
	}
	
	/**
	 * 根据传入的参数重新加载指定缓冲名的缓冲数据
	 * @param bufferName 缓冲名称
	 * @param paramMap 参数表
	 */
	default void reloadData(String bufferName, Map<String, Serializable> paramMap)
	{
		if(paramMap==null) reloadAllData(bufferName);
		else
		{
			reloadDataDirectly(bufferName, paramMap);
			if(isBufferComplete(bufferName))
			{
				Serializable storageObject = getStorageObject(bufferName);
				saveDataToLocalStorage(bufferName, storageObject);
			}
		}
	}
	
	/**
	 * 从本地存储中根据传入的参数加载指定缓冲名的缓冲数据
	 * @param bufferName
	 * @param paramMap
	 * @return
	 */
	default boolean reloadDataFromLocalStorage(String bufferName)
	{
		int curVersion = getCurrentVersion(bufferName);
		if(curVersion<0) return false;
		int localVersion = getLocalStorageVersion(bufferName);
		if(localVersion<0||curVersion>localVersion) return false;
		// 从本地存储中加载缓冲
		boolean success = false;
		try
		{
			String storageDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance();
			storageDir += File.separator + LOCAL_STORAGE_DIR;
			storageDir = FileUtil.fixFilePath(storageDir);
			String fileName = StringUtil.getMD5String(bufferName);
			String filePath = storageDir + File.separator + fileName;
			byte[] fileBytes = FileUtil.getByteFromFile(filePath);
			// 前四位是版本号
			byte[] objBytes = Arrays.copyOfRange(fileBytes, 4, fileBytes.length);
			Serializable storageObj = (Serializable) IOUtil.readObject(objBytes);
			success = loadDataFromStorageObject(bufferName, storageObj);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	
	/**
	 * 获取指定缓冲名称对应的本地缓冲版本号
	 * @param bufferName
	 * @return
	 */
	default int getLocalStorageVersion(String bufferName)
	{
		int version = -1;
		DataInputStream dis = null;
		try
		{
			String storageDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance();
			storageDir += File.separator + LOCAL_STORAGE_DIR;
			storageDir = FileUtil.fixFilePath(storageDir);
			String fileName = StringUtil.getMD5String(bufferName);
			String filePath = storageDir + File.separator + fileName;
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
		return version;
	}
}
