package com.edoc.service.files;

import java.io.FileInputStream;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.files.FileVersion;

/**
 * �ļ��汾����
 * @author �³� ${@link FileVersionServiceImpl }
 *
 */
public interface FileVersionService {

	/**
	 * ��ҳ��ȡ�ļ��汾��Ϣ
	 * @param currentPage		��ǰҳ��
	 * @param pageSize			ÿҳ��ʾ�ļ�¼��
	 * @param sourceFileId		ԭʼ�ļ���ID
	 * @return
	 */
	PageValueObject<FileVersion> getEdocFileVersions(int currentPage,
			int pageSize, String sourceFileId);

	/**
	 * ������Ӱ汾��Ϣ
	 * @param fileVersion
	 * @param fileInputStream
	 */
	public void addFileVersionFromOnline(FileVersion fileVersion,
			FileInputStream fileInputStream);
	
	
	/**
	 * �����ļ��汾��Ϣ
	 * @param sFileId		ԭʼ�ļ���Ϣ
	 * @param version		�ļ��汾��
	 */
	public FileVersion findFileVersion(String sFileId,String version);

}
