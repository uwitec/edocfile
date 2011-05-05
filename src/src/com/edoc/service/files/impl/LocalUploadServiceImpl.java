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
 * UploadService��Ĭ��ʵ������,�ϴ����ļ��������ڱ���Ӳ���ϴ�
 * 
 * @author �³� 2010-8-7
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