package xlsys.base.script.regex.nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NFAStar extends NFANode
{
	private NFANode child;
	
	public NFAStar(NFAParser parser, NFANode child)
	{
		super(parser);
		this.child = child;
	}

	@Override
	protected NFANode[] getChildren()
	{
		return new NFANode[]{child};
	}
	
	@Override
	public String toString()
	{
		return "("+child.toString() + ")*";
	}

	@Override
	protected void doCreateTranMap()
	{
		child.createTranMap();
		beginPst = parser.getNextPst();
		endPst = parser.getNextPst();
		// 本节点的开始位置通过空串可到达子节点的开始位置和本节点的结束位置
		Map<Integer, Set<Integer>> pathMap = new HashMap<Integer, Set<Integer>>();
		Set<Integer> toPstSet = new HashSet<Integer>();
		toPstSet.add(child.beginPst);
		toPstSet.add(endPst);
		pathMap.put(NFARegex.NULL_STR, toPstSet);
		tranMap.put(beginPst, pathMap);
		// 子节点的结束位置通过空串可到达子节点的开始位置和本节点的结束位置
		pathMap = new HashMap<Integer, Set<Integer>>();
		toPstSet = new HashSet<Integer>();
		toPstSet.add(child.beginPst);
		toPstSet.add(endPst);
		pathMap.put(NFARegex.NULL_STR, toPstSet);
		tranMap.put(child.endPst, pathMap);
	}
}
