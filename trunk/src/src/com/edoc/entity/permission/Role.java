package com.edoc.entity.permission;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edoc.entity.AbstractBaseEntity;
import com.edoc.utils.RandomGUID;
@Entity
@Table(name = "sys_role")
public class Role extends AbstractBaseEntity{
	
	@Id
	@Column(name = "ID")
	private String id = null;
	
	@Column(name = "C_ROLENAME", nullable = true)
	private String roleName = "";
	
	@Column(name = "C_ROLECODE", nullable = true)
	private String roleCode = "";
	
	@Column(name = "C_DESC", nullable = true)
	private String desc = "";
	
	@Column(name = "D_CREATETIME", nullable = true)
	private Date createTime = null;						//创建时间
	
	@Column(name = "I_DELETE", nullable = true)
	private int isDelete=0; //记录删除标志
	
	public Role(){
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	
}
