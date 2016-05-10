package xlsys.base.test;

public class TempTest
{
	
	public static void main(String[] args) throws Exception
	{
		String dataSource = "jdbc:mysql://192.168.1.37:3306/golf";
		String[] params = dataSource.split("/");
		System.out.println(params[params.length-1]);
	}
}
