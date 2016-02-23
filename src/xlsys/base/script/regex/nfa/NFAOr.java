package xlsys.base.script.regex.nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NFAOr extends NFANode
{
	private NFANode left;
	private NFANode right;
	
	public NFAOr(NFAParser parser, NFANode left, NFANode right)
	{
		super(parser);
		this.left = left;
		this.right = right;
	}

	@Override
	protected NFANode[] getChildren()
	{
		return new NFANode[]{left, right};
	}
	
	@Override
	public String toString()
	{
		return "("+left.toString() + "|" + right.toString()+")";
	}

	@Override
	protected void doCreateTranMap()
	{
		left.createTranMap();
		right.createTranMap();
		beginPst = parser.getNextPst();
		endPst = parser.getNextPst();
		// 本节点的开始位置通过空串可到达左右子节点的开始位置
		Map<Integer, Set<Integer>> pathMap = new HashMap<Integer, Set<Integer>>();
		Set<Integer> toPstSet = new HashSet<Integer>();
		toPstSet.add(left.beginPst);
		toPstSet.add(right.beginPst);
		pathMap.put(NFARegex.NULL_STR, toPstSet);
		tranMap.put(beginPst, pathMap);
		// 左右子节点的结束位置通过空串可到达本节点的结束位置
		pathMap = new HashMap<Integer, Set<Integer>>();
		toPstSet = new HashSet<Integer>();
		toPstSet.add(endPst);
		pathMap.put(NFARegex.NULL_STR, toPstSet);
		tranMap.put(left.endPst, pathMap);
		tranMap.put(right.endPst, pathMap);
	}
}
