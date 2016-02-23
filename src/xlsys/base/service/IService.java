package xlsys.base.service;

public interface IService
{
	/**
	 * 返回该服务是否能处理当前任务
	 * <li> event.publisher 发布者
	 * <li> event.task 任务
	 * @param event
	 * @return
	 */
	public boolean canHandlerTask(TaskEvent event);
	
	/**
	 * 在服务能才处理当前任务时调用, 询问是否终止该任务的继续发布, 默认为终止发布. 如果继续发布的话, 服务管理器则会继续将该任务下发给下一个可以处理该任务的服务对象.
	 * <li> event.publisher 发布者
	 * <li> event.task 任务
	 * @param event
	 * @return
	 */
	public Boolean interruptPublish(TaskEvent event);
	
	/**
	 * 处理任务
	 * <li> event.publisher 发布者
	 * <li> event.task 任务
	 * <li> out -> event.success 任务是否成功
	 * <li> out -> event.ret 返回值
	 * @param event
	 */
	public void handlerTask(TaskEvent event);
}
