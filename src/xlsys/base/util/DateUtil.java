package xlsys.base.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期工具类
 * @author Lewis
 *
 */
public class DateUtil
{
	@Deprecated
	public final static int PINPOINT_TO_YEAR = 0;
	@Deprecated
	public final static int PINPOINT_TO_MONTH = 1;
	@Deprecated
	public final static int PINPOINT_TO_DATE = 2;
	@Deprecated
	public final static int PINPOINT_TO_HOUR = 3;
	@Deprecated
	public final static int PINPOINT_TO_MINUTE = 4;
	@Deprecated
	public final static int PINPOINT_TO_SECOND = 5;
	
	public static Date getInternetTime_zh() throws IOException
	{
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8")); // 时区设置
		URL url = new URL("http://www.bjtime.cn");
		URLConnection con = url.openConnection();
		con.connect();
		long time = con.getDate();
		return new Date(time);
	}
	
	/**
	 * 获取指定年月的当月最大天数
	 * @param yearMonth
	 * 		like "201306"
	 * @return
	 * 		指定月的最大天数
	 */
	public static String getMaxDayByMonth(String yearMonth)
	{
		// int maxDate = 0;
		int year = Integer.parseInt(yearMonth.substring(0, 4));
		int month = Integer.parseInt(yearMonth.substring(4, 6))-1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		return ""+calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		/*boolean exit = false;
		while(!exit)
		{
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			int date = calendar.get(Calendar.DAY_OF_MONTH);
			if(date>maxDate)
			{
				maxDate = date;
			}
			else
			{
				exit = true;
			}
		}		
		return ""+maxDate;*/
	}
	
	/**
	 * 增加指定日期的特定区域值,注意：该方法会重置传入的date值
	 * @param date
	 * @param field 参见{@link Calendar}中的静态常量
	 * @param value 需要加的值
	 * @return
	 */
	public static Date addTime(Date date, int field, int value)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, value);
		date.setTime(calendar.getTimeInMillis());
		return date;
	}
	
	/**
	 * 将指定区域的值归零(或最小)
	 * @param date
	 * @param field
	 */
	public static void initTime(Date date, int field)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch(field)
		{
			case Calendar.DAY_OF_MONTH : 
			{
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				break;
			}
			default : 
			{
				calendar.set(field, 0);
				break;
			}
		}
		date.setTime(calendar.getTimeInMillis());
	}
	
	/**
	 * 将日期按照指定区域之后的所有区域(包含指定区域)全部归零
	 * @param date
	 * @param field
	 */
	public static void trunc(Date date, int field)
	{
		switch(field)
		{
			case Calendar.YEAR :
			{
				initTime(date,Calendar.MONTH);
				initTime(date,Calendar.DAY_OF_MONTH);
				initTime(date,Calendar.HOUR_OF_DAY);
				initTime(date,Calendar.MINUTE);
				initTime(date,Calendar.SECOND);
				break;
			}
			case Calendar.MONTH :
			{
				initTime(date,Calendar.DAY_OF_MONTH);
				initTime(date,Calendar.HOUR_OF_DAY);
				initTime(date,Calendar.MINUTE);
				initTime(date,Calendar.SECOND);
				break;
			}
			case Calendar.DAY_OF_MONTH :
			{
				initTime(date,Calendar.HOUR_OF_DAY);
				initTime(date,Calendar.MINUTE);
				initTime(date,Calendar.SECOND);
				break;
			}
			case Calendar.HOUR :
			{
				initTime(date,Calendar.MINUTE);
				initTime(date,Calendar.SECOND);
				break;
			}
			case Calendar.MINUTE :
			{
				initTime(date,Calendar.SECOND);
				break;
			}
		}
	}
	
	/**
	 * 根据传入的参数返回一个日期所代表的long型整数
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static long getDateTime(int year, int month, int day, int hour, int minute, int second)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, minute, second);
		return calendar.getTimeInMillis();
	}
	
	@Deprecated
	public static String getDate(long millis, int pinpoint, String split)
	{
		String date = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		String year = ""+calendar.get(Calendar.YEAR);
		String month = calendar.get(Calendar.MONTH)+1+"";
		String day = ""+calendar.get(Calendar.DAY_OF_MONTH);
		String hour = ""+calendar.get(Calendar.HOUR_OF_DAY);
		String minute = ""+calendar.get(Calendar.MINUTE);
		String second = ""+calendar.get(Calendar.SECOND);
		if(month.length()==1)
		{
			month = "0"+month;
		}
		if(day.length()==1)
		{
			day = "0"+day;
		}
		if(hour.length()==1)
		{
			hour = "0"+hour;
		}
		if(minute.length()==1)
		{
			minute = "0"+minute;
		}
		if(second.length()==1)
		{
			second = "0"+second;
		}
		if(pinpoint==DateUtil.PINPOINT_TO_YEAR)
		{
			date = year;
		}
		else if(pinpoint==DateUtil.PINPOINT_TO_MONTH)
		{
			date = year+split+month;
		}
		else if(pinpoint==DateUtil.PINPOINT_TO_DATE)
		{
			date = year+split+month+split+day;
		}
		else if(pinpoint==DateUtil.PINPOINT_TO_HOUR)
		{
			date = year+split+month+split+day+split+hour;
		}
		else if(pinpoint==DateUtil.PINPOINT_TO_MINUTE)
		{
			date = year+split+month+split+day+split+hour+split+minute;
		}
		else if(pinpoint==DateUtil.PINPOINT_TO_SECOND)
		{
			date = year+split+month+split+day+split+hour+split+minute+split+second;
		}
		return date;
	}
	
	/**
	 * 获取DBF中的日期
	 * @param str
	 * @return
	 */
	public static String getDBFDateTime(String str)
	{
		byte[] b = str.getBytes();
		byte[] b1 = new byte[4];
		int i = 0;
		for (; i < b.length; i++)
		{
			b1[i] = b[i];
		}
		for (; i < b1.length; i++)
		{
			b1[i] = 0;
		}
		int days = (int) ((((b1[0] & 0xff) | (b1[1] << 8)) | (b1[2] << 0x10)) | (b1[3] << 0x18));
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(0);
		c.add(Calendar.DAY_OF_YEAR, days-2440588);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}
	
	/**
	 * 按照指定的格式解析字符串,获取对应的日期对象
	 * @param str 日期字符串
	 * @param format 日期格式
	 * @return 日期对象
	 * @throws ParseException
	 */
	public static Date getDate(String str, String format) throws ParseException
	{
		if(format==null)
		{
			if(str.indexOf('-')!=-1)
			{
				format = "yyyy-MM-dd";
			}
			else if(str.indexOf('.')!=-1)
			{
				format = "yyyy.MM.dd";
			}
			else if(str.indexOf('/')!=-1)
			{
				format = "yyyy/MM/dd";
			}
			else
			{
				format = "yyyyMMdd";
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	/**
	 * 按照给定的格式返回日期字符串
	 * @param date 日期
	 * @param format 日期格式
	 * @return
	 */
	public static String getDateString(Date date, String format)
	{
		if(format==null)
		{
			format = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 根据给定的语言环境格式化一个日期字符串
	 * @param date : 要格式化的日期
	 * @param style : 格式化模式，包含以下几种模式：DateFormat.DEFAULT;DateFormat.FULL;DateFormat.LONG;DateFormat.MEDIUM;DateFormat.SHORT
	 * @param language :  语言参数是一个有效的 ISO 语言代码。这些代码是由 ISO-639 定义的小写两字母代码。在许多网站上都可以找到这些代码的完整列表，如：http://www.loc.gov/standards/iso639-2/php/English_list.php。 
	 * @return
	 */
	public static String getDateStringByLanguage(Date date, int style, String language)
	{
		DateFormat df = DateFormat.getDateInstance(style, new Locale(language));
		return df.format(date);
	}
	
	/**
	 * 根据给定的语言环境格式化一个时间字符串
	 * @param date : 要格式化的日期
	 * @param style : 格式化模式，包含以下几种模式：DateFormat.DEFAULT;DateFormat.FULL;DateFormat.LONG;DateFormat.MEDIUM;DateFormat.SHORT
	 * @param language :  语言参数是一个有效的 ISO 语言代码。这些代码是由 ISO-639 定义的小写两字母代码。在许多网站上都可以找到这些代码的完整列表，如：http://www.loc.gov/standards/iso639-2/php/English_list.php。 
	 * @return
	 */
	public static String getTimeStringByLanguage(Date date, int style, String language)
	{
		DateFormat df = DateFormat.getTimeInstance(style, new Locale(language));
		return df.format(date);
	}
	
	/**
	 * 根据给定的语言环境格式化一个日期时间字符串
	 * @param date : 要格式化的日期
	 * @param dateStyle : 格式化模式，包含以下几种模式：DateFormat.DEFAULT;DateFormat.FULL;DateFormat.LONG;DateFormat.MEDIUM;DateFormat.SHORT
	 * @param timeStyle : 格式化模式，包含以下几种模式：DateFormat.DEFAULT;DateFormat.FULL;DateFormat.LONG;DateFormat.MEDIUM;DateFormat.SHORT
	 * @param language :  语言参数是一个有效的 ISO 语言代码。这些代码是由 ISO-639 定义的小写两字母代码。在许多网站上都可以找到这些代码的完整列表，如：http://www.loc.gov/standards/iso639-2/php/English_list.php。 
	 * @return
	 */
	public static String getDateTimeStringByLanguage(Date date, int dateStyle, int timeStyle, String language)
	{
		DateFormat df = DateFormat.getDateTimeInstance(dateStyle, timeStyle, new Locale(language));
		return df.format(date);
	}
	
	/**
	 * 获取日期中的年
	 * @param date
	 * @return
	 */
	public static int getYear(Date date)
	{
		return getDateField(date, Calendar.YEAR);
	}
	
	/**
	 * 获取日期中的月.
	 * 注意month是从0开始的，即1月份返回0，2月份返回1
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date)
	{
		return getDateField(date, Calendar.MONTH);
	}
	
	/**
	 * 获取日期中的日
	 * @param date
	 * @return
	 */
	public static int getDay(Date date)
	{
		return getDateField(date, Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取日期中的小时
	 * @param date
	 * @return
	 */
	public static int getHour(Date date)
	{
		return getDateField(date, Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取日期中的分钟
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date)
	{
		return getDateField(date, Calendar.MINUTE);
	}
	
	/**
	 * 获取日期中的秒
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date)
	{
		return getDateField(date, Calendar.SECOND);
	}
	
	/**
	 * 获取日期是当前月的第几周
	 * @param date
	 * @return
	 */
	public static int getWeekOfMonth(Date date)
	{
		return getDateField(date, Calendar.WEEK_OF_MONTH);
	}
	
	/**
	 * 获取日期是当前年的第几周
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date)
	{
		return getDateField(date, Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 获取日期中指定的区域值
	 * @param date 日期对象
	 * @param field 指定的日期属性. 详见{@link Calendar}
	 * @return
	 */
	public static int getDateField(Date date, int field)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(field);
	}
	
	/**
	 *  从本地时间的1970-1-1 0:0:0到time的毫秒数来获取Date对象(非JDK中的格里高利历的1970-1-1 0:0:0时间)
	 * @param time
	 * @return
	 */
	public static Date getDateFromFixTime(long time)
	{
		Calendar c = Calendar.getInstance();
		// 减去时区偏移量
		time -= (c.get(Calendar.ZONE_OFFSET) + c.get(Calendar.DST_OFFSET));
		return new Date(time);
	}
	
	public static long getFixTimeFromDate(Date date)
	{
		Calendar c = Calendar.getInstance();
		// 加上时区偏移量
		return date.getTime() + (c.get(Calendar.ZONE_OFFSET) + c.get(Calendar.DST_OFFSET));
	}
	
	public static void main(String[] args) throws IOException
	{
		Date date = getInternetTime_zh();
		System.out.println(date);
		System.out.println(getDateTimeStringByLanguage(date, DateFormat.LONG, DateFormat.LONG, "zh"));
	}
}
