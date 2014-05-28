package uap.bq.chart.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ufida.iufo.pub.tools.DeepCopyUtilities;

public class DataGenerateResult implements Cloneable {
	public static final String BQ_BLOCK_CHART = "BQ_Chart-Node";
	public static final String BQ_BLOCK_CATEGORIES = "BQ_Categories-Node";
	public static final String BQ_BLOCK_DATASET = "BQ_Dataset-Node";
	public static final String BQ_BLOCK_STYLES = "BQ_Styles-Node";
	public static final String BQ_BLOCK_TRENDLINES = "BQ_Trendlines-Node";
	
	private List<String> categoryLabels = new ArrayList<String>();
	private Map<String,StringBuilder> chartDataDict= new HashMap<String,StringBuilder>();
	
	public String [] getCategoryLabels (){
		return categoryLabels.toArray(new String [categoryLabels.size()]);
	}
	public void setCategoryLabels (List<String> values){
		categoryLabels.clear();
		categoryLabels.addAll(values);
	}
	
	public StringBuilder getChartBlock (String block){
		StringBuilder sb = chartDataDict.get(block);
		if ( sb== null){
			sb = new StringBuilder();
			chartDataDict.put(block, sb);
		}

		return sb;
	}
	
	
	public StringBuilder toChartDataXml(){
		StringBuilder xml = new StringBuilder ();
		xml
		.append(getChartBlock(BQ_BLOCK_CHART)==null || getChartBlock(BQ_BLOCK_CHART).toString().equals("")?"<chart>":getChartBlock(BQ_BLOCK_CHART))
		.append(getChartBlock(BQ_BLOCK_CATEGORIES)==null?"":getChartBlock(BQ_BLOCK_CATEGORIES))
		.append(getChartBlock(BQ_BLOCK_DATASET)==null?"":getChartBlock(BQ_BLOCK_DATASET))
		.append(getChartBlock(BQ_BLOCK_STYLES)==null?"":getChartBlock(BQ_BLOCK_STYLES))
		.append(getChartBlock(BQ_BLOCK_TRENDLINES)==null?"":getChartBlock(BQ_BLOCK_TRENDLINES))
		.append("</chart>")
		;
		return xml;
		
	}
	@SuppressWarnings("unchecked")
	@Override
	protected Object clone() throws CloneNotSupportedException {
		DataGenerateResult newDataGenerateResult = null;
		try{
		newDataGenerateResult = (DataGenerateResult) super.clone();
		newDataGenerateResult.categoryLabels = (List<String>) DeepCopyUtilities.copy(this.categoryLabels);
		newDataGenerateResult.chartDataDict = (Map<String, StringBuilder>) DeepCopyUtilities.copy(this.chartDataDict);
		}
		catch (Exception e){
			throw new RuntimeException (e);
		}
		return newDataGenerateResult;
	}
	
	
}
