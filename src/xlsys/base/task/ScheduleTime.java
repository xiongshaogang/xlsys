package xlsys.base.task;

/**
 * 调度时间的映射实体类
 * @author Lewis
 *
 */
public class ScheduleTime
{
	// 调度模式
	/**
	 * 调度模式:每年
	 */
	public final static int MODE_EVERY_YEAR = 0;
	/**
	 * 调度模式:每月
	 */
	public final static int MODE_EVERY_MONTH = 1;
	/**
	 * 调度模式:每日
	 */
	public final static int MODE_EVERY_DAY = 2;
	/**
	 * 调度模式:每小时
	 */
	public final static int MODE_EVERY_HOUR = 3;
	/**
	 * 调度模式:每分钟
	 */
	public final static int MODE_EVERY_MINUTE = 4;
	/**
	 * 调度模式:固定频率
	 */
	public final static int MODE_FIX_RATE = 5;
	/**
	 * 调度模式:固定日期
	 */
	public final static int MODE_FIX_DATE = 6;
	/**
	 * 调度模式:每星期
	 */
	public final static int MODE_FIX_WEEK_DAY = 7;
	
	private int mode;
	
	/**
	 * 年
	 */
	public int year;
	/**
	 * 月
	 */
	public int month;
	/**
	 * 日
	 */
	public int day;
	/**
	 * 小时
	 */
	public int hour;
	/**
	 * 分钟
	 */
	public int minute;
	/**
	 * 秒
	 */
	public int second;
	/**
	 * 周期
	 */
	public long period;
	/**
	 * 星期几.数组
	 */
	public int[] weekDays;
	
	/**
	 * 构造一个调度时间对象
	 * @param mode 调度模式
	 */
	public ScheduleTime(int mode)
	{
		this.mode = mode;
		year = 0;
		month = 0;
		day = 0;
		hour = 0;
		minute = 0;
		second = 0;
		period = 0;
	}

	/**
	 * 获取调度模式
	 * @return
	 */
	public int getMode()
	{
		return mode;
	}
}
