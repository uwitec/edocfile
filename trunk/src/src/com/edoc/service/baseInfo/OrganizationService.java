package com.edoc.service.baseInfo;

import java.util.List;

import com.edoc.entity.baseinfo.Department;

/**
 * 组织结构服务类,实现类型{@link OrganizationServiceImpl}
 * @author 陈超 2010-7-26
 *
 */
public interface OrganizationService {
	
	/**
	 * 查询所有的科室信息
	 * @return 返回查询到的科室信息集合
	 * @author 陈超 2010-7-26
	 */
	public List<Department> getAllOrgs();
	
	/**
	 * 保存科室信息
	 * @return 保存成功返回true,否则返回false
	 * @author 陈超 2010-7-26
	 */
	public boolean saveOrgInfo(Department dept);

	/**
	 * 判断改科室是否存在子科室
	 * 
	 * @return
	 * @author 陈超 2010-7-26
	 */
	public boolean hasSubOrg(String orgId);
	
	/**
	 * 删除指定的科室信息
	 * 
	 * @param orgId
	 * @author 陈超 2010-7-26
	 */
	public void deleteOrg(String orgId);
	
	/**
	 * 获取根节点科室信息
	 * @return
	 * @author  陈超 2010-7-26
	 */
	public List<Department> getRootOrgs();
	
	/**
	 * 根据科室Id获取该科室的信息
	 * @param orgId
	 * @return
	 */
	public Department getOrgById(String orgId);
	
	/**
	 * 更新科室信息
	 * @param org
	 */
	public void updateOrg(Department org);

}
