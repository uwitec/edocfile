package com.edoc.service.files;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.files.RecycleInfo;

/**
 * ����վ����ӿ�{@link RecycleServiceImpl}
 * @author �³� 2011-4-11
 *
 */
public interface RecycleService {

	/**
	 * ��ȡ����վ�б�
	 * @param currentPage
	 * @param pageSize
	 * @param userId
	 * @param fileName
	 * @return
	 */
	PageValueObject<RecycleInfo> getRecycleList(int currentPage, int pageSize,
			String userId, String fileName);

	
	/**
	 * ��ԭ����վ������ļ�
	 * @param fileIds
	 */
	void revert(String[] fileIds);


	/**
	 * ��ջ���վ,��ջ���վ��ͬʱҪɾ���ļ�����Ϣ�������ļ���¼���汾��Ϣ��������Ϣ�ȵ�
	 */
	void clearAll();


	/**
	 * ɾ������վ�е��ļ�,ɾ���ļ���ͬʱҪɾ���ļ�����Ϣ�������ļ���¼���汾��Ϣ��������Ϣ�ȵ�
	 * @param fileIds
	 */
	void delete(String[] fileIds);

}
