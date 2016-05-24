package xlsys.base.model;

import java.math.BigDecimal;

/**
 * This class is automatically generated by the program of Lewis.
 * @author Lewis
 *
 */
public class ViewQueryParamParamModel implements ITableModel
{
	private static final long serialVersionUID = 2000180227879856750L;
	
	private BigDecimal viewid;
	private BigDecimal type;
	private BigDecimal idx;
	private String attrname;
	private String attrvalue;

	protected ViewQueryParamParamModel() {}

	public ViewQueryParamParamModel(BigDecimal viewid, BigDecimal type, BigDecimal idx, String attrname)
	{
		this.viewid = viewid;
		this.type = type;
		this.idx = idx;
		this.attrname = attrname;
	}

	public BigDecimal getViewid()
	{
		return viewid;
	}

	protected void setViewid(BigDecimal viewid)
	{
		this.viewid = viewid;
	}

	public BigDecimal getType()
	{
		return type;
	}

	protected void setType(BigDecimal type)
	{
		this.type = type;
	}

	public BigDecimal getIdx()
	{
		return idx;
	}

	protected void setIdx(BigDecimal idx)
	{
		this.idx = idx;
	}

	public String getAttrname()
	{
		return attrname;
	}

	protected void setAttrname(String attrname)
	{
		this.attrname = attrname;
	}

	public String getAttrvalue()
	{
		return attrvalue;
	}

	public void setAttrvalue(String attrvalue)
	{
		this.attrvalue = attrvalue;
	}

	@Override
	public String getRefTableName()
	{
		return "xlv2_viewqueryparamparam";
	}
}