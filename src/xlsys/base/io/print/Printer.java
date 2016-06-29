package xlsys.base.io.print;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

import xlsys.base.exception.UnsupportedException;
import xlsys.base.io.util.IOUtil;
import xlsys.base.script.XlsysScript;

/**
 * 打印格式抽象类
 * @author Lewis
 *
 */
public abstract class Printer
{
	/**
	 * Excel类型打印格式
	 */
	public final static int TYPE_EXCEL = 1;
	
	protected XlsysScript xlsysScript;
	
	/**
	 * 构造一个打印格式类
	 * @param filePath 文件路径
	 */
	public Printer(String filePath)
	{
		this(new File(filePath));
	}
	
	/**
	 * 构造一个打印格式类
	 * @param file 文件
	 */
	public Printer(File file)
	{
		FileInputStream fis = null;
		try
		{
			init();
			fis = new FileInputStream(file);
			read(fis);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			IOUtil.close(fis);
		}
	}
	
	/**
	 * 构造一个打印格式类
	 * @param bytes 数据
	 */
	public Printer(byte[] bytes)
	{
		ByteArrayInputStream bais = null;
		try
		{
			init();
			bais = new ByteArrayInputStream(bytes);
			read(bais);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			IOUtil.close(bais);
		}
	}
	
	/**
	 * 构造一个打印格式类
	 * @param is 读入流
	 */
	public Printer(InputStream is)
	{
		try
		{
			init();
			read(is);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void init() throws UnsupportedException
	{
		xlsysScript = new XlsysScript();
	}
	
	/**
	 * 设置全局变量
	 * @param key 变量名
	 * @param value 变量值
	 * @throws UnsupportedException
	 */
	public void putVar(String key, Object value) throws UnsupportedException
	{
		xlsysScript.put(key, value);
	}
	
	/**
	 * 将传入map中的所有变量添加到全局变量
	 * @param varMap 
	 * 			<li> key 变量名
	 * 			<li> value 变量值
	 * @throws UnsupportedException
	 */
	public void putAllVars(Map<? extends String, ? extends Object> varMap) throws UnsupportedException
	{
		for(Entry<? extends String, ? extends Object> entry : varMap.entrySet())
		{
			xlsysScript.put(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * 获取全局变量的值
	 * @param key 变量名
	 * @return
	 * @throws UnsupportedException
	 */
	public Object getVar(String key) throws UnsupportedException
	{
		return xlsysScript.get(key);
	}
	
	/**
	 * 打印模板到指定文件
	 * @param file 目标文件
	 * @throws Exception
	 */
	public void print(File file) throws Exception
	{
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(file, false);
			print(fos);
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(fos);
		}
	}
	
	/**
	 * 打印模板到指定路径
	 * @param filePath 目标路径
	 * @throws Exception
	 */
	public void print(String filePath) throws Exception
	{
		print(new File(filePath));
	}
	
	/**
	 * 获取脚本对象
	 * @return
	 */
	public XlsysScript getXlsysScript()
	{
		return xlsysScript;
	}

	/**
	 * 读取模板内容
	 * @param stream 读入流
	 * @throws Exception
	 */
	protected abstract void read(InputStream stream) throws Exception;
	
	/**
	 * 输出生成的内容
	 * @param stream 输出流
	 * @throws Exception
	 */
	public abstract void print(OutputStream stream) throws Exception;
	
	/**
	 * 获取文件后缀
	 * @return
	 */
	public abstract String getSuffix();
}
