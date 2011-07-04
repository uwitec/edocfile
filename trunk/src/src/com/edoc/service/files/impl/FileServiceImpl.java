package com.edoc.service.files.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.dbsupport.PropertyFilter;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.FileVersion;
import com.edoc.entity.files.RecycleInfo;
import com.edoc.lucene.index.IndexService;
import com.edoc.orm.hibernate.dao.FileDAO;
import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.service.files.FileService;
import com.edoc.service.files.ShoreFileService;
import com.edoc.service.files.UploadService;
import com.edoc.service.files.VisitUserService;
import com.edoc.utils.ConfigResource;
import com.edoc.utils.StringUtils;
import com.edoc.utils.Timer;

@Component("fileService")
@Transactional(readOnly=true)
public class FileServiceImpl implements FileService{

	@Resource(name="edocFileDao")
	private FileDAO edocFileDao=null;
	
	@Resource(name="fileVersionDao")
	private GenericDAO<FileVersion,String> fileVersionDao=null;
	
	@Resource(name="recycleDao")
	private GenericDAO<RecycleInfo,String> recycleDao=null;
	
	@Resource(name="localUploadServie")
	private UploadService upService = null;
	
	@Resource(name="defaultIndexService")
	private IndexService indexService = null;
	
	@Resource(name="shoreFileService")
	private ShoreFileService shoreFileService = null;
	
	@Resource(name="visitUserService")
	private VisitUserService visitUserService = null;
	

	/**
	 * �����ҵ������ļ�ID,��������ɾ�����ļ�
	 * @return
	 */
	public String[] getMyFileIds(String userId,int fileType){
		String[] fileIds = null;
		List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
		if(fileType!=2){
			if(fileType==0){
				PropertyFilter filter01 = new PropertyFilter("isFolder",1,PropertyFilter.MatchType.EQ);
				filters.add(filter01);
			}else if(fileType==1){
				PropertyFilter filter01 = new PropertyFilter("isFolder",0,PropertyFilter.MatchType.EQ);
				filters.add(filter01);
			}
		}
		PropertyFilter filter02 = new PropertyFilter("creatorId",userId,PropertyFilter.MatchType.EQ);
		filters.add(filter02);
		List<EdocFile> rs = edocFileDao.find(filters);
		if(rs!=null && !rs.isEmpty()){
			fileIds = new String[rs.size()];
			int index = 0;
			for(EdocFile e:rs){
				fileIds[index] = e.getId();
				index++;
			}
		}
		return fileIds;
	}
	/**
	 * ��ѯ�ļ��汾
	 * @param sourceFileId	Դ�ļ���¼ID
	 * @param version		�ļ��汾��
	 * @return
	 */
	public FileVersion getFileVersion(String sourceFileId, String version){
		List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
		PropertyFilter filter01 = new PropertyFilter("edocFileId",sourceFileId,PropertyFilter.MatchType.EQ);
		filters.add(filter01);
		
		PropertyFilter filter02 = new PropertyFilter("version",version,PropertyFilter.MatchType.EQ);
		filters.add(filter02);
		List<FileVersion> rs = fileVersionDao.find(filters);
		if(rs!=null && !rs.isEmpty()){
			return rs.get(0);
		}
		return null;
	}
	/**
	 * �жϵ�ǰ�ļ���������ļ��Ƿ����
	 * @param parentFileId
	 * @param fileName
	 * @param userId
	 * @return
	 */
	public boolean isExist(String parentFileId,String fileName,String userId){
		List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
		PropertyFilter filter01 = new PropertyFilter("parentId",parentFileId,PropertyFilter.MatchType.EQ);
		filters.add(filter01);
		
		PropertyFilter filter02 = new PropertyFilter("fileName",fileName,PropertyFilter.MatchType.EQ);
		filters.add(filter02);
		
		if(StringUtils.isValid(userId)){
			PropertyFilter filter03 = new PropertyFilter("creatorId",userId,PropertyFilter.MatchType.EQ);
			filters.add(filter03);
		}
		
		List<EdocFile> rs = edocFileDao.find(filters);
		if(rs!=null && !rs.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * ��ȡĿ¼�ṹ��Ϣ(�����ļ�)
	 * @param parentId	�ڵ�ID
	 * @param showRoot	�Ƿ���ʾ���ڵ�
	 * @return
	 */
	public List<EdocFile> getShoredMulus(String parentId, int showRoot,int showSelf){
		return edocFileDao.getShoredParentFiles(parentId, showRoot, showSelf);
	}
	/**
	 * ��ȡĿ¼�ṹ��Ϣ
	 * @param parentId	�ڵ�ID
	 * @param showRoot	�Ƿ���ʾ���ڵ�
	 * @return
	 */
	public List<EdocFile> getMulus(String parentId, int showRoot,int showSelf){
		return edocFileDao.getParentFiles(parentId, showRoot, showSelf);
	}
	/**
	 * �����ļ��������
	 * @param fileId 	�ļ�Id
	 * @author 			�³� 
	 */
	@Transactional(readOnly = false)
	public void cancelShore(String fileId){
		String[] sourceFileIds = {fileId};
		visitUserService.deleteVisitUserBySourceFileId(sourceFileIds);		//��ӷ��ʸ��ļ����û���Ϣ
		shoreFileService.deleteShoreFileBySourceFileId(sourceFileIds);				//��ӹ����ļ�����Ϣ
		updateFileShoreState(false, fileId);						//����Դ�ļ���"����"״̬
	}
	
	/**
	 * �����ļ���
	 */
	public boolean createFolder(EdocFile edocFile) {
		edocFileDao.save(edocFile);
		return true;
	}
	
	/**
	 * ��ѯ�ļ���
	 * @param parentId
	 * @return
	 */
	public List<EdocFile> getFoldersByParentId(String parentId,String userId){
		List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
		PropertyFilter filter01 = new PropertyFilter("isFolder",1,PropertyFilter.MatchType.EQ);
		filters.add(filter01);
		
		if(StringUtils.isValid(parentId)){
			PropertyFilter filter02 = new PropertyFilter("parentId",parentId,PropertyFilter.MatchType.EQ);
			filters.add(filter02);
		}
		
		PropertyFilter filter03 = new PropertyFilter("creatorId",userId,PropertyFilter.MatchType.EQ);
		filters.add(filter03);
		
		return edocFileDao.find(filters);
	}
	/**
	 * ��ѯ�ļ���Ϣ
	 * @param currentPage
	 * @param pageSize
	 * @param userId
	 * @param parentId
	 * @param fileName
	 * @return
	 * @author �³� 2010-11-20
	 */
	public PageValueObject<EdocFile> getMyFilesByParentId(int currentPage,
			int pageSize, String userId, String parentId, String fileName){
		PageValueObject<EdocFile> page = null;
		if(StringUtils.isValid(userId) && StringUtils.isValid(parentId)){
			page = new PageValueObject<EdocFile>(currentPage,pageSize);
			/*
			 * ���ò�ѯ����:�ļ�������ID=creatorId
			 */
			List<PropertyFilter> filterList = new LinkedList<PropertyFilter>();
			PropertyFilter filter01 = new PropertyFilter("creatorId",userId,PropertyFilter.MatchType.EQ);
			filterList.add(filter01);
			PropertyFilter filter02 = new PropertyFilter("parentId",parentId,PropertyFilter.MatchType.EQ);
			filterList.add(filter02);
			//��Ӳ�ѯ����:queryFileName
			if(StringUtils.isValid(fileName)){
				PropertyFilter filter03 = new PropertyFilter("fileName",fileName,PropertyFilter.MatchType.LIKE);
				filterList.add(filter03);
			}
			
			page.setResult(edocFileDao.find(filterList, page.getFirstResult(), page.getPageSize()));
			page.setTotalRows(edocFileDao.getCount(filterList));
		}
		return page;
	}
	

	/**
	 * ����parentId��ȡ��Ӧ�����ļ���Ϣ
	 * @param currentPage
	 * @param pageSize
	 * @param parentId
	 * @return
	 * @author �³� 2010-11-18
	 */
	public PageValueObject<EdocFile> getSubFilesByParentId(int currentPage,
			int pageSize, String parentId){
		PageValueObject<EdocFile> page = null;
		if(parentId!=null && parentId.trim().length()>0){
			page = new PageValueObject<EdocFile>(currentPage,pageSize);
			
			/*
			 * ���ò�ѯ����:�ļ�������ID=creatorId
			 */
			List<PropertyFilter> filterList = new ArrayList<PropertyFilter>(1);
			PropertyFilter filter = new PropertyFilter("parentId",parentId,PropertyFilter.MatchType.EQ);
			filterList.add(filter);
			
			page.setResult(edocFileDao.find(filterList, page.getFirstResult(), page.getPageSize()));
			page.setTotalRows(edocFileDao.getCount());
		}
		return page;
		
	}
	
	/**
	 * ��ҳ����"�ҵ��ļ���"����ĸ��ļ�(�����ļ��к���ͨ�ļ�)
	 * @param creatorId ������ID
	 * @return
	 * @author �³� 2010-7-29
	 */
	public PageValueObject<EdocFile> getRootFileFromMyFolder(int currentPage, int pageSize, String creatorId){
		PageValueObject<EdocFile> page = null;
		if(creatorId!=null && creatorId.trim().length()>0){
			page = new PageValueObject<EdocFile>(currentPage,pageSize);
			
			/*
			 * ���ò�ѯ����:�ļ�������ID=creatorId
			 */
//			List<PropertyFilter> filterList = new ArrayList<PropertyFilter>(1);
//			PropertyFilter filter = new PropertyFilter("creatorId",creatorId,PropertyFilter.MatchType.EQ);
//			filterList.add(filter);
//			
//			page.setResult(edocFileDao.find(filterList, page.getFirstResult(), page.getPageSize()));
			page.setResult(edocFileDao.find(null, page.getFirstResult(), page.getPageSize()));
			page.setTotalRows(edocFileDao.getCount());
		}
		return page;
	}
	
	/**
	 * ��ҳ��ȡ�����ļ���/�ļ�
	 * @param currentPage	��ҳ��ѯ����ʼ��ַ
	 * @param pageSize		��ҳ��ѯÿҳ��ʾ�ļ�¼��
	 * @return
	 * @author 				�³� 2010-8-17
	 */
	public PageValueObject<EdocFile> getRootFileFromShoreFolder(
			int currentPage, int pageSize){
		PageValueObject<EdocFile> page = null;
		page = new PageValueObject<EdocFile>(currentPage, pageSize);

		/*
		 * ���ò�ѯ����:�ļ�������ID=creatorId
		 */
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>(1);
		PropertyFilter filter = new PropertyFilter("isShored",1,PropertyFilter.MatchType.EQ);
		filterList.add(filter);
		
		page.setResult(edocFileDao.find(filterList, page.getFirstResult(), page.getPageSize()));
		page.setTotalRows(edocFileDao.getCount(filterList));
		return page;
	}
	
	/**
	 * �����µ��ļ���
	 * @param edocFile �ļ�����Ϣ
	 * @return	�����ɹ�����true���򷵻�false
	 * @author �³� 2010-7-31
	 */
	@Transactional(readOnly=false)
	public boolean saveFolder(EdocFile edocFile){
		edocFileDao.save(edocFile);
		return true;
	}
	
	/**
	 * �ϴ��ļ�����
	 * @param edocFile	�ļ���Ϣ��
	 * @param src		�ļ�������
	 * @return   		�ϴ��ɹ�����true,���򷵻�false
	 * @author 			�³� 2010-8-7
	 */
	@Transactional(readOnly=false)
	public boolean uploadFile(EdocFile edocFile, FileInputStream src){
		//upService.uploadFile(edocFile.getNewFileName(), src);
		//indexService.addIndex(new File(ConfigResource.getConfig(ConfigResource.EDOCUPLOADDIR)+"\\"+edocFile.getNewFileName()), edocFile);
		
		FileVersion newFileVersion = new FileVersion();
		newFileVersion.setEdocFileId(edocFile.getId());
		newFileVersion.setFileName(edocFile.getFileName());										//�����ļ�ԭ����								
		newFileVersion.setFileSize(edocFile.getFileSize());										//�����ļ���С(�����С����������)
		
		newFileVersion.setFileSuffix(edocFile.getFileSuffix());									//�����ļ��ĺ�׺
		newFileVersion.setFileType(edocFile.getFileType());										//�����ļ�����
		newFileVersion.setNewFileName(edocFile.getNewFileName());								//�����ļ����µ�����(���ļ�������ʹ��GUID���ɵ�32λ�ַ���)
		newFileVersion.setIcon(edocFile.getIcon());
		
		newFileVersion.setCreatorId(edocFile.getCreatorId());									//���ô�����ԱID
		newFileVersion.setCreatorName(edocFile.getCreatorName());								//���ô�����Ա����
		
		newFileVersion.setUpdateUserId(edocFile.getCreatorId());								//���õ�ǰ�汾�ı༭��ԱId
		newFileVersion.setUpdateUserName(edocFile.getCreatorName());							//���õ�ǰ�汾�ı༭��Ա����
		
		String currentVersion = getFileVersion(edocFile.getId());								//��ȡ�汾��
		newFileVersion.setVersion(currentVersion);
		edocFile.setCurrentVersion(currentVersion);
		upService.uploadFile(newFileVersion.getNewFileName(), src);
		
		indexService.addIndex(edocFile.getFileName(),new File(ConfigResource.getConfig(ConfigResource.EDOCUPLOADDIR)+"\\"+newFileVersion.getNewFileName()), newFileVersion);
		fileVersionDao.save(newFileVersion);
		edocFileDao.save(edocFile);
		return true;
	}
	
	
	/**
	 * ��Ӱ汾��Ϣ
	 * @param sourceFileId
	 * @param newFileVersion
	 * @param fileInputStream
	 * @author 	�³� 2011-01-09
	 */
	@Transactional(readOnly=false)
	public void addNewVersionFile(String sourceFileId,
			FileVersion newFileVersion, FileInputStream fileInputStream){
		
		String currentVersion = getFileVersion(sourceFileId);	//��ȡ�汾��
		newFileVersion.setVersion(currentVersion);
		EdocFile sourceFile = this.getFileById(sourceFileId);
		if(sourceFile!=null){
			sourceFile.setCurrentVersion(currentVersion);
			edocFileDao.update(sourceFile);
		}
		upService.uploadFile(newFileVersion.getNewFileName(), fileInputStream);
		indexService.addIndex(newFileVersion.getFileName(),new File(ConfigResource.getConfig(ConfigResource.EDOCUPLOADDIR)+"\\"+newFileVersion.getNewFileName()), newFileVersion);
		fileVersionDao.save(newFileVersion);
	}
	
	/**
	 * ��ȡ�ĵ��İ汾��Ϣ
	 * @param sourceFileId
	 * @return
	 */
	private String getFileVersion(String sourceFileId){
		//��ȡ��ǰ�汾��:VyyyyMMDD-version
		String currentDate = Timer.convertToString(new Date(), "yyyyMMdd");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>(1);
		PropertyFilter filter1 = new PropertyFilter("version",currentDate,PropertyFilter.MatchType.LIKE);
		filters.add(filter1);
		PropertyFilter filter2 = new PropertyFilter("edocFileId",sourceFileId,PropertyFilter.MatchType.EQ);
		filters.add(filter2);
		int count = fileVersionDao.getCount(filters);
		String currentVersion = "V"+currentDate+"-"+Integer.toString(count+1);
		return currentVersion;
	}
	
	
	/**
	 * ����ԭ�ļ�ID��ȡ�ļ���Ϣ
	 * @param id
	 * @return
	 */
	public EdocFile getFileById(String id){
		return edocFileDao.get(id);
	}
	
	/**
	 * ɾ���ļ�
	 * @param deleteParams 	ɾ������
	 * @return
	 * @author 				�³� 2010-8-10
	 */
	@Transactional(readOnly=false)
	public void deleteFile(String[] deleteParams, String creatorId){
		if(deleteParams!=null){
			
			visitUserService.deleteVisitUserBySourceFileId(deleteParams);				//��ӷ��ʸ��ļ����û���Ϣ
			shoreFileService.deleteShoreFileBySourceFileId(deleteParams);				//��ӹ����ļ�����Ϣ
			List<RecycleInfo> recyclyInfos = new LinkedList<RecycleInfo>();
			//���ļ����ó���ɾ����ȡ����������
			String sql = "update sys_fileinfo set I_ISDELETE = 1,I_ISSHORED=0 where id in ('-1'";
			for(String id:deleteParams){
				sql += ",'"+id+"'";
				
				RecycleInfo tempRecycle = new RecycleInfo();
				tempRecycle.setCreatorId(creatorId);
				tempRecycle.setTableName("sys_fileinfo");
				tempRecycle.setSourceId(id);
				
				recyclyInfos.add(tempRecycle);
			}
			recycleDao.save(recyclyInfos);		//��ɾ�����ļ���Ϣ���浽����վ����ȥ
			sql += ")";
			edocFileDao.update(sql);
		}
	}
	
	/**
	 * ���¹����ļ��Ĺ���״̬
	 * @param flag true��ʾ����,flase��ʾ��������
	 */
	public boolean updateFileShoreState(boolean flag,String sourceFileId){
		if(StringUtils.isValid(sourceFileId)){
			String hql = "";
			if(flag){
				hql = "update EdocFile set isShored = 1 where id='"+sourceFileId+"'";
			}else{
				hql = "update EdocFile set isShored = 0 where id='"+sourceFileId+"'";
			}
			int count = edocFileDao.executeUpdate(hql);
			if(count>0){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	

}
