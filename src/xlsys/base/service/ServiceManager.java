package xlsys.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.dom4j.DocumentException;

import xlsys.base.log.LogUtil;
import xlsys.base.model.PairModel;
import xlsys.base.thread.XlsysThreadPool;

public class ServiceManager implements Runnable
{
	private static ServiceManager manager;
	
	protected Map<Integer, Set<IService>> serviceMap;
	protected ConcurrentLinkedQueue<PairModel<ITask, IPublisher>> taskQueue;
	
	private ServiceManager()
	{
		serviceMap = new HashMap<Integer, Set<IService>>();
		taskQueue = new ConcurrentLinkedQueue<PairModel<ITask, IPublisher>>();
	}
	
	public synchronized static ServiceManager getInstance()
	{
		if(manager==null) manager = new ServiceManager();
		return manager;
	}
	
	/**
	 * 注册服务
	 * @param taskType
	 * @param service
	 */
	public void registerService(int taskType, IService service)
	{
		synchronized(serviceMap)
		{
			Set<IService> serviceSet = serviceMap.get(taskType);
			if(serviceSet==null)
			{
				serviceSet = new HashSet<IService>();
				serviceMap.put(taskType, serviceSet);
			}
			serviceSet.add(service);
		}
		
	}
	
	/**
	 * 取消注册服务
	 * @param taskType
	 * @param service
	 */
	public void unregisterService(int taskType, IService service)
	{
		synchronized(serviceMap)
		{
			Set<IService> serviceSet = serviceMap.get(taskType);
			if(serviceSet!=null) serviceSet.remove(service);
		}
	}
	
	/**
	 * 判断指定的任务是否有服务能处理
	 * @param task 任务对象
	 * @return
	 */
	public boolean canHandlerTask(ITask task)
	{
		boolean can = false;
		TaskEvent event = new TaskEvent(this);
		event.task = task;
		Set<IService> serviceSet = serviceMap.get(task.getTastType());
		if(serviceSet!=null)
		{
			IService[] services = serviceSet.toArray(new IService[0]);
			for(IService service : services)
			{
				if(service.canHandlerTask(event))
				{
					can = true;
					break;
				}
			}
		}
		return can;
	}
	
	/**
	 * 判断返回指定的服务是否已注册到指定的任务类型
	 * @param taskType
	 * @param service
	 * @return
	 */
	public boolean containsService(int taskType, IService service)
	{
		boolean contains = false;
		Set<IService> serviceSet = serviceMap.get(taskType);
		if(serviceSet!=null) contains = serviceSet.contains(service);
		return contains;
	}
	
	/**
	 * 获取已注册指定任务类型的所有服务
	 * @param taskType
	 * @return
	 */
	public List<IService> getRegistedServices(int taskType)
	{
		List<IService> serviceList = new ArrayList<IService>();
		Set<IService> serviceSet = serviceMap.get(taskType);
		if(serviceSet!=null) serviceList.addAll(serviceSet);
		return serviceList;
	}
	
	public void addTask(IPublisher publisher, ITask task) throws DocumentException
	{
		PairModel<ITask, IPublisher> taskPair = new PairModel<ITask, IPublisher>(task, publisher);
		taskQueue.offer(taskPair);
		XlsysThreadPool.getInstance().execute(this);
	}
	
	@Override
	public void run()
	{
		PairModel<ITask, IPublisher> taskPair = taskQueue.poll();
		if(taskPair==null) return;
		ITask task = taskPair.first;
		IPublisher publisher = taskPair.second;
		int taskType = task.getTastType();
		IService[] services = null;
		synchronized(serviceMap)
		{
			Set<IService> serviceSet = serviceMap.get(taskType);
			if(serviceSet!=null) services = serviceSet.toArray(new IService[0]);
		}
		if(services==null) return;
		// 遍历所有注册了该任务类型的Service
		for(IService service : services)
		{
			try
			{
				// 询问是否能处理该任务
				TaskEvent event = new TaskEvent(this);
				event.service = service;
				event.task = task;
				if(service.canHandlerTask(event))
				{
					// 询问是否需要中断该任务的发布, 默认为不中断
					Boolean interrupt = service.interruptPublish(event);
					if(interrupt==null) interrupt = false;
					// 使用线程处理任务
					TaskRunner taskRunner = new TaskRunner(service, task, publisher);
					XlsysThreadPool.getInstance().execute(taskRunner);
					// 如果任务不需要继续发布, 则中断执行
					if(interrupt) break;
				}
			}
			catch(Exception e)
			{
				LogUtil.printlnError(e);
			}
		}
	}
}

class TaskRunner implements Runnable
{
	private IService service;
	private ITask task;
	private IPublisher publisher;
	
	public TaskRunner(IService service, ITask task, IPublisher publisher)
	{
		this.service = service;
		this.task = task;
		this.publisher = publisher;
	}

	@Override
	public void run()
	{
		// 执行publisher的开始任务前事件
		TaskEvent fpEvent = new TaskEvent(this);
		fpEvent.service = service;
		fpEvent.task = task;
		publisher.beforeTaskRun(fpEvent);
		if(!fpEvent.doit) return;
		// 处理任务
		TaskEvent event = new TaskEvent(this);
		event.service = service;
		event.task = task;
		service.handlerTask(event);
		// 执行publisher的任务结束后事件
		fpEvent.success = event.success;
		if(fpEvent.success==null) fpEvent.success = true;
		fpEvent.ret = event.ret;
		publisher.afterTastRun(fpEvent);
	}
}
