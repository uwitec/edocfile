package com.edoc.service.files;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.files.RecycleInfo;

/**
 * 回收站服务接口
 * @author 陈超 2011-4-11
 *
 */
public interface RecycleService {

	/**
	 * 获取回收站列表
	 * @param currentPage
	 * @param pageSize
	 * @param userId
	 * @param fileName
	 * @return
	 */
	PageValueObject<RecycleInfo> getRecycleList(int currentPage, int pageSize,
			String userId, String fileName);

	
	/**
	 * 还原回收站里面的文件
	 * @param fileIds
	 */
	void revert(String[] fileIds);


	/**
	 * 清空回收站
	 */
	void clearAll();


	/**
	 * 删除回收站中的文件
	 * @param fileIds
	 */
	void delete(String[] fileIds);

}
