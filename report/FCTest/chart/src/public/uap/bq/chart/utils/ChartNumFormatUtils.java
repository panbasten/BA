package uap.bq.chart.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import uap.bq.chart.ChartPropConst;
import uap.vo.bq.chart.model.ChartModel;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;

public class ChartNumFormatUtils {
	/**
	 * 正则匹配，若字符串为数字则返回true
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isDigital(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	/**
	 * 格式化值
	 * 
	 * @param value
	 * @return
	 */
	public static String getFormatValue(String value, boolean isParentYAxisS,ChartModel model,boolean isScale) {
		if (StringUtils.isEmpty(value) || !isDigital(value)) {
			return value;
		}			
		PropertyGroup propertyGroup = model.getPropertyGroup(ChartPropConst.NUMBER_FORMATTING_CODE);
		if (propertyGroup == null) {
			return value;
		}
		Double dValue = new Double(value);		
		// 前导字符
		String propNumberPrefix = isParentYAxisS ? ChartPropConst.PROPERTY_SNUMBERPREFIX
				: ChartPropConst.PROPERTY_NUMBERPREFIX;
		// 后缀字符
		String propNumberSuffix = isParentYAxisS ? ChartPropConst.PROPERTY_SNUMBERSUFFIX
				: ChartPropConst.PROPERTY_NUMBERSUFFIX;		
		DecimalFormat dformat = (DecimalFormat) DecimalFormat.getInstance();
		
		String propNumScalvalue = isParentYAxisS ? ChartPropConst.PROPERTY_SNUMBERSCALEVALUEMY
				: ChartPropConst.PROPERTY_NUMBERSCALEVALUEMY;
		
		String numberScaleValue = getPropertyValue(propertyGroup,propNumScalvalue);	
		
		// 前缀字符	
		String prifix = getPropertyValue(propertyGroup, propNumberPrefix);
		setPrefix(dformat, prifix, dValue);			
		// 后缀字符			
		String suffix = getPropertyValue(propertyGroup, propNumberSuffix);				
		setSuffix(dformat, suffix, dValue);	
		//若格式化值 为空或包含非数字
		if(!isDigital(numberScaleValue)){
			dValue = setNumScaleDef(propertyGroup,dformat, dValue, isParentYAxisS);				
		}else{				
			dValue = setNumScale(model, dValue, isParentYAxisS);					
		}				
		// 格式化数字			
		setFormatNumber(dformat, propertyGroup, isParentYAxisS);	
		//显示小数	
		setDecimals(dformat, propertyGroup, isParentYAxisS);	
				
		return dformat.format(dValue);

	}	
	
	/**
	 * 兼容问题，若为格式化单位，默认格式化单位，格式化值则generate属性为false
	 * @param property
	 */
	public static void setGenerate(PropertyGroup propertyGroup){
		for(Property property:propertyGroup.getProperties()){
//		if(ChartPropConst.PROPERTY_DEFAULTNUMBERSCALE.equals(property.getRefCode())
//				|| ChartPropConst.PROPERTY_NUMBERSCALEUNIT.equals(property.getRefCode())
//				|| ChartPropConst.PROPERTY_NUMBERSCALEVALUE.equals(property.getRefCode())
//				|| ChartPropConst.PROPERTY_SNUMBERSCALEUNIT.equals(property.getRefCode())
//				|| ChartPropConst.PROPERTY_SDEFAULTNUMBERSCALE .equals(property.getRefCode())
//				|| ChartPropConst.PROPERTY_SNUMBERSCALEVALUE.equals(property.getRefCode())
//				 ){
//			property.setGenerate(false);
//			//property.setValue("");
//		}
		
		if(ChartPropConst.PROPERTY_FORMATNUMBERSCALE.equals(property.getRefCode())
				|| ChartPropConst.PROPERTY_SFORMATNUMBERSCALE.equals(property.getRefCode())){
			property.setValue("0");
		}
		}
	}

	/**
	 * 设置比例缩放
	 */
	public static Double setNumScale(ChartModel model, Double dValue,boolean isParentYAxisS) {	
		
		PropertyGroup propertyGroup = model.getPropertyGroup(ChartPropConst.NUMBER_FORMATTING_CODE);
		// 比例缩放
		String propertyCode = isParentYAxisS ? ChartPropConst.PROPERTY_SFORMATNUMBERSCALEMY
						: ChartPropConst.PROPERTY_FORMATNUMBERSCALEMY;
		String numScale = getPropertyValue(propertyGroup, propertyCode);
		if (StringUtils.isEmpty(numScale) || numScale.equals("0")) {
			return dValue;
		}		
		String propNumScalvalue = isParentYAxisS ? ChartPropConst.PROPERTY_SNUMBERSCALEVALUEMY
				: ChartPropConst.PROPERTY_NUMBERSCALEVALUEMY;
		
		String numberScaleValue = getPropertyValue(propertyGroup,propNumScalvalue);			
		if (!isDigital(numberScaleValue)) {
			return dValue;
		}
	
		Double value = new Double(numberScaleValue);
		Double result = dValue / value;			
		return result;

	}
	
	/**
	 * 设置比例缩放
	 */
	public static Double setNumScaleDef(PropertyGroup propertyGroup,DecimalFormat format,Double dValue,boolean isParentYAxisS) {
		// 比例缩放
		String propertyCode = isParentYAxisS ? ChartPropConst.PROPERTY_SFORMATNUMBERSCALEMY
								: ChartPropConst.PROPERTY_FORMATNUMBERSCALEMY;
		
		String numScale = getPropertyValue(propertyGroup, propertyCode);
		String[] formatValue = {dValue.toString(),null};
	
		if (StringUtils.isEmpty(numScale) || numScale.equals("0")) {
			return dValue;
		}			
		String[] values = ChartPropConst.NUMBERSCALEVALUE_DEF.split(",");
		String[] units =  ChartPropConst.NUMBERSCALEUNIT_DEF.split(",");
		// 若格式化值与格式化单位数目不匹配，则返回
		if (values.length != units.length) {
			return dValue;		
		}	
		
		for (int i = 0; i < values.length; i++) {
			if (!ChartNumFormatUtils.isDigital(values[i])) {
				break;
			}
			// 与fushionCharts的缩放规则相适应
			Double value = new Double(values[i]);
			Double result = dValue / value;
			if (result > 1) {
				dValue = result;				
				formatValue[0] = dValue.toString();
				formatValue[1] = units[i];				
			} else {			
				return dValue;
			}
		}	
		
		return dValue;
		
	}

	public static String getPropertyValue(PropertyGroup propertyGroup, String code) {
		Property property = propertyGroup.getPropertyByRefCode(code);
		if (property == null) {
			return null;
		}
		return property.getValue();
	}

	/**
	 * 格式化数字
	 * 
	 * @param format
	 * @param propertyGroup
	 * @param code
	 */
	public static void setFormatNumber(DecimalFormat format,PropertyGroup propertyGroup, boolean isParentYAxisS) {
		String propFormatNumber = isParentYAxisS ? ChartPropConst.PROPERTY_SFORMATNUMBER
				: ChartPropConst.PROPERTY_FORMATNUMBER;
		String formatNumber = getPropertyValue(propertyGroup, propFormatNumber);
		if (StringUtils.isEmpty(formatNumber)) {
			return;
		}
		boolean formats = "0".equals(formatNumber) ? Boolean.FALSE: Boolean.TRUE;		
		format.setGroupingUsed(formats);

	}

	/**
	 * 当格式化数字、显示小数两个属性均勾选时，才显示小数 强制格式化，若小数位数不足，则补0
	 * 
	 * @param format
	 */
	public static void setDecimals(DecimalFormat format,
			PropertyGroup propertyGroup, boolean isParentYAxisS) {

		String propFormatNumber = isParentYAxisS ? ChartPropConst.PROPERTY_SFORMATNUMBER
				: ChartPropConst.PROPERTY_FORMATNUMBER;
		// 显示小数
		String propForcedecimals = isParentYAxisS ? ChartPropConst.PROPERTY_SFORCEDECIMALS
				: ChartPropConst.PROPERTY_FORCEDECIMALS;
		// 小数位数
		String propDecimals = isParentYAxisS ? ChartPropConst.PROPERTY_SDECIMALS
				: ChartPropConst.PROPERTY_DECIMALS;

		String forceDecimal = getPropertyValue(propertyGroup, propForcedecimals);
		String value = getPropertyValue(propertyGroup, propDecimals);
		String formatNumber = getPropertyValue(propertyGroup, propFormatNumber);
		if (StringUtils.isEmpty(forceDecimal) || StringUtils.isEmpty(value)
				|| StringUtils.isEmpty(formatNumber)) {
			return;
		}
		int decimals = "0".equals(formatNumber) || "0".equals(forceDecimal) ? 0
				: Integer.valueOf(value);
		// 设置小数位数为n
		format.setMinimumFractionDigits(decimals);
		format.setMaximumFractionDigits(decimals);

	}

	/**
	 * 设置前缀
	 * 
	 * @param format
	 *            格式化类的实例
	 * @param prifix
	 *            前缀
	 * @param dValue
	 *            需要格式化的值
	 */
	public static void setPrefix(DecimalFormat format, String prifix,
			Double dValue) {
		// 设置前缀
		if (dValue > 0) {
			format.setPositivePrefix(prifix);
		} else if (dValue < 0) {
			format.setNegativePrefix(prifix);
		}
	}

	/**
	 * 设置后缀
	 * 
	 * @param format
	 *            格式化类的实例
	 * @param suffix
	 *            后缀
	 * @param dValue
	 *            需要格式化的值
	 */
	public static void setSuffix(DecimalFormat format, String suffix,
			Double dValue) {
		// 设置后缀
		if (dValue > 0) {
			format.setPositiveSuffix(suffix);
		} else if (dValue < 0) {
			format.setNegativeSuffix(suffix);
		}
	}

	/**
	 * 格式化百分比
	 * @param obj 分类中各个指标的和值
	 * @param value 当前指标的值
	 * @param isParentYAxisS  是否含有次Y轴
	 * @param model
	 * @return
	 */
	public static String getFormatPercent(Object obj, String value,	boolean isParentYAxisS, ChartModel model) {
		String percent = "";
		// 获取分类中指标的和
		DecimalFormat Dformat = (DecimalFormat) DecimalFormat.getInstance();
		if (obj instanceof Double) {
			Double sum = (Double) obj;
			if (isDigital(value) && isDigital(sum.toString())) {
				// 获取当前指标值
				Double serieValue = new Double(value);
				// 计算指标百分比
				Double seriePercet = (serieValue / sum) * 100;
				if (StringUtils.isNotEmpty(seriePercet.toString())) {
					// 设置后缀
					setSuffix(Dformat, "%", seriePercet);
					PropertyGroup propertyGroup = model
							.getPropertyGroup(ChartPropConst.NUMBER_FORMATTING_CODE);
					if (propertyGroup == null) {
						return value;
					}
					// 设置是否显示小数，及小数位数
					setDecimals(Dformat, propertyGroup, isParentYAxisS);
					percent = Dformat.format(seriePercet);
				}
			}
		}
		return percent;
	}	
	
	/**
	 * 获得不带单位的格式化值
	 * @return
	 */
	public static String getFormatValueNo(ChartModel model, String value,boolean isParentYAxisS) {
		if (StringUtils.isEmpty(value) || !isDigital(value)) {
			return value;
		}		
		PropertyGroup propertyGroup = model.getPropertyGroup(ChartPropConst.NUMBER_FORMATTING_CODE);
		if (propertyGroup == null) {
			return value;
		}	
		Double dValue = new Double(value);		
		DecimalFormat dformat = (DecimalFormat) DecimalFormat.getInstance();	
		String propNumScalvalue = isParentYAxisS ? ChartPropConst.PROPERTY_SNUMBERSCALEVALUEMY
				: ChartPropConst.PROPERTY_NUMBERSCALEVALUEMY;
		
		String numberScaleValue = getPropertyValue(propertyGroup,propNumScalvalue);	
		//若格式化值 为空或包含非数字
		if(!isDigital(numberScaleValue)){
			dValue  = setNumScaleDef(propertyGroup,dformat, dValue, isParentYAxisS);											
		}else{				
			dValue = setNumScale(model, dValue, isParentYAxisS);					
		}		
		//显示小数	
		setDecimals(dformat, propertyGroup, isParentYAxisS);
		dformat.setGroupingUsed(false);
		return dformat.format(dValue);	
	}

}