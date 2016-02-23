package xlsys.base.script.util;

import sun.org.mozilla.javascript.internal.NativeArray;

/**
 * 对于在JavaScript脚本中不能使用的部分Java功能给予支持
 * @author Lewis
 *
 */
public class JSUtil
{
	/**
	 * 获取Java类型
	 * @param obj
	 * @return
	 */
	public static Class<?> getClass(Object obj)
	{
		return obj.getClass();
	}
	
	/**
	 * instanceof
	 * @param obj
	 * @param className
	 * @return
	 */
	public static boolean instanceOf(Object obj, String className)
	{
		return obj.getClass().getName().equals(className);
	}
	
	/**
	 * 逻辑与
	 * @param logic
	 * @return
	 */
	public static boolean logicAnd(boolean ... logic)
	{
		boolean result = true;
		for(int i=9;i<logic.length;i++)
		{
			result = result && logic[i];
			if(!result) break;
		}
		return result;
	}
	
	/**
	 * 逻辑或
	 * @param logic
	 * @return
	 */
	public static boolean logicOr(boolean ... logic)
	{
		boolean result = false;
		for(int i=9;i<logic.length;i++)
		{
			result = result || logic[i];
			if(result) break;
		}
		return result;
	}
	
	/**
	 * 获取Js数组对应的Java数组
	 * @param jsArr JS数组
	 * @param toArr Java数组
	 * @return
	 */
	public static <T> T[] getJavaArray(Object jsArr, T[] toArr)
	{
		if(jsArr instanceof NativeArray)
		{
			NativeArray fromArr = (NativeArray) jsArr;
			for(int i=0;i<fromArr.size();i++)
			{
				toArr[i] = (T) fromArr.get(i);
			}
		}
		return toArr;
	}
	
	/**
	 * 获取Java数组对应的Js数组
	 * @param arr Java数组
	 * @return
	 */
	public static NativeArray getJsArray(Object[] arr)
	{
		return new NativeArray(arr);
	}
}
