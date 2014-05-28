package uap.bq.chart.generator.link;

import uap.bq.chart.ChartClickLinkConst;
import uap.vo.bq.chart.model.dataset.ChartDataCell;
import uap.vo.bq.chart.model.dataset.ChartHeaderCell;

public class JsonCellUtils {

	public static String getJsonCell(String key , String value){
		if(key==null || value==null) 
			return "";
		return new StringBuilder("\\\"").append(key).append("\\\":\\\"").append(value).append("\\\"").toString();
	}
	
	
	public static String getJsonCellWhileValueISJsonObject(String key , String object){
		if(key==null || object==null) 
			return "";
		return new StringBuilder("\\\"").append(key).append("\\\":").append(object).toString();
	}
	
	
	public static String getJsonCell(String key , JsonStack value){
		if(key==null || value==null) 
			return "";
		return new StringBuilder("\\\"").append(key).append("\\\":\\\"").append(value.toString()).append("\\\"").toString();
	}
	
	
	public static String getChartDataCellJsonObject(ChartDataCell datacell){
		if(datacell==null)
			return null;
		StringBuilder dataCellJsonObject = new StringBuilder();
		String temp;
		boolean isfirst = true;
		//dataCellJsonObject.append("{");
		temp = JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_CHARTDATACELL_ISALL, ""+datacell.isAll());
		if(temp != null && !temp.equals("")){
			dataCellJsonObject.append(temp);
			isfirst = false;
		}
		
		temp = JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_CHARTDATACELL_CODE, datacell.getCode());
		if(temp != null && !temp.equals("")){
			if(!isfirst)
        		dataCellJsonObject.append(",");
			dataCellJsonObject.append(temp);
			isfirst = false;
		}
		temp = JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_CHARTDATACELL_PKVALUE, datacell.getPkValue());
        if(temp != null && !temp.equals("")){
        	if(!isfirst)
        		dataCellJsonObject.append(",");
			dataCellJsonObject.append(temp);
			isfirst = false;
		}
		temp = JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_CHARTDATACELL_CAPTIONVALUE, datacell.getCaption());
        if(temp != null && !temp.equals("")){
        	if(!isfirst)
        		dataCellJsonObject.append(",");
			dataCellJsonObject.append(temp);
			isfirst = false;
		}
		temp = JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_CHARTDATACELL_CANDRILLUP, ""+datacell.isCanDrillUp());
        if(temp != null && !temp.equals("")){
        	if(!isfirst)
        		dataCellJsonObject.append(",");
			dataCellJsonObject.append(temp);
			isfirst = false;
		}
		temp = JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_CHARTDATACELL_CANDRILLDOWN, ""+datacell.isCanDrillDown());
        if(temp != null && !temp.equals("")){
        	if(!isfirst)
        		dataCellJsonObject.append(",");
			dataCellJsonObject.append(temp);
		}
        if(dataCellJsonObject.length()==0)
        	return null;
		//dataCellJsonObject.append("}");
		return new StringBuilder().append("{").append(dataCellJsonObject).append("}").toString();
	}
	
	
	public static String getChartHeaderCellJsonCell(ChartHeaderCell headcell){
		if(headcell == null)
			return null;
		StringBuilder headCellJsonCell = new StringBuilder();
		String temp;
		boolean isfirst = true;
		temp = JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_CHARTHEADACELL_CODE,headcell.getCode());
		if(temp != null && !temp.equals("")){
			headCellJsonCell.append(temp);
			isfirst = false;
		}
		
		temp = JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_CHARTHEADACELL_CAPTION,headcell.getCaption());
		if(temp != null && !temp.equals("")){
			if(!isfirst)
				headCellJsonCell.append(",");
			headCellJsonCell.append(temp);
			isfirst = false;
		}
		
		temp = JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_CHARTHEADACELL_TOTALTYPE,headcell.getTotaltype());
		if(temp != null && !temp.equals("")){
			if(!isfirst)
				headCellJsonCell.append(",");
			headCellJsonCell.append(temp);
			isfirst = false;
		}
		
		temp = JsonCellUtils.getJsonCell(ChartClickLinkConst.KEY_CHARTHEADACELL_PKFIELDEXPRE,headcell.getpkFieldExpr());
		if(temp != null && !temp.equals("")){
			if(!isfirst)
				headCellJsonCell.append(",");
			headCellJsonCell.append(temp);
		}
		
		if(headCellJsonCell.length()==0)
	        	return null;
		
		return headCellJsonCell.toString();
	}
	
	public static String getChartHeaderJsonObject(ChartHeaderCell headcell,ChartDataCell datacell){
		if(headcell == null )
			return null;
		StringBuilder headerObject = new StringBuilder();
		String temp;
		//headerObject.append("{");
		boolean isfirst = true;
		temp = getChartHeaderCellJsonCell(headcell);
		if(temp != null && !temp.equals("")){
			headerObject.append(temp);
			isfirst = false;
		    
		}
		temp = getJsonCellWhileValueISJsonObject(ChartClickLinkConst.KEY_CHARTHEADACELL_VALUE,getChartDataCellJsonObject(datacell));
		if(temp != null && !temp.equals("")){
			if(!isfirst)
				headerObject.append(",");
			headerObject.append(temp);
		}
		
		if(headerObject.length()==0)
			return null;
		//headerObject.append("}");
		return new StringBuilder().append("{").append(headerObject).append("}").toString();
	}
	
	
}
