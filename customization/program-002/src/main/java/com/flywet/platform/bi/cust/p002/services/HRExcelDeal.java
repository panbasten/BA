package com.flywet.platform.bi.cust.p002.services;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.spreadsheet.KCell;
import org.pentaho.di.core.spreadsheet.KSheet;
import org.pentaho.di.core.spreadsheet.KWorkbook;
import org.pentaho.di.trans.steps.excelinput.SpreadSheetType;
import org.pentaho.di.trans.steps.excelinput.WorkbookFactory;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.DateUtils;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.cust.p002.db.CYDatabaseRepositoryBase;
import com.flywet.platform.bi.model.BIDatabaseConnectionDelegate;
import com.flywet.platform.bi.services.intf.BIDatabaseDelegates;

public abstract class HRExcelDeal {

	private final Logger log = Logger.getLogger(HRExcelDeal.class);

	String filename;
	String sheetname;
	BIDatabaseDelegates dbDelegates;

	public HRExcelDeal(String filename, String sheetname,
			BIDatabaseDelegates dbDelegates) {
		this.filename = filename;
		this.sheetname = sheetname;
		this.dbDelegates = dbDelegates;
	}

	public void deal() throws Exception {
		KWorkbook workbook = WorkbookFactory.getWorkbook(SpreadSheetType.POI,
				filename, "GBK");

		KSheet sheet = workbook.getSheet(sheetname);

		int rownr = 1;
		boolean isEmpty = false;
		KCell[] line = null;

		BIDatabaseConnectionDelegate dcd = dbDelegates
				.getDatabaseConnectionDelegate(3L);

		try {
			dcd.connect();

			while (!isEmpty) {
				try {
					line = sheet.getRow(rownr++);
				} catch (Exception e) {
					break;
				}

				isEmpty = isLineEmpty(line);

				if (!isEmpty) {
					try {
						dealRow(line, dcd);
					} catch (Exception e) {
						log.error("------err----:" + str(line, 1));
					}
				}
			}

		} catch (KettleException e) {
			throw new BIException(e);
		} finally {
			dcd.disconnect();
		}
	}

	public abstract void dealRow(KCell[] line, BIDatabaseConnectionDelegate dcd)
			throws Exception;

	public long getEmployeeAffairId(BIDatabaseConnectionDelegate dcd)
			throws KettleException {
		return dcd
				.getNextBatchId(
						CYDatabaseRepositoryBase.TABLE_BIHR_D_EMPLOYEE_AFFAIR,
						CYDatabaseRepositoryBase.FIELD_BIHR_D_EMPLOYEE_AFFAIR_ID_EMPLOYEE);
	}

	public long getDempartmentId(BIDatabaseConnectionDelegate dcd)
			throws KettleException {
		return dcd.getNextBatchId(
				CYDatabaseRepositoryBase.TABLE_BIHR_D_DEPARTMENT,
				CYDatabaseRepositoryBase.FIELD_BIHR_D_DEPARTMENT_ID_DEPARTMENT);
	}

	public boolean isLineEmpty(KCell[] line) {
		if (line.length == 0)
			return true;

		boolean isEmpty = true;
		for (int i = 0; i < line.length && isEmpty; i++) {
			if (line[i] != null && !Const.isEmpty(line[i].getContents()))
				isEmpty = false;
		}
		return isEmpty;
	}

	public String date(KCell[] line, String idx) {
		return date(line, Utils.s26t10(idx) - 1);
	}

	public String date(KCell[] line, int idx) {
		String d = str(line, idx);
		if (d == null || d.length() != 10) {
			return null;
		}

		return d.substring(0, 4) + d.substring(5, 7) + d.substring(8, 10);
	}

	public String str(KCell[] line, String idx) {
		return str(line, Utils.s26t10(idx) - 1);
	}

	public String str(KCell[] line, int idx) {
		if (line == null || line.length <= idx) {
			return null;
		}

		KCell cell = line[idx];
		if (cell == null)
			return null;

		Object obj = cell.getValue();
		if (obj == null) {
			return null;
		} else if (obj instanceof String) {
			return Const.removeCRLF(Const.trim((String) obj));
		} else if (obj instanceof Boolean) {
			return ((Boolean) obj) ? "Y" : "N";
		} else if (obj instanceof Date) {
			return DateUtils.formatDate((Date) obj,
					DateUtils.GENERALIZED_DATE_FORMAT);
		} else if (obj instanceof Double) {
			BigDecimal bd = new BigDecimal((Double) obj);
			bd.setScale(0);
			return bd.toPlainString();
		} else {
			return String.valueOf(obj);
		}
	}

}
