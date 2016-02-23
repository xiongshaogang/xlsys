package xlsys.base.dataset;

import java.io.Serializable;
import java.util.EventObject;

import xlsys.base.event.XlsysEvent;

/**
 * 数据集事件
 * @author Lewis
 *
 */
public class DataSetEvent extends XlsysEvent
{
	private static final long serialVersionUID = -8675895675538384791L;

	private IDataSet dataSet;

	public Serializable oldValue;
	public Serializable newValue;
	public DataSetRow oldRow;
	public DataSetColumn oldCol;
	public DataSetRow row;
	public DataSetColumn col;
	public int beginIdxOfExpand;
	public int endIdxOfExpand;
	public int logLevel;
	
	public String customId;
	public Serializable customData;

	public DataSetEvent(IDataSet dataSet)
	{
		this(dataSet, null);
	}

	public DataSetEvent(IDataSet dataSet, EventObject srcEvent)
	{
		super(dataSet, srcEvent);
		this.dataSet = dataSet;
	}

	public IDataSet getDataSet()
	{
		return dataSet;
	}
}
