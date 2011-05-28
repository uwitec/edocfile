package com.edoc.service.files;

import java.util.List;

import com.edoc.entity.files.VisitUserInfo;
/**
 * ��������û�����ӿ� {@link VisitUserServiceImpl}
 * @author dell
 *
 */
public interface VisitUserService {
	
	public  static final String PERTYPE_VIEW = "view";
	public  static final String PERTYPE_DOWNLOAD = "download"; 
	public  static final String PERTYPE_EDIT = "edit";
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

	/**
	 * ��֤�û����ļ��Ĳ���Ȩ��
	 * @param currentUserId
	 * @param sourceFileId
	 * @param perType
	 * @return
	 */
	public boolean checkPermission(String currentUserId, String sourceFileId,
			String perType);
}
