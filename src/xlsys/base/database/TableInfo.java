package xlsys.base.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import xlsys.base.dataset.DataSetColumn;

/**
 * 表信息，该类对数据库中表的信息进行了封装
 * @author Lewis
 *
 */
public class TableInfo implements Serializable
{
	private static final long serialVersionUID = -360520976340630103L;
	
	private String tableName;
	private String tableComment;
	private Map<String, DataSetColumn> dataColumnMap;
	private Map<String, String> columnCommentMap;
	private List<DataSetColumn> dataColumnList;
	private LinkedHashSet<String> pkColSet;
	private Map<String, TableConstraint> constraintMap;
	
	/**
	 * 构造一个表信息对象
	 * @param tableName
	 */
	public TableInfo(String tableName)
	{
		this.tableName = tableName.toLowerCase();
		dataColumnMap = new HashMap<String, DataSetColumn>();
		columnCommentMap = new HashMap<String, String>();
		dataColumnList = new ArrayList<DataSetColumn>();
		pkColSet = new LinkedHashSet<String>();
		constraintMap = new HashMap<String, TableConstraint>();
	}

	/**
	 * 获取表注释
	 * @return
	 */
	public String getTableComment()
	{
		return tableComment;
	}

	/**
	 * 设置表注释
	 * @param comment
	 */
	public void setTableComment(String tableComment)
	{
		this.tableComment = tableComment;
	}

	/**
	 * 添加表列
	 * @param dsc
	 */
	public void addDataSetColumn(DataSetColumn dsc)
	{
		if(!dataColumnList.contains(dsc))
		{
			dataColumnMap.put(dsc.getColumnName(), dsc);
			dataColumnList.add(dsc);
			if(pkColSet.contains(dsc.getColumnName()))
			{
				dsc.setPrimaryKey(true);
			}
			if(dsc.isPrimaryKey())
			{
				pkColSet.add(dsc.getColumnName());
			}
		}
	}
	
	/**
	 * 添加列注释
	 * @param colName
	 * @param comment
	 */
	public void addColumnComment(String colName, String comment)
	{
		columnCommentMap.put(colName.toLowerCase(), comment);
	}
	
	/**
	 * 获取列注释
	 * @param colName
	 * @return
	 */
	public String getColumnComment(String colName)
	{
		return columnCommentMap.get(colName);
	}
	
	/**
	 * 添加主键列
	 * @param pkColName
	 */
	public void addPkCol(String pkColName)
	{
		pkColName = pkColName.toLowerCase();
		pkColSet.add(pkColName);
		DataSetColumn dsc = dataColumnMap.get(pkColName);
		if(dsc!=null) dsc.setPrimaryKey(true);
	}
	
	/**
	 * 添加表约束信息
	 * @param constraintName 约束名称
	 * @param constraint
	 */
	public void addConstraint(String constraintName, TableConstraint constraint)
	{
		constraintMap.put(constraintName, constraint);
	}
	
	/**
	 * 获取表约束信息
	 * @param constraintName
	 * @return
	 */
	public TableConstraint getConstraint(String constraintName)
	{
		return constraintMap.get(constraintName);
	}
	
	/**
	 * 获取表约束信息映射
	 * @return
	 */
	public Map<String, TableConstraint> getConstraintMap()
	{
		return constraintMap;
	}

	/**
	 * 获取表列
	 * @param colName
	 * @return
	 */
	public DataSetColumn getDataSetColumn(String colName)
	{
		DataSetColumn dsc = dataColumnMap.get(colName.toLowerCase());
		return dsc;
	}

	/**
	 * 获取表名
	 * @return
	 */
	public String getTableName()
	{
		return tableName;
	}

	/**
	 * 获取所有表列
	 * @return
	 */
	public List<DataSetColumn> getDataColumnList()
	{
		return dataColumnList;
	}

	/**
	 * 获取所有主键列
	 * @return
	 */
	public LinkedHashSet<String> getPkColSet()
	{
		return pkColSet;
	}
	
}
