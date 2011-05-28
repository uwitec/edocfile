package com.edoc.action.files;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.service.files.VisitUserService;

/**
 * ����վ����
 * 
 * @author �³�	2011-4-11
 *
 */
@Component("visitUserAction")
@Scope("prototype")
public class VisitUserAction  extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="visitUserService")
	private VisitUserService visitUserService = null;
	
	/**
	 * ɾ�������û���Ϣ
	 * @return
	 */
	public String deleteVisitUserInfo(){
		String id = this.getParameter("visitUserInfoId");
		visitUserService.deleteVisitUserInfo(id);
		this.print("ɾ���ɹ�!");
		return null;
	}
	
	/**
	 * ��֤�û�Ȩ��
	 * @return
	 */
	public String checkPermission(){
		String currentUserId = this.getParameter("currentUserId");
		String sourceFileId = this.getParameter("sourceFileId");
		String perType = this.getParameter("perType");				//��֤��Ȩ������,view��download��edit
		
		boolean flag = visitUserService.checkPermission(currentUserId,sourceFileId,perType);
		if(flag){
			this.print("true");
		}else{
			this.print("false");
		}
		return null;
	}

	
}
