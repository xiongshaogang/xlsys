package xlsys.base.buffer;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import xlsys.base.XLSYS;
import xlsys.base.database.EnvDataBase;
import xlsys.base.database.IEnvDataBase;
import xlsys.base.database.util.DBUtil;
import xlsys.base.log.LogUtil;
import xlsys.base.model.IModel;
import xlsys.base.model.IModelCreator;
import xlsys.base.model.TrisModel;
import xlsys.base.session.Session;
import xlsys.base.util.ObjectUtil;
import xlsys.base.util.ReflectUtil;

public abstract class ModelBuffer implements XlsysBuffer
{
	public static final String BUFFER_KEY_BUFFER_NAME = "_BUFFER_KEY_BUFFER_NAME";
	
	private Map<String, TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>>> bindModelInfoMap;
	
	/**
	 * 第一层 : envId, 第二层
	 * 第二层 : bufferName, 第三层
	 * 第三层 : 自定义Map, key和value都继承自Serializable
	 */
	protected Map<Integer, Map<String, Map<? extends Serializable, ? extends Serializable>>> allEnvBufferMap;
	
	protected ModelBuffer()
	{
		allEnvBufferMap = new HashMap<Integer, Map<String, Map<? extends Serializable, ? extends Serializable>>>();
		bindModelInfoMap = new HashMap<String, TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>>>();
	}
	
	protected Map<? extends Serializable, ? extends Serializable> getBufferMap(int envId, String bufferName)
	{
		return getBufferMap(envId, bufferName, LinkedHashMap.class);
	}

	protected synchronized Map<? extends Serializable, ? extends Serializable> getBufferMap(int envId, String bufferName, Class<? extends Map> mapClass, Object ...params)
	{
		Map<String, Map<? extends Serializable, ? extends Serializable>> envBufferMap = allEnvBufferMap.get(envId);
		if(envBufferMap==null)
		{
			envBufferMap = new HashMap<String, Map<? extends Serializable, ? extends Serializable>>();
			allEnvBufferMap.put(envId, envBufferMap);
		}
		Map<? extends Serializable, ? extends Serializable> bufferMap = envBufferMap.get(bufferName);
		if(bufferMap==null)
		{
			try
			{
				if(params!=null)
				{
					Constructor<?>[] constructors = mapClass.getConstructors();
					for(Constructor<?> constructor : constructors)
					{
						Class<?>[] paramTypes = constructor.getParameterTypes();
						boolean matched = true;
						if(paramTypes.length==params.length)
						{
							for(int i=0;i<params.length;++i)
							{
								if(!ReflectUtil.isAssignableFrom(paramTypes[i], params[i].getClass()))
								{
									matched = false;
									break;
								}
							}
						}
						else matched = false;
						if(matched)
						{
							bufferMap = (Map<? extends Serializable, ? extends Serializable>) constructor.newInstance(params);
							break;
						}
					}
				}
				else bufferMap = mapClass.newInstance();
			}
			catch (Exception e)
			{
				bufferMap = new LinkedHashMap<Serializable, Serializable>();
			}
			envBufferMap.put(bufferName, bufferMap);
		}
		return bufferMap;
	}
	
	protected <M extends IModel, K extends Serializable> void initAllModels(String bufferName, IEnvDataBase dataBase) throws Exception
	{
		TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>> modelInfo = bindModelInfoMap.get(bufferName);
		if(modelInfo==null) throw new RuntimeException("Please bind model info first!");
		initAllModels(bufferName, dataBase, (Class<M>)modelInfo.first, (Class<K>)modelInfo.second, (IModelCreator<M, K>)modelInfo.third);
	}
	
	protected <M extends IModel, K extends Serializable> void initAllModels(String bufferName, IEnvDataBase dataBase, Class<M> modelClass, Class<K> keyClass, IModelCreator<M, K> modelCreator) throws Exception
	{
		Map<K, M> modelMap = (Map<K, M>) getBufferMap(dataBase.getEnvId(), bufferName);
		synchronized(modelMap)
		{
			if(modelMap.isEmpty())
			{
				modelMap.putAll(modelCreator.createAllModels(dataBase));
			}
		}
	}
	
	protected <M extends IModel, K extends Serializable> void bindModelInfoToBufferName(String bufferName, Class<M> modelClass, Class<K> keyClass, IModelCreator<M, K> modelCreator)
	{
		bindModelInfoMap.put(bufferName, new TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>>(modelClass, keyClass, modelCreator));
	}
	
	public <M extends IModel, K extends Serializable> List<M> getAllModels(String bufferName, Session session) throws Exception
	{
		TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>> modelInfo = bindModelInfoMap.get(bufferName);
		if(modelInfo==null) throw new RuntimeException("Please bind model info first!");
		return getAllModels(bufferName, session, (Class<M>)modelInfo.first, (Class<K>)modelInfo.second, (IModelCreator<M, K>)modelInfo.third);
	}
	
	public <M extends IModel, K extends Serializable> List<M> getAllModels(String bufferName, Session session, Class<M> modelClass, Class<K> keyClass, IModelCreator<M, K> modelCreator) throws Exception
	{
		int envId = ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
		return getAllModels(bufferName, envId, modelClass, keyClass, modelCreator);
	}
	
	public <M extends IModel, K extends Serializable> List<M> getAllModels(String bufferName, int envId) throws Exception
	{
		TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>> modelInfo = bindModelInfoMap.get(bufferName);
		if(modelInfo==null) throw new RuntimeException("Please bind model info first!");
		return getAllModels(bufferName, envId, (Class<M>)modelInfo.first, (Class<K>)modelInfo.second, (IModelCreator<M, K>)modelInfo.third);
	}
	
	public <M extends IModel, K extends Serializable> List<M> getAllModels(String bufferName, int envId, Class<M> modelClass, Class<K> keyClass, IModelCreator<M, K> modelCreator) throws Exception
	{
		Map<K, M> modelMap = (Map<K, M>) getBufferMap(envId, bufferName);
		List<M> modelList = null;
		synchronized(modelMap)
		{
			if(modelMap.isEmpty())	
			{
				IEnvDataBase dataBase = null;
				try
				{
					dataBase = EnvDataBase.getInstance(envId);
					initAllModels(bufferName, dataBase, modelClass, keyClass, modelCreator);
				}
				catch(Exception e)
				{
					LogUtil.printlnError(e);
					throw e;
				}
				finally
				{
					DBUtil.close(dataBase);
				}
			}
			modelList = new ArrayList<M>();
			modelList.addAll(modelMap.values());
		}
		return modelList;
	}
	
	public <M extends IModel, K extends Serializable> List<M> getAllModels(String bufferName, IEnvDataBase dataBase) throws Exception
	{
		TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>> modelInfo = bindModelInfoMap.get(bufferName);
		if(modelInfo==null) throw new RuntimeException("Please bind model info first!");
		return getAllModels(bufferName, dataBase, (Class<M>)modelInfo.first, (Class<K>)modelInfo.second, (IModelCreator<M, K>)modelInfo.third);
	}
	
	public <M extends IModel, K extends Serializable> List<M> getAllModels(String bufferName, IEnvDataBase dataBase, Class<M> modelClass, Class<K> keyClass, IModelCreator<M, K> modelCreator) throws Exception
	{
		Map<K, M> modelMap = (Map<K, M>) getBufferMap(dataBase.getEnvId(), bufferName);
		List<M> modelList = null;
		synchronized(modelMap)
		{
			if(modelMap.isEmpty()) initAllModels(bufferName, dataBase, modelClass, keyClass, modelCreator);
			modelList = new ArrayList<M>();
			modelList.addAll(modelMap.values());
		}
		return modelList;
	}
	
	public <M extends IModel, K extends Serializable> M getModel(String bufferName, Session session, K key) throws Exception
	{
		TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>> modelInfo = bindModelInfoMap.get(bufferName);
		if(modelInfo==null) throw new RuntimeException("Please bind model info first!");
		return getModel(bufferName, session, key, (Class<M>)modelInfo.first, (Class<K>)modelInfo.second, (IModelCreator<M, K>)modelInfo.third);
	}
	
	public <M extends IModel, K extends Serializable> M getModel(String bufferName, Session session, K key, Class<M> modelClass, Class<K> keyClass, IModelCreator<M, K> modelCreator) throws Exception
	{
		int envId = ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
		return getModel(bufferName, envId, key, modelClass, keyClass, modelCreator);
	}
	
	public <M extends IModel, K extends Serializable> M getModel(String bufferName, int envId, K key) throws Exception
	{
		TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>> modelInfo = bindModelInfoMap.get(bufferName);
		if(modelInfo==null) throw new RuntimeException("Please bind model info first!");
		return getModel(bufferName, envId, key, (Class<M>)modelInfo.first, (Class<K>)modelInfo.second, (IModelCreator<M, K>)modelInfo.third);
	}
	
	public <M extends IModel, K extends Serializable> M getModel(String bufferName, int envId, K key, Class<M> modelClass, Class<K> keyClass, IModelCreator<M, K> modelCreator) throws Exception
	{
		if(key==null) return null;
		M model = null;
		Map<K, M> modelMap = (Map<K, M>) getBufferMap(envId, bufferName);
		synchronized(modelMap)
		{
			model = modelMap.get(key);
			if(model==null)
			{
				IEnvDataBase dataBase = null;
				try
				{
					dataBase = EnvDataBase.getInstance(envId);
					model = getModel(bufferName, dataBase, key, modelClass, keyClass, modelCreator);
				}
				catch(Exception e)
				{
					LogUtil.printlnError(e);
					throw e;
				}
				finally
				{
					DBUtil.close(dataBase);
				}
			}
		}
		return model;
	}
	
	public <M extends IModel, K extends Serializable> M getModel(String bufferName, IEnvDataBase dataBase, K key) throws Exception
	{
		TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>> modelInfo = bindModelInfoMap.get(bufferName);
		if(modelInfo==null) throw new RuntimeException("Please bind model info first!");
		return getModel(bufferName, dataBase, key, (Class<M>)modelInfo.first, (Class<K>)modelInfo.second, (IModelCreator<M, K>)modelInfo.third);
	}
	
	public <M extends IModel, K extends Serializable> M getModel(String bufferName, IEnvDataBase dataBase, K key, Class<M> modelClass, Class<K> keyClass, IModelCreator<M, K> modelCreator) throws Exception
	{
		if(key==null) return null;
		M model = null;
		Map<K, M> modelMap = (Map<K, M>) getBufferMap(dataBase.getEnvId(), bufferName);
		model = modelMap.get(key);
		if(model==null)
		{
			synchronized(modelMap)
			{
				model = modelMap.get(key);
				if(model==null)
				{
					model = modelCreator.createModel(dataBase, key);
					modelMap.put(key, model);
				}
			}
		}
		return model;
	}
	
	@Override
	public void loadAllData()
	{
		allEnvBufferMap.clear();
	}

	@Override
	public void loadData(Map<String, Serializable> paramMap)
	{
		if(paramMap!=null)
		{
			if(paramMap.containsKey(BUFFER_KEY_ENVID))
			{
				int envId = ObjectUtil.objectToInt(paramMap.get(BUFFER_KEY_ENVID));
				if(paramMap.containsKey(BUFFER_KEY_BUFFER_NAME))
				{
					String bufferName = ObjectUtil.objectToString(paramMap.get(BUFFER_KEY_BUFFER_NAME));
					Map<String, Map<? extends Serializable, ? extends Serializable>> envBufferMap = allEnvBufferMap.get(envId);
					if(envBufferMap!=null) envBufferMap.remove(bufferName);
				}
				else allEnvBufferMap.remove(envId);
			}
		}
		else loadAllData();
	}
}
