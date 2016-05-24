package xlsys.base.dataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import xlsys.base.log.Log;
import xlsys.base.util.ObjectUtil;
import xlsys.base.util.ReflectUtil;

/**
 * 标准数据集类
 * @author Lewis
 *
 */
public class DataSet implements IDataSet
{
	private static final long serialVersionUID = 6162437341502690699L;
	
	private String name;
	private int style;
	private int expandPerCount;
	private Map<String, Integer> sortColMap;
	private List<DataSetColumn> columns;
	private List<DataSetRow> rows;
	protected int rowCursor;
	protected int colCursor;
	private Map<String, Integer> columnMap;
	/**
	 * 0~9999 for DataSet use
	 * 10000~19999 for IStorableDataSet use
	 */
	private Map<Integer, Integer> optionMap;
	protected transient Set<DataSetListener> dataSetListenerSet;
	/**
	 * 之前编辑的行，用来判断行提交事件的触发
	 */
	protected DataSetRow oldEditRow;
	/**
	 * 是否正在进行边界检查
	 */
	private boolean regionChecking;
	
	public DataSet()
	{
		this(STYLE_NONE);
	}
	
	public DataSet(int style)
	{
		sortColMap = new LinkedHashMap<String, Integer>();
		columns = new ArrayList<DataSetColumn>();
		rows = new ArrayList<DataSetRow>();
		rowCursor = -1;
		colCursor = -1;
		expandPerCount = 32;
		optionMap = new HashMap<Integer, Integer>();
		this.style = style;
		regionChecking = false;
	}
	
	public DataSet(List<DataSetColumn> dataSetColumns)
	{
		this(dataSetColumns, STYLE_NONE);
	}
	
	public DataSet(List<DataSetColumn> dataSetColumns, int style)
	{
		this(style);
		for(DataSetColumn dsc : dataSetColumns) insertNewColumnAfterLast().cloneOf(dsc);
		buildColumnMap();
	}
	
	@Override
	public int getExpandPerCount()
	{
		return expandPerCount;
	}

	@Override
	public void setExpandPerCount(int expandPerCount)
	{
		this.expandPerCount = expandPerCount;
	}

	/**
	 * 此方法只有当style为STYLE_VIRTUAL时有效
	 */
	@Override
	public synchronized void setRowCount(int rowCount)
	{
		if(ObjectUtil.hasBitProp(style, STYLE_VIRTUAL))
		{
			int curRowCount = getRowCount();
			if(curRowCount<rowCount)
			{
				this.setEventLock(true);
				int oldCursor = getRowCursor();
				for(int i=0;i<rowCount-curRowCount;++i) insertNewRowAfterLast(true);
				setRowCursor(oldCursor);
				this.setEventLock(false);
			}
		}
	}
	
	/**
	 * 此方法只有当style为STYLE_VIRTUAL时有效
	 */
	public synchronized void setColumnCount(int colCount)
	{
		if(ObjectUtil.hasBitProp(style, STYLE_VIRTUAL))
		{
			int curColCount = getColumnCount();
			if(curColCount<colCount)
			{
				this.setEventLock(true);
				int oldCursor = getColCursor();
				for(int i=0;i<colCount-curColCount;i++)
				{
					insertNewColumnAfterLast();
				}
				setColCursor(oldCursor);
				this.setEventLock(false);
			}
		}
	}
	
	@Override
	public void buildColumnMap()
	{
		if(columnMap==null) columnMap = new HashMap<String, Integer>();
		else columnMap.clear();
		for(int i=0;i<columns.size();i++)
		{
			DataSetColumn column = (DataSetColumn) columns.get(i);
			columnMap.put(column.getColumnName().toLowerCase(), i);
		}
	}
	
	@Override
	public int getColumnIndex(String columnName)
	{
		if(columnName==null) return -1;
		if(columnMap==null||columnMap.isEmpty()||!columnMap.containsKey(columnName.toLowerCase()))
		{
			buildColumnMap();
		}
		Integer i = columnMap.get(columnName.toLowerCase());
		return i==null?-1:i.intValue();
	}
	
	@Override
	public void removeColumn(String columnName)
	{
		removeColumn(getColumnIndex(columnName));
	}
	
	@Override
	public void removeColumn(int colIdx)
	{
		if(!colRegionCheck(colIdx)) return;
		DataSetColumn toRemoveCol = getColumn(colIdx);
		DataSetEvent event = new DataSetEvent(this);
		event.col = toRemoveCol;
		fireBeforeRemoveCol(event);
		if(event.doit)
		{
			for(int i=0;i<rows.size();++i)
			{
				DataSetRow row = rows.get(i);
				row.removeCell(colIdx);
			}
			columns.remove(colIdx);
			buildColumnMap();
			int cursor = getColCursor();
			if(colIdx<=cursor) setColCursor(cursor-1);
			event = new DataSetEvent(this);
			event.col = toRemoveCol;
			fireAfterRemoveCol(event);
		}
	}
	
	@Override
	public int getRowCount()
	{
		return rows.size();
	}
	
	@Override
	public int getColumnCount()
	{
		return columns.size();
	}
	
	@Override
	public int getRowCursor()
	{
		return rowCursor;
	}
	
	protected boolean setRowCursor(int rowCursor)
	{
		boolean success = false;
		if(rowRegionCheck(rowCursor))
		{
			// 扩展到需要的行数
			success = expandToRow(rowCursor);
		}
		else if(rowCursor==-1) success = true;
		if(success) this.rowCursor = rowCursor;
		return success;
	}
	
	@Override
	public int getColCursor()
	{
		return colCursor;
	}
	
	protected boolean setColCursor(int colCursor)
	{
		boolean success = false;
		if(colRegionCheck(colCursor)||colCursor==-1) success = true;
		if(success) this.colCursor = colCursor;
		return success;
	}
	
	@Override
	public int[] getPageRangeOfRowIndex(int wantRowIndex)
	{
		// 注意这里不能使用regionCheck，否则会触发两次expandRow
		if(wantRowIndex<0||wantRowIndex>=getRowCount()) return null;
		int beginRowIdx = wantRowIndex / expandPerCount * expandPerCount;
		int endRowIdx = Math.min(beginRowIdx + expandPerCount-1, getRowCount()-1);
		return new int[]{beginRowIdx, endRowIdx};
	}
	
	@Override
	public boolean expandToRow(int wantRowIndex)
	{
		boolean success = true;
		if(wantRowIndex<0||wantRowIndex>=getRowCount()) return success;
		if(ObjectUtil.hasBitProp(style, STYLE_VIRTUAL)&&isVirtualRow(wantRowIndex))
		{
			int[] range = getPageRangeOfRowIndex(wantRowIndex);
			if(range!=null)
			{
				DataSetEvent event = new DataSetEvent(this);
				event.beginIdxOfExpand = range[0];
				event.endIdxOfExpand = range[1];
				success = fireExpandRows(event);
				if(success)
				{
					// 注意这里不能使用getRow来获取行，因为getRow里面还会触发expandToRow
					for(int i=event.beginIdxOfExpand;i<=event.endIdxOfExpand;i++)
					{
						rows.get(i).setChangeStatus(DataSetRow.STATUS_COMMON);
					}
				}
			}
		}
		return success;
	}
	
	@Override
	public boolean isVirtualRow(DataSetRow row)
	{
		return row.getChangeStatus()==DataSetRow.STATUS_VIRTUAL;
	}
	
	@Override
	public boolean isVirtualRow(int rowIndex)
	{
		return rows.get(rowIndex).getChangeStatus()==DataSetRow.STATUS_VIRTUAL;
	}
	
	protected void expandAllRows()
	{
		for(int i=0;i<getRowCount();i+=expandPerCount) expandToRow(i);
	}
	
	@Override
	public DataSetRow getCurRow()
	{
		DataSetRow row = null;
		int cursor = getRowCursor();
		if(cursor>=0) row = getRow(cursor);
		return row;
	}
	
	@Override
	public DataSetColumn getCurColumn()
	{
		DataSetColumn col = null;
		int cursor = getColCursor();
		if(cursor>=0) col = getColumn(cursor);
		return col;
	}
	
	@Override
	public boolean beforeFirst()
	{
		return gotoRow(-1);
	}
	
	@Override
	public boolean gotoRow(int rowIndex)
	{
		return gotoRow(rowIndex, true);
	}
	
	@Override
	public boolean gotoRow(int rowIndex, boolean postLastEditRow)
	{
		return gotoCell(rowIndex, getColCursor(), postLastEditRow);
	}

	@Override
	public boolean gotoRow(DataSetRow row)
	{
		return gotoRow(row, true);
	}
	
	@Override
	public boolean gotoRow(DataSetRow row, boolean postLastEditRow)
	{
		boolean success = false;
		if(getCurRow()==row) return true;
		int index = getRowIndex(row);
		success = gotoRow(index, postLastEditRow);
		return success;
	}
	
	@Override
	public boolean gotoColumn(int colIndex)
	{
		return gotoCell(getRowCursor(), colIndex, false);
	}
	
	@Override
	public boolean gotoColumn(String columnName)
	{
		return gotoColumn(getColumnIndex(columnName));
	}
	
	@Override
	public boolean gotoCell(int rowIndex, int colIndex)
	{
		return gotoCell(rowIndex, colIndex, true);
	}
	
	@Override
	public boolean gotoCell(int rowIndex, int colIndex, boolean postLastEditRow)
	{
		boolean success = false;
		if((!rowRegionCheck(rowIndex)&&rowIndex!=-1)||(!colRegionCheck(colIndex)&&colIndex!=-1)) return false;
		if(rowIndex==getRowCursor()&&colIndex==getColCursor()) return true;
		boolean postSuccess = !postLastEditRow||rowIndex==getRowCursor();
		if(postSuccess||postRow())
		{
			int oldRowCursor = getRowCursor();
			int oldColCursor = getColCursor();
			DataSetRow oldRow = getRow(oldRowCursor);
			DataSetColumn oldCol = getColumn(oldColCursor);
			DataSetRow toRow = getRow(rowIndex);
			DataSetColumn toCol = getColumn(colIndex);
			DataSetEvent event = new DataSetEvent(this);
			event.oldRow = oldRow;
			event.oldCol = oldCol;
			event.row = toRow;
			event.col = toCol;
			fireBeforeCursorChange(event);
			if(event.doit&&setRowCursor(rowIndex)&&setColCursor(colIndex))
			{
				success = true;
				event = new DataSetEvent(this);
				event.oldRow = oldRow;
				event.oldCol = oldCol;
				event.row = toRow;
				event.col = toCol;
				fireAfterCursorChange(event);
			}
			else
			{
				setRowCursor(oldRowCursor);
				setColCursor(oldColCursor);
			}
		}
		return success;
	}
	
	@Override
	public boolean gotoCell(int rowIndex, String columnName)
	{
		return gotoCell(rowIndex, getColumnIndex(columnName));
	}
	
	@Override
	public boolean gotoCell(DataSetRow row, int colIndex)
	{
		return gotoCell(getRowIndex(row), colIndex);
	}
	
	@Override
	public boolean gotoCell(DataSetRow row, String columnName)
	{
		return gotoCell(getRowIndex(row), getColumnIndex(columnName));
	}
	
	@Override
	public DataSetRow getRow(int rowIndex)
	{
		if(!rowRegionCheck(rowIndex)) return null;
		if(ObjectUtil.hasBitProp(style, STYLE_VIRTUAL))
		{
			if(!expandToRow(rowIndex)) return null;
		}
		return rows.get(rowIndex);
	}
	
	@Override
	public int getRowIndex(DataSetRow row)
	{
		if(row==null) return -1;
		return rows.indexOf(row);
	}
	
	@Override
	public DataSetColumn getColumn(int columnIndex)
	{
		if(!colRegionCheck(columnIndex)) return null;
		return columns.get(columnIndex);
	}
	
	@Override
	public DataSetColumn getColumn(String colName)
	{
		DataSetColumn dsc = null;
		int idx = getColumnIndex(colName);
		if(idx!=-1) dsc = getColumn(idx);
		return dsc;
	}
	
	@Override
	public String getColumnName(int columnIndex)
	{
		String colName = null;
		DataSetColumn dsc = getColumn(columnIndex);
		if(dsc!=null) colName = dsc.getColumnName();
		return colName;
	}
	
	protected synchronized boolean rowRegionCheck(int rowAt)
	{
		if(regionChecking) return true;
		regionChecking = true;
		boolean success = true;
		if(rowAt<0||rowAt>=getRowCount())
		{
			success = false;
		}
		if(success) success = expandToRow(rowAt);
		regionChecking = false;
		return success;
	}
	
	protected synchronized boolean colRegionCheck(int colAt)
	{
		if(regionChecking) return true;
		regionChecking = true;
		boolean success = true;
		if(colAt<0||colAt>=getColumnCount())
		{
			success = false;
		}
		regionChecking = false;
		return success;
	}
	
	protected boolean regionCheck(int rowAt, int colAt)
	{
		return rowRegionCheck(rowAt)&&colRegionCheck(colAt);
	}
	
	@Override
	public DataSetRow insertRow(DataSetRow row)
	{
		return insertRow(row, false);
	}
	
	@Override
	public DataSetRow insertRow(DataSetRow row, boolean clone)
	{
		return insertRowAt(getRowCursor(), row, clone);
	}

	@Override
	public DataSetRow insertRowAtFirst(DataSetRow row)
	{
		return insertRowAtFirst(row, false);
	}
	
	@Override
	public DataSetRow insertRowAtFirst(DataSetRow row, boolean clone)
	{
		return insertRowAt(0, row, clone);
	}
	
	@Override
	public DataSetRow insertRowAfterLast(DataSetRow row)
	{
		return insertRowAfterLast(row, false);
	}
	
	@Override
	public DataSetRow insertRowAfterLast(DataSetRow row, boolean clone)
	{
		return insertRowAt(rows.size(), row, clone);
	}
	
	@Override
	public DataSetRow insertRowAt(int index, DataSetRow row)
	{
		return insertRowAt(index, row, false);
	}
	
	@Override
	public synchronized DataSetRow insertRowAt(int index, DataSetRow row, boolean clone)
	{
		DataSetRow newRow = null;
		if(postRow())
		{
			newRow = doInsertRow(index, row, clone);
			if(gotoRow(newRow)) oldEditRow = newRow;
		}
		return newRow;
	}
	
	protected synchronized DataSetRow doInsertRow(int index, DataSetRow row, boolean clone)
	{
		DataSetRow newRow = null;
		if(clone)
		{
			newRow = new DataSetRow();
			for(int i=0;i<row.getCells().size();i++)
			{
				DataSetCell cell = new DataSetCell();
				newRow.addCell(cell);
			}
			newRow.cloneOf(row);
		}
		else newRow = row;
		if(index<0) index = 0;
		else if(index>rows.size()) index = rows.size();
		DataSetEvent event = new DataSetEvent(this);
		event.row = newRow;
		fireBeforeInsertRow(event);
		rows.add(index, newRow);
		if(index<=getRowCursor()) setRowCursor(index+1);
		event = new DataSetEvent(this);
		event.row = newRow;
		fireAfterInsertRow(event);
		return newRow;
	}
	
	@Override
	public DataSetRow insertNewRow()
	{
		return insertNewRow(false);
	}
	
	@Override
	public DataSetRow insertNewRow(boolean virtual)
	{
		return insertNewRowAt(getRowCursor(), virtual);
	}
	
	@Override
	public DataSetRow insertNewRowAtFirst()
	{
		return insertNewRowAtFirst(false);
	}
	
	@Override
	public DataSetRow insertNewRowAtFirst(boolean virtual)
	{
		return insertNewRowAt(0, virtual);
	}
	
	@Override
	public DataSetRow insertNewRowAfterLast()
	{
		return insertNewRowAfterLast(false);
	}
	
	@Override
	public DataSetRow insertNewRowAfterLast(boolean virtual)
	{
		return insertNewRowAt(rows.size(), virtual);
	}
	
	@Override
	public DataSetRow insertNewRowAt(int index)
	{
		return insertNewRowAt(index, false);
	}
	
	@Override
	public DataSetRow insertNewRowAt(int index, boolean virtual)
	{
		DataSetRow newRow = null;
		if(postRow())
		{
			newRow = doInsertNewRow(index, virtual);
			if(!virtual)
			{
				if(gotoRow(newRow)) oldEditRow = newRow;
			}
		}
		return newRow;
	}
	
	protected synchronized DataSetRow doInsertNewRow(int index, boolean virtual)
	{
		if(index<0) index = 0;
		else if(index>rows.size()) index = rows.size();
		DataSetRow newRow = new DataSetRow(virtual);
		for(int i=0;i<columns.size();++i)
		{
			DataSetCell cell = new DataSetCell();
			newRow.addCell(cell);
		}
		DataSetEvent event = new DataSetEvent(this);
		event.row = newRow;
		fireBeforeInsertRow(event);
		rows.add(index, newRow);
		if(index<=getRowCursor()) setRowCursor(index+1);
		event = new DataSetEvent(this);
		event.row = newRow;
		fireAfterInsertRow(event);
		return newRow;
	}
	
	@Override
	public DataSetColumn insertNewColumnAtFirst()
	{
		return insertNewColumnAt(0);
	}
	
	@Override
	public DataSetColumn insertNewColumnAfterLast()
	{
		return insertNewColumnAt(columns.size());
	}
	
	@Override
	public DataSetColumn insertNewColumn()
	{
		return insertNewColumnAt(getColCursor());
	}

	@Override
	public DataSetColumn insertNewColumnAt(int index)
	{
		DataSetColumn newColumn = null;
		if(postRow())
		{
			if(index<0) index = 0;
			else if(index>columns.size()) index = columns.size();
			newColumn = new DataSetColumn();
			DataSetEvent event = new DataSetEvent(this);
			event.col = newColumn;
			fireBeforeInsertCol(event);
			columns.add(index, newColumn);
			for(int i=0;i<rows.size();i++)
			{
				DataSetCell cell = new DataSetCell();
				DataSetRow row = rows.get(i);
				row.getCells().add(index, cell);
			}
			if(index<=getColCursor()) setColCursor(index+1);
			event = new DataSetEvent(this);
			event.col = newColumn;
			fireAfterInsertCol(event);
		}
		return newColumn;
	}
	
	@Override
	public synchronized DataSetRow removeRow(DataSetRow row)
	{
		return removeRow(getRowIndex(row));
	}
	
	@Override
	public synchronized DataSetRow removeRow(int rowIndex)
	{
		if(!rowRegionCheck(rowIndex)||!expandToRow(rowIndex)) return null;
		DataSetRow toRemoveRow = doRemoveRow(rowIndex);
		if(toRemoveRow!=null)
		{
			if(oldEditRow==toRemoveRow) oldEditRow = null;
			int cursor = getRowCursor();
			if(cursor>rowIndex) setRowCursor(cursor-1);
			else if(cursor==rowIndex)
			{
				if(cursor>=rows.size()) gotoRow(cursor-1);
				else
				{
					int oldCursor = cursor;
					setRowCursor(-1);
					gotoRow(oldCursor);
				}
			}
		}
		return toRemoveRow;
	}
	
	protected DataSetRow doRemoveRow(int rowNum)
	{
		DataSetRow toRemoveRow = rows.get(rowNum);
		DataSetEvent event = new DataSetEvent(this);
		event.row = toRemoveRow;
		fireBeforeRemoveRow(event);
		if(event.doit)
		{
			rows.remove(rowNum);
			event = new DataSetEvent(this);
			event.row = toRemoveRow;
			fireAfterRemoveRow(event);
		}
		else toRemoveRow = null;
		return toRemoveRow;
	}
	
	@Override
	public synchronized void removeAllRow()
	{
		for(int i=rows.size()-1;i>=0;--i) removeRow(i);
		rows.clear();
		oldEditRow = null;
		setRowCursor(-1);
	}
	
	@Override
	public Serializable getValue()
	{
		return getValue(getRowCursor(), getColCursor());
	}
	
	@Override
	public Serializable getValue(String columnName)
	{
		return getValue(getRowCursor(), getColumnIndex(columnName));
	}
	
	@Override
	public Serializable getValue(int colIndex)
	{
		return getValue(getRowCursor(), colIndex);
	}
	
	@Override
	public Serializable getValue(int rowIndex, String columnName)
	{
		int colIndex = getColumnIndex(columnName);
		return getValue(rowIndex, colIndex);
	}
	
	@Override
	public Serializable getValue(DataSetRow row, String columnName)
	{
		return getValue(getRowIndex(row), getColumnIndex(columnName));
	}
	
	@Override
	public Serializable getValue(DataSetRow row, int colIndex)
	{
		return getValue(getRowIndex(row), colIndex);
	}
	
	@Override
	public Serializable getValue(int rowIndex, int colIndex)
	{
		if(!regionCheck(rowIndex, colIndex)) return null;
		expandToRow(rowIndex);
		Serializable value = null;
		DataSetRow row = rows.get(rowIndex);
		if(row!=null)
		{
			DataSetCell cell = row.getCells().get(colIndex);
			if(cell!=null) value = cell.getContent();
		}
		return value;
	}
	
	@Override
	public boolean setValue(Serializable value)
	{
		return setValue(getRowCursor(), getColCursor(), value);
	}
	
	@Override
	public boolean setValue(String columnName, Serializable value)
	{
		return setValue(getRowCursor(), columnName, value);
	}
	
	@Override
	public boolean setValue(int colIndex, Serializable value)
	{
		return setValue(getRowCursor(), colIndex, value);
	}
	
	@Override
	public boolean setValue(int rowIndex, String columnName, Serializable value)
	{
		return setValue(rowIndex, getColumnIndex(columnName), value);
	}
	
	@Override
	public boolean setValue(DataSetRow row, String columnName, Serializable value)
	{
		return setValue(getRowIndex(row), getColumnIndex(columnName), value);
	}
	
	@Override
	public boolean setValue(DataSetRow row, int colIndex, Serializable value)
	{
		return setValue(getRowIndex(row), colIndex, value);
	}
	
	@Override
	public synchronized boolean setValue(int rowIndex, int colIndex, Serializable value)
	{
		boolean success = false;
		if(!regionCheck(rowIndex, colIndex)) return success;
		DataSetRow row = rows.get(rowIndex);
		if(row!=null)
		{
			DataSetCell cell = row.getCells().get(colIndex);
			if(cell!=null)
			{
				Serializable oldValue = cell.getContent();
				// 如果是byte数组，就用byte数组比较;如果不是byte数组，就直接比较
				boolean needSet = false;
				if(value instanceof byte[] && oldValue instanceof byte[])
				{
					if(!Arrays.equals((byte[])value, (byte[])oldValue)) needSet = true;
				}
				else if((value!=null&&!value.equals(oldValue)) || (oldValue!=null&&!oldValue.equals(value))) needSet = true;
				if(needSet)
				{
					DataSetEvent event = new DataSetEvent(this);
					event.oldValue = oldValue;
					event.newValue = value;
					event.row = row;
					event.col = getColumn(colIndex);
					// 当旧值和新值不一样时，设置值
					boolean postRowSuccess = true;
					if(oldEditRow!=row)
					{
						// 当设置值的行和之前编辑的行不一致时，先提交之前编辑的行
						postRowSuccess = postRow();
					}
					if(postRowSuccess)
					{
						fireBeforePostValue(event);
						if(event.doit)
						{
							doSetValue(rowIndex, colIndex, value);
							oldEditRow = row;
							success = true;
							fireAfterPostValue(event);
						}
					}
				}
			}
			else
			{
				DataSetEvent event = new DataSetEvent(this);
				event.errMsg = new NullPointerException("Cell is not initialized. Row At : "+rowIndex+"     Column At : "+colIndex);
				event.logLevel = Log.LOG_LEVEL_ERROR;
				fireShowMessage(event);
			}
		}
		return success;
	}
	
	protected void doSetValue(int rowIndex, int colIndex, Serializable value)
	{
		DataSetCell cell = rows.get(rowIndex).getCells().get(colIndex);
		cell.setContent(value);
	}
	
	@Override
	public synchronized boolean postRow(int rowIndex)
	{
		boolean success = false;
		if(!rowRegionCheck(rowIndex)) return true;
		DataSetEvent event = new DataSetEvent(this);
		DataSetRow row = getRow(rowIndex);
		event.row = row;
		fireBeforePostRow(event);
		if(event.doit)
		{
			if(row==oldEditRow) oldEditRow = null;
			success = true;
			event = new DataSetEvent(this);
			event.row = row;
			fireAfterPostRow(event);
		}
		return success;
	}
	
	@Override
	public synchronized boolean postRow()
	{
		return postRow(getRowIndex(oldEditRow));
	}
	
	@Override
	public void setOption(int option, boolean value)
	{
		setOption(option, value, false);
	}
	
	@Override
	public synchronized void setOption(int option, boolean value, boolean useCount)
	{
		if(useCount)
		{
			Integer oldCount = optionMap.get(option);
			if(oldCount==null) oldCount = 0;
			if(value) optionMap.put(option, ++oldCount);
			else
			{
				optionMap.put(option, --oldCount);
				if(oldCount<=0) optionMap.remove(option);
			}
		}
		else
		{
			if(value) optionMap.put(option, 0);
			else optionMap.remove(option);
		}
	}
	
	@Override
	public boolean isOption(int option)
	{
		return optionMap.containsKey(option);
	}
	
	protected void refDataSet(IDataSet toRefDataSet)
	{
		List<DataSetRow> refRows = toRefDataSet.getRows();
		List<DataSetColumn> refColumns = toRefDataSet.getColumns();
		if(ObjectUtil.hasBitProp(style, STYLE_VIRTUAL))
		{
			setRowCount(refRows.size());
			setColumnCount(refColumns.size());
			for(int i=0;i<refRows.size();++i)
			{
				rows.get(i).cloneOf(refRows.get(i));
			}
			for(int i=0;i<refColumns.size();++i)
			{
				columns.get(i).cloneOf(refColumns.get(i));
			}
		}
		else
		{
			rows = refRows;
			columns = refColumns;
		}
		buildColumnMap();
	}
	
	@Override
	public synchronized void setSortColumn(String... colNames)
	{
		setSortColumn(colNames, null);
	}
	
	@Override
	public synchronized void setSortColumn(String[] colNames, int[] sortTypes)
	{
		Map<String, Integer> sortColMap = new LinkedHashMap<String, Integer>();
		for(int i=0;i<colNames.length;++i)
		{
			int sortType = SORT_TYPE_ASC;
			if(sortTypes!=null&&i<sortTypes.length) sortType = sortTypes[i];
			sortColMap.put(colNames[i], sortType);
		}
		setSortColumn(sortColMap);
	}
	
	@Override
	public void setSortColumn(Map<String, Integer> sortColMap)
	{
		setSortColMap(sortColMap);
	}
	
	@Override
	public void setSortColMap(Map<String, Integer> sortColMap)
	{
		this.sortColMap.clear();
		this.sortColMap.putAll(sortColMap);
	}
	
	@Override
	public synchronized void sort()
	{
		expandAllRows();
		LinkedHashMap<Integer, Integer> sortColIdxMap = new LinkedHashMap<Integer, Integer>();
		for(String sortColName : sortColMap.keySet())
		{
			int colIdx = getColumnIndex(sortColName);
			if(colIdx!=-1)
			{
				int sortType = sortColMap.get(sortColName);
				sortColIdxMap.put(colIdx, sortType);
			}
		}
		List<ComparableRow> crList = new ArrayList<ComparableRow>();
		for(DataSetRow row : rows)
		{
			crList.add(new ComparableRow(row, sortColIdxMap));
		}
		Collections.sort(crList);
		// rebuilt rows and cols
		DataSetRow curRow = getCurRow();
		setEventLock(true);
		rows.clear();
		for(ComparableRow cr : crList)
		{
			insertRowAfterLast(cr.row, false);
		}
		// redirect row cursor
		setRowCursor(getRowIndex(curRow));
		setEventLock(false);
	}

	@Override
	public synchronized void swapRow(int i, int j)
	{
		if(i==j) return;
		int min = i<j?i:j;
		int max = i<j?j:i;
		setEventLock(true);
		// 先删除大的行
		DataSetRow maxRow = removeRow(max);
		// 再删除小的行
		DataSetRow minRow = removeRow(min);
		// 先插入大行到小的序号中
		insertRowAt(min, maxRow, false);
		// 再插入小行到大的序号中
		insertRowAt(max, minRow, false);
		setEventLock(false);
	}
	
	@Override
	public synchronized void addDataSetListener(DataSetListener dataSetListener)
	{
		if(dataSetListenerSet==null) dataSetListenerSet = new LinkedHashSet<DataSetListener>();
		dataSetListenerSet.add(dataSetListener);
		DataSetEvent event = new DataSetEvent(this);
		fireAfterAddToDataSet(dataSetListener, event);
	}
	
	@Override
	public synchronized void removeDataSetListener(DataSetListener dataSetListener)
	{
		if(dataSetListenerSet!=null)
		{
			if(dataSetListenerSet.remove(dataSetListener))
			{
				DataSetEvent event = new DataSetEvent(this);
				fireBeforeRemoveFromDataSet(dataSetListener, event);
			}
		}
	}
	
	@Override
	public Set<DataSetListener> getDataSetListeners()
	{
		return dataSetListenerSet;
	}
	
	@Override
	public synchronized void refresh()
	{
		DataSetEvent event = new DataSetEvent(this);
		fireBeforeRefresh(event);
		refreshing();
		event = new DataSetEvent(this);
		fireAfterRefresh(event);
	}
	
	protected void refreshing()
	{
		// Do nothing in this class
	}
	
	@Override
	public void setEventLock(Class<? extends IDataSet> dataSetClass, boolean lock)
	{
		try
		{
			Set<Integer> fieldValueSet = ReflectUtil.getStaticFinalField(dataSetClass, "OPTION_LOCK_", false).keySet();
			for(Integer option : fieldValueSet)
			{
				setOption(option, lock, true);
			}
		}
		catch(Exception e)
		{
			DataSetEvent event = new DataSetEvent(this);
			event.errMsg = e;
			event.logLevel = Log.LOG_LEVEL_ERROR;
			fireShowMessage(event);
		}
	}
	
	@Override
	public void setEventLock(boolean lock)
	{
		setEventLock(getClass(), lock);
	}
	
	public void fireBeforeInsertCol(DataSetEvent event)
	{
		if(isOption(OPTION_LOCK_INSERT_COL_EVENT)) return;
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					listener.beforeInsertCol(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireAfterInsertCol(DataSetEvent event)
	{
		if(isOption(OPTION_LOCK_INSERT_COL_EVENT)) return;
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					listener.afterInsertCol(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireBeforeInsertRow(DataSetEvent event)
	{
		if(isOption(OPTION_LOCK_INSERT_ROW_EVENT)) return;
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					listener.beforeInsertRow(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireAfterInsertRow(DataSetEvent event)
	{
		if(isOption(OPTION_LOCK_INSERT_ROW_EVENT)) return;
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					listener.afterInsertRow(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireBeforeRemoveRow(DataSetEvent event)
	{
		if(isOption(OPTION_LOCK_REMOVE_ROW_EVENT)) return;
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					listener.beforeRemoveRow(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireAfterRemoveRow(DataSetEvent event)
	{
		if(isOption(OPTION_LOCK_REMOVE_ROW_EVENT)) return;
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					listener.afterRemoveRow(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireBeforeRemoveCol(DataSetEvent event)
	{
		if(isOption(OPTION_LOCK_REMOVE_COL_EVENT)) return;
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					listener.beforeRemoveCol(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireAfterRemoveCol(DataSetEvent event)
	{
		if(isOption(OPTION_LOCK_REMOVE_COL_EVENT)) return;
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					listener.afterRemoveCol(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireBeforeRefresh(DataSetEvent event)
	{
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					listener.beforeRefresh(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireAfterRefresh(DataSetEvent event)
	{
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					listener.afterRefresh(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireAfterAddToDataSet(DataSetListener dataSetListener, DataSetEvent event)
	{
		try
		{
			if(dataSetListener!=null)
			{
				dataSetListener.afterAddToDataSet(event);
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireBeforeRemoveFromDataSet(DataSetListener dataSetListener, DataSetEvent event)
	{
		try
		{
			if(dataSetListener!=null)
			{
				dataSetListener.beforeRemoveFromDataSet(event);
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireShowMessage(DataSetEvent event)
	{
		if(dataSetListenerSet!=null)
		{
			for(DataSetListener listener : dataSetListenerSet)
			{
				listener.showMessage(event);
				if(event.interrupt) break;
			}
		}
	}
	
	public void fireBeforePostValue(DataSetEvent event)
	{
		if(isOption(OPTION_LOCK_POST_VALUE_EVENT)) return;
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					listener.beforePostValue(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireAfterPostValue(DataSetEvent event)
	{
		if(isOption(OPTION_LOCK_POST_VALUE_EVENT)) return;
		try
		{
			if(dataSetListenerSet!=null)
			{
				DataSetListener[] listeners = dataSetListenerSet.toArray(new DataSetListener[0]);
				for(DataSetListener listener : listeners)
				{
					listener.afterPostValue(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireBeforePostRow(DataSetEvent event)
	{
		if(isOption(OPTION_LOCK_POST_ROW_EVENT)) return;
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					listener.beforePostRow(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireAfterPostRow(DataSetEvent event)
	{
		if(isOption(OPTION_LOCK_POST_ROW_EVENT)) return;
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					listener.afterPostRow(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public void fireBeforeCursorChange(DataSetEvent event)
	{
		if(isOption(OPTION_LOCK_CURSOR_CHANGE)) return;
		try
		{
			if(dataSetListenerSet!=null)
			{
				DataSetListener[] listeners = dataSetListenerSet.toArray(new DataSetListener[0]);
				for(DataSetListener listener : listeners)
				{
					listener.beforeCursorChange(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}

	public void fireAfterCursorChange(DataSetEvent event)
	{
		if(isOption(OPTION_LOCK_CURSOR_CHANGE)) return;
		try
		{
			if(dataSetListenerSet!=null)
			{
				// 防止快速迭代失败。在有共享界面的情况下，数据集变换会导致在换行时共享界面的监听也加入到当前DataSet中，从而导致快速迭代失败
				DataSetListener[] listeners = dataSetListenerSet.toArray(new DataSetListener[0]);
				for(DataSetListener listener : listeners)
				{
					listener.afterCursorChange(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	public boolean fireExpandRows(DataSetEvent event)
	{
		try
		{
			setEventLock(true);
			int oldRowCursor = getRowCursor();
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					listener.expandRows(event);
					if(event.interrupt) break;
				}
			}
			setRowCursor(oldRowCursor);
			setEventLock(false);
			if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
		return event.doit;
	}
	
	public void fireCustomTrigger(DataSetEvent event)
	{
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					listener.customTrigger(event);
					if(event.interrupt) break;
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}

	@Override
	public List<DataSetColumn> getColumns()
	{
		return columns;
	}

	@Override
	public List<DataSetRow> getRows()
	{
		return rows;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}
	
	@Override
	public int getStyle()
	{
		return style;
	}

	@Override
	public Map<String, Integer> getSortColMap()
	{
		return sortColMap;
	}
}

class ComparableRow implements Comparable<ComparableRow>
{
	protected DataSetRow row;
	protected LinkedHashMap<Integer, Integer> sortColIdxMap;
	
	public ComparableRow(DataSetRow row, LinkedHashMap<Integer, Integer> sortColIdxMap)
	{
		this.row = row;
		this.sortColIdxMap = sortColIdxMap;
	}

	@Override
	public int compareTo(ComparableRow other)
	{
		int result = 0;
		for(int colIdx : sortColIdxMap.keySet())
		{
			int sortType = sortColIdxMap.get(colIdx);
			Object content = row.getCell(colIdx).getContent();
			Object oContent = other.row.getCell(colIdx).getContent();
			if(content!=null&&oContent!=null)
			{
				if(content instanceof Comparable)
				{
					if(!content.getClass().isAssignableFrom(oContent.getClass())&&!oContent.getClass().isAssignableFrom(content.getClass()))
					{
						// 类型不一致,无法进行直接比较,先进行强制转换, 再比较
						try
						{
							content = ObjectUtil.objectCast(content, oContent.getClass());
						}
						catch(Exception e)
						{
							oContent = ObjectUtil.objectCast(oContent, content.getClass());
						}
					}
					if(sortType==DataSet.SORT_TYPE_ASC)
					{
						result = ((Comparable) content).compareTo(oContent);
					}
					else
					{
						result = -1*((Comparable) content).compareTo(oContent);
					}
				}
			}
			else
			{
				if(content==null&&oContent==null) result = 0;
				else if(content!=null&&oContent==null) result = sortType==DataSet.SORT_TYPE_ASC?1:-1;
				else if(content==null&&oContent!=null) result = sortType==DataSet.SORT_TYPE_ASC?-1:1;
			}
			if(result!=0) break;
		}
		return result;
	}
	
}
