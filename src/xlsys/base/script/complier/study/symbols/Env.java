package xlsys.base.script.complier.study.symbols;

import java.util.HashMap;
import java.util.Map;

import xlsys.base.script.complier.study.inter.Id;
import xlsys.base.script.complier.study.lexer.Token;

public class Env
{
	private Map<Token, Id> table;
	protected Env parent;
	
	public Env(Env parent)
	{
		table = new HashMap<Token, Id>();
		this.parent = parent;
	}
	
	public void put(Token w, Id i)
	{
		table.put(w, i);
	}
	
	public Id get(Token w)
	{
		for(Env e = this;e!=null;e=e.parent)
		{
			Id found = e.table.get(w);
			if(found!=null) return found;
		}
		return null;
	}
}
