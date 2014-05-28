package uap.vo.bq.chart.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ufida.iufo.pub.tools.DeepCopyUtilities;

import uap.vo.bq.chart.model.PropertyGroup;

public class ChartTheme implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private String colorCode;
	private String styleCode;
	
	

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public String getStyleCode() {
		return styleCode;
	}

	public void setStyleCode(String styleCode) {
		this.styleCode = styleCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		ChartTheme newObject = (ChartTheme) super.clone();
		newObject.code = this.code;
		newObject.name = this.name;
		newObject.colorCode = this.colorCode;
		newObject.styleCode = this.styleCode;
		return newObject;
	}
	
	
	public static class ChartStyle  implements Serializable, Cloneable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String code;
		private String name;

		private Map<String, PropertyGroup> groupMap = new HashMap<String, PropertyGroup>();
		
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public void addPropertyGroup (PropertyGroup group){
			if (group != null){
				groupMap.put(group.getCode(), group);
			}
		}
		
		public PropertyGroup getPropertyGroup(String code){
			return groupMap.get(code);
		}
		
		public List<PropertyGroup> getPropertyGroups(){
			List<PropertyGroup> ls = new ArrayList<PropertyGroup>();
			ls.addAll(groupMap.values());
			return ls;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		protected Object clone() throws CloneNotSupportedException {
			ChartStyle newObject = (ChartStyle) super.clone();
			newObject.code = this.code;
			newObject.name = this.name;
			newObject.groupMap = (Map<String, PropertyGroup>) DeepCopyUtilities.copy(this.groupMap);
			return newObject;
		}
	}
	
	
	public static class ChartPaletteColor implements Serializable, Cloneable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String code;
		private String name;
		private PropertyGroup colors;
		
		public ChartPaletteColor (String code, String name, PropertyGroup colors){
			this.code = code;
			this.name = name;
			this.colors = colors;
		}
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public PropertyGroup getColorPropertyGroup() {
			return colors;
		}
		public void setColors(PropertyGroup colors) {
			this.colors = colors;
		}

		@Override
		protected Object clone() throws CloneNotSupportedException {
			ChartPaletteColor newObj = (ChartPaletteColor) super.clone();
			newObj.code = this.code;
			newObj.name = this.name;
			newObj.colors = (PropertyGroup) this.colors.clone();
			return newObj;
		}
		
	}

}
