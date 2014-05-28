package uap.bq.chart.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import uap.bq.chart.ChartClickLinkConst.ValueChartType;
import uap.util.bq.chart.ToolClassDefine.DataBindingPropertyMap;
import uap.vo.bq.chart.model.DataBindingRefGroup;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.databinding.ChartCategoryAxis;
import uap.vo.bq.chart.model.databinding.ChartXYSeriesAxis;
import uap.vo.bq.chart.model.databinding.ChartXYZSeriesAxis;
import uap.vo.bq.chart.model.dataset.ChartBodyRow;
import uap.vo.bq.chart.model.dataset.ChartDataset;

public class MultiSeriesBubbleChartDataGenerator extends
		MultiSeriesScatterChartDataGenerator {
	
	
	private String setalpha = null;
	
	@Override
	protected StringBuilder generateAdataset(ChartXYSeriesAxis cxysa_xy,List<ChartBodyRow> chartBodyRows){
		ChartXYZSeriesAxis cxysa = (ChartXYZSeriesAxis) cxysa_xy;
		String xcolumnCode = cxysa.getxColumnCode();
		categorystack.push(xcolumnCode);
		String ycolumnCode = cxysa.getyColumnCode();
		String zcolumnCode = cxysa.getzColumnCode();   //z轴绑定的行号yColumnCode
		String namecolumnCode = cxysa.getNameColumnCode();   //name轴绑定的行号yColumnCode
		StringBuilder datasetxml = new StringBuilder();
		for (ChartBodyRow cbr : chartBodyRows){
			String xvalue = cbr.getChartDataCell(xcolumnCode).getCaption();
		    String yvalue = cbr.getChartDataCell(ycolumnCode).getCaption();
		    String zvalue = cbr.getChartDataCell(zcolumnCode).getCaption();
		    yvalue = getScaleValue(yvalue,false);
		    zvalue = getScaleValue(zvalue,false);
		    if( StringUtils.isEmpty(xvalue) || StringUtils.isEmpty(yvalue) || StringUtils.isEmpty(zvalue) ) continue;
		    if(super.xType.equals("Number"))
		    	datasetxml.append("<set x='").append(xvalue).append("' y='").append(yvalue).append("' z='").append(zvalue).append("' ");
		    else if(xType.equals("String"))
		    	datasetxml.append("<set x='").append(labelmapvalue.get(xvalue) ).append("' y='").append(yvalue).append("' z='").append(zvalue).append("' ");
		    //datasetxml.append(bCategoryNameInToolTip?"":"toolText='"+yvalue+","+zvalue+"' ");
		    if(setalpha!=null && !setalpha.equals("")){
		    	datasetxml.append(" alpha='").append(setalpha).append("' ");
		    }
		    if (cxysa.isHasOperate()) {
				linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(
						xcolumnCode, cbr.getChartDataCell(xcolumnCode)));
				linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(
						ycolumnCode, cbr.getChartDataCell(ycolumnCode)));
				linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(
						zcolumnCode, cbr.getChartDataCell(zcolumnCode)));
				if (crossAxis != null && !crossAxis.getColumnCode().isEmpty())
					linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(
							crossAxis.getColumnCode(),
							cbr.getChartDataCell(crossAxis.getColumnCode())));
				StringBuilder link = getlink(ValueChartType.VALUE_CHART_SET);
				if (link != null && !link.toString().equals(""))
					datasetxml.append(" link='").append(link).append("' ");
				linkCategoryAixsstack.pop();
				linkSeriesAxisstack.pop();
				linkSeriesAxisstack.pop();
				if (crossAxis != null && !crossAxis.getColumnCode().isEmpty())
					linkCategoryAixsstack.pop();
			}
		    
		    if( cbr.getChartDataCell(namecolumnCode)==null || StringUtils.isEmpty(cbr.getChartDataCell(namecolumnCode).getCaption()) ) 
		    	datasetxml.append(" />");
		    else {
		    	 String namevalue = cbr.getChartDataCell(namecolumnCode).getCaption();
		    	 datasetxml.append(" name='").append(namevalue).append("' />");
		    }
		}
		categorystack.pop();
		return datasetxml;
	}
	
	
	
	protected StringBuilder generatedatasets( List<ChartXYSeriesAxis>  xySeries,ChartCategoryAxis cca,DataBindingPropertyMap dbpMap, ChartDataset dataset) {
		StringBuilder datasetsxml = new StringBuilder();
		if(cca == null || cca.getColumnCode().isEmpty()){
			for (ChartXYSeriesAxis cxysa : xySeries){
				ChartXYZSeriesAxis cxyzsa = (ChartXYZSeriesAxis) cxysa;
				datasetsxml.append("<dataset seriesName='").append(getdatasetnaem(cxysa,dataset)).append("' ");
				DataBindingRefGroup dbrg = dbpMap.get(dataset.getCode()+"-"+DataBindingRefGroup.COMMONCOLUMNCODE);    //根据数据集和列的code获得DataBindingRefGroup
				if(dbrg==null)dbrg = dbpMap.get(dataset.getCode()+"-"+cxysa.getCode());    //根据数据集和列的code获得DataBindingRefGroup
				if(dbrg!=null){
					setAlphaFrom(dbrg);
					datasetsxml.append(toDataBindingRefGroupAttributeString(dbrg));   //给<dataset ....>加属性
				}
				if (cxysa.isHasOperate()) {
					linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(cxyzsa
							.getyColumnCode(), null));
					linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(cxyzsa
							.getzColumnCode(), null));
					StringBuilder datasetlink = getlink(ValueChartType.VALUE_CHART_LEGEND);
					datasetsxml.append(" link='").append(datasetlink).append("' ");
					linkSeriesAxisstack.pop();
					linkSeriesAxisstack.pop();
				}
				datasetsxml.append(" >");
				datasetsxml.append(generateAdataset(cxysa,Arrays.asList(dataset.getChartBody().getChartBodyRows() ) ) );
				datasetsxml.append("</dataset>");
				setalpha = null;
			}
			return datasetsxml;
		}

		String colcode = cca.getColumnCode();
		HashMap<String,List<ChartBodyRow>> valueMaplist = new HashMap<String,List<ChartBodyRow>>();
		for (ChartBodyRow cbr : dataset.getChartBody().getChartBodyRows()){
			String categoryvalue = cbr.getChartDataCell(colcode).getCaption();
			if(valueMaplist.containsKey(categoryvalue))valueMaplist.get(categoryvalue).add(cbr);
			else{
				valueMaplist.put(categoryvalue, new ArrayList<ChartBodyRow>());
				valueMaplist.get(categoryvalue).add(cbr);
			}
		}

		for(String key : valueMaplist.keySet()){
			for (ChartXYSeriesAxis cxysa : xySeries){
				ChartXYZSeriesAxis cxyzsa = (ChartXYZSeriesAxis) cxysa;
				datasetsxml.append("<dataset seriesName='");
				datasetsxml.append(key+": ").append(getdatasetnaem(cxysa,dataset) ).append("' ");
				DataBindingRefGroup dbrg = dbpMap.get(dataset.getCode()+"-"+DataBindingRefGroup.COMMONCOLUMNCODE);    //根据数据集和列的code获得DataBindingRefGroup
				if(dbrg==null)dbrg = dbpMap.get(dataset.getCode()+"-"+cxysa.getCode());    //根据数据集和列的code获得DataBindingRefGroup
				if(dbrg!=null){
					setAlphaFrom(dbrg);
					datasetsxml.append(toDataBindingRefGroupAttributeString(dbrg));   //给<dataset ....>加属性
				}
				if (cxysa.isHasOperate()) {
					linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(
							colcode, getOneChartDataCell(colcode,
									valueMaplist.get(key))));
					linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(cxyzsa
							.getxColumnCode(), null));
					linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(cxyzsa
							.getzColumnCode(), null));
					StringBuilder datasetlink = getlink(ValueChartType.VALUE_CHART_LEGEND);
					if (datasetlink != null && !datasetlink.toString().equals(""))
						datasetsxml.append(" link='").append(datasetlink)
								.append("' ");
					linkSeriesAxisstack.pop();
					linkSeriesAxisstack.pop();
					linkCategoryAixsstack.pop();
				}
				datasetsxml.append(" >");
				datasetsxml.append(generateAdataset(cxysa,valueMaplist.get(key) ));
				datasetsxml.append("</dataset>");
				setalpha = null;
			}
		}
		return datasetsxml;
	}
	
	
	
	protected String getdatasetnaem(ChartXYSeriesAxis cxysa_xy,ChartDataset dataset){
		ChartXYZSeriesAxis cxysa = (ChartXYZSeriesAxis) cxysa_xy;
		String xcolumnCode = cxysa.getxColumnCode();
		String ycolumnCode = cxysa.getyColumnCode();
		String zcolumnCode = cxysa.getzColumnCode();
		String xcolumnName = dataset.getChartHeader().getHeaderCell(xcolumnCode).getCaption();
		String ycolumnName = dataset.getChartHeader().getHeaderCell(ycolumnCode).getCaption();
		String zcolumnName = dataset.getChartHeader().getHeaderCell(zcolumnCode).getCaption();
		return xcolumnName+"_"+ycolumnName+"_"+zcolumnName;
	}
	
	
	
	private void setAlphaFrom(DataBindingRefGroup binding){
		if(binding == null)
			setalpha = null;
		else 
			for (Property property : binding.getProperties()) {
				if (property.getCode().equals("alpha") ){
					setalpha = property.getValue();
				   // property.setValue(null);
				    break;
				}
					
			}
	}

	

}
