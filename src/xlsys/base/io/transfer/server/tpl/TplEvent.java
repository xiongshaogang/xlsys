package xlsys.base.io.transfer.server.tpl;

import java.util.EventObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xlsys.base.event.XlsysEvent;

public class TplEvent extends XlsysEvent
{
	private static final long serialVersionUID = -7791119947487377526L;

	private String tplCmd;
	public HttpServletRequest request;
	public HttpServletResponse response;
	public Object fillObj;
	public String template;
	public String outData;
	public String redirectPath;
	
	public TplEvent(String tplCmd)
	{
		this(tplCmd, null);
	}
	
	public TplEvent(String tplCmd, EventObject srcEvent)
	{
		super(tplCmd, srcEvent);
	}

	public String getTplCmd()
	{
		return tplCmd;
	}

}
