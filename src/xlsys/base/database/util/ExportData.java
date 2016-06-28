package xlsys.base.database.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xlsys.base.database.EnvDataBase;
import xlsys.base.database.IDataBase;
import xlsys.base.dataset.StorableDataSet;
import xlsys.base.io.util.IOUtil;

public class ExportData implements Runnable
{
	private int envId;
	private String[] sqls;
	private String[] tableNames;
	private Integer value;
	private Exception e;
	private byte[] datas;
	
	// <sessionId, ExportData>
	public static Map<String, ExportData> exportSessionMap = new HashMap<String, ExportData>();
	
	public ExportData(int envId, String[] sqls)
	{
		this(envId, sqls, null);
	}
	
	public ExportData(int envId, String[] sqls, String[] tableNames)
	{
		this.envId = envId;
		this.sqls = sqls;
		this.tableNames = tableNames;
	}
	
	@Override
	public void run()
	{
		IDataBase dataBase = null;
		try
		{
			dataBase = EnvDataBase.getInstance(envId);
			List<StorableDataSet> sdsList = new ArrayList<StorableDataSet>();
			for(int i=0;i<sqls.length;++i)
			{
				StorableDataSet sds = new StorableDataSet(dataBase, sqls[i], tableNames==null?SqlUtil.tryToGetTableName(sqls[i]):tableNames[i]);
				sds.open();
				sdsList.add(sds);
				pushValue(i+1);
			}
			datas = IOUtil.getObjectBytes(sdsList);
			pushValue(sqls.length+1);
		}
		catch(Exception e)
		{
			pushException(e);
		}
		finally
		{
			DBUtil.close(dataBase);
		}
	}
	
	public int getMaximum()
	{
		return sqls.length+1;
	}
	
	private synchronized void pushValue(int value)
	{
		this.value = value;
		notifyAll();
	}
	
	private synchronized void pushException(Exception e)
	{
		this.e = e;
		notifyAll();
	}
	
	public synchronized Serializable popNextResult() throws InterruptedException
	{
		Serializable temp = null;
		if(e==null&&value==null) wait();
		if(e!=null)
		{
			temp = e;
			e = null;
		}
		else
		{
			temp = value;
			value = null;
		}
		return temp;
	}
	
	public byte[] getDatas()
	{
		return datas;
	}
}
