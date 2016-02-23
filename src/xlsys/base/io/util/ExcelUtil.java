package xlsys.base.io.util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

import xlsys.base.dataset.DataSet;
import xlsys.base.dataset.DataSetColumn;
import xlsys.base.dataset.DataSetRow;
import xlsys.base.dataset.IDataSet;
import xlsys.base.dataset.util.DataSetUtil;
import xlsys.base.image.ImageUtil;
import xlsys.base.log.LogUtil;
import xlsys.base.script.XlsysClassLoader;
import xlsys.base.util.ObjectUtil;

public class ExcelUtil
{
	private static final String COLUMN_NAME_INNER = "_COLUMN_NAME_INNER";
	
	public static int getRowCountOfSheet(HSSFSheet sheet)
	{
		int rowCount = sheet.getLastRowNum();
		if(rowCount==0) rowCount = sheet.getPhysicalNumberOfRows();
		else ++rowCount;
		return rowCount;
	}
	
	public static int getColCountOfSheet(HSSFSheet sheet)
	{
		int colCount = 0;
		int rowCount = getRowCountOfSheet(sheet);
		for(int i=0;i<rowCount;i++)
		{
			HSSFRow row = sheet.getRow(i);
			if(row!=null)
			{
				int maxColIdx = row.getLastCellNum();
				if(colCount<maxColIdx) colCount = maxColIdx;
			}
		}
		return colCount;
	}
	
	public static HSSFCell insertCell(HSSFWorkbook workbook, HSSFRow row, int rowNum)
	{
		HSSFCell cell = null;
		int maxColIdx = row.getLastCellNum();
		if(maxColIdx<rowNum)
		{
			cell = row.createCell(rowNum);
		}
		else
		{
			// 移动要插入列号之后(包含)的所有的单元格
			for(int i=maxColIdx;i>=rowNum;i--)
			{
				HSSFCell toCell = row.createCell(i+1);
				HSSFCell fromCell = row.getCell(i);
				cloneCell(workbook, fromCell, toCell);
			}
			cell = row.createCell(rowNum);
		}
		return cell;
	}
	
	public static void cloneCell(HSSFWorkbook workbook, HSSFCell srcCell, HSSFCell newCell)
	{
		if(srcCell==null) return;
		// CellStyle
		HSSFCellStyle srcCellStyle = srcCell.getCellStyle();
		/*HSSFCellStyle newCellStyle = workbook.createCellStyle();
		newCellStyle.cloneStyleFrom(srcCellStyle);
		newCell.setCellStyle(newCellStyle);*/
		newCell.setCellStyle(srcCellStyle);
		// CellType
		int cellType = srcCell.getCellType();
		newCell.setCellType(srcCell.getCellType());
		// Comment
		HSSFComment srcComment = srcCell.getCellComment();
		if(srcComment!=null) newCell.setCellComment(srcComment);
		// content
		if(cellType==HSSFCell.CELL_TYPE_BLANK)
		{
			// do nothing
		}
		else if(cellType==HSSFCell.CELL_TYPE_BOOLEAN)
		{
			newCell.setCellValue(srcCell.getBooleanCellValue());
		}
		else if(cellType==HSSFCell.CELL_TYPE_ERROR)
		{
			newCell.setCellErrorValue(srcCell.getErrorCellValue());
		}
		else if(cellType==HSSFCell.CELL_TYPE_FORMULA)
		{
			newCell.setCellFormula(srcCell.getCellFormula());
		}
		else if(cellType==HSSFCell.CELL_TYPE_NUMERIC)
		{
			if(HSSFDateUtil.isCellDateFormatted(srcCell))
			{
				newCell.setCellValue(srcCell.getDateCellValue());
			}
			else newCell.setCellValue(srcCell.getNumericCellValue());
		}
		else if(cellType==HSSFCell.CELL_TYPE_STRING)
		{
			newCell.setCellValue(srcCell.getRichStringCellValue());
		}
	}
	
	public static void createColumn(HSSFSheet sheet, int colIndex)
	{
		int rowCount = getRowCountOfSheet(sheet);
		for(int i=0;i<rowCount;i++)
		{
			HSSFRow row = sheet.getRow(i);
			if(row==null) row = sheet.createRow(i);
			row.createCell(colIndex);
		}
	}
	
	public static Set<CellRangeAddress> getRowRelativeRange(List<CellRangeAddress> rangeAddressList, Set<CellRangeAddress> newRangeSet, Integer[] rowIndices)
	{
		Set<CellRangeAddress> set = new HashSet<CellRangeAddress>();
		for(CellRangeAddress rangeAddress : rangeAddressList)
		{
			for(int rowIndex : rowIndices)
			{
				if(rowIndex>=rangeAddress.getFirstRow()&&rowIndex<=rangeAddress.getLastRow())
				{
					set.add(rangeAddress);
				}
			}
		}
		for(CellRangeAddress rangeAddress : newRangeSet)
		{
			for(int rowIndex : rowIndices)
			{
				if(rowIndex>=rangeAddress.getFirstRow()&&rowIndex<=rangeAddress.getLastRow())
				{
					set.add(rangeAddress);
				}
			}
		}
		return set;
	}
	
	public static Set<CellRangeAddress> getColRelativeRange(List<CellRangeAddress> rangeAddressList, Set<CellRangeAddress> newRangeSet, Integer[] colIndices)
	{
		Set<CellRangeAddress> set = new HashSet<CellRangeAddress>();
		for(CellRangeAddress rangeAddress : rangeAddressList)
		{
			for(int colIndex : colIndices)
			{
				if(colIndex>=rangeAddress.getFirstColumn()&&colIndex<=rangeAddress.getLastColumn())
				{
					set.add(rangeAddress);
				}
			}
		}
		for(CellRangeAddress rangeAddress : newRangeSet)
		{
			for(int colIndex : colIndices)
			{
				if(colIndex>=rangeAddress.getFirstColumn()&&colIndex<=rangeAddress.getLastColumn())
				{
					set.add(rangeAddress);
				}
			}
		}
		return set;
	}
	
	public static CellRangeAddress getCellRelativeRange(List<CellRangeAddress> rangeAddressList, int rowIdx, int colIdx)
	{
		CellRangeAddress range = null;
		for(CellRangeAddress rangeAddress : rangeAddressList)
		{
			if(rowIdx>=rangeAddress.getFirstRow()&&rowIdx<=rangeAddress.getLastRow()&&colIdx>=rangeAddress.getFirstColumn()&&colIdx<=rangeAddress.getLastColumn())
			{
				range = rangeAddress;
				break;
			}
		}
		return range;
	}
	
	public static boolean containsRange(Collection<CellRangeAddress> rangeCollection, CellRangeAddress range)
	{
		boolean contains = false;
		for(CellRangeAddress cra : rangeCollection)
		{
			if(cra.getFirstRow()==range.getFirstRow()&&cra.getLastRow()==range.getLastRow()&&cra.getFirstColumn()==range.getFirstColumn()&&cra.getLastColumn()==range.getLastColumn())
			{
				contains = true;
				break;
			}
		}
		return contains;
	}
	
	public static Serializable getCellValue(HSSFCell cell)
	{
		Serializable result = null; 
		if(cell!=null)
		{
			int cellType = cell.getCellType();
			if(cellType==HSSFCell.CELL_TYPE_BLANK)
			{
				// do nothing
			}
			else if(cellType==HSSFCell.CELL_TYPE_BOOLEAN)
			{
				result = cell.getBooleanCellValue();
			}
			else if(cellType==HSSFCell.CELL_TYPE_ERROR)
			{
				result = cell.getErrorCellValue();
			}
			else if(cellType==HSSFCell.CELL_TYPE_FORMULA)
			{
				result = cell.getCellFormula();
			}
			else if(cellType==HSSFCell.CELL_TYPE_NUMERIC)
			{
				if(HSSFDateUtil.isCellDateFormatted(cell))
				{
					result = cell.getDateCellValue();
				}
				else result = new BigDecimal(cell.getNumericCellValue());
			}
			else if(cellType==HSSFCell.CELL_TYPE_STRING)
			{
				result = cell.getStringCellValue();
			}
		}
		return result;
	}
	
	public static IDataSet getDataSetFromExcel(File file, String sheetName) throws Exception
	{
		return getDataSetFromExcel(file, sheetName, true, true, true);
	}
	
	public static IDataSet getDataSetFromExcel(InputStream is, String sheetName) throws Exception
	{
		return getDataSetFromExcel(is, sheetName, true, true, true);
	}
	
	public static IDataSet getDataSetFromExcel(File file, String sheetName, boolean hasHeader, boolean removeNullRow, boolean removeNullCol) throws Exception
	{
		IDataSet dataSet = null;
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(file);
			dataSet = getDataSetFromExcel(fis, sheetName, hasHeader, removeNullRow, removeNullCol);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(fis);
		}
		return dataSet;
	}
	
	public static IDataSet getDataSetFromExcel(InputStream is, String sheetName, boolean hasHeader, boolean removeNullRow, boolean removeNullCol) throws Exception
	{
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		HSSFSheet sheet = workbook.getSheet(sheetName);
		return getDataSetFromExcel(sheet, hasHeader, removeNullRow, removeNullCol);
	}
	
	public static IDataSet getDataSetFromExcel(File file, int sheetIdx) throws Exception
	{
		return getDataSetFromExcel(file, sheetIdx, true, true, true);
	}
	
	public static IDataSet getDataSetFromExcel(InputStream is, int sheetIdx) throws Exception
	{
		return getDataSetFromExcel(is, sheetIdx, true, true, true);
	}
	
	public static IDataSet getDataSetFromExcel(File file, int sheetIdx, boolean hasHeader, boolean removeNullRow, boolean removeNullCol) throws Exception
	{
		IDataSet dataSet = null;
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(file);
			dataSet = getDataSetFromExcel(fis, sheetIdx, hasHeader, removeNullRow, removeNullCol);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(fis);
		}
		return dataSet;
	}
	
	public static IDataSet getDataSetFromExcel(InputStream is, int sheetIdx, boolean hasHeader, boolean removeNullRow, boolean removeNullCol) throws Exception
	{
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		HSSFSheet sheet = workbook.getSheetAt(sheetIdx);
		return getDataSetFromExcel(sheet, hasHeader, removeNullRow, removeNullCol);
	}
	
	private static IDataSet getDataSetFromExcel(HSSFSheet sheet, boolean hasHeader, boolean removeNullRow, boolean removeNullCol) throws Exception
	{
		IDataSet dataSet = null;
		if(sheet!=null)
		{
			LogUtil.printlnInfo("Load DataSet From Sheet Of Excel : " + sheet.getSheetName());
			dataSet = new DataSet();
			int colCount = getColCountOfSheet(sheet);
			int rowCount = getRowCountOfSheet(sheet);
			HSSFRow headRow = null;
			if(hasHeader&&rowCount>0) headRow = sheet.getRow(0);
			for(int i=0;i<colCount;++i)
			{
				DataSetColumn dsc = dataSet.insertNewColumnAfterLast();
				String colName = null;
				if(headRow!=null) colName = ObjectUtil.objectToString(getCellValue(headRow.getCell(i)));
				if(colName==null) colName = COLUMN_NAME_INNER+i;
				dsc.setColumnName(colName);
			}
			for(int i=(headRow==null?0:1);i<rowCount;++i)
			{
				dataSet.insertNewRowAfterLast();
				HSSFRow curRow = sheet.getRow(i);
				if(curRow!=null)
				{
					for(int j=0;j<colCount;++j)
					{
						dataSet.setValue(j, getCellValue(curRow.getCell(j)));
					}
				}
			}
			if(removeNullRow)
			{
				// 删除所有的空行
				for(int i=dataSet.getRowCount()-1;i>=0;--i)
				{
					DataSetRow dsr = dataSet.getRow(i);
					if(DataSetUtil.isNullDataSetRow(dsr)) dataSet.removeRow(i);
				}
			}
			if(removeNullCol)
			{
				// 删除所有的空列
				for(int i=dataSet.getColumnCount()-1;i>=0;--i)
				{
					DataSetColumn dsc = dataSet.getColumn(i);
					if(DataSetUtil.isNullDataSetColumn(dataSet, dsc)&&dsc.getColumnName().startsWith(COLUMN_NAME_INNER)) dataSet.removeColumn(i);
				}
			}
		}
		return dataSet;
	}
	
	/**
	 * 获取指定的Excel列对应的列号, 从0开始
	 * @param xlsColumnName Excel列
	 * @return 数字列号
	 */
	public static int getColumnIndex(String xlsColumnName)
	{
		int result = 0;
		char[] c1 = new char[1];
		xlsColumnName = xlsColumnName.toUpperCase();
		xlsColumnName.getChars(0, 1, c1, 0);
		if(xlsColumnName.length()==1)
		{
			result = c1[0]-64;
		}
		else if(xlsColumnName.length()==2)
		{
			char[] c2 = new char[1];
			xlsColumnName.getChars(1, 2, c2, 0);
			result = (c1[0]-64)*26+c2[0]-64;
		}
		return result;
	}
	
	/**
	 * 获取指定的列号对应的Excel列
	 * @param columnIndex 列号, 从0开始
	 * @return Excel中的列
	 */
	public static String getXlsColumnName(int columnIndex)
	{
		char firstChar = 0;
		char secondChar = (char) (columnIndex%26+65);
		int perch = columnIndex/26;
		if(perch!=0)
		{
			firstChar = (char) (perch+64);
			return ""+firstChar+secondChar;
		}
		return ""+secondChar;
	}
	
	public static void exportDataSetToExcel(OutputStream os, IDataSet dataSet, LinkedHashMap<String, String> exportColsMap, Map<String, LinkedHashMap<Serializable, String>> colSupportValueMap) throws Exception
	{
		if(dataSet!=null)
		{
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			int rowCount = dataSet.getRowCount();
			if(exportColsMap==null)
			{
				exportColsMap = new LinkedHashMap<String, String>();
				for(DataSetColumn dsc : dataSet.getColumns()) exportColsMap.put(dsc.getColumnName(), dsc.getColumnName());
			}
			// 先写列标题
			HSSFRow row = sheet.createRow(0);
			int colIdx = 0;
			for(String colName : exportColsMap.keySet())
			{
				HSSFCell cell = row.createCell(colIdx++, Cell.CELL_TYPE_STRING);
				cell.setCellValue(exportColsMap.get(colName));
			}
			// 再写值
			for(int i=0;i<=rowCount;++i)
			{
				row = sheet.createRow(i+1);
				colIdx = 0;
				for(String colName : exportColsMap.keySet())
				{
					HSSFCell cell = row.createCell(colIdx++);
					Serializable value = dataSet.getValue(i, colName);
					if(value!=null)
					{
						if(colSupportValueMap!=null&&colSupportValueMap.containsKey(colName))
						{
							LinkedHashMap<Serializable, String> supportValueMap = colSupportValueMap.get(colName);
							if(supportValueMap.containsKey(value))
							{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(supportValueMap.get(value));
								continue;
							}
						}
						String javaClass = dataSet.getColumn(colName).getJavaClass();
						if(byte[].class.getName().equals(javaClass))
						{
							setImageToCell(workbook, cell, (byte[])value);
						}
						else
						{
							Class<?> colClass = XlsysClassLoader.getInstance().loadClass(javaClass);
							if(String.class.isAssignableFrom(colClass))
							{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(ObjectUtil.objectToString(value));
							}
							else if(Number.class.isAssignableFrom(colClass))
							{
								cell.setCellType(Cell.CELL_TYPE_NUMERIC);
								cell.setCellValue(ObjectUtil.objectToDouble(value));
							}
							else if(Date.class.isAssignableFrom(colClass))
							{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(ObjectUtil.objectToString(value));
							}
						}
					}
				}
			}
			workbook.write(os);
		}
	}
	
	private static void setImageToCell(HSSFWorkbook workbook, HSSFCell cell, byte[] imageBytes) throws Exception
	{
		// 返回值为图像, 直接生成图像到当前格中
		HSSFPatriarch patriarch = cell.getSheet().createDrawingPatriarch();
		ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = null;
		try
		{
			// 获取图片的大小
			bais = new ByteArrayInputStream((byte[])imageBytes);
			BufferedImage image = ImageIO.read(bais);
			Graphics graphics = image.getGraphics();
			int width = image.getWidth();
			int height = image.getHeight();
			// 获取放置图片的区域大小, 以及区域内所有单元格的参数
			int rangeWidth = 0;
			int rangeHeight = 0;
			int colHeight = cell.getRow().getHeight();
			// 转成像素
			rangeHeight = new Double(((double)colHeight)/20/72*96).intValue();
			HSSFCellStyle style = cell.getSheet().getColumnStyle(cell.getColumnIndex());
			HSSFFont font = null;
			if(style!=null) font = style.getFont(workbook);
			if(font==null) font = workbook.getFontAt((short) 0);
			String fontName = font.getFontName();
			int fontSize = font.getFontHeightInPoints();
			Font awtFont = new Font(fontName, Font.PLAIN, fontSize);
			FontMetrics fm = graphics.getFontMetrics(awtFont);
			int charWidth = fm.charWidth('a');
			int charCount = cell.getSheet().getColumnWidth(cell.getColumnIndex())/256;
			// 转成像素
			rangeWidth = charCount*(charWidth+2);
			// 获取合适的图片大小
			int[] canSize = ImageUtil.getScaleSize(width, height, rangeWidth, rangeHeight);
			// 计算起始点
			int beginX = (rangeWidth-canSize[0])/2;
			int beginY = (rangeHeight-canSize[1])/2;
			int endX = rangeWidth - beginX;
			int endY = rangeHeight - beginY;
			int x1 = beginX;
			int y1 = beginY;
			int x2 = endX;
			int y2 = endY;
			x1 = new Double(((double)x1)/rangeWidth*1024).intValue();
			x1 = x1>1023?1023:x1;
			y1 = new Double(((double)y1)/rangeHeight*256).intValue();
			y1 = y1>255?255:y1;
			x2 = new Double(((double)x2)/rangeWidth*1024).intValue();
			x2 = x2>1023?1023:x2;
			y2 = new Double(((double)y2)/rangeHeight*256).intValue();
			y2 = y2>255?255:y2;
			// 放置图片
			HSSFClientAnchor anchor = new HSSFClientAnchor(x1,y1,x2,y2,(short)cell.getColumnIndex(),cell.getRowIndex(),(short)cell.getColumnIndex(),cell.getRowIndex());
			// HSSFClientAnchor anchor = new HSSFClientAnchor(0,0,1023,255,beginCol,beginRow,endCol,endRow);
			// 将图片转为jpg
			baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			patriarch.createPicture(anchor, cell.getSheet().getWorkbook().addPicture(baos.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(bais);
			IOUtil.close(baos);
		}
	}

	public static void main(String[] args) throws Exception
	{
		//IDataSet dataSet = getDataSetFromExcel(new File("d:/141224 人事档案管理需求模3722487530684401859.xls"), 0, true);
		IDataSet dataSet = getDataSetFromExcel(new File("d:/141224 人事档案管理需求模3722487530684401859.xls"), "2014年三九军团", true, true, true);
		DataSetUtil.dumpData(dataSet);
	}
}
