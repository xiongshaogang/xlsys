package xlsys.base.test;

public class TempTest
{

	public static void main(String[] args) throws Exception
	{
		String attrValue = "#AF2d3a";
		System.out.println(attrValue.matches("#[0-9a-fA-F]{6}"));
	}
}
