package com.edoc.scheduler.job;

import java.io.File;
import org.springframework.stereotype.Component;

import com.edoc.utils.StringUtils;

/**
 * �����ʱ�ļ��е�����
 * 
 * @author dell
 * 
 */
@Component("clearTempFileJob")
public class ClearTempFileJob {

	public void execute() {
		System.out.println("#### ��ʼִ����ʱ�ļ��й������� ####");
		String tempFileDir = getTempFileDir();
		if (StringUtils.isValid(tempFileDir)) {
			File dir = new File(tempFileDir);
			if (dir.exists() && dir.isDirectory()) { // �ж�·��ָ�����ļ��Ƿ������ΪĿ¼

				// ɾ����Ŀ¼�µ������ļ�
				File[] files = dir.listFiles();
				if (files != null && files.length > 0) {
					for (File f : files) {
						f.delete();
					}
				}
			} else {
				System.out.println("#### ��ʱ�ļ��й�������ָ����·�������ڻ��߲����ļ���! ####");
			}
		} else {
			System.out.println("#### ��ʱ�ļ��й�������û��ָ���ļ��е�·��! ####");
		}
	}

	/**
	 * ��ȡ��ʱ�ļ��е�Ŀ¼��ַ
	 * @return
	 */
	private String getTempFileDir() {
		String tempFileDir = "";
		try {
			tempFileDir = this.getClass().getClassLoader().getResource("/").toURI().getPath();
			tempFileDir = tempFileDir.substring(1, tempFileDir.length());
			tempFileDir = tempFileDir.substring(0, tempFileDir.indexOf("WEB-INF/"))+"temp/";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempFileDir;
	}
}
