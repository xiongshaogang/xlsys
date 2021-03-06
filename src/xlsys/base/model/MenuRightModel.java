package xlsys.base.model;

import java.math.BigDecimal;

/**
 * This class is automatically generated by the program of Lewis.
 * @author Lewis
 *
 */
public class MenuRightModel implements ITableModel
{
	private String menuid;
	private BigDecimal idx;
	private BigDecimal righttype;
	private String rightvalue;

	protected MenuRightModel() {}

	public MenuRightModel(String menuid, BigDecimal idx)
	{
		this.menuid = menuid;
		this.idx = idx;
	}

	public String getMenuid()
	{
		return menuid;
	}

	protected void setMenuid(String menuid)
	{
		this.menuid = menuid;
	}

	public BigDecimal getIdx()
	{
		return idx;
	}

	protected void setIdx(BigDecimal idx)
	{
		this.idx = idx;
	}

	public BigDecimal getRighttype()
	{
		return righttype;
	}

	public void setRighttype(BigDecimal righttype)
	{
		this.righttype = righttype;
	}

	public String getRightvalue()
	{
		return rightvalue;
	}

	public void setRightvalue(String rightvalue)
	{
		this.rightvalue = rightvalue;
	}

	@Override
	public String getRefTableName()
	{
		return "xlv2_menuright";
	}
}