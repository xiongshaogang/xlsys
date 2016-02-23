package xlsys.base.script;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

/**
 * 系统Java文件对象
 * @author Lewis
 *
 */
public class XlsysJavaFileObject extends SimpleJavaFileObject
{
	// 当kind为CLASS时，用来存放编译过的class字节
	private ByteArrayOutputStream byteos;
	// 当kind为SOURCE时，用来存在java源代码
	private CharSequence source;

	/**
	 * 构造一个给定类全名和源代码的JavaFileObject
	 * @param baseName 
	 * @param source
	 * @throws URISyntaxException 
	 */
	protected XlsysJavaFileObject(String baseName, CharSequence source) throws URISyntaxException
	{
		super(URI.create("string:///" + baseName.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
		this.source = source;
	}

	/**
	 * 构造一个给定类全名和编译字节码的JavaFileObject
	 * @param baseName
	 * @param binary
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	protected XlsysJavaFileObject(String baseName, byte[] binary) throws URISyntaxException, IOException
	{
		super(URI.create("string:///" + baseName.replace('.', '/') + Kind.CLASS.extension), Kind.CLASS);
		byteos = new ByteArrayOutputStream();
		byteos.write(binary);
	}
	
	protected XlsysJavaFileObject(String baseName, JavaFileObject.Kind kind) throws URISyntaxException, IOException
	{
		super(URI.create("string:///" + baseName.replace('.', '/') + kind.extension), kind);
	}

	@Override
	public CharSequence getCharContent(final boolean ignoreEncodingErrors)
			throws UnsupportedOperationException
	{
		if(source==null) throw new UnsupportedOperationException("The kind of this JavaObject is not SOURCE");
		return source;
	}

	@Override
	public InputStream openInputStream()
	{
		ByteArrayInputStream bais = null;
		if(byteos!=null)
		{
			bais = new ByteArrayInputStream(byteos.toByteArray());
		}
		return bais;
	}

	@Override
	public OutputStream openOutputStream()
	{
		if(byteos==null) byteos = new ByteArrayOutputStream();
		return byteos;
	}
}
