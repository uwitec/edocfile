package com.edoc.lucene.reader;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hwpf.extractor.WordExtractor;

/**
 * 文件读取类
 * @author 陈超
 *
 */
public class DocReader {
	public static String getContent(File uWordFile){
		String text = "";
	    try {
	      FileInputStream in = new FileInputStream(uWordFile);
	      if (uWordFile.getName().toLowerCase().endsWith(".doc")) {
	        WordExtractor extractor = null; // 创建WordExtractor
	        extractor = new WordExtractor(in);// 对DOC文件进行提取
	        text = extractor.getText();
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return text;
	}
}
