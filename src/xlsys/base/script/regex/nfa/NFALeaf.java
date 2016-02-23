package xlsys.base.script.regex.nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NFALeaf extends NFANode
{
	protected char c;
	
	protected NFALeaf(NFAParser parser, char c)
	{
		super(parser);
		this.c = c;
	}

	@Override
	protected NFANode[] getChildren()
	{
		return null;
	}

	@Override
	public String toString()
	{
		return ""+c;
	}

	@Override
	protected void doCreateTranMap()
	{
		beginPst = parser.getNextPst();
		endPst = parser.getNextPst();
		Map<Integer, Set<Integer>> pathMap = new HashMap<Integer, Set<Integer>>();
		Set<Integer> toPstSet = new HashSet<Integer>();
		toPstSet.add(endPst);
		pathMap.put((int)c, toPstSet);
		tranMap.put(beginPst, pathMap);
	}
}
