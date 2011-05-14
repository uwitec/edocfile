package com.edoc.entity.tools;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edoc.entity.AbstractBaseEntity;
import com.edoc.utils.RandomGUID;

/**
 * 消息
 * @author 陈超
 *
 */
@Entity
@Table(name = "sys_msg_send")
public class SendMsg extends AbstractBaseEntity{
	
	@Id
	@Column(name = "ID")
	private String id = "";
	
	@Column(name = "C_TITLE", nullable = true)
	private String title = "";			//消息标题
	
	@Column(name = "C_CONTENT", nullable = true)
	private String content = "";		//消息正文
	
	@Column(name = "C_FROMUSERID", nullable = true)
	private String fromUserId = "";		//发件人Id
	
	@Column(name = "C_FROMUSERNAME", nullable = true)
	private String fromUserName = "";	//发件人名称
	
	@Column(name = "I_STATE", nullable = true)
	private int state = 0;				//是否已发送,0未发送,1已发送
	
	@Column(name = "I_ISDELETE", nullable = true)
	private int isDelete =0;				//是否删除
	
	@Column(name = "D_CREATEDATE", nullable = true)
	private Date createDate = null;			//创建时间
	
	@Column(name = "C_RECEIVERIDS", nullable = true)
	private String receiverIds = "";		//收件人Id
	
	@Column(name = "C_RECEIVERNAMES", nullable = true)
	private String receiverNames = "";		//收件人Id
	
	public SendMsg(){
		super();
		id = new RandomGUID().toString();
		createDate = new Date();
	}

	public String getReceiverIds() {
		return receiverIds;
	}

	public void setReceiverIds(String receiverIds) {
		this.receiverIds = receiverIds;
	}

	public String getReceiverNames() {
		return receiverNames;
	}

	public void setReceiverNames(String receiverNames) {
		this.receiverNames = receiverNames;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
}
