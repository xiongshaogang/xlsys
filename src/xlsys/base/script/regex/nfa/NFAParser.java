package xlsys.base.script.regex.nfa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NFAParser
{
	private NFALexer lexer;
	private int look;
	protected NFANode tree;
	protected Map<Integer, Map<Integer, Set<Integer>>> tranMap;
	private int pstCounter;
	
	public NFAParser(NFALexer lexer) throws IOException
	{
		this.lexer = lexer;
		pstCounter = -1;
		move();
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
	
	public synchronized int getNextPst()
	{
		return ++pstCounter;
	}
	
	public NFANode createSyntaxTree() throws IOException
	{
		if(tree==null)
		{
			tree = or();
			// 建立构造NFA
			tree.createTranMap();
			// 合并所有的NFA,得到最终的NFA
			tranMap = new HashMap<Integer, Map<Integer, Set<Integer>>>();
			mergeAllNFA(tree);
		}
		return tree;
	}
	
	private void mergeAllNFA(NFANode node)
	{
		NFANode[] children = node.getChildren();
		if(children!=null)
		{
			for(NFANode child : children) mergeAllNFA(child);
		}
		// 将当前节点的tranMap并入
		for(Integer fromPst : node.tranMap.keySet())
		{
			Map<Integer, Set<Integer>> allPathMap = tranMap.get(fromPst);
			if(allPathMap==null)
			{
				allPathMap = new HashMap<Integer, Set<Integer>>();
				tranMap.put(fromPst, allPathMap);
			}
			Map<Integer, Set<Integer>> pathMap = node.tranMap.get(fromPst);
			for(Integer c : pathMap.keySet())
			{
				Set<Integer> allToPstSet = allPathMap.get(c);
				if(allToPstSet==null)
				{
					allToPstSet = new HashSet<Integer>();
					allPathMap.put(c, allToPstSet);
				}
				allToPstSet.addAll(pathMap.get(c));
			}
		}
	}

	private NFANode or() throws IOException
	{
		NFANode n = cat();
		while(look=='|')
		{
			move();
			n = new NFAOr(this, n, cat());
		}
		return n;
	}
	
	private NFANode cat() throws IOException
	{
		NFANode n = plus();
		while(look!='|'&&look!='*'&&look!=')'&&look!='+'&&look!=-1)
		{
			n = new NFACat(this, n, plus());
		}
		return n;
	}
	
	private NFANode plus() throws IOException
	{
		NFANode n = star();
		while(look=='+')
		{
			move();
			n = new NFACat(this, n, new NFAStar(this, n));
		}
		return n;
	}
	
	private NFANode star() throws IOException
	{
		NFANode n = leaf();
		while(look=='*')
		{
			move();
			n = new NFAStar(this, n);
		}
		return n;
	}
	
	private NFANode leaf() throws IOException
	{
		NFANode n = null;
		if(look=='(')
		{
			move();
			n = or();
			match(')');
		}
		else if(look!=-1)
		{
			n = new NFALeaf(this, (char)look);
			move();
		}
		return n;
	}
	
	public static void main(String[] args) throws IOException
	{
		String regex = "(a|b)*abb";
		ByteArrayInputStream bais = new ByteArrayInputStream(regex.getBytes());
		NFALexer lexer = new NFALexer(bais);
		NFAParser parser = new NFAParser(lexer);
		parser.createSyntaxTree();
		for(Integer fromPst : parser.tranMap.keySet())
		{
			Map<Integer, Set<Integer>> pathMap = parser.tranMap.get(fromPst);
			for(Integer c : pathMap.keySet())
			{
				Set<Integer> toPstSet = pathMap.get(c);
				for(Integer toPst : toPstSet)
				{
					System.out.print(fromPst + " -- [");
					if(c!=-1) System.out.print(""+(char)(int)c);
					System.out.println("] --> " + toPst);
				}
			}
		}
	}
}
