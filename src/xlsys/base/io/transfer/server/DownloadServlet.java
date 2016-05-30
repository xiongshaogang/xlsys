package xlsys.base.io.transfer.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xlsys.base.XLSYS;
import xlsys.base.io.util.IOUtil;

public class DownloadServlet extends AbstractServlet
{
	private static final long serialVersionUID = 6396038885347264253L;

	public DownloadServlet()
	{
		super();
	}
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException
    {
    	String urlStr = request.getParameter(XLSYS.DOWNLOAD_PARAM_URL);
    	String fileName = request.getParameter(XLSYS.DOWNLOAD_PARAM_FILE_NAME);
    	response.setContentType("application/octet-stream");
    	response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
    	OutputStream os = null;
    	InputStream is = null;
    	try
        {
    		os = response.getOutputStream();
    		URL url = new URL(urlStr);
    		is = url.openConnection().getInputStream();
    		IOUtil.writeBytesFromIsToOs(is, -1, os);
        }
        catch(Exception ex)
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    	finally
    	{
    		IOUtil.close(os);
    		IOUtil.close(is);
    	}
    }
}
