package xlsys.base.test;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.database.ConnectionPool;
import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.DBUtil;

public class TempTest
{

	public static void main(String[] args) throws Exception
	{
		IDataBase dataBase = null;
		try
		{
			dataBase = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(2001)).getNewDataBase();
			ParamBean pb = new ParamBean("insert into xlsys_user (userid,name,password,isenable) values(?,?,?,?)");
			for(int i=0;i<=1000;i++)
			{
				pb.addParamGroup();
				pb.setParam(1, "lxd"+i);
				pb.setParam(2, "lxd"+i);
				pb.setParam(3, "123456");
				pb.setParam(4, 0);
			}
			/*
			 * ParamBean pb = new ParamBean("delete xlsys_user where userid=?");
			 * for(int i=0;i<=1000;i++){ pb.addParamGroup(); pb.setParam(1, i);
			 * }
			 */
			dataBase.sqlExecute(pb);
		}
		catch (Exception e)
		{
			DBUtil.rollback(dataBase);
			e.printStackTrace();
		}
		finally
		{
			DBUtil.close(dataBase);
		}
		System.exit(0);
	}
}
