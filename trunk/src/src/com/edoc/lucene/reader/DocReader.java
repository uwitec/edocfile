package com.edoc.lucene.reader;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

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
	      }else {
	          OPCPackage opcPackage = POIXMLDocument.openPackage(uWordFile.getAbsolutePath());
	          POIXMLTextExtractor ex = new XWPFWordExtractor(opcPackage);
	          text = ex.getText();

	        }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return text;
	}
}
