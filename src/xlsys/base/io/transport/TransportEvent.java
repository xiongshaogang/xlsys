package xlsys.base.io.transport;

import java.io.Serializable;
import java.util.EventObject;
import java.util.Map;

import xlsys.base.database.IDataBase;
import xlsys.base.dataset.IDataSet;
import xlsys.base.event.XlsysEvent;
import xlsys.base.session.Session;

public class TransportEvent extends XlsysEvent
{
	private static final long serialVersionUID = 1154309047651094883L;

	public Session session;
	public String tsId;
	public String fromTable;
	public String toTable;
	public String fromSql;
	public IDataSet fromDataSet;
	public int batchCount;
	public boolean cpSmCol;
	public int threadCount;
	public int dataPerThread;
	public IDataBase dataBase;
	public IDataBase fromDataBase;
	public IDataBase toDataBase;
	public Map<String, String> fromToColMap;
	public Map<String, Serializable> globalVarMap;
	public Map<String, Serializable> threadVarMap;
	public Map<String, Serializable> fromDataMap;
	public Map<String, Serializable> toDataMap;
	
	public TransportEvent(Object source, EventObject srcEvent)
	{
		super(source, srcEvent);
	}

	public TransportEvent(Object source)
	{
		super(source);
	}

}
