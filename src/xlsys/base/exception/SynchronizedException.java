package xlsys.base.exception;

/**
 * 同步异常。常用在信息不同步时抛出异常。
 * @author Lewis
 *
 */
public class SynchronizedException extends Exception
{
	private static final long serialVersionUID = -7825427924372803782L;

	public SynchronizedException()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public SynchronizedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public SynchronizedException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SynchronizedException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SynchronizedException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
