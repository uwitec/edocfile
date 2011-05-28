package com.edoc.service.files;

import java.util.List;

import com.edoc.entity.files.VisitUserInfo;
/**
 * 共享访问用户服务接口 {@link VisitUserServiceImpl}
 * @author dell
 *
 */
public interface VisitUserService {
	
	public  static final String PERTYPE_VIEW = "view";
	public  static final String PERTYPE_DOWNLOAD = "download"; 
	public  static final String PERTYPE_EDIT = "edit";
	/**
	 * 插入访问共享文件的用户信息
	 * @param visitUserInfos
	 */
	public void insertVisitUserInfo(List<VisitUserInfo> visitUserInfos);
	
	/**
	 * 删除访问用户的信息
	 * @param sourceFileIds
	 */
	public void deleteVisitUserBySourceFileId(String[] sourceFileIds);

	/**
	 * 获取允许访问该文件的用户信息
	 * @param sourceFileId		文件ID
	 * @return
	 */
	public List<VisitUserInfo> getVisitUsers(String sourceFileId);

	/**
	 * 删除共享用户信息
	 * @param id
	 */
	public void deleteVisitUserInfo(String id);

	/**
	 * 验证用户对文件的操作权限
	 * @param currentUserId
	 * @param sourceFileId
	 * @param perType
	 * @return
	 */
	public boolean checkPermission(String currentUserId, String sourceFileId,
			String perType);
}
