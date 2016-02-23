package xlsys.base.database.bean;

import java.io.Serializable;

/**
 * 进行参数传递时封装具体参数所使用的类，一般不会独立使用
 * @author Lewis
 *
 */
public class Param implements Serializable
{
	private static final long serialVersionUID = -6156324090747703973L;
	
	/**
	 * 要传递的参数值
	 */
	public Serializable value;
	/**
	 * 该值对应的Java类型，一般在value为null时需要使用
	 */
	public String javaClass;
	
	/**
	 * 构造一个sql的执行参数
	 * @param value 参数值
	 * @param javaClass 参数对应的Java类型
	 */
	public Param(Serializable value, String javaClass)
	{
		this.value = value;
		this.javaClass = javaClass;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((javaClass == null) ? 0 : javaClass.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Param other = (Param) obj;
		if (javaClass == null)
		{
			if (other.javaClass != null)
				return false;
		} else if (!javaClass.equals(other.javaClass))
			return false;
		if (value == null)
		{
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
