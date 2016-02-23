package xlsys.base.buffer;

import java.util.Map;

/**
 * 缓冲池接口，如果要定义缓冲池，必须实现此接口
 * @author Lewis
 *
 * @param <K>
 * @param <V>
 */
public interface BufferPool<K, V> extends Map<K, V>
{
	
}
