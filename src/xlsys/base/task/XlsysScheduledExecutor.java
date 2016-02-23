package xlsys.base.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.dom4j.DocumentException;

import xlsys.base.cfg.BaseConfig;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.thread.XlsysThreadPool;
import xlsys.base.util.ReflectUtil;

/**
 * 系统任务调度器
 * @author Lewis
 *
 */
public class XlsysScheduledExecutor
{
	private static XlsysScheduledExecutor xlsysScheduledExecutor;
	
	private Runnable scheduledTimerTask;
	private ScheduledFuture<?> scheduledFuture;
	private ScheduledExecutorService service;
	private Map<Integer, XlsysTask> taskMap;
	private Map<Integer, List<ScheduledFuture<?>>> sfMap;
	
	private XlsysScheduledExecutor(int corePoolSize)
	{
		service = Executors.newScheduledThreadPool(corePoolSize);
		taskMap = new HashMap<Integer, XlsysTask>();
		sfMap = new HashMap<Integer, List<ScheduledFuture<?>>>();
		scheduledTimerTask = new ScheduledTimerTask(this);
		schedule();
	}
	
	protected Map<Integer, XlsysTask> getTaskMap()
	{
		return taskMap;
	}
	
	/**
	 * 开始调度
	 * @throws Exception
	 */
	public static void startSchedule() throws Exception
	{
		XlsysScheduledExecutor.getInstance();
	}
	
	/**
	 * 停止调度
	 */
	public static void shutdown()
	{
		shutdown(false);
	}
	
	/**
	 * 停止调度
	 * @param interrupt 是否中断已经在运行的调度
	 */
	public static synchronized void shutdown(boolean interrupt)
	{
		if(xlsysScheduledExecutor!=null)
		{
			if(xlsysScheduledExecutor.scheduledFuture!=null)
			{
				xlsysScheduledExecutor.scheduledFuture.cancel(interrupt);
				xlsysScheduledExecutor.scheduledFuture = null;
			}
			for(XlsysTask task : xlsysScheduledExecutor.taskMap.values())
			{
				xlsysScheduledExecutor.removeTask(task, interrupt);
			}
			if(interrupt) xlsysScheduledExecutor.service.shutdownNow();
			else xlsysScheduledExecutor.service.shutdown();
			xlsysScheduledExecutor = null;
		}
	}

	/**
	 * 获取一个调度执行对象实例
	 * @return
	 * @throws Exception
	 */
	public static synchronized XlsysScheduledExecutor getInstance() throws Exception
	{
		if(xlsysScheduledExecutor==null)
		{
			XmlModel taskCfgXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.TASK_CONFIG);
			if(taskCfgXm==null) return null;
			XmlModel taskThreadPoolXm = taskCfgXm.getChild("TaskThreadPool");
			int corePoolSize = Integer.parseInt(taskThreadPoolXm.getChild("corePoolSize").getText());
			xlsysScheduledExecutor = new XlsysScheduledExecutor(corePoolSize);
			List<XmlModel> taskXmList = taskCfgXm.getChilds("Task");
			for(XmlModel taskXm : taskXmList)
			{
				int id = Integer.parseInt(taskXm.getChild("id").getText());
				String name = taskXm.getChild("name").getText();
				Object obj = ReflectUtil.getInstanceFromXm(taskXm, "runnableClass", "param", "field");
				if(obj instanceof Runnable)
				{
					XlsysTask task = new XlsysTask(id, name, (Runnable)obj);
					List<XmlModel> scheduleTimeXmList = taskXm.getChilds("ScheduleTime");
					for(XmlModel scheduleTimeXm : scheduleTimeXmList)
					{
						int mode = Integer.parseInt(scheduleTimeXm.getChild("mode").getText());
						int year = Integer.parseInt(scheduleTimeXm.getChild("year").getText());
						int month = Integer.parseInt(scheduleTimeXm.getChild("month").getText());
						int day = Integer.parseInt(scheduleTimeXm.getChild("day").getText());
						int hour = Integer.parseInt(scheduleTimeXm.getChild("hour").getText());
						int minute = Integer.parseInt(scheduleTimeXm.getChild("minute").getText());
						int second = Integer.parseInt(scheduleTimeXm.getChild("second").getText());
						long period = Long.parseLong(scheduleTimeXm.getChild("period").getText());
						String weekDayStr = scheduleTimeXm.getChild("weekDays").getText();
						ScheduleTime st = new ScheduleTime(mode);
						st.year = year;
						st.month = month;
						st.day = day;
						st.hour = hour;
						st.minute = minute;
						st.second = second;
						st.period = period;
						if(weekDayStr!=null&&weekDayStr.length()>0)
						{
							String[] weekDayStrArr = weekDayStr.split(",");
							int[] weekDays = new int[weekDayStrArr.length];
							for(int i=0;i<weekDayStrArr.length;i++)
							{
								weekDays[i] = Integer.parseInt(weekDayStrArr[i]);
							}
							st.weekDays = weekDays;
						}
						task.addScheduleTime(st);
					}
					xlsysScheduledExecutor.addTask(task);
				}
			}
		}
		return xlsysScheduledExecutor;
	}
	
	/**
	 * 添加任务
	 * @param task 任务
	 */
	public void addTask(XlsysTask task)
	{
		taskMap.put(task.getId(), task);
		scheduleFixTime(task);
	}
	
	/**
	 * 移除任务
	 * @param taskId 任务ID
	 */
	public void removeTask(int taskId)
	{
		removeTask(taskId, false);
	}
	
	/**
	 * 移除任务
	 * @param taskId 任务ID
	 * @param interrupt 如果任务正在执行,是否中断其执行
	 */
	public void removeTask(int taskId, boolean interrupt)
	{
		if(taskMap.containsKey(taskId))
		{
			removeTask(taskMap.get(taskId), interrupt);
		}
	}
	
	/**
	 * 移除任务
	 * @param task 任务
	 */
	public void removeTask(XlsysTask task)
	{
		removeTask(task, false);
	}
	
	/**
	 * 移除任务
	 * @param task 任务
	 * @param interrupt 如果任务正在执行,是否中断其执行
	 */
	public void removeTask(XlsysTask task, boolean interrupt)
	{
		if(taskMap.containsKey(task.getId()))
		{
			taskMap.remove(task.getId());
			List<ScheduledFuture<?>> sfList = sfMap.get(task.getId());
			if(sfList!=null)
			{
				for(ScheduledFuture<?> sf : sfList)
				{
					sf.cancel(interrupt);
				}
			}
			sfMap.remove(task.getId());
		}
	}
	
	private void schedule()
	{
		// 先将按一定间隔触发的任务或指定日期时间触发的任务启动,这里直接使用ScheduledExecutorService来执行
		for(XlsysTask task : taskMap.values())
		{
			scheduleFixTime(task);
		}
		// 处理其余间隔任务,使用自定义的线程池来运行,对时间的检测则放入每秒运行一次的定时任务中来检测
		scheduledFuture = service.scheduleAtFixedRate(scheduledTimerTask, 0, 1, TimeUnit.SECONDS);
	}
	
	private void scheduleFixTime(XlsysTask task)
	{
		Calendar calendar = Calendar.getInstance();
		List<ScheduleTime> stList = task.getStList();
		Runnable runnable = task.getTask();
		for(ScheduleTime st : stList)
		{
			if(st.getMode()==ScheduleTime.MODE_FIX_RATE)
			{
				ScheduledFuture<?> sf = service.scheduleAtFixedRate(runnable, st.period, st.period, TimeUnit.SECONDS);
				List<ScheduledFuture<?>> sfList = null;
				if(sfMap.containsKey(task.getId()))
				{
					sfList = sfMap.get(task.getId());
				}
				else
				{
					sfList = new ArrayList<ScheduledFuture<?>>();
					sfMap.put(task.getId(), sfList);
				}
				sfList.add(sf);
			}
			else if(st.getMode()==ScheduleTime.MODE_FIX_DATE)
			{
				calendar.set(st.year, st.month, st.day, st.hour, st.minute, st.second);
				service.schedule(runnable, calendar.getTime().getTime()-System.currentTimeMillis(), TimeUnit.MILLISECONDS);
			}
		}
	}
}

class ScheduledTimerTask implements Runnable
{
	private XlsysScheduledExecutor xlsysScheduledExecutor;
	
	public ScheduledTimerTask(XlsysScheduledExecutor xlsysScheduledExecutor)
	{
		this.xlsysScheduledExecutor = xlsysScheduledExecutor;
	}
	
	@Override
	public void run()
	{
		Calendar calendar = Calendar.getInstance();
		XlsysThreadPool xtp = null;;
		try
		{
			xtp = XlsysThreadPool.getInstance();
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
		if(xtp!=null)
		{
			Map<Integer, XlsysTask> taskMap = xlsysScheduledExecutor.getTaskMap();
			for(XlsysTask task : taskMap.values())
			{
				List<ScheduleTime> stList = task.getStList();
				Runnable runnable = task.getTask();
				for(ScheduleTime st : stList)
				{
					boolean execute = false;
					if(st.getMode()==ScheduleTime.MODE_EVERY_YEAR &&calendar.get(Calendar.MONTH)==st.month&&calendar.get(Calendar.DAY_OF_MONTH)==st.day&&calendar.get(Calendar.HOUR_OF_DAY)==st.hour&&calendar.get(Calendar.MINUTE)==st.minute&&calendar.get(Calendar.SECOND)==st.second)
					{
						execute = true;
					}
					else if(st.getMode()==ScheduleTime.MODE_EVERY_MONTH &&calendar.get(Calendar.DAY_OF_MONTH)==st.day&&calendar.get(Calendar.HOUR_OF_DAY)==st.hour&&calendar.get(Calendar.MINUTE)==st.minute&&calendar.get(Calendar.SECOND)==st.second)
					{
						execute = true;
					}
					else if(st.getMode()==ScheduleTime.MODE_EVERY_DAY &&calendar.get(Calendar.HOUR_OF_DAY)==st.hour&&calendar.get(Calendar.MINUTE)==st.minute&&calendar.get(Calendar.SECOND)==st.second)
					{
						execute = true;
					}
					else if(st.getMode()==ScheduleTime.MODE_EVERY_HOUR &&calendar.get(Calendar.MINUTE)==st.minute&&calendar.get(Calendar.SECOND)==st.second)
					{
						execute = true;
					}
					else if(st.getMode()==ScheduleTime.MODE_EVERY_MINUTE &&calendar.get(Calendar.SECOND)==st.second)
					{
						execute = true;
					}
					else if(st.getMode()==ScheduleTime.MODE_FIX_WEEK_DAY)
					{
						int weekDayField = calendar.get(Calendar.DAY_OF_WEEK);
						int weekDay = -1;
						switch(weekDayField)
						{
							case Calendar.SUNDAY:
							{
								weekDay = 0;
								break;
							}
							case Calendar.MONDAY:
							{
								weekDay = 1;
								break;
							}
							case Calendar.TUESDAY:
							{
								weekDay = 2;
								break;
							}
							case Calendar.WEDNESDAY:
							{
								weekDay = 3;
								break;
							}
							case Calendar.THURSDAY:
							{
								weekDay = 4;
								break;
							}
							case Calendar.FRIDAY:
							{
								weekDay = 5;
								break;
							}
							case Calendar.SATURDAY:
							{
								weekDay = 6;
								break;
							}
						}
						for(int i=0;i<st.weekDays.length;i++)
						{
							if(st.weekDays[i]==weekDay&&calendar.get(Calendar.HOUR_OF_DAY)==st.hour&&calendar.get(Calendar.MINUTE)==st.minute&&calendar.get(Calendar.SECOND)==st.second)
							{
								execute = true;
								break;
							}
						}
					}
					if(execute) xtp.execute(runnable);
				}
			}
		}
	}
}
