package xlsys.base.script.calculate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;

import xlsys.base.io.util.IOUtil;

/**
 * 
 * @author Lewis
 *
 */
public class CParser
{
	private CLexer lexer;
	private CToken look;
	private BigDecimal result;
	
	public CParser(CLexer lexer) throws IOException
	{
		this.lexer = lexer;
		move();
	}
	
	private void move() throws IOException
	{
		look = lexer.scan();
	}
	
	private void match(int t) throws IOException
	{
		if(look.tag==t) move();
		else throw new Error("syntax error");
	}
	
	public BigDecimal calculate() throws IOException
	{
		if(result==null) result = expr().calculate();
		return result;
	}
	
	private CExpr expr() throws IOException
	{
		CExpr x = term();
		while(look.tag=='+'||look.tag=='-')
		{
			CToken tok = look;
			move();
			x = new CArith(tok, x, term());
		}
		return x;
	}
	
	private CExpr term() throws IOException
	{
		CExpr x = unary();
		while(look.tag=='*'||look.tag=='/'||look.tag=='%')
		{
			CToken tok = look;
			move();
			x = new CArith(tok, x, unary());
		}
		return x;
	}
	
	private CExpr unary() throws IOException
	{
		if(look.tag=='+'||look.tag=='-')
		{
			CToken tok = look;
			move();
			return new CUnary(tok, unary());
		}
		else
		{
			return factor();
		}
	}
	
	private CExpr factor() throws IOException
	{
		CExpr x = null;
		switch(look.tag)
		{
			case '(' :
				move();
				x = expr();
				match(')');
				break;
			case CTag.NUM :
				x = new CConstant(look);
				move();
				break;
			case CTag.VAR : 
				x = new CVar(look);
				move();
				break;
			case CTag.UNKNOWN_VAR : 
				throw new Error("unknown variable");
			default : 
				throw new Error("syntax error");
		}
		return x;
	}
	
	public static void main(String[] args) throws IOException
	{
		String expr = "23*(32-22)+19.6 + a - b*c";
		ByteArrayInputStream bais = new ByteArrayInputStream(expr.getBytes());
		CLexer lexer = new CLexer(bais);
		lexer.addVar("a", 10);
		lexer.addVar("b", 20);
		lexer.addVar("c", 30);
		CParser parser = new CParser(lexer);
		System.out.println(parser.calculate());
		IOUtil.close(bais);
	}
}
