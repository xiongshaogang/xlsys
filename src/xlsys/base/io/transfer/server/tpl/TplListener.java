package xlsys.base.io.transfer.server.tpl;

public interface TplListener
{
	/**
	 * 模板填充前
	 * <li> in&out event.request 请求对象
	 * <li> in&out event.response 应答对象
	 * <li> in&out event.fillObj 模板填充对象
	 * <li> in&out event.template 模板
	 * @param event
	 */
	public void beforeTemplateFill(TplEvent event);
	
	/**
	 * 模板填充前
	 * <li> in&out event.request 请求对象
	 * <li> in&out event.response 应答对象
	 * <li> in event.fillObj 模板填充对象
	 * <li> in event.template 模板
	 * <li> in&out event.outData 填充后的输出数据
	 * @param event
	 */
	public void afterTemplateFill(TplEvent event);
	
	/**
	 * 获取跳转页面路径
	 * <li> event.dataBase 数据库连接
	 * <li> in&out event.request 请求对象
	 * <li> in&out event.response 应答对象
	 * <li> in&out event.redirectPath 要跳转到的页面路径
	 * @param event
	 */
	public void redirect(TplEvent event);
}
