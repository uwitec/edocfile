package com.edoc.action.files;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.entity.baseinfo.User;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.ShoreFile;
import com.edoc.entity.files.VisitUserInfo;
import com.edoc.service.files.FileService;
import com.edoc.service.files.ShoreFileService;
import com.edoc.service.files.VisitUserService;
import com.edoc.utils.StringUtils;

/**
 * 共享文件管理
 * 
 * @author 陈超	2011-06-02
 *
 */
@Component("shoreFileAction")
@Scope("prototype")
public class ShoreFileAction  extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="fileService")
	private FileService fileService = null;

	@Resource(name="shoreFileService")
	private ShoreFileService shoreFileService = null;
	
	@Resource(name="visitUserService")
	private VisitUserService visitUserService = null;
	
	private boolean sendMsgFlag = false;
	
	/**
	 * 共享操作前的准备工作
	 * @return
	 */
	public String beforeShoreFile(){
		String sourceFileId = this.getParameter("sourceFileId");
		EdocFile edocFile = fileService.getFileById(sourceFileId);
		ShoreFile shoreFile = shoreFileService.getShoreFileBySourceFileId(sourceFileId);
		List<VisitUserInfo> visitUsers = visitUserService.getVisitUsers(sourceFileId);
		
		this.setAttribute("edocFile", edocFile);
		this.setAttribute("shoreFile", shoreFile);
		this.setAttribute("visitUsers", visitUsers);
		return "showShorePage";
	}
	
	
	/**
	 * 共享文件操作
	 * @return
	 */
	public String shoreFile(){
		User user = (User)this.getSession().getAttribute("DOCUSER");
		String sourceFileId = getParameter("sourceFileId");					//获取共享文件Id
		String shoreNowFlagStr = getParameter("shoreNowFlag");				//是否立即共享
		if(!StringUtils.isValid(shoreNowFlagStr)){
			shoreNowFlagStr = "true";
		}
		boolean shoreNowFlag = Boolean.parseBoolean(shoreNowFlagStr);
		String parentId = this.getParameter("parentId");
		
		ShoreFile shoreFile = new ShoreFile();
		String shoreFileId = this.getParameter("shoreFileId");
		if(StringUtils.isValid(shoreFileId)){
			shoreFile.setId(shoreFileId);
		}
		shoreFile.setSourceFileId(sourceFileId);
		shoreFile.setParentId(parentId);
		shoreFile.setShoreUserId(user.getId());
		shoreFile.setShoreUserName(user.getTrueName());
		
		List<VisitUserInfo> visitUserInfos = getVisitUserInfos(user, sourceFileId);
		shoreFileService.shoreFile(shoreFile,visitUserInfos,user,shoreNowFlag,sendMsgFlag);
		return null;
	}
	
	/**
	 * 在共享设置时获取表单中的访问用户信息
	 * @param user
	 * @param sourceFileId
	 * @return
	 */
	private List<VisitUserInfo> getVisitUserInfos(User user, String sourceFileId){
		/*
		 * 获取允许访问该文件的用户信息 
		 */
		List<VisitUserInfo> visitUserInfos = null;
		String[] visitUserIds = getParameterValues("visitUserIds");	//获取运行访问的用户Id数组
		if(visitUserIds!=null){
			visitUserInfos = new ArrayList<VisitUserInfo>();
			VisitUserInfo visitUserInfo = null;
			boolean tempFlag = false;								//判断共享用户中是否存在当前用户
			for(String visitUserId:visitUserIds){
				if(visitUserId.equals(user.getId())){
					tempFlag = true;
				}
				visitUserInfo = new VisitUserInfo();
				String tempId = this.getParameter("visitUserInfoId_"+visitUserId);
				if(StringUtils.isValid(tempId)){
					visitUserInfo.setId(tempId);
				}
				visitUserInfo.setVisitUserId(visitUserId);
				visitUserInfo.setVisitUserName(getParameter("visitUserName_"+visitUserId));
				visitUserInfo.setSourceFileId(sourceFileId);
				String[] permissions = getParameterValues("permission_"+visitUserId);
				visitUserInfo.setPermissions(permissions);
				visitUserInfos.add(visitUserInfo);
			}
			//如果不存在当前用户的话,则将当前用户添加到共享用户信息中去(在页面上展示时可以不显示),并设为可预览可下载
			if(!tempFlag){		
				visitUserInfo = new VisitUserInfo();
				visitUserInfo.setPerView(1);
				visitUserInfo.setPerDownLoad(1);
				visitUserInfo.setVisitUserId(user.getId());
				visitUserInfo.setVisitUserName(user.getTrueName());
				visitUserInfo.setSourceFileId(sourceFileId);
				visitUserInfos.add(visitUserInfo);
			}
		}
		
		return visitUserInfos;
	}


	public boolean isSendMsgFlag() {
		return sendMsgFlag;
	}
	public void setSendMsgFlag(boolean sendMsgFlag) {
		this.sendMsgFlag = sendMsgFlag;
	}
}
