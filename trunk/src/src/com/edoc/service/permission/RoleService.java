package com.edoc.service.permission;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.permission.Role;
/**
 * 角色服务类,负责对菜单的创建、编辑、查询等操作,实现类{@link RoleServiceImpl}
 * @author 陈超 2011-01-15
 */
public interface RoleService {
	/**
	 * 添加角色信息
	 * @param role
	 * @return
	 * @author 陈超 2010-01-15
	 */
	public boolean saveRole(Role role);
	
	
	/**
	 * 分页获取角色信息
	 * @param currentPage	分页查询的起始页
	 * @param pageSize		没页显示的记录数
	 * @param roleName		角色名称
	 * @return
	 * @author 陈超 2011-01-15
	 */
	public PageValueObject<Role> getRoles(int currentPage, int pageSize, String roleName);

	
	/**
	 * 删除角色信息
	 * @param deleteParams	角色Ids
	 * @author 	陈超 2011-01-15
	 */
	public void deleteRoles(String[] ids);
	
	/**
	 * 判断该名称的角色是否存在
	 * @param roleName	角色名称
	 * @return
	 * @author 		陈超 2011-01-15
	 */
	public boolean isExist(String roleName);

	
	/**
	 * 修改角色信息
	 * @param role	角色信息
	 * @return 
	 * @author 		陈超 2011-01-15
	 */
	public boolean updateRole(Role role);

	
	/**
	 * 根据角色Id获取角色信息
	 * @param id
	 * @return
	 * @author 		陈超 2011-01-15
	 */
	public Role getRoleById(String id);

	
	/**
	 * 设置菜单
	 * @param roleId		角色Id
	 * @param menuIds		菜单Ids
	 */
	public boolean setMenus(String roleId, String[] menuIds);
	
}
