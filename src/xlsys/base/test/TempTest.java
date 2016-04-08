package xlsys.base.test;

import java.util.Calendar;

public class TempTest
{
	
	public static void main(String[] args) throws Exception
	{
		Calendar c = Calendar.getInstance();
		Long l = new Long("1460252614293");
		c.setTimeInMillis(1460252614293l);
		//c.set(2016, 3, 10);
		System.out.println(c.getTimeInMillis());
	}
}
