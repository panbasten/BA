package com.flywet.platform.bi.di.model;

import java.util.Calendar;
import java.util.Date;

import com.flywet.platform.bi.core.utils.DateUtils;

public class DateDimension {

	// 日期关键字
	private String id;

	// 日期完全描述
	private String desc;

	// 星期几
	private String week;
	private int weekInt;

	// 旬
	private String tenDay;
	private int tenDayInt;

	// 日历年YYYY
	private String yyyy;

	// 日历月MM
	private String mm;
	private int monthInt;

	// 日历日DD
	private String dd;
	private int dateInt;

	// 日历年月YYYY-MM
	private String yyyymm;

	// 日历年月日YYYY-MM-DD
	private String yyyymmdd;

	// 日历月日MM-DD
	private String mmdd;

	// 日历半年度
	private String half;
	private int halfInt;

	// 日历季度
	private String quarter;
	private int quarterInt;

	// 日编号（当年）
	private int dateIdx;

	// 周编号（当年）
	private int weekIdx;

	// 周末指示符
	private boolean weekEndFlag;

	// 月末指示符
	private boolean monthEndFlag;

	// 节假日指示符
	private boolean vacationFlag;

	// 重大事件
	private String importantEvent;

	@SuppressWarnings("deprecation")
	public static DateDimension instance(Date d) {
		DateDimension dd = new DateDimension();

		Calendar c = Calendar.getInstance();
		c.setTime(d);

		dd.id = DateUtils.formatDate(d, "yyyyMMdd");

		dd.weekInt = c.get(Calendar.DAY_OF_WEEK);
		dd.week = DateUtils.getDayOfWeekDesc(d);

		dd.tenDayInt = DateUtils.getTenDay(d);
		dd.tenDay = DateUtils.getTenDayDesc(d);

		dd.yyyy = DateUtils.formatDate(d, "yyyy");

		dd.mm = DateUtils.formatDate(d, "MM");
		dd.monthInt = d.getMonth() + 1;

		dd.dd = DateUtils.formatDate(d, "dd");
		dd.dateInt = d.getDate();

		dd.yyyymm = DateUtils.formatDate(d, "yyyy-MM");

		dd.yyyymmdd = DateUtils.formatDate(d, "yyyy-MM-dd");

		dd.mmdd = DateUtils.formatDate(d, "MM-dd");

		dd.halfInt = DateUtils.getHalfYear(d);
		dd.half = DateUtils.getHalfYearDesc(d);

		dd.quarterInt = DateUtils.getQuarter(d);
		dd.quarter = DateUtils.getQuarterDesc(d);

		dd.dateIdx = c.get(Calendar.DAY_OF_YEAR);

		dd.weekIdx = c.get(Calendar.WEEK_OF_YEAR);

		// 周末指示符
		if (dd.weekInt == Calendar.SUNDAY || dd.weekInt == Calendar.SATURDAY) {
			dd.weekEndFlag = true;
		}

		// 月末指示符
		Date ldm = DateUtils.getLastDateOfMonth(d);
		if (ldm.equals(d)) {
			dd.monthEndFlag = true;
		}

		// 节假日指示符，具体调整方案待定 TODO
		dd.vacationFlag = dd.weekEndFlag;

		// TODO
		dd.importantEvent = "";

		// 详细描述
		dd.desc = DateUtils.formatDate(d, "yyyy年MM月dd日") + " " + dd.week;

		return dd;
	}

	public String getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public String getWeek() {
		return week;
	}

	public int getWeekInt() {
		return weekInt;
	}

	public String getTenDay() {
		return tenDay;
	}

	public int getTenDayInt() {
		return tenDayInt;
	}

	public String getYyyy() {
		return yyyy;
	}

	public String getMm() {
		return mm;
	}

	public int getMonthInt() {
		return monthInt;
	}

	public String getDd() {
		return dd;
	}

	public int getDateInt() {
		return dateInt;
	}

	public String getYyyymm() {
		return yyyymm;
	}

	public String getYyyymmdd() {
		return yyyymmdd;
	}

	public String getMmdd() {
		return mmdd;
	}

	public String getHalf() {
		return half;
	}

	public int getHalfInt() {
		return halfInt;
	}

	public String getQuarter() {
		return quarter;
	}

	public int getQuarterInt() {
		return quarterInt;
	}

	public int getDateIdx() {
		return dateIdx;
	}

	public int getWeekIdx() {
		return weekIdx;
	}

	public boolean isWeekEndFlag() {
		return weekEndFlag;
	}

	public boolean isMonthEndFlag() {
		return monthEndFlag;
	}

	public boolean isVacationFlag() {
		return vacationFlag;
	}

	public String getImportantEvent() {
		return importantEvent;
	}

}
