package uap.bq.chart.generator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import uap.bq.chart.ChartClickLinkConst.ValueChartType;
import uap.bq.chart.generator.LinkChartDataGenerator.AxiscodeANDChartDataCell;
import uap.bq.chart.generator.link.JsonStack;
import uap.util.bq.chart.ToolClassDefine.DataBindingPropertyMap;
import uap.vo.bq.chart.model.ChartModel;
import uap.vo.bq.chart.model.ChartModelFactory;
import uap.vo.bq.chart.model.DataBindingRefGroup;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.databinding.ChartCategoryAxis;
import uap.vo.bq.chart.model.databinding.ChartDataBinding;
import uap.vo.bq.chart.model.databinding.ChartSeriesAxis;
import uap.vo.bq.chart.model.dataset.ChartBodyRow;
import uap.vo.bq.chart.model.dataset.ChartDataCell;
import uap.vo.bq.chart.model.dataset.ChartDataset;
import uap.vo.bq.chart.model.dataset.ChartHeaderCell;

public class RadarChartDataGenerator extends MultiSeriesChartDataGenerator {

	/**
	 * 数据集Dataset置换标志
	 */
	private boolean isReplacement = true;
	private DataBindingRefGroup dbrg = null;

	private boolean modelhasnoDataset = false;
	
	@Override
	public void clear() {
		super.clear();
		modelhasnoDataset = false;
		isReplacement = true;
		dbrg = null;
	}

	public StringBuilder toChartDataXml(List<DataBindingItem> dataBindingItems,
			DataBindingPropertyMap dbpMap, ChartDataset dataset)
			throws ChartGenerateException {
		if (!modelhasnoDataset && !isReplacement)
			return super.toChartDataXml(dataBindingItems, dbpMap, dataset);
		else {
			initChartDataBindingInfo(dataBindingItems, dataset); // 初始化关于系列分类变量的信息
			initDataset(dataset); // 将数据构造成一颗Map树
			categorystack = new JsonStack<String>();
			valuestack = new JsonStack<String>();
			linkCategoryAixsstack = new JsonStack<AxiscodeANDChartDataCell>();
			linkSeriesAxisstack = new JsonStack<AxiscodeANDChartDataCell>();
			// linkstack = new JsonStack<String>();
			// linkstack.push(JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_DATASET_CODE,
			// dataset.getCode()));
			// linkstack.push(JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_CHART_CATEGORYAXIS,
			// categorys.get(0).getColumnCode()) );
			dbrg = dbpMap.get(dataset.getCode() + "-"
					+ DataBindingRefGroup.COMMONCOLUMNCODE);
			StringBuilder datasetxml = new StringBuilder();
			datasetxml.append("<categories>")
					.append(generateCategoryDataXml(dataset, 0))
					.append("</categories>")
					.append(generateChartDataXml(keymapdata, 0));

			return datasetxml;
		}
	}
	
	
	/**
	 * 如果model没有数据集，便取默认数据集
	 * @return
	 */
	@Override
	protected ChartDataset[] getDatasets(){
		boolean modelhasnodatasets = this.model==null || this.model.getDatasets() == null || this.model
				.getDatasets().length == 0;
		if(modelhasnodatasets){
			if(this.chartDefine != null){
				modelhasnoDataset = true;
				ChartModel defaultModel = ChartModelFactory.createChartModel(this.model.getChartDefineCode());
				this.model = getDefaultDataBingChartModel(defaultModel);
				return this.chartDefine.getDefaultSetting().getDatasets();
			}else 
				return null;
		}else{
			return this.model.getDatasets();
		}
	}

	private StringBuilder generateCategoryDataXml(ChartDataset dataset,
			int cate_series_level) {
		StringBuilder categorydataxml = new StringBuilder();
		if (cate_series == null || cate_series.size() == 0
				|| cate_series_level == cate_series.size()) {
			String colname;
			for (int i = 0; i < real_series.size(); i++) {
				String colcode = real_series.get(i).getColumnCode();
				colname = dataset.getChartHeader().getHeaderCell(colcode)
						.getCaption();
				// linkstack.push(JsonCellUtils.getJsonCell(colcode,
				// ChartDataCell.Align.NONE+","+colcode));
				linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(colcode,
						null));
				StringBuilder link = getlink(ValueChartType.VALUE_CHART_CATEGORY);
				// linkstack.pop();
				linkSeriesAxisstack.pop();
				categorydataxml
						.append("<category label='")
						.append(valuestack.size() == 0 ? colname : valuestack
								.toString("_") + "_" + colname).append("' ")
						.append(" link='").append(link).append("' />");
			}
		} else {
			ChartSeriesAxis seriesaxis = cate_series.get(cate_series_level);
			LinkedHashSet<ChartDataCell> cellSet = sets[cate_series_level];
			for (ChartDataCell cell : cellSet) {
				// linkstack.push(JsonCellUtils.getJsonCell(seriesaxis.getColumnCode(),
				// cell.getAlign()+","+cell.getCode()));
				linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(
						seriesaxis.getColumnCode(), cell));
				valuestack.push(cell.getCaption());
				categorydataxml.append(generateCategoryDataXml(dataset,
						cate_series_level + 1));
				valuestack.pop();
				linkCategoryAixsstack.pop();
				// linkstack.pop();
			}
		}
		return categorydataxml;
	}

	private StringBuilder generateChartDataXml(
			LinkedHashMap<ChartDataCell, Object> datamap, int level) {

		StringBuilder setdataxml = new StringBuilder();
		for (ChartDataCell key : datamap.keySet()) {

			ChartCategoryAxis categoryaxis = categorys.get(level);
			// linkstack.push(JsonCellUtils.getJsonCell(categoryaxis.getColumnCode(),
			// key.getAlign()+","+key.getCode()));

			Object obj = datamap.get(key);
			boolean inAutoGrouping = (level < categorys.size() - 1);
			if ((obj instanceof DataNode<?, ?>)
					|| (obj instanceof ChartBodyRow) || !inAutoGrouping) {
				linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(
						categoryaxis.getColumnCode(), key));
				categorystack.push(categoryaxis.getColumnCode());
				StringBuilder link = getlink(ValueChartType.VALUE_CHART_LEGEND);
				setdataxml
						.append("<dataset seriesName='")
						.append(valuestack.size() == 0 ? key.getCaption()
								: valuestack.toString("_") + "_"
										+ key.getCaption()).append("' link='")
						.append(link).append("' ");
				if (dbrg != null) {
				    setdataxml.append(toAttributeString(dbrg));
				    String alpha = getAlpha(dbrg);
				    if(alpha!=null){
				       if(alpha.equals("0"))
				    	     alpha = "1";
				       setdataxml.append(" alpha='").append(alpha).append("' ");
				    }
				}
				setdataxml.append(">").append(generateAChartSetDataXml(obj))
						.append("</dataset>");
				categorystack.pop();
				linkCategoryAixsstack.pop();
			} else {
				linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(
						categoryaxis.getColumnCode(), key));
				categorystack.push(categoryaxis.getColumnCode());
				valuestack.push(key.getCaption());
				setdataxml.append(generateChartDataXml(
						(LinkedHashMap<ChartDataCell, Object>) obj, level + 1));
				valuestack.pop();
				categorystack.pop();
				linkCategoryAixsstack.pop();
			}

			// linkstack.pop();
		}

		return setdataxml;
	}

	

	private StringBuilder generateAChartSetDataXml(Object obj) {
		if (obj instanceof LinkedHashMap<?, ?>)
			return generateAChartSetDataXmlwithLinkedHashMap(
					(LinkedHashMap<ChartDataCell, Object>) obj, 0);
		else if (obj instanceof ChartBodyRow)
			return generateAChartSetDataXmlwithChartBodyRow((ChartBodyRow) obj);
		return null;
	}

	private StringBuilder generateAChartSetDataXmlwithLinkedHashMap(
			LinkedHashMap<ChartDataCell, Object> datamap, int cate_series_level) {
		StringBuilder setdataxml = new StringBuilder();
		LinkedHashSet<ChartDataCell> cellSet = sets[cate_series_level];
		ChartSeriesAxis serisecata = cate_series.get(cate_series_level);
		for (ChartDataCell cell : cellSet) {
			boolean hascontainsKey = datamap.containsKey(cell);
			if (!hascontainsKey)
				setdataxml.append(getnullset(cate_series_level + 1));
			else {
				linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(
						serisecata.getColumnCode(), cell));
				Object obj = datamap.get(cell);
				if (cate_series_level == cate_series.size() - 1)
					setdataxml
							.append(generateAChartSetDataXmlwithChartBodyRow((ChartBodyRow) obj));
				else
					setdataxml
							.append(generateAChartSetDataXmlwithLinkedHashMap(
									(LinkedHashMap<ChartDataCell, Object>) obj,
									cate_series_level + 1));
				linkCategoryAixsstack.pop();
			}
		}

		return setdataxml;
	}

	private StringBuilder generateAChartSetDataXmlwithChartBodyRow(
			ChartBodyRow cbr) {
		StringBuilder setdataxml = new StringBuilder();
		String colcode, value;
		for (int i = 0; i < real_series.size(); i++) {
			colcode = real_series.get(i).getColumnCode();
			ChartDataCell datacell = cbr.getChartDataCell(colcode);
			value = datacell.getCaption();
			// linkstack.push(JsonCellUtils.getJsonCell(colcode,
			// datacell.getAlign()+","+value));
			linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(colcode,
					datacell));
			StringBuilder link = getlink(ValueChartType.VALUE_CHART_SET);
			// linkstack.pop();
			linkSeriesAxisstack.pop();
			setdataxml.append("<set value='").append(getScaleValue(value,false)).append("' link='")
					.append(link).append("' />");
		}

		return setdataxml;
	}

	protected void generateDatasets(
			LinkedHashMap<ChartDataCell, Object> datamap, int level) {
		ChartDataBinding databinding = categorys.get(level - 1);
		boolean hasoperate = databinding.isHasOperate();
		String colcode = databinding.getColumnCode();
		if (hasoperate)
			categorystack.push(colcode);
		for (ChartDataCell key : datamap.keySet()) {
			Object obj = datamap.get(key);
			if (hasoperate) {
				// linkstack.push(JsonCellUtils.getJsonCell(colcode,key.getAlign()+","+key.getCode()));
				linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(
						colcode, key));
			}
			valuestack.push(key.getCaption());
			boolean inAutoGrouping = level < categorys.size() - 1;
			if ((obj instanceof DataNode<?, ?>)
					|| (obj instanceof ChartBodyRow)) {
				generateDatasetPartXmlWithAllDataCell(key, obj, level + 1);
			} else {
				if (inAutoGrouping)
					generateDatasets(
							(LinkedHashMap<ChartDataCell, Object>) obj,
							level + 1);
				else
					generateDatasetPartXml(obj);
				/*
				 * categoriesxml.append(" <vLine label='")
				 * .append(key.getCaption()) .append("' color='")
				 * .append(categorys.size()-1 <= colors.length ?
				 * colors[categorys.size()-level-1] : colors[0])
				 * .append("' thickness='") .append(categorys.size()-1 <=
				 * thickness.length ? thickness[categorys.size()-level-1] :
				 * thickness[0]) .append("' />");
				 */
			}
			// categoriesxml.append("<category label='' />");
			// fillDatasetPartXmlWithNoDataSet(dataSets_Xml);
			if (hasoperate) {
				linkCategoryAixsstack.pop();
				// linkstack.pop();
			}
			valuestack.pop();
		}
		if (hasoperate)
			categorystack.pop();
	}

	private StringBuilder getnullset(int cate_series_level) {
		StringBuilder nullsetdataxml = new StringBuilder();
		boolean isend = (cate_series_level == cate_series.size());
		if (isend) {
			for (int i = 0; i < real_series.size(); i++)
				nullsetdataxml.append("<set />");
		} else {
			LinkedHashSet<ChartDataCell> cellSet = sets[cate_series_level];
			for (ChartDataCell cell : cellSet)
				nullsetdataxml.append(getnullset(cate_series_level + 1));
		}

		return nullsetdataxml;
	}

	protected void toCustomProperty(Property property) {

		if (property.getCode().equals("isreplacement")) {
			// 处理用户自定义属性
			isReplacement = property.getValue() != null
					&& (property.getValue().equals("false") || property
							.getValue().equals("0")) ? false : true;
		} else {
			super.toCustomProperty(property);
		}
	}
	
	
	@Override
	protected void initDataSetXmlCan(int index, DataBindingPropertyMap dbpMap,List<Object> list,ChartDataset dataset){
		LinkedHashSet<ChartDataCell> setTemp = sets[index];
		ChartDataBinding catedatabinding = cate_series.get(index);
		String catecode = catedatabinding.getColumnCode();
		boolean cateisHasOperate = catedatabinding.isHasOperate();
		if(cateisHasOperate)
			categorystack.push(catecode);
		for(ChartDataCell cdc : setTemp){
			List<Object> listson = new ArrayList<Object>();
			list.add(listson);
			if(cateisHasOperate){
				//linkstack.push(JsonCellUtils.getJsonCell(catecode,cdc.getCode()) );
				linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(catecode,cdc));
			}
				
            valuestack.push(cdc.getCaption());
			if(index == cate_series.size()-1){
				for(int j=0; j<real_series.size(); j++){
					StringBuilder datasetxml = new StringBuilder();
					ChartDataBinding cdb = real_series.get(j);
					boolean seriesisHasOperate = cdb.isHasOperate();
					ChartHeaderCell chc = dataset.getChartHeader().getHeaderCell(cdb.getColumnCode());
					linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(cdb.getColumnCode(),null));
					String seriesName = chc.getCaption();
					datasetxml.append("<dataset seriesName='")
					          .append(valuestack.toString("_")+"_"+seriesName)
					          .append("' ");
					if(seriesisHasOperate){
						//linkstack.push(JsonCellUtils.getJsonCell(cdb.getColumnCode(),chc.getCode()) );
					    StringBuilder datasetlink = getlink(ValueChartType.VALUE_CHART_LEGEND);
					    datasetxml.append(" link='")
				                  .append(datasetlink)
				                  .append("' ");
					    //linkstack.pop();
					}
					linkSeriesAxisstack.pop();
					DataBindingRefGroup dbrg = dbpMap.get(dataset.getCode()+"-"+real_series.get(j).getColumnCode());    //根据数据集和列的code获得DataBindingRefGroup
					if(dbrg!=null){
						datasetxml.append(toAttributeString(dbrg));   //给<dataset ....>加属性
						 String alpha = getAlpha(dbrg);
						    if(alpha!=null){
						       if(alpha.equals("0"))
						    	     alpha = "1";
						       datasetxml.append(" alpha='").append(alpha).append("' ");
						    }
					}
					datasetxml.append(">");
					listson.add(datasetxml);
					
				}

			}else
				initDataSetXmlCan(index+1,dbpMap,listson,dataset);
			valuestack.pop();
			if(cateisHasOperate){
				//linkstack.pop();
				linkCategoryAixsstack.pop();
			}
				
		}
		if(cateisHasOperate)
			categorystack.pop();

	}
	
	
	@Override
	protected void initDatasetsCan(ChartDataset dataset, DataBindingPropertyMap dbpMap){
		categoriesxml = new StringBuilder();      //储存<category label='' />。。。。。。<category label='' />信息
		dataSets_Xml  = new ArrayList<Object>();
		//linkstack.push(JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_CHART_TYPE,ChartClickLinkConst.VALUE_CHART_LEGEND));

		if(cate_series == null || cate_series.size()==0 )
			for(int i=0; i<real_series.size(); i++){
				StringBuilder datasetxml = new StringBuilder();
				ChartDataBinding cdb = real_series.get(i);
				boolean databindingisHasOperate = cdb.isHasOperate();
				ChartHeaderCell chc = dataset.getChartHeader().getHeaderCell(cdb.getColumnCode());
				linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(cdb.getColumnCode(),null));
				String seriesName = chc.getCaption();
				datasetxml.append("<dataset seriesName='").append(seriesName).append("' ");
				if(databindingisHasOperate){
					//linkstack.push(JsonCellUtils.getJsonCell(cdb.getColumnCode(), chc.getCode()));
					StringBuilder datasetlink = getlink(ValueChartType.VALUE_CHART_LEGEND);;
					datasetxml.append(" link='").append(datasetlink).append("' ");
					//linkstack.pop();
				}
				linkSeriesAxisstack.pop();
				DataBindingRefGroup dbrg = dbpMap.get(dataset.getCode()+"-"+real_series.get(i).getColumnCode());    //根据数据集和列的code获得DataBindingRefGroup
				if(dbrg!=null){
					datasetxml.append(toAttributeString(dbrg));   //给<dataset ....>加属性
					 String alpha = getAlpha(dbrg);
					    if(alpha!=null){
					       if(alpha.equals("0"))
					    	     alpha = "1";
					       datasetxml.append(" alpha='").append(alpha).append("' ");
					    }
				}
				datasetxml.append(">");
				dataSets_Xml.add(datasetxml);
			}
		else
			initDataSetXmlCan(0,dbpMap,dataSets_Xml,dataset);

		//linkstack.pop();
	}
	
	protected String getAlpha(DataBindingRefGroup binding) {
		if (binding == null)
			return null;
		for (Property property : binding.getProperties()) {
			if (property.getCode().equals("alpha")) {
				return property.getValue();
			}
		}
		return null;
	}
	
	@Override
	protected void fillDatasetPartXmlWithChartBodyRow(List<Object> dataSetsXmlList,ChartBodyRow cbr){
		for(int i=0; i<real_series.size(); i++){
			ChartDataBinding databinding = real_series.get(i);
			boolean hasopearate = databinding.isHasOperate();
			String    colcode = databinding.getColumnCode();
			
			ChartDataCell   datacell = cbr.getChartDataCell(colcode);			
			linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(colcode,datacell));
			String   value = datacell.getCaption();
			if(value==null || value.equals(""))
				value = "0";
			StringBuilder datasetXml = (StringBuilder)dataSetsXmlList.get(i);
			//填入value值，并根据value值和趋势线判断颜色
			DataBindingRefGroup dBindGroup = getDataBindGroup(databinding.getColumnCode());
			datasetXml.append("<set value='")
			          .append(getScaleValue(value,isParentYAxisS(dBindGroup)))
			          .append("'")
			          .append(" displayValue='")
			          .append(getDisplayValue(cbr, value, databinding))
			          .append("'")
			          .append( compareValueWithTrendlines(value) );
			if(hasopearate){
				StringBuilder linktemp = getlink(ValueChartType.VALUE_CHART_SET);
				datasetXml.append(" link='").append(linktemp).append("' ");
			}
			datasetXml.append(" />");
			linkSeriesAxisstack.pop();
		}
	}	
	
	
}
