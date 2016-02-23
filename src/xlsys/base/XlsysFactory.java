package xlsys.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentException;

import xlsys.base.cfg.BaseConfig;
import xlsys.base.database.DBPoolFactory;
import xlsys.base.env.EnvFactory;
import xlsys.base.io.ftp.FtpModelFactory;
import xlsys.base.io.transfer.client.ClientTransferFactory;
import xlsys.base.io.workdir.WorkDirFactory;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.log.LogFactory;
import xlsys.base.script.XlsysClassLoader;

/**
 * 工厂类的抽象类，如果要使用工厂方法创建实例的话，工厂类必须继承自该类
 * @author Lewis
 *
 * @param <K>
 * @param <V>
 */
public abstract class XlsysFactory<K, V>
{
	private static Map<String, XlsysFactory> factoryConfigMap;
	
	protected Map<K, V> instanceMap;
	protected K defaultKey;
	
	protected XlsysFactory()
	{
		instanceMap = new HashMap<K, V>();
		loadConfig();
	}
	
	public static XlsysFactory getFactoryInstance(String factoryName) throws DocumentException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		initSystemFactory(factoryName);
		return factoryConfigMap.get(factoryName);
	}
	
	private synchronized static void initSystemFactory(String factoryName) throws DocumentException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		if(factoryConfigMap==null) factoryConfigMap = new HashMap<String, XlsysFactory>();
		if(!factoryConfigMap.containsKey(factoryName))
		{
			boolean isSystemFactory = false;
			XmlModel configXm = null;
			if(XLSYS.FACTORY_DATABASE.equals(factoryName))
			{
				XmlModel sysCfg = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
				configXm = sysCfg.getChild("DBConPool");
				isSystemFactory = true;
			}
			else if(XLSYS.FACTORY_FTP.equals(factoryName))
			{
				XmlModel sysCfg = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
				configXm = sysCfg.getChild("FtpConfig");
				isSystemFactory = true;
			}
			else if(XLSYS.FACTORY_CLIENT_TRANSFER.equals(factoryName))
			{
				XmlModel sysCfg = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.CLIENT_CONFIG);
				configXm = sysCfg.getChild("ClientTransfer");
				isSystemFactory = true;
			}
			else if(XLSYS.FACTORY_WORKDIR.equals(factoryName))
			{
				XmlModel sysCfg = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
				configXm = sysCfg.getChild("WorkDirConfig");
				isSystemFactory = true;
			}
			else if(XLSYS.FACTORY_LOG.equals(factoryName))
			{
				XmlModel sysCfg = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
				configXm = sysCfg.getChild("LogConfig");
				isSystemFactory = true;
			}
			else if(XLSYS.FACTORY_ENV.equals(factoryName))
			{
				XmlModel sysCfg = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
				configXm = sysCfg.getChild("Env");
				isSystemFactory = true;
			}
			// 注册工厂
			if(configXm!=null) registerfactory(factoryName, configXm);
			if(isSystemFactory&&!factoryConfigMap.containsKey(factoryName))
			{
				// 如果是系统工厂，但是没有注册成功，则直接初始化默认的工厂
				if(XLSYS.FACTORY_DATABASE.equals(factoryName))
				{
					factoryConfigMap.put(XLSYS.FACTORY_DATABASE, DBPoolFactory.getFactoryInstance());
				}
				else if(XLSYS.FACTORY_FTP.equals(factoryName))
				{
					factoryConfigMap.put(XLSYS.FACTORY_FTP, FtpModelFactory.getFactoryInstance());
				}
				else if(XLSYS.FACTORY_CLIENT_TRANSFER.equals(factoryName))
				{
					factoryConfigMap.put(XLSYS.FACTORY_CLIENT_TRANSFER, ClientTransferFactory.getFactoryInstance());
				}
				else if(XLSYS.FACTORY_WORKDIR.equals(factoryName))
				{
					factoryConfigMap.put(XLSYS.FACTORY_WORKDIR, WorkDirFactory.getFactoryInstance());
				}
				else if(XLSYS.FACTORY_LOG.equals(factoryName))
				{
					factoryConfigMap.put(XLSYS.FACTORY_LOG, LogFactory.getFactoryInstance());
				}
				else if(XLSYS.FACTORY_ENV.equals(factoryName))
				{
					factoryConfigMap.put(XLSYS.FACTORY_ENV, EnvFactory.getFactoryInstance());
				}
			}
		}
	}
	
	public static void registerfactory(String factoryName, XmlModel factoryConfig)
	{
		registerfactory(factoryName, factoryConfig, false);
	}
	
	public synchronized static void registerfactory(String factoryName, XmlModel factoryConfig, boolean reload)
	{
		registerfactory(factoryName, factoryConfig, reload, null);
	}

	public synchronized static void registerfactory(String factoryName, XmlModel factoryConfig, boolean reload, ClassLoader classLoader)
	{
		if(factoryConfigMap==null) factoryConfigMap = new HashMap<String, XlsysFactory>();
		if(reload||!factoryConfigMap.containsKey(factoryName)&&factoryConfig!=null)
		{
			XmlModel factoryModel = factoryConfig.getChild("Factory");
			if(factoryModel!=null)
			{
				XmlModel classNameXml = factoryModel.getChild("className");
				XmlModel instanceMethodXml = factoryModel.getChild("instanceMethod");
				if(classNameXml!=null&&instanceMethodXml!=null)
				{
					String className = classNameXml.getText();
					String instanceMethod = instanceMethodXml.getText();
					if(classLoader==null) classLoader = XlsysClassLoader.getInstance();
					try
					{
						Class<?> factoryClass = classLoader.loadClass(className);
						Method[] methods = factoryClass.getDeclaredMethods();
						for(Method m : methods)
						{
							int modifiers = m.getModifiers();
							if(m.getName().equals(instanceMethod)&&Modifier.isStatic(modifiers)&&m.getParameterTypes().length==0)
							{
								XlsysFactory factoryInstance = (XlsysFactory) m.invoke(null);
								factoryConfigMap.put(factoryName, factoryInstance);
								break;
							}
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 加载配置，该方法与loadConfig(false)等价
	 */
	public final void loadConfig()
	{
		loadConfig(false);
	}
	
	/**
	 * 该方法一般用来做重新加载配置前的清理工作
	 */
	protected abstract void beforeDoLoad();
	
	/**
	 * 加载配置
	 * @param reload 该参数决定是否重新加载
	 */
	public final void loadConfig(boolean reload)
	{
		if(instanceMap==null||instanceMap.isEmpty()||reload)
		{
			beforeDoLoad();
			doLoadConfig();
		}
	}
	
	/**
	 * 加载配置的具体行为方法
	 */
	protected abstract void doLoadConfig();
	
	/**
	 * 获取默认的实例，该方法与getInstance(false)等价
	 * @return
	 */
	public final V getInstance()
	{
		return getInstance(false);
	}
	
	/**
	 * 获取默认的实例
	 * @param reload 是否重新加载配置
	 * @return
	 */
	public V getInstance(boolean reload)
	{
		loadConfig(reload);
		return instanceMap.get(defaultKey);
	}
	
	/**
	 * 根据key来获取指定的实例，该方法与getInstance(key, false)等价
	 * @param key
	 * @return
	 */
	public final V getInstance(K key)
	{
		return getInstance(key, false);
	}
	
	/**
	 * 根据key来获取指定的实例
	 * @param key
	 * @param reload 是否重新加载配置
	 * @return
	 */
	public final V getInstance(K key, boolean reload)
	{
		loadConfig(reload);
		return instanceMap.get(key);
	}
	
	/**
	 * 获取实例集合，该方法与getInstances(false)等价
	 * @return
	 */
	public final Collection<V> getInstances()
	{
		return getInstances(false);
	}
	
	/**
	 * 获取实例集合
	 * @param reload 是否重新加载配置
	 * @return
	 */
	public final Collection<V> getInstances(boolean reload)
	{
		loadConfig(reload);
		return instanceMap.values();
	}

	/**
	 * 设置默认实例的key值
	 * @param defaultKey
	 */
	public final void setDefaultKey(K defaultKey)
	{
		this.defaultKey = defaultKey;
	}
}
