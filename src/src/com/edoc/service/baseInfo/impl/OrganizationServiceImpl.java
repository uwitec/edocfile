package com.edoc.service.baseInfo.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edoc.dbsupport.PropertyFilter;
import com.edoc.entity.baseinfo.Department;
import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.service.baseInfo.OrganizationService;

@Component("orgService")
@Transactional(readOnly=true)
public class OrganizationServiceImpl implements OrganizationService{

	@Resource(name="deptDao")
	private GenericDAO<Department,String> deptDao=null;
	
	
	/**
	 * 根据科室Id获取该科室的信息
	 * @param orgId
	 * @return
	 */
	public Department getOrgById(String orgId){
		
		return deptDao.get(orgId);
	}
	
	@Transactional(readOnly = false)
	public void updateOrg(Department org){
		if(org!=null){
			deptDao.update(org);
		}
	}
	
	@Transactional(readOnly = false)
	public void deleteOrg(String orgId) {
		String[] ids = {orgId};
		deptDao.delete(ids);
		
	}

	public List<Department> getAllOrgs() {
		
		return deptDao.getAll();
	}

	public List<Department> getRootOrgs() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasSubOrg(String orgId) {
		List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
		PropertyFilter filter01 = new PropertyFilter("parentId",orgId,PropertyFilter.MatchType.EQ);
		filters.add(filter01);
		
		int count = deptDao.getCount(filters);
		if(count>0){
			return true;
		}
		return false;
	}

	@Transactional(readOnly = false)
	public boolean saveOrgInfo(Department dept) {
		deptDao.save(dept);
		return true;
	}

}
