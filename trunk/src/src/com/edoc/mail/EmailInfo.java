package com.edoc.mail;

/**
 * �ʼ���Ϣ
 * @author �³�
 *
 */
public class EmailInfo {
	private String id = "";
	private String toAddress = "";
	private String fromAddress = "";
	private String toUserName = "";		//�ռ��˵��û�����
	private String toUserId = "";		//�ռ��˵�id
	private String host = "";
	private String username = "";
	private String password = "";
	private String port = "";
	private String fileName = "";		//�����ļ�����
	private String file = "";			//����·������,�� ; ����
	private String subject = "";		//�ʼ�����
	private String content = "";		//�ʼ�����
	
	public EmailInfo(){
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
}
