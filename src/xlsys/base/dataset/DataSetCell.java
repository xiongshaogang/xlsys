package xlsys.base.dataset;

import java.io.Serializable;

import xlsys.base.util.ObjectUtil;

/**
 * 数据集单元类
 * @author Lewis
 *
 */
public class DataSetCell implements Serializable
{
	private static final long serialVersionUID = -1555684345190263233L;
	
	private Serializable content;
	private Serializable oldContent;

	public Serializable getContent()
	{
		return content;
	}

	public void setContent(Serializable content)
	{
		this.content = content;
	}

	public Serializable getOldContent()
	{
		return oldContent;
	}

	public void setOldContent(Serializable oldContent)
	{
		this.oldContent = oldContent;
	}

	@Override
	public String toString()
	{
		return ObjectUtil.objectToString(content);
	}
}
