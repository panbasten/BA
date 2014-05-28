package uap.vo.bq.chart.model;

import java.io.Serializable;

/**
 * ���ݰ�������
 * @author zhanglld
 *
 */
public class DataBindingRefGroup extends PropertyGroup implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final String COMMONCOLUMNCODE = "COMMONCOLUMNCODE";
	
	/*�󶨵����ݼ�*/
	private String datasetDefine;
	/*�󶨵�������*/
	private String column;
	
	public DataBindingRefGroup(String refCode){
		super(refCode);
	}

	public String getDatasetDefine() {
		return datasetDefine;
	}

	public void setDatasetDefine(String datasetDefine) {
		this.datasetDefine = datasetDefine;
		this.setChange(true);
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
		this.setChange(true);
	}

	@Override
	public Object clone() {
		DataBindingRefGroup newDataBindingRefGroup = null;
		newDataBindingRefGroup = (DataBindingRefGroup)super.clone();
		newDataBindingRefGroup.column = this.column;
		newDataBindingRefGroup.datasetDefine = this.datasetDefine;
		return newDataBindingRefGroup;
	}	
	
	
}
