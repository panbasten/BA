package com.flywet.cust.p001.vo;

public class MonthPredictEvaVo {

	private String yearMonth;

	private long s1;
	private long s2;
	private long s3;
	private long s4;
	private long s5;
	private long s6;

	public static MonthPredictEvaVo instance() {
		return new MonthPredictEvaVo();
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public MonthPredictEvaVo setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
		return this;
	}

	public long getS1() {
		return s1;
	}

	public MonthPredictEvaVo setS1(long s1) {
		this.s1 = s1;
		return this;
	}

	public long getS2() {
		return s2;
	}

	public MonthPredictEvaVo setS2(long s2) {
		this.s2 = s2;
		return this;
	}

	public long getS3() {
		return s3;
	}

	public MonthPredictEvaVo setS3(long s3) {
		this.s3 = s3;
		return this;
	}

	public long getS4() {
		return s4;
	}

	public MonthPredictEvaVo setS4(long s4) {
		this.s4 = s4;
		return this;
	}

	public long getS5() {
		return s5;
	}

	public MonthPredictEvaVo setS5(long s5) {
		this.s5 = s5;
		return this;
	}

	public long getS6() {
		return s6;
	}

	public MonthPredictEvaVo setS6(long s6) {
		this.s6 = s6;
		return this;
	}

}
