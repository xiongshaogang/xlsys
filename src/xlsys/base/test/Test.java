package xlsys.base.test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.script.ScriptException;

import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.database.ClientDataBase;
import xlsys.base.database.ConnectionPool;
import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.DBUtil;
import xlsys.base.dataset.IDataSet;
import xlsys.base.dataset.StorableDataSet;
import xlsys.base.dataset.util.DataSetUtil;
import xlsys.base.exception.UnsupportedException;
import xlsys.base.io.transfer.client.ClientTransfer;
import xlsys.base.io.util.LockUtil;
import xlsys.base.log.Log;
import xlsys.base.script.XlsysClassLoader;
import xlsys.base.script.XlsysCompiler;
import xlsys.base.script.XlsysScript;
import xlsys.base.service.IPublisher;
import xlsys.base.service.IService;
import xlsys.base.service.ITask;
import xlsys.base.service.ServiceManager;
import xlsys.base.service.Task;
import xlsys.base.service.TaskEvent;
import xlsys.base.session.Session;
import xlsys.base.session.SessionManager;
import xlsys.base.util.NumUtil;
import xlsys.base.util.ObjectUtil;

public class Test
{
	private static int idx = 0;
	
	public static void testDbPool() throws Exception
	{
		ConnectionPool cp = null;
		IDataBase db = null;
		try
		{
			cp = (ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance();
			db = cp.getNewDataBase();
			String sql = "select tables,datatableid,dtname,dttitle,formula from datatable where datatableid=?";
			ParamBean sb = new ParamBean(sql);
			sb.addParamGroup();
			sb.setParam(1, 2029010135);
			sb.addParamGroup();
			sb.setParam(1, 2029010136);
			sb.addParamGroup();
			sb.setParam(1, 2029010137);
			sb.addParamGroup();
			sb.setParam(1, 2029010150);
			StorableDataSet sds = new StorableDataSet(db, sb, "datatable");
			sds.open();
			DataSetUtil.dumpData(sds, false, true);
			sds.removeRow(3);
			sds.setValue(0, "formula", "//This code is for test1!".getBytes());
			sds.setValue(1, "dttitle", "lhh数据定义主表");
			sds.insertNewRowAfterLast();
			sds.setValue("tables", "wcode");
			sds.setValue("datatableid", 2029010151);
			sds.setValue("dtname", "lxd_test");
			sds.setValue("dttitle", "测试");
			sds.setValue("formula", "//公式测试".getBytes());
			sds.save();

			sb.addParamGroup();
			sb.setParam(1, 2029010151);
			IDataSet ds = db.sqlSelect(sb);
			DataSetUtil.dumpData(ds, false, true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db!=null&&!db.isClose()) db.close();
			if(cp!=null) cp.close();
		}
	}
	
	public static void testJs() throws IOException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException, ScriptException, UnsupportedException
	{
		FileInputStream fis = new FileInputStream("test1.js");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = -1;
		while((len=fis.read(b))!=-1)
		{
			baos.write(b, 0, len);
		}
		fis.close();
		String script = baos.toString();
		baos.close();
		XlsysScript xs = new XlsysScript();
		xs.setScript(script);
		xs.put("log", (Log) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_LOG).getInstance());
		//xs.put("str", "123");
		xs.compile();
		//xs.invoke();
		//System.out.println(xs.get("str"));
		xs.invoke("test1");
		
		/*fis = new FileInputStream("testInterface.js");
		baos = new ByteArrayOutputStream();
		b = new byte[1024];
		len = -1;
		while((len=fis.read(b))!=-1)
		{
			baos.write(b, 0, len);
		}
		fis.close();
		script = baos.toString();
		baos.close();
		XlsysScript xs1 = new XlsysScript(true);
		xs1.setScript(script);
		xs1.invoke();
		TestInterface tif = xs1.getInterface(TestInterface.class);
		System.out.println(tif.max(10, 50));*/
	}
	
	public static void testCompiler() throws URISyntaxException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException, IOException
	{
		XlsysCompiler xlsysCompiler = XlsysCompiler.getInstance();
		xlsysCompiler.addSource("xlsys.script.A", new File("testClassA.txt"));
		xlsysCompiler.addSource("xlsys.script.B", new File("testClassB.txt"));
		if(xlsysCompiler.compile())
		{
			XlsysClassLoader xlsysClassLoader = XlsysClassLoader.getInstance();
			Class<?> c = xlsysClassLoader.loadClass("xlsys.script.A");
			Object a = c.newInstance();
			Method m = c.getDeclaredMethod("max", int.class, int.class);
			System.out.println(m.invoke(a, 10, 20));
			m = c.getDeclaredMethod("testList");
			m.invoke(a);
		}
	}
	
	public static void testLockUtil()
	{
		for(int i=0;i<3;i++)
		{
			new Thread()
			{
				public void run()
				{
					String key = null;
					try
					{
						System.out.println(++idx + " : " + Thread.currentThread().getName() + " started!");
						key = LockUtil.getInstance().getLockKey("aaa","bbb");
						System.out.println(++idx + " : " + Thread.currentThread().getName() + " get key : " + key);
						Thread.sleep(1000);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						try
						{
							if(key!=null)
							{
								LockUtil.getInstance().releaseKey(key);
								System.err.println(++idx + " : " + Thread.currentThread().getName() + " release key : " + key);
							}
						}
						catch(Exception e){e.printStackTrace();}
					}
					System.out.println(++idx + " : " + Thread.currentThread().getName() + " ended!");
				}
			}.start();
			new Thread()
			{
				public void run()
				{
					String key = null;
					try
					{
						System.out.println(++idx + " : " + Thread.currentThread().getName() + " started!");
						key = LockUtil.getInstance().tryLockKey("aaa","bbb");
						System.out.println(++idx + " : " + Thread.currentThread().getName() + " try key : " + key);
						if(key!=null) Thread.sleep(1000);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						try
						{
							if(key!=null)
							{
								LockUtil.getInstance().releaseKey(key);
								System.err.println(++idx + " : " + Thread.currentThread().getName() + " release key : " + key);
							}
						}
						catch(Exception e){e.printStackTrace();}
					}
					System.out.println(++idx + " : " + Thread.currentThread().getName() + " ended!");
				}
			}.start();
		}
		
	}
	
	public static void testVirtualDataSet() throws Exception
	{
		String selectSql = "select * from xlsys_viewdetail";
		Session session = new Session("Test");
		session.setAttribute(XLSYS.SESSION_ENV_ID, 1001);
		SessionManager.getInstance().setCurrentSession(session);
		StorableDataSet sds = new StorableDataSet(ClientDataBase.getInstance(), selectSql, "xlsys_viewdetail", IDataSet.STYLE_VIRTUAL);
		sds.setSortColumn("viewid", "idx");
		sds.open();
		sds.sort();
		DataSetUtil.dumpData(sds);
		System.err.println(sds.getRows().size());
	}
	
	public static void insertTestData()
	{
		IDataBase dataBase = null;
		try
		{
			dataBase = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance()).getNewDataBase();
			ParamBean pb = new ParamBean("insert into xlsys_user(userid, name, password) values(?,?,?)");
			for(int i=1;i<=2000;i++)
			{
				pb.addParamGroup();
				pb.setParam(1, ""+i);
				pb.setParam(2, ""+i);
				pb.setParam(3, ""+i);
			}
			dataBase.sqlExecute(pb);
			System.out.println("Done");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DBUtil.close(dataBase);
		}
	}
	
	public static void updateTestData()
	{
		IDataBase dataBase = null;
		try
		{
			dataBase = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(1004)).getNewDataBase();
			ExecuteBean eb = new ExecuteBean(ExecuteBean.EXECUTE_TYPE_UPDATE, "salorder");
			Map<String, Serializable> dataMap = new HashMap<String, Serializable>();
			dataMap.put("salordicode", "7004000000000000");
			dataMap.put("picode", "12345");
			eb.addData(dataMap);
			dataBase.sqlExecute(eb);
			System.out.println("Done");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DBUtil.close(dataBase);
		}
	}
	
	public static void testHttpClientTransfer() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		ClientTransfer clientTransfer = (ClientTransfer) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_CLIENT_TRANSFER).getInstance();
		System.out.println(clientTransfer.post(XLSYS.COMMAND_FOR_TEST));
	}
	
	public static void test()
	{
		// Print out a number using the localized number, integer, currency,
		 // and percent format for each locale
		 Locale[] locales = NumberFormat.getAvailableLocales();
		 double myNumber = -1234.56;
		 NumberFormat form;
		 for (int j=0; j<4; ++j) {
		     System.out.println("FORMAT");
		     for (int i = 0; i < locales.length; ++i) {
		         if (locales[i].getCountry().length() == 0) {
		            continue; // Skip language-only locales
		         }
		         System.out.print(locales[i].getDisplayName());
		         switch (j) {
		         case 0:
		             form = NumberFormat.getInstance(locales[i]); break;
		         case 1:
		             form = NumberFormat.getIntegerInstance(locales[i]); break;
		         case 2:
		             form = NumberFormat.getCurrencyInstance(locales[i]); break;
		default:
		             form = NumberFormat.getPercentInstance(locales[i]); break;
		         }
		         if (form instanceof DecimalFormat) {
		             System.out.print(": " + ((DecimalFormat) form).toPattern());
		         }
		         System.out.print(" -> " + form.format(myNumber));
		         try {
		             System.out.println(" -> " + form.parse(form.format(myNumber)));
		} catch (ParseException e) {}
		     }
		 }
	}
	
	public static void testNumUtil() throws Exception
	{
		NumUtil nu = new NumUtil();
		nu.addVar("a", 10);
		nu.addVar("b", 20);
		nu.addVar("c", 30);
		System.out.println(nu.calculateExpr("23*(32-22)+19.6 + a - b*c"));
	}
	
	public static void testService() throws DocumentException
	{
		final int add = 0;
		final int multi = 1;
		IService addService1 = new IService()
		{
			@Override
			public boolean canHandlerTask(TaskEvent event)
			{
				return event.task.getTastType()==add;
			}

			@Override
			public Boolean interruptPublish(TaskEvent event)
			{
				return null;
			}

			@Override
			public void handlerTask(TaskEvent event)
			{
				ITask task = event.task;
				Map<String, Object> paramMap = task.getTaskParams();
				int left = ObjectUtil.objectToInt(paramMap.get("left"));
				int right = ObjectUtil.objectToInt(paramMap.get("right"));
				int ret = left + right;
				event.success = true;
				event.ret = ret;
			}
		};
		
		IService addService2 = new IService()
		{
			@Override
			public boolean canHandlerTask(TaskEvent event)
			{
				return event.task.getTastType()==add;
			}

			@Override
			public Boolean interruptPublish(TaskEvent event)
			{
				return null;
			}

			@Override
			public void handlerTask(TaskEvent event)
			{
				ITask task = event.task;
				Map<String, Object> paramMap = task.getTaskParams();
				int left = ObjectUtil.objectToInt(paramMap.get("left"));
				int right = ObjectUtil.objectToInt(paramMap.get("right"));
				double ret = left + right;
				event.success = true;
				event.ret = ret;
			}
		};
		
		IService multiService = new IService()
		{
			@Override
			public boolean canHandlerTask(TaskEvent event)
			{
				return event.task.getTastType()==multi;
			}

			@Override
			public Boolean interruptPublish(TaskEvent event)
			{
				return null;
			}

			@Override
			public void handlerTask(TaskEvent event)
			{
				ITask task = event.task;
				Map<String, Object> paramMap = task.getTaskParams();
				int left = ObjectUtil.objectToInt(paramMap.get("left"));
				int right = ObjectUtil.objectToInt(paramMap.get("right"));
				int ret = left * right;
				event.success = true;
				event.ret = ret;
			}
		};
		
		IPublisher publisher = new IPublisher()
		{
			@Override
			public void beforeTaskRun(TaskEvent event)
			{
				ITask task = event.task;
				Map<String, Object> paramMap = task.getTaskParams();
				int left = ObjectUtil.objectToInt(paramMap.get("left"));
				int right = ObjectUtil.objectToInt(paramMap.get("right"));
				char op = task.getTastType()==add?'+':'*';
				System.out.println(task+" : "+left+op+right+"=?");
			}

			@Override
			public void afterTastRun(TaskEvent event)
			{
				System.out.println(event.task+" : return "+event.success+" with "+event.ret);
			}
		};
		
		ServiceManager serviceManager = ServiceManager.getInstance();
		serviceManager.registerService(add, addService1);
		serviceManager.registerService(add, addService2);
		serviceManager.registerService(multi, multiService);
		Random random = new Random();
		for(int i=0;i<100;++i)
		{
			Task task = new Task(random.nextInt(2)==0?add:multi);
			task.addParam("left", random.nextInt(101));
			task.addParam("right", random.nextInt(101));
			serviceManager.addTask(publisher, task);
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			//testDbPool();
			//testJs();
			//testCompiler();
			/*ConnectionPool cp = (ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(1001);
			DataBase db = cp.getNewDataBase();
			DataSet ds = db.sqlSelect("select * from XLSYS_PARTDETAIL");
			DataSetUtil.dumpData(ds, true, true, false);
			cp.close();*/
			//testLockUtil();
			//testVirtualDataSet();
			//insertTestData();
			//test();
			// updateTestData();
			// testLockUtil();
			// testHttpClientTransfer();
			// testCompiler();
			// testNumUtil();
			testService();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
