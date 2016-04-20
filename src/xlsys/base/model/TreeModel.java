package xlsys.base.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形结构的模型实现
 * @author Lewis
 *
 * @param <T>
 */
public class TreeModel<T> implements IModel
{
	private static final long serialVersionUID = -4374112396898893358L;

	/**
	 * 元素可重复
	 */
	public static final int STYLE_NORMAL = 0;
	/**
	 * 元素不可重复
	 */
	public static final int STYLE_UNIQUE = 1;
	
	private int style;
	private Integer id;
	private String name;
	private T data;
	private TreeModel<T> parent;
	private List<TreeModel<T>> children;
	private Map<Serializable, Serializable> properties;
	
	/**
	 * 构造一个树形模型
	 * @param data 节点数据
	 */
	public TreeModel(T data)
	{
		this(data, STYLE_NORMAL);
	}
	
	/**
	 * 使用指定的类型构造一个树形类型
	 * @param data 节点数据
	 * @param style 模型类型
	 */
	public TreeModel(T data, int style)
	{
		this.data = data;
		this.style = style;
	}
	
	/**
	 * 获取当前节点数据
	 * @return
	 */
	public T getData()
	{
		return data;
	}
	
	/**
	 * 获取当前节点ID
	 * @return
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * 设置当前节点ID
	 * @param id
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * 获取当前节点名称
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 设置当前节点名称
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 设置父节点
	 * @param parentData 父节点数据
	 */
	public TreeModel<T> setParentData(T parentData)
	{
		return setParent(new TreeModel<T>(parentData));
	}
	
	/**
	 * 设置父节点
	 * @param parent 父节点模型
	 */
	public TreeModel<T> setParent(TreeModel<T> parent)
	{
		this.parent = parent;
		if(parent!=null)
		{
			if(style==STYLE_NORMAL||(style==STYLE_UNIQUE&&!parent.containsChild(this)))
			{
				parent.addChild(this);
			}
		}
		return parent;
	}
	
	/**
	 * 添加子节点
	 * @param childData 子节点数据
	 */
	public TreeModel<T> addChildData(T childData)
	{
		return addChild(new TreeModel<T>(childData));
	}
	
	/**
	 * 添加子节点
	 * @param child 子节点模型
	 */
	public TreeModel<T> addChild(TreeModel<T> child)
	{
		if(children==null) children = new ArrayList<TreeModel<T>>();
		children.add(child);
		child.parent = this;
		return child;
	}
	
	/**
	 * 判断是否包含子节点
	 * @param childData 要查找的子节点数据
	 * @return
	 */
	public boolean containsChildData(T childData)
	{
		return containsChild(new TreeModel<T>(childData));
	}
	
	/**
	 * 判断是否包含子节点
	 * @param child 要查找的子节点模型
	 * @return
	 */
	public boolean containsChild(TreeModel<T> child)
	{
		if(children!=null) return children.contains(child);
		return false;
	}
	
	/**
	 * 获取父节点数据
	 * @return
	 */
	public T getParentData()
	{
		if(parent!=null) return parent.data;
		return null;
	}
	
	/**
	 * 获取父节点模型
	 * @return
	 */
	public TreeModel<T> getParent()
	{
		return parent;
	}
	
	/**
	 * 获取指定序号的子节点数据
	 * @param index 子节点序号
	 * @return
	 */
	public T getChildData(int index)
	{
		if(children!=null) return children.get(index).data;
		return null;
	}
	
	/**
	 * 获取指定序号的子节点模型
	 * @param index 子节点序号
	 * @return
	 */
	public TreeModel<T> getChild(int index)
	{
		if(children!=null) return children.get(index);
		return null;
	}
	
	/**
	 * 获取所有的子节点模型
	 * @return
	 */
	public List<TreeModel<T>> getChildren()
	{
		if(children!=null) return children;
		else return new ArrayList<TreeModel<T>>();
	}
	
	/**
	 * 设置属性
	 * @param key 属性key值
	 * @param value 属性value值
	 */
	public void putProperty(Serializable key, Serializable value)
	{
		if(properties==null) properties = new HashMap<Serializable, Serializable>();
		properties.put(key, value);
	}
	
	/**
	 * 获取属性
	 * @param key 属性key值
	 * @return 属性的value值
	 */
	public Serializable getProperty(Serializable key)
	{
		if(properties!=null) return properties.get(key);
		return null;
	}
	
	/**
	 * 判断是否包含属性
	 * @param key 属性key值
	 * @return 包含返回true,否则返回false
	 */
	public boolean containsProperty(Serializable key)
	{
		if(properties!=null) return properties.containsKey(key);
		return false;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeModel other = (TreeModel) obj;
		if (data == null)
		{
			if (other.data != null)
				return false;
		}
		else if (!data.equals(other.data))
			return false;
		return true;
	}
}
