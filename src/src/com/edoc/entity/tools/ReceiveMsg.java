package com.edoc.entity.tools;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edoc.entity.AbstractBaseEntity;
import com.edoc.utils.RandomGUID;

/**
 * 接收到的消息
 * @author 陈超
 *
 */
@Entity
@Table(name = "sys_msg_receive")
public class ReceiveMsg extends AbstractBaseEntity{
	
	@Id
	@Column(name = "ID")
	private String id = "";
	
	@Column(name = "C_TITLE", nullable = true)
	private String title = "";			//消息标题
	
	@Column(name = "C_CONTENT", nullable = true)
	private String content = "";		//消息正文
	
	@Column(name = "C_TOUSERID", nullable = true)
	private String toUserId = "";		//收件人Id
	
	@Column(name = "C_TOUSERNAME", nullable = true)
	private String toUsernName = "";		//收件人姓名
	
	@Column(name = "C_FROMUSERID", nullable = true)
	private String fromUserId = "";		//发件人Id
	
	@Column(name = "C_FROMUSERNAME", nullable = true)
	private String fromUserName = "";	//发件人名称
	
	@Column(name = "I_STATE", nullable = true)
	private int state = 0;				//查阅情况,0未查阅,1已查阅
	
	@Column(name = "I_ISDELETE", nullable = true)
	private int isDelete =0;				//是否删除
	
	@Column(name = "D_CREATEDATE", nullable = true)
	private Date createDate = null;			//创建时间
	
	@Column(name = "C_SENDMSGID", nullable = true)
	private String sendMsgId = "";
	
	public ReceiveMsg(){
		super();
		id = new RandomGUID().toString();
		createDate = new Date();
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUsernName() {
		return toUsernName;
	}

	public void setToUsernName(String toUsernName) {
		this.toUsernName = toUsernName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSendMsgId() {
		return sendMsgId;
	}

	public void setSendMsgId(String sendMsgId) {
		this.sendMsgId = sendMsgId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
}
