package xlsys.base.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.charset.Charset;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import xlsys.base.io.util.IOUtil;
import xlsys.base.log.LogUtil;

/**
 * FTP工具类
 * @author Lewis
 *
 */
public class FTPUtil
{
	private FTPClient ftpClient;
	private String ip;
	private int port;
	private String username;
	private String password;
	private String pwd;
	private Exception curException;
	
	/**
	 * 构造一个FTP工具
	 * @param ip 主机地址
	 * @param port 主机端口
	 * @param username 用户名
	 * @param password 密码
	 */
	public FTPUtil(String ip, int port, String username, String password)
	{
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * 构造一个FTP工具
	 * @param ip 主机地址
	 * @param username 用户名
	 * @param password 密码
	 */
	public FTPUtil(String ip, String username, String password)
	{
		this(ip, 21, username, password);
	}
	
	/**
	 * 获取主机地址
	 * @return
	 */
	public String getIp()
	{
		return ip;
	}

	private boolean _cd(String path) throws SocketException, IOException
	{
		boolean success = true;
		ensureOpen();
		try
		{
			success = ftpClient.changeWorkingDirectory(path);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			curException = e;
			success = false;
		}
		return success;
	}
	
	/**
	 * 进入指定路径
	 * @param path 要进入的路径
	 * @throws SocketException
	 * @throws IOException
	 */
	public void cd(String path) throws SocketException, IOException
	{
		boolean success = false;
		for(int i=0;i<5;i++)
		{
			if(_cd(path))
			{
				success = true;
				break;
			}
			reConnect();
		}
		if(success)
		{
			if(path.startsWith("/"))
			{
				this.pwd = path;
			}
			else
			{
				if(pwd.endsWith("/"))
				{
					this.pwd += path;
				}
				else
				{
					this.pwd += "/" + path;
				}
			}
		}
		else
		{
			IOException e = new IOException("Change Dir Path Error : " + path);
			curException = e;
			throw e;
		}
	}
	
	/**
	 * 获取当前路径
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public String pwd() throws SocketException, IOException
	{
		ensureOpen();
		return ftpClient.printWorkingDirectory();
	}
	
	/**
	 * 删除文件
	 * @param pathName 文件路径
	 * @return 成功返回true, 失败返回false
	 * @throws IOException
	 */
	public boolean deleteFile(String pathName) throws IOException
	{
		return ftpClient.deleteFile(pathName);
	}
	
	/**
	 * 删除目录
	 * @param dirName 目录路径
	 * @return 成功返回true, 失败返回false
	 * @throws IOException
	 */
	public boolean removeDirectory(String dirName) throws IOException
	{
		boolean success = false;
		FTPFile[] files = ftpClient.listFiles(dirName);
		for(FTPFile file : files)
		{
			String subPath = dirName+"/"+file.getName();
			if(file.isFile()) ftpClient.deleteFile(subPath);
			else removeDirectory(subPath);
		}
		success = ftpClient.removeDirectory(dirName);
		return success;
	}
	
	/**
	 * 发送命令到FTP服务端执行
	 * @param command 命令
	 * @throws IOException
	 */
	public void sendServer(String command) throws IOException
	{
		ensureOpen();
		ftpClient.sendCommand(command);
	}
	
	/**
	 * 重连接
	 * @return
	 */
	public boolean reConnect()
	{
		boolean success = true;
		try
		{
			close();
			ftpClient = null;
			ensureOpen();
		}
		catch(Exception e)
		{
			curException = e;
			success = false;
		}
		return success;
	}
	
	/**
	 * 确保FTP已连通
	 * @throws SocketException
	 * @throws IOException
	 */
	public void ensureOpen() throws SocketException, IOException
	{
		if(ftpClient==null)
		{
			ftpClient = new FTPClient();
			ftpClient.setCharset(Charset.forName("utf-8"));
			ftpClient.setControlEncoding("utf-8");
			//FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);  
			//conf.setServerLanguageCode(Locale.getDefault().getLanguage());  
			//ftpClient.configure(conf);
			ftpClient.setControlKeepAliveTimeout(0);
			ftpClient.enterLocalPassiveMode();
		}
		if(!ftpClient.isConnected())
		{
			ftpClient.connect(ip, port);
			ftpClient.login(username, password);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if(pwd!=null)
			{
				ftpClient.changeWorkingDirectory(pwd);
			}
			this.pwd = ftpClient.printWorkingDirectory();
		}
	}
	
	public boolean completePendingCommand() throws IOException
	{
		return ftpClient.completePendingCommand();
	}
	
	/**
	 * 准备写入文件
	 * @param path 客户端文件路径
	 * @return 文件输出流
	 * @throws IOException
	 */
	public OutputStream put(String path) throws IOException
	{
		ensureOpen();
		OutputStream os = ftpClient.storeFileStream(path);
		if (os == null)
		{
			LogUtil.printlnError(ftpClient.getReplyCode() + ":" + ftpClient.getReplyString());
        }
		return os;
	}
	
	/**
	 * 准备获取文件
	 * @param path 主机端文件路径
	 * @return 文件输入流
	 */
	public InputStream get(String path)
	{
		InputStream is = null;
		try
		{
			ensureOpen();
			is =  ftpClient.retrieveFileStream(path);
		}
		catch(IOException e)
		{
			curException = e;
			IOUtil.close(is);
			is = null;
		}
		return is;
	}
	
	/**
	 * 创建目录
	 * @param directoryName 目录名称
	 * @throws IOException
	 */
	public void createDirectory(String directoryName) throws IOException
	{   
		ensureOpen();
		 //ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
		 String[] dirs = directoryName.split("/|\\\\");
		 String path = "";   
		 for(int i=0;i<dirs.length;i++)
		 {
			 if(i==0) path += dirs[i];
			 else path += "/" + dirs[i];
			 try
			 {
				 //ftpClient.makeDirectory(new String(path.getBytes("utf-8"), "utf-8"));
				 ftpClient.makeDirectory(path);
				 //sendServer("MKD " + pwd + path);
				 //System.out.println(ftpClient.getReplyString());
			 }
			 catch(Exception e)
			 {
				 curException = e;
				 e.printStackTrace();
			 }
			 ftpClient.getReplyCode();
		 }   
		// ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	}
	
	/**
	 * 上传文件
	 * @param is 文件输入流
	 * @param fileName 文件名称
	 * @return
	 */
	public boolean uploadFile(InputStream is, String fileName)
	{
		boolean success = true;
		OutputStream os = null;
		try
		{
			ensureOpen();
			BufferedInputStream bis = new BufferedInputStream(is);
			os = ftpClient.storeFileStream(fileName);
			IOUtil.writeBytesFromIsToOs(bis, -1, os);
		}
		catch(IOException e)
		{
			curException = e;
			success = false;
		}
		finally
		{
			IOUtil.close(os);
		}
		return success;
	}
	
	/**
	 * 获取FTP文件列表
	 * @return
	 * @throws IOException
	 */
	public FTPFile[] listFiles() throws IOException
	{
		return ftpClient.listFiles();
	}
	
	/**
	 * 获取FTP文件
	 * @param pathName 文件名
	 * @return
	 * @throws IOException
	 */
	public FTPFile listFile(String pathName) throws IOException
	{
		FTPFile[] files = ftpClient.listFiles(pathName);
		return files==null||files.length==0?null:files[0];
	}
	
	/**
	 * 关闭FTP工具
	 */
	public void close()
	{
		try
		{
			if(ftpClient!=null&&ftpClient.isConnected())
			{
				ftpClient.disconnect();
			}
		}
		catch(IOException e)
		{
			curException = e;
		}
	}

	/**
	 * 获取当前的错误
	 * @return
	 */
	public Exception getCurException()
	{
		return curException;
	}

	/**
	 * 复制当前的FTP工具
	 */
	public FTPUtil clone()
	{
		return new FTPUtil(ip, port, username, password);
	}
}
