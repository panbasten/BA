package uap.bq.chart.utils;

public class ChartParamUtils {

	/**
	 * 
	* @Description: 获取引用参数的code
	 */
	public static String getRefParamCoder(String refParam) {
		if(refParam == null){
			return null;
		}
		if (refParam.startsWith("param(") && refParam.endsWith(")")) {
			return refParam.substring(6, refParam.length() - 1);
		}
		return refParam;
	}
	
	
	/**
	 * 判断是否引用了参数
	 * @param value
	 * @return
	 */
	public static boolean isRefParam(Object value){
		if (value instanceof String) {
			String valStr = (String) value;
			if (valStr.startsWith("param(") && valStr.endsWith(")")) {
				return true;
			}
		}
		return false;
	}

}
