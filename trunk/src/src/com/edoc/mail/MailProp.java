package com.edoc.mail;
/**
 * �ʼ�����������Ϣ
 * @author 		�³�
 *
 */
public class MailProp {
	String host = ""; 			// �����ʼ�������
	String user = ""; 			// �ʼ���������¼�û���
	String password = ""; 		// �ʼ���������¼����
	String from = ""; 			// �������ʼ���ַ
	String port = "";			// �˿ں�
	
	public MailProp(){
		super();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	
}
