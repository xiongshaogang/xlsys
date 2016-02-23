package xlsys.base.exception;

/**
 * 不支持的数据源异常。当系统使用不受支持的数据源时，抛出此异常。
 * @author Lewis
 *
 */
public class UnsupportedDataSourceException extends UnsupportedException
{
	private static final long serialVersionUID = 6599783142113580093L;

	public UnsupportedDataSourceException()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public UnsupportedDataSourceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public UnsupportedDataSourceException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UnsupportedDataSourceException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UnsupportedDataSourceException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
