package xlsys.base.io.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

/**
 * 邮件信息，用来封装整封邮件所包含的内容。
 * @author Lewis
 *
 */
public class MailInfo
{
	public static final int TYPE_TEXT = 0;
	public static final int TYPE_HTML = 1;
	public static final int TYPE_ATTACHMENT = 2;
	public static final int TYPE_PICTURE = 3;
	
	private String fromAddress; // 发件人
	private List<String> toAddress; // 收件人
	private List<String> ccAddress; // 抄送
	private List<String> bccAddress; // 密送
	private String subject; // 主题
	private List<BodyPart> bodys;
	private MimeBodyPart mainBody;
	private MimeMultipart mainBodyMultipart;
	private MimeBodyPart subHtmlBody;
	private String charsetName;
	
	public MailInfo()
	{
		toAddress = new ArrayList<String>();
		ccAddress = new ArrayList<String>();
		bccAddress = new ArrayList<String>();
		bodys = new ArrayList<BodyPart>();
		charsetName = "utf-8";
	}
	
	public MailInfo(String fromAddress)
	{
		this();
		this.fromAddress = fromAddress;
	}
	
	public MailInfo(String fromAddress, String toAddr)
	{
		this(fromAddress);
		toAddress.add(toAddr);
	}

	public MailInfo(String fromAddress, List<String> toAddress)
	{
		this(fromAddress);
		this.toAddress.addAll(toAddress);
	}
	
	public void addText(String textStr) throws MessagingException, IOException
	{
		addContent(TYPE_TEXT, textStr.getBytes(charsetName), null);
	}
	
	public void addHtml(String htmlStr) throws MessagingException, IOException
	{
		addContent(TYPE_HTML, htmlStr.getBytes(charsetName), null);
	}
	
	public void addAttachment(byte[] content, String name) throws MessagingException, IOException
	{
		addContent(TYPE_ATTACHMENT, content, name);
	}
	
	public void addPicture(byte[] picture, String contentID) throws MessagingException, IOException
	{
		addContent(TYPE_PICTURE, picture, contentID);
	}
	
	private void initMainBody() throws MessagingException
	{
		if(mainBody==null)
		{
			mainBody = new MimeBodyPart();
			mainBodyMultipart = new MimeMultipart(); 
			mainBodyMultipart.setSubType("related");
			mainBody.setContent(mainBodyMultipart); 
			subHtmlBody = new MimeBodyPart(); 
			mainBodyMultipart.addBodyPart(subHtmlBody); 
			subHtmlBody.setContent("", "text/html;charset="+charsetName);
		}
	}
	
	public void addContent(int type, byte[] content, String contentID) throws MessagingException, IOException
	{
		MimeBodyPart msgbody = null;
		if(type==TYPE_TEXT)
		{
			// msgbody.setContent(new String(content, charsetName), "text/plain;charset="+charsetName);
			initMainBody();
			msgbody = mainBody;
			String oldContent = (String) subHtmlBody.getContent();
			subHtmlBody.setContent(oldContent+"<p><html>"+new String(content, charsetName)+"</html>", "text/html;charset="+charsetName);
		}
		else if(type==TYPE_HTML)
		{
			//msgbody.setContent(new String(content, charsetName), "text/html;charset="+charsetName);
			initMainBody();
			msgbody = mainBody;
			String oldContent = (String) subHtmlBody.getContent();
			subHtmlBody.setContent(oldContent+"<p>"+new String(content, charsetName), "text/html;charset="+charsetName);
		}
		else if(type==TYPE_ATTACHMENT)
		{
			msgbody = new MimeBodyPart();
			ByteArrayDataSource bads = new ByteArrayDataSource(content,"application/octet-stream");
			msgbody.setDataHandler(new DataHandler(bads));
			msgbody.setFileName(MimeUtility.encodeWord(contentID, charsetName,null));
		}
		else if(type==TYPE_PICTURE)
		{
			/*ByteArrayDataSource bads = new ByteArrayDataSource(content,"application/octet-stream");
			msgbody.setDataHandler(new DataHandler(bads));
			msgbody.setContentID(MimeUtility.encodeWord(contentID, charsetName,null));*/
			initMainBody();
			MimeBodyPart picPart = new MimeBodyPart(); 
			ByteArrayDataSource bads = new ByteArrayDataSource(content,"application/octet-stream");
			picPart.setDataHandler(new DataHandler(bads));
			picPart.setDisposition(MimeBodyPart.INLINE);
			picPart.setContentID(MimeUtility.encodeWord(contentID, charsetName,null));
			mainBodyMultipart.addBodyPart(picPart);
		}
		if(msgbody!=null&&!bodys.contains(msgbody)) bodys.add(msgbody);
	}

	public String getSubject()
	{
		return subject;
	}
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	public List<BodyPart> getBodys()
	{
		return bodys;
	}
	public void addToAddress(String toAddr)
	{
		toAddress.add(toAddr);
	}
	public void addCcAddress(String ccAddr)
	{
		ccAddress.add(ccAddr);
	}
	public void addBccAddress(String bccAddr)
	{
		bccAddress.add(bccAddr);
	}
	public void setFromAddress(String fromAddress)
	{
		this.fromAddress = fromAddress;
	}
	public String getFromAddress()
	{
		return fromAddress;
	}
	public List<String> getToAddress()
	{
		return toAddress;
	}
	public List<String> getCcAddress()
	{
		return ccAddress;
	}
	public List<String> getBccAddress()
	{
		return bccAddress;
	}
	public String getCharsetName()
	{
		return charsetName;
	}
	public void setCharsetName(String charsetName)
	{
		this.charsetName = charsetName;
	}
	
}
