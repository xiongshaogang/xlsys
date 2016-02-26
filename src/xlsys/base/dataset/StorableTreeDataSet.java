package xlsys.base.dataset;

import java.util.List;
import java.util.Map;

import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ParamBean;

/**
 * 可存储树形数据集类
 * @author Lewis
 *
 */
public class StorableTreeDataSet extends StorableDataSet implements ITreeDataSet
{
	private static final long serialVersionUID = -3668321342835593829L;
	
	protected Integer treeColIdx;
	
	public StorableTreeDataSet(IStorableDataSet sds)
	{
		this(sds, sds.getStyle());
	}

	public StorableTreeDataSet(IStorableDataSet sds, int style)
	{
		super(sds.getDataBase(), sds.getSelectBean(), sds.getTableName(), style);
		refDataSet(sds);
	}

	public StorableTreeDataSet(IDataBase dataBase, String tableName) throws Exception
	{
		super(dataBase, tableName);
	}
	
	public StorableTreeDataSet(IDataBase dataBase, String tableName, int style) throws Exception
	{
		super(dataBase, tableName, style);
	}
	
	public StorableTreeDataSet(IDataBase dataBase, String selectSql, String tableName)
	{
		super(dataBase, selectSql, tableName);
	}
	
	public StorableTreeDataSet(IDataBase dataBase, String selectSql, String tableName, int style)
	{
		super(dataBase, selectSql, tableName, style);
	}
	
	public StorableTreeDataSet(IDataBase dataBase, ParamBean selectBean, String tableName)
	{
		super(dataBase, selectBean, tableName);
	}
	
	public StorableTreeDataSet(IDataBase dataBase, ParamBean selectBean, String tableName, int style)
	{
		super(dataBase, selectBean, tableName, style);
	}

	@Override
	public synchronized void open() throws Exception
	{
		super.open();
		if(getTreeColumn()!=-1)
		{
			sorted = false;
			sort();
		}
	}

	@Override
	public synchronized boolean setTreeColumn(String columnName)
	{
		return setTreeColumn(getColumnIndex(columnName));
	}
	
	@Override
	public synchronized boolean setTreeColumn(int columnIdx)
	{
		return TreeDataSet.doSetTreeColumn(this, columnIdx);
	}
	
	@Override
	public void setTreeColIdx(int treeColIdx)
	{
		this.treeColIdx = treeColIdx;
	}
	
	@Override
	public String getTreeColumnName()
	{
		return TreeDataSet.doGetTreeColumnName(this);
	}
	
	@Override
	public int getTreeColumn()
	{
		return treeColIdx==null?-1:treeColIdx;
	}
	
	@Override
	public synchronized void setSortColumn(String ... colNames)
	{
		TreeDataSet.doSetSortColumn(this);
	}

	@Override
	public synchronized void setSortColumn(String[] colNames, int[] sortTypes)
	{
		TreeDataSet.doSetSortColumn(this);
	}

	@Override
	public void setSortColumn(Map<String, Integer> sortColMap)
	{
		TreeDataSet.doSetSortColumn(this);
	}

	@Override
	public synchronized void swapRow(int i, int j)
	{
		TreeDataSet.doSwapRow(this);
	}
	
	@Override
	public synchronized List<DataSetRow> getRootRows()
	{
		return TreeDataSet.getRowList(this, getRootRowNums());
	}
	
	@Override
	public synchronized List<Integer> getRootRowNums()
	{
		return TreeDataSet.doGetRootRowNums(this);
	}
	
	@Override
	public synchronized List<DataSetRow> getChildrenRows(DataSetRow row)
	{
		return TreeDataSet.getRowList(this, getChildrenRowNums(row));
	}
	
	@Override
	public synchronized List<DataSetRow> getChildrenRows(int rowAt)
	{
		return TreeDataSet.getRowList(this, getChildrenRowNums(rowAt));
	}
	
	@Override
	public synchronized List<Integer> getChildrenRowNums(DataSetRow row)
	{
		return getChildrenRowNums(getRowIndex(row));
	}
	
	@Override
	public synchronized List<Integer> getChildrenRowNums(int rowAt)
	{
		return TreeDataSet.doGetChildrenRowNums(this, rowAt);
	}
	
	@Override
	public synchronized List<DataSetRow> getAllChildrenRows(DataSetRow row)
	{
		return TreeDataSet.getRowList(this, getAllChildrenRowNums(row));
	}
	
	@Override
	public synchronized List<DataSetRow> getAllChildrenRows(int rowAt)
	{
		return TreeDataSet.getRowList(this, getAllChildrenRowNums(rowAt));
	}
	
	@Override
	public synchronized List<Integer> getAllChildrenRowNums(DataSetRow row)
	{
		return getAllChildrenRowNums(getRowIndex(row));
	}
	
	@Override
	public synchronized List<Integer> getAllChildrenRowNums(int rowAt)
	{
		return TreeDataSet.doGetAllChildrenRowNums(this, rowAt);
	}
	
	@Override
	public synchronized DataSetRow getParentRow(DataSetRow row)
	{
		return getParentRow(getRowIndex(row));
	}
	
	@Override
	public synchronized DataSetRow getParentRow(int rowAt)
	{
		return TreeDataSet.doGetParentRow(this, rowAt);
	}
	
	@Override
	public synchronized int getParentRowNum(DataSetRow row)
	{
		return getParentRowNum(getRowIndex(row));
	}
	
	@Override
	public synchronized int getParentRowNum(int rowAt)
	{
		return TreeDataSet.doGetParentRowNum(this, rowAt);
	}
	
	@Override
	public synchronized List<Integer> getAllParent(DataSetRow row)
	{
		return getAllParent(getRowIndex(row));
	}
	
	@Override
	public synchronized List<Integer> getAllParent(int rowAt)
	{
		return TreeDataSet.doGetAllParent(this, rowAt);
	}
}
