package xlsys.base.script.regex.nfa;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class NFANode
{
	protected NFAParser parser;
	// Map<位置, Map<经过符号, 到达位置>>
	protected Map<Integer, Map<Integer, Set<Integer>>> tranMap;
	// 开始位置
	protected Integer beginPst;
	// 结束位置
	protected Integer endPst;
	
	protected NFANode(NFAParser parser)
	{
		this.parser = parser;
		
	}
	
	protected abstract NFANode[] getChildren();
	
	/**
	 * 构建NFA
	 */
	protected void createTranMap()
	{
		if(tranMap==null)
		{
			tranMap = new HashMap<Integer, Map<Integer, Set<Integer>>>();
			doCreateTranMap();
		}
	}

	protected abstract void doCreateTranMap();
	
	protected void replacePst(Integer oldPst, Integer newPst)
	{
		if(tranMap!=null)
		{
			Integer[] fromPsts = tranMap.keySet().toArray(new Integer[0]);
			for(Integer fromPst : fromPsts)
			{
				// 先替换toPstSet
				Map<Integer, Set<Integer>> pathMap = tranMap.get(fromPst);
				for(Integer c : pathMap.keySet())
				{
					Set<Integer> toPstSet = pathMap.get(c);
					if(toPstSet.contains(oldPst))
					{
						toPstSet.remove(oldPst);
						toPstSet.add(newPst);
					}
				}
				// 再替换fromPst
				if(fromPst==oldPst)
				{
					tranMap.remove(oldPst);
					tranMap.put(newPst, pathMap);
				}
			}
		}
	}
}
