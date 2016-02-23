package xlsys.base.dataset;

/**
 * 可存储数据集监听接口
 * @author Lewis
 *
 */
public interface StorableDataSetListener extends DataSetListener
{
	/**
	 * 可存储数据集打开前事件.
	 * <li> event.getDataSet() : 数据集
	 * <li> event.selectBean : 将要使用的查询对象
	 * <li> event.refDataSet : 将要引用的DataSet对象
	 * @param event
	 * @return
	 */
	public void beforeOpen(StorableDataSetEvent event);
	
	/**
	 * 可存储数据集打开后事件.
	 * <li> event.getDataSet() : 数据集
	 * @param event
	 * @return
	 */
	public void afterOpen(StorableDataSetEvent event);
	
	/**
	 * 可存储数据集存盘前事件.
	 * <li> event.getDataSet() : 数据集
	 * @param event
	 * @return
	 */
	public void beforeSave(StorableDataSetEvent event);
	
	/**
	 * 可存储数据集存盘后事件(存盘事务中)
	 * <li> event.getDataSet() : 数据集
	 * @param event
	 * @return
	 */
	public void afterSave(StorableDataSetEvent event);
	
	/**
	 * 可存储数据集存盘提交后事件(不包含在存盘事务中)
	 * <li> event.getDataSet() : 数据集
	 * @param event
	 * @return
	 */
	public void afterSaveCommit(StorableDataSetEvent event);
	
	/**
	 * 可存储数据集存盘最终事件(不包含在存盘事务中)
	 * <li> event.getDataSet() : 数据集
	 * @param event
	 * @return
	 */
	public void saveFinally(StorableDataSetEvent event);
}
