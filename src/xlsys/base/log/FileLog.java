package xlsys.base.log;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import xlsys.base.io.util.FileUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.util.ObjectUtil;

/**
 * 文件日志形式的Log实现类
 * @author Lewis
 *
 */
public class FileLog extends Log
{
	private String outFilePath;
	private String errFilePath;
	private BufferedOutputStream out;
	private BufferedOutputStream err;

	protected FileLog(int logLevel, XmlModel params) throws FileNotFoundException
	{
		super(logLevel, params);
		outFilePath = params.getChild("outFilePath").getText();
		errFilePath = params.getChild("errFilePath").getText();
		FileUtil.createParentPath(outFilePath);
		FileUtil.createParentPath(errFilePath);
		out = IOUtil.getBufferedOutputStream(outFilePath, true);
		err = IOUtil.getBufferedOutputStream(errFilePath, true);
	}

	@Override
	public void close()
	{
		IOUtil.close(out);
		IOUtil.close(err);
	}

	@Override
	protected int write(int level, Object content)
	{
		BufferedOutputStream bos = null;
		if(Log.LOG_LEVEL_WARN<=level) bos = out;
		else bos = err;
		int ret = -1;
		try
		{
			ret = write(bos, content);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ret;
	}
	
	private int write(BufferedOutputStream bos, Object content) throws IOException
	{
		byte[] b = ObjectUtil.objectToString(content).getBytes();
		bos.write(b, 0, b.length);
		bos.flush();
		return b.length;
	}

}
