package com.edoc.service.files;

import java.io.InputStream;

/**
 * �ļ��ϴ�������,ʵ����{@link UploadServiceImpl}
 * @author �³� 2010-8-7
 */
public interface UploadService {
	
	public boolean uploadFile(String fileName, InputStream in);
}
