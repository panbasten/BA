package uap.bq.chart.generator.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.vo.pub.format.exception.FormatException;

import org.dom4j.Element;



import uap.itf.bq.chart.IChartThemeService;
import uap.util.bq.chart.DefaultUtil;
import uap.util.bq.chart.ServerInfoUtil;
import uap.vo.bq.chart.define.ChartTheme;
import uap.vo.bq.chart.define.ChartTheme.ChartPaletteColor;
import uap.vo.bq.chart.define.ChartTheme.ChartStyle;
import uap.vo.bq.chart.define.DefaultSetting;
import uap.vo.bq.chart.model.DataBindingRefGroup;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.StyleGroup;
import uap.vo.bq.chart.model.databinding.IChartDataBinding;
import uap.vo.bq.chart.model.dataset.ChartDataset;

public class DefaultSettingParser extends DataParser {

	/**
	 * parser DefaultSetting/PropertyGroup �ڵ�
	 * 
	 * @param elemItem
	 * @return PropertyGroup ����
	 */
	ChartTheme chartTheme = null;
	static IChartThemeService chartThemeSvr = null;

	static {
		if (!ServerInfoUtil.isLocalModel()){
		chartThemeSvr = NCLocator.getInstance()
				.lookup(IChartThemeService.class);
		}
	}
	
	public static void setChartThemeService (IChartThemeService chartThemeSvr){
		DefaultSettingParser.chartThemeSvr = chartThemeSvr;
	}

	public PropertyGroup parserPropertyGroup(Element elemItem) {
		return DefaultUtil.parserPropertyGroup(elemItem);
	}

	protected List<ChartDataset> parserChartDatasets(Element elemItem) {
		return DefaultUtil.parserChartDatasets(elemItem);
	}

	// <categorieAxises>
	// <category datasetDefine="defaultDataDefine" column="�·�"
	// name="�·�"></category>
	// </categorieAxises>

	//
	/**
	 * parserdefaultSetting/categorieAxises �ڵ�
	 * 
	 * @param elemItem
	 * @return List<DataBinding> ���ݼ��󶨹�ϵ�����б�
	 */
	protected List<IChartDataBinding> parserCategorieAxises(Element elemItem) {
		return DefaultUtil.parserCategorieAxises(elemItem);
	}

	//
	// <seriesAxises>
	// <series datasetDefine="defaultDataDefine" column="2005"
	// isMeasure="true" name="2005��"></series>
	// <series datasetDefine="defaultDataDefine" column="2006"
	// isMeasure="true"></series>
	// </seriesAxises>
	/**
	 * parserdefaultSetting/categorieAxises �ڵ�
	 * 
	 * @param elemItem
	 * @return List<IChartDataBinding> ���ݼ��󶨹�ϵ�����б�
	 */
	protected List<IChartDataBinding> parserSeriesAxises(Element elemItem) {
		return DefaultUtil.parserSeriesAxises(elemItem);
	}

	//
	/**
	 * parserdefaultSetting/StyleGroup �ڵ�
	 * 
	 * @param elemItem
	 * @return StyleGroup��ʽ����
	 */
	protected StyleGroup parserStyleGroup(Element elemItem) {
		return DefaultUtil.parserStyleGroup(elemItem);
	}

	//
	// <dataBindingRefGroup code="refSeries" refCode="defaultDataDefine"
	// datasetDefine="" column="">
	// <property refCode="color" value="1000"/>
	// <property refCode="alpha" value=""/>
	// <property refCode="ratio" value=""/>
	// <property refCode="showValues" value="true"/>
	// <property refCode="includeInLegend" value=""/>
	// </dataBindingRefGroup>
	/**
	 * parserdefaultSetting/dataBindingRefGroup �ڵ�
	 * 
	 * @param elemItem
	 * @return DataBindingRefGroupϵ�����Զ���
	 */
	protected DataBindingRefGroup parserDataBindingRefGroup(Element elemItem) {
		return DefaultUtil.parserDataBindingRefGroup(elemItem);
	}

	//
	/**
	 * parserdefaultSetting �ڵ�
	 * 
	 * @param elemDefault
	 * @return DefaultSettingĬ�����ݽṹ����
	 * @throws FormatException
	 */
	public DefaultSetting parserDefaultSetting(Element elemDefault)
			throws FormatException {
		try {
			DefaultSetting defaultSetting = new DefaultSetting();
			Map<String, PropertyGroup> tmpCheckGroupMap = new HashMap<String, PropertyGroup>();
			/* ��ȡĬ�����ݵ�������ʽcode */
			defaultSetting.setThemeCode(DefaultUtil.getAttributeValue(
					elemDefault, "themeCode"));

			PropertyGroup propertyGroup = null;
			for (Element elemItem : elemDefault.elements()) {
				
				String tagName = elemItem.getName();
				if (tagName.endsWith("Group")) {
					if (elemItem.getName().equals("styleGroup")) {
						propertyGroup = parserStyleGroup(elemItem);

					} else if (elemItem.getName().equals("dataBindingRefGroup")) {
						// read dataBindingGroup
						propertyGroup = parserDataBindingRefGroup(elemItem);
					} else {
						propertyGroup = parserPropertyGroup(elemItem);
					}
					//
					if (null != propertyGroup) {
						defaultSetting.addPropertyGroups(propertyGroup);
						
						tmpCheckGroupMap.put(propertyGroup.getCode(),
								propertyGroup);
					}
				} else if (tagName.endsWith("Axises")) {
					if (elemItem.getName().equals("categorieAxises")) {
						// read CategorieAxises
						List<IChartDataBinding> dataBindings = parserCategorieAxises(elemItem);
						if (null != dataBindings) {
							defaultSetting.addChartDataBindings(dataBindings);
						}
					} else if (elemItem.getName().equals("seriesAxises")) {
						// read SeriesAxis
						List<IChartDataBinding> seriesAxises = parserSeriesAxises(elemItem);
						if (null != seriesAxises) {
							defaultSetting.addChartDataBindings(seriesAxises);
						}
					}
				} else if (elemItem.getName().equals("datasets")) {
					// read dataset
					List<ChartDataset> datasetList = parserChartDatasets(elemItem);
					if (null != datasetList) {
						defaultSetting.addDatasets(datasetList);
					}
				}
			}

			// �ϲ�����������
			mergerChartThemeGroup(defaultSetting.getThemeCode(),tmpCheckGroupMap, defaultSetting.getPropertyGroupList());

			return defaultSetting;
		} catch (Exception e) {

			throw new FormatException(
					"parserDefaultSetting object dom object failed"
							+ e.getMessage() + ")!", e);
		}
		// return DefineUtil.parserDefaultSetting(elemDefault);
	}

	private void mergerChartThemeGroup(String themeCode,
			Map<String, PropertyGroup> inPropertyGroupMap, List<PropertyGroup> inPropertyGroups) {
		/* �����ȡ�������ʧ�ܣ��򲻽�������Ӧ�õĲ��� */
		if (chartThemeSvr == null) {
			return ;
		}
		/* ��ȡĬ��������ָ���������� */
		ChartTheme chartTheme = chartThemeSvr.getChartTheme(themeCode);
		if (chartTheme == null){
			return ;
		}
		/** �ϲ�ɫϵ��ʽ */
		ChartPaletteColor color = chartThemeSvr.getChartPaletteColor(chartTheme.getColorCode());
		if (null != color){

			String groupCode = color.getColorPropertyGroup().getRefCode();
			PropertyGroup propertyGroup = inPropertyGroupMap.get(groupCode);
			/* ��û����ͳ��ͼ�����е�����������ӵ�����Ĭ������ */
			if (propertyGroup == null){
				inPropertyGroups.add(color.getColorPropertyGroup());
			}
			else{
				/* �ϲ���ͬ���ԣ�׷�Ӳ�ͬ����  */
				for (Property themeProperty : color.getColorPropertyGroup().getProperties()){
					String propertyRefCode = themeProperty.getRefCode();
					Property prop = propertyGroup.getPropertyByRefCode(propertyRefCode);
					if (prop == null){
						propertyGroup.getProperties().add(themeProperty);
					}
					else{
						prop.setValue(themeProperty.getValue());
					}
				}
			}
		
		}
		
		/** �ϲ�������ʽ */
		ChartStyle chartStyle = chartThemeSvr.getChartStyle(chartTheme.getStyleCode());
		if (null != chartStyle){
			/* ��ȡ�������������� */
			List<PropertyGroup> themePropertyGroups = chartStyle.getPropertyGroups();
			if (themePropertyGroups == null){
				return ;
			}
			
			for (PropertyGroup themeGroup : themePropertyGroups ){
				String groupCode = themeGroup.getCode();
				PropertyGroup propertyGroup = inPropertyGroupMap.get(groupCode);
				/* ��û����ͳ��ͼ�����е�����������ӵ�����Ĭ������ */
				if (propertyGroup == null){
					inPropertyGroups.add(themeGroup);
				}
				else{
					/* �ϲ���ͬ���ԣ�׷�Ӳ�ͬ����  */
					for (Property themeProperty : themeGroup.getProperties()){
						String propertyRefCode = themeProperty.getRefCode();
						Property prop = propertyGroup.getPropertyByRefCode(propertyRefCode);
						if (prop == null){
							propertyGroup.getProperties().add(themeProperty);
						}
						else{
							prop.setValue(themeProperty.getValue());
						}
					}
				}
			}			
		}
		
		// 

	}

}