package xlsys.base.io.transfer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;

import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.cfg.BaseConfig;
import xlsys.base.io.pack.InnerPackage;
import xlsys.base.io.pack.XlsysPackage;
import xlsys.base.io.util.IOUtil;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.util.EDCoder;

/**
 * 系统数据传输器抽象类，所有的数据传输类都必须继承自该类。
 * @author Lewis
 *
 */
public abstract class XlsysTransfer
{
	private static Boolean hasInit;
	private static int timeout;
	private static boolean encrypt;
	private static int compress;
	
	private synchronized static void init() throws DocumentException
	{
		if(hasInit==null)
		{
			XmlModel sysCfgXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
			XmlModel transferConfigXm = sysCfgXm.getChild("TransferConfig");
			timeout = Integer.parseInt(transferConfigXm.getChild("timeout").getText());
			encrypt = Boolean.parseBoolean(transferConfigXm.getChild("encrypt").getText());
			compress = Integer.parseInt(transferConfigXm.getChild("compress").getText());
			if(timeout<0) timeout = 0;
			if(compress<0) compress = 0;
			hasInit = true;
		}
	}
	
	/**
	 * 构造一个传输类对象
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws DocumentException
	 */
	public XlsysTransfer() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		init();
	}
	
	/**
	 * 获取传输包中的内部包
	 * @param xlsysPackage 传输包
	 * @return
	 * @throws Exception
	 */
	public InnerPackage getInnerPackage(XlsysPackage xlsysPackage) throws Exception
	{
		byte[] packBytes = xlsysPackage.getObjBytes();
		if(xlsysPackage.isCompress())
		{
			packBytes = IOUtil.decompress(packBytes);
		}
		if(xlsysPackage.isEncrypt())
		{
			packBytes = EDCoder.getDefaultInstance().decrypt(packBytes);
		}
		ByteArrayInputStream bais = null;
		InnerPackage innerPackage = null;
		try
		{
			bais = new ByteArrayInputStream(packBytes);
			// 读取序列化模式
			byte seriMode = (byte) bais.read();
			// 读取包内容
			if(seriMode==XLSYS.SERIALIZATION_MODE_JDK)
			{
				innerPackage = (InnerPackage) IOUtil.readObject(bais);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_INTERNAL)
			{
				innerPackage = (InnerPackage) IOUtil.readInternalObject(bais);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_JSON)
			{
				innerPackage = (InnerPackage) IOUtil.readJSONObject(bais);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(bais);
		}
		return innerPackage;
	}
	
	/**
	 * 将一个内部包封装成一个传输包
	 * @param innerPackage 内部包
	 * @param seriMode 序列化模式, 详见 {@link XLSYS}
	 * @return
	 * @throws Exception
	 */
	public XlsysPackage getXlsysPackage(InnerPackage innerPackage, byte seriMode) throws Exception
	{
		XlsysPackage xlpkg = new XlsysPackage(encrypt, compress);
		ByteArrayOutputStream baos = null;
		try
		{
			baos = new ByteArrayOutputStream();
			// 写入序列化模式
			baos.write(seriMode);
			// 写入包内容
			if(seriMode==XLSYS.SERIALIZATION_MODE_JDK)
			{
				IOUtil.writeObject(innerPackage, baos);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_INTERNAL)
			{
				IOUtil.writeInternalObject(innerPackage, baos);
			}
			else if(seriMode==XLSYS.SERIALIZATION_MODE_JSON)
			{
				IOUtil.writeJSONObject(innerPackage, baos);
			}
			byte[] packBytes = baos.toByteArray();
			if(encrypt)
			{
				packBytes = EDCoder.getDefaultInstance().encrypt(packBytes);
			}
			if(compress>0&&packBytes.length>=compress)
			{
				packBytes = IOUtil.compress(packBytes);
				xlpkg.setIsCompress(true);
			}
			xlpkg.setObjBytes(packBytes);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(baos);
		}
		return xlpkg;
	}

	/**
	 * 获取传输超时时间
	 * @return
	 */
	public static int getTimeout()
	{
		return timeout;
	}

	/**
	 * 判断是否已加密
	 * @return
	 */
	public static boolean isEncrypt()
	{
		return encrypt;
	}

	/**
	 * 获取压缩临界值
	 * @return
	 */
	public static int getCompress()
	{
		return compress;
	}
}
