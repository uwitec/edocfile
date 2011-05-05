package com.edoc.service.permission;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.permission.Role;
/**
 * ��ɫ������,����Բ˵��Ĵ������༭����ѯ�Ȳ���,ʵ����{@link RoleServiceImpl}
 * @author �³� 2011-01-15
 */
public interface RoleService {
	/**
	 * ��ӽ�ɫ��Ϣ
	 * @param role
	 * @return
	 * @author �³� 2010-01-15
	 */
	public boolean saveRole(Role role);
	
	
	/**
	 * ��ҳ��ȡ��ɫ��Ϣ
	 * @param currentPage	��ҳ��ѯ����ʼҳ
	 * @param pageSize		ûҳ��ʾ�ļ�¼��
	 * @param roleName		��ɫ����
	 * @return
	 * @author �³� 2011-01-15
	 */
	public PageValueObject<Role> getRoles(int currentPage, int pageSize, String roleName);

	
	/**
	 * ɾ����ɫ��Ϣ
	 * @param deleteParams	��ɫIds
	 * @author 	�³� 2011-01-15
	 */
	public void deleteRoles(String[] ids);
	
	/**
	 * �жϸ����ƵĽ�ɫ�Ƿ����
	 * @param roleName	��ɫ����
	 * @return
	 * @author 		�³� 2011-01-15
	 */
	public boolean isExist(String roleName);

	
	/**
	 * �޸Ľ�ɫ��Ϣ
	 * @param role	��ɫ��Ϣ
	 * @return 
	 * @author 		�³� 2011-01-15
	 */
	public boolean updateRole(Role role);

	
	/**
	 * ���ݽ�ɫId��ȡ��ɫ��Ϣ
	 * @param id
	 * @return
	 * @author 		�³� 2011-01-15
	 */
	public Role getRoleById(String id);

	
	/**
	 * ���ò˵�
	 * @param roleId		��ɫId
	 * @param menuIds		�˵�Ids
	 */
	public boolean setMenus(String roleId, String[] menuIds);
	
}
