package xlsys.base.buffer;

import java.io.Serializable;
import java.util.Map;

public abstract class UnstorageBuffer extends AbstractBuffer
{
	@Override
	public Serializable getStorageObject(int envId, String bufferName)
	{
		return (Serializable) getBufferMap(envId, bufferName);
	}

	@Override
	public boolean isBufferComplete(int envId, String bufferName)
	{
		return false;
	}

	@Override
	public void reloadDataDirectly(int envId, String bufferName, Map<String, Object> paramMap, boolean forceLoad)
	{
		getBufferMap(envId, bufferName).clear();
	}

	@Override
	public boolean loadDataFromStorageObject(int envId, String bufferName, Serializable storageObj)
	{
		return false;
	}

	@Override
	public Serializable doGetBufferData(int envId, String bufferName, Map<String, Object> paramMap)
	{
		return (Serializable) getBufferMap(envId, bufferName);
	}
}
