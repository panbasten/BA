package uap.bq.chart.generator.parser;

import java.util.List;

import nc.vo.pub.BusinessRuntimeException;

import org.dom4j.Element;

import uap.util.bq.chart.ChartUtil;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.RangeColorPropertyGroup;
import uap.vo.bq.chart.model.WarningPropertyGroup;

public class AngularGaugeDefaultSettingParser extends DefaultSettingParser {
	/**
	 * parser DefaultSetting/PropertyGroup 节点
	 * 
	 * @param elemItem
	 * @return PropertyGroup 对象
	 */
	public PropertyGroup parserPropertyGroup(Element elemItem) {
		try {
			PropertyGroup defGroup = null;
			String tagName = elemItem.getName();
			String lowTagName = tagName.toLowerCase();
			if (lowTagName.indexOf("propertygroup") != -1) {
				String refCode = getAttributeValue(elemItem,
						"refCode");

				if (elemItem.getName().equals("warningPropertyGroup")) {
					defGroup = new WarningPropertyGroup(refCode);
				}  else if (elemItem.getName().equals(
						"rangeColorPropertyGroup")) {
					defGroup = new RangeColorPropertyGroup(refCode);
				} else {
					defGroup = new PropertyGroup(refCode);
				}

				List<Element> propertyList = elemItem.elements();
				for (Element elemProperty : propertyList) {
					Property property = new Property(
							elemProperty.attributeValue("refCode"));
					property.setCode(getAttributeValue(
							elemProperty, "code"));
					String mulCode = ChartUtil.FromLangRes(elemProperty, "mulCode");
					String value = ChartUtil.FromLangRes(elemProperty, "value");
					if (null != value
							&& value.compareToIgnoreCase("true") == 0) {
						value = "1";
					} else if (null != value
							&& value.compareToIgnoreCase("false") == 0) {
						value = "0";
					}
					//一定先设置value 再设置 mulCode
					property.setValue(value);
					property.setMulCode(mulCode);
					property.setGenerate((elemProperty
							.attributeValue("generate") == null || elemProperty
							.attributeValue("generate") == "true") ? true
							: false);
					defGroup.getProperties().add(property);
				}
			}
			return defGroup;
		} catch (Exception exception) {

			throw new BusinessRuntimeException(
					"parser DefaultSetting/PropertyGroup obejct node failed"
							+ exception.getMessage() + ")!", exception);
		}
	}

}
