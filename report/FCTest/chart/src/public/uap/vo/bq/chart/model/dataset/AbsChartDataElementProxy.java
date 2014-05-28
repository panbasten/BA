package uap.vo.bq.chart.model.dataset;

import com.ufida.iufo.pub.tools.AppDebug;
import com.ufida.iufo.pub.tools.DeepCopyUtilities;

/**
 * 数据项的抽象代理类
 * @author wangqzh
 *
 */
public abstract class AbsChartDataElementProxy implements IChartDataElement{
	
	private static final long serialVersionUID = 2111971918824123505L;
	
	private IChartDataElement chartDataElement = null;

	public AbsChartDataElementProxy(IChartDataElement chartDataElement){
		this.chartDataElement = chartDataElement;
	}

	@Override
	public String getCode() {
		if(this.chartDataElement == null){
			return null;
		}
		return this.chartDataElement.getCode();
	}

	@Override
	public void setCode(String code) {
		if(this.chartDataElement == null){
			return ;
		}
		this.chartDataElement.setCode(code);
	}

	@Override
	public String getCaption() {
		if(this.chartDataElement == null){
			return null;
		}
		return this.chartDataElement.getCaption();
	}

	@Override
	public void setCaption(String caption) {
		if(this.chartDataElement == null){
			return ;
		}
		this.chartDataElement.setCaption(caption);
	}

	@Override
	public boolean isChanged() {
		if(this.chartDataElement == null){
			return false;
		}
		return this.chartDataElement.isChanged();
	}

	@Override
	public void setChanged(boolean changed) {
		if(this.chartDataElement == null){
			return ;
		}
		chartDataElement.setChanged(changed);
	}

	public IChartDataElement getChartDataElement() {
		return this.chartDataElement;
	}

	public void setChartDataElement(IChartDataElement chartDataElement) {
		this.chartDataElement = chartDataElement;
	}

	@Override
	public Object clone() {
		AbsChartDataElementProxy copy = null;
		try{
			copy = (AbsChartDataElementProxy) super.clone();
			copy.chartDataElement = (IChartDataElement)DeepCopyUtilities.copy(this.chartDataElement);
		}catch (CloneNotSupportedException e) {
			AppDebug.debug(e);
			throw new RuntimeException(e);
		}
		return copy;
	}
	
	

}
