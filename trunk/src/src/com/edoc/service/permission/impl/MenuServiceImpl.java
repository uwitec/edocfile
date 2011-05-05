package com.edoc.service.permission.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edoc.entity.permission.Menu;
import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.service.permission.MenuService;
@Component("menuService")
@Transactional(readOnly=true)
public class MenuServiceImpl implements MenuService{
	@Resource(name="menuDao")
	private GenericDAO<Menu,String> menuDao=null;
	
	
	/**
	 * 获取根节点菜单
	 * @return
	 * @author 		陈超 2011-02-03
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getMenuByParentId(String parentId, String userId){
//		List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
//		PropertyFilter filter01 = new PropertyFilter("parentMenuId",parentId,PropertyFilter.MatchType.EQ);
//		filters.add(filter01);
//		List<Menu> results=menuDao.find(filters);
		
		List<Menu> results = new LinkedList<Menu>();
		String sql = "select a.id,a.C_ICON,a.C_NAME,a.C_PARENT_ID,a.C_URL,a.C_ENNAME from sys_menu as a,sys_role_menu as b where a.c_parent_id='"+parentId+"' and a.id=b.c_menu_id and b.c_role_id in (select c_role_id from sys_user_role where c_user_id='"+userId+"') order by a.C_NO asc";
		List r = menuDao.excuteQuery(sql);
		for(Object o :r){
			Object[] vs = (Object[])o;
			Menu m = new Menu();
			m.setId((String)vs[0]);
			m.setIcon((String)vs[1]);
			m.setName((String)vs[2]);
			m.setParentMenuId((String)vs[3]);
			m.setUrl((String)vs[4]);
			m.setEnName((String)vs[5]);
			results.add(m);
		}
		return results;
	}
	/**
	 * 获取已赋权的菜单信息
	 * @param roleId
	 * @return
	 * @author 	陈超 2011-01-16
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getSelectedMeunsByRoleId(String roleId){
		List<Menu> menus = new LinkedList();
		String sql = "select a.id,a.C_ICON,a.C_NAME,a.C_PARENT_ID from sys_menu as a,sys_role_menu as b where b.c_menu_id = a.id and b.c_role_id='"+roleId+"'";
		List r = menuDao.excuteQuery(sql);
		for(Object o :r){
			Object[] vs = (Object[])o;
			Menu m = new Menu();
			m.setId((String)vs[0]);
			m.setIcon((String)vs[1]);
			m.setName((String)vs[2]);
			m.setParentMenuId((String)vs[3]);
			
			menus.add(m);
		}
		return menus;
	}
	
	/**
	 * 获取所有的菜单
	 * @return
	 */
	public List<Menu> getAllMenus(){
		List<Menu> results=menuDao.getAll();
		return results;
	}
	
	/**
	 * 创建一个菜单
	 * @param menu 菜单
	 * @return 创建成功返回true,否则返回false
	 */
	@Transactional(readOnly=false)
	public boolean createMenu(Menu menu){
		try{
			menuDao.save(menu);
		}catch(Exception e){
			//出现异常时,打印异常信息同时返回false
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 编辑菜单信息
	 * @param newMenu 新的菜单信息,newMenu中的Id必须有效(数据库中存在Id对应的记录)
	 */
	@Transactional(readOnly=false)
	public void editMenu(Menu newMenu){
		menuDao.update(newMenu);
	}
	
	/**
	 * 查询所有的菜单信息
	 * 
	 * @return 返回查询到得菜单信息列表,如果不存在菜单信息则返回null
	 * @author 陈超 2010-6-21
	 */
	public List<Menu> findAllMenus() {
		return menuDao.getAll();
	}
	
	/**
	 * 创建新的菜单信息
	 * 
	 * @param 	menu 菜单信息
	 * @return 	创建成功返回true,创建失败返回false
	 * @author 	陈超 2010-6-23
	 */
	@Transactional(readOnly=false)
	public boolean saveMenu(Menu menu){
		if(menu!=null){
			menuDao.save(menu);
			return true;
		}
		return false;
	}
}
