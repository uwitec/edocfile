package com.edoc.service.files.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.dbsupport.PropertyFilter;
import com.edoc.entity.baseinfo.User;
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
import com.edoc.utils.RandomGUID;
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
	 * 根据文件ID集合查询对应的文件信息
	 * @param fileIds
	 * @return
	 */
	public List<EdocFile> findFiles(List<String> fileIds){
		return edocFileDao.findByIds(fileIds);
	}
	

	/**
	 * 文件拷贝操作,如果是剪切操作的话只需修改被剪切文件的上级目录Id即可。如果是拷贝操作的话需要保留
	 * 源文件信息同时需要对电子文件实体进行拷贝。
	 * 
	 * (拷贝操作时暂时不拷贝版本信息)
	 * 
	 * @param sFileIds				拷贝文件ID
	 * @param clipBoardType 		操作类型
	 * @param destFolderFileId		目标文件夹ID
	 */
	@Transactional(readOnly = false)
	public void copyOrCatFile(List<String> sFileIds,int operType, User user, String destFolderFileId){
		try{
		if(sFileIds!=null){
			//获取要复制/剪切的文件信息
			List<EdocFile> edocFiles = edocFileDao.findByIds(sFileIds);
			if(edocFiles!=null){
				Date currentDate = new Date();
				for(EdocFile efile:edocFiles){
					if(operType==CAT){
						//设置文件的属性
						efile.setCreateTime(currentDate);
						efile.setUpdateTime(currentDate);
						efile.setCreatorId(user.getId());
						efile.setCreatorName(user.getTrueName());
						efile.setParentId(destFolderFileId);
						
						//查看当前文件夹下是否存在同名的文件,如果存在则在名称前面加一个"复件"
						int count = getSameFileCount(destFolderFileId,efile.getFileName());
						if(count>0){
							efile.setFileName("复件("+count+")_"+efile.getFileName());
						}
						edocFileDao.saveOrUpdate(efile);
					}else if(operType==COPY){
						EdocFile newEdocFile = new EdocFile();
						BeanUtils.copyProperties(newEdocFile, efile);
						//设置文件的属性
						newEdocFile.setCreateTime(currentDate);
						newEdocFile.setUpdateTime(currentDate);
						newEdocFile.setCreatorId(user.getId());
						newEdocFile.setCreatorName(user.getTrueName());
						newEdocFile.setParentId(destFolderFileId);
						
						//查看当前文件夹下是否存在同名的文件,如果存在则在名称前面加一个"复件"
						int count = getSameFileCount(destFolderFileId,newEdocFile.getFileName());
						if(count>0){
							newEdocFile.setFileName("复件("+count+")_"+newEdocFile.getFileName());
						}
						
						newEdocFile.setId(new RandomGUID().toString());
						if(newEdocFile.getIsFolder()==0){
							String oldFileName = newEdocFile.getNewFileName();
							newEdocFile.setNewFileName(new RandomGUID().toString()+"."+newEdocFile.getFileSuffix());
							uploadFile(newEdocFile, new FileInputStream(new File(ConfigResource.getConfig(ConfigResource.EDOCUPLOADDIR)+"\\"+oldFileName)));
						}else{
							edocFileDao.save(newEdocFile);
						}
					}
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

	/**
	 * 查找用户创建的所有文件ID,不包含以删除的文件
	 * @return
	 */
	public String[] getMyFileIds(String userId,int fileType){
		String[] fileIds = null;
		List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
		
		//判断文件类型：2=所有文件(包括文件夹以及普通文件), 0=普通文件, 1=文件夹
		if(fileType!=2){
			PropertyFilter filter01 = new PropertyFilter("isFolder",fileType,PropertyFilter.MatchType.EQ);
			filters.add(filter01);
		}
		
		//文件创建人
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
	 * 判断当前文件夹下面该文件是否存在
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
	 * 获取具有相同名称的文件数
	 * @param parentFileId
	 * @param fileName
	 * @return
	 */
	private int getSameFileCount(String parentFileId,String fileName){
		List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
		PropertyFilter filter01 = new PropertyFilter("parentId",parentFileId,PropertyFilter.MatchType.EQ);
		filters.add(filter01);
		
		PropertyFilter filter02 = new PropertyFilter("fileName",fileName,PropertyFilter.MatchType.EQ);
		filters.add(filter02);
		
		
		return edocFileDao.getCount(filters);
	}
	/**
	 * 获取目录结构信息(共享文件)
	 * @param parentId	节点ID
	 * @param showRoot	是否显示根节点
	 * @return
	 */
	public List<EdocFile> getShoredMulus(String parentId, int showRoot,int showSelf){
		return edocFileDao.getShoredParentFiles(parentId, showRoot, showSelf);
	}
	/**
	 * 获取目录结构信息
	 * @param parentId	节点ID
	 * @param showRoot	是否显示根节点
	 * @return
	 */
	public List<EdocFile> getMulus(String parentId, int showRoot,int showSelf){
		return edocFileDao.getParentFiles(parentId, showRoot, showSelf);
	}
	/**
	 * 撤销文件共享操作
	 * @param fileId 	文件Id
	 * @author 			陈超 
	 */
	@Transactional(readOnly = false)
	public void cancelShore(String fileId){
		String[] sourceFileIds = {fileId};
		visitUserService.deleteVisitUserBySourceFileId(sourceFileIds);		//添加访问该文件的用户信息
		shoreFileService.deleteShoreFileBySourceFileId(sourceFileIds);				//添加共享文件的信息
		updateFileShoreState(false, fileId);						//更新源文件的"共享"状态
	}
	
	/**
	 * 创建文件夹
	 */
	public boolean createFolder(EdocFile edocFile) {
		edocFileDao.save(edocFile);
		return true;
	}
	
	/**
	 * 查询文件夹
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
	 * 查询文件信息
	 * @param currentPage
	 * @param pageSize
	 * @param userId
	 * @param parentId
	 * @param fileName
	 * @return
	 * @author 陈超 2010-11-20
	 */
	public PageValueObject<EdocFile> getMyFilesByParentId(int currentPage,
			int pageSize, String userId, String parentId, String fileName){
		PageValueObject<EdocFile> page = null;
		if(StringUtils.isValid(userId) && StringUtils.isValid(parentId)){
			page = new PageValueObject<EdocFile>(currentPage,pageSize);
			/*
			 * 设置查询条件:文件创建人ID=creatorId
			 */
			List<PropertyFilter> filterList = new LinkedList<PropertyFilter>();
			PropertyFilter filter01 = new PropertyFilter("creatorId",userId,PropertyFilter.MatchType.EQ);
			filterList.add(filter01);
			PropertyFilter filter02 = new PropertyFilter("parentId",parentId,PropertyFilter.MatchType.EQ);
			filterList.add(filter02);
			//添加查询参数:queryFileName
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
	 * 根据parentId获取对应的子文件信息
	 * @param currentPage
	 * @param pageSize
	 * @param parentId
	 * @return
	 * @author 陈超 2010-11-18
	 */
	public PageValueObject<EdocFile> getSubFilesByParentId(int currentPage,
			int pageSize, String parentId){
		PageValueObject<EdocFile> page = null;
		if(parentId!=null && parentId.trim().length()>0){
			page = new PageValueObject<EdocFile>(currentPage,pageSize);
			
			/*
			 * 设置查询条件:文件创建人ID=creatorId
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
	 * 分页或许"我的文件夹"下面的根文件(包括文件夹和普通文件)
	 * @param creatorId 创建人ID
	 * @return
	 * @author 陈超 2010-7-29
	 */
	public PageValueObject<EdocFile> getRootFileFromMyFolder(int currentPage, int pageSize, String creatorId){
		PageValueObject<EdocFile> page = null;
		if(creatorId!=null && creatorId.trim().length()>0){
			page = new PageValueObject<EdocFile>(currentPage,pageSize);
			
			/*
			 * 设置查询条件:文件创建人ID=creatorId
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
	 * 分页获取共享文件夹/文件
	 * @param currentPage	分页查询的起始地址
	 * @param pageSize		分页查询每页显示的记录数
	 * @return
	 * @author 				陈超 2010-8-17
	 */
	public PageValueObject<EdocFile> getRootFileFromShoreFolder(int currentPage, int pageSize){
		PageValueObject<EdocFile> page = null;
		page = new PageValueObject<EdocFile>(currentPage, pageSize);

		/*
		 * 设置查询条件:文件创建人ID=creatorId
		 */
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>(1);
		PropertyFilter filter = new PropertyFilter("isShored",1,PropertyFilter.MatchType.EQ);
		filterList.add(filter);
		
		page.setResult(edocFileDao.find(filterList, page.getFirstResult(), page.getPageSize()));
		page.setTotalRows(edocFileDao.getCount(filterList));
		return page;
	}
	
	/**
	 * 创建新的文件夹
	 * @param edocFile 文件夹信息
	 * @return	创建成功返回true否则返回false
	 * @author 陈超 2010-7-31
	 */
	@Transactional(readOnly=false)
	public boolean saveFolder(EdocFile edocFile){
		edocFileDao.save(edocFile);
		return true;
	}
	
//	/**
//	 * 上传文件操作
//	 * @param edocFile	文件信息类
//	 * @param src		文件输入流
//	 * @return   		上传成功返回true,否则返回false
//	 * @author 			陈超 2010-8-7
//	 */
//	@Transactional(readOnly=false)
//	public boolean uploadFile(EdocFile edocFile, FileInputStream src){
//		
//		FileVersion newFileVersion = new FileVersion();
//		newFileVersion.setEdocFileId(edocFile.getId());
//		newFileVersion.setFileName(edocFile.getFileName());										//设置文件原名称								
//		newFileVersion.setFileSize(edocFile.getFileSize());										//设置文件大小(算出大小后四舍五入)
//		
//		newFileVersion.setFileSuffix(edocFile.getFileSuffix());									//设置文件的后缀
//		newFileVersion.setFileType(edocFile.getFileType());										//设置文件类型
//		newFileVersion.setNewFileName(edocFile.getNewFileName());								//设置文件的新的名称(新文件名称是使用GUID生成的32位字符串)
//		newFileVersion.setIcon(edocFile.getIcon());
//		
//		newFileVersion.setCreatorId(edocFile.getCreatorId());									//设置创建人员ID
//		newFileVersion.setCreatorName(edocFile.getCreatorName());								//设置创建人员名称
//		
//		newFileVersion.setUpdateUserId(edocFile.getCreatorId());								//设置当前版本的编辑人员Id
//		newFileVersion.setUpdateUserName(edocFile.getCreatorName());							//设置当前版本的编辑人员姓名
//		
//		String currentVersion = getFileVersion(edocFile.getId());								//获取版本号
//		newFileVersion.setVersion(currentVersion);
//		edocFile.setCurrentVersion(currentVersion);
//		upService.uploadFile(newFileVersion.getNewFileName(), src);
//		
//		indexService.addIndex(edocFile.getFileName(),new File(ConfigResource.getConfig(ConfigResource.EDOCUPLOADDIR)+"\\"+newFileVersion.getNewFileName()), newFileVersion);
//		fileVersionDao.save(newFileVersion);
//		edocFileDao.save(edocFile);
//		return true;
//	}
	
	/**
	 * 上传文件操作,上传文件过程中首先为该文件建立版本信息同时还需要上传文件、建立索引等操作
	 * @param edocFile	文件信息类
	 * @param src		文件输入流
	 * @return   		上传成功返回true,否则返回false
	 * @author 			陈超 2010-8-7
	 */
	@Transactional(readOnly=false)
	public boolean uploadFile(EdocFile edocFile, FileInputStream src){
		String currentVersion = getFileVersion(edocFile.getId());								//获取版本号
		edocFile.setCurrentVersion(currentVersion);
		
		//根据EdocFile创建文件版本信息
		FileVersion newFileVersion = new FileVersion(edocFile,currentVersion,edocFile.getCreatorId(),edocFile.getCreatorName());
		
		//上传文件操作
		upService.uploadFile(newFileVersion.getNewFileName(), src);
		
		//建立索引操作
		indexService.addIndex(edocFile.getFileName(),new File(ConfigResource.getConfig(ConfigResource.EDOCUPLOADDIR)
				+"\\"+newFileVersion.getNewFileName()), newFileVersion);
		
		
		fileVersionDao.save(newFileVersion);
		edocFileDao.save(edocFile);
		return true;
	}
	
	
	/**
	 * 添加版本信息
	 * @param sourceFileId
	 * @param newFileVersion
	 * @param fileInputStream
	 * @author 	陈超 2011-01-09
	 */
	@Transactional(readOnly=false)
	public void addNewVersionFile(String sourceFileId,
			FileVersion newFileVersion, FileInputStream fileInputStream){
		
		String currentVersion = getFileVersion(sourceFileId);	//获取版本号
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
	 * 获取文档的版本信息
	 * @param sourceFileId
	 * @return
	 */
	private String getFileVersion(String sourceFileId){
		//获取当前版本号:VyyyyMMDD-version
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
	 * 根据原文件ID获取文件信息
	 * @param id
	 * @return
	 */
	public EdocFile getFileById(String id){
		return edocFileDao.get(id);
	}
	
	/**
	 * 删除文件
	 * @param deleteParams 	删除参数
	 * @return
	 * @author 				陈超 2010-8-10
	 */
	@Transactional(readOnly=false)
	public void deleteFile(String[] deleteParams, String creatorId){
		if(deleteParams!=null){
			
			visitUserService.deleteVisitUserBySourceFileId(deleteParams);				//添加访问该文件的用户信息
			shoreFileService.deleteShoreFileBySourceFileId(deleteParams);				//添加共享文件的信息
			List<RecycleInfo> recyclyInfos = new LinkedList<RecycleInfo>();
			//将文件设置成已删除且取消共享设置
			String sql = "update sys_fileinfo set I_ISDELETE = 1,I_ISSHORED=0 where id in ('-1'";
			for(String id:deleteParams){
				sql += ",'"+id+"'";
				
				RecycleInfo tempRecycle = new RecycleInfo();
				tempRecycle.setCreatorId(creatorId);
				tempRecycle.setTableName("sys_fileinfo");
				tempRecycle.setSourceId(id);
				
				recyclyInfos.add(tempRecycle);
			}
			recycleDao.save(recyclyInfos);		//将删除的文件信息保存到回收站里面去
			sql += ")";
			edocFileDao.update(sql);
		}
	}
	
	/**
	 * 更新共享文件的共享状态
	 * @param flag true表示共享,flase表示撤销共享
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
