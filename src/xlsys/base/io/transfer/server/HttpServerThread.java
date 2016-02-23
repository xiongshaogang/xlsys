package xlsys.base.io.transfer.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;

import org.apache.http.HttpConnectionFactory;
import org.apache.http.HttpServerConnection;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.apache.http.impl.DefaultBHttpServerConnectionFactory;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpService;

import xlsys.base.log.LogUtil;

/**
 * 使用Http方式传输的服务器传输类线程
 * @author Lewis
 *
 */
public class HttpServerThread extends Thread
{
	private HttpConnectionFactory<DefaultBHttpServerConnection> connectionFactory;
	private Set<Thread> runningThreadSet;
	private Socket clientSocket;
	private HttpService httpService;
	
	public HttpServerThread(Set<Thread> runningThreadSet, Socket clientSocket, HttpService httpService)
	{
		this.runningThreadSet = runningThreadSet;
		this.clientSocket = clientSocket;
		this.httpService = httpService;
		connectionFactory = DefaultBHttpServerConnectionFactory.INSTANCE;
	}

	@Override
	public void run()
	{
		HttpServerConnection connection = null;
		try
		{
			LogUtil.printlnInfo(Thread.currentThread() + ": Connection from " + clientSocket.getLocalAddress().getHostName());
			connection = connectionFactory.createConnection(clientSocket);
			HttpContext context = new BasicHttpContext(null);
			httpService.handleRequest(connection, context);
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
		}
		finally
		{
			try
			{
				if(connection!=null&&connection.isOpen()) connection.shutdown();
			}
			catch(IOException e)
			{
				LogUtil.printlnError(e);
			}
			runningThreadSet.remove(this);
		}
	}
}
