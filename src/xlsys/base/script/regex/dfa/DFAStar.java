package xlsys.base.script.regex.dfa;

import java.util.Set;

public class DFAStar extends DFANode
{
	protected DFANode child;
	
	protected DFAStar(DFANode child)
	{
		this.child = child;
	}

	@Override
	public String toString()
	{
		return "("+child.toString() + ")*";
	}

	@Override
	protected boolean calculateNullable()
	{
		return true;
	}

	@Override
	protected Set<Integer> calculateFirstpos()
	{
		return child.firstpos();
	}

	@Override
	protected Set<Integer> calculateLastpos()
	{
		return child.lastpos();
	}
	
	@Override
	protected DFANode[] getChildren()
	{
		return new DFANode[]{child};
	}
}
