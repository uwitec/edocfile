package com.edoc.lucene.reader;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

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
