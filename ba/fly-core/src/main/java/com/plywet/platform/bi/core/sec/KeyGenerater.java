package com.plywet.platform.bi.core.sec;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import mondrian.util.Base64;

/**
 * 用于生成公钥私钥对
 * 
 * @author PeterPan
 * 
 */
public class KeyGenerater {

	private String priKey;

	private String pubKey;

	public void generater() {
		try {

			SecureRandom secrand = new SecureRandom();
			secrand.setSeed("plywet2013".getBytes()); // 初始化随机产生器

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
