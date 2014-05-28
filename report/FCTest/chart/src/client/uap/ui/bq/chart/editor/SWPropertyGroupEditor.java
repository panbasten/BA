package uap.ui.bq.chart.editor;

import uap.vo.bq.chart.model.ChartModel;


public interface SWPropertyGroupEditor {
	public ChartModel setChartModel();
	public void setValue(String propertyName, Float value);
	public void setPropertyGroupName(String name);
	public void setPropertyNames(String[] names);
}
