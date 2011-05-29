package com.edoc.lucene.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * pde文件内容抽取
 * @author 陈超 2011-05-29
 */
public class PDFReader {

	public static String getContent(File uPdfFile) {
		PDDocument pdfDocument = null;
		String contents = "";
		try {
			FileInputStream in = new FileInputStream(uPdfFile);
			pdfDocument = PDDocument.load(in);
			if (pdfDocument.isEncrypted()) {
				pdfDocument.decrypt("");
			}
			StringWriter writer = new StringWriter();
			PDFTextStripper stripper = new PDFTextStripper();
			stripper.writeText(pdfDocument, writer);
			contents = writer.getBuffer().toString();
			in.close();

//			// 判断pdf抽取是否成功
//			boolean isSuccess = true;
//
//			String testStr = contents.replaceAll("\\r\\n", "");
//			testStr = testStr.replaceAll("\\.", "");
//			if (testStr.length() < 100) {
//				isSuccess = false;
//			}
//
//			 如果未成功则将pdf转换成tif文件，再转换成txt文件
//			 if (!isSuccess) {
//			 contents = makeTif(uPdfFile);
//			 }
		} catch (CryptographyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidPasswordException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
//			contents = makeTif(uPdfFile);
		} finally {
			if (pdfDocument != null) {
				try {
					pdfDocument.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return contents;
	}

	public static void main(String[] args) {
		File file = new File("G:\\DLP介绍.pdf");
		
		System.out.println(PDFReader.getContent(file));
	}

}