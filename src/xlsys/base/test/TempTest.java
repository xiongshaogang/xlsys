package xlsys.base.test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.database.ConnectionPool;
import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.dataset.IDataSet;
import xlsys.base.util.ObjectUtil;

public class TempTest
{
	
	public static void main(String[] args) throws Exception
	{
		int dbid = 1009;
		IDataBase dataBase = null;
		try
		{
			dataBase = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
			dataBase.setAutoCommit(false);
			String selectSql = "select menuid from xlsys_menu where menuid like ?";
			ParamBean pb = new ParamBean(selectSql);
			pb.addParamGroup();
			pb.setParam(1, "xlsys.golf.menu%");
			IDataSet dataSet = dataBase.sqlSelect(pb);
			int rowCount = dataSet.getRowCount();
			ExecuteBean eb = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_INSERT, "xlsys_menuright");
			for(int i=0;i<rowCount;++i)
			{
				String menuId = ObjectUtil.objectToString(dataSet.getValue(i, 0));
				Map<String, Serializable> dataMap = new HashMap<String, Serializable>();
				dataMap.put("menuid", menuId);
				dataMap.put("idx", 10);
				dataMap.put("righttype", 2);
				dataMap.put("rightvalue", "200000%");
				eb.addData(dataMap);
			}
			dataBase.sqlExecute(eb);
			dataBase.commit();
		}
		catch(Exception e)
		{
			DBUtil.rollback(dataBase);
		}
		finally
		{
			DBUtil.close(dataBase);
		}
	}
}
