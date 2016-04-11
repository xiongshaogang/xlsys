package xlsys.base.io.transfer.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.io.pack.XlsysPackage;
import xlsys.base.io.util.IOUtil;
import xlsys.base.log.LogUtil;

/**
 * 使用Http协议传输数据的客户端传输类
 * @author Lewis
 *
 */
public class HttpClientTransfer extends ClientTransfer
{
	private String serverIp;
	private int serverPort;
	private String path;
	private HttpProcessor httpProc;
	HttpRequestExecutor httpExecutor;
	
	protected HttpClientTransfer(String serverIp, int serverPort, String path) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		super();
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		if(path==null||"".equals(path)) path = "/";
		this.path = path;
		httpProc = HttpProcessorBuilder.create()
	            .add(new RequestContent())
	            .add(new RequestTargetHost())
	            .add(new RequestConnControl())
	            .add(new RequestUserAgent("Xlsys-Http/1.1"))
	            .add(new RequestExpectContinue(true)).build();
		httpExecutor = new HttpRequestExecutor();
	}
	
	@Override
	protected XlsysPackage postPackage(XlsysPackage sendPkg)
	{
		XlsysPackage recvPkg = null;
		DefaultBHttpClientConnection connection = null;
		try
		{
			HttpCoreContext coreContext = HttpCoreContext.create();
			HttpHost host = new HttpHost(serverIp, serverPort);
			coreContext.setTargetHost(host);
			connection = new DefaultBHttpClientConnection(8 * 1024);
	        if(!connection.isOpen())
	        {
	        	Socket socket = new Socket(host.getHostName(), host.getPort());
	        	connection.bind(socket);
	        }
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte sendSeriMode = getSeriMode();
			// 写入序列化模式
			baos.write(sendSeriMode);
			// 写入包内容
			if(sendSeriMode==XLSYS.SERIALIZATION_MODE_JDK)
			{
				IOUtil.writeObject(sendPkg, baos);
			}
			else if(sendSeriMode==XLSYS.SERIALIZATION_MODE_INTERNAL)
			{
				IOUtil.writeInternalObject(sendPkg, baos);
			}
			else if(sendSeriMode==XLSYS.SERIALIZATION_MODE_JSON)
			{
				IOUtil.writeJSONObject(sendPkg, baos);
			}
			ByteArrayEntity body = new ByteArrayEntity(baos.toByteArray(), ContentType.DEFAULT_BINARY);
			BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest("POST", path);
            request.setEntity(body);
			
            httpExecutor.preProcess(request, httpProc, coreContext);
            HttpResponse response = httpExecutor.execute(request, connection, coreContext);
            httpExecutor.postProcess(response, httpProc, coreContext);
            
			HttpEntity re = response.getEntity();
			
			long len = re.getContentLength();
			if(len>0)
			{
				InputStream is = re.getContent();
				// 读取序列化模式
				byte recvSeriMode = (byte) is.read();
				// 读取包内容
				if(recvSeriMode==XLSYS.SERIALIZATION_MODE_JDK)
				{
					recvPkg = (XlsysPackage) IOUtil.readObject(is);
				}
				else if(recvSeriMode==XLSYS.SERIALIZATION_MODE_INTERNAL)
				{
					recvPkg = (XlsysPackage) IOUtil.readInternalObject(is);
				}
				else if(recvSeriMode==XLSYS.SERIALIZATION_MODE_JSON)
				{
					recvPkg = (XlsysPackage) IOUtil.readJSONObject(is);
				}
			}
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
		}
		finally
		{
			try
			{
				if(connection!=null) connection.close();
			}
			catch (IOException e)
			{
				LogUtil.printlnError(e);
			}
		}
		return recvPkg;
	}

	@Override
	public String getServerIp()
	{
		return serverIp;
	}

	@Override
	public int getServerPort()
	{
		return serverPort;
	}
}
