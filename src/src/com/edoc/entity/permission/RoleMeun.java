package com.edoc.entity.permission;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edoc.utils.RandomGUID;

@Entity
@Table(name = "sys_role_menu")
public class RoleMeun {
	
	@Id
	@Column(name = "ID")
	private String id = null;
	
	@Column(name = "C_ROLE_ID", nullable = true)
	private String roleId = null;
	
	@Column(name = "C_MENU_ID", nullable = true)
	private String menuId = null;
	
	public RoleMeun(){
		super();
		id = new RandomGUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
