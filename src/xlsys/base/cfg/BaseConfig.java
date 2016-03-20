package xlsys.base.cfg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.io.xml.util.XmlUtil;

/**
 * 配置文件读取类，所有的系统配置都会通过该类进行加载
 * @author Lewis
 *
 */
public class BaseConfig
{
	// 配置文件配置
	private static BaseConfig baseConfig;
	
	private Map<String, XmlModel> configMap;
	
	// 系统配置
	public final static String SYSTEM_CONFIG = "Syscfg";
	public final static String SERVER_CONFIG = "Servercfg";
	public final static String CLIENT_CONFIG = "Clientcfg";
	public final static String TASK_CONFIG = "Taskcfg";
	public final static String EM_CONFIG = "Emcfg";
	
	private BaseConfig() throws DocumentException
	{
		configMap = new HashMap<String, XmlModel>();
		loadConfig();
	}
	
	private void loadConfig() throws DocumentException
	{
		loadConfig(null, false);
	}
	
	private synchronized void loadConfig(String cfgName, boolean reload) throws DocumentException
	{
		if(cfgName==null)
		{
			cfgName = XLSYS.CONFIG_DEFAULT_NAME;
		}
		if(!configMap.containsKey(cfgName)||reload)
		{
			String filePath = "";
			if(XLSYS.CONFIG_DEFAULT_NAME.equals(cfgName)) filePath += "config.xml";
			else
			{
				XmlModel baseCfg = configMap.get(XLSYS.CONFIG_DEFAULT_NAME);
				List<XmlModel> cfgList = baseCfg.getChilds("Cfg");
				for(int i=0;i<cfgList.size();i++)
				{
					XmlModel cfgXM = cfgList.get(i);
					XmlModel cfgNameXM = cfgXM.getChild("cfgName");
					if(cfgNameXM!=null&&cfgName.equals(cfgNameXM.getText()))
					{
						XmlModel cfgPathXM = cfgXM.getChild("cfgPath");
						if(cfgPathXM!=null)
						{
							filePath += cfgPathXM.getText();
							break;
						}
					}
				}
			}
			if(filePath.length()!=0)
			{
				XmlModel cfg = XmlUtil.readXml(filePath);
				configMap.put(cfgName, cfg);
			}
		}
	}
	
	/**
	 * 获取基础配置实例
	 * @return
	 * @throws DocumentException
	 */
	public synchronized static final BaseConfig getInstance() throws DocumentException
	{
		if(baseConfig==null)
		{
			baseConfig = new BaseConfig();
		}
		return baseConfig;
	}
	
	/**
	 * 根据配置名称获取对应的Xml模型。该方法与getConfigXmlModel(cfgName, false)等价
	 * @param cfgName 配置名称
	 * @return
	 * @throws DocumentException
	 */
	public final XmlModel getConfigXmlModel(String cfgName) throws DocumentException
	{
		return getConfigXmlModel(cfgName, false);
	}
	
	/**
	 * 根据配置名称获取对应的Xml模型
	 * @param cfgName 配置名称
	 * @param reset 是否重新加载配置
	 * @return
	 * @throws DocumentException
	 */
	public final XmlModel getConfigXmlModel(String cfgName, boolean reset) throws DocumentException
	{
		XmlModel xmlModel = null;
		loadConfig(cfgName, reset);
		if(configMap.containsKey(cfgName))
		{
			xmlModel = configMap.get(cfgName);
		}
		return xmlModel;
	}
}
