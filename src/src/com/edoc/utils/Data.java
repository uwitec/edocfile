package com.edoc.utils;

import java.util.Calendar;
import java.util.Date;

/*
 * 对数据的处理类，静态方法类
 * 包括在外部显示时对要显示的数据的处理
 */
public class Data {
	public static boolean isEmpty(String s) {
		return (s == null || s.length() == 0 || s.equals("undefined")); // 在web中当域未定义时，取值时返回"undefined"
	}

	public static boolean isEmpty(Object obj) {
		return (obj == null);
	}

	public static String showString(String s) {
		if (isEmpty(s)) {
			return "";
		} else {
			return s;
		}
	}

	public static String showDate(Date dt, String formatText) {
		if (isEmpty(dt)) {
			return "";
		} else {
			return DateTimeFormat.getString(dt, formatText);
		}
	}

	public static Long showLong(Long l) {
		if (isEmpty(l)) {
			return new Long(0);
		} else {
			return l;
		}
	}

	/**
	 * 两个日期之间的年份差
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static int marginOfYear(Date date1, Date date2) {
		Date dt1 = date1;
		Date dt2 = date2;
		if (dt1.compareTo(dt2) > 0) { // 使得dt1<=dt2
			Date dtTemp = dt1;
			dt1 = dt2;
			dt2 = dtTemp;
		}
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(dt1);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(dt2);

		int margin = calendar2.YEAR - calendar1.YEAR;
		if (calendar2.YEAR > calendar1.YEAR) {
			System.out.println("year相等:" +calendar1.YEAR + "," +calendar2.YEAR);
			if (calendar2.MONTH > calendar1.MONTH) {
				System.out.println("MONTH2>MONTH1:" +calendar1.MONTH + "," +calendar2.MONTH);
				return margin + 1;
			} else if (calendar2.MONTH == calendar1.MONTH) {
				if (calendar2.DATE >= calendar1.DATE) {
					System.out.println("DATE2>=DATE1:" +calendar1.DATE + "," +calendar2.DATE);
					System.out.println("year相等");
					return margin + 1;
				}else{
					return margin;
				}
			}else{
				return margin;
			}
		} else {
			// 即calendar1.YEAR == calendar2.YEAR
			return margin;
		}
	}
}
