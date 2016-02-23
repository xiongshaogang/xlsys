package xlsys.base.io.print.internal;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import xlsys.base.exception.UnsupportedException;
import xlsys.base.io.print.ExcelPrinter;
import xlsys.base.io.util.ExcelUtil;
import xlsys.base.log.Log;
import xlsys.base.util.ObjectUtil;
import xlsys.base.util.StringUtil;

public class ExcelCreater implements IPrintListener
{
	private ExcelPrinter excelPrint;
	private String globalScript;
	
	public ExcelCreater(ExcelPrinter excelPrint)
	{
		this.excelPrint = excelPrint;
	}
	
	@Override
	public boolean begin()
	{
		boolean canRun = true;
		HSSFWorkbook workbook = excelPrint.getWorkbook();
		int sheetIdx = workbook.getSheetIndex(ExcelPrinter.SCRIPT_SHEET_NAME);
		HSSFSheet xsSheet = null;
		if(sheetIdx>=0) xsSheet = workbook.getSheetAt(sheetIdx);
		if(xsSheet!=null)
		{
			// 读取所有的全局代码,并删除当前代码sheet页
			StringBuffer script = new StringBuffer();
			int rowCount = ExcelUtil.getRowCountOfSheet(xsSheet);
			for(int i=0;i<rowCount;i++)
			{
				HSSFRow row = xsSheet.getRow(i);
				if(row==null) continue;
				int minColIdx = row.getFirstCellNum();
				int maxColIdx = row.getLastCellNum();
				for(int j=minColIdx;j<maxColIdx;j++)
				{
					HSSFCell cell = row.getCell(j);
					String str = cell.getStringCellValue();
					if(!StringUtil.isNullStr(str))
					{
						script.append(str);
					}
				}
			}
			globalScript = script.toString();
			workbook.removeSheetAt(sheetIdx);
		}
		else globalScript = "";
		excelPrint.getXlsysScript().setScript(globalScript);
		try
		{
			Object ret = excelPrint.getXlsysScript().invoke("begin", workbook);
			if(ret!=null) canRun = ObjectUtil.objectToBoolean(ret);
		}
		catch (ScriptException e)
		{
			showMessage(Log.LOG_LEVEL_ERROR, e.getMessage(), e);
		}
		return canRun;
	}

	@Override
	public void end()
	{
		excelPrint.getXlsysScript().setScript(globalScript);
		try
		{
			excelPrint.getXlsysScript().invoke("end", excelPrint.getWorkbook());
		}
		catch (ScriptException e)
		{
			showMessage(Log.LOG_LEVEL_ERROR, e.getMessage(), e);
		}
	}
	
	/**
	 * 返回是否删除被当做模板的sheet页, 如果不删除, 则会隐藏
	 * 默认为不删除
	 * @return
	 */
	public boolean removeTemplateSheet(String sheetName)
	{
		boolean ret = false;
		excelPrint.getXlsysScript().setScript(globalScript);
		try
		{
			Object o = excelPrint.getXlsysScript().invoke("removeTemplateSheet", sheetName);
			if(o!=null) ret = ObjectUtil.objectToBoolean(o);
		}
		catch (ScriptException e)
		{
			showMessage(Log.LOG_LEVEL_ERROR, e.getMessage(), e);
		}
		return ret;
	}
	
	/**
	 * 用来返回循环sheet页的循环顺序，sheet的循环用sheet组来划分，一组一个循环模式
	 * <li> LOOP_SHEET_ONCE为每组循环一次后即进入下一个组
	 * <li> LOOP_SHEET_ALL为该组循环结束后才进入下一个组
	 * @return 
	 * @throws ScriptException 
	 */
	public List<String[]> getSheetLoopGroup(String[] sheetNames) throws ScriptException
	{
		List<String[]> sheetLoopList = null;
		excelPrint.getXlsysScript().setScript(globalScript);
		List<Object[]> tempList = (List<Object[]>) excelPrint.getXlsysScript().invokeFunctionWithOneArrayParam("getSheetLoopGroup", sheetNames);
		if(tempList!=null&&!tempList.isEmpty())
		{
			sheetLoopList = new ArrayList<String[]>();
			for(Object[] objArr : tempList)
			{
				String[] strArr = new String[objArr.length];
				for(int i=0;i<objArr.length;i++)
				{
					strArr[i] = ObjectUtil.objectToString(objArr[i]);
				}
				sheetLoopList.add(strArr);
			}
		}
		return sheetLoopList;
	}
	
	/**
	 * 获取sheet组的循环模式
	 * <li> LOOP_SHEET_ONCE为该组循环一次后即进入下一个组
	 * <li> LOOP_SHEET_ALL为该组循环结束后才进入下一个组
	 * @param sheetGroup
	 * @return
	 * @throws ScriptException
	 * @throws UnsupportedException 
	 */
	public int getSheetGroupLoopType(String[] sheetGroup) throws ScriptException, UnsupportedException
	{
		int type = ExcelPrinter.LOOP_SHEET_TYPE_ALL;
		excelPrint.getXlsysScript().setScript(globalScript);
		Object obj = excelPrint.getXlsysScript().invokeFunctionWithOneArrayParam("getSheetGroupLoopType", sheetGroup);
		if(obj!=null) type = ObjectUtil.objectToInt(obj);
		return type;
	}
	
	/**
	 * 获取sheet组的循环次数
	 * @param sheetGroup 当前需要计算的sheet组
	 * @return 需要循环的次数
	 * @throws ScriptException 
	 * @throws UnsupportedException 
	 */
	public int getSheetGroupLoopCount(String[] sheetGroup) throws ScriptException, UnsupportedException
	{
		int count = 1;
		excelPrint.getXlsysScript().setScript(globalScript);
		Object obj = excelPrint.getXlsysScript().invokeFunctionWithOneArrayParam("getSheetGroupLoopCount", sheetGroup);
		if(obj!=null) count = ObjectUtil.objectToInt(obj);
		return count;
	}
	
	/**
	 * 获取sheet组循环时的游标变量名
	 * @param sheetGroup
	 * @return 需要将循环游标放入的变量
	 * @throws ScriptException
	 */
	public String getSheetGroupLoopVar(String[] sheetGroup) throws ScriptException
	{
		excelPrint.getXlsysScript().setScript(globalScript);
		String globalVar = (String) excelPrint.getXlsysScript().invokeFunctionWithOneArrayParam("getSheetGroupLoopVar", sheetGroup);
		if(globalVar==null) globalVar = ""+sheetGroup.hashCode();
		return globalVar;
	}
	
	/**
	 * 获取sheet页的新名称
	 * @param sheetName 原始sheet页名称
	 * @param loopCursor 当前循环游标的位置
	 * @return
	 * @throws ScriptException 
	 */
	public String renameSheet(String sheetName, int loopCursor) throws ScriptException
	{
		excelPrint.getXlsysScript().setScript(globalScript);
		String newSheetName = (String) excelPrint.getXlsysScript().invoke("renameSheet", sheetName, loopCursor);
		if(newSheetName==null) newSheetName = sheetName;
		return newSheetName;
	}
	
	/**
	 * 获得需要循环的行组序号
	 * @param sheetName 原始sheet页名称
	 * @return List中的每个元素都是一个行组，循环时会以一个行组来循环
	 * @throws ScriptException 
	 * @throws UnsupportedException 
	 */
	public List<Integer[]> getRowLoopGroups(String sheetName) throws ScriptException, UnsupportedException
	{
		List<Integer[]> rowLoopList = null;
		excelPrint.getXlsysScript().setScript(globalScript);
		List<Object[]> tempList = (List<Object[]>) excelPrint.getXlsysScript().invoke("getRowLoopGroups", sheetName);
		if(tempList!=null&&!tempList.isEmpty())
		{
			rowLoopList = new ArrayList<Integer[]>();
			for(Object[] objArr : tempList)
			{
				Integer[] intArr = new Integer[objArr.length];
				for(int i=0;i<objArr.length;i++)
				{
					intArr[i] = ObjectUtil.objectToInt(objArr[i]);
				}
				rowLoopList.add(intArr);
			}
		}
		return rowLoopList;
	}
	
	/**
	 * 获取行组的循环次数
	 * @param sheetName 原始sheet页名称
	 * @param sheetLoopCursor 当前sheet循环游标的位置
	 * @param rowGroup 当前需要计算的行组
	 * @param globalVar 将要存放该循环游标的全局变量
	 * @return 需要循环的次数
	 * @throws ScriptException 
	 * @throws UnsupportedException 
	 */
	public int getRowGroupLoopCount(String sheetName, int sheetLoopCursor, Integer[] rowGroup) throws ScriptException, UnsupportedException
	{
		int count = 1;
		excelPrint.getXlsysScript().setScript(globalScript);
		Object obj = excelPrint.getXlsysScript().invoke("getRowGroupLoopCount", sheetName, sheetLoopCursor, rowGroup);
		if(obj!=null) count = ObjectUtil.objectToInt(obj);
		return count;
	}
	
	/**
	 * 获取行组循环时的游标变量名
	 * @param sheetName
	 * @param sheetLoopCursor
	 * @param rowGroup
	 * @return
	 * @throws ScriptException
	 */
	public String getRowGroupLoopVar(String sheetName, int sheetLoopCursor, Integer[] rowGroup) throws ScriptException
	{
		excelPrint.getXlsysScript().setScript(globalScript);
		String globalVar = (String) excelPrint.getXlsysScript().invoke("getRowGroupLoopVar", sheetName, sheetLoopCursor, rowGroup);
		if(globalVar==null) globalVar = sheetLoopCursor + "_"+rowGroup.hashCode();
		return globalVar;
	}
	
	/**
	 * 获得需要循环的列组序号
	 * @param sheetName 原始sheet页名称
	 * @return List中的每个元素都是一个列组，循环时会以一个列组来循环
	 * @throws ScriptException 
	 * @throws UnsupportedException 
	 */
	public List<Integer[]> getColLoopGroups(String sheetName) throws ScriptException, UnsupportedException
	{
		List<Integer[]> colLoopList = null;
		excelPrint.getXlsysScript().setScript(globalScript);
		List<Object[]> tempList = (List<Object[]>) excelPrint.getXlsysScript().invoke("getColLoopGroups", sheetName);
		if(tempList!=null&&!tempList.isEmpty())
		{
			colLoopList = new ArrayList<Integer[]>();
			for(Object[] objArr : tempList)
			{
				Integer[] intArr = new Integer[objArr.length];
				for(int i=0;i<objArr.length;i++)
				{
					intArr[i] = ObjectUtil.objectToInt(objArr[i]);
				}
				colLoopList.add(intArr);
			}
		}
		return colLoopList;
	}
	
	/**
	 * 获取列组的循环次数
	 * @param sheetName 原始sheet页名称
	 * @param sheetLoopCursor 当前sheet循环游标的位置
	 * @param colGroup 当前需要计算的列组
	 * @param globalVar 将要存放该循环游标的全局变量
	 * @return 需要循环的次数
	 * @throws ScriptException 
	 * @throws UnsupportedException 
	 */
	public int getColGroupLoopCount(String sheetName, int sheetLoopCursor, Integer[] colGroup) throws ScriptException, UnsupportedException
	{
		int count = 1;
		excelPrint.getXlsysScript().setScript(globalScript);
		Object obj = excelPrint.getXlsysScript().invoke("getColGroupLoopCount", sheetName, sheetLoopCursor, colGroup);
		if(obj!=null) count = ObjectUtil.objectToInt(obj);
		return count;
	}
	
	/**
	 * 获取列组循环时的游标变量名
	 * @param sheetName
	 * @param sheetLoopCursor
	 * @param colGroup
	 * @return
	 * @throws ScriptException
	 */
	public String getColGroupLoopVar(String sheetName, int sheetLoopCursor, Integer[] colGroup) throws ScriptException
	{
		excelPrint.getXlsysScript().setScript(globalScript);
		String globalVar = (String) excelPrint.getXlsysScript().invoke("getColGroupLoopVar", sheetName, sheetLoopCursor, colGroup);
		if(globalVar==null) globalVar = sheetLoopCursor + "_"+colGroup.hashCode();
		return globalVar;
	}
	
	/**
	 * 获取指定的sheet是否先进行行循环，默认为先进行行循环
	 * @param sheetName
	 * @return
	 * @throws ScriptException
	 */
	public boolean isLoopRowFirst(String sheetName) throws ScriptException
	{
		excelPrint.getXlsysScript().setScript(globalScript);
		Boolean loopRowFirst = (Boolean) excelPrint.getXlsysScript().invoke("isLoopRowFirst", sheetName);
		if(loopRowFirst==null) loopRowFirst = true;
		return loopRowFirst;
	}
	
	/**
	 * 判断是否需要行循环,默认为需要
	 * @param sheetName
	 * @return
	 * @throws ScriptException
	 */
	public boolean isNeedLoopRow(String sheetName) throws ScriptException
	{
		excelPrint.getXlsysScript().setScript(globalScript);
		Boolean needLoopRow = (Boolean) excelPrint.getXlsysScript().invoke("isNeedLoopRow", sheetName);
		if(needLoopRow==null) needLoopRow = true;
		return needLoopRow;
	}
	
	/**
	 * 判断是否需要列循环,默认为不需要(因为列循环开销很大)
	 * @param sheetName
	 * @return
	 * @throws ScriptException
	 */
	public boolean isNeedLoopCol(String sheetName) throws ScriptException
	{
		excelPrint.getXlsysScript().setScript(globalScript);
		Boolean needLoopCol = (Boolean) excelPrint.getXlsysScript().invoke("isNeedLoopCol", sheetName);
		if(needLoopCol==null) needLoopCol = false;
		return needLoopCol;
	}

	@Override
	public void showMessage(int logLevel, String message, Throwable e)
	{
		excelPrint.getXlsysScript().setScript(globalScript);
		try
		{
			excelPrint.getXlsysScript().invoke("showMessage", logLevel, message, e);
		}
		catch (ScriptException e1)
		{
			e1.printStackTrace();
		}
	}
}
