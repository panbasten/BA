package uap.vo.bq.chart.model;

public interface ChartModelListener {
	void onChange(ChartModel chartModel) throws ChangeHandleException, ClassNotFoundException;
}
