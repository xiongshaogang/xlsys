package xlsys.base.io.transport;

import java.util.List;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.event.XlsysEvent;
import xlsys.base.exception.UnsupportedException;
import xlsys.base.log.LogUtil;
import xlsys.base.script.XlsysScript;
import xlsys.base.util.ObjectUtil;

public class TransportJsListener implements TransportListener
{
	private XlsysScript xs;
	
	public TransportJsListener(String script) throws UnsupportedException
	{
		xs = new XlsysScript();
		xs.setScript(script);
	}
	
	private Object callFunction(String functionName, XlsysEvent event, Object ... params)
	{
		Object ret = null;
		try
		{
			xs.put("log", XlsysFactory.getFactoryInstance(XLSYS.FACTORY_LOG).getInstance()); //$NON-NLS-1$
			xs.put("event", event); //$NON-NLS-1$
			xs.compile();
			ret = xs.invoke(functionName, params);
		}
		catch (Exception e)
		{
			LogUtil.printlnError(e);
		}
		return ret;
	}

	@Override
	public void begin(TransportEvent event)
	{
		callFunction("begin", event); //$NON-NLS-1$
	}

	@Override
	public List<String> splitFromSqlForThread(TransportEvent event)
	{
		return (List<String>) callFunction("splitFromSqlForThread", event); //$NON-NLS-1$
	}

	@Override
	public void end(TransportEvent event)
	{
		callFunction("end", event); //$NON-NLS-1$
	}

	@Override
	public void threadBegin(TransportEvent event)
	{
		callFunction("threadBegin", event); //$NON-NLS-1$
	}

	@Override
	public Boolean threadNeedRow(TransportEvent event)
	{
		return (Boolean) callFunction("threadNeedRow", event); //$NON-NLS-1$
	}

	@Override
	public Integer threadExecuteType(TransportEvent event)
	{
		return ObjectUtil.objectToInt(callFunction("threadExecuteType", event));
	}

	@Override
	public void threadBeforeTransport(TransportEvent event)
	{
		callFunction("threadBeforeTransport", event); //$NON-NLS-1$
	}

	@Override
	public void threadAfterTransport(TransportEvent event)
	{
		callFunction("threadAfterTransport", event); //$NON-NLS-1$
	}

	@Override
	public void threadEnd(TransportEvent event)
	{
		callFunction("threadEnd", event); //$NON-NLS-1$
	}
}
