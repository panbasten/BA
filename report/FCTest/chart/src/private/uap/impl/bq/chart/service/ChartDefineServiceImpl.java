// ChartDefineServiceImpl.java
package uap.impl.bq.chart.service;

import java.io.IOException;

import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pub.format.exception.FormatException;
import uap.impl.bq.chart.define.ChartDefineFactory;
import uap.itf.bq.chart.IChartDefineService;
import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.define.ChartDefineDigest;

public class ChartDefineServiceImpl implements IChartDefineService{

	@Override
	public ChartDefine getChartDefine(String chartDefineCode) {
		
		try {
			return ChartDefineFactory.getInstance().getChartDefine(chartDefineCode);
		} catch (FormatException e) {
			throw new BusinessRuntimeException("get chart object failed("
					+ e.getMessage() + ")!", e);
			
		} catch (IOException e) {
			throw new BusinessRuntimeException("get chart object failed("
					+ e.getMessage() + ")!", e);
		}
	
	}

	@Override
	public ChartDefine[] getChartDefines(String[] chartDefineCodes) {
		// TODO Auto-generated method stub
		try {
			return ChartDefineFactory.getInstance().getChartDefines(chartDefineCodes);
		} catch (FormatException e) {
			throw new BusinessRuntimeException("get chart object array failed"
					+ e.getMessage() + ")!", e);
		}catch (IOException e) {
			throw new BusinessRuntimeException("get chart object array failed"
					+ e.getMessage() + ")!", e);
		}
	
	}

	@Override
	public ChartDefineDigest[] getChartDefineDigests() {
		// TODO Auto-generated method stub
		try {
			return ChartDefineFactory.getInstance().getChartDefineDigests();
		} catch (FormatException e) {
			throw new BusinessRuntimeException("get chart object abstract failed"
					+ e.getMessage() + ")!", e);
		} catch (IOException e) {
			throw new BusinessRuntimeException("get chart object abstract failed"
					+ e.getMessage() + ")!", e);
		}
	}
}
