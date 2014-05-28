package uap.vo.bq.chart.model.dataset;

import com.ufida.iufo.pub.tools.AppDebug;
/**
 * 数据集
 * @author wangqzh
 *
 */
public class ChartDataset extends AbsChartDataElementProxy  {

	private static final long serialVersionUID = -708429199264948780L;
	
	private ChartHeader chartHeader = null;
	
	private ChartBody chartBody = null;
	

	public ChartDataset(IChartDataElement chartDataElement) {
		super(chartDataElement);
	}
	
	public ChartDataset(IChartDataElement chartDataElement,ChartHeader chartHeader,ChartBody chartBody) {
		super(chartDataElement);
		this.chartHeader = chartHeader;
		this.chartBody = chartBody;
	}

	public ChartHeader getChartHeader() {
		return chartHeader;
	}

	public void setChartHeader(ChartHeader chartHeader) {
		this.chartHeader = chartHeader;
		
	}

	public ChartBody getChartBody() {
		return chartBody;
	}

	public void setChartBody(ChartBody chartBody) {
		this.chartBody = chartBody;
	}

	public boolean swap(int column1, int column2) {
		this.chartHeader.swap(column1, column2);
		this.chartBody.swapColumn(column1, column1);
		return false;
	}

	public void clear() {
		this.chartBody.clear();
		this.chartHeader.clear();
	}

	@Override
	public Object clone(){
		ChartDataset copy = null;
		copy = (ChartDataset) super.clone();
		if (this.chartBody != null)
			copy.chartBody = (ChartBody) this.chartBody.clone();
		if (this.chartHeader != null)
			copy.chartHeader = (ChartHeader) this.chartHeader.clone();
		return copy;
	}
	
	public boolean isEmpty(){
		return chartHeader == null || chartHeader.getHeaderCells().length == 0 ||
				chartBody == null || chartBody.getChartBodyRows().length == 0;
	}
	
	public String toString(){
		//生成头部信息code、caption
		StringBuilder xml = new StringBuilder();
		xml.append("<dataset code ='")
		.append(this.getCode()==null?"":this.getCode())
		.append("' caption='")
		.append(this.getCaption()==null?"":this.getCaption())
		.append("' />\n");
		
		//生成ChartHeader信息
		ChartHeader head = this.getChartHeader();
		if(head==null)xml.append("<header />");
		else{
			xml.append("  <header code ='")
			.append(head.getCode()==null?"":head.getCode())
			.append("' caption='")
			.append(head.getCaption()==null?"":head.getCaption())
			.append("' >\n");
			
			ChartHeaderCell[] headcells = head.getHeaderCells();
			for(ChartHeaderCell chc : headcells){
				xml.append("    <cell code ='")
				.append(chc.getCode()==null?"":chc.getCode())
				.append("' caption='")
				.append(chc.getCaption()==null?"":chc.getCaption())
				.append("' type='")
				.append(chc.getType()==null?"":chc.getType())
				.append("' />\n");
			}
			xml.append("  </header>\n");
		}
		
		//生成body信息
		ChartBody body=this.getChartBody();
		if(body==null)xml.append("  <body />");
		else{
			xml.append("  <body code ='")
			.append(body.getCode()==null?"":body.getCode())
			.append("' caption='")
			.append(body.getCaption()==null?"":body.getCaption())
			.append("' >\n");
			
			ChartBodyRow[] bodyrows = body.getChartBodyRows();
			for(ChartBodyRow cbc : bodyrows){
				xml.append("    <row code ='")
				.append(cbc.getCode()==null?"":cbc.getCode())
				.append("' caption='")
				.append(cbc.getCaption()==null?"":cbc.getCaption())
				.append("' >\n");
			
				ChartDataCell[] datacells = cbc.getChartDataCells();
				for(ChartDataCell cdc : datacells){
					xml.append("      <cell refcode ='")
					.append(cdc.getChartHeaderCellCode()==null?"":cdc.getChartHeaderCellCode())
					.append("' code ='")
					.append(cdc.getCode()==null?"":cdc.getCode())
					.append("' >")
					.append(cbc.getCaption()==null?"":cdc.getCaption())
					.append("</cell>\n");
				}
				xml.append("    </row>\n");	
			}
			xml.append("  </body>\n");
		}
		xml.append("</dataset>");
		
		return xml.toString();
	}
	
	/**
	 * 鏁版嵁椤瑰疄鐜扮被
	 * @author wangqzh
	 *
	 */
	public static class ChartDataElement implements IChartDataElement{
		
		private static final long serialVersionUID = 1L;
		private String code;
		private String caption;
		private boolean changed;
		/**
		 * Caption 多语
		 */
		private String mulCode = null;
		
		public ChartDataElement() {
			super();
			this.code = null;
			this.caption = null;
		}
		
		public ChartDataElement(String code, String caption) {
			super();
			this.code = code;
			this.caption = caption;
		}
		
		public ChartDataElement(String code, String caption,String mulCode) {
			super();
			this.code = code;
			this.caption = caption;
			this.mulCode = mulCode;
		}
		
		@Override
		public String getCode() {
			return code;
		}

		@Override
		public void setCode(String code) {
			this.code = code;
		}

		public String getMulCode() {
			return mulCode;
		}

		public void setMulCode(String mulCode) {
			this.mulCode = mulCode;
		}

		@Override
		public String getCaption() {
			return caption;
		}

		@Override
		public void setCaption(String caption) {
			if(this.caption == null && caption == null){
				return;
			}
			if(this.caption != null && this.caption.equals(caption)){
				return;
			}
			this.caption = caption;
			this.mulCode = null;
		}
		
		public void setMulCaption(String mulCaption){
			this.caption = mulCaption;
		}

		@Override
		public boolean isChanged() {
			return changed;
		}

		@Override
		public void setChanged(boolean changed) {
			this.changed = changed;
		}
		
		@Override
		public Object clone() {
			ChartDataElement copy = null;
			try{
				copy = (ChartDataElement) super.clone();
				copy.caption = this.caption;
				copy.code = this.code;
				copy.changed = this.changed;
			}catch (Exception e) {
				AppDebug.debug(e);
				throw new RuntimeException(e);
			}
			return copy;
		}
		
	}
	
}
