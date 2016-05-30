package xlsys.base.io.transfer.server;

import javax.servlet.http.HttpServlet;

import xlsys.base.task.XlsysScheduledExecutor;
import xlsys.base.util.SystemUtil;

public abstract class AbstractServlet extends HttpServlet
{
	private static final long serialVersionUID = 8152915573824605227L;

	public AbstractServlet()
	{
		super();
		try
		{
			SystemUtil.systemInit();
			XlsysScheduledExecutor.startSchedule();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
