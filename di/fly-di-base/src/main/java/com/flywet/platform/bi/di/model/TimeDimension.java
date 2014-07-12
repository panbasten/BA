package com.flywet.platform.bi.di.model;

import java.util.Date;

import com.flywet.platform.bi.core.utils.DateUtils;

public class TimeDimension {

	// 时间关键字
	private String id;

	// 时间完整描述
	private String desc;

	// 日历小时HH
	private String hh;

	// 日历分钟MM
	private String mm;

	// 日历秒SS
	private String ss;

	// 日历小时分钟HH:MM
	private String hhmm;

	// 日历小时分钟秒HH:MM:SS
	private String hhmmss;

	// 日历分钟秒MM:SS
	private String mmss;

	// 日历时段
	private String intervalDesc;

	// 小时编号
	private int hour;

	// 分钟编号
	private int minute;

	// 秒编号
	private int second;

	// 天-分钟编号
	private int minuteIdx;

	// 天-秒编号
	private int secondIdx;

	@SuppressWarnings("deprecation")
	public static TimeDimension instance(Date d) {
		TimeDimension td = new TimeDimension();

		td.id = DateUtils.formatDate(d, "HHmmss");

		td.hh = DateUtils.formatDate(d, "HH");
		td.mm = DateUtils.formatDate(d, "mm");
		td.ss = DateUtils.formatDate(d, "ss");
		td.hhmm = DateUtils.formatDate(d, "HH:mm");
		td.hhmmss = DateUtils.formatDate(d, "HH:mm:ss");
		td.mmss = DateUtils.formatDate(d, "mm:ss");

		td.hour = d.getHours();
		td.minute = d.getMinutes();
		td.second = d.getSeconds();

		td.minuteIdx = td.hour * 60 + td.minute;
		td.secondIdx = td.minuteIdx * 60 + td.second;

		if (td.hour >= 4 && td.hour < 7) {
			td.intervalDesc = "凌晨";
		} else if (td.hour >= 7 && td.hour < 11) {
			td.intervalDesc = "早上";
		} else if (td.hour >= 11 && td.hour < 14) {
			td.intervalDesc = "中午";
		} else if (td.hour >= 14 && td.hour < 17) {
			td.intervalDesc = "下午";
		} else if (td.hour >= 17 && td.hour < 19) {
			td.intervalDesc = "傍晚";
		} else {
			td.intervalDesc = "晚上";
		}

		td.desc = td.hour + "点" + td.minute + "分" + td.second + "秒 "
				+ td.intervalDesc;

		return td;
	}

	public String getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public String getHh() {
		return hh;
	}

	public String getMm() {
		return mm;
	}

	public String getSs() {
		return ss;
	}

	public String getHhmm() {
		return hhmm;
	}

	public String getHhmmss() {
		return hhmmss;
	}

	public String getMmss() {
		return mmss;
	}

	public String getIntervalDesc() {
		return intervalDesc;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public int getSecond() {
		return second;
	}

	public int getMinuteIdx() {
		return minuteIdx;
	}

	public int getSecondIdx() {
		return secondIdx;
	}

}
