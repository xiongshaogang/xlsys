package xlsys.base.buffer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 基于HashMap实现的无限制缓冲池
 * @author Lewis
 *
 * @param <K>
 * @param <V>
 */
public class MapBufferPool<K, V> implements BufferPool<K, V>
{
	private Map<K, V> map;
	
	public MapBufferPool()
	{
		map = new HashMap<K, V>();
	}

	@Override
	public void clear()
	{
		map.clear();
	}

	@Override
	public boolean containsKey(Object key)
	{
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value)
	{
		return map.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet()
	{
		return map.entrySet();
	}

	@Override
	public V get(Object key)
	{
		return map.get(key);
	}

	@Override
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	@Override
	public Set<K> keySet()
	{
		return map.keySet();
	}

	@Override
	public V put(K key, V value)
	{
		return map.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m)
	{
		map.putAll(m);
	}

	@Override
	public V remove(Object key)
	{
		return map.remove(key);
	}

	@Override
	public int size()
	{
		return map.size();
	}

	@Override
	public Collection<V> values()
	{
		return map.values();
	}
}
