package xlsys.base.io.transfer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import xlsys.base.log.LogUtil;
import xlsys.base.thread.XlsysThreadPool;

/**
 * 使用Socket方式传输的服务器传输类
 * @author Lewis
 *
 */
public class SocketServerTransfer extends ServerTransfer
{
	private int port;
	private Set<Thread> runningThreadSet;
	
	public SocketServerTransfer(int port) throws Exception
	{
		super();
		this.port = port;
		runningThreadSet = Collections.synchronizedSet(new HashSet<Thread>());
	}

	@Override
	public void startup()
	{
		ServerSocket ss = null;
		try
		{
			ss = new ServerSocket(port);
			Socket clientSocket = null;
			while(serverStatus==SERVER_STATUS_RUNNING)
			{
				clientSocket = ss.accept();
				clientSocket.setSoTimeout(getTimeout());
				SocketServerThread sst = new SocketServerThread(this, runningThreadSet, packageProcessor, clientSocket);
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
