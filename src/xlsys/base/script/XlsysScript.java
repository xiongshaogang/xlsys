package xlsys.base.script;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import xlsys.base.exception.UnsupportedException;

/**
 * 系统脚本工具，可运行JavaScript脚本
 * @author Lewis
 *
 */
public class XlsysScript
{
	private static ScriptEngineManager manager = new ScriptEngineManager(XlsysClassLoader.getInstance());
	private ScriptEngine engine;
	private Bindings bindings;
	private String script;
	private CompiledScript compiledScript;
	
	private boolean forInterface;
	
	private boolean changed;
	
	/**
	 * 构造一个脚本对象
	 * @throws UnsupportedException
	 */
	public XlsysScript()
	{
		this("JavaScript", false);
	}
	
	/**
	 * 构造一个脚本对象
	 * @param forInterface 指示该对象是否为了实现Java接口而写
	 * @throws UnsupportedException
	 */
	public XlsysScript(boolean forInterface)
	{
		this("JavaScript", forInterface);
	}
	
	/**
	 * 构造一个脚本对象
	 * @param engineName 脚本引擎名称
	 * @param forInterface 指示该对象是否为了实现Java接口而写
	 * @throws UnsupportedException
	 */
	public XlsysScript(String engineName, boolean forInterface)
	{
		engine = manager.getEngineByName(engineName);
		if(engine==null) throw new RuntimeException("This engine name is not be supported : " + engineName);
		this.forInterface = forInterface;
		if(!forInterface) bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		changed = false;
	}
	
	/**
	 * 返回当前脚本对象是否为了实现Java接口而写
	 * @return
	 */
	public boolean isForInterface()
	{
		return forInterface;
	}
	
	private boolean checkForInterface(boolean throwException) throws UnsupportedException
	{
		if(forInterface&&throwException) throw new UnsupportedException("This script is for interface, it cannot bind any variable!");
		return forInterface;
	}
	
	/**
	 * 获取全局变量的值
	 * @param key 全局变量名
	 * @return
	 * @throws UnsupportedException
	 */
	public Object get(String key) throws UnsupportedException
	{
		checkForInterface(true);
		Object value = null;
		if(bindings!=null) value = bindings.get(key);
		return value;
	}
	
	/**
	 * 放置全局变量
	 * @param key 全局变量名
	 * @param value 全局变量值
	 * @throws UnsupportedException
	 */
	public void put(String key, Object value) throws UnsupportedException
	{
		checkForInterface(true);
		bindings.put(key, value);
		changed = true;
	}
	
	/**
	 * 编译当前脚本.
	 * 可选操作,编译后的执行速度会快一点
	 * @throws ScriptException
	 */
	public void compile() throws ScriptException
	{
		if(script!=null)
		{
			if(compiledScript==null||changed)
			{
				compiledScript = ((Compilable)engine).compile(script);
			}
		}
	}
	
	/**
	 * 执行当前脚本
	 * @return 最后一行的值
	 * @throws ScriptException
	 */
	public Object invoke() throws ScriptException
	{
		Object result = null;
		if(compiledScript!=null)
		{
			if(forInterface) result = compiledScript.eval();
			else result = compiledScript.eval(bindings);
		}
		else
		{
			if(forInterface) result = engine.eval(script);
			else result = engine.eval(script,bindings);
		}
		changed = false;
		return result;
	}
	
	/**
	 * 当要执行的方法只需要传递一个数组参数的时候，调用此方法来执行，否则会出现参数转化错误
	 * @param functionName 方法名
	 * @param param 数组参数
	 * @return
	 * @throws ScriptException
	 */
	public Object invokeFunctionWithOneArrayParam(String functionName, Object[] param) throws ScriptException
	{
		return invoke(functionName, param);
	}
	
	/**
	 * 执行 Js中的方法，注意：当需要传递的参数有且只有一个数组参数的时候，必须调用invokeFunctionWithOneArrayParam来执行
	 * @param functionName 参数名
	 * @param params 参数数组
	 * @return
	 * @throws ScriptException
	 */
	public Object invoke(String functionName, Object... params) throws ScriptException
	{
		Thread.currentThread().setContextClassLoader(XlsysClassLoader.getInstance());
		// 执行方法
		Object result = null;
		if(changed) invoke();
		try
		{
			if(compiledScript!=null)
			{
				result = ((Invocable)compiledScript.getEngine()).invokeFunction(functionName, params);
			}
			else
			{
				result = ((Invocable)engine).invokeFunction(functionName, params);
			}
		}
		catch (NoSuchMethodException e) {}
		return result;
	}
	
	/**
	 * 此方法可返回java接口的实现
	 * @param clasz 要实现的接口class
	 * @return 实现的接口实例
	 * @throws ScriptException
	 * @throws UnsupportedException
	 */
	public <T> T getInterface(Class<T> clasz) throws ScriptException, UnsupportedException
	{
		if(!forInterface) throw new UnsupportedException("This script is not for interface!");
		return ((Invocable)engine).getInterface(clasz);
	}

	/**
	 * 获取脚本字符串
	 * @return
	 */
	public String getScript()
	{
		return script;
	}

	/**
	 * 设置脚本字符串
	 * @param script
	 */
	public void setScript(String script)
	{
		changed = true;
		this.script = script;
	}
}
