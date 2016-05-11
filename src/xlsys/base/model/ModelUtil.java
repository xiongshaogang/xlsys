package xlsys.base.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.database.ConnectionPool;
import xlsys.base.database.IDataBase;
import xlsys.base.database.TableInfo;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.dataset.DataSet;
import xlsys.base.dataset.DataSetColumn;
import xlsys.base.dataset.DataSetRow;
import xlsys.base.dataset.IDataSet;
import xlsys.base.dataset.StorableDataSet;
import xlsys.base.io.util.FileUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.util.ObjectUtil;
import xlsys.base.util.StringUtil;

public class ModelUtil
{
	public static <T extends IModel> T getModelFromData(IDataBase dataBase, Class<T> modelClass, ParamBean pb)
	{
		List<T> modelList = getModelsFromDatas(dataBase, modelClass, pb);
		if(modelList!=null&&modelList.size()>0) return modelList.get(0);
		else return null;
	}
	
	public static <T extends IModel> List<T> getModelsFromDatas(IDataBase dataBase, Class<T> modelClass, ParamBean pb)
	{
		List<T> modelList = new ArrayList<T>();
		try
		{
			IDataSet dataSet = dataBase.sqlSelect(pb);
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
					if(value!=null&&(value instanceof byte[])&&(method.getParameterTypes()[0]!=byte[].class))
					{
						value = (Serializable) IOUtil.readInternalObject((byte[])value);
					}
					if(method!=null&&method.getParameterTypes().length==1) method.invoke(model, ObjectUtil.objectCast(value, method.getParameterTypes()[0]));
				}
				modelList.add(model);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		return modelList;
	}
	
	private static void addImportClass(String packageName, String className, StringBuilder importSb, Set<String> importedClass)
	{
		if(className.startsWith(packageName))
		{
			String temp = className.substring(packageName.length()+1);
			if(temp.indexOf('.')==-1) return;
		}
		if(!importedClass.contains(className))
		{
			importedClass.add(className);
			importSb.append("import ").append(className).append(';').append('\n');
		}
	}
	
	/**
	 * 创建表对应的model类, 如果类存在, 则会覆盖.
	 * @param dataBase 数据库连接
	 * @param tableName 对应的表名
	 * @param classFullName 类全名
	 * @param childrenModelList 子表model列表(全名)
	 * @param blobClass blob字段对应的class类型
	 * @param srcRoot 创建类的根目录
	 * @return
	 * @throws Exception 
	 */
	public static void generateModelClass(IDataBase dataBase, String tableName, String classFullName, List<String> childrenModelList, Class<?> blobClass, String srcRoot) throws Exception
	{
		String classStr = createModelClass(dataBase, tableName, classFullName, childrenModelList, blobClass);
		String[] classNameArr = classFullName.split("\\.");
		// 创建目录
		File srcRootDir = new File(srcRoot);
		String filePath = srcRootDir.getCanonicalPath();
		for(int i=0;i<classNameArr.length;++i) filePath += File.separator + classNameArr[i];
		filePath += ".java";
		FileUtil.createParentPath(filePath);
		FileOutputStream fos = null;
		try
		{
			fos = IOUtil.getFileOutputStream(filePath, false);
			fos.write(classStr.getBytes("UTF-8"));
			fos.flush();
		}
		finally
		{
			IOUtil.close(fos);
		}
	}
	
	/**
	 * 自动生成表对应的model类
	 * @param dataBase 数据库连接
	 * @param tableName 表名称
	 * @param classFullName 生成类的全名称
	 * @param childrenModelList 对应的子表model类列表
	 * @param blobClass blob字段对应的class类型
	 * @throws Exception
	 * @return 类文本
	 */
	public static String createModelClass(IDataBase dataBase, String tableName, String classFullName, List<String> childrenModelList, Class<?> blobClass) throws Exception
	{
		TableInfo tableInfo = dataBase.getTableInfo(tableName);
		if(tableInfo==null) throw new RuntimeException("Can not find table : " + tableName);
		int lastDotIdx = classFullName.lastIndexOf('.');
		String packageName = classFullName.substring(0, lastDotIdx);
		String className = classFullName.substring(lastDotIdx+1);
		StringBuilder sb = new StringBuilder();
		// 添加包信息
		sb.append("package ").append(packageName).append(';').append('\n');
		sb.append('\n');
		// 生成import信息
		StringBuilder importSb = new StringBuilder();
		Set<String> importedClass = new HashSet<String>();
		addImportClass(packageName, "xlsys.base.model.ITableModel", importSb, importedClass);
		// 生成类注释
		StringBuilder classSb = new StringBuilder();
		classSb.append("/**").append('\n');
		classSb.append(" * This class is automatically generated by the program of Lewis.").append('\n');
		classSb.append(" * @author Lewis").append('\n');
		classSb.append(" *").append('\n');
		classSb.append(" */").append('\n');
		// 生成类头
		classSb.append("public class ").append(className).append(" implements ITableModel").append('\n');
		classSb.append('{').append('\n');
		// 生成类主体
		StringBuilder fieldSb = new StringBuilder();
		StringBuilder methodSb = new StringBuilder();
		// 生成类构造方法
		boolean needProtectedConstruction = false;
		List<String> pkColList = new ArrayList<String>();
		pkColList.addAll(tableInfo.getPkColSet());
		if(!pkColList.isEmpty()) needProtectedConstruction = true;
		methodSb.append('\t');
		if(needProtectedConstruction) methodSb.append("protected ");
		else methodSb.append("public ");
		methodSb.append(className).append("() {}").append('\n');
		methodSb.append('\n');
		if(!pkColList.isEmpty())
		{
			methodSb.append('\t').append("public ").append(className).append('(');
			StringBuilder tempSb = new StringBuilder();
			for(int i=0;i<pkColList.size();++i)
			{
				String pkColName = pkColList.get(i);
				DataSetColumn dsc = tableInfo.getDataSetColumn(pkColName);
				String fieldJavaClass = dsc.getJavaClass();
				if(byte[].class.getName().equals(fieldJavaClass)) fieldJavaClass = blobClass.getName();
				String fieldColumnName = dsc.getColumnName();
				if(!fieldJavaClass.startsWith("java.lang.")&&!byte[].class.getName().equals(fieldJavaClass)) addImportClass(packageName, fieldJavaClass, importSb, importedClass);
				if(byte[].class.getName().equals(fieldJavaClass)) fieldJavaClass = "byte[]";
				else
				{
					int tempLastDotIdx = fieldJavaClass.lastIndexOf('.');
					fieldJavaClass = fieldJavaClass.substring(tempLastDotIdx+1);
				}
				methodSb.append(fieldJavaClass).append(' ').append(fieldColumnName);
				if(i!=pkColList.size()-1) methodSb.append(", ");
				tempSb.append('\t').append('\t').append("this.").append(fieldColumnName).append(" = ").append(fieldColumnName).append(';').append('\n');
			}
			methodSb.append(')').append('\n');
			methodSb.append('\t').append('{').append('\n');
			methodSb.append(tempSb);
			methodSb.append('\t').append('}').append('\n');
			methodSb.append('\n');
		}
		// 生成本表信息
		for(DataSetColumn dsc : tableInfo.getDataColumnList())
		{
			// 生成属性
			String fieldJavaClass = dsc.getJavaClass();
			if(byte[].class.getName().equals(fieldJavaClass)) fieldJavaClass = blobClass.getName();
			String fieldColumnName = dsc.getColumnName();
			if(!fieldJavaClass.startsWith("java.lang.")&&!byte[].class.getName().equals(fieldJavaClass)) addImportClass(packageName, fieldJavaClass, importSb, importedClass);
			fieldSb.append('\t').append("private ");
			if(byte[].class.getName().equals(fieldJavaClass)) fieldJavaClass = "byte[]";
			else
			{
				int tempLastDotIdx = fieldJavaClass.lastIndexOf('.');
				fieldJavaClass = fieldJavaClass.substring(tempLastDotIdx+1);
			}
			fieldSb.append(fieldJavaClass).append(' ').append(fieldColumnName).append(';').append('\n');
			// 生成get方法
			methodSb.append('\t').append("public ").append(fieldJavaClass).append(" get").append(StringUtil.toInitialsUpperCase(fieldColumnName)).append("()").append('\n');
			methodSb.append('\t').append('{').append('\n');
			methodSb.append('\t').append('\t').append("return ").append(fieldColumnName).append(';').append('\n');
			methodSb.append('\t').append('}').append('\n');
			methodSb.append('\n');
			// 生成set方法
			methodSb.append('\t');
			if(dsc.isPrimaryKey()) methodSb.append("protected");
			else methodSb.append("public");
			methodSb.append(" void set").append(StringUtil.toInitialsUpperCase(fieldColumnName)).append('(').append(fieldJavaClass).append(' ').append(fieldColumnName).append(')').append('\n');
			methodSb.append('\t').append('{').append('\n');
			methodSb.append('\t').append('\t').append("this.").append(fieldColumnName).append(" = ").append(fieldColumnName).append(';').append('\n');
			methodSb.append('\t').append('}').append('\n');
			methodSb.append('\n');
		}
		// 生成子表信息
		if(childrenModelList!=null&&!childrenModelList.isEmpty())
		{
			addImportClass(packageName, "java.util.List", importSb, importedClass);
			addImportClass(packageName, "java.util.ArrayList", importSb, importedClass);
			for(String childModelFullName :childrenModelList)
			{
				// field
				addImportClass(packageName, childModelFullName, importSb, importedClass);
				int tempLastDotIdx = childModelFullName.lastIndexOf('.');
				String childModelName = childModelFullName.substring(tempLastDotIdx+1);
				String childFieldName = StringUtil.toInitialsLowerCase(childModelName);
				if(childFieldName.endsWith("Model")) childFieldName = childFieldName.substring(0, childFieldName.length()-5);
				String singleFieldName = childFieldName;
				if(!childFieldName.endsWith("List")) childFieldName += "List";
				else singleFieldName = childFieldName.substring(0, childFieldName.length()-4);
				fieldSb.append('\t').append("private List<").append(childModelName).append("> ").append(childFieldName).append(';').append('\n');
				// get
				methodSb.append('\t').append("public List<").append(childModelName).append("> get").append(StringUtil.toInitialsUpperCase(childFieldName)).append("()").append('\n');
				methodSb.append('\t').append('{').append('\n');
				methodSb.append('\t').append('\t').append("return ").append(childFieldName).append(';').append('\n');
				methodSb.append('\t').append('}').append('\n');
				methodSb.append('\n');
				// set
				methodSb.append('\t').append("public void set").append(StringUtil.toInitialsUpperCase(childFieldName)).append("(List<").append(childModelName).append("> ").append(childFieldName).append(')').append('\n');
				methodSb.append('\t').append('{').append('\n');
				methodSb.append('\t').append('\t').append("this.").append(childFieldName).append(" = ").append(childFieldName).append(';').append('\n');
				methodSb.append('\t').append('}').append('\n');
				methodSb.append('\n');
				// add
				methodSb.append('\t').append("public void add").append(StringUtil.toInitialsUpperCase(singleFieldName)).append('(').append(childModelName).append(' ').append(singleFieldName).append(')').append('\n');
				methodSb.append('\t').append('{').append('\n');
				methodSb.append('\t').append('\t').append("if(").append(childFieldName).append("==null) ").append(childFieldName).append(" = new ArrayList<").append(childModelName).append(">()").append(';').append('\n');
				methodSb.append('\t').append('\t').append(childFieldName).append(".add(").append(singleFieldName).append(')').append(';').append('\n');
				methodSb.append('\t').append('}').append('\n');
				methodSb.append('\n');
			}
		}
		// 生成接口方法
		methodSb.append('\t').append("@Override").append('\n');
		methodSb.append('\t').append("public String getRefTableName()").append('\n');
		methodSb.append('\t').append('{').append('\n');
		methodSb.append('\t').append('\t').append("return \"").append(tableName).append("\";").append('\n');
		methodSb.append('\t').append('}').append('\n');
		classSb.append(fieldSb).append('\n').append(methodSb);
		// 生成类尾
		classSb.append('}');
		sb.append(importSb).append('\n').append(classSb);
		return sb.toString();
	}
	
	private static Method findGetMethod(Method[] methods, String fieldName)
	{
		Method method = null;
		String getMethodName = "get" + fieldName;
		for(Method m : methods)
		{
			if(m.getName().equalsIgnoreCase(getMethodName))
			{
				method = m;
				break;
			}
		}
		return method;
	}
	
	/**
	 * 创建表对应的Js Model类, 如果类存在, 则会覆盖.
	 * @param dataBase 数据库连接
	 * @param tableName 对应的表名
	 * @param classFullName 类全名
	 * @param childrenModelList 子表model列表(全名)
	 * @param srcRoot 创建类的根目录
	 * @return
	 * @throws Exception 
	 */
	public static void generateJsModelClass(IDataBase dataBase, String tableName, String classFullName, List<String> childrenModelList, String srcRoot) throws Exception
	{
		String classStr = createJsModelClass(dataBase, tableName, classFullName, childrenModelList);
		String[] classNameArr = classFullName.split("\\.");
		// 创建目录
		File srcRootDir = new File(srcRoot);
		String filePath = srcRootDir.getCanonicalPath();
		for(int i=0;i<classNameArr.length;++i) filePath += File.separator + classNameArr[i];
		filePath += ".js";
		FileUtil.createParentPath(filePath);
		FileOutputStream fos = null;
		try
		{
			fos = IOUtil.getFileOutputStream(filePath, false);
			fos.write(classStr.getBytes("UTF-8"));
			fos.flush();
		}
		finally
		{
			IOUtil.close(fos);
		}
	}
	
	/**
	 * 自动生成表对应的Js Model类
	 * @param dataBase 数据库连接
	 * @param tableName 表名称
	 * @param classFullName 生成类的全名称
	 * @param childrenModelList 对应的子表model类列表
	 * @throws Exception
	 * @return 类文本
	 */
	public static String createJsModelClass(IDataBase dataBase, String tableName, String classFullName, List<String> childrenModelList) throws Exception
	{
		TableInfo tableInfo = dataBase.getTableInfo(tableName);
		if(tableInfo==null) throw new RuntimeException("Can not find table : " + tableName);
		// 生成类注释
		StringBuilder classSb = new StringBuilder();
		classSb.append("/**").append('\n');
		classSb.append(" * This class is automatically generated by the program of Lewis.").append('\n');
		classSb.append(" * @author Lewis").append('\n');
		classSb.append(" *").append('\n');
		classSb.append(" */").append('\n');
		// 生成类头
		classSb.append("qx.Class.define(\"").append(classFullName).append("\",").append('\n');
		classSb.append('{').append('\n');
		classSb.append('\t').append("extend : xlsys.java.io.Serializable,").append('\n');
		classSb.append('\t').append("implement : [xlsys.base.model.ITableModel],").append('\n');
		classSb.append('\n');
		// 生成类主体
		// 生成类构造方法
		StringBuilder constructSb = new StringBuilder();
		List<String> pkColList = new ArrayList<String>();
		pkColList.addAll(tableInfo.getPkColSet());
		constructSb.append('\t').append("construct : function(");
		StringBuilder tempSb = null;
		if(!pkColList.isEmpty())
		{
			tempSb = new StringBuilder();
			for(int i=0;i<pkColList.size();++i)
			{
				String pkColName = pkColList.get(i);
				constructSb.append(pkColName);
				if(i!=pkColList.size()-1) constructSb.append(", ");
				tempSb.append('\t').append('\t').append("this.").append(pkColName).append(" = ").append(pkColName).append(';').append('\n');
			}
		}
		if(tempSb==null) constructSb.append(") {},").append('\n');
		else
		{
			constructSb.append(')').append('\n');
			constructSb.append('\t').append('{').append('\n');
			constructSb.append(tempSb);
			constructSb.append('\t').append("},").append('\n');
			constructSb.append('\n');
		}
		classSb.append(constructSb);
		// 生成成员方法
		classSb.append('\t').append("members : ").append('\n');
		classSb.append('\t').append('{').append('\n');
		StringBuilder fieldSb = new StringBuilder();
		StringBuilder methodSb = new StringBuilder();
		StringBuilder seriSb = new StringBuilder();
		// 生成本表信息
		for(DataSetColumn dsc : tableInfo.getDataColumnList())
		{
			// 生成属性
			String fieldColumnName = dsc.getColumnName();
			fieldSb.append('\t').append('\t').append(fieldColumnName).append(" : null,").append('\n');
			// 生成序列化需要添加的属性
			seriSb.append('\t').append('\t').append('\t').append("arr.push(\"").append(fieldColumnName).append("\");").append('\n');
			// 生成get方法
			methodSb.append('\t').append('\t').append("get").append(StringUtil.toInitialsUpperCase(fieldColumnName)).append(" : function()").append('\n');
			methodSb.append('\t').append('\t').append('{').append('\n');
			methodSb.append('\t').append('\t').append('\t').append("return this.").append(fieldColumnName).append(';').append('\n');
			methodSb.append('\t').append('\t').append("},").append('\n');
			methodSb.append('\n');
			// 生成set方法
			methodSb.append('\t').append('\t').append("set").append(StringUtil.toInitialsUpperCase(fieldColumnName)).append(" : function(").append(fieldColumnName).append(')').append('\n');
			methodSb.append('\t').append('\t').append('{').append('\n');
			methodSb.append('\t').append('\t').append('\t').append("this.").append(fieldColumnName).append(" = ").append(fieldColumnName).append(';').append('\n');
			methodSb.append('\t').append('\t').append("},").append('\n');
			methodSb.append('\n');
		}
		// 生成子表信息
		if(childrenModelList!=null&&!childrenModelList.isEmpty())
		{
			for(String childModelFullName : childrenModelList)
			{
				// field
				int tempLastDotIdx = childModelFullName.lastIndexOf('.');
				String childModelName = childModelFullName.substring(tempLastDotIdx+1);
				String childFieldName = StringUtil.toInitialsLowerCase(childModelName);
				if(childFieldName.endsWith("Model")) childFieldName = childFieldName.substring(0, childFieldName.length()-5);
				String singleFieldName = childFieldName;
				if(!childFieldName.endsWith("List")) childFieldName += "List";
				else singleFieldName = childFieldName.substring(0, childFieldName.length()-4);
				fieldSb.append('\t').append('\t').append(childFieldName).append(" : null,").append('\n');
				seriSb.append('\t').append('\t').append('\t').append("arr.push(\"").append(childFieldName).append("\");").append('\n');
				// get
				methodSb.append('\t').append('\t').append("get").append(StringUtil.toInitialsUpperCase(childFieldName)).append(" : function()").append('\n');
				methodSb.append('\t').append('\t').append('{').append('\n');
				methodSb.append('\t').append('\t').append('\t').append("return this.").append(childFieldName).append(';').append('\n');
				methodSb.append('\t').append('\t').append("},").append('\n');
				methodSb.append('\n');
				// set
				methodSb.append('\t').append('\t').append("set").append(StringUtil.toInitialsUpperCase(childFieldName)).append(" : function(").append(childFieldName).append(')').append('\n');
				methodSb.append('\t').append('\t').append('{').append('\n');
				methodSb.append('\t').append('\t').append('\t').append("this.").append(childFieldName).append(" = ").append(childFieldName).append(';').append('\n');
				methodSb.append('\t').append('\t').append("},").append('\n');
				methodSb.append('\n');
				// add
				methodSb.append('\t').append('\t').append("add").append(StringUtil.toInitialsUpperCase(singleFieldName)).append(" : function(").append(singleFieldName).append(')').append('\n');
				methodSb.append('\t').append('\t').append('{').append('\n');
				methodSb.append('\t').append('\t').append('\t').append("if(this.").append(childFieldName).append("==null) this.").append(childFieldName).append(" = new xlsys.java.util.ArrayList").append("()").append(';').append('\n');
				methodSb.append('\t').append('\t').append('\t').append("this.").append(childFieldName).append(".add(").append(singleFieldName).append(')').append(';').append('\n');
				methodSb.append('\t').append('\t').append("},").append('\n');
				methodSb.append('\n');
			}
		}
		// 生成接口方法
		methodSb.append('\t').append('\t').append("getRefTableName : function()").append('\n');
		methodSb.append('\t').append('\t').append('{').append('\n');
		methodSb.append('\t').append('\t').append('\t').append("return \"").append(tableName).append("\";").append('\n');
		methodSb.append('\t').append('\t').append("},").append('\n');
		methodSb.append('\n');
		// 生成序列化必要方法
		methodSb.append('\t').append('\t').append("getSerializableMembers : function()").append('\n');
		methodSb.append('\t').append('\t').append('{').append('\n');
		methodSb.append('\t').append('\t').append('\t').append("var arr = new Array();").append('\n');
		methodSb.append(seriSb);
		methodSb.append('\t').append('\t').append('\t').append("return arr;").append('\n');
		methodSb.append('\t').append('\t').append('}').append('\n');
		// 合并类体
		classSb.append(fieldSb).append('\n').append(methodSb);
		// 生成类尾
		classSb.append('\t').append('}').append('\n');
		classSb.append("});");
		return classSb.toString();
	}
	
	/**
	 * 将model同步到数据库中
	 * @param dataBase 数据库连接
	 * @param model
	 * @param synchronizeSubModel 是否同步子模型
	 * @throws Exception
	 * @return 成功返回true, 否则返回false
	 */
	public static boolean synchronizeModel(IDataBase dataBase, ITableModel model, boolean synchronizeSubModel) throws Exception
	{
		// 获取对应的表名
		String tableName = model.getRefTableName();
		// 获取主键字段
		TableInfo tableInfo = dataBase.getTableInfo(tableName);
		List<String> pkColList = new ArrayList<String>();
		pkColList.addAll(tableInfo.getPkColSet());
		// 按照主键字段查询数据库中的数据
		StringBuilder selectSql = new StringBuilder();
		selectSql.append("select * from ").append(tableName).append(" where ");
		for(int i=0;i<pkColList.size();++i)
		{
			String pkColName = pkColList.get(i);
			selectSql.append(pkColName).append("=?");
			if(i!=pkColList.size()-1) selectSql.append(" and ");
		}
		ParamBean pb = new ParamBean(selectSql.toString());
		pb.addParamGroup();
		Method[] methods = model.getClass().getMethods();
		for(int i=0;i<pkColList.size();++i)
		{
			String pkColName = pkColList.get(i);
			Method getMethod = findGetMethod(methods, pkColName);
			if(getMethod==null) throw new RuntimeException("Can not find get method of field : "+pkColName);
			pb.setParam(i+1, (Serializable)getMethod.invoke(model));
		}
		StorableDataSet sds = new StorableDataSet(dataBase, pb, tableName);
		sds.setSaveTransaction(false);
		sds.open();
		if(sds.getRowCount()>0)
		{
			// 有数据, 更新即可
			sds.gotoRow(0);
		}
		else
		{
			// 没有数据, 写入新数据
			sds.insertNewRowAfterLast();
		}
		for(DataSetColumn dsc :sds.getColumns())
		{
			String colName = dsc.getColumnName();
			Method getMethod = findGetMethod(methods, colName);
			if(getMethod==null) throw new RuntimeException("Can not find get method of field : "+colName);
			sds.setValue(colName, (Serializable)getMethod.invoke(model));
		}
		boolean success = sds.save();
		if(success&&synchronizeSubModel)
		{
			// 写入子模型数据
			Field[] fields = model.getClass().getDeclaredFields();
			for(Field field : fields)
			{
				Class<?> fieldClass = field.getType();
				if(Collection.class.isAssignableFrom(fieldClass))
				{
					field.setAccessible(true);
					Collection<?> fieldCollection = (Collection<?>) field.get(model);
					if(fieldCollection==null) continue;
					for(Object subModel : fieldCollection)
					{
						if(subModel instanceof ITableModel)
						{
							success = synchronizeModel(dataBase, (ITableModel)subModel, synchronizeSubModel);
							if(!success) break;
						}
					}
				}
				if(!success) break;
			}
		}
		return success;
	}
	
	public static <T extends IModel> T cloneModel(T model)
	{
		T cloneModel = null;
		try
		{
			Constructor<?> c = model.getClass().getDeclaredConstructor();
			c.setAccessible(true);
			cloneModel = (T) c.newInstance();
			Field[] fields = model.getClass().getDeclaredFields();
			for(Field field : fields)
			{
				int modifiers = field.getModifiers();
				if(Modifier.isStatic(modifiers)&&Modifier.isFinal(modifiers)) continue;
				field.setAccessible(true);
				field.set(cloneModel, field.get(model));
			}
		}
		catch(Exception e) {}
		return cloneModel;
	}
	
	public static <T extends IModel> IDataSet modelListToDataSet(List<T> modelList) throws IllegalArgumentException, IllegalAccessException
	{
		if(modelList==null||modelList.isEmpty()) return null;
		IDataSet dataSet = new DataSet();
		T firstModel = modelList.get(0);
		Field[] fields = firstModel.getClass().getDeclaredFields();
		List<Field> colFieldList = new ArrayList<Field>();
		for(Field field : fields)
		{
			int modifiers = field.getModifiers();
			if(Modifier.isStatic(modifiers)&&Modifier.isFinal(modifiers)) continue;
			Class<?> fieldClass = field.getType();
			if(Collection.class.isAssignableFrom(fieldClass)||Map.class.isAssignableFrom(fieldClass)) continue;
			field.setAccessible(true);
			colFieldList.add(field);
			DataSetColumn dsc = dataSet.insertNewColumnAfterLast();
			dsc.setColumnName(field.getName());
			dsc.setJavaClass(fieldClass.getName());
		}
		dataSet.buildColumnMap();
		for(T model : modelList)
		{
			dataSet.insertNewRowAfterLast();
			for(Field field : colFieldList)
			{
				dataSet.setValue(field.getName(), (Serializable) field.get(model));
			}
		}
		return dataSet;
	}
	
	public static <T> TreeModel<T> listToTreeModel(List<T> sortedList, String treeFieldName, TreeModel<T> root) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		// 获取第一层元素
		Set<Integer> addedSet = new HashSet<Integer>();
		// 获取field映射对象
		T first = sortedList.get(0);
		Field treeField = first.getClass().getDeclaredField(treeFieldName);
		treeField.setAccessible(true);
		for(int i=0;i<sortedList.size();++i)
		{
			if(!addedSet.contains(i))
			{
				TreeModel<T> childModel = root.addChildData(sortedList.get(i));
				addChildrenToTreeModel(sortedList, treeField, childModel, i, addedSet);
			}
		}
		return root;
	}
	
	private static <T> void addChildrenToTreeModel(List<T> sortedList, Field treeField, TreeModel<T> treeModel, int index, Set<Integer> addedSet) throws IllegalArgumentException, IllegalAccessException
	{
		String keyStr = ObjectUtil.objectToString(treeField.get(treeModel.getData()));
		for(int i=index+1;i<sortedList.size();++i)
		{
			if(!addedSet.contains(i))
			{
				T temp = sortedList.get(i);
				String tempStr = ObjectUtil.objectToString(treeField.get(temp));
				if(tempStr.startsWith(keyStr))
				{
					TreeModel<T> childModel = treeModel.addChildData(temp);
					addedSet.add(i);
					addChildrenToTreeModel(sortedList, treeField, childModel, i, addedSet);
				}
			}
		}
	}
	
	public static TreeModel<DataSetRow> dataSetToTreeModel(IDataSet sortedDataSet, String treeColumnName, TreeModel<DataSetRow> root) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		// 获取第一层元素
		Set<Integer> addedSet = new HashSet<Integer>();
		List<DataSetRow> rowList = sortedDataSet.getRows();
		for(int i=0;i<rowList.size();++i)
		{
			if(!addedSet.contains(i))
			{
				TreeModel<DataSetRow> childModel = root.addChildData(rowList.get(i));
				addChildrenRowToTreeModel(sortedDataSet, treeColumnName, childModel, i, addedSet);
			}
		}
		return root;
	}
	
	private static void addChildrenRowToTreeModel(IDataSet sortedDataSet, String treeColumnName, TreeModel<DataSetRow> treeModel, int index, Set<Integer> addedSet) throws IllegalArgumentException, IllegalAccessException
	{
		String keyStr = ObjectUtil.objectToString(sortedDataSet.getValue(index, treeColumnName));
		List<DataSetRow> rowList = sortedDataSet.getRows();
		for(int i=index+1;i<rowList.size();++i)
		{
			if(!addedSet.contains(i))
			{
				String tempStr = ObjectUtil.objectToString(sortedDataSet.getValue(i, treeColumnName));
				if(tempStr.startsWith(keyStr))
				{
					TreeModel<DataSetRow> childModel = treeModel.addChildData(sortedDataSet.getRow(i));
					addedSet.add(i);
					addChildrenRowToTreeModel(sortedDataSet, treeColumnName, childModel, i, addedSet);
				}
			}
		}
	}

	public static void main(String[] args)
	{
		IDataBase dataBase = null;
		try
		{
			dataBase = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(1001)).getNewDataBase();
			
			/*String srcRoot = "D:/work/code/MyProject/xlsys.base/src";
			ModelUtil.generateModelClass(dataBase, "xlv2_framedetailparam", "xlsys.base.model.FrameDetailParamModel", null, byte[].class, srcRoot);
			List<String> childrenList = new ArrayList<String>();
			childrenList.add("xlsys.base.model.FrameDetailParamModel");
			ModelUtil.generateModelClass(dataBase, "xlv2_framedetail", "xlsys.base.model.FrameDetailModel", childrenList, byte[].class, srcRoot);
			ModelUtil.generateModelClass(dataBase, "xlv2_frameparam", "xlsys.base.model.FrameParamModel", null, byte[].class, srcRoot);
			childrenList = new ArrayList<String>();
			childrenList.add("xlsys.base.model.FrameParamModel");
			childrenList.add("xlsys.base.model.FrameDetailModel");
			ModelUtil.generateModelClass(dataBase, "xlv2_frame", "xlsys.base.model.FrameModel", childrenList, byte[].class, srcRoot);
			
			String jsSrcRoot = "D:/work/code/MyProject/xlsys.client.web.base/source/class";
			ModelUtil.generateJsModelClass(dataBase, "xlv2_framedetailparam", "xlsys.base.model.FrameDetailParamModel", null, jsSrcRoot);
			List<String> jsChildrenList = new ArrayList<String>();
			jsChildrenList.add("xlsys.base.model.FrameDetailParamModel");
			ModelUtil.generateJsModelClass(dataBase, "xlv2_framedetail", "xlsys.base.model.FrameDetailModel", jsChildrenList, jsSrcRoot);
			ModelUtil.generateJsModelClass(dataBase, "xlv2_frameparam", "xlsys.base.model.FrameParamModel", null, jsSrcRoot);
			jsChildrenList = new ArrayList<String>();
			jsChildrenList.add("xlsys.base.model.FrameParamModel");
			jsChildrenList.add("xlsys.base.model.FrameDetailModel");
			ModelUtil.generateJsModelClass(dataBase, "xlv2_frame", "xlsys.base.model.FrameModel", jsChildrenList, jsSrcRoot);*/
			
			/*String srcRoot = "D:/work/code/MyProject/xlsys.client.web.base/source/class";
			ModelUtil.generateJsModelClass(dataBase, "xlsys_idrelation", "xlsys.business.model.IdRelationModel", null, srcRoot);
			List<String> childrenList = new ArrayList<String>();
			childrenList.add("xlsys.business.model.IdRelationModel");
			ModelUtil.generateJsModelClass(dataBase, "xlsys_identity", "xlsys.business.model.IdentityModel", childrenList, srcRoot);*/
			
			/*String srcRoot = "D:/work/code/MyProject/xlsys.business/src";
			ModelUtil.generateModelClass(dataBase, "xlsys_right", "xlsys.business.model.RightModel", null, srcRoot);*/
			
			/*String srcRoot = "D:/work/code/MyProject/xlsys.client.web.base/source/class";
			ModelUtil.generateJsModelClass(dataBase, "xlsys_right", "xlsys.business.model.RightModel", null, srcRoot);*/
			
			/*String srcRoot = "D:/work/code/MyProject/xlsys.business/src";
			ModelUtil.generateModelClass(dataBase, "xlsys_menuright", "xlsys.business.model.MenuRightModel", null, byte[].class, srcRoot);
			List<String> childrenList = new ArrayList<String>();
			childrenList.add("xlsys.business.model.MenuRightModel");
			ModelUtil.generateModelClass(dataBase, "xlsys_menu", "xlsys.business.model.MenuModel", childrenList, XlsysAttachment.class, srcRoot);*/
			
			/*String jsSrcRoot = "D:/work/code/MyProject/xlsys.client.web.base/source/class";
			ModelUtil.generateJsModelClass(dataBase, "xlsys_menuright", "xlsys.business.model.MenuRightModel", null, jsSrcRoot);
			List<String> jsChildrenList = new ArrayList<String>();
			jsChildrenList.add("xlsys.business.model.MenuRightModel");
			ModelUtil.generateJsModelClass(dataBase, "xlsys_menu", "xlsys.business.model.MenuModel", jsChildrenList, jsSrcRoot);*/
			
			/*String srcRoot = "D:/work/code/MyProject/xlsys.base/src";
			ModelUtil.generateModelClass(dataBase, "xlv2_viewqueryparamparam", "xlsys.base.model.ViewQueryParamParamModel", null, byte[].class, srcRoot);
			List<String> childrenList = new ArrayList<String>();
			childrenList.add("xlsys.base.model.ViewQueryParamParamModel");
			ModelUtil.generateModelClass(dataBase, "xlv2_viewqueryparam", "xlsys.base.model.ViewQueryParamModel", childrenList, byte[].class, srcRoot);
			ModelUtil.generateModelClass(dataBase, "xlv2_viewcolumnparam", "xlsys.base.model.ViewColumnParamModel", null, byte[].class, srcRoot);
			childrenList = new ArrayList<String>();
			childrenList.add("xlsys.base.model.ViewColumnParamModel");
			ModelUtil.generateModelClass(dataBase, "xlv2_viewcolumn", "xlsys.base.model.ViewColumnModel", childrenList, byte[].class, srcRoot);
			ModelUtil.generateModelClass(dataBase, "xlv2_viewparam", "xlsys.base.model.ViewParamModel", null, byte[].class, srcRoot);
			childrenList = new ArrayList<String>();
			childrenList.add("xlsys.base.model.ViewParamModel");
			childrenList.add("xlsys.base.model.ViewColumnModel");
			childrenList.add("xlsys.base.model.ViewQueryParamModel");
			ModelUtil.generateModelClass(dataBase, "xlv2_view", "xlsys.base.model.ViewModel", childrenList, byte[].class, srcRoot);
			
			
			
			String jsSrcRoot = "D:/work/code/MyProject/xlsys.client.web.base/source/class";
			ModelUtil.generateJsModelClass(dataBase, "xlv2_viewqueryparamparam", "xlsys.base.model.ViewQueryParamParamModel", null, jsSrcRoot);
			List<String> jsChildrenList = new ArrayList<String>();
			jsChildrenList.add("xlsys.base.model.ViewQueryParamParamModel");
			ModelUtil.generateJsModelClass(dataBase, "xlv2_viewqueryparam", "xlsys.base.model.ViewQueryParamModel", jsChildrenList, jsSrcRoot);
			ModelUtil.generateJsModelClass(dataBase, "xlv2_viewcolumnparam", "xlsys.base.model.ViewColumnParamModel", null, jsSrcRoot);
			jsChildrenList = new ArrayList<String>();
			jsChildrenList.add("xlsys.base.model.ViewColumnParamModel");
			ModelUtil.generateJsModelClass(dataBase, "xlv2_viewcolumn", "xlsys.base.model.ViewColumnModel", jsChildrenList, jsSrcRoot);
			ModelUtil.generateJsModelClass(dataBase, "xlv2_viewparam", "xlsys.base.model.ViewParamModel", null, jsSrcRoot);
			jsChildrenList = new ArrayList<String>();
			jsChildrenList.add("xlsys.base.model.ViewParamModel");
			jsChildrenList.add("xlsys.base.model.ViewColumnModel");
			jsChildrenList.add("xlsys.base.model.ViewQueryParamModel");
			ModelUtil.generateJsModelClass(dataBase, "xlv2_view", "xlsys.base.model.ViewModel", jsChildrenList, jsSrcRoot);*/
			
			/*String srcRoot = "D:/work/code/MyProject/xlsys.base/src";
			ModelUtil.generateModelClass(dataBase, "xlv2_menuright", "xlsys.base.model.MenuRightModel", null, byte[].class, srcRoot);
			ModelUtil.generateModelClass(dataBase, "xlv2_menuhandlerparam", "xlsys.base.model.MenuHandlerParamModel", null, byte[].class, srcRoot);
			List<String> childrenList = new ArrayList<String>();
			childrenList.add("xlsys.base.model.MenuRightModel");
			childrenList.add("xlsys.base.model.MenuHandlerParamModel");
			ModelUtil.generateModelClass(dataBase, "xlv2_menu", "xlsys.base.model.MenuModel", childrenList, XlsysAttachment.class, srcRoot);
			
			
			String jsSrcRoot = "D:/work/code/MyProject/xlsys.client.web.base/source/class";
			ModelUtil.generateJsModelClass(dataBase, "xlv2_menuright", "xlsys.base.model.MenuRightModel", null, jsSrcRoot);
			ModelUtil.generateJsModelClass(dataBase, "xlv2_menuhandlerparam", "xlsys.base.model.MenuHandlerParamModel", null, jsSrcRoot);
			List<String> jsChildrenList = new ArrayList<String>();
			jsChildrenList.add("xlsys.base.model.MenuRightModel");
			jsChildrenList.add("xlsys.base.model.MenuHandlerParamModel");
			ModelUtil.generateJsModelClass(dataBase, "xlv2_menu", "xlsys.base.model.MenuModel", jsChildrenList, jsSrcRoot);*/
			
			/*String srcRoot = "D:/work/code/MyProject/xlsys.base/src";
			ModelUtil.generateModelClass(dataBase, "xlv2_toolright", "xlsys.base.model.ToolRightModel", null, byte[].class, srcRoot);
			ModelUtil.generateModelClass(dataBase, "xlv2_toolhandlerparam", "xlsys.base.model.ToolHandlerParamModel", null, byte[].class, srcRoot);
			List<String> childrenList = new ArrayList<String>();
			childrenList.add("xlsys.base.model.ToolRightModel");
			childrenList.add("xlsys.base.model.ToolHandlerParamModel");
			ModelUtil.generateModelClass(dataBase, "xlv2_tool", "xlsys.base.model.ToolModel", childrenList, XlsysAttachment.class, srcRoot);
			
			
			String jsSrcRoot = "D:/work/code/MyProject/xlsys.client.web.base/source/class";
			ModelUtil.generateJsModelClass(dataBase, "xlv2_toolright", "xlsys.base.model.ToolRightModel", null, jsSrcRoot);
			ModelUtil.generateJsModelClass(dataBase, "xlv2_toolhandlerparam", "xlsys.base.model.ToolHandlerParamModel", null, jsSrcRoot);
			List<String> jsChildrenList = new ArrayList<String>();
			jsChildrenList.add("xlsys.base.model.ToolRightModel");
			jsChildrenList.add("xlsys.base.model.ToolHandlerParamModel");
			ModelUtil.generateJsModelClass(dataBase, "xlv2_tool", "xlsys.base.model.ToolModel", jsChildrenList, jsSrcRoot);*/
			
			/*String srcRoot = "D:/work/code/MyProject/xlsys.base/src";
			ModelUtil.generateModelClass(dataBase, "xlv2_uimodule", "xlsys.base.model.UIModuleModel", null, byte[].class, srcRoot);
			
			String jsSrcRoot = "D:/work/code/MyProject/xlsys.client.web.base/source/class";
			ModelUtil.generateJsModelClass(dataBase, "xlv2_uimodule", "xlsys.base.model.UIModuleModel", null, jsSrcRoot);*/
			
			String srcRoot = "D:/work/code/MyProject/xlsys.base/src";
			ModelUtil.generateModelClass(dataBase, "xlv2_dialog", "xlsys.base.model.DialogModel", null, byte[].class, srcRoot);
			
			String jsSrcRoot = "D:/work/code/MyProject/xlsys.client.web.base/source/class";
			ModelUtil.generateJsModelClass(dataBase, "xlv2_dialog", "xlsys.base.model.DialogModel", null, jsSrcRoot);
		}
		catch(Exception e)
		{
			DBUtil.rollback(dataBase);
			e.printStackTrace();
		}
		finally
		{
			DBUtil.close(dataBase);
		}
		System.exit(0);
	}
}
