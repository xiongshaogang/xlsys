package xlsys.base.service;

import xlsys.base.event.XlsysEvent;

public class TaskEvent extends XlsysEvent
{
	private static final long serialVersionUID = -8886493978080767359L;
	
	public IService service;
	public ITask task;
	public IPublisher publisher;
	public Boolean success;
	public Object ret;

	public TaskEvent(Object source)
	{
		super(source);
	}
}
