package xlsys.base.script.regex.nfa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NFARegex
{
	public static final int NULL_STR = -1;
	
	private InputStream in;
	private NFALexer lexer;
	private NFAParser parser;
	private Map<Integer, Map<Integer, Set<Integer>>> closureMap;
	
	public NFARegex(String regex) throws IOException
	{
		in = new ByteArrayInputStream(regex.getBytes());
		init();
	}
	
	public NFARegex(InputStream in) throws IOException
	{
		this.in = in;
		init();
	}
	
	private void init() throws IOException
	{
		lexer = new NFALexer(in);
		parser = new NFAParser(lexer);
		parser.createSyntaxTree();
		closureMap = new HashMap<Integer, Map<Integer, Set<Integer>>>();
	}
	
	public boolean matches(String str) throws IOException
	{
		// 获取开始状态集合
		Set<Integer> curStates = getClosure(parser.tree.beginPst, NULL_STR);
		for(int i=0;i<str.length();++i)
		{
			char c = str.charAt(i);
			Integer[] curStateArr = curStates.toArray(new Integer[0]);
			Set<Integer> nextStates = new HashSet<Integer>();
			for(Integer curPst : curStateArr)
			{
				nextStates.addAll(getClosure(curPst, (int)c));
			}
			curStates = nextStates;
			if(curStates.isEmpty()) break;
		}
		return curStates.contains(parser.tree.endPst);
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
		Set<Integer> curStates = getClosure(parser.tree.beginPst, NULL_STR);
		for(int i=beginIndex;i<str.length();++i)
		{
			char c = str.charAt(i);
			Integer[] curStateArr = curStates.toArray(new Integer[0]);
			Set<Integer> nextStates = new HashSet<Integer>();
			for(Integer curPst : curStateArr)
			{
				nextStates.addAll(getClosure(curPst, (int)c));
			}
			curStates = nextStates;
			if(curStates.isEmpty()) break;
			else if(curStates.contains(parser.tree.endPst)) matchedIndex = i+1;
		}
		return matchedIndex;
	}
	
	/**
	 * 获取位置position通过字符c能到达的状态集合
	 * 注意实现的时候要考虑空串的情况
	 * @param position
	 * @param c
	 * @return
	 */
	private Set<Integer> getClosure(Integer position, Integer c)
	{
		Map<Integer, Set<Integer>> cPathMap = closureMap.get(position);
		if(cPathMap==null)
		{
			cPathMap = new HashMap<Integer, Set<Integer>>();
			closureMap.put(position, cPathMap);
		}
		Set<Integer> set = cPathMap.get(c);
		if(set==null)
		{
			set = new HashSet<Integer>();
			cPathMap.put(c, set);
			Map<Integer, Set<Integer>> pathMap = parser.tranMap.get(position);
			if(pathMap!=null)
			{
				// 先获取该位置通过空串能到达的状态集合
				Set<Integer> allNCSet = new HashSet<Integer>();
				Set<Integer> nullClosureSet = pathMap.get(NULL_STR);
				if(nullClosureSet!=null)
				{
					// 对所有的能到达的空串集合进行递归调用, 获取所有的子集合
					for(Integer ncPst : nullClosureSet)
					{
						allNCSet.addAll(getClosure(ncPst, NULL_STR));
					}
					allNCSet.addAll(nullClosureSet);
				}
				// 当前位置本身也可以通过空串到达自身, 所以这里要把当前位置也加入
				allNCSet.add(position);
				if(c==NULL_STR) set.addAll(allNCSet);
				else
				{
					// 获取所有可通过空串到达的位置，通过字符c能到达的位置集合
					Set<Integer> passCSet = new HashSet<Integer>();
					for(Integer ncPst : allNCSet)
					{
						Map<Integer, Set<Integer>> tempPathMap = parser.tranMap.get(ncPst);
						if(tempPathMap!=null)
						{
							Set<Integer> tempSet = tempPathMap.get(c);
							if(tempSet!=null) passCSet.addAll(tempSet);
						}
					}
					// 在获取通过c能到达的每个位置，通过空串能到达的位置集合
					if(!passCSet.isEmpty())
					{
						set.addAll(passCSet);
						for(Integer passCPst : passCSet)
						{
							set.addAll(getClosure(passCPst, NULL_STR));
						}
					}
				}
			}
		}
		return set;
	}
	
	public static void main(String[] args) throws IOException
	{
		NFARegex regex = new NFARegex("(a|b)*abb");
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
