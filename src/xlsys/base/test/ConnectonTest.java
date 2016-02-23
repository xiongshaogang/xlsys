package xlsys.base.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.pool.OracleDataSource;
import xlsys.base.database.util.DBUtil;

public class ConnectonTest
{
	public static void dbPoolTest()
	{
		
	}
	
	public static void sqlServerTest() throws SQLException
	{
		Connection con = DriverManager.getConnection("jdbc:sqlserver://172.17.5.120:1433;databaseName=xlsys", "xlsys", "xlsys");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from Table_2");
		StringBuilder sb = new StringBuilder();
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		for(int i=1;i<=colCount;++i)
		{
			sb.append("type:"+rsmd.getColumnType(i)).append('\t');
			sb.append("typeName:"+rsmd.getColumnTypeName(i)).append('\t');
			sb.append("className:"+rsmd.getColumnClassName(i)).append('\t');
			sb.append("precision:"+rsmd.getPrecision(i)).append('\t');
			sb.append("scale:"+rsmd.getScale(i)).append('\t');
			sb.append('\n');
		}
		System.out.println(sb.toString());
		con.close();
	}
	
	public static void oracleTest() throws SQLException
	{
		OracleDataSource oracleDataSource = new OracleDataSource();
		oracleDataSource.setURL("jdbc:oracle:thin:@211.155.27.237:1521:xlsys");
		oracleDataSource.setUser("xlsys");
		oracleDataSource.setPassword("Icey121D");
		oracleDataSource.setLoginTimeout(0);
		Connection con = oracleDataSource.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from table_1");
		StringBuilder sb = new StringBuilder();
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		for(int i=1;i<=colCount;++i)
		{
			sb.append("type:"+rsmd.getColumnType(i)).append('\t');
			sb.append("typeName:"+rsmd.getColumnTypeName(i)).append('\t');
			sb.append("className:"+rsmd.getColumnClassName(i)).append('\t');
			sb.append("precision:"+rsmd.getPrecision(i)).append('\t');
			sb.append("scale:"+rsmd.getScale(i)).append('\t');
			sb.append('\n');
		}
		System.out.println(sb.toString());
		con.close();
	}
	
	public static void sqliteTest() throws ClassNotFoundException
	{
		Class.forName("org.sqlite.JDBC");
		Connection con = null;
		try
		{
			con = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement stmt = con.createStatement();
			String selectSql = "PRAGMA foreign_key_list(test2)";
			// String selectSql = "select * from Table_3";
			ResultSet rs = stmt.executeQuery(selectSql);
			StringBuilder sb = new StringBuilder();
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			for(int i=1;i<=colCount;++i)
			{
				sb.append("colName:").append(rsmd.getColumnName(i)).append('\t');
				sb.append("type:"+rsmd.getColumnType(i)).append('\t');
				sb.append("typeName:"+rsmd.getColumnTypeName(i)).append('\t');
				sb.append("className:"+rsmd.getColumnClassName(i)).append('\t');
				sb.append("precision:"+rsmd.getPrecision(i)).append('\t');
				sb.append("scale:"+rsmd.getScale(i)).append('\t');
				sb.append('\n');
			}
			while(rs.next())
			{
				for(int i=1;i<=colCount;++i)
				{
					Object content = rs.getObject(i);
					sb.append(content).append('\t');
					/*if(content!=null)
					{
						sb.append("object:").append(rs.getObject(i).getClass().getName()).append('\t');
						sb.append("content:").append(rs.getObject(i));
					}
					else
					{
						sb.append("object:").append("null").append('\t');
						sb.append("content:").append("null");
					}*/
				}
				sb.append('\n');
			}
			System.out.println(sb.toString());
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DBUtil.close(con);
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		sqliteTest();
	}

}
