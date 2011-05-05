package com.edoc.mail;
/**
 * 邮件服务配置信息
 * @author 		陈超
 *
 */
public class MailProp {
	String host = ""; 			// 发送邮件服务器
	String user = ""; 			// 邮件服务器登录用户名
	String password = ""; 		// 邮件服务器登录密码
	String from = ""; 			// 发送人邮件地址
	String port = "";			// 端口号
	
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
