package xlsys.base.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class AnalyzerUtil
{
	/**
	 * 获取默认的分词器
	 * @return
	 */
	public static Analyzer getDefaultAnalyzer()
	{
		return getAnalyzerByLanguage(Locale.getDefault().getLanguage());
	}
	
	/**
	 * 根据语言获取对应的分词器
	 * @param language
	 * @return
	 */
	public static Analyzer getAnalyzerByLanguage(String language)
	{
		Analyzer analyzer = null;
		if("en".equals(language)) analyzer = new StandardAnalyzer();
		else if("zh".equals(language)) analyzer = new SmartChineseAnalyzer();
		else analyzer = new StandardAnalyzer();
		return analyzer;
	}
	
	/**
	 * 使用默认的分析器进行分词
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public static List<String> tokenizer(String text) throws IOException
	{
		return tokenizer(text, getDefaultAnalyzer());
	}
	
	/**
	 * 根据指定语言获取对应的分词器进行分词
	 * @param text
	 * @param language
	 * @return
	 * @throws IOException
	 */
	public static List<String> tokenizer(String text, String language) throws IOException
	{
		return tokenizer(text, getAnalyzerByLanguage(language));
	}
	
	/**
	 * 使用指定的分词器进行分词
	 * @param text
	 * @param analyzer
	 * @return
	 * @throws IOException
	 */
	public static List<String> tokenizer(String text, Analyzer analyzer) throws IOException
	{
		List<String> result = new ArrayList<String>();
		try
		{
			// 获得分词流
			TokenStream tokenStream = analyzer.tokenStream("", text);
			// 分词
			CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
			tokenStream.reset();
			while(tokenStream.incrementToken()) result.add(term.toString()); 
			tokenStream.end();
			tokenStream.close();
		}
		catch(IOException e)
		{
			throw e;
		}
		finally
		{
			analyzer.close();
		}
		return result;
	}
}
