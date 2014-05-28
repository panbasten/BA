package uap.itf.bq.chart;

public interface IChartConfigureService {
	
	public String[] getChartCodes();
	
	public String getChartType (String code);
	
	public String getChartGenerator (String key);

}
