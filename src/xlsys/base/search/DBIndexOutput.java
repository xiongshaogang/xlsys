package xlsys.base.search;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

import org.apache.lucene.store.IndexOutput;

import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.io.util.IOUtil;

public class DBIndexOutput extends IndexOutput
{
	private CRC32 crc32;
	private IDataBase dataBase;
	private String tableName;
	private String fileNameCol;
	private String idxCol;
	private String fileContentCol;
	private String fileName;
	private int bufferSize;
	
	private int pos;
	private ByteArrayOutputStream baos;
	private int curIdx;
	
	public DBIndexOutput(String resourceDescription, IDataBase dataBase, String tableName, String fileNameCol, String idxCol, String fileContentCol, String fileName)
	{
		this(resourceDescription, dataBase, tableName, fileNameCol, idxCol, fileContentCol, fileName, 1024*1024);
	}
	
	public DBIndexOutput(String resourceDescription, IDataBase dataBase, String tableName, String fileNameCol, String idxCol, String fileContentCol, String fileName, int bufferSize)
	{
		super(resourceDescription);
		crc32 = new CRC32();
		this.dataBase = dataBase;
		this.tableName = tableName;
		this.fileNameCol = fileNameCol;
		this.idxCol = idxCol;
		this.fileContentCol = fileContentCol;
		this.fileName = fileName;
		this.bufferSize = bufferSize;
		pos = 0;
		curIdx = 0;
		resetOutputStream();
	}

	@Override
	public void close() throws IOException
	{
		if(baos.size()>0) writeContentToDb(baos.toByteArray());
		DBUtil.close(baos);
	}

	@Override
	public long getChecksum() throws IOException
	{
		return crc32.getValue();
	}

	@Override
	public long getFilePointer()
	{
		return pos;
	}

	private void resetOutputStream()
	{
		if(baos!=null)
		{
			IOUtil.close(baos);
			baos = null;
		}
		if(baos==null) baos = new ByteArrayOutputStream();
	}
	
	protected void writeContentToDb(byte[] byteArray) throws IOException
	{
		ExecuteBean eb = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_INSERT, tableName);
		Map<String, Serializable> dataMap = new HashMap<String, Serializable>();
		dataMap.put(fileNameCol, fileName);
		dataMap.put(idxCol, curIdx);
		dataMap.put(fileContentCol, byteArray);
		eb.addData(dataMap);
		try
		{
			dataBase.sqlExecute(eb);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
	
	@Override
	public void writeByte(byte b) throws IOException
	{
		baos.write(b);
		crc32.update(b);
		++pos;
		if(baos.size()==bufferSize)
		{
			// 将数据写入数据库当前文件序号段中
			writeContentToDb(baos.toByteArray());
			// 重置数据缓冲
			resetOutputStream();
			// 将当前文件段序号+1
			++curIdx;
		}
	}

	@Override
	public void writeBytes(byte[] b, int offset, int length) throws IOException
	{
		for(int i=offset;i<(offset+length)&&i<b.length;++i)
		{
			writeByte(b[i]);
		}
	}

}
