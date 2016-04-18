
package xlsys.base.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import xlsys.base.exception.UnsupportedException;
import xlsys.base.script.XlsysClassLoader;

/**
 * 对象工具类
 * @author Lewis
 *
 */
public class ObjectUtil
{
	/**
	 * 该方法会复制对象中的所有field, 要复制的对象必须具有无参的构造方法
	 * @param srcObj
	 * @return
	 */
	public static Object cloneSimpleObject(Object srcObj)
	{
		Object newObj = null;
		if(srcObj!=null)
		{
			try
			{
				
				newObj = srcObj.getClass().newInstance();
				Field[] fields = srcObj.getClass().getFields();
				for(Field field : fields)
				{
					if((field.getModifiers()&Modifier.FINAL)==Modifier.FINAL)
					{
						// final字段不复制
						continue;
					}
					field.set(newObj, field.get(srcObj));
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return newObj;
	}
	
	/**
	 * 对象转换
	 * @param srcObj 源对象
	 * @param toJavaClass 目标对象的Java类名称
	 * @return 成功返回目标类型对象,失败返回null
	 * @throws ClassNotFoundException
	 */
	public static Object objectCast(Object srcObj, String toJavaClass) throws ClassNotFoundException
	{
		return objectCast(srcObj, toJavaClass, XlsysClassLoader.getInstance());
	}
	
	/**
	 * 对象转换
	 * @param srcObj 源对象
	 * @param toJavaClass 目标对象的Java类名称
	 * @param classLoader 查找目标类型的类加载器
	 * @return 成功返回目标类型对象,失败返回null
	 * @throws ClassNotFoundException
	 */
	public static Object objectCast(Object srcObj, String toJavaClass, ClassLoader classLoader) throws ClassNotFoundException
	{
		if(srcObj==null) return null;
		if(toJavaClass==null) return srcObj;
		// 对特殊的class做处理
		Class<?> toClass = null;
		if("[B".equals(toJavaClass)) toClass = byte[].class;
		else toClass = classLoader.loadClass(toJavaClass);
		return objectCast(srcObj, toClass);
	}
	
	/**
	 * 对象转换
	 * @param srcObj 源对象
	 * @param toClass 目标对象类型
	 * @return 成功返回目标类型对象,失败返回null
	 */
	public static Object objectCast(Object srcObj, Class<?> toClass)
	{
		if(srcObj==null) return null;
		if(toClass==null) return srcObj;
		Object toObj = null;
		Class<?> srcClass = srcObj.getClass();
		if(!toClass.isAssignableFrom(srcClass))
		{
			// 尝试特殊转换
			if(srcObj instanceof Date&&toClass.getName().equals(Timestamp.class.getName()))
			{
				toObj = new Timestamp(((Date)srcObj).getTime());
			}
			else if(srcObj instanceof String)
			{
				// 针对String做一些特殊转换
				String str = (String) srcObj;
				try
				{
					if(Date.class.isAssignableFrom(toClass))
					{
						toObj = DateUtil.getDate(str, null);
					}
					else if(byte[].class.equals(toClass)||Byte[].class.equals(toClass))
					{
						toObj = str.getBytes("utf-8");
					}
					else if(String[].class.equals(toClass))
					{
						toObj = new String[]{str};
					}
				}
				catch(Exception e){}
			}
			else if(int.class.equals(toClass)||Integer.class.equals(toClass))
			{
				try
				{
					toObj = objectToInt(srcObj);
				}
				catch(Exception e){}
			}
			else if(Date.class.isAssignableFrom(toClass))
			{
				try
				{
					toObj = objectToDate(srcObj);
				}
				catch(Exception e){}
			}
			else if(boolean.class.equals(toClass)||Boolean.class.equals(toClass))
			{
				try
				{
					toObj = objectToBoolean(srcObj);
				}
				catch(Exception e){}
			}
			else if(String.class.equals(toClass))
			{
				try
				{
					toObj = objectToString(srcObj);
				}
				catch(Exception e){}
			}
			// 非特殊类型，则转入通用方式处理
			if(toObj==null)
			{
				// 先尝试通过构造方法转换
				try
				{
					Constructor<?> c = toClass.getDeclaredConstructor(srcClass);
					c.setAccessible(true);
					toObj = c.newInstance(srcObj);
				}
				catch(Exception e){}
			}
			if(toObj==null)
			{
				// 尝试通过valueOf(class)方法转换
				try
				{
					Method m = toClass.getMethod("valueOf", srcClass);
					toObj = m.invoke(null, srcObj);
				}
				catch(Exception e){}
			}
			if(toObj==null)
			{
				// 尝试通过valueOf(String)方法转换
				try
				{
					Method m = toClass.getMethod("valueOf", String.class);
					toObj = m.invoke(null, srcObj.toString());
				}
				catch(Exception e){}
			}
			if(toObj==null)
			{
				// 尝试将srcObj转换成str后再通过toClass的构造方法转换
				try
				{
					Constructor<?> c = toClass.getDeclaredConstructor(String.class);
					c.setAccessible(true);
					toObj = c.newInstance(srcObj.toString());
				}
				catch(Exception e){}
			}
		}
		else
		{
			toObj = srcObj;
		}
		return toObj;
	}
	
	/**
	 * 对象转整型
	 * @param obj 源对象
	 * @return 
	 * @throws UnsupportedException
	 */
	public static Integer objectToInt(Object obj)
	{
		if(obj==null) return null;
		if(obj instanceof String)
		{
			return new BigDecimal(obj.toString().trim()).intValue();
		}
		else if(obj instanceof Number)
		{
			return ((Number) obj).intValue();
		}
		else
		{
			throw new UnsupportedOperationException("Can not transform " + obj + " to int");
		}
	}
	
	public static Double objectToDouble(Object obj) throws UnsupportedException
	{
		if(obj==null) return null;
		if(obj instanceof String)
		{
			return new BigDecimal(obj.toString()).doubleValue();
		}
		else if(obj instanceof Number)
		{
			return ((Number) obj).doubleValue();
		}
		else
		{
			throw new UnsupportedException("Can not transform " + obj + " to double");
		}
	}
	
	public static Byte objectToByte(Object obj) throws NumberFormatException
	{
		if(obj==null) return null;
		else if(obj instanceof Number)
		{
			byte b = ((Number) obj).byteValue();
			return b;
		}
		else if(obj instanceof Byte)
		{
			return (Byte) obj;
		}
		else
		{
			return new Byte(ObjectUtil.objectToString(obj));
		}
	}
	
	/**
	 * 对象转日期类型
	 * @param obj 源对象
	 * @return
	 * @throws UnsupportedException
	 */
	public static Date objectToDate(Object obj) throws UnsupportedException
	{
		Date date = null;
		if(obj instanceof Date)
		{
			date = (Date) obj;
		}
		else
		{
			String dateStr = objectToString(obj);
			if(dateStr!=null)
			{
				SimpleDateFormat sdf = new SimpleDateFormat();
				try
				{
					date = sdf.parse(dateStr);
				}
				catch (ParseException e) {}
				if(date==null)
				{
					String[] formats = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyyMMdd", "yyyyMMddHHmmss"};
					for(String format : formats)
					{
						sdf.applyPattern(format);
						try
						{
							date = sdf.parse(dateStr);
							break;
						}
						catch (ParseException e){}
					}
				}
				if(date==null)
				{
					throw new UnsupportedException("Can not transform " + obj + " to date");
				}
			}
		}
		return date;
	}
	
	/**
	 * 对象转boolean类型
	 * @param obj 源对象
	 * @return
	 */
	public static boolean objectToBoolean(Object obj)
	{
		if(obj==null) return false;
		else if(obj instanceof Boolean) return ((Boolean) obj).booleanValue();
		else if(obj instanceof Number)
		{
			if(((Number) obj).intValue()==0) return false;
			else return true;
		}
		else
		{
			String objStr = obj.toString();
			if(objStr.equalsIgnoreCase("true")) return true;
			else return false;
		}
	}
	
	/**
	 * 对象转字符串类型
	 * @param obj 源对象
	 * @return
	 */
	public static String objectToString(Object obj)
	{
		return objectToString(obj, null);
	}
	
	/**
	 * 对象转字符串类型
	 * @param obj 源对象
	 * @param format 格式
	 * @return
	 */
	public static String objectToString(Object obj, String format)
	{
		String str = null;
		if(obj!=null)
		{
			if(Number.class.isAssignableFrom(obj.getClass()))
			{
				if(format!=null)
				{
					DecimalFormat df = new DecimalFormat(format);
					str = df.format(obj);
				}
				else str = obj.toString();
			}
			else if (obj instanceof Throwable)
			{
				Throwable e = (Throwable) obj;
				str = e.toString() + "\n";
				StackTraceElement[] ste = e.getStackTrace();
				for (int i = 0; i < ste.length; i++)
				{
					str += ste[i].toString();
					if (i != ste.length - 1)
					{
						str += "\n";
					}
				}
				if(e.getCause()!=null) str += "\nFrom Cause : "+objectToString(e.getCause());
			}
			else if(obj instanceof Byte)
			{
				str = new String(new byte[]{((Byte)obj).byteValue()});
			}
			else if(obj instanceof byte[])
			{
				try
				{
					str = new String((byte[])obj, "utf-8");
				}
				catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}
			}
			else if(obj instanceof Date)
			{
				str = DateUtil.getDateString((Date)obj, null);
			}
			else if(obj instanceof Object[])
			{
				StringBuffer sb = new StringBuffer();
				sb.append('[');
				Object[] arr = (Object[]) obj;
				for(int i=0;i<arr.length;i++)
				{
					sb.append(objectToString(arr[i], format));
					if(i!=arr.length-1) sb.append(',');
				}
				sb.append(']');
				str = sb.toString();
			}
			else
			{
				str = obj.toString();
			}
		}
		return str;
	}
	
	public static String[] getStackTrace(Throwable throwable)
	{
		StackTraceElement[] ste = throwable.getStackTrace();
		String[] stackTrace = new String[ste.length];
		for (int i=0;i<ste.length;++i) stackTrace[i] = ste[i].toString();
		return stackTrace;
	}
	
	public static StackTraceElement[] createStackTraceElement(Object stackTrace)
	{
		StackTraceElement[] ste = null;
		if(stackTrace instanceof String[])
		{
			String[] st = (String[]) stackTrace;
			ste = new StackTraceElement[st.length];
			for(int i=0;i<st.length;++i) ste[i] = new StackTraceElement(st[i], "", null, -1);
		}
		return ste;
	}
	
	/**
	 * 查看prop中是否包含二进制位bitProp
	 * @param prop
	 * @param bitProp
	 * @return
	 */
	public static boolean hasBitProp(int prop, int bitProp)
	{
		return (prop&bitProp)==bitProp;
	}
}
