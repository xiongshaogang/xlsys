package xlsys.base.script.regex.dfa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public class DFARegex
{
	private InputStream in;
	private DFAParser parser;
	private DFALexer lexer;
	
	public DFARegex(String regex) throws IOException
	{
		in = new ByteArrayInputStream(regex.getBytes());
		init();
	}
	
	public DFARegex(InputStream in) throws IOException
	{
		this.in = in;
		init();
	}
	
	private void init() throws IOException
	{
		lexer = new DFALexer(in);
		parser = new DFAParser(lexer);
		parser.createSyntaxTree();
	}
	
	public boolean matches(String str) throws IOException
	{
		int finalPst = parser.getFinalPosition();
		Map<Set<Integer>, Map<Character, Set<Integer>>> tranMap = parser.getTranMap();
		Set<Integer> curState = parser.getBeginState();
		for(int i=0;i<str.length();++i)
		{
			char c = str.charAt(i);
			Map<Character, Set<Integer>> pathMap = tranMap.get(curState);
			curState = pathMap.get(c);
			if(curState==null) break;
		}
		return curState!=null&&curState.contains(finalPst);
	}
	
	public int matchedAt(String str) throws IOException
	{
		return matchedAt(str, 0);
	}
	
	/**
	 * 返回匹配到的字符串的结束位置的后一个位置, 没有匹配到则返回-1
	 * @param str
	 * @param beginIndex
	 * @return
	 * @throws IOException 
	 */
	public int matchedAt(String str, int beginIndex) throws IOException
	{
		int matchedIndex = -1;
		int finalPst = parser.getFinalPosition();
		Map<Set<Integer>, Map<Character, Set<Integer>>> tranMap = parser.getTranMap();
		Set<Integer> curState = parser.getBeginState();
		for(int i=beginIndex;i<str.length();++i)
		{
			char c = str.charAt(i);
			Map<Character, Set<Integer>> pathMap = tranMap.get(curState);
			curState = pathMap.get(c);
			if(curState==null) break;
			else if(curState!=null&&curState.contains(finalPst)) matchedIndex = i+1;
		}
		return matchedIndex;
	}
	
	public static void main(String[] args) throws IOException
	{
		DFARegex regex = new DFARegex("(a|b)*abb");
		System.out.println(regex.matches("ababbaababb"));
		System.out.println(regex.matches("ababbaababba"));
		System.out.println(regex.matches("abb"));
		String str = "ababbaacbabba";
		int beginIdx = 2;
		int endIdx = regex.matchedAt(str, beginIdx);
		if(endIdx!=-1)
		{
			System.out.println(str.substring(beginIdx, endIdx));
		}
	}
}
