package com.edoc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
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
	public synchronized static Date convertToDate(String dateStr, String format) {
		if(!StringUtils.isValid(format)){
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format); 
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
