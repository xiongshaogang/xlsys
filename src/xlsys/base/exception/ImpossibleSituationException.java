package xlsys.base.exception;

/**
 * 不可能的情况异常。常用在检测到不可能出现的情景时抛出的异常。
 * @author Lewis
 *
 */
public class ImpossibleSituationException extends Exception
{
	private static final long serialVersionUID = 1209181065224224528L;

	public ImpossibleSituationException()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public ImpossibleSituationException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3)
	{
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public ImpossibleSituationException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ImpossibleSituationException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ImpossibleSituationException(Throwable arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
