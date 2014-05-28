package uap.bq.chart.generator.parser;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import uap.util.bq.chart.ChartUtil;
import uap.vo.bq.chart.model.databinding.ChartStackedSeriesAxis;
import uap.vo.bq.chart.model.databinding.IChartDataBinding;
import uap.vo.bq.chart.model.dataset.ChartDataset.ChartDataElement;

/**
 * 
 * @author wangqzh
 *
 */

/*
 * <seriesAxises>
		<stackSeries name="" code="">
			<item datasetCode="" code=""/>
			<item datasetCode="" code=""/>
		</stackSeries>
		<stackSeries name="" code="">
			<item datasetCode="" code=""/>
			<item datasetCode="" code=""/>
			<item datasetCode="" code=""/>
		</stackSeries>
	</seriesAxises>
 */
public class StackedChartDefaultSettingParser extends DefaultSettingParser {

	@Override
	protected List<IChartDataBinding> parserSeriesAxises(Element elemItem) {
		// TODO Auto-generated method stub
		List<IChartDataBinding> dataBindings = new ArrayList<IChartDataBinding>();
		List<Element> dataBindingList = elemItem.elements();
		for (Element elemSeries : dataBindingList) {
			if (elemSeries.getName().equals("stackSeries")){
				String code = super.getAttributeValue(elemItem, "code");
				String caption = super.getAttributeValue(elemItem, "name");
				String mulCode = super.getAttributeValue(elemItem, "mulCode");
				ChartDataElement chartDataElement = new ChartDataElement(code, caption, mulCode);
				ChartStackedSeriesAxis axis = new ChartStackedSeriesAxis (chartDataElement);
				List<IChartDataBinding> serieses = super.parserSeriesAxises(elemSeries);
				if (serieses != null){
					axis.add(serieses);
				}
				dataBindings.add(axis);
			}
		}
		return dataBindings;
	}

}
