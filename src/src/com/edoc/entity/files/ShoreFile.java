package com.edoc.entity.files;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.edoc.entity.AbstractBaseEntity;
import com.edoc.utils.RandomGUID;

/**
 * 共享文件
 * @author 陈超 2010-8-15
 *
 */
@Entity
@Table(name = "sys_shorefile")
public class ShoreFile extends AbstractBaseEntity{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	private String id = null;
	
	@Column(name = "C_SHOREUSERNAME", nullable = true)
	private String shoreUserName = null;				//共享此文件的用户姓名
	
	@Column(name = "C_SHOREUSERID", nullable = true)
	private String shoreUserId = null;					//共享此文件的用户Id
	
	@Column(name = "D_SHORE_STARTTIME", nullable = true)
	private Date shoreStartTime = null;					//共享开始时间
	
	@Column(name = "D_SHORE_ENDTIME", nullable = true)
	private Date shoreEndTime = null;					//共享截止时间
	
	@Column(name = "C_SOURCE_ID", nullable = true)
	private String sourceFileId = null;					//共享文件的ID
	
	@Column(name = "C_PARENTID", nullable = true)
	private String parentId = null;						//上一级目录ID
	
	@Column(name = "C_FILENAME", nullable = true)
	private String fileName = null;						//上一级目录名称
	
	@Column(name = "I_ISDELETE", nullable = true)
	private int isDelete =0;							//该字段无意义
	
	@Column(name = "I_ISHOME", nullable = true)
	private int isHome = 0;								//是否是用户主目录
	
	
	@Transient
	private String newFileName = null;					//新文件名称
	@Transient
	private String fileType = "文件夹";					//文件类型,如:Word文档,Excel文档等
	@Transient
	private Date createTime = null;						//创建时间
	@Transient
	private Date updateTime = null;						//上一次修改时间
	@Transient
	private float fileSize = 0;							//文件大小
	@Transient
	private int isFolder = 1;							//是否是文件夹,0不是,1是	
	@Transient
	private String fileSuffix = null;					//文件后缀如doc、xls等
	@Transient
	private String icon = "icon/folder.png";			//对应的图标
	@Transient
	private String desc = null;
	@Transient
	private String currentVersion = "";					//当前版本
	@Transient
	private int shoredFlag = 0;							//判断对于的文件是否已经共享
	@Transient
	private int perView = 0;							//查看权限
	@Transient
	private int perDownLoad = 0;						//下载权限
	
//	private String visitUserId = null;					//访问用户的ID
//
//	@OneToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="C_USERID")
//	private User user = null;							//允许访问该文件的用户
	
	public ShoreFile(){
		super();
		id = new RandomGUID().toString();
	}
	
	public int getIsHome() {
		return isHome;
	}

	public void setIsHome(int isHome) {
		this.isHome = isHome;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public int getPerView() {
		return perView;
	}

	public void setPerView(int perView) {
		this.perView = perView;
	}

	public int getPerDownLoad() {
		return perDownLoad;
	}

	public void setPerDownLoad(int perDownLoad) {
		this.perDownLoad = perDownLoad;
	}

	public int getShoredFlag() {
		return shoredFlag;
	}

	public void setShoredFlag(int shoredFlag) {
		this.shoredFlag = shoredFlag;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSourceFileId() {
		return sourceFileId;
	}


	public void setSourceFileId(String sourceFileId) {
		this.sourceFileId = sourceFileId;
	}


	public Date getShoreStartTime() {
		return shoreStartTime;
	}


	public void setShoreStartTime(Date shoreStartTime) {
		this.shoreStartTime = shoreStartTime;
	}


	public Date getShoreEndTime() {
		return shoreEndTime;
	}


	public void setShoreEndTime(Date shoreEndTime) {
		this.shoreEndTime = shoreEndTime;
	}


	public String getShoreUserId() {
		return shoreUserId;
	}

	public void setShoreUserId(String shoreUserId) {
		this.shoreUserId = shoreUserId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShoreUserName() {
		return shoreUserName;
	}

	public void setShoreUserName(String shoreUserName) {
		this.shoreUserName = shoreUserName;
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


//	public synchronized User getUser() {
//		return user;
//	}
//
//	public synchronized void setUser(User user) {
//		this.user = user;
//	}
//	
}
