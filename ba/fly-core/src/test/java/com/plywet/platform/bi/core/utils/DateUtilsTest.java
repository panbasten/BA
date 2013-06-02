package com.plywet.platform.bi.core.utils;

import java.util.Date;

import junit.framework.TestCase;

public class DateUtilsTest extends TestCase {
	public void testDateUtils() {
		String strDate = "2010-03-26";
		Date date = DateUtils.parseDate(strDate);
		System.out.println(strDate + " 是一年的第几周？"
				+ DateUtils.getWeekOfYear(date));
		System.out.println(strDate + " 所在周起始结束日期？"
				+ DateUtils.getWeekBeginAndEndDate(date, "yyyy年MM月dd日"));
		System.out.println(strDate + " 所在周周一是？"
				+ DateUtils.formatDate(DateUtils.getMondayOfWeek(date)));
		System.out.println(strDate + " 所在周周日是？"
				+ DateUtils.formatDate(DateUtils.getSundayOfWeek(date)));

		System.out.println(strDate + " 当月第一天日期？"
				+ DateUtils.formatDate(DateUtils.getFirstDateOfMonth(date)));
		System.out.println(strDate + " 当月最后一天日期？"
				+ DateUtils.formatDate(DateUtils.getLastDateOfMonth(date)));
		System.out.println(strDate + " 当月天数？" + DateUtils.getDayOfMonth(date));
		System.out.println(strDate + " 当月已过多少天？"
				+ DateUtils.getPassDayOfMonth(date));
		System.out.println(strDate + " 当月剩余多少天？"
				+ DateUtils.getRemainDayOfMonth(date));

		System.out.println(strDate + " 所在季度第一天日期？"
				+ DateUtils.formatDate(DateUtils.getFirstDateOfSeason(date)));
		System.out.println(strDate + " 所在季度最后一天日期？"
				+ DateUtils.formatDate(DateUtils.getLastDateOfSeason(date)));
		System.out.println(strDate + " 所在季度天数？"
				+ DateUtils.getDayOfSeason(date));
		System.out.println(strDate + " 所在季度已过多少天？"
				+ DateUtils.getPassDayOfSeason(date));
		System.out.println(strDate + " 所在季度剩余多少天？"
				+ DateUtils.getRemainDayOfSeason(date));
		System.out.println(strDate + " 是第几季度？" + DateUtils.getSeason(date));
		System.out.println(strDate
				+ " 所在季度月份？"
				+ DateUtils.formatDate(DateUtils.getSeasonDate(date)[0],
						"yyyy年MM月")
				+ "/"
				+ DateUtils.formatDate(DateUtils.getSeasonDate(date)[1],
						"yyyy年MM月")
				+ "/"
				+ DateUtils.formatDate(DateUtils.getSeasonDate(date)[2],
						"yyyy年MM月"));
	}
}
