package uap.bq.chart.generator;

import java.util.List;

import uap.bq.chart.ChartPropConst;
import uap.bq.chart.ChartClickLinkConst.ValueChartType;
import uap.bq.chart.utils.ChartNumFormatUtils;
import uap.util.bq.chart.ToolClassDefine.DataBindingPropertyMap;
import uap.vo.bq.chart.model.DataBindingRefGroup;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.RangeColorPropertyGroup;
import uap.vo.bq.chart.model.WarningPropertyGroup;
import uap.vo.bq.chart.model.databinding.ChartSeriesAxis;
import uap.vo.bq.chart.model.dataset.ChartDataset;

public class GaugeFusionWidgetsChartDataGenerator extends LinkChartDataGenerator {
	
	

    //�˷������Ǵ洢������,����trendpoints
	@Override
	protected StringBuilder toChartTrendlines(List<PropertyGroup> propertyGroups) {
		StringBuilder preWarnings = new StringBuilder("<trendpoints>");
		for (PropertyGroup group : propertyGroups) {
			// ����type, ���뻺�棬 ����������
			if (group instanceof WarningPropertyGroup){ 
				preWarnings.append(toTrendPoints(group));
			}
		}
		preWarnings.append("</trendpoints>");
		return preWarnings;
	}
	
	protected StringBuilder toTrendPoints(PropertyGroup group) {
		StringBuilder xml = new StringBuilder();
		return xml.append("<").append("point").append(toTrendPointAttributeString(group))
				.append("/>");
	}
	
	
	protected StringBuilder toTrendPointAttributeString(PropertyGroup group) {
		StringBuilder objectXml = new StringBuilder();
		ChartNumFormatUtils.setGenerate(group);
		for (Property property : group.getProperties()) {
			if (property.getValue() != null && property.isGenerate()) {
				
				objectXml
						.append(" ")
						.append(property.getCode() == null ? "" : property
								.getCode()).append("='");
				if (isTrendPointZoomScale(property.getCode())) {
					objectXml.append(
							property.getValue() == null ? "" : this
									.getScaleValue(property.getValue(), false))
							.append("' ");
				}else {
					objectXml.append(
							property.getValue() == null ? "" : property
									.getValue()).append("' ");
				}
			}
		}
		return objectXml;
	}

	private static final String POINTSTARTVALUE = "startValue";
	private static final String POINTENDVALUE = "endValue";
	
	private boolean isTrendPointZoomScale(String code){
		return code.equals(POINTSTARTVALUE) || code.equals(POINTENDVALUE);
	}
	
	
	/**
	 * ����ͳ��ͼfusionchart��Ⱦ����
	 * 
	 * @param dataBindList
	 * @param dataBindingPropMap
	 * @param datasetMap
	 * @return StringBuilder����Ⱦ�����ַ�������������
	 * @throws ChartGenerateException
	 */
	@Override
	public StringBuilder generateLinkChartDataXml(List<DataBindingItem> dataBindingItems,
			DataBindingPropertyMap dbpMap, ChartDataset dataset)
			throws ChartGenerateException {
		StringBuilder xml = new StringBuilder();
		//userDivisionPoint.clear();
		
		xml.append(generateColorRange(super.userGroupLines)); //����userGroupLines����Ϣ����<colorRange>....</colorRange>
		xml.append(generateDials(dataBindingItems,dbpMap,dataset));   //����<dials>....</dials>
		
		return xml;
	}
	
	protected String generateDials(List<DataBindingItem> dataBindingItems,DataBindingPropertyMap dbpMap, ChartDataset dataset){
		StringBuilder dials = new StringBuilder();
		dials.append("<dials>");
		for(DataBindingItem dbi : dataBindingItems){
			ChartSeriesAxis csa ;
			if(dbi.getChartDataBinding() instanceof ChartSeriesAxis){
				csa = (ChartSeriesAxis)dbi.getChartDataBinding();
				String colcode  = csa.getColumnCode();
				String datacode = csa.getDatasetCode();
				String value = dataset.getChartBody().getChartBodyRow(0).getChartDataCell(colcode).getCaption();
				value = getScaleValue(value,false);
				if(value == null || value.equals(""))continue;    //����cellΪ�ջ���û��ֵ�����
				dials.append("<dial value='").append(value).append("' ");
				
				if(csa.isHasOperate()){
					linkSeriesAxisstack.push( new AxiscodeANDChartDataCell(colcode,dataset.getChartBody().getChartBodyRow(0).getChartDataCell(colcode))  );
					StringBuilder datasetlink = getlink(ValueChartType.VALUE_CHART_SET);
					dials.append(" link='").append(datasetlink).append("' ");
					linkSeriesAxisstack.pop();
				}
				
				DataBindingRefGroup dbrg = dbpMap.get(datacode+"-"+colcode);    //�������ݼ����е�code���DataBindingRefGroup
				if(dbrg!=null)dials.append(toDataBindingRefGroupAttributeString(dbrg));   //��dial������
				dials.append(" />");
				}
		}
		dials.append("</dials>");
		return dials.toString();
	}
	
	
	
	/**
	 * ��group�е����ԣ� ����xml�е������У�
	 * 
	 * @param group
	 * @param objectXml
	 */
	protected StringBuilder toDataBindingRefGroupAttributeString(PropertyGroup group) {
		StringBuilder objectXml = new StringBuilder();
		for (Property property : group.getProperties()) {
			if (property.getValue() != null && property.isGenerate()) {
				objectXml.append(" ").append(property.getCode()==null?"":property.getCode()).append("='")
				.append(property.getValue()==null?"":property.getValue()).append("' ");
			}
		}
		return objectXml;
	}
	
	
	//����<colorRange>��������</colorRange>
	private StringBuilder generateColorRange(List<StringBuilder> userGroupLines) {
		StringBuilder colorRange = new StringBuilder();
		
		colorRange.append("<colorRange>");
		
		for(StringBuilder sb : userGroupLines)
			colorRange.append(sb);
		
		colorRange.append("</colorRange>");
		
		return colorRange;
	}
	
	
	
	//���Ǹ���ķ���,  �˷������ڴ洢vline,���Ǵ洢color
	@Override
	protected void toChartCategoryGroupLine(List<PropertyGroup> propertyGroups) {
		userGroupLines.clear();
		//autoGroupLines.clear();

		for (PropertyGroup group : propertyGroups) {
			// ����type, ���뻺�棬 ����������
			if (group instanceof RangeColorPropertyGroup) {
				this.userGroupLines.add(toGroupLine(group,"color"));
			}
			/*else if (group instanceof AutoDivisionPropertyGroup){
				this.autoGroupLines.add(toGroupLine(group,"vLine"));
			}*/
		}
	}
	

	
}
