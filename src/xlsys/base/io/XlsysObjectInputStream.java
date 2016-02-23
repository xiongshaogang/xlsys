package xlsys.base.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import xlsys.base.script.XlsysClassLoader;

public class XlsysObjectInputStream extends ObjectInputStream
{
	public XlsysObjectInputStream(InputStream in) throws IOException
	{
		super(in);
	}

	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws ClassNotFoundException
	{
		Class<?> toFind = null;
		try
		{
			toFind = super.resolveClass(desc);
		}
		catch(Exception e){}
		if(toFind==null) toFind = XlsysClassLoader.getInstance().loadClass(desc.getName());
		return toFind;
	}

}
