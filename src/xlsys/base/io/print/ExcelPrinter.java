package xlsys.base.io.print;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.script.ScriptException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.dom4j.DocumentException;

import xlsys.base.exception.UnsupportedException;
import xlsys.base.image.ImageUtil;
import xlsys.base.io.print.internal.CellPoint;
import xlsys.base.io.print.internal.ExcelColumn;
import xlsys.base.io.print.internal.ExcelCreater;
import xlsys.base.io.print.internal.SheetEntity;
import xlsys.base.io.util.ExcelUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.io.xml.util.XmlUtil;
import xlsys.base.log.LogUtil;
import xlsys.base.util.ObjectUtil;
import xlsys.base.util.StringUtil;

public class ExcelPrinter extends Printer
{
	/**
	 * sheet页循环类型:一次
	 */
	public final static int LOOP_SHEET_TYPE_ONCE = 1;
	/**
	 * sheet页循环类型:全循环
	 */
	public final static int LOOP_SHEET_TYPE_ALL = 2;
	/**
	 * 脚本sheet页名称
	 */
	public final static String SCRIPT_SHEET_NAME = "_xs";
	/**
	 * 脚本开始tag
	 */
	public final static String SCRIPT_BEGIN_TAG = "<xs";
	/**
	 * 脚本结束tag
	 */
	public final static String SCRIPT_END_TAG = "</xs>";
	/**
	 * 脚本的loopType属性名称
	 */
	public final static String SCRIPT_ATTR_LOOP_TYPE = "loopType";
	/**
	 * 脚本的loopType属性值:行
	 */
	public final static String LOOP_TYPE_ROW = "row";
	/**
	 * 脚本的loopType属性值:列
	 */
	public final static String LOOP_TYPE_COL = "col";
	
	private HSSFWorkbook workbook;
	private boolean hasCreated;
	private ExcelCreater creater;

	public ExcelPrinter(byte[] bytes)
	{
		super(bytes);
	}

	public ExcelPrinter(File file)
	{
		super(file);
	}

	public ExcelPrinter(InputStream is)
	{
		super(is);
	}

	public ExcelPrinter(String filePath)
	{
		super(filePath);
	}
	
	@Override
	protected void read(InputStream stream) throws Exception
	{
		workbook = new HSSFWorkbook(new POIFSFileSystem(stream));
	}

	@Override
	public synchronized void print(OutputStream stream) throws Exception
	{
		boolean success = false;
		if(!hasCreated) success = create();
		else success = true;
		if(success) workbook.write(stream);
	}
	
	@Override
	public String getSuffix()
	{
		return "xls";
	}
	
	public HSSFWorkbook getWorkbook()
	{
		return workbook;
	}

	/**
	 * 根据Excel打印模板生成对应的打印内容
	 */
	private boolean create() throws Exception
	{
		creater = new ExcelCreater(this);
		// 执行初始化方法
		boolean canRun = creater.begin();
		if(!canRun) return canRun;
		// 获取所有的sheet名称
		int sheetNum = workbook.getNumberOfSheets();
		String[] sheetNames = new String[sheetNum];
		for(int i=0;i<sheetNum;i++)
		{
			sheetNames[i] = workbook.getSheetName(i);
		}
		List<String[]> sheetNameGroupList = creater.getSheetLoopGroup(sheetNames);
		// 初始化所有sheet组，并建立Map<sheetName,sheetGroup>映射.
		List<SheetGroup> sheetGroupList = new ArrayList<SheetGroup>();
		Map<String, SheetGroup> sheetGroupMap = new HashMap<String, SheetGroup>();
		if(sheetNameGroupList!=null)
		{
			for(String[] sheetNamesGroup : sheetNameGroupList)
			{
				SheetGroup group = new SheetGroup();
				group.sheetNames = sheetNamesGroup;
				// 查询该组的循环类型
				group.loopType = creater.getSheetGroupLoopType(sheetNamesGroup);
				// 查询该组的循环次数
				group.loopCount = creater.getSheetGroupLoopCount(sheetNamesGroup);
				// 查询该组的循环变量
				group.loopVar = creater.getSheetGroupLoopVar(sheetNamesGroup);
				putVar(group.loopVar, 0);
				HSSFSheet[] srcSheets = new HSSFSheet[sheetNamesGroup.length];
				for(int i=0;i<sheetNamesGroup.length;i++)
				{
					srcSheets[i] = workbook.getSheet(sheetNamesGroup[i]);
					sheetGroupMap.put(sheetNamesGroup[i], group);
				}
				group.srcSheets = srcSheets;
				sheetGroupList.add(group);
			}
		}
		// 开始执行sheet循环
		Set<String> finishSheet = new HashSet<String>(); // 处理完成的sheet
		while(finishSheet.size()!=sheetNames.length)
		{
			Set<String> curTimeFinishSheet = new HashSet<String>(); // 在本次循环中处理完成的sheet
			for(String sheetName : sheetNames)
			{
				if(finishSheet.contains(sheetName)||curTimeFinishSheet.contains(sheetName)) continue;
				// 获取当前要循环的sheetGroup
				SheetGroup sheetGroup = sheetGroupMap.get(sheetName);
				if(sheetGroup==null)
				{
					// 直接运行sheet,运行一次即可
					HSSFSheet sheet = workbook.getSheet(sheetName);
					generateSheet(sheet, sheetName);
					finishSheet.add(sheetName);
					curTimeFinishSheet.add(sheetName);
				}
				else
				{
					// 如果存在sheetGroup, 则循环sheetGroup
					int groupLoopCursor = (int) getVar(sheetGroup.loopVar);
					if(sheetGroup.loopType==LOOP_SHEET_TYPE_ONCE)
					{
						// 循环一次
						for(HSSFSheet srcSheet : sheetGroup.srcSheets)
						{
							String curSheetName = workbook.getSheetName(workbook.getSheetIndex(srcSheet));
							HSSFSheet sheet = workbook.cloneSheet(workbook.getSheetIndex(srcSheet));
							String newSheetName = creater.renameSheet(curSheetName, groupLoopCursor);
							workbook.setSheetName(workbook.getSheetIndex(sheet), newSheetName);
							generateSheet(sheet, curSheetName, groupLoopCursor);
							curTimeFinishSheet.add(curSheetName);
						}
						putVar(sheetGroup.loopVar, ++groupLoopCursor);
					}
					else if(sheetGroup.loopType==LOOP_SHEET_TYPE_ALL)
					{
						// 全循环
						for(;groupLoopCursor<sheetGroup.loopCount;groupLoopCursor++)
						{
							putVar(sheetGroup.loopVar, groupLoopCursor);
							for(HSSFSheet srcSheet : sheetGroup.srcSheets)
							{
								String curSheetName = workbook.getSheetName(workbook.getSheetIndex(srcSheet));
								HSSFSheet sheet = workbook.cloneSheet(workbook.getSheetIndex(srcSheet));
								String newSheetName = creater.renameSheet(curSheetName, groupLoopCursor);
								workbook.setSheetName(workbook.getSheetIndex(sheet), newSheetName);
								generateSheet(sheet, curSheetName, groupLoopCursor);
							}
						}
					}
					if(groupLoopCursor>=sheetGroup.loopCount)
					{
						for(String temp : sheetGroup.sheetNames) finishSheet.add(temp);
					}
				}
			}
		}
		// 循环完毕，将原始的sheet组的模板sheet删除
		for(SheetGroup group : sheetGroupList)
		{
			for(HSSFSheet sheet : group.srcSheets)
			{
				try
				{
					int sheetIndex = workbook.getSheetIndex(sheet);
					if(sheetIndex>=0)
					{
						if(creater.removeTemplateSheet(sheet.getSheetName()))
						{
							workbook.removeSheetAt(sheetIndex);
						}
						else
						{
							workbook.setSheetHidden(sheetIndex, Workbook.SHEET_STATE_HIDDEN);
						}
					}
				}
				catch(Exception e)
				{
					LogUtil.printlnWarn(e);
				}
			}
		}
		creater.end();
		return true;
	}
	
	private void generateSheet(HSSFSheet sheet, String srcSheetName) throws Exception
	{
		generateSheet(sheet, srcSheetName, -1);
	}

	private void generateSheet(HSSFSheet sheet, String srcSheetName, int sheetLoopCursor) throws Exception
	{
		boolean needLoopRow = creater.isNeedLoopRow(srcSheetName);
		boolean needLoopCol = creater.isNeedLoopCol(srcSheetName);
		// 获取所有的合并区域
		List<CellRangeAddress> rangeAddressList = new ArrayList<CellRangeAddress>(); // 所有的合并区域
		Set<CellRangeAddress> toDeleteRangeAddressSet = new HashSet<CellRangeAddress>(); // 已经在生成时处理完成合并区域
		Set<CellRangeAddress> newRangeSet = new HashSet<CellRangeAddress>(); // 在处理行或列时产生的新区域
		Map<Integer, Integer> rowContrastMap = new HashMap<Integer, Integer>(); // 原始行号和当前行号之前的对照Map,只包含非循环行
		Map<Integer, Integer> colContrastMap = new HashMap<Integer, Integer>(); // 原始列号和当前列号之前的对照Map,只包含非循环列
		int mergedRegionCount = sheet.getNumMergedRegions();
		for(int i=0;i<mergedRegionCount;i++)
		{
			rangeAddressList.add(sheet.getMergedRegion(i));
		}
		// 清除所有的合并区域
		for(int i=0;i<mergedRegionCount;i++)
		{
			sheet.removeMergedRegion(i);
		}
		if(creater.isLoopRowFirst(srcSheetName))
		{
			if(needLoopRow)
			{
				// 开始生成各行
				buildRows(sheet, srcSheetName, sheetLoopCursor, rangeAddressList, toDeleteRangeAddressSet, newRangeSet, rowContrastMap);
			}
			if(needLoopCol)
			{
				// 开始生成各列
				buildCols(sheet, srcSheetName, sheetLoopCursor, rangeAddressList, toDeleteRangeAddressSet, newRangeSet, colContrastMap);
			}
		}
		else
		{
			if(needLoopCol)
			{
				// 开始生成各列
				buildCols(sheet, srcSheetName, sheetLoopCursor, rangeAddressList, toDeleteRangeAddressSet, newRangeSet, colContrastMap);
			}
			if(needLoopRow)
			{
				// 开始生成各行
				buildRows(sheet, srcSheetName, sheetLoopCursor, rangeAddressList, toDeleteRangeAddressSet, newRangeSet, rowContrastMap);
			}
		}
		// 设置剩余还未处理的合并区域
		rangeAddressList.removeAll(toDeleteRangeAddressSet);
		setMergedRegion(sheet, rangeAddressList, rowContrastMap, colContrastMap);
	}
	
	private void setMergedRegion(HSSFSheet sheet, List<CellRangeAddress> rangeAddressList, Map<Integer, Integer> rowContrastMap, Map<Integer, Integer> colContrastMap)
	{
		for(CellRangeAddress rangeAddress: rangeAddressList)
		{
			int firstCol = rangeAddress.getFirstColumn();
			int firstRow = rangeAddress.getFirstRow();
			int lastCol = rangeAddress.getLastColumn();
			int lastRow = rangeAddress.getLastRow();
			if(rowContrastMap.containsKey(firstRow)) firstRow = rowContrastMap.get(firstRow);
			if(rowContrastMap.containsKey(lastRow)) lastRow = rowContrastMap.get(lastRow);
			if(colContrastMap.containsKey(firstCol)) firstCol = colContrastMap.get(firstCol);
			if(colContrastMap.containsKey(lastCol)) lastCol = colContrastMap.get(lastCol);
			rangeAddress.setFirstColumn(firstCol);
			rangeAddress.setFirstRow(firstRow);
			rangeAddress.setLastColumn(lastCol);
			rangeAddress.setLastRow(lastRow);
			sheet.addMergedRegion(rangeAddress);
		}
	}
	
	private List<CellRangeAddress> getRowContrastCellRanges(List<CellRangeAddress> rangeAddressList, Map<Integer, Integer> rowContrastMap, int rowIndex)
	{
		List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
		for(CellRangeAddress rangeAddress: rangeAddressList)
		{
			int firstCol = rangeAddress.getFirstColumn();
			int firstRow = rangeAddress.getFirstRow();
			int lastCol = rangeAddress.getLastColumn();
			int lastRow = rangeAddress.getLastRow();
			CellRangeAddress newRange = rangeAddress.copy();
			if(rowContrastMap!=null)
			{
				if(rowContrastMap.containsKey(firstRow))
				{
					int temp = rowContrastMap.get(firstRow);
					lastRow = temp + lastRow - firstRow;
					firstRow = temp;
				}
				else if(rowContrastMap.containsKey(lastRow)) lastRow = rowContrastMap.get(lastRow);
			}
			newRange.setFirstColumn(firstCol);
			newRange.setFirstRow(firstRow);
			newRange.setLastColumn(lastCol);
			newRange.setLastRow(lastRow);
			list.add(newRange);
		}
		return list;
	}
	
	private List<CellRangeAddress> getColContrastCellRanges(List<CellRangeAddress> rangeAddressList, Map<Integer, Integer> colContrastMap, int colIndex)
	{
		List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
		for(CellRangeAddress rangeAddress: rangeAddressList)
		{
			int firstCol = rangeAddress.getFirstColumn();
			int firstRow = rangeAddress.getFirstRow();
			int lastCol = rangeAddress.getLastColumn();
			int lastRow = rangeAddress.getLastRow();
			CellRangeAddress newRange = rangeAddress.copy();
			if(colContrastMap!=null)
			{
				if(colContrastMap.containsKey(firstCol))
				{
					int temp = colContrastMap.get(firstCol);
					lastCol = temp + lastCol - firstCol;
					firstCol = temp;
				}
				else if(colContrastMap.containsKey(lastCol)) lastCol = colContrastMap.get(lastCol);
			}
			newRange.setFirstColumn(firstCol);
			newRange.setFirstRow(firstRow);
			newRange.setLastColumn(lastCol);
			newRange.setLastRow(lastRow);
			list.add(newRange);
		}
		return list;
	}

	private Map<HSSFRow, RowGroup> initSheetRow(HSSFSheet sheet, String srcSheetName, int sheetLoopCursor) throws ScriptException, UnsupportedException
	{
		// 初始化需要循环的行
		List<Integer[]> rowLoopGroupList = creater.getRowLoopGroups(srcSheetName);
		Map<HSSFRow, RowGroup> rowGroupMap = new HashMap<HSSFRow, RowGroup>();
		if(rowLoopGroupList!=null)
		{
			for(Integer[] rowNumsGroup : rowLoopGroupList)
			{
				RowGroup rowGroup = new RowGroup();
				HSSFRow[] rows = new HSSFRow[rowNumsGroup.length];
				for(int i=0;i<rowNumsGroup.length;i++)
				{
					rows[i] = sheet.getRow(rowNumsGroup[i]);
					rowGroupMap.put(rows[i], rowGroup);
				}
				rowGroup.rowNums = rowNumsGroup;
				rowGroup.rows = rows;
				rowGroup.loopCount = creater.getRowGroupLoopCount(srcSheetName, sheetLoopCursor, rowNumsGroup);
				rowGroup.loopVar = creater.getRowGroupLoopVar(srcSheetName, sheetLoopCursor, rowNumsGroup);
				putVar(rowGroup.loopVar, 0);
			}
		}
		return rowGroupMap;
	}
	
	private Object[] initSheetCol(HSSFSheet sheet, String srcSheetName, int sheetLoopCursor) throws ScriptException, UnsupportedException
	{
		// 初始化需要循环的列
		List<Integer[]> colLoopGroupList = creater.getColLoopGroups(srcSheetName);
		Map<ExcelColumn, ColGroup> colGroupMap = new HashMap<ExcelColumn, ColGroup>();
		SheetEntity sheetEntity = new SheetEntity(sheet);
		if(colLoopGroupList!=null)
		{
			for(Integer[] colNumsGroup : colLoopGroupList)
			{
				ColGroup colGroup = new ColGroup();
				ExcelColumn[] cols = new ExcelColumn[colNumsGroup.length];
				for(int i=0;i<colNumsGroup.length;i++)
				{
					cols[i] = sheetEntity.getColumn(colNumsGroup[i]);
					colGroupMap.put(cols[i], colGroup);
				}
				colGroup.colNums = colNumsGroup;
				colGroup.cols = cols;
				colGroup.loopCount = creater.getColGroupLoopCount(srcSheetName, sheetLoopCursor, colNumsGroup);
				colGroup.loopVar = creater.getColGroupLoopVar(srcSheetName, sheetLoopCursor, colNumsGroup);
				putVar(colGroup.loopVar, 0);
			}
		}
		return new Object[]{colGroupMap, sheetEntity};
	}
	
	private void buildCols(HSSFSheet sheet, String srcSheetName, int sheetLoopCursor, List<CellRangeAddress> rangeAddressList, Set<CellRangeAddress> toDeleteRangeAddressSet, Set<CellRangeAddress> newRangeSet, Map<Integer, Integer> colContrastMap) throws Exception
	{
		Object[] arr = initSheetCol(sheet, srcSheetName, sheetLoopCursor);
		Set<CellRangeAddress> tempNewRangeSet = new HashSet<CellRangeAddress>();
		Map<Integer, ColGroup> colGroupMap = (Map<Integer, ColGroup>) arr[0];
		SheetEntity sheetEntity = (SheetEntity) arr[1];
		// 对所有的列进行循环，开始生成sheet内容
		int colCount = sheetEntity.getColCount();
		Set<ExcelColumn> finishedCol = new HashSet<ExcelColumn>(); // 记录已经生成完成的列
		int colCursor = 0; // 记录当前的列游标位置
		for(int i=0;i<colCount;i++)
		{
			ExcelColumn srcCol = sheetEntity.getColumn(i);
			if(finishedCol.contains(srcCol)) continue;
			ColGroup colGroup = colGroupMap.get(srcCol);
			if(colGroup==null)
			{
				colContrastMap.put(i, colCursor);
				// 不需要循环，一次性计算的列
				ExcelUtil.createColumn(sheet, colCursor);
				cloneCol(sheet, srcCol, colCursor);
				List<CellRangeAddress> contrastRange = getColContrastCellRanges(rangeAddressList, colContrastMap, colCursor);
				generateCol(sheet, colCursor, contrastRange);
				if(srcCol!=null) finishedCol.add(srcCol);
				++colCursor;
			}
			else
			{
				// 获取当前循环列中包含的区域
				Set<CellRangeAddress> relaveRanges = ExcelUtil.getColRelativeRange(rangeAddressList, newRangeSet, colGroup.colNums);
				toDeleteRangeAddressSet.addAll(relaveRanges);
				// 需要循环的列
				int colLoopCursor = (int) getVar(colGroup.loopVar);
				for(;colLoopCursor<colGroup.loopCount;colLoopCursor++)
				{
					int curBeginColIdx = colCursor;
					putVar(colGroup.loopVar, colLoopCursor);
					// 设置循环区域中的合并区域
					List<CellRangeAddress> newCellRangeList = new ArrayList<CellRangeAddress>();
					for(CellRangeAddress range : relaveRanges)
					{
						int firstCol = range.getFirstColumn();
						int lastCol = range.getLastColumn();
						firstCol = curBeginColIdx-(i-firstCol);
						lastCol = curBeginColIdx+(lastCol-i);
						CellRangeAddress newRange = range.copy();
						newRange.setFirstColumn(firstCol);
						newRange.setLastColumn(lastCol);
						newCellRangeList.add(newRange);
					}
					for(ExcelColumn curSrcCol : colGroup.cols)
					{
						ExcelUtil.createColumn(sheet, colCursor);
						cloneCol(sheet, curSrcCol, colCursor);
						generateCol(sheet, colCursor, newCellRangeList);
						++colCursor;
					}
					for(CellRangeAddress newRange : newCellRangeList)
					{
						sheet.addMergedRegion(newRange);
						if(!ExcelUtil.containsRange(rangeAddressList, newRange)) tempNewRangeSet.add(newRange);
					}
				}
				for(ExcelColumn curSrcCol : colGroup.cols)
				{
					if(curSrcCol!=null) finishedCol.add(curSrcCol);
				}
			}
		}
		newRangeSet.addAll(tempNewRangeSet);
	}
	
	private void generateCol(HSSFSheet sheet, int colIdx, List<CellRangeAddress> cellRangeList) throws Exception
	{
		int rowCount = ExcelUtil.getRowCountOfSheet(sheet);
		// 运行每个单元格中的Js脚本
		for(int i=0;i<rowCount;i++)
		{
			HSSFRow row = sheet.getRow(i);
			if(row!=null)
			{
				HSSFCell cell = row.getCell(colIdx);
				if(cell==null) continue;
				generateCell(cell, LOOP_TYPE_COL, cellRangeList);
			}
		}
	}
	
	private void cloneCol(HSSFSheet sheet, ExcelColumn srcCol, int newColIdx)
	{
		int rowCount = srcCol.getLastCellNum();
		for(int i=0;i<rowCount;i++)
		{
			HSSFCell srcCell = srcCol.getCell(i);
			if(srcCell!=null)
			{
				HSSFRow targetRow = sheet.getRow(i);
				if(targetRow==null) targetRow = sheet.createRow(i);
				HSSFCell newCell = targetRow.getCell(newColIdx);
				if(newCell==null) newCell = targetRow.createCell(newColIdx);
				sheet.setColumnWidth(newColIdx, srcCol.getColumnWidth());
				ExcelUtil.cloneCell(workbook, srcCell, newCell);
			}
		}
	}
	
	private void buildRows(HSSFSheet sheet, String srcSheetName, int sheetLoopCursor, List<CellRangeAddress> rangeAddressList, Set<CellRangeAddress> toDeleteRangeAddressSet, Set<CellRangeAddress> newRangeSet, Map<Integer, Integer> rowContrastMap) throws Exception
	{
		Map<HSSFRow, RowGroup> rowGroupMap = initSheetRow(sheet, srcSheetName, sheetLoopCursor);
		Set<CellRangeAddress> tempNewRangeSet = new HashSet<CellRangeAddress>();
		// 先记录所有的原始行
		List<HSSFRow> allRows = new ArrayList<HSSFRow>();
		int rowCount = ExcelUtil.getRowCountOfSheet(sheet);
		for(int i=0;i<rowCount;i++)
		{
			allRows.add(sheet.getRow(i));
		}
		// 对所有的行进行循环，开始生成sheet内容
		Set<HSSFRow> finishedRow = new HashSet<HSSFRow>(); // 记录已经生成完成的行
		int rowCursor = 0; // 记录当前的行游标位置
		for(int i=0;i<rowCount;i++)
		{
			HSSFRow srcRow = allRows.get(i);
			if(finishedRow.contains(srcRow)) continue;
			RowGroup rowGroup = rowGroupMap.get(srcRow);
			if(rowGroup==null)
			{
				rowContrastMap.put(i, rowCursor);
				// 不需要循环，一次性计算的行
				HSSFRow newRow = sheet.createRow(rowCursor);
				cloneRow(srcRow, newRow);
				List<CellRangeAddress> contrastRange = getRowContrastCellRanges(rangeAddressList, rowContrastMap, rowCursor);
				generateRow(newRow, contrastRange);
				if(srcRow!=null) finishedRow.add(srcRow);
				++rowCursor;
			}
			else
			{
				// 获取当前循环行中包含的区域
				Set<CellRangeAddress> relaveRanges = ExcelUtil.getRowRelativeRange(rangeAddressList, newRangeSet, rowGroup.rowNums);
				toDeleteRangeAddressSet.addAll(relaveRanges);
				// 先删除原始的行
				for(HSSFRow curSrcRow : rowGroup.rows)
				{
					if(curSrcRow!=null&&sheet.getRow(curSrcRow.getRowNum())==curSrcRow)
					{
						sheet.removeRow(curSrcRow);
					}
				}
				// 需要循环的行
				int rowLoopCursor = (int) getVar(rowGroup.loopVar);
				for(;rowLoopCursor<rowGroup.loopCount;rowLoopCursor++)
				{
					int curBeginRowIdx = rowCursor;
					putVar(rowGroup.loopVar, rowLoopCursor);
					// 设置循环区域中的合并区域
					List<CellRangeAddress> newCellRangeList = new ArrayList<CellRangeAddress>();
					for(CellRangeAddress range : relaveRanges)
					{
						int firstRow = range.getFirstRow();
						int lastRow = range.getLastRow();
						firstRow = curBeginRowIdx-(i-firstRow);
						lastRow = curBeginRowIdx+(lastRow-i);
						CellRangeAddress newRange = range.copy();
						newRange.setFirstRow(firstRow);
						newRange.setLastRow(lastRow);
						newCellRangeList.add(newRange);
					}
					for(int j=0;j<rowGroup.rows.length;j++)
					{
						HSSFRow curSrcRow = rowGroup.rows[j];
						HSSFRow newRow = sheet.createRow(rowCursor);
						cloneRow(curSrcRow, newRow);
						generateRow(newRow, newCellRangeList);
						++rowCursor;
					}
					for(CellRangeAddress newRange : newCellRangeList)
					{
						sheet.addMergedRegion(newRange);
						if(!ExcelUtil.containsRange(rangeAddressList, newRange)) tempNewRangeSet.add(newRange);
					}
				}
				for(HSSFRow curSrcRow : rowGroup.rows)
				{
					if(curSrcRow!=null) finishedRow.add(curSrcRow);
				}
			}
		}
		newRangeSet.addAll(tempNewRangeSet);
	}
	
	private void generateRow(HSSFRow row, List<CellRangeAddress> cellRangeList) throws Exception
	{
		// 运行每个单元格中的Js脚本
		if(row==null) return;
		int minColIdx = row.getFirstCellNum();
		int maxColIdx = row.getLastCellNum();
		for(int i=minColIdx;i<maxColIdx;i++)
		{
			HSSFCell cell = row.getCell(i);
			if(cell==null) continue;
			generateCell(cell, LOOP_TYPE_ROW, cellRangeList);
		}
	}
	
	private void cloneRow(HSSFRow srcRow, HSSFRow newRow)
	{
		if(srcRow==null) return;
		newRow.setHeight(srcRow.getHeight());
		int minColIdx = srcRow.getFirstCellNum();
		int maxColIdx = srcRow.getLastCellNum();
		for(int i=minColIdx;i<maxColIdx;i++)
		{
			HSSFCell srcCell = srcRow.getCell(i);
			HSSFCell newCell = newRow.createCell(i);
			ExcelUtil.cloneCell(workbook, srcCell, newCell);
		}
	}
	
	private void generateCell(HSSFCell cell, String curloopType, List<CellRangeAddress> cellRangeList) throws Exception
	{
		StringBuffer sb = new StringBuffer();
		String cellStr = null;
		if(cell!=null)
		{
			try
			{
				cellStr = cell.getStringCellValue();
			}
			catch(Exception e){}
			if(cellStr!=null&&cellStr.contains(SCRIPT_BEGIN_TAG))
			{
				String[] strArr = StringUtil.split(cellStr, SCRIPT_BEGIN_TAG, SCRIPT_END_TAG);
				Object lastResult = null;
				for(String str : strArr)
				{
					if(str.startsWith(SCRIPT_BEGIN_TAG)&&str.endsWith(SCRIPT_END_TAG))
					{
						// 不使用xml方式来解析原始字符串，这样就可以正常的使用所有符号
						String script = "";
						int beginTagEndIndex = str.indexOf('>');
						String xmlStr = null;
						if(beginTagEndIndex!=-1)
						{
							script = str.substring(beginTagEndIndex+1, str.length()-SCRIPT_END_TAG.length());
							xmlStr = str.substring(0, beginTagEndIndex+1)+SCRIPT_END_TAG;
						}
						// JS公式
						ByteArrayInputStream bais = null;
						XmlModel jsXm = null;
						try
						{
							bais = new ByteArrayInputStream(xmlStr.getBytes());
							jsXm = XmlUtil.readXml(bais);
						}
						catch(DocumentException e)
						{
							throw e;
						}
						finally
						{
							IOUtil.close(bais);
						}
						String loopType = jsXm.getAttributeValue(SCRIPT_ATTR_LOOP_TYPE);
						if(loopType==null||curloopType.equals(loopType))
						{
							// 执行行循环代码
							getXlsysScript().setScript(script);
							Object result = null;
							try
							{
								result = getXlsysScript().invoke();
							}
							catch(Exception e)
							{
								LogUtil.printlnError("Script invoke error: \n" + script);
								throw e;
							}
							if(result instanceof byte[])
							{
								// 返回值为图像, 直接生成图像到当前格中
								HSSFPatriarch patriarch = cell.getSheet().createDrawingPatriarch();
								int beginRow = cell.getRowIndex();
								int endRow = cell.getRowIndex();
								short beginCol = (short) cell.getColumnIndex();
								short endCol = (short) cell.getColumnIndex();
								if(cellRangeList!=null)
								{
									// 判断当前图像是否在合并单元格内
									for(CellRangeAddress cellRange : cellRangeList)
									{
										int firstRow = cellRange.getFirstRow();
										int lastRow = cellRange.getLastRow();
										int firstCol = cellRange.getFirstColumn();
										int lastCol = cellRange.getLastColumn();
										if(beginRow>=firstRow&&beginRow<=lastRow&&beginCol>=firstCol&&beginCol<=lastCol)
										{
											// 重置图片区域
											beginRow = firstRow;
											endRow = lastRow;
											beginCol = (short) firstCol;
											endCol = (short) lastCol;
											break;
										}
									}
								}
								ByteArrayOutputStream baos = null;
								try
								{
									// 获取图片的大小
									bais = new ByteArrayInputStream((byte[])result);
									BufferedImage image = ImageIO.read(bais);
									Graphics graphics = image.getGraphics();
									int width = image.getWidth();
									int height = image.getHeight();
									// 获取放置图片的区域大小, 以及区域内所有单元格的参数
									int rangeWidth = 0;
									int rangeHeight = 0;
									List<List<CellPoint>> cellPoints = new ArrayList<List<CellPoint>>();
									for(int i=beginRow;i<=endRow;++i)
									{
										
										List<CellPoint> cpRow = new ArrayList<CellPoint>();
										cellPoints.add(cpRow);
										CellPoint cp = new CellPoint();
										cpRow.add(cp);
										cp.row = i;
										cp.offY = rangeHeight;
										
										HSSFRow tempRow = cell.getSheet().getRow(i);
										int colHeight = cell.getSheet().getDefaultRowHeight();
										if(tempRow!=null) colHeight = tempRow.getHeight();
										// 转成像素
										colHeight = new Double(((double)colHeight)/20/72*96).intValue();
										rangeHeight += colHeight;
										
										cp.height = colHeight;
										
									}
									for(int i=beginCol;i<=endCol;++i)
									{
										for(int j=beginRow;j<=endRow;++j)
										{
											List<CellPoint> cpRow = cellPoints.get(j-beginRow);
											if(cpRow.size()<=i-beginCol)
											{
												cpRow.add(cpRow.get(0).clone());
											}
											CellPoint cp = cpRow.get(i-beginCol);
											cp.col = i;
											cp.offX = rangeWidth;
										}
										HSSFCellStyle tempStyle = cell.getSheet().getColumnStyle(i);
										HSSFFont font = null;
										if(tempStyle!=null) font = tempStyle.getFont(workbook);
										if(font==null) font = workbook.getFontAt((short) 0);
										String fontName = font.getFontName();
										int fontSize = font.getFontHeightInPoints();
										Font awtFont = new Font(fontName, Font.PLAIN, fontSize);
										FontMetrics fm = graphics.getFontMetrics(awtFont);
										int charWidth = fm.charWidth('a');
										int charCount = cell.getSheet().getColumnWidth(i)/256;
										// 转成像素
										int columnWidth = charCount*(charWidth+2);
										rangeWidth += columnWidth;
										
										for(int j=beginRow;j<=endRow;++j)
										{
											List<CellPoint> cpRow = cellPoints.get(j-beginRow);
											CellPoint cp = cpRow.get(i-beginCol);
											cp.width = columnWidth;
										}
									}

									// 获取合适的图片大小
									int[] canSize = ImageUtil.getScaleSize(width, height, rangeWidth, rangeHeight);
									// 计算起始点
									int beginX = (rangeWidth-canSize[0])/2;
									int beginY = (rangeHeight-canSize[1])/2;
									int endX = rangeWidth - beginX;
									int endY = rangeHeight - beginY;
									// 查找起始和结束点的所在单元格
									CellPoint beginCell = null;
									CellPoint endCell = null;
									for(int i=0;i<cellPoints.size();++i)
									{
										List<CellPoint> cpRow = cellPoints.get(i);
										for(CellPoint temp : cpRow)
										{
											if(beginX>=temp.offX&&beginX<=temp.offX+temp.width&&beginY>=temp.offY&&beginY<=temp.offY+temp.height)
											{
												beginCell = temp;
											}
											if(endX>=temp.offX&&endX<=temp.offX+temp.width&&endY>=temp.offY&&endY<=temp.offY+temp.height)
											{
												endCell = temp;
											}
										}
									}
									int x1 = beginX - beginCell.offX;
									int y1 = beginY - beginCell.offY;
									int x2 = endX - endCell.offX;
									int y2 = endY - endCell.offY;
									x1 = new Double(((double)x1)/beginCell.width*1024).intValue();
									x1 = x1>1023?1023:x1;
									y1 = new Double(((double)y1)/beginCell.height*256).intValue();
									y1 = y1>255?255:y1;
									x2 = new Double(((double)x2)/endCell.width*1024).intValue();
									x2 = x2>1023?1023:x2;
									y2 = new Double(((double)y2)/endCell.height*256).intValue();
									y2 = y2>255?255:y2;
									// 放置图片
									HSSFClientAnchor anchor = new HSSFClientAnchor(x1,y1,x2,y2,(short)beginCell.col,beginCell.row,(short)endCell.col,endCell.row);
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
							else sb.append(ObjectUtil.objectToString(result));
							lastResult = result;
						}
						else
						{
							// 非行循环代码，执行附加即可
							sb.append(str);
						}
					}
					else sb.append(str);
				}
				boolean hasSet = false;
				if(sb.toString().equals(ObjectUtil.objectToString(lastResult)))
				{
					// 根据返回值类型直接确定单元格类型
					hasSet = true;
					if(lastResult instanceof Number) cell.setCellValue(ObjectUtil.objectToDouble(lastResult));
					else if(lastResult instanceof Date) cell.setCellValue(ObjectUtil.objectToDate(lastResult));
					else if(lastResult instanceof Boolean) cell.setCellValue(ObjectUtil.objectToBoolean(lastResult));
					else hasSet = false;
				}
				if(!hasSet)
				{
					String str = sb.toString();
					if(!str.isEmpty()&&str.charAt(0)=='=') cell.setCellFormula(str);
					else cell.setCellValue(str);
				}
			}
		}
	}
	
	private class ColGroup
	{
		public Integer[] colNums;
		public ExcelColumn[] cols;
		public int loopCount;
		public String loopVar;
	}
	
	private class RowGroup
	{
		public Integer[] rowNums;
		public HSSFRow[] rows;
		public int loopCount;
		public String loopVar;
	}
	
	private class SheetGroup
	{
		public String[] sheetNames;
		public HSSFSheet[] srcSheets;
		public int loopType;
		public int loopCount;
		public String loopVar;
	}
	
	public static void main(String[] args) throws Exception
	{
		Printer xlsPrint = new ExcelPrinter("d:/test.xls");
		xlsPrint.print("d:/test1.xls");
	}
}
