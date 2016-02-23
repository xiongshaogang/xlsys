package xlsys.base.search;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.apache.lucene.store.IndexInput;

import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.dataset.IDataSet;
import xlsys.base.util.ObjectUtil;

public class DBIndexInput extends IndexInput
{
	/**
	 * 数据库连接
	 */
	private IDataBase dataBase;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 文件名字段名
	 */
	private String fileNameCol;
	/**
	 * 文件段序号字段名(大于2G的文件会拆分成多个文件段来存储)
	 */
	private String idxCol;
	/**
	 * 文件内容字段名
	 */
	private String fileContentCol;
	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 * 开始序号(包含,如果没有可为null)
	 */
	private Integer startIdx;
	/**
	 * 结束序号(包含,如果没有可为null)
	 */
	private Integer endIdx;
	/**
	 * 开始的偏移量
	 */
	private long startOffSet;
	/**
	 * 总长度
	 */
	private long length;
	private LinkedHashMap<Integer, Integer> lenMap;
	private long pos;
	
	/**
	 * 当前文件段序号
	 */
	private int curIdx;
	/**
	 * 当前文件段内容
	 */
	private byte[] curContent;
	/**
	 * 当前文件段的绝对起始位置
	 */
	private int absStartPos;
	/**
	 * 当前文件段的绝对结束位置
	 */
	private int absEndPos;
	
	public DBIndexInput(String resourceDescription, IDataBase dataBase, String tableName, String fileNameCol, String idxCol, String fileContentCol, String fileName) throws IOException
	{
		this(resourceDescription, dataBase, tableName, fileNameCol, idxCol, fileContentCol, fileName, null, null, null, null);
	}
	
	public DBIndexInput(String resourceDescription, IDataBase dataBase, String tableName, String fileNameCol, String idxCol, String fileContentCol, String fileName, Integer startIdx, Integer endIdx, Long startOffSet, Long length) throws IOException
	{
		super(resourceDescription);
		this.dataBase = dataBase;
		this.tableName = tableName;
		this.fileNameCol = fileNameCol;
		this.idxCol = idxCol;
		this.fileContentCol = fileContentCol;
		this.fileName = fileName;
		this.startIdx = startIdx;
		this.endIdx = endIdx;
		this.startOffSet = (startOffSet==null||startOffSet<0)?0:startOffSet;
		this.length = (length==null||length<0)?-1:length;
		lenMap = new LinkedHashMap<Integer, Integer>();
		pos = 0;
		curIdx = -1;
		absStartPos = -1;
		absEndPos = -1;
		loadContentByPos();
	}
	
	/**
	 * 根据指针绝对位置获取相应的参数
	 * @return Integer[], 分别为 文件段的所在序号, 该文件段的开始绝对位置, 该文件段的结束绝对位置(最后位置的后一个位置).
	 * 如果找不到则返回null
	 */
	private Integer[] getXYbyPos(long absPos)
	{
		initLengthMap();
		int startPos = 0;
		int endPos = 0;
		Integer targetIdx = null;
		for(Integer idx : lenMap.keySet())
		{
			endPos += lenMap.get(idx);
			if(absPos>=startPos&&absPos<endPos)
			{
				// 已找到文件序号位置, 直接跳出循环
				targetIdx = idx;
				break;
			}
			startPos = endPos;
		}
		if(targetIdx==null) return null;
		Integer[] ret = new Integer[3];
		ret[0] = targetIdx;
		ret[1] = startPos;
		ret[2] = endPos;
		return ret;
	}
	
	/**
	 * 根据当前指针位置加载缓冲内容
	 * @throws IOException 
	 */
	private void loadContentByPos() throws IOException
	{
		Integer[] param = getXYbyPos(pos+startOffSet);
		if(param==null)
		{
			curIdx = -1;
			absStartPos = -1;
			absEndPos = -1;
			curContent = null;
			return;
		}
		int targetIdx = param[0]; 
		int startPos = param[1];
		int endPos = param[2];
		if(curIdx==targetIdx) return;
		curIdx = targetIdx;
		absStartPos = startPos;
		absEndPos = endPos;
		curContent = getContentByIdx(targetIdx);
	}

	private byte[] getContentByIdx(int idx) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("select ").append(fileContentCol).append(" from ").append(tableName).append(" where ").append(fileNameCol).append("=? and ").append(idxCol).append("=?");
		ParamBean pb = new ParamBean(sb.toString());
		pb.addParamGroup();
		byte[] content = null;
		try
		{
			pb.setParam(1, fileName);
			pb.setParam(2, idx);
			content = (byte[]) dataBase.sqlSelectAsOneValue(pb);
		}
		catch(Exception e)
		{
			throw new IOException(e);
		}
		return content;
	}
	
	@Override
	public void close() throws IOException {}

	@Override
	public long getFilePointer()
	{
		return pos;
	}
	
	private void initLengthMap()
	{
		if(lenMap.isEmpty())
		{
			try
			{
				// 获取该名称所有文件长度的加总
				String lengthAlias = "filelength";
				StringBuilder sb = new StringBuilder();
				sb.append("select ").append(idxCol).append(',').append(dataBase.getByteLengthFunc(fileContentCol)).append(" as ").append(lengthAlias).append(" from ").append(tableName).append(" where ").append(fileNameCol).append("=?");
				if(startIdx!=null) sb.append(" and ").append(idxCol).append(">=?");
				if(endIdx!=null) sb.append(" and ").append(idxCol).append("<=?");
				sb.append(" order by ").append(idxCol);
				ParamBean pb = new ParamBean(sb.toString());
				pb.addParamGroup();
				int paramIdx = 1;
				pb.setParam(paramIdx++, fileName);
				if(startIdx!=null) pb.setParam(paramIdx++, startIdx);
				if(endIdx!=null) pb.setParam(paramIdx++, endIdx);
				IDataSet dataSet = dataBase.sqlSelect(pb);
				int rowCount = dataSet.getRowCount();
				for(int i=0;i<rowCount;++i)
				{
					lenMap.put(ObjectUtil.objectToInt(dataSet.getValue(i, idxCol)), ObjectUtil.objectToInt(dataSet.getValue(i, lengthAlias)));
				}
			}
			catch(Exception e) {}
		}
	}

	@Override
	public long length()
	{
		initLengthMap();
		if(length<0)
		{
			length = 0;
			for(Integer len : lenMap.values()) length += len;
		}
		return length;
	}

	@Override
	public void seek(long pos) throws IOException
	{
		this.pos = pos;
		loadContentByPos();
	}

	@Override
	public IndexInput slice(String sliceDescription, long offset, long length) throws IOException
	{
		// 获取开始位置
		long tempAbsStartPos = offset+startOffSet;
		Integer[] startParam = getXYbyPos(tempAbsStartPos);
		if(startParam==null) throw new IOException();
		int tempStartIdx = startParam[0]; 
		int tempStart1Pos = startParam[1];
		// 获取结束位置
		long tempAbsEndPos = tempAbsStartPos + length - 1;
		Integer[] endParam = getXYbyPos(tempAbsEndPos);
		long tempLength = -1;
		Integer tempEndIdx = null;
		if(endParam==null)
		{
			// 结束位置找不到，则说明直接截取的末尾
			tempLength = length()-offset;
		}
		else
		{
			// 找到结束位置, 则截取到指定的位置
			tempEndIdx = endParam[0];
			tempLength = length;
		}
		if(tempStartIdx==tempEndIdx)
		{
			byte[] tempContent = null;
			if(tempStartIdx==curIdx) tempContent = curContent;
			else tempContent = getContentByIdx(tempStartIdx);
			byte[] sliceContent = Arrays.copyOfRange(tempContent, (int)(tempAbsStartPos-tempStart1Pos), (int)(tempAbsStartPos-tempStart1Pos+tempLength));
			return new ByteArrayIndexInput(getFullSliceDescription(sliceDescription), sliceContent);
		}
		else return new DBIndexInput(getFullSliceDescription(sliceDescription), dataBase, tableName, fileNameCol, idxCol, fileContentCol, fileName, tempStartIdx, tempEndIdx, tempAbsStartPos-tempStart1Pos, tempLength);
	}

	@Override
	public byte readByte() throws IOException
	{
		if(pos>=length()) return -1;
		long absPos = pos + startOffSet;
		if(absPos>=absEndPos) loadContentByPos();
		byte b = curContent[(int)(absPos-absStartPos)];
		++pos;
		return b;
	}

	@Override
	public void readBytes(byte[] b, int offset, int len) throws IOException
	{
		for(int i=offset;i<(len+offset)&&i<b.length;++i)
		{
			b[i] = readByte();
		}
	}

}
