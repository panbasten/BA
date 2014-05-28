package uap.bq.chart.generator;

import java.util.List;

import uap.util.bq.chart.ToolClassDefine.DataBindingPropertyMap;
import uap.vo.bq.chart.model.DataBindingRefGroup;
import uap.vo.bq.chart.model.databinding.ChartSeriesAxis;
import uap.vo.bq.chart.model.dataset.ChartDataset;

public class LinearGaugeFusionWidgetsChartDataGenerator extends
		GaugeFusionWidgetsChartDataGenerator {
	
	protected String generateDials(List<DataBindingItem> dataBindingItems,DataBindingPropertyMap dbpMap, ChartDataset dataset){
		StringBuilder dials = new StringBuilder();
		dials.append("<pointers>");
		for(DataBindingItem dbi : dataBindingItems){
			ChartSeriesAxis csa ;
			if(dbi.getChartDataBinding() instanceof ChartSeriesAxis){
				csa = (ChartSeriesAxis)dbi.getChartDataBinding();
				String colcode  = csa.getColumnCode();
				String datacode = csa.getDatasetCode();
				String value = dataset.getChartBody().getChartBodyRow(0).getChartDataCell(colcode).getCaption();
				value = getScaleValue(value, false);
				if(value == null || value.equals(""))continue;    //����cellΪ�ջ���û��ֵ�����
				dials.append("<pointer value='").append(value).append("' ");
				DataBindingRefGroup dbrg = dbpMap.get(datacode+"-"+colcode);    //�������ݼ����е�code���DataBindingRefGroup
				if(dbrg!=null)dials.append(toDataBindingRefGroupAttributeString(dbrg));   //��dial������
				dials.append(" />");
				}
		}
		dials.append("</pointers>");
		return dials.toString();
	}

}
