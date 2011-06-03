package com.edoc.service.files;

import java.util.List;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.baseinfo.User;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.ShoreFile;
import com.edoc.entity.files.VisitUserInfo;

/**
 * �����ļ�������,ʵ����{@link ShoreFileServiceImpl}
 * @author �³� 2010-11-16
 *
 */
public interface ShoreFileService {
	
	/**
	 * ���������ļ���Ϣ
	 * @param shoreFile	��������ļ���Ϣ
	 * @param mulus		�������ļ����ϼ�Ŀ¼
	 * @param user
	 * @param userHome �Ƿ�����ļ����ϼ�Ŀ¼
	 */
	public void insertShoreFile(ShoreFile shoreFile, List<EdocFile> mulus, User user, ShoreFile userHome);

	/**
	 * ɾ��������ļ���Ϣ
	 * @param fileId
	 * @author 			�³�
	 */
	public void deleteShoreFileBySourceFileId(String[] sourceFileIds);
	
	/**
	 * ��ȡ�����ļ�����Ϣ
	 * @param parentId	�ϼ��˵�Id
	 * @param userId	�û�Id
	 * @return
	 */
	public List<ShoreFile> getShoredFoldersByParentId(String parentId, String userId);

	/**
	 * ��ȡ�����ļ���Ϣ
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
	 * �жϼ�¼�Ƿ����
	 * @param id
	 * @return
	 */
	public boolean isExist(String id);

	/**
	 * ����Դ�ļ�Id��ȡ���ڵĹ����ļ���Ϣ
	 * @param sourceFileId
	 * @return
	 */
	public ShoreFile getShoreFileBySourceFileId(String sourceFileId);
	
	
	/**
	 * �����ļ���,���ļ��л�ֱ����"�����ļ���"����ʾ(�����û����ܲ鿴��,���ļ����е��ļ�����Ȩ�����Ƶ�)
	 * �ڴ����ļ��е�ʱ����� sys_fileinfo ���ű������һ����¼,��ʾ���ɸ��û�������,����c_parentid = '-1'
	 * ��ʾ����"�ҵ��ļ���"����ʾ
	 * 
	 * @param shoreFile		�����ļ���Ϣ
	 * @author 				�³� 2011-06-01
	 */
	public void createFolder(EdocFile edocFile,String parentId);

	/**
	 * �����ļ��������ڹ�����ļ���ͬʱҪ�����ļ����ϲ��ļ������ó��ѹ���(���ļ�������������ļ������������)
	 * ������"�����ļ���"�¼���Ƿ����һ������Ϊ: ����(��¼��) ���ļ���,����������򴴽�,Ȼ�󽫹����ļ���
	 * parentId ���óɸ��ļ��е�ID
	 * 
	 * @param shoreFile			�����ļ�����Ϣ
	 * @param visitUserInfos	�����û�����Ϣ
	 * @param user				������ļ����û���Ϣ
	 * @param shoreNowFlag		�Ƿ���������
	 * @author �³� 2011-06-02
	 */
	public boolean shoreFile(ShoreFile shoreFile,
			List<VisitUserInfo> visitUserInfos, User user, boolean shoreNowFlag);
}
