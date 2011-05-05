package com.edoc.service.baseInfo;

import java.util.List;

import com.edoc.entity.baseinfo.Department;

/**
 * ��֯�ṹ������,ʵ������{@link OrganizationServiceImpl}
 * @author �³� 2010-7-26
 *
 */
public interface OrganizationService {
	
	/**
	 * ��ѯ���еĿ�����Ϣ
	 * @return ���ز�ѯ���Ŀ�����Ϣ����
	 * @author �³� 2010-7-26
	 */
	public List<Department> getAllOrgs();
	
	/**
	 * ���������Ϣ
	 * @return ����ɹ�����true,���򷵻�false
	 * @author �³� 2010-7-26
	 */
	public boolean saveOrgInfo(Department dept);

	/**
	 * �жϸĿ����Ƿ�����ӿ���
	 * 
	 * @return
	 * @author �³� 2010-7-26
	 */
	public boolean hasSubOrg(String orgId);
	
	/**
	 * ɾ��ָ���Ŀ�����Ϣ
	 * 
	 * @param orgId
	 * @author �³� 2010-7-26
	 */
	public void deleteOrg(String orgId);
	
	/**
	 * ��ȡ���ڵ������Ϣ
	 * @return
	 * @author  �³� 2010-7-26
	 */
	public List<Department> getRootOrgs();
	
	/**
	 * ���ݿ���Id��ȡ�ÿ��ҵ���Ϣ
	 * @param orgId
	 * @return
	 */
	public Department getOrgById(String orgId);
	
	/**
	 * ���¿�����Ϣ
	 * @param org
	 */
	public void updateOrg(Department org);

}
