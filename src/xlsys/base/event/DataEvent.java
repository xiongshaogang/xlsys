package xlsys.base.event;

import java.io.Serializable;
import java.util.EventObject;

public class DataEvent extends Event
{
	private static final long serialVersionUID = -7054379109458520793L;
	
	private Object data;

	public DataEvent(Object source, EventObject srcEvent)
	{
		super(source, srcEvent);
	}

	public DataEvent(Object source)
	{
		super(source);
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}
}
