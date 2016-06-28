package xlsys.base.dataset.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.database.ConnectionPool;
import xlsys.base.database.IDataBase;
import xlsys.base.database.TableConstraint;
import xlsys.base.database.TableInfo;
import xlsys.base.database.util.DBUtil;
import xlsys.base.dataset.DataSet;
import xlsys.base.dataset.DataSetCell;
import xlsys.base.dataset.DataSetColumn;
import xlsys.base.dataset.DataSetRow;
import xlsys.base.dataset.IDataSet;
import xlsys.base.dataset.StorableDataSet;
import xlsys.base.io.util.FileUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.script.XlsysClassLoader;

/**
 * 数据集工具类
 * @author Lewis
 *
 */
public class DataSetUtil
{
	public final static int COLUMN_MATCH_TOTAL = 0;
	public final static int COLUMN_MATCH_CLASS = 1;
	public final static int COLUMN_MATCH_NOT = 2;
	
	/**
	 *  DataSet列匹配检查, 并将匹配后的列写入参数newColumns中.
	 *  @param dataSetList 要检查的数据集列表
	 *  @param newColumns 可匹配的列
	 *  @return <li> 0 列完全相等
	 *  		<li> 1 列类型在java中基本相同，可进行强制转换
	 *  		<li> 列类型不匹配
	 */
	public static int dataSetColumnEqualsCheck(List<DataSet> dataSetList, List<DataSetColumn> newColumns)
	{
		int match = COLUMN_MATCH_TOTAL;
		List<DataSetColumn> temp = new ArrayList<DataSetColumn>();
		try
		{
			if(dataSetList!=null&&!dataSetList.isEmpty())
			{
				List<DataSetColumn> columnsList1 = dataSetList.get(0).getColumns();
				for(int i=1;i<dataSetList.size();i++)
				{
					List<DataSetColumn> columnsList2 = dataSetList.get(i).getColumns();
					if(columnsList1.size()==columnsList2.size())
					{
						for(int j=0;j<columnsList1.size();j++)
						{
							DataSetColumn column1 = columnsList1.get(j);
							DataSetColumn column2 = columnsList1.get(j);
							if(temp.size()<j+1)
							{
								DataSetColumn column = new DataSetColumn();
								column.cloneOf(column1);
								temp.add(column);
							}
							if(!column1.equals(column2))
							{
								if(match<COLUMN_MATCH_CLASS)
								{
									match = COLUMN_MATCH_CLASS;
								}
								Class<?> class1 = XlsysClassLoader.getInstance().loadClass(column1.getJavaClass());
								Class<?> class2 = XlsysClassLoader.getInstance().loadClass(column2.getJavaClass());
								if(!class1.isAssignableFrom(class2)&&!class2.isAssignableFrom(class1))
								{
									if(!Number.class.isAssignableFrom(class1)||!Number.class.isAssignableFrom(class2))
									{
										if(java.util.Date.class.isAssignableFrom(class1)&&java.util.Date.class.isAssignableFrom(class2))
										{
											DataSetColumn column = temp.get(j);
											column.setJavaClass(java.util.Date.class.getName());
										}
										else
										{
											if(match<COLUMN_MATCH_NOT)
											{
												match = COLUMN_MATCH_NOT;
											}
										}
									}
									else if(Number.class.isAssignableFrom(class1)&&Number.class.isAssignableFrom(class2))
									{
										DataSetColumn column = temp.get(j);
										column.setJavaClass(Number.class.getName());
									}
									else
									{
										if(match<COLUMN_MATCH_NOT)
										{
											match = COLUMN_MATCH_NOT;
										}
									}
								}
								else if(class1.isAssignableFrom(class2))
								{
									DataSetColumn column = temp.get(j);
									column.cloneOf(column1);
								}
								else if(class2.isAssignableFrom(class1))
								{
									DataSetColumn column = temp.get(j);
									column.cloneOf(column2);
								}
							}
						}
					}
					else
					{
						match = COLUMN_MATCH_NOT;
					}
				}
			}
		}
		catch(ClassNotFoundException e)
		{
			match = COLUMN_MATCH_NOT;
			e.printStackTrace();
		}
		if(match!=COLUMN_MATCH_NOT)
		{
			newColumns.addAll(temp);
		}
		return match;
	}
	
	/**
	 * 合并DataSet,会进行匹配检查
	 * @param dataSetList 要合并的数据集列表
	 * @param clone 是否复制数据集
	 * @return 合并后的数据集
	 */
	public static DataSet mergeDataSet(List<DataSet> dataSetList, boolean clone)
	{
		DataSet ds = null;
		List<DataSetColumn> newColumns = new ArrayList<DataSetColumn>();
		int match = dataSetColumnEqualsCheck(dataSetList, newColumns);
		if(match!=COLUMN_MATCH_NOT)
		{
			ds = new DataSet();
			for(int i=0;i<newColumns.size();i++)
			{
				ds.insertNewColumnAfterLast();
				ds.getColumns().get(i).cloneOf(newColumns.get(i));
			}
			for(int i=0;i<dataSetList.size();i++)
			{
				DataSet tempDs = dataSetList.get(i);
				List<DataSetRow> rows = tempDs.getRows();
				for(int j=0;j<rows.size();j++)
				{
					ds.insertRowAfterLast(rows.get(j));
				}
			}
		}
		return ds;
	}
	
	/**
	 * 输出数据集. 该方法等价于dumpData(ds, false, true, false)
	 * @param ds
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void dumpData(IDataSet ds) throws SQLException, IOException
	{
		dumpData(ds, false, true);
	}
	
	/**
	 * 输出数据集
	 * @param ds 要输出的数据集
	 * @param dumpColumnInfo 是否输出列信息
	 * @param dumpRow 是否输出行
	 * @param dumpColumn 是否输出列
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void dumpData(IDataSet ds, boolean dumpColumnInfo, boolean dumpRow) throws SQLException, IOException
	{
		if(ds!=null)
		{
			System.out.println("Dump Start ################## DataSetName : "+ds.getName()+"   ColumnCount : "+ds.getColumnCount()+"    RowColumn:"+ds.getRowCount());
			if(dumpColumnInfo)
			{
				System.out.println("-------------Column Info-------------");
				for(int i=0;i<ds.getColumnCount();i++)
				{
					DataSetColumn column = ds.getColumn(i);
					System.out.println("ColumnIndex:"+i+"      ColumnName:"+column.getColumnName()+"      DbColumnType:"+column.getDbColumnType()+"      JavaClass:"+column.getJavaClass()+"      Precision:"+column.getPrecision() + "      Scale:"+column.getScale() + "      SqlType:"+column.getSqlType() + "      IsNullAble:"+column.isNullAble()+"      IsPrimaryKey:"+column.isPrimaryKey());
				}
			}
			if(dumpRow)
			{
				System.out.println("Row Data--------------------------RowCount : "+ds.getRowCount()+"--------------------------");
				for(int i=0;i<ds.getRowCount();i++)
				{
					for(int j=0;j<ds.getColumnCount();j++)
					{
						System.out.print(ds.getColumn(j).getColumnName()+":"+ds.getRow(i).getCell(j)+"      ");
					}
					System.out.println();
				}
			}
			System.out.println("Dump End ################## ");
		}
	}
	
	/**
	 * 判断数据集行中的元素是否全部都为空
	 * @param dsr
	 * @return
	 */
	public static boolean isNullDataSetRow(DataSetRow dsr)
	{
		boolean ret = true;
		if(dsr!=null)
		{
			for(DataSetCell cell : dsr.getCells())
			{
				if(cell!=null&&cell.getContent()!=null)
				{
					ret = false;
					break;
				}
			}
		}
		return ret;
	}
	
	/**
	 * 判断数据集列中的元素是否全部都为空
	 * @param dsr
	 * @return
	 */
	public static boolean isNullDataSetColumn(IDataSet dataSet, DataSetColumn dsc)
	{
		boolean ret = true;
		if(dsc!=null)
		{
			int colIndex = dataSet.getColumnIndex(dsc.getColumnName());
			int rowCount = dataSet.getRowCount();
			for(int i=0;i<rowCount;++i)
			{
				DataSetCell cell = dataSet.getRow(i).getCell(colIndex);
				if(cell.getContent()!=null)
				{
					ret = false;
					break;
				}
			}
		}
		return ret;
	}
	
	/**
	 * 截取数据集.
	 * @param srcDataSet 源数据集
	 * @param beginRowNum 开始行号(包含)
	 * @param endRowNum 结束行号(包含)
	 * @return
	 */
	public static IDataSet subDataSet(IDataSet srcDataSet, int beginRowNum, int endRowNum)
	{
		IDataSet retDataSet = new DataSet(srcDataSet.getColumns());
		retDataSet.buildColumnMap();
		if(srcDataSet.getRowCount()>0)
		{
			beginRowNum = beginRowNum<0?0:beginRowNum;
			endRowNum = endRowNum>(srcDataSet.getRowCount()-1)?(srcDataSet.getRowCount()-1):endRowNum;
			for(int i=0;i<endRowNum-beginRowNum+1;i++)
			{
				retDataSet.insertRowAfterLast(srcDataSet.getRow(i+beginRowNum));
			}
		}
		return retDataSet;
	}
	
	public static IDataSet cloneDataSet(IDataSet srcDataSet)
	{
		IDataSet targetDataSet = new DataSet(srcDataSet.getColumns());
		targetDataSet.buildColumnMap();
		for(int i=0;i<srcDataSet.getRowCount();i++)
		{
			targetDataSet.insertRowAfterLast(srcDataSet.getRow(i));
		}
		return targetDataSet;
	}
	
	/**
	 * 将数据集中的数据写入数据库中
	 * @param dataBase 数据库连接
	 * @param sds 要写入的数据集
	 * @return
	 * @throws Exception
	 */
	public static boolean importDataFromDataSet(IDataBase dataBase, StorableDataSet sds) throws Exception
	{
		Field dbField = sds.getClass().getDeclaredField("dataBase");
		dbField.setAccessible(true);
		dbField.set(sds, dataBase);
		sds.setSaveTransaction(false);
		sds.setChanged(true);
		for(DataSetRow row : sds.getRows())
		{
			row.setChanged(true);
			row.setChangeStatus(DataSetRow.STATUS_FOR_NEW);
		}
		return sds.save();
	}
	
	/**
	 * 将数据集列表中的数据插入数据库
	 * @param sdsList 文件路径
	 * @param transaction 是否启用事务处理
	 * @param disablePKCons 是否屏蔽主键约束
	 * @param disableFKCons 是否屏蔽外键约束
	 * @return
	 * @throws Exception
	 */
	public static boolean importData(int dbid, List<StorableDataSet> sdsList, boolean transaction, boolean disablePKCons, boolean disableFKCons) throws Exception
	{
		boolean success = false;
		IDataBase dataBase = null;
		List<TableConstraint> resetConstraintList = new ArrayList<TableConstraint>();
		try
		{
			dataBase = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
			if(transaction) dataBase.setAutoCommit(false);
			for(StorableDataSet sds : sdsList)
			{
				if(disablePKCons||disableFKCons)
				{
					// 获取当前表得所有主外键约束
					String tableName = sds.getTableName();
					TableInfo tableInfo = dataBase.getTableInfo(tableName);
					Map<String, TableConstraint> constraintMap = tableInfo.getConstraintMap();
					for(TableConstraint constraint : constraintMap.values())
					{
						boolean needReset = false;
						if(disablePKCons&&constraint.getConstraintType()==TableConstraint.CONSTRAINT_TYPE_PK) needReset = true;
						else if(disableFKCons&&constraint.getConstraintType()==TableConstraint.CONSTRAINT_TYPE_FK) needReset = true;
						if(needReset)
						{
							resetConstraintList.add(constraint);
							// 屏蔽约束
							dataBase.disableConstraint(tableName, constraint.getConstraintName());
						}
					}
				}
				importDataFromDataSet(dataBase, sds);
			}
			if(transaction) dataBase.commit();
			success = true;
		}
		catch(Exception e)
		{
			if(transaction)
			try
			{
				dataBase.rollback();
			}
			catch (Exception e1) {}
			throw e;
		}
		finally
		{
			// 如果存在屏蔽的约束，则启用已经屏蔽的约束
			if(dataBase!=null)
			{
				for(TableConstraint constraint : resetConstraintList)
				{
					try
					{
						dataBase.enableConstraint(constraint.getTableName(), constraint.getConstraintName());
					}
					catch(Exception e1){e1.printStackTrace();}
				}
			}
			DBUtil.close(dataBase);
		}
		return success;
	}
	
	public static void main(String[] args) throws Exception
	{
		IDataBase dataBase = null;
		try
		{
			int dbid = 1002; // 目标库的数据库编号
			String dataFilePath = "d:/20160620.data"; // 要导入的数据文件的路径
			dataBase = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
			dataBase.setAutoCommit(false);
			List<StorableDataSet> sdsList = (List<StorableDataSet>) IOUtil.readObject(FileUtil.getByteFromFile(dataFilePath));
			for(StorableDataSet sds : sdsList)
			{
				DataSetUtil.importDataFromDataSet(dataBase, sds);
			}
			dataBase.commit();
		}
		catch(Exception e)
		{
			DBUtil.rollback(dataBase);
			e.printStackTrace();
		}
		finally
		{
			DBUtil.close(dataBase);
		}
		System.exit(0);
	}
}
