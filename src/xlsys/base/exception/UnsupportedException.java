package xlsys.base.exception;

/**
 * 不受支持的异常。当系统不支持某种操作时抛出的异常。
 * @author Lewis
 *
 */
public class UnsupportedException extends Exception
{
	private static final long serialVersionUID = -6646775988238546513L;

	public UnsupportedException()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public UnsupportedException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3)
	{
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public UnsupportedException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public UnsupportedException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public UnsupportedException(Throwable arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
