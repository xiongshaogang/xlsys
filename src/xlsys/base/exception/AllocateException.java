package xlsys.base.exception;

/**
 * 获取异常。常用在获取失败时抛出异常。
 * @author Lewis
 *
 */
public class AllocateException extends Exception
{
	private static final long serialVersionUID = 8105347957189868933L;

	public AllocateException()
	{
		super();
	}

	public AllocateException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public AllocateException(String message)
	{
		super(message);
	}

	public AllocateException(Throwable cause)
	{
		super(cause);
	}

}
