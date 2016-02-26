package xlsys.base.database;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.DocumentException;

import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.bean.ISqlBean;
import xlsys.base.database.bean.Param;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.database.util.SqlUtil;
import xlsys.base.dataset.DataSet;
import xlsys.base.dataset.DataSetColumn;
import xlsys.base.dataset.DataSetRow;
import xlsys.base.dataset.IDataSet;
import xlsys.base.exception.AlreadyClosedException;
import xlsys.base.io.util.IOUtil;
import xlsys.base.log.LogUtil;
import xlsys.base.script.XlsysClassLoader;
import xlsys.base.util.DateUtil;
import xlsys.base.util.ObjectUtil;

public class SQLiteDataBase extends DataBase
{
	protected SQLiteDataBase(ConnectionPool conPool, Connection con) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		super(conPool, con);
	}

	private void initDataSetColumn(IDataSet ds, ResultSetMetaData md) throws SQLException
	{
		int colCount = md.getColumnCount();
		for(int i=0;i<colCount;i++)
		{
			ds.insertNewColumnAfterLast();
			DataSetColumn dsc = ds.getColumns().get(ds.getColumns().size()-1);
			dsc.setColumnName(md.getColumnName(i+1).toLowerCase());
			int sqlType = md.getColumnType(i+1);
			dsc.setJavaClass(getClassNameFromSqlType(sqlType));
			dsc.setDbColumnType(md.getColumnTypeName(i+1));
			dsc.setSqlType(sqlType);
			dsc.setPrecision(md.getPrecision(i+1));
			dsc.setScale(md.getScale(i+1));
			int nullablestatus = md.isNullable(i+1);
			if(nullablestatus==ResultSetMetaData.columnNoNulls) dsc.setNullAble(false);
			else dsc.setNullAble(true);
		}
		ds.buildColumnMap();
	}
	
	private void setRsDataToDs(ResultSet rs, IDataSet ds) throws SQLException, IOException
	{
		while(rs.next())
		{
			ds.insertNewRowAfterLast();
			DataSetRow dsr = ds.getRows().get(ds.getRows().size()-1);
			for(int i=0;i<rs.getMetaData().getColumnCount();i++)
			{
				Object data = rs.getObject(i+1);
				if(data instanceof Blob)
				{
					InputStream is = ((Blob)data).getBinaryStream();
					byte[] bytes = IOUtil.readBytesFromInputStream(is, -1);
					is.close();
					dsr.getCells().get(i).setContent(bytes);
				}
				else
				{
					dsr.getCells().get(i).setContent((Serializable)rs.getObject(i+1));
				}
			}
		}
	}
	
	private IDataSet doSqlSelect(ParamBean paramBean, boolean tryTableInfo) throws Exception
	{
		IDataSet ds = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String selectSql = paramBean.getSelectSql();
			LogUtil.printlnInfo("[SELECT_SQL]"+selectSql);
			ps = con.prepareStatement(selectSql);
			List<List<Param>> paramsList = paramBean.getParamsList();
			if(paramsList!=null)
			{
				for(List<Param> params : paramsList)
				{
					if(params!=null&&params.size()>0)
					{
						for(int i=0;i<params.size();i++)
						{
							Param param = params.get(i);
							if(param.value!=null)
							{
								if(param.value.getClass().equals(java.util.Date.class))
								{
									ps.setDate(i+1, new java.sql.Date(((java.util.Date)param.value).getTime()));
								}
								else ps.setObject(i+1, param.value);
							}
							else
							{
								ps.setNull(i+1, ps.getParameterMetaData().getParameterType(i+1));
							}
						}
						rs = ps.executeQuery();
						if(ds==null)
						{
							ds = new DataSet();
							initDataSetColumn(ds, rs.getMetaData());
						}
						setRsDataToDs(rs, ds);
						rs.close();
					}
				}
			}
			else
			{
				rs = ps.executeQuery();
				if(ds==null)
				{
					ds = new DataSet();
					initDataSetColumn(ds, rs.getMetaData());
				}
				setRsDataToDs(rs, ds);
				rs.close();
			}
			// 添加主键信息
			if(ds!=null&&tryTableInfo)
			{
				String tableName = SqlUtil.tryToGetTableName(selectSql);
				if(tableName!=null)
				{
					TableInfo tableInfo = getTableInfo(tableName);
					if(tableInfo!=null&&tableInfo.getPkColSet()!=null)
					{
						LinkedHashSet<String> pkColSet = tableInfo.getPkColSet();
						for(DataSetColumn dsc : ds.getColumns())
						{
							if(pkColSet.contains(dsc.getColumnName()))
							{
								dsc.setPrimaryKey(true);
							}
						}
					}
				}
			}
		}
		catch(SQLException se)
		{
			// sqlite在查询时如果没有记录, 则会返回 java.sql.SQLException: Query does not return results, 这里简单返回一个空数据集
			ds = new DataSet();
		}
		catch (Exception e)
		{
			LogUtil.printlnError(e);
			throw e;
		}
		finally
		{
			DBUtil.close(rs);
			DBUtil.close(ps);	
		}
		return ds;
	}
	
	@Override
	public Map<String, String> getAllTableBaseInfo() throws Exception
	{
		String selectSql = "select tbl_name from sqlite_master where type='table' order by tbl_name";
		IDataSet ds = null;
		try
		{
			ds = doSqlSelect(new ParamBean(selectSql), false);
		}
		catch (AlreadyClosedException e)
		{
			LogUtil.printlnError(e);
		}
		Map<String, String> map = new LinkedHashMap<String, String>();
		if(ds!=null)
		{
			int rowCount = ds.getRowCount();
			for(int i=0;i<rowCount;++i)
			{
				map.put(((String)ds.getValue(i, 0)).toLowerCase(), "");
			}
		}
		return map;
	}

	@Override
	public boolean disableConstraint(String tableName, String constraintName) throws Exception
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean enableConstraint(String tableName, String constraintName) throws Exception
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String getLowerFunc(String srcExp)
	{
		return "lower("+srcExp+")";
	}

	@Override
	public String getUpperFunc(String srcExp)
	{
		return "upper("+srcExp+")";
	}

	@Override
	public String getByteLengthFunc(String srcExp)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSubStringFunc(String srcExp, int begin)
	{
		return "substr("+srcExp+","+(begin+1)+")";
	}

	@Override
	public String getSubStringFunc(String srcExp, int begin, int end)
	{
		return "substr("+srcExp+","+(begin+1)+","+(end-begin)+")";
	}

	@Override
	public String getToDateFunc(Date date)
	{
		
		return "strftime('%Y-%m-%d %H:%M:%S', '"+DateUtil.getDateString(date, "yyyy-MM-dd HH:mm:ss")+"')";
	}

	@Override
	protected TableInfo queryTableInfo(String tableName) throws Exception
	{
		TableInfo tableInfo = new TableInfo(tableName);
		// 查询主键约束以及列信息
		String selectSql = "PRAGMA table_info("+tableName.toUpperCase()+")";
		IDataSet ds = null;
		try
		{
			ds = doSqlSelect(new ParamBean(selectSql), false);
		}
		catch (AlreadyClosedException e)
		{
			LogUtil.printlnError(e);
		}
		if(ds!=null)
		{
			// 生成列对象
			int rowCount = ds.getRowCount();
			for(int i=0;i<rowCount;i++)
			{
				DataSetColumn dsc = new DataSetColumn();
				String colName = (String) ds.getValue(i, 1);
				dsc.setColumnName(colName);
				String type = (String) ds.getValue(i, 2);
				int precision = 0;
				int scale = 0;
				int tempIdx = type.indexOf('(');
				if(tempIdx!=-1)
				{
					int endIdx = type.lastIndexOf(')');
					String[] temp = type.substring(tempIdx+1, endIdx).split(",");
					type = type.substring(0, tempIdx);
					precision = ObjectUtil.objectToInt(temp[0]);
					if(temp.length>1) scale = ObjectUtil.objectToInt(temp[1]);
				}
				dsc.setDbColumnType(type);
				int sqlType = getSqlTypeFromDBColumnType(type);
				dsc.setSqlType(sqlType);
				dsc.setJavaClass(getClassNameFromSqlType(sqlType));
				dsc.setPrecision(precision);
				dsc.setScale(scale);
				int notNull = ObjectUtil.objectToInt(ds.getValue(i, 3));
				dsc.setNullAble(notNull==0);
				tableInfo.addDataSetColumn(dsc);
			}
		}
		// 查询所有主键及唯一约束
		selectSql = "PRAGMA index_list("+tableName.toUpperCase()+")";
		ds = null;
		try
		{
			ds = doSqlSelect(new ParamBean(selectSql), false);
		}
		catch (AlreadyClosedException e)
		{
			LogUtil.printlnError(e);
		}
		if(ds!=null)
		{
			// 生成主键的constraint对象
			int rowCount = ds.getRowCount();
			for(int i=0;i<rowCount;i++)
			{
				String origin = ObjectUtil.objectToString(ds.getValue(i, 3));
				if("u".equals(origin)||"pk".equals(origin))
				{
					String constraintName = ObjectUtil.objectToString(ds.getValue(i, 1));
					TableConstraint constraint = new TableConstraint(constraintName);
					int constraintType = TableConstraint.CONSTRAINT_TYPE_UNKNOWN;
					if("u".equals(origin)) constraintType = TableConstraint.CONSTRAINT_TYPE_U;
					else if("pk".equals(origin)) constraintType = TableConstraint.CONSTRAINT_TYPE_PK;
					constraint.setConstraintType(constraintType);
					tableInfo.addConstraint(constraintName, constraint);
					constraint.setTableName(tableName);
					// 查询对应的字段
					selectSql = "PRAGMA index_info("+constraintName+")";
					IDataSet tempDs = null;
					try
					{
						tempDs = doSqlSelect(new ParamBean(selectSql), false);
					}
					catch (AlreadyClosedException e)
					{
						LogUtil.printlnError(e);
					}
					if(tempDs!=null)
					{
						int tempRowCount = tempDs.getRowCount();
						for(int j=0;j<tempRowCount;++j)
						{
							String colName = ObjectUtil.objectToString(tempDs.getValue(j, 2));
							constraint.addColumnName(colName);
							// 如果是主键约束，将主键字段加入tableInfo
							if(constraintType==TableConstraint.CONSTRAINT_TYPE_PK) tableInfo.addPkCol(colName);
						}
					}
				}
			}
		}
		// 查询所有的外键约束
		selectSql = "PRAGMA foreign_key_list("+tableName.toUpperCase()+")";
		ds = null;
		try
		{
			ds = doSqlSelect(new ParamBean(selectSql), false);
		}
		catch (AlreadyClosedException e)
		{
			LogUtil.printlnError(e);
		}
		if(ds!=null)
		{
			int rowCount = ds.getRowCount();
			for(int i=0;i<rowCount;++i)
			{
				int id = ObjectUtil.objectToInt(ds.getValue(i, 0));
				String constraintName = "FK_"+tableName.toUpperCase()+"_"+id;
				TableConstraint constraint = tableInfo.getConstraint(constraintName);
				if(constraint==null)
				{
					constraint = new TableConstraint(constraintName);
					constraint.setConstraintType(TableConstraint.CONSTRAINT_TYPE_FK);
					tableInfo.addConstraint(constraintName, constraint);
					constraint.setTableName(tableName);
					String refTable = ObjectUtil.objectToString(ds.getValue(0, 2)).toUpperCase();
					constraint.setRefTableName(refTable);
					// 获取父类的主键约束名称
					TableInfo parentTableInfo = getTableInfo(refTable);
					for(TableConstraint parentConstraint : parentTableInfo.getConstraintMap().values())
					{
						if(parentConstraint.getConstraintType()==TableConstraint.CONSTRAINT_TYPE_PK)
						{
							constraint.setRefConstraintName(parentConstraint.getConstraintName());
							break;
						}
					}
				}
				String colName = ObjectUtil.objectToString(ds.getValue(i, 3));
				constraint.addColumnName(colName);
			}
		}
		return tableInfo;
	}

	@Override
	protected IDataSet doSqlSelect(ParamBean paramBean) throws Exception
	{
		return doSqlSelect(paramBean, true);
	}

	private ExecuteBean[] splitExecuteBeanForUpdate(ExecuteBean mainEb)
	{
		ExecuteBean[] ebArr = null;
		if(mainEb.getExecuteType()==ExecuteBean.EXECUTE_TYPE_UPDATE&&mainEb.getOldDataMaps()!=null&&!mainEb.getOldDataMaps().isEmpty())
		{
			// 如果是update类型的eb, 并且包含自定义的update条件, 则拆分ExecuteBean为多个独立的子ExecuteBean
			List<ExecuteBean> subEbList = new ArrayList<ExecuteBean>();
			Map<String, Serializable> lastOldDataMap = null;
			ExecuteBean lastSubEb = null;
			boolean isFirst = true;
			for(Map<String, Serializable> dataMap : mainEb.getDataList())
			{
				Map<String, Serializable> oldDataMap = mainEb.getOldDataMaps().get(dataMap);
				boolean needMerge = true;
				if(isFirst)
				{
					isFirst = false;
					needMerge = false;
				}
				else
				{
					// 判断当前的oldDataMap是否与上一个oldDataMap的字段相同
					if(oldDataMap==null&&lastOldDataMap==null)
					{
						// 都为空，则直接并入上一个即可
						needMerge = true;
					}
					else if(oldDataMap!=null&&lastOldDataMap!=null)
					{
						// 都不为空, 则检查两边字段数量和名称是否一致，不一致则新建，一致则并入
						if(oldDataMap.size()==lastOldDataMap.size())
						{
							// 数量一致，检查key是否一致
							for(String tempKey : oldDataMap.keySet())
							{
								if(!lastOldDataMap.containsKey(tempKey))
								{
									// 存在不一样的key, 则需要新建
									needMerge = false;
									break;
								}
							}
						}
						else
						{
							// 数量不一致，需要新建
							needMerge = false;
						}
					}
					else
					{
						// 其中有一个为空，一个不为空，则新建
						needMerge = false;
					}
				}
				if(needMerge) lastSubEb.addData(dataMap, oldDataMap);
				else
				{
					lastSubEb = new ExecuteBean(mainEb.getExecuteType(), mainEb.getTableName());
					lastSubEb.addData(dataMap, oldDataMap);
					subEbList.add(lastSubEb);
					lastOldDataMap = oldDataMap;
				}
			}
			ebArr = subEbList.toArray(new ExecuteBean[0]);
		}
		else ebArr = new ExecuteBean[]{mainEb};
		return ebArr;
	}
	
	private void initInsertEB(ExecuteBean insertEb, Set<String> needColSet, TableInfo tableInfo)
	{
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		LinkedHashSet<String> paramColSet = new LinkedHashSet<String>();
		String tableName = tableInfo.getTableName();
		sb1.append("insert into ").append(tableName).append('(');
		for(String colName : needColSet)
		{
			sb1.append(colName).append(',');
			paramColSet.add(colName);
			sb2.append("?,");
		}
		sb2.deleteCharAt(sb2.length()-1);
		sb1.deleteCharAt(sb1.length()-1).append(") values(").append(sb2).append(')');
		insertEb.setExecuteSql(sb1.toString());
		insertEb.setParamColSet(paramColSet);
	}
	
	private void initUpdateEB(ExecuteBean updateEb, Set<String> needColSet, TableInfo tableInfo)
	{
		StringBuffer sb1 = new StringBuffer();
		LinkedHashSet<String> paramColSet = new LinkedHashSet<String>();
		LinkedHashSet<String> pkColSet = tableInfo.getPkColSet();
		String tableName = tableInfo.getTableName();
		Set<String> whereColSet = new HashSet<String>();
		if(updateEb.getOldDataMaps()!=null)
		{
			for(Map<String, Serializable> dataMap : updateEb.getOldDataMaps().keySet())
			{
				Map<String, Serializable> oldDataMap = updateEb.getOldDataMaps().get(dataMap);
				if(oldDataMap!=null) whereColSet.addAll(oldDataMap.keySet());
			}
		}
		sb1.append("update ").append(tableName).append(" set ");
		for(String colName : needColSet)
		{
			if((whereColSet.isEmpty()&&!pkColSet.contains(colName.toLowerCase()))||!whereColSet.isEmpty())
			{
				sb1.append(colName).append('=');
				paramColSet.add(colName);
				sb1.append("?,");
			}
		}
		sb1.deleteCharAt(sb1.length()-1);
		if(whereColSet.isEmpty())
		{
			// 仅根据主键来生成where
			if(pkColSet!=null&&!pkColSet.isEmpty()) sb1.append(" where ");
			for(String pkColName : pkColSet)
			{
				sb1.append(pkColName).append("=? and ");
				paramColSet.add(pkColName);
			}
			if(!pkColSet.isEmpty()) sb1.delete(sb1.length()-5, sb1.length());
		}
		else
		{
			LinkedHashSet<String> updateWhereColSet = new LinkedHashSet<String>();
			// 支持自定义的where
			sb1.append(" where ");
			for(String whereColName : whereColSet)
			{
				sb1.append(whereColName).append("=? and ");
				updateWhereColSet.add(whereColName);
			}
			sb1.delete(sb1.length()-5, sb1.length());
			updateEb.setUpdateWhereColSet(updateWhereColSet);
		}
		updateEb.setExecuteSql(sb1.toString());
		updateEb.setParamColSet(paramColSet);
	}
	
	private void initDeleteEB(ExecuteBean deleteEb, Set<String> needColSet, TableInfo tableInfo)
	{
		StringBuffer sb1 = new StringBuffer();
		LinkedHashSet<String> paramColSet = new LinkedHashSet<String>();
		String tableName = tableInfo.getTableName();
		sb1.append("delete from ").append(tableName).append(" where ");
		for(String colName : needColSet)
		{
			sb1.append(colName).append("=? and ");
			paramColSet.add(colName);
		}
		sb1.delete(sb1.length()-5, sb1.length());
		deleteEb.setExecuteSql(sb1.toString());
		deleteEb.setParamColSet(paramColSet);
	}
	
	@Override
	protected boolean doSqlExecute(List<? extends ISqlBean> sqlBeanList) throws Exception
	{
		boolean success = false;
		PreparedStatement ps = null;
		try
		{
			for(ISqlBean sb : sqlBeanList)
			{
				if(sb instanceof ExecuteBean)
				{
					ExecuteBean tempEb = (ExecuteBean) sb;
					ExecuteBean[] ebArr = splitExecuteBeanForUpdate(tempEb);
					for(ExecuteBean eb : ebArr)
					{
						if(eb.getExecuteType()==ExecuteBean.EXECUTE_TYPE_OTHER)
						{
							// 这里注意，对于一些set等数据库语句的执行不能使用PreparedStatment
							Statement stmt = con.createStatement();
							stmt.execute(eb.getExecuteSql());
							DBUtil.close(stmt);
							continue;
						}
						TableInfo tableInfo = getTableInfo(eb.getTableName());
						if(eb.getExecuteSql()==null&&eb.getDataList()!=null)
						{
							Set<String> needColSet = new HashSet<String>();
							for(Map<String, Serializable> dataMap : eb.getDataList())
							{
								needColSet.addAll(dataMap.keySet());
							}
							if(eb.getExecuteType()==ExecuteBean.EXECUTE_TYPE_INSERT)
							{
								initInsertEB(eb, needColSet, tableInfo);
							}
							else if(eb.getExecuteType()==ExecuteBean.EXECUTE_TYPE_UPDATE)
							{
								initUpdateEB(eb, needColSet, tableInfo);
							}
							else if(eb.getExecuteType()==ExecuteBean.EXECUTE_TYPE_DELETE)
							{
								initDeleteEB(eb, needColSet, tableInfo);
							}
						}
						if(eb.getExecuteSql()==null) continue;
						// 执行
						String executeSql = eb.getExecuteSql();
						LogUtil.printlnInfo("[EXECUTE_SQL]"+executeSql);
						ps = con.prepareStatement(executeSql);
						if(eb.getDataList()!=null)
						{
							for(Map<String, Serializable> dataMap : eb.getDataList())
							{
								int idx = 0;
								for(String paramCol : eb.getParamColSet())
								{
									++idx;
									DataSetColumn dsc = tableInfo.getDataSetColumn(paramCol);
									if(dsc==null) LogUtil.printlnInfo("Can not find information of column : " + paramCol);
									if(dataMap.containsKey(paramCol))
									{
										if(byte[].class.getName().equals(dsc.getJavaClass()))
										{
											ps.setBytes(idx, (byte[])dataMap.get(paramCol));
										}
										else
										{
											ps.setObject(idx, ObjectUtil.objectCast(dataMap.get(paramCol), dsc.getJavaClass(), XlsysClassLoader.getInstance()), dsc.getSqlType());
										}
									}
									else
									{
										ps.setNull(idx, dsc.getSqlType());
									}
								}
								if(eb.getExecuteType()==ExecuteBean.EXECUTE_TYPE_UPDATE&&eb.getUpdateWhereColSet()!=null)
								{
									Map<String, Serializable> oldDataMap = eb.getOldDataMaps().get(dataMap);
									for(String updateWhereCol : eb.getUpdateWhereColSet())
									{
										if(oldDataMap!=null&&!oldDataMap.isEmpty())
										{
											++idx;
											DataSetColumn dsc = tableInfo.getDataSetColumn(updateWhereCol);
											if(oldDataMap.containsKey(updateWhereCol))
											{
												if(byte[].class.getName().equals(dsc.getJavaClass()))
												{
													ps.setBytes(idx, (byte[])oldDataMap.get(updateWhereCol));
												}
												else
												{
													ps.setObject(idx, ObjectUtil.objectCast(oldDataMap.get(updateWhereCol), dsc.getJavaClass(), XlsysClassLoader.getInstance()), dsc.getSqlType());
												}
											}
											else
											{
												ps.setNull(idx, dsc.getSqlType());
											}
										}
									}
								}
								ps.addBatch();
							}
							ps.executeBatch();
						}
						else
						{
							ps.executeUpdate();
						}
						DBUtil.close(ps);
					}
					
				}
				else if(sb instanceof ParamBean)
				{
					ParamBean pb = (ParamBean) sb;
					LogUtil.printlnInfo("[EXECUTE_SQL]"+pb.getSelectSql());
					ps = con.prepareStatement(pb.getSelectSql());
					List<List<Param>> paramsList = pb.getParamsList();
					if(paramsList!=null)
					{
						for(List<Param> params : paramsList)
						{
							if(params!=null&&params.size()>0)
							{
								for(int i=0;i<params.size();i++)
								{
									Param param = params.get(i);
									if(param.value!=null)
									{
										// 将java.util.Date转为java.sql.Date
										if(param.value.getClass().equals(java.util.Date.class))
										{
											ps.setDate(i+1, new java.sql.Date(((java.util.Date)param.value).getTime()));
										}
										else ps.setObject(i+1, param.value);
									}
									else
									{
										ps.setNull(i+1, getSqlTypeFromClassName(param.javaClass));
									}
								}
								ps.addBatch();
							}
						}
						ps.executeBatch();
					}
					else
					{
						ps.executeUpdate();
					}
					DBUtil.close(ps);
				}
			}
			success = true;
		}
		catch(Exception e)
		{
			success = false;
			LogUtil.printlnError(e);
			if(!DBUtil.getAutoCommit(con)) DBUtil.rollback(con);
			throw e;
		}
		finally
		{
			if(!success) DBUtil.close(ps);
		}
		return success;
	}

	@Override
	protected ParamBean getSplitedParamBean(ParamBean paramBean, int beginRowNum, int endRowNum) throws Exception
	{
		StringBuffer selectSql = new StringBuffer();
		selectSql.append(paramBean.getSelectSql()).append(" limit ").append(endRowNum-beginRowNum+1).append(" offset ").append(beginRowNum);
		ParamBean splitedParamBean = new ParamBean(selectSql.toString());
		splitedParamBean.setParamsList(paramBean.getParamsList());
		return splitedParamBean;
	}

	@Override
	protected void afterSplitedSqlQueried(IDataSet dataSet) {}

	@Override
	protected int getSqlTypeFromJavaClass(Class<?> javaClass)
	{
		int sqlType = Types.OTHER;
		if(String.class==javaClass) sqlType = Types.VARCHAR;
		else if(BigDecimal.class==javaClass) sqlType = Types.NUMERIC;
		else if(Date.class==javaClass) sqlType = Types.DATE;
		else if(Timestamp.class==javaClass) sqlType = Types.TIMESTAMP;
		else if(boolean.class==javaClass||Boolean.class==javaClass) sqlType = Types.NUMERIC;
		else if(byte.class==javaClass||Byte.class==javaClass) sqlType = Types.NUMERIC;
		else if(char.class==javaClass||Character.class==javaClass) sqlType = Types.VARCHAR;
		else if(short.class==javaClass||Short.class==javaClass) sqlType = Types.NUMERIC;
		else if(int.class==javaClass||Integer.class==javaClass) sqlType = Types.NUMERIC;
		else if(long.class==javaClass||Long.class==javaClass) sqlType = Types.NUMERIC;
		else if(float.class==javaClass||Float.class==javaClass) sqlType = Types.NUMERIC;
		else if(double.class==javaClass||Double.class==javaClass) sqlType = Types.NUMERIC;
		else if(byte[].class==javaClass) sqlType = Types.BLOB;
		return sqlType;
	}

	@Override
	protected int getSqlTypeFromSpecialDBColumnType(String dbColumnType)
	{
		int type = Types.OTHER;
		dbColumnType = dbColumnType.toUpperCase();
		if("BLOB_TEXT".equals(dbColumnType)) type = Types.BLOB;
		else if("BOOL".equals(dbColumnType)) type = Types.NUMERIC;
		else if("CURRENCY".equals(dbColumnType)) type = Types.VARCHAR;
		else if("DATETEXT".equals(dbColumnType)) type = Types.VARCHAR;
		else if("DATETIME".equals(dbColumnType)) type = Types.DATE;
		else if("DEC".equals(dbColumnType)) type = Types.NUMERIC;
		else if("DOUBLE PRECISION".equals(dbColumnType)) type = Types.NUMERIC;
		else if("GRAPHIC".equals(dbColumnType)) type = Types.BLOB;
		else if("GUID".equals(dbColumnType)) type = Types.VARCHAR;
		else if("IMAGE".equals(dbColumnType)) type = Types.BLOB;
		else if("INT".equals(dbColumnType)) type = Types.NUMERIC;
		else if("INT64".equals(dbColumnType)) type = Types.NUMERIC;
		else if("LARGEINT".equals(dbColumnType)) type = Types.NUMERIC;
		else if("MEMO".equals(dbColumnType)) type = Types.BLOB;
		else if("NTEXT".equals(dbColumnType)) type = Types.VARCHAR;
		else if("NUMBER".equals(dbColumnType)) type = Types.NUMERIC;
		else if("NVARCHAR2".equals(dbColumnType)) type = Types.VARCHAR;
		else if("PHOTO".equals(dbColumnType)) type = Types.BLOB;
		else if("PICTURE".equals(dbColumnType)) type = Types.BLOB;
		else if("RAW".equals(dbColumnType)) type = Types.BLOB;
		else if("SMALLMONEY".equals(dbColumnType)) type = Types.NUMERIC;
		else if("TEXT".equals(dbColumnType)) type = Types.VARCHAR;
		else if("UNIQUEIDENTIFIER".equals(dbColumnType)) type = Types.VARCHAR;
		else if("VARCHAR2".equals(dbColumnType)) type = Types.VARCHAR;
		else if("WORD".equals(dbColumnType)) type = Types.VARCHAR;
		return type;
	}
}
