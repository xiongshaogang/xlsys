package xlsys.base.buffer;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import xlsys.base.exception.ParameterNotEnoughException;

/**
 * 多层LRU算法缓冲池,注意：此实践是同步的
 * @author Lewis
 * 
 */
public class MutiLRUBufferPool<K, V> implements BufferPool<K, V>
{
	// 每个值的热度
	private Map<K, Integer> hotMap;
	// 每个链表的长度
	private int[] listSize;
	// 每个链表的最高热度
	private int[] hotLevel;
	// 缓存链表
	private LinkedList<K>[] lists;
	// 缓存数据
	private Map<K, V> valueMap;
	// 监听器
	private Set<LRUBufferListener<K, V>> listenerSet;

	public MutiLRUBufferPool() throws ParameterNotEnoughException
	{
		this(100, 2, new int[] { 3, 1 }, new int[] { 1 });
	}

	public MutiLRUBufferPool(int bufferSize) throws ParameterNotEnoughException
	{
		this(bufferSize, 1, null, null);
	}

	/**
	 * @param 
	 * bufferSize : 总的缓冲区大小(并非所占空间大小，而是缓冲个数),如果缓冲区大小小于所有权重之和，则缓冲区自动等于缓冲权重之和 
	 * listNum : 使用的层级数量 
	 * weight : 各层级的权重，如果权重数量于层级数量不匹配则抛出 
	 * hotLevel: 各层热度最高值定义，如果高于该值，则会将元素传递给下一层，该参数的个数应该总是等于listNum-1
	 */
	@SuppressWarnings("unchecked")
	public MutiLRUBufferPool(int bufferSize, int listNum, int[] weight,
			int[] hotLevel) throws ParameterNotEnoughException
	{
		if (listNum <= 0)
			listNum = 1;
		if (weight == null)
			weight = new int[0];
		if (hotLevel == null)
			hotLevel = new int[0];
		if (listNum > 1
				&& (weight.length < listNum || hotLevel.length < listNum - 1))
		{
			throw new ParameterNotEnoughException();
		}
		hotMap = new HashMap<K, Integer>();
		valueMap = new HashMap<K, V>();
		lists = new LinkedList[listNum];
		listSize = new int[listNum];
		this.hotLevel = Arrays.copyOf(hotLevel, listNum - 1);
		int totleWeight = 0;
		for (int i = 0; i < weight.length; i++)
		{
			totleWeight += weight[i];
		}
		if (bufferSize < totleWeight)
			bufferSize = totleWeight;
		if (listNum == 1)
			listSize[0] = bufferSize;
		else
		{
			int baseSize = bufferSize / totleWeight;
			int tempSize = 0;
			for (int i = listNum - 1; i > 0; i--)
			{
				listSize[i] = weight[i] * baseSize;
				tempSize += listSize[i];
			}
			listSize[0] = bufferSize - tempSize;
		}
		for (int i = 0; i < listNum; i++)
		{
			lists[i] = new LinkedList<K>();
		}
	}

	/**
	 * 注意此方法不会增加热度
	 */
	@Override
	public synchronized V put(K key, V value)
	{
		if (!valueMap.containsKey(key))
		{
			// 往第一个链表中添加值，并且初始化热度计数
			putToList(key, 0);
			hotMap.put(key, 0);
			fireEntryAdded(key, value);
		}
		valueMap.put(key, value);
		return value;
	}

	/**
	 * 此方法不会增加热度
	 * @param key
	 * @return
	 */
	public synchronized V getWithOutHeatIncrease(K key)
	{
		return valueMap.get(key);
	}
	
	/**
	 * 此方法会增加热度
	 */
	@Override
	public synchronized V get(Object k)
	{
		V value = null;
		K key = (K) k;
		for (int i = lists.length - 1; i >= 0; i--)
		{
			int idx = lists[i].indexOf(key);
			if (idx != -1)
			{
				value = valueMap.get(key);
				// 增加热度值
				int hotCount = hotMap.get(key);
				hotMap.put(key, ++hotCount);
				if (lists.length > 1 && i != lists.length - 1
						&& hotCount > hotLevel[i])
				{
					// 如果当前列表有多个并且不是最后一个列表，并且该Key的热度值已经大于当前列表的最大热度值
					// 移动当前Key到下一级列表
					lists[i].remove(idx);
					putToList(key, i + 1);
					// 将当前Key的热度计数设置为0
					hotMap.put(key, 0);
				}
				else
				{
					// 向链表前移动
					lists[i].remove(idx);
					lists[i].addFirst(key);
				}
				break;
			}
		}
		return value;
	}

	private void putToList(K key, int listIdx)
	{
		lists[listIdx].addFirst(key);
		if (lists[listIdx].size() > listSize[listIdx])
		{
			K overdue = lists[listIdx].getLast();
			V value = valueMap.get(overdue);
			lists[listIdx].removeLast();
			hotMap.remove(overdue);
			valueMap.remove(overdue);
			entryRemoved(overdue, value);
		}
	}
	
	@Override
	public synchronized void clear()
	{
		Map<K, V> tempMap = null;
		if(!valueMap.isEmpty())
		{
			tempMap = new HashMap<K, V>();
			tempMap.putAll(valueMap);
		}
		hotMap.clear();
		valueMap.clear();
		for(LinkedList<K> list : lists)
		{
			list.clear();
		}
		if(tempMap!=null)
		{
			for(K key : tempMap.keySet())
			{
				entryRemoved(key, tempMap.get(key));
			}
		}
	}
	
	public void addListener(LRUBufferListener<K, V> listener)
	{
		if(listenerSet==null) listenerSet = new LinkedHashSet<LRUBufferListener<K, V>>();
		listenerSet.add(listener);
	}

	private void fireEntryAdded(K key, V value)
	{
		if(listenerSet!=null)
		{
			for(LRUBufferListener<K, V> listener : listenerSet)
			{
				listener.entryAdded(key, value);
			}
		}
	}
	
	private void entryRemoved(K key, V value)
	{
		if(listenerSet!=null)
		{
			for(LRUBufferListener<K, V> listener : listenerSet)
			{
				listener.entryRemoved(key, value);
			}
		}
	}
	
	@Override
	public boolean containsKey(Object key)
	{
		return valueMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value)
	{
		return valueMap.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet()
	{
		return valueMap.entrySet();
	}

	@Override
	public boolean isEmpty()
	{
		return valueMap.isEmpty();
	}

	@Override
	public Set<K> keySet()
	{
		return valueMap.keySet();
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m)
	{
		for(K key : m.keySet()) put(key, m.get(key));
	}

	@Override
	public V remove(Object key)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int size()
	{
		return valueMap.size();
	}

	@Override
	public Collection<V> values()
	{
		return valueMap.values();
	}
	
	public static void main(String[] args) throws ParameterNotEnoughException
	{
		BufferPool<Integer, String> myBufPool = new MutiLRUBufferPool<Integer, String>(
				6, 2, new int[] { 1, 1 }, new int[] { 1 });
		myBufPool.put(1, "1");
		myBufPool.put(2, "2");
		myBufPool.put(3, "3");
		myBufPool.put(4, "4");
		myBufPool.get(2);
		myBufPool.put(1, "1");
		myBufPool.get(2);
		myBufPool.get(2);
		myBufPool.get(3);
		myBufPool.put(5, "5");
		myBufPool.get(1);
		myBufPool.get(1);
		myBufPool.put(3, "3");
		myBufPool.get(5);
		myBufPool.get(5);
		myBufPool.put(4, "4");
		myBufPool.get(3);
		myBufPool.get(3);
	}
}
