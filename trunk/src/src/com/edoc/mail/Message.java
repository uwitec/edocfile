package com.edoc.mail;

public class Message {
	private String subject = "";		//�ʼ�����
	private String content = "";		//�ʼ�����
	
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
