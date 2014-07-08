package com.flywet.platform.bi.cust.p001.delegates;

import com.flywet.platform.bi.cust.p001.db.CustomDatabaseRepositoryBase;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleDatabaseException;

import java.util.List;

public class ForecastDBAdaptorImpl extends BIAbstractDbAdaptor implements
		ForecastDBAdaptor {

	@Override
	public Object[] getExtendPredict(String title)
			throws KettleDatabaseException {
		String sql = null;
		Object[] params = null;
		if (title.indexOf(" ") > 0) {
			String t1 = title.substring(0, title.indexOf(" "));
			String t2 = title.substring(title.indexOf(" ") + 1);

			sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_DESCRIPTION)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_TITLE)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_OTHER_TITLE)
					+ " = ?";
			params = new Object[2];
			params[0] = t1;
			params[1] = t2;
		} else {
			sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_DESCRIPTION)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_TITLE)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_OTHER_TITLE)
					+ " is null";
			params = new Object[1];
			params[0] = title;
		}

		return getOneRow(sql, params);
	}

	@Override
	public String getDescriptionFromExtendPredict(String title)
			throws KettleDatabaseException {
		Object[] row = null;
		if (title.indexOf(" ") > 0) {
			String t1 = title.substring(0, title.indexOf(" "));
			String t2 = title.substring(title.indexOf(" ") + 1);

			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_DESCRIPTION)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_TITLE)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_OTHER_TITLE)
					+ " = ?";
			row = getOneRow(sql, new Object[] { t1, t2 });

		} else {
			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_DESCRIPTION)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_TITLE)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_OTHER_TITLE)
					+ " is null";
			row = getOneRow(sql, new Object[] { title });
		}

		if (row != null) {
			return (String) row[0];
		}
		return null;
	}

	@Override
	public Long getIDFromExtendPredict(String title, String otherTitle)
			throws KettleDatabaseException {
		Object[] row = null;

		if (Const.isEmpty(otherTitle)) {
			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_ID_EXTEND_PREDICT)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_TITLE)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_OTHER_TITLE)
					+ " is null";

			row = getOneRow(sql, new Object[] { title });
		} else {
			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_ID_EXTEND_PREDICT)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_TITLE)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_OTHER_TITLE)
					+ " = ?";

			row = getOneRow(sql, new Object[] { title, otherTitle });
		}

		if (row != null) {
			return (Long) row[0];
		}

		return null;
	}

	@Override
	public List<Object[]> getAllExtendPredict() throws KettleDatabaseException {
		String sql = "SELECT "
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_TITLE)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_OTHER_TITLE)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_DESCRIPTION)
				+ " FROM "
				+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT)
				+ " ORDER BY "
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_TITLE)
				+ " DESC LIMIT 20";
		return getRows(sql);
	}

	@Override
	public List<Object[]> getAllExtendPredictEva()
			throws KettleDatabaseException {
		String sql = "SELECT "
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_YEAR)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_MONTH)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S1)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S2)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S3)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S4)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S5)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S6)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S7)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S8)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S9)
				+ " FROM "
				+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT_EVA)
				+ " ORDER BY "
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_YEAR)
				+ " DESC , "
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_MONTH)
				+ " DESC";

		return getRows(sql);
	}

	@Override
	public List<Object[]> getMonthPredictEvaD(long year, long month)
			throws KettleDatabaseException {
		String sql = "SELECT "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_CITY)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S1)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S2)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S3)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S4)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S5)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S6)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S7)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S8)
				+ " FROM "
				+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_EVA_D)
				+ " WHERE "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_YEAR)
				+ " = ? AND "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_MONTH)
				+ " = ?";

		return getRows(sql, new Object[] { year, month });
	}

	@Override
	public void delMonthPredictEvaD(long year, long month)
			throws KettleDatabaseException {
		String delSql = "DELETE FROM "
				+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_EVA_D)
				+ " WHERE "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_YEAR)
				+ " = ? AND "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_MONTH)
				+ " = ?";
		execSql(delSql, new Object[] { year, month });
	}

	@Override
	public Long getIDFromMonthPredictEva(long year, long month)
			throws KettleDatabaseException {
		String sql = "SELECT "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_ID_MONTH_PREDICT_EVA)
				+ " FROM "
				+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_EVA)
				+ " WHERE "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_YEAR)
				+ " = ? AND "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_MONTH)
				+ " = ?";

		Object[] row = getOneRow(sql, new Object[] { year, month });
		if (row != null) {
			return (Long) row[0];
		}

		return null;
	}

	@Override
	public Object[] getExtendPredictEva(long year, long month)
			throws KettleDatabaseException {
		String sql = "SELECT "
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S1)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S2)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S3)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S4)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S5)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S6)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S7)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S8)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S9)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_DESCRIPTION)
				+ " FROM "
				+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT_EVA)
				+ " WHERE "
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_YEAR)
				+ " = ? AND "
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_MONTH)
				+ " = ?";

		return getOneRow(sql, new Object[] { year, month });
	}

	@Override
	public Long getIDFromExtendPredictEva(long year, long month)
			throws KettleDatabaseException {
		String sql = "SELECT "
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_ID_MONTH_PREDICT_EVA)
				+ " FROM "
				+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT_EVA)
				+ " WHERE "
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_YEAR)
				+ " = ? AND "
				+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_MONTH)
				+ " = ?";

		Object[] row = getOneRow(sql, new Object[] { year, month });
		if (row != null) {
			return (Long) row[0];
		}
		return null;
	}

	@Override
	public List<Object[]> getAllMonthPredictEva()
			throws KettleDatabaseException {
		String sql = "SELECT "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_YEAR)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_MONTH)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S1)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S2)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S3)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S4)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S5)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S6)
				+ " FROM "
				+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_EVA)
				+ " ORDER BY "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_YEAR)
				+ " DESC , "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_MONTH)
				+ " DESC";

		return getRows(sql);
	}

	@Override
	public List<Object[]> getAllMonthPredictScore()
			throws KettleDatabaseException {
		String sql = "SELECT "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_YEAR)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_MONTH)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S1)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S2)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S3)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S4)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S5)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S6)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S7)
				+ " FROM "
				+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_SCORE)
				+ " ORDER BY "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_YEAR)
				+ " DESC , "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_MONTH)
				+ " DESC";

		return getRows(sql);
	}

	@Override
	public Object[] getMonthPredictScore(long year, long month)
			throws KettleDatabaseException {
		String sql = "SELECT "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S1)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S2)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S3)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S4)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S5)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S6)
				+ ","
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S7)
				+ " FROM "
				+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_SCORE)
				+ " WHERE "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_YEAR)
				+ " = ? AND "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_MONTH)
				+ " = ?";

		return getOneRow(sql, new Object[] { year, month });
	}

	@Override
	public Long getIDFromMonthPredictScore(long year, long month)
			throws KettleDatabaseException {
		String sql = "SELECT "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_ID_MONTH_PREDICT_SCORE)
				+ " FROM "
				+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_SCORE)
				+ " WHERE "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_YEAR)
				+ " = ? AND "
				+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_MONTH)
				+ " = ?";

		Object[] row = getOneRow(sql, new Object[] { year, month });
		if (row != null) {
			return (Long) row[0];
		}
		return null;
	}
}
