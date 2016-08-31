package xlsys.base.io.transfer.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Headers", "_PLATFORM, Content-type, X-File-Name");
		super.service(req, resp);
	}
}
