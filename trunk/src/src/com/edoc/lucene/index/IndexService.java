package com.edoc.lucene.index;

import java.io.File;

import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.FileVersion;

/**
 * ����������
 * @author �³� 2010-8-28
 *
 */
public interface IndexService {
	/**
	 * �Ե����ļ���������
	 * @param file		�����ļ�
	 * @param edocFile	�ļ���Ϣ
	 * @return
	 */
	public boolean addIndex(String sourceFileName, File file, EdocFile edocFile);
	
	/**
	 * �Ե����ļ���������
	 * @param file		�����ļ�
	 * @param edocFile	�ļ���Ϣ
	 * @return
	 */
	public boolean addIndex(String sourceFileName, File file, FileVersion fileVersion);
}
