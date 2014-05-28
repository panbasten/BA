package uap.bq.chart.generator.Axis;

import uap.vo.bq.chart.model.ChartModel;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;

public class YAxisHelp {

	ChartModel model;

	public YAxisHelp(ChartModel model) {
		this.model = model;
	}

	public static final String NUMDIVLINESPROPERTYGROUPCODE = "FC_DIVISIONAL_LINES_AND_GRIDS";

	public static final String NUMDIVLINESPROPERTYCODE = "numDivLines";

	public String getnumDivLines() {
		PropertyGroup group = this.model
				.getPropertyGroup(NUMDIVLINESPROPERTYGROUPCODE);
		String value = getPropertyValue(group, NUMDIVLINESPROPERTYCODE);
		if(value == null)
			return null;
		if (value.equals("0"))
			return null;
		return value;
	}

	public static final String YAXISPROPERTYGROUPCODE = "FC_TITLES_AND_AXIS_NAMES";

	public static final String YAXISMAXVALUEPROPERTYCODE = "yAxisMaxValue";

	public static final String YAXISMINVALUEPROPERTYCODE = "yAxisMinValue";
	
	public String getyAxisMinValue() {
		PropertyGroup group = this.model
				.getPropertyGroup(YAXISPROPERTYGROUPCODE);
		return getPropertyValue(group, YAXISMINVALUEPROPERTYCODE);
	}

	public String getyAxisMaxValue() {
		PropertyGroup group = this.model
				.getPropertyGroup(YAXISPROPERTYGROUPCODE);
		return getPropertyValue(group, YAXISMAXVALUEPROPERTYCODE);
	}

	private String getPropertyValue(PropertyGroup group, String code) {
		if (group == null)
			return null;
		Property property = group.getPropertyByRefCode(code);
		if (property == null)
			return null;
		if(property.getValue()==null)
			return null;
		if(property.getValue().equals(""))
			return null;
		return property.getValue();
	}

}
