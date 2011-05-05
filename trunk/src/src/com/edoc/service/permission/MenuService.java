package com.edoc.service.permission;

import java.util.List;

import com.edoc.entity.permission.Menu;
/**
 * 菜单服务类,负责对菜单的创建、编辑、查询等操作,实现类{@link MenuServiceImpl}
 * @author 陈超
 *
 */
public interface MenuService {
	/**
	 * 获取所有的菜单
	 * @return
	 */
	public List<Menu> getAllMenus();
	
	/**
	 * 创建一个菜单
	 * @param menu 菜单
	 * @return 创建成功返回true,否则返回false
	 */
	public boolean createMenu(Menu menu);
	
	/**
	 * 编辑菜单信息
	 * @param newMenu 新的菜单信息,newMenu中的Id必须有效(数据库中存在Id对应的记录)
	 */
	public void editMenu(Menu newMenu);
	
	/**
	 * 查询所有的菜单信息
	 * 
	 * @return 		返回查询到得菜单信息列表,如果不存在菜单信息则返回null
	 * @author 		陈超 2010-6-21
	 */
	public List<Menu> findAllMenus();
	
	/**
	 * 创建新的菜单信息
	 * 
	 * @param 	menu 菜单信息
	 * @return 	创建成功返回true,创建失败返回false
	 * @author 	陈超 2010-6-23
	 */
	public boolean saveMenu(Menu menu);
	
	/**
	 * 获取已赋权的菜单信息
	 * @param roleId
	 * @return
	 * @author 	陈超 2011-01-16
	 */
	public List<Menu> getSelectedMeunsByRoleId(String roleId);

	/**
	 * 获取根节点菜单
	 * @return
	 * @author 		陈超 2011-02-03
	 * @param userId 
	 */
	public List<Menu> getMenuByParentId(String parentId, String userId);

	
}
