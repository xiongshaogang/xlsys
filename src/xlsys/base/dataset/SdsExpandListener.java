package xlsys.base.dataset;

import java.util.List;

import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.log.Log;
import xlsys.base.log.LogUtil;

public class SdsExpandListener extends StorableDataSetAdapter
{
	@Override
	public void expandRows(DataSetEvent event)
	{
		IStorableDataSet sds = (IStorableDataSet)event.getDataSet();
		LogUtil.printlnInfo("event.beginIdxOfExpand="+event.beginIdxOfExpand+" event.endIdxOfExpand="+event.endIdxOfExpand);
		ParamBean selectBean = sds.getSelectBean();
		if(selectBean!=null)
		{
			IDataBase dataBase = null;
			try
			{
				dataBase = sds.getDataBase();
				IDataSet tempDataSet = null;
				if(sds.isSorted())
				{
					tempDataSet = dataBase.sqlSelect(selectBean,sds.getSortColMap(), event.beginIdxOfExpand, event.endIdxOfExpand);
				}
				else tempDataSet = dataBase.sqlSelect(selectBean, event.beginIdxOfExpand, event.endIdxOfExpand);
				if(tempDataSet!=null)
				{
					List<DataSetRow> tempRows = tempDataSet.getRows();
					for(int i=0;i<tempRows.size();++i)
					{
						//StorableDataSet.this.getRows().set(i+event.beginIdxOfExpand, tempRows.get(i));
						sds.getRows().get(i+event.beginIdxOfExpand).cloneOf(tempRows.get(i));
					}
				}
			}
			catch(Exception e)
			{
				event.doit = false;
				event.errMsg = e;
				event.interrupt = true;
				DataSetEvent msgEvent = new DataSetEvent(event.getDataSet(), event);
				msgEvent.logLevel = Log.LOG_LEVEL_ERROR;
				sds.fireShowMessage(event);
			}
		}
	}
}