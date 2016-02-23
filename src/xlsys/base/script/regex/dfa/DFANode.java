package xlsys.base.script.regex.dfa;

import java.util.Set;

public abstract class DFANode
{
	private Boolean canNull;
	private Set<Integer> firstSet;
	private Set<Integer> lastSet;
	
	public boolean nullable()
	{
		if(canNull==null) canNull = calculateNullable();
		return canNull;
	}
	
	protected abstract boolean calculateNullable();
	
	public Set<Integer> firstpos()
	{
		if(firstSet==null) firstSet = calculateFirstpos();
		return firstSet;
	}
	
	protected abstract Set<Integer> calculateFirstpos();
	
	public Set<Integer> lastpos()
	{
		if(lastSet==null) lastSet = calculateLastpos();
		return lastSet;
	}
	
	protected abstract Set<Integer> calculateLastpos();
	
	protected abstract DFANode[] getChildren();
}