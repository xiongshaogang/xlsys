package xlsys.base.database.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.Union;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import xlsys.base.XLSYS;
import xlsys.base.io.util.FileUtil;
import xlsys.base.log.LogUtil;
import xlsys.base.util.StringUtil;

/**
 * sql语句工具
 * @author Lewis
 *
 */
public class SqlUtil
{
	private static CCJSqlParserManager parserManager = new CCJSqlParserManager();
	
	/**
	 * 判断传入的sql语句是否是一个select类型的语句
	 * @param sql
	 * @return
	 */
	public static boolean isSelectSql(String sql)
	{
		boolean success = false;
		if(sql!=null)
		{
			success = sql.toLowerCase().trim().startsWith("select ");
		}
		return success;
	}
	
	/**
	 * 尝试从一个sql语句中获取最外层的第一个表名
	 * @param sql
	 * @return
	 */
	public static String tryToGetTableName(String sql)
	{
		String tableName = null;
		try
		{
			sql = sql.toLowerCase();
			sql = new String(sql.getBytes("iso-8859-1"), "iso-8859-1");
			sql = sql.replaceAll("\\?\\[[^\\]]+\\]", "?"); // 将类似?[sdsd]替换为?
			sql = sql.replace("regexp_like", "a in"); // 将类似REGEXP_LIKE替换为a in
			Statement statement = parserManager.parse(new StringReader(sql));
			if(statement instanceof Select)
			{
				tableName = getTableNameFromSelectBody(((Select) statement).getSelectBody());
			}
			else if(statement instanceof Update)
			{
				tableName = ((Update) statement).getTable().getName();
			}
			else if(statement instanceof Insert)
			{
				tableName = ((Insert) statement).getTable().getName();
			}
			else if(statement instanceof Delete)
			{
				tableName = ((Delete) statement).getTable().getName();
			}
			else if(statement instanceof Truncate)
			{
				tableName = ((Truncate) statement).getTable().getName();
			}
			else if(statement instanceof CreateTable)
			{
				tableName = ((CreateTable) statement).getTable().getName();
			}
		}
		catch(Exception e)
		{
			LogUtil.printlnWarn(e);
		}
		return tableName;
	}

	/**
	 * 从fromItem中获取表名
	 * @param fromItem
	 * @return
	 */
	private static String getTableNameFromFromItem(FromItem fromItem)
	{
		String tableName = null;
		if(fromItem instanceof Table)
		{
			tableName = ((Table) fromItem).getName();
		}
		else if(fromItem instanceof SubSelect)
		{
			tableName = getTableNameFromSelectBody(((SubSelect) fromItem).getSelectBody());
		}
		else if(fromItem instanceof SubJoin)
		{
			FromItem subFromItem = ((SubJoin) fromItem).getLeft();
			tableName = getTableNameFromFromItem(subFromItem);
		}
		return tableName;
	}
	
	/**
	 * 从selectBody中获取表名
	 * @param selectBody
	 * @return
	 */
	private static String getTableNameFromSelectBody(SelectBody selectBody)
	{
		String tableName = null;
		if(selectBody instanceof PlainSelect)
		{
			PlainSelect plainSelect = (PlainSelect) selectBody;
			FromItem fromItem = plainSelect.getFromItem();
			tableName = getTableNameFromFromItem(fromItem);
		}
		else if(selectBody instanceof Union)
		{
			// TODO
		}
		return tableName;
	}
	
	/**
	 * 建立where表达式等号右边部分，比如rightValue为 0001%, 则返回 like '0001%', 如果有包含表达式，则会自动替换表达式中的宏定义
	 * @param rightValue
	 * @param macroMap
	 * @return
	 */
	public static String createExpressionSql(String left, String rightValue, Map<String, String> macroMap)
	{
		// 获取连接符号
		String connectToken = "=";
		if(rightValue.matches(".*(%|_).*"))
		{
			connectToken = " like ";
		}
		// 获取结果
		StringBuilder sb = new StringBuilder();
		
		if(rightValue.matches(".*\\[.*\\].*"))
		{
			// 包含公式
			String[] rightResult = replaceExpression(rightValue, macroMap);
			sb.append('(');
			for(int i=0;i<rightResult.length;++i)
			{
				sb.append(left).append(connectToken).append('\'').append(rightResult[i]).append('\'');
				if(i!=rightResult.length-1) sb.append(" or ");
			}
			sb.append(')');
		}
		else
		{
			sb.append(left).append(connectToken).append('\'').append(rightValue).append('\'');
		}
		return sb.toString();
	}
	
	/**
	 * 解析表达式，形如[deptid] [deptid,0] [deptid,0,15], 这里的deptid是指当前macroMap中的deptid
	 * @param rightValue
	 * @return
	 */
	public static String[] replaceExpression(String rightValue, Map<String, String> macroMap)
	{
		// 先判断是否含有包含多个value的marcoKey
		String[] valueToken = StringUtil.split(rightValue, '[', ']', true);
		String multiKey = null;
		String[] multiValue = null;
		for(String token : valueToken)
		{
			if(token.startsWith("[")&&token.endsWith("]"))
			{
				String[] params = token.substring(1, token.length()-1).split(",");
				if(macroMap.containsKey(params[0]))
				{
					if(macroMap.get(params[0]).contains(XLSYS.KEY_CODE_SEPARATOR))
					{
						multiValue = macroMap.get(params[0]).split(XLSYS.KEY_CODE_SEPARATOR);
						multiKey = params[0];
						break;
					}
				}
			}
		}
		// 组装结果
		String[] result = null;
		int len = 1;
		if(multiValue!=null) len = multiValue.length;
		result = new String[len];
		for(int n=0;n<len;++n)
		{
			result[n] = "";
			for(String token : valueToken)
			{
				if(token.startsWith("[")&&token.endsWith("]"))
				{
					// 表达式
					String[] params = token.substring(1, token.length()-1).split(",");
					if(macroMap.containsKey(params[0]))
					{
						String macroValue = macroMap.get(params[0]);
						if(params[0].equals(multiKey)) macroValue = multiValue[n];
						if(params.length==1)
						{
							result[n] += macroValue;
						}
						else if(params.length==2)
						{
							int startIdx = Integer.parseInt(params[1]);
							result[n] += macroValue.substring(startIdx);
						}
						else if(params.length==3)
						{
							int startIdx = Integer.parseInt(params[1]);
							int endIdx = Integer.parseInt(params[2]);
							result[n] += macroValue.substring(startIdx, endIdx);
						}
					}
				}
				else result[n] += token;
			}
		}
		return result;
	}
	
	/**
	 * 去除sql中的注释
	 * @param sql
	 * @return
	 */
	public static String removeComment(String sql)
	{
		// 去除多行注释
		String[] splitArr = StringUtil.split(sql, "/*", "*/");
		StringBuilder sb = new StringBuilder();
		for(String pieces : splitArr)
		{
			if(pieces.startsWith("/*")&&pieces.endsWith("*/")) continue;
			sb.append(pieces);
		}
		sql = sb.toString();
		// 去除单行注释
		sb.setLength(0);
		splitArr = StringUtil.split(sql, "\r\n|\n\r|\n|\r");
		for(String pieces : splitArr)
		{
			int idx = StringUtil.indexOfIgnoreMark(pieces, "--", 0, pieces.length(), "'");
			if(idx!=-1) sb.append(pieces.substring(0, idx)).append('\n');
			else sb.append(pieces);
		}
		sql = sb.toString();
		return sql;
	}
	
	/**
	 * 获取sql数组，该方法会去除注释，并且按照分号将文本拆分返回数组
	 * @param sqlText
	 * @return
	 */
	public static String[] getSplitedSql(String sqlText)
	{
		sqlText = removeComment(sqlText);
		String[] arr = sqlText.split(";");
		for(int i=0;i<arr.length;i++)
		{
			arr[i] = arr[i].trim();
		}
		return arr;
	}
	
	public static void main(String[] args) throws IOException
	{
		String sql = new String(FileUtil.getByteFromFile("dbmodel/crebas_ora.sql"));
		String[] sqlArr = getSplitedSql(sql);
		for(String temp : sqlArr)
		{
			System.out.println(temp+";");
		}
	}
}
