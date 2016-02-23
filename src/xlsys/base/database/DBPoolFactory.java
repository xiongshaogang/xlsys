package xlsys.base.database;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.cfg.BaseConfig;
import xlsys.base.database.util.DBUtil;
import xlsys.base.dataset.IDataSet;
import xlsys.base.exception.ParameterNotEnoughException;
import xlsys.base.exception.UnsupportedDataSourceException;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.log.LogUtil;
import xlsys.base.util.ObjectUtil;

/**
 * 数据库连接池工厂类
 * @author Lewis
 *
 */
public class DBPoolFactory extends XlsysFactory<Integer, ConnectionPool>
{
	private static DBPoolFactory dbPoolFactory;
	
	// 默认的连接池配置
	private int defaultCorePoolSize;
	private int defaultMaximumPoolSize;
	private int defaultKeepAliveTime;
	private int defaultQueueCapacity;
	private int innerDbId;
	
	private DBPoolFactory() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		super();
	}

	/**
	 * 获取数据库连接工厂类的实例
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws DocumentException
	 */
	public static synchronized DBPoolFactory getFactoryInstance() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		if(dbPoolFactory==null)
		{
			dbPoolFactory = new DBPoolFactory();
		}
		return dbPoolFactory;
	}
	
	@Override
	protected void beforeDoLoad()
	{
		if(instanceMap!=null)
		{
			Iterator<Integer> iter = instanceMap.keySet().iterator();
			while(iter.hasNext())
			{
				ConnectionPool cp = instanceMap.get(iter.next());
				cp.close();
			}
			instanceMap.clear();
		}
	}
	
	private ConnectionPool getConnectionPool(int dbId, String description, String dataSource, String user, String password, int corePoolSize, int maximumPoolSize, int keepAliveTime, int queueCapacity) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException, SQLException, UnsupportedDataSourceException, ParameterNotEnoughException
	{
		ConnectionPool cp = null;
		if(dataSource.toLowerCase().contains("oracle"))
		{
			cp = new OracleConnectionPool(dbId, description, dataSource, user, password, corePoolSize, maximumPoolSize, keepAliveTime, queueCapacity);
		}
		else if(dataSource.toLowerCase().contains("sqlserver"))
		{
			cp = new SqlServerConnectionPool(dbId, description, dataSource, user, password, corePoolSize, maximumPoolSize, keepAliveTime, queueCapacity);
		}
		else if(dataSource.toLowerCase().contains("sqlite"))
		{
			cp = new SQLiteConnectionPool(dbId, description, dataSource, user, password, corePoolSize, maximumPoolSize, keepAliveTime, queueCapacity);
		}
		else
		{
			throw new UnsupportedDataSourceException("系统暂不支持该数据源:"+dataSource);
		}
		return cp;
	}

	@Override
	protected void doLoadConfig()
	{
		try
		{
			XmlModel sysCfgXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
			XmlModel dbConPoolXm = sysCfgXm.getChild("DBConPool");
			XmlModel defaultPoolXm = dbConPoolXm.getChild("DefaultPool");
			defaultCorePoolSize = Integer.parseInt(defaultPoolXm.getChild("corePoolSize").getText());
			if(defaultCorePoolSize<=0) defaultCorePoolSize = 1;
			defaultMaximumPoolSize = Integer.parseInt(defaultPoolXm.getChild("maximumPoolSize").getText());
			if(defaultMaximumPoolSize<=0) defaultMaximumPoolSize = Integer.MAX_VALUE;
			defaultKeepAliveTime = Integer.parseInt(defaultPoolXm.getChild("keepAliveTime").getText());
			if(defaultKeepAliveTime<0) defaultKeepAliveTime = 0;
			defaultQueueCapacity = Integer.parseInt(defaultPoolXm.getChild("queueCapacity").getText());
			// 获取内部数据库配置
			XmlModel innerDbXm = dbConPoolXm.getChild("InnerDataBase");
			XmlModel idXm = innerDbXm.getChild("id");
			innerDbId = Integer.parseInt(idXm.getText());
			String description = innerDbXm.getChild("description").getText();
			String dataSource = innerDbXm.getChild("dataSource").getText();
			String user = innerDbXm.getChild("user").getText();
			String password = innerDbXm.getChild("password").getText();
			int corePoolSize = defaultCorePoolSize;
			if(innerDbXm.getChild("corePoolSize")!=null)
			{
				corePoolSize = Integer.parseInt(innerDbXm.getChild("corePoolSize").getText());
			}
			int maximumPoolSize = defaultMaximumPoolSize;
			if(innerDbXm.getChild("maximumPoolSize")!=null)
			{
				maximumPoolSize = Integer.parseInt(innerDbXm.getChild("maximumPoolSize").getText());
			}
			int keepAliveTime = defaultKeepAliveTime;
			if(innerDbXm.getChild("keepAliveTime")!=null)
			{
				keepAliveTime = Integer.parseInt(innerDbXm.getChild("keepAliveTime").getText());
			}
			int queueCapacity = defaultQueueCapacity;
			if(innerDbXm.getChild("queueCapacity")!=null)
			{
				queueCapacity = Integer.parseInt(innerDbXm.getChild("queueCapacity").getText());
			}
			ConnectionPool innerCp = getConnectionPool(innerDbId, description, dataSource, user, password, corePoolSize, maximumPoolSize, keepAliveTime, queueCapacity);
			instanceMap.put(innerDbId, innerCp);
			// 从内部数据库读取其余连接配置
			IDataBase innerDataBase = null;
			try
			{
				innerDataBase = innerCp.getNewDataBase();
				String selectSql = "select * from xlsys_db";
				IDataSet dataSet = innerDataBase.sqlSelect(selectSql);
				int rowCount = dataSet.getRowCount();
				ScheduledExecutorService service = Executors.newScheduledThreadPool(rowCount+1);
				service.scheduleAtFixedRate(innerCp, 0, 1, TimeUnit.SECONDS);
				for(int i=0;i<rowCount;++i)
				{
					int t_dbid = ObjectUtil.objectToInt(dataSet.getValue(i, "dbid"));
					String t_name = ObjectUtil.objectToString(dataSet.getValue(i, "name"));
					String t_dataSource = ObjectUtil.objectToString(dataSet.getValue(i, "datasource"));
					String t_userName = ObjectUtil.objectToString(dataSet.getValue(i, "username"));
					String t_password = ObjectUtil.objectToString(dataSet.getValue(i, "password"));
					BigDecimal tb_corePoolSize = (BigDecimal) dataSet.getValue(i, "corepoolsize");
					BigDecimal tb_maximumPoolSize = (BigDecimal) dataSet.getValue(i, "maximumpoolsize");
					BigDecimal tb_keepAliveTime = (BigDecimal) dataSet.getValue(i, "keepalivetime");
					BigDecimal tb_queueCapacity = (BigDecimal) dataSet.getValue(i, "queuecapacity");
					int t_corePoolSize = (tb_corePoolSize==null?defaultCorePoolSize:tb_corePoolSize.intValue());
					int t_maximumPoolSize = (tb_maximumPoolSize==null?defaultMaximumPoolSize:tb_maximumPoolSize.intValue());
					int t_keepAliveTime = (tb_keepAliveTime==null?defaultKeepAliveTime:tb_keepAliveTime.intValue());
					int t_queueCapacity = (tb_queueCapacity==null?defaultQueueCapacity:tb_queueCapacity.intValue());
					ConnectionPool connectionPool = getConnectionPool(t_dbid, t_name, t_dataSource, t_userName, t_password, t_corePoolSize, t_maximumPoolSize, t_keepAliveTime, t_queueCapacity);
					service.scheduleAtFixedRate(connectionPool, 0, 1, TimeUnit.SECONDS);
					instanceMap.put(t_dbid, connectionPool);
					if(i==0) defaultKey = t_dbid;
				}
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				DBUtil.close(innerDataBase);
			}
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
		}
	}

	/**
	 * 获取内部数据库连接池
	 * @return
	 */
	public ConnectionPool getInnerConnectionPool()
	{
		loadConfig();
		return instanceMap.get(innerDbId);
	}
	
	/**
	 * 获取所有的dbid
	 * @return
	 */
	public String[] getAllDbId()
	{
		loadConfig();
		List<String> list = new ArrayList<String>();
		for(int dbid : instanceMap.keySet())
		{
			ConnectionPool cp = instanceMap.get(dbid);
			list.add(dbid + XLSYS.CODE_NAME_RELATION + cp.getDescription());
		}
		Collections.sort(list);
		String[] strs = new String[list.size()];
		return list.toArray(strs);
	}

}
