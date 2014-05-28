package uap.vo.bq.chart.model.dataset;

/**
 * 单元格数据 @author wangqzh
 *
 */
public class ChartDataCell extends AbsChartDataElementProxy{

	private static final long serialVersionUID = 5381453105554996578L;
	
	
	/*public enum Align{
        TOP,TOPANDBOTTOM,CENTRE,BOTTOM,NONE;
    }
    
    private Align align = Align.NONE;
    
    public Align getAlign(){
        
        return align;
    }
    
    public void setAlign(Align align){
        
        this.align = align;
    }*/

	
    private boolean canDrillUp;
    private boolean canDrillDown;
    private String  pkValue;
    
	public boolean isCanDrillUp() {
		return canDrillUp;
	}

	public void setCanDrillUp(boolean canDrillUp) {
		this.canDrillUp = canDrillUp;
	}

	public boolean isCanDrillDown() {
		return canDrillDown;
	}

	public void setCanDrillDown(boolean canDrillDown) {
		this.canDrillDown = canDrillDown;
	}

	public String getPkValue() {
		return pkValue;
	}

	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}


	private String chartHeaderCellCode = null;

	public ChartDataCell(IChartDataElement chartDataElement) {
		super(chartDataElement);
	}
	
	public ChartDataCell(String chartHeaderCellCode,IChartDataElement chartDataElement) {
		super(chartDataElement);
		this.chartHeaderCellCode = chartHeaderCellCode;
	}

	@Override
	public IChartDataElement getChartDataElement() {
		return super.getChartDataElement();
	}

	@Override
	public void setChartDataElement(IChartDataElement chartDataElement) {
		super.setChartDataElement(chartDataElement);
	}

	public String getChartHeaderCellCode() {
		return this.chartHeaderCellCode;
	}

	public void setChartHeaderCellCode(String headerCellCode) {
		this.chartHeaderCellCode = headerCellCode;
	}
	
	public boolean isAll(){
		return false;
	}
	
	
	@Override
	public Object clone() {
		ChartDataCell newChartDataCell = null;
		newChartDataCell = (ChartDataCell)super.clone();
		newChartDataCell.chartHeaderCellCode = this.chartHeaderCellCode;
		newChartDataCell.canDrillDown = this.canDrillDown;
		newChartDataCell.canDrillUp = this.canDrillUp;
		newChartDataCell.pkValue = this.pkValue;
		return newChartDataCell;
	}
	
	//对应两个不同的对象，我们假设他们相等，当且仅当对象的code的值相等
	@Override
	public int hashCode(){
		if(this.getCode()==null)return super.hashCode();
		return this.getCode().hashCode();
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj)return true;
		//if(obj == null)return false;
		if(!(obj instanceof ChartDataCell))return false;
		ChartDataCell cdc = (ChartDataCell)obj;
		return     cdc.getCode().equals(this.getCode()) 
				//&& this.hashCode()==cdc.hashCode()
				&& this.getCaption().equals(cdc.getCaption())
				&& this.getChartHeaderCellCode().equals(cdc.getChartHeaderCellCode());
	}

	/**
	 * 
	 * @author wangqzh
	 *
	 */
	public static class AllChartDataCell extends ChartDataCell{

		private static final long serialVersionUID = 1L;

		public AllChartDataCell(IChartDataElement chartDataElement) {
			super(chartDataElement);
			// TODO Auto-generated constructor stub
		}
		public AllChartDataCell(String chartHeaderCellCode,IChartDataElement chartDataElement) {
			super(chartHeaderCellCode,chartDataElement);
			// TODO Auto-generated constructor stub
		}
		@Override
		public Object clone() {
			return super.clone();
		}
		@Override
		public boolean isAll(){
			return true;
		}
		
	}

}
