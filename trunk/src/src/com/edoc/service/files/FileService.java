package com.edoc.service.files;

import java.io.FileInputStream;
import java.util.List;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.baseinfo.User;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.FileVersion;
import com.edoc.service.files.impl.FileServiceImpl;

/**
 * 文件服务类,包括:获取文件存放地址,文件上传、下载等等操作.实现类{@link FileServiceImpl}
 * @author 陈超 2010-7-21
 *
 */
public interface FileService {
	public static final int FILETYPE_FILE = 0;		//普通文件
	public static final int FILETYPE_FOLDER = 1;	//文件夹
	public static final int FILETYPE_ALL = 2;		//所有文件类型
	public static final int COPY = 0;
	public static final int CAT = 1;
	
	/**
	 * 创建文件夹操作
	 * @return
	 * @author 陈超 2010-7-29
	 */
	public boolean createFolder(EdocFile edocFile);
	
	/**
	 * 分页获取"我的文件夹"下面的根文件(包括文件夹和普通文件)
	 * @param creatorId 	创建人ID
	 * @param currentPage	分页查询的起始页
	 * @param pageSize		没页显示的记录数
	 * @return
	 * @author 陈超 2010-7-29
	 */
	public PageValueObject<EdocFile> getRootFileFromMyFolder(int currentPage, int pageSize, String creatorId);
	
	/**
	 * 创建新的文件夹
	 * @param edocFile 文件夹信息
	 * @return	创建成功返回true否则返回false
	 * @author 陈超 2010-7-31
	 */
	public boolean saveFolder(EdocFile edocFile);
	
	/**
	 * 上传文件操作
	 * @param edocFile	文件信息类
	 * @param src		文件输入流
	 * @return   		上传成功返回true,否则返回false
	 * @author 			陈超 2010-8-7
	 */
	public boolean uploadFile(EdocFile edocFile, FileInputStream src);

	/**
	 * 删除文件
	 * @param deleteParams 	删除参数
	 * @param creatorId 	文件创建人Id
	 * @return
	 * @author 				陈超 2010-8-10
	 */
	public void deleteFile(String[] deleteParams, String creatorId);
	
	
	/**
	 * 分页获取共享文件夹/文件
	 * @param currentPage	分页查询的起始地址
	 * @param pageSize		分页查询每页显示的记录数
	 * @return
	 * @author 				陈超 2010-8-17
	 */
	public PageValueObject<EdocFile> getRootFileFromShoreFolder(
			int currentPage, int pageSize);

	/**
	 * 根据parentId获取对应的子文件信息
	 * @param currentPage
	 * @param pageSize
	 * @param parentId
	 * @return
	 * @author 陈超 2010-11-18
	 */
	public PageValueObject<EdocFile> getSubFilesByParentId(int currentPage,
			int pageSize, String parentId);

	/**
	 * 查询文件信息
	 * @param currentPage
	 * @param pageSize
	 * @param userId
	 * @param parentId
	 * @param queryFileName
	 * @return
	 * @author 陈超 2010-11-20
	 */
	public PageValueObject<EdocFile> getMyFilesByParentId(int currentPage,
			int pageSize, String userId, String parentId, String queryFileName);

	
	/**
	 * 添加版本信息
	 * @param sourceFileId
	 * @param newFileVersion
	 * @param fileInputStream
	 * @author 	陈超 2011-01-09
	 */
	public void addNewVersionFile(String sourceFileId,
			FileVersion newFileVersion, FileInputStream fileInputStream);
	
	/**
	 * 根据原文件ID获取文件信息
	 * @param id
	 * @return
	 */
	public EdocFile getFileById(String id);

	/**
	 * 查询文件夹
	 * @param parentId
	 * @param userId 
	 * @return
	 */
	public List<EdocFile> getFoldersByParentId(String parentId, String userId);
	
	/**
	 * 撤销文件共享操作
	 * @param fileId 	文件Id
	 * @author 			陈超 
	 */
	public void cancelShore(String fileId);

	/**
	 * 获取目录结构信息
	 * @param parentId	节点ID
	 * @param showRoot	是否显示根节点
	 * @return
	 */
	public List<EdocFile> getMulus(String parentId, int showRoot,int showSelf);
	
	/**
	 * 判断当前文件夹下面该文件是否存在
	 * @param parentFileId
	 * @param fileName
	 * @param userId
	 * @return
	 */
	public boolean isExist(String parentFileId,String fileName,String userId);

	/**
	 * 获取目录结构信息(共享文件)
	 * @param parentId	节点ID
	 * @param showRoot	是否显示根节点
	 * @return
	 */
	public List<EdocFile> getShoredMulus(String parentId, int showRoot,int showSelf);

	/**
	 * 查询我的所有文件ID,不包含以删除的文件
	 * @return
	 */
	public String[] getMyFileIds(String userId,int fileType);

	/**
	 * 文件拷贝操作
	 * @param sFileIds				拷贝文件ID
	 * @param operType 				操作类型:0=拷贝,1=剪切
	 * @param destFolderFileId		目标文件夹ID
	 */
	public void copyOrCatFile(List<String> sFileIds,int operType, User user, String destFolderFileId);
	
	/**
	 * 根据文件ID集合查询对应的文件信息
	 * @param fileIds
	 * @return
	 */
	public List<EdocFile> findFiles(List<String> fileIds);

}
