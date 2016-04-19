package xlsys.base.test;

import java.math.BigDecimal;
import java.util.Calendar;

public class TempTest
{
	
	public static void main(String[] args) throws Exception
	{
		/*Calendar c = Calendar.getInstance();
		Long l = new Long("1460252614293");
		// c.setTimeInMillis(1460252614293l);
		c.set(2016, 3, 20);
		System.out.println(c.getTimeInMillis());*/
		BigDecimal b1 = new BigDecimal("1");
		BigDecimal b2 = new BigDecimal(1);
		System.out.println(b1==b2);
		System.out.println(b1.equals(b2));
	}
}
