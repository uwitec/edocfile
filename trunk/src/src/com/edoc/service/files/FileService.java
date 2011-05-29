package com.edoc.service.files;

import java.io.FileInputStream;
import java.util.List;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.baseinfo.User;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.FileVersion;
import com.edoc.entity.files.ShoreFile;
import com.edoc.entity.files.VisitUserInfo;

/**
 * �ļ�������,����:��ȡ�ļ���ŵ�ַ,�ļ��ϴ������صȵȲ���.ʵ����{@link FileServiceImpl}
 * @author �³� 2010-7-21
 *
 */
public interface FileService {
	
	/**
	 * �����ļ��в���
	 * @return
	 * @author �³� 2010-7-29
	 */
	public boolean createFolder(EdocFile edocFile);
	
	/**
	 * ��ҳ��ȡ"�ҵ��ļ���"����ĸ��ļ�(�����ļ��к���ͨ�ļ�)
	 * @param creatorId 	������ID
	 * @param currentPage	��ҳ��ѯ����ʼҳ
	 * @param pageSize		ûҳ��ʾ�ļ�¼��
	 * @return
	 * @author �³� 2010-7-29
	 */
	public PageValueObject<EdocFile> getRootFileFromMyFolder(int currentPage, int pageSize, String creatorId);
	
	/**
	 * �����µ��ļ���
	 * @param edocFile �ļ�����Ϣ
	 * @return	�����ɹ�����true���򷵻�false
	 * @author �³� 2010-7-31
	 */
	public boolean saveFolder(EdocFile edocFile);
	
	/**
	 * �ϴ��ļ�����
	 * @param edocFile	�ļ���Ϣ��
	 * @param src		�ļ�������
	 * @return   		�ϴ��ɹ�����true,���򷵻�false
	 * @author 			�³� 2010-8-7
	 */
	public boolean uploadFile(EdocFile edocFile, FileInputStream src);

	/**
	 * ɾ���ļ�
	 * @param deleteParams 	ɾ������
	 * @param creatorId 	�ļ�������Id
	 * @return
	 * @author 				�³� 2010-8-10
	 */
	public void deleteFile(String[] deleteParams, String creatorId);
	
	
	/**
	 * ��ҳ��ȡ�����ļ���/�ļ�
	 * @param currentPage	��ҳ��ѯ����ʼ��ַ
	 * @param pageSize		��ҳ��ѯÿҳ��ʾ�ļ�¼��
	 * @return
	 * @author 				�³� 2010-8-17
	 */
	public PageValueObject<EdocFile> getRootFileFromShoreFolder(
			int currentPage, int pageSize);

	/**
	 * �����ļ�
	 * �³� 2010-11-6
	 * @param user 
	 * @param shoreMuluFlag �Ƿ������ļ����ϼ�Ŀ¼
	 * @param shoreNowFlag 	�Ƿ���������
	 */
	public boolean shoreFile(ShoreFile shoreFile, List<VisitUserInfo> visitUserInfos, User user, int shoreMuluFlag, boolean shoreNowFlag);
	
	/**
	 * ����parentId��ȡ��Ӧ�����ļ���Ϣ
	 * @param currentPage
	 * @param pageSize
	 * @param parentId
	 * @return
	 * @author �³� 2010-11-18
	 */
	public PageValueObject<EdocFile> getSubFilesByParentId(int currentPage,
			int pageSize, String parentId);

	/**
	 * ��ѯ�ļ���Ϣ
	 * @param currentPage
	 * @param pageSize
	 * @param userId
	 * @param parentId
	 * @param queryFileName
	 * @return
	 * @author �³� 2010-11-20
	 */
	public PageValueObject<EdocFile> getMyFilesByParentId(int currentPage,
			int pageSize, String userId, String parentId, String queryFileName);

	
	/**
	 * ���Ӱ汾��Ϣ
	 * @param sourceFileId
	 * @param newFileVersion
	 * @param fileInputStream
	 * @author 	�³� 2011-01-09
	 */
	public void addNewVersionFile(String sourceFileId,
			FileVersion newFileVersion, FileInputStream fileInputStream);
	
	/**
	 * ����ԭ�ļ�ID��ȡ�ļ���Ϣ
	 * @param id
	 * @return
	 */
	public EdocFile getFileById(String id);

	/**
	 * ��ѯ�ļ���
	 * @param parentId
	 * @param userId 
	 * @return
	 */
	public List<EdocFile> getFoldersByParentId(String parentId, String userId);
	
	/**
	 * �����ļ���������
	 * @param fileId 	�ļ�Id
	 * @author 			�³� 
	 */
	public void cancelShore(String fileId);

	/**
	 * ��ȡĿ¼�ṹ��Ϣ
	 * @param parentId	�ڵ�ID
	 * @param showRoot	�Ƿ���ʾ���ڵ�
	 * @return
	 */
	public List<EdocFile> getMulus(String parentId, int showRoot,int showSelf);
	
	/**
	 * �жϵ�ǰ�ļ���������ļ��Ƿ����
	 * @param parentFileId
	 * @param fileName
	 * @param userId
	 * @return
	 */
	public boolean isExist(String parentFileId,String fileName,String userId);

	/**
	 * ��ȡĿ¼�ṹ��Ϣ(�����ļ�)
	 * @param parentId	�ڵ�ID
	 * @param showRoot	�Ƿ���ʾ���ڵ�
	 * @return
	 */
	public List<EdocFile> getShoredMulus(String parentId, int showRoot,int showSelf);

	/**
	 * ��ѯ�ļ��汾
	 * @param sourceFileId	Դ�ļ���¼ID
	 * @param version		�ļ��汾��
	 * @return
	 */
	public FileVersion getFileVersion(String sourceFileId, String version);

	
	
}