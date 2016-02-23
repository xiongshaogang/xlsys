package xlsys.base.task;

/**
 * 可进度化的接口
 * @author Lewis
 *
 */
public interface IProgressable
{
	public static final int TYPE_COMMON = 0;
	public static final int TYPE_INDETERMINATE = 1;
	
	/**
	 * 显示进度条
	 */
	public void showProgress();
	
	/**
	 * 关闭进度条
	 */
	public void closeProgress();
}
