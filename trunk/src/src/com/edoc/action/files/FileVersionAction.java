package com.edoc.action.files;

import java.io.File;
import java.io.FileInputStream;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.baseinfo.User;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.FileVersion;
import com.edoc.service.files.FileService;
import com.edoc.service.files.FileUseRecordService;
import com.edoc.service.files.FileVersionService;
import com.edoc.utils.RandomGUID;

/**
 * 文本版本控制action
 * 
 * @author 陈超 2011-05-28
 * 
 */
@Component("fileVersionAction")
@Scope("prototype")
public class FileVersionAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	@Resource(name="fileService")
	private FileService fileService = null;
	private File fileObj = null;				//上传的文件 

	@Resource(name = "fileVersionServiceImpl")
	private FileVersionService fversionService = null;
	
	@Resource(name="fileUseRecordService")
	private FileUseRecordService fileUseRecordService = null;

	/**
	 * 获取文件版本信息
	 * 
	 * @return
	 */
	public String getFileVersions() {
		String sourceFileId = this.getParameter("sourceFileId"); // 获取原文件的ID
		PageValueObject<FileVersion> fileVersions = fversionService.getEdocFileVersions(getCurrentPage(), getPageSize(), sourceFileId);

		this.getRequest().setAttribute("fileVersions", fileVersions);
		this.getRequest().setAttribute("sourceFileId", sourceFileId);
		this.setAttribute("currentTab", 1);

		return "showFileVersions";
	}

	/**
	 * 通过手动上传的方式增加版本信息(这类方法提供无法在线编辑的文档进行版本控制或者手工通过上传新的版本信息)
	 * 
	 * @return
	 */
	public String addFileVersionFromUpload() {

		return "";
	}

	/**
	 * 通过在线编辑的方式增加版本信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String addFileVersionFromOnline() {
		User user = (User)this.getSession().getAttribute("DOCUSER");
		String sourceFileId = this.getParameter("sourceFileId");		//原文件记录Id
		String versionDesc = this.getParameter("versionDesc");			//版本描述(用来备案)
		EdocFile efile = fileService.getFileById(sourceFileId);
		try{
			FileVersion fileVersion = this.createFileVersionObj(efile, sourceFileId, versionDesc, user);
			fversionService.addFileVersionFromOnline(fileVersion,new FileInputStream(fileObj));
			
			//添加文件操作记录
			fileUseRecordService.addFileUseRecord(user, efile, FileUseRecordService.USETYPE_EDIT);
			this.print("true");
		}catch(Exception e){
			e.printStackTrace();
			this.print("false");
		}
		return null;
	}
	
	
	public FileVersion createFileVersionObj(EdocFile efile,String sourceFileId, String versionDesc, User user){
		//创建文件版本对象
		FileVersion fileVersion = new FileVersion();
		
		//从表单信息中设置fileVersion属性
		fileVersion.setEdocFileId(sourceFileId);
		fileVersion.setUpdateUserId(user.getId());
		fileVersion.setUpdateUserName(user.getTrueName());
		fileVersion.setDesc(versionDesc);
		fileVersion.setFileSize(Math.round(((double)fileObj.length()/1024)));
		
		//从efile中设置fileVersion属性
		fileVersion.setCreatorId(efile.getCreatorId());
		fileVersion.setCreatorName(efile.getCreatorName());
		fileVersion.setFileName(efile.getFileName());
		fileVersion.setFileSuffix(efile.getFileSuffix());
		fileVersion.setFileType(efile.getFileType());
		fileVersion.setIcon(efile.getIcon());
		fileVersion.setNewFileName(new RandomGUID().toString()+"."+efile.getFileSuffix());
		return fileVersion;
	}

	public File getFileObj() {
		return fileObj;
	}

	public void setFileObj(File fileObj) {
		this.fileObj = fileObj;
	}

}
