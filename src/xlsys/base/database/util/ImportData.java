package xlsys.base.database.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.database.EnvDataBase;
import xlsys.base.database.IDataBase;
import xlsys.base.database.TableInfo;
import xlsys.base.dataset.DataSetColumn;
import xlsys.base.dataset.DataSetRow;
import xlsys.base.dataset.IDataSet;
import xlsys.base.dataset.StorableDataSet;
import xlsys.base.io.attachment.XlsysAttachment;
import xlsys.base.io.ftp.FtpModel;
import xlsys.base.io.util.FileUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.util.FTPUtil;
import xlsys.base.util.ObjectUtil;

public class ImportData implements Runnable
{
	private int envId;
	private XlsysAttachment[] attachments;
	private Integer maximum;
	private int innerValue;
	private Integer value;
	private List<String> logList;
	private Exception e;
	private List<List<StorableDataSet>> sdsListList;
	private boolean batch;
	private boolean override;
	
	// <sessionId, ImportData>
	public static Map<String, ImportData> importSessionMap = new HashMap<String, ImportData>();
	
	public ImportData(int envId, XlsysAttachment[] attachments, boolean batch, boolean override)
	{
		this.envId = envId;
		this.attachments = attachments;
		this.batch = batch;
		this.override = override;
		innerValue = 0;
		logList = new ArrayList<String>();
	}
	
	@Override
	public void run()
	{
		IDataBase dataBase = null;
		try
		{
			dataBase = EnvDataBase.getInstance(envId);
			initData();
			// 设置是否批量提交
			Field dbfield = StorableDataSet.class.getDeclaredField("dataBase");
			dbfield.setAccessible(true);
			if(batch) dataBase.setAutoCommit(false);
			else dataBase.setAutoCommit(true);
			// 导入数据
			StringBuilder sb = new StringBuilder();
			for(List<StorableDataSet> sdsList : sdsListList)
			{
				for(StorableDataSet sds : sdsList)
				{
					// 先获取当前数据库中该表的所有主键数据
					String tableName = sds.getTableName();
					TableInfo tableInfo = null;
					try
					{
						tableInfo = dataBase.getTableInfo(tableName);
					}
					catch(Exception e)
					{
						pushLog("Can not find table ["+tableName+"], please create table first!");
						continue;
					}
					// 如果sds中的sql是select *开头, 那么对表字段进行检查, 查询下当前表是否包含sds中的所有字段, 如果不包含, 则提示
					if(sds.getSql().toLowerCase().trim().startsWith("select * from "))
					{
						for(DataSetColumn dsc : sds.getColumns())
						{
							if(tableInfo.getDataSetColumn(dsc.getColumnName())==null)
							{
								pushLog("Can not find column ["+dsc.getColumnName()+"] in table ["+tableName+"], please update table's struct!");
							}
						}
					}
					if(tableInfo.getPkColSet().isEmpty()) continue;
					sb.setLength(0);
					sb.append("select ");
					LinkedHashSet<String> pkColSet = tableInfo.getPkColSet();
					for(String pkCol : pkColSet) sb.append(pkCol).append(',');
					sb.deleteCharAt(sb.length()-1);
					sb.append(" from ").append(tableName);
					Set<String> keySet = new HashSet<String>();
					IDataSet tempDataSet = dataBase.sqlSelect(sb.toString());
					for(int j=0;j<tempDataSet.getRowCount();++j) keySet.add(getPKColKey(tempDataSet, j, pkColSet));
					dbfield.set(sds, dataBase);
					sds.setSaveTransaction(false);
					sds.setChanged(true);
					for(int j=0;j<sds.getRowCount();++j)
					{
						DataSetRow row = sds.getRow(j);
						String pkColKey = getPKColKey(sds, j, pkColSet);
						if(keySet.contains(pkColKey))
						{
							if(override) row.setChanged(true);
							else row.setChanged(false);
							row.setChangeStatus(DataSetRow.STATUS_COMMON);
						}
						else
						{
							row.setChanged(true);
							row.setChangeStatus(DataSetRow.STATUS_FOR_NEW);
						}
					}
					pushLog("Import data from sql : " + sds.getSql() + " ... ");
					if(sds.save())
					{
						if(!batch) pushLog("successed!");
					}
					else pushLog("failed");
					this.pushValue(++innerValue);
				}
			}
			if(!dataBase.getAutoCommit()) dataBase.commit();
		}
		catch(Exception e)
		{
			pushException(e);
		}
		finally
		{
			DBUtil.close(dataBase);
		}
	}
	
	private String getPKColKey(IDataSet dataSet, int rowAt, LinkedHashSet<String> pkColSet)
	{
		String pkColKey = "";
		for(String pkCol : pkColSet) pkColKey += "_" + dataSet.getValue(rowAt, pkCol);
		return pkColKey;
	}
	
	protected synchronized void initData() throws Exception
	{
		if(sdsListList!=null) return;
		sdsListList = new ArrayList<List<StorableDataSet>>();
		for(int i=0;i<attachments.length;++i)
		{
			XlsysAttachment attachment = attachments[i];
			initAttachmentContent(attachment);
			byte[] data = attachment.getAttachmentData();
			sdsListList.add((List<StorableDataSet>)IOUtil.readObject(data));
		}
	}
	
	private void initAttachmentContent(XlsysAttachment attachment) throws Exception
	{
		if(attachment.getAttachmentData()!=null) return;
		byte[] bytes = null;
		if(attachment.getStyle()==XlsysAttachment.STYLE_FILE_SYSTEM)
		{
			// 从文件系统下载
			String workDir = null;
			if(attachment.getId()!=null) workDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance(attachment.getId());
			else workDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance();
			// 使用内部名称获取文件
			String filePath = workDir + '/' + (attachment.getPath()==null?"":attachment.getPath()) + '/' + attachment.getInnerName();
			filePath = FileUtil.fixFilePath(filePath);
			bytes = FileUtil.getByteFromFile(filePath);
		}
		else if(attachment.getStyle()==XlsysAttachment.STYLE_FTP)
		{
			// 从Ftp下载
			FTPUtil ftpUtil = null;
			InputStream is = null;
			try
			{
				if(attachment.getId()!=null) ftpUtil = ((FtpModel)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_FTP).getInstance(attachment.getId())).getFtpInstance();
				else ftpUtil = ((FtpModel)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_FTP).getInstance()).getFtpInstance();
				if(attachment.getPath()!=null) ftpUtil.cd(attachment.getPath());
				// 使用内部名称获取文件
				is = ftpUtil.get(attachment.getInnerName());
				BufferedInputStream bis = new BufferedInputStream(is);
				bytes = IOUtil.readBytesFromInputStream(bis, -1);
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				if(ftpUtil!=null) ftpUtil.close();
				IOUtil.close(is);
			}
		}
		attachment.setAttachmentData(bytes);
		attachment.decompress();
	}
	
	public int getMaximum()
	{
		if(maximum==null)
		{
			try
			{
				maximum = 0;
				initData();
				for(List<StorableDataSet> sdsList : sdsListList) maximum += sdsList.size();
			}
			catch(Exception e)
			{
				pushException(e);
			}
		}
		return maximum;
	}
	
	private synchronized void pushValue(int value)
	{
		this.value = value;
		notifyAll();
	}
	
	private synchronized void pushException(Exception e)
	{
		this.e = e;
		notifyAll();
	}
	
	private synchronized void pushLog(Object content)
	{
		logList.add(ObjectUtil.objectToString(content));
	}
	
	public synchronized Serializable popNextResult() throws InterruptedException
	{
		Serializable temp = null;
		if(e==null&&value==null) wait();
		if(e!=null)
		{
			temp = e;
			e = null;
		}
		else
		{
			temp = value;
			value = null;
		}
		return temp;
	}
	
	public synchronized String popLog()
	{
		if(logList.isEmpty()) return null; 
		StringBuilder sb = new StringBuilder();
		int size = logList.size();
		for(int i=0;i<size;++i)
		{
			sb.append(logList.get(i));
			if(i!=size-1) sb.append('\n');
		}
		logList.clear();
		return sb.toString();
	}
}
