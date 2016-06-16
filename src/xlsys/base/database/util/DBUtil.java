package xlsys.base.database.util;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.database.ConnectionPool;
import xlsys.base.database.IDataBase;
import xlsys.base.database.TableConstraint;
import xlsys.base.database.TableInfo;
import xlsys.base.dataset.DataSetColumn;
import xlsys.base.dataset.DataSetRow;
import xlsys.base.dataset.IDataSet;
import xlsys.base.dataset.StorableDataSet;
import xlsys.base.io.util.FileUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.model.PairModel;

/**
 * 数据库工具
 * @author Lewis
 *
 */
public class DBUtil
{
	public static void dbBackup(IDataBase dataBase, String exportFilePath) throws Exception
	{
		// 执行sql语句, 将返回的dataSet保存成文件
		List<PairModel<String, String>> pairList = getAllTableExportSql(dataBase);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		List<StorableDataSet> sdsList = new ArrayList<StorableDataSet>();
		try
		{
			for(int i=0;i<pairList.size();++i)
			{
				PairModel<String, String> sqlPair = pairList.get(i);
				StorableDataSet sds = new StorableDataSet(dataBase, sqlPair.first, sqlPair.second);
				sds.open();
				sdsList.add(sds);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(baos);
		}
		FileUtil.writeFile(exportFilePath, IOUtil.getObjectBytes(sdsList));
	}
	
	private static List<PairModel<String, String>> getAllTableExportSql(IDataBase dataBase) throws Exception
	{
		List<PairModel<String, String>> pairList = new ArrayList<PairModel<String, String>>();
		// 获取数据库中所有表信息
		Set<String> allTableSet = dataBase.getAllTableBaseInfo().keySet();
		Set<String> handledTableSet = new HashSet<String>();
		Set<String> handlingTableSet = new HashSet<String>();
		for(String tableName : allTableSet) handleExportTable(dataBase, tableName, handlingTableSet, handledTableSet, pairList);
		return pairList;
	}
	
	private static void handleExportTable(IDataBase dataBase, String tableName, Set<String> handlingTableSet, Set<String> handledTableSet, List<PairModel<String, String>> pairList) throws Exception
	{
		if(handledTableSet.contains(tableName)) return;
		handlingTableSet.add(tableName);
		TableInfo tableInfo = dataBase.getTableInfo(tableName);
		for(TableConstraint tc : tableInfo.getConstraintMap().values())
		{
			if(tc.getConstraintType()==TableConstraint.CONSTRAINT_TYPE_FK&&!handlingTableSet.contains(tc.getRefTableName()))
			{
				handleExportTable(dataBase, tc.getRefTableName(), handlingTableSet, handledTableSet, pairList);
			}
		}
		String exportSql = "select * from " + tableName;
		PairModel<String, String> pair = new PairModel<String, String>(exportSql, tableName);
		pairList.add(pair);
		handledTableSet.add(tableName);
		handlingTableSet.remove(tableName);
	}
	
	public static Statement getStatement(Connection con) throws SQLException
	{
		return getStatement(con, false, false);
	}
	
	public static Statement getStatement(Connection con, boolean scroll) throws SQLException
	{
		return getStatement(con, scroll, false);
	}
	
	public static Statement getStatement(Connection con, boolean scroll, boolean updatable) throws SQLException
	{
		Statement stmt = null;
		if(scroll&&updatable)
		{
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else if(scroll&&!updatable)
		{
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		}
		else if(!scroll&&updatable)
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			stmt = con.createStatement();
		}
		return stmt;
	}
	
	public static void commit(Object o)
	{
		try
		{
			if(o instanceof Connection)
			{
				((Connection)o).commit();
			}
			else if(o instanceof IDataBase)
			{
				((IDataBase)o).commit();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static boolean getAutoCommit(Connection con)
	{
		boolean autoCommit = true;
		try
		{
			autoCommit = con.getAutoCommit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return autoCommit;
	}
	
	public static void rollback(Object o)
	{
		try
		{
			if(o instanceof Connection)
			{
				((Connection)o).rollback();
			}
			else if(o instanceof IDataBase)
			{
				((IDataBase)o).rollback();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void close(Object o)
	{
		try
		{
			if(o instanceof IDataBase)
			{
				((IDataBase) o).close();
			}
			else if (o instanceof Connection)
			{
				((Connection) o).close();
			}
			else if(o instanceof Statement)
			{
				((Statement)o).close();
			}
			else if(o instanceof PreparedStatement)
			{
				((PreparedStatement)o).close();
			}
			else if(o instanceof ResultSet)
			{
				((ResultSet)o).close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static String getPKColKey(IDataSet dataSet, int rowAt, LinkedHashSet<String> pkColSet)
	{
		String pkColKey = "";
		for(String pkCol : pkColSet)
		{
			pkColKey += "_"+dataSet.getValue(rowAt, pkCol);
		}
		return pkColKey;
	}
	
	public static void dbRestore(IDataBase dataBase, String dataFilePath) throws Exception
	{
		ArrayList<StorableDataSet> sdsList = (ArrayList<StorableDataSet>) IOUtil.readObject(FileUtil.getByteFromFile(dataFilePath));
		StringBuffer sb = new StringBuffer();
		for(StorableDataSet sds : sdsList)
		{
			// 先获取当前数据库中该表的所有主键数据
			String tableName = sds.getTableName();
			TableInfo tableInfo = null;
			try
			{
				tableInfo = dataBase.getTableInfo(tableName);
			}
			catch(Exception e)
			{
				System.err.println("Can not find table ["+tableName+"], please create table first!");
				continue;
			}
			// 如果sds中的sql是select *开头, 那么对表字段进行检查, 查询下当前表是否包含sds中的所有字段, 如果不包含, 则提示
			if(sds.getSql().toLowerCase().trim().startsWith("select * from "))
			{
				for(DataSetColumn dsc : sds.getColumns())
				{
					if(tableInfo.getDataSetColumn(dsc.getColumnName())==null)
					{
						System.err.println("Can not find column ["+dsc.getColumnName()+"] in table ["+tableName+"], please update table's struct!");
					}
				}
			}
			if(tableInfo.getPkColSet().isEmpty()) continue;
			sb.setLength(0);
			sb.append("select ");
			LinkedHashSet<String> pkColSet = tableInfo.getPkColSet();
			for(String pkCol : pkColSet)
			{
				sb.append(pkCol).append(',');
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(" from ").append(tableName);
			Set<String> keySet = new HashSet<String>();
			IDataSet tempDataSet = dataBase.sqlSelect(sb.toString());
			for(int j=0;j<tempDataSet.getRowCount();++j)
			{
				keySet.add(getPKColKey(tempDataSet, j, pkColSet));
			}
			Field dbfield = sds.getClass().getDeclaredField("dataBase");
			dbfield.setAccessible(true);
			dbfield.set(sds, dataBase);
			sds.setSaveTransaction(false);
			sds.setChanged(true);
			for(int j=0;j<sds.getRowCount();++j)
			{
				DataSetRow row = sds.getRow(j);
				String pkColKey = getPKColKey(sds, j, pkColSet);
				row.setChanged(true);
				if(keySet.contains(pkColKey)) row.setChangeStatus(DataSetRow.STATUS_COMMON);
				else row.setChangeStatus(DataSetRow.STATUS_FOR_NEW);
			}
			if(!sds.save())
			{
				System.err.println("Import fail with table : " + tableName);
			}
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		IDataBase dataBase = null;
		try
		{
			int dbid = 1001; // 目标库的数据库编号
			String exportFilePath = "d:/20160616.data"; // 要导出的数据文件的路径
			dataBase = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
			dbBackup(dataBase, exportFilePath);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DBUtil.close(dataBase);
		}
		System.exit(0);
	}
}
