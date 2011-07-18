package com.edoc.action.baseinfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.entity.baseinfo.Department;
import com.edoc.service.baseInfo.OrganizationService;

/**
 * �ļ��й���
 * 
 * @author �³�	2010-7-26
 *
 */
@Component("orgAction")
@Scope("prototype")
public class OrganizationAction  extends AbstractAction{

	private static final long serialVersionUID = 1L;
	@Resource(name="orgService")
	private OrganizationService orgService = null;
	private Department dept = null;
	private String orgId = null;
	
	public String updateOrg(){
		dept.setId(this.getParameter("deptId"));
		orgService.updateOrg(dept);
		return "";
	}
	public String getOrgById(){
		Department org = orgService.getOrgById(orgId);
		this.setAttribute("org", org);
		return "";
	}
	/**
	 * ��ȡ���е���֯�ṹ��Ϣ
	 * 
	 * @return
	 * @author �³� 2010-7-26
	 */
	public String getAllOrgs(){
		List<Department> orgList = orgService.getAllOrgs();
		
		//�ж��Ƿ�����JSON��ʽ��������
		if(isPrintJSON()){
			printJSON(null, null, orgList);
			return null;
		}else{
			this.getRequest().setAttribute("orgList", orgList);
			return "showOrgListPage";
		}
	}
	
	
	public String beforeUpdate(){
		String deptId = this.getParameter("deptId");
		Department dept = orgService.getOrgById(deptId);
		
		//��ȡ��������Ϣ
		if(dept!=null){
			Department pDept = orgService.getOrgById(dept.getParentId());
			setAttribute("pDept", pDept);
		}
		
		setAttribute("dept", dept);
		return "showUpdatePage";
	}
	/**
	 * ���������Ϣ
	 * 
	 * @return
	 * @author �³� 2010-7-26
	 */
	public String saveOrg(){
		orgService.saveOrgInfo(dept);
		return null;
	}
	
	/**
	 * ɾ��������Ϣ
	 * 
	 * @return
	 * @author �³� 2010-7-26
	 */
	public String deleteOrg(){
		System.out.println("orgId:"+orgId);
		/*
		 * ���ָ��ɾ���Ŀ��Ҵ����ӿ��ҵĻ�����ʾ�û��޷�ɾ������ɾ��ָ���Ŀ���
		 */
		if(!orgService.hasSubOrg(orgId)){
			orgService.deleteOrg(orgId);
		}else{
			String msg = "�޷�ɾ�����ÿ�����������ӿ���!";
			this.print(msg);
			return "showOrgsPage";
		}
		return null;
	}
	
	/**
	 * ��ȡ���ڵ������Ϣ
	 * @return
	 * @author �³� 2010-7-26
	 */
	public String getRootOrgs(){
		List<Department> orgList =  orgService.getRootOrgs();
		
		/*
		 * �ж��Ƿ�����JSON��ʽ��������
		 */
		if(isPrintJSON()){
			printJSON(null, null, orgList);
			return null;
		}else{
			return null;
		}
	}

	
	
	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
