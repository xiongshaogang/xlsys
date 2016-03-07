package xlsys.base.test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.database.ConnectionPool;
import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.dataset.IDataSet;
import xlsys.base.util.ObjectUtil;

public class FlowPartTransform
{

	public static void main(String[] args)
	{
		IDataBase dataBase = null;
		try
		{
			dataBase = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(1007)).getNewDataBase();
			String selectSql = "select * from xlsys_flow";
			IDataSet dataSet = dataBase.sqlSelect(selectSql);
			int rowCount = dataSet.getRowCount();
			ExecuteBean eb = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_INSERT, "xlsys_flowpart");
			for(int i=0;i<rowCount;++i)
			{
				dataSet.gotoRow(i);
				String flowId = ObjectUtil.objectToString(dataSet.getValue("flowid"));
				String listPartId = ObjectUtil.objectToString(dataSet.getValue("listpartid"));
				BigDecimal mVidOfLPart = (BigDecimal) dataSet.getValue("mvidoflpart");
				String mainPartId = ObjectUtil.objectToString(dataSet.getValue("mainpartid"));
				BigDecimal mVidOfMPart = (BigDecimal) dataSet.getValue("mvidofmpart");
				Map<String, Serializable> dataMap = new HashMap<String, Serializable>();
				dataMap.put("flowid", flowId);
				dataMap.put("idx", 10);
				dataMap.put("clienttype", "%");
				dataMap.put("righttype", 0);
				dataMap.put("rightvalue", "%");
				dataMap.put("listpartid", listPartId);
				dataMap.put("mvidoflpart", mVidOfLPart);
				dataMap.put("mainpartid", mainPartId);
				dataMap.put("mvidofmpart", mVidOfMPart);
				eb.addData(dataMap);
			}
			dataBase.sqlExecute(eb);
		}
		catch(Exception e)
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
