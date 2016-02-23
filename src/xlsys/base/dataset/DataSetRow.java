package xlsys.base.dataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据集行
 * @author Lewis
 *
 */
public class DataSetRow implements Serializable
{
	public final static int STATUS_VIRTUAL = 0;
	public final static int STATUS_COMMON = 1;
	public final static int STATUS_FOR_NEW = 2;
	public final static int STATUS_REPLACE = 4;
	
	private static final long serialVersionUID = 8237244494142011771L;
	
	private ArrayList<DataSetCell> cells;
	private boolean changed;
	private int changeStatus;
	private int oldChangeStatus;
	/**
	 * this field is for user use.
	 */
	private transient Map<Object, Object> properties;
	
	public DataSetRow()
	{
		this(false);
	}
	
	public DataSetRow(boolean virtual)
	{
		cells = new ArrayList<DataSetCell>();
		changed = false;
		if(virtual)
		{
			changeStatus = STATUS_VIRTUAL;
			oldChangeStatus = STATUS_VIRTUAL;
		}
		else
		{
			changeStatus = STATUS_COMMON;
			oldChangeStatus = STATUS_COMMON;
		}
	}
	
	protected void addCell(DataSetCell cell)
	{
		cells.add(cell);
	}
	
	protected void removeCell(int cellIndex)
	{
		cells.remove(cellIndex);
	}
	
	public DataSetCell getCell(int index)
	{
		return cells.get(index);
	}
	
	public void setCell(int index, DataSetCell cell)
	{
		cells.add(index, cell);
	}

	public ArrayList<DataSetCell> getCells()
	{
		return cells;
	}

	public void setCells(ArrayList<DataSetCell> cells)
	{
		this.cells = cells;
	}

	public boolean isChanged()
	{
		return changed;
	}

	public void setChanged(boolean changed)
	{
		this.changed = changed;
	}

	public int getChangeStatus()
	{
		return changeStatus;
	}

	public void setChangeStatus(int changeStatus)
	{
		oldChangeStatus = this.changeStatus;
		this.changeStatus = changeStatus;
	}

	public int getOldChangeStatus()
	{
		return oldChangeStatus;
	}

	public Map<Object, Object> getProperties()
	{
		return properties;
	}
	
	public void putProperty(Object key, Object value)
	{
		if(properties==null) properties = new HashMap<Object, Object>();
		properties.put(key, value);
	}
	
	public boolean containsProperty(Object key)
	{
		boolean contains = false;
		if(properties!=null)
		{
			contains = properties.containsKey(key);
		}
		return contains;
	}
	
	public void removeProperty(Object key)
	{
		if(properties!=null)
		{
			properties.remove(key);
		}
	}
	
	public Object getProperty(Object key)
	{
		Object value = null;
		if(properties!=null)
		{
			value = properties.get(key);
		}
		return value;
	}

	protected void cloneOf(DataSetRow anotherRow)
	{
		for(int i=0;i<anotherRow.cells.size();i++)
		{
			DataSetCell cell = null;
			if(i>=cells.size())
			{
				cell = new DataSetCell();
				cells.add(cell);
			}
			else cell = cells.get(i);
			cell.setContent(anotherRow.cells.get(i).getContent());
		}
		for(int i=cells.size()-1;i>=anotherRow.cells.size();--i) cells.remove(i);
		changed = anotherRow.changed;
		changeStatus = anotherRow.changeStatus;
		oldChangeStatus = anotherRow.oldChangeStatus;
	}
}
