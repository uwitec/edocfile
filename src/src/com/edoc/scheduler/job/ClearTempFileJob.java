package com.edoc.scheduler.job;

import java.io.File;
import org.springframework.stereotype.Component;

import com.edoc.utils.StringUtils;

/**
 * 清空临时文件夹的任务
 * 
 * @author dell
 * 
 */
@Component("clearTempFileJob")
public class ClearTempFileJob {

	public void execute() {
		System.out.println("#### 开始执行临时文件夹管理任务 ####");
		String tempFileDir = getTempFileDir();
		if (StringUtils.isValid(tempFileDir)) {
			File dir = new File(tempFileDir);
			if (dir.exists() && dir.isDirectory()) { // 判断路径指定的文件是否存在且为目录

				// 删除该目录下的所有文件
				File[] files = dir.listFiles();
				if (files != null && files.length > 0) {
					for (File f : files) {
						f.delete();
					}
				}
			} else {
				System.out.println("#### 临时文件夹管理任务：指定的路径不存在或者不是文件夹! ####");
			}
		} else {
			System.out.println("#### 临时文件夹管理任务：没有指定文件夹的路径! ####");
		}
	}

	/**
	 * 获取临时文件夹的目录地址
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
