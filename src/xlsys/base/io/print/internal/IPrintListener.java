package xlsys.base.io.print.internal;

public interface IPrintListener
{
	/**
	 * 开始生成前事件，可用来初始化一些环境变量
	 * @return
	 * 		返回true则继续执行，返回false则取消执行
	 */
	public boolean begin();
	
	/**
	 * 生成结束后事件，可用来做一些事后处理
	 */
	public void end();
	
	/**
	 * 用来返回信息的回调函数
	 * @param logLevel 信息日志等级
	 * @param message 信息
	 * @param e 抛出的错误，如果没有，则为null
	 */
	public void showMessage(int logLevel, String message, Throwable e);
}
