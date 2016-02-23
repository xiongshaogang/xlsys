package xlsys.base.io.pack;

import java.io.Serializable;

/**
 * 系统外部传输包，包内的内容可被压缩和加密
 * @author Lewis
 *
 */
public class XlsysPackage implements Serializable
{
	private static final long serialVersionUID = 6633138794611870537L;
	
	private boolean encrypt; // 是否加密
	private int compress; // 压缩临界值
	private boolean compressed;
	private byte[] objBytes;
	
	/**
	 * 构造一个传输包
	 */
	public XlsysPackage()
	{
		this(false, 0, null);
	}
	
	/**
	 * 构造一个传输包
	 * @param encrypt 是否加密
	 * @param compress 压缩临界值
	 */
	public XlsysPackage(boolean encrypt, int compress)
	{
		this(encrypt, compress, null);
	}
	/**
	 * 构造一个传输包
	 * @param encrypt 是否加密
	 * @param compress 压缩临界值
	 * @param objBytes 包数据
	 */
	public XlsysPackage(boolean encrypt, int compress, byte[] objBytes)
	{
		this.encrypt = encrypt;
		this.compress = compress;
		this.objBytes = objBytes;
	}
	
	/**
	 * 判断是否加密
	 * @return
	 */
	public boolean isEncrypt()
	{
		return encrypt;
	}
	
	/**
	 * 设置是否加密
	 * @param encrypt
	 */
	public void setEncrypt(boolean encrypt)
	{
		this.encrypt = encrypt;
	}
	
	/**
	 * 获取压缩临界值
	 * @return
	 */
	public int getCompress()
	{
		return compress;
	}
	
	/**
	 * 设置压缩临界值
	 * @param compress
	 */
	public void setCompress(int compress)
	{
		this.compress = compress;
	}
	
	/**
	 * 获取包数据
	 * @return
	 */
	public byte[] getObjBytes()
	{
		return objBytes;
	}
	
	/**
	 * 设置包数据
	 * @param objBytes
	 */
	public void setObjBytes(byte[] objBytes)
	{
		this.objBytes = objBytes;
	}
	
	/**
	 * 判断是否压缩
	 * @return
	 */
	public boolean isCompress()
	{
		return compressed;
	}
	
	/**
	 * 设置是否压缩
	 * @param isCompress
	 */
	public void setIsCompress(boolean compressed)
	{
		this.compressed = compressed;
	}
}
