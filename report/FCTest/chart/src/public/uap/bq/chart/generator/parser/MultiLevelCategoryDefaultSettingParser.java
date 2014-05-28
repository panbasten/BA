package uap.bq.chart.generator.parser;

import java.util.ArrayList;
import java.util.List;

import nc.vo.pub.BusinessRuntimeException;

import org.dom4j.Element;

import uap.vo.bq.chart.model.OrderEnum;
import uap.vo.bq.chart.model.databinding.IChartDataBinding;
import uap.vo.bq.chart.model.databinding.MultiLevelChartCategoryAxis;
import uap.vo.bq.chart.model.dataset.ChartDataset.ChartDataElement;

public class MultiLevelCategoryDefaultSettingParser extends DefaultSettingParser {

	public MultiLevelCategoryDefaultSettingParser() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * parserdefaultSetting/categorieAxises 节点
	 * 
	 * @param elemItem
	 * @return List<DataBinding> 数据集绑定关系对象列表
	 */
	protected List<IChartDataBinding> parserCategorieAxises(Element elemItem) {
		try {
			List<IChartDataBinding> dataBindingArrayList = new ArrayList<IChartDataBinding>();
			List<Element> dataBindingList = elemItem.elements();
			for (Element elemCategory : dataBindingList) {
				IChartDataBinding axis = parserMultiLevelCategorieAxise(elemCategory);
				dataBindingArrayList.add(axis);

			}
			return dataBindingArrayList.size() == 0 ? null : dataBindingArrayList;
		} catch (Exception exception) {

			throw new BusinessRuntimeException("parser DefaultSetting/Datasets obejct node failed"
					+ exception.getMessage() + ")!", exception);
		}
	}

	protected IChartDataBinding parserMultiLevelCategorieAxise(Element elemItem) {

		String code = super.getAttributeValue(elemItem, "code");
		String caption = super.getAttributeValue(elemItem, "name");
		String mulCode = super.getAttributeValue(elemItem, "mulCode");
		ChartDataElement chartDataElement = new ChartDataElement(code, caption, mulCode);
		MultiLevelChartCategoryAxis axis = new MultiLevelChartCategoryAxis(chartDataElement);
		axis.setDatasetCode(super.getAttributeValue(elemItem, "datasetCode"));
		axis.setColumnCode(super.getAttributeValue(elemItem, "columnCode"));
		axis.setOrder(OrderEnum.genOrder(super.getAttributeValue(elemItem, "order")));

		List<Element> list = elemItem.elements();
		for (Element elem : list) {
			if (elem.getName().equals("category")) {
				axis.setSonCategoryAxis((MultiLevelChartCategoryAxis) parserMultiLevelCategorieAxise(elem));
				return axis;
			}
		}

		axis.setSonCategoryAxis(null);
		return axis;
	}

}
