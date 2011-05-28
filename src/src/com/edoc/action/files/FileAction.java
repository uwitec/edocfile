package com.edoc.action.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.FileTypeEnum;
import com.edoc.entity.baseinfo.User;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.FileVersion;
import com.edoc.entity.files.ShoreFile;
import com.edoc.entity.files.VisitUserInfo;
import com.edoc.mail.MailSender;
import com.edoc.mail.EmailInfo;
import com.edoc.service.files.FileService;
import com.edoc.service.files.ShoreFileService;
import com.edoc.service.files.VisitUserService;
import com.edoc.utils.ConfigResource;
import com.edoc.utils.FileUtils;
import com.edoc.utils.RandomGUID;
import com.edoc.utils.StringUtils;

/**
 * �ļ��й���
 * 
 * @author �³�	2010-7-21
 *
 */
@Component("fileAction")
@Scope("prototype")
public class FileAction  extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private EdocFile edocFile = null;	//�ļ���Ϣ
	private File docFile;				//�ϴ����ļ�
	private String docFileFileName;		//�ļ�����
	private String docFileContentType;	//�ļ�����
	private String[] deleteParams;		//ɾ���ļ�ʱ�Ĳ���
	@Resource(name="fileService")
	private FileService fileService = null;

	@Resource(name="mailSender")
	private MailSender mailSender = null;
	
	@Resource(name="shoreFileService")
	private ShoreFileService shoreFileService = null;
	
	@Resource(name="visitUserService")
	private VisitUserService visitUserService = null;
	
	/**
	 * ȡ���������
	 * @return
	 */
	public String cancelShore(){
		String fileId = this.getParameter("fileId");
		
		fileService.cancelShore(fileId);
		
		//�ڶ��ҳ������˴˷���,���Ҫ�ж���ת���ĸ�ҳ����ȥ
		String page = this.getParameter("page");
		if(StringUtils.isValid(page) && page.equals("myFolder")){
			return getMyFilesByParentId();
		}
		return getShoredFiles();
	}
	
	/**
	 * �鿴�ļ�������Ϣ
	 * @return
	 */
	public String getFileInfo(){
		String sourceFileId = this.getParameter("sourceFileId");
		edocFile = fileService.getFileById(sourceFileId);
		this.setAttribute("edocFile", edocFile);
		this.setAttribute("sourceFileId", sourceFileId);
		this.setAttribute("currentTab", 0);
		return "showFileInfo";
	}
	
//	public String getSubFilesByParentId(){
//		String parentId = getRequest().getParameter("parentId");
//		PageValueObject<EdocFile> filePageVO = fileService.getSubFilesByParentId(getCurrentPage(), getPageSize(),parentId);
//		this.getRequest().setAttribute("filePageVO", filePageVO);
//		return "showMyFileListPage";
//	}
	
	/**
	 * ��ȡ�ļ���������ļ���Ϣ
	 * @return
	 */
	public String getMyFilesByParentId(){
		
		//��ȡparentId�µ��ļ���Ϣ
		//��ȡ�ϼ�Ŀ¼ID,����ϼ��˵�ID��Ч�Ļ�����Ϊ"0"
		String parentId = getRequest().getParameter("parentId");
		if(!StringUtils.isValid(parentId)){
			parentId = "0";
		}
		User user = (User)this.getSession().getAttribute("DOCUSER");
		String fileName = getRequest().getParameter("fileName");		//Ҫ��ѯ���ļ�����
		
		PageValueObject<EdocFile> filePageVO = fileService.getMyFilesByParentId(getCurrentPage(), getPageSize(),user.getId(), parentId, fileName);
		getRequest().setAttribute("filePageVO", filePageVO);
		getRequest().setAttribute("parentId", parentId);
		getRequest().setAttribute("fileName", fileName);
		
		//��ȡĿ¼�ṹ��Ϣ
		List<EdocFile> mulus = fileService.getMulus(parentId,1,1);
		this.setAttribute("mulus", mulus);
		
		String layoutStyle = this.getParameter("layoutStyle");
		if(StringUtils.isValid(layoutStyle) && layoutStyle.equals("1")){
			this.setAttribute("layoutStyle", layoutStyle);
			return "showMyFileListPage_h";
		}
		if(StringUtils.isValid(this.getForward())){
			return this.getForward();
		}
		return "showMyFileListPage";
	}
	/**
	 * ��ȡ�����ļ���Ϣ
	 * @return
	 */
	public String getShoredFiles(){
		String parentId = getRequest().getParameter("parentId");
		if(!StringUtils.isValid(parentId)){
			parentId = "-1";
		}
		User user = (User)this.getSession().getAttribute("DOCUSER");
		String fileName = getParameter("fileName");		//Ҫ��ѯ���ļ�����
		
		PageValueObject<ShoreFile> filePageVO = shoreFileService.getShoredFiles(getCurrentPage(), getPageSize(),user.getId(), parentId, fileName);
		getRequest().setAttribute("filePageVO", filePageVO);
		getRequest().setAttribute("parentId", parentId);
		getRequest().setAttribute("fileName", fileName);
		
		//��ȡĿ¼�ṹ��Ϣ
		List<EdocFile> mulus = fileService.getShoredMulus(parentId,1,1);
		this.setAttribute("mulus", mulus);
		
		String layoutStyle = this.getParameter("layoutStyle");
		if(StringUtils.isValid(layoutStyle) && layoutStyle.equals("1")){
			this.setAttribute("layoutStyle", layoutStyle);
			return "showShoreFileListPage_h";
		}
		
		return "showShoreFileListPage";
	}
	
	/**
	 * �������β˵�"�ҵ��ļ���",������JSON�ĸ�ʽ���ص��ͻ���
	 * @author �³� 2011-01-29
	 */
	public void createTreeMenu(){
		String parentId = this.getParameter("parentId");
		User user = (User)this.getSession().getAttribute("DOCUSER");
		List<EdocFile> folders = fileService.getFoldersByParentId(parentId,user.getId());
		if(folders!=null && !folders.isEmpty()){
			String str = "";
			str += "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
			str += "<tree>";
			for(EdocFile e:folders){
				str += "<tree text=\""+e.getFileName()+"\" action=\"javascript:link('fileAction!getMyFilesByParentId.action?parentId="+e.getId()+"','perspective_content')\" src=\"fileAction!createTreeMenu.action?parentId="+e.getId()+"\"/>";
			}
			str += "</tree>";
			try {
				HttpServletResponse res = this.getResponse();
				this.getResponse().setContentType("text/xml;charset=GBK");
				res.setCharacterEncoding("utf-8");

				PrintWriter out = res.getWriter();
				out.print(str);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.print(str);
		}
		return;
	}
	
	/**
	 * �������β˵�"�ҵ��ļ���",������JSON�ĸ�ʽ���ص��ͻ���
	 * @author �³� 2011-01-29
	 */
	public void createShoredTreeMenu(){
		String parentId = this.getParameter("parentId");
		User user = (User)this.getSession().getAttribute("DOCUSER");
		List<ShoreFile> folders = shoreFileService.getShoredFileByParentId(parentId,user.getId());
		if(folders!=null && !folders.isEmpty()){
			String str = "";
			str += "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
			str += "<tree>";
			for(ShoreFile e:folders){
				str += "<tree text=\""+e.getFileName()+"\" action=\"javascript:link('fileAction!getShoredFiles.action?parentId="+e.getId()+"','perspective_content')\" src=\"fileAction!createShoredTreeMenu.action?parentId="+e.getId()+"\"/>";
			}
			str += "</tree>";
			try {
				HttpServletResponse res = this.getResponse();
				this.getResponse().setContentType("text/xml;charset=GBK");
				res.setCharacterEncoding("utf-8");

				PrintWriter out = res.getWriter();
				out.print(str);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.print(str);
		}
		return;
	}
	
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
		int shoreMuluFlag = 0;												//�Ƿ���Ŀ¼,0������,1����
		String shoreMulu = this.getParameter("shoreMulu");
		if(StringUtils.isValid(shoreMulu)){
			shoreMuluFlag = Integer.parseInt(shoreMulu);
		}

		
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
		fileService.shoreFile(shoreFile,visitUserInfos,user,shoreMuluFlag,shoreNowFlag);
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
	
	/**
	 * ɾ���ļ�/�ļ���
	 * @return
	 */
	public String deleteFile(){
		User user = (User)this.getSession().getAttribute("DOCUSER");
		fileService.deleteFile(deleteParams,user.getId());
		return getMyFilesByParentId();
	}
//	/**
//	 * ��ȡ"�ҵ��ļ���"�еĸ��ļ�����Ϣ�Լ����ļ���Ϣ
//	 * 
//	 * @author �³� 2010-7-21
//	 * @return
//	 */
//	public String getRootFileFromMyFolder(){
//		String creatorId = "0";
//		PageValueObject<EdocFile> filePageVO = fileService.getRootFileFromMyFolder(getCurrentPage(), getPageSize(),creatorId);
//		this.getRequest().setAttribute("filePageVO", filePageVO);
//		return "showMyFileListPage";
//	}
	
	/**
	 * �ļ�Ԥ������ҪԤ�����ļ����ص� temp/ �ļ�����
	 * @return
	 */
	public String beforePreviewFile(){
		String sourceFileId = this.getParameter("sourceFileId");
		String version = this.getParameter("version");
		
		FileVersion fileVersion = fileService.getFileVersion(sourceFileId,version);
		if(fileVersion!=null){
			String tempDir = this.getSession().getServletContext().getRealPath("\\temp");
			String filePath = tempDir+"\\"+fileVersion.getNewFileName();
			File file = new File(filePath);
			if(!file.exists()){
				try{
					file.createNewFile();
		            String sourceFilePath = ConfigResource.getConfig(ConfigResource.EDOCUPLOADDIR)+"\\"+fileVersion.getNewFileName();
		            //������   
		            InputStream in =new FileInputStream(sourceFilePath);   
		            //�����   
		            OutputStream out =new FileOutputStream(file,true);   
		            
		            byte[] buffer=new byte[1024];   
	                while(true){   
	                    int byteRead=in.read(buffer);   
	                    if(byteRead==-1)
	                    	break;   
	                    out.write(buffer,0,byteRead);   
	                }   
	                out.close();
	                in.close();
				}catch(Exception e){
					e.printStackTrace();
					this.showMessage(this.getResponse(), "Ԥ��ʧ�ܣ��������������ĵ���Ϣ!", true);
				}
			}
			this.setAttribute("fileName", fileVersion.getNewFileName());
		}else{
			this.showMessage(this.getResponse(), "Ԥ��ʧ�ܣ�����������ȡ�ð汾���ĵ���Ϣ!", true);
		}
		
		this.setAttribute("fileVersion", fileVersion);
		if(StringUtils.isValid(getForward())){
			return this.getForward();
		}
		
		if(FileUtils.isPic(fileVersion.getFileSuffix())){
			return "previewPic";
		}else if(FileUtils.isMedia(fileVersion.getFileSuffix())){
			return "previewMedia";
		}
		return "previewFile";
	}
	/**
	 * �ļ����ع���
	 */
	public void downLoadFile(){
		EdocFile sourceFile = fileService.getFileById(this.getParameter("sourceFileId"));
		String fileName =sourceFile.getNewFileName();
//		String tempName = this.getRequest().getParameter("sourceFilename");
		if(StringUtils.isValid(fileName)){
			String tempDir = ConfigResource.getConfig(ConfigResource.EDOCUPLOADDIR);
			BufferedOutputStream outstream = null;
			BufferedInputStream inputstream = null;
			try {
				//��ȡĿ¼�ľ���·��
				File dir = new File(tempDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				String dirAbsolutePath = dir.getAbsolutePath();
			
				//���Ҹ��ļ�,����ļ�������ɾ��������ͬ������true
				File file = new File(dirAbsolutePath + "\\" + fileName);
				this.getResponse().setContentType("application/msdownload;charset=GBK");
				this.getResponse().setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");
				outstream = new BufferedOutputStream(this.getResponse().getOutputStream());
	            outstream.flush();
	            inputstream = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
	            int len;
	    	    byte[] buff = new byte[1024];
	    	    
	            while ((len=inputstream.read(buff,0,buff.length)) != -1){
	                 outstream.write(buff,0,len);
	            }
	            outstream.flush();
	           	inputstream.close();
	           	outstream.close();
			}catch(Exception e){
				try{
		           	inputstream.close();
		           	outstream.close();
				}catch(Exception ee){
					ee.printStackTrace();
				}
			}
		}
	}
	/**
	 * �ļ��ϴ�(Ŀǰֻ֧�ֵ����ļ����ϴ�)
	 * @return
	 * @author 2010-8-5
	 */
	public void uploadFiles(){
		User user = (User)this.getSession().getAttribute("DOCUSER");
		edocFile = new EdocFile();
		edocFile.setFileName(docFileFileName);										//�����ļ�ԭ����								
		edocFile.setFileSize(Math.round(((double)docFile.length()/1024)));			//�����ļ���С(�����С����������)
		edocFile.setIsFolder(0);
		
		String suffix = docFileFileName.substring(docFileFileName.lastIndexOf(".")+1);
		edocFile.setFileSuffix(suffix);												//�����ļ��ĺ�׺
		edocFile.setFileType(FileTypeEnum.getType(suffix));							//�����ļ�����
		edocFile.setNewFileName(new RandomGUID().toString()+"."+suffix);			//�����ļ����µ�����(���ļ�������ʹ��GUID���ɵ�32λ�ַ���)
		
		edocFile.setCreatorId(user.getId());										//���ô�����ԱID
		edocFile.setCreatorName(user.getTrueName());								//���ô�����������
		edocFile.setParentId(getRequest().getParameter("parentId"));				//�����ϼ��ļ�Id
		edocFile.setDesc(getParameter("desc"));
		try {
			fileService.uploadFile(edocFile, new FileInputStream(docFile));

			//�����ʼ�֪ͨ�����
			String sendMailFlag = getParameter("sendMailFlag");						//�Ƿ����ʼ�֪ͨ
			if(StringUtils.isValid(sendMailFlag) && sendMailFlag.equals("true")){
				EmailInfo msg = new EmailInfo();
				msg.setSubject(getParameter("mailSubject"));
				msg.setContent(getParameter("mailContent"));

				mailSender.send(msg,null);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.showMessage(this.getResponse(), "�ļ��ϴ��ɹ�!", true);
		return;
	}
	
	/**
	 * �ϴ��µ��ļ��汾
	 * @author 	�³� 2011-01-09
	 */
	public void addNewVersionFile(){
		User user = (User)this.getSession().getAttribute("DOCUSER");
		
		HttpServletRequest req = this.getRequest();
		String sourceFileId = req.getParameter("sourceFileId");			//��ȡԭ�ļ���ID
		String sourceFileName = req.getParameter("sourceFileName");
		if(!sourceFileName.equals(docFileFileName)){
			this.showMessage2(this.getResponse(), "�°汾���ļ����Ʊ�����Դ�ļ�'"+sourceFileName+"'����һ��!", true);
			return;
		}
		FileVersion newFileVersion = new FileVersion();
		
		newFileVersion.setEdocFileId(sourceFileId);
		newFileVersion.setFileName(docFileFileName);										//�����ļ�ԭ����								
		newFileVersion.setFileSize(Math.round(((double)docFile.length()/1024)));			//�����ļ���С(�����С����������)
		
		String suffix = docFileFileName.substring(docFileFileName.indexOf(".")+1);
		newFileVersion.setFileSuffix(suffix);												//�����ļ��ĺ�׺
		newFileVersion.setFileType(FileTypeEnum.getType(suffix));							//�����ļ�����
		newFileVersion.setNewFileName(new RandomGUID().toString()+"."+suffix);				//�����ļ����µ�����(���ļ�������ʹ��GUID���ɵ�32λ�ַ���)
		
		newFileVersion.setCreatorId(user.getId());											//���ô�����ԱID
		newFileVersion.setCreatorName(user.getTrueName());									//���ô�����������
		try {
			fileService.addNewVersionFile(sourceFileId,newFileVersion, new FileInputStream(docFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.showMessage(this.getResponse(), "����°汾�����ɹ�!", true);
		return;
	}
	
	/**
	 * �����ļ���
	 * @return
	 */
	public String createFolder(){
		String parentId = this.getParameter("parentId");
		User user = (User)this.getSession().getAttribute("DOCUSER");
		if(fileService.isExist(parentId, edocFile.getFileName(), user.getId())){
			this.showMessage2(this.getResponse(), "����ͬ�����ļ��У�", true);
			return null;
		}
		edocFile.setCreatorId(user.getId());
		edocFile.setCreatorName(user.getTrueName());
		fileService.saveFolder(edocFile);
		return null;
	}
	public EdocFile getEdocFile() {
		return edocFile;
	}
	public void setEdocFile(EdocFile edocFile) {
		this.edocFile = edocFile;
	}
	public File getDocFile() {
		return docFile;
	}
	public void setDocFile(File docFile) {
		this.docFile = docFile;
	}
	public String getDocFileFileName() {
		return docFileFileName;
	}
	public void setDocFileFileName(String docFileFileName) {
		this.docFileFileName = docFileFileName;
	}
	public String getDocFileContentType() {
		return docFileContentType;
	}
	public void setDocFileContentType(String docFileContentType) {
		this.docFileContentType = docFileContentType;
	}

	public String[] getDeleteParams() {
		return deleteParams;
	}

	public void setDeleteParams(String[] deleteParams) {
		this.deleteParams = deleteParams;
	}

	
}
