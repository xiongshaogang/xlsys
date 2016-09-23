package xlsys.base.test;

import java.util.UUID;

import xlsys.base.util.StringUtil;

public class TempTest
{

	public static void main(String[] args) throws Exception
	{
		for(int i=0;i<100;++i)
		{
			System.out.println(StringUtil.getMD5String(UUID.randomUUID().toString()));
		}
	}
}
