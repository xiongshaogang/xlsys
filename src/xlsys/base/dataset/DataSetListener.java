package xlsys.base.dataset;

import java.util.EventListener;

/**
 * 数据集监听类接口
 * @author Lewis
 *
 */
public interface DataSetListener extends EventListener
{
	/**
	 * 当该Listener加入到DataSet之后立刻调用.
	 * <li> event.getDataSet() : dataSet
	 * @param event
	 */
	public void afterAddToDataSet(DataSetEvent event);
	
	/**
	 * 当该Listener从DataSet移除之前调用.
	 * <li> event.getDataSet() : dataSet
	 * @param event
	 */
	public void beforeRemoveFromDataSet(DataSetEvent event);
	
	/**
	 * 单元格提交前事件.
	 * <li> event.getDataSet() : dataSet
	 * <li> event.oldValue : 旧值
	 * <li> event.newValue : 新值
	 * <li> event.row : 当前行
	 * <li> event.col : 当前列
	 * @param event
	 */
	public void beforePostValue(DataSetEvent event);
	
	/**
	 * 单元格提交后事件.
	 * <li> event.getDataSet() : dataSet
	 * <li> event.oldValue : 旧值
	 * <li> event.newValue : 新值
	 * <li> event.row : 当前行
	 * <li> event.col : 当前列
	 * @param event
	 */
	public void afterPostValue(DataSetEvent event);
	
	/**
	 * 行提交前事件.
	 * <li> event.getDataSet() : dataSet
	 * <li> event.row : 当前行
	 * @param event
	 */
	public void beforePostRow(DataSetEvent event);
	
	/**
	 * 行提交后事件.
	 * <li> event.getDataSet() : dataSet
	 * <li> event.row : 当前行
	 * @param event
	 */
	public void afterPostRow(DataSetEvent event);

	/**
	 * 游标改变前事件.
	 * <li> event.getDataSet() : dataSet
	 * <li> event.oldRow : 改变前行
	 * <li> event.oldCol : 改变前列
	 * <li> event.row : 改变后行
	 * <li> event.col : 改变后列
	 * @param event
	 */
	public void beforeCursorChange(DataSetEvent event);

	/**
	 * 行指针改变后事件.
	 * <li> event.getDataSet() : dataSet
	 * <li> event.oldRow : 改变前行
	 * <li> event.oldCol : 改变前列
	 * <li> event.row : 改变后行
	 * <li> event.col : 改变后列
	 * @param event
	 */
	public void afterCursorChange(DataSetEvent event);
	
	/**
	 * 行插入前事件.
	 * <li> event.row 将要插入的行
	 * @param event
	 */
	public void beforeInsertRow(DataSetEvent event);
	
	/**
	 * 行插入后事件.
	 * <li> event.row 插入的行
	 * @param event
	 */
	public void afterInsertRow(DataSetEvent event);
	
	/**
	 * 列插入前事件.
	 * <li> event.col 将要插入的列
	 * @param event
	 */
	public void beforeInsertCol(DataSetEvent event);
	
	/**
	 * 列插入后事件.
	 * <li> event.col 插入的列
	 * @param event
	 */
	public void afterInsertCol(DataSetEvent event);
	
	/**
	 * 行删除前事件.
	 * <li> event.getDataSet() : dataSet
	 * <li> event.row 将要删除的行
	 * @param event
	 */
	public void beforeRemoveRow(DataSetEvent event);
	
	/**
	 * 行删除后事件.
	 * <li> event.getDataSet() : dataSet
	 * <li> event.row 删除的行
	 * @param event
	 */
	public void afterRemoveRow(DataSetEvent event);
	
	/**
	 * 列删除前事件.
	 * <li> event.getDataSet() : dataSet
	 * <li> event.col 将要删除的行
	 * @param event
	 */
	public void beforeRemoveCol(DataSetEvent event);
	
	/**
	 * 列删除后事件.
	 * <li> event.getDataSet() : dataSet
	 * <li> event.col 删除的行
	 * @param event
	 */
	public void afterRemoveCol(DataSetEvent event);
	
	/**
	 * 行扩展事件.
	 * <li> event.getDataSet() : dataSet
	 * <li> event.beginIdxOfExpand : 要扩展的开始行数
	 * <li> event.endIdxOfExpand : 要扩展的结束行数
	 * @param event
	 */
	public void expandRows(DataSetEvent event);
	
	/**
	 * 数据集刷新前事件
	 * <li> event.getDataSet() : dataSet
	 * @param event
	 */
	public void beforeRefresh(DataSetEvent event);
	
	/**
	 * 数据集刷新后事件
	 * <li> event.getDataSet() : dataSet
	 * @param event
	 */
	public void afterRefresh(DataSetEvent event);
	
	/**
	 * 数据集自定义事件，用户可通过fireCustomTrigger来自行触发事件，并传入相应参数
	 * <li> event.customId 用户自定义事件ID
	 * <li> event.customData 用户自定义事件数据
	 * @param event
	 */
	public void customTrigger(DataSetEvent event);
	
	/**
	 * 数据集用来显示提示的回调函数
	 * <li> event.getSrcEvent() 触发源事件
	 * <li> event.logLevel 日志显示级别
	 * @param event
	 */
	public void showMessage(DataSetEvent event);
}
