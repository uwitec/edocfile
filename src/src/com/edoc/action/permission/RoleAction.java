package com.edoc.action.permission;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.permission.Menu;
import com.edoc.entity.permission.Role;
import com.edoc.service.permission.MenuService;
import com.edoc.service.permission.RoleService;

/**
 * 文件夹管理
 * 
 * @author 陈超	2010-7-21
 *
 */
@Component("roleAction")
@Scope("prototype")
public class RoleAction  extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private String[] deleteParams;		//删除文件时的参数
	private Role role;
	private String id ="";
	
	@Resource(name="roleService")
	private RoleService roleService = null;
	
	@Resource(name="menuService")
	private MenuService menuService=null;
	
	public void setPermissions(){
		String menuIdsStr = this.getParameter("menuIds");		//获取选择的菜单Id
		String roleId = this.getParameter("roleId");			//获取角色Id
		String[] menuIds = menuIdsStr.split(",");
		if(roleService.setMenus(roleId,menuIds)){
			showMessage(this.getResponse(), "设置权限成功!", true);
		}
		return;
	}
	
	/**
	 * 设置权限前的准备工作
	 * @return
	 */
	public String beforeSetPermissions(){
		String roleId = this.getParameter("roleId");			//获取角色Id
		List<Menu> selectedMenus = menuService.getSelectedMeunsByRoleId(roleId);		//获取已赋权的菜单信息
		List<Menu> allMenus = menuService.getAllMenus();
		String treeStr = "";
		if(allMenus!=null){
			List<String> selectedMenuIds = convertToMenuIds(selectedMenus);
			for(Menu menu:allMenus){
				if(selectedMenuIds.contains(menu.getId())){
					treeStr += "d.add(\""+menu.getId()+"\",\""+menu.getParentMenuId()+"\",\"<font color='blue'>"+menu.getName()+"</font>\""+",true);";
				}else{
					treeStr += "d.add(\""+menu.getId()+"\",\""+menu.getParentMenuId()+"\",\"<font color='blue'>"+menu.getName()+"</font>\""+",false);";
				}
			}
		}
		this.setAttribute("roleId", roleId);
		this.setAttribute("treeStr", treeStr);
		return "toSetPermissionsPage";
	}
	
	private List<String> convertToMenuIds(List<Menu> selectedMenus) {
		List<String> ids = new LinkedList<String>();
		if(selectedMenus!=null){
			for(Menu menu:selectedMenus){
				ids.add(menu.getId());
			}
		}
		return ids;
	}

	/**
	 * 获取角色信息
	 * 
	 * @return
	 * @author 		陈超 2011-01-15
	 */
	public String getRoles(){
		HttpServletRequest req = this.getRequest();
		String roleName = req.getParameter("roleName");
		PageValueObject<Role> rolesPageVO = roleService.getRoles(getCurrentPage(), getPageSize(),roleName);
		req.setAttribute("rolesPageVO", rolesPageVO);
		req.setAttribute("roleName", roleName);
		
		return "showRoleListPage";
	}
	
	public void saveRole(){
		if(roleService.saveRole(role)){
			this.showMessage(this.getResponse(), "添加角色成功!", true);
		}
		return;
	}
	
	public void updateRole(){
		if(roleService.updateRole(role)){
			this.showMessage(this.getResponse(), "修改角色成功!", true);
		}
		return;
	}
	
	public String getRoleById(){
		Role role = roleService.getRoleById(id);
		setAttribute("role",role);
		return "toUpdatePage";
	}
	
	public void isExist(){
		String roleName = this.getParameter("roleName");
		if(roleService.isExist(roleName)){
			this.print("{\"isExist\":\"true\"}");
		}else{
			this.print("{\"isExist\":\"false\"}");
		}
		return;
	}
	public String deleteRoles(){
		roleService.deleteRoles(deleteParams);
		return getRoles();
	}
	
	
	public String[] getDeleteParams() {
		return deleteParams;
	}
	public void setDeleteParams(String[] deleteParams) {
		this.deleteParams = deleteParams;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	
}
