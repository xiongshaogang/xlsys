package xlsys.base.script.regex.dfa;

import java.util.HashSet;
import java.util.Set;

public class DFAOr extends DFANode
{
	private DFANode left;
	private DFANode right;
	
	protected DFAOr(DFANode left, DFANode right)
	{
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString()
	{
		return "("+left.toString() + "|" + right.toString()+")";
	}

	@Override
	protected boolean calculateNullable()
	{
		return left.nullable() || right.nullable();
	}

	@Override
	protected Set<Integer> calculateFirstpos()
	{
		Set<Integer> set = new HashSet<Integer>();
		set.addAll(left.firstpos());
		set.addAll(right.firstpos());
		return set;
	}

	@Override
	protected Set<Integer> calculateLastpos()
	{
		Set<Integer> set = new HashSet<Integer>();
		set.addAll(left.lastpos());
		set.addAll(right.lastpos());
		return set;
	}
	
	@Override
	protected DFANode[] getChildren()
	{
		return new DFANode[]{left, right};
	}
}
