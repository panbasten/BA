package com.flywet.platform.bi.cust.p001.delegates;

import java.util.List;

import org.pentaho.di.core.exception.KettleDatabaseException;

import com.flywet.platform.bi.delegates.model.BIDBAdaptorInterface;

public interface ForecastDBAdaptor extends BIDBAdaptorInterface {

	public Object[] getExtendPredict(long year, long month, long xun)
			throws KettleDatabaseException;

	public String getDescriptionFromExtendPredict(long year, long month,
			long xun) throws KettleDatabaseException;

	public Long getIDFromExtendPredict(long year, long month, long xun)
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
