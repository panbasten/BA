package com.flywet.platform.bi.cust.p002.services;

import org.pentaho.di.core.ConstDB;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.spreadsheet.KCell;

import com.flywet.platform.bi.cust.p002.db.CYDatabaseRepositoryBase;
import com.flywet.platform.bi.cust.p002.vo.CYEmployeeAffairVo;
import com.flywet.platform.bi.model.BIDatabaseConnectionDelegate;
import com.flywet.platform.bi.services.intf.BIDatabaseDelegates;

public class OffJob2014Deal extends HRExcelDeal {

	public OffJob2014Deal(String filename, String sheetname,
			BIDatabaseDelegates dbDelegates) {
		super(filename, sheetname, dbDelegates);
	}

	@Override
	public void dealRow(KCell[] line, BIDatabaseConnectionDelegate dcd)
			throws Exception {

		// 1.按照员工工号查询
		String empNum = str(line, 1);
		String oldEmpNum = str(line, 2);

		String sql = "SELECT "
				+ dcd.quoteField(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_ID_EMPLOYEE)
				+ ", "
				+ dcd.quoteField(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_ON_JOB_FLAG)
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

		String start = date(line, "AG");// 入职时间
		if (start == null) {
			start = date(line, "AJ");
		}
		String end = date(line, "BP"); // 离职时间

		// 如果没有查到，添加一条入职记录和一条离职记录
		if (emp == null) {
			CYEmployeeAffairVo vo = new CYEmployeeAffairVo();
			// 1.生成入职记录
			initForEntry(vo, line, dcd);
			vo.entry(start);
			vo.closeState(end);
			// 保存
//			vo.setId(getEmployeeAffairId(dcd));
//			dcd.insertTableRow(
//					CYDatabaseRepositoryBase.TABLE_BIHR_D_EMPLOYEE_AFFAIR,
//					ConstDB.getRowMetaAndData(vo.getFields()));
			dcd.commit();

			// 2.生成离职记录
			initForDeparture(vo, line, dcd);
			vo.leave(end);
			// 保存
//			vo.setId(getEmployeeAffairId(dcd));
//			dcd.insertTableRow(
//					CYDatabaseRepositoryBase.TABLE_BIHR_D_EMPLOYEE_AFFAIR,
//					ConstDB.getRowMetaAndData(vo.getFields()));
			dcd.commit();
		}
		// 如果查到
		else {
			// 如果是在职，更新关闭在职记录，添加一条离职纪录
			if ("Y".equals((String) emp[1])) {
				// 1.关闭在职记录
				CYEmployeeAffairVo vo = new CYEmployeeAffairVo();
				vo.closeState(end);
				// 更新
//				vo.setId((Long) emp[0]);
//				dcd.updateTableRow(
//						CYDatabaseRepositoryBase.TABLE_BIHR_D_EMPLOYEE_AFFAIR,
//						CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_ID_EMPLOYEE,
//						ConstDB.getRowMetaAndData(vo.getFields()));
				dcd.commit();

				// 2.生成离职记录
				vo = new CYEmployeeAffairVo();
				initForEntry(vo, line, dcd);
				initForDeparture(vo, line, dcd);
				vo.leave(end);
				// 保存
//				vo.setId(getEmployeeAffairId(dcd));
//				dcd.insertTableRow(
//						CYDatabaseRepositoryBase.TABLE_BIHR_D_EMPLOYEE_AFFAIR,
//						ConstDB.getRowMetaAndData(vo.getFields()));
				dcd.commit();
			}
			// 如果是离职，不做处理
		}

	}

	private void initForEntry(CYEmployeeAffairVo vo, KCell[] line,
			BIDatabaseConnectionDelegate dcd) throws KettleException {
		// 属性
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMP_NUM,
				str(line, "B"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_OLD_EMP_NUM,
				str(line, "C"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_NAME,
				str(line, "D"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_SEX,
				str(line, "E"));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_JOB_CODE,
				str(line, "Q"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_POSITION,
				str(line, "R"));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_ENTRY_DATE,
				date(line, "AG"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_PERIOD_END_DATE,
				date(line, "AH"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_CONTRACT_EXPIRY_DATE,
				date(line, "AI"));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_INTERNSHIP_START_DATE,
				date(line, "AJ"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_INTERNSHIP_END_DATE,
				date(line, "AK"));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMAIL,
				str(line, "AL"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_PHONE_NUM,
				str(line, "AP"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_ID_NUM,
				str(line, "AQ"));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_BIRTHDAY,
				date(line, "AR"));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_NATIVE_PLACE,
				str(line, "AS"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_DOMICILE_PLACE,
				str(line, "AT"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_NATIONAL,
				str(line, "AU"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_POLITICAL_LANDSCAPE,
				str(line, "AV"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_GRADUATED_SCHOOL,
				str(line, "AW"));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_ADMISSION_DATE,
				date(line, "AX"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_GRADUATION_DATE,
				date(line, "AY"));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_MAJAR,
				str(line, "AZ"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EDUCATION_BACKGROUND,
				str(line, "BA"));
		// vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_MARITAL_STATUS_FLAG,
		// str(line, 58));

		// vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_START_WORK_DATE,
		// date(line, 60));

		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_LAST_COMPANY,
				str(line, "BE"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_EMERGENCY_CONTACT_PERSON,
				str(line, "BG"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_CONTACT,
				str(line, "BH"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_RELATION_TO,
				str(line, "BI"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_PRIVATE_EMAIL,
				str(line, "BJ"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_WORKPLACE,
				str(line, "BN"));

		// TODO 部门
	}

	private void initForDeparture(CYEmployeeAffairVo vo, KCell[] line,
			BIDatabaseConnectionDelegate dcd) throws KettleException {
		// 属性
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_DEPARTURE_DATE,
				date(line, "BP"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_DEPARTURE_CATEGORY_1,
				str(line, "BR"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_DEPARTURE_CATEGORY_2,
				str(line, "BS"));
		// vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_PAYMENT,
		// d(line, 1));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_DEPARTURE_REASON_1,
				str(line, "BU"));
		// vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_DEPARTURE_REASON_2,
		// str(line, 73));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_DEPARTURE_TARGET,
				str(line, "BV"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_PAY_RISE,
				str(line, "BW"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_JOB_CHANGEE,
				str(line, "BX"));
		vo.put(CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_COMPLETED_DATE,
				date(line, "BY"));
	}

}
