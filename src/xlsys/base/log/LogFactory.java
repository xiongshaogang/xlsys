package xlsys.base.log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.cfg.BaseConfig;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.script.XlsysClassLoader;

/**
 * Log工厂类
 * @author Lewis
 *
 */
public class LogFactory extends XlsysFactory<Integer, Log>
{
	private static LogFactory logFactory;
	
	public static synchronized LogFactory getFactoryInstance() throws DocumentException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		if(logFactory==null)
		{
			logFactory = new LogFactory();
		}
		return logFactory;
	}
	
	@Override
	protected void beforeDoLoad()
	{
		if(instanceMap!=null)
		{
			Iterator<Integer> iter = instanceMap.keySet().iterator();
			while(iter.hasNext())
			{
				Log log = instanceMap.get(iter.next());
				log.close();
			}
			instanceMap.clear();
		}
	}

	@Override
	protected void doLoadConfig()
	{
		try
		{
			XmlModel sysCfgXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
			XmlModel logConfigXm = sysCfgXm.getChild("LogConfig");
			int logLevel = Integer.parseInt(logConfigXm.getChild("LogLevel").getText());
			System.setProperty(XLSYS.ENV_LOG_LEVEL, ""+logLevel);
			List<XmlModel> logXmList = logConfigXm.getChilds("Log");
			for(int i=0;i<logXmList.size();i++)
			{
				XmlModel logXm = logXmList.get(i);
				XmlModel idXm = logXm.getChild("id");
				String defaultValue = idXm.getAttributeValue("default");
				int id = Integer.parseInt(idXm.getText());
				defaultValue = defaultValue==null?"false":defaultValue;
				boolean isDefault = Boolean.parseBoolean(defaultValue);
				String logType = logXm.getChild("logType").getText();
				XmlModel paramsXm = logXm.getChild("params");
				String logClassName = null;
				if(logType.indexOf('.')!=-1)
				{
					logClassName = logType;
				}
				else
				{
					logClassName = "xlsys.base.log."+logType.substring(0, 1).toUpperCase()+logType.substring(1)+"Log";
				}
				Constructor<?> constructor = XlsysClassLoader.getInstance().loadClass(logClassName).getDeclaredConstructor(int.class, XmlModel.class);
				constructor.setAccessible(true);
				Log log = (Log) constructor.newInstance(logLevel, paramsXm);
				instanceMap.put(id, log);
				if(i==0) defaultKey = id;
				if(isDefault) defaultKey = id;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
