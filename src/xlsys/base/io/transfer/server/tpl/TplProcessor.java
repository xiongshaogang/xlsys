package xlsys.base.io.transfer.server.tpl;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import xlsys.base.util.ReflectUtil;

/**
 * 附加命令处理器
 * @author Lewis
 *
 */
public class TplProcessor
{
	private String templateId;
	private String defaultTemplate;
	private String defaultRedirectPath;
	private Set<TplListener> listeners;
	
	public TplProcessor(String templateId)
	{
		this.templateId = templateId;
		listeners = new LinkedHashSet<TplListener>();
	}
	
	public void addListener(TplListener listener)
	{
		listeners.add(listener);
	}

	public String getTemplateId()
	{
		return templateId;
	}

	public void setTemplateId(String templateId)
	{
		this.templateId = templateId;
	}

	public Set<TplListener> getListeners()
	{
		return listeners;
	}

	public String getDefaultTemplate()
	{
		return defaultTemplate;
	}

	public void setDefaultTemplate(String defaultTemplate)
	{
		this.defaultTemplate = defaultTemplate;
	}

	public String getDefaultRedirectPath()
	{
		return defaultRedirectPath;
	}

	public void setDefaultRedirectPath(String defaultRedirectPath)
	{
		this.defaultRedirectPath = defaultRedirectPath;
	}

	public void process(TplEvent event) throws Exception
	{
		// 调用模板填充前事件
		this.fireEvent("beforeTemplateFill", event);
		if(event.doit&&event.template!=null)
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// 填充模板
			MustacheFactory mf = new DefaultMustacheFactory();
		    Mustache mustache = mf.compile(new StringReader(event.template), templateId);
		    mustache.execute(new PrintWriter(baos), event.fillObj).flush();
		    event.outData = new String(baos.toByteArray(), "utf-8");
		    baos.close();
		    // 调用模板填充后事件
		    this.fireEvent("afterTemplateFill", event);
		    // 输出数据
		    PrintWriter pw = event.response.getWriter();
			pw.write(event.outData);
		}
		// 获取跳转页面
		event.doit = true;
		this.fireEvent("redirect", event);
		if(event.doit&&event.redirectPath!=null)
		{
			event.response.sendRedirect(event.redirectPath);
		}
	}
	
	private void fireEvent(String methodName, TplEvent event) throws Exception
	{
		for(TplListener listener : listeners)
		{
			Method m = ReflectUtil.getDeclaredMethod(listener.getClass(), methodName, TplEvent.class);
			m.setAccessible(true);
			m.invoke(listener, event);
			if(event.interrupt) break;
		}
	}
}
