package xlsys.base.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.script.XlsysClassLoader;
import xlsys.base.session.Session;
import xlsys.base.session.SessionManager;

/**
 * 反射工具类
 * @author Lewis
 *
 */
public class ReflectUtil
{
	public static Object invoke(String invokeStr) throws Exception
	{
		return invoke(invokeStr, SessionManager.getInstance().getCurrentSession(), XlsysClassLoader.getInstance());
	}
	
	/**
	 * 调用指定的java程序或属性，格式如下：
	 * JavaStaticField:xlsys.test.JavaInvokeTest.testOs
	 * JavaField:xlsys.test.JavaInvokeTest.testName
	 * JavaStaticMethod:xlsys.test.JavaInvokeTest.getTestOs?ccc=3&ddd=4
	 * JavaMethod:xlsys.test.JavaInvokeTest.getTestName?eee=5
	 * @param invokeStr
	 * @return
	 * @throws Exception
	 */
	public static Object invoke(String invokeStr, Session session, ClassLoader classLoader) throws Exception
	{
		Object ret = null;
		if(invokeStr.startsWith(XLSYS.JAVA_STATIC_FIELD_PREFIX))
		{
			invokeStr = invokeStr.substring(XLSYS.JAVA_STATIC_FIELD_PREFIX.length());
			ret = invokeStaticField(invokeStr, session, classLoader);
		}
		else if(invokeStr.startsWith(XLSYS.JAVA_FIELD_PREFIX))
		{
			invokeStr = invokeStr.substring(XLSYS.JAVA_FIELD_PREFIX.length());
			ret = invokeField(invokeStr, session, classLoader);
		}
		else if(invokeStr.startsWith(XLSYS.JAVA_STATIC_METHOD_PREFIX))
		{
			invokeStr = invokeStr.substring(XLSYS.JAVA_STATIC_METHOD_PREFIX.length());
			ret = invokeStaticMethod(invokeStr, session, classLoader);
		}
		else if(invokeStr.startsWith(XLSYS.JAVA_METHOD_PREFIX))
		{
			invokeStr = invokeStr.substring(XLSYS.JAVA_METHOD_PREFIX.length());
			ret = invokeMethod(invokeStr, session, classLoader);
		}
		return ret;
	}

	/**
	 * 或者指定的静态属性的值，不支持传入参数
	 * @param invokeStr
	 * @return
	 * @throws Exception
	 */
	private static Object invokeStaticField(String invokeStr, Session session, ClassLoader classLoader) throws Exception
	{
		Object ret = null;
		if(classLoader==null) classLoader = XlsysClassLoader.getInstance();
		String[] params = invokeStr.split("\\"+XLSYS.COMMAND_QUESTION);
		int lastPointIdx = params[0].lastIndexOf('.');
		String className = params[0].substring(0, lastPointIdx);
		String fieldName = params[0].substring(lastPointIdx+1);
		Field field = classLoader.loadClass(className).getField(fieldName);
		if(Modifier.isStatic(field.getModifiers()))
		{
			ret = field.get(null);
		}
		return ret;
	}
	
	/**
	 * 获取指定属性的值，指定的类会自动初始化实例，初始化可使用带一个session的构造方法或无参的构造方法，不支持参数
	 * @param invokeStr
	 * @return
	 * @throws Exception
	 */
	private static Object invokeField(String invokeStr, Session session, ClassLoader classLoader) throws Exception
	{
		Object ret = null;
		if(classLoader==null) classLoader = XlsysClassLoader.getInstance();
		String[] params = invokeStr.split("\\"+XLSYS.COMMAND_QUESTION);
		int lastPointIdx = params[0].lastIndexOf('.');
		String className = params[0].substring(0, lastPointIdx);
		String fieldName = params[0].substring(lastPointIdx+1);
		Class<?> classClass = classLoader.loadClass(className);
		Object instance = null;
		try
		{
			Constructor<?> constructor = classClass.getConstructor(Session.class);
			instance = constructor.newInstance(session);
		}
		catch(Exception e){}
		if(instance==null) instance = classClass.newInstance();
		Field field = classClass.getField(fieldName);
		if(!Modifier.isStatic(field.getModifiers()))
		{
			ret = field.get(instance);
		}
		return ret;
	}
	
	/**
	 * 调用静态方法，方法中的参数会按照参数个数自动进行匹配，该方法可自动添加参数session
	 * @param invokeStr
	 * @return
	 * @throws Exception
	 */
	private static Object invokeStaticMethod(String invokeStr, Session session, ClassLoader classLoader) throws Exception
	{
		Object ret = null;
		if(classLoader==null) classLoader = XlsysClassLoader.getInstance();
		String[] params = invokeStr.split("\\"+XLSYS.COMMAND_QUESTION);
		int lastPointIdx = params[0].lastIndexOf('.');
		String className = params[0].substring(0, lastPointIdx);
		String methodName = params[0].substring(lastPointIdx+1);
		Class<?> classClass = classLoader.loadClass(className);
		Method method = null;
		String[] paramArr = null;
		if(params.length>1)
		{
			int matchCount = 0;
			paramArr = params[1].split(XLSYS.COMMAND_AND);
			for(Method m : classClass.getMethods())
			{
				if(m.getName().equals(methodName)&&Modifier.isStatic(m.getModifiers()))
				{
					int paramLen = m.getParameterTypes().length;
					boolean hasSessionParam = false;
					for(Class<?> paramClass : m.getParameterTypes())
					{
						if(Session.class.isAssignableFrom(paramClass))
						{
							hasSessionParam = true;
							break;
						}
					}
					if((hasSessionParam&&paramLen<=paramArr.length+1&&paramLen>matchCount) || (!hasSessionParam&&paramLen<=paramArr.length&&paramLen>matchCount))
					{
						method = m;
						matchCount = paramLen;
					}
				}
			}
		}
		else
		{
			try
			{
				method = classClass.getMethod(methodName);
			}
			catch(Exception e){}
			method = classClass.getMethod(methodName, Session.class);
		}
		Object[] parameters = new Object[method.getParameterTypes().length];
		Class<?>[] paramClass = method.getParameterTypes();
		int j=0;
		for(int i=0;i<parameters.length;i++)
		{
			if(Session.class.isAssignableFrom(paramClass[i]))
			{
				parameters[i] = session;
			}
			else
			{
				String[] values = paramArr[j++].split(XLSYS.COMMAND_RELATION);
				parameters[i] = ObjectUtil.objectCast(values[1], paramClass[i]);
			}
		}
		if(method!=null)
		{
			ret = method.invoke(null, parameters);
		}
		return ret;
	}

	/**
	 * 调用指定的方法，会自动初始化对象实例，使用无参或带有一个session参数的构造方法，参数会使用注入的方式写入对象中，调用的方法为无参或带有一个session参数的方法。
	 * @param invokeStr
	 * @return
	 * @throws Exception
	 */
	private static Object invokeMethod(String invokeStr, Session session, ClassLoader classLoader) throws Exception
	{
		Object ret = null;
		if(classLoader==null) classLoader = XlsysClassLoader.getInstance();
		String[] params = invokeStr.split("\\"+XLSYS.COMMAND_QUESTION);
		int lastPointIdx = params[0].lastIndexOf('.');
		String className = params[0].substring(0, lastPointIdx);
		String methodName = params[0].substring(lastPointIdx+1);
		Class<?> classClass = classLoader.loadClass(className);
		Object instance = null;
		try
		{
			Constructor<?> constructor = classClass.getConstructor(Session.class);
			instance = constructor.newInstance(session);
		}
		catch(Exception e){}
		if(instance==null) instance = classClass.newInstance();
		if(params.length>1)
		{
			String[] paramArr = params[1].split("\\"+XLSYS.COMMAND_QUESTION);
			for(String param : paramArr)
			{
				String[] values = param.split(XLSYS.COMMAND_RELATION);
				Field field = classClass.getField(values[0]);
				field.set(instance, ObjectUtil.objectCast(values[1], field.getType()));
			}
		}
		Method method = null;
		try
		{
			method = classClass.getMethod(methodName);
		}
		catch(Exception e){}
		method = classClass.getMethod(methodName, Session.class);
		if(method!=null)
		{
			if(method.getParameterTypes().length>0)
			{
				ret = method.invoke(instance, session);
			}
			else ret = method.invoke(instance);
		}
		return ret;
	}
	
	public static Object getInstanceFromXm(XmlModel xm, String classNameTag, String paramTag, String fieldTag) throws Exception
	{
		String className = xm.getChild(classNameTag).getText();
		List<XmlModel> paramXmList = xm.getChilds(paramTag);
		List<Entry<Class<?>, Object>> paramList = new ArrayList<Entry<Class<?>, Object>>();
		for(XmlModel paramXm : paramXmList)
		{
			Entry<Class<?>, Object> entry = null;
			String javaClass = paramXm.getAttributeValue("javaClass");
			String paramValue = paramXm.getText();
			if(XlsysClassLoader.getBaseTypeClassMap().containsKey(javaClass))
			{
				Class<?> loadedClass = XlsysClassLoader.getBaseTypeClassMap().get(javaClass);
				if("int".equals(javaClass))
				{
					entry = new AbstractMap.SimpleEntry<Class<?>, Object>(loadedClass, Integer.parseInt(paramValue));
				}
				else if("byte".equals(javaClass))
				{
					entry = new AbstractMap.SimpleEntry<Class<?>, Object>(loadedClass, Byte.parseByte(paramValue));
				}
				else if("char".equals(javaClass))
				{
					entry = new AbstractMap.SimpleEntry<Class<?>, Object>(loadedClass, paramValue.charAt(0));
				}
				else if("short".equals(javaClass))
				{
					entry = new AbstractMap.SimpleEntry<Class<?>, Object>(loadedClass, Short.parseShort(paramValue));
				}
				else if("long".equals(javaClass))
				{
					entry = new AbstractMap.SimpleEntry<Class<?>, Object>(loadedClass, Long.parseLong(paramValue));
				}
				else if("float".equals(javaClass))
				{
					entry = new AbstractMap.SimpleEntry<Class<?>, Object>(loadedClass, Float.parseFloat(paramValue));
				}
				else if("double".equals(javaClass))
				{
					entry = new AbstractMap.SimpleEntry<Class<?>, Object>(loadedClass, Double.parseDouble(paramValue));
				}
				else if("boolean".equals(javaClass))
				{
					entry = new AbstractMap.SimpleEntry<Class<?>, Object>(loadedClass, Boolean.parseBoolean(paramValue));
				}
			}
			else
			{
				Class<?> loadedClass = XlsysClassLoader.getInstance().loadClass(javaClass);
				Object paramObj = ObjectUtil.objectCast(paramValue, loadedClass);
				entry = new AbstractMap.SimpleEntry<Class<?>, Object>(loadedClass, paramObj);
			}
			paramList.add(entry);
		}
		Class<?> objectClass = XlsysClassLoader.getInstance().loadClass(className);
		Object object = null;
		/*if(paramClassMap.isEmpty())
		{
			object = objectClass.newInstance();
		}
		else
		{*/
			Class<?>[] classParams = new Class<?>[paramList.size()];
			Object[] paramsValue = new Object[paramList.size()];
			for(int i=0;i<paramList.size();i++)
			{
				classParams[i] = paramList.get(i).getKey();
				paramsValue[i] = paramList.get(i).getValue();
			}
			Constructor<?> constructor = objectClass.getDeclaredConstructor(classParams);
			constructor.setAccessible(true);
			object = constructor.newInstance(paramsValue);
		//}
		// 注入成员属性
		List<XmlModel> fieldXmList = xm.getChilds(fieldTag);
		for(XmlModel fieldXm : fieldXmList)
		{
			String fieldName = fieldXm.getAttributeValue("name");
			Field field = objectClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			String fieldValueStr = fieldXm.getText();
			Class<?> fieldClass = field.getType();
			Object fieldValue = ObjectUtil.objectCast(fieldValueStr, fieldClass);
			field.set(object, fieldValue);
		}
		return object;
	}
	
	public static Map getStaticFinalField(Class<?> objClass, String prefix) throws IllegalArgumentException, IllegalAccessException
	{
		return getStaticFinalField(objClass, prefix, true);
	}
	
	public static Map getStaticFinalField(Class<?> objClass, String prefix, boolean makeNameKey) throws IllegalArgumentException, IllegalAccessException
	{
		Map fieldMap = new LinkedHashMap();
		initStaticFinalField(fieldMap, objClass, prefix, makeNameKey);
		if(!objClass.isInterface())
		{
			// 从实现接口中获取
			Class<?>[] interfaces = objClass.getInterfaces();
			for(Class<?> i : interfaces)
			{
				initStaticFinalField(fieldMap, i, prefix, makeNameKey);
			}
		}
		return fieldMap;
	}
	
	private static void initStaticFinalField(Map fieldMap, Class<?> objClass, String prefix, boolean makeNameKey) throws IllegalArgumentException, IllegalAccessException
	{
		Field[] fields = objClass.getFields();
		for(Field field : fields)
		{
			int modifiers = field.getModifiers();
			if(field.getName().startsWith(prefix)&&Modifier.isStatic(modifiers)&&Modifier.isFinal(modifiers))
			{
				if(makeNameKey) fieldMap.put(field.getName(), field.get(null));
				else fieldMap.put(field.get(null), field.getName());
			}
		}
	}
	
	public static ISupportValue getISupportValueFromClass(String className, String fieldPrefix) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		Class<?> objClass = XlsysClassLoader.getInstance().loadClass(className);
		final Map fieldMap = getStaticFinalField(objClass, fieldPrefix, false);
		ISupportValue supportValue = new ISupportValue()
		{
			@Override
			public LinkedHashMap<String, String> getSupportValueMap()
			{
				LinkedHashMap<String, String> supportValueMap = new LinkedHashMap<String, String>();
				for(Object key : fieldMap.keySet())
				{
					Object value = fieldMap.get(key);
					supportValueMap.put(ObjectUtil.objectToString(key), ObjectUtil.objectToString(value));
				}
				return supportValueMap;
			}
		};
		return supportValue;
	}
	
	/**
	 * 该方法不仅会在当前class中查找,还会查找其父类中的方法
	 * @return
	 */
	public static Method getDeclaredMethod(Class<?> type, String name, Class<?>... parameterTypes)
	{
		
		Method method = null;
		Class<?> curClass = type;
		do
		{
			try
			{
				// 先获取本类的方法
				method = curClass.getDeclaredMethod(name, parameterTypes);
			}
			catch (Exception e){}
			// 再获取其实现的接口中的方法
			if(method==null)
			{
				Class<?>[] interfaces = curClass.getInterfaces();
				for(Class<?> i : interfaces)
				{
					try
					{
						method = i.getDeclaredMethod(name, parameterTypes);
					}
					catch (Exception e){}
					if(method!=null) break;
				}
			}
		}
		while(method==null&&(curClass=curClass.getSuperclass())!=null);
		return method;
	}
	
	public static <T> T[] toArray(Object[] srcArr, T[] toArr)
	{
		for(int i=0;i<srcArr.length;i++)
		{
			toArr[i] = (T) srcArr[i];
		}
		return toArr;
	}
	
	/**
	 * 获取符合条件的Field
	 * @param type 要查找的类
	 * @param regex field名称要匹配的正则表达式
	 * @param mustHaveModifiers field包含的修饰词
	 * @return
	 */
	public static List<Field> getMatchedFields(Class<?> type, String regex, int ... mustHaveModifier)
	{
		List<Field> list = new ArrayList<Field>();
		Field[] fields = type.getDeclaredFields();
		for(Field field : fields)
		{
			int modifiers = field.getModifiers();
			String fieldName = field.getName();
			if(fieldName.matches(regex))
			{
				boolean containsModifiers = true;
				for(int i=0;i<mustHaveModifier.length;i++)
				{
					if((modifiers&mustHaveModifier[i])!=mustHaveModifier[i])
					{
						containsModifiers = false;
						break;
					}
				}
				if(containsModifiers) list.add(field);
			}
		}
		return list;
	}
	
	private static Class<?> castBasicClassToClass(Class<?> cls)
	{
		if(cls.getName().equals(int.class.getName())) cls = Integer.class;
		else if(cls.getName().equals(short.class.getName())) cls = Short.class;
		else if(cls.getName().equals(long.class.getName())) cls = Long.class;
		else if(cls.getName().equals(float.class.getName())) cls = Float.class;
		else if(cls.getName().equals(double.class.getName())) cls = Double.class;
		else if(cls.getName().equals(char.class.getName())) cls = Character.class;
		else if(cls.getName().equals(boolean.class.getName())) cls = Boolean.class;
		else if(cls.getName().equals(byte.class.getName())) cls = Byte.class;
		return cls;
	}
	
	/**
	 * 在JDK原有方法的基础上增加了对基本类型的直接转换判断
	 * @param obj
	 * @param cls
	 * @return
	 */
	public static boolean isAssignableFrom(Class<?> cls1, Class<?> cls2)
	{
		cls1 = castBasicClassToClass(cls1);
		cls2 = castBasicClassToClass(cls2);
		return cls1.isAssignableFrom(cls2);
	}
}
