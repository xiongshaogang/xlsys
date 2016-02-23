package xlsys.base.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import xlsys.base.log.LogUtil;

public class ProcessStreamLoader extends Thread
{
	public static final int PROCESS_INPUTSTREAM = 0;
	public static final int PROCESS_ERRORSTREAM = 1;
	
	private Process p;
	private int steamType;
	private boolean close;
	
	public ProcessStreamLoader(Process p)
	{
		this.p = p;
		steamType = PROCESS_INPUTSTREAM;
		close = false;
	}
	
	public void run()
	{
		try
		{
			if(steamType==PROCESS_INPUTSTREAM)
			{
				loadInputStream();
			}
			else if(steamType==PROCESS_ERRORSTREAM)
			{
				loadErrorStream();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void loadInputStream() throws IOException
	{
		InputStream is = p.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String str = null;
		while((str=br.readLine())!=null)
		{
			LogUtil.printlnInfo(str);
		}
		if(close)
		{
			br.close();
		}
	}
	
	private void loadErrorStream() throws IOException
	{
		InputStream is = p.getErrorStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String str = null;
		while((str=br.readLine())!=null)
		{
			LogUtil.printlnError(str);
		}
		if(close)
		{
			br.close();
		}
	}

	public Process getProcess()
	{
		return p;
	}

	public void setProcess(Process p)
	{
		this.p = p;
	}

	public int getSteamType()
	{
		return steamType;
	}

	public void setSteamType(int steamType)
	{
		this.steamType = steamType;
	}

	public boolean isClose()
	{
		return close;
	}

	public void setClose(boolean close)
	{
		this.close = close;
	}
	
}
