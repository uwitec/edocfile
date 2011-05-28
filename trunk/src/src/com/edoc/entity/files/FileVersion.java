package com.edoc.entity.files;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edoc.entity.AbstractBaseEntity;
import com.edoc.utils.RandomGUID;

/**
 * 文件版本信息
 * @author 陈超 2010-12-23
 *
 */
@Entity
@Table(name = "sys_fileversion")
public class FileVersion extends AbstractBaseEntity{
	@Id
	@Column(name = "ID")
	private String id = "";
	
	@Column(name = "C_FILENAME", nullable = true)
	private String fileName = "";
	
	@Column(name = "C_VERSION", nullable = true)
	private String version = "";		//版本号
	
	@Column(name = "C_DESC", nullable = true)
	private String desc = "";			//描述
	
	@Column(name = "C_EDOCFILEID", nullable = true)
	private String edocFileId = "";		//edocFileID
	
	@Column(name = "D_CREATETIME", nullable = true)
	private Date createTime = null;						//创建时间
	
	@Column(name = "D_UPDATETIME", nullable = true)
	private Date updateTime = null;						//上一次修改时间
	
	@Column(name = "C_FILETYPE", nullable = true)
	private String fileType = "文件夹";					//文件类型,如:Word文档,Excel文档等
	
	@Column(name = "C_CREATORID", nullable = true)
	private String creatorId = null;					//创建人ID
	
	@Column(name = "C_CREATORNAME", nullable = true)
	private String creatorName = null;					//创建人姓名	
	
	@Column(name = "F_FILESIZE", nullable = true)
	private float fileSize = 0;							//文件大小
	
	@Column(name = "I_ISDELETE", nullable = true)
	private int isDelete =0;							//是否删除,0未删除,1已删除
	
	@Column(name = "C_ICON", nullable = true)
	private String icon = "icon/folder.png";			//对应的图标
	
	@Column(name = "C_NEWFILENAME", nullable = true)
	private String newFileName = "";
	
	@Column(name = "C_FILESUFFIX", nullable = true)
	private String fileSuffix = null;					//文件后缀如doc、xls等
	
	@Column(name = "C_UPDATEUSERID", nullable = true)
	private String updateUserId = "";					//修改人的ID
	
	@Column(name = "C_UPDATEUSERNAME", nullable = true)
	private String updateUserName = "";					//修改人的姓名
	
	public FileVersion(){
		id = new RandomGUID().toString();
		createTime = new Date();
		updateTime = new Date();
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getEdocFileId() {
		return edocFileId;
	}

	public void setEdocFileId(String edocFileId) {
		this.edocFileId = edocFileId;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
		this.setIcon("icon/"+fileSuffix+".gif");
	}
	
}
