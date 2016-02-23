package xlsys.base.io.print.internal;

import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class ExcelColumn
{
	protected HSSFCell[] cells;
	protected int columnWidth;
	
	protected ExcelColumn(int length)
	{
		cells = new HSSFCell[length];
	}

	public HSSFCell[] getCells()
	{
		return cells;
	}

	public int getLastCellNum()
	{
		return cells.length;
	}
	
	public HSSFCell getCell(int colIndex)
	{
		return cells[colIndex];
	}
	
	public int getColumnWidth()
	{
		return columnWidth;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cells);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExcelColumn other = (ExcelColumn) obj;
		if (!Arrays.equals(cells, other.cells))
			return false;
		return true;
	}
	
}
