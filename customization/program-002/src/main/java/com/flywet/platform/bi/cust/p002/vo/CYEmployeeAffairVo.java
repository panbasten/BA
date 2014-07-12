package com.flywet.platform.bi.cust.p002.vo;

import java.util.HashMap;
import java.util.Map;

import com.flywet.platform.bi.cust.p002.db.CYDatabaseRepositoryBase;

public class CYEmployeeAffairVo {

	private Map<String, Object> fields = new HashMap<String, Object>();

	public void setId(long id) {
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_ID_EMPLOYEE,
				id);
	}

	public void setOnJobFlag(boolean f) {
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_ON_JOB_FLAG,
				f ? "Y" : "N");
	}

	public void setLastestFlag(boolean f) {
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_LASTEST_FLAG,
				f ? "Y" : "N");
	}

	public void setBelongToDep(String id) {
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_ID_DEP,
				id);
	}

	public void setBatchKey(String key) {
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_BATCH_KEY,
				key);
	}

	public void put(String key, Object val) {
		fields.put(key, val);
	}

	public Object get(String key) {
		return fields.get(key);
	}

	public Map<String, Object> getFields() {
		return fields;
	}

	// 结束当前状态
	public void closeState(String closeDate) {
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMP_AFFAIR_END_DATE,
				closeDate);
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMP_AFFAIR_END_TIME,
				"000000");

		setLastestFlag(false);
	}

	// 设置入职（1）状态
	public void entry(String entryDate) {
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMP_AFFAIR_TYPE,
				"1");
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMP_AFFAIR_DATE,
				entryDate);
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMP_AFFAIR_TIME,
				"000000");
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMP_AFFAIR_END_DATE,
				"20501231");
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMP_AFFAIR_END_TIME,
				"000000");

		setOnJobFlag(true);
		setLastestFlag(true);
	}

	// 设置离职（2）状态
	public void leave(String leaveDate) {
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMP_AFFAIR_TYPE,
				"2");
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMP_AFFAIR_DATE,
				leaveDate);
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMP_AFFAIR_TIME,
				"000000");
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMP_AFFAIR_END_DATE,
				"20501231");
		fields.put(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMP_AFFAIR_END_TIME,
				"000000");

		setOnJobFlag(false);
		setLastestFlag(true);
	}
}
