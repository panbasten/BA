package uap.vo.bq.chart.model.dataset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ufida.iufo.pub.tools.DeepCopyUtilities;

/**
 * 数据集的行数据表示信息
 * @author wangqzh
 *
 */
public class ChartBodyRow extends AbsChartDataElementProxy{

	private static final long serialVersionUID = 5465623598308207021L;
	
	private List<ChartDataCell> chartDataCells = new ArrayList<ChartDataCell>();

	public ChartBodyRow(IChartDataElement chartDataElement) {
		super(chartDataElement);
	}
	

	public void addChartDataCell(ChartDataCell chartDataCell) {
		this.chartDataCells.add(chartDataCell);
	}

	public void addChartDataCell(int index, ChartDataCell chartDataCell) {
		this.chartDataCells.add(index,chartDataCell);
		
	}

	public ChartDataCell getChartDataCell(int index) {
		return this.chartDataCells.get(index);
	}

	public ChartDataCell getChartDataCell(String code) {
		if(StringUtils.isEmpty(code)){
			return null;
		}
		for (ChartDataCell chartDataCell : this.chartDataCells) {
			if(code.equals(chartDataCell.getChartHeaderCellCode())){
				return chartDataCell;
			}
		}
		return null;
	}

	public int getChartDataCellCount() {
		return this.chartDataCells.size();
	}

	public void removeChartDataCell(int index) {
		this.chartDataCells.remove(index);
		
	}

	public void removeChartDataCell(String code) {
		if(StringUtils.isEmpty(code)){
			return ;
		}
		ChartDataCell removeChartDataCell = null;
		for (ChartDataCell chartDataCell : this.chartDataCells) {
			if(code.equals(chartDataCell.getCode())){
				removeChartDataCell = chartDataCell;
			}
		}
		if(removeChartDataCell != null){
			this.chartDataCells.remove(removeChartDataCell);
		}
		
	}

	public void clear() {
		this.chartDataCells.clear();
		
	}

	public boolean swap(int column1, int column2) {
		try{
			Collections.swap(chartDataCells, column1, column2);
			return true;
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}

	public boolean swapByHeaderCode(String code1, String code2) {
		int column1 = -1;
		int column2 = -1;
		int column = 0;
		for (ChartDataCell cell : this.chartDataCells){
			if (cell.getChartHeaderCellCode().equals(code1) && column1 == -1){
				column1 = column;
			}
			else if (cell.getChartHeaderCellCode().equals(code2) && column2 == -1){
				column2 = column;
			} 
			else if (column1 != -1 && column2 != -1){
				break;
			}
			column++;
		}
		
		return swap (column1, column2);
	}


	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		ChartBodyRow copy = (ChartBodyRow) super.clone();
		copy.chartDataCells = (List<ChartDataCell>) DeepCopyUtilities.copy(this.chartDataCells);
		return copy;
	}

	public ChartDataCell[] getChartDataCells() {
		// TODO Auto-generated method stub
		return chartDataCells.toArray(new ChartDataCell[chartDataCells.size()]);
	}

}
