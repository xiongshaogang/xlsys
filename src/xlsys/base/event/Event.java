package xlsys.base.event;

import java.util.EventObject;

public class Event extends XlsysEvent
{
	private static final long serialVersionUID = 5494833132186876691L;
	
	private String name;

	public Event(Object source, EventObject srcEvent)
	{
		super(source, srcEvent);
	}

	public Event(Object source)
	{
		super(source);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
