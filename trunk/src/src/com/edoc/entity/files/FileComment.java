package com.edoc.entity.files;

import java.util.Date;

import com.edoc.utils.RandomGUID;

/**
 * 评注信息
 * @author 陈超
 *
 */
public class FileComment {
	private String id = "";					//ID
	private String content = "";			//评论内容
	private String userId = "";				//评论人ID
	private String userName = "";			//评论人姓名
	private String sourceFileId = "";		//评论文件ID
	private String sourceFileName = "";		//评论文件名称
	private String sourceFileVersion = "";	//评论文件版本信息
	private Date createTime = null;			//评论事件
	
	public FileComment(){
		super();
		id = new RandomGUID().toString();
		createTime = new Date();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSourceFileId() {
		return sourceFileId;
	}

	public void setSourceFileId(String sourceFileId) {
		this.sourceFileId = sourceFileId;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public String getSourceFileVersion() {
		return sourceFileVersion;
	}

	public void setSourceFileVersion(String sourceFileVersion) {
		this.sourceFileVersion = sourceFileVersion;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
