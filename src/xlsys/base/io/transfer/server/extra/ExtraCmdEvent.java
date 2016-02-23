package xlsys.base.io.transfer.server.extra;

import java.util.EventObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xlsys.base.database.IDataBase;
import xlsys.base.event.XlsysEvent;

public class ExtraCmdEvent extends XlsysEvent
{
	private static final long serialVersionUID = -7791119947487377526L;

	private String extraCmd;
	public IDataBase dataBase;
	public HttpServletRequest request;
	public HttpServletResponse response;
	public String dispatchPath;
	
	public ExtraCmdEvent(String extraCmd)
	{
		this(extraCmd, null);
	}
	
	public ExtraCmdEvent(String extraCmd, EventObject srcEvent)
	{
		super(extraCmd, srcEvent);
	}

	public String getExtraCmd()
	{
		return extraCmd;
	}

}
