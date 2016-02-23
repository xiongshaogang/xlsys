package xlsys.base.script.regex.dfa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DFAParser
{
	private DFALexer lexer;
	private int look;
	private DFANode tree;
	private int leafPst;
	
	// 重要位置的leafMap
	private Map<Integer, DFALeaf> leafMap;
	// followpos的值Map
	private Map<Integer, Set<Integer>> followMap;
	// 所有出现过的符号
	private Set<Character> charSet;
	// 所有DFA状态
	private Set<Set<Integer>> states;
	// 状态路径Map, 即key中的状态进行字符c后,进入哪个状态
	private Map<Set<Integer>, Map<Character, Set<Integer>>> tranMap;
	
	public DFAParser(DFALexer lexer) throws IOException
	{
		this.lexer = lexer;
		leafPst = 0;
		leafMap = new HashMap<Integer, DFALeaf>();
		followMap = new HashMap<Integer, Set<Integer>>();
		charSet = new HashSet<Character>();
		states = new HashSet<Set<Integer>>();
		tranMap = new HashMap<Set<Integer>, Map<Character, Set<Integer>>>();
		move();
	}
	
	public int getFinalPosition() throws IOException
	{
		createSyntaxTree();
		return leafPst;
	}
	
	public Map<Set<Integer>, Map<Character, Set<Integer>>> getTranMap() throws IOException
	{
		createSyntaxTree();
		return tranMap;
	}
	
	public Set<Integer> getBeginState() throws IOException
	{
		createSyntaxTree();
		return tree.firstpos();
	}
	
	private void move() throws IOException
	{
		look = lexer.scan();
	}
	
	private void match(char t) throws IOException
	{
		if(look==t) move();
		else throw new Error("syntax error");
	}
	
	public DFANode createSyntaxTree() throws IOException
	{
		if(tree==null)
		{
			tree = or();
			// 在末尾添加#节点
			DFALeaf endLeaf = new DFALeaf('#', ++leafPst);
			tree = new DFACat(tree, endLeaf);
			leafMap.put(leafPst, endLeaf);
			// 计算followpos
			createFollowpos(tree);
			// 计算所有状态路径
			calculateAllPath(tree.firstpos());
		}
		return tree;
	}

	private void createFollowpos(DFANode node)
	{
		DFANode[] children = node.getChildren();
		if(children!=null)
		{
			for(DFANode child : children) createFollowpos(child);
		}
		if(node instanceof DFACat)
		{
			DFACat cat = (DFACat) node;
			Set<Integer> leftLastSet = cat.left.lastpos();
			Set<Integer> rightFirstSet = cat.right.firstpos();
			for(Integer i : leftLastSet)
			{
				Set<Integer> followSet = followMap.get(i);
				if(followSet==null)
				{
					followSet = new HashSet<Integer>();
					followMap.put(i, followSet);
				}
				followSet.addAll(rightFirstSet);
			}
		}
		else if(node instanceof DFAStar)
		{
			DFAStar star = (DFAStar) node;
			Set<Integer> lastSet = star.lastpos();
			Set<Integer> firstSet = star.firstpos();
			for(Integer i : lastSet)
			{
				Set<Integer> followSet = followMap.get(i);
				if(followSet==null)
				{
					followSet = new HashSet<Integer>();
					followMap.put(i, followSet);
				}
				followSet.addAll(firstSet);
			}
		}
	}
	
	private void calculateAllPath(Set<Integer> beginState)
	{
		if(!states.contains(beginState))
		{
			states.add(beginState);
			for(char c : charSet)
			{
				Set<Integer> endState = new HashSet<Integer>();
				for(int i : beginState)
				{
					if(leafMap.get(i).c==c)
					{
						endState.addAll(followMap.get(i));
					}
				}
				if(!endState.isEmpty())
				{
					Map<Character, Set<Integer>> tempMap = tranMap.get(beginState);
					if(tempMap==null)
					{
						tempMap = new HashMap<Character, Set<Integer>>();
						tranMap.put(beginState, tempMap);
					}
					tempMap.put(c, endState);
					calculateAllPath(endState);
				}
			}
		}
	}

	private DFANode or() throws IOException
	{
		DFANode n = cat();
		while(look=='|')
		{
			move();
			n = new DFAOr(n, cat());
		}
		return n;
	}
	
	private DFANode cat() throws IOException
	{
		DFANode n = plus();
		while(look!='|'&&look!='*'&&look!=')'&&look!='+'&&look!=-1)
		{
			n = new DFACat(n, plus());
		}
		return n;
	}
	
	private DFANode plus() throws IOException
	{
		DFANode n = star();
		while(look=='+')
		{
			move();
			n = new DFACat(n, new DFAStar(n));
		}
		return n;
	}
	
	private DFANode star() throws IOException
	{
		DFANode n = leaf();
		while(look=='*')
		{
			move();
			n = new DFAStar(n);
		}
		return n;
	}
	
	private DFANode leaf() throws IOException
	{
		DFANode n = null;
		if(look=='(')
		{
			move();
			n = or();
			match(')');
		}
		else if(look!=-1)
		{
			DFALeaf leaf = new DFALeaf((char)look, ++leafPst);
			leafMap.put(leafPst, leaf);
			charSet.add((char)look);
			n = leaf;
			move();
		}
		return n;
	}
	
	public static void main(String[] args) throws IOException
	{
		String regex = "(a|b)*abb";
		ByteArrayInputStream bais = new ByteArrayInputStream(regex.getBytes());
		DFALexer lexer = new DFALexer(bais);
		DFAParser parser = new DFAParser(lexer);
		DFANode node = parser.createSyntaxTree();
		System.out.println(node.firstpos());
		System.out.println(node.lastpos());
		for(int i=1;i<=parser.leafPst;++i)
		{
			System.out.println(i + " : " + parser.followMap.get(i));
		}
		for(Set<Integer> state : parser.tranMap.keySet())
		{
			Map<Character, Set<Integer>> pathMap = parser.tranMap.get(state);
			for(char c : pathMap.keySet())
			{
				System.out.println(state + " -- " + c + " -->" + pathMap.get(c));
			}
		}
		System.out.println(node);
	}
}
