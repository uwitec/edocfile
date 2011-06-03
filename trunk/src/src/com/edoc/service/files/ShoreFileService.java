package com.edoc.service.files;

import java.util.List;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.baseinfo.User;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.ShoreFile;
import com.edoc.entity.files.VisitUserInfo;

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
	 * @param userHome 是否共享该文件的上级目录
	 */
	public void insertShoreFile(ShoreFile shoreFile, List<EdocFile> mulus, User user, ShoreFile userHome);

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
	public List<ShoreFile> getShoredFoldersByParentId(String parentId, String userId);

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
	
	
	/**
	 * 创建文件夹,改文件夹会直接在"共享文件夹"中显示(其他用户都能查看到,但文件夹中的文件是受权限限制的)
	 * 在创建文件夹的时候会在 sys_fileinfo 这张表中添加一条记录,表示是由该用户创建的,其中c_parentid = '-1'
	 * 表示不再"我的文件夹"中显示
	 * 
	 * @param shoreFile		共享文件信息
	 * @author 				陈超 2011-06-01
	 */
	public void createFolder(EdocFile edocFile,String parentId);

	/**
	 * 共享文件操作。在共享该文件的同时要将该文件的上层文件夹设置成已共享(但文件夹下面的其他文件不做共享操作)
	 * 首先在"共享文件夹"下检查是否存在一个名称为: 姓名(登录名) 的文件夹,如果不存在则创建,然后将共享文件的
	 * parentId 设置成该文件夹的ID
	 * 
	 * @param shoreFile			共享文件夹信息
	 * @param visitUserInfos	共享用户的信息
	 * @param user				共享该文件的用户信息
	 * @param shoreNowFlag		是否立即共享
	 * @author 陈超 2011-06-02
	 */
	public boolean shoreFile(ShoreFile shoreFile,
			List<VisitUserInfo> visitUserInfos, User user, boolean shoreNowFlag);
}
