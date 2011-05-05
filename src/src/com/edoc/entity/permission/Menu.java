package com.edoc.entity.permission;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.edoc.entity.AbstractBaseEntity;
import com.edoc.utils.RandomGUID;

/**
 * 菜单实体类
 * 
 * @author 陈超
 * 
 */
@Entity
@Table(name = "sys_menu")
public class Menu extends AbstractBaseEntity{
	@Id
	@Column(name = "ID")
	private String id = null; // 菜单节点Id
	
	@Column(name = "I_DEL", nullable = true)
	private int isDelete=0; //记录删除标志
	
	@Column(name = "D_CREATEDATE", nullable = true)
	private Date createDate=null; //记录创建日期
	
	@Column(name = "C_PARENT_ID", nullable = true)
	private String parentMenuId = null; // 菜单父节点Id
	
	@Column(name = "C_NAME", nullable = true)
	private String name = null; // 菜单名称
	
	@Column(name = "C_ENNAME", nullable = true)
	private String enName = null;	//菜单英文名称
	
	@Column(name = "C_TITLE", nullable = true)
	private String title = null; // 说明
	
	@Column(name = "C_URL", nullable = true)
	private String url = null; // 菜单连接地址
	
	@Column(name = "C_TARGET", nullable = true)
	private String target = null; // 连接目标
	
	@Column(name = "C_ICON", nullable = true)
	private String icon = null; // 菜单图标
	
	
	@Column(name = "C_NO", nullable = true)
	private String no = null; 				//菜单编号
	
	@Transient
	private List<Menu> subMenus = new LinkedList<Menu>();

	public Menu() {
		super();
		id = new RandomGUID().toString();
		createDate = new Date();
	}

	
	public String getNo() {
		return no;
	}


	public void setNo(String no) {
		this.no = no;
	}


	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public List<Menu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getParentMenuId() {
		return parentMenuId;
	}



	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
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


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
