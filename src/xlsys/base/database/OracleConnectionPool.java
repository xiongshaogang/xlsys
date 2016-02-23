package xlsys.base.database;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;
import xlsys.base.exception.ParameterNotEnoughException;
import xlsys.base.log.LogUtil;

import org.dom4j.DocumentException;

/**
 * oracle数据库连接池的实现类
 * @author Lewis
 *
 */
public class OracleConnectionPool extends ConnectionPool
{
	private OracleDataSource oracleDataSource;
	
	protected OracleConnectionPool(int id, String description,
			String dataSource, String user, String password, int corePoolSize, int maximumPoolSize,
			int keepAliveTime, int queueCapacity) throws NoSuchMethodException,
			SecurityException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, DocumentException, SQLException, ParameterNotEnoughException
	{
		super(id, description, dataSource, user, password, corePoolSize, maximumPoolSize,
				keepAliveTime, queueCapacity);
		oracleDataSource = new OracleDataSource();
		oracleDataSource.setURL(dataSource);
		oracleDataSource.setUser(user);
		oracleDataSource.setPassword(password);
		oracleDataSource.setLoginTimeout(0);
	}

	@Override
	protected Connection createConnection()
	{
		Connection con = null;
		boolean success = false;
		while(!success)
		{
			try
			{
				con = oracleDataSource.getConnection();
				success = true;
			}
			catch (Exception e)
			{
				LogUtil.printlnError(e);
			}
		}
		return con;
	}

	@Override
	protected DataBase newDataBase(Connection con)
	{
		DataBase db = null;
		try
		{
			db = new OracleDataBase(this, con);
		}
		catch (Exception e)
		{
			LogUtil.printlnError(e);
		}
		return db;
	}


}
