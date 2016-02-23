package xlsys.base.io.transfer.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Set;

import xlsys.base.XLSYS;
import xlsys.base.io.pack.XlsysPackage;
import xlsys.base.io.util.IOUtil;
import xlsys.base.log.LogUtil;

/**
 * 使用Socket方式传输的服务器传输类线程
 * @author Lewis
 *
 */
public class SocketServerThread extends Thread
{
	private ServerTransfer serverTransfer;
	private Set<Thread> runningThreadSet;
	private PackageProcessor packageProcessor;
	private Socket clientSocket;
	
	public SocketServerThread(ServerTransfer serverTransfer, Set<Thread> runningThreadSet, PackageProcessor packageProcessor, Socket clientSocket)
	{
		this.serverTransfer = serverTransfer;
		this.runningThreadSet = runningThreadSet;
		this.packageProcessor = packageProcessor;
		this.clientSocket = clientSocket;
	}

	@Override
	public void run()
	{
		super.run();
		try
		{
			LogUtil.printlnInfo(Thread.currentThread() + ": Connection from " + clientSocket.getLocalAddress().getHostName());
			InputStream is = clientSocket.getInputStream();
			// 读取序列化模式
			byte seriMode = (byte) is.read();
			// 读取包内容
			XlsysPackage recvPkg = null;
			if(seriMode==XLSYS.SERIALIZATION_MODE_JDK)
			{
				recvPkg = (XlsysPackage) IOUtil.readObject(is);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_INTERNAL)
			{
				recvPkg = (XlsysPackage) IOUtil.readInternalObject(is);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_JSON)
			{
				recvPkg = (XlsysPackage) IOUtil.readJSONObject(is);
			}
			clientSocket.shutdownInput();
			XlsysPackage outPkg = packageProcessor.process(recvPkg, serverTransfer, seriMode);
			OutputStream os = clientSocket.getOutputStream();
			// 写入序列化模式
			os.write(seriMode);
			// 写入内容
			if(seriMode==XLSYS.SERIALIZATION_MODE_JDK)
			{
				IOUtil.writeObject(outPkg, os);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_INTERNAL)
			{
				IOUtil.writeInternalObject(outPkg, os);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_JSON)
			{
				IOUtil.writeJSONObject(outPkg, os);
			}
			clientSocket.shutdownOutput();
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
		}
		finally
		{
			try
			{
				if(clientSocket!=null)
				{
					clientSocket.close();
					clientSocket = null;
				}
			}
			catch(IOException e)
			{
				LogUtil.printlnError(e);
			}
			runningThreadSet.remove(this);
		}
	}
}
