package xlsys.base.io.attachment;

import java.io.IOException;

import xlsys.base.exception.NativeException;
import xlsys.base.io.util.IOUtil;
import xlsys.base.model.IModel;
import xlsys.base.util.StringUtil;

/**
 * 附件类
 * @author Lewis
 *
 */
public class XlsysAttachment implements IModel
{
	private static final long serialVersionUID = -3379341298971287808L;
	
	// 附件存储方式
	/**
	 * 将附件存储在文件系统中
	 */
	public final static int STYLE_FILE_SYSTEM = 1;
	/**
	 * 将附件存储在FTP中
	 */
	public final static int STYLE_FTP = 2;
	/**
	 * 将附件存储在数据库中
	 */
	public final static int STYLE_DATA_BASE = 3;
	
	private String attachmentName;
	private String innerName;
	private long size;
	private long lastModified;
	private int style;
	private boolean isCompress;
	private byte[] attachmentData;
	/**
	 * 当style为STYLE_FILE_SYSTEM和STYLE_FTP时有效
	 */
	private Integer id;
	/**
	 * 当style为STYLE_FILE_SYSTEM和STYLE_FTP时有效
	 */
	private String path;
	
	/**
	 * 构造一个附件对象
	 * @param attachmentName 附件名称
	 * @param lastModified 最后修改时间
	 * @param style 附件存储类型
	 * @param attachmentData 附件数据(如果为STYLE_DATA_BASE时需要传入)
	 * @param isCompress 是否已压缩
	 */
	public XlsysAttachment(String attachmentName, long lastModified, int style, byte[] attachmentData, boolean isCompress)
	{
		this.attachmentName = attachmentName;
		if(attachmentName!=null) innerName = StringUtil.getMD5String(attachmentName);
		this.lastModified = lastModified;
		this.style = style;
		this.attachmentData = attachmentData;
		if(attachmentData!=null) size = attachmentData.length;
		this.isCompress = isCompress;
	}

	/**
	 * 设置附件名称
	 * @param attachmentName
	 */
	public void setAttachmentName(String attachmentName)
	{
		this.attachmentName = attachmentName;
		if(attachmentName!=null) innerName = StringUtil.getMD5String(attachmentName);
		else innerName = null;
	}

	/**
	 * 获取附件名称
	 * @return
	 */
	public String getAttachmentName()
	{
		return attachmentName;
	}

	/**
	 * 获取内部存储名称
	 * @return
	 */
	public String getInnerName()
	{
		return innerName;
	}

	/**
	 * 获取附件类型
	 * @return
	 */
	public int getStyle()
	{
		return style;
	}

	/**
	 * 设置附件大小
	 * @param size
	 */
	public void setSize(long size)
	{
		this.size = size;
	}

	/**
	 * 获取附件大小
	 * @return
	 */
	public long getSize()
	{
		return size;
	}

	/**
	 * 判断是否已压缩
	 * @return
	 */
	public boolean isCompress()
	{
		return isCompress;
	}

	/**
	 * 获取附件数据
	 * @return
	 */
	public byte[] getAttachmentData()
	{
		return attachmentData;
	}
	
	/**
	 * 获取存储路径
	 * @return
	 */
	public String getPath()
	{
		return path;
	}

	/**
	 * 获取最近修改时间
	 * @return
	 */
	public long getLastModified()
	{
		return lastModified;
	}

	/**
	 * 设置存储路径
	 * @param path
	 */
	public void setPath(String path)
	{
		this.path = path;
	}

	/**
	 * 获取要存储到FTP或文件路径的配置编号
	 * @return
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * 设置要存储到FTP或文件路径的配置编号
	 * @param id
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * 压缩附件
	 * @throws IOException
	 * @throws NativeException 
	 */
	public synchronized void compress() throws IOException, NativeException
	{
		if(!isCompress&&attachmentData!=null&&attachmentData.length>0)
		{
			attachmentData = IOUtil.compress(attachmentData);
			size = attachmentData.length;
			isCompress = true;
		}
	}
	
	/**
	 * 解压附件
	 * @throws IOException
	 * @throws NativeException 
	 */
	public synchronized void decompress() throws IOException, NativeException
	{
		if(isCompress&&attachmentData!=null&&attachmentData.length>0)
		{
			attachmentData = IOUtil.decompress(attachmentData);
			size = attachmentData.length;
			isCompress = false;
		}
	}
}
