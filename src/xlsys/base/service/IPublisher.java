package xlsys.base.service;

/**
 * 任务发布者接口
 * @author Lewis
 *
 */
public interface IPublisher
{
	/**
	 * 任务开始前事件
	 * <li> event.service 处理该任务的service
	 * <li> event.task 任务
	 * @param event
	 * @param service
	 */
	public void beforeTaskRun(TaskEvent event);
	
	/**
	 * 任务结束后事件
	 * <li> event.service 处理该任务的service
	 * <li> event.task 任务
	 * <li> event.success 任务是否成功
	 * <li> event.ret 任务完成后的返回对象
	 * @param event
	 */
	public void afterTastRun(TaskEvent event);
}
