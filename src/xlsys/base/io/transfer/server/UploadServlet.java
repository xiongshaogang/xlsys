package xlsys.base.io.transfer.server;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

import xlsys.base.XLSYS;
import xlsys.base.io.attachment.XlsysAttachment;
import xlsys.base.io.util.IOUtil;
import xlsys.base.script.XlsysClassLoader;
import xlsys.base.session.SessionManager;
import xlsys.base.task.XlsysScheduledExecutor;
import xlsys.base.util.StringUtil;
import xlsys.base.util.SystemUtil;

public class UploadServlet extends HttpServlet
{
	private static final long serialVersionUID = -510198373556743326L;
	
	public UploadServlet()
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException
    {
    	PrintWriter writer = null;
    	try
        {
    		writer = response.getWriter();
    		String contentType = request.getContentType();
    		int pos = contentType.indexOf(';');
    		if (pos > -1) contentType = contentType.substring(0, pos);
    		XlsysAttachment attachment = null;
    		// Older browsers
        	if (contentType.equals("multipart/form-data")) attachment = receiveMultipart(request);
        	// Modern browsers
        	else if (contentType.equals("application/octet-stream")) attachment = receiveOctetStream(request);
        	if(attachment!=null)
        	{
        		XlsysServlet.virtualPost(SessionManager.getInstance().defaultSession, XLSYS.COMMAND_UPLOAD_FILE, attachment);
        		response.setStatus(HttpServletResponse.SC_OK);
                writer.print("({ success : true, md5 : \""+attachment.getMd5()+"\"})");
        	}
        	else
        	{
        		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.print("({success : false })");
        	}
        }
        catch(Exception ex)
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print("({ success : false})");
        }
    	finally
    	{
    		if(writer!=null)
    		{
    			writer.flush();
    			writer.close();
    		}
    	}
    }
    
    private XlsysAttachment receiveMultipart(HttpServletRequest request) throws Exception
    {
    	XlsysAttachment attachment = null;
    	InputStream is = null;
    	ByteArrayOutputStream baos = null;
    	String attachmentStr = null;
    	try
    	{
    		MultipartParser parser = new MultipartParser(request, Integer.MAX_VALUE, true, true, null);
    		Part part = null;
    		while((part=parser.readNextPart())!=null)
    		{
    			if(part.isFile())
    			{
    				baos = new ByteArrayOutputStream();
    				// Get the file details
    				FilePart filePart = (FilePart) part;
    				// Save the file
    				is = filePart.getInputStream();
    				IOUtil.writeBytesFromIsToOs(is, -1, baos);
    			}
    			else
    			{
    				ParamPart paramPart = (ParamPart) part;
    				String param = paramPart.getName();
    				if(XLSYS.UPLOAD_PARAM_ATTACHMENT.equals(param)) attachmentStr = URLDecoder.decode(paramPart.getStringValue(), "UTF-8");
    			}
    		}
    		XlsysAttachment src = (XlsysAttachment) IOUtil.readJSONObject(attachmentStr, XlsysClassLoader.getInstance());
    		byte[] datas = baos.toByteArray();
    		attachment = new XlsysAttachment(src.getAttachmentName(), src.getLastModified(), src.getStyle(), datas, false, StringUtil.getMD5String(datas));
    	}
    	finally
    	{
    		IOUtil.close(is);
        	IOUtil.close(baos);
    	}
		return attachment;
    }
    
    private XlsysAttachment receiveOctetStream(HttpServletRequest request) throws Exception
    {
        XlsysAttachment attachment = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try
        {
        	baos = new ByteArrayOutputStream();
        	is = request.getInputStream();
        	IOUtil.writeBytesFromIsToOs(is, -1, baos);
        	String attachmentStr = URLDecoder.decode(request.getParameter(XLSYS.UPLOAD_PARAM_ATTACHMENT), "UTF-8");
    		XlsysAttachment src = (XlsysAttachment) IOUtil.readJSONObject(attachmentStr, XlsysClassLoader.getInstance());
    		byte[] datas = baos.toByteArray();
    		attachment = new XlsysAttachment(src.getAttachmentName(), src.getLastModified(), src.getStyle(), datas, false, StringUtil.getMD5String(datas));
        }
        finally
        {
        	IOUtil.close(is);
        	IOUtil.close(baos);
        }
        return attachment;
    }
}
