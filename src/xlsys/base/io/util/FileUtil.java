package xlsys.base.io.util;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import xlsys.base.io.attachment.XlsysAttachment;
import xlsys.base.util.StringUtil;

/**
 * 文件工具
 * @author Lewis
 *
 */
public class FileUtil
{
	private static final int FILE_LIST_TYPE_PATH = 0;
	private static final int FILE_LIST_TYPE_FILE = 1;

	public static final String ALGORITHM_MD5 = "MD5";
	public static final String ALGORITHM_SHA_1 = "SHA-1";
	public static final String ALGORITHM_SHA_256 = "SHA-256";
	public static final String ALGORITHM_SHA_384 = "SHA-384";
	public static final String ALGORITHM_SHA_512 = "SHA-512";

	public static String getAlgorithmValue(String filePath, String algorithm) throws NoSuchAlgorithmException, IOException
	{
		FileInputStream fis = IOUtil.getFileInputStream(filePath);
		byte[] buffer = new byte[1024];
		MessageDigest md5 = MessageDigest.getInstance(algorithm);
		int numRead = 0;
		while ((numRead = fis.read(buffer)) > 0)
		{
			md5.update(buffer, 0, numRead);
		}
		fis.close();
		return StringUtil.byteToHexString(md5.digest());
	}

	public static byte[] getByteFromFile(String filePath) throws IOException
	{
		byte[] bytes = null;
		FileInputStream fis = null;
		try
		{
			fis = IOUtil.getFileInputStream(filePath);
			bytes = IOUtil.readBytesFromInputStream(fis, -1);
		}
		catch(IOException e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(fis);
		}
		return bytes;
	}

	public static String getFileMd5(byte[] fileData) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance(ALGORITHM_MD5);
		md.update(fileData);
		return StringUtil.byteToHexString(md.digest());
	}
	
	public static boolean exists(String filePath)
	{
		File file = new File(filePath);
		return file.exists();
	}

	public static boolean createNewFile(String path) throws IOException
	{
		File file = new File(path);
		createParentPath(file);
		return file.createNewFile();
	}

	/*
	 * 遍历所有子目录,只返回文件路径
	 */
	public static List<String> getSubFilePath(String directoryPath)
	{
		List<String> list = new ArrayList<String>();
		File directory = new File(directoryPath);
		subFiles(directory, list, FILE_LIST_TYPE_PATH);
		return list;
	}

	/*
	 * 遍历所有子目录,只返回文件
	 */
	public static List<File> getSubFiles(File directory)
	{
		List<File> list = new ArrayList<File>();
		subFiles(directory, list, FILE_LIST_TYPE_FILE);
		return list;
	}

	@SuppressWarnings("unchecked")
	private static void subFiles(File file, @SuppressWarnings("rawtypes") List list, int listType)
	{
		if (file.isDirectory())
		{
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				subFiles(files[i], list, listType);
			}
		}
		else
		{
			if (listType == FILE_LIST_TYPE_FILE)
			{
				list.add(file);
			}
			else if (listType == FILE_LIST_TYPE_PATH)
			{
				list.add(file.getAbsolutePath());
			}
		}
	}

	public static boolean createParentPath(File file)
	{
		if(!file.exists())
		{
			File newFile = new File(file.getAbsolutePath());
			File parent = newFile.getParentFile();
			return parent.mkdirs();
		}
		return false;
	}
	
	public static boolean createParentPath(String filePath)
	{
		File file = new File(filePath);
		return createParentPath(file);
		
	}

	public static boolean rename(File srcFile, String newName)
	{
		File file = new File(srcFile.getParent() + File.separator + newName);
		createParentPath(file);
		return srcFile.renameTo(file);
	}

	public static boolean cutFile(File srcFile, String descDir)
	{
		File file = new File(descDir + File.separator + srcFile.getName());
		createParentPath(file);
		return srcFile.renameTo(file);
	}

	public static boolean renameTo(File srcFile, File descFile)
	{
		createParentPath(descFile);
		return srcFile.renameTo(descFile);
	}

	public static boolean copyFile(File srcFile, String descPath)
	{
		boolean flag = true;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try
		{
			bis = IOUtil.getBufferedInputStream(srcFile.getAbsolutePath());
			File fosFile = new File(descPath);
			createParentPath(fosFile);
			bos = IOUtil.getBufferedOutputStream(descPath, false);
			IOUtil.writeBytesFromIsToOs(bis, -1, bos);
		}
		catch (Exception e)
		{
			flag = false;
		}
		finally
		{
			IOUtil.close(bis);
			IOUtil.close(bos);
		}
		return flag;
	}

	/*
	 * isSub : 放在descPath下的子目录里
	 */
	public static void xcopyToSub(File srcFile, String descPath)
	{
		if (srcFile.isDirectory())
		{
			xcopy(srcFile, descPath + File.separator + srcFile.getName());
		}
		else
		{
			copyFile(srcFile, descPath);
		}
	}

	/*
	 * 将src下的所有文件复制进desc下,不含src和desc目录
	 */
	public static void subXcopy(File srcFile, String descPath)
	{
		if (srcFile.isDirectory())
		{
			File[] files = srcFile.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				xcopy(files[i], descPath + File.separator + files[i].getName());
			}
		}
		else
		{
			copyFile(srcFile, descPath);
		}
	}

	public static void xcopy(File srcFile, String descPath)
	{
		if (srcFile.isDirectory())
		{
			File dir = new File(descPath);
			dir.mkdirs();
			File[] files = srcFile.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				xcopy(files[i], descPath + File.separator + files[i].getName());
			}
		}
		else
		{
			copyFile(srcFile, descPath);
		}
	}

	public static boolean deleteDir(File file)
	{
		boolean flag = true;
		if (file.isDirectory())
		{
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				if (files[i].isDirectory())
				{
					flag = deleteDir(files[i]);
				}
				else
				{
					flag = files[i].delete();
				}
				if (!flag)
				{
					break;
				}
			}
			flag = file.delete();
		}
		return flag;
	}

	public static void replaceAll(String filePath, String regex,
			String replacement)
	{
		BufferedReader br = null;
		PrintWriter pw = null;
		try
		{
			br = IOUtil.getBufferedReader(filePath);
			List<String> list = new ArrayList<String>();
			String str = null;
			while ((str = br.readLine()) != null)
			{
				str = str.replaceAll(regex, replacement);
				list.add(str);
			}
			IOUtil.close(br);
			pw = IOUtil.getPrintWriter(filePath, false);
			for (int i = 0; i < list.size(); i++)
			{
				pw.println(list.get(i));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			IOUtil.close(br);
			IOUtil.close(pw);
		}
	}
	
	public static String getFileSuffix(File file)
	{
		return getFileSuffix(file.getAbsolutePath());
	}
	
	public static String getFileSuffix(String filePath)
	{
		String suffix = "";
		int idx = filePath.lastIndexOf('.');
		if(idx!=-1) suffix = filePath.substring(idx+1);
		return suffix;
	}
	
	public static void openFile(File file) throws IOException
	{
		Desktop desk = Desktop.getDesktop();
		desk.open(file); //调用open（File f）方法打开文件 
	}
	
	public static void openFile(String filePath) throws IOException
	{
		openFile(new File(filePath));
	}
	
	public static void openFile(String suffix, byte[] fileBytes)
	{
		FileOutputStream fos = null;
		File file = null;
		try
		{
			file = Files.createTempFile("temp_", suffix).toFile();
			fos = new FileOutputStream(file);
			fos.write(fileBytes);
			fos.close();
			fos = null;
			openFile(file);
			file.deleteOnExit();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			IOUtil.close(fos);
		}
	}
	
	public static void writeFile(String filePath, byte[] fileBytes) throws IOException
	{
		FileOutputStream fos = null;
		try
		{
			fos = IOUtil.getFileOutputStream(filePath, false);
			fos.write(fileBytes);
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(fos);
		}
	}
	
	/**
	 * 根据文件路径创建附件实例
	 * @param filePath
	 * @param attachStyle
	 * @return
	 */
	public static XlsysAttachment createAttachment(String filePath, int attachStyle)
	{
		File file = new File(filePath);
		return createAttachment(file, file.getName(), attachStyle);
	}
	
	/**
	 * 根据文件和指定文件名创建附件实例
	 * @param filePath
	 * @param attachStyle
	 * @return
	 */
	public static XlsysAttachment createAttachment(File file, String fileName, int attachStyle)
	{
		XlsysAttachment attachment = null;
		if(file.exists()&&file.isFile())
		{
			try
			{
				byte[] bytes = FileUtil.getByteFromFile(file.getAbsolutePath());
				attachment = new XlsysAttachment(fileName, file.lastModified(), attachStyle, bytes, false, getFileMd5(bytes));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return attachment;
	}
	
	/**
	 * 修正拼接的文件路径中的常见问题。目前会修正以下问题
	 * 1. 连续出现两个或两个以上/时，自动替换成一个/
	 * @param filePath
	 * @return
	 */
	public static String fixFilePath(String filePath)
	{
		return filePath.replaceAll("/{2,}+", "/");
	}
	
	public static String readTextContentChecked(String resource, ClassLoader classLoader) throws IOException
	{
		InputStream stream = classLoader.getResourceAsStream(resource);
	    if(stream == null) throw new IllegalArgumentException("Not found: " + resource);
	    try
	    {
	    	BufferedReader reader = new BufferedReader( new InputStreamReader(stream, "UTF-8"));
	    	return readLines(reader);
	    }
	    finally
	    {
	      IOUtil.close(stream);
	    }
	}

	private static String readLines(BufferedReader reader) throws IOException
	{
		StringBuilder builder = new StringBuilder();
		String line = reader.readLine();
		while(line != null)
		{
			builder.append(line);
			builder.append('\n');
			line = reader.readLine();
	    }
	    return builder.toString();
	}
}
