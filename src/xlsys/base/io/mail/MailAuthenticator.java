package xlsys.base.io.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 邮件身份验证的实现类
 * @author Lewis
 *
 */
public class MailAuthenticator extends Authenticator
{
	private String username;
	private String password;
	
	public MailAuthenticator(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(username, password);
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}
}
