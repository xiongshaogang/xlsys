package xlsys.base.dataset;

import java.io.Serializable;

/**
 * 数据集列
 * @author Lewis
 *
 */
public class DataSetColumn implements Serializable
{
	private static final long serialVersionUID = 6015197200496219658L;
	
	private String columnName;
	private String javaClass;
	private String dbColumnType;
	private int sqlType; // 来自 java.sql.Types 的 SQL 类型
	private int precision; // 长度
	private int scale; // 精度
	private boolean nullAble;
	private boolean primaryKey;

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result
				+ ((dbColumnType == null) ? 0 : dbColumnType.hashCode());
		result = prime * result
				+ ((javaClass == null) ? 0 : javaClass.hashCode());
		result = prime * result + (nullAble ? 1231 : 1237);
		result = prime * result + precision;
		result = prime * result + (primaryKey ? 1231 : 1237);
		result = prime * result + scale;
		result = prime * result + sqlType;
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
		DataSetColumn other = (DataSetColumn) obj;
		if (columnName == null)
		{
			if (other.columnName != null)
				return false;
		}
		else if (!columnName.equals(other.columnName))
			return false;
		if (dbColumnType == null)
		{
			if (other.dbColumnType != null)
				return false;
		}
		else if (!dbColumnType.equals(other.dbColumnType))
			return false;
		if (javaClass == null)
		{
			if (other.javaClass != null)
				return false;
		}
		else if (!javaClass.equals(other.javaClass))
			return false;
		if (nullAble != other.nullAble)
			return false;
		if (precision != other.precision)
			return false;
		if (primaryKey != other.primaryKey)
			return false;
		if (scale != other.scale)
			return false;
		if (sqlType != other.sqlType)
			return false;
		return true;
	}
	
	public String getColumnName()
	{
		return columnName;
	}
	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}
	public String getDbColumnType()
	{
		return dbColumnType;
	}
	public void setDbColumnType(String dbColumnType)
	{
		this.dbColumnType = dbColumnType;
	}
	public int getSqlType()
	{
		return sqlType;
	}
	public void setSqlType(int sqlType)
	{
		this.sqlType = sqlType;
	}
	
	public String getJavaClass()
	{
		return javaClass;
	}

	public void setJavaClass(String javaClass)
	{
		this.javaClass = javaClass;
	}

	public int getPrecision()
	{
		return precision;
	}

	public void setPrecision(int precision)
	{
		this.precision = precision;
	}

	public int getScale()
	{
		return scale;
	}

	public void setScale(int scale)
	{
		this.scale = scale;
	}

	public boolean isNullAble()
	{
		return nullAble;
	}

	public void setNullAble(boolean nullAble)
	{
		this.nullAble = nullAble;
	}

	public boolean isPrimaryKey()
	{
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey)
	{
		this.primaryKey = primaryKey;
	}

	@Override
	public String toString()
	{
		return columnName;
	}

	public void cloneOf(DataSetColumn anotherCol)
	{
		if(anotherCol!=null)
		{
			this.columnName = anotherCol.getColumnName();
			this.javaClass = anotherCol.getJavaClass();
			this.dbColumnType = anotherCol.getDbColumnType();
			this.sqlType = anotherCol.getSqlType();
			this.precision = anotherCol.getPrecision();
			this.scale = anotherCol.getScale();
			this.nullAble = anotherCol.isNullAble();
			this.primaryKey = anotherCol.isPrimaryKey();
		}
	}
}
