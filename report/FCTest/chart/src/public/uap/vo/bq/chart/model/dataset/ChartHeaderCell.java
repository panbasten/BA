package uap.vo.bq.chart.model.dataset;

/**
 * 数据集表头单元格信息
 * @author wangqzh
 *
 */
public class ChartHeaderCell extends AbsChartDataElementProxy {

	private static final long serialVersionUID = -5427300806032451919L;
	private String type;
	
	public static final String NNMBER_TYPE = "Number"; 
	public static final String STRING_TYPE = "String"; 
	
	
	private String pkFieldExpr;
	
	public void setpkFieldExpr(String pkFieldExpr){
		this.pkFieldExpr = pkFieldExpr;
	}
	
	public String getpkFieldExpr(){
		return pkFieldExpr;
	}
	
	String totaltype = null;
	
	
	public String getTotaltype() {
		return totaltype;
	}

	public void setTotaltype(String totaltype) {
		this.totaltype = totaltype;
	}

	public ChartHeaderCell(IChartDataElement chartDataElement) {
		super(chartDataElement);
		type = "String";
	}
	public ChartHeaderCell(IChartDataElement chartDataElement, String type) {
		super(chartDataElement);
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public Object clone() {
		ChartHeaderCell newChartHeaderCell = (ChartHeaderCell)super.clone();
		newChartHeaderCell.type = this.type;
		return newChartHeaderCell;
	}
	
	
}
