package com.plywet.platform.bi.core.sec;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

import mondrian.util.Base64;

/**
 * 使用私钥进行签名
 * 
 * @author PeterPan
 * 
 */
public class Signaturer {
	public static String sign(String priKeyText, String plainText) {

		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64
					.decode(priKeyText));

			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey prikey = keyf.generatePrivate(priPKCS8);

			// 用私钥对信息生成数字签名
			Signature signet = java.security.Signature
					.getInstance("MD5withRSA");
			signet.initSign(prikey);
			signet.update(plainText.getBytes());
			String signed = Base64.encodeBytes(signet.sign());
			return signed;

		} catch (java.lang.Exception e) {
			System.out.println("签名失败");
			e.printStackTrace();
		}

		return null;

	}

}
