package uap.vo.bq.chart.define;

import java.io.Serializable;

public class ChartDefineDigest implements Serializable, Cloneable{
	private static final long serialVersionUID = 100L;
	/*图表索引标示*/
	private int index;
	private String code;
	private String name;
	private String category;
	private String type;
	private Icon icon;
	
	public ChartDefineDigest(int index,String type, String code, String name, String categroy,  Icon icon){
		this.index = index;
		this.type = type;
		this.code = code;
		this.icon = icon;
		this.name = name;
		this.category = categroy;
		this.type = type;
	}
	
	public int getIndex (){
		return index;
	}
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Icon getIcon() {
		return icon;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getCategory(){
		return category;
	}
	public void setCategory(String category){
		this.category = category;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + index;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChartDefineDigest other = (ChartDefineDigest) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (index != other.index)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public Object clone() {
		ChartDefineDigest newChartDefineDigest = null;
		try {
			newChartDefineDigest = (ChartDefineDigest)super.clone();
			newChartDefineDigest.index = this.index;
			newChartDefineDigest.code = this.code;
			newChartDefineDigest.name = this.name;
			newChartDefineDigest.category = this.category;
			newChartDefineDigest.type = this.type;
			newChartDefineDigest.icon = (Icon) this.icon.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException (e);
		}
		
		return newChartDefineDigest;
	}
}
