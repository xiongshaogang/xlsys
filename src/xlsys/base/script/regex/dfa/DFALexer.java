package xlsys.base.script.regex.dfa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DFALexer
{
	private InputStream in;
	private int peek;
	
	public DFALexer(InputStream in)
	{
		this.in = in;
		peek = ' ';
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
	
	public int scan() throws IOException
	{
		readch();
		int c = peek;
		peek = ' ';
		return c;
	}
	
	public static void main(String[] args) throws IOException
	{
		String expr = "23*(32-22)+19.6 + a - b*c";
		ByteArrayInputStream bais = new ByteArrayInputStream(expr.getBytes());
		DFALexer lexer = new DFALexer(bais);
		int token = -1;
		while((token=lexer.scan())!=-1)
		{
			System.out.println((char)token);
		}
	}
}
