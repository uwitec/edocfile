package com.edoc.service.files.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edoc.service.files.UploadService;
import com.edoc.utils.ConfigResource;

/**
 * UploadService的默认实现类型,上传的文件均保存在本地硬盘上传
 * 
 * @author 陈超 2010-8-7
 */

@Component("localUploadServie")
@Transactional(readOnly = true)
public class LocalUploadServiceImpl implements UploadService {
	private static final int BUFFER_SIZE = 8*1024;
	public boolean uploadFile(String fileName, InputStream in) {
		try {
			BufferedInputStream bufin = null;
			BufferedOutputStream bufout = null;
			try {
				File desc = new File(ConfigResource.getConfig(ConfigResource.EDOCUPLOADDIR)+"\\"+fileName);
				bufin = new BufferedInputStream(in,BUFFER_SIZE);
				bufout = new BufferedOutputStream(new FileOutputStream(desc),BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					bufout.write(buffer);
				}
			} finally {
				if (null != bufin) {
					bufin.close();
				}
				if (null != bufout) {
					bufout.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}