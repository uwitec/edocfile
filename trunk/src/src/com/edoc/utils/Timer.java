package com.edoc.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 获取当前的日期格式为:yyyy-MM-dd HH:mm:ss
 * 
 * @author 陈超
 * 
 */
public class Timer {
	/**
	 * 获取当前的日期
	 * 
	 * @return 当前的日期
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
	 * 把字符形式的日期转换成Date类型
	 * 
	 * @param date
	 *            日期的字符串形式
	 * @return Data类型的日期
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
	 * 把字符形式的日期转换成Date类型
	 * 
	 * @param date
	 *            日期的字符串形式
	 * @return Data类型的日期
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
	 * 获取本周一的日期
	 * 
	 * @return
	 */
	public synchronized static Date getMondayOFWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 这里设置从周一开始,若需要根据系统时区自动获取，则采用下边的方式
		// calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		return calendar.getTime();
	}

	/**
	 * 获取本周日日期
	 * 
	 * @return
	 */
	public synchronized static Date getCurrentWeekday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, getCurrentPlus());// 把当前日期的DATE加上当前日期与本周日之间相差的天数
		return calendar.getTime();
	}

	/**
	 * 获取上周日的日期
	 * 
	 * @return
	 */
	public synchronized static Date getPreviousWeekday() {
		Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY+7);
		calendar.add(Calendar.DATE, getCurrentPlus() - 7);// 把当前日期的DATE加上当前日期与本周日之间相差的天数
		return calendar.getTime();
	}

	/**
	 * 获取上周日的日期
	 * 
	 * @return
	 */
	public synchronized static Date getPreviousMonday() {
		Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY+7);
		calendar.add(Calendar.DATE, getCurrentPlus() - 13);// 把当前日期的DATE加上当前日期与本周日之间相差的天数
		return calendar.getTime();
	}

	/**
	 * 获取上周日的日期
	 * 
	 * @return
	 */
	public synchronized static Date getNextWeekday() {
		Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY+7);
		calendar.add(Calendar.DATE, getCurrentPlus() + 1 + 6);// 把当前日期的DATE加上当前日期与本周日之间相差的天数
		return calendar.getTime();
	}

	/**
	 * 获取上周日的日期
	 * 
	 * @return
	 */
	public synchronized static Date getNextMonday() {
		Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY+7);
		calendar.add(Calendar.DATE, getCurrentPlus() + 1);// 把当前日期的DATE加上当前日期与本周日之间相差的天数
		return calendar.getTime();
	}

	/**
	 * 获取当前日期与本周周日之间相差的天数
	 * 
	 * @return
	 */
	public synchronized static int getCurrentPlus() {
		Calendar calendar = Calendar.getInstance();
		int days = calendar.get(Calendar.DAY_OF_WEEK) - 1;// 在中国是已星期一作为一周的第一天,所以这里减1
		return 7 - days;
	}

	public synchronized static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回指定日期的当月第一天
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
	 * 返回指定日期的当月最后一天
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
