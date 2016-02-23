package xlsys.base.script.calculate;

import java.math.BigDecimal;

import xlsys.base.util.ObjectUtil;

public class CVar extends CExpr
{
	protected CVar(CToken op)
	{
		super(op);
	}

	@Override
	public BigDecimal calculate()
	{
		CVarToken tok = (CVarToken) op;
		return new BigDecimal(ObjectUtil.objectToString(tok.value));
	}

	@Override
	public String toString()
	{
		return op.toString();
	}

}
