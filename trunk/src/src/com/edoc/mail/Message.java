package com.edoc.mail;

public class Message {
	private String subject = "";		//邮件主题
	private String content = "";		//邮件内容
	
	public Message(){
		super();
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
