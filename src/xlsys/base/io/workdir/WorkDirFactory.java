package xlsys.base.io.workdir;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.dom4j.DocumentException;

import xlsys.base.XlsysFactory;
import xlsys.base.cfg.BaseConfig;
import xlsys.base.io.xml.XmlModel;

public class WorkDirFactory extends XlsysFactory<Integer, String>
{
	private static WorkDirFactory workDirFactory;

	public static synchronized WorkDirFactory getFactoryInstance() throws DocumentException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		if(workDirFactory==null)
		{
			workDirFactory = new WorkDirFactory();
		}
		return workDirFactory;
	}
	
	@Override
	protected void beforeDoLoad()
	{
		if(instanceMap!=null)
		{
			instanceMap.clear();
		}
	}

	@Override
	protected void doLoadConfig()
	{
		try
		{
			XmlModel sysCfgXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
			XmlModel workDirConfigXm = sysCfgXm.getChild("WorkDirConfig");
			List<XmlModel> workDirXmList = workDirConfigXm.getChilds("WorkDir");
			for(int i=0;i<workDirXmList.size();i++)
			{
				XmlModel workDirXm = workDirXmList.get(i);
				XmlModel idXm = workDirXm.getChild("id");
				String defaultValue = idXm.getAttributeValue("default");
				int id = Integer.parseInt(idXm.getText());
				defaultValue = defaultValue==null?"false":defaultValue;
				boolean isDefault = Boolean.parseBoolean(defaultValue);
				String dir = workDirXm.getChild("dir").getText();
				instanceMap.put(id, dir);
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
