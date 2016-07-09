package xlsys.base.script.util;

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
}
