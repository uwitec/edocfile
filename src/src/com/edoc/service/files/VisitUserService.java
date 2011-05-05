package com.edoc.service.files;

import java.util.List;

import com.edoc.entity.files.VisitUserInfo;
/**
 * 共享访问用户服务接口 {@link VisitUserServiceImpl}
 * @author dell
 *
 */
public interface VisitUserService {
	
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
}
