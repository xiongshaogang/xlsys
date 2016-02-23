package xlsys.base.dataset;

import java.util.HashSet;
import java.util.List;

import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ParamBean;

/**
 * 可存储数据集的接口定义类
 * @author Lewis
 *
 */
public interface IStorableDataSet extends IDataSet
{
	// 事件锁，以下锁在使用setOption方法设置时，useCount参数必须设置为true
	/**
	 * change事件锁(在数据集的内容发生变化时触发)
	 */
	public final static int OPTION_LOCK_CHANGE = 10000;
	/**
	 * toBeDeleteRow事件锁(在deleteRow和deleteAllRow中触发)
	 */
	public final static int OPTION_LOCK_TO_BE_DELETE_ROW = 10001;
	
	/**
	 * 可存储数据集的打开方法, 调用此方法后, 数据集会从数据库中加载数据
	 * @throws Exception
	 */
	public void open() throws Exception;
	
	/**
	 * 可存储数据集的保存方法, 调用后会将当前数据集中的修改同步到数据库中
	 * @return
	 * @throws Exception
	 */
	public boolean save() throws Exception;
	
	public void fireBeforeOpen(StorableDataSetEvent event);
	
	public void fireAfterOpen(StorableDataSetEvent event);
	
	public void fireBeforeSave(StorableDataSetEvent event);
	
	public void fireAfterSave(StorableDataSetEvent event);
	
	public void fireAfterSaveCommit(StorableDataSetEvent event);
	
	public void fireSaveFinally(StorableDataSetEvent event);
	
	/**
	 * 判断当前的数据集是否发生改变
	 * @return
	 */
	public boolean isChanged();

	/**
	 * 设置当前的数据集是否发生改变
	 * @param changed
	 */
	public void setChanged(boolean changed);
	
	/**
	 * 获取当前可存储数据集对应的数据库连接
	 * @return
	 */
	public IDataBase getDataBase();

	/**
	 * 获取当前可存储数据集对应的数据库表名
	 * @return
	 */
	public String getTableName();
	
	/**
	 * 获取当前可存储数据集对应的查询sql语句
	 * @return
	 */
	public String getSql();
	
	/**
	 * 获取当前可存储数据集对应的查询参数
	 * @return
	 */
	public ParamBean getSelectBean();

	/**
	 * 添加不需要存储的列名称集合
	 * @param colName
	 */
	public void addUnsaveCol(String colName);
	
	/**
	 * 获取不需要存储的列名称集合
	 * @return
	 */
	public HashSet<String> getUnSaveColNameSet();
	
	/**
	 * 判断是否有要删除的行
	 * @return
	 */
	public boolean hasToBeDeleteRow();
	
	/**
	 * 获取要删除的行列表
	 * @return
	 */
	public List<DataSetRow> getToBeDeleteRowList();
	
	/**
	 * 判断是否有发生改变的行
	 * @return
	 */
	public boolean hasChangedRow();
	
	/**
	 * 设置保存时是否启用事务处理
	 * @param saveTransaction
	 */
	public void setSaveTransaction(boolean saveTransaction);
	
	/**
	 * 获取保存时是否启用事务处理
	 * @return
	 */
	public boolean isSaveTransaction();
	
	/**
	 * 是否已经排过序
	 * @return
	 */
	public boolean isSorted();
}
