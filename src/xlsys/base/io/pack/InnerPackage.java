package xlsys.base.io.pack;

import java.io.Serializable;

import xlsys.base.session.Session;

/**
 * 系统内部使用包。数据未加密。
 * @author Lewis
 *
 */
public class InnerPackage implements Serializable
{
	private static final long serialVersionUID = 8124867136549541066L;
	
	private Session session;
	private String command;
	private Serializable obj;
	
	/**
	 * 构造一个内部包
	 * @param session
	 */
	public InnerPackage(Session session)
	{
		this.session = session;
	}

	/**
	 * 获取命令字
	 * @return
	 */
	public String getCommand()
	{
		return command;
	}

	/**
	 * 设置命令字
	 * @param command
	 */
	public void setCommand(String command)
	{
		this.command = command;
	}

	/**
	 * 获取包数据
	 * @return
	 */
	public Serializable getObj()
	{
		return obj;
	}

	/**
	 * 设置包数据
	 * @param obj
	 */
	public void setObj(Serializable obj)
	{
		this.obj = obj;
	}

	/**
	 * 获取Session
	 * @return
	 */
	public Session getSession()
	{
		return session;
	}
}
