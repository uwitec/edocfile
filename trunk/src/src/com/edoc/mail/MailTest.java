package com.edoc.mail;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MailTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MailSender s = new MailSender();
		EmailInfo msg = new EmailInfo();
		msg.setSubject("²âÊÔ"+(new Date()).toString());
		msg.setContent("ÓÊ¼ş²âÊÔ");
		List<String> toEmails = new LinkedList<String>();
		toEmails.add("chenchao2008208@126.com");
		s.send(msg, toEmails);

	}

}
