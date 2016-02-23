package xlsys.base.io.transfer.server.extra;

import xlsys.base.event.XlsysEvent;
import xlsys.base.exception.UnsupportedException;
import xlsys.base.log.LogUtil;
import xlsys.base.script.XlsysScript;

public class JSExtraCmdListener implements ExtraCmdListener
{

	private XlsysScript xs;
	private String script;
	
	public JSExtraCmdListener(String script) throws UnsupportedException
	{
		this.script = script;
		xs = new XlsysScript();
		xs.setScript(script);
	}
	
	private Object callFunction(String functionName, XlsysEvent event, Object ... params)
	{
		Object ret = null;
		try
		{
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
	public void doPost(ExtraCmdEvent event)
	{
		callFunction("doPost", event);
	}

	@Override
	public void dispatch(ExtraCmdEvent event)
	{
		callFunction("dispatch", event);
	}

}
