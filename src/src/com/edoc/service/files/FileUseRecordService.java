package com.edoc.service.files;

import java.util.Date;
import java.util.List;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.baseinfo.User;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.FileUseRecord;

/**
 * �ļ�ʹ�ü�¼�ӿ�
 * @author �³�
 *
 */
public interface FileUseRecordService {
	public static int USETYPE_ALL = -1;
	public static int USETYPE_VIEW = 0;			//Ԥ��
	public static int USETYPE_EDIT = 1;			//�༭
	public static int USETYPE_DOWNLOAD = 2;		//����
	public static int USETYPE_BORROW = 3;		//����
	public static int USETYPE_COPE = 4;			//����
	public static int USETYPE_CAT = 5;			//����
	
	/**
	 * ����ļ�ʹ�ü�¼
	 * @param fileUseRecord
	 */
	public void addFileUseRecord(FileUseRecord fileUseRecord);
	
	/**
	 * ����ļ�ʹ�ü�¼
	 * @param user			ʹ���û���Ϣ
	 * @param edocFile		�ļ���Ϣ
	 * @param useType		ʹ������
	 */
	public void addFileUseRecord(User user, EdocFile edocFile,int useType);
	
	
	/**
	 * �����û�ID��ҳ��ѯ���û����ļ�ʹ�ü�¼��Ϣ
	 * @param userId		�û�ID
	 * @param type			ʹ������
	 * @param startTime		ʱ���������ʼʱ��
	 * @param endTime       ʱ���������ֹʱ��
	 * @param currentPage	��ҳ��ѯ��������ǰҳ��
	 * @param pageSize		��ҳ��ѯ������ÿҳ��¼��
	 * @return
	 */
	public PageValueObject<FileUseRecord> getFileUseRecords(String userId,int type,Date startTime,Date endTime, int currentPage, int pageSize);
	
	/**
	 * ��ȡ�ļ�ʹ�ü�¼��Ϣ
	 * @param type			ʹ������
	 * @param startTime		ʱ���������ʼʱ��
	 * @param endTime       ʱ���������ֹʱ��
	 * @param currentPage	��ҳ��ѯ��������ǰҳ��
	 * @param pageSize		��ҳ��ѯ������ÿҳ��¼��
	 * @return
	 */
	public PageValueObject<FileUseRecord> getFileUseRecords(int type,Date startTime,Date endTime, int currentPage, int pageSize);
	
	/**
	 * �������ʹ�õ��ļ���¼��Ϣ(ȡǰnum����¼)
	 * @param userId		�û�ID
	 * @param type			ʹ������
	 * @param num			ǰnum����¼,��� num=-1 ��ʾ��ȥ���еļ�¼
	 * @return
	 */
	public List<FileUseRecord> getRecentUseFileRecord(String userId,int type, int num);
	
}
