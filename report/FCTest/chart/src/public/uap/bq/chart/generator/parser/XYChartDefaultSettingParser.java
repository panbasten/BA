package uap.bq.chart.generator.parser;

import java.util.ArrayList;
import java.util.List;

import nc.vo.pub.BusinessRuntimeException;

import org.dom4j.Element;

import uap.util.bq.chart.ChartUtil;
import uap.vo.bq.chart.model.AutoDivisionPropertyGroup;
import uap.vo.bq.chart.model.CategoriesPropertyGroup;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.UserDivisionPropertyGroup;
import uap.vo.bq.chart.model.WarningPropertyGroup;
import uap.vo.bq.chart.model.databinding.ChartXYSeriesAxis;
import uap.vo.bq.chart.model.databinding.IChartDataBinding;
import uap.vo.bq.chart.model.dataset.ChartDataset.ChartDataElement;

public class XYChartDefaultSettingParser extends DefaultSettingParser {
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
				ChartXYSeriesAxis seriesAxis = new ChartXYSeriesAxis(chartDataElement);
				seriesAxis.setDatasetCode(ChartUtil.FromLangRes(elemSeries, "datasetCode"));
				seriesAxis.setxColumnCode(ChartUtil.FromLangRes(elemSeries, "xcolumnCode"));
				seriesAxis.setxColumnType(ChartUtil.FromLangRes(elemSeries, "xColumnType"));
				seriesAxis.setyColumnCode(ChartUtil.FromLangRes(elemSeries, "ycolumnCode"));
				/*
				 * String isMeasure = DefaultUtil.FromLangRes(elemSeries,
				 * "isMeasure");
				 * seriesAxis.setMeasure(ChartUtil.isTrue(isMeasure, false));
				 */
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

	/**
	 * parser DefaultSetting/PropertyGroup 节点
	 * 
	 * @param elemItem
	 * @return PropertyGroup 对象
	 */
	/*@Override
	public PropertyGroup parserPropertyGroup(Element elemItem) {
		try {
			PropertyGroup defGroup = null;
			String tagName = elemItem.getName();
			String lowTagName = tagName.toLowerCase();
			if (lowTagName.indexOf("propertygroup") != -1) {
				String refCode = getAttributeValue(elemItem, "refCode");

				if (elemItem.getName().equals("warningPropertyGroup")) {
					defGroup = new WarningPropertyGroup(refCode);
				} else if (elemItem.getName().equals("autoDivisionPropertyGroup")) {
					defGroup = new AutoDivisionPropertyGroup(refCode);
				} else if (elemItem.getName().equals("userDivisionPropertyGroup")) {
					defGroup = new UserDivisionPropertyGroup(refCode);
				} else if (elemItem.getName().equals("categoriesPropertyGroup")) {
					defGroup = new CategoriesPropertyGroup(refCode);
				} else {
					defGroup = new PropertyGroup(refCode);
				}

				List<Element> propertyList = elemItem.elements();
				for (Element elemProperty : propertyList) {
					Property property = new Property(elemProperty.attributeValue("refCode"));
					property.setCode(getAttributeValue(elemProperty, "code"));
					String mulCode = getAttributeValue(elemProperty, "mulCode");
					String value = getAttributeValue(elemProperty, "value");
					if (null != value && value.compareToIgnoreCase("true") == 0) {
						value = "1";
					} else if (null != value && value.compareToIgnoreCase("false") == 0) {
						value = "0";
					}
					property.setValue(value);
					property.setValue(mulCode);
					property.setGenerate((elemProperty.attributeValue("generate") == null || elemProperty
							.attributeValue("generate") == "true") ? true : false);
					defGroup.getProperties().add(property);
				}
			}
			return defGroup;
		} catch (Exception exception) {

			throw new BusinessRuntimeException("parser DefaultSetting/PropertyGroup obejct node failed"
					+ exception.getMessage() + ")!", exception);
		}
	}*/

}
