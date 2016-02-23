package xlsys.base.database;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.env.Env;

public class EnvDataBase extends IEnvDataBase
{
	protected EnvDataBase(int envId) throws Exception
	{
		super(envId);
	}
	
	public synchronized static final EnvDataBase getInstance(int envId) throws Exception
	{
		return new EnvDataBase(envId);
	}
	
	@Override
	protected Env getEnvModel(int envId) throws Exception
	{
		return (Env) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_ENV).getInstance(envId);
	}

	@Override
	protected IDataBase getNewDataBase(int dbId) throws Exception
	{
		IDataBase dataBase = ((ConnectionPool)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbId)).getNewDataBase();
		return dataBase;
	}
}
