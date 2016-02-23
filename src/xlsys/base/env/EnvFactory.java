package xlsys.base.env;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.buffer.ModelBuffer;
import xlsys.base.database.DBPoolFactory;
import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.log.LogUtil;

public class EnvFactory extends XlsysFactory<Integer, Env>
{
	private static EnvFactory envFactory;
	
	public synchronized static EnvFactory getFactoryInstance()
	{
		if(envFactory==null) envFactory = new EnvFactory();
		return envFactory;
	}

	@Override
	protected void beforeDoLoad()
	{
		if(instanceMap!=null) instanceMap.clear();
	}

	@Override
	protected void doLoadConfig()
	{
		// 从内部数据库中读取环境信息
		IDataBase innerDataBase = null;
		try
		{
			DBPoolFactory dbPoolFactory = (DBPoolFactory) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE);
			innerDataBase = dbPoolFactory.getInnerConnectionPool().getNewDataBase();
			String selectSql = "select * from xlsys_env";
			List<Env> envList = ModelBuffer.getModelsFromDatas(innerDataBase, Env.class, new ParamBean(selectSql));
			selectSql = "select * from xlsys_envdetail";
			List<EnvDetail> envDtList = ModelBuffer.getModelsFromDatas(innerDataBase, EnvDetail.class, new ParamBean(selectSql));
			for(Env env : envList)
			{
				if(defaultKey==null) defaultKey = env.getEnvId();
				instanceMap.put(env.getEnvId(), env);
			}
			for(EnvDetail envDt : envDtList)
			{
				Env env = instanceMap.get(envDt.getEnvId());
				if(env!=null) env.addDetail(envDt);
			}
		}
		catch (Exception e)
		{
			LogUtil.printlnError(e);
		}
		finally
		{
			DBUtil.close(innerDataBase);
		}
	}

	/**
	 * 获取所有的envId
	 * @return
	 */
	public String[] getAllEnvId()
	{
		loadConfig();
		List<String> list = new ArrayList<String>();
		for(int envId : instanceMap.keySet())
		{
			Env env = instanceMap.get(envId);
			list.add(envId + XLSYS.CODE_NAME_RELATION + env.getName());
		}
		Collections.sort(list);
		String[] strs = new String[list.size()];
		return list.toArray(strs);
	}
}
