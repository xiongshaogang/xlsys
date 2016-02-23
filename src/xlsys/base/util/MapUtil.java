package xlsys.base.util;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Map工具
 * @author Lewis
 *
 */
public class MapUtil<K,V>
{
	/**
	 * 尝试获取反向Map.
	 * 将目标的value转换成key, 将目标的key转换成value
	 * @param srcMap 源map
	 * @return 转化后的map
	 */
	public static Map tryToGetReflectMap(Map<?, ?> srcMap)
	{
		Map reflectMap = null;
		try
		{
			reflectMap = srcMap.getClass().newInstance();
			for(Entry<?, ?> entry : srcMap.entrySet())
			{
				reflectMap.put(entry.getValue(), entry.getKey());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return reflectMap;
	}
	
	/**
	 * 获取value为给定值的key
	 * @param map
	 * @param value
	 * @return
	 */
	public static Object getMapKeyFromValue(Map<?, ?> map, Object value)
	{
		Object key = null;
		if(map.containsValue(value))
		{
			for(Entry<?, ?> entry : map.entrySet())
			{
				if(entry.getValue().equals(value))
				{
					key = entry.getKey();
					break;
				}
			}
		}
		return key;
	}
	
	/**
	 * 判断map中是否包含指定的键值对
	 * @param map 要查询的map
	 * @param key 要查询的key
	 * @param value 要查询的value
	 * @param caseSensitive 是否忽略大小写
	 * @return
	 */
	public static boolean hasValue(Map<String, String> map, String key, String value, boolean caseSensitive)
	{
		boolean has = false;
		if(map!=null)
		{
			String temp = map.get(key);
			if(temp==value)
			{
				has = true;
			}
			else if((temp!=null&&temp.equalsIgnoreCase(value))||(value!=null&&value.equalsIgnoreCase(temp)))
			{
				has = true;
			}
		}
		return has;
	}
	
	/**
	 * 判断map中是否包含指定的键值对
	 * @param map 要查询的map
	 * @param key 要查询的key
	 * @param value 要查询的value
	 * @return
	 */
	public static boolean hasValue(Map<?, ?> map, Object key, Object value)
	{
		boolean has = false;
		if(map!=null)
		{
			Object temp = map.get(key);
			if(temp==value)
			{
				has = true;
			}
			else if((temp!=null&&temp.equals(value))||(value!=null&&value.equals(temp)))
			{
				has = true;
			}
		}
		return has;
	}
}
