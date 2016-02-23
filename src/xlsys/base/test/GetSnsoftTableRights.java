package xlsys.base.test;

import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.database.ConnectionPool;
import xlsys.base.database.IDataBase;
import xlsys.base.database.util.DBUtil;
import xlsys.base.dataset.IDataSet;
import xlsys.base.io.util.IOUtil;
import xlsys.base.util.ObjectUtil;

public class GetSnsoftTableRights
{
	private final static int RIGHT_MODE_ALL = 0;
	private final static int RIGHT_MODE_ALL_WITH_BCODE = 1;
	private final static int RIGHT_MODE_READ = 2;

	public static void writeRightsSqlToFile(int dbid, String filePath, String newUser, String pwdOfNewUser)
	{
		IDataBase dataBase = null;
		FileOutputStream fos = null; 
		try
		{
			
			ConnectionPool cp = (ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid);
			String owner = cp.getUser();
			dataBase = cp.getNewDataBase();
			// 获取所有需要写权限的表
			Set<String> needFullRightTables = new HashSet<String>();
			String selectSql = "select distinct fromtable from gdftgifcfg";
			IDataSet dataSet = dataBase.sqlSelect(selectSql);
			for(int i=0;i<dataSet.getRowCount();i++)
			{
				needFullRightTables.add(ObjectUtil.objectToString(dataSet.getValue(i, 0)).toLowerCase());
			}
			StringBuilder sb = new StringBuilder();
			StringBuilder synonymSb = new StringBuilder();
			// 连接源用户
			sb.append("-- connect to ").append(owner).append('\n');
			sb.append("connect ").append(owner).append('/').append(cp.getPassword()).append("\n\n");
			// 先创建用户
			sb.append("-- create user ").append(newUser).append('\n');
			sb.append("create user ").append(newUser).append(" identified by \"").append(pwdOfNewUser).append("\" default tablespace user_data temporary tablespace user_temp profile default;\n\n");
			// 赋予基本权限
			sb.append("-- grant base right\n");
			sb.append("grant connect to ").append(newUser).append(";\n");
			sb.append("grant create table to ").append(newUser).append(";\n");
			sb.append("grant create view to ").append(newUser).append(";\n");
			sb.append("grant create trigger to ").append(newUser).append(";\n");
			sb.append("grant create sequence to ").append(newUser).append(";\n");
			sb.append("grant create procedure to ").append(newUser).append(";\n");
			sb.append("grant create synonym to ").append(newUser).append(";\n");
			sb.append("grant unlimited tablespace to ").append(newUser).append(";\n");
			sb.append('\n');
			// 赋予表权限
			sb.append("-- grant table right\n");
			String sql = "select t.TABLE_NAME,tc.COLUMN_NAME from USER_TABLES t left join USER_TAB_COLUMNS tc on t.TABLE_NAME=tc.TABLE_NAME and tc.COLUMN_NAME='BCODE' order by tc.COLUMN_NAME, t.TABLE_NAME";
			dataSet = dataBase.sqlSelect(sql);
			for(int i=0;i<dataSet.getRowCount();i++)
			{
				String tableName = (String) dataSet.getValue(i, "TABLE_NAME");
				String colName = (String) dataSet.getValue(i, "COLUMN_NAME");
				int rightMode = RIGHT_MODE_ALL;
				if(tableName.startsWith("KF7_")) rightMode = RIGHT_MODE_ALL;
				else if(colName!=null) rightMode = RIGHT_MODE_ALL_WITH_BCODE;
				else if(needFullRightTables.contains(tableName.toLowerCase())) rightMode = RIGHT_MODE_ALL;
				else rightMode = RIGHT_MODE_READ;
				writeRightSqlWithRightMode(sb, synonymSb, owner, newUser, tableName, rightMode);
			}
			sb.append('\n');
			// 赋予存储过程的执行权限
			sb.append("-- grant execute right\n");
			sb.append("grant execute on ").append(owner).append(".lxd_kf7 to ").append(newUser).append(";\n");
			synonymSb.append("create synonym lxd_kf7 for ").append(owner).append(".lxd_kf7;\n");
			sb.append('\n');
			// 连接至新用户
			sb.append("-- connect to ").append(newUser).append('\n');
			sb.append("connect ").append(newUser).append('/').append(pwdOfNewUser).append("\n\n");
			// 建立同义词
			sb.append("-- create synonym\n");
			sb.append(synonymSb);
			fos = IOUtil.getFileOutputStream(filePath, false);
			fos.write(sb.toString().getBytes());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DBUtil.close(dataBase);
			IOUtil.close(fos);
		}
		
	}
	
	private static void writeRightSqlWithRightMode(StringBuilder sb, StringBuilder synonymSb, String owner, String newUser, String tableName, int rightMode)
	{
		synonymSb.append("create synonym ").append(tableName).append(" for ");
		if(rightMode==RIGHT_MODE_ALL)
		{
			sb.append("grant all on ").append(owner).append('.').append(tableName).append(" to ").append(newUser).append(";\n");
			synonymSb.append(owner).append('.').append(tableName);
		}
		else if(rightMode==RIGHT_MODE_ALL_WITH_BCODE)
		{
			sb.append("create or replace view view_").append(newUser).append('_').append(tableName).append(" as select * from ").append(tableName).append(" where bcode like '004%' with check option;\n");
			sb.append("grant all on ").append(owner).append(".view_").append(newUser).append('_').append(tableName).append(" to ").append(newUser).append(";\n");
			synonymSb.append(owner).append(".view_").append(newUser).append('_').append(tableName);
		}
		else if(rightMode==RIGHT_MODE_READ)
		{
			sb.append("grant select on ").append(owner).append('.').append(tableName).append(" to ").append(newUser).append(";\n");
			synonymSb.append(owner).append('.').append(tableName);
		}
		synonymSb.append(";\n");
	}
	
	public static void main(String[] args)
	{
		writeRightsSqlToFile(1005, "D:/lxd/CreateUserOfXlsys.sql", "xlsys", "xlsys2014");
	}

}
