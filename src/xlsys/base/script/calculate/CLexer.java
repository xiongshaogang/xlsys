package xlsys.base.script.calculate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import xlsys.base.io.util.IOUtil;

public class CLexer
{
	private InputStream in;
	private int peek;
	private Map<String, CVarToken> vars;
	
	public CLexer(InputStream in)
	{
		this.in = in;
		peek = ' ';
		vars = new HashMap<String, CVarToken>();
	}
	
	public void addVar(String var, Object value)
	{
		CVarToken cvar = new CVarToken(var, value);
		vars.put(var, cvar);
	}
	
	void readch() throws IOException
	{
		peek = in.read();
	}
	
	boolean readch(char c) throws IOException
	{
		readch();
		if(peek!=c) return false;
		peek = ' ';
		return true;
	}
	
	public CToken scan() throws IOException
	{
		// 跳过空白字符
		for(;;readch())
		{
			if(Character.isWhitespace(peek)) continue;
			else break;
		}
		// 如果到末尾, 则返回末尾Token
		if(peek==-1)
		{
			return new CToken(CTag.EOF);
		}
		// 检查数字
		if(Character.isDigit(peek))
		{
			StringBuilder sb = new StringBuilder();
			do
			{
				sb.append((char)peek);
				readch();
			}
			while(Character.isDigit(peek));
			if(peek!='.') return new CNumToken(sb.toString());
			else sb.append((char)peek);
			for(;;)
			{
				readch();
				if(!Character.isDigit(peek)) break;
				sb.append((char)peek);
			}
			return new CNumToken(sb.toString());
		}
		// 检查Var
		if(Character.isLetter(peek))
		{
			StringBuilder sb = new StringBuilder();
			do
			{
				sb.append((char)peek);
				readch();
			}
			while(Character.isLetterOrDigit(peek));
			String var = sb.toString();
			CVarToken cvar = vars.get(var);
			if(cvar!=null) return cvar;
			cvar = new CVarToken(var);
			vars.put(var, cvar);
			return cvar;
		}
		// 最后，其余的任意字符都被作为词法单元返回
		CToken tok = new CToken(peek);
		peek = ' ';
		return tok;
	}
	
	public static void main(String[] args) throws IOException
	{
		String expr = "23*(32-22)+19.6 + a - b*c";
		ByteArrayInputStream bais = new ByteArrayInputStream(expr.getBytes());
		CLexer lexer = new CLexer(bais);
		lexer.addVar("a", 10);
		lexer.addVar("b", 20);
		lexer.addVar("c", 30);
		CToken token = null;
		while((token=lexer.scan())!=null)
		{
			if(token.tag==CTag.EOF) break;
			System.out.println("TAG " + token.tag + " : " + token.toString());
		}
		IOUtil.close(bais);
	}
}
