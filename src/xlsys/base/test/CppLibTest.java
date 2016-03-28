package xlsys.base.test;

import java.io.IOException;

import xlsys.base.exception.NativeException;
import xlsys.base.io.util.FileUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.io.util.VideoUtil;
import xlsys.base.util.EDCoder;

public class CppLibTest
{
	
	public static void testEDCoder() throws IOException, NativeException
	{
		Runtime.getRuntime().load("D:/work/code/MyProject/XlsysBase/cpplib/libCppXlsysBase.dll");
		byte[] src = FileUtil.getByteFromFile("Koala.jpg");
		byte[] target = IOUtil.compressC(src);
		byte[] src1 = IOUtil.decompressC(target);
		FileUtil.writeFile("testZip.jpg", src1);
		
		EDCoder edCoder = EDCoder.getDefaultInstance();
		target = edCoder.base64Encode(src);
		src1 = edCoder.base64Decode(target);
		FileUtil.writeFile("testBase64.jpg", src1);
		
		target = edCoder.encrypt(src);
		src1 = edCoder.decrypt(target);
		FileUtil.writeFile("testCrypt.jpg", src1);
	}
	
	public static void testVideoUtil() throws IOException, NativeException
	{
		Runtime.getRuntime().load("D:/work/code/MyProject/xlsys.base/cpplib/libXlsysJni.dll");
		Runtime.getRuntime().load("D:/work/code/MyProject/xlsys.base/cpplib/libXlsysVideo.dll");
		VideoUtil.remuxingVideoFile("d:/1.flv", "d:/1.mp4");
	}

	public static void main(String[] args) throws Exception
	{
		//Runtime.getRuntime().load("D:/work/code/MyProject/XlsysBase/cpplib/libCppXlsysBase.dll");
		//EDCoder.generateKey(EDCoder.SECRET_KEY);
		// testEDCoder();
		testVideoUtil();
	}

}
