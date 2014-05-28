package uap.bq.chart.generator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import uap.bq.chart.ChartClickLinkConst.ValueChartType;
import uap.bq.chart.ChartPropConst;
import uap.bq.chart.ChartTypeConst;
import uap.bq.chart.generator.link.JsonStack;
import uap.bq.chart.utils.ChartNumFormatUtils;
import uap.util.bq.chart.ToolClassDefine.DataBindingPropertyMap;
import uap.vo.bq.chart.model.DataBindingRefGroup;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.databinding.ChartCategoryAxis;
import uap.vo.bq.chart.model.databinding.ChartDataBinding;
import uap.vo.bq.chart.model.databinding.ChartSeriesAxis;
import uap.vo.bq.chart.model.dataset.ChartBodyRow;
import uap.vo.bq.chart.model.dataset.ChartDataCell;
import uap.vo.bq.chart.model.dataset.ChartDataset;
import uap.vo.bq.chart.model.dataset.ChartHeaderCell;

import com.ufida.iufo.pub.tools.AppDebug;


/**
 * 多系列多分类数据生成器
 * @author fuzw
 *
 */
public class MultiSeriesChartDataGenerator extends LinkChartDataGenerator {
	/**
	 * 是否在悬浮提示中显示次分类轴
	 */
	private boolean bsCategoryNameInToolTip = true;
	/**
	 * 是否在悬浮提示中显示分类轴
	 */
	private boolean bCategoryNameInToolTip = true;	
	/**
	 * 
	 */
	
	private String autolienPaletteColors = null;
	private String 	autolienThickness = null; 
	/**
	 * 是否在数据标签中显示次分类轴
	 */
	private boolean bsCategoryInDataValue = false;
	/**
	 * 是否在数据标签中显示分类轴
	 */
	private boolean bCategoryInDataValue = true;
	/**
	 * 是否在数据标签中显示值
	 */
	private boolean bValueInDataValue = true;
	/**
	 * 是否在数据标签中显示系列
	 */
	private boolean bSeriesInDataValue = false;
	/**
	 * 数据标签分割符号
	 */
	private String  bSepCharInDataValue = ",";
	/**
	 * 是否在数据标签中显示百分比
	 */
	private boolean  bPercentInDataValue = true;
	
	
	

	//所有预警线
    private List<InnerWarningLine> warningLines =null;


    private static final String  LABELPOSITIONVALUE = "0";
    private static final String[]  DEFAULTCOLORS = {"CC0066","009999","FFCC33","FF0033","333399","CCCC00","FF6600","FF99CC","99CCFF","993366"};
	private static final String[]  DEFAULTTHICKNESS = {"2","3","4","5","6","7","8","9","10"};


	//protected ChartDataset dataset;
	protected List<ChartCategoryAxis>  categorys;
	protected ChartCategoryAxis        lastcategory;
	protected List<ChartSeriesAxis>   real_series;
	protected List<ChartSeriesAxis>   cate_series;
	protected LinkedHashMap<ChartDataCell,Object>  keymapdata;
	protected LinkedHashMap<String,Double>  summap = null;
	protected LinkedHashSet<ChartDataCell>[]  sets;
	protected StringBuilder     categoriesxml = null;        //储存<category label='' />。。。。。。<category label='' />信息
	//private StringBuilder[]   datasetsxml   = null;
	protected String[]          colors    = MultiSeriesChartDataGenerator.DEFAULTCOLORS;
	protected String[]          thickness = MultiSeriesChartDataGenerator.DEFAULTTHICKNESS;
	protected String            labelPosition = MultiSeriesChartDataGenerator.LABELPOSITIONVALUE;
	protected List<Object>      dataSets_Xml = null;        //储存<set value=''/>....<set value=''/>信息 数组

	//protected JsonStack<String> linkstack;
	//protected JsonStack<AxiscodeANDChartDataCell> linkCategoryAixsstack;
	//protected JsonStack<AxiscodeANDChartDataCell> linkSeriesAxisstack;
	//protected JsonStack<String> categorystack;
	protected JsonStack<String> valuestack;
	//protected static String linkstart = "javascript:_processLink(\\\"{";
	//protected static String linkend   = "}\\\")";	
	@Override
	public void clear() {
		super.clear();
		dataset = null;
		linkCategoryAixsstack = null;
		linkSeriesAxisstack = null;
		bCategoryNameInToolTip = true;
		bsCategoryNameInToolTip = true;
		//bCategoryInDataValue = false;
		bsCategoryInDataValue = false;
		bSeriesInDataValue = false;
		bValueInDataValue = true;
		bPercentInDataValue = false;
		summap = null;
		autolienPaletteColors = null;
		autolienThickness = null;
		categorys = null;
		lastcategory = null;
		real_series = null;
		cate_series = null;
		keymapdata = null;
		sets = null;
		categoriesxml = null;
		labelPosition = MultiSeriesChartDataGenerator.LABELPOSITIONVALUE;
		colors    = MultiSeriesChartDataGenerator.DEFAULTCOLORS;
		thickness = MultiSeriesChartDataGenerator.DEFAULTTHICKNESS;
		dataSets_Xml = null;
		//linkstack = null;
		categorystack = null;
		valuestack = null;
		warningLines =null;
		bSepCharInDataValue = ",";
	}



	public StringBuilder generateLinkChartDataXml(List<DataBindingItem> dataBindingItems,
			DataBindingPropertyMap dbpMap,
			ChartDataset dataset) throws ChartGenerateException {

		initAutoLinePaletteColorsAndThickness(); //初始化自动分组线colors、thickness
		updateWarningLines();     //更新预警线信息
		
		boolean noerror = initChartDataBindingInfo(dataBindingItems,dataset);      //初始化关于系列分类变量的信息
		if(!noerror){
			return new StringBuilder ();
		}
		
		initDataset(dataset);     //将数据构造成一颗Map树

		//linkCategoryAixsstack = new JsonStack<AxiscodeANDChartDataCell>();
		//linkSeriesAxisstack   = new JsonStack<AxiscodeANDChartDataCell>();
		//categorystack = new JsonStack<String>();
		valuestack = new JsonStack<String>();
		//linkstack = new JsonStack<String>();
		//linkstack.push(JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_DATASET_CODE, dataset.getCode()));
		
		initDatasetsCan(dataset, dbpMap);   //初始化dataSets_Xml、categoriesxml
		if(categorys.size()>1)
			generateDatasets(keymapdata,1);   //生成多分类xml
		else if(categorys.size()==1)
			generateDatasetPartXml(keymapdata);  //生成单分类xml
		StringBuilder dataxml = getXml();		
		return dataxml;
	}



	protected boolean initChartDataBindingInfo(List<DataBindingItem> dataBindingItems,ChartDataset dataset) throws ChartGenerateException{
		this.dataset = dataset;
		categorys   = new LinkedList<ChartCategoryAxis>();
		real_series = new LinkedList<ChartSeriesAxis>();
		cate_series = new LinkedList<ChartSeriesAxis>();
		List<ChartSeriesAxis> seriesTemp = new ArrayList<ChartSeriesAxis>();    //所有的系列 ，对应y轴
		for(DataBindingItem dbi : dataBindingItems){                              //遍历数据绑定
			if(dbi.getChartDataBinding() instanceof ChartSeriesAxis)
				seriesTemp.add( (ChartSeriesAxis)dbi.getChartDataBinding() );          //找出系列（指标）数据绑定
			else if (dbi.getChartDataBinding() instanceof ChartCategoryAxis)
				categorys.add( (ChartCategoryAxis)dbi.getChartDataBinding() );      //找出分类  数据绑定
		}
		if(categorys.size()==0){
			//throw new ChartGenerateException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0074")/*@res "Error : 没有合法的分类！"*/);
			AppDebug.debug( nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0074") );
			return false;
		}
			
		lastcategory = categorys.get(categorys.size()-1); //获取最后一个
		//categorys.remove(lastcategory);       //在队列里面删除最后一个
		for(ChartSeriesAxis chartdb : seriesTemp){
			if(chartdb.isMeasure())
				real_series.add(chartdb);
			else if(!chartdb.isMeasure())
				cate_series.add(chartdb);
		}
		if(real_series.size()==0){
			//throw new ChartGenerateException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0075")/*@res "Error : 没有合法的系列！"*/);
			AppDebug.debug( nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0075") );
			return false;
		}
			
		if(cate_series!=null && cate_series.size()>0){
			sets = new LinkedHashSet[cate_series.size()];
			for(int i=0;i<cate_series.size();i++){
				sets[i] = new LinkedHashSet<ChartDataCell>();
			}
		}
		
		return true;

	}

	protected void initDataset(ChartDataset dataset){
	    keymapdata = new LinkedHashMap<ChartDataCell,Object>();    //把dataset中的数据按分类的值分类
		for( ChartBodyRow cbr : dataset.getChartBody().getChartBodyRows() ){
			LinkedHashMap<ChartDataCell,Object> maptemp = keymapdata;
			for(int i = 0; i < categorys.size(); i++){
				ChartCategoryAxis ccatemp = categorys.get(i);                               //当前分类
				String colcodetemp = ccatemp.getColumnCode();                               //当前分类绑定的数据列
				ChartDataCell valuetemp = cbr.getChartDataCell(colcodetemp);                 //当前分类的取值datacell
				String next_colcode = (i==categorys.size()-1) ?
						              null : categorys.get(i+1).getColumnCode();
				boolean dataCellisAll = next_colcode !=null
						               && cbr.getChartDataCell(next_colcode).isAll();
				if(!dataCellisAll && i!= categorys.size()-1){
					if(!maptemp.containsKey(valuetemp))              //判断是否已经按照当前分类值兴建了一个分类
						maptemp.put(valuetemp, new LinkedHashMap<ChartDataCell,Object>());
					Object obj = maptemp.get(valuetemp);
					if(!(obj instanceof LinkedHashMap<?,?>) || obj instanceof DataNode<?,?>)
						break;
					maptemp = (LinkedHashMap<ChartDataCell,Object>)maptemp.get(valuetemp);
					continue;
				}
				if(cate_series == null || cate_series.size()==0){
					maptemp.put(valuetemp, cbr);
				}else{
					if(!maptemp.containsKey(valuetemp))              //判断是否已经按照当前分类值兴建了一个分类
						maptemp.put(valuetemp, new DataNode<ChartDataCell,Object>());
					maptemp = (LinkedHashMap<ChartDataCell,Object>)maptemp.get(valuetemp);
					for(int j = 0; j < cate_series.size(); j++){
						ChartDataBinding databinding = cate_series.get(j);
						String    colcode = databinding.getColumnCode();           //当前分类绑定的数据列
						ChartDataCell  datacell = cbr.getChartDataCell(colcode);
						sets[j].add(datacell);
						if(j == cate_series.size()-1)maptemp.put(datacell, cbr);
						else{
							if(!maptemp.containsKey(datacell))maptemp.put(datacell, new DataNode<ChartDataCell,Object>());
							maptemp = (LinkedHashMap<ChartDataCell,Object>)maptemp.get(datacell);
						}
					}
				}
				break;
			}
		}
	}

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
				if(dbrg!=null)datasetxml.append(toAttributeString(dbrg));   //给<dataset ....>加属性
				datasetxml.append(">");
				dataSets_Xml.add(datasetxml);
			}
		else
			initDataSetXmlCan(0,dbpMap,dataSets_Xml,dataset);

		//linkstack.pop();
	}

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
					if(dbrg!=null)datasetxml.append(toAttributeString(dbrg));   //给<dataset ....>加属性
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


	private boolean getCross(){
		return cate_series!=null && cate_series.size()>0;
	}


	protected void generateDatasets(LinkedHashMap<ChartDataCell,Object> datamap,int level){
		ChartDataBinding databinding = categorys.get(level-1);
		boolean hasoperate = databinding.isHasOperate();
		String colcode = databinding.getColumnCode();
		if(hasoperate)
			categorystack.push(colcode);
		for(ChartDataCell key : datamap.keySet() ){
			Object obj = datamap.get(key);
			if(hasoperate){
				//linkstack.push(JsonCellUtils.getJsonCell(colcode,key.getAlign()+","+key.getCode()));
				linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(colcode,key));
			}
			valuestack.push(key.getCaption());
			boolean inAutoGrouping = level < categorys.size()-1;
			if((obj instanceof DataNode<?,?>) || (obj instanceof ChartBodyRow)){
				generateDatasetPartXmlWithAllDataCell(key, obj,level+1);
			}else{
				if(inAutoGrouping)
					generateDatasets( (LinkedHashMap<ChartDataCell,Object>)obj,level+1);
				else
					generateDatasetPartXml(obj);
				categoriesxml.append(" <vLine label='")
				             .append(key.getCaption())
                             .append("' color='")
                             .append(categorys.size()-1 <= colors.length ? colors[categorys.size()-level-1] : colors[0])
                             .append("' thickness='")
                             .append(categorys.size()-1  <= thickness.length ? thickness[categorys.size()-level-1] : thickness[0])
                             .append("' labelPosition='"+labelPosition)
                             .append("'  />");
			}
			categoriesxml.append("<category label='' />");
			fillDatasetPartXmlWithNoDataSet(dataSets_Xml);
			if(hasoperate){
				linkCategoryAixsstack.pop();
				//linkstack.pop();
			}
			valuestack.pop();
		}
		if(hasoperate)
			categorystack.pop();
	}

	protected void generateDatasetPartXml(Object obj){
		LinkedHashMap<ChartDataCell,Object> dataMap = (LinkedHashMap<ChartDataCell,Object>)obj;
		ChartDataBinding databinding = lastcategory;
		boolean hasopearate = databinding.isHasOperate();
		String  colcode = databinding.getColumnCode();
		if(hasopearate)
			categorystack.push(colcode);
		for(ChartDataCell key : dataMap.keySet() ){
			//StringBuilder linktemp = new StringBuilder().append(prelink);
			if(hasopearate){
				//linkstack.push(JsonCellUtils.getJsonCell(colcode,key.getAlign().toString()+","+key.getCode()));
				linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(colcode,key));
			}
			valuestack.push(key.getCaption());
			Object valueObj = dataMap.get(key);
			generateDatasetPartXmlWithDataCell(key,valueObj);
			if(hasopearate){
				//linkstack.pop();
				linkCategoryAixsstack.pop();
			}
			valuestack.pop();
		}
		if(hasopearate)
			categorystack.pop();
	}
	
	protected void generateDatasetPartXmlWithDataCell(ChartDataCell key,Object obj){
		StringBuilder cataLink = getlink(ValueChartType.VALUE_CHART_CATEGORY);
		StringBuilder toolText = getToolText();
		categoriesxml.append("<category label='")
                     .append(key.getCaption())
                     .append("' ")
                    // .append(!bCategoryNameInToolTip? " toolText=' '":"")
                     .append(toolText);
		if(cataLink!=null && !cataLink.toString().equals("")){
			categoriesxml.append(" link='").append(cataLink).append("' ");
		}
        
        categoriesxml.append(" />");
		StringBuilder vLine = generateUserGroupLine("vValue='"+userDivisionPoint.size()+"'");
		categoriesxml.append(vLine);
		userDivisionPoint.add(valuestack.toString("|"));
		if(obj instanceof ChartBodyRow)
			fillDatasetPartXmlWithChartBodyRow(dataSets_Xml,(ChartBodyRow)obj);
		else if(obj instanceof LinkedHashMap<?,?>)
			fillDatasetPartXmlWithDataMap(dataSets_Xml, (LinkedHashMap<ChartDataCell,Object>)obj,0);
	}
	
	/**
	 * 设置悬浮提示中toolText属性的值
	 * @return
	 */
	protected StringBuilder getToolText(){
		StringBuilder toolText = new StringBuilder();
		@SuppressWarnings("unchecked")
		JsonStack<String> s = (JsonStack<String>) valuestack.clone();
		toolText.append("toolText='");
		
		if(bCategoryNameInToolTip && !bsCategoryNameInToolTip){
			toolText.append(s.lastElement().toString());
		}
		else if(!bCategoryNameInToolTip && bsCategoryNameInToolTip){
			s.pop();
			toolText.append(s.toString(" "));
		}
		else if(bCategoryNameInToolTip && bsCategoryNameInToolTip){
			toolText.append(s.toString(this.toolTipSepChar));
		}
		else if(!bCategoryNameInToolTip && !bsCategoryNameInToolTip){
			toolText.append(" ");
		}
			
		toolText.append("'");
		return toolText;
		
	}
	

	protected void generateDatasetPartXmlWithAllDataCell(ChartDataCell key,Object obj,int level){
		ChartBodyRow row;
		if(obj instanceof ChartBodyRow)
			row = (ChartBodyRow)obj;
		else{
			LinkedHashMap<ChartDataCell,Object> datamaptemp = (LinkedHashMap<ChartDataCell,Object>)obj;
			row = getRowFromMap(datamaptemp);
		}
		
		int leveltemp = level;
		while(leveltemp <= categorys.size()){
			ChartDataBinding databinding = categorys.get(leveltemp-1);
			boolean hasoperate = databinding.isHasOperate();
			String colcode = databinding.getColumnCode();
			if(hasoperate){
				categorystack.push(colcode);
				linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(colcode, row.getChartDataCell(colcode)));
			}
			leveltemp++;
		}
		
		generateDatasetPartXmlWithDataCell(key,obj);
		
		leveltemp = level;
		while(leveltemp <= categorys.size()){
			categorystack.pop();
			linkCategoryAixsstack.pop();
			leveltemp++;
		}
		
	}

	
	private ChartBodyRow getRowFromMap(LinkedHashMap<ChartDataCell,Object> map){
		Object obj = map.get(map.keySet().toArray()[0]);
		if(obj instanceof LinkedHashMap<?,?>){
			obj = getRowFromMap( (LinkedHashMap<ChartDataCell,Object>) obj);
		}
		return (ChartBodyRow)obj;
	}
	

	protected void fillDatasetPartXmlWithChartBodyRow(List<Object> dataSetsXmlList,ChartBodyRow cbr){
		for(int i=0; i<real_series.size(); i++){
			ChartDataBinding databinding = real_series.get(i);
			boolean hasopearate = databinding.isHasOperate();
			String    colcode = databinding.getColumnCode();
			
			ChartDataCell   datacell = cbr.getChartDataCell(colcode);			
			linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(colcode,datacell));
			String   value = datacell.getCaption();
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
	
	protected boolean isParentYAxisS(DataBindingRefGroup dBindGroup){
		boolean parentYAxis = false;
		if(dBindGroup == null || !isMscombiDy2d() ){
			return parentYAxis;
		}
		Property property = dBindGroup.getPropertyByRefCode(ChartPropConst.PROPERTY_PARENTYAXIS);
		if(property == null || StringUtils.isEmpty(property.getValue())){
			return parentYAxis;
		}
		parentYAxis = ChartPropConst.PROPERTY_PARENTYAXIS_S.equals(property.getValue()) ? Boolean.TRUE : Boolean.FALSE;
		
		return parentYAxis;
		
	}
	
	/**
	 * 是否是双Y轴组合图
	 * 
	 * @return
	 */
	private boolean isMscombiDy2d() {
		return model.getChartDefineCode().equals(ChartTypeConst.MSCOMBIDY2D);
	}

	
	/**
	 * 返回数据标签中displayValue属性的值
	 * @param cbr
	 * @param value
	 * @param databinding
	 * @return
	 */
	protected String getDisplayValue(ChartBodyRow cbr,String value,ChartDataBinding databinding){
		JsonStack<String> disPlayValue = new JsonStack<String>();	
		// 若为默认数据则不格式化
		ChartDataset[] da = chartDefine.getDefaultSetting().getDatasets();
		if(da != null && da.length != 0 && databinding.getDatasetCode().equals(da[0].getChartDataElement().getCode())){
			return "";
		}
		DataBindingRefGroup dBindGroup = getDataBindGroup(databinding.getColumnCode());		
		bCategoryInDataValue = setDataValueFlag(dBindGroup, "showPCategory");
		bsCategoryInDataValue= setDataValueFlag(dBindGroup,  "showSCategory");
		bSeriesInDataValue =setDataValueFlag(dBindGroup,  "showSeries");
		bValueInDataValue = setDataValueFlag(dBindGroup,  "showValue");		
		bPercentInDataValue = setDataValueFlag(dBindGroup, "showInPercent");	
		
		//分类轴
		if(!categorys.isEmpty() && bCategoryInDataValue){
			String pCategory = cbr.getChartDataCell(categorys.get(categorys.size()-1).getColumnCode()).getCaption();
			disPlayValue.push(pCategory);					
		}
		//次分类轴
		if(categorys.size() > 1 && bsCategoryInDataValue ){
			String sCategory;
			for(int i=categorys.size()-2; i>=0; i--){
				sCategory = cbr.getChartDataCell(categorys.get(i).getColumnCode()).getCaption();
				disPlayValue.push(sCategory);
			}
		}
		//系列名称
		if(bSeriesInDataValue){
			String series = databinding.getCaption();
			if(series != null){
				disPlayValue.push(databinding.getCaption());
			}
		}
		//值
		if(bValueInDataValue){				
			String formatValue = ChartNumFormatUtils.getFormatValue(value,isParentYAxisS(dBindGroup),model,true);
			disPlayValue.push(formatValue);			
		}	
		//显示百分比
		if(bPercentInDataValue){
			String sumCode = getSumCode(cbr, databinding.getColumnCode());
			// 获取分类中指标的和
			String percent = getFormatPercent(sumCode,value,isParentYAxisS(dBindGroup));
			disPlayValue.push(percent);
		}		
		if(disPlayValue.isEmpty()){
			return " ";
		}
		// 若disPlayValue不为空，则将sepChar做为分隔符分割字符串
		else{
			String sepChar = getDataValueSepChar(dBindGroup, ChartPropConst.PROPERTY_SEPCHAR);
			return disPlayValue.toString(sepChar);
		}
		
	}
	
	/**
	 * 格式化百分比
	 * @return
	 */
	private String getFormatPercent(String sumCode, String value,boolean isParentYAxisS) {
			
		// 获取分类中指标的和
		Object obj = getRealSeriSum(sumCode);
		return ChartNumFormatUtils.getFormatPercent(obj, value, isParentYAxisS, model);

	}		
	
	/**
	 * 返回数据绑定属性组
	 * @param columnCode
	 * @return
	 */
	protected DataBindingRefGroup getDataBindGroup(String columnCode){
		for(PropertyGroup propertyGroup: model.getPropertyGroups()){
			if(propertyGroup instanceof DataBindingRefGroup ){
				if(((DataBindingRefGroup) propertyGroup).getColumn().equals(columnCode)){
					return (DataBindingRefGroup) propertyGroup;
				}
			}
			
		}
		return null;
		
	}
	
	private boolean setDataValueFlag(DataBindingRefGroup dBindGroup, String proertyCode){		
		boolean flag = false;
		if(dBindGroup == null){
			return flag;		
		}
		Property property = dBindGroup.getPropertyByRefCode(proertyCode);
		if(property != null && property.getValue()!=null){
			flag = property.getValue().equals("0")? false:true;
		}	
		return flag;
	}
	
	private String getDataValueSepChar(DataBindingRefGroup dBindGroup, String propertyCode){
		String sepChar = ",";			
		if(dBindGroup == null ||dBindGroup.getPropertyByRefCode(propertyCode)== null){
			return sepChar;
		}
		Property property = dBindGroup.getPropertyByRefCode(propertyCode);
		if(property == null || StringUtils.isEmpty(property.getValue())){
			return sepChar;
		}
		return property.getValue();
		
		
	}

	protected void fillDatasetPartXmlWithDataMap(List<Object> dataXmlListTemp,LinkedHashMap<ChartDataCell,Object> datamap,int cate_series_level){
		ChartDataBinding databinding = cate_series.get(cate_series_level);
		boolean hasoperate = databinding.isHasOperate();
		String  colcode = databinding.getColumnCode();
		LinkedHashSet<ChartDataCell> cellSet = sets[cate_series_level];
		int cellSetIndex = 0;
		//if(hasoperate)
			//categorystack.push(colcode);
		for(ChartDataCell cdc : cellSet){
			if(hasoperate){
				//linkstack.push(JsonCellUtils.getJsonCell(colcode,cdc.getAlign().toString()+","+cdc.getCode()) );
				linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(colcode,cdc));
			}
			List<Object> temp2 = (List<Object>)dataXmlListTemp.get(cellSetIndex);
			if(!datamap.containsKey(cdc))
				fillDatasetPartXmlWithNoDataSet(temp2);
			else{
				Object objTemp = datamap.get(cdc);
				if(objTemp instanceof LinkedHashMap<?,?>)
					fillDatasetPartXmlWithDataMap(temp2,(LinkedHashMap<ChartDataCell,Object>) objTemp,cate_series_level+1);
				else if(objTemp instanceof ChartBodyRow)
					fillDatasetPartXmlWithChartBodyRow(temp2,(ChartBodyRow)objTemp);
			}
			cellSetIndex ++;
			if(hasoperate){
				linkCategoryAixsstack.pop();
				//linkstack.pop();
			}
				
		}
		//if(hasoperate)
			//categorystack.pop();
	}

	protected void fillDatasetPartXmlWithNoDataSet(List<Object> dataSetsXmlList){
		for(Object obj : dataSetsXmlList){
			if(obj instanceof List<?>)fillDatasetPartXmlWithNoDataSet((List<Object>) obj);
			else if(obj instanceof StringBuilder){
				((StringBuilder) obj).append("<set value=''/>");
			}
		}
	}

	private StringBuilder getXml(){
		StringBuilder dataxml = new StringBuilder();
		dataxml.append("<categories>").append(categoriesxml).append(" </categories>");
		fillDataset(dataxml,dataSets_Xml);
		return dataxml;
	}

	protected void fillDataset(StringBuilder dataxml,List<Object> list){
		for(Object obj : list){
			if(obj instanceof List<?>)
				fillDataset(dataxml,(List<Object>)obj);
			else if(obj instanceof StringBuilder)
				dataxml.append(obj).append("</dataset>");
		}
	}



	private StringBuilder generateUserGroupLine(String value) {
		StringBuilder vlines = new StringBuilder();
		for (StringBuilder userStringBuilder : this.userGroupLines){
			if (userStringBuilder.indexOf(value) != -1){
				vlines.append(userStringBuilder);
			}
		}
		return vlines;
	}



	protected void toCustomProperty (Property property){
		if (property.getCode().equals("categoryNameInToolTip") && chartDefine.isCustomProperty(property.getCode())){
			// 处理用户自定义属性
			bCategoryNameInToolTip = property.getValue()!=null && (property.getValue().equals("false")||property.getValue().equals("0"))? false : true;
			
		}else if (property.getCode().equals("autolienPaletteColors") ){
			// 处理用户自定义属性
			autolienPaletteColors = property.getValue();
		}else if (property.getCode().equals("autolienThickness") ){
			// 处理用户自定义属性
			autolienThickness = property.getValue();
		}else if(property.getCode().equals("scategoryNameInToolTip") && chartDefine.isCustomProperty(property.getCode())){
			bsCategoryNameInToolTip = property.getValue()!=null && (property.getValue().equals("false")||property.getValue().equals("0"))? false : true;
			
		}else if (property.getCode().equals("labelPosition") ){
			// 处理用户自定义属性
			labelPosition = property.getValue();
		}		
	}	
	

	protected void initAutoLinePaletteColorsAndThickness(){
		if(autolienPaletteColors!=null && !autolienPaletteColors.equals(""))
			colors = generateStringArray(autolienPaletteColors);
		if(autolienThickness!=null && !autolienThickness.equals(""))
			thickness = generateStringArray(autolienThickness);
	}

	protected String[] generateStringArray(String singleValue){
		return singleValue.split(",");
	}
	
	
	/*protected StringBuilder getlink(ValueChartType type){//linkSeriesAxisstack
		StringBuilder link = new StringBuilder();
		link.append(this.linkstart);
		link.append(JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_DATASET_CODE, dataset.getCode()));
		
		String jsontemp;
		
		switch(type){
		   case VALUE_CHART_CATEGORY:
			   link.append(",")
		           .append( JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_CHART_TYPE,ValueChartType.VALUE_CHART_CATEGORY.toString()) );
			   
			   if(categorystack !=null && !categorystack.empty()){
				    jsontemp = JsonCellUtils.getJsonCellWhileValueISJsonObject(ChartClickLinkConst.KEY_CHART_REALCATEGORY,categorystack.toJsonArray());
			        if(jsontemp != null && !jsontemp.equals("")){
				            link.append(",")
		                        .append( jsontemp );
			        }
			   }

			   break;
			   
		   case VALUE_CHART_SET:
			   link.append(",")
	               .append( JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_CHART_TYPE,ValueChartType.VALUE_CHART_SET.toString()) );
			   
			   if(categorystack !=null && !categorystack.empty()){
				   jsontemp = JsonCellUtils.getJsonCellWhileValueISJsonObject(ChartClickLinkConst.KEY_CHART_REALCATEGORY,categorystack.toJsonArray());
			       if(jsontemp != null && !jsontemp.equals("")){
				          link.append(",")
	                          .append(jsontemp );
			       }
			   }
				   
		        break;
		        
		   case VALUE_CHART_LEGEND:
			    link.append(",")
	                .append( JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_CHART_TYPE,ValueChartType.VALUE_CHART_LEGEND.toString()) );
			    
			    break;
		}
		
		
		if(linkCategoryAixsstack!=null && !linkCategoryAixsstack.empty()){
		    StringBuilder linktemp2 = new StringBuilder();
            linktemp2.append("{").append(linkCategoryAixsstack).append("}");
            link.append(",")
                .append( JsonCellUtils.getJsonCellWhileValueISJsonObject(ChartClickLinkConst.KEY_CHART_CATEGORYAXIS, linktemp2.toString()) );
	   }
       
	   if(linkSeriesAxisstack!=null && !linkSeriesAxisstack.empty()){
		    StringBuilder linktemp3 = new StringBuilder();
            linktemp3.append("{").append(linkSeriesAxisstack).append("}");
            link.append(",")
                .append( JsonCellUtils.getJsonCellWhileValueISJsonObject(ChartClickLinkConst.KEY_CHART_SERIESAXIS, linktemp3.toString()) );
	   }
		
		
		link.append(this.linkend);
		return link;
	}
	*/
	
	/**
	 * 表示数据节点
	 */
	class DataNode<E,T> extends LinkedHashMap<E,T>{}
	
	/*class AxiscodeANDChartDataCell{
		
		String  columnCode;
		
		ChartDataCell  datacell;
		
		public AxiscodeANDChartDataCell(String columnCode,ChartDataCell datacell){
			this.columnCode = columnCode;
			this.datacell = datacell;
		}
		
		public String toString(){
			if(this.columnCode == null)
				return "";
			StringBuilder result = new StringBuilder();
			ChartHeaderCell headcell = dataset.getChartHeader().getHeaderCell(columnCode);
			result.append(JsonCellUtils.getJsonCellWhileValueISJsonObject(columnCode, JsonCellUtils.getChartHeaderJsonObject(headcell, datacell)));
			return result.toString();
		}
	}*/
	
	//预警线内部类
	protected class InnerWarningLine{
		public InnerWarningLine(double startValue,double endValue,boolean isTrendZone,String color,String alpha){
			this.startValue = startValue;
			this.endValue = endValue;
			this.isTrendZone = isTrendZone;
			this.color = color;
			this.alpha = alpha;
		}

		double startValue;
		double endValue;
		boolean isTrendZone;
		String color;
		String alpha;
	}

	/**
	 * 获取警示线的分组列表
	 *
	 * @return List<InnerWarningLine>， 返回属性组列表
	 */
	protected List<InnerWarningLine> getWarningLines() {
		if(warningLines!=null && warningLines.size()>0) return warningLines;
		PropertyGroup[] preWarning = this.model.getWarnningPropertyGroups();
		if (preWarning == null) return null;
		warningLines = new ArrayList<InnerWarningLine>();
		for(PropertyGroup group : preWarning){
			double startValue = Double.MIN_VALUE;
			double endValue = Double.MAX_VALUE;
			boolean isTrendZone = false;
			String color = null;
			String alpha = "100";
			for (Property prop : group.getProperties()){
				if (prop.getRefCode().equals("startValue")) {
					if (prop.getValue() != null && !prop.getValue().isEmpty()) {
						startValue = Double.parseDouble(prop.getValue());
					}
				} else if (prop.getRefCode().equals("endValue")) {
					if (prop.getValue() != null && !prop.getValue().isEmpty()) {
						endValue = Double.parseDouble(prop.getValue());
					}
				} else if (prop.getRefCode().equals("isTrendZone")) {
					if (prop.getValue() != null
							&& (prop.getValue().equals("true") || prop
									.getValue().equals("1"))) {
						isTrendZone = true;
					}
				} else if (prop.getRefCode().equals("color")) {
					if (prop.getValue() != null && !prop.getValue().isEmpty()) {
						color = prop.getValue();
					}
				} else if (prop.getRefCode().equals("alpha")) {
					if (prop.getValue() != null && !prop.getValue().isEmpty()) {
						alpha = prop.getValue();
					}
				}
			}
			if(endValue == Double.MAX_VALUE) endValue = startValue;
			if(startValue != Double.MIN_VALUE && color!=null && endValue >= startValue)
			              warningLines.add(new InnerWarningLine(startValue,endValue,isTrendZone,color,alpha) );
		}
		if(warningLines!=null && warningLines.size()>0)return warningLines;
		return null;
	}

	protected void updateWarningLines() {
		warningLines =null;
		getWarningLines();
	}
	/**
	 * 判断每个指标是否与警示线标记发生冲突
	 *
	 * @param dvalue
	 * @return
	 */
	protected String compareValueWithTrendlines(String seriesvaluecode){
		if(seriesvaluecode == null || seriesvaluecode.equals("") )
			return "";
		//首先判断字符串是否为数字
		//if(!seriesvaluecode.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$"))
			//return "";
		try{
			double dvalue = Double.valueOf(seriesvaluecode);
		    List<InnerWarningLine>  wlines = this.getWarningLines();
		    if(wlines == null || wlines.size()==0)return "";
		    String color = null;
		    String alpha = "100";
		    for(InnerWarningLine line : wlines){
			    double tempEndValue = Double.MIN_VALUE;
			    if(line.endValue < tempEndValue) continue;
			    if(line.isTrendZone){
				    if(line.startValue<=dvalue && dvalue<line.endValue){
					    color = line.color;
					    alpha = line.alpha;
					    tempEndValue = line.endValue;
				     }
			    }else{
				    if(dvalue >= line.endValue){
					    color = line.color;
					    alpha = line.alpha;
					    tempEndValue = line.endValue;
				    }
			    }
		     }
		     if(color!=null)return  new StringBuilder(" color='").append(color)
				                         .append("' alpha='").append(alpha)
				                         .append("' ").toString();
		   }catch(NumberFormatException e){
			   AppDebug.debug(e);
		   }

		return "";
	}
	
	private Object getRealSeriSum(String sumCode){		
		LinkedHashMap<ChartDataCell, Object> tmpMapData = keymapdata;
		if(summap == null){
			summap = new LinkedHashMap<String,Double>();
			visit(tmpMapData);
		}		
		return summap.get(sumCode);			
	}
	
//	@SuppressWarnings({ "unchecked" })
//	private void visit(Object object){
//		
//		if(!(object instanceof LinkedHashMap<?,?>)){
//			return;
//		}		
//		LinkedHashMap<ChartDataCell, Object> tmpMapData = (LinkedHashMap<ChartDataCell, Object>) object;
//		for(ChartDataCell dataCell:tmpMapData.keySet()){
//			Object obj = tmpMapData.get(dataCell);
//			if(obj instanceof ChartBodyRow){				
//				for(ChartSeriesAxis serie:real_series){						
//					ChartBodyRow chartBodyRow = (ChartBodyRow) obj;
//					ChartDataCell cellSerie = chartBodyRow.getChartDataCell(serie.getColumnCode());
//					String sumCode = serie.getColumnCode();
//					String value = cellSerie.getCaption();	
//					if(StringUtils.isEmpty(value)){
//						continue;
//					}
//					Double sum = new Double(value);
//					for(int i=0; i <cate_series.size()-1; i++)
//					{
//						String pCategory = chartBodyRow.getChartDataCell(cate_series.get(i).getColumnCode()).getCaption();
//						sumCode = sumCode+ "_" + pCategory;
//					}
//					
//					int level = categorys.size();
//					if(cate_series == null || cate_series.size() == 0){
//						level -=1;
//					}
//					for(int i=0; i <level; i++)
//					{
//						String pCategory = chartBodyRow.getChartDataCell(categorys.get(i).getColumnCode()).getCaption();
//						sumCode = sumCode+ "_" + pCategory;
//					}							
//					
//					if(summap.containsKey(sumCode)){						
//						summap.put(sumCode, summap.get(sumCode).add(sum));
//					}else{
//						summap.put(sumCode, sum);
//					}
//					
//					
//				}
//			}else{
//				visit(tmpMapData.get(dataCell));
//			}			
//		}			
//	}
	@SuppressWarnings({ "unchecked" })
	private void visit(Object object) {

		if (!(object instanceof LinkedHashMap<?, ?>)) {
			return;
		}
		LinkedHashMap<ChartDataCell, Object> tmpMapData = (LinkedHashMap<ChartDataCell, Object>) object;
		for (ChartDataCell dataCell : tmpMapData.keySet()) {
			Object obj = tmpMapData.get(dataCell);
			if (obj instanceof ChartBodyRow) {
				for (ChartSeriesAxis serie : real_series) {
					ChartBodyRow chartBodyRow = (ChartBodyRow) obj;
					ChartDataCell cellSerie = chartBodyRow
							.getChartDataCell(serie.getColumnCode());
					String sumCode = getSumCode(chartBodyRow, serie.getColumnCode());
					String value = cellSerie.getCaption();
					if (StringUtils.isEmpty(value)) {
						continue;
					}
					Double sum = new Double(value);
					if (summap.containsKey(sumCode)) {
						summap.put(sumCode, (summap.get(sumCode)+ sum));
					} else {
						summap.put(sumCode, sum);
					}

				}
			} else {
				visit(tmpMapData.get(dataCell));
			}
		}
	}
	/**
	 * 返回和值的ID
	 * @return
	 */
	private String getSumCode(ChartBodyRow chartBodyRow,String serie){			
			String sumCode = "";		
			for(int i=0; i <cate_series.size()-1; i++)
			{
				String pCategory = chartBodyRow.getChartDataCell(cate_series.get(i).getColumnCode()).getCaption();
				sumCode = sumCode+ "_" + pCategory;
			}			
			int level = categorys.size();
			if(cate_series == null || cate_series.size() == 0){
				//level -=1;
			}
			for(int i=0; i <level; i++)
			{
				String pCategory = chartBodyRow.getChartDataCell(categorys.get(i).getColumnCode()).getCaption();
				sumCode = sumCode+ "_" + pCategory;
			}
		return sumCode;
		
	}


}