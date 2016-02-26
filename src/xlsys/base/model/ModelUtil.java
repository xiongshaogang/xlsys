package xlsys.base.model;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xlsys.base.database.IDataBase;
import xlsys.base.database.IEnvDataBase;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.TranslateUtil;
import xlsys.base.dataset.IDataSet;
import xlsys.base.util.ObjectUtil;

public class ModelUtil
{
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
}
