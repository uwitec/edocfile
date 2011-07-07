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
 * �ı��汾����action
 * 
 * @author �³� 2011-05-28
 * 
 */
@Component("fileVersionAction")
@Scope("prototype")
public class FileVersionAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	@Resource(name="fileService")
	private FileService fileService = null;
	private File fileObj = null;				//�ϴ����ļ� 

	@Resource(name = "fileVersionServiceImpl")
	private FileVersionService fversionService = null;
	
	@Resource(name="fileUseRecordService")
	private FileUseRecordService fileUseRecordService = null;

	/**
	 * ��ȡ�ļ��汾��Ϣ
	 * 
	 * @return
	 */
	public String getFileVersions() {
		String sourceFileId = this.getParameter("sourceFileId"); // ��ȡԭ�ļ���ID
		PageValueObject<FileVersion> fileVersions = fversionService.getEdocFileVersions(getCurrentPage(), getPageSize(), sourceFileId);

		this.getRequest().setAttribute("fileVersions", fileVersions);
		this.getRequest().setAttribute("sourceFileId", sourceFileId);
		this.setAttribute("currentTab", 1);

		return "showFileVersions";
	}

	/**
	 * ͨ���ֶ��ϴ��ķ�ʽ���Ӱ汾��Ϣ(���෽���ṩ�޷����߱༭���ĵ����а汾���ƻ����ֹ�ͨ���ϴ��µİ汾��Ϣ)
	 * 
	 * @return
	 */
	public String addFileVersionFromUpload() {

		return "";
	}

	/**
	 * ͨ�����߱༭�ķ�ʽ���Ӱ汾��Ϣ
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String addFileVersionFromOnline() {
		User user = (User)this.getSession().getAttribute("DOCUSER");
		String sourceFileId = this.getParameter("sourceFileId");		//ԭ�ļ���¼Id
		String versionDesc = this.getParameter("versionDesc");			//�汾����(��������)
		EdocFile efile = fileService.getFileById(sourceFileId);
		try{
			FileVersion fileVersion = this.createFileVersionObj(efile, sourceFileId, versionDesc, user);
			fversionService.addFileVersionFromOnline(fileVersion,new FileInputStream(fileObj));
			
			//����ļ�������¼
			fileUseRecordService.addFileUseRecord(user, efile, FileUseRecordService.USETYPE_EDIT);
			this.print("true");
		}catch(Exception e){
			e.printStackTrace();
			this.print("false");
		}
		return null;
	}
	
	
	public FileVersion createFileVersionObj(EdocFile efile,String sourceFileId, String versionDesc, User user){
		//�����ļ��汾����
		FileVersion fileVersion = new FileVersion();
		
		//�ӱ���Ϣ������fileVersion����
		fileVersion.setEdocFileId(sourceFileId);
		fileVersion.setUpdateUserId(user.getId());
		fileVersion.setUpdateUserName(user.getTrueName());
		fileVersion.setDesc(versionDesc);
		fileVersion.setFileSize(Math.round(((double)fileObj.length()/1024)));
		
		//��efile������fileVersion����
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
