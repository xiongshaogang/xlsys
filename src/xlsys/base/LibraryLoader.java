package xlsys.base;

import java.io.File;
import java.util.List;

import xlsys.base.cfg.BaseConfig;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.log.LogUtil;

/**
 * 动态库加载器
 * @author Lewis
 *
 */
public class LibraryLoader
{
	private static boolean loaded = false;
	
	/**
	 * 从系统配置文件中加载动态库
	 */
	public static synchronized void loadLibrary()
	{
		if(!loaded)
		{
			loaded = true;
			try
			{
				XmlModel sysXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
				XmlModel slXm = sysXm.getChild("SharedLib");
				if(slXm!=null)
				{
					List<XmlModel> lfList = slXm.getChilds("libFile");
					for(XmlModel lfXm : lfList)
					{
						try
						{
							String sharedLib = lfXm.getText();
							if(sharedLib.indexOf('.')==-1)
							{
								// 使用loadLibrary加载
								System.out.println("Prepare to load library : " + sharedLib);
								//LogUtil.printlnInfo("Prepare to load library : " + sharedLib);
								Runtime.getRuntime().loadLibrary(sharedLib);
								System.out.println("Shared Library Loaded : " + sharedLib);
								//LogUtil.printlnInfo("Shared Library Loaded : " + sharedLib);
							}
							else
							{
								// 使用load加载
								File file = new File(sharedLib).getAbsoluteFile();
								System.out.println("Prepare to load library : " + file.getAbsolutePath());
								//LogUtil.printlnInfo("Prepare to load library : " + file.getAbsolutePath());
								if(file.exists()&&file.isFile())
								{
									// if(!file.isAbsolute()) file = new File(file.getAbsolutePath());
									Runtime.getRuntime().load(file.getPath());
									System.out.println("Shared Library Loaded : " + file.getPath());
									//LogUtil.printlnInfo("Shared Library Loaded : " + file.getPath());
								}
							}
						}
						catch(Exception e1)
						{
							LogUtil.printlnError(e1);
						}
					}
				}
			}
			catch(Exception e)
			{
				LogUtil.printlnError(e);
			}
		}
	}
}
