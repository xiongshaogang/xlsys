package xlsys.base.script.calculate;

import java.math.BigDecimal;

import xlsys.base.util.NumUtil;

public class CArith extends COp
{
	private static NumUtil nu = new NumUtil(); 
	
	public CExpr expr1;
	public CExpr expr2;
	
	protected CArith(CToken op, CExpr expr1, CExpr expr2)
	{
		super(op);
		this.expr1 = expr1;
		this.expr2 = expr2;
	}

	@Override
	public BigDecimal calculate()
	{
		BigDecimal left = expr1.calculate();
		BigDecimal right = expr2.calculate();
		BigDecimal result = null;
		if(op.tag=='+') result = nu.add(left, right);
		else if(op.tag=='-') result = nu.subtract(left, right);
		else if(op.tag=='*') result = nu.multiply(left, right);
		else if(op.tag=='/') result = nu.divide(left, right);
		else if(op.tag=='%') result = nu.remainder(left, right);
		return result;
	}

	@Override
	public String toString()
	{
		return expr1.toString() + op.toString() + expr1.toString();
	}

}
