package xlsys.base.model;

import xlsys.base.model.ITableModel;
import java.math.BigDecimal;

/**
 * This class is automatically generated by the program of Lewis.
 * @author Lewis
 *
 */
public class HandlerModel implements ITableModel
{
	private static final long serialVersionUID = 4006516209122181075L;
	
	private BigDecimal handlerid;
	private String name;
	private String impl;

	protected HandlerModel() {}

	public HandlerModel(BigDecimal handlerid)
	{
		this.handlerid = handlerid;
	}

	public BigDecimal getHandlerid()
	{
		return handlerid;
	}

	protected void setHandlerid(BigDecimal handlerid)
	{
		this.handlerid = handlerid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getImpl()
	{
		return impl;
	}

	public void setImpl(String impl)
	{
		this.impl = impl;
	}

	@Override
	public String getRefTableName()
	{
		return "xlv2_handler";
	}
}