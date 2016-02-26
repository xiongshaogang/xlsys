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
import xlsys.base.util.DateUtil;
import xlsys.base.util.ObjectUtil;

/**
 * sqlserver数据库交互类
 * @author Lewis
 *
 */
public class SqlServerDataBase extends DataBase
{

	protected SqlServerDataBase(ConnectionPool conPool, Connection con)
			throws NoSuchMethodException, SecurityException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, DocumentException
	{
		super(conPool, con);
	}

	private void initDataSetColumn(IDataSet ds, ResultSetMetaData md) throws SQLException, ClassNotFoundException
	{
		int colCount = md.getColumnCount();
		for(int i=0;i<colCount;i++)
		{
			ds.insertNewColumnAfterLast();
			DataSetColumn dsc = ds.getColumns().get(ds.getColumns().size()-1);
			dsc.setColumnName(md.getColumnName(i+1).toLowerCase());
			int sqlType = md.getColumnType(i+1);
			dsc.setSqlType(sqlType);
			dsc.setDbColumnType(md.getColumnTypeName(i+1));
			dsc.setJavaClass(getClassNameFromSqlType(sqlType));
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
				else if(data instanceof Number&&!(data instanceof BigDecimal))
				{
					dsr.getCells().get(i).setContent(new BigDecimal(rs.getObject(i+1).toString()));
				}
				else
				{
					dsr.getCells().get(i).setContent((Serializable)rs.getObject(i+1));
				}
			}
		}
	}
	
	@Override
	public int getResultCount(ParamBean paramBean) throws Exception
	{
		StringBuffer selectSql = new StringBuffer();
		String srcSql = paramBean.getSelectSql().trim();
		int idx = srcSql.toLowerCase().lastIndexOf(" order by ");
		if(idx!=-1) srcSql = "select top 100 percent " + srcSql.substring(7);
		selectSql.append("select count(1) from (").append(srcSql).append(") lxd_sub");
		ParamBean countBean = new ParamBean(selectSql.toString());
		countBean.setParamsList(paramBean.getParamsList());
		return ((BigDecimal)sqlSelectAsOneValue(countBean)).intValue();
	}

	@Override
	protected IDataSet doSqlSelect(ParamBean paramBean) throws Exception
	{
		return doSqlSelect(paramBean, true);
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
								//ps.setNull(i+1, DBUtil.getOracleSqlTypeFromJavaClass(param.javaClass));
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
											ps.setObject(idx, dataMap.get(paramCol), dsc.getSqlType());
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
													ps.setObject(idx, oldDataMap.get(updateWhereCol), dsc.getSqlType());
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
										//ps.setNull(i+1, DBUtil.getOracleSqlTypeFromJavaClass(param.javaClass));
										// 注意在sql server中语句该写空格的地方一定要写空格，要不会引发奇怪的BUG
										ps.setNull(i+1, ps.getParameterMetaData().getParameterType(i+1));
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
	public Map<String, String> getAllTableBaseInfo() throws Exception
	{
		String selectSql = "select t.name, cast(ep.value as varchar(max)) as value from sys.tables t left join sys.extended_properties ep on t.object_id=ep.major_id and ep.minor_id=0";
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
				map.put(((String)ds.getValue(i, 0)).toLowerCase(), (String)ds.getValue(i, 1));
			}
		}
		return map;
	}
	
	@Override
	protected TableInfo queryTableInfo(String tableName) throws Exception
	{
		TableInfo tableInfo = new TableInfo(tableName);
		// 查询表注释
		String selectSql = "select cast(ep.value as varchar(max)) as value from sys.tables t, sys.extended_properties ep where t.name=? and t.object_id=ep.major_id and ep.minor_id=0";
		ParamBean pb = new ParamBean(selectSql);
		pb.addParamGroup();
		pb.setParam(1, tableName.toLowerCase());
		IDataSet tempDataSet = doSqlSelect(pb, false);
		String tableComment = null;
		if(tempDataSet.getRowCount()>0) tableComment = (String) tempDataSet.getValue(0, 0);
		tableInfo.setTableComment(tableComment);
		// 查询所有的列信息
		selectSql = "select c.name as colname, p.name as typename, c.max_length, c.precision, c.scale, c.is_nullable from sys.tables t, sys.columns c, sys.types p where t.name=? and t.object_id=c.object_id and c.system_type_id=p.system_type_id and c.user_type_id=p.user_type_id";
		pb = new ParamBean(selectSql);
		pb.addParamGroup();
		pb.setParam(1, tableName.toLowerCase());
		tempDataSet = doSqlSelect(pb, false);
		int rowCount = tempDataSet.getRowCount();
		for(int i=0;i<rowCount;++i)
		{
			DataSetColumn dsc = new DataSetColumn();
			String colName = ObjectUtil.objectToString(tempDataSet.getValue(i, 0)).toLowerCase();
			dsc.setColumnName(colName);
			String dataType = ObjectUtil.objectToString(tempDataSet.getValue(i, 1));
			dsc.setDbColumnType(dataType);
			int sqlType = getSqlTypeFromDBColumnType(dataType);
			dsc.setSqlType(sqlType);
			dsc.setJavaClass(getClassNameFromSqlType(sqlType));
			Integer dataLength = ObjectUtil.objectToInt(tempDataSet.getValue(i, 2));
			if(dataLength!=null) dsc.setPrecision(dataLength);
			Integer precision = ObjectUtil.objectToInt(tempDataSet.getValue(i, 3));
			if(precision!=null) dsc.setPrecision(precision);
			Integer scale = ObjectUtil.objectToInt(tempDataSet.getValue(i, 4));
			if(scale!=null) dsc.setScale(scale);
			boolean nullable = ObjectUtil.objectToBoolean(tempDataSet.getValue(i, 5));
			dsc.setNullAble(nullable);
			tableInfo.addDataSetColumn(dsc);
		}
		// 查询所有的列注释
		selectSql = "select c.name, cast(ep.value as varchar(max)) as value from sys.tables t, sys.columns c, sys.extended_properties ep where t.name=? and t.object_id=c.object_id and t.object_id=ep.major_id and c.column_id=ep.minor_id";
		pb = new ParamBean(selectSql);
		pb.addParamGroup();
		pb.setParam(1, tableName.toLowerCase());
		tempDataSet = doSqlSelect(pb, false);
		rowCount = tempDataSet.getRowCount();
		for(int i=0;i<rowCount;++i)
		{
			tableInfo.addColumnComment(ObjectUtil.objectToString(tempDataSet.getValue(i, 0)).toLowerCase(), ObjectUtil.objectToString(tempDataSet.getValue(i, 1)));
		}
		// 查询主外键约束
		selectSql = "select sub.CONSTRAINT_NAME as constraint_name,sub.TABLE_NAME as table_name,sub.CONSTRAINT_TYPE as constraint_type,rc.UNIQUE_CONSTRAINT_NAME as r_constraint_name,tc1.TABLE_NAME as r_table_name,sub.COLUMN_NAME as column_name from (select tc.CONSTRAINT_NAME,tc.TABLE_NAME,tc.CONSTRAINT_TYPE,ccu.COLUMN_NAME from INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc,INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE ccu where tc.CONSTRAINT_NAME=ccu.CONSTRAINT_NAME and tc.TABLE_NAME=?) sub left join INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS rc on rc.CONSTRAINT_NAME=sub.CONSTRAINT_NAME left join INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc1 on rc.UNIQUE_CONSTRAINT_NAME=tc1.CONSTRAINT_NAME";
		pb = new ParamBean(selectSql);
		pb.addParamGroup();
		pb.setParam(1, tableName.toLowerCase());
		IDataSet ds = null;
		try
		{
			ds = doSqlSelect(pb, false);
		}
		catch (AlreadyClosedException e)
		{
			LogUtil.printlnError(e);
		}
		if(ds!=null)
		{
			// 生成constraint对象
			rowCount = ds.getRowCount();
			for(int i=0;i<rowCount;i++)
			{
				String constraintName = (String) ds.getValue(i, "constraint_name");
				TableConstraint constraint = tableInfo.getConstraint(constraintName);
				if(constraint==null)
				{
					constraint = new TableConstraint(constraintName);
					tableInfo.addConstraint(constraintName, constraint);
					constraint.setTableName(tableName);
					String constraintTypeStr = (String) ds.getValue(i, "constraint_type");
					int constraintType = TableConstraint.CONSTRAINT_TYPE_UNKNOWN;
					if("PRIMARY KEY".equals(constraintTypeStr)) constraintType = TableConstraint.CONSTRAINT_TYPE_PK;
					else if("FOREIGN KEY".equals(constraintTypeStr)) constraintType = TableConstraint.CONSTRAINT_TYPE_FK;
					constraint.setConstraintType(constraintType);
					String refConstraintName = ObjectUtil.objectToString(ds.getValue(i, "r_constraint_name"));
					constraint.setRefConstraintName(refConstraintName);
					String refTableName = ObjectUtil.objectToString(ds.getValue(i, "r_table_name"));
					constraint.setRefTableName(refTableName);
				}
				String columnName = (String) ds.getValue(i, "column_name");
				constraint.addColumnName(columnName);
				// 如果是主键约束，将主键字段加入tableInfo
				if(constraint.getConstraintType()==TableConstraint.CONSTRAINT_TYPE_PK) tableInfo.addPkCol(columnName);
			}
		}
		// 查询唯一键约束
		selectSql = "select i.name as constraint_name, t.name as table_name, c.name as column_name from sys.tables t, sys.indexes i, sys.index_columns ic, sys.columns c where t.name=? and t.object_id=i.object_id and i.is_unique=1 and t.object_id=ic.object_id and i.index_id=ic.index_id and t.object_id=c.object_id and ic.column_id=c.column_id order by i.index_id, ic.index_column_id";
		pb = new ParamBean(selectSql);
		pb.addParamGroup();
		pb.setParam(1, tableName.toLowerCase());
		ds = null;
		try
		{
			ds = doSqlSelect(pb, false);
		}
		catch (AlreadyClosedException e)
		{
			LogUtil.printlnError(e);
		}
		if(ds!=null)
		{
			rowCount = ds.getRowCount();
			for(int i=0;i<rowCount;i++)
			{
				String constraintName = (String) ds.getValue(i, "constraint_name");
				TableConstraint constraint = tableInfo.getConstraint(constraintName);
				if(constraint==null)
				{
					constraint = new TableConstraint(constraintName);
					tableInfo.addConstraint(constraintName, constraint);
					constraint.setTableName(tableName);
					constraint.setConstraintType(TableConstraint.CONSTRAINT_TYPE_U);
				}
				String columnName = (String) ds.getValue(i, "column_name");
				constraint.addColumnName(columnName);
			}
		}
		return tableInfo;
	}

	@Override
	protected ParamBean getSplitedParamBean(ParamBean paramBean, int beginRowNum, int endRowNum) throws Exception
	{
		return getSplitedParamBean2008(paramBean, beginRowNum, endRowNum);
	}
	
	/**
	 * For SqlServer 2005/2008
	 */
	protected ParamBean getSplitedParamBean2008(ParamBean paramBean, int beginRowNum, int endRowNum) throws Exception
	{
		StringBuffer selectSql = new StringBuffer();
		// 获取原始sql中最后一次出现的order by语句
		String srcSql = paramBean.getSelectSql();
		int idx = srcSql.toLowerCase().lastIndexOf(" order by ");
		String orderBy = null;
		if(idx!=-1)
		{
			orderBy = srcSql.substring(idx+10);
			srcSql = srcSql.substring(0, idx);
		}
		if(orderBy==null) orderBy = "(select 1) asc";
		selectSql.append("select * from (select *,row_number() over (order by ").append(orderBy).append(") as lxd_rn from (");
		selectSql.append(srcSql).append(") lxd_sub) lxd_sub1 where lxd_rn between ").append(beginRowNum+1).append(" and ").append(endRowNum+1);
		ParamBean splitedParamBean = new ParamBean(selectSql.toString());
		splitedParamBean.setParamsList(paramBean.getParamsList());
		return splitedParamBean;
	}
	
	/**
	 * For SqlServer 2012
	 */
	protected ParamBean getSplitedParamBean2012(ParamBean paramBean, int beginRowNum, int endRowNum) throws Exception
	{
		StringBuffer selectSql = new StringBuffer();
		String srcSql = paramBean.getSelectSql();
		selectSql.append(srcSql).append(" offset ").append(beginRowNum+1).append(" rows fetch next ").append(endRowNum-beginRowNum+1).append(" rows only");
		ParamBean splitedParamBean = new ParamBean(selectSql.toString());
		splitedParamBean.setParamsList(paramBean.getParamsList());
		return splitedParamBean;
	}
	
	/*@Override
	protected ParamBean getSplitedParamBean(ParamBean paramBean, int beginRowNum, int endRowNum) throws Exception
	{
		StringBuffer selectSql = new StringBuffer();
		String srcSql = paramBean.getSelectSql().trim();
		int idx = srcSql.toLowerCase().lastIndexOf(" order by ");
		if(idx!=-1) srcSql = "select top 100 percent " + srcSql.substring(7);
		selectSql.append("select * from (select *,row_number() over (order by (select 1) asc) as lxd_rn from (");
		selectSql.append(srcSql).append(") lxd_sub) lxd_sub1 where lxd_rn between ").append(beginRowNum+1).append(" and ").append(endRowNum+1);
		ParamBean splitedParamBean = new ParamBean(selectSql.toString());
		splitedParamBean.setParamsList(paramBean.getParamsList());
		return splitedParamBean;
	}*/

	@Override
	protected void afterSplitedSqlQueried(IDataSet dataSet)
	{
		int colIdx = dataSet.getColumnIndex("lxd_rn");
		if(colIdx!=-1)
		{
			dataSet.setEventLock(true);
			dataSet.removeColumn(colIdx);
			dataSet.setEventLock(false);
		}
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
	public String getSubStringFunc(String srcExp, int begin)
	{
		String lenFuncStr = getByteLengthFunc(srcExp);
		return "substring("+srcExp+","+(begin+1)+","+lenFuncStr+"-"+begin+")";
	}
	
	@Override
	public String getSubStringFunc(String srcExp, int begin, int end)
	{
		return "substring("+srcExp+","+(begin+1)+","+(end-begin)+")";
	}

	@Override
	public String getByteLengthFunc(String srcExp)
	{
		return "datalength("+srcExp+")";
	}
	
	@Override
	public String getToDateFunc(Date date)
	{
		return "cast('"+DateUtil.getDateString(date, "yyyy-MM-dd HH:mm:ss")+"' as datetime)";
	}

	@Override
	public boolean disableConstraint(String tableName, String constraintName) throws Exception
	{
		String executeSql = "alter table "+tableName+" nocheck constraint "+constraintName;
		return sqlExecute(executeSql);
	}

	@Override
	public boolean enableConstraint(String tableName, String constraintName) throws Exception
	{
		String executeSql = "alter table "+tableName+" check constraint "+constraintName;
		return sqlExecute(executeSql);
	}

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
		else if(byte[].class==javaClass) sqlType = Types.VARBINARY;
		return sqlType;
	}

	@Override
	protected int getSqlTypeFromSpecialDBColumnType(String dbColumnType)
	{
		int sqlType = Types.OTHER;
		dbColumnType = dbColumnType.toLowerCase();
		if("datetime".equals(dbColumnType)) sqlType = Types.TIMESTAMP;
		else if("datetime2".equals(dbColumnType)) sqlType = Types.TIMESTAMP;
		else if("datetimeoffset".equals(dbColumnType)) sqlType = Types.TIMESTAMP;
		else if("image".equals(dbColumnType)) sqlType = Types.VARBINARY;
		else if("int".equals(dbColumnType)) sqlType = Types.NUMERIC;
		else if("money".equals(dbColumnType)) sqlType = Types.NUMERIC;
		else if("ntext".equals(dbColumnType)) sqlType = Types.VARCHAR;
		else if("smalldatetime".equals(dbColumnType)) sqlType = Types.TIMESTAMP;
		else if("smallmoney".equals(dbColumnType)) sqlType = Types.NUMERIC;
		else if("text".equals(dbColumnType)) sqlType = Types.VARCHAR;
		else if("uniqueidentifier".equals(dbColumnType)) sqlType = Types.VARCHAR;
		else if("xml".equals(dbColumnType)) sqlType = Types.VARCHAR;
		return sqlType;
	}
}
