package com.edoc.utils;

public class T {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new RandomGUID().toString());
		System.out.println("size:"+2/3);
		System.out.println("size:"+((double)2/3));
		System.out.println("docFileFileSize:"+Math.round(((double)2/3)));
		
		System.out.println(Timer.convertToDate2("2011-04-25 16:17:33"));
	}

}
