package xlsys.base.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import xlsys.base.exception.NativeException;
import xlsys.base.io.util.FileUtil;
import xlsys.base.io.util.IOUtil;

public class EDCoder
{
	public final static String SECRET_KEY = "secretKeyC.sk";
	private static EDCoder edCoder;
	
	private byte[] key;
	
	/**
	 * 使用输入流构造一个EDCoder
	 * @param in
	 */
	public EDCoder(InputStream in)
	{
		try
		{
			init(in);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 使用文件路径构造一个EDCoder
	 * @param filePath
	 */
	public EDCoder(String filePath)
	{
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(filePath);
			init(fis);
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
	
	private void init(InputStream is) throws Exception
	{
		key = getSecretKey(is);
	}
	
	/**
	 * 获取默认的EDCoder实例
	 * @return
	 */
	public static EDCoder getDefaultInstance()
	{
		if(edCoder==null)
		{
			String keyFile = "/" + StringUtil.transJavaPackToPath(EDCoder.class.getPackage().getName()).replaceAll("\\\\", "/") + "/"+SECRET_KEY;
			InputStream is = EDCoder.class.getResourceAsStream(keyFile);
			edCoder = new EDCoder(is);
			IOUtil.close(is);
		}
		return edCoder;
	}
	
	/**
	 * 生成密钥文件到指定路径中
	 * @param filePath 要存放密钥文件的路径
	 * @throws NativeException
	 * @throws IOException
	 */
	public static void generateKey(String filePath) throws NativeException, IOException
	{
		byte[] key = generateKey();
		FileUtil.writeFile(filePath, key);
	}
	
	/**
	 * 从读入流中获取密钥
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static byte[] getSecretKey(InputStream is) throws Exception
	{
		return IOUtil.readBytesFromInputStream(is, -1);
	}
	
	public byte[] base64Encode(byte[] srcByte) throws NativeException
	{
		return base64EncodeC(srcByte);
	}
	
	public byte[] base64Decode(byte[] srcByte) throws NativeException
	{
		return base64DecodeC(srcByte);
	}
	
	/**
	 * 加密数据
	 * @param srcBytes 原始的字节数组
	 * @return 加密后的字节数组
	 * @throws NativeException 
	 */
	public byte[] encrypt(byte[] srcBytes) throws NativeException
	{
		return encryptC(key, srcBytes);
	}
	
	/**
	 * 解密数据
	 * @param srcBytes 密文
	 * @return 解密后的字节数组
	 * @throws NativeException 
	 */
	public byte[] decrypt(byte[] srcBytes) throws NativeException
	{
		return decryptC(key, srcBytes);
	}
	
	/**
	 * 字节数组转unicode字符串
	 * @param b
	 * @return
	 */
	public String byteToUnicode(byte[] b)
	{
		String str = "";
		for(int i=0;i<b.length;i++)
		{
			str += b[i];
			if(i!=b.length-1)
			{
				str += ",";
			}
		}
		return str;
	}
	
	/**
	 * unicode字符串转字节数组
	 * @param unicodeStr
	 * @return
	 */
	public byte[] UnicodeToByte(String unicodeStr)
	{
		String[] unicode = unicodeStr.split(",");
		byte[] b = new byte[unicode.length];
		for(int i=0;i<b.length;i++)
		{
			b[i] = Integer.valueOf(unicode[i]).byteValue();
		}
		return b;
	}
	
	protected static native byte[] generateKey() throws NativeException;
	
	protected static native byte[] base64EncodeC(byte[] srcBytes) throws NativeException;
	
	protected static native byte[] base64DecodeC(byte[] srcBytes) throws NativeException;
	
	protected static native byte[] encryptC(byte[] key, byte[] srcBytes) throws NativeException;
	
	protected static native byte[] decryptC(byte[] key, byte[] srcBytes) throws NativeException;
}
