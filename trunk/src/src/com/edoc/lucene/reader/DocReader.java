package com.edoc.lucene.reader;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hwpf.extractor.WordExtractor;

/**
 * �ļ���ȡ��
 * @author �³�
 *
 */
public class DocReader {
	public static String getContent(File uWordFile){
		String text = "";
	    try {
	      FileInputStream in = new FileInputStream(uWordFile);
	      if (uWordFile.getName().toLowerCase().endsWith(".doc")) {
	        WordExtractor extractor = null; // ����WordExtractor
	        extractor = new WordExtractor(in);// ��DOC�ļ�������ȡ
	        text = extractor.getText();
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return text;
	}
}
