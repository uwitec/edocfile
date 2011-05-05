package com.edoc.entity.files;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edoc.entity.AbstractBaseEntity;
import com.edoc.utils.RandomGUID;

/**
 * �ļ���Ϣ
 * @author �³� 2010-7-21
 *
 */
@Entity
@Table(name = "sys_fileinfo")
public class EdocFile  extends AbstractBaseEntity{
	
	@Id
	@Column(name = "ID")
	private String id = null;
	
	@Column(name = "C_FILENAME", nullable = true)
	private String fileName = null;						//�ļ�����
	
	@Column(name = "C_NEWFILENAME", nullable = true)
	private String newFileName = null;					//���ļ�����
	
	@Column(name = "C_FILETYPE", nullable = true)
	private String fileType = "�ļ���";					//�ļ�����,��:Word�ĵ�,Excel�ĵ���
	
	@Column(name = "C_CREATORID", nullable = true)
	private String creatorId = null;					//������ID
	
	@Column(name = "C_CREATORNAME", nullable = true)
	private String creatorName = null;					//����������	
	
	@Column(name = "D_CREATETIME", nullable = true)
	private Date createTime = null;						//����ʱ��
	
	@Column(name = "D_UPDATETIME", nullable = true)
	private Date updateTime = null;						//��һ���޸�ʱ��
	
	@Column(name = "F_FILESIZE", nullable = true)
	private float fileSize = 0;							//�ļ���С
	
	@Column(name = "I_ISDELETE", nullable = true)
	private int isDelete =0;							//�Ƿ�ɾ��,0δɾ��,1��ɾ��
	
	@Column(name = "I_ISFOLDER", nullable = true)
	private int isFolder = 1;							//�Ƿ����ļ���,0����,1��	
		
	@Column(name = "C_FILESUFFIX", nullable = true)
	private String fileSuffix = null;					//�ļ���׺��doc��xls��
	
	@Column(name = "C_PARENTID", nullable = true)
	private String parentId = null;						//��һ��Ŀ¼ID
	
	@Column(name = "C_ICON", nullable = true)
	private String icon = "icon/folder.png";			//��Ӧ��ͼ��
	
	@Column(name = "C_DESC", nullable = true)
	private String desc = null;
	
	@Column(name = "I_ISSHORED", nullable = true)
	private int isShored = 0;							//�Ƿ���0δ����,1�ѹ���
	
	@Column(name = "C_CURRENTVERSION", nullable = true)
	private String currentVersion = "";					//��ǰ�汾
	
	public EdocFile(){
		super();
		id = new RandomGUID().toString();
		createTime = new Date();
		updateTime = new Date();
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public int getIsShored() {
		return isShored;
	}

	public void setIsShored(int isShored) {
		this.isShored = isShored;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public float getFileSize() {
		return fileSize;
	}

	public void setFileSize(float fileSize) {
		this.fileSize = fileSize;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public int getIsFolder() {
		return isFolder;
	}

	public void setIsFolder(int isFolder) {
		this.isFolder = isFolder;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
		this.setIcon("icon/"+fileSuffix+".gif");
	}
	
}
