package com.flywet.platform.bi.cust.p002.vo;

import java.util.HashMap;
import java.util.Map;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.row.ValueMetaInterface;

import com.flywet.platform.bi.cust.p002.db.CYDatabaseRepositoryBase;
import com.flywet.platform.bi.model.BIDatabaseConnectionDelegate;

public class CYDepartmentVo {
	private long id;
	private String company;
	private String dep0;
	private String dep1;
	private String dep2;
	private String dep3;
	private String dep4;
	private String dep5;
	private String dep6;
	private String dep7;
	private String depSmall;

	private boolean validDepFlag;

	private String batchKey;

	private static Map<String, Long> cache = new HashMap<String, Long>();

	public RowMetaAndData getRMD() {
		RowMetaAndData rmd = new RowMetaAndData();

		rmd.addValue(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_DEPARTMENT_ID_DEPARTMENT,
				ValueMetaInterface.TYPE_INTEGER, id);

		rmd.addValue(CYDatabaseRepositoryBase.FIELD_BIHR_D_DEPARTMENT_COMPANY,
				ValueMetaInterface.TYPE_STRING, company);

		rmd.addValue(CYDatabaseRepositoryBase.FIELD_BIHR_D_DEPARTMENT_DEP0,
				ValueMetaInterface.TYPE_STRING, dep0);
		rmd.addValue(CYDatabaseRepositoryBase.FIELD_BIHR_D_DEPARTMENT_DEP1,
				ValueMetaInterface.TYPE_STRING, dep1);
		rmd.addValue(CYDatabaseRepositoryBase.FIELD_BIHR_D_DEPARTMENT_DEP2,
				ValueMetaInterface.TYPE_STRING, dep2);
		rmd.addValue(CYDatabaseRepositoryBase.FIELD_BIHR_D_DEPARTMENT_DEP3,
				ValueMetaInterface.TYPE_STRING, dep3);
		rmd.addValue(CYDatabaseRepositoryBase.FIELD_BIHR_D_DEPARTMENT_DEP4,
				ValueMetaInterface.TYPE_STRING, dep4);
		rmd.addValue(CYDatabaseRepositoryBase.FIELD_BIHR_D_DEPARTMENT_DEP5,
				ValueMetaInterface.TYPE_STRING, dep5);
		rmd.addValue(CYDatabaseRepositoryBase.FIELD_BIHR_D_DEPARTMENT_DEP6,
				ValueMetaInterface.TYPE_STRING, dep6);
		rmd.addValue(CYDatabaseRepositoryBase.FIELD_BIHR_D_DEPARTMENT_DEP7,
				ValueMetaInterface.TYPE_STRING, dep7);

		rmd.addValue(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_DEPARTMENT_DEP_SMALL,
				ValueMetaInterface.TYPE_STRING, depSmall);

		rmd.addValue(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_DEPARTMENT_VALID_DEP_FLAG,
				ValueMetaInterface.TYPE_STRING, validDepFlag ? "Y" : "N");

		rmd.addValue(
				CYDatabaseRepositoryBase.FIELD_BIHR_D_DEPARTMENT_BATCH_KEY,
				ValueMetaInterface.TYPE_STRING, batchKey);

		return rmd;
	}

	public CYDepartmentVo setId(long id) {
		this.id = id;
		return this;
	}

	public CYDepartmentVo setValidDepFlag(boolean f) {
		this.validDepFlag = f;
		return this;
	}

	public CYDepartmentVo setCompany(String company) {
		this.company = company;
		return this;
	}

	public CYDepartmentVo setDep0(String dep0) {
		this.dep0 = dep0;
		return this;
	}

	public CYDepartmentVo setDep1(String dep1) {
		this.dep1 = dep1;
		return this;
	}

	public CYDepartmentVo setDep2(String dep2) {
		this.dep2 = dep2;
		return this;
	}

	public CYDepartmentVo setDep3(String dep3) {
		this.dep3 = dep3;
		return this;
	}

	public CYDepartmentVo setDep4(String dep4) {
		this.dep4 = dep4;
		return this;
	}

	public CYDepartmentVo setDep5(String dep5) {
		this.dep5 = dep5;
		return this;
	}

	public CYDepartmentVo setDep6(String dep6) {
		this.dep6 = dep6;
		return this;
	}

	public CYDepartmentVo setDep7(String dep7) {
		this.dep7 = dep7;
		return this;
	}

	public CYDepartmentVo setDepSmall(String depSmall) {
		this.depSmall = depSmall;
		return this;
	}

	public CYDepartmentVo setBatchKey(String key) {
		this.batchKey = key;
		return this;
	}

	private String key() {
		String k = "";
		k = k + Const.NVL(company, "") + ",";
		k = k + Const.NVL(dep0, "") + ",";
		k = k + Const.NVL(dep1, "") + ",";
		k = k + Const.NVL(dep2, "") + ",";
		k = k + Const.NVL(dep3, "") + ",";
		k = k + Const.NVL(dep4, "") + ",";
		k = k + Const.NVL(dep5, "") + ",";
		k = k + Const.NVL(dep6, "") + ",";
		k = k + Const.NVL(dep7, "") + ",";
		k = k + Const.NVL(depSmall, "") + ",";
		return k;
	}

	public void cache() {
		CYDepartmentVo.cache.put(key(), this.id);
	}

	public Long match(BIDatabaseConnectionDelegate dcd) {
		Long key = CYDepartmentVo.cache.get(key());
		if(key == null){
			// 从数据库中匹配 TODO
			
		}
		
		return key;
	}
}
