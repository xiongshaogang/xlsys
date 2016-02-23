package xlsys.base.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import xlsys.base.dataset.DataSet;
import xlsys.base.dataset.DataSetCell;
import xlsys.base.dataset.DataSetColumn;
import xlsys.base.dataset.DataSetRow;
import xlsys.base.dataset.util.DataSetUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.log.LogUtil;

/**
 * DBF读入流
 * @author Lewis
 *
 */
public class DBFInputStream extends InputStream
{
	private InputStream is;
	private int dbfVersion;
	private int dbfYear;
	private int dbfMonth;
	private int dbfDay;
	private int recordCount;
	private int headLength;
	private int recordLength;
	
	private int currentRecord;
	private DataSet dbfDataSet;

	/**
	 * 构造一个DBF读入流
	 * @param is
	 */
	public DBFInputStream(InputStream is)
	{
		this.is = is;
		this.dbfDataSet = new DataSet();
		currentRecord = 0;
		open();
	}
	
	private void open()
	{
		try
		{
			dbfVersion = is.read();
			dbfYear = is.read();
			dbfMonth = is.read();
			dbfDay = is.read();
			recordCount = this.readInt();
			headLength = this.readShort();
			recordLength = this.readShort();
			is.skip(16+2+2);
			int ncolumns = (headLength-32-1)/32;
			if(dbfVersion==0x30)
	        {
				int curLen = 32;
	            for(int i=0;curLen<headLength&&i<ncolumns;i++)
	            {
	            	String columnName = readString(11).toLowerCase();
	            	if(columnName.trim().length()==0)
	            	{
	            		is.skip(headLength-curLen-11);
	            		break;
	            	}
	            	dbfDataSet.insertNewColumnAfterLast();
	            	DataSetColumn dsc = dbfDataSet.getColumns().get(dbfDataSet.getColumns().size()-1);
	            	dsc.setColumnName(columnName);
	            	int type = (byte)is.read();
	            	is.skip(4);
	            	dsc.setPrecision((short)is.read());
	            	dsc.setScale((short)is.read());
	            	dsc.setDbColumnType(""+(char)type);
	            	dsc.setSqlType(type);
	            	dsc.setJavaClass(dbfTypeToJavaClass(type, dsc.getPrecision(), dsc.getScale()));          
	            	is.skip(14);
	            	curLen += 32;
	            }
	        }
			else
	        {
	            for(int i=0;i<ncolumns;i++)
	            {
	            	String columnName = readString(11).toLowerCase();
	            	dbfDataSet.insertNewColumnAfterLast();
	            	DataSetColumn dsc = dbfDataSet.getColumns().get(dbfDataSet.getColumns().size()-1);
	            	dsc.setColumnName(columnName);
	            	int type = (byte)is.read();
	            	is.skip(4);
	            	dsc.setPrecision((short)is.read());
	            	dsc.setScale((short)is.read());
	            	dsc.setDbColumnType(""+(char)type);
	            	dsc.setJavaClass(dbfTypeToJavaClass(type, dsc.getPrecision(), dsc.getScale()));          
	            	is.skip(14);
	            }
	            if(is.read()!=0x0D)
	            {
	            	throw new IOException();
	            }
	        }
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * @rowCount : 需要获得的记录数，为-1将返回剩下的所有记录
	 */
	/**
	 * 获取DBF数据到DataSet中.
	 * @param rowCount 需要获得的记录数，为-1将返回剩下的所有记录
	 * @param needDeletedRow 是否需要获取标记为删除的数据
	 * @return
	 * @throws IOException
	 */
	public DataSet getDbfDataSet(int rowCount, boolean needDeletedRow) throws IOException
	{
		byte[] recordBuffer = new byte[recordLength];
		dbfDataSet.removeAllRow();
		while(rowCount!=0&&currentRecord<recordCount)
		{
			for(int i=0;i<recordBuffer.length;i++)
			{
				recordBuffer[i] = (byte)is.read();
			}
			if(recordBuffer[0]==0x2A)
			{
				if(needDeletedRow)
				{
					addRowFromDbfBytes(recordBuffer);
					rowCount--;
				}
			}
			else
			{
				addRowFromDbfBytes(recordBuffer);
				rowCount--;
			}
			currentRecord++;
		}
		return dbfDataSet;
	}
	
	private void addRowFromDbfBytes(byte[] recordBuffer)
	{
		int pos = 1;
		dbfDataSet.insertNewRowAfterLast();
		DataSetRow dsr = dbfDataSet.getRows().get(dbfDataSet.getRowCount()-1);
		for(int i=0;i<dbfDataSet.getColumnCount();pos+=dbfDataSet.getColumn(i++).getPrecision())
		{
			DataSetColumn dsc = dbfDataSet.getColumn(i);
			DataSetCell dsCell = dsr.getCell(i);
			String text = null;
			if(!"java.lang.Double".equals(dsc.getJavaClass()))
			{
				text = readString(recordBuffer,pos,dsc.getPrecision());
			}
			if("java.sql.Time".equals(dsc.getJavaClass()))
			{
				if(dsc.getPrecision()==8)
				{
					byte a[] = new byte[dsc.getPrecision()];
					for(int j=0;j<a.length;j++)
	         		{
						a[j] = recordBuffer[pos+j];
	         		}
					long ndays = ((a[3]&0xff)<<24)|((a[2]&0xff)<<16)|((a[1]&0xff)<<8)|((a[0]&0xff)<<0);
	            	long millseconds = ((a[7]&0xff)<<24)|((a[6]&0xff)<<16)|((a[5]&0xff)<<8)|((a[4]&0xff)<<0);
	            	Date date = new Date(-210866832000000L+ndays*24*60*60*1000L+millseconds);
	            	dsCell.setContent(date);
				}
				else
				{
					throw new IllegalArgumentException("读取时间类型长度出错!");
				}
			}
			else if("java.lang.String".equals(dsc.getJavaClass()))
			{
				dsCell.setContent(text);
			}
			else if("java.lang.Integer".equals(dsc.getJavaClass()))
			{
				dsCell.setContent(Integer.valueOf(text.trim()));
			}
			else if("java.math.BigDecimal".equals(dsc.getJavaClass()))
			{
				BigDecimal bd = new BigDecimal(text.trim());
				bd.setScale(dsc.getScale(),BigDecimal.ROUND_HALF_UP);
				dsCell.setContent(bd);
			}
			else if("java.util.Date".equals(dsc.getJavaClass()))
			{
				int year = Integer.parseInt(text.substring(0,4));
                int month = Integer.parseInt(text.substring(4,6));
                int day = Integer.parseInt(text.substring(6,8));
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month-1);
                calendar.set(Calendar.DAY_OF_MONTH,day);
				dsCell.setContent(calendar.getTime());
			}
			else if("java.lang.Boolean".equals(dsc.getJavaClass()))
			{
				dsCell.setContent("T".equals(text)?Boolean.TRUE:Boolean.FALSE);
			}
			else if("java.lang.Double".equals(dsc.getJavaClass()))
			{
				if(dsc.getPrecision()==8)
				{
					long x = 0;
	                for(int j=0;j<8;j++)
	                {
	                    x |= (recordBuffer[pos+j]&0xffl)<<(j*8);
	                }
	                dsCell.setContent(new Double(Double.longBitsToDouble(x)));
				}
				else
				{
					throw new IllegalArgumentException("读取double类型长度出错!");
				}
			}
			else
			{
				dsCell.setContent(text);
			}
		}
	}
	
	private String dbfTypeToJavaClass(int dbfType, int precision, int scale)
    {
		String javaClassStr = "java.lang.String";
        switch( dbfType )
        {
            case 'C':
            	javaClassStr = "java.lang.String";
            	break;
            case 'N': 
            	if(precision==0&&scale<9)
            	{
            		javaClassStr = "java.lang.Integer";
            	}
            	else
            	{
            		javaClassStr = "java.math.BigDecimal";
            	}
            	break;
            case 'T':
            	javaClassStr = "java.sql.Time";
            	break;
            case 'D':
            	javaClassStr = "java.util.Date";
            	break;
            case 'L':
            	javaClassStr = "java.lang.Boolean";
            	break;
            case 'B':
            	javaClassStr = "java.lang.Double";
            	break;
            case '0':
            	javaClassStr = "null";
            default:
            	LogUtil.printlnError("Unsupport type of DBF ："+(char)dbfType);
        }
        return javaClassStr;
    }
	
	private int readInt()  throws IOException
    {
        int ch1 = is.read()&0xff;
        int ch2 = is.read()&0xff;
        int ch3 = is.read()&0xff;
        int ch4 = is.read()&0xff;
        if ((ch1 | ch2 | ch3 | ch4) < 0)
        {
        	throw new EOFException();
        }
        return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0));
    }
	
    private int readShort()  throws IOException
    {
        int ch1 = is.read()&0xff;
        int ch2 = is.read()&0xff;
        if ((ch1 | ch2 ) < 0)
        {
        	 throw new EOFException();
        }
        return  (short)((ch2 << 8) + (ch1 << 0));
    }
     private String readString(int length) throws IOException
    {
        byte[] buffer = new byte[length];
        int end = -1;
        for(int i=0;i<length;i++)
        {
            int c = is.read();
            if(c>0)
            {
            	buffer[i] = (byte)c;
            }
            else if(end==-1)
            {
            	end = i;
            }
        }
        if(end==-1)
        {
        	end = length;
        }
        return readString(buffer,0,end);
    }
     
    private String readString(byte[]buffer,int start,int count)
    {
        for(;count>0 && buffer[start+count-1] >=0 && buffer[start+count-1]<=0x20;count--)
            ;
        return new String(buffer,start,count);
    }
	
	@Override
	public void close() throws IOException
	{
		if(is!=null)
		{
			is.close();
		}
	}

	@Override
	public int read() throws IOException
	{
		return is.read();
	}
	
	public static void main(String[] args)
	{
		DBFInputStream dbfInputStream = null;
		try
		{
			dbfInputStream = new DBFInputStream(IOUtil.getFileInputStream("D:\\lxd\\ftsjh.dbf"));
			System.err.println(dbfInputStream.recordCount);
			DataSet ds = dbfInputStream.getDbfDataSet(10,false);
			DataSetUtil.dumpData(ds, true, true);
			ds = dbfInputStream.getDbfDataSet(10,false);
			DataSetUtil.dumpData(ds, true, true);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			IOUtil.close(dbfInputStream);
		}
	}

	/**
	 * 获取DBF的年
	 * @return
	 */
	public int getDbfYear()
	{
		return dbfYear;
	}

	/**
	 * 获取DBF的月
	 * @return
	 */
	public int getDbfMonth()
	{
		return dbfMonth;
	}

	/**
	 * 获取DBF的日
	 * @return
	 */
	public int getDbfDay()
	{
		return dbfDay;
	}
}
