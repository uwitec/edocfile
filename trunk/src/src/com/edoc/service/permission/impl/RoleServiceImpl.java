package com.edoc.service.permission.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.dbsupport.PropertyFilter;
import com.edoc.entity.permission.Role;
import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.service.permission.RoleService;
import com.edoc.utils.RandomGUID;
import com.edoc.utils.StringUtils;

@Component("roleService")
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
	@Resource(name = "roleDao")
	private GenericDAO<Role, String> roleDao = null;

	@Transactional(readOnly = false)
	public boolean saveRole(Role role) {
		if (role != null) {
			roleDao.save(role);
			return true;
		}
		return false;
	}
	
	
	/**
	 * ���ò˵�
	 * @param roleId		��ɫId
	 * @param menuIds		�˵�Ids
	 */
	@Transactional(readOnly = false)
	public boolean setMenus(String roleId, String[] menuIds){
		//ɾ�������õĽ�ɫ��Ϣ
		String sql = "delete from sys_role_menu where c_role_id = '"+roleId+"'";
		roleDao.update(sql);
		
		if(menuIds!=null){
			for(String menuId:menuIds){
				String insertSQL = "insert into sys_role_menu(id,c_role_id,c_menu_id) values ('"+new RandomGUID().toString()+"','"
						+ roleId +"','" + menuId + "')";
				
				roleDao.update(insertSQL);
			}
		}
		return true;
	}
	
	/**
	 * ���ݽ�ɫId��ȡ��ɫ��Ϣ
	 * @param id
	 * @return
	 * @author 		�³� 2011-01-15
	 */
	public Role getRoleById(String id){
		
		return roleDao.get(id);
	}
	/**
	 * �޸Ľ�ɫ��Ϣ
	 * @param role	��ɫ��Ϣ
	 * @return 
	 * @author 		�³� 2011-01-15
	 */
	@Transactional(readOnly = false)
	public boolean updateRole(Role role){
		if (role != null) {
			roleDao.update(role);
			return true;
		}
		return false;
	}
	
	/**
	 * �жϸ����ƵĽ�ɫ�Ƿ����
	 * @param roleName	��ɫ����
	 * @return
	 * @author 		�³� 2011-01-15
	 */
	public boolean isExist(String roleName){
		List<PropertyFilter> filterList = new LinkedList<PropertyFilter>();
	
		PropertyFilter filter = new PropertyFilter("roleName", roleName,PropertyFilter.MatchType.EQ);
		filterList.add(filter);
		int count = roleDao.getCount(filterList);
		if(count>0){
			return true;
		}else{
			return false;
		}
		
	}
	
	
	/**
	 * ɾ����ɫ��Ϣ
	 * @param deleteParams	��ɫIds
	 * @author 	�³� 2011-01-15
	 */
	@Transactional(readOnly = false)
	public void deleteRoles(String[] ids){
		roleDao.delete(ids);
	}

	/**
	 * ��ҳ��ȡ��ɫ��Ϣ
	 * 
	 * @param currentPage
	 *            ��ҳ��ѯ����ʼҳ
	 * @param pageSize
	 *            ûҳ��ʾ�ļ�¼��
	 * @param roleName
	 *            ��ɫ����
	 * @return
	 * @author �³� 2011-01-15
	 */
	public PageValueObject<Role> getRoles(int currentPage, int pageSize,
			String roleName) {
		PageValueObject<Role> page = null;
		page = new PageValueObject<Role>(currentPage, pageSize);

		List<PropertyFilter> filterList = new LinkedList<PropertyFilter>();
		// ��Ӳ�ѯ����:queryFileName
		if (StringUtils.isValid(roleName)) {
			PropertyFilter filter03 = new PropertyFilter("roleName", roleName,
					PropertyFilter.MatchType.LIKE);
			filterList.add(filter03);
		}

		page.setResult(roleDao.find(filterList, page.getFirstResult(), page.getPageSize()));
		page.setTotalRows(roleDao.getCount(filterList));
		return page;
	}

}
