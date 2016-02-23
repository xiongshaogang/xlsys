package xlsys.base.exception;

/**
 * 已关闭异常。常用在重复关闭或者使用已关闭的对象时抛出异常。
 * @author Lewis
 *
 */
public class AlreadyClosedException extends Exception
{
	private static final long serialVersionUID = 3188326886712290780L;

	public AlreadyClosedException()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public AlreadyClosedException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3)
	{
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public AlreadyClosedException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public AlreadyClosedException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public AlreadyClosedException(Throwable arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
