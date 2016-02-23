package xlsys.base.io.transfer.server.extra;

public interface ExtraCmdListener
{
	/**
	 * 处理post请求
	 * <li> event.dataBase 数据库连接
	 * <li> in&out event.request 请求对象
	 * <li> in&out event.response 应答对象
	 * @param event
	 */
	public void doPost(ExtraCmdEvent event);
	
	/**
	 * 获取跳转页面路径
	 * <li> event.dataBase 数据库连接
	 * <li> in&out event.request 请求对象
	 * <li> in&out event.response 应答对象
	 * <li> in&out event.dispatchPath 要跳转到的页面路径
	 * @param event
	 */
	public void dispatch(ExtraCmdEvent event);
}
