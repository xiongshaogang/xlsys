package xlsys.base.buffer;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xlsys.base.XLSYS;
import xlsys.base.database.EnvDataBase;
import xlsys.base.database.IClientDataBase;
import xlsys.base.database.IDataBase;
import xlsys.base.database.IEnvDataBase;
import xlsys.base.database.util.DBUtil;
import xlsys.base.database.util.TranslateUtil;
import xlsys.base.log.LogUtil;
import xlsys.base.model.IModel;
import xlsys.base.model.IModelCreator;
import xlsys.base.model.TrisModel;
import xlsys.base.session.Session;
import xlsys.base.util.ObjectUtil;

public abstract class ModelBuffer extends AbstractBuffer
{
	public final static String BUFFER_KEY_MODEL_KEY = "_BUFFER_KEY_MODEL_KEY";
	public final static String BUFFER_KEY_DATABASE = "_BUFFER_KEY_DATABASE";
	public final static String BUFFER_KEY_SESSION = "_BUFFER_KEY_SESSION";
	public final static String BUFFER_KEY_ENV_ID = "_BUFFER_KEY_ENV_ID";
	
	/**
	 * 模型信息Map
	 * <bufferName, <modelClass, keyClass, modelCreator>>
	 */
	private Map<String, TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>>> bindModelInfoMap;
	/**
	 * 模型数量Map
	 * <envId, <bufferName, allModelCount>>
	 */
	private Map<Integer, Map<String, Integer>> modelCountMap;
	/**
	 * 模型翻译Map
	 * <envId, <bufferName, <model, <language, transModel>>>>
	 */
	private Map<Integer, Map<String, Map<IModel, Map<String, IModel>>>> modelTransMap;
	
	protected ModelBuffer()
	{
		super();
		bindModelInfoMap = new HashMap<String, TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>>>();
		modelCountMap = new HashMap<Integer, Map<String, Integer>>();
		modelTransMap = new HashMap<Integer, Map<String, Map<IModel, Map<String, IModel>>>>();
	}
	
	protected void initAllModels(String bufferName, IEnvDataBase dataBase) throws Exception
	{
		TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>> modelInfo = bindModelInfoMap.get(bufferName);
		if(modelInfo==null) throw new RuntimeException("Please bind model info first!");
		initAllModels(bufferName, dataBase, modelInfo.first, modelInfo.second, modelInfo.third);
	}
	
	protected void initAllModels(String bufferName, IEnvDataBase dataBase, Class<? extends IModel> modelClass, Class<? extends Serializable> keyClass, IModelCreator<? extends IModel, ? extends Serializable> modelCreator) throws Exception
	{
		int envId = dataBase.getEnvId();
		Map modelMap = getBufferMap(envId, bufferName);
		int allCount = getModelCount(bufferName, dataBase);
		synchronized(modelMap)
		{
			if(allCount==-1||modelMap.size()<allCount)
			{
				modelMap.clear();
				modelMap.putAll(modelCreator.createAllModels(dataBase));
			}
		}
	}
	
	protected <M extends IModel, K extends Serializable> void bindModelInfoToBufferName(String bufferName, Class<M> modelClass, Class<K> keyClass, IModelCreator<M, K> modelCreator)
	{
		bindModelInfoMap.put(bufferName, new TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>>(modelClass, keyClass, modelCreator));
	}
	
	public int getModelCount(String bufferName, Session session) throws Exception
	{
		int envId = ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
		return getModelCount(bufferName, envId);
	}
	
	public int getModelCount(String bufferName, int envId) throws Exception
	{
		Map<String, Integer> countMap = modelCountMap.get(envId);
		if(countMap==null)
		{
			synchronized(modelCountMap)
			{
				countMap = modelCountMap.get(envId);
				if(countMap==null)
				{
					countMap = new HashMap<String, Integer>();
					modelCountMap.put(envId, countMap);
				}
			}
		}
		Integer allCount = countMap.get(bufferName);
		if(allCount==null)
		{
			synchronized(countMap)
			{
				allCount = countMap.get(bufferName);
				if(allCount==null)
				{
					TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>> modelInfo = bindModelInfoMap.get(bufferName);
					if(modelInfo==null) throw new RuntimeException("Please bind model info first!");
					IModelCreator<? extends IModel, ? extends Serializable> modelCreator = modelInfo.third;
					IEnvDataBase dataBase = null;
					try
					{
						dataBase = EnvDataBase.getInstance(envId);
						allCount = modelCreator.getAllCount(dataBase);
						countMap.put(bufferName, allCount);
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
		}
		return allCount;
	}
	
	public int getModelCount(String bufferName, IEnvDataBase dataBase) throws Exception
	{
		int envId = dataBase.getEnvId();
		Map<String, Integer> countMap = modelCountMap.get(envId);
		if(countMap==null)
		{
			synchronized(modelCountMap)
			{
				countMap = modelCountMap.get(envId);
				if(countMap==null)
				{
					countMap = new HashMap<String, Integer>();
					modelCountMap.put(envId, countMap);
				}
			}
		}
		Integer allCount = countMap.get(bufferName);
		if(allCount==null)
		{
			synchronized(countMap)
			{
				allCount = countMap.get(bufferName);
				if(allCount==null)
				{
					TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>> modelInfo = bindModelInfoMap.get(bufferName);
					if(modelInfo==null) throw new RuntimeException("Please bind model info first!");
					IModelCreator<? extends IModel, ? extends Serializable> modelCreator = modelInfo.third;
					allCount = modelCreator.getAllCount(dataBase);
					countMap.put(bufferName, allCount);
				}
			}
		}
		return allCount;
	}
	
	public List<? extends IModel> getAllModels(String bufferName, Session session) throws Exception
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int envId = ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
		paramMap.put(BUFFER_KEY_SESSION, session);
		return (List<? extends IModel>) getBufferData(envId, bufferName, paramMap);
	}
	
	private List<IModel> getAllTranslatedModels(String bufferName, Session session, List<IModel> srcModelList) throws Exception
	{
		if(srcModelList==null) return null;
		// 先翻译当前Model
		List<IModel> transModelList = new ArrayList<IModel>();
		for(int i=0;i<srcModelList.size();++i)
		{
			IModel transModel = this.getTranslatedModel(bufferName, session, srcModelList.get(i));
			transModelList.add(transModel);
		}
		return transModelList;
	}
	
	private List<? extends IModel> doGetAllModels(String bufferName, Session session, Class<? extends IModel> modelClass, Class<? extends Serializable> keyClass, IModelCreator<? extends IModel, ? extends Serializable> modelCreator) throws Exception
	{
		int envId = ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
		List<? extends IModel> modelList = doGetAllModels(bufferName, envId, modelClass, keyClass, modelCreator);
		return getAllTranslatedModels(bufferName, session, (List<IModel>) modelList);
	}
	
	public List<? extends IModel> getAllModels(String bufferName, int envId) throws Exception
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(BUFFER_KEY_ENV_ID, envId);
		return (List<? extends IModel>) getBufferData(envId, bufferName, paramMap);
	}
	
	private List<? extends IModel> doGetAllModels(String bufferName, int envId, Class<? extends IModel> modelClass, Class<? extends Serializable> keyClass, IModelCreator<? extends IModel, ? extends Serializable> modelCreator) throws Exception
	{
		Map modelMap = getBufferMap(envId, bufferName);
		List<? extends IModel> modelList = null;
		synchronized(modelMap)
		{
			int allCount = getModelCount(bufferName, envId);
			if(modelMap.isEmpty()||modelMap.size()<allCount)
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
			modelList = new ArrayList<IModel>();
			modelList.addAll(modelMap.values());
		}
		return modelList;
	}
	
	public List<? extends IModel> getAllModels(String bufferName, IEnvDataBase dataBase) throws Exception
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(BUFFER_KEY_DATABASE, dataBase);
		return (List<? extends IModel>) getBufferData(dataBase.getEnvId(), bufferName, paramMap);
	}
	
	private List<? extends IModel> doGetAllModels(String bufferName, IEnvDataBase dataBase, Class<? extends IModel> modelClass, Class<? extends Serializable> keyClass, IModelCreator<? extends IModel, ? extends Serializable> modelCreator) throws Exception
	{
		Map modelMap = getBufferMap(dataBase.getEnvId(), bufferName);
		List<? extends IModel> modelList = null;
		synchronized(modelMap)
		{
			int allCount = getModelCount(bufferName, dataBase.getEnvId());
			if(modelMap.isEmpty()||modelMap.size()<allCount) initAllModels(bufferName, dataBase, modelClass, keyClass, modelCreator);
			modelList = new ArrayList<IModel>();
			modelList.addAll(modelMap.values());
		}
		return modelList;
	}
	
	public IModel getModel(String bufferName, Session session, Serializable key) throws Exception
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int envId = ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
		paramMap.put(BUFFER_KEY_SESSION, session);
		paramMap.put(BUFFER_KEY_MODEL_KEY, key);
		return (IModel) getBufferData(envId, bufferName, paramMap);
	}
	
	private IModel doGetModel(String bufferName, Session session, Serializable key, Class<? extends IModel> modelClass, Class<? extends Serializable> keyClass, IModelCreator<? extends IModel, ? extends Serializable> modelCreator) throws Exception
	{
		int envId = ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
		IModel model = doGetModel(bufferName, envId, key, modelClass, keyClass, modelCreator);
		return getTranslatedModel(bufferName, session, model);
	}
	
	private void translateSubList(IModel model, int envId, IDataBase dataBase, String language) throws Exception
	{
		Field[] fields = model.getClass().getDeclaredFields();
		for(Field field : fields)
		{
			if(List.class.isAssignableFrom(field.getType()))
			{
				field.setAccessible(true);
				List<?> subList = (List<?>) field.get(model);
				if(subList!=null&&!subList.isEmpty()&&subList.get(0) instanceof IModel)
				{
					List<IModel> translatedList = TranslateUtil.getInstance(language, !(dataBase instanceof IClientDataBase)).translateModelList(envId, dataBase, (List<IModel>) subList);
					field.set(model, translatedList);
				}
			}
		}
	}
	
	private IModel getTranslatedModel(String bufferName, Session session, IModel srcModel) throws Exception
	{
		if(srcModel==null) return null;
		int envId = ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
		String language = (String) session.getAttribute(XLSYS.SESSION_LANGUAGE);
		// 查询翻译缓存中是否存在
		Map<String, Map<IModel, Map<String, IModel>>> envTransMap = modelTransMap.get(envId);
		if(envTransMap==null)
		{
			envTransMap = new HashMap<String, Map<IModel, Map<String, IModel>>>();
			modelTransMap.put(envId, envTransMap);
		}
		Map<IModel, Map<String, IModel>> bnTransMap = envTransMap.get(bufferName);
		if(bnTransMap==null)
		{
			bnTransMap = new HashMap<IModel, Map<String, IModel>>();
			envTransMap.put(bufferName, bnTransMap);
		}
		Map<String, IModel> mTransMap = bnTransMap.get(srcModel);
		if(mTransMap==null)
		{
			mTransMap = new HashMap<String, IModel>();
			bnTransMap.put(srcModel, mTransMap);
		}
		IModel transModel = mTransMap.get(language);
		if(transModel==null)
		{
			IEnvDataBase dataBase = null;
			try
			{
				dataBase = EnvDataBase.getInstance(envId);
				transModel = TranslateUtil.getInstance(language, !(dataBase instanceof IClientDataBase)).translateModel(dataBase, srcModel, language);
				// 查找当前Model的所有属性, 如果属性为List, 并且List中的元素为IModel, 则翻译该属性
				translateSubList(transModel, envId, dataBase, language);
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
		return transModel;
	}
	
	public IModel getModel(String bufferName, int envId, Serializable key) throws Exception
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(BUFFER_KEY_ENV_ID, envId);
		paramMap.put(BUFFER_KEY_MODEL_KEY, key);
		return (IModel) getBufferData(envId, bufferName, paramMap);
	}

	private IModel doGetModel(String bufferName, int envId, Serializable key, Class<? extends IModel> modelClass, Class<? extends Serializable> keyClass, IModelCreator<? extends IModel, ? extends Serializable> modelCreator) throws Exception
	{
		if(key==null) return null;
		IModel model = null;
		Map<? extends Serializable, ? extends IModel> modelMap = (Map<? extends Serializable, ? extends IModel>) getBufferMap(envId, bufferName);
		synchronized(modelMap)
		{
			model = modelMap.get(key);
			if(model==null)
			{
				IEnvDataBase dataBase = null;
				try
				{
					dataBase = EnvDataBase.getInstance(envId);
					model = doGetModel(bufferName, dataBase, key, modelClass, keyClass, modelCreator);
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
	
	public IModel getModel(String bufferName, IEnvDataBase dataBase, Serializable key) throws Exception
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(BUFFER_KEY_DATABASE, dataBase);
		paramMap.put(BUFFER_KEY_MODEL_KEY, key);
		return (IModel) getBufferData(dataBase.getEnvId(), bufferName, paramMap);
	}
	
	private IModel doGetModel(String bufferName, IEnvDataBase dataBase, Serializable key, Class<? extends IModel> modelClass, Class<? extends Serializable> keyClass, IModelCreator<? extends IModel, ? extends Serializable> modelCreator)
	{
		if(key==null) return null;
		IModel model = null;
		Map modelMap = getBufferMap(dataBase.getEnvId(), bufferName);
		model = (IModel) modelMap.get(key);
		if(model==null)
		{
			synchronized(modelMap)
			{
				model = (IModel) modelMap.get(key);
				if(model==null)
				{
					model = modelCreator.createModelObject(dataBase, key);
					modelMap.put(key, model);
				}
			}
		}
		return model;
	}

	@Override
	public Serializable getStorageObject(int envId, String bufferName)
	{
		Map bufferMap = allEnvBufferMap.get(envId).get(bufferName);
		int modelCount = modelCountMap.get(envId).get(bufferName);
		Map transMap = this.modelTransMap.get(envId).get(bufferName);
		Serializable[] storageObject = new Serializable[3];
		storageObject[0] = (Serializable) bufferMap;
		storageObject[1] = modelCount;
		storageObject[2] = (Serializable) transMap;
		return storageObject;
	}

	@Override
	public boolean isBufferComplete(int envId, String bufferName)
	{
		Map<String, Map<? extends Serializable, ? extends Serializable>> envBufferMap = allEnvBufferMap.get(envId);
		Map<String, Integer> countMap = modelCountMap.get(envId);
		if(envBufferMap==null||countMap==null) return false;
		Map<? extends Serializable, ? extends Serializable> bufferMap = envBufferMap.get(bufferName);
		Integer count = countMap.get(bufferName);
		if(bufferMap==null||count==null) return false;
		if(bufferMap.size()==count) return true;
		return false;
	}

	@Override
	public void reloadDataDirectly(int envId, String bufferName, Map<String, Object> paramMap, boolean forceLoad)
	{
		boolean reloadAll = false;
		if(paramMap==null) reloadAll = true;
		else
		{
			Serializable key = (Serializable) paramMap.get(BUFFER_KEY_MODEL_KEY);
			if(key==null) reloadAll = true;
			else
			{
				getBufferMap(envId, bufferName).remove(key);
				if(forceLoad)
				{
					try
					{
						getModel(bufferName, envId, key);
					}
					catch (Exception e)
					{
						throw new RuntimeException(e);
					}
				}
			}
		}
		if(reloadAll)
		{
			getBufferMap(envId, bufferName).clear();
			Map<String, Integer> countMap = modelCountMap.get(envId);
			if(countMap!=null) countMap.remove(bufferName);
			Map<String, Map<IModel, Map<String, IModel>>> envTransMap = this.modelTransMap.get(envId);
			if(envTransMap!=null) envTransMap.remove(bufferName);
			if(forceLoad)
			{
				try
				{
					getAllModels(bufferName, envId);
				}
				catch (Exception e)
				{
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	public boolean loadDataFromStorageObject(int envId, String bufferName, Serializable storageObj)
	{
		Serializable[] objArr = (Serializable[]) storageObj;
		Map bufferMap = (Map) objArr[0];
		int modelCount = ObjectUtil.objectToInt(objArr[1]);
		Map transMap = (Map) objArr[2];
		Map curBufferMap = getBufferMap(envId, bufferName);
		synchronized(curBufferMap)
		{
			curBufferMap.clear();
			curBufferMap.putAll(bufferMap);
		}
		Map<String, Integer> countMap = modelCountMap.get(envId);
		if(countMap==null)
		{
			synchronized(modelCountMap)
			{
				countMap = modelCountMap.get(envId);
				if(countMap==null)
				{
					countMap = new HashMap<String, Integer>();
					modelCountMap.put(envId, countMap);
				}
			}
		}
		synchronized(countMap)
		{
			countMap.put(bufferName, modelCount);
		}
		Map<String, Map<IModel, Map<String, IModel>>> envTransMap = modelTransMap.get(envId);
		if(envTransMap==null)
		{
			synchronized(modelTransMap)
			{
				envTransMap = modelTransMap.get(envId);
				if(envTransMap==null)
				{
					envTransMap = new HashMap<String, Map<IModel, Map<String, IModel>>>();
					modelTransMap.put(envId, envTransMap);
				}
			}
		}
		synchronized(envTransMap)
		{
			envTransMap.put(bufferName, transMap);
		}
		return true;
	}
	
	public Serializable doGetBufferData(int envId, String bufferName, Map<String, Object> paramMap)
	{
		Serializable ret = null;
		TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>> modelInfo = bindModelInfoMap.get(bufferName);
		if(modelInfo==null) throw new RuntimeException("Please bind model info first!");
		try
		{
			if(paramMap==null) ret = dispatchGetAllModels(bufferName, null, envId, null, modelInfo);
			else
			{
				Serializable key = (Serializable) paramMap.get(BUFFER_KEY_MODEL_KEY);
				Session session = (Session) paramMap.get(BUFFER_KEY_SESSION);
				IEnvDataBase dataBase = (IEnvDataBase) paramMap.get(BUFFER_KEY_DATABASE);
				Integer tempEnvId = (Integer) paramMap.get(BUFFER_KEY_ENV_ID);
				if(tempEnvId==null) tempEnvId = envId;
				if(key==null) ret = dispatchGetAllModels(bufferName, session, tempEnvId, dataBase, modelInfo);
				else ret = dispatchGetModel(bufferName, session, tempEnvId, dataBase, key, modelInfo);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		return ret;
	}

	private Serializable dispatchGetModel(String bufferName, Session session, int envId, IEnvDataBase dataBase, Serializable key, TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>> modelInfo) throws Exception
	{
		Serializable model = null;
		if(session!=null) model = doGetModel(bufferName, session, key, modelInfo.first, modelInfo.second, modelInfo.third);
		else if(dataBase!=null) model = doGetModel(bufferName, dataBase, key, modelInfo.first, modelInfo.second, modelInfo.third);
		else model = doGetModel(bufferName, envId, key, modelInfo.first, modelInfo.second, modelInfo.third);
		return model;
	}

	private Serializable dispatchGetAllModels(String bufferName, Session session, int envId, IEnvDataBase dataBase, TrisModel<Class<? extends IModel>, Class<? extends Serializable>, IModelCreator<? extends IModel, ? extends Serializable>> modelInfo) throws Exception
	{
		Serializable modelList = null;
		if(session!=null) modelList = (Serializable) doGetAllModels(bufferName, session, modelInfo.first, modelInfo.second, modelInfo.third);
		else if(dataBase!=null) modelList = (Serializable) doGetAllModels(bufferName, dataBase, modelInfo.first, modelInfo.second, modelInfo.third);
		else modelList = (Serializable) doGetAllModels(bufferName, envId, modelInfo.first, modelInfo.second, modelInfo.third);
		return modelList;
	}
}
