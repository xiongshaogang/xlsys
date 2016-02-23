package xlsys.base.script.calculate;

public class CVarToken extends CToken
{
	public String var;
	public Object value;
	
	public CVarToken(String var, Object value)
	{
		super(CTag.VAR);
		this.var = var;
		this.value = value;
	}
	
	public CVarToken(String var)
	{
		super(CTag.UNKNOWN_VAR);
		this.var = var;
	}

	@Override
	public String toString()
	{
		return var;
	}
}
