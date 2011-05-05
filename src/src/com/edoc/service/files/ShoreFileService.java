package com.edoc.service.files;

import java.util.List;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.baseinfo.User;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.ShoreFile;

/**
 * 共享文件服务类,实现类{@link ShoreFileServiceImpl}
 * @author 陈超 2010-11-16
 *
 */
public interface ShoreFileService {
	
	/**
	 * 新增共享文件信息
	 * @param shoreFile	被共享的文件信息
	 * @param mulus		被共享文件的上级目录
	 * @param user
	 * @param shoreMuluFlag 是否共享该文件的上级目录
	 */
	public void insertShoreFile(ShoreFile shoreFile, List<EdocFile> mulus, User user, int shoreMuluFlag);

	/**
	 * 删除共享的文件信息
	 * @param fileId
	 * @author 			陈超
	 */
	public void deleteShoreFileBySourceFileId(String[] sourceFileIds);
	
	/**
	 * 获取共享文件夹信息
	 * @param parentId	上级菜单Id
	 * @param userId	用户Id
	 * @return
	 */
	public List<ShoreFile> getShoredFileByParentId(String parentId, String userId);

	/**
	 * 获取共享文件信息
	 * @param currentPage
	 * @param pageSize
	 * @param id
	 * @param parentId
	 * @param queryFileName
	 * @return
	 */
	public PageValueObject<ShoreFile> getShoredFiles(int currentPage,
			int pageSize, String userId, String parentId, String queryFileName);
	
	/**
	 * 判断记录是否存在
	 * @param id
	 * @return
	 */
	public boolean isExist(String id);

	/**
	 * 根据源文件Id获取对于的共享文件信息
	 * @param sourceFileId
	 * @return
	 */
	public ShoreFile getShoreFileBySourceFileId(String sourceFileId);
}
