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
import xlsys.base.util.StringUtil;

public class ViewParamTranform
{

	public static void main(String[] args)
	{
		IDataBase dataBase = null;
		try
		{
			dataBase = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(1007)).getNewDataBase();
			String selectSql = "select * from xlsys_viewdetail";
			IDataSet dataSet = dataBase.sqlSelect(selectSql);
			int rowCount = dataSet.getRowCount();
			ExecuteBean eb = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_INSERT, "xlsys_viewdetailparam");
			for(int i=0;i<rowCount;++i)
			{
				dataSet.gotoRow(i);
				BigDecimal viewId = (BigDecimal) dataSet.getValue("viewid");
				BigDecimal idx = (BigDecimal) dataSet.getValue("idx");
				String param = ObjectUtil.objectToString(dataSet.getValue("param"));
				if(param==null) continue;
				Map<String, String> paramMap = StringUtil.getParamMap(param, XLSYS.COMMAND_RELATION, XLSYS.COMMAND_SEPARATOR);
				if(paramMap==null||paramMap.isEmpty()) continue;
				for(String attrName : paramMap.keySet())
				{
					String attrValue = paramMap.get(attrName);
					Map<String, Serializable> dataMap = new HashMap<String, Serializable>();
					dataMap.put("viewid", viewId);
					dataMap.put("idx", idx);
					dataMap.put("attrname", attrName);
					dataMap.put("attrvalue", attrValue);
					eb.addData(dataMap);
				}
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
