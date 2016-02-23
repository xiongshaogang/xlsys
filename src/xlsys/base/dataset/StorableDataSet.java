package xlsys.base.dataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import xlsys.base.database.IClientDataBase;
import xlsys.base.database.IDataBase;
import xlsys.base.database.TableInfo;
import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.bean.ISqlBean;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.log.Log;
import xlsys.base.util.ObjectUtil;

/**
 * 可存储数据集实现类
 * @author Lewis
 *
 */
public class StorableDataSet extends DataSet implements IStorableDataSet
{
	private static final long serialVersionUID = 9199289784243770061L;
	
	protected transient IDataBase dataBase;
	protected String tableName;
	protected HashSet<String> unSaveColNameSet;
	protected List<DataSetRow> toBeDeleteRowList;
	protected boolean changed;
	protected ParamBean selectBean;
	protected boolean sorted;
	protected transient boolean saveTransaction;
	
	public StorableDataSet(IDataBase dataBase, String tableName) throws Exception
	{
		this(dataBase, tableName, IDataSet.STYLE_NONE);
	}
	
	public StorableDataSet(IDataBase dataBase, String tableName, int style) throws Exception
	{
		super(style);
		init(dataBase, new ParamBean(getTableSelectSql(tableName)), tableName);
	}
	
	public StorableDataSet(IDataBase dataBase, String selectSql, String tableName)
	{
		this(dataBase, selectSql, tableName, IDataSet.STYLE_NONE);
	}
	
	public StorableDataSet(IDataBase dataBase, String selectSql, String tableName, int style)
	{
		this(dataBase, new ParamBean(selectSql), tableName, style);
	}
	
	public StorableDataSet(IDataBase dataBase, ParamBean selectBean, String tableName)
	{
		this(dataBase, selectBean, tableName, IDataSet.STYLE_NONE);
	}
	
	public StorableDataSet(IDataBase dataBase, ParamBean selectBean, String tableName, int style)
	{
		super(style);
		init(dataBase, selectBean, tableName);
	}
	
	private void init(IDataBase dataBase, ParamBean selectBean, String tableName)
	{
		this.dataBase = dataBase;
		this.tableName = tableName;
		this.selectBean = selectBean;
		changed = false;
		sorted = false;
		saveTransaction = true;
		addExpandListener();
	}
	
	private String getTableSelectSql(String tableName) throws Exception
	{
		TableInfo tableInfo = dataBase.getTableInfo(tableName);
		StringBuffer sb = new StringBuffer("select ");
		for(DataSetColumn dsCol : tableInfo.getDataColumnList())
		{
			sb.append(dsCol.getColumnName()).append(',');
		}
		sb.deleteCharAt(sb.length()-1).append(" from ").append(tableName);
		return sb.toString();
	}
	
	@Override
	public void setSaveTransaction(boolean saveTransaction)
	{
		this.saveTransaction = saveTransaction;
	}

	@Override
	public boolean isSaveTransaction()
	{
		return saveTransaction;
	}
	
	@Override
	public boolean isSorted()
	{
		return sorted;
	}

	private void addExpandListener()
	{
		if(ObjectUtil.hasBitProp(getStyle(), IDataSet.STYLE_VIRTUAL))
		{
			addDataSetListener(new SdsExpandListener());
		}
	}
	
	@Override
	public String getSql()
	{
		return selectBean.getSelectSql();
	}
	
	@Override
	public ParamBean getSelectBean()
	{
		return selectBean;
	}
	
	@Override
	public synchronized boolean save() throws Exception
	{
		boolean success = false;
		boolean oldAutoCommit = true;
		try
		{
			oldAutoCommit = dataBase.getAutoCommit();
			// 启动事务处理
			if(saveTransaction) dataBase.setAutoCommit(false);
			if(postRow())
			{
				if(changed)
				{
					StorableDataSetEvent event = new StorableDataSetEvent(this);
					fireBeforeSave(event);
					if(event.doit)
					{
						List<DataSetColumn> cols = this.getColumns();
						List<ISqlBean> sqlBeanList = new ArrayList<ISqlBean>();
						ExecuteBean insertEb = null;
						ExecuteBean updateEb = null;
						ExecuteBean deleteEb = null;
						for(DataSetRow row : getRows())
						{
							if(row.isChanged())
							{
								if(row.getChangeStatus()==DataSetRow.STATUS_REPLACE)
								{
									Map<String, Serializable> updateDataMap = new HashMap<String, Serializable>();
									Map<String, Serializable> oldDataMap = new HashMap<String, Serializable>();
									for(DataSetColumn dsc : cols)
									{
										String colName = dsc.getColumnName();
										if(unSaveColNameSet==null||!unSaveColNameSet.contains(colName))
										{
											updateDataMap.put(colName, getValue(row, colName));
										}
										if(dsc.isPrimaryKey())
										{
											Serializable oldValue = row.getCell(getColumnIndex(colName)).getOldContent();
											if(oldValue==null)
											{
												oldValue = row.getCell(getColumnIndex(colName)).getContent();
											}
											oldDataMap.put(colName, oldValue);
										}
									}
									if(updateEb==null)
									{
										updateEb = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_UPDATE, tableName);
									}
									if(!updateDataMap.isEmpty()) updateEb.addData(updateDataMap, oldDataMap);
								}
								else
								{
									Map<String, Serializable> dataMap = new HashMap<String, Serializable>();
									for(DataSetColumn dsc : cols)
									{
										String colName = dsc.getColumnName();
										if(unSaveColNameSet==null||!unSaveColNameSet.contains(colName))
										{
											dataMap.put(colName, row.getCell(getColumnIndex(colName)).getContent());
										}
									}
									if(row.getChangeStatus()==DataSetRow.STATUS_FOR_NEW)
									{
										if(insertEb==null)
										{
											insertEb = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_INSERT, tableName);
										}
										if(!dataMap.isEmpty()) insertEb.addData(dataMap);
									}
									else
									{
										if(updateEb==null)
										{
											updateEb = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_UPDATE, tableName);
										}
										if(!dataMap.isEmpty()) updateEb.addData(dataMap);
									}
								}
							}
						}
						// 处理删除的列
						if(toBeDeleteRowList!=null)
						{
							TableInfo tableInfo = dataBase.getTableInfo(tableName);
							//closeDataBase();
							for(DataSetRow row : toBeDeleteRowList)
							{
								if(deleteEb==null)
								{
									deleteEb = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_DELETE, tableName);
								}
								Map<String, Serializable> dataMap = new HashMap<String, Serializable>();
								for(String colName : tableInfo.getPkColSet())
								{
									dataMap.put(colName, row.getCell(getColumnIndex(colName)).getContent());
								}
								deleteEb.addData(dataMap);
							}
						}
						if(insertEb!=null) sqlBeanList.add(insertEb);
						if(updateEb!=null) sqlBeanList.add(updateEb);
						if(deleteEb!=null) sqlBeanList.add(deleteEb);
						if(dataBase.sqlExecute(sqlBeanList))
						{
							success = true;
							fireAfterSave(new StorableDataSetEvent(this));
						}
					}
					else
					{
						if(saveTransaction&&!dataBase.getAutoCommit()) dataBase.rollback();
					}
				}
				else success = true;
				if(saveTransaction&&!dataBase.getAutoCommit()) dataBase.commit();
				if(success)
				{
					changed = false;
					if(toBeDeleteRowList!=null) toBeDeleteRowList.clear();
					for(DataSetRow row : getRows())
					{
						row.setChanged(false);
						if(!isVirtualRow(row))
						{
							row.setChangeStatus(DataSetRow.STATUS_COMMON);
						}
					}
				}
				fireAfterSaveCommit(new StorableDataSetEvent(this));
			}
		}
		catch(Exception e)
		{
			if(saveTransaction&&!dataBase.getAutoCommit()) dataBase.rollback();
			throw e;
		}
		finally
		{
			if(saveTransaction) dataBase.setAutoCommit(oldAutoCommit);
			fireSaveFinally(new StorableDataSetEvent(this));
		}
		return success;
	}

	@Override
	public synchronized void open() throws Exception
	{
		IDataSet refds = null;
		try
		{
			StorableDataSetEvent event = new StorableDataSetEvent(this);
			event.selectBean = selectBean;
			fireBeforeOpen(event);
			selectBean = event.selectBean;
			if(event.refDataSet!=null)
			{
				refds = event.refDataSet;
			}
			else if(event.refDataSet==null&&selectBean!=null)
			{
				if(ObjectUtil.hasBitProp(getStyle(), IDataSet.STYLE_VIRTUAL))
				{
					Map<String, Integer> sortColMap = getSortColMap();
					if(sortColMap!=null&&!sortColMap.isEmpty()&&sorted)
					{
						// 使用带排序功能的查询加载
						refds = dataBase.sqlSelect(selectBean, sortColMap, 0, this.getExpandPerCount()-1);
					}
					else
					{
						refds = dataBase.sqlSelect(selectBean, 0, this.getExpandPerCount()-1);
					}
					setColumnCount(refds.getColumnCount());
					setRowCount(dataBase.getResultCount(selectBean));
				}
				else
				{
					refds = dataBase.sqlSelect(selectBean);
				}
			}
			if(refds!=null)
			{
				refDataSet(refds);
				setRowCursor(-1);
				event = new StorableDataSetEvent(this);
				fireAfterOpen(event);
			}
		}
		catch(Exception e)
		{
			if(dataBase!=null&&!dataBase.getAutoCommit()) dataBase.rollback();
			throw e;
		}
	}
	
	@Override
	public synchronized void addUnsaveCol(String colName)
	{
		if(unSaveColNameSet==null) unSaveColNameSet = new HashSet<String>();
		unSaveColNameSet.add(colName);
	}
	
	private synchronized void addDeleteRow(DataSetRow row)
	{
		if(row==null) return;
		if(!isOption(OPTION_LOCK_TO_BE_DELETE_ROW))
		{
			if(toBeDeleteRowList==null) toBeDeleteRowList = new ArrayList<DataSetRow>();
			toBeDeleteRowList.add(row);
		}
	}

	private void setNewRowStatus(DataSetRow row, boolean virtual)
	{
		if(row!=null)
		{
			if(!isOption(OPTION_LOCK_CHANGE)&&!virtual)
			{
				row.setChanged(true);
				row.setChangeStatus(DataSetRow.STATUS_FOR_NEW);
				changed = true;
			}
		}
	}
	
	@Override
	public synchronized DataSetRow doInsertRow(int index, DataSetRow row, boolean clone)
	{
		DataSetRow newRow = super.doInsertRow(index, row, clone);
		setNewRowStatus(newRow, false);
		return newRow;
	}

	@Override
	protected synchronized DataSetRow doInsertNewRow(int index, boolean virtual)
	{
		DataSetRow newRow = super.doInsertNewRow(index, virtual);
		setNewRowStatus(newRow, virtual);
		return newRow;
	}

	@Override
	protected DataSetRow doRemoveRow(int rowNum)
	{
		DataSetRow row = super.doRemoveRow(rowNum);
		addDeleteRow(row);
		if(!isOption(OPTION_LOCK_CHANGE)) changed = true;
		return row;
	}

	@Override
	public synchronized void removeAllRow()
	{
		expandAllRows();
		if(!isOption(OPTION_LOCK_TO_BE_DELETE_ROW))
		{
			for(DataSetRow row : getRows()) addDeleteRow(row);
			if(!isOption(OPTION_LOCK_CHANGE)) changed = true;
		}
		super.removeAllRow();
	}

	@Override
	public void doSetValue(int rowIndex, int colIndex, Serializable value)
	{
		boolean isPkCol = getColumn(colIndex).isPrimaryKey();
		DataSetRow row = getRow(rowIndex);
		boolean needReplace = isPkCol&&row.getChangeStatus()!=DataSetRow.STATUS_FOR_NEW;
		Serializable oldContent = null;
		if(needReplace) oldContent = getValue(rowIndex, colIndex);
		super.doSetValue(rowIndex, colIndex, value);
		if(needReplace)
		{
			row.getCell(colIndex).setOldContent(oldContent);
			row.setChangeStatus(DataSetRow.STATUS_REPLACE);
		}
		if(!isOption(OPTION_LOCK_CHANGE))
		{
			if(!changed) changed = true;
			getRow(rowIndex).setChanged(changed);
		}
	}
	
	@Override
	public Serializable getValue(DataSetRow row, int colIndex)
	{
		Serializable value = super.getValue(row, colIndex);
		if(value==null&&toBeDeleteRowList!=null&&toBeDeleteRowList.contains(row))
		{
			value = row.getCell(colIndex).getContent();
		}
		return value;
	}

	@Override
	protected void refreshing()
	{
		if(toBeDeleteRowList!=null) toBeDeleteRowList.clear();
		changed = false;
		sorted = false;
		this.getRows().clear();
		this.getColumns().clear();
		try
		{
			open();
		}
		catch (Exception e)
		{
			DataSetEvent event = new DataSetEvent(this);
			event.errMsg = e;
			event.logLevel = Log.LOG_LEVEL_ERROR;
			fireShowMessage(event);
		}
	}

	@Override
	public void fireBeforeOpen(StorableDataSetEvent event)
	{
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					if(listener instanceof StorableDataSetListener)
					{
						((StorableDataSetListener) listener).beforeOpen(event);
						if(event.interrupt) break;
					}
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}

	@Override
	public void fireAfterOpen(StorableDataSetEvent event)
	{
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					if(listener instanceof StorableDataSetListener)
					{
						((StorableDataSetListener) listener).afterOpen(event);
						if(event.interrupt) break;
					}
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	@Override
	public void fireBeforeSave(StorableDataSetEvent event)
	{
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					if(listener instanceof StorableDataSetListener)
					{
						((StorableDataSetListener) listener).beforeSave(event);
						if(event.interrupt) break;
					}
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}

	@Override
	public void fireAfterSave(StorableDataSetEvent event)
	{
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					if(listener instanceof StorableDataSetListener)
					{
						((StorableDataSetListener) listener).afterSave(event);
						if(event.interrupt) break;
					}
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}
	
	@Override
	public void fireAfterSaveCommit(StorableDataSetEvent event)
	{
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					if(listener instanceof StorableDataSetListener)
					{
						((StorableDataSetListener) listener).afterSaveCommit(event);
						if(event.interrupt) break;
					}
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}

	@Override
	public void fireSaveFinally(StorableDataSetEvent event)
	{
		try
		{
			if(dataSetListenerSet!=null)
			{
				for(DataSetListener listener : dataSetListenerSet)
				{
					if(listener instanceof StorableDataSetListener)
					{
						((StorableDataSetListener) listener).saveFinally(event);
						if(event.interrupt) break;
					}
				}
				if(!event.doit&&event.errMsg!=null) fireShowMessage(event);
			}
		}
		catch(Exception e)
		{
			event.doit = false;
			event.errMsg = e;
			fireShowMessage(event);
		}
	}

	@Override
	public boolean isChanged()
	{
		return changed;
	}

	@Override
	public void setChanged(boolean changed)
	{
		this.changed = changed;
	}

	@Override
	public IDataBase getDataBase()
	{
		return dataBase;
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public HashSet<String> getUnSaveColNameSet()
	{
		return unSaveColNameSet;
	}

	@Override
	public boolean hasToBeDeleteRow()
	{
		return toBeDeleteRowList!=null&&!toBeDeleteRowList.isEmpty();
	}
	
	@Override
	public List<DataSetRow> getToBeDeleteRowList()
	{
		return toBeDeleteRowList;
	}
	
	@Override
	public boolean hasChangedRow()
	{
		boolean hasChangedRow = false;
		for(DataSetRow row : getRows())
		{
			if(row.isChanged())
			{
				hasChangedRow = true;
				break;
			}
		}
		return hasChangedRow;
	}
	
	@Override
	public synchronized void sort()
	{
		try
		{
			setEventLock(true);
			if(selectBean!=null&&ObjectUtil.hasBitProp(getStyle(), IDataSet.STYLE_VIRTUAL)&&dataBase instanceof IClientDataBase&&save())
			{
				getRows().clear();
				setRowCount(dataBase.getResultCount(selectBean));
			}
			else super.sort();
			setEventLock(false);
		}
		catch(Exception e)
		{
			DataSetEvent event = new DataSetEvent(this);
			event.errMsg = e;
			event.logLevel = Log.LOG_LEVEL_ERROR;
			fireShowMessage(event);
			super.sort();
		}
		sorted = true;
	}
}
