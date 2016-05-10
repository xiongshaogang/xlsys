package xlsys.base.model;


/**
 * This class is automatically generated by the program of Lewis.
 * @author Lewis
 *
 */
public class ToolHandlerParamModel implements ITableModel
{
	private static final long serialVersionUID = -1017251469363809356L;
	
	private String toolid;
	private String attrname;
	private String attrvalue;

	protected ToolHandlerParamModel() {}

	public ToolHandlerParamModel(String toolid, String attrname)
	{
		this.toolid = toolid;
		this.attrname = attrname;
	}

	public String getToolid()
	{
		return toolid;
	}

	protected void setToolid(String toolid)
	{
		this.toolid = toolid;
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
		return "xlv2_toolhandlerparam";
	}
}