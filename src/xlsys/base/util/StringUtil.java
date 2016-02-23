package xlsys.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import xlsys.base.io.util.IOUtil;

/**
 * 字符串工具类
 * @author Lewis
 *
 */
public class StringUtil
{
	public final static String REGEX_ANYSTRING = "(.|\n|\r\n|\r|\u0085|\u2028|\u2029)*";

	private static char[] hexChar =
	{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	public static String byteToHexString(byte[] b)
	{
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++)
		{
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * 按照匹配正则表达式的值将字符串进行拆分
	 * @param src 原始字符串
	 * @param regex 正则表达式
	 * @param maxLen 匹配正则表达式的最大字符串长度，-1为不限定，如果不限定则性能较低
	 * @return
	 */
	public static String[] split(String src, String regex, int maxLen)
	{
		List<String> list = new ArrayList<String>();
		int fromIndex = 0;
		int[] idx = null;
		while((idx=indexOf(src, regex, fromIndex, false, maxLen))[0]!=-1)
		{
			if(fromIndex!=idx[0]) list.add(src.substring(fromIndex, idx[0]));
			list.add(src.substring(idx[0], idx[1]));
			fromIndex = idx[1];
		}
		list.add(src.substring(fromIndex));
		return list.toArray(new String[list.size()]);
	}
	
	/**
	 * 获取str数组对应的MD5值
	 * @param str
	 * @return
	 */
	public static String getMD5String(String ... str)
	{
		String md5 = null;
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			for(int i=0;i<str.length;i++)
			{
				md.update(str[i].getBytes());
			}
			md5 = byteToHexString(md.digest());
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return md5;
	}
	
	/**
	 * 获取byte数组对应的MD5值
	 * @param bytes
	 * @return
	 */
	public static String getMD5String(byte[] bytes)
	{
		String md5 = null;
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bytes);
			md5 = byteToHexString(md.digest());
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return md5;
	}
	
	/**
	 * 根据开始和结束符将str拆分成数组
	 * @param str 源字符串
	 * @param beginChar 开始字符
	 * @param endChar 结束字符
	 * @param reserve 是否保留开始结束符号
	 * @return
	 */
	public static String[] split(String str, char beginChar, char endChar,
			boolean reserve)
	{
		List<String> strList = new ArrayList<String>();
		boolean begin = false;
		String temp = "";
		for (int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			if (begin)
			{
				temp += c;
				if (c == endChar)
				{
					if (reserve)
					{
						strList.add(temp);
					}
					else
					{
						strList.add(temp.substring(1, temp.length() - 1));
					}
					temp = "";
					begin = false;
				}
			}
			else
			{
				if (c == beginChar)
				{
					if (!"".equals(temp))
					{
						strList.add(temp);
						temp = "";
					}
					begin = true;
				}
				temp += c;
			}
		}
		if (!"".equals(temp))
		{
			strList.add(temp);
		}
		String[] strs = new String[strList.size()];
		return strList.toArray(strs);
	}

	/**
	 * 根据开始和结束符将str拆分成数组
	 * @param str 源字符串
	 * @param beginStr 开始字符串
	 * @param endStr 结束字符串
	 * @return
	 */
	public static String[] split(String str, String beginStr, String endStr)
	{
		int bidx = -1;
		int eidx = -1;
		List<String> list = new ArrayList<String>();
		while((bidx=str.indexOf(beginStr, bidx+1))!=-1)
		{
			// 写入bidx之前的字符串
			if(eidx==-1)
			{
				if(bidx!=0) list.add(str.substring(0, bidx));
			}
			else list.add(str.substring(eidx+endStr.length(), bidx));
			eidx = str.indexOf(endStr, bidx+1);
			if(eidx!=-1)
			{
				list.add(str.substring(bidx, eidx+endStr.length()));
				bidx = eidx+endStr.length();
			}
			else
			{
				// 非正常结束，写入剩余的字符串
				list.add(str.substring(bidx));
				bidx = str.length()-1;
			}
		}
		if(eidx!=-1) list.add(str.substring(eidx+endStr.length()));
		if(list.isEmpty()) list.add(str);
		return list.toArray(new String[list.size()]);
	}

	/**
	 * 检查是否是空串
	 * @param str 源字符串
	 * @return
	 */
	public static boolean isNullStr(String... str)
	{
		boolean b = false;
		for (int i = 0; i < str.length; i++)
		{
			b = str[i] == null || "".equals(str[i]);
			if (b)
			{
				break;
			}
		}
		return b;
	}
	
	/**
	 * 检查是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str)
	{
		if(str!=null) return str.matches("[0-9]+(\\.[0-9]+)?");
		else return false;
	}

	public static String leftTrim(String str)
	{
		for (int i = 0; i < str.length(); i++)
		{
			if (str.charAt(i) != 32)
			{
				str = str.substring(i);
				break;
			}
		}
		return str;
	}

	/**
	 * 去右空格
	 * @param str
	 * @return
	 */
	public static String rightTrim(String str)
	{
		for (int i = str.length() - 1; i >= 0; i--)
		{
			if (str.charAt(i) != 32)
			{
				str = str.substring(0, i + 1);
				break;
			}
		}
		return str;
	}
	
	/**
	 * 去掉字符串中的所有空格
	 * @param str
	 * @return
	 */
	public static String trimAll(String str)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < str.length(); ++i)
		{
			char c = str.charAt(i);
			if (c > '\u0020')
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 通过给定的正则表达式和起始序号，查找指定的符合正则表达式的字符串 
	 * @param str 源字符串
	 * @param regex 要匹配的正则表达式
	 * @return 返回一个大小为2的数组，第一个元素是该匹配字符串的起始位置，第二个参数是结束位置,如果没有找到则都返回-1
	 */
	public static int[] indexOf(String str, String regex)
	{
		return indexOf(str, regex, false);
	}

	/**
	 * 通过给定的正则表达式和起始序号，查找指定的符合正则表达式的字符串 
	 * @param str 源字符串
	 * @param regex 要匹配的正则表达式
	 * @param ignoreCase 是否忽略大小写
	 * @return 返回一个大小为2的数组，第一个元素是该匹配字符串的起始位置，第二个参数是结束位置,如果没有找到则都返回-1
	 */
	public static int[] indexOf(String str, String regex, boolean ignoreCase)
	{
		return indexOf(str, regex, 0, ignoreCase);
	}
	
	/**
	 * 通过给定的正则表达式和起始序号，查找指定的符合正则表达式的字符串 
	 * @param str 源字符串
	 * @param regex 要匹配的正则表达式
	 * @param fromIndex 查找开始序号
	 * @param ignoreCase 是否忽略大小写
	 * @return 返回一个大小为2的数组，第一个元素是该匹配字符串的起始位置，第二个参数是结束位置,如果没有找到则都返回-1
	 */
	public static int[] indexOf(String str, String regex, int fromIndex, boolean ignoreCase)
	{
		return indexOf(str, regex, fromIndex, ignoreCase, -1);
	}
	
	/**
	 * 查找符合正则表达式的值所在的起始位置
	 * @param str 要查找的原始字符串
	 * @param regex 要匹配的正则表达式
	 * @param fromIndex 开始位置
	 * @param ignoreCase 是否忽略大小写
	 * @param maxLen 匹配值的最大长度，-1为不限制
	 * @return 返回一个大小为2的数组，第一个元素是该匹配字符串的起始位置，第二个参数是结束位置,如果没有找到则都返回-1
	 */
	public static int[] indexOf(String str, String regex, int fromIndex, boolean ignoreCase, int maxLen)
	{
		if(ignoreCase)
		{
			str = str.toLowerCase();
			regex = regex.toLowerCase();
		}
		int[] idx = new int[2];
		idx[0] = -1;
		idx[1] = -1;
		boolean match = false;
		for(int i=fromIndex;i<str.length();i++)
		{
			for(int j=i+1;j<=str.length()&&(maxLen<0||(j-i)<=maxLen);j++)
			{
				String sub = str.substring(i, j);
				if(sub.matches(regex))
				{
					match = true;
					idx[0] = i;
					idx[1] = j;
				}
				else
				{
					if(match)
					{
						idx[0] = i;
						idx[1] = j-1;
						match = false;
					}
				}
			}
			if(idx[0]!=-1) break;
		}
		return idx;
	}

	/**
	 * 检查路径结束符号是否存在，不存在则添加"\"返回 只对目录有效，如果是文件则直接返回
	 * @param path
	 * @return
	 */
	public static String addPathEnd(String path)
	{
		File file = new File(path);
		if (file.isDirectory() && !path.endsWith("\\") && !path.endsWith("/"))
		{
			path += File.separator;
		}
		return path;
	}

	/**
	 * 将Java包名转换成路径的形式
	 * @param javaClass
	 * @return
	 */
	public static String transJavaPackToPath(String javaClass)
	{
		String javaPath = "";
		for (int i = 0; i < javaClass.length(); i++)
		{
			char temp = javaClass.charAt(i);
			if (temp != '.')
			{
				javaPath += temp;
			}
			else
			{
				javaPath += File.separator;
			}
		}
		return javaPath;
	}

	/**
	 * 返回首字母大写的字符串
	 * @param str
	 * @return
	 */
	public static String toInitialsUpperCase(String str)
	{
		if (str != null && str.length() > 0)
		{
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
		}
		return str;
	}

	/**
	 * 返回首字母小写的字符串
	 * @param str
	 * @return
	 */
	public static String toInitialsLowerCase(String str)
	{
		if (str != null && str.length() > 0)
		{
			str = str.substring(0, 1).toLowerCase() + str.substring(1);
		}
		return str;
	}

	/**
	 * 将指定文件中的行从后往前重新排列 
	 * @param fromPath 源路径
	 * @param toPath 目标路径
	 * @return
	 */
	public static boolean turnOffFileContent(String fromPath, String toPath)
	{
		boolean success = true;
		BufferedReader br = null;
		PrintWriter pw = null;
		try
		{
			br = IOUtil.getBufferedReader(fromPath);
			ArrayList<String> strList = new ArrayList<String>();
			String str = null;
			while ((str = br.readLine()) != null)
			{
				strList.add(str);
			}
			if (!strList.isEmpty())
			{
				pw = IOUtil.getPrintWriter(toPath, false);
				for (int i = 0; i < strList.size(); i++)
				{
					pw.println(strList.get(strList.size() - i - 1));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			success = false;
		}
		finally
		{
			IOUtil.close(br);
			IOUtil.close(pw);
		}
		return success;
	}

	public static String getAppPath(Class<?> cls)
	{
		// 检查用户传入的参数是否为空
		if (cls == null)
			throw new java.lang.IllegalArgumentException("参数不能为空！");
		ClassLoader loader = cls.getClassLoader();
		// 获得类的全名，包括包名
		String clsName = cls.getName() + ".class";
		// 获得传入参数所在的包
		Package pack = cls.getPackage();
		String path = "";
		// 如果不是匿名包，将包名转化为路径
		if (pack != null)
		{
			String packName = pack.getName();
			// 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
			if (packName.startsWith("java.") || packName.startsWith("javax."))
				throw new java.lang.IllegalArgumentException("不要传送系统类！");
			// 在类的名称中，去掉包名的部分，获得类的文件名
			clsName = clsName.substring(packName.length() + 1);
			// 判定包名是否是简单包名，如果是，则直接将包名转换为路径，
			if (packName.indexOf(".") < 0)
				path = packName + "/";
			else
			{// 否则按照包名的组成部分，将包名转换为路径
				int start = 0, end = 0;
				end = packName.indexOf(".");
				while (end != -1)
				{
					path = path + packName.substring(start, end) + "/";
					start = end + 1;
					end = packName.indexOf(".", start);
				}
				path = path + packName.substring(start) + "/";
			}
		}
		// 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
		java.net.URL url = loader.getResource(path + clsName);
		// 从URL对象中获取路径信息
		String realPath = url.getPath();
		// 去掉路径信息中的协议名"file:"
		int pos = realPath.indexOf("file:");
		if (pos > -1)
			realPath = realPath.substring(pos + 5);
		// 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
		pos = realPath.indexOf(path + clsName);
		realPath = realPath.substring(0, pos - 1);
		// 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
		if (realPath.endsWith("!"))
			realPath = realPath.substring(0, realPath.lastIndexOf("/"));
		/*------------------------------------------------------------  
		 ClassLoader的getResource方法使用了utf-8对路径信息进行了编码，当路径  
		  中存在中文和空格时，他会对这些字符进行转换，这样，得到的往往不是我们想要  
		  的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的  
		  中文及空格路径  
		-------------------------------------------------------------*/
		try
		{
			realPath = java.net.URLDecoder.decode(realPath, "utf-8");
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		File file = new File(realPath);
		if (file.exists())
		{
			if ("src".equals(file.getName()) || "bin".equals(file.getName()))
			{
				realPath = file.getParent();
			}
		}
		return realPath;
	}

	/**
	 * 将字符串转换成Unicode
	 * @param s
	 * @return
	 */
	public static String stringToUnicode(String s)
	{
		String uni_s = "";
		String temp = "";
		for(int i=0;i<s.length() ;i++)
		{
			temp = Integer.toHexString((int)s.charAt(i));
			while(temp.length()<4)
			{
				temp = "0" + temp;
			}
			uni_s += "\\u" + temp;
		}
		return uni_s;
	}

	/**
	 * 将Unicode转换成字符串
	 * @param unicodeStr
	 * @return
	 */
	public static String unicodeToString(String unicodeStr)
	{
		StringBuffer sb = new StringBuffer();
		String str[] = unicodeStr.toUpperCase().split("U");
		for (int i = 0; i < str.length; i++)
		{
			if (str[i].equals(""))
				continue;
			char c = (char) Integer.parseInt(str[i].trim(), 16);
			sb.append(c);
		}
		return sb.toString();
	}
	
	/**
	 * 将字符串拆分成一个Map,key为命令字，value为值
	 * @param paramStr
	 * 		command=aaa;partid=sss;user=fff;name=ddd
	 * @param relation
	 * 		=
	 * @param separator
	 * 		;
	 * @return Map<String, String>
	 */
	public static Map<String, String> getParamMap(String paramStr, String relation, String separator)
	{
		Map<String, String> paramMap = null;
		if(paramStr!=null)
		{
			String[] params = paramStr.split(separator);
			for(String param : params)
			{
				String[] temp = param.split(relation, 2);
				if(temp.length==2)
				{
					if(paramMap==null) paramMap = new HashMap<String, String>();
					paramMap.put(temp[0], temp[1]);
				}
			}
		}
		return paramMap;
	}
	
	/**
	 * 将map中的参数拼接成一个字符串
	 * @param paramMap 源map
	 * @param relation 值连接符
	 * @param separator key分隔符
	 * @return
	 */
	public static String getParamStrFromMap(Map<String, String> paramMap, String relation, String separator)
	{
		String paramStr = null;
		if(paramMap!=null)
		{
			StringBuffer sb = new StringBuffer();
			for(Entry<String, String> entry : paramMap.entrySet())
			{
				sb.append(entry.getKey()).append(relation).append(entry.getValue()).append(separator);
			}
			if(sb.length()>0)
			{
				sb.delete(sb.length()-separator.length(), sb.length());
				paramStr = sb.toString();
			}
		}
		return paramStr;
	}

	/**
	 * 获取所有查找到的字符串的开始和结束位置
	 * @param srcStr 源字符串
	 * @param findStr 要查找的字符串
	 * @param start 开始查找位置
	 * @param end 结束查找位置
	 * @return
	 */
	public static List<Integer> getAllIndexOf(String srcStr, String findStr, int start, int end)
	{
		if(srcStr.length()>end) srcStr = srcStr.substring(0, end);
		List<Integer> list = new ArrayList<Integer>();
		if(srcStr.length()>start)
		{
			int curIdx = start;
			while((curIdx=srcStr.indexOf(findStr, curIdx))!=-1)
			{
				list.add(curIdx);
				curIdx += findStr.length();
			}
		}
		return list;
	}
	
	/**
	 * This method is for indexOfIgnoreMark use.
	 * @return
	 */
	private static boolean isIndexBetweenList(int idx, List<Integer> list)
	{
		boolean isBetween = false;
		for(int i=0;i<list.size();i+=2)
		{
			if(i!=list.size()-1)
			{
				int start = list.get(i);
				int end = list.get(i+1);
				if(idx>start&&idx<end)
				{
					isBetween = true;
					break;
				}
			}
		}
		return isBetween;
	}
	
	/**
	 * 查找指定的字符串的开始位置
	 * @param srcStr
	 * 		原始字符串
	 * @param findStr
	 * 		想要查找的字符串
	 * @param start
	 * 		查找的起始位置
	 * @param end
	 * 		查找的终止位置
	 * @param ignoreStrBetweenThisMark
	 * 		忽略在此mark之中的字符串
	 * @return
	 * 		找到该字符串的位置, 没找到则返回-1
	 */
	public static int indexOfIgnoreMark(String srcStr, String findStr, int start, int end, String ignoreStrBetweenThisMark)
	{
		int index = -1;
		if(srcStr.length()>end) srcStr = srcStr.substring(0, end);
		List<Integer> ignoreList = getAllIndexOf(srcStr, ignoreStrBetweenThisMark, start, end);
		int curIdx = start;
		while((curIdx=srcStr.indexOf(findStr, curIdx))!=-1)
		{
			if(!isIndexBetweenList(curIdx, ignoreList))
			{
				index = curIdx;
				break;
			}
			curIdx += findStr.length();
		}
		return index;
	}
	
	public static boolean matches(String srcStr, String regex)
	{
		return srcStr.matches(regex);
	}
	
	public static String replaceAll(String src, String regex, String replacement)
	{
		return src.replaceAll(regex, replacement);
	}
	
	/**
	 * 获取格式化后的数字字符串
	 * @param number 源数字
	 * @return
	 */
	public static String getFormattedNumber(Number number)
	{
		return getFormattedNumber(number, "#,###");
	}

	/**
	 * 获取格式化后的数字字符串
	 * @param number 源数字
	 * @param pattern 数字格式.详见{@link DecimalFormat}
	 * @return
	 */
	public static String getFormattedNumber(Number number, String pattern)
	{
		DecimalFormat decimalFormat = new DecimalFormat(pattern); 
		return decimalFormat.format(number);
	}
	
	public static void main(String[] args)
	{
		//System.out.println(transJavaPackToPath("com.lxd.Test"));
		// System.out.println(getAppPath(StringUtil.class));
		/*
		 * String str = " where todate <= '2010-08-10' and 1=2"; String regex =
		 * "date *((<|>|=)|((<|>){1}=?)) *'"; int[] a = indexOf(str, regex);
		 * System.out.println(a[0]+"    "+a[1]);
		 */
		/*System.out.println(stringToUnicode("SQL"));
		System.out.println("\u0044\u0042\u0041");*/
		//System.out.println("044015826321452]".replaceAll("\\]", ""));
		/*System.out.println(getMD5String("111","222","333"));
		System.out.println(getMD5String("1111","222","333"));
		System.out.println(getMD5String("111","222","333"));
		System.out.println(getMD5String("1111","222","333"));*/
		/*String[] strs = StringUtil.split("60*61*97-105.5CM", "[0-9]+(\\.[0-9]+)?");
		for(int i=0;i<strs.length;i++)
		{
			System.out.println(strs[i]);
		}*/
		/*String[] strs = StringUtil.split("sdsd<xs>sfdsff</xs>111111<xs>7777</xs>", "<xs>", "</xs>");
		for(int i=0;i<strs.length;i++)
		{
			System.out.println(strs[i]);
		}*/
		System.out.println(trimAll("  刘      旭    东   "));
	}
}
