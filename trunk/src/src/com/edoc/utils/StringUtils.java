package com.edoc.utils;

public class StringUtils {

	public synchronized static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}

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
	public synchronized static String cutString(String str, int startIndex,
			int length) throws Exception {
		char temp[] = str.toCharArray(); // ת��Ϊchar���͵���ֵ
		return new String(temp, startIndex, length);
	}
	
	/**
	 * �ж��ַ����Ƿ���Ч,���ַ�����Ϊnullͬʱ��Ϊ��
	 * @param str
	 * @return ��Ч����true���򷵻�false
	 * @author �³� 2010-7-30
	 */
	public synchronized static boolean isValid(String str){
		if(str!=null && str.trim().length()>0){
			return true;
		}
		return false;
	}
}
