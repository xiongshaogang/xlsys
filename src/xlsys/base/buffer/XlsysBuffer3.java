package xlsys.base.buffer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.database.EnvDataBase;
import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.io.util.FileUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.util.ObjectUtil;
import xlsys.base.util.StringUtil;

/**
 * 标准缓冲对象接口
 * @author Lewis
 *
 */
public interface XlsysBuffer3
{
	final static String LOCAL_STORAGE_DIR = "LOCAL_STORAGE";
	
	// <envId, <bufferName, version>> 用来存放版本号的缓存
	final static Map<Integer, Map<String, Integer>> versionMap = new HashMap<Integer, Map<String, Integer>>();
	// <envId, <bufferName>> 用来存在数据库中是否有缓存版本记录的缓存
	final static Map<Integer, Set<String>> recordExistsMap = new HashMap<Integer, Set<String>>();

	/**
	 * 获取指定缓冲名称的可序列化缓冲数据
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	public Serializable getStorageObject(int envId, String bufferName);
	
	/**
	 * 返回指定缓冲名称的缓冲是否已经完整
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	public boolean isBufferComplete(int envId, String bufferName);
	
	/**
	 * 根据传入的参数重新加载指定缓冲名的缓冲数据. 该方法不会读取本地存储数据.
	 * @param envId
	 * @param bufferName
	 * @param paramMap
	 */
	public void reloadDataDirectly(int envId, String bufferName, Map<String, Serializable> paramMap);
	
	/**
	 * 从存储对象中加载缓冲
	 * @param envId
	 * @param bufferName
	 * @param storageObj
	 * @param paramMap
	 * @return
	 */
	public boolean loadDataFromStorageObject(int envId, String bufferName, Serializable storageObj);
	
	/**
	 * 刷新指定缓冲名的当前缓冲版本号, 刷新后版本号为当前版本号+1
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	default void refreshCurrentVersion(int envId, String bufferName)
	{
		synchronized(versionMap)
		{
			int version = getCurrentVersion(envId, bufferName);
			// 把当前版本号加1
			version += 1;
			// 存入当前版本号到缓存和数据库中
			Map<String, Integer> tempMap = versionMap.get(envId);
			if(tempMap==null)
			{
				tempMap = new HashMap<String, Integer>();
				versionMap.put(envId, tempMap);
			}
			tempMap.put(bufferName, version);
			IDataBase dataBase = null;
			try
			{
				dataBase = EnvDataBase.getInstance(envId);
				// 查询数据库中是否存在当前记录
				Set<String> bufferSet = recordExistsMap.get(envId);
				if(bufferSet==null)
				{
					bufferSet = new HashSet<String>();
					recordExistsMap.put(envId, bufferSet);
				}
				ExecuteBean eb = null;
				if(!bufferSet.contains(bufferName)) eb = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_INSERT, "xlsys_bufferinfo");
				else eb = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_UPDATE, "xlsys_bufferinfo");
				Map<String, Serializable> dataMap = new HashMap<String, Serializable>();
				dataMap.put("buffername", bufferName);
				dataMap.put("version", version);
				dataBase.sqlExecute(eb);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				DBUtil.close(dataBase);
			}
		}
	}
	
	/**
	 * 获取指定缓冲名的当前缓冲版本号
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	default int getCurrentVersion(int envId, String bufferName)
	{
		int version = -1;
		synchronized(versionMap)
		{
			Map<String, Integer> tempMap = versionMap.get(envId);
			if(tempMap==null)
			{
				tempMap = new HashMap<String, Integer>();
				versionMap.put(envId, tempMap);
			}
			if(tempMap.containsKey(bufferName)) version = tempMap.get(bufferName);
			else
			{
				// 从数据库中读取版本号
				IDataBase dataBase = null;
				try
				{
					dataBase = EnvDataBase.getInstance(envId);
					String selectSql = "select version from xlsys_bufferinfo where buffername=?";
					ParamBean pb = new ParamBean(selectSql);
					pb.addParamGroup();
					pb.setParam(1, bufferName);
					Serializable tempVersion = dataBase.sqlSelectAsOneValue(pb);
					if(tempVersion!=null)
					{
						version = ObjectUtil.objectToInt(tempVersion);
						tempMap.put(bufferName, version);
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					DBUtil.close(dataBase);
				}
			}
		}
		return version;
	}
	
	/**
	 * 保存数据到本地存储
	 * @param envId
	 * @param bufferName
	 * @param storageObject
	 * @return
	 */
	default boolean saveDataToLocalStorage(int envId, String bufferName, Serializable storageObject)
	{
		boolean success = false;
		synchronized(this)
		{
			int version = getCurrentVersion(envId, bufferName);
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
		}
		return success;
	}
	
	/**
	 * 重新加载指定缓冲名的所有缓冲数据
	 * @param envId
	 * @param bufferName
	 */
	default void reloadAllData(int envId, String bufferName)
	{
		synchronized(this)
		{
			if(!reloadDataFromLocalStorage(envId, bufferName))
			{
				reloadDataDirectly(envId, bufferName, null);
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
	 */
	default void reloadData(int envId, String bufferName, Map<String, Serializable> paramMap)
	{
		synchronized(this)
		{
			if(paramMap==null) reloadAllData(envId, bufferName);
			else
			{
				reloadDataDirectly(envId, bufferName, paramMap);
				if(isBufferComplete(envId, bufferName))
				{
					Serializable storageObject = getStorageObject(envId, bufferName);
					saveDataToLocalStorage(envId, bufferName, storageObject);
				}
			}
		}
		
	}
	
	/**
	 * 从本地存储中根据传入的参数加载指定缓冲名的缓冲数据
	 * @param envId
	 * @param bufferName
	 * @param paramMap
	 * @return
	 */
	default boolean reloadDataFromLocalStorage(int envId, String bufferName)
	{
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
	 * 获取指定缓冲名称对应的本地缓冲版本号
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	default int getLocalStorageVersion(int envId, String bufferName)
	{
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
