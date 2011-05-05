package com.edoc.service.files;

import java.util.List;

import com.edoc.entity.files.VisitUserInfo;
/**
 * ��������û�����ӿ� {@link VisitUserServiceImpl}
 * @author dell
 *
 */
public interface VisitUserService {
	
	/**
	 * ������ʹ����ļ����û���Ϣ
	 * @param visitUserInfos
	 */
	public void insertVisitUserInfo(List<VisitUserInfo> visitUserInfos);
	
	/**
	 * ɾ�������û�����Ϣ
	 * @param sourceFileIds
	 */
	public void deleteVisitUserBySourceFileId(String[] sourceFileIds);

	/**
	 * ��ȡ������ʸ��ļ����û���Ϣ
	 * @param sourceFileId		�ļ�ID
	 * @return
	 */
	public List<VisitUserInfo> getVisitUsers(String sourceFileId);

	/**
	 * ɾ�������û���Ϣ
	 * @param id
	 */
	public void deleteVisitUserInfo(String id);
}
