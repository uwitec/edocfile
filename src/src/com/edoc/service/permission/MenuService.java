package com.edoc.service.permission;

import java.util.List;

import com.edoc.entity.permission.Menu;
/**
 * �˵�������,����Բ˵��Ĵ������༭����ѯ�Ȳ���,ʵ����{@link MenuServiceImpl}
 * @author �³�
 *
 */
public interface MenuService {
	/**
	 * ��ȡ���еĲ˵�
	 * @return
	 */
	public List<Menu> getAllMenus();
	
	/**
	 * ����һ���˵�
	 * @param menu �˵�
	 * @return �����ɹ�����true,���򷵻�false
	 */
	public boolean createMenu(Menu menu);
	
	/**
	 * �༭�˵���Ϣ
	 * @param newMenu �µĲ˵���Ϣ,newMenu�е�Id������Ч(���ݿ��д���Id��Ӧ�ļ�¼)
	 */
	public void editMenu(Menu newMenu);
	
	/**
	 * ��ѯ���еĲ˵���Ϣ
	 * 
	 * @return 		���ز�ѯ���ò˵���Ϣ�б�,��������ڲ˵���Ϣ�򷵻�null
	 * @author 		�³� 2010-6-21
	 */
	public List<Menu> findAllMenus();
	
	/**
	 * �����µĲ˵���Ϣ
	 * 
	 * @param 	menu �˵���Ϣ
	 * @return 	�����ɹ�����true,����ʧ�ܷ���false
	 * @author 	�³� 2010-6-23
	 */
	public boolean saveMenu(Menu menu);
	
	/**
	 * ��ȡ�Ѹ�Ȩ�Ĳ˵���Ϣ
	 * @param roleId
	 * @return
	 * @author 	�³� 2011-01-16
	 */
	public List<Menu> getSelectedMeunsByRoleId(String roleId);

	/**
	 * ��ȡ���ڵ�˵�
	 * @return
	 * @author 		�³� 2011-02-03
	 * @param userId 
	 */
	public List<Menu> getMenuByParentId(String parentId, String userId);

	
}
