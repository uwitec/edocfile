package com.edoc.utils;

import java.io.UnsupportedEncodingException;

/**
 * 用于应用程序与数据库之间字符转换
 * 
 * @author 陈超
 * 
 */
public class Chinese {
	/**
	 * 主要把从数据库中取出的字符转换为程序中可认的中文字符
	 * 
	 * @param change
	 *            需要转换的数据库字符
	 * @return String 转换后的程序中的中文字符
	 * @throws UnsupportedEncodingException
	 *             不能转换时抛出的异常
	 */

	public synchronized static String fromDatabase(String change)
			throws UnsupportedEncodingException {
		return new String(change.getBytes("iso-8859-1"), "utf-8");
		// return change ;
	}

	/**
	 * 主要把程序中可认的中文字符转换为存入数据库中的字符
	 * 
	 * @param change
	 *            需要转换的程序中的中文字符
	 * @return 转换后的数据库字符
	 * @throws UnsupportedEncodingException
	 *             不能转换时抛出的异常
	 */
	public synchronized static String toDatabase(String change)
			throws UnsupportedEncodingException {
		return new String(change.getBytes("utf-8"), "iso-8859-1");
	}

}
