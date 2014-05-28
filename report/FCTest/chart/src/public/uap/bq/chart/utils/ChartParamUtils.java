package uap.bq.chart.utils;

public class ChartParamUtils {

	/**
	 * 
	* @Description: ��ȡ���ò�����code
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
	 * �ж��Ƿ������˲���
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
