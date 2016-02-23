package xlsys.base.io.mail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮件测试类
 * @author Lewis
 *
 */
public class MailTest
{

	public static void main(String[] args) throws Exception
	{
		MailSender mailSender = new MailSender("smtp.39f.net", "lewis@39f.net", "Sarah121D");
		List<String> toAddr = new ArrayList<String>();
		toAddr.add("adong_84@163.com");
		MailInfo mailInfo = new MailInfo("lewis@39f.net", toAddr);
		mailInfo.addBccAddress("lewis@39f.net");
		mailInfo.setSubject("Test");
		mailInfo.addText("11111111111");
		mailInfo.addText("222222222222");
		mailInfo.addHtml("<html>Dear aaa:</html>");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		File file = new File("D:/Penguins.jpg");
		FileInputStream fis = new FileInputStream(file);
		int ch = -1;
		while((ch=fis.read())!=-1)
		{
			baos.write(ch);
		}
		fis.close();
		mailInfo.addHtml("<html><a href=\"http://172.16.129.3/snsoft/webstart.jnlp\">这是测试HTML链接</a><p><img src=\"cid:pic001\"><p>asdadadsasdad</html>");
		mailInfo.addPicture(baos.toByteArray(), "pic001");
		baos.close();
		baos = new ByteArrayOutputStream();
		file = new File("D:/doc/temp.sql");
		fis = new FileInputStream(file);
		ch = -1;
		while((ch=fis.read())!=-1)
		{
			baos.write(ch);
		}
		mailInfo.addAttachment(baos.toByteArray(), file.getName());
		fis.close();
		baos.close();
		mailInfo.addAttachment("这只是一个文本附件".getBytes(), "手写附件.txt");
		mailInfo.addText("333333333333");
		mailSender.sendMail(mailInfo);
	}

}
