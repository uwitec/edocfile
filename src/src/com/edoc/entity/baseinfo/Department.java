package com.edoc.entity.baseinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edoc.entity.AbstractBaseEntity;
import com.edoc.utils.RandomGUID;

/**
 * 组织结构信息
 * @author 陈超
 *
 */
@Entity
@Table(name = "sys_department")
public class Department extends AbstractBaseEntity{
	
	@Id
	@Column(name = "ID")
	private String id = null;			//主键ID
	
	@Column(name = "C_DEPTNAME", nullable = true)
	private String deptName = null;		//科室名称
	
	@Column(name = "C_PARENTID", nullable = true)
	private String parentId = "-1";		//父科室
	
	@Column(name = "I_ISDELETE", nullable = true)
	private int isDelete = 0;			//是否删除标志,0未删除,1已删除
	
	@Column(name = "D_CREATETIME", nullable = true)
	private Date createTime = null;		//记录创建时间
	
	public Department(){
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
