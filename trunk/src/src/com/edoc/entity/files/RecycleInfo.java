package com.edoc.entity.files;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;



import com.edoc.entity.AbstractBaseEntity;
import com.edoc.utils.RandomGUID;
@Entity
@Table(name = "sys_recycle")
public class RecycleInfo extends AbstractBaseEntity{
	@Id
	@Column(name = "ID")
	private String id = "";
	
	@Column(name = "C_SOURCE_ID", nullable = true)
	private String sourceId = "";
	
	@Column(name = "C_TABLENAME", nullable = true)
	private String tableName = "";
	
	@Column(name = "C_DISPLAY_NAME", nullable = true)
	private String displayName = "";
	
	@Column(name = "C_CREATOR_ID", nullable = true)
	private String creatorId = "";
	
	@Column(name = "D_DELETE_TIME", nullable = true)
	private Date deleteTime = null;
	
	@Column(name = "I_ISDELETE", nullable = true)
	private int isDelete = 0;							//��������Ч,����������ʵ���ౣ��һ��
	
	@Transient
	private String newFileName = null;					//���ļ�����
	@Transient
	private String fileType = "�ļ���";					//�ļ�����,��:Word�ĵ�,Excel�ĵ���
	@Transient
	private Date createTime = null;						//����ʱ��
	@Transient
	private Date updateTime = null;						//��һ���޸�ʱ��
	@Transient
	private float fileSize = 0;							//�ļ���С
	@Transient
	private int isFolder = 1;							//�Ƿ����ļ���,0����,1��	
	@Transient
	private String fileSuffix = null;					//�ļ���׺��doc��xls��
	@Transient
	private String icon = "icon/folder.png";			//��Ӧ��ͼ��
	@Transient
	private String desc = null;
	@Transient
	private String currentVersion = "";					//��ǰ�汾
	
//	@OneToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "C_SOURCE_ID")
//	@Cascade(value = { CascadeType.SAVE_UPDATE, CascadeType.MERGE })
//	private EdocFile edocFile = new EdocFile();

	public RecycleInfo(){
		super();
		id = new RandomGUID().toString();
		deleteTime = new Date();
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
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
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

//	public EdocFile getEdocFile() {
//		return edocFile;
//	}
//
//	public void setEdocFile(EdocFile edocFile) {
//		this.edocFile = edocFile;
//	}
	
	
}
