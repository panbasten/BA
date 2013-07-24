package com.flywet.platform.lic;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 * 用于生成公钥私钥对
 * 
 * @author PeterPan
 * 
 */
public class KeyGenerater {

	private String priKey;

	private String pubKey;

	public static KeyGenerater instance(String randonKey) {
		KeyGenerater kg = new KeyGenerater();
		kg.generater(randonKey);
		return kg;
	}

	public void generater(String randonKey) {
		try {

			SecureRandom secrand = new SecureRandom();
			secrand.setSeed(randonKey.getBytes()); // 初始化随机产生器

			KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
			keygen.initialize(1024, secrand);
			KeyPair keys = keygen.genKeyPair();
			PublicKey pubkey = keys.getPublic();
			PrivateKey prikey = keys.getPrivate();

			pubKey = Base64.encodeBytes(pubkey.getEncoded());
			priKey = Base64.encodeBytes(prikey.getEncoded());

		} catch (java.lang.Exception e) {
			System.out.println("生成密钥对失败");
			e.printStackTrace();
		}

	}

	public String getPriKey() {
		return priKey;
	}

	public String getPubKey() {
		return pubKey;
	}

}
