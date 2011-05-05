package com.edoc.entity.baseinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edoc.entity.AbstractBaseEntity;
import com.edoc.utils.RandomGUID;
/**
 * 用户信息
 * @author 陈超
 *
 */
@Entity
@Table(name = "sys_user")
public class User extends AbstractBaseEntity{
	@Id
	@Column(name = "ID")
	private String id =null;				//主键ID
	
	@Column(name = "C_TRUENAME", nullable = true)
	private String trueName = null;			//真实姓名
	
	@Column(name = "C_LOGINNAME", nullable = true)
	private String loginName = null;		//登录名
	
	@Column(name = "C_PASSWORD", nullable = true)
	private String password = null;			//登录密码
	
	@Column(name = "I_ISDELETE", nullable = true)
	private int isDelete =0;				//是否删除
	
	@Column(name = "D_CREATEDATE", nullable = true)
	private Date createDate = null;			//创建时间
	
	@Column(name = "C_ORGID", nullable = true)
	private String orgId = null;			//所属科室
	
	public User(){
		super();
		id = new RandomGUID().toString();
		createDate = new Date();
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
