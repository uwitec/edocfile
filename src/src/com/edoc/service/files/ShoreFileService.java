package com.edoc.service.files;

import java.util.List;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.baseinfo.User;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.ShoreFile;

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
	 * @param shoreMuluFlag �Ƿ�����ļ����ϼ�Ŀ¼
	 */
	public void insertShoreFile(ShoreFile shoreFile, List<EdocFile> mulus, User user, int shoreMuluFlag);

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
	public List<ShoreFile> getShoredFileByParentId(String parentId, String userId);

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
}
