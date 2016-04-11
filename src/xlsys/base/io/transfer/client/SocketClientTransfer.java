package xlsys.base.io.transfer.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.io.pack.XlsysPackage;
import xlsys.base.io.util.IOUtil;
import xlsys.base.log.LogUtil;

/**
 * 直接使用Socket来进行数据传输的客户端传输类
 * @author Lewis
 *
 */
public class SocketClientTransfer extends ClientTransfer
{
	private String serverIp;
	private int serverPort;
	
	protected SocketClientTransfer(String serverIp, int serverPort) throws NoSuchMethodException,
			SecurityException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, DocumentException
	{
		super();
		this.serverIp = serverIp;
		this.serverPort = serverPort;
	}

	@Override
	protected XlsysPackage postPackage(XlsysPackage sendPkg)
	{
		XlsysPackage recvPkg = null;
		Socket socket = null;
		try
		{
			socket = new Socket(serverIp, serverPort);
			socket.setSoTimeout(getTimeout());
			OutputStream os = socket.getOutputStream();
			// 写入序列化模式
			byte sendSeriMode = getSeriMode();
			os.write(sendSeriMode);
			// 写入包内容
			if(sendSeriMode==XLSYS.SERIALIZATION_MODE_JDK)
			{
				IOUtil.writeObject(sendPkg, os);
			}
			else if(sendSeriMode==XLSYS.SERIALIZATION_MODE_INTERNAL)
			{
				IOUtil.writeInternalObject(sendPkg, os);
			}
			else if(sendSeriMode==XLSYS.SERIALIZATION_MODE_JSON)
			{
				IOUtil.writeJSONObject(sendPkg, os);
			}
			socket.shutdownOutput();
			InputStream is = socket.getInputStream();
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
			socket.shutdownInput();
		}
		catch (Exception e)
		{
			LogUtil.printlnError(e);
		}
		finally
		{
			try
			{
				if(socket!=null) socket.close();
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
