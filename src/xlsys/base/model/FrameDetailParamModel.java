package xlsys.base.model;

import java.math.BigDecimal;

/**
 * This class is automatically generated by the program of Lewis.
 * @author Lewis
 *
 */
public class FrameDetailParamModel implements ITableModel
{
	private static final long serialVersionUID = -3606198717529178471L;
	
	private BigDecimal frameid;
	private String fdtid;
	private String attrname;
	private String attrvalue;

	protected FrameDetailParamModel() {}

	public FrameDetailParamModel(BigDecimal frameid, String fdtid, String attrname)
	{
		this.frameid = frameid;
		this.fdtid = fdtid;
		this.attrname = attrname;
	}

	public BigDecimal getFrameid()
	{
		return frameid;
	}

	protected void setFrameid(BigDecimal frameid)
	{
		this.frameid = frameid;
	}

	public String getFdtid()
	{
		return fdtid;
	}

	protected void setFdtid(String fdtid)
	{
		this.fdtid = fdtid;
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
		return "xlv2_framedetailparam";
	}
}