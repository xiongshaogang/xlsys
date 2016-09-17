package xlsys.base.buffer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import xlsys.base.XLSYS;
import xlsys.base.database.EnvDataBase;
import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.io.util.LockUtil;
import xlsys.base.session.Session;

public class MXlsysBuffer
{
	// <envId, <bufferName, version>> 用来存放版本号的缓存
	final static Map<Integer, Map<String, Integer>> versionMap = new HashMap<Integer, Map<String, Integer>>();
	
	public static int doUpdateCurrentVersion(int envId, String bufferName, boolean useGlobalLock)
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
				version = doUpdateCurrentVersionToDB(dataBase, envId, bufferName, false);
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
	
	public static int doUpdateCurrentVersionToDB(IDataBase dataBase, int envId, String bufferName, boolean useGlobalLock)
	{
		Session session = new Session(XLSYS.SESSION_DEFAULT_ID);
		session.setAttribute(XLSYS.SESSION_ENV_ID, envId);
		String lockKey = null;
		int version = -1;
		try
		{
			if(useGlobalLock) lockKey = LockUtil.getGlobalLock(session, bufferName);
			BigDecimal dbBufferVersion = doGetCurrentVersionFromDB(dataBase, envId, bufferName, false);
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
	public static int doGetCurrentVersion(int envId, String bufferName, boolean useGlobalLock)
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
					BigDecimal dbBufferVersion = doGetCurrentVersionFromDB(dataBase, envId, bufferName, false);
					if(dbBufferVersion!=null) version = dbBufferVersion.intValue();
					else
					{
						version = 0;
						// 插入数据到数据库中
						ExecuteBean eb = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_INSERT, "xlsys_bufferinfo");
						Map<String, Serializable> dataMap = new HashMap<String, Serializable>();
						dataMap.put("buffername", bufferName);
						dataMap.put("version", version);
						eb.addData(dataMap);
						dataBase.sqlExecute(eb);
					}
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
	
	public static BigDecimal doGetCurrentVersionFromDB(IDataBase dataBase, int envId, String bufferName, boolean useGlobalLock)
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
}
