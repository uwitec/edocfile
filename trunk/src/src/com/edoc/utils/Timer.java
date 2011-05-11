package com.edoc.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ��ȡ��ǰ�����ڸ�ʽΪ:yyyy-MM-dd HH:mm:ss
 * 
 * @author �³�
 * 
 */
public class Timer {
	/**
	 * ��ȡ��ǰ������
	 * 
	 * @return ��ǰ������
	 */
	public synchronized static Date getCurrentCalendar() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	public synchronized static String getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		sb.append(calendar.get(Calendar.YEAR)).append("-");
		sb.append(calendar.get(Calendar.MONTH) + 1).append("-");
		sb.append(calendar.get(Calendar.DAY_OF_MONTH));
		return sb.toString();
	}

	/**
	 * ���ַ���ʽ������ת����Date����
	 * 
	 * @param date
	 *            ���ڵ��ַ�����ʽ
	 * @return Data���͵�����
	 */
	public synchronized static Date convertToDate(String date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-DD");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ���ַ���ʽ������ת����Date����
	 * 
	 * @param date
	 *            ���ڵ��ַ�����ʽ
	 * @return Data���͵�����
	 */
	public synchronized static Date convertToDate2(String date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public synchronized static Date format(Date date,String pattern){
		if(pattern!=null && pattern.trim().length()>0){
			DateFormat format = new SimpleDateFormat(pattern);
			String stringDate=format.format(date);
			
			return Timer.convertToDate(stringDate);
		}
		return null;
	}
	public synchronized static String convertToString(Date date) {
		if(date==null){
			return "";
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
		return format.format(date);
	}

	public synchronized static String convertToString(Date date,
			String formatText) {
		if(date==null){
			return "";
		}
		DateFormat format = new SimpleDateFormat(formatText);
		Calendar calendar = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		sb.append(calendar.get(Calendar.YEAR)).append("-");
		sb.append(calendar.get(Calendar.MONTH) + 1).append("-");
		sb.append(calendar.get(Calendar.DAY_OF_MONTH));
		return format.format(date);
	}

	/**
	 * ��ȡ����һ������
	 * 
	 * @return
	 */
	public synchronized static Date getMondayOFWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// �������ô���һ��ʼ,����Ҫ����ϵͳʱ���Զ���ȡ��������±ߵķ�ʽ
		// calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		return calendar.getTime();
	}

	/**
	 * ��ȡ����������
	 * 
	 * @return
	 */
	public synchronized static Date getCurrentWeekday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, getCurrentPlus());// �ѵ�ǰ���ڵ�DATE���ϵ�ǰ�����뱾����֮����������
		return calendar.getTime();
	}

	/**
	 * ��ȡ�����յ�����
	 * 
	 * @return
	 */
	public synchronized static Date getPreviousWeekday() {
		Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY+7);
		calendar.add(Calendar.DATE, getCurrentPlus() - 7);// �ѵ�ǰ���ڵ�DATE���ϵ�ǰ�����뱾����֮����������
		return calendar.getTime();
	}

	/**
	 * ��ȡ�����յ�����
	 * 
	 * @return
	 */
	public synchronized static Date getPreviousMonday() {
		Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY+7);
		calendar.add(Calendar.DATE, getCurrentPlus() - 13);// �ѵ�ǰ���ڵ�DATE���ϵ�ǰ�����뱾����֮����������
		return calendar.getTime();
	}

	/**
	 * ��ȡ�����յ�����
	 * 
	 * @return
	 */
	public synchronized static Date getNextWeekday() {
		Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY+7);
		calendar.add(Calendar.DATE, getCurrentPlus() + 1 + 6);// �ѵ�ǰ���ڵ�DATE���ϵ�ǰ�����뱾����֮����������
		return calendar.getTime();
	}

	/**
	 * ��ȡ�����յ�����
	 * 
	 * @return
	 */
	public synchronized static Date getNextMonday() {
		Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY+7);
		calendar.add(Calendar.DATE, getCurrentPlus() + 1);// �ѵ�ǰ���ڵ�DATE���ϵ�ǰ�����뱾����֮����������
		return calendar.getTime();
	}

	/**
	 * ��ȡ��ǰ�����뱾������֮����������
	 * 
	 * @return
	 */
	public synchronized static int getCurrentPlus() {
		Calendar calendar = Calendar.getInstance();
		int days = calendar.get(Calendar.DAY_OF_WEEK) - 1;// ���й���������һ��Ϊһ�ܵĵ�һ��,���������1
		return 7 - days;
	}

	public synchronized static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * ����ָ�����ڵĵ��µ�һ��
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("static-access")
	public synchronized static Date getFirstDayInMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * ����ָ�����ڵĵ������һ��
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("static-access")
	public synchronized static Date getLastDayInMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.MONTH, 1);
		calendar.set(calendar.DATE, 1);
		calendar.add(calendar.DATE, -1);
		return calendar.getTime();
	}

}
