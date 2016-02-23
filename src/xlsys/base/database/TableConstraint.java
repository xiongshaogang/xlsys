package xlsys.base.database;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 表约束封装类.
 * 注意此类重构了equals和hashcode方法，当constraintName相同时，被认为是相等的
 * @author Lewis
 *
 */
public class TableConstraint implements Serializable
{
	private static final long serialVersionUID = 6421525587174674724L;
	
	/**
	 * 未知约束类型
	 */
	public static final int CONSTRAINT_TYPE_UNKNOWN = 0;
	/**
	 * 主键约束类型
	 */
	public static final int CONSTRAINT_TYPE_PK = 1;
	/**
	 * 外键约束类型
	 */
	public static final int CONSTRAINT_TYPE_FK = 2;
	/**
	 * 唯一键约束
	 */
	public static final int CONSTRAINT_TYPE_U = 3;
	
	/**
	 * 约束名
	 */
	private String constraintName;
	/**
	 * 约束对应的表名
	 */
	private String tableName;
	/**
	 * 约束类型
	 */
	private int constraintType;
	/**
	 * 引用的约束名(当约束类型为CONSTRAINT_TYPE_FK时有效)
	 */
	private String refConstraintName;
	/**
	 * 
	 */
	private String refTableName;
	/**
	 * 约束对应的字段集合
	 */
	private Set<String> columnNameSet;
	
	public TableConstraint(String constraintName)
	{
		this.constraintName = constraintName.toLowerCase();
		columnNameSet = new HashSet<String>();
	}
	
	public void addColumnName(String columnName)
	{
		columnNameSet.add(columnName.toLowerCase());
	}

	public Set<String> getColumnNameSet()
	{
		return columnNameSet;
	}

	public String getConstraintName()
	{
		return constraintName;
	}

	public void setConstraintName(String constraintName)
	{
		this.constraintName = constraintName.toLowerCase();
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName.toLowerCase();
	}

	public int getConstraintType()
	{
		return constraintType;
	}

	public void setConstraintType(int constraintType)
	{
		this.constraintType = constraintType;
	}

	public String getRefConstraintName()
	{
		return refConstraintName;
	}

	public void setRefConstraintName(String refConstraintName)
	{
		this.refConstraintName = refConstraintName==null?refConstraintName:refConstraintName.toLowerCase();
	}

	public String getRefTableName()
	{
		return refTableName;
	}

	public void setRefTableName(String refTableName)
	{
		this.refTableName = refTableName==null?refTableName:refTableName.toLowerCase();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constraintName == null) ? 0 : constraintName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		TableConstraint other = (TableConstraint) obj;
		if(constraintName == null)
		{
			if(other.constraintName != null) return false;
		}
		else if(!constraintName.equals(other.constraintName)) return false;
		return true;
	}
}
