package com.flywet.platform.bi.cust.p001.delegates;

import com.flywet.platform.bi.delegates.model.BIDBAdaptorInterface;
import org.pentaho.di.core.exception.KettleDatabaseException;

import java.util.List;

public interface ForecastDBAdaptor extends BIDBAdaptorInterface {

	public Object[] getExtendPredict(String title)
			throws KettleDatabaseException;

	public String getDescriptionFromExtendPredict(String title)
			throws KettleDatabaseException;

	public Long getIDFromExtendPredict(String title, String otherTitle)
			throws KettleDatabaseException;

	public List<Object[]> getAllExtendPredict() throws KettleDatabaseException;

	public List<Object[]> getAllExtendPredictEva()
			throws KettleDatabaseException;

	public List<Object[]> getMonthPredictEvaD(long year, long month)
			throws KettleDatabaseException;

	public void delMonthPredictEvaD(long year, long month)
			throws KettleDatabaseException;

	public Long getIDFromMonthPredictEva(long year, long month)
			throws KettleDatabaseException;

	public Object[] getExtendPredictEva(long year, long month)
			throws KettleDatabaseException;

	public Long getIDFromExtendPredictEva(long year, long month)
			throws KettleDatabaseException;

	public List<Object[]> getAllMonthPredictEva()
			throws KettleDatabaseException;

	public List<Object[]> getAllMonthPredictScore()
			throws KettleDatabaseException;

	public Object[] getMonthPredictScore(long year, long month)
			throws KettleDatabaseException;

	public Long getIDFromMonthPredictScore(long year, long month)
			throws KettleDatabaseException;
}
