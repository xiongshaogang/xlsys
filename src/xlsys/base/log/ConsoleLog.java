package xlsys.base.log;

import java.io.PrintStream;

import xlsys.base.io.xml.XmlModel;
import xlsys.base.util.ObjectUtil;

/**
 * 控制台形式的Log实现类
 * @author Lewis
 *
 */
public class ConsoleLog extends Log
{

	protected ConsoleLog(int logLevel, XmlModel params)
	{
		super(logLevel, params);
	}

	@Override
	protected int write(int level, Object content)
	{
		PrintStream os = null;
		if(Log.LOG_LEVEL_WARN<=level) os = System.out;
		else os = System.err;
		byte[] b;
		b = ObjectUtil.objectToString(content).getBytes();
		os.write(b, 0, b.length);
		return 0;
	}

	@Override
	public void close()
	{
		// TODO
	}

}
