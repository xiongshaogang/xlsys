package xlsys.base.util;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import xlsys.base.XLSYS;
import xlsys.base.io.util.IOUtil;
import xlsys.base.script.calculate.CLexer;
import xlsys.base.script.calculate.CParser;

/**
 * 数字工具类
 * @author Lewis
 *
 */
public class NumUtil
{
	private static NumUtil nu;
	
	private int scale;
	private Map<String, Object> vars;
	
	public NumUtil()
	{
		this(XLSYS.NUMBER_SCALE);
	}
	
	public NumUtil(int scale)
	{
		this.scale = scale;
	}
	
	public synchronized static NumUtil getInstance()
	{
		if(nu==null) nu = new NumUtil();
		return nu;
	}
	
	public static final String[] enNum = { // 基本数词表
	"zero", "one", "tow", "three", "four", "five", "six", "seven", "eight",
			"nine", "ten", "eleven", "twelve", "thirteen", "fourteen",
			"fifteen", "sixteen", "seventeen", "eighteen", "nineteen",
			"twenty", "", "", "", "", "", "", "", "", "", "thirty", "", "", "",
			"", "", "", "", "", "", "fourty", "", "", "", "", "", "", "", "",
			"", "fifty", "", "", "", "", "", "", "", "", "", "sixty", "", "",
			"", "", "", "", "", "", "", "seventy", "", "", "", "", "", "", "",
			"", "", "eighty", "", "", "", "", "", "", "", "", "", "ninety" };

	public static final String[] enUnit = { "hundred", "thousand", "million",
			"billion", "trillion", "quintillion" }; // 单位表
	
	public static final String[] zhNum = { // 基本数词表
			"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌",
					"玖"};

	public static final String[] zhUnit = {"圆", "万", "亿", "仟", "佰", "拾", "角", "分"}; // 单位表

	public static void main(String[] args) throws Exception
	{
		NumUtil nu = new NumUtil(2);
		nu.addVar("sum5930", new BigDecimal("2322.23"));
		nu.addVar("sum0010", new BigDecimal("65445.45"));
		BigDecimal avgRate = nu.calculateExpr("sum5930/sum0010*100");
		String macroData = avgRate.toString()+"%";
		System.err.println(macroData);
	}

	/**
	 * 将数字转换成本地可读字符串
	 * @param num
	 * @return
	 */
	public static String analyze(long num)
	{ // long型参数，
		return analyze(String.valueOf(num)); // 因为long型有极限，所以以字符串参数方法为主
	}

	/**
	 * 将字符串数字转换成本地可读字符串
	 * @param num
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static String analyze(String num)
	{
		String numStr = null;
		String language = SystemUtil.getUserLanguage();
		String methodName = "analyze_" + language;
		try
		{
			Method targetMethod = NumUtil.class.getDeclaredMethod(methodName, String.class);
			targetMethod.setAccessible(true);
			numStr = (String) targetMethod.invoke(null, num);
		}
		catch(Exception e)
		{
			numStr = "Unsupport Number : " + num;
		}
		return numStr;
	}
	
	/**
	 * 将数字转换成中文大写可读字符串
	 * @param num
	 * @return
	 */
	public static String analyze_zh(long num)
	{ // long型参数，
		return analyze_zh(String.valueOf(num)); // 因为long型有极限，所以以字符串参数方法为主
	}
	
	/**
	 * 将字符串数字转换成中文大写可读字符串
	 * @param numStr
	 * @return
	 */
	public static String analyze_zh(String numStr)
	{ // 数字字符串参数
		// 判断字符串是否为数字
		if (!numStr.matches("(\\d+)|(\\d+\\.\\d+)"))
		{
			return String.format("%s不是一个可转换的有效数字", numStr);
		}

		String numLeft = numStr;
		String numRight = null;
		if(numStr.contains("."))
		{
			// 把数字分成两份
			String[] temp = numStr.split("\\.", 2);
			numLeft = temp[0];
			numRight = temp[1];
			if(numRight.length()>2) numRight = numRight.substring(0, 2);
			numRight = numRight.replaceAll("0*$", ""); // 把小数后面的0去掉
			if(numRight.length()==0) numRight = null;
		}
		numLeft = numLeft.replaceAll("^[0]*([1-9]*)", "$1"); // 把整数前面的0去掉
		
		if (numLeft.length() == 0)
		{ // 如果长度为0，则原串都是0
			return zhNum[0];
		}
		else if (numLeft.length() > 12)
		{ // 如果大于12，即大于9999,9999,9999，不可读
			return String.format("%s太大", numLeft);
		}

		// 按4位分割分组
		int count = (numLeft.length() % 4 == 0) ? numLeft.length() / 4 : numLeft.length() / 4 + 1;
		String[] group = new String[count];
		for (int i = numLeft.length(), j = group.length - 1; i > 0; i -= 4)
		{
			group[j--] = numLeft.substring(Math.max(i - 4, 0), i);
		}

		StringBuilder buf = new StringBuilder(); // 结果保存
		for (int i = 0; i < count; i++)
		{ // 遍历分割的组
			int v = Integer.valueOf(group[i]);
			boolean flag = false;
			if (v >= 1000)
			{ // 因为按4位分割，所以这里不会有超过9999的数
				buf.append(zhNum[v / 1000]).append(zhUnit[3]);
				v = v % 1000; // 获取千位，并得到千位以后的数
				flag = true;
			}
			else flag = false;
			if (v >= 100)
			{
				if(!flag)
				{
					if(buf.length()>0) buf.append(zhNum[0]);
					flag = true;
				}
				buf.append(zhNum[v/100]).append(zhUnit[4]);
				v = v % 100; // 获取百位，并得到百位以后的数
			}
			else flag = false;
			if (v >= 10)
			{
				if(!flag)
				{
					if(buf.length()>0) buf.append(zhNum[0]);
					flag = true;
				}
				buf.append(zhNum[v/10]).append(zhUnit[5]);
				v = v % 10; // 获取十位，并得到十位以后的数
			}
			else flag = false;
			if (v !=0 )
			{
				if(!flag&&buf.length()>0) buf.append(zhNum[0]);
				buf.append(zhNum[v]);
			}
			// 追加万分位
			buf.append(zhUnit[count-i-1]);
		}
		if(numRight!=null)
		{
			for(int i=0;i<numRight.length();++i)
			{
				int v = Integer.valueOf(""+numRight.charAt(i));
				buf.append(zhNum[v]);
				if(i==0&&v!=0) buf.append(zhUnit[6]);
				else if(i==1) buf.append(zhUnit[7]);
			}
		}
		return buf.toString().trim(); // 返回值
	}
	
	/**
	 * 将数字转换成英文可读字符串
	 * @param num
	 * @return
	 */
	public static String analyze_en(long num)
	{ // long型参数，
		return analyze_en(String.valueOf(num)); // 因为long型有极限，所以以字符串参数方法为主
	}

	/**
	 * 将字符串数字转换成英文可读字符串
	 * @param num
	 * @return
	 */
	public static String analyze_en(String num)
	{ // 数字字符串参数
		// 判断字符串是否为数字
		if (!num.matches("\\d+"))
		{
			return String.format("%s is not number", num);
		}

		num = num.replaceAll("^[0]*([1-9]*)", "$1"); // 把字符串前面的0去掉

		if (num.length() == 0)
		{ // 如果长度为0，则原串都是0
			return enNum[0];
		}
		else if (num.length() > 9)
		{ // 如果大于9，即大于999999999，题目限制条件
			return "too big";
		}

		// 按3位分割分组
		int count = (num.length() % 3 == 0) ? num.length() / 3
				: num.length() / 3 + 1;
		if (count > enUnit.length)
		{
			return "too big";
		} // 判断组单位是否超过，
			// 可以根据需求适当追加enUnit
		String[] group = new String[count];
		for (int i = num.length(), j = group.length - 1; i > 0; i -= 3)
		{
			group[j--] = num.substring(Math.max(i - 3, 0), i);
		}

		StringBuilder buf = new StringBuilder(); // 结果保存
		for (int i = 0; i < count; i++)
		{ // 遍历分割的组
			int v = Integer.valueOf(group[i]);
			if (v >= 100)
			{ // 因为按3位分割，所以这里不会有超过999的数
				buf.append(enNum[v / 100]).append(" ").append(enUnit[0])
						.append(" ");
				v = v % 100; // 获取百位，并得到百位以后的数
				if (v != 0)
				{
					buf.append("and ");
				} // 如果百位后的数不为0，则追加and
			}
			if (v != 0)
			{ // 前提是v不为0才作解析
				if (v < 20 || v % 10 == 0)
				{ // 如果小于20或10的整数倍，直接取基本数词表的单词
					buf.append(enNum[v]).append(" ");
				}
				else
				{ // 否则取10位数词，再取个位数词
					buf.append(enNum[v - v % 10]).append(" ");
					buf.append(enNum[v % 10]).append(" ");
				}
				if (i != count - 1)
				{ // 百位以上的组追加相应的单位
					buf.append(enUnit[count - 1 - i]).append(" ");
				}
			}
		}

		return buf.toString().trim(); // 返回值
	}
	
	/**
	 * 按照给定的格式从文本中获取数字
	 * @param numStr 源文本
	 * @param format 符合DecimalFormat中解析规则的格式符号
	 * @return
	 * @throws ParseException
	 */
	public static BigDecimal getNumberFromString(String numStr, String format) throws ParseException
	{
		BigDecimal num = null;
		if(format!=null)
		{
			DecimalFormat df = new DecimalFormat(format);
			num = new BigDecimal(df.parse(numStr).toString());
		}
		else
		{
			num = new BigDecimal(numStr);
		}
		return num;
	}
	
	public BigDecimal add(BigDecimal left, BigDecimal right)
	{
		BigDecimal ret = left.add(right);
		ret = ret.setScale(scale, RoundingMode.HALF_UP);
		return ret;
	}
	
	public BigDecimal subtract(BigDecimal left, BigDecimal right)
	{
		BigDecimal ret = left.subtract(right);
		ret = ret.setScale(scale, RoundingMode.HALF_UP);
		return ret;
	}
	
	public BigDecimal multiply(BigDecimal left, BigDecimal right)
	{
		BigDecimal ret = left.multiply(right);
		ret = ret.setScale(scale, RoundingMode.HALF_UP);
		return ret;
	}
	
	public BigDecimal divide(BigDecimal left, BigDecimal right)
	{
		return left.divide(right, scale, RoundingMode.HALF_UP);
	}
	
	public BigDecimal remainder(BigDecimal left, BigDecimal right)
	{
		BigDecimal ret = left.remainder(right);
		ret = ret.setScale(scale, RoundingMode.HALF_UP);
		return ret;
	}
	
	public void addVar(String var, Object value)
	{
		if(vars==null) vars = new HashMap<String, Object>();
		vars.put(var, value);
	}
	
	/**
	 * 计算表达式的值，该表达式可通过addVar方法来添加变量
	 * @param expr
	 * @return
	 * @throws Exception
	 */
	public BigDecimal calculateExpr(String expr) throws Exception
	{
		ByteArrayInputStream bais = null;
		BigDecimal result = null;
		try
		{
			bais = new ByteArrayInputStream(expr.getBytes());
			CLexer lexer = new CLexer(bais);
			if(vars!=null)
			{
				for(String var : vars.keySet())
				{
					lexer.addVar(var, vars.get(var));
				}
			}
			CParser parser = new CParser(lexer);
			result = parser.calculate();
			result = result.setScale(scale, RoundingMode.HALF_UP);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			IOUtil.close(bais);
		}
		return result;
	}
}
