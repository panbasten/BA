package uap.bq.chart.generator.parser;

import java.util.ArrayList;
import java.util.List;

import nc.vo.pub.BusinessRuntimeException;

import org.dom4j.Element;

import uap.util.bq.chart.ChartUtil;
import uap.vo.bq.chart.model.databinding.ChartXYZSeriesAxis;
import uap.vo.bq.chart.model.databinding.IChartDataBinding;
import uap.vo.bq.chart.model.dataset.ChartDataset.ChartDataElement;

public class XYZChartDefaultSettingParser extends XYChartDefaultSettingParser {

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
			for (Element elemSeries : dataBindingList) {
				String code = super.getAttributeValue(elemItem, "code");
				String caption = super.getAttributeValue(elemItem, "name");
				String mulCode = super.getAttributeValue(elemItem, "mulCode");
				ChartDataElement chartDataElement = new ChartDataElement(code, caption, mulCode);
				ChartXYZSeriesAxis seriesAxis = new ChartXYZSeriesAxis(chartDataElement);
				seriesAxis.setDatasetCode(ChartUtil.FromLangRes(elemSeries, "datasetCode"));
				seriesAxis.setxColumnCode(ChartUtil.FromLangRes(elemSeries, "xcolumnCode"));
				seriesAxis.setxColumnType(ChartUtil.FromLangRes(elemSeries, "xColumnType"));
				seriesAxis.setyColumnCode(ChartUtil.FromLangRes(elemSeries, "ycolumnCode"));
				seriesAxis.setzColumnCode(ChartUtil.FromLangRes(elemSeries, "zcolumnCode"));
				seriesAxis.setNameColumnCode(ChartUtil.FromLangRes(elemSeries, "namecolumnCode"));
				String hasOperate = ChartUtil.FromLangRes(elemSeries, "hasOperate");
				seriesAxis.setHasOperate(ChartUtil.isTrue(hasOperate, false));
				dataBindingArrayList.add(seriesAxis);
			}
			return dataBindingArrayList.size() == 0 ? null : dataBindingArrayList;
		} catch (Exception exception) {
			throw new BusinessRuntimeException("parser DefaultSetting/Datasets obejct node failed"
					+ exception.getMessage() + ")!", exception);
		}

	}

}
