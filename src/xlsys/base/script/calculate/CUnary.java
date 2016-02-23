package xlsys.base.script.calculate;

import java.math.BigDecimal;

public class CUnary extends COp
{
	public CExpr expr;
	
	protected CUnary(CToken op, CExpr expr)
	{
		super(op);
		this.expr = expr;
	}
	
	@Override
	public BigDecimal calculate()
	{
		BigDecimal value = expr.calculate();
		BigDecimal result = null;
		if(op.tag=='+') result = value;
		else if(op.tag=='-') result = value.negate();
		return result;
	}

	@Override
	public String toString()
	{
		return op.toString() + expr.toString();
	}
}
