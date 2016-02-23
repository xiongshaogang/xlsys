package xlsys.base.io.ftp;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.dom4j.DocumentException;

import xlsys.base.XlsysFactory;
import xlsys.base.cfg.BaseConfig;
import xlsys.base.io.xml.XmlModel;

public class FtpModelFactory extends XlsysFactory<Integer, FtpModel>
{
	private static FtpModelFactory ftpModelFactory;

	public static synchronized FtpModelFactory getFactoryInstance() throws DocumentException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		if(ftpModelFactory==null)
		{
			ftpModelFactory = new FtpModelFactory();
		}
		return ftpModelFactory;
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
			XmlModel ftpConfigXm = sysCfgXm.getChild("FtpConfig");
			List<XmlModel> ftpXmList = ftpConfigXm.getChilds("Ftp");
			for(int i=0;i<ftpXmList.size();i++)
			{
				XmlModel ftpXm = ftpXmList.get(i);
				XmlModel idXm = ftpXm.getChild("id");
				String defaultValue = idXm.getAttributeValue("default");
				int id = Integer.parseInt(idXm.getText());
				defaultValue = defaultValue==null?"false":defaultValue;
				boolean isDefault = Boolean.parseBoolean(defaultValue);
				String host = ftpXm.getChild("host").getText();
				int port = Integer.parseInt(ftpXm.getChild("port").getText());
				String user = ftpXm.getChild("user").getText();
				String password = ftpXm.getChild("password").getText();
				FtpModel ftpModel = new FtpModel(id, host, port, user, password);
				instanceMap.put(id, ftpModel);
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
