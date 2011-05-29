package com.edoc.lucene.reader;

import java.io.File;

import com.edoc.utils.FileUtils;

public class FileReader {
	public static final String TXT = "txt";
	public static final String WORD = "doc";
	public static final String WORD_2007 = "docx";
	public static final String PDF = "pdf";
	public static final String EXCEL = "xls";
	public static final String EXCEL_2007 = "xlsx";
	
	public synchronized static String getContent(File file){
		String content = "";
		if(file!=null){
			String type = FileUtils.getFileType(file);
			if(type.toLowerCase().equals(TXT)){
				content = TXTReader.getContent(file);
			}else if(type.toLowerCase().equals(WORD) || type.toLowerCase().equals(WORD_2007)){
				content = DocReader.getContent(file);
			}else if(type.toLowerCase().equals(EXCEL) || type.toLowerCase().equals(EXCEL_2007)){
				content = ExcelReader.getContent(file);
			}else if(type.toLowerCase().equals(PDF)){
				content = PDFReader.getContent(file);
			}
		}
		return content;
	}
}
