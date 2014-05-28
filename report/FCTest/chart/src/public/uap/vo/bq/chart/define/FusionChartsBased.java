package uap.vo.bq.chart.define;


public class FusionChartsBased {
	//Ïà¶ÔÂ·¾¶
	private String xsltFile;
	private String javascriptFiles;
	
	public FusionChartsBased(String xsltFile, String javascriptFiles){
		this.xsltFile = xsltFile;
		this.javascriptFiles = javascriptFiles;
	}
	
	public String getXsltFile() {
		return xsltFile;
	}

	public String getJavascriptFiles() {
		return javascriptFiles;
	}
}
