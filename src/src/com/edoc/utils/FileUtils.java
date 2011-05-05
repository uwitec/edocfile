package com.edoc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



/**
 * 文件处理工具类
 * 
 * @author 陈超
 * 
 */
public class FileUtils {
	/**
	 * 将文件写到目标目录
	 * 
	 * @param file
	 *            文件
	 * @param destDir
	 *            目标目录
	 * @param filename
	 *            文件名称
	 */
	public synchronized static void outPut(File file, String destDir,
			String filename) {
		InputStream is = null;
		OutputStream os = null;
		try {
			// 生成输入流
			is = new FileInputStream(file);
			// 创建目标文件
			File destFile = new File(destDir, filename);
			// 生成输出流
			os = new FileOutputStream(destFile);

			byte[] buffer = new byte[1000];
			int length = 0;

			// 将输入流中的字节写到输出流中
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}

			// 关闭输入、输出流
			is.close();
			os.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized static String getFileType(File file) {
		if (file != null && file.isFile()) {
			String fileName = file.getName();
			int index = fileName.indexOf(".");
			if (index > 0) {
				return fileName.substring(index + 1, fileName.length());
			}
		}
		return null;
	}

	/**
	 * 判断是否是图片文件
	 * 
	 * @param fileSuffix
	 *            文件后缀名称
	 * @return
	 */
	public synchronized static boolean isPic(String fileSuffix) {
		if (StringUtils.isValid(fileSuffix)) {
			fileSuffix = fileSuffix.toLowerCase();
			if (fileSuffix.equals("gif") || fileSuffix.equals("bpm")
					|| fileSuffix.equals("jpg") || fileSuffix.equals("png")
					|| fileSuffix.equals("jpeg") || fileSuffix.equals("ico")
					|| fileSuffix.equals("tif") || fileSuffix.equals("tiff")) {
				return true;
			}
		}
		return false;
	}
	
	public synchronized static boolean isMedia(String fileSuffix) {
		if (StringUtils.isValid(fileSuffix)) {
			fileSuffix = fileSuffix.toLowerCase();
			if (fileSuffix.equals("asf") || fileSuffix.equals("wma")
					|| fileSuffix.equals("wmv") || fileSuffix.equals("cd")
					|| fileSuffix.equals("avi") || fileSuffix.equals("ico")
					|| fileSuffix.equals("wav") || fileSuffix.equals("mpeg")
					|| fileSuffix.equals("mp3") || fileSuffix.equals("midi")
					|| fileSuffix.equals("aiff") || fileSuffix.equals("au")
					|| fileSuffix.equals("rm")) {
				return true;
			}
		}
		return false;
	} 
	
//	public synchronized static boolean isMedia(String fileSuffix){
//		
//	} 

//	/**
//	 * 拷贝文件
//	 * 
//	 * @param sourceDir
//	 * @param targetDir
//	 * @throws IOException
//	 */
//	public synchronized static void copyFile(String sourceFile,
//			String targetFile, String fileSuffix) throws IOException {
//		// 判断参数是否有效
//		if (StringUtils.isValid(sourceFile) && StringUtils.isValid(targetFile)
//				&& StringUtils.isValid(fileSuffix)) {
//			if (isPic(fileSuffix)) { // 如果是图片文件
//				copyPic(sourceFile, targetFile);
//			} else {
//
//			}
//		}
//	}
//
//	private static void copyPic(String sourceFile, String targetFile)
//			throws IOException {
//		File file = new File(sourceFile);
//		Image image = ImageIO.read(file);
//		int width = image.getWidth(null);
//		int height = image.getHeight(null);
//
//		BufferedImage imageTag = new BufferedImage(width, height,
//				BufferedImage.TYPE_INT_RGB);
//		imageTag.getGraphics().drawImage(image, 0, 0, width, height, null);
//		FileOutputStream out = new FileOutputStream(targetFile);
//		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//		encoder.encode(imageTag);
//		out.close();
//	}

//	private static void copyOfficeFile(String sourceDir, String targetDir) {
//		// 输入流
//		InputStream in = new FileInputStream(sourceDir);
//
//		String sourceFilePath = ConfigResource.getConfig(ConfigResource.EDOCUPLOADDIR)
//				+ "\\" + fileVersion.getNewFileName();
//		// 输出流
//		OutputStream out = new FileOutputStream(sourceFilePath, true);
//
//		byte[] buffer = new byte[1024];
//		while (true) {
//			int byteRead = in.read(buffer);
//			if (byteRead == -1)
//				break;
//			out.write(buffer, 0, byteRead);
//		}
//		out.close();
//		in.close();
//	}
}
