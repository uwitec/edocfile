package com.edoc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	/**
	 * ��ȡ�ַ������ڲ�ͬ�����ʽ���Լ���Ӣ��
	 * 
	 * @param str
	 *            Ҫ��ȡ���ַ���
	 * @param startIndex
	 *            ��ȡ�Ŀ�ʼλ��(��0��ʼ)
	 * @param length
	 *            ��ȡ�ĳ���
	 * 
	 * @return String ��ȡ������ַ���
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
