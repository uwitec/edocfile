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
 * 文件夹管理
 * 
 * @author 陈超	2010-7-21
 *
 */
@Component("fileAction")
@Scope("prototype")
public class FileAction  extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private EdocFile edocFile = null;	//文件信息
	private File docFile;				//上传的文件
	private String docFileFileName;		//文件名称
	private String docFileContentType;	//文件类型
	private String[] deleteParams;		//删除文件时的参数
	@Resource(name="fileService")
	private FileService fileService = null;

	@Resource(name="mailSender")
	private MailSender mailSender = null;
	
	@Resource(name="shoreFileService")
	private ShoreFileService shoreFileService = null;
	
	@Resource(name="visitUserService")
	private VisitUserService visitUserService = null;
	
	/**
	 * 取消共享操作
	 * @return
	 */
	public String cancelShore(){
		String fileId = this.getParameter("fileId");
		
		fileService.cancelShore(fileId);
		
		//在多个页面调用了此方法,因此要判断跳转到哪个页面中去
		String page = this.getParameter("page");
		if(StringUtils.isValid(page) && page.equals("myFolder")){
			return getMyFilesByParentId();
		}
		return getShoredFiles();
	}
	
	/**
	 * 查看文件基本信息
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
	 * 获取文件夹下面的文件信息
	 * @return
	 */
	public String getMyFilesByParentId(){
		
		//获取parentId下的文件信息
		//获取上级目录ID,如果上级菜单ID无效的话则设为"0"
		String parentId = getRequest().getParameter("parentId");
		if(!StringUtils.isValid(parentId)){
			parentId = "0";
		}
		User user = (User)this.getSession().getAttribute("DOCUSER");
		String fileName = getRequest().getParameter("fileName");		//要查询的文件名称
		
		PageValueObject<EdocFile> filePageVO = fileService.getMyFilesByParentId(getCurrentPage(), getPageSize(),user.getId(), parentId, fileName);
		getRequest().setAttribute("filePageVO", filePageVO);
		getRequest().setAttribute("parentId", parentId);
		getRequest().setAttribute("fileName", fileName);
		
		//获取目录结构信息
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
	 * 获取共享文件信息
	 * @return
	 */
	public String getShoredFiles(){
		String parentId = getRequest().getParameter("parentId");
		if(!StringUtils.isValid(parentId)){
			parentId = "-1";
		}
		User user = (User)this.getSession().getAttribute("DOCUSER");
		String fileName = getParameter("fileName");		//要查询的文件名称
		
		PageValueObject<ShoreFile> filePageVO = shoreFileService.getShoredFiles(getCurrentPage(), getPageSize(),user.getId(), parentId, fileName);
		getRequest().setAttribute("filePageVO", filePageVO);
		getRequest().setAttribute("parentId", parentId);
		getRequest().setAttribute("fileName", fileName);
		
		//获取目录结构信息
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
	 * 创建树形菜单"我的文件夹",最终以JSON的格式返回到客户端
	 * @author 陈超 2011-01-29
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
	 * 创建树形菜单"我的文件夹",最终以JSON的格式返回到客户端
	 * @author 陈超 2011-01-29
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
		int shoreMuluFlag = 0;												//是否共享目录,0不共享,1共享
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
	
	/**
	 * 删除文件/文件夹
	 * @return
	 */
	public String deleteFile(){
		User user = (User)this.getSession().getAttribute("DOCUSER");
		fileService.deleteFile(deleteParams,user.getId());
		return getMyFilesByParentId();
	}
//	/**
//	 * 获取"我的文件夹"中的根文件夹信息以及根文件信息
//	 * 
//	 * @author 陈超 2010-7-21
//	 * @return
//	 */
//	public String getRootFileFromMyFolder(){
//		String creatorId = "0";
//		PageValueObject<EdocFile> filePageVO = fileService.getRootFileFromMyFolder(getCurrentPage(), getPageSize(),creatorId);
//		this.getRequest().setAttribute("filePageVO", filePageVO);
//		return "showMyFileListPage";
//	}
	
	/**
	 * 文件预览：将要预览的文件下载到 temp/ 文件夹下
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
		            //输入流   
		            InputStream in =new FileInputStream(sourceFilePath);   
		            //输出流   
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
					this.showMessage(this.getResponse(), "预览失败：不能正常下载文档信息!", true);
				}
			}
			this.setAttribute("fileName", fileVersion.getNewFileName());
		}else{
			this.showMessage(this.getResponse(), "预览失败：不能正常获取该版本的文档信息!", true);
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
	 * 文件下载功能
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
				//获取目录的绝对路径
				File dir = new File(tempDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				String dirAbsolutePath = dir.getAbsolutePath();
			
				//查找该文件,如果文件存在则删除不存在同样返回true
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
	 * 文件上传(目前只支持单个文件的上传)
	 * @return
	 * @author 2010-8-5
	 */
	public void uploadFiles(){
		User user = (User)this.getSession().getAttribute("DOCUSER");
		edocFile = new EdocFile();
		edocFile.setFileName(docFileFileName);										//设置文件原名称								
		edocFile.setFileSize(Math.round(((double)docFile.length()/1024)));			//设置文件大小(算出大小后四舍五入)
		edocFile.setIsFolder(0);
		
		String suffix = docFileFileName.substring(docFileFileName.lastIndexOf(".")+1);
		edocFile.setFileSuffix(suffix);												//设置文件的后缀
		edocFile.setFileType(FileTypeEnum.getType(suffix));							//设置文件类型
		edocFile.setNewFileName(new RandomGUID().toString()+"."+suffix);			//设置文件的新的名称(新文件名称是使用GUID生成的32位字符串)
		
		edocFile.setCreatorId(user.getId());										//设置创建人员ID
		edocFile.setCreatorName(user.getTrueName());								//设置创建任意名称
		edocFile.setParentId(getRequest().getParameter("parentId"));				//设置上级文件Id
		edocFile.setDesc(getParameter("desc"));
		try {
			fileService.uploadFile(edocFile, new FileInputStream(docFile));

			//发送邮件通知相关人
			String sendMailFlag = getParameter("sendMailFlag");						//是否发送邮件通知
			if(StringUtils.isValid(sendMailFlag) && sendMailFlag.equals("true")){
				EmailInfo msg = new EmailInfo();
				msg.setSubject(getParameter("mailSubject"));
				msg.setContent(getParameter("mailContent"));

				mailSender.send(msg,null);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.showMessage(this.getResponse(), "文件上传成功!", true);
		return;
	}
	
	/**
	 * 上传新的文件版本
	 * @author 	陈超 2011-01-09
	 */
	public void addNewVersionFile(){
		User user = (User)this.getSession().getAttribute("DOCUSER");
		
		HttpServletRequest req = this.getRequest();
		String sourceFileId = req.getParameter("sourceFileId");			//获取原文件的ID
		String sourceFileName = req.getParameter("sourceFileName");
		if(!sourceFileName.equals(docFileFileName)){
			this.showMessage2(this.getResponse(), "新版本的文件名称必须与源文件'"+sourceFileName+"'名称一致!", true);
			return;
		}
		FileVersion newFileVersion = new FileVersion();
		
		newFileVersion.setEdocFileId(sourceFileId);
		newFileVersion.setFileName(docFileFileName);										//设置文件原名称								
		newFileVersion.setFileSize(Math.round(((double)docFile.length()/1024)));			//设置文件大小(算出大小后四舍五入)
		
		String suffix = docFileFileName.substring(docFileFileName.indexOf(".")+1);
		newFileVersion.setFileSuffix(suffix);												//设置文件的后缀
		newFileVersion.setFileType(FileTypeEnum.getType(suffix));							//设置文件类型
		newFileVersion.setNewFileName(new RandomGUID().toString()+"."+suffix);				//设置文件的新的名称(新文件名称是使用GUID生成的32位字符串)
		
		newFileVersion.setCreatorId(user.getId());											//设置创建人员ID
		newFileVersion.setCreatorName(user.getTrueName());									//设置创建任意名称
		try {
			fileService.addNewVersionFile(sourceFileId,newFileVersion, new FileInputStream(docFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.showMessage(this.getResponse(), "添加新版本操作成功!", true);
		return;
	}
	
	/**
	 * 创建文件夹
	 * @return
	 */
	public String createFolder(){
		String parentId = this.getParameter("parentId");
		User user = (User)this.getSession().getAttribute("DOCUSER");
		if(fileService.isExist(parentId, edocFile.getFileName(), user.getId())){
			this.showMessage2(this.getResponse(), "存在同名的文件夹！", true);
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
