package xlsys.base.script.regex.dfa;

import java.util.HashSet;
import java.util.Set;

public class DFALeaf extends DFANode
{
	protected char c;
	private int position;
	
	protected DFALeaf(char c, int position)
	{
		this.c = c;
		this.position = position;
	}
	
	protected int getPosition()
	{
		return position;
	}

	@Override
	public String toString()
	{
		return ""+c;
	}

	@Override
	protected boolean calculateNullable()
	{
		return false;
	}

	@Override
	protected Set<Integer> calculateFirstpos()
	{
		Set<Integer> set = new HashSet<Integer>();
		set.add(position);
		return set;
	}

	@Override
	protected Set<Integer> calculateLastpos()
	{
		Set<Integer> set = new HashSet<Integer>();
		set.add(position);
		return set;
	}

	@Override
	protected DFANode[] getChildren()
	{
		return null;
	}
}
