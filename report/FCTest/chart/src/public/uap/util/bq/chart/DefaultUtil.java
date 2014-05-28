package uap.util.bq.chart;

import java.util.ArrayList;
import java.util.List;

import nc.vo.pub.BusinessRuntimeException;

import org.dom4j.Attribute;
import org.dom4j.Element;

import uap.vo.bq.chart.model.AutoDivisionPropertyGroup;
import uap.vo.bq.chart.model.DataBindingRefGroup;
import uap.vo.bq.chart.model.OrderEnum;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.StyleGroup;
import uap.vo.bq.chart.model.UserDivisionPropertyGroup;
import uap.vo.bq.chart.model.WarningPropertyGroup;
import uap.vo.bq.chart.model.databinding.ChartCategoryAxis;
import uap.vo.bq.chart.model.databinding.ChartSeriesAxis;
import uap.vo.bq.chart.model.databinding.IChartDataBinding;
import uap.vo.bq.chart.model.dataset.ChartBody;
import uap.vo.bq.chart.model.dataset.ChartBodyRow;
import uap.vo.bq.chart.model.dataset.ChartDataCell;
import uap.vo.bq.chart.model.dataset.ChartDataCell.AllChartDataCell;
import uap.vo.bq.chart.model.dataset.ChartDataset;
import uap.vo.bq.chart.model.dataset.ChartDataset.ChartDataElement;
import uap.vo.bq.chart.model.dataset.ChartHeader;
import uap.vo.bq.chart.model.dataset.ChartHeaderCell;

public class DefaultUtil {
	public static String getAttributeValue(Element elem, String attri) {
		if (elem == null)
			return null;
		Attribute objAttri = elem.attribute(attri);

		if (objAttri == null)
			return null;

		return objAttri.getText();
	}

	public static PropertyGroup parserPropertyGroup(Element elemItem) {
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
				} else {
					defGroup = new PropertyGroup(refCode);
				}

				List<Element> propertyList = elemItem.elements();
				for (Element elemProperty : propertyList) {
					Property property = new Property(elemProperty.attributeValue("refCode"));
					property.setCode(getAttributeValue(elemProperty, "code"));
					String mulCode = ChartUtil.FromLangRes(elemProperty, "mulCode");
					String value = ChartUtil.FromLangRes(elemProperty, "value");
					if (null != value && value.compareToIgnoreCase("true") == 0) {
						value = "1";
					} else if (null != value && value.compareToIgnoreCase("false") == 0) {
						value = "0";
					}
					// 一定先设置value 再设置 mulCode
					property.setValue(value);
					property.setMulCode(mulCode);
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
	}

	public static List<ChartDataset> parserChartDatasets(Element elemItem) {
		try {
			// read dataset .
			List<ChartDataset> datasets = new ArrayList<ChartDataset>();
			List<Element> datasetList = elemItem.elements();
			for (Element elemDataset : datasetList) {
				String datasetCode = getAttributeValue(elemDataset, "code");
				String datasetName = ChartUtil.FromLangRes(elemDataset, "name");
				String datasetmulCode = ChartUtil.FromLangRes(elemDataset, "mulCode");

				ChartDataset dataset = new ChartDataset(new ChartDataElement(datasetCode, datasetName, datasetmulCode));
				for (Element elemSubItem : elemDataset.elements()) {
					if (elemSubItem.getName().equals("header")) {
						ChartHeader chartHeader = new ChartHeader(new ChartDataElement());
						// read datasetdefine
						Element elemDatasetDefine = elemSubItem;
						for (Element elemColumnDefine : elemDatasetDefine.elements()) {
							String code = getAttributeValue(elemColumnDefine, "code");
							String name = ChartUtil.FromLangRes(elemColumnDefine, "name");
							String mulCode = ChartUtil.FromLangRes(elemColumnDefine, "mulCode");
							String type = getAttributeValue(elemColumnDefine, "type");
							chartHeader.addHeaderCell(new ChartHeaderCell(new ChartDataElement(code, name, mulCode),
									type));
						}
						dataset.setChartHeader(chartHeader);
					} else if (elemSubItem.getName().equals("body")) {
						// body
						Element elemBody = elemSubItem;
						String code = getAttributeValue(elemBody, "code");
						String mulCode = getAttributeValue(elemBody, "mulCode");
						String name = ChartUtil.FromLangRes(elemBody, "name");
						ChartBody chartBody = new ChartBody(new ChartDataElement(code, name,mulCode));
						List<Element> elemRows = elemBody.elements();
						// row
						for (Element elemRow : elemRows) {
							code = getAttributeValue(elemRow, "code");
							mulCode = getAttributeValue(elemBody, "mulCode");
							name = ChartUtil.FromLangRes(elemRow, "name");
							ChartBodyRow chartBodyRow = new ChartBodyRow(new ChartDataElement(code, name,mulCode));
							// cell
							for (Element elemCell : elemRow.elements()) {
								code = getAttributeValue(elemCell, "code");
								mulCode = getAttributeValue(elemBody, "mulCode");
								String refcode = getAttributeValue(elemCell, "refCode");
								name = elemCell.getText();
								String sIsAll = getAttributeValue(elemCell, "isAll");
								ChartDataCell acd = null;
								if (ChartUtil.isTrue(sIsAll, false)) {
									acd = new AllChartDataCell(refcode, new ChartDataElement(code, name,mulCode));
								} else {
									acd = new ChartDataCell(refcode, new ChartDataElement(code, name,mulCode));
								}
								chartBodyRow.addChartDataCell(acd);
							}
							chartBody.addChartBodyRow(chartBodyRow);
						}
						dataset.setChartBody(chartBody);
					}
				}
				datasets.add(dataset);
			}
			return datasets.size() == 0 ? null : datasets;

		} catch (Exception exception) {

			throw new BusinessRuntimeException("parser DefaultSetting/Datasets obejct node failed"
					+ exception.getMessage() + ")!", exception);
		}
	}

	public static List<IChartDataBinding> parserCategorieAxises(Element elemItem) {
		try {
			List<IChartDataBinding> dataBindingArrayList = new ArrayList<IChartDataBinding>();
			List<Element> dataBindingList = elemItem.elements();
			for (Element elemCategory : dataBindingList) {
				String code = getAttributeValue(elemItem, "code");
				String caption = getAttributeValue(elemItem, "name");
				String mulCode = getAttributeValue(elemItem, "mulCode");
				ChartDataElement chartDataElement = new ChartDataElement(code, caption, mulCode);
				ChartCategoryAxis axis = new ChartCategoryAxis(chartDataElement);
				axis.setDatasetCode(getAttributeValue(elemCategory, "datasetCode"));
				axis.setColumnCode(getAttributeValue(elemCategory, "columnCode"));
				axis.setOrder(OrderEnum.genOrder(getAttributeValue(elemCategory, "order")));
				String hasOperate = getAttributeValue(elemCategory, "hasOperate");
				axis.setHasOperate(ChartUtil.isTrue(hasOperate, false));
				dataBindingArrayList.add(axis);

			}
			return dataBindingArrayList.size() == 0 ? null : dataBindingArrayList;
		} catch (Exception exception) {

			throw new BusinessRuntimeException("parser DefaultSetting/Datasets obejct node failed"
					+ exception.getMessage() + ")!", exception);
		}
	}

	public static List<IChartDataBinding> parserSeriesAxises(Element elemItem) {
		try {
			List<IChartDataBinding> dataBindingArrayList = new ArrayList<IChartDataBinding>();
			List<Element> dataBindingList = elemItem.elements();
			for (Element elemSeries : dataBindingList) {
				String code = getAttributeValue(elemItem, "code");
				String caption = getAttributeValue(elemItem, "name");
				String mulCode = getAttributeValue(elemItem, "mulCode");
				ChartDataElement chartDataElement = new ChartDataElement(code, caption, mulCode);
				ChartSeriesAxis seriesAxis = new ChartSeriesAxis(chartDataElement);
				seriesAxis.setDatasetCode(getAttributeValue(elemSeries, "datasetCode"));
				seriesAxis.setColumnCode(getAttributeValue(elemSeries, "columnCode"));
				String isMeasure = getAttributeValue(elemSeries, "isMeasure");
				seriesAxis.setMeasure(ChartUtil.isTrue(isMeasure, false));
				String hasOperate = getAttributeValue(elemSeries, "hasOperate");
				seriesAxis.setHasOperate(ChartUtil.isTrue(hasOperate, false));

				dataBindingArrayList.add(seriesAxis);
			}
			return dataBindingArrayList.size() == 0 ? null : dataBindingArrayList;
		} catch (Exception exception) {

			throw new BusinessRuntimeException("parser DefaultSetting/Datasets obejct node failed"
					+ exception.getMessage() + ")!", exception);
		}
	}

	public static StyleGroup parserStyleGroup(Element elemItem) {
		StyleGroup styleGroup = new StyleGroup(getAttributeValue(elemItem, "refCode"));
		styleGroup.setApplyObject(getAttributeValue(elemItem, "applyObject"));
		List<Element> propertyList = elemItem.elements();
		for (Element elemProperty : propertyList) {
			Property property = new Property(getAttributeValue(elemProperty, "refCode"));
			property.setCode(getAttributeValue(elemProperty, "code"));
			String mulCode = ChartUtil.FromLangRes(elemProperty, "mulCode");
			String value = ChartUtil.FromLangRes(elemProperty, "value");
			if (null != value && value.compareToIgnoreCase("true") == 0) {
				value = "1";
			} else if (null != value && value.compareToIgnoreCase("false") == 0) {
				value = "0";
			}
			property.setValue(value);
			property.setMulCode(mulCode);
			property.setGenerate((elemProperty.attributeValue("generate") == null || elemProperty
					.attributeValue("generate") == "true") ? true : false);
			styleGroup.getProperties().add(property);
		}
		return styleGroup;
	}

	public static DataBindingRefGroup parserDataBindingRefGroup(Element elemItem) {
		try {
			DataBindingRefGroup defGroup = new DataBindingRefGroup(getAttributeValue(elemItem, "refCode"));
			defGroup.setColumn(getAttributeValue(elemItem, "columnCode"));
			String code = getAttributeValue(elemItem, "code");
			defGroup.setCode(code.isEmpty() ? defGroup.getRefCode() + "_" + defGroup.getColumn() : code);
			defGroup.setDatasetDefine(getAttributeValue(elemItem, "datasetCode"));

			List<Element> propertyList = elemItem.elements();

			for (Element elemProperty : propertyList) {
				Property property = new Property(elemProperty.attributeValue("refCode"));
				property.setCode(elemProperty.attributeValue("code"));
				String mulCode = ChartUtil.FromLangRes(elemProperty, "mulCode");
				String value = ChartUtil.FromLangRes(elemProperty, "value");
				if (null != value && value.compareToIgnoreCase("true") == 0) {
					value = "1";
				} else if (null != value && value.compareToIgnoreCase("false") == 0) {
					value = "0";
				}
				property.setValue(value);
				property.setMulCode(mulCode);
				property.setGenerate((elemProperty.attributeValue("generate") == null || elemProperty
						.attributeValue("generate") == "true") ? true : false);
				defGroup.getProperties().add(property);
			}

			return defGroup;
		} catch (Exception exception) {

			throw new BusinessRuntimeException("parserdefaultSetting/dataBindingRefGroup object dom object failed"
					+ exception.getMessage() + ")!", exception);
		}
	}

}
