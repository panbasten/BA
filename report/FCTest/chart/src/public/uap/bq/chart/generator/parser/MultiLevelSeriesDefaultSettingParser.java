package uap.bq.chart.generator.parser;

import java.util.ArrayList;
import java.util.List;

import nc.vo.pub.BusinessRuntimeException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import uap.util.bq.chart.ChartUtil;
import uap.vo.bq.chart.model.databinding.IChartDataBinding;
import uap.vo.bq.chart.model.databinding.MultiLevelChartSeriesAxis;
import uap.vo.bq.chart.model.dataset.ChartDataset.ChartDataElement;

public class MultiLevelSeriesDefaultSettingParser extends DefaultSettingParser {

	/**
	 * parserdefaultSetting/categorieAxises 节点
	 * 
	 * @param elemItem
	 * @return List<IChartDataBinding> 数据集绑定关系对象列表
	 */
	@Override
	protected List<IChartDataBinding> parserSeriesAxises(Element elemItem) {
		try {
			List<IChartDataBinding> dataBindingArrayList = new ArrayList<IChartDataBinding>();
			List<Element> dataBindingList = elemItem.elements();
			for (Element series : dataBindingList) {
				if (!series.getName().equals("seriesAxise"))
					continue;
				List<Element> seriesAxise = series.elements();
				String axisecode = super.getAttributeValue(series, "code");
				int i = 1;
				for (Element elemSeries : seriesAxise) {
					if (!elemSeries.getName().equals("series"))
						continue;
					String code = super.getAttributeValue(elemItem, "code");
					String caption = super.getAttributeValue(elemItem, "name");
					String mulCode = super.getAttributeValue(elemItem, "mulCode");
					ChartDataElement chartDataElement = new ChartDataElement(code, caption, mulCode);
					MultiLevelChartSeriesAxis seriesAxis = new MultiLevelChartSeriesAxis(chartDataElement);
					seriesAxis.setDatasetCode(super.getAttributeValue(elemSeries, "datasetCode"));
					seriesAxis.setColumnCode(super.getAttributeValue(elemSeries, "columnCode"));
					String hasOperate = super.getAttributeValue(elemSeries, "hasOperate");
					seriesAxis.setHasOperate(ChartUtil.isTrue(hasOperate, false));

					seriesAxis.setAxiscode(StringUtils.isEmpty(axisecode) ? "Axis_" + i : axisecode);

					dataBindingArrayList.add(seriesAxis);
				}
				i++;
			}
			return dataBindingArrayList.size() == 0 ? null : dataBindingArrayList;
		} catch (Exception exception) {

			throw new BusinessRuntimeException("parser DefaultSetting/Datasets obejct node failed"
					+ exception.getMessage() + ")!", exception);
		}
	}

}
