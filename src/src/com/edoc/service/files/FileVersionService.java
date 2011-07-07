package com.edoc.service.files;

import java.io.FileInputStream;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.files.FileVersion;

/**
 * 文件版本操作
 * @author 陈超 ${@link FileVersionServiceImpl }
 *
 */
public interface FileVersionService {

	/**
	 * 分页获取文件版本信息
	 * @param currentPage		当前页号
	 * @param pageSize			每页显示的记录数
	 * @param sourceFileId		原始文件的ID
	 * @return
	 */
	PageValueObject<FileVersion> getEdocFileVersions(int currentPage,
			int pageSize, String sourceFileId);

	/**
	 * 在想添加版本信息
	 * @param fileVersion
	 * @param fileInputStream
	 */
	public void addFileVersionFromOnline(FileVersion fileVersion,
			FileInputStream fileInputStream);
	
	
	/**
	 * 查找文件版本信息
	 * @param sFileId		原始文件信息
	 * @param version		文件版本号
	 */
	public FileVersion findFileVersion(String sFileId,String version);

}
