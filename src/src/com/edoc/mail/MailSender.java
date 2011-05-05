package com.edoc.mail;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import com.edoc.service.SysParamTypeDef;
import com.edoc.service.SystemParam;
import com.edoc.service.SystemParamSupport;

/**
 * 邮件发送类
 * @author 		陈超
 *
 */
@Component("mailSender")
public class MailSender {
	private SystemParamSupport sysParamsSupport = new SystemParamSupport();
	
	
	public boolean send(com.edoc.mail.Message msg, List<String> toEmails){
		if(msg!=null && (toEmails!=null && !toEmails.isEmpty())){
			MailProp mailProp = loadMailProp();
			if(mailProp!=null){
				Properties props = new Properties();
				props.put("mail.smtp.host", mailProp.getHost());
				props.put("mail.smtp.auth", "true");
//				props.put("mail.smtp.port", mailProp.getPort());
				
				//创建Session
				final String username = mailProp.getUser();
				final String password = mailProp.getPassword();
				Session session = Session.getDefaultInstance(props, new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
				    }
				});
				try {
					MimeMessage mimeMsg = new MimeMessage(session);
					mimeMsg.setFrom(new InternetAddress(mailProp.getFrom()));
					InternetAddress[] address = new InternetAddress[toEmails.size()];
					for(int i=0;i<toEmails.size();i++){
						address[i] = new InternetAddress(toEmails.get(i));
					}
				    mimeMsg.setRecipients(Message.RecipientType.TO, address);
				    mimeMsg.setSubject(msg.getSubject());
				    mimeMsg.setText(msg.getContent());
				    
				    Transport.send(mimeMsg);
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			
		}else{
			System.out.println("参数有问题!");
		}
		return false;
	}

	
	/**
	 * 加载邮件服务器参数
	 * @return
	 */
	private MailProp loadMailProp() {
		MailProp mailProp = null;
		List<SystemParam> params = sysParamsSupport.findSysParams(SysParamTypeDef.PARAM_MAIL);
		if(params!=null && !params.isEmpty()){
			mailProp = new MailProp();
			for(SystemParam p:params){
				if(p.getName().equals("host")){
					mailProp.setHost(p.getValue());
				}else if(p.getName().equals("from")){
					mailProp.setFrom(p.getValue());
				}else if(p.getName().equals("user")){
					mailProp.setUser(p.getValue());
				}else if(p.getName().equals("password")){
					mailProp.setPassword(p.getValue());
				}else if(p.getName().equals("port")){
					mailProp.setPort(p.getValue());
				}
			}
		}
		return mailProp;
	}
}
