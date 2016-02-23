package xlsys.base.script.complier.study.lexer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import xlsys.base.io.util.IOUtil;
import xlsys.base.script.complier.study.symbols.Type;

public class Lexer
{
	private InputStream in;
	private char peek;
	private Map<String, Word> words;
	
	public int line;
	
	public Lexer(InputStream in)
	{
		this.in = in;
		line = 1;
		peek = ' ';
		words = new HashMap<String, Word>();
		// 加入保留的关键字
		reserve(new Word("if", Tag.IF));
		reserve(new Word("else", Tag.ELSE));
		reserve(new Word("while", Tag.WHILE));
		reserve(new Word("do", Tag.DO));
		reserve(new Word("break", Tag.BREAK));
		reserve(Word.True);
		reserve(Word.False);
		reserve(Type.Int);
		reserve(Type.Char);
		reserve(Type.Bool);
		reserve(Type.Float);
	}
	
	private void reserve(Word w)
	{
		words.put(w.lexeme, w);
	}
	
	void readch() throws IOException
	{
		peek = (char) in.read();
	}
	
	boolean readch(char c) throws IOException
	{
		readch();
		if(peek!=c) return false;
		peek = ' ';
		return true;
	}
	
	public Token scan() throws IOException
	{
		// 跳过空白字符
		for(;;readch())
		{
			if(peek==' '||peek=='\t'||peek=='\r') continue;
			else if(peek=='\n') line += 1;
			else break;
		}
		// 检查运算符
		switch(peek)
		{
			case '&' :
				if(readch('&')) return Word.and;
				else return new Token('&');
			case '|' : 
				if(readch('|')) return Word.or;
				else return new Token('|');
			case '=' :
				if(readch('=')) return Word.eq;
				else return new Token('=');
			case '!' :
				if(readch('=')) return Word.ne;
				else return new Token('!');
			case '<' :
				if(readch('=')) return Word.le;
				else return new Token('<');
			case '>' :
				if(readch('=')) return Word.ge;
				else return new Token('>');
		}
		// 检查数字
		if(Character.isDigit(peek))
		{
			int v = 0;
			do
			{
				v = 10*v + Character.digit(peek, 10);
				readch();
			}
			while(Character.isDigit(peek));
			if(peek!='.') return new Num(v);
			float x = v;
			float d = 10;
			for(;;)
			{
				readch();
				if(!Character.isDigit(peek)) break;
				x = x + Character.digit(peek, 10)/d;
				d *= 10;
			}
			return new Real(x);
		}
		// 检查Word
		if(Character.isLetter(peek))
		{
			StringBuilder b = new StringBuilder();
			do
			{
				b.append(peek);
				readch();
			}
			while(Character.isLetterOrDigit(peek));
			String s = b.toString();
			Word w = (Word) words.get(s);
			if(w!=null) return w;
			w = new Word(s, Tag.ID);
			words.put(s, w);
			return w;
		}
		// 最后，其余的任意字符都被作为词法单元返回
		Token tok = new Token(peek);
		peek = ' ';
		return tok;
	}
	
	public static void main(String[] args) throws IOException
	{
		FileInputStream fis = IOUtil.getFileInputStream("testLexer.txt");
		Token t = null;
		Lexer l = new Lexer(fis);
		while((t=l.scan())!=null)
		{
			if(t.tag==65535) break;
			System.out.println("TAG " + t.tag + " : " + t.toString());
		}
		IOUtil.close(fis);
	}
}
