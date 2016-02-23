package xlsys.base.buffer;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import xlsys.base.database.IDataBase;
import xlsys.base.database.IEnvDataBase;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.TranslateUtil;
import xlsys.base.dataset.IDataSet;
import xlsys.base.model.IModel;
import xlsys.base.util.ObjectUtil;
import xlsys.base.util.ReflectUtil;

public abstract class ModelBuffer implements XlsysBuffer
{
	public static final String BUFFER_KEY_BUFFER_NAME = "_BUFFER_KEY_BUFFER_NAME";
	
	/**
	 * 第一层 : envId, 第二层
	 * 第二层 : bufferName, 第三层
	 * 第三层 : 自定义Map, key和value都继承自Serializable
	 */
	protected Map<Integer, Map<String, Map<? extends Serializable, ? extends Serializable>>> allEnvBufferMap;
	
	protected ModelBuffer()
	{
		allEnvBufferMap = new HashMap<Integer, Map<String, Map<? extends Serializable, ? extends Serializable>>>();
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
	
	public static <T extends IModel> T getModelFromData(IDataBase dataBase, Class<T> modelClass, ParamBean pb) throws Exception
	{
		List<T> modelList = getModelsFromDatas(dataBase, modelClass, pb);
		if(modelList!=null&&modelList.size()>0) return modelList.get(0);
		else return null;
	}
	
	public static <T extends IModel> List<T> getModelsFromDatas(IDataBase dataBase, Class<T> modelClass, ParamBean pb) throws Exception
	{
		List<T> modelList = new ArrayList<T>();
		IDataSet dataSet = dataBase.sqlSelect(pb);
		if(dataBase instanceof IEnvDataBase) TranslateUtil.getInstance().translateDataSet((IEnvDataBase)dataBase, dataSet);
		Constructor<T> constructor = modelClass.getDeclaredConstructor();
		constructor.setAccessible(true);
		// 获取所有方法,方法名全部转换成小写,同名会被覆盖掉,这里要注意
		Map<String, Method> methodMap = new HashMap<String, Method>();
		Method[] methods = modelClass.getDeclaredMethods();
		for(Method m : methods)
		{
			m.setAccessible(true);
			methodMap.put(m.getName().toLowerCase(), m);
		}
		// 构造新的model
		for(int i=0;i<dataSet.getRowCount();i++)
		{
			T model = constructor.newInstance();
			// 设置属性
			for(int j=0;j<dataSet.getColumnCount();j++)
			{
				Serializable value = dataSet.getValue(i, j);
				String colName = dataSet.getColumnName(j);
				Method method = methodMap.get("set"+colName.toLowerCase());
				if(method!=null&&method.getParameterTypes().length==1) method.invoke(model, ObjectUtil.objectCast(value, method.getParameterTypes()[0]));
			}
			modelList.add(model);
		}
		return modelList;
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
