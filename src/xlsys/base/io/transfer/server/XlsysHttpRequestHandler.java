package xlsys.base.io.transfer.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import xlsys.base.XLSYS;
import xlsys.base.io.pack.XlsysPackage;
import xlsys.base.io.util.IOUtil;

public class XlsysHttpRequestHandler implements HttpRequestHandler
{
	private ServerTransfer serverTransfer;
	private PackageProcessor packageProcessor;
	
	public XlsysHttpRequestHandler(ServerTransfer serverTransfer, PackageProcessor packageProcessor)
	{
		this.serverTransfer = serverTransfer;
		this.packageProcessor = packageProcessor;
	}
	
	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException
	{
		String method = request.getRequestLine().getMethod().toUpperCase(Locale.ENGLISH);
		if("POST".equals(method))
		{
			if(request instanceof HttpEntityEnclosingRequest)
			{
				HttpEntityEnclosingRequest heeRequest = (HttpEntityEnclosingRequest) request;
				HttpEntity entity = heeRequest.getEntity();
				if(ContentType.DEFAULT_BINARY.toString().equals(entity.getContentType().getValue()))
				{
					try
					{
						doBinaryPost(heeRequest, response);
					}
					catch (Exception e)
					{
						throw new IOException(e);
					}
				}
			}
		}
	}

	private void doBinaryPost(HttpEntityEnclosingRequest request, HttpResponse response) throws Exception
	{
		XlsysPackage respPkg = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		byte seriMode = XLSYS.SERIALIZATION_MODE_JDK;
		try
		{
			is = request.getEntity().getContent();
			// 读取序列化模式
			seriMode = (byte) is.read();
			// 读取包内容
			XlsysPackage reqPak = null;
			if(seriMode==XLSYS.SERIALIZATION_MODE_JDK)
			{
				reqPak = (XlsysPackage) IOUtil.readObject(is);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_INTERNAL)
			{
				reqPak = (XlsysPackage) IOUtil.readInternalObject(is);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_JSON)
			{
				reqPak = (XlsysPackage) IOUtil.readJSONObject(is);
			}
			if(reqPak != null)
			{
				respPkg = packageProcessor.process(reqPak, serverTransfer, seriMode);
			}
			response.setStatusCode(HttpStatus.SC_OK);
			baos = new ByteArrayOutputStream();
			// 写入序列化模式
			baos.write(seriMode);
			// 写入内容
			if(seriMode==XLSYS.SERIALIZATION_MODE_JDK)
			{
				IOUtil.writeObject(respPkg, baos);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_INTERNAL)
			{
				IOUtil.writeInternalObject(respPkg, baos);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_JSON)
			{
				IOUtil.writeJSONObject(respPkg, baos);
			}
			ByteArrayEntity body = new ByteArrayEntity(baos.toByteArray(), ContentType.DEFAULT_BINARY);
			response.setEntity(body);
		}
		catch (Exception e)
		{	
			throw e;
		}
		finally
		{
			IOUtil.close(is);
			IOUtil.close(baos);
		}
	}
}
