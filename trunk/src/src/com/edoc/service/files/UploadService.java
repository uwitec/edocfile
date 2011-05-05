package com.edoc.service.files;

import java.io.InputStream;

/**
 * 文件上传服务类,实现类{@link UploadServiceImpl}
 * @author 陈超 2010-8-7
 */
public interface UploadService {
	
	public boolean uploadFile(String fileName, InputStream in);
}
