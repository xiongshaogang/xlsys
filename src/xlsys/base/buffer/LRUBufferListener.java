package xlsys.base.buffer;

public interface LRUBufferListener<K, V>
{
	/**
	 * 新元素添加后事件
	 * @param key 新添加的元素key
	 * @param value 新添加的元素值
	 */
	public void entryAdded(K key, V value);
	
	/**
	 * 元素移除后事件
	 * @param key 被移除的元素key
	 * @param value 被移除的元素值
	 */
	public void entryRemoved(K key, V value);
}
