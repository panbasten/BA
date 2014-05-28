package uap.vo.bq.chart.model.dataset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ufida.iufo.pub.tools.AppDebug;
import com.ufida.iufo.pub.tools.DeepCopyUtilities;

/**
 * 数据集的数据体封装表示
 * @author wangqzh
 *
 */
public class ChartBody extends AbsChartDataElementProxy{

	private static final long serialVersionUID = 7710044922644143686L;
	private List<ChartBodyRow> chartBodyRows = new ArrayList<ChartBodyRow>();

	public ChartBody(IChartDataElement chartDataElement) {
		super(chartDataElement);
	}

	public void addChartBodyRow(ChartBodyRow row) {
		this.chartBodyRows.add(row);
	}

	public void addChartBodyRow(int index, ChartBodyRow row) {
		this.chartBodyRows.add(index, row);
	}

	public ChartBodyRow getChartBodyRow(int index) {
		return this.chartBodyRows.get(index);
	}

	public int getChartBodyRowCount() {
		return this.chartBodyRows.size();
	}

	public ChartBodyRow[] getChartBodyRows() {
		return this.chartBodyRows.toArray(new ChartBodyRow[chartBodyRows
				.size()]);
	}

	public void removeChartBodyRow(int index) {
		this.chartBodyRows.remove(index);
	}

	public void clear() {
		this.chartBodyRows.clear();
	}

	public ChartDataCell[] getColumnCells(int columnIndex) {
		List<ChartDataCell> columnCells = new ArrayList<ChartDataCell>();
		for (ChartBodyRow row : this.chartBodyRows) {
			int column = 0;
			for (ChartDataCell cell : row.getChartDataCells()) {
				if (column == columnIndex) {
					columnCells.add(cell);
					break;
				}
				++column;
			}
		}
		return (ChartDataCell[]) columnCells
				.toArray(new ChartDataCell[columnCells.size()]);
	}

	public ChartDataCell[] getColumnCells(ChartHeaderCell headerCell) {
		
		List<ChartDataCell> columnCells = new ArrayList<ChartDataCell>();
		for (ChartBodyRow row : this.chartBodyRows) {
			
			for (ChartDataCell cell : row.getChartDataCells()) {
				if (cell.getChartHeaderCellCode().equals (headerCell.getCode())) {
					columnCells.add(cell);
				}
			}
		}
		return columnCells
				.toArray(new ChartDataCell[columnCells.size()]);
	}

	public boolean swapColumn(int column1, int column2) {
		boolean isSuccessfull = true;
		for (ChartBodyRow row : this.chartBodyRows) {
			isSuccessfull &= row.swap(column1, column2);
		}
		return isSuccessfull;
	}

	public boolean swapColumn(String headerCellCode1, String headerCellCode2) {
		boolean isSuccessfull = true;
		for (ChartBodyRow row : this.chartBodyRows) {
			isSuccessfull &= row.swapByHeaderCode(headerCellCode1, headerCellCode2);
		}
		return isSuccessfull;
	}

	public boolean swapRow(int row1, int row2) {
		if (row1 < 0 || row1 >= this.chartBodyRows.size() || 
				row2 < 0 || row2 >= this.chartBodyRows.size())
			return false;
		try{
		Collections.swap(chartBodyRows, row1, row2);
		}catch (Exception e){
			AppDebug.debug(e);
			return false;
		}
		return true;
	}

	public boolean swapRow(String rowCode1, String rowCode2) {
		int row1 = -1, row2 = -1;
		int rowIndex = 0;
		for (ChartBodyRow row : this.chartBodyRows){
			row1 = (row.getCode().equals(rowCode1)&& -1 == row1) ? rowIndex : row1;
			row2 = (row.getCode().equals(rowCode2)&& -1 == row2) ? rowIndex : row2;
			rowIndex++;
			if (row1 != -1 && row2 != -1)
				break;
		}
		return swapRow (row1, row2);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		ChartBody copy = null;
		try {
			copy = (ChartBody) super.clone();
			copy.chartBodyRows = (List<ChartBodyRow>) DeepCopyUtilities
					.copy(this.chartBodyRows);
		} catch (Exception e) {
			AppDebug.debug(e);
			throw new RuntimeException(e);
		}
		return copy;
	}
}
