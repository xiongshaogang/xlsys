package xlsys.base.dataset;

import java.util.List;

public interface ITreeDataSet extends IDataSet
{
	/**
	 * 设置树结点列的列名
	 * @param columnName
	 * @return
	 */
	public boolean setTreeColumn(String columnName);
	
	/**
	 * 设置树结点列的列名以及对应的排序方式
	 * @param columnName
	 * @param sortType
	 * @return
	 */
	public boolean setTreeColumn(String columnName, int sortType);
	
	/**
	 * 设置树结点列的列号
	 * @param columnIdx
	 * @return
	 */
	public boolean setTreeColumn(int columnIdx);
	
	/**
	 * 设置树结点列的列号以及对应的排序方式
	 * @param columnIdx
	 * @param sortType
	 * @return
	 */
	public boolean setTreeColumn(int columnIdx, int sortType);
	
	/**
	 * 设置树结点列的列号, 该方法为内部使用.
	 * @param treeColIdx
	 */
	public void setTreeColIdx(int treeColIdx);
	
	/**
	 * 获取树节点列的列名
	 * @return
	 */
	public String getTreeColumnName();
	
	/**
	 * 获取树结点列的列号
	 * @return
	 */
	public int getTreeColumn();
	
	/**
	 * 获取所有根结点行的列表
	 * @return
	 */
	public List<DataSetRow> getRootRows();
	
	/**
	 * 获取所有根结点行的行号列表
	 * @return
	 */
	public List<Integer> getRootRowNums();
	
	/**
	 * 获取指定行的直接子行列表
	 * @param row
	 * @return
	 */
	public List<DataSetRow> getChildrenRows(DataSetRow row);
	
	/**
	 * 获取指定行号的直接子行列表
	 * @param rowAt
	 * @return
	 */
	public List<DataSetRow> getChildrenRows(int rowAt);
	
	/**
	 * 获取指定行的直接子行的行号列表
	 * @param row
	 * @return
	 */
	public List<Integer> getChildrenRowNums(DataSetRow row);
	
	/**
	 * 获取指定行号的直接子行的行号列表
	 * @param rowAt
	 * @return
	 */
	public List<Integer> getChildrenRowNums(int rowAt);
	
	/**
	 * 获取指定行的所有子行列表
	 * @param row
	 * @return
	 */
	public List<DataSetRow> getAllChildrenRows(DataSetRow row);
	
	/**
	 * 获取指定行号的所有子行列表
	 * @param rowAt
	 * @return
	 */
	public List<DataSetRow> getAllChildrenRows(int rowAt);
	
	/**
	 * 获取指定行的所有子行行号列表
	 * @param row
	 * @return
	 */
	public List<Integer> getAllChildrenRowNums(DataSetRow row);

	/**
	 * 获取指定行号的所有子行行号列表
	 * @param rowAt
	 * @return
	 */
	public List<Integer> getAllChildrenRowNums(int rowAt);
	
	/**
	 * 获取指定行的父行
	 * @param row
	 * @return
	 */
	public DataSetRow getParentRow(DataSetRow row);
	
	/**
	 * 获取指定行号的父行
	 * @param rowAt
	 * @return
	 */
	public DataSetRow getParentRow(int rowAt);
	
	/**
	 * 获取指定行的父行行号
	 * @param row
	 * @return
	 */
	public int getParentRowNum(DataSetRow row);
	
	/**
	 * 获取指定行号的父行行号
	 * @param rowAt
	 * @return
	 */
	public int getParentRowNum(int rowAt);
	
	/**
	 * 获取指定行的所有父行行号列表
	 * @param row
	 * @return
	 */
	public List<Integer> getAllParent(DataSetRow row);
	
	/**
	 * 获取指定行号的所有父行行号列表
	 * @param rowAt
	 * @return
	 */
	public List<Integer> getAllParent(int rowAt);
}
