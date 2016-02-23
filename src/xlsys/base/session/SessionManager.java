package xlsys.base.session;

import xlsys.base.XLSYS;
import xlsys.base.cfg.BaseConfig;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.util.ReflectUtil;

/**
 * 系统会话管理器
 * @author Lewis
 *
 */
public class SessionManager
{
	private static SessionManager sessionManager;
	
	private Session currentSession;
	
	public Session defaultSession;
	
	protected SessionManager()
	{
		defaultSession = new Session(XLSYS.SESSION_DEFAULT_ID);
	}
	
	/**
	 * 获取一个会话管理器实例
	 * @return
	 */
	public synchronized static SessionManager getInstance()
	{
		if(sessionManager==null)
		{
			try
			{
				XmlModel sysCfgXm = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SYSTEM_CONFIG);
				XmlModel managerXm = sysCfgXm.getChild("Session").getChild("Manager");
				sessionManager = (SessionManager) ReflectUtil.getInstanceFromXm(managerXm, "className", "param", "field");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				sessionManager = new SessionManager();
			}
		}
		return sessionManager;
	}
	
	/**
	 * 设置当前会话
	 * @param session 当前会话
	 */
	public void setCurrentSession(Session session)
	{
		currentSession = session;
	}
	
	/**
	 * 获取当前会话
	 * @return 当前会话
	 */
	public Session getCurrentSession()
	{
		return currentSession;
	}
}
