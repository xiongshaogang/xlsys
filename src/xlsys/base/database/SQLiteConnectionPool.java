package xlsys.base.database;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dom4j.DocumentException;

import xlsys.base.exception.ParameterNotEnoughException;
import xlsys.base.log.LogUtil;
import xlsys.base.script.XlsysClassLoader;

public class SQLiteConnectionPool extends ConnectionPool
{

	protected SQLiteConnectionPool(int id, String description, String dataSource, String user, String password, int corePoolSize, int maximumPoolSize, int keepAliveTime, int queueCapacity)
			throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException, ParameterNotEnoughException
	{
		super(id, description, dataSource, user, password, corePoolSize, maximumPoolSize, keepAliveTime, queueCapacity);
		XlsysClassLoader.getInstance().loadClass("org.sqlite.JDBC");
	}

	@Override
	protected Connection createConnection()
	{
		Connection con = null;
		try
		{
			con = DriverManager.getConnection(getDataSource());
		}
		catch(SQLException e)
		{
			LogUtil.printlnError(e);
		}
		return con;
	}

	@Override
	protected DataBase newDataBase(Connection con)
	{
		DataBase db = null;
		try
		{
			db = new SQLiteDataBase(this, con);
		}
		catch (Exception e)
		{
			LogUtil.printlnError(e);
		}
		return db;
	}

}
