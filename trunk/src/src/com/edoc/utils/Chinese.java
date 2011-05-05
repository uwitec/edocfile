package com.edoc.utils;

import java.io.UnsupportedEncodingException;

/**
 * ����Ӧ�ó��������ݿ�֮���ַ�ת��
 * 
 * @author �³�
 * 
 */
public class Chinese {
	/**
	 * ��Ҫ�Ѵ����ݿ���ȡ�����ַ�ת��Ϊ�����п��ϵ������ַ�
	 * 
	 * @param change
	 *            ��Ҫת�������ݿ��ַ�
	 * @return String ת����ĳ����е������ַ�
	 * @throws UnsupportedEncodingException
	 *             ����ת��ʱ�׳����쳣
	 */

	public synchronized static String fromDatabase(String change)
			throws UnsupportedEncodingException {
		return new String(change.getBytes("iso-8859-1"), "utf-8");
		// return change ;
	}

	/**
	 * ��Ҫ�ѳ����п��ϵ������ַ�ת��Ϊ�������ݿ��е��ַ�
	 * 
	 * @param change
	 *            ��Ҫת���ĳ����е������ַ�
	 * @return ת��������ݿ��ַ�
	 * @throws UnsupportedEncodingException
	 *             ����ת��ʱ�׳����쳣
	 */
	public synchronized static String toDatabase(String change)
			throws UnsupportedEncodingException {
		return new String(change.getBytes("utf-8"), "iso-8859-1");
	}

}
