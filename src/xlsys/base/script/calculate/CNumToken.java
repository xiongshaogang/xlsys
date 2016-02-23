package xlsys.base.script.calculate;

public class CNumToken extends CToken
{
	public String numStr;
	
	public CNumToken(String numStr)
	{
		super(CTag.NUM);
		this.numStr = numStr;
	}
	
	@Override
	public String toString()
	{
		return numStr;
	}
}
