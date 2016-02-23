package xlsys.base.script.regex.dfa;

import java.util.HashSet;
import java.util.Set;

public class DFACat extends DFANode
{
	protected DFANode left;
	protected DFANode right;
	
	protected DFACat(DFANode left, DFANode right)
	{
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString()
	{
		return left.toString() + right.toString();
	}

	@Override
	protected boolean calculateNullable()
	{
		return left.nullable()&&right.nullable();
	}

	@Override
	protected Set<Integer> calculateFirstpos()
	{
		Set<Integer> set = null;
		if(left.nullable())
		{
			set = new HashSet<Integer>();
			set.addAll(left.firstpos());
			set.addAll(right.firstpos());
		}
		else set = left.firstpos();
		return set;
	}

	@Override
	protected Set<Integer> calculateLastpos()
	{
		Set<Integer> set = null;
		if(right.nullable())
		{
			set = new HashSet<Integer>();
			set.addAll(right.lastpos());
			set.addAll(left.lastpos());
		}
		else set = right.lastpos();
		return set;
	}

	@Override
	protected DFANode[] getChildren()
	{
		return new DFANode[]{left, right};
	}
}
