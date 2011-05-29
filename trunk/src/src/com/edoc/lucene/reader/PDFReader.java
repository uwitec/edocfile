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
 * pde�ļ����ݳ�ȡ
 * @author �³� 2011-05-29
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

//			// �ж�pdf��ȡ�Ƿ�ɹ�
//			boolean isSuccess = true;
//
//			String testStr = contents.replaceAll("\\r\\n", "");
//			testStr = testStr.replaceAll("\\.", "");
//			if (testStr.length() < 100) {
//				isSuccess = false;
//			}
//
//			 ���δ�ɹ���pdfת����tif�ļ�����ת����txt�ļ�
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
		File file = new File("G:\\DLP����.pdf");
		
		System.out.println(PDFReader.getContent(file));
	}

}