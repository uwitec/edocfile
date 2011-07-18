package com.edoc.action.baseinfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.entity.baseinfo.Department;
import com.edoc.service.baseInfo.OrganizationService;

/**
 * 文件夹管理
 * 
 * @author 陈超	2010-7-26
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
	 * 获取所有的组织结构信息
	 * 
	 * @return
	 * @author 陈超 2010-7-26
	 */
	public String getAllOrgs(){
		List<Department> orgList = orgService.getAllOrgs();
		
		//判断是否是以JSON格式返回数据
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
		
		//获取父科室信息
		if(dept!=null){
			Department pDept = orgService.getOrgById(dept.getParentId());
			setAttribute("pDept", pDept);
		}
		
		setAttribute("dept", dept);
		return "showUpdatePage";
	}
	/**
	 * 保存科室信息
	 * 
	 * @return
	 * @author 陈超 2010-7-26
	 */
	public String saveOrg(){
		orgService.saveOrgInfo(dept);
		return null;
	}
	
	/**
	 * 删除科室信息
	 * 
	 * @return
	 * @author 陈超 2010-7-26
	 */
	public String deleteOrg(){
		System.out.println("orgId:"+orgId);
		/*
		 * 如果指定删除的科室存在子科室的话则提示用户无法删除否则删除指定的科室
		 */
		if(!orgService.hasSubOrg(orgId)){
			orgService.deleteOrg(orgId);
		}else{
			String msg = "无法删除：该科室下面存在子科室!";
			this.print(msg);
			return "showOrgsPage";
		}
		return null;
	}
	
	/**
	 * 获取根节点科室信息
	 * @return
	 * @author 陈超 2010-7-26
	 */
	public String getRootOrgs(){
		List<Department> orgList =  orgService.getRootOrgs();
		
		/*
		 * 判断是否是以JSON格式返回数据
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
