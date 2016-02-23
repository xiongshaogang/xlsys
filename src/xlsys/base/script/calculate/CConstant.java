package xlsys.base.script.calculate;

import java.math.BigDecimal;

public class CConstant extends CExpr
{
	protected CConstant(CToken op)
	{
		super(op);
	}

	@Override
	public BigDecimal calculate()
	{
		return new BigDecimal(op.toString());
	}

	@Override
	public String toString()
	{
		return op.toString();
	}

}
