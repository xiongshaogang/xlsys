package xlsys.base.io.print.internal;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import xlsys.base.io.util.ExcelUtil;

public class SheetEntity
{
	private HSSFRow[] rows;
	private ExcelColumn[] cols;
	
	public SheetEntity(HSSFSheet sheet)
	{
		int rowCount = ExcelUtil.getRowCountOfSheet(sheet);
		int colCount = ExcelUtil.getColCountOfSheet(sheet);
		rows = new HSSFRow[rowCount];
		cols = new ExcelColumn[colCount];
		for(int i=0;i<colCount;i++)
		{
			cols[i] = new ExcelColumn(rowCount);
			cols[i].columnWidth = sheet.getColumnWidth(i);
		}
		for(int i=0;i<rowCount;i++)
		{
			rows[i] = sheet.getRow(i);
			if(rows[i]!=null)
			{
				int minColIdx = rows[i].getFirstCellNum();
				int maxColIdx = rows[i].getLastCellNum();
				for(int j=minColIdx;j<maxColIdx;j++)
				{
					cols[j].cells[i] = rows[i].getCell(j);
				}
			}
		}
	}

	public HSSFRow[] getRows()
	{
		return rows;
	}

	public ExcelColumn[] getCols()
	{
		return cols;
	}
	
	public HSSFRow getRow(int rowIndex)
	{
		return rows[rowIndex];
	}
	
	public ExcelColumn getColumn(int colIndex)
	{
		return cols[colIndex];
	}
	
	public int getRowCount()
	{
		return rows.length;
	}
	
	public int getColCount()
	{
		return cols.length;
	}
}
