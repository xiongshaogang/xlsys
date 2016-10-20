package xlsys.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 系统上下文抽象类，系统中所使用的上下文必须是其子类，该类使用树型结构来构造上下文，使用该类获取上下文变量时，会优先获取本身的上下文变量，如果在本上下文环境中找不到，则去父节点获取
 * @author Lewis
 *
 */
public abstract class XlsysContext
{
	private XlsysContext parent;
	private Map<String, Object> defaultMap;
	private Object sourceContext;
	
	/**
	 * 构造一个上下文环境
	 */
	protected XlsysContext()
	{
		defaultMap = new HashMap<String, Object>();
	}
	
	/**
	 * 构造一个带父节点的上下文环境
	 * @param parent 父节点
	 */
	protected XlsysContext(XlsysContext parent)
	{
		defaultMap = new HashMap<String, Object>();
		this.parent = parent;
	}

	/**
	 * 清除当前的上下文环境
	 */
	public final void clear()
	{
		defaultMap.clear();
		doClear();
	}

	/**
	 * 获取源上下文
	 * @return
	 */
	public final Object getSourceContext()
	{
		return sourceContext;
	}

	/**
	 * 设置源上下文
	 * @param sourceContext
	 */
	public final void setSourceContext(Object sourceContext)
	{
		this.sourceContext = sourceContext;
	}

	/**
	 * 清除当前上下文环境的具体行为方法，子类可空实现此方法
	 */
	protected abstract void doClear();
	
	/**
	 * 注意此方法会优先查找当前对象中的Key, 当前对象中找不到, 则在父对象中查找
	 * @param key
	 * @return
	 */
	public final boolean containsKey(String key)
	{
		boolean contains = false;
		contains = doContainsKey(key);
		if(!contains)
		{
			contains = defaultMap.containsKey(key);
			if(!contains&&parent!=null) contains = parent.containsKey(key);
		}
		return contains;
	}
	
	/**
	 * 查看是否包含指定Key时的具体行为方法，子类可空实现此方法(返回false即可)
	 * @param key
	 * @return
	 */
	protected abstract boolean doContainsKey(String key);

	/**
	 * 注意此方法会优先查找当前对象中的Value, 如果当前对象中找不到, 则在父对象中查找
	 * @param value
	 * @return
	 */
	public final boolean containsValue(Object value)
	{
		boolean contains = false;
		contains = doContainsValue(value);
		if(!contains)
		{
			contains = defaultMap.containsValue(value);
			if(!contains&&parent!=null) contains = parent.containsValue(value);
		}
		return contains;
	}
	
	/**
	 * 查看是否包含指定Value时的具体行为方法，子类可空实现此方法(返回false)即可
	 * @param value
	 * @return
	 */
	protected abstract boolean doContainsValue(Object value);

	/**
	 * 获取当前上下文环境中的的所有变量。注意，本方法不会返回父对象中的上下文环境变量
	 * @return
	 */
	public final Set<Entry<String, Object>> entrySet()
	{
		Set<Entry<String, Object>> set = defaultMap.entrySet();
		Set<Entry<String, Object>> subSet = doEntrySet();
		if(subSet!=null) set.addAll(subSet);
		return set;
	}
	
	/**
	 * 获取当前上下文环境变量的具体行为方法，子类可空实现(返回null即可)
	 * @return
	 */
	protected abstract Set<Entry<String, Object>> doEntrySet();
	
	/**
	 * 此方法将返回包含其父对象的所有上下文变量
	 * @return
	 */
	public final Set<Entry<String, Object>> allEntrySet()
	{
		Set<Entry<String, Object>> set = entrySet();
		if(parent!=null) set.addAll(parent.allEntrySet());
		return set;
	}

	/**
	 * 注意此方法会优先查找当前对象中的Key, 如果当前对象中找不到, 则在父对象中查找
	 * @param key
	 * @return
	 */
	public final Object get(String key)
	{
		Object value = doGet(key);
		if(value==null) value = defaultMap.get(key);
		if(value==null&&parent!=null) value = parent.get(key);
		return value;
	}
	
	/**
	 * 获取环境变量的具体行为方法，子类可空实现(返回null即可)
	 * @param key
	 * @return
	 */
	protected abstract Object doGet(String key);

	/**
	 * 判断当前的上下文环境是否为空。注意，此方法不会对父对象进行判断
	 * @return
	 */
	public final boolean isEmpty()
	{
		boolean empty = doIsEmpty();
		if(empty) empty = defaultMap.isEmpty();
		return empty;
	}
	
	/**
	 * 判断当前的上下文环境是否为空的具体行为方法，子类可空实现(返回true即可).
	 * @return
	 */
	protected abstract boolean doIsEmpty();

	/**
	 * 此方法返回包含其父对象是否全部都为空
	 * @return
	 */
	public final boolean isAllEmpty()
	{
		boolean empty = isEmpty();
		if(empty&&parent!=null) empty = parent.isAllEmpty();
		return empty;
	}
	
	/**
	 * 获取所有的key集合，不包含父对象中的环境变量
	 * @return
	 */
	public final Set<String> keySet()
	{
		Set<String> set = new HashSet<String>(defaultMap.keySet());
		Set<String> subSet = doKeySet();
		if(subSet!=null) set.addAll(subSet);
		return set;
	}
	
	/**
	 * 获取所有的key集合的行为方法，子类可以空实现(返回null即可).
	 * @return
	 */
	protected abstract Set<String> doKeySet();
	
	/**
	 * 此方法将返回包含其父对象的所有key
	 * @return
	 */
	public final Set<String> allKeySet()
	{
		Set<String> set = new HashSet<String>(keySet());
		if(parent!=null) set.addAll(parent.allKeySet());
		return set;
	}

	/**
	 * 往环境变量中设置值
	 * @param key
	 * @param value
	 * @return 成功返回true,否则返回false
	 */
	public final boolean put(String key, Object value)
	{
		if(!doPut(key, value)) defaultMap.put(key, value);
		return true;
	}
	
	/**
	 * 往环境变量中设置值的具体行为方法, 子类可空实现(返回false即可)
	 * @param key
	 * @param value
	 * @return 成功返回true,否则返回false
	 */
	protected abstract boolean doPut(String key, Object value);

	/**
	 * 将参数map中的所有变量放入当前的环境变量
	 * @param map
	 * @return 成功返回true,否则返回false
	 */
	public final boolean putAll(Map<? extends String, ? extends Object> map)
	{
		if(!doPutAll(map)) defaultMap.putAll(map);
		return true;
	}
	
	/**
	 * 将参数map中的所有变量放入当前环境变量的具体行为方法, 子类可空实现(返回false即可)
	 * @param map
	 * @return 成功返回true,否则返回false
	 */
	protected abstract boolean doPutAll(Map<? extends String, ? extends Object> map);

	/**
	 * 删除key所对应的环境变量
	 * @param key
	 * @return
	 */
	public final Object remove(Object key)
	{
		Object obj = null;
		if(doRemove(key)==null) obj = defaultMap.remove(key);
		return obj;
	}
	
	/**
	 * 删除key所对应的环境变量的具体行为方法，子类可空实现(返回null即可)
	 * @param key
	 * @return
	 */
	protected abstract Object doRemove(Object key);

	/**
	 * 获取当前环境变量的个数
	 * @return
	 */
	public final int size()
	{
		int s = defaultMap.size();
		s += doSize();
		return s;
	}
	
	/**
	 * 获取当前环境变量的个数的具体行为方法，子类可空实现(返回0即可).
	 * @return
	 */
	protected abstract int doSize();
	
	/**
	 * 注意此方法将返回包含其父对象的元素大小
	 * @return
	 */
	public final int allSize()
	{
		int s = size();
		if(parent!=null) s += parent.allSize();
		return s;
	}
	
	/**
	 * 获取当前环境变量的所有的值的集合
	 * @return
	 */
	public final Collection<Object> values()
	{
		Collection<Object> values = defaultMap.values();
		Collection<Object> subValues = doValues();
		if(subValues!=null) values.addAll(subValues);
		return values;
	}
	
	/**
	 * 获取当前环境变量的所有的值的集合，子类可空实现此方法(返回null即可)
	 * @return
	 */
	protected abstract Collection<Object> doValues();
	
	/**
	 * 注意此方法返回包含其父对象的所有value
	 * @return
	 */
	public final Collection<Object> allValues()
	{
		Collection<Object> values = values();
		if(parent!=null) values.addAll(parent.allValues());
		return values;
	}
	
	/**
	 * 获取默认的环境Map
	 * @return
	 */
	public final Map<String, Object> getDefaultMap()
	{
		return defaultMap;
	}
	
	/**
	 * 获取父对象
	 * @return
	 */
	public final XlsysContext getParent()
	{
		return parent;
	}
}
