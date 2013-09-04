package com.flywet.platform.pwd;

import java.math.BigInteger;

public class Xor {

	private static final int RADIX = 16;

	private static final String SEED = "0933910847463829827159347601486730416058";

	public static final String PASSWORD_ENCRYPTED_PREFIX = "Encrypted ";

	public static final String PASSWORD_TYPE_REP = "rep";

	public static final String PASSWORD_TYPE_ENCRYPT = "encrypt";

	public static final String PASSWORD_TYPE_DECRYPT = "decrypt";

	public static final String encryptPassword(String password) {
		if (password == null)
			return "";
		if (password.length() == 0)
			return "";

		BigInteger bi_passwd = new BigInteger(password.getBytes());

		BigInteger bi_r0 = new BigInteger(SEED);
		BigInteger bi_r1 = bi_r0.xor(bi_passwd);

		return bi_r1.toString(RADIX);
	}

	public static final String encryptRep(String password) {
		String obfuscated = encryptPassword(password);
		return PASSWORD_ENCRYPTED_PREFIX + obfuscated;
	}

	public static final String decryptPassword(String encrypted) {
		if (encrypted == null)
			return "";
		if (encrypted.length() == 0)
			return "";

		BigInteger bi_confuse = new BigInteger(SEED);

		try {
			BigInteger bi_r1 = new BigInteger(encrypted, RADIX);
			BigInteger bi_r0 = bi_r1.xor(bi_confuse);

			return new String(bi_r0.toByteArray());
		} catch (Exception e) {
			return "";
		}
	}

	private static void printOptions() {
		System.err.println("Xor加密使用方法:\n");
		System.err
				.println("  java -jar xor.jar <-rep | -encrypt | -decrypt> <password>");
		System.err.println();
	}

	public static void main(String[] args) throws Exception {

		if (args.length != 2) {
			printOptions();
			System.exit(9);
		}

		String option = args[0];
		String password = args[1];
		// 加密资源库密码
		if (option.trim().substring(1).equalsIgnoreCase(PASSWORD_TYPE_REP)) {
			System.out.println(encryptRep(password));
			System.exit(0);
		} else if (option.trim().substring(1).equalsIgnoreCase(
				PASSWORD_TYPE_ENCRYPT)) {
			System.out.println(encryptPassword(password));
			System.exit(0);
		} else if (option.trim().substring(1).equalsIgnoreCase(
				PASSWORD_TYPE_DECRYPT)) {
			System.out.println(decryptPassword(password));
			System.exit(0);
		} else {
			System.err.println((new StringBuilder("Unknown option '")).append(
					option).append("'\n").toString());
			printOptions();
			System.exit(1);
		}

	}
}
