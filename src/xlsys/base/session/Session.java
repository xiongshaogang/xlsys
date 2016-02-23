package xlsys.base.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统会话类
 * @author Lewis
 *
 */
public class Session implements Serializable, Cloneable
{
	private static final long serialVersionUID = 528929304740310932L;
	
	private String sessionId;
	private Integer sessionType;
	private Map<String, Serializable> sessionEnv;
	private transient Map<String, Object> tempEnv;
	
	/**
	 * 使用给定的会话ID构造一个会话对象
	 * @param sessionId
	 */
	public Session(String sessionId)
	{
		this.sessionId = sessionId;
		sessionEnv = new HashMap<String, Serializable>();
		initTempEnv();
	}
	
	private void initTempEnv()
	{
		if(tempEnv==null) tempEnv = new HashMap<String, Object>();
	}

	/**
	 * 设置会话ID
	 * @param sessionId
	 */
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	/**
	 * 获取会话ID
	 * @return
	 */
	public String getSessionId()
	{
		return sessionId;
	}

	/**
	 * 获取会话类型
	 * @return
	 */
	public Integer getSessionType()
	{
		return sessionType;
	}

	/**
	 * 设置会话类型
	 * @param sessionType
	 */
	public void setSessionType(Integer sessionType)
	{
		this.sessionType = sessionType;
	}

	/**
	 * 获取会话环境参数map
	 * @return
	 */
	public Map<String, Serializable> getSessionEnv()
	{
		return sessionEnv;
	}
	
	/**
	 * 获取会话临时环境参数map
	 * @return
	 */
	public Map<String, Object> getTempEnv()
	{
		initTempEnv();
		return tempEnv;
	}
	
	/**
	 * 设置环境变量
	 * @param key 环境变量名
	 * @param value 环境变量值
	 */
	public void setAttribute(String key, Serializable value)
	{
		sessionEnv.put(key, value);
	}
	
	/**
	 * 获取环境变量
	 * @param key 环境变量名
	 * @return 环境变量值
	 */
	public Serializable getAttribute(String key)
	{
		return sessionEnv.get(key);
	}
	
	/**
	 * 设置临时环境变量
	 * @param key 环境变量名
	 * @param value 环境变量值
	 */
	public void setTempAttribute(String key, Object value)
	{
		initTempEnv();
		tempEnv.put(key, value);
	}
	
	/**
	 * 获取临时环境变量
	 * @param key 环境变量名
	 * @return 环境变量值
	 */
	public Object getTempAttribute(String key)
	{
		initTempEnv();
		return tempEnv.get(key);
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		Session cloneSession = new Session(sessionId);
		cloneSession.sessionType = sessionType;
		cloneSession.sessionEnv.putAll(sessionEnv);
		if(tempEnv!=null) cloneSession.tempEnv.putAll(tempEnv);
		return cloneSession;
	}
	
}
