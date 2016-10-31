package xlsys.base.test;

import xlsys.base.io.util.IOUtil;

public class TempTest
{

	public static void main(String[] args) throws Exception
	{
		String str = "时代覅回复,948682451!@##$$%^%&&*(:\"~";
		byte[] srcBytes = str.getBytes("utf-8");
		String hexStr = IOUtil.bytesToHexStr(srcBytes);
		System.out.println(hexStr);
		byte[] targetBytes = IOUtil.hexStrToBytes(hexStr);
		String targetStr = new String(targetBytes, "utf-8");
		System.out.println(targetStr);
	}
}
