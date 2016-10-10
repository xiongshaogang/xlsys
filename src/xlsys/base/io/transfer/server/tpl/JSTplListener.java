package xlsys.base.io.transfer.server.tpl;

import xlsys.base.event.XlsysEvent;
import xlsys.base.exception.UnsupportedException;
import xlsys.base.log.LogUtil;
import xlsys.base.script.XlsysScript;

public class JSTplListener implements TplListener
{
	private XlsysScript xs;
	private String script;
	
	public JSTplListener(String script) throws UnsupportedException
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
	public void redirect(TplEvent event)
	{
		callFunction("redirect", event);
	}

	@Override
	public void beforeTemplateFill(TplEvent event)
	{
		callFunction("beforeTemplateFill", event);
	}

	@Override
	public void afterTemplateFill(TplEvent event)
	{
		callFunction("afterTemplateFill", event);
	}

}
