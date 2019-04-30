package pers.dafacloud.utils.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Time {

	public static void main(String[] args) throws ParseException {
		/*System.out.println(YYYYMMDD());
		
	      SimpleDateFormat simpleDateFormat = 
	                new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	      	Date date = new Date();
	      	Date startDate = simpleDateFormat.parse(simpleDateFormat.format(date));
	        Date endDate = simpleDateFormat.parse("2018-12-05 11:02:50");
	 
	        Time.printDifference(startDate, endDate);*/
		//System.out.println(year());
		/*for (int i = 0; i < year().size(); i++) {
			System.out.println(year().get(i));
		}*/
		
		//System.out.println(Time.YYYYgMMgDD());
		/*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
		Date date = new Date();
		String sfm = simpleDateFormat.format(date);
		
		Date endDate = simpleDateFormat.parse("2018-12-05 11:02:50");
		long l=endDate.getTime();
		
		System.out.println(sfm);
		System.out.println(l);*/
		System.out.println(YYYYMMDD());
	}

	/**
	 * 返回时间，格式 01-01 01:01:01
	 * */
	public static String MMDDhms() {
		// 获取当前时间字符串
		SimpleDateFormat simpleDateFormat;
		simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
		Date date = new Date();
		String sfm = simpleDateFormat.format(date);
		return sfm;
	}

	/**
	 * 返回年月日，格式20190101
	 * */
	public static String YYYYMMDD() {
		// 获取当前时间字符串
		SimpleDateFormat simpleDateFormat;
		simpleDateFormat = new SimpleDateFormat("yyyyMMdd");//YYYY年底时候回加一年
		Date date = new Date();
		//System.out.println(date);
		String sfm = simpleDateFormat.format(date);
		return sfm;
	}

	public static String YYYYgMMgDD() {
		// 获取当前时间字符串
		SimpleDateFormat simpleDateFormat;
		simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		Date date = new Date();
		String sfm = simpleDateFormat.format(date);
		return sfm;
	}

	/**
	 * 计算当前时间到结束时间的时间差秒数
	 */
	public static long diffSecond(String endDates) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		Date date = new Date();
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = simpleDateFormat.parse(simpleDateFormat.format(date));
			endDate = simpleDateFormat.parse(endDates);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long different = endDate.getTime() - startDate.getTime();
		return different / 1000;
	}

	// 来源网上，目前没有使用，计算时间差： 天，时，分，秒
	public static long printDifference(Date startDate, Date endDate) {

		// milliseconds
		long different = endDate.getTime() - startDate.getTime();

		// System.out.println("startDate : " + startDate);
		// System.out.println("endDate : "+ endDate);
		/*
		 * System.out.println("different : " + different);
		 * 
		 * long secondsInMilli = 1000; long minutesInMilli = secondsInMilli * 60; long
		 * hoursInMilli = minutesInMilli * 60; long daysInMilli = hoursInMilli * 24;
		 * 
		 * long elapsedDays = different / daysInMilli; different = different %
		 * daysInMilli;
		 * 
		 * long elapsedHours = different / hoursInMilli; different = different %
		 * hoursInMilli;
		 * 
		 * long elapsedMinutes = different / minutesInMilli; different = different %
		 * minutesInMilli;
		 * 
		 * long elapsedSeconds = different / secondsInMilli;
		 * 
		 * System.out.printf( "%d days, %d hours, %d minutes, %d seconds%n",
		 * elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
		 */
		return different / 1000;

	}
	
	/**
	 * 生成一年的日历
	 * */
	public static List<String> year() {
		int year = 2018;
		int m = 1;// 月份计数
		List<String> list=new  ArrayList<String>();
		while (m < 13) {
			int month = m;
			Calendar cal = Calendar.getInstance();// 获得当前日期对象
			cal.clear();// 清除信息
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month - 1);// 1月从0开始
			int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//每个月的天数
			for (int j = 1; j <= count; j++) {
				//list.add(year+"-"+String.format("%02d", month)+"-"+String.format("%02d", j));
				list.add(year+String.format("%02d", month)+String.format("%02d", j));
				//System.out.print(year+"-"+String.format("%02d", month)+"-"+String.format("%02d", j) + "\t");
				
			}
			m++;
		}
		return list;
	}
	/**
	 * 网上拷贝 生成一年的日历
	 * */
	public static void yearRiLi() {
		int year = 2018;
		int m = 1;// 月份计数
		while (m < 13) {
			int month = m;
			Calendar cal = Calendar.getInstance();// 获得当前日期对象
			cal.clear();// 清除信息
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month - 1);// 1月从0开始
			int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			int week = cal.get(Calendar.DAY_OF_WEEK);
			System.out.printf("\t\t\t%d年%d月\n\n", year, month);
			System.out.print("日\t一\t二\t三\t四\t五\t六\n");
			int i;
			for (i = 0; i < week - 1; i++) {
				System.out.print("\t");
			}
			for (int j = 1; j <= count; j++) {
				System.out.print(j + "\t");
				if ((i + j) % 7 == 0) {
					System.out.println();
				}
			}
			System.out.println();
			m++;
		}
	}

}
