package xlsys.base.script.regex.nfa;

public class NFACat extends NFANode
{
	private NFANode left;
	private NFANode right;
	
	public NFACat(NFAParser parser, NFANode left, NFANode right)
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
		return left.toString() + right.toString();
	}

	@Override
	protected void doCreateTranMap()
	{
		left.createTranMap();
		right.createTranMap();
		// 本节点的开始位置为左子节点的开始位置
		beginPst = left.beginPst;
		// 本节点的结束位置为右子节点的结束位置
		endPst = right.endPst;
		// 合并左子节点的结束位置和右子节点的开始位置.这里实现为将右子节点的开始位置替换为左子节点的结束位置
		right.replacePst(right.beginPst, left.endPst);
	}
}
