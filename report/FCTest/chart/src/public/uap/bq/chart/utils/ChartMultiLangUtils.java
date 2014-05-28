package uap.bq.chart.utils;

import nc.vo.ml.NCLangRes4VoTransl;

import org.apache.commons.lang.StringUtils;

public class ChartMultiLangUtils {
	
	/**
	 * <p>是否含有中文</p>
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		if(StringUtils.isEmpty(str)) {
			return false;
		}
		for(int i = 0;i < str.length();i++) {
			String c = str.charAt(i) + "";
			if(c.matches("[\\u4E00-\\u9FA5]+"))
				return true;
		}
		return false;
	}
	
	public static String getResValue(String resId) {
		if (resId == null) {
			return null;
		}
//		return NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0", resId);
		return NCLangRes4VoTransl.getNCLangRes().getStrByID(getModelcode(resId), resId);
	}
	
	private static String getModelcode(String resId) {
		if (resId == null) {
			return null;
		}
		if (resId.startsWith("1") && resId.contains("-")) {
			return resId.substring(1, resId.lastIndexOf("-"));
		} else {
			return "common";
		}
	}

}
