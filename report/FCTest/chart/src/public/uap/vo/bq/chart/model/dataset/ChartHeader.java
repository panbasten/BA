package uap.vo.bq.chart.model.dataset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ufida.iufo.pub.tools.AppDebug;
import com.ufida.iufo.pub.tools.DeepCopyUtilities;
/**
 * 数据集表头信息
 * @author wangqzh
 *
 */
public class ChartHeader extends AbsChartDataElementProxy {

	private static final long serialVersionUID = 3707737921877540697L;
	private List<ChartHeaderCell> chartHeaderCells = new ArrayList<ChartHeaderCell>();

	public ChartHeader(IChartDataElement chartDataElement) {
		super(chartDataElement);
	}
	
	public void addHeaderCell(ChartHeaderCell chartHeaderCell) {
		this.chartHeaderCells.add(chartHeaderCell);

	}

	public void addHeaderCell(int index, ChartHeaderCell chartHeaderCell) {
		this.chartHeaderCells.add(index, chartHeaderCell);

	}

	public ChartHeaderCell getHeaderCell(int index) {
		return this.chartHeaderCells.get(index);
	}
	
	public int indexOf(String code) {
		if(StringUtils.isEmpty(code)){
			return -1;
		}
		for (ChartHeaderCell chartHeaderCell : this.chartHeaderCells) {
			if(chartHeaderCell.getCode().equals(code)){
				return this.chartHeaderCells.indexOf(chartHeaderCell);
			}
		}
		return -1;
	}

	public ChartHeaderCell getHeaderCell(String code) {
		if(StringUtils.isEmpty(code)){
			return null;
		}
		for (ChartHeaderCell chartHeaderCell : this.chartHeaderCells) {
			if(chartHeaderCell.getCode().equals(code)){
				return chartHeaderCell;
			}
		}
		return null;
	}

	public ChartHeaderCell[] getHeaderCells() {
		return this.chartHeaderCells.toArray(new ChartHeaderCell[this.chartHeaderCells.size()]);
	}
	
	public int getHeaderCellCount() {
		return this.chartHeaderCells.size();
	}

	public void removeHeaderCell(int index) {
		this.chartHeaderCells.remove(index);

	}

	public void removeHeaderCell(String code) {
		if(StringUtils.isEmpty(code)){
			return ;
		}
		ChartHeaderCell removeChartHeaderCell = null;
		for (ChartHeaderCell chartHeaderCell : this.chartHeaderCells) {
			if(chartHeaderCell.getCode().equals(code)){
				removeChartHeaderCell = chartHeaderCell;
			}
		}
		if(removeChartHeaderCell == null){
			this.chartHeaderCells.remove(removeChartHeaderCell);
		}

	}

	public void clear() {
		this.chartHeaderCells.clear();

	}

	public boolean swap(int column1, int column2) {
		
		if(column1 <0 || column1 >= this.chartHeaderCells.size()){
			return false;
		}
		if(column2 <0 || column2 >= this.chartHeaderCells.size()){
			return false;
		}
		
		Collections.swap(chartHeaderCells, column1, column2);
		
		return true;
	}

	public boolean swap(String code1, String code2) {
		if (StringUtils.isEmpty(code1) || StringUtils.isEmpty(code2)) {
			return false;
		}
		ChartHeaderCell chartHeaderCell1 = null;
		ChartHeaderCell chartHeaderCell2 = null;
		for (ChartHeaderCell chartHeaderCell : this.chartHeaderCells) {
			if (code1.equals(chartHeaderCell.getCode())) {
				chartHeaderCell1 = chartHeaderCell;
			}

			if (code2.equals(chartHeaderCell.getCode())) {
				chartHeaderCell2 = chartHeaderCell;
			}
		}

		if (chartHeaderCell1 == null || chartHeaderCell2 == null) {
			return false;
		}

		return swap(this.chartHeaderCells.indexOf(chartHeaderCell1),
				this.chartHeaderCells.indexOf(chartHeaderCell2));
	}


	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		ChartHeader copy = null;
		try{
			copy = (ChartHeader) super.clone();
			copy.chartHeaderCells = (List<ChartHeaderCell>) DeepCopyUtilities.copy(this.chartHeaderCells);
		}catch (Exception e) {
			AppDebug.debug(e);
			throw new RuntimeException(e);
		}
		return copy;
	}

	
}
