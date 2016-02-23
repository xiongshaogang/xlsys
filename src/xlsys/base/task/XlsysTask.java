package xlsys.base.task;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统任务映射的实体类
 * @author Lewis
 *
 */
public class XlsysTask
{
	private int id;
	private String name;
	private List<ScheduleTime> stList;
	private Runnable task;
	
	/**
	 * 构造一个任务
	 * @param id 任务ID
	 * @param name 任务名称
	 * @param task 实现Runnable接口的对象
	 */
	public XlsysTask(int id, String name, Runnable task)
	{
		this.id = id;
		this.name = name;
		this.task = task;
	}
	
	/**
	 * 构造一个任务
	 * @param id 任务ID
	 * @param task 实现Runnable接口的对象
	 */
	public XlsysTask(int id, Runnable task)
	{
		this.id = id;
		this.task = task;
	}
	
	/**
	 * 添加调度时间
	 * @param st 调度时间
	 */
	public void addScheduleTime(ScheduleTime st)
	{
		if(stList==null) stList = new ArrayList<ScheduleTime>();
		stList.add(st);
	}

	/**
	 * 获取调度时间列表
	 * @return 调度时间列表
	 */
	public List<ScheduleTime> getStList()
	{
		return stList;
	}

	/**
	 * 获取任务ID
	 * @return
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * 获取任务名称
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 获取任务执行对象
	 * @return
	 */
	public Runnable getTask()
	{
		return task;
	}
	
}
