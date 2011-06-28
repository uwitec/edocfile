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
 * �����ļ�����
 * 
 * @author �³�	2011-06-02
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
	 * �������ǰ��׼������
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
	 * �����ļ�����
	 * @return
	 */
	public String shoreFile(){
		User user = (User)this.getSession().getAttribute("DOCUSER");
		String sourceFileId = getParameter("sourceFileId");					//��ȡ�����ļ�Id
		String shoreNowFlagStr = getParameter("shoreNowFlag");				//�Ƿ���������
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
	 * �ڹ�������ʱ��ȡ���еķ����û���Ϣ
	 * @param user
	 * @param sourceFileId
	 * @return
	 */
	private List<VisitUserInfo> getVisitUserInfos(User user, String sourceFileId){
		/*
		 * ��ȡ������ʸ��ļ����û���Ϣ 
		 */
		List<VisitUserInfo> visitUserInfos = null;
		String[] visitUserIds = getParameterValues("visitUserIds");	//��ȡ���з��ʵ��û�Id����
		if(visitUserIds!=null){
			visitUserInfos = new ArrayList<VisitUserInfo>();
			VisitUserInfo visitUserInfo = null;
			boolean tempFlag = false;								//�жϹ����û����Ƿ���ڵ�ǰ�û�
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
			//��������ڵ�ǰ�û��Ļ�,�򽫵�ǰ�û���ӵ������û���Ϣ��ȥ(��ҳ����չʾʱ���Բ���ʾ),����Ϊ��Ԥ��������
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
