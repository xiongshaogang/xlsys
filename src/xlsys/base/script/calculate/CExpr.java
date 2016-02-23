package xlsys.base.script.calculate;

import java.math.BigDecimal;

public class CExpr extends CNode
{
	public CToken op;
	
	protected CExpr(CToken op)
	{
		this.op = op;
	}
	
	public BigDecimal calculate()
	{
		return null;
	}
	
	
}
