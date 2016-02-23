package xlsys.base.script.calculate;

public class CToken
{
	public final int tag;
	
	public CToken(int tag)
	{
		this.tag = tag;
	}

	@Override
	public String toString()
	{
		return "" + (char) tag;
	}
}
