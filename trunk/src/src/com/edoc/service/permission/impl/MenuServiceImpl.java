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
	 * ��ȡ���ڵ�˵�
	 * @return
	 * @author 		�³� 2011-02-03
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
	 * ��ȡ�Ѹ�Ȩ�Ĳ˵���Ϣ
	 * @param roleId
	 * @return
	 * @author 	�³� 2011-01-16
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
	 * ��ȡ���еĲ˵�
	 * @return
	 */
	public List<Menu> getAllMenus(){
		List<Menu> results=menuDao.getAll();
		return results;
	}
	
	/**
	 * ����һ���˵�
	 * @param menu �˵�
	 * @return �����ɹ�����true,���򷵻�false
	 */
	@Transactional(readOnly=false)
	public boolean createMenu(Menu menu){
		try{
			menuDao.save(menu);
		}catch(Exception e){
			//�����쳣ʱ,��ӡ�쳣��Ϣͬʱ����false
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * �༭�˵���Ϣ
	 * @param newMenu �µĲ˵���Ϣ,newMenu�е�Id������Ч(���ݿ��д���Id��Ӧ�ļ�¼)
	 */
	@Transactional(readOnly=false)
	public void editMenu(Menu newMenu){
		menuDao.update(newMenu);
	}
	
	/**
	 * ��ѯ���еĲ˵���Ϣ
	 * 
	 * @return ���ز�ѯ���ò˵���Ϣ�б�,��������ڲ˵���Ϣ�򷵻�null
	 * @author �³� 2010-6-21
	 */
	public List<Menu> findAllMenus() {
		return menuDao.getAll();
	}
	
	/**
	 * �����µĲ˵���Ϣ
	 * 
	 * @param 	menu �˵���Ϣ
	 * @return 	�����ɹ�����true,����ʧ�ܷ���false
	 * @author 	�³� 2010-6-23
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
