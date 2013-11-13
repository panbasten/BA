package com.flywet.platform.bi.cust.p001.vo;

public class MonthPredictScoreVo {

	private String yearMonth;

	private String s1;
	private String s2;
	private String s3;
	private String s4;
	private String s5;
	private String s6;
	private String s7;

	public static MonthPredictScoreVo instance() {
		return new MonthPredictScoreVo();
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public MonthPredictScoreVo setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
		return this;
	}

	public String getS1() {
		return s1;
	}

	public MonthPredictScoreVo setS1(String s1) {
		this.s1 = s1;
		return this;
	}

	public String getS2() {
		return s2;
	}

	public MonthPredictScoreVo setS2(String s2) {
		this.s2 = s2;
		return this;
	}

	public String getS3() {
		return s3;
	}

	public MonthPredictScoreVo setS3(String s3) {
		this.s3 = s3;
		return this;
	}

	public String getS4() {
		return s4;
	}

	public MonthPredictScoreVo setS4(String s4) {
		this.s4 = s4;
		return this;
	}

	public String getS5() {
		return s5;
	}

	public MonthPredictScoreVo setS5(String s5) {
		this.s5 = s5;
		return this;
	}

	public String getS6() {
		return s6;
	}

	public MonthPredictScoreVo setS6(String s6) {
		this.s6 = s6;
		return this;
	}

	public String getS7() {
		return s7;
	}

	public MonthPredictScoreVo setS7(String s7) {
		this.s7 = s7;
		return this;
	}

}
