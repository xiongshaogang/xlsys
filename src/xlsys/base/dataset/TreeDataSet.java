package xlsys.base.dataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 树形数据集
 * @author Lewis
 *
 */
public class TreeDataSet extends DataSet implements ITreeDataSet
{
	private static final long serialVersionUID = 4909100073447454621L;
	
	protected Integer treeColIdx;
	
	public TreeDataSet()
	{
		super();
	}
	
	public TreeDataSet(int style)
	{
		super(style);
	}
	
	public TreeDataSet(IDataSet dataSet)
	{
		this(dataSet, IDataSet.STYLE_NONE);
	}
	
	public TreeDataSet(IDataSet dataSet, int style)
	{
		super(style);
		refDataSet(dataSet);
	}

	@Override
	public synchronized boolean setTreeColumn(String columnName)
	{
		return setTreeColumn(columnName, IDataSet.SORT_TYPE_ASC);
	}
	
	@Override
	public synchronized boolean setTreeColumn(String columnName, int sortType)
	{
		return setTreeColumn(getColumnIndex(columnName), sortType);
	}
	
	@Override
	public synchronized boolean setTreeColumn(int columnIdx)
	{
		return setTreeColumn(columnIdx, IDataSet.SORT_TYPE_ASC);
	}
	
	@Override
	public synchronized boolean setTreeColumn(int columnIdx, int sortType)
	{
		return doSetTreeColumn(this, columnIdx, sortType);
	}
	
	@Override
	public void setTreeColIdx(int treeColIdx)
	{
		this.treeColIdx = treeColIdx;
	}
	
	protected static boolean doSetTreeColumn(ITreeDataSet treeDataSet, int columnIdx, int sortType)
	{
		boolean success = false;
		if(columnIdx>=0&&columnIdx<treeDataSet.getColumnCount())
		{
			Map<String, Integer> sortColMap = new HashMap<String, Integer>();
			sortColMap.put(treeDataSet.getColumnName(columnIdx), sortType);
			treeDataSet.setSortColMap(sortColMap);
			treeDataSet.sort();
			treeDataSet.setTreeColIdx(columnIdx);
			success = true;
		}
		return success;
	}
	
	@Override
	public String getTreeColumnName()
	{
		return doGetTreeColumnName(this);
	}
	
	protected static String doGetTreeColumnName(ITreeDataSet treeDataSet)
	{
		String colName = null;
		int treeColIdx = treeDataSet.getTreeColumn();
		if(treeColIdx!=-1) colName = treeDataSet.getColumnName(treeColIdx);
		return colName;
	}
	
	@Override
	public int getTreeColumn()
	{
		return treeColIdx==null?-1:treeColIdx;
	}
	
	@Override
	public synchronized void setSortColumn(String ... colNames)
	{
		doSetSortColumn(this);
	}

	@Override
	public synchronized void setSortColumn(String[] colNames, int[] sortTypes)
	{
		doSetSortColumn(this);
	}

	@Override
	public void setSortColumn(Map<String, Integer> sortColMap)
	{
		doSetSortColumn(this);
	}
	
	protected static void doSetSortColumn(ITreeDataSet treeDataSet)
	{
		throw new UnsupportedOperationException("This method is unsupported for ITreeDataSet, try method : setTreeColumn instead.");
	}

	@Override
	public synchronized void swapRow(int i, int j)
	{
		doSwapRow(this);
	}
	
	protected static void doSwapRow(ITreeDataSet treeDataSet)
	{
		throw new UnsupportedOperationException("This method is unsupported for ITreeDataSet.");
	}

	protected static List<DataSetRow> getRowList(ITreeDataSet treeDataSet, List<Integer> rowAtList)
	{
		List<DataSetRow> rowList = null;
		if(rowAtList!=null)
		{
			rowList = new ArrayList<DataSetRow>();
			List<DataSetRow> rows = treeDataSet.getRows();
			for(int i : rowAtList)
			{
				rowList.add(rows.get(i));
			}
		}
		return rowList;
	}
	
	@Override
	public synchronized List<DataSetRow> getRootRows()
	{
		return getRowList(this, getRootRowNums());
	}
	
	@Override
	public synchronized List<Integer> getRootRowNums()
	{
		return doGetRootRowNums(this);
	}
	
	protected static List<Integer> doGetRootRowNums(ITreeDataSet treeDataSet)
	{
		List<Integer> root = null;
		Set<Integer> childrenSet = new HashSet<Integer>();
		List<DataSetRow> rows = treeDataSet.getRows();
		for(int i=0;i<rows.size();++i)
		{
			if(!childrenSet.contains(i))
			{
				if(root==null) root = new ArrayList<Integer>();
				root.add(i);
				List<Integer> children = treeDataSet.getAllChildrenRowNums(i);
				if(children!=null) childrenSet.addAll(children);
			}
		}
		return root;
	}
	
	@Override
	public synchronized List<DataSetRow> getChildrenRows(DataSetRow row)
	{
		return getRowList(this, getChildrenRowNums(row));
	}
	
	@Override
	public synchronized List<DataSetRow> getChildrenRows(int rowAt)
	{
		return getRowList(this, getChildrenRowNums(rowAt));
	}
	
	@Override
	public synchronized List<Integer> getChildrenRowNums(DataSetRow row)
	{
		return getChildrenRowNums(getRowIndex(row));
	}
	
	@Override
	public synchronized List<Integer> getChildrenRowNums(int rowAt)
	{
		return doGetChildrenRowNums(this, rowAt);
	}
	
	protected static List<Integer> doGetChildrenRowNums(ITreeDataSet treeDataSet, int rowAt)
	{
		List<Integer> children = null;
		int treeColIdx = treeDataSet.getTreeColumn();
		if(treeColIdx!=-1)
		{
			List<DataSetRow> rows = treeDataSet.getRows();
			if(rowAt>=0&&rowAt<rows.size())
			{
				DataSetRow row = rows.get(rowAt);
				String rowStr = "" + row.getCell(treeColIdx).getContent();
				String lastStr = null;
				for(int i=rowAt+1;i<rows.size();++i)
				{
					DataSetRow otherRow = rows.get(i);
					String otherRowStr = "" + otherRow.getCell(treeColIdx).getContent();
					if((lastStr==null || (lastStr!=null&&!otherRowStr.startsWith(lastStr))) && otherRowStr.startsWith(rowStr)&&otherRowStr.length()>rowStr.length())
					{
						if(children==null) children = new ArrayList<Integer>();
						children.add(i);
						lastStr = otherRowStr;
					}
					else if(otherRowStr.length()<=rowStr.length())
					{
						break;
					}
				}
			}
		}
		return children;
	}
	
	@Override
	public synchronized List<DataSetRow> getAllChildrenRows(DataSetRow row)
	{
		return getRowList(this, getAllChildrenRowNums(row));
	}
	
	@Override
	public synchronized List<DataSetRow> getAllChildrenRows(int rowAt)
	{
		return getRowList(this, getAllChildrenRowNums(rowAt));
	}
	
	@Override
	public synchronized List<Integer> getAllChildrenRowNums(DataSetRow row)
	{
		return getAllChildrenRowNums(getRowIndex(row));
	}
	
	@Override
	public synchronized List<Integer> getAllChildrenRowNums(int rowAt)
	{
		return doGetAllChildrenRowNums(this, rowAt);
	}
	
	protected static List<Integer> doGetAllChildrenRowNums(ITreeDataSet treeDataSet, int rowAt)
	{
		List<Integer> children = null;
		int treeColIdx = treeDataSet.getTreeColumn();
		if(treeColIdx!=-1)
		{
			List<DataSetRow> rows = treeDataSet.getRows();
			if(rowAt>=0&&rowAt<rows.size())
			{
				DataSetRow row = rows.get(rowAt);
				String rowStr = "" + row.getCell(treeColIdx).getContent();
				for(int i=rowAt+1;i<rows.size();++i)
				{
					DataSetRow otherRow = rows.get(i);
					String otherRowStr = "" + otherRow.getCell(treeColIdx).getContent();
					if(otherRowStr.startsWith(rowStr)&&otherRowStr.length()>rowStr.length())
					{
						if(children==null) children = new ArrayList<Integer>();
						children.add(i);
					}
					else
					{
						break;
					}
				}
			}
		}
		return children;
	}
	
	@Override
	public synchronized DataSetRow getParentRow(DataSetRow row)
	{
		return getParentRow(getRowIndex(row));
	}
	
	@Override
	public synchronized DataSetRow getParentRow(int rowAt)
	{
		return doGetParentRow(this, rowAt);
	}
	
	protected static DataSetRow doGetParentRow(ITreeDataSet treeDataSet, int rowAt)
	{
		DataSetRow parentRow = null;
		int parentIdx = treeDataSet.getParentRowNum(rowAt);
		if(parentIdx!=-1) parentRow = treeDataSet.getRow(parentIdx);
		return parentRow;
	}
	
	@Override
	public synchronized int getParentRowNum(DataSetRow row)
	{
		return getParentRowNum(getRowIndex(row));
	}
	
	@Override
	public synchronized int getParentRowNum(int rowAt)
	{
		return doGetParentRowNum(this, rowAt);
	}
	
	protected static int doGetParentRowNum(ITreeDataSet treeDataSet, int rowAt)
	{
		int parentIdx = -1;
		int treeColIdx = treeDataSet.getTreeColumn();
		if(treeColIdx!=-1)
		{
			List<DataSetRow> rows = treeDataSet.getRows();
			if(rowAt>=0&&rowAt<rows.size())
			{
				DataSetRow row = rows.get(rowAt);
				String rowStr = "" + row.getCell(treeColIdx).getContent();
				for(int i=rowAt-1;i>=0;--i)
				{
					DataSetRow otherRow = rows.get(i);
					String otherRowStr = "" + otherRow.getCell(treeColIdx).getContent();
					if(rowStr.startsWith(otherRowStr)&&otherRowStr.length()<rowStr.length())
					{
						parentIdx = i;
						break;
					}
				}
			}
		}
		return parentIdx;
	}
	
	@Override
	public synchronized List<Integer> getAllParent(DataSetRow row)
	{
		return getAllParent(getRowIndex(row));
	}
	
	@Override
	public synchronized List<Integer> getAllParent(int rowAt)
	{
		return doGetAllParent(this, rowAt);
	}
	
	protected static List<Integer> doGetAllParent(ITreeDataSet treeDataSet, int rowAt)
	{
		List<Integer> parentList = null;
		int treeColIdx = treeDataSet.getTreeColumn();
		if(treeColIdx!=-1)
		{
			List<DataSetRow> rows = treeDataSet.getRows();
			if(rowAt>=0&&rowAt<rows.size())
			{
				DataSetRow row = rows.get(rowAt);
				String rowStr = "" + row.getCell(treeColIdx).getContent();
				for(int i=rowAt-1;i>=0;--i)
				{
					DataSetRow otherRow = rows.get(i);
					String otherRowStr = "" + otherRow.getCell(treeColIdx).getContent();
					if(rowStr.startsWith(otherRowStr)&&otherRowStr.length()<rowStr.length())
					{
						if(parentList==null) parentList = new ArrayList<Integer>();
						parentList.add(i);
					}
				}
			}
		}
		return parentList;
	}
	
	@Override
	public synchronized DataSetRow removeRow(int rowIndex)
	{
		// 删除子行
		List<Integer> children = getChildrenRowNums(rowIndex);
		if(children!=null)
		{
			for(int i=children.size()-1;i>=0;--i) removeRow(children.get(i));
		}
		return super.removeRow(rowIndex);
	}
}
