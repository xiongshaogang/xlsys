package xlsys.base.io.ftp;

import xlsys.base.model.IModel;
import xlsys.base.util.FTPUtil;

/**
 * Ftp模型
 * @author Lewis
 *
 */
public class FtpModel implements IModel
{
	private static final long serialVersionUID = 8098176953405401675L;
	
	private int id;
	private String host;
	private int port;
	private String user;
	private String password;
	
	/**
	 * 构造一个FTP模型
	 * @param id ftp编号
	 * @param host 主机地址
	 * @param port 主机端口
	 * @param user 用户名
	 * @param password 密码
	 */
	public FtpModel(int id, String host, int port, String user, String password)
	{
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
	}

	/**
	 * 获取一个对应的FTPUtil连接实例
	 * @return
	 */
	public FTPUtil getFtpInstance()
	{
		return new FTPUtil(host, port, user, password);
	}

	/**
	 * 获取FTP编号
	 * @return
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * 获取主机地址
	 * @return
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * 获取主机端口
	 * @return
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * 获取用户名
	 * @return
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * 获取密码
	 * @return
	 */
	public String getPassword()
	{
		return password;
	}

}
