package xlsys.base.script.complier.study.inter;

import xlsys.base.script.complier.study.lexer.Token;
import xlsys.base.script.complier.study.symbols.Type;

public class Expr extends Node
{
	public Token op;
	public Type type;
	
	protected Expr(int lexline, Token tok, Type p)
	{
		super(lexline);
		op = tok;
		type = p;
	}

}
