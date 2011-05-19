package com.edoc.lucene.reader;

import java.io.File;

import com.edoc.utils.FileUtils;

public class FileReader {
	public static final String TXT = "txt";
	public static final String WORD = "doc";
	
	public synchronized static String getContent(File file){
		String content = "";
		if(file!=null){
			String type = FileUtils.getFileType(file);
			if(type.toLowerCase().equals(TXT)){
				content = TXTReader.getContent(file);
			}else if(type.toLowerCase().equals(WORD)){
				content = DocReader.getContent(file);
			}
		}
		return content;
	}
}
