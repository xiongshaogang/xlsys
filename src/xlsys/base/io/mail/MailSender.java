package xlsys.base.io.mail;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 邮件发送器
 * @author Lewis
 *
 */
public class MailSender
{
	private String smtpHost;
	private String smtpPort;
	private boolean validate;
	private String username;
	private String password;
	
	public MailSender(String smtpHost, String smtpPort, boolean validate, String username, String password)
	{
		this.smtpHost = smtpHost;
		this.smtpPort = smtpPort;
		this.validate = validate;
		this.username = username;
		this.password = password;
	}

	public MailSender(String smtpHost)
	{
		this(smtpHost, "25", false, null, null);
	}

	public MailSender(String smtpHost, String username, String password)
	{
		this(smtpHost, "25", true, username, password);
	}
	
	public void sendMail(MailInfo mailInfo)
	{
		 Properties prop = new Properties();
		 prop.put("mail.smtp.host", smtpHost);   
		 prop.put("mail.smtp.port", smtpPort);   
		 prop.put("mail.smtp.auth", validate ? "true" : "false");  
		 MailAuthenticator authenticator = null;
		 if(validate)
		 {
			 authenticator = new MailAuthenticator(username, password);
		 }
		 try
		 {
			 Session mailSession = Session.getDefaultInstance(prop,authenticator);
			 Message msg = new MimeMessage(mailSession);
			 // 发件人
			 msg.setFrom(new InternetAddress(mailInfo.getFromAddress()));
			 // 收件人
			 InternetAddress[] toInAddr = new InternetAddress[mailInfo.getToAddress().size()];
			 for(int i=0;i<mailInfo.getToAddress().size();i++)
			 {
				 toInAddr[i] = new InternetAddress(mailInfo.getToAddress().get(i));
			 }
			 msg.setRecipients(Message.RecipientType.TO, toInAddr);
			 // 抄送
			 InternetAddress[] ccInAddr = new InternetAddress[mailInfo.getCcAddress().size()];
			 for(int i=0;i<mailInfo.getCcAddress().size();i++)
			 {
				 ccInAddr[i] = new InternetAddress(mailInfo.getCcAddress().get(i));
			 }
			 msg.setRecipients(Message.RecipientType.CC, ccInAddr);
			 // 密送
			 InternetAddress[] bccInAddr = new InternetAddress[mailInfo.getBccAddress().size()];
			 for(int i=0;i<mailInfo.getBccAddress().size();i++)
			 {
				 bccInAddr[i] = new InternetAddress(mailInfo.getBccAddress().get(i));
			 }
			 msg.setRecipients(Message.RecipientType.BCC, bccInAddr);
			 // 主题
			 msg.setSubject(mailInfo.getSubject());
			 // 发送时间
			 msg.setSentDate(new Date());
			 // 发送内容
			 MimeMultipart multiPart = new MimeMultipart();
			 multiPart.setSubType("mixed");
			 for(BodyPart part : mailInfo.getBodys())
			 {
				 multiPart.addBodyPart(part);
			 }
			 msg.setContent(multiPart);
			 msg.setHeader("MIME-Version", "1.0");
			 msg.setHeader("Content-Type", multiPart.getContentType());
			 // 发送邮件
			 Transport transport = mailSession.getTransport("smtp");
			 if(validate)
			 {
				 transport.connect(smtpHost, Integer.parseInt(smtpPort), username, password);
			 }
			 else
			 {
				 transport.connect();
			 }
			 transport.sendMessage(msg, msg.getAllRecipients());
			 //Transport.send(msg);
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	}

	public String getSmtpHost()
	{
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost)
	{
		this.smtpHost = smtpHost;
	}

	public String getSmtpPort()
	{
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort)
	{
		this.smtpPort = smtpPort;
	}

	public boolean isValidate()
	{
		return validate;
	}

	public void setValidate(boolean validate)
	{
		this.validate = validate;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public static void main(String[] args) throws Exception
	{
		MailSender mailSender = new MailSender("smtp.163.com", "adong_84", "Icey121DD");
		List<String> toAddr = new ArrayList<String>();
		toAddr.add("adong_84@163.com");
		//toAddr.add("Dennis@39f.net");
		//toAddr.add("lisa@39f.net");
		MailInfo mailInfo = new MailInfo("adong_84@163.com", toAddr);
		/*mailInfo.addCcAddress("lewis@39f.net");
		mailInfo.addCcAddress("Dennis@39f.net");
		mailInfo.addCcAddress("lisa@39f.net");
		mailInfo.addBccAddress("lewis@39f.net");
		mailInfo.addBccAddress("Dennis@39f.net");
		mailInfo.addBccAddress("lisa@39f.net");*/
		mailInfo.setSubject("瑶瑶");
		//mailInfo.addText("宝贝在上网");
		/*
		mailInfo.addHtml("<html><a href=\"http://172.16.129.3/snsoft/webstart.jnlp\">这是测试HTML链接</a><p><img src=\"cid:pic001\"></html>");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		File file = new File("D:/清理往来帐目规范指引.doc");
		FileInputStream fis = new FileInputStream(file);
		int ch = -1;
		while((ch=fis.read())!=-1)
		{
			baos.write(ch);
		}
		mailInfo.addAttachment(baos.toByteArray(), file.getName());
		fis.close();
		baos.close();
		mailInfo.addAttachment("这只是一个文本附件".getBytes(), "手写附件.txt");
		baos = new ByteArrayOutputStream();
		file = new File("D:/test.png");
		fis = new FileInputStream(file);
		ch = -1;
		while((ch=fis.read())!=-1)
		{
			baos.write(ch);
		}
		mailInfo.addPicture(baos.toByteArray(), "pic001");
		*/
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Robot rb = new Robot();
		BufferedImage bi = rb.createScreenCapture(new Rectangle(0,0,(int)d.getWidth(),(int)d.getHeight()));
		File file = new File("C:/DELL/yy.jpg");
		ImageIO.write(bi, "jpg", file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis = new FileInputStream(file);
		int ch = -1;
		while((ch=fis.read())!=-1)
		{
			baos.write(ch);
		}
		fis.close();
		mailInfo.addHtml("<html>宝贝在上网<p><img src=\"cid:yy.jpg\"></html>");
		mailInfo.addPicture(baos.toByteArray(), "yy.jpg");
		mailSender.sendMail(mailInfo);
	}
}
