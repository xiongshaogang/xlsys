package xlsys.base.exception;

/**
 * 参数不够异常。常用在需要的参数多余实际获得的参数时抛出异常。
 * @author Lewis
 *
 */
public class ParameterNotEnoughException extends Exception
{
	private static final long serialVersionUID = -3497693796823322377L;

	public ParameterNotEnoughException()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public ParameterNotEnoughException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3)
	{
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public ParameterNotEnoughException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ParameterNotEnoughException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ParameterNotEnoughException(Throwable arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	
}
