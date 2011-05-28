package com.edoc.action.files;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.service.files.VisitUserService;

/**
 * 回收站管理
 * 
 * @author 陈超	2011-4-11
 *
 */
@Component("visitUserAction")
@Scope("prototype")
public class VisitUserAction  extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="visitUserService")
	private VisitUserService visitUserService = null;
	
	/**
	 * 删除共享用户信息
	 * @return
	 */
	public String deleteVisitUserInfo(){
		String id = this.getParameter("visitUserInfoId");
		visitUserService.deleteVisitUserInfo(id);
		this.print("删除成功!");
		return null;
	}
	
	/**
	 * 验证用户权限
	 * @return
	 */
	public String checkPermission(){
		String currentUserId = this.getParameter("currentUserId");
		String sourceFileId = this.getParameter("sourceFileId");
		String perType = this.getParameter("perType");				//验证的权限类型,view、download、edit
		
		boolean flag = visitUserService.checkPermission(currentUserId,sourceFileId,perType);
		if(flag){
			this.print("true");
		}else{
			this.print("false");
		}
		return null;
	}

	
}
