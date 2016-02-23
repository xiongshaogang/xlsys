package xlsys.base.script.complier.study.inter;

public class Node
{
	protected static int labels = 0;
	
	private int lexline;
	
	protected Node(int lexline)
	{
		this.lexline = lexline;
	}
	
	private void error(String s)
	{
		throw new Error("near line " + lexline + ": " + s);
	}
	
	public int newlabel()
	{
		return ++labels;
	}
	
	public void emitlabel(int i)
	{
		System.out.println("L" + i + ":");
	}
	
	public void emit(String s)
	{
		System.out.println("\t" + s);
	}
}
