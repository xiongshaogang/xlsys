package xlsys.base.io.transfer.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.io.pack.XlsysPackage;
import xlsys.base.io.util.IOUtil;
import xlsys.base.log.LogUtil;

/**
 * 使用Http协议传输数据的客户端传输类
 * @author Lewis
 *
 */
public class ServletClientTransfer extends ClientTransfer
{
	private String servletUrl;
	
	protected ServletClientTransfer(String servletUrl) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		super();
		this.servletUrl = servletUrl;
	}
	
	@Override
	protected XlsysPackage postPackage(XlsysPackage sendPkg)
	{
		XlsysPackage recvPkg = null;
		CloseableHttpClient httpClient = null;
		try
		{
			HttpPost post = new HttpPost(servletUrl);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte sendSeriMode = getSeriMode();
			// 写入序列化模式
			baos.write(sendSeriMode);
			// 写入包内容
			if(sendSeriMode==XLSYS.SERIALIZATION_MODE_JDK)
			{
				IOUtil.writeObject(sendPkg, baos);
			}
			else if(sendSeriMode==XLSYS.SERIALIZATION_MODE_INTERNAL)
			{
				IOUtil.writeInternalObject(sendPkg, baos);
			}
			else if(sendSeriMode==XLSYS.SERIALIZATION_MODE_JSON)
			{
				IOUtil.writeJSONObject(sendPkg, baos);
			}
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			InputStreamEntity ise = new InputStreamEntity(bais,baos.size(),ContentType.DEFAULT_BINARY);
			post.setEntity(ise);
			httpClient = HttpClients.createDefault();
			HttpResponse response = httpClient.execute(post);
			HttpEntity re = response.getEntity();
			long len = re.getContentLength();
			if(len>0)
			{
				InputStream is = re.getContent();
				// 读取序列化模式
				byte recvSeriMode = (byte) is.read();
				// 读取包内容
				if(recvSeriMode==XLSYS.SERIALIZATION_MODE_JDK)
				{
					recvPkg = (XlsysPackage) IOUtil.readObject(is);
				}
				else if(recvSeriMode==XLSYS.SERIALIZATION_MODE_INTERNAL)
				{
					recvPkg = (XlsysPackage) IOUtil.readInternalObject(is);
				}
				else if(recvSeriMode==XLSYS.SERIALIZATION_MODE_JSON)
				{
					recvPkg = (XlsysPackage) IOUtil.readJSONObject(is);
				}
			}
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
		}
		finally
		{
			try
			{
				if(httpClient!=null) httpClient.close();
			}
			catch (IOException e)
			{
				LogUtil.printlnError(e);
			}
		}
		return recvPkg;
	}
}
