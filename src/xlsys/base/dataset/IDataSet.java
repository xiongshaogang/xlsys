package xlsys.base.dataset;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据集接口，所有的数据集都必须实现此接口
 * @author Lewis
 *
 */
public interface IDataSet extends Serializable
{
	/**
	 * 普通的DataSet类型
	 */
	public final static int STYLE_NONE = 0;
	/**
	 * 支持延迟加载的DataSet类型
	 */
	public final static int STYLE_VIRTUAL = 1;
	
	/**
	 * 升序排列类型
	 */
	public final static int SORT_TYPE_ASC = 0;
	/**
	 * 降序排列类型
	 */
	public final static int SORT_TYPE_DESC = 1;
	
	// 事件锁，以下锁在使用setOption方法设置时，useCount参数必须设置为true
	/**
	 * postValue事件锁(由setValue触发)
	 */
	public final static int OPTION_LOCK_POST_VALUE_EVENT = 0;
	/**
	 * postRow事件锁(由postRow触发)
	 */
	public final static int OPTION_LOCK_POST_ROW_EVENT = 1;
	/**
	 * cursorChange事件锁(由gotoRow触发)
	 */
	public final static int OPTION_LOCK_CURSOR_CHANGE = 2;
	/**
	 * removeRow事件锁(由removeRow触发)
	 */
	public final static int OPTION_LOCK_REMOVE_ROW_EVENT = 3;
	/**
	 * removeColumn事件锁(由removeColumn触发)
	 */
	public final static int OPTION_LOCK_REMOVE_COL_EVENT = 4;
	
	/**
	 * 设置行数(只有当style类型包含STYLE_VIRTUAL时有效)
	 * @param rowCount
	 */
	public void setRowCount(int rowCount);
	
	/**
	 * 获取DataSet的类型
	 * @return
	 */
	public int getStyle();
	
	/**
	 * 获取每次扩展的行数(只有当style类型包含STYLE_VIRTUAL时有效)
	 * @return
	 */
	public int getExpandPerCount();

	/**
	 * 设置每次扩展时的行数(只有当style类型包含STYLE_VIRTUAL时有效)
	 * @param expandPerCount
	 */
	public void setExpandPerCount(int expandPerCount);
	
	/**
	 * 获取指定行号当页的起始行号和结束行号(只有当style类型包含STYLE_VIRTUAL时有效)
	 * @param wantRowIndex
	 * @return
	 */
	public int[] getPageRangeOfRowIndex(int wantRowIndex);
	
	/**
	 * 加载指定的行数据(只有当style类型包含STYLE_VIRTUAL时有效).
	 * 注意:该方法会加载wantRowIndex行所在的一整页数据
	 * @param wantRowIndex 想要加载的行
	 * @return
	 */
	public boolean expandToRow(int wantRowIndex);
	
	/**
	 * 判断传入的行是否为虚拟行(即还未装载数据的行)
	 * @param row
	 * @return
	 */
	public boolean isVirtualRow(DataSetRow row);
	
	/**
	 * 判断传入的行是否为虚拟行(即还未装载数据的行)
	 * @param rowIndex
	 * @return
	 */
	public boolean isVirtualRow(int rowIndex);
	
	/**
	 * 重新创建列名称与序号之间的映射关系
	 */
	public void buildColumnMap();
	
	/**
	 * 获取列序号，从0开始
	 * @param columnName
	 * @return 返回列号，找不到对应的列返回-1
	 */
	public int getColumnIndex(String columnName);
	
	/**
	 * 删除指定名称的列
	 * @param columnName
	 */
	public void removeColumn(String columnName);
	
	/**
	 * 删除指定序号的列
	 * @param colIdx
	 */
	public void removeColumn(int colIdx);
	
	/**
	 * 获取行数
	 * @return
	 */
	public int getRowCount();
	
	/**
	 * 获取列数
	 * @return
	 */
	public int getColumnCount();
	
	/**
	 * 获取当前游标所在行的行号
	 * @return
	 */
	public int getRowCursor();
	
	/**
	 * 获取当前游标所在的列号
	 * @return
	 */
	public int getColCursor();
	
	/**
	 * 获取当前的行对象
	 * @return
	 */
	public DataSetRow getCurRow();
	
	/**
	 * 获取当前的列对象
	 * @return
	 */
	public DataSetColumn getCurColumn();
	
	/**
	 * 将行游标移动第一行之前的位置
	 * @return 移动成功返回true，否则返回false
	 */
	public boolean beforeFirst();
	
	/**
	 * 将行游标移动至指定位置. 等价于gotoRow(rowIndex, true)
	 * @param rowIndex
	 * @return
	 */
	public boolean gotoRow(int rowIndex);
	
	/**
	 * 将行游标移动至指定位置.
	 * @param rowIndex 行号
	 * @param postLastEditRow 是否提交上一次编辑的行, 如果为true, 则会执行postRow方法
	 * @return
	 */
	public boolean gotoRow(int rowIndex, boolean postLastEditRow);
	
	/**
	 * 将行游标移动至指定的行. 等价于gotoRow(row, true)
	 * @param row
	 * @return
	 */
	public boolean gotoRow(DataSetRow row);
	
	/**
	 * 将行游标移动至指定的行.
	 * @param row 行对象
	 * @param postLastEditRow 是否提交上一次编辑的行, 如果为true, 则会执行postRow方法
	 * @return
	 */
	public boolean gotoRow(DataSetRow row, boolean postLastEditRow);
	
	/**
	 * 将列游标移动至指定位置.
	 * @param colIndex 列号
	 * @return
	 */
	public boolean gotoColumn(int colIndex);
	
	/**
	 * 将列游标移动至指定的列.
	 * @param columnName 列名称
	 * @return
	 */
	public boolean gotoColumn(String columnName);
	
	/**
	 * 将行列游标移动至指定的行列
	 * @param rowIndex 行位置
	 * @param colIndex 列位置
	 * @return
	 */
	public boolean gotoCell(int rowIndex, int colIndex);
	
	/**
	 * 将行列游标移动至指定的行列
	 * @param rowIndex 行位置
	 * @param colIndex 列位置
	 * @param postLastEditRow 是否提交上一次编辑的行, 如果为true, 则会执行postRow方法
	 * @return
	 */
	public boolean gotoCell(int rowIndex, int colIndex, boolean postLastEditRow);
	
	/**
	 * 将行列游标移动至指定的行列
	 * @param rowIndex 行位置
	 * @param columnName 列名称
	 * @return
	 */
	public boolean gotoCell(int rowIndex, String columnName);
	
	/**
	 * 将行列游标移动至指定的行列
	 * @param row 行对象
	 * @param colIndex 列位置
	 * @return
	 */
	public boolean gotoCell(DataSetRow row, int colIndex);
	
	/**
	 * 将行列游标移动至指定的行列
	 * @param row 行对象
	 * @param columnName 列名称
	 * @return
	 */
	public boolean gotoCell(DataSetRow row, String columnName);
	
	/**
	 * 获取指定的行
	 * @param rowIndex
	 * @return
	 */
	public DataSetRow getRow(int rowIndex);
	
	/**
	 * 获取行对象所在的行号
	 * @param row
	 * @return 返回行号，找不到对应的行返回-1
	 */
	public int getRowIndex(DataSetRow row);
	
	/**
	 * 获取指定序号的列
	 * @param columnIndex
	 * @return
	 */
	public DataSetColumn getColumn(int columnIndex);
	
	/**
	 * 按照给定的列名获取列
	 * @param colName
	 * @return
	 */
	public DataSetColumn getColumn(String colName);
	
	/**
	 * 获取指定序号的列名称
	 * @param columnIndex
	 * @return
	 */
	public String getColumnName(int columnIndex);
	
	/**
	 * 插入一个行对象到当前数据集的当前行位置.
	 * @param row 行对象
	 * @return
	 */
	public DataSetRow insertRow(DataSetRow row);
	
	/**
	 * 插入一个行对象到当前数据集的当前行位置.
	 * @param row 行对象
	 * @param clone 是否复制当前行对象中的单元格元素
	 * @return
	 */
	public DataSetRow insertRow(DataSetRow row, boolean clone);
	
	/**
	 * 插入一个行对象到当前数据集的首行位置.
	 * @param row 行对象
	 * @return
	 */
	public DataSetRow insertRowAtFirst(DataSetRow row);
	
	/**
	 * 插入一个行对象到当前数据集的首行位置.
	 * @param row 行对象
	 * @param clone 是否复制当前行对象中的单元格元素
	 * @return
	 */
	public DataSetRow insertRowAtFirst(DataSetRow row, boolean clone);
	
	/**
	 * 插入一个行对象到当前数据集的末尾位置.
	 * @param row 行对象
	 * @return
	 */
	public DataSetRow insertRowAfterLast(DataSetRow row);
	
	/**
	 * 插入一个行对象到当前数据集的末尾位置.
	 * @param row 行对象
	 * @param clone 是否复制当前行对象中的单元格元素
	 * @return
	 */
	public DataSetRow insertRowAfterLast(DataSetRow row, boolean clone);
	
	/**
	 * 插入一个行对象到当前数据集的指定位置.
	 * @param index 要插入的位置
	 * @param row 行对象
	 * @return
	 */
	public DataSetRow insertRowAt(int index, DataSetRow row);
	
	/**
	 * 插入一个行对象到当前数据集的指定位置.
	 * @param index 要插入的位置
	 * @param row 行对象
	 * @param clone 是否复制当前行对象中的单元格元素
	 * @return
	 */
	public DataSetRow insertRowAt(int index, DataSetRow row, boolean clone);
	
	/**
	 * 插入一个新行到当前的数据集的当前位置中
	 * @return
	 */
	public DataSetRow insertNewRow();
	
	/**
	 * 插入一个新行到当前的数据集的当前位置中
	 * @param virtual 是否是虚拟行
	 * @return
	 */
	public DataSetRow insertNewRow(boolean virtual);
	
	/**
	 * 插入一个新行到当前的数据集的首行位置
	 * @return
	 */
	public DataSetRow insertNewRowAtFirst();
	
	/**
	 * 插入一个新行到当前的数据集的首行位置
	 * @param virtual 是否是虚拟行
	 * @return
	 */
	public DataSetRow insertNewRowAtFirst(boolean virtual);
	
	/**
	 * 插入一个新行到当前的数据集的末尾
	 * @return
	 */
	public DataSetRow insertNewRowAfterLast();
	
	/**
	 * 插入一个新行到当前的数据集的末尾
	 * @param virtual 是否是虚拟行
	 * @return
	 */
	public DataSetRow insertNewRowAfterLast(boolean virtual);
	
	/**
	 * 插入一个新行到当前的数据集的指定位置中
	 * @param index 插入的位置
	 * @return
	 */
	public DataSetRow insertNewRowAt(int index);
	
	/**
	 * 插入一个新行到当前的数据集的指定位置中
	 * @param index 插入的位置
	 * @param virtual 是否是虚拟行
	 * @return
	 */
	public DataSetRow insertNewRowAt(int index, boolean virtual);
	
	/**
	 * 插入一个新列到当前数据集的首列位置
	 * @return
	 */
	public DataSetColumn insertNewColumnAtFirst();
	
	/**
	 * 插入一个新列到当前数据集的末尾位置
	 * @return
	 */
	public DataSetColumn insertNewColumnAfterLast();
	
	/**
	 * 插入一个新列到当前数据集的列游标所在位置
	 * @return
	 */
	public DataSetColumn insertNewColumn();

	/**
	 * 插入一个新列到当前数据集的指定位置
	 * @param index 插入的列序号
	 * @return
	 */
	public DataSetColumn insertNewColumnAt(int index);
	
	/**
	 * 删除一个行对象
	 * @param row
	 * @return
	 */
	public DataSetRow removeRow(DataSetRow row);
	
	/**
	 * 删除指定序号的行
	 * @param rowNum
	 * @return
	 */
	public DataSetRow removeRow(int rowNum);

	/**
	 * 删除所有行
	 */
	public void removeAllRow();
	
	/**
	 * 获取当前行列游标所在位置的单元格的值
	 * @return
	 */
	public Serializable getValue();
	
	/**
	 * 获取指定列对应的单元格的值
	 * @param columnName
	 * @return
	 */
	public Serializable getValue(String columnName);
	
	/**
	 * 获取指定列号对应的单元格的值
	 * @param colIndex
	 * @return
	 */
	public Serializable getValue(int colIndex);
	
	/**
	 * 获取指定行号和指定列名所对应的单元格的值
	 * @param rowIndex
	 * @param columnName
	 * @return
	 */
	public Serializable getValue(int rowIndex, String columnName);
	
	/**
	 * 获取指定行对象和指定列名所对应的单元格的值
	 * @param row
	 * @param columnName
	 * @return
	 */
	public Serializable getValue(DataSetRow row, String columnName);
	
	/**
	 * 获取指定行对象和指定列号所对应的单元格的值
	 * @param row
	 * @param colIndex
	 * @return
	 */
	public Serializable getValue(DataSetRow row, int colIndex);
	
	/**
	 * 获取指定行号和指定列号所对应的单元格的值
	 * @param rowIndex
	 * @param colIndex
	 * @return
	 */
	public Serializable getValue(int rowIndex, int colIndex);
	
	/**
	 * 设置行列游标对应的单元格的值
	 * @param value
	 * @return
	 */
	public boolean setValue(Serializable value);
	
	/**
	 * 设置指定列名所对应的单元格的值
	 * @param columnName
	 * @param value
	 * @return
	 */
	public boolean setValue(String columnName, Serializable value);
	
	/**
	 * 设置指定列号所对应的单元格的值
	 * @param colIndex
	 * @param value
	 * @return
	 */
	public boolean setValue(int colIndex, Serializable value);
	
	/**
	 * 设置指定行号和指定列名所对应的单元格的值
	 * @param rowIndex
	 * @param columnName
	 * @param value
	 * @return
	 */
	public boolean setValue(int rowIndex, String columnName, Serializable value);
	
	/**
	 * 设置指定行对象和指定列名所对应的单元格的值
	 * @param row
	 * @param columnName
	 * @param value
	 * @return
	 */
	public boolean setValue(DataSetRow row, String columnName, Serializable value);
	
	/**
	 * 设置指定行对象和指定列号所对应的单元格的值
	 * @param row
	 * @param colIndex
	 * @param value
	 * @return
	 */
	public boolean setValue(DataSetRow row, int colIndex, Serializable value);
	
	/**
	 * 设置指定行号和指定列号所对应的单元格的值
	 * @param rowIndex
	 * @param colIndex
	 * @param value
	 * @return
	 */
	public boolean setValue(int rowIndex, int colIndex, Serializable value);
	
	/**
	 * 提交指定的行
	 * @param rowIndex
	 * @return
	 */
	public boolean postRow(int rowIndex);
	
	/**
	 * 提交行
	 * @return
	 */
	public boolean postRow();
	
	/**
	 * 设置选项
	 * @param option 数据集选项
	 * @param value true为设置,false为取消
	 */
	public void setOption(int option, boolean value);
	
	/**
	 * 设置选项
	 * @param option 数据集选项
	 * @param value true为设置,false为取消
	 * @param useCount 是否使用计数
	 */
	public void setOption(int option, boolean value, boolean useCount);
	
	/**
	 * 判断当前数据集是否包含指定的选项
	 * @param option 数据集选项
	 * @return 包含返回true,否则返回false
	 */
	public boolean isOption(int option);
	
	/**
	 * 设置排序列
	 * @param colNames
	 */
	public void setSortColumn(String... colNames);
	
	/**
	 * 设置排序列和排序方式. 注意,排序列数组的长度必须和排序方式数组的长度相同
	 * @param colNames 排序列
	 * @param sortTypes 排序方式
	 */
	public void setSortColumn(String[] colNames, int[] sortTypes);
	
	/**
	 * 设置排序列和排序方式
	 * @param sortColMap key为排序列,value为排序方式
	 */
	public void setSortColumn(Map<String, Integer> sortColMap);
	
	/**
	 * 获取排序列及其对应的排序规则
	 * @return
	 */
	public Map<String, Integer> getSortColMap();
	
	/**
	 * 设置排序Map, 该方法为内部使用.
	 * @param sortColMap
	 */
	public void setSortColMap(Map<String, Integer> sortColMap);
	
	/**
	 * 排序数据集
	 */
	public void sort();
	
	/**
	 * 交换数据集中的两个行
	 */
	public void swapRow(int i, int j);
	
	/**
	 * 添加数据集监听
	 * @param dataSetListener
	 */
	public void addDataSetListener(DataSetListener dataSetListener);
	
	/**
	 * 删除数据集监听
	 * @param dataSetListener
	 */
	public void removeDataSetListener(DataSetListener dataSetListener);
	
	/**
	 * 获取当前数据集中的所有监听
	 * @return
	 */
	public Set<DataSetListener> getDataSetListeners();
	
	/**
	 * 设置事件锁. 该方法会设置所有的事件锁
	 * @param dataSetClass 要设置的事件类型所对应的IDataSet类型
	 * @param lock
	 */
	public void setEventLock(Class<? extends IDataSet> dataSetClass, boolean lock);
	
	/**
	 * 设置事件锁. 该方法会设置所有的事件锁
	 * @param lock
	 */
	public void setEventLock(boolean lock);
	
	public void fireShowMessage(DataSetEvent event);
	
	public void fireBeforePostValue(DataSetEvent event);
	
	public void fireAfterPostValue(DataSetEvent event);
	
	public void fireBeforePostRow(DataSetEvent event);
	
	public void fireAfterPostRow(DataSetEvent event);
	
	public void fireBeforeCursorChange(DataSetEvent event);

	public void fireAfterCursorChange(DataSetEvent event);
	
	public void fireCustomTrigger(DataSetEvent event);

	/**
	 * 获取数据集中的所有列对象
	 * @return
	 */
	public List<DataSetColumn> getColumns();

	/**
	 * 获取数据集的所有行对象
	 * @return
	 */
	public List<DataSetRow> getRows();

	/**
	 * 获取数据集的名称
	 * @return
	 */
	public String getName();

	/**
	 * 设置数据集的名称
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * 刷新数据集
	 */
	public void refresh();
}
