package xlsys.base.event;

import java.io.Serializable;
import java.util.EventObject;
import java.util.Map;

/**
 * 系统事件抽象基类，所有的事件类都必须继承自该类
 * @author Lewis
 *
 */
public abstract class XlsysEvent extends EventObject
{
	private static final long serialVersionUID = -6984319447145127558L;

	private EventObject srcEvent;
	
	/**
	 * 如果为真，则会中断后续listener对该方法的执行
	 */
	public boolean interrupt;
	/**
	 * 如果为真，则执行当前操作(带有before前缀的方法中有效)，否则，中止该操作
	 */
	public boolean doit;
	/**
	 * store the error message when the "doit" is false
	 */
	public Serializable errMsg;
	
	/**
	 * 临时数据
	 */
	public transient Object data;
	/**
	 * 临时数据Map
	 */
	public transient Map<String, ? extends Object> dataMap;
	
	/**
	 * 构造一个事件对象
	 * @param source 触发事件的对象
	 */
	public XlsysEvent(Object source)
	{
		this(source, null);
	}
	
	/**
	 * 构造一个事件对象
	 * @param source 触发事件的对象
	 * @param srcEvent 原始事件
	 */
	public XlsysEvent(Object source, EventObject srcEvent)
	{
		super(source);
		doit = true;
		interrupt = false;
		this.srcEvent = srcEvent;
	}

	/**
	 * 获取原始事件
	 * @return
	 */
	public EventObject getSrcEvent()
	{
		return srcEvent;
	}
}
