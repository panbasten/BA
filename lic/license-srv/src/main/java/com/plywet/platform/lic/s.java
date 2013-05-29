package com.plywet.platform.lic;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

/**
 * 用公钥验证 Class: SignProvider
 * 
 * @author PeterPan
 * 
 */
public class s {

	private s() {

	}

	private static String pubKeyText = "";

	/**
	 * 验证License<br>
	 * method: verify
	 * 
	 * @param a
	 *            pubKeyText
	 * @param b
	 *            plainText
	 * @param c
	 *            signText
	 * @return
	 */
	public static boolean v(String a, String b, String c) {

		try {

			// 解密由base64编码的公钥 ,并构造X509EncodedKeySpec 对象
			X509EncodedKeySpec d = new X509EncodedKeySpec(Base64.decode(a));

			// RSA 对称加密算法
			KeyFactory e = KeyFactory.getInstance("RSA");

			// 取公钥匙对象
			PublicKey f = e.generatePublic(d);

			// 解密由base64编码的数字签名
			byte[] g = Base64.decode(c);

			Signature h = Signature.getInstance("MD5withRSA");
			h.initVerify(f);
			h.update(b.getBytes());

			// 验证签名是否正常
			if (h.verify(g))
				return true;
			else
				return false;

		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 解析License文件，获得明文信息<br>
	 * method: decodeLicense
	 * 
	 * @param a
	 *            licText
	 * @return
	 */
	public static List<String[]> d(String a) {
		try {
			List<String[]> rtn = new ArrayList<String[]>();

			String[] lics = a.split(",,");

			// 第一个字符片段是用户信息
			String[] customer = lics[0].split(",");
			for (int i = 0; i < customer.length; i++) {
				customer[i] = (String) Base64.decodeToObject(customer[i]);
			}
			rtn.add(customer);

			// 后续字段是模块license信息
			String userMessage = customer[0] + customer[1];
			for (int i = 1; i < lics.length; i++) {
				String[] lic = lics[i].split(",");
				Integer modelId = (Integer) Base64.decodeToObject(lic[1]);
				String expiredDateString = (String) Base64
						.decodeToObject(lic[2]), concurrentString = (String) Base64
						.decodeToObject(lic[3]);
				if (v(pubKeyText, userMessage + modelId, lic[0])) {
					rtn.add(new String[] { String.valueOf(modelId),
							expiredDateString, concurrentString });
				}
			}

			return rtn;
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return null;
	}

}
