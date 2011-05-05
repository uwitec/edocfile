package com.edoc.lucene.reader;

import java.io.File;

import com.edoc.utils.FileUtils;

/**
 * 文件读取类
 * @author 陈超
 *
 */
public class DocReader {
	private static final String TYPE_TXT = "txt";
	public synchronized static String getDocContent(File file){
		String content = "";
		if(file!=null){
			String type = FileUtils.getFileType(file);
			if(type.toLowerCase().equals(TYPE_TXT)){
				content = TXTReader.getContent(file);
			}
		}
		return content;
	}
}
