package com.edoc.entity.files;

import java.util.Date;

import com.edoc.utils.RandomGUID;

/**
 * ��ע��Ϣ
 * @author �³�
 *
 */
public class FileComment {
	private String id = "";					//ID
	private String content = "";			//��������
	private String userId = "";				//������ID
	private String userName = "";			//����������
	private String sourceFileId = "";		//�����ļ�ID
	private String sourceFileName = "";		//�����ļ�����
	private String sourceFileVersion = "";	//�����ļ��汾��Ϣ
	private Date createTime = null;			//�����¼�
	
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
