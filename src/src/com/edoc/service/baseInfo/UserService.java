package com.edoc.service.baseInfo;

import java.util.List;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.baseinfo.User;

/**
 * �û�������,�����û���Ϣ��ѯ��ɾ�����޸ġ���ӵȲ���,ʵ������{@link UserServiceImpl}
 * @author dell
 *
 */
public interface UserService {
	
	/**
	 * ��ȡ�û���Ϣ
	 * @return
	 */
	public List<User> getUserList();
	
	
	/**
	 * ��ҳ��ѯĳ�������µ�����Ա����Ϣ
	 * @param currentPage	��ҳ��ʼҳ
	 * @param pageSize		ÿҳ��ʾ�ļ�¼��
	 * @param orgId			����Id,��deptId=-1ʱ��ʾ��ȡ˵�е�Ա����Ϣ
	 * @param userName 		�û�����
	 * @return 				���ؾ�����ҳ����(PageValueObject)��װ����û�������Ϣ
	 * @author 				�³� 2010-7-26
	 */
	public PageValueObject<User> getUsersByOrgId(int currentPage, int pageSize, String orgId, String userName);
	

	/**
	 * �����û���Ϣ
	 * @param user
	 * @return ����ɹ�����true���򷵻�false
	 * @author �³� 2010-7-26
	 */
	public boolean saveUser(User user);

	/**
	 * ɾ���û���Ϣ
	 * @param deleteParams
	 * @author �³� 2010-7-26
	 */
	public boolean deleteUser(String[] deleteParams);

	/**
	 * ��ȡ���е��û���Ϣ
	 * @return ���ؾ�����ҳ����(PageValueObject)��װ����û�������Ϣ
	 * @author �³� 2010-7-29
	 */
	public List<User> getAllUsers();
	
	/**
	 * �����û�Id��ȡ���û�����Ϣ
	 * @param userId	�û�Id
	 * @return		���ػ�ȡ�����û���Ϣ
	 * @author 		�³�
	 */
	public User getUserById(String userId);
	
	/**
	 * �����û���Ϣ
	 * @param user
	 */
	public void updateUser(User user);
	

	/**
	 * �û���¼ҵ��
	 * @param username	�û���
	 * @param password	����
	 * @return
	 */
	public User doLogin(String username, String password);

	/**
	 * �޸��û�����
	 * @param id			�û���ϢId
	 * @param newPassword	������
	 * @return
	 */
	public boolean updatePassword(String id, String newPassword);

	
	/**
	 * ��ѯ��ɫ�û�
	 * @param currentPage
	 * @param pageSize
	 * @param roleId
	 * @param userName
	 * @return
	 */
	public PageValueObject<User> getUsersByRoleId(int currentPage,
			int pageSize, String roleId, String userName);

	
	/**
	 * ��ӽ�ɫ�û�
	 * @param roleId	��ɫId
	 * @param userIds	�û�Id
	 */
	public void addRoleUser(String roleId, String[] userIds);

	/**
	 * ɾ����ɫ�û�
	 * @param roleId
	 * @param userIds
	 */
	public void deleteRoleUser(String roleId, String[] userIds);
}
