package xlsys.base.io.transfer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.ResponseContent;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;
import org.apache.http.protocol.UriHttpRequestHandlerMapper;

import xlsys.base.log.LogUtil;
import xlsys.base.thread.XlsysThreadPool;

public class HttpServerTransfer extends ServerTransfer
{
	private int port;
	private Set<Thread> runningThreadSet;
	
	public HttpServerTransfer(int port) throws Exception
	{
		super();
		this.port = port;
		runningThreadSet = Collections.synchronizedSet(new HashSet<Thread>());
	}

	@Override
	public void startup()
	{
		HttpProcessor httpproc = HttpProcessorBuilder.create()
                .add(new ResponseDate())
                .add(new ResponseServer("Xlsys-Http/1.1"))
                .add(new ResponseContent())
                .add(new ResponseConnControl()).build();

        // Set up request handlers
        UriHttpRequestHandlerMapper reqistry = new UriHttpRequestHandlerMapper();
        reqistry.register("*", new XlsysHttpRequestHandler(this, packageProcessor));

        // Set up the HTTP service
        HttpService httpService = new HttpService(httpproc, reqistry);
		
		ServerSocket ss = null;
		try
		{
			ss = new ServerSocket(port);
			Socket clientSocket = null;
			while(serverStatus==SERVER_STATUS_RUNNING)
			{
				clientSocket = ss.accept();
				clientSocket.setSoTimeout(getTimeout());
				HttpServerThread sst = new HttpServerThread(runningThreadSet, clientSocket, httpService);
				runningThreadSet.add(sst);
				XlsysThreadPool.getInstance().execute(sst);
				clientSocket = null;
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
				if(ss!=null) ss.close();
			}
			catch (IOException e)
			{
				LogUtil.printlnError(e);
			}	
			shutdown(SERVER_SHUTDOWN_COMMON);
		}
	}

	@Override
	protected void release()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void waitForRunningRequest()
	{
		try
		{
			while(!runningThreadSet.isEmpty())
			{
				runningThreadSet.iterator().next().join();
			}
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
		}
	}

	@Override
	protected void stop()
	{
		for(Thread t : runningThreadSet)
		{
			t.interrupt();
		}
	}
}


