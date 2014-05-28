package uap.vo.bq.chart.model.databinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ufida.iufo.pub.tools.DeepCopyUtilities;

public class ChartDataBindingMgr implements Serializable, Cloneable{

	private static final long serialVersionUID = 1L;
	/**�󶨶������鶨��*/
	private List<IChartDataBinding> chartDataBindings = new ArrayList<IChartDataBinding>();
	
	/**
	 * ׷�ӵ������󵽰��б���
	 * @param chartDataBinding
	 */
	public void add (IChartDataBinding chartDataBinding){
		this.chartDataBindings.add((ChartDataBinding) chartDataBinding);
	}
	
	/**
	 * ��ָ��λ��׷�Ӹ�������
	 * @param index
	 * @param chartDataBinding
	 */
	public void add (int index, IChartDataBinding chartDataBinding){
		this.chartDataBindings.add(index, (ChartDataBinding) chartDataBinding);
	}
	
	/**
	 * ����ColumnCode��ȡ�󶨶���
	 * @param columnCode
	 * @return
	 */
	public IChartDataBinding getChartDataBindingByColumnCode (String columnCode){
		for (IChartDataBinding dbg : this.chartDataBindings){
			if (dbg.getColumnCode().equals(columnCode)){
				return dbg;
			}
		}
		return null;
	}
	/**
	 * ��������code��ʾ��ȡChartDataBinding����
	 * @param code
	 * @return
	 */
	public IChartDataBinding getChartDataBindingByCode (String code){
		for (IChartDataBinding dbg : this.chartDataBindings){
			if (dbg.getCode().equals(code)){
				return dbg;
			}
		}
		return null;
	}
	/**
	 * ��ȡChartDataBinding����
	 * @return
	 */
	public IChartDataBinding[] getChartBindings(){
		return this.chartDataBindings.toArray(new IChartDataBinding[chartDataBindings.size()]);
	}
	/**
	 * �滻
	 * @param oldChartDataBinding
	 * @param newChartDataBinding
	 * @return
	 */
	public boolean replace (IChartDataBinding oldChartDataBinding, IChartDataBinding newChartDataBinding){
		int index = -1;
		for (index = 0; index < chartDataBindings.size(); ++index){
			if ( chartDataBindings.get(index).equals(oldChartDataBinding)){
				break;
			}
		}
		if ( -1 == index || index == chartDataBindings.size()){
			return false;
		}
		
		this.chartDataBindings.remove(index);
		this.chartDataBindings.add(index,newChartDataBinding);
		return	true;
	}
	
	/**
	 * ��ָ������λ�õ�ChartDataBinding �滻Ϊ��������
	 * @param index
	 * @param chartDataBinding
	 * @return
	 */
	public boolean replace (int index, IChartDataBinding chartDataBinding){
		if ( 0 > index || chartDataBindings.size() <= index){
			return false;
		}
		this.chartDataBindings.remove(index);
	
		this.chartDataBindings.add(index,chartDataBinding);
		
		return true;
	}
	/**
	 * ����
	 * @param index1
	 * @param index2
	 */
	public void swap (int index1, int index2){
		Collections.swap(chartDataBindings, index1, index2);
	}
	/**
	 * �Ƴ�ָ������
	 * @param chartDataBinding
	 */
	public void remove (IChartDataBinding chartDataBinding){
		this.chartDataBindings.remove(chartDataBinding);
	}
	/**
	 * �Ƴ�ָ��λ�õĶ���
	 * @param index
	 */
	public void remove (int index){
		this.chartDataBindings.remove(index);
	}
	/**
	 * ���
	 */
	public void clear (){
		this.chartDataBindings.clear();	
	}
	
	/**
	 * ��¡
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		ChartDataBindingMgr copy = (ChartDataBindingMgr) super.clone();
		copy.chartDataBindings = (List<IChartDataBinding>) DeepCopyUtilities.copy(this.chartDataBindings);
		return copy;
	}
}
