package xlsys.base.database.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import xlsys.base.database.IDataBase;

/**
 * 数据库工具
 * @author Lewis
 *
 */
public class DBUtil
{
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
}
