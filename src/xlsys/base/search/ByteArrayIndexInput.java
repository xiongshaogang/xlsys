package xlsys.base.search;

import java.io.IOException;
import java.util.Arrays;

import org.apache.lucene.store.IndexInput;

public class ByteArrayIndexInput extends IndexInput
{
	private byte[] content;
	private long pos;
	
	public ByteArrayIndexInput(String resourceDescription, byte[] content)
	{
		super(resourceDescription);
		this.content = content;
		pos = 0;
	}

	@Override
	public void close() throws IOException
	{
		content = null;
	}

	@Override
	public long getFilePointer()
	{
		return pos;
	}

	@Override
	public long length()
	{
		return content.length;
	}

	@Override
	public void seek(long pos) throws IOException
	{
		this.pos = pos;
	}

	@Override
	public IndexInput slice(String sliceDescription, long offset, long length) throws IOException
	{
		byte[] sliceContent = Arrays.copyOfRange(content, (int)offset, (int)(offset+length));
		return new ByteArrayIndexInput(getFullSliceDescription(sliceDescription), sliceContent);
	}

	@Override
	public byte readByte() throws IOException
	{
		return content[(int)(pos++)];
	}

	@Override
	public void readBytes(byte[] b, int offset, int len) throws IOException
	{
		for(int i=offset;i<(len+offset)&&i<b.length;++i)
		{
			b[i] = readByte();
		}
	}

}
