package xlsys.base.io.xml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import xlsys.base.model.IModel;

/**
 * Xml模型映射实体类
 * @author Lewis
 *
 */
public class XmlModel implements IModel
{
	private static final long serialVersionUID = 1637611381224104242L;
	
	private String name;
	private String text;
	private XmlModel parent;
	private LinkedHashMap<String, String> attribute;
	private List<XmlModel> children;
	
	/**
	 * 构造一个XmlModel对象
	 * @param name 名称
	 * @param text 值
	 */
	public XmlModel(String name, String text)
	{
		this.name = name;
		this.text = text;
	}
	
	/**
	 * 添加一个属性
	 * @param attrName 属性名称
	 * @param attrValue 属性值
	 */
	public void addAttribute(String attrName, String attrValue)
	{
		if(attribute==null)
		{
			attribute = new LinkedHashMap<String, String>();
		}
		attribute.put(attrName, attrValue);
	}
	
	/**
	 * 添加一个XmlModel子节点
	 * @param child
	 */
	public void addChild(XmlModel child)
	{
		if(children==null)
		{
			children = new ArrayList<XmlModel>();
		}
		children.add(child);
		child.parent = this;
	}

	/**
	 * 获取名称
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 获取父节点
	 * @return
	 */
	public XmlModel getParent()
	{
		return parent;
	}

	/**
	 * 获取所有的属性映射
	 * @return
	 */
	public LinkedHashMap<String, String> getAttribute()
	{
		return attribute;
	}
	
	/**
	 * 获取属性值
	 * @param attrName
	 * @return
	 */
	public String getAttributeValue(String attrName)
	{
		if(attribute==null) return null;
		return attribute.get(attrName);
	}

	/**
	 * 获取所有的子节点
	 * @return
	 */
	public List<XmlModel> getChildren()
	{
		return children;
	}
	
	/**
	 * 获取指定名称的子节点
	 * @param name 子节点名称
	 * @return
	 */
	public XmlModel getChild(String name)
	{
		return getChild(name, true);
	}
	
	/**
	 * 获取指定名称的子节点
	 * @param name 子节点名称
	 * @param ignoreCase 是否忽略大小写
	 * @return
	 */
	public XmlModel getChild(String name, boolean ignoreCase)
	{
		XmlModel child = null;
		if(name!=null&&children!=null&&!children.isEmpty())
		{
			for(int i=0;i<children.size();i++)
			{
				String childName = children.get(i).getName();
				if(name.equals(childName)||(ignoreCase&&name.equalsIgnoreCase(childName)))
				{
					child = children.get(i);
					break;
				}
			}
		}
		return child;
	}
	
	/**
	 * 获取指定名称的所有子节点
	 * @param name 子节点名称
	 * @return
	 */
	public List<XmlModel> getChilds(String name)
	{
		return getChilds(name, true);
	}
	
	/**
	 * 获取指定名称的所有子节点
	 * @param name 子节点名称
	 * @param ignoreCase 是否忽略大小写
	 * @return
	 */
	public List<XmlModel> getChilds(String name, boolean ignoreCase)
	{
		List<XmlModel> list = new ArrayList<XmlModel>();
		XmlModel child = null;
		if(name!=null&&children!=null&&!children.isEmpty())
		{
			for(int i=0;i<children.size();i++)
			{
				String childName = children.get(i).getName();
				if(name.equals(childName)||(ignoreCase&&name.equalsIgnoreCase(childName)))
				{
					child = children.get(i);
					list.add(child);
				}
			}
		}
		return list;
	}

	/**
	 * 获取值
	 * @return
	 */
	public String getText()
	{
		return text;
	}
}
