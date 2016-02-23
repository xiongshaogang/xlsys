package xlsys.base.io.transfer.client;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.dom4j.DocumentException;

import xlsys.base.XlsysFactory;
import xlsys.base.cfg.BaseConfig;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.util.ReflectUtil;

/**
 * 客户端传输工厂类
 * @author Lewis
 *
 */
public class ClientTransferFactory extends XlsysFactory<Integer, ClientTransfer>
{
	private static ClientTransferFactory clientTransferFactory;
	
	public static synchronized ClientTransferFactory getFactoryInstance() throws DocumentException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		if(clientTransferFactory==null)
		{
			clientTransferFactory = new ClientTransferFactory();
		}
		return clientTransferFactory;
	}
	
	@Override
	protected void beforeDoLoad()
	{
		instanceMap.clear();
	}
	
	public static ClientTransfer getClientTransfer(XmlModel transferXm) throws Exception
	{
		return (ClientTransfer) ReflectUtil.getInstanceFromXm(transferXm, "className", "param", "field");
	}

	@Override
	protected void doLoadConfig()
	{
		try
		{
			XmlModel sysCfgXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.CLIENT_CONFIG);
			XmlModel clientTransferXm = sysCfgXm.getChild("ClientTransfer");
			List<XmlModel> transferXmList = clientTransferXm.getChilds("Transfer");
			for(int i=0;i<transferXmList.size();i++)
			{
				XmlModel transferXm = transferXmList.get(i);
				XmlModel idXm = transferXm.getChild("id");
				String defaultValue = idXm.getAttributeValue("default");
				int id = Integer.parseInt(idXm.getText());
				defaultValue = defaultValue==null?"false":defaultValue;
				boolean isDefault = Boolean.parseBoolean(defaultValue);
				ClientTransfer clientTransfer = getClientTransfer(transferXm);
				XmlModel seriModeXm = transferXm.getChild("seriMode");
				if(seriModeXm!=null)
				{
					clientTransfer.setSeriMode((byte)Integer.parseInt(seriModeXm.getText()));
				}
				instanceMap.put(id, clientTransfer);
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
