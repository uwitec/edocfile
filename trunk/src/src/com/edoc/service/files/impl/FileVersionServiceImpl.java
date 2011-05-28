package com.edoc.service.files.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.dbsupport.PropertyFilter;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.FileVersion;
import com.edoc.lucene.index.IndexService;
import com.edoc.orm.hibernate.dao.FileDAO;
import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.service.files.FileVersionService;
import com.edoc.service.files.UploadService;
import com.edoc.utils.ConfigResource;
import com.edoc.utils.Timer;

@Component("fileVersionServiceImpl")
@Transactional(readOnly=true)
public class FileVersionServiceImpl implements FileVersionService{

	@Resource(name="fileVersionDao")
	private GenericDAO<FileVersion,String> fileVersionDao=null;
	
	@Resource(name="edocFileDao")
	private FileDAO edocFileDao=null;
	
	@Resource(name="localUploadServie")
	private UploadService upService = null;
	
	@Resource(name="defaultIndexService")
	private IndexService indexService = null;
	
	/**
	 * 获取文件版本信息
	 */
	public PageValueObject<FileVersion> getEdocFileVersions(int currentPage,
			int pageSize, String sourceFileId) {
		PageValueObject<FileVersion> page = null;
		if(sourceFileId!=null && sourceFileId.trim().length()>0){
			page = new PageValueObject<FileVersion>(currentPage,pageSize);
			
			List<PropertyFilter> filterList = new ArrayList<PropertyFilter>(1);
			PropertyFilter filter = new PropertyFilter("edocFileId",sourceFileId,PropertyFilter.MatchType.EQ);
			filterList.add(filter);
			
			page.setResult(fileVersionDao.find(filterList, page.getFirstResult(), page.getPageSize()));
			page.setTotalRows(fileVersionDao.getCount(filterList));
		}
		return page;
	}
	
	
	/**
	 * 在想添加版本信息
	 * @param fileVersion
	 * @param fileInputStream
	 */
	@Transactional(readOnly = false)
	public void addFileVersionFromOnline(FileVersion fileVersion, FileInputStream fileInputStream){
		String currentVersion = getFileVersion(fileVersion.getEdocFileId());	//获取版本号
		fileVersion.setVersion(currentVersion);
		
		//修改原始文件的版本号
		EdocFile sourceFile = edocFileDao.get(fileVersion.getEdocFileId());
		if(sourceFile!=null){
			sourceFile.setCurrentVersion(currentVersion);
			edocFileDao.update(sourceFile);
		}
		
		//对新版本文件进行上传、建索引操作
		upService.uploadFile(fileVersion.getNewFileName(), fileInputStream);					//上次文件
		String filePath = ConfigResource.getConfig(ConfigResource.EDOCUPLOADDIR)+"\\"+fileVersion.getNewFileName();
		indexService.addIndex(fileVersion.getFileName(),new File(filePath), fileVersion);		//创建索引
		
		fileVersionDao.save(fileVersion);
	}
	
	/**
	 * 获取版本好信息
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

}
