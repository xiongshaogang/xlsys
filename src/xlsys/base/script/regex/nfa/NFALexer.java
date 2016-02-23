package xlsys.base.script.regex.nfa;

import java.io.IOException;
import java.io.InputStream;

public class NFALexer
{
	private InputStream in;
	private int peek;
	
	public NFALexer(InputStream in)
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
}
