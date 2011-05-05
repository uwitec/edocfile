package com.edoc.utils;

public class StringUtils {

	public synchronized static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}

	/**
	 * 截取字符串，在不同编码格式下以及中英文
	 * 
	 * @param str
	 *            要截取的字符串
	 * @param startIndex
	 *            截取的开始位置(从0开始)
	 * @param length
	 *            截取的长度
	 * 
	 * @return String 截取过后的字符串
	 */
	public synchronized static String cutString(String str, int startIndex,
			int length) throws Exception {
		char temp[] = str.toCharArray(); // 转化为char类型的数值
		return new String(temp, startIndex, length);
	}
	
	/**
	 * 判断字符串是否有效,即字符串不为null同时不为空
	 * @param str
	 * @return 有效返回true否则返回false
	 * @author 陈超 2010-7-30
	 */
	public synchronized static boolean isValid(String str){
		if(str!=null && str.trim().length()>0){
			return true;
		}
		return false;
	}
}
