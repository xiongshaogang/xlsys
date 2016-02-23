package xlsys.base.exception;

/**
 * 已启动异常。常用在重复启动同一个实例时抛出异常。
 * @author Lewis
 *
 */
public class AlreadyStartedException extends Exception
{
	private static final long serialVersionUID = 4326180644659351938L;

	public AlreadyStartedException()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public AlreadyStartedException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3)
	{
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public AlreadyStartedException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public AlreadyStartedException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public AlreadyStartedException(Throwable arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
