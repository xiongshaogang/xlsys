package xlsys.base.io.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipInputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.json.JSONArray;
import org.json.JSONObject;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.database.ConnectionPool;
import xlsys.base.database.IDataBase;
import xlsys.base.database.util.DBUtil;
import xlsys.base.dataset.IDataSet;
import xlsys.base.dataset.util.DataSetUtil;
import xlsys.base.exception.NativeException;
import xlsys.base.exception.UnsupportedException;
import xlsys.base.io.DBFInputStream;
import xlsys.base.io.XlsysObjectInputStream;
import xlsys.base.log.LogUtil;
import xlsys.base.script.XlsysClassLoader;
import xlsys.base.util.ObjectUtil;

/**
 * IO流工具
 * @author Lewis
 *
 */
public class IOUtil
{
	private final static String NULL_OBJ_HASH_STRING = "_NULL_OBJ_HASH_STRING";
	
	public static BufferedReader getBufferedReader(String filePath) throws FileNotFoundException, UnsupportedEncodingException
	{
		return getBufferedReader(filePath,null);
	}
	
	public static BufferedReader getBufferedReader(String filePath, String charsetName) throws FileNotFoundException, UnsupportedEncodingException
	{
		InputStream is = getFileInputStream(filePath);
		InputStreamReader isr = null;
		if(charsetName!=null)
		{
			isr = new InputStreamReader(is,charsetName);
		}
		else
		{
			isr = new InputStreamReader(is);
		}
		BufferedReader br = new BufferedReader(isr);
		return br;
	}
	
	public static PrintWriter getPrintWriter(String filePath, boolean append) throws FileNotFoundException
	{
		FileOutputStream fos = getFileOutputStream(filePath,append);
		PrintWriter pw = new PrintWriter(fos);
		return pw;
	}
	
	public static ObjectInputStream getObjectInputStream(String filePath) throws IOException
	{
		FileInputStream fis = getFileInputStream(filePath);
		ObjectInputStream ois = new ObjectInputStream(fis);
		return ois;
	}
	
	public static ObjectOutputStream getObjectOutputStream(String filePath, boolean append) throws IOException
	{
		FileOutputStream fos = getFileOutputStream(filePath,append);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		return oos;
	}
	
	public static FileInputStream getFileInputStream(String filePath) throws FileNotFoundException
	{
		FileInputStream fis = new FileInputStream(new File(filePath).getAbsoluteFile());
		return fis;
	}
	
	public static FileOutputStream getFileOutputStream(String filePath, boolean append) throws FileNotFoundException
	{
		FileUtil.createParentPath(filePath);
		FileOutputStream fos = new FileOutputStream(new File(filePath).getAbsoluteFile(), append);
		return fos;
	}
	
	public static BufferedInputStream getBufferedInputStream(String filePath) throws FileNotFoundException
	{
		FileInputStream fis = getFileInputStream(filePath);
		BufferedInputStream bis = new BufferedInputStream(fis);
		return bis;
	}
	
	public static BufferedOutputStream getBufferedOutputStream(String filePath, boolean append) throws FileNotFoundException
	{
		FileOutputStream fos = getFileOutputStream(filePath,append);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		return bos;
	}
	
	public static DataInputStream getDataInputStream(String filePath, boolean buffered) throws FileNotFoundException
	{
		DataInputStream dis = null;
		if(buffered)
		{
			BufferedInputStream bis = getBufferedInputStream(filePath);
			dis = new DataInputStream(bis);
		}
		else
		{
			FileInputStream fis = getFileInputStream(filePath);
			dis = new DataInputStream(fis);
		}
		return dis;
	}
	
	public static DataOutputStream getDataOutputStream(String filePath, boolean append, boolean buffered) throws FileNotFoundException
	{
		DataOutputStream dos = null;
		if(buffered)
		{
			BufferedOutputStream bos = getBufferedOutputStream(filePath,append);
			dos = new DataOutputStream(bos);
		}
		else
		{
			FileOutputStream fos = getFileOutputStream(filePath,append);
			dos = new DataOutputStream(fos);
		}
		return dos;
	}
	
	public static RandomAccessFile getRandomAccessFile(String name, String mode) throws FileNotFoundException
	{
		RandomAccessFile raf = new RandomAccessFile(name, mode);
		return raf;
	}
	
	public static void close(Object o)
	{
		if(o!=null)
		{
			try
			{
				
				if(o instanceof Reader)
				{
					((Reader)o).close();
				}
				else if(o instanceof Writer)
				{
					((Writer)o).close();
				}
				else if(o instanceof InputStream)
				{
					((InputStream)o).close();
				}
				else if(o instanceof OutputStream)
				{
					((OutputStream)o).close();
				}
				else if(o instanceof RandomAccessFile)
				{
					((RandomAccessFile)o).close();
				}
				else if(o instanceof org.apache.tools.zip.ZipFile)
				{
					((org.apache.tools.zip.ZipFile)o).close();
				}
				else if (o instanceof DBFInputStream)
				{
					((DBFInputStream)o).close();
				}
				else if(o instanceof Socket)
				{
					((Socket) o).close();
				}
			}
			catch(Exception e){}
		}
	}
	
	public static native byte[] decompressC(byte[] srcBytes) throws NativeException;
	
	public static native byte[] compressC(byte[] srcBytes) throws NativeException;
	
	public static byte[] compress(byte[] srcBytes) throws NativeException
	{
		return compressC(srcBytes);
	}
	
	public static byte[] decompress(byte[] srcBytes) throws NativeException
	{
		return decompressC(srcBytes);
	}
	
	public static byte[] compressJ(byte[] srcBytes) throws IOException
	{
		byte[] compressBytes = null;
		ZipOutputStream zos = null;
		ByteArrayInputStream bais = null;
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			zos = new ZipOutputStream(baos);
			ZipEntry zipEntry = new ZipEntry("root");
			zipEntry.setMethod(ZipEntry.DEFLATED);
			zos.putNextEntry(zipEntry);
			bais = new ByteArrayInputStream(srcBytes);
			byte[] b = new byte[1024];
			int len = -1;
			while((len=bais.read(b, 0, b.length))!=-1)
			{
				zos.write(b, 0, len);
			}
			zos.flush();
			zos.closeEntry();
			compressBytes = baos.toByteArray();
		}
		catch(IOException e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(zos);
			IOUtil.close(bais);
		}
		return compressBytes;
	}
	
	public static byte[] decompressJ(byte[] srcBytes) throws IOException
	{
		byte[] decompression = null;
		ByteArrayOutputStream baos = null;
		ZipInputStream zis = null;
		try
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(srcBytes);
			baos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			zis = new ZipInputStream(bais);
			zis.getNextEntry();
			while((len=zis.read(b, 0, b.length))!=-1)
			{
				baos.write(b, 0, len);
			}
			decompression = baos.toByteArray();
		}
		catch(IOException e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(baos);
			IOUtil.close(zis);
		}
		return decompression;
	}
	
	public static void zipFile(File file, OutputStream os) throws IOException
	{
		ZipOutputStream zos = null;
		try
		{
			// 创建根路径
			String rootPath = "";
			zos = new ZipOutputStream(os);
			doZipFile(file, zos, rootPath);
			zos.closeEntry();
		}
		catch(IOException e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(zos);
		}
	}
	
	private static void doZipFile(File file, ZipOutputStream zos, String parentPath) throws IOException
	{
		if(file.isDirectory())
		{
			parentPath += file.getName() + "/";
			File[] files = file.listFiles();
			if(files.length==0)
			{
				// 如果目录为空，直接创建目录
				ZipEntry zipEntry = new ZipEntry(parentPath);
				System.out.println(zipEntry.isDirectory());
				zipEntry.setMethod(ZipEntry.DEFLATED);
				zos.putNextEntry(zipEntry);
				zos.closeEntry();
			}
			for(File subFile : files)
			{
				doZipFile(subFile, zos, parentPath);
			}
		}
		else
		{
			ZipEntry zipEntry = new ZipEntry(parentPath+file.getName());
			zipEntry.setMethod(ZipEntry.DEFLATED);
			zos.putNextEntry(zipEntry);
			byte[] fileBytes = FileUtil.getByteFromFile(file.getAbsolutePath());
			zos.write(fileBytes);
			zos.closeEntry();
		}
	}
	
	public static byte[] getObjectBytes(Object obj) throws IOException
	{
		ByteArrayOutputStream baos = null;
		byte[] objectBytes = null;
		try
		{
			baos = new ByteArrayOutputStream();
			writeObject(obj, baos);
			objectBytes = baos.toByteArray();
		}
		catch(IOException e)
		{
			throw e;
		}
		finally
		{
			close(baos);
		}
		return objectBytes;
	}
	
	public static void writeObject(Object obj, OutputStream out) throws IOException
	{
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(obj);
		oos.flush();
	}
	
	public static Object readObject(InputStream in) throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois = new XlsysObjectInputStream(in);
		return ois.readObject();
	}
	
	public static Object readObject(byte[] objectBytes) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream bais = null;
		Object obj = null;
		try
		{
			bais = new ByteArrayInputStream(objectBytes);
			obj = readObject(bais);
		}
		catch(IOException|ClassNotFoundException e)
		{
			throw e;
		}
		finally
		{
			close(bais);
		}
		return obj;
	}
	
	private static Object tryInitObj(Class<?> objClass)
	{
		Object obj = null;
		// 先使用无参的构造方法构造
		try
		{
			Constructor<?> constructor = objClass.getDeclaredConstructor();
			constructor.setAccessible(true);
			obj = constructor.newInstance();
		}
		catch(Exception e){}
		if(obj==null)
		{
			// 构造不成功，尝试使用其它构造方法初始化
			Constructor<?>[] constructors = objClass.getDeclaredConstructors();
			for(Constructor<?> constructor : constructors)
			{
				int paramLength = constructor.getParameterTypes().length;
				if(paramLength>0)
				{
					constructor.setAccessible(true);
					try
					{
						Object[] params = new Object[paramLength];
						for(int i=0;i<paramLength;i++)
						{
							Class<?> paramClass = constructor.getParameterTypes()[i];
							if(paramClass.equals(short.class)) params[i] = (short)0;
							else if(paramClass.equals(int.class)) params[i] = 0;
							else if(paramClass.equals(long.class)) params[i] = 0l;
							else if(paramClass.equals(char.class)) params[i] = '\0';
							else if(paramClass.equals(boolean.class)) params[i] = false;
							else if(paramClass.equals(float.class)) params[i] = 0f;
							else if(paramClass.equals(double.class)) params[i] = 0d;
							else if(paramClass.equals(byte.class)) params[i] = (byte)0;
							else params[i] = null;
						}
						obj = constructor.newInstance(params);
						break;
					}
					catch(Exception e) {}
				}
			}
		}
		return obj;
	}
	
	public static void writeInternalObject(Object obj, OutputStream out) throws Exception
	{
		out.write(getInternalObjectBytes(obj));
	}
	
	public static Object readInternalObject(byte[] objBytes) throws Exception
	{
		return readInternalObject(objBytes, null);
	}
	
	public static Object readInternalObject(byte[] objBytes, ClassLoader classLoader) throws Exception
	{
		if(objBytes==null||objBytes.length==0) return null;
		Object obj = null;
		DataInputStream dis = null;
		try
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
			dis = new DataInputStream(bais);
			obj = readInternalObject(dis, classLoader);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(dis);
		}
		return obj;
	}
	
	public static Object readInternalObject(InputStream in) throws Exception
	{
		return readInternalObject(in, null);
	}
	
	public static Object readInternalObject(InputStream in, ClassLoader classLoader) throws Exception
	{
		Object obj = null;
		DataInputStream dis = null;
		int firstHashcode = 0;
		// 获取所有引用的对象
		Map<Integer, byte[]> hasWriteObjMap = new HashMap<Integer, byte[]>();
		if(in instanceof DataInputStream) dis = (DataInputStream) in;
		else dis = new DataInputStream(in);
		// 读取总长度
		int totalLength = dis.readInt();
		// 读取所有内容
		byte[] totalContent = new byte[totalLength];
		dis.readFully(totalContent, 0, totalLength);
		/*for(int i=0;i<totalLength;i++)
		{
			totalContent[i] = dis.readByte();
		}*/
		DataInputStream tempDis = null;
		try
		{
			tempDis = new DataInputStream(new ByteArrayInputStream(totalContent));
			while(tempDis.available()>0)
			{
				// 读取hashcode
				int hashcode = tempDis.readInt();
				if(firstHashcode==0) firstHashcode = hashcode;
				// 读取长度
				int length = tempDis.readInt();
				// 读取内容
				byte[] content = new byte[length];
				tempDis.readFully(content, 0, length);
				hasWriteObjMap.put(hashcode, content);
			}
			// 生成第一个对象
			Map<Integer, Object> rebuiltObjMap = new HashMap<Integer, Object>();
			if(classLoader==null) classLoader = XlsysClassLoader.getInstance();
			obj = rebuildInternalObject(firstHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(tempDis);
		}
		return obj;
	}
	
	private static Object rebuildInternalObject(int hashcode, Map<Integer, byte[]> hasWriteObjMap, Map<Integer, Object> rebuiltObjMap, ClassLoader classLoader) throws Exception
	{
		Object obj = null;
		if(rebuiltObjMap.containsKey(hashcode)) obj = rebuiltObjMap.get(hashcode);
		else
		{
			byte[] content = hasWriteObjMap.get(hashcode);
			DataInputStream dis = null;
			DataInputStream tempIn = null;
			try
			{
				dis = new DataInputStream(new ByteArrayInputStream(content));
				// 读取类型
				byte type = dis.readByte();
				if(type==XLSYS.DATA_TYPE_NULL)
				{
					// Do Nothing
				}
				else if(type==XLSYS.DATA_TYPE_STR)
				{
					// 读取长度
					int length = dis.readInt();
					// 读取内容
					byte[] bytes = new byte[length];
					dis.readFully(bytes, 0, length);
					obj = new String(bytes, "utf-8");
				}
				else if(type==XLSYS.DATA_TYPE_STR_ARRAY)
				{
					// 数组对象
					// 读取长度
					int length = dis.readInt();
					// 读取内容
					List<String> list = new ArrayList<String>();
					byte[] bytes = new byte[length];
					dis.readFully(bytes, 0, length);
					tempIn = new DataInputStream(new ByteArrayInputStream(bytes));
					while(tempIn.available()>0)
					{
						// 读取元素的hashcode
						int tempHashcode = tempIn.readInt();
						// 添加元素
						list.add((String) rebuildInternalObject(tempHashcode, hasWriteObjMap, rebuiltObjMap, classLoader));
					}
					obj = list.toArray(new String[list.size()]);
				}
				else if(type==XLSYS.DATA_TYPE_SHORT)
				{
					obj = dis.readShort();
				}
				else if(type==XLSYS.DATA_TYPE_INT)
				{
					obj = dis.readInt();
				}
				else if(type==XLSYS.DATA_TYPE_LONG)
				{
					obj = dis.readLong();
				}
				else if(type==XLSYS.DATA_TYPE_CHAR)
				{
					obj = dis.readChar();
				}
				else if(type==XLSYS.DATA_TYPE_BOOL)
				{
					obj = dis.readByte()==1?true:false;
				}
				else if(type==XLSYS.DATA_TYPE_FLOAT)
				{
					obj = dis.readFloat();
				}
				else if(type==XLSYS.DATA_TYPE_DOUBLE)
				{
					obj = dis.readDouble();
				}
				else if(type==XLSYS.DATA_TYPE_BYTE)
				{
					obj = dis.readByte();
				}
				else if(type==XLSYS.DATA_TYPE_BYTE_ARRAY)
				{
					// 读取长度
					int length = dis.readInt();
					// 读取内容
					byte[] bytes = new byte[length];
					dis.readFully(bytes, 0, length);
					obj = bytes;
				}
				else if(type==XLSYS.DATA_TYPE_BIGDECIMAL)
				{
					// 读取数字字符串的hashcode
					int numHashcode = dis.readInt();
					String numStr = (String) rebuildInternalObject(numHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
					obj = new BigDecimal(numStr);
				}
				else if(type==XLSYS.DATA_TYPE_DATE)
				{
					// 读取时间long
					long time = dis.readLong();
					obj = new Date(time);
				}
				else if(type==XLSYS.DATA_TYPE_ENTRY)
				{
					// 读取长度
					int length = dis.readInt();
					// 读取内容
					byte[] bytes = new byte[length];
					dis.readFully(bytes, 0, length);
					tempIn = new DataInputStream(new ByteArrayInputStream(bytes));
					// 读取key
					int tempHashcode = tempIn.readInt();
					Object key = rebuildInternalObject(tempHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
					// 读取value
					tempHashcode = tempIn.readInt();
					Object value = rebuildInternalObject(tempHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
					obj = new AbstractMap.SimpleEntry(key, value);
				}
				else if(type==XLSYS.DATA_TYPE_ENTRY_ARRAY)
				{
					// 数组对象
					// 读取长度
					int length = dis.readInt();
					// 读取内容
					List<Entry> list = new ArrayList<Entry>();
					byte[] bytes = new byte[length];
					dis.readFully(bytes, 0, length);
					tempIn = new DataInputStream(new ByteArrayInputStream(bytes));
					while(tempIn.available()>0)
					{
						// 读取元素的hashcode
						int tempHashcode = tempIn.readInt();
						// 添加元素
						list.add((Entry) rebuildInternalObject(tempHashcode, hasWriteObjMap, rebuiltObjMap, classLoader));
					}
					obj = list.toArray(new Entry[list.size()]);
				}
				else if(type==XLSYS.DATA_TYPE_OBJECT_ARRAY)
				{
					// 数组对象
					// 读取长度
					int length = dis.readInt();
					// 读取内容
					List<Object> list = new ArrayList<Object>();
					byte[] bytes = new byte[length];
					dis.readFully(bytes, 0, length);
					tempIn = new DataInputStream(new ByteArrayInputStream(bytes));
					while(tempIn.available()>0)
					{
						// 读取元素的hashcode
						int tempHashcode = tempIn.readInt();
						// 添加元素
						list.add(rebuildInternalObject(tempHashcode, hasWriteObjMap, rebuiltObjMap, classLoader));
					}
					obj = list.toArray();
				}
				else if(type==XLSYS.DATA_TYPE_COLLECTION)
				{
					// 集合
					// 读取名称
					int nameHashcode = dis.readInt();
					String objClassName = (String) rebuildInternalObject(nameHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
					Class<?> objClass = classLoader.loadClass(objClassName);
					obj = tryInitObj(objClass);
					if(obj==null) throw new UnsupportedException();
					// 先将对象放入重建Map, 以免出现死循环
					rebuiltObjMap.put(hashcode, obj);
					// 读取长度
					int length = dis.readInt();
					// 读取内容
					Collection collection = (Collection) obj;
					byte[] bytes = new byte[length];
					dis.readFully(bytes, 0, length);
					tempIn = new DataInputStream(new ByteArrayInputStream(bytes));
					while(tempIn.available()>0)
					{
						// 读取元素的hashcode
						int tempHashcode = tempIn.readInt();
						// 添加元素
						collection.add(rebuildInternalObject(tempHashcode, hasWriteObjMap, rebuiltObjMap, classLoader));
					}
				}
				else if(type==XLSYS.DATA_TYPE_MAP)
				{
					// Map
					// 读取名称
					int nameHashcode = dis.readInt();
					String objClassName = (String) rebuildInternalObject(nameHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
					Class<?> objClass = classLoader.loadClass(objClassName);
					obj = tryInitObj(objClass);
					if(obj==null) throw new UnsupportedException();
					// 先将对象放入重建Map, 以免出现死循环
					rebuiltObjMap.put(hashcode, obj);
					// 读取长度
					int length = dis.readInt();
					// 读取内容
					Map map = (Map) obj;
					byte[] bytes = new byte[length];
					dis.readFully(bytes, 0, length);
					tempIn = new DataInputStream(new ByteArrayInputStream(bytes));
					while(tempIn.available()>0)
					{
						// 读取key
						int tempHashcode = tempIn.readInt();
						Object key = rebuildInternalObject(tempHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
						// 读取value
						tempHashcode = tempIn.readInt();
						Object value = rebuildInternalObject(tempHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
						// 放入map
						map.put(key, value);
					}
				}
				else if(type==XLSYS.DATA_TYPE_EXCEPTION)
				{
					// Exception
					// 读取名称
					int nameHashcode = dis.readInt();
					String objClassName = (String) rebuildInternalObject(nameHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
					Class<?> objClass = classLoader.loadClass(objClassName);
					// 读取长度
					int length = dis.readInt();
					// 读取内容
					byte[] bytes = new byte[length];
					dis.readFully(bytes, 0, length);
					// 填充对象Field
					tempIn = new DataInputStream(new ByteArrayInputStream(bytes));
					// 读取虚拟field : msg
					// 读取名称(这里不使用)
					int fieldNameHashcode = tempIn.readInt();
					rebuildInternalObject(fieldNameHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
					// 读取内容
					int msgHashcode = tempIn.readInt();
					Object message = rebuildInternalObject(msgHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
					// 读取虚拟field : stackTrace
					// 读取名称(这里不使用)
					fieldNameHashcode = tempIn.readInt();
					rebuildInternalObject(fieldNameHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
					// 读取内容
					int stackTraceHashcode = tempIn.readInt();
					Object stackTrace = rebuildInternalObject(stackTraceHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
					// 读取虚拟field : cause
					// 读取名称(这里不使用)
					fieldNameHashcode = tempIn.readInt();
					rebuildInternalObject(fieldNameHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
					// 读取内容
					int causeHashcode = tempIn.readInt();
					Object cause = rebuildInternalObject(causeHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
					// 创建对象
					Constructor<?> constructor = objClass.getDeclaredConstructor(String.class, Throwable.class);
					constructor.setAccessible(true);
					obj = constructor.newInstance(message, cause);
					Throwable throwable = (Throwable) obj;
					throwable.setStackTrace(ObjectUtil.createStackTraceElement(stackTrace));
					// 先将对象放入重建Map, 以免出现死循环
					rebuiltObjMap.put(hashcode, obj);
				}
				else
				{
					// 为复杂对象类型
					// 读取名称
					int nameHashcode = dis.readInt();
					String objClassName = (String) rebuildInternalObject(nameHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
					Class<?> objClass = classLoader.loadClass(objClassName);
					obj = tryInitObj(objClass);
					if(obj==null) throw new UnsupportedException();
					// 先将对象放入重建Map, 以免出现死循环
					rebuiltObjMap.put(hashcode, obj);
					// 读取长度
					int length = dis.readInt();
					// 读取内容
					byte[] bytes = new byte[length];
					dis.readFully(bytes, 0, length);
					// 填充对象Field
					tempIn = new DataInputStream(new ByteArrayInputStream(bytes));
					while(tempIn.available()>0)
					{
						// 读取Field名称
						int fieldNameHashcode = tempIn.readInt();
						String fieldName = (String) rebuildInternalObject(fieldNameHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
						// 获取Field对象
						int tempHashcode = tempIn.readInt();
						Object fieldObj = rebuildInternalObject(tempHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
						// 设置field到obj对象
						Field field = obj.getClass().getDeclaredField(fieldName);
						if(fieldObj!=null&&field.getType().getName().indexOf('.')>=0&&!field.getType().isAssignableFrom(fieldObj.getClass()))
						{
							LogUtil.printlnError("Type mismatch: hashcode:"+tempHashcode+"\tneed type:"+field.getType().getName()+"\tcurrent type:"+fieldObj.getClass().getName());
						}
						field.setAccessible(true);
						field.set(obj, fieldObj);
					}
				}
				rebuiltObjMap.put(hashcode, obj);
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				IOUtil.close(dis);
				IOUtil.close(tempIn);
			}
		}
		return obj;
	}

	public static byte[] getInternalObjectBytes(Object obj) throws Exception
	{
		// 获取参数的对象中包含的所有对象的字节数组Map,复杂类型均使用hashcode代表
		Map<Integer, byte[]> hasWriteObjMap = new LinkedHashMap<Integer, byte[]>();
		prepareInternalBytes(obj, hasWriteObjMap);
		// 将Map写成byte数组
		byte[] data = null;
		ByteArrayOutputStream baos = null;
		ByteArrayOutputStream out = null;
		try
		{
			baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			for(Entry<Integer, byte[]> entry : hasWriteObjMap.entrySet())
			{
				// 写入hashcode
				dos.writeInt(entry.getKey());
				// 写入长度
				byte[] content = entry.getValue();
				dos.writeInt(content.length);
				// System.out.println("write : hashcode="+entry.getKey()+"\tlength="+content.length);
				// 写入内容
				dos.write(content);
			}
			dos.flush();
			out = new ByteArrayOutputStream();
			DataOutputStream tempOut = new DataOutputStream(out);
			byte[] temp = baos.toByteArray();
			// 写入总长度
			tempOut.writeInt(temp.length);
			// 写入总内容
			tempOut.write(temp);
			// System.out.println("Total length="+temp.length);
			data = out.toByteArray();
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(baos);
			IOUtil.close(out);
		}
		return data;
	}
	
	private static void prepareInternalBytes(Object obj, Map<Integer, byte[]> hasWriteObjMap) throws Exception
	{
		if(hasWriteObjMap.containsKey(getHashCode(obj))) return;
		// 先将当前obj放入,以免子属性递归时重复引用造成无限循环
		hasWriteObjMap.put(getHashCode(obj), null);
		byte[] data = null;
		DataOutputStream out = null;
		DataOutputStream tempOut = null;
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			out = new DataOutputStream(baos);
			if(obj==null)
			{
				// 写入类型
				out.writeByte(XLSYS.DATA_TYPE_NULL);
			}
			else if(obj instanceof String)
			{
				byte[] bytes = ((String)obj).getBytes("utf-8");
				// 写入类型
				out.writeByte(XLSYS.DATA_TYPE_STR);
				// 写入长度
				out.writeInt(bytes.length);
				// 写入内容
				out.write(bytes);
			}
			else if(obj instanceof String[])
			{
				// 数组对象
				String[] objArr = (String[]) obj;
				// 写入类型
				out.writeByte(XLSYS.DATA_TYPE_STR_ARRAY);
				ByteArrayOutputStream baosTemp = new ByteArrayOutputStream();
				tempOut = new DataOutputStream(baosTemp);
				for(String temp : objArr)
				{
					// 写入元素的hashcode
					tempOut.writeInt(getHashCode(temp));
					// 递归遍历元素
					prepareInternalBytes(temp, hasWriteObjMap);
				}
				byte[] arrBytes = baosTemp.toByteArray();
				// 写入长度
				out.writeInt(arrBytes.length);
				// 写入内容
				out.write(arrBytes);
			}
			else if(obj instanceof Short || obj.getClass().equals(short.class))
			{
				out.writeByte(XLSYS.DATA_TYPE_SHORT);
				out.writeShort((short)obj);
			}
			else if(obj instanceof Integer || obj.getClass().equals(int.class))
			{
				out.writeByte(XLSYS.DATA_TYPE_INT);
				out.writeInt((int)obj);
			}
			else if(obj instanceof Long || obj.getClass().equals(long.class))
			{
				out.writeByte(XLSYS.DATA_TYPE_LONG);
				out.writeLong((long)obj);
			}
			else if(obj instanceof Character || obj.getClass().equals(char.class))
			{
				out.writeByte(XLSYS.DATA_TYPE_CHAR);
				out.writeChar((char)obj);
			}
			else if(obj instanceof Boolean || obj.getClass().equals(boolean.class))
			{
				out.writeByte(XLSYS.DATA_TYPE_BOOL);
				out.writeByte(((boolean)obj)?1:0);
			}
			else if(obj instanceof Float || obj.getClass().equals(float.class))
			{
				out.writeByte(XLSYS.DATA_TYPE_FLOAT);
				out.writeFloat((float)obj);
			}
			else if(obj instanceof Double || obj.getClass().equals(double.class))
			{
				out.writeByte(XLSYS.DATA_TYPE_DOUBLE);
				out.writeDouble((double)obj);
			}
			else if(obj instanceof Byte || obj.getClass().equals(byte.class))
			{
				out.writeByte(XLSYS.DATA_TYPE_BYTE);
				out.writeByte((byte)obj);
			}
			else if(obj instanceof byte[])
			{
				out.writeByte(XLSYS.DATA_TYPE_BYTE_ARRAY);
				byte[] bytes = (byte[]) obj;
				out.writeInt(bytes.length);
				out.write(bytes);
			}
			else if(obj instanceof BigDecimal)
			{
				out.writeByte(XLSYS.DATA_TYPE_BIGDECIMAL);
				String numStr = obj.toString();
				// 写入数字文本的hashcode
				out.writeInt(getHashCode(numStr));
				prepareInternalBytes(numStr, hasWriteObjMap);
			}
			else if(obj instanceof Date)
			{
				out.writeByte(XLSYS.DATA_TYPE_DATE);
				// 写入时间long
				out.writeLong(((Date) obj).getTime());
			}
			else if(obj instanceof Entry)
			{
				Entry entry = (Entry) obj;
				// 写入类型
				out.writeByte(XLSYS.DATA_TYPE_ENTRY);
				ByteArrayOutputStream baosTemp = new ByteArrayOutputStream();
				tempOut = new DataOutputStream(baosTemp);
				// 写入Key
				Object key = entry.getKey();
				tempOut.writeInt(getHashCode(key));
				prepareInternalBytes(key, hasWriteObjMap);
				// 写入Value
				Object value = entry.getValue();
				tempOut.writeInt(getHashCode(value));
				prepareInternalBytes(value, hasWriteObjMap);
				byte[] arrBytes = baosTemp.toByteArray();
				// 写入长度
				out.writeInt(arrBytes.length);
				// 写入内容
				out.write(arrBytes);
			}
			else if(obj instanceof Entry[])
			{
				// 数组对象
				Entry[] objArr = (Entry[]) obj;
				// 写入类型
				out.writeByte(XLSYS.DATA_TYPE_ENTRY_ARRAY);
				ByteArrayOutputStream baosTemp = new ByteArrayOutputStream();
				tempOut = new DataOutputStream(baosTemp);
				for(Entry temp : objArr)
				{
					// 写入元素的hashcode
					tempOut.writeInt(getHashCode(temp));
					// 递归遍历元素
					prepareInternalBytes(temp, hasWriteObjMap);
				}
				byte[] arrBytes = baosTemp.toByteArray();
				// 写入长度
				out.writeInt(arrBytes.length);
				// 写入内容
				out.write(arrBytes);
			}
			else if(obj instanceof Object[])
			{
				// 数组对象
				Object[] objArr = (Object[]) obj;
				// 写入类型
				out.writeByte(XLSYS.DATA_TYPE_OBJECT_ARRAY);
				ByteArrayOutputStream baosTemp = new ByteArrayOutputStream();
				tempOut = new DataOutputStream(baosTemp);
				for(Object temp : objArr)
				{
					// 写入元素的hashcode
					tempOut.writeInt(getHashCode(temp));
					// 递归遍历元素
					prepareInternalBytes(temp, hasWriteObjMap);
				}
				byte[] arrBytes = baosTemp.toByteArray();
				// 写入长度
				out.writeInt(arrBytes.length);
				// 写入内容
				out.write(arrBytes);
			}
			else if(obj instanceof Collection)
			{
				// 集合
				Collection<?> collection = (Collection<?>) obj;
				// 写入类型
				out.writeByte(XLSYS.DATA_TYPE_COLLECTION);
				// 写入名称
				String name = collection.getClass().getName();
				out.writeInt(getHashCode(name));
				prepareInternalBytes(name, hasWriteObjMap);
				ByteArrayOutputStream baosTemp = new ByteArrayOutputStream();
				tempOut = new DataOutputStream(baosTemp);
				for(Object temp : collection.toArray())
				{
					// 写入元素的hashcode
					tempOut.writeInt(getHashCode(temp));
					// 递归遍历元素
					prepareInternalBytes(temp, hasWriteObjMap);
				}
				byte[] arrBytes = baosTemp.toByteArray();
				// 写入长度
				out.writeInt(arrBytes.length);
				// 写入内容
				out.write(arrBytes);
			}
			else if(obj instanceof Map)
			{
				// Map
				Map<?, ?> map = (Map<?, ?>) obj;
				// 写入类型
				out.writeByte(XLSYS.DATA_TYPE_MAP);
				// 写入名称
				String name = map.getClass().getName();
				out.writeInt(getHashCode(name));
				prepareInternalBytes(name, hasWriteObjMap);
				ByteArrayOutputStream baosTemp = new ByteArrayOutputStream();
				tempOut = new DataOutputStream(baosTemp);
				for(Entry<?, ?> entry : map.entrySet())
				{
					// 写入Key
					Object key = entry.getKey();
					tempOut.writeInt(getHashCode(key));
					prepareInternalBytes(key, hasWriteObjMap);
					// 写入Value
					Object value = entry.getValue();
					tempOut.writeInt(getHashCode(value));
					prepareInternalBytes(value, hasWriteObjMap);
				}
				byte[] arrBytes = baosTemp.toByteArray();
				// 写入长度
				out.writeInt(arrBytes.length);
				// 写入内容
				out.write(arrBytes);
			}
			else if(obj instanceof Throwable)
			{
				// 写入类型
				out.writeByte(XLSYS.DATA_TYPE_EXCEPTION);
				// 写入名称
				String name = obj.getClass().getName();
				out.writeInt(getHashCode(name));
				prepareInternalBytes(name, hasWriteObjMap);
				// 放入所有的非final以及非transient的Field对象
				ByteArrayOutputStream baosTemp = new ByteArrayOutputStream();
				tempOut = new DataOutputStream(baosTemp);
				// Exception
				Throwable exception = (Throwable) obj;
				// 插入虚拟field : msg
				String fieldName = "msg";
				String message = exception.getMessage();
				if(message==null) message = "";
				// 写入Field名称
				tempOut.writeInt(getHashCode(fieldName));
				prepareInternalBytes(fieldName, hasWriteObjMap);
				// 写入Field的hashcode
				tempOut.writeInt(getHashCode(message));
				prepareInternalBytes(message, hasWriteObjMap);
				// 插入虚拟field : stackTrace
				fieldName = "stackTrace";
				String[] stackTrace = ObjectUtil.getStackTrace(exception);
				// 写入Field名称
				tempOut.writeInt(getHashCode(fieldName));
				prepareInternalBytes(fieldName, hasWriteObjMap);
				// 写入Field的hashcode
				tempOut.writeInt(getHashCode(stackTrace));
				prepareInternalBytes(stackTrace, hasWriteObjMap);
				// 插入虚拟field : cause
				fieldName = "cause";
				Throwable cause = exception.getCause();
				// 写入Field名称
				tempOut.writeInt(getHashCode(fieldName));
				prepareInternalBytes(fieldName, hasWriteObjMap);
				// 写入Field的hashcode
				tempOut.writeInt(getHashCode(cause));
				prepareInternalBytes(cause, hasWriteObjMap);
				// 写入长度
				byte[] fieldsData = baosTemp.toByteArray();
				out.writeInt(fieldsData.length);
				// 写入内容
				out.write(fieldsData);
			}
			else
			{
				// 为复杂对象类型
				// 写入类型
				out.writeByte(XLSYS.DATA_TYPE_OBJECT);
				// 写入名称
				String name = obj.getClass().getName();
				out.writeInt(getHashCode(name));
				prepareInternalBytes(name, hasWriteObjMap);
				// 放入所有的非final以及非transient的Field对象
				ByteArrayOutputStream baosTemp = new ByteArrayOutputStream();
				tempOut = new DataOutputStream(baosTemp);
				Field[] fields = obj.getClass().getDeclaredFields();
				for(Field field : fields)
				{
					if(!Modifier.isFinal(field.getModifiers())&&!Modifier.isTransient(field.getModifiers()))
					{
						field.setAccessible(true);
						// 写入Field名称
						tempOut.writeInt(getHashCode(field.getName()));
						prepareInternalBytes(field.getName(), hasWriteObjMap);
						// 写入Field的hashcode
						Object fieldObj = field.get(obj);
						tempOut.writeInt(getHashCode(fieldObj));
						prepareInternalBytes(fieldObj, hasWriteObjMap);
					}
				}
				// 写入长度
				byte[] fieldsData = baosTemp.toByteArray();
				out.writeInt(fieldsData.length);
				// 写入内容
				out.write(fieldsData);
			}
			data = baos.toByteArray();
			hasWriteObjMap.put(getHashCode(obj), data);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(out);
			IOUtil.close(tempOut);
		}
	}
	
	private static int getHashCode(Object obj)
	{
		if(obj==null)
		{
			obj = NULL_OBJ_HASH_STRING;
		}
		int prime = 31;
		int result = 1;
		result = prime * result
				+ obj.getClass().getName().hashCode();
		result = prime * result
				+ obj.hashCode();
		return result;
	}
	
	public static byte[] getJSONObjectBytes(Object obj) throws Exception
	{
		byte[] data = null;
		ByteArrayOutputStream baos = null;
		try
		{
			String jsonStr = getJSONObjectStr(obj);
			baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			byte[] jsonBytes = jsonStr.getBytes("utf-8");
			// 写入总长度
			dos.writeInt(jsonBytes.length);
			// 写入内容
			dos.write(jsonBytes);
			dos.flush();
			data = baos.toByteArray();
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(baos);
		}
		return data;
	}
	
	public static String getJSONObjectStr(Object obj) throws Exception
	{
		// 获取参数的对象中包含的所有对象的JSON字符串Map,复杂类型均使用hashcode代表
		Map<Integer, JSONObject> hasWriteObjMap = new LinkedHashMap<Integer, JSONObject>();
		prepareJSONStr(obj, hasWriteObjMap);
		JSONArray allJson = new JSONArray();
		Integer[] keys = hasWriteObjMap.keySet().toArray(new Integer[0]);
		for(int i=0;i<keys.length;++i)
		{
			JSONObject jsonObj = new JSONObject();
			// 写入hashcode
			jsonObj.put(XLSYS.JSON_OBJ_HASHCODE, keys[i].intValue());
			// 写入内容
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, hasWriteObjMap.get(keys[i]));
			allJson.put(jsonObj);
		}
		return allJson.toString();
	}
	
	private static void prepareJSONStr(Object obj, Map<Integer, JSONObject> hasWriteObjMap) throws IllegalArgumentException, IllegalAccessException
	{
		if(hasWriteObjMap.containsKey(getHashCode(obj))) return;
		// 先将当前obj放入,以免子属性递归时重复引用造成无限循环
		hasWriteObjMap.put(getHashCode(obj), null);
		JSONObject jsonObj = new JSONObject();
		if(obj==null)
		{
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_NULL);
		}
		else if(obj instanceof String)
		{
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_STR);
			// 写入内容
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, obj.toString());
		}
		else if(obj instanceof byte[])
		{
			// 字节数组
			byte[] bytes = (byte[]) obj;
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_BYTE_ARRAY);
			// 写入内容
			JSONArray jsonArray = new JSONArray();
			for(int i=0;i<bytes.length;++i)
			{
				jsonArray.put(bytes[i]&0x000000ff);
			}
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, jsonArray);
		}
		else if(obj instanceof String[])
		{
			// 数组对象
			String[] strArr = (String[]) obj;
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_STR_ARRAY);
			// 写入内容
			JSONArray jsonArray = new JSONArray();
			for(int i=0;i<strArr.length;++i)
			{
				// 写入元素的hashcode
				jsonArray.put(getHashCode(strArr[i]));
				// 递归遍历元素
				prepareJSONStr(strArr[i], hasWriteObjMap);
			}
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, jsonArray);
		}
		else if(obj instanceof Short || obj.getClass().equals(short.class))
		{
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_SHORT);
			// 写入内容
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, ((short)obj)&0x0000ffff);
		}
		else if(obj instanceof Integer || obj.getClass().equals(int.class))
		{
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_INT);
			// 写入内容
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, (int)obj);
		}
		else if(obj instanceof Long || obj.getClass().equals(long.class))
		{
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_LONG);
			// 写入内容
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, (long)obj);
		}
		else if(obj instanceof Character || obj.getClass().equals(char.class))
		{
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_CHAR);
			// 写入内容
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, ((char)obj)&0x000000ff);
		}
		else if(obj instanceof Boolean || obj.getClass().equals(boolean.class))
		{
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_BOOL);
			// 写入内容
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, (boolean)obj);
		}
		else if(obj instanceof Float || obj.getClass().equals(float.class))
		{
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_FLOAT);
			// 写入内容
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, (float)obj);
		}
		else if(obj instanceof Double || obj.getClass().equals(double.class))
		{
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_DOUBLE);
			// 写入内容
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, (double)obj);
		}
		else if(obj instanceof Byte || obj.getClass().equals(byte.class))
		{
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_BYTE);
			// 写入内容
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, ((byte)obj)&0x000000ff);
		}
		else if(obj instanceof BigDecimal)
		{
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_BIGDECIMAL);
			// 写入内容
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, obj.toString());
		}
		else if(obj instanceof Date)
		{
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_DATE);
			// 写入内容
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, ((Date)obj).getTime());
		}
		else if(obj instanceof Entry)
		{
			Entry entry = (Entry) obj;
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_ENTRY);
			// 写入内容
			JSONArray jsonArray = new JSONArray();
			// 写入Key的hashcode
			Object key = entry.getKey();
			jsonArray.put(getHashCode(key));
			prepareJSONStr(key, hasWriteObjMap);
			// 写入Value的hashcode
			Object value = entry.getValue();
			jsonArray.put(getHashCode(value));
			prepareJSONStr(value, hasWriteObjMap);
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, jsonArray);
		}
		else if(obj instanceof Entry[])
		{
			// 数组对象
			Entry[] objArr = (Entry[]) obj;
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_ENTRY_ARRAY);
			// 写入内容
			JSONArray jsonArray = new JSONArray();
			for(int i=0;i<objArr.length;++i)
			{
				// 写入元素的hashcode
				jsonArray.put(getHashCode(objArr[i]));
				// 递归遍历元素
				prepareJSONStr(objArr[i], hasWriteObjMap);
			}
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, jsonArray);
		}
		else if(obj instanceof Object[])
		{
			// 数组对象
			Object[] objArr = (Object[]) obj;
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_OBJECT_ARRAY);
			// 写入内容
			JSONArray jsonArray = new JSONArray();
			for(int i=0;i<objArr.length;++i)
			{
				// 写入元素的hashcode
				jsonArray.put(getHashCode(objArr[i]));
				// 递归遍历元素
				prepareJSONStr(objArr[i], hasWriteObjMap);
			}
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, jsonArray);
		}
		else if(obj instanceof Collection)
		{
			// 集合
			Collection<?> collection = (Collection<?>) obj;
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_COLLECTION);
			// 写入名称
			jsonObj.put(XLSYS.JSON_OBJ_CLASS, collection.getClass().getName());
			// 写入内容
			JSONArray jsonArray = new JSONArray();
			Object[] objArr = collection.toArray();
			for(int i=0;i<objArr.length;++i)
			{
				// 写入元素的hashcode
				jsonArray.put(getHashCode(objArr[i]));
				// 递归遍历元素
				prepareJSONStr(objArr[i], hasWriteObjMap);
			}
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, jsonArray);
		}
		else if(obj instanceof Map)
		{
			// Map
			Map<?, ?> map = (Map<?, ?>) obj;
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_MAP);
			// 写入名称
			jsonObj.put(XLSYS.JSON_OBJ_CLASS, map.getClass().getName());
			// 写入内容
			JSONArray jsonArray = new JSONArray();
			Object[] keyArr = map.keySet().toArray();
			for(int i=0;i<keyArr.length;++i)
			{
				// 写入Key的hashcode
				jsonArray.put(getHashCode(keyArr[i]));
				prepareJSONStr(keyArr[i], hasWriteObjMap);
				// 写入value的hashcode
				Object value = map.get(keyArr[i]);
				jsonArray.put(getHashCode(value));
				prepareJSONStr(value, hasWriteObjMap);
			}
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, jsonArray);
		}
		else if(obj instanceof Throwable)
		{
			// Exception
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_EXCEPTION);
			// 写入名称
			jsonObj.put(XLSYS.JSON_OBJ_CLASS, obj.getClass().getName());
			// 放入所有的非final以及非transient的Field对象
			JSONObject allField = new JSONObject();
			Throwable exception = (Throwable) obj;
			// 插入虚拟field : msg
			String fieldName = "msg";
			String message = exception.getMessage();
			if(message==null) message = "";
			// 写入内容
			allField.put(fieldName, getHashCode(message));
			prepareJSONStr(message, hasWriteObjMap);	
			// 插入虚拟field : stackTrace
			fieldName = "stackTrace";
			String[] stackTrace = ObjectUtil.getStackTrace(exception);
			// 写入内容
			allField.put(fieldName, getHashCode(stackTrace));
			prepareJSONStr(stackTrace, hasWriteObjMap);
			// 插入虚拟field : cause
			fieldName = "cause";
			Throwable cause = exception.getCause();
			// 写入内容
			allField.put(fieldName, getHashCode(cause));
			prepareJSONStr(cause, hasWriteObjMap);	
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, allField);
		}
		else
		{
			// 为复杂对象类型
			// 写入类型
			jsonObj.put(XLSYS.JSON_OBJ_DATA_TYPE, XLSYS.DATA_TYPE_OBJECT);
			// 写入名称
			jsonObj.put(XLSYS.JSON_OBJ_CLASS, obj.getClass().getName());
			// 放入所有的非final以及非transient的Field对象
			JSONObject allField = new JSONObject();
			Field[] fields = obj.getClass().getDeclaredFields();
			for(Field field : fields)
			{
				if(!Modifier.isFinal(field.getModifiers())&&!Modifier.isTransient(field.getModifiers()))
				{
					field.setAccessible(true);
					// 写入Field名称:hashcode
					Object fieldObj = field.get(obj);
					allField.put(field.getName(), getHashCode(fieldObj));
					prepareJSONStr(fieldObj, hasWriteObjMap);
				}
			}
			jsonObj.put(XLSYS.JSON_OBJ_CONTENT, allField);
		}
		hasWriteObjMap.put(getHashCode(obj), jsonObj);
	}
	
	public static void writeJSONObject(Object obj, OutputStream out) throws Exception
	{
		out.write(getJSONObjectBytes(obj));
	}
	
	public static Object readJSONObject(byte[] objBytes) throws Exception
	{
		return readJSONObject(objBytes, null);
	}
	
	public static Object readJSONObject(byte[] objBytes, ClassLoader classLoader) throws Exception
	{
		Object obj = null;
		DataInputStream dis = null;
		try
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
			dis = new DataInputStream(bais);
			obj = readJSONObject(dis, classLoader);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(dis);
		}
		return obj;
	}
	
	public static Object readJSONObject(InputStream in) throws Exception
	{
		return readJSONObject(in, null);
	}
	
	public static Object readJSONObject(InputStream in, ClassLoader classLoader) throws Exception
	{
		DataInputStream dis = null;
		if(in instanceof DataInputStream) dis = (DataInputStream) in;
		else dis = new DataInputStream(in);
		// 读取总长度
		int totalLength = dis.readInt();
		// 读取所有内容
		byte[] totalContent = new byte[totalLength];
		dis.readFully(totalContent, 0, totalLength);
		String jsonStr = new String(totalContent, "utf-8");
		return readJSONObject(jsonStr, classLoader);
	}
	
	public static Object readJSONObject(String jsonStr) throws Exception
	{
		return readJSONObject(jsonStr, null);
	}
	
	public static Object readJSONObject(String jsonStr, ClassLoader classLoader) throws Exception
	{
		Object obj = null;
		int firstHashcode = 0;
		// 获取所有引用的对象
		Map<Integer, JSONObject> hasWriteObjMap = new HashMap<Integer, JSONObject>();
		JSONArray allJson = new JSONArray(jsonStr);
		for(int i=0;i<allJson.length();++i)
		{
			// 读取hashcode
			JSONObject jsonObj = allJson.getJSONObject(i);
			int hashcode = jsonObj.getInt(XLSYS.JSON_OBJ_HASHCODE);
			if(firstHashcode==0) firstHashcode = hashcode;
			// 读取内容
			JSONObject content = jsonObj.getJSONObject(XLSYS.JSON_OBJ_CONTENT);
			hasWriteObjMap.put(hashcode, content);
		}
		// 生成第一个对象
		Map<Integer, Object> rebuiltObjMap = new HashMap<Integer, Object>();
		if(classLoader==null) classLoader = XlsysClassLoader.getInstance();
		obj = rebuildJSONObject(firstHashcode, hasWriteObjMap, rebuiltObjMap, classLoader);
		return obj;
	}
	
	private static Object rebuildJSONObject(int hashcode, Map<Integer, JSONObject> hasWriteObjMap, Map<Integer, Object> rebuiltObjMap, ClassLoader classLoader) throws Exception
	{
		Object obj = null;
		if(rebuiltObjMap.containsKey(hashcode)) obj = rebuiltObjMap.get(hashcode);
		else
		{
			JSONObject jsonObj = hasWriteObjMap.get(hashcode);
			// 读取类型
			int type = jsonObj.getInt(XLSYS.JSON_OBJ_DATA_TYPE);
			if(type==XLSYS.DATA_TYPE_NULL)
			{
				// Do Nothing
			}
			else if(type==XLSYS.DATA_TYPE_STR)
			{
				// 读取内容
				obj = jsonObj.getString(XLSYS.JSON_OBJ_CONTENT);
			}
			else if(type==XLSYS.DATA_TYPE_STR_ARRAY)
			{
				// 数组对象
				// 读取内容
				JSONArray jsonArray = jsonObj.getJSONArray(XLSYS.JSON_OBJ_CONTENT);
				String[] strArr = new String[jsonArray.length()];
				for(int i=0;i<jsonArray.length();++i)
				{
					int tempHashCode = jsonArray.getInt(i);
					strArr[i] = (String) rebuildJSONObject(tempHashCode, hasWriteObjMap, rebuiltObjMap, classLoader);
				}
				obj = strArr;
			}
			else if(type==XLSYS.DATA_TYPE_SHORT)
			{
				obj = (short) jsonObj.getInt(XLSYS.JSON_OBJ_CONTENT);
			}
			else if(type==XLSYS.DATA_TYPE_INT)
			{
				obj = jsonObj.getInt(XLSYS.JSON_OBJ_CONTENT);
			}
			else if(type==XLSYS.DATA_TYPE_LONG)
			{
				obj = jsonObj.getLong(XLSYS.JSON_OBJ_CONTENT);
			}
			else if(type==XLSYS.DATA_TYPE_CHAR)
			{
				obj = (char) jsonObj.getInt(XLSYS.JSON_OBJ_CONTENT);
			}
			else if(type==XLSYS.DATA_TYPE_BOOL)
			{
				obj = jsonObj.getBoolean(XLSYS.JSON_OBJ_CONTENT);
			}
			else if(type==XLSYS.DATA_TYPE_FLOAT)
			{
				obj = (float) jsonObj.getDouble(XLSYS.JSON_OBJ_CONTENT);
			}
			else if(type==XLSYS.DATA_TYPE_DOUBLE)
			{
				obj = jsonObj.getDouble(XLSYS.JSON_OBJ_CONTENT);
			}
			else if(type==XLSYS.DATA_TYPE_BYTE)
			{
				obj = (byte) jsonObj.getInt(XLSYS.JSON_OBJ_CONTENT);
			}
			else if(type==XLSYS.DATA_TYPE_BYTE_ARRAY)
			{
				JSONArray jsonArr = jsonObj.getJSONArray(XLSYS.JSON_OBJ_CONTENT);
				// 读取内容
				byte[] bytes = new byte[jsonArr.length()];
				for(int i=0;i<jsonArr.length();++i)
				{
					bytes[i] = (byte) jsonArr.getInt(i);
				}
				obj = bytes;
			}
			else if(type==XLSYS.DATA_TYPE_BIGDECIMAL)
			{
				obj = new BigDecimal(jsonObj.getString(XLSYS.JSON_OBJ_CONTENT));
			}
			else if(type==XLSYS.DATA_TYPE_DATE)
			{
				// 读取时间long
				long time = jsonObj.getLong(XLSYS.JSON_OBJ_CONTENT);
				obj = new Date(time);
			}
			else if(type==XLSYS.DATA_TYPE_ENTRY)
			{
				JSONArray jsonArr = jsonObj.getJSONArray(XLSYS.JSON_OBJ_CONTENT);
				// 读取key
				int tempHashCode = jsonArr.getInt(0);
				Object key = rebuildJSONObject(tempHashCode, hasWriteObjMap, rebuiltObjMap, classLoader);
				// 读取value
				tempHashCode = jsonArr.getInt(1);
				Object value = rebuildJSONObject(tempHashCode, hasWriteObjMap, rebuiltObjMap, classLoader);
				obj = new AbstractMap.SimpleEntry(key, value);
			}
			else if(type==XLSYS.DATA_TYPE_ENTRY_ARRAY)
			{
				// 读取内容
				JSONArray jsonArr = jsonObj.getJSONArray(XLSYS.JSON_OBJ_CONTENT);
				Entry[] entrys = new Entry[jsonArr.length()];
				for(int i=0;i<jsonArr.length();++i)
				{
					// 读取元素的hashcode
					int tempHashCode = jsonArr.getInt(i);
					// 添加元素
					entrys[i] = (Entry) rebuildJSONObject(tempHashCode, hasWriteObjMap, rebuiltObjMap, classLoader);
				}
				obj = entrys;
			}
			else if(type==XLSYS.DATA_TYPE_OBJECT_ARRAY)
			{
				// 数组对象
				// 读取内容
				JSONArray jsonArr = jsonObj.getJSONArray(XLSYS.JSON_OBJ_CONTENT);
				Object[] objArr = new Object[jsonArr.length()];
				for(int i=0;i<jsonArr.length();++i)
				{
					// 读取元素的hashcode
					int tempHashCode = jsonArr.getInt(i);
					// 添加元素
					objArr[i] = rebuildJSONObject(tempHashCode, hasWriteObjMap, rebuiltObjMap, classLoader);
				}
				obj = objArr;
			}
			else if(type==XLSYS.DATA_TYPE_COLLECTION)
			{
				// 集合
				// 读取名称
				String objClassName = jsonObj.getString(XLSYS.JSON_OBJ_CLASS);
				Class<?> objClass = classLoader.loadClass(objClassName);
				obj = tryInitObj(objClass);
				if(obj==null) throw new UnsupportedException();
				// 先将对象放入重建Map, 以免出现死循环
				rebuiltObjMap.put(hashcode, obj);
				// 读取内容
				Collection collection = (Collection) obj;
				JSONArray jsonArr = jsonObj.getJSONArray(XLSYS.JSON_OBJ_CONTENT);
				for(int i=0;i<jsonArr.length();++i)
				{
					// 读取元素的hashcode
					int tempHashCode = jsonArr.getInt(i);
					// 添加元素
					collection.add(rebuildJSONObject(tempHashCode, hasWriteObjMap, rebuiltObjMap, classLoader));
				}
			}
			else if(type==XLSYS.DATA_TYPE_MAP)
			{
				// Map
				// 读取名称
				String objClassName = jsonObj.getString(XLSYS.JSON_OBJ_CLASS);
				Class<?> objClass = classLoader.loadClass(objClassName);
				obj = tryInitObj(objClass);
				if(obj==null) throw new UnsupportedException();
				// 先将对象放入重建Map, 以免出现死循环
				rebuiltObjMap.put(hashcode, obj);
				// 读取内容
				Map map = (Map) obj;
				JSONArray jsonArr = jsonObj.getJSONArray(XLSYS.JSON_OBJ_CONTENT);
				for(int i=0;i<jsonArr.length();i+=2)
				{
					// 读取key
					int tempHashCode = jsonArr.getInt(i);
					Object key = rebuildJSONObject(tempHashCode, hasWriteObjMap, rebuiltObjMap, classLoader);
					// 读取value
					tempHashCode = jsonArr.getInt(i+1);
					Object value = rebuildJSONObject(tempHashCode, hasWriteObjMap, rebuiltObjMap, classLoader);
					// 放入map
					map.put(key, value);
				}
			}
			else if(type==XLSYS.DATA_TYPE_EXCEPTION)
			{
				// Exception
				// 读取名称
				String objClassName = jsonObj.getString(XLSYS.JSON_OBJ_CLASS);
				Class<?> objClass = classLoader.loadClass(objClassName);
				// 读取内容
				JSONObject allField = jsonObj.getJSONObject(XLSYS.JSON_OBJ_CONTENT);
				// 读取虚拟field : msg
				String fieldName = "msg";
				int tempHashCode = allField.getInt(fieldName);
				Object message = rebuildJSONObject(tempHashCode, hasWriteObjMap, rebuiltObjMap, classLoader);
				// 读取虚拟field : stackTrace
				fieldName = "stackTrace";
				tempHashCode = allField.getInt(fieldName);
				Object stackTrace = rebuildJSONObject(tempHashCode, hasWriteObjMap, rebuiltObjMap, classLoader);
				// 读取虚拟field : cause
				fieldName = "cause";
				tempHashCode = allField.getInt(fieldName);
				Object cause = rebuildJSONObject(tempHashCode, hasWriteObjMap, rebuiltObjMap, classLoader);
				// 创建对象
				Constructor<?> constructor = objClass.getDeclaredConstructor(String.class, Throwable.class);
				constructor.setAccessible(true);
				obj = constructor.newInstance(message, cause);
				Throwable throwable = (Throwable) obj;
				throwable.setStackTrace(ObjectUtil.createStackTraceElement(stackTrace));
				// 先将对象放入重建Map, 以免出现死循环
				rebuiltObjMap.put(hashcode, obj);
			}
			else
			{
				// 为复杂对象类型
				// 读取名称
				String objClassName = jsonObj.getString(XLSYS.JSON_OBJ_CLASS);
				Class<?> objClass = classLoader.loadClass(objClassName);
				obj = tryInitObj(objClass);
				if(obj==null) throw new UnsupportedException();
				// 先将对象放入重建Map, 以免出现死循环
				rebuiltObjMap.put(hashcode, obj);
				// 读取内容
				JSONObject allField = jsonObj.getJSONObject(XLSYS.JSON_OBJ_CONTENT);
				String[] allFieldNames = JSONObject.getNames(allField);
				if(allFieldNames!=null)
				{
					for(String fieldName : allFieldNames)
					{
						// 获取Field对象
						int tempHashCode = allField.getInt(fieldName);
						Object fieldObj = rebuildJSONObject(tempHashCode, hasWriteObjMap, rebuiltObjMap, classLoader);
						// 设置field到obj对象
						try
						{
							Field field = obj.getClass().getDeclaredField(fieldName);
							if(fieldObj!=null&&!field.getType().isAssignableFrom(fieldObj.getClass()))
							{
								if(field.getType().getName().indexOf('.')>=0)
								{
									LogUtil.printlnError("Type mismatch: hashcode:"+tempHashCode+"\tneed type:"+field.getType().getName()+"\tcurrent type:"+fieldObj.getClass().getName());
								}
								else fieldObj = ObjectUtil.objectCast(fieldObj, field.getType());
							}
							field.setAccessible(true);
							if(fieldObj!=null||field.getType().getName().indexOf('.')>=0) field.set(obj, fieldObj);
						}
						catch(NoSuchFieldException e1) {}
					}
				}
			}
			rebuiltObjMap.put(hashcode, obj);
		}
		return obj;
	}
	
	/**
	 * 将指定个数的字节流从指定的输入流写入到指定的输出流
	 * @param is
	 * @param targetLength 指定的字节个数, 为-1则写入所有
	 * @param os
	 * @throws IOException
	 */
	public static void writeBytesFromIsToOs(InputStream is, int targetLength, OutputStream os) throws IOException
	{
		int bufferLen = 1024;
		int length = -1;
		byte[] b = new byte[bufferLen];
		if(targetLength<0)
		{
			while((length=is.read(b, 0, bufferLen))!=-1)
			{
				os.write(b, 0, length);
			}
		}
		else
		{
			int leftLen = targetLength;
			int curTargetLen = leftLen<bufferLen?leftLen:bufferLen;
			while(curTargetLen>0&&(length=is.read(b, 0, curTargetLen))!=-1)
			{
				os.write(b, 0, length);
				leftLen -= length;
				curTargetLen = leftLen<bufferLen?leftLen:bufferLen;
			}
		}
		os.flush();
	}
	
	/**
	 * 从指定的输入流读入指定的字节个数
	 * @param is 输入流
	 * @param targetLength 字节个数, 如果为-1则读取到输入流的结尾
	 * @return
	 * @throws IOException
	 */
	public static byte[] readBytesFromInputStream(InputStream is, int targetLength) throws IOException
	{
		ByteArrayOutputStream baos = null;
		byte[] ret = null;
		try
		{
			baos = new ByteArrayOutputStream();
			writeBytesFromIsToOs(is, targetLength, baos);
			if(targetLength>=0&&baos.size()!=targetLength) throw new IOException("Cannot read " + targetLength + " bytes from " + is);
			ret = baos.toByteArray();
		}
		finally
		{
			IOUtil.close(baos);
		}
		return ret;
	}

	public static void main(String[] args) throws Exception
	{
		IDataBase dataBase = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(1001)).getNewDataBase();
		IDataSet dataSet = dataBase.sqlSelect("select * from xlsys_user");
		
		String jsonStr = getJSONObjectStr(dataSet);
		DBUtil.close(dataBase);
		
		System.out.println(jsonStr);
		
		dataSet = (IDataSet) readJSONObject(jsonStr);
		DataSetUtil.dumpData(dataSet, true, true);
		System.exit(0);
	}
}
