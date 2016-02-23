package xlsys.base.test;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class MatchesTest
{

	public static void main(String[] args) throws IOException
	{
		//String str = "123.22";
		//System.out.println(str.matches("[0-9]+(\\.[0-9]+)?"));

		//String filePath = "e://111///222/333//444////55/66";
		//System.out.println(filePath.replaceAll("/{2,}+", "/"));
		

		/*ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedImage bufferedImage = ImageIO.read(new File(""));
		ImageIO.write(bufferedImage, "jpg", baos);
		baos.toByteArray();*/
		
		/*boolean[] a = new boolean[2];
		System.out.println(a[0]);*/
		
		/*Class<?>[] classes = StorableTreeDataSet.class.getInterfaces();
		for(Class<?> c : classes)
		{
			System.out.println(c.getName());
			Method[] mArr = c.getDeclaredMethods();
			for(Method m : mArr)
			{
				System.out.println("\t"+m.getName());
			}
		}*/
		/*byte[] b = new byte[2];
		Serializable s = b;
		System.out.println(s instanceof Object[]);*/
		// System.out.println("123".compareTo(""));
		/*BigDecimal num = new BigDecimal("0.22");
		DecimalFormat df = new DecimalFormat("#,##0.00\u2030");
		System.out.println('\u2030');
		System.out.println(df.format(num));*/
		/*int a = 10;
		Object b = a;
		System.out.println(b.getClass());
		System.out.println(Number.class.isAssignableFrom(b.getClass()));
		String str = null;
		if(str instanceof String)
		{
			System.out.println("111111111111111111111");
		}*/
		/*System.out.println(System.getProperties());*/
		Set<Integer> set = new TreeSet<Integer>();
		set.add(3);
		set.add(7);
		set.add(2);
		set.add(5);
		set.add(1);
		for(Integer i : set) System.out.println(i);
	}

}
