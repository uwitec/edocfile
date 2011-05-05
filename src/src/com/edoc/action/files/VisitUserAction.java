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

	
}
