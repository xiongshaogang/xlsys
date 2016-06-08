package xlsys.base.test;

import java.util.LinkedHashSet;
import java.util.Set;

import xlsys.base.model.PairModel;

public class TempTest
{
	
	public static void main(String[] args) throws Exception
	{
		PairModel<String, String> a = new PairModel<String, String>("a", "b");
		PairModel<String, String> b = new PairModel<String, String>("a", "b");
		Set<PairModel<String, String>> set = new LinkedHashSet<PairModel<String, String>>();
		set.add(a);
		set.add(b);
		System.out.println(set.size());
	}
}
