package xlsys.base.io.transfer.client;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.io.pack.InnerPackage;
import xlsys.base.io.pack.XlsysPackage;
import xlsys.base.io.transfer.XlsysTransfer;
import xlsys.base.log.LogUtil;
import xlsys.base.session.Session;
import xlsys.base.session.SessionManager;

/**
 * 客户端传输抽象类
 * @author Lewis
 *
 */
public abstract class ClientTransfer extends XlsysTransfer
{
	private byte seriMode;
	
	/**
	 * 构造一个客户端传输类对象
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws DocumentException
	 */
	public ClientTransfer() throws NoSuchMethodException, SecurityException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, DocumentException
	{
		super();
		seriMode = XLSYS.SERIALIZATION_MODE_JDK;
	}
	
	/**
	 * 发送一个命令给服务端
	 * @param command 命令字
	 * @return 服务端返回值
	 */
	public Serializable post(String command)
	{
		return post(command, null);
	}
	
	/**
	 * 发送一个命令给服务端
	 * @param command 命令字
	 * @param sendObj 对应参数
	 * @return 服务端返回值
	 */
	public Serializable post(String command, Serializable sendObj)
	{
		Session currentSession = SessionManager.getInstance().getCurrentSession();
		if(currentSession==null)
		{
			currentSession = SessionManager.getInstance().defaultSession;
		}
		return post(currentSession, command, sendObj);
	}
	
	/**
	 * 发送一个命令给服务端
	 * @param session 执行该命令的会话1
	 * @param command 命令字
	 * @param sendObj 对应参数
	 * @return 服务端返回值
	 */
	public Serializable post(Session session, String command, Serializable sendObj)
	{
		Serializable recvObj = null;
		InnerPackage sendPkg = new InnerPackage(session);
		sendPkg.setCommand(command);
		sendPkg.setObj(sendObj);
		try
		{
			InnerPackage recvPkg = postInnerPackage(sendPkg);
			recvObj = recvPkg.getObj();
			if(recvObj instanceof Exception)
			{
				throw new Exception("Exception from Server : " + ((Exception)recvObj).getMessage(), (Throwable) recvObj);
			}
		}
		catch (Exception e)
		{
			recvObj = e;
			LogUtil.printlnError(e);
		}
		return recvObj;
	}

	/**
	 * 发送一个内部包给服务端
	 * @param sendPkg 内部包对象
	 * @return 服务端返回的内部包
	 * @throws Exception
	 */
	public InnerPackage postInnerPackage(InnerPackage sendPkg) throws Exception
	{
		InnerPackage recvPkg = null;
		XlsysPackage xlSendPkg = getXlsysPackage(sendPkg, seriMode);
		XlsysPackage xlRecvPkg = postPackage(xlSendPkg);
		recvPkg = getInnerPackage(xlRecvPkg);
		return recvPkg;
	}

	/**
	 * 获取序列化方式. 详见{@link XLSYS}
	 * @return
	 */
	public byte getSeriMode()
	{
		return seriMode;
	}

	/**
	 * 设置序列化方式. 详见{@link XLSYS}
	 * @param seriMode
	 */
	protected void setSeriMode(byte seriMode)
	{
		this.seriMode = seriMode;
	}

	/**
	 * 发送一个传输包到服务端
	 * @param sendPkg 传输包
	 * @return 服务端返回的传输包
	 */
	protected abstract XlsysPackage postPackage(XlsysPackage sendPkg);
}
