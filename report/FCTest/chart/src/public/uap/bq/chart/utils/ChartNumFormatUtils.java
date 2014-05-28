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
	 * ����ƥ�䣬���ַ���Ϊ�����򷵻�true
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
	 * ��ʽ��ֵ
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
		// ǰ���ַ�
		String propNumberPrefix = isParentYAxisS ? ChartPropConst.PROPERTY_SNUMBERPREFIX
				: ChartPropConst.PROPERTY_NUMBERPREFIX;
		// ��׺�ַ�
		String propNumberSuffix = isParentYAxisS ? ChartPropConst.PROPERTY_SNUMBERSUFFIX
				: ChartPropConst.PROPERTY_NUMBERSUFFIX;		
		DecimalFormat dformat = (DecimalFormat) DecimalFormat.getInstance();
		
		String propNumScalvalue = isParentYAxisS ? ChartPropConst.PROPERTY_SNUMBERSCALEVALUEMY
				: ChartPropConst.PROPERTY_NUMBERSCALEVALUEMY;
		
		String numberScaleValue = getPropertyValue(propertyGroup,propNumScalvalue);	
		
		// ǰ׺�ַ�	
		String prifix = getPropertyValue(propertyGroup, propNumberPrefix);
		setPrefix(dformat, prifix, dValue);			
		// ��׺�ַ�			
		String suffix = getPropertyValue(propertyGroup, propNumberSuffix);				
		setSuffix(dformat, suffix, dValue);	
		//����ʽ��ֵ Ϊ�ջ����������
		if(!isDigital(numberScaleValue)){
			dValue = setNumScaleDef(propertyGroup,dformat, dValue, isParentYAxisS);				
		}else{				
			dValue = setNumScale(model, dValue, isParentYAxisS);					
		}				
		// ��ʽ������			
		setFormatNumber(dformat, propertyGroup, isParentYAxisS);	
		//��ʾС��	
		setDecimals(dformat, propertyGroup, isParentYAxisS);	
				
		return dformat.format(dValue);

	}	
	
	/**
	 * �������⣬��Ϊ��ʽ����λ��Ĭ�ϸ�ʽ����λ����ʽ��ֵ��generate����Ϊfalse
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
	 * ���ñ�������
	 */
	public static Double setNumScale(ChartModel model, Double dValue,boolean isParentYAxisS) {	
		
		PropertyGroup propertyGroup = model.getPropertyGroup(ChartPropConst.NUMBER_FORMATTING_CODE);
		// ��������
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
	 * ���ñ�������
	 */
	public static Double setNumScaleDef(PropertyGroup propertyGroup,DecimalFormat format,Double dValue,boolean isParentYAxisS) {
		// ��������
		String propertyCode = isParentYAxisS ? ChartPropConst.PROPERTY_SFORMATNUMBERSCALEMY
								: ChartPropConst.PROPERTY_FORMATNUMBERSCALEMY;
		
		String numScale = getPropertyValue(propertyGroup, propertyCode);
		String[] formatValue = {dValue.toString(),null};
	
		if (StringUtils.isEmpty(numScale) || numScale.equals("0")) {
			return dValue;
		}			
		String[] values = ChartPropConst.NUMBERSCALEVALUE_DEF.split(",");
		String[] units =  ChartPropConst.NUMBERSCALEUNIT_DEF.split(",");
		// ����ʽ��ֵ���ʽ����λ��Ŀ��ƥ�䣬�򷵻�
		if (values.length != units.length) {
			return dValue;		
		}	
		
		for (int i = 0; i < values.length; i++) {
			if (!ChartNumFormatUtils.isDigital(values[i])) {
				break;
			}
			// ��fushionCharts�����Ź�������Ӧ
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
	 * ��ʽ������
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
	 * ����ʽ�����֡���ʾС���������Ծ���ѡʱ������ʾС�� ǿ�Ƹ�ʽ������С��λ�����㣬��0
	 * 
	 * @param format
	 */
	public static void setDecimals(DecimalFormat format,
			PropertyGroup propertyGroup, boolean isParentYAxisS) {

		String propFormatNumber = isParentYAxisS ? ChartPropConst.PROPERTY_SFORMATNUMBER
				: ChartPropConst.PROPERTY_FORMATNUMBER;
		// ��ʾС��
		String propForcedecimals = isParentYAxisS ? ChartPropConst.PROPERTY_SFORCEDECIMALS
				: ChartPropConst.PROPERTY_FORCEDECIMALS;
		// С��λ��
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
		// ����С��λ��Ϊn
		format.setMinimumFractionDigits(decimals);
		format.setMaximumFractionDigits(decimals);

	}

	/**
	 * ����ǰ׺
	 * 
	 * @param format
	 *            ��ʽ�����ʵ��
	 * @param prifix
	 *            ǰ׺
	 * @param dValue
	 *            ��Ҫ��ʽ����ֵ
	 */
	public static void setPrefix(DecimalFormat format, String prifix,
			Double dValue) {
		// ����ǰ׺
		if (dValue > 0) {
			format.setPositivePrefix(prifix);
		} else if (dValue < 0) {
			format.setNegativePrefix(prifix);
		}
	}

	/**
	 * ���ú�׺
	 * 
	 * @param format
	 *            ��ʽ�����ʵ��
	 * @param suffix
	 *            ��׺
	 * @param dValue
	 *            ��Ҫ��ʽ����ֵ
	 */
	public static void setSuffix(DecimalFormat format, String suffix,
			Double dValue) {
		// ���ú�׺
		if (dValue > 0) {
			format.setPositiveSuffix(suffix);
		} else if (dValue < 0) {
			format.setNegativeSuffix(suffix);
		}
	}

	/**
	 * ��ʽ���ٷֱ�
	 * @param obj �����и���ָ��ĺ�ֵ
	 * @param value ��ǰָ���ֵ
	 * @param isParentYAxisS  �Ƿ��д�Y��
	 * @param model
	 * @return
	 */
	public static String getFormatPercent(Object obj, String value,	boolean isParentYAxisS, ChartModel model) {
		String percent = "";
		// ��ȡ������ָ��ĺ�
		DecimalFormat Dformat = (DecimalFormat) DecimalFormat.getInstance();
		if (obj instanceof Double) {
			Double sum = (Double) obj;
			if (isDigital(value) && isDigital(sum.toString())) {
				// ��ȡ��ǰָ��ֵ
				Double serieValue = new Double(value);
				// ����ָ��ٷֱ�
				Double seriePercet = (serieValue / sum) * 100;
				if (StringUtils.isNotEmpty(seriePercet.toString())) {
					// ���ú�׺
					setSuffix(Dformat, "%", seriePercet);
					PropertyGroup propertyGroup = model
							.getPropertyGroup(ChartPropConst.NUMBER_FORMATTING_CODE);
					if (propertyGroup == null) {
						return value;
					}
					// �����Ƿ���ʾС������С��λ��
					setDecimals(Dformat, propertyGroup, isParentYAxisS);
					percent = Dformat.format(seriePercet);
				}
			}
		}
		return percent;
	}	
	
	/**
	 * ��ò�����λ�ĸ�ʽ��ֵ
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
		//����ʽ��ֵ Ϊ�ջ����������
		if(!isDigital(numberScaleValue)){
			dValue  = setNumScaleDef(propertyGroup,dformat, dValue, isParentYAxisS);											
		}else{				
			dValue = setNumScale(model, dValue, isParentYAxisS);					
		}		
		//��ʾС��	
		setDecimals(dformat, propertyGroup, isParentYAxisS);
		dformat.setGroupingUsed(false);
		return dformat.format(dValue);	
	}

}