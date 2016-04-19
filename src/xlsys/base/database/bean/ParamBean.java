package xlsys.base.database.bean;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import xlsys.base.exception.UnsupportedException;

/**
 * 执行DML和DDL语句时的传递参数封装类,该类可封装任意带"?"的sql语句，并以传入参数的形式进行执行，类似于JDBC中的PreparedStatement
 * @author Lewis
 *
 */
public class ParamBean implements Serializable, ISqlBean
{
	private static final long serialVersionUID = 6572570824261853984L;
	
	private String selectSql;
	private int cursor;
	private List<List<Param>> paramsList;
	
	/**
	 * 使用sql语句直接构造一个ParamBean
	 * @param selectSql
	 */
	public ParamBean(String selectSql)
	{
		this.selectSql = selectSql;
		cursor = -1;
	}
	
	/**
	 * 将参数游标移动至下一条，如果成功，返回true, 否则返回false
	 * @return
	 */
	public boolean next()
	{
		return gotoRow(cursor+1);
	}
	
	/**
	 * 将参数游标移动至上一条，如果成功，返回true, 否则返回false
	 * @return
	 */
	public boolean previous()
	{
		return gotoRow(cursor-1);
	}
	
	/**
	 * 将参数游标移动至第一条，如果成功，返回true, 否则返回false
	 * @return
	 */
	public boolean first()
	{
		return gotoRow(0);
	}
	
	/**
	 * 将参数游标移动至最后一条，如果成功，返回true, 否则返回false
	 * @return
	 */
	public boolean last()
	{
		if(paramsList!=null)
		return gotoRow(paramsList.size()-1);
		else return false;
	}
	
	/**
	 * 将参数游标移动至指定位置，如果成功，返回true, 否则返回false
	 * @param rowIdx 指定的行号
	 * @return
	 */
	public boolean gotoRow(int rowIdx)
	{
		boolean success = false;
		if(paramsList!=null&&rowIdx>=0&&rowIdx<paramsList.size())
		{
			cursor = rowIdx;
			success = true;
		}
		return success;
	}
	
	/**
	 * 获取当前游标所在位置，即第几组参数
	 * @return
	 */
	public int getRowIdx()
	{
		return cursor;
	}
	
	/**
	 * 添加一组参数
	 */
	public void addParamGroup()
	{
		if(paramsList==null) paramsList = new ArrayList<List<Param>>();
		paramsList.add(null);
		cursor = paramsList.size()-1;
	}
	
	/**
	 * 在当前参数组中设置一个特定位置的参数
	 * @param idx 参数序号，从1开始
	 * @param value 参数值
	 * @throws UnsupportedException
	 */
	public void setParam(int idx, Serializable value)
	{
		String javaClass = null;
		if(value!=null)
		{
			javaClass = value.getClass().getName();
		}
		else
		{
			throw new RuntimeException("Can not set param using this method when value is null, use setParam(int, Serializable, String) instead.");
		}
		setParam(cursor, idx, value, javaClass);
	}
	
	/**
	 * 在当前参数组中设置一个特定位置的参数
	 * @param idx 参数序号，从1开始
	 * @param value 参数值
	 * @param javaClass value对应的Java类型
	 */
	public void setParam(int idx, Serializable value, String javaClass)
	{
		setParam(cursor, idx, value, javaClass);
	}
	
	/**
	 * 在当前参数组中设置一个特定位置的参数
	 * @param rowIdx 参数所在组号
	 * @param colIdx 参数序号，从1开始
	 * @param value 参数值
	 * @param javaClass value对应的Java类型
	 */
	private void setParam(int rowIdx, int colIdx, Serializable value, String javaClass)
	{
		if(rowIdx<0||colIdx<1) throw new InvalidParameterException("Invalid parameter : row : " + rowIdx + " col : " + colIdx);
		if(paramsList==null)
		{
			paramsList = new ArrayList<List<Param>>();
		}
		while(paramsList.size()<rowIdx+1)
		{
			paramsList.add(null);
		}
		List<Param> params = paramsList.get(rowIdx);
		if(params==null)
		{
			params = new ArrayList<Param>();
			paramsList.set(rowIdx, params);
		}
		while(params.size()<colIdx)
		{
			params.add(null);
		}
		Param param = new Param(value, javaClass);
		
		params.set(colIdx-1, param);
	}
	
	/**
	 * 获取当前参数组中指定序号的参数
	 * @param idx 参数序号，从1开始
	 * @return
	 */
	public Serializable getParam(int idx)
	{
		return getParam(cursor, idx);
	}
	
	/**
	 * 获取指定参数组中指定序号的参数
	 * @param rowIdx 参数组序号，从0开始
	 * @param colIdx 参数序号，从1开始
	 * @return
	 */
	private Serializable getParam(int rowIdx, int colIdx)
	{
		Serializable value = null;
		if(paramsList!=null&&rowIdx>=0&&rowIdx<paramsList.size())
		{
			List<Param> params = paramsList.get(rowIdx);
			if(params!=null&&colIdx>=1&&colIdx<=params.size())
			{
				value = params.get(colIdx-1).value;
			}
		}
		return value;
	}

	/**
	 * 设置当前要执行的sql语句
	 * @param selectSql
	 */
	public void setSelectSql(String selectSql)
	{
		this.selectSql = selectSql;
	}

	/**
	 * 获取当前要执行的sql语句
	 * @return
	 */
	public String getSelectSql()
	{
		return selectSql;
	}

	/**
	 * 获取参数组列表
	 * @return
	 */
	public List<List<Param>> getParamsList()
	{
		return paramsList;
	}

	/**
	 * 设置参数组列表
	 * @param paramsList
	 */
	public void setParamsList(List<List<Param>> paramsList)
	{
		this.paramsList = paramsList;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + cursor;
		result = prime * result
				+ ((paramsList == null) ? 0 : paramsList.hashCode());
		result = prime * result
				+ ((selectSql == null) ? 0 : selectSql.hashCode());
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
		ParamBean other = (ParamBean) obj;
		if (cursor != other.cursor)
			return false;
		if (paramsList == null)
		{
			if (other.paramsList != null)
				return false;
		} else if (!paramsList.equals(other.paramsList))
			return false;
		if (selectSql == null)
		{
			if (other.selectSql != null)
				return false;
		} else if (!selectSql.equals(other.selectSql))
			return false;
		return true;
	}
	
	
}
