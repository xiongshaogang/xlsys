package xlsys.base.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TempTest
{
	public static String replaceKeyWord(String sql)
	{
		Set<String> keyWordSet = new HashSet<String>();
		keyWordSet.add("condition");
		// 查询语句中是否包含关键字
		List<int[]> keyWordList = null;
		StringBuilder keyWordRegex = new StringBuilder();
		keyWordRegex.append("(^|[^\\w]){1}(");
		for(String keyWord : keyWordSet) keyWordRegex.append(keyWord).append('|');
		if(keyWordRegex.charAt(keyWordRegex.length()-1)=='|') keyWordRegex.deleteCharAt(keyWordRegex.length()-1);
		keyWordRegex.append(")($|[^\\w]){1}");
		Pattern p = Pattern.compile(keyWordRegex.toString());
		Pattern tp = Pattern.compile("[^\\w]{1}");
		Matcher m = p.matcher(sql);
		while(m.find())
		{
			if(keyWordList==null) keyWordList = new ArrayList<int[]>();
			String matchedStr = m.group();
			// 查找第一个非单词字符和最后一个非单词字符
			Matcher mtp = tp.matcher(matchedStr);
			int[] indices = new int[]{m.start(), m.end()};
			while(mtp.find())
			{
				if(0==mtp.start()) ++indices[0];
				if(matchedStr.length()-1==mtp.start()) --indices[1];
			}
			keyWordList.add(indices);
		}
		if(keyWordList!=null&&!keyWordList.isEmpty())
		{
			// 把所有关键字的前后加上`
			StringBuilder tempSb = new StringBuilder(sql);
			for(int i=keyWordList.size()-1;i>=0;--i)
			{
				int[] indices = keyWordList.get(i);
				tempSb.insert(indices[1], '`');
				tempSb.insert(indices[0], '`');
			}
			sql = tempSb.toString();
		}
		return sql;
	}
	
	public static void main(String[] args) throws Exception
	{
		String sql = "condition select condition from xlsys_condition .condition";
		System.out.println(replaceKeyWord(sql));
		
	}
}
