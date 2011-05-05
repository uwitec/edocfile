package com.edoc.entity.files;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edoc.entity.AbstractBaseEntity;
import com.edoc.utils.RandomGUID;
@Entity
@Table(name = "sys_visituserinfo")
public class VisitUserInfo extends AbstractBaseEntity{
	private static final long serialVersionUID = 1L;
	private static final String PERMISSION_VIEW = "view";
	private static final String PERMISSION_DOWNLOAD = "download";
	
	@Id
	@Column(name = "ID")
	private String id = null;
	
	@Column(name = "C_VISITUSERID", nullable = true)
	private String visitUserId = null;		//共享用户Id
	@Column(name = "C_VISITUSERNAME", nullable = true)
	private String visitUserName = null;	//共享用户名称
	
	@Column(name = "I_PERVIEW", nullable = true)
	private int perView = 0;				//查看权限
	@Column(name = "I_PERDOWNLOAD", nullable = true)
	private int perDownLoad = 0;			//下载权限
	
	@Column(name = "D_SHORESTARTDATE", nullable = true)
	private Date shoreStartDate = null;		//共享开始日期
	@Column(name = "D_SHOREENDDATE", nullable = true)
	private Date shoreEndDate = null;		//共享截止日期
	
	@Column(name = "C_SOURCEFILEID", nullable = true)
	private String sourceFileId = null;		//源文件Id
	
	@Column(name = "I_ISDELETE", nullable = true)
	private int isDelete =0;							//该字段无效
	
	public VisitUserInfo(){
		super();
		id = new RandomGUID().toString();
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVisitUserId() {
		return visitUserId;
	}

	public void setVisitUserId(String visitUserId) {
		this.visitUserId = visitUserId;
	}

	public String getVisitUserName() {
		return visitUserName;
	}

	public void setVisitUserName(String visitUserName) {
		this.visitUserName = visitUserName;
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

	public Date getShoreStartDate() {
		return shoreStartDate;
	}

	public void setShoreStartDate(Date shoreStartDate) {
		this.shoreStartDate = shoreStartDate;
	}

	public Date getShoreEndDate() {
		return shoreEndDate;
	}

	public void setShoreEndDate(Date shoreEndDate) {
		this.shoreEndDate = shoreEndDate;
	}

	public String getSourceFileId() {
		return sourceFileId;
	}

	public void setSourceFileId(String sourceFileId) {
		this.sourceFileId = sourceFileId;
	}
	
	public void setPermissions(String[] permissions){
		if(permissions!=null && permissions.length>0){
			for(String s:permissions){
				if(s.toLowerCase().equals(VisitUserInfo.PERMISSION_VIEW)){
					this.setPerView(1);
				}else if(s.toLowerCase().equals(VisitUserInfo.PERMISSION_DOWNLOAD)){
					this.setPerDownLoad(1);
				}
			}
		}
	}
	
}
