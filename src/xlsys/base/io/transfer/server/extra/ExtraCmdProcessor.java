package xlsys.base.io.transfer.server.extra;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

/**
 * 附加命令处理器
 * @author Lewis
 *
 */
public class ExtraCmdProcessor
{
	private String extraCmd;
	private String defaultDispatchPath;
	private Set<ExtraCmdListener> listeners;
	
	public ExtraCmdProcessor(String extraCmd)
	{
		this.extraCmd = extraCmd;
		listeners = new LinkedHashSet<ExtraCmdListener>();
	}
	
	public void addListener(ExtraCmdListener listener)
	{
		listeners.add(listener);
	}

	public String getExtraCmd()
	{
		return extraCmd;
	}

	public Set<ExtraCmdListener> getListeners()
	{
		return listeners;
	}

	public String getDefaultDispatchPath()
	{
		return defaultDispatchPath;
	}

	public void setDefaultDispatchPath(String defaultDispatchPath)
	{
		this.defaultDispatchPath = defaultDispatchPath;
	}

	public void process(ExtraCmdEvent event) throws ServletException, IOException
	{
		// 处理请求
		for(ExtraCmdListener listener : listeners)
		{
			listener.doPost(event);
			if(event.interrupt) break;
		}
		if(event.doit)
		{
			// 跳转页面
			for(ExtraCmdListener listener : listeners)
			{
				listener.dispatch(event);
				if(event.interrupt) break;
			}
			if(event.doit&&event.dispatchPath!=null)
			{
				RequestDispatcher dispatcher = event.request.getRequestDispatcher(event.dispatchPath);
		        dispatcher.forward(event.request, event.response); 
			}
		}
	}
}
