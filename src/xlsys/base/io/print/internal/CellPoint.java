package xlsys.base.io.print.internal;

public class CellPoint
{
	public int width;
	public int height;
	public int row;
	public int col;
	public int offX;
	public int offY;
	
	public CellPoint clone()
	{
		CellPoint c = new CellPoint();
		c.width = width;
		c.height = height;
		c.row = row;
		c.col = col;
		c.offX = offX;
		c.offY = offY;
		return c;
	}
}
