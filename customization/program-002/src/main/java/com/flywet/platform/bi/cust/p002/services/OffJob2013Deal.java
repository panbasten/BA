package com.flywet.platform.bi.cust.p002.services;

import org.pentaho.di.core.ConstDB;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.spreadsheet.KCell;

import com.flywet.platform.bi.cust.p002.db.CYDatabaseRepositoryBase;
import com.flywet.platform.bi.cust.p002.vo.CYDepartmentVo;
import com.flywet.platform.bi.cust.p002.vo.CYEmployeeAffairVo;
import com.flywet.platform.bi.model.BIDatabaseConnectionDelegate;
import com.flywet.platform.bi.services.intf.BIDatabaseDelegates;

public class OffJob2013Deal extends HRExcelDeal {

	public OffJob2013Deal(String filename, String sheetname,
			BIDatabaseDelegates dbDelegates) {
		super(filename, sheetname, dbDelegates);
	}

	public void dealRow(KCell[] line, BIDatabaseConnectionDelegate dcd)
			throws Exception {

		// 1.按照员工工号查询
		String empNum = str(line, 1);
		String oldEmpNum = str(line, 2);

		String sql = "SELECT "
				+ dcd.quoteField(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_ID_EMPLOYEE)
				+ " FROM "
				+ dcd.quoteTable(CYDatabaseRepositoryBase.TABLE_BIHR_D_EMPLOYEE_AFFAIR)
				+ " WHERE ("
				+ dcd.quoteField(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMP_NUM)
				+ " = ? or "
				+ dcd.quoteField(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_OLD_EMP_NUM)
				+ " = ?) and "
				+ dcd.quoteField(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_LASTEST_FLAG)
				+ " = 'Y'";
		Object[] params = new Object[2];
		params[0] = empNum;
		params[1] = oldEmpNum;

		Object[] emp = dcd.getOneRow(sql, params);

		// 如果没有查到，添加一条入职纪录和一条离职纪录
		if (emp == null) {
			// 1.通过2003离职，生成入职信息
			CYEmployeeAffairVo vo = new CYEmployeeAffairVo();
			instanceForOffJob2003For1(vo, line, dcd);
			String start = date(line, 35);// 入职时间
			if (start == null) {
				start = date(line, 38);
			}
			String end = date(line, 68); // 离职时间
			vo.entry(start);
			vo.closeState(end);
			// 保存
			vo.setId(getEmployeeAffairId(dcd));
			dcd.insertTableRow(
					CYDatabaseRepositoryBase.TABLE_BIHR_D_EMPLOYEE_AFFAIR,
					ConstDB.getRowMetaAndData(vo.getFields()));
			dcd.commit();

			// 2.通过2003离职，生成离职信息
			instanceForOffJob2003For2(vo, line);
			vo.leave(end);
			// 保存
			vo.setId(getEmployeeAffairId(dcd));
			dcd.insertTableRow(
					CYDatabaseRepositoryBase.TABLE_BIHR_D_EMPLOYEE_AFFAIR,
					ConstDB.getRowMetaAndData(vo.getFields()));

			dcd.commit();
		}
		// 如果查到，对于离职信息不再处理

	}

	private void instanceForOffJob2003For2(CYEmployeeAffairVo vo, KCell[] line) {
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_DEPARTURE_DATE,
				date(line, 68));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_DEPARTURE_CATEGORY_1,
				str(line, 70));
		// vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_DEPARTURE_CATEGORY_2,
		// str(line,1));
		// vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_PAYMENT,
		// d(line,1));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_DEPARTURE_REASON_1,
				str(line, 72));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_DEPARTURE_REASON_2,
				str(line, 73));
		// vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_DEPARTURE_TARGET,
		// str(line,1));
		// vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_PAY_RISE,
		// str(line,1));
		// vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_JOB_CHANGEE,
		// str(line,1));
		// vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_COMPLETED_DATE,
		// date(line,1));

	}

	private void instanceForOffJob2003For1(CYEmployeeAffairVo vo, KCell[] line,
			BIDatabaseConnectionDelegate dcd) throws KettleException {
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMP_NUM,
				str(line, 1));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_OLD_EMP_NUM,
				str(line, 2));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_NAME,
				str(line, 3));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_SEX,
				str(line, 4));

		// vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_JOB_CODE,
		// str(line,1));
		// vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_POSITION,
		// str(line,1));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_ENTRY_DATE,
				date(line, 35));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_PERIOD_END_DATE,
				date(line, 36));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_CONTRACT_EXPIRY_DATE,
				date(line, 37));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_INTERNSHIP_START_DATE,
				date(line, 38));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_INTERNSHIP_END_DATE,
				date(line, 39));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMAIL,
				str(line, 40));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_PHONE_NUM,
				str(line, 44));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_ID_NUM,
				str(line, 45));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_BIRTHDAY,
				date(line, 46));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_NATIVE_PLACE,
				str(line, 47));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_DOMICILE_PLACE,
				str(line, 48));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_NATIONAL,
				str(line, 49));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_POLITICAL_LANDSCAPE,
				str(line, 50));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_GRADUATED_SCHOOL,
				str(line, 51));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_ADMISSION_DATE,
				date(line, 52));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_GRADUATION_DATE,
				date(line, 53));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_MAJAR,
				str(line, 54));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EDUCATION_BACKGROUND,
				str(line, 55));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_MARITAL_STATUS_FLAG,
				str(line, 58));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_START_WORK_DATE,
				date(line, 60));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_LAST_COMPANY,
				str(line, 61));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMERGENCY_CONTACT_PERSON,
				str(line, 62));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_CONTACT,
				str(line, 63));
		// vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_RELATION_TO,
		// str(line,1));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_PRIVATE_EMAIL,
				str(line, 64));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_WORKPLACE,
				str(line, 66));

		// 设置部门ID
		CYDepartmentVo dep = new CYDepartmentVo();
		dep.setCompany(str(line, 8)).setDep1(str(line, 9))
				.setDep2(str(line, 10)).setDep3(str(line, 11))
				.setDep4(str(line, 12)).setDepSmall(str(line, 13));

		Long depId = dep.match(dcd);
		if (depId == null) {
			depId = getDempartmentId(dcd);
			dep.setId(depId);
			dep.cache();
			dcd.insertTableRow(
					CYDatabaseRepositoryBase.TABLE_BIHR_D_DEPARTMENT,
					dep.getRMD());
		}

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_ID_DEP,
				depId);

	}
}
