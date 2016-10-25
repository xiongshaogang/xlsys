package xlsys.base.buffer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.database.EnvDataBase;
import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.io.util.FileUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.io.util.LockUtil;
import xlsys.base.log.LogUtil;
import xlsys.base.session.Session;
import xlsys.base.util.StringUtil;
import xlsys.base.util.SystemUtil;

public class MXlsysBuffer
{
	// <envId, <bufferName, version>> 用来存放版本号的缓存
	public final static Map<Integer, Map<String, Integer>> versionMap = new HashMap<Integer, Map<String, Integer>>();
	
	public static int _updateCurrentVersion(XlsysBuffer buffer, int envId, String bufferName, boolean useGlobalLock)
	{
		int version = -1;
		synchronized(versionMap)
		{
			Session session = new Session(XLSYS.SESSION_DEFAULT_ID);
			session.setAttribute(XLSYS.SESSION_ENV_ID, envId);
			String lockKey = null;
			IDataBase dataBase = null;
			try
			{
				if(useGlobalLock) lockKey = LockUtil.getGlobalLock(session, bufferName);
				dataBase = EnvDataBase.getInstance(envId);
				version = _updateCurrentVersionToDB(buffer, dataBase, envId, bufferName, false);
				// 存入当前版本号到缓存中
				Map<String, Integer> tempMap = versionMap.get(envId);
				if(tempMap==null)
				{
					tempMap = new HashMap<String, Integer>();
					versionMap.put(envId, tempMap);
				}
				tempMap.put(bufferName, version);
			}
			catch(Exception e)
			{
				throw new RuntimeException(e);
			}
			finally
			{
				DBUtil.close(dataBase);
				if(lockKey!=null) LockUtil.releaseGlobalLock(session, lockKey);
			}
		}
		return version;
	}
	
	public static int _updateCurrentVersionToDB(XlsysBuffer buffer, IDataBase dataBase, int envId, String bufferName, boolean useGlobalLock)
	{
		Session session = new Session(XLSYS.SESSION_DEFAULT_ID);
		session.setAttribute(XLSYS.SESSION_ENV_ID, envId);
		String lockKey = null;
		int version = -1;
		try
		{
			if(useGlobalLock) lockKey = LockUtil.getGlobalLock(session, bufferName);
			BigDecimal dbBufferVersion = _getCurrentVersionFromDB(buffer, dataBase, envId, bufferName, false, false);
			if(dbBufferVersion!=null) version = dbBufferVersion.intValue();
			// 把当前版本号加1
			version += 1;
			// 存入当前版本号到数据库中
			ExecuteBean eb = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_UPDATE, "xlsys_bufferinfo");
			Map<String, Serializable> dataMap = new HashMap<String, Serializable>();
			dataMap.put("buffername", bufferName);
			dataMap.put("version", version);
			eb.addData(dataMap);
			dataBase.sqlExecute(eb);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			if(lockKey!=null) LockUtil.releaseGlobalLock(session, lockKey);
		}
		return version;
	}
	
	/**
	 * 获取指定缓冲名的当前缓冲版本号
	 * @param envId
	 * @param bufferName
	 * @return
	 */
	public static int _getCurrentVersion(XlsysBuffer buffer, int envId, String bufferName, boolean useGlobalLock)
	{
		int version = -1;
		synchronized(versionMap)
		{
			Session session = new Session(XLSYS.SESSION_DEFAULT_ID);
			session.setAttribute(XLSYS.SESSION_ENV_ID, envId);
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
				String lockKey = null;
				IDataBase dataBase = null;
				try
				{
					if(useGlobalLock) lockKey = LockUtil.getGlobalLock(session, bufferName);
					dataBase = EnvDataBase.getInstance(envId);
					dataBase.setAutoCommit(false);
					BigDecimal dbBufferVersion = _getCurrentVersionFromDB(buffer, dataBase, envId, bufferName, false, true);
					if(dbBufferVersion!=null) version = dbBufferVersion.intValue();
					dataBase.commit();
				}
				catch(Exception e)
				{
					DBUtil.rollback(dataBase);
					throw new RuntimeException(e);
				}
				finally
				{
					DBUtil.close(dataBase);
					if(lockKey!=null) LockUtil.releaseGlobalLock(session, lockKey);
				}
				if(version==-1) version = 0;
				tempMap.put(bufferName, version);
			}
		}
		return version;
	}
	
	public static BigDecimal _getCurrentVersionFromDB(XlsysBuffer buffer, IDataBase dataBase, int envId, String bufferName, boolean useGlobalLock, boolean init)
	{
		BigDecimal version = null;
		Session session = new Session(XLSYS.SESSION_DEFAULT_ID);
		session.setAttribute(XLSYS.SESSION_ENV_ID, envId);
		String lockKey = null;
		try
		{
			if(useGlobalLock) lockKey = LockUtil.getGlobalLock(session, bufferName);
			String selectSql = "select version from xlsys_bufferinfo where buffername=?";
			ParamBean pb = new ParamBean(selectSql);
			pb.addParamGroup();
			pb.setParam(1, bufferName);
			version = (BigDecimal) dataBase.sqlSelectAsOneValue(pb);
			if(version==null&&init)
			{
				version = BigDecimal.ZERO;
				// 插入数据到数据库中
				ExecuteBean eb = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_INSERT, "xlsys_bufferinfo");
				Map<String, Serializable> dataMap = new HashMap<String, Serializable>();
				dataMap.put("buffername", bufferName);
				dataMap.put("version", version);
				eb.addData(dataMap);
				dataBase.sqlExecute(eb);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			if(lockKey!=null) LockUtil.releaseGlobalLock(session, lockKey);
		}
		return version;
	}

	public static Serializable _getBufferData(XlsysBuffer buffer, int envId, String bufferName, Map<String, Object> paramMap)
	{
		if(!buffer.isBufferComplete(envId, bufferName)) buffer.reloadDataFromLocalStorage(envId, bufferName);
		Serializable ret = buffer.doGetBufferData(envId, bufferName, paramMap);
		if(buffer.isBufferComplete(envId, bufferName))
		{
			Serializable storageObject = buffer.getStorageObject(envId, bufferName);
			buffer.saveDataToLocalStorage(envId, bufferName, storageObject);
		}
		return ret;
	}

	public static boolean _saveDataToLocalStorage(XlsysBuffer buffer, int envId, String bufferName, Serializable storageObject)
	{
		if(SystemUtil.isDebug()) return false;
		boolean success = false;
		synchronized(buffer)
		{
			int version = buffer.getCurrentVersion(envId, bufferName);
			int localVersion = buffer.getLocalStorageVersion(envId, bufferName);
			if(localVersion>=version) return true;
			DataOutputStream dos = null;
			try
			{
				String storageDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance();
				storageDir += File.separator + XlsysBuffer.LOCAL_STORAGE_DIR;
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

	public static void _reloadAllData(XlsysBuffer buffer, int envId, String bufferName)
	{
		buffer.reloadAllData(envId, bufferName, false);
	}
	
	public static void _reloadAllData(XlsysBuffer buffer, int envId, String bufferName, boolean forceLoad)
	{
		synchronized(buffer)
		{
			if(!buffer.reloadDataFromLocalStorage(envId, bufferName))
			{
				buffer.reloadDataDirectly(envId, bufferName, null, forceLoad);
				if(buffer.isBufferComplete(envId, bufferName))
				{
					Serializable storageObject = buffer.getStorageObject(envId, bufferName);
					buffer.saveDataToLocalStorage(envId, bufferName, storageObject);
				}
			}
		}
	}

	public static void _reloadData(XlsysBuffer buffer, int envId, String bufferName, Map<String, Object> paramMap)
	{
		buffer.reloadData(envId, bufferName, paramMap, false);
	}
	
	public static void _reloadData(XlsysBuffer buffer, int envId, String bufferName, Map<String, Object> paramMap, boolean forceLoad)
	{
		synchronized(buffer)
		{
			if(paramMap==null) buffer.reloadAllData(envId, bufferName);
			else
			{
				buffer.reloadDataDirectly(envId, bufferName, paramMap, forceLoad);
				if(buffer.isBufferComplete(envId, bufferName))
				{
					Serializable storageObject = buffer.getStorageObject(envId, bufferName);
					buffer.saveDataToLocalStorage(envId, bufferName, storageObject);
				}
			}
		}
	}

	public static boolean _reloadDataFromLocalStorage(XlsysBuffer buffer, int envId, String bufferName)
	{
		if(SystemUtil.isDebug()) return false;
		boolean success = false;
		synchronized(buffer)
		{
			int curVersion = buffer.getCurrentVersion(envId, bufferName);
			if(curVersion<0) return false;
			int localVersion = buffer.getLocalStorageVersion(envId, bufferName);
			if(localVersion<0||curVersion>localVersion) return false;
			// 从本地存储中加载缓冲
			try
			{
				String storageDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance();
				storageDir += File.separator + XlsysBuffer.LOCAL_STORAGE_DIR;
				storageDir = FileUtil.fixFilePath(storageDir);
				String fileName = envId + "_" + StringUtil.getMD5String(bufferName);
				String filePath = storageDir + File.separator + fileName;
				byte[] fileBytes = FileUtil.getByteFromFile(filePath);
				// 前四位是版本号
				byte[] objBytes = Arrays.copyOfRange(fileBytes, 4, fileBytes.length);
				Serializable storageObj = (Serializable) IOUtil.readObject(objBytes);
				success = buffer.loadDataFromStorageObject(envId, bufferName, storageObj);
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
	
	public static int _getLocalStorageVersion(XlsysBuffer buffer, int envId, String bufferName)
	{
		if(SystemUtil.isDebug()) return -1;
		int version = -1;
		synchronized(buffer)
		{
			DataInputStream dis = null;
			try
			{
				String storageDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance();
				storageDir += File.separator + XlsysBuffer.LOCAL_STORAGE_DIR;
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
