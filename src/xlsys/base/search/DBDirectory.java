package xlsys.base.search;

import java.io.IOException;
import java.util.Collection;

import org.apache.lucene.store.BaseDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.IndexOutput;
import org.apache.lucene.store.NoLockFactory;

import xlsys.base.database.EnvDataBase;
import xlsys.base.database.IEnvDataBase;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.dataset.IDataSet;
import xlsys.base.util.ObjectUtil;

public class DBDirectory extends BaseDirectory
{
	protected boolean needClose;
	protected IEnvDataBase dataBase;
	protected String tableName;
	protected String fileNameCol;
	protected String idxCol;
	protected String fileContentCol;
	
	public DBDirectory(int envId, String tableName, String fileNameCol, String idxCol, String fileContentCol) throws Exception
	{
		super(NoLockFactory.INSTANCE);
		dataBase = EnvDataBase.getInstance(envId);
		needClose = true;
		this.tableName = tableName;
		this.fileNameCol = fileNameCol;
		this.idxCol = idxCol;
		this.fileContentCol = fileContentCol;
	}
	
	public DBDirectory(IEnvDataBase dataBase, String tableName, String fileNameCol, String idxCol, String fileContentCol)
	{
		super(NoLockFactory.INSTANCE);
		this.dataBase = dataBase;
		needClose = false;
		this.tableName = tableName;
		this.fileNameCol = fileNameCol;
		this.idxCol = idxCol;
		this.fileContentCol = fileContentCol;
	}

	@Override
	public void close() throws IOException
	{
		if(needClose) DBUtil.close(dataBase);
	}

	@Override
	public IndexOutput createOutput(String name, IOContext context) throws IOException
	{
		return new DBIndexOutput(name, dataBase, tableName, fileNameCol, idxCol, fileContentCol, name);
	}

	@Override
	public void deleteFile(String name) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ").append(tableName).append(" where ").append(fileNameCol).append("=?");
		ParamBean pb = new ParamBean(sb.toString());
		pb.addParamGroup();
		try
		{
			pb.setParam(1, name);
			dataBase.sqlExecute(pb);
		}
		catch(Exception e)
		{
			throw new IOException(e);
		}
	}

	@Override
	public long fileLength(String name) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("select ").append(dataBase.getByteLengthFunc(fileContentCol)).append(" as fileLength from ").append(tableName).append(" where ").append(fileNameCol).append("=? order by ").append(idxCol);
		ParamBean pb = new ParamBean(sb.toString());
		pb.addParamGroup();
		long length = 0;
		try
		{
			pb.setParam(1, name);
			IDataSet dataSet = dataBase.sqlSelect(pb);
			int rowCount = dataSet.getRowCount();
			for(int i=0;i<rowCount;++i)
			{
				length += ObjectUtil.objectToInt(dataSet.getValue(i, 0));
			}
		}
		catch(Exception e)
		{
			throw new IOException(e);
		}
		return length;
	}

	@Override
	public String[] listAll() throws IOException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct ").append(fileNameCol).append(" from ").append(tableName).append(" order by ").append(fileNameCol);
		String[] allName = null;
		try
		{
			IDataSet dataSet = dataBase.sqlSelect(sb.toString());
			int rowCount = dataSet.getRowCount();
			allName = new String[rowCount];
			for(int i=0;i<rowCount;++i)
			{
				allName[i] = ObjectUtil.objectToString(dataSet.getValue(i, 0));
			}
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
		return allName;
	}

	@Override
	public IndexInput openInput(String name, IOContext context) throws IOException
	{
		return new DBIndexInput(name, dataBase, tableName, fileNameCol, idxCol, fileContentCol, name);
	}

	@Override
	public void renameFile(String source, String dest) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("update ").append(tableName).append(" set ").append(fileNameCol).append("=? where ").append(fileNameCol).append("=?");
		ParamBean pb = new ParamBean(sb.toString());
		pb.addParamGroup();
		try
		{
			pb.setParam(1, dest);
			pb.setParam(2, source);
			dataBase.sqlExecute(pb);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	@Override
	public void sync(Collection<String> names) throws IOException {}
}
