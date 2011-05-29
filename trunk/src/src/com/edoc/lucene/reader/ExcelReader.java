package com.edoc.lucene.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel文件内容抽取
 * @author 陈超 2011-05-29
 */
public class ExcelReader {

	public static String getContent(File uExcelFile) {
		String text = "";
		try {
			Workbook wb = null;
			if (uExcelFile.getName().toLowerCase().endsWith(".xls")) {// 2003

				FileInputStream is = new FileInputStream(uExcelFile);
				POIFSFileSystem fs = new POIFSFileSystem(is);
				wb = new HSSFWorkbook(fs);
				is.close();
			} else {// 2007
				wb = new XSSFWorkbook(uExcelFile.getPath());
			}
			text = read(wb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}

	@SuppressWarnings("deprecation")
	private static String read(Workbook workbook) {
		StringBuffer content = new StringBuffer("");
		try {
			for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++) {
				if (null != workbook.getSheetAt(numSheets)) {
					HSSFSheet aSheet = (HSSFSheet) workbook.getSheetAt(numSheets);// 获得一个sheet
					for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
						if (null != aSheet.getRow(rowNumOfSheet)) {
							HSSFRow aRow = aSheet.getRow(rowNumOfSheet); // 获得一个行
							for (short cellNumOfRow = 0; cellNumOfRow <= aRow.getLastCellNum(); cellNumOfRow++) {
								if (null != aRow.getCell(cellNumOfRow)) {
									HSSFCell aCell = aRow.getCell(cellNumOfRow);// 获得列值
									content.append(aCell.getStringCellValue());
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return content.toString();
	}

	public static void main(String[] args) {

		File file = new File("G:\\1.xls");

		System.out.println(ExcelReader.getContent(file));
	}

}