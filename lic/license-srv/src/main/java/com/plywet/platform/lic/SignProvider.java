package com.plywet.platform.lic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * 用公钥验证 Class: SignProvider
 * 
 * @author PeterPan
 * 
 */
public class SignProvider {

	private SignProvider() {

	}

	private static final String pubKeyText = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6J18/7iM4onsi4p1h8wyoiE1M51csWOrrJYPX\n"
			+ "0lyvrFxkTvOWI8U4OkSFVDLnOMNLmIKdQeDM5kp1mT9+PyjaHjgf98BNWQVx4xNbIhZjPSGwJXU4\n"
			+ "NC6bPmDFEYfkmz0UfZMRQtkvyfMNICZN7lG35RZm5gIxEKRnVeR0CO7a6wIDAQAB";

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
	public static final boolean verify(String a, String b, String c) {

		try {

			// 解密由base64编码的公钥 ,并构造X509EncodedKeySpec 对象
			X509EncodedKeySpec d = new X509EncodedKeySpec(decode(a));

			// RSA 对称加密算法
			KeyFactory e = KeyFactory.getInstance("RSA");

			// 取公钥匙对象
			PublicKey f = e.generatePublic(d);

			// 解密由base64编码的数字签名
			byte[] g = decode(c);

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
	 * @param licText
	 * @return
	 */
	public static final List<String[]> decodeLicense(String licText) {
		try {
			// Base64解码
			licText = zipDecompress(decode(licText));

			List<String[]> rtn = new ArrayList<String[]>();

			String[] lics = licText.split(",,");

			// 第一个字符片段是用户信息
			String[] customer = lics[0].split(",");
			for (int i = 0; i < customer.length; i++) {
				customer[i] = (String) decodeToObject(customer[i]);
			}
			rtn.add(customer);

			// 后续字段是模块license信息
			String userMessage = customer[0] + customer[1];
			for (int i = 1; i < lics.length; i++) {
				String[] lic = lics[i].split(",");
				Integer modelId = (Integer) decodeToObject(lic[1]);
				String modelCode = (String) decodeToObject(lic[2]);
				String expiredDateString = (String) decodeToObject(lic[3]);
				String concurrentString = (String) decodeToObject(lic[4]);
				if (verify(pubKeyText, userMessage + modelCode
						+ expiredDateString + concurrentString, lic[0])) {
					rtn.add(new String[] { String.valueOf(modelId), modelCode,
							expiredDateString, concurrentString });
				}
			}

			return rtn;
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return null;
	}

	/*********************************************
	 * 后面的类来自Base64.java中进行了复制
	 *********************************************/

	/** The equals sign (=) as a byte. */
	private final static byte EQUALS_SIGN = (byte) '=';

	/** Preferred encoding. */
	private final static String PREFERRED_ENCODING = "UTF-8";

	private final static byte[] DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9,
			-9, // Decimal 0 - 8
			-5, -5, // Whitespace: Tab and Linefeed
			-9, -9, // Decimal 11 - 12
			-5, // Whitespace: Carriage Return
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 14 -
			// 26
			-9, -9, -9, -9, -9, // Decimal 27 - 31
			-5, // Whitespace: Space
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 33 - 42
			62, // Plus sign at decimal 43
			-9, -9, -9, // Decimal 44 - 46
			63, // Slash at decimal 47
			52, 53, 54, 55, 56, 57, 58, 59, 60, 61, // Numbers zero through nine
			-9, -9, -9, // Decimal 58 - 60
			-1, // Equals sign at decimal 61
			-9, -9, -9, // Decimal 62 - 64
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, // Letters 'A' through
			// 'N'
			14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, // Letters 'O'
			// through 'Z'
			-9, -9, -9, -9, -9, -9, // Decimal 91 - 96
			26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, // Letters 'a'
			// through 'm'
			39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, // Letters 'n'
			// through 'z'
			-9, -9, -9, -9 // Decimal 123 - 126
	/*
	 * ,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9, // Decimal 127 - 139
	 * -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9, // Decimal 140 - 152
	 * -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9, // Decimal 153 - 165
	 * -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9, // Decimal 166 - 178
	 * -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9, // Decimal 179 - 191
	 * -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9, // Decimal 192 - 204
	 * -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9, // Decimal 205 - 217
	 * -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9, // Decimal 218 - 230
	 * -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9, // Decimal 231 - 243
	 * -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9 // Decimal 244 - 255
	 */
	};

	// I think I end up not using the BAD_ENCODING indicator.
	// private final static byte BAD_ENCODING = -9; // Indicates error in
	// encoding
	private final static byte WHITE_SPACE_ENC = -5; // Indicates white space in
	// encoding
	private final static byte EQUALS_SIGN_ENC = -1; // Indicates equals sign in

	private final static byte[] decode(String s) {
		byte[] bytes;
		try {
			bytes = s.getBytes(PREFERRED_ENCODING);
		} // end try
		catch (java.io.UnsupportedEncodingException uee) {
			bytes = s.getBytes();
		} // end catch
		// </change>

		// Decode
		bytes = decode(bytes, 0, bytes.length);

		// Check to see if it's gzip-compressed
		// GZIP Magic Two-Byte Number: 0x8b1f (35615)
		if (bytes != null && bytes.length >= 4) {

			int head = ((int) bytes[0] & 0xff) | ((bytes[1] << 8) & 0xff00);
			if (java.util.zip.GZIPInputStream.GZIP_MAGIC == head) {
				java.io.ByteArrayInputStream bais = null;
				java.util.zip.GZIPInputStream gzis = null;
				java.io.ByteArrayOutputStream baos = null;
				byte[] buffer = new byte[2048];
				int length = 0;

				try {
					baos = new java.io.ByteArrayOutputStream();
					bais = new java.io.ByteArrayInputStream(bytes);
					gzis = new java.util.zip.GZIPInputStream(bais);

					while ((length = gzis.read(buffer)) >= 0) {
						baos.write(buffer, 0, length);
					} // end while: reading input

					// No error? Get new bytes.
					bytes = baos.toByteArray();

				} // end try
				catch (java.io.IOException e) {
					// Just return originally-decoded bytes
				} // end catch
				finally {
					try {
						baos.close();
					} catch (Exception e) {
					}
					try {
						gzis.close();
					} catch (Exception e) {
					}
					try {
						bais.close();
					} catch (Exception e) {
					}
				} // end finally

			} // end if: gzipped
		} // end if: bytes.length >= 2

		return bytes;
	}

	private final static byte[] decode(byte[] source, int off, int len) {
		int len34 = len * 3 / 4;
		byte[] outBuff = new byte[len34]; // Upper limit on size of output
		int outBuffPosn = 0;

		byte[] b4 = new byte[4];
		int b4Posn = 0;
		int i = 0;
		byte sbiCrop = 0;
		byte sbiDecode = 0;
		for (i = off; i < off + len; i++) {
			sbiCrop = (byte) (source[i] & 0x7f); // Only the low seven bits
			sbiDecode = DECODABET[sbiCrop];

			if (sbiDecode >= WHITE_SPACE_ENC) // White space, Equals sign or
			// better
			{
				if (sbiDecode >= EQUALS_SIGN_ENC) {
					b4[b4Posn++] = sbiCrop;
					if (b4Posn > 3) {
						outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn);
						b4Posn = 0;

						// If that was the equals sign, break out of 'for' loop
						if (sbiCrop == EQUALS_SIGN)
							break;
					} // end if: quartet built

				} // end if: equals sign or better

			} // end if: white space, equals sign or better
			else {
				System.err.println("Bad Base64 input character at " + i + ": "
						+ source[i] + "(decimal)");
				return null;
			} // end else:
		} // each input character

		byte[] out = new byte[outBuffPosn];
		System.arraycopy(outBuff, 0, out, 0, outBuffPosn);
		return out;
	}

	private final static int decode4to3(byte[] source, int srcOffset,
			byte[] destination, int destOffset) {
		// Example: Dk==
		if (source[srcOffset + 2] == EQUALS_SIGN) {
			// Two ways to do the same thing. Don't know which way I like best.
			// int outBuff = ( ( DECODABET[ source[ srcOffset ] ] << 24 ) >>> 6
			// )
			// | ( ( DECODABET[ source[ srcOffset + 1] ] << 24 ) >>> 12 );
			int outBuff = ((DECODABET[source[srcOffset]] & 0xFF) << 18)
					| ((DECODABET[source[srcOffset + 1]] & 0xFF) << 12);

			destination[destOffset] = (byte) (outBuff >>> 16);
			return 1;
		}

		// Example: DkL=
		else if (source[srcOffset + 3] == EQUALS_SIGN) {
			// Two ways to do the same thing. Don't know which way I like best.
			// int outBuff = ( ( DECODABET[ source[ srcOffset ] ] << 24 ) >>> 6
			// )
			// | ( ( DECODABET[ source[ srcOffset + 1 ] ] << 24 ) >>> 12 )
			// | ( ( DECODABET[ source[ srcOffset + 2 ] ] << 24 ) >>> 18 );
			int outBuff = ((DECODABET[source[srcOffset]] & 0xFF) << 18)
					| ((DECODABET[source[srcOffset + 1]] & 0xFF) << 12)
					| ((DECODABET[source[srcOffset + 2]] & 0xFF) << 6);

			destination[destOffset] = (byte) (outBuff >>> 16);
			destination[destOffset + 1] = (byte) (outBuff >>> 8);
			return 2;
		}

		// Example: DkLE
		else {
			try {
				// Two ways to do the same thing. Don't know which way I like
				// best.
				// int outBuff = ( ( DECODABET[ source[ srcOffset ] ] << 24 )
				// >>> 6 )
				// | ( ( DECODABET[ source[ srcOffset + 1 ] ] << 24 ) >>> 12 )
				// | ( ( DECODABET[ source[ srcOffset + 2 ] ] << 24 ) >>> 18 )
				// | ( ( DECODABET[ source[ srcOffset + 3 ] ] << 24 ) >>> 24 );
				int outBuff = ((DECODABET[source[srcOffset]] & 0xFF) << 18)
						| ((DECODABET[source[srcOffset + 1]] & 0xFF) << 12)
						| ((DECODABET[source[srcOffset + 2]] & 0xFF) << 6)
						| ((DECODABET[source[srcOffset + 3]] & 0xFF));

				destination[destOffset] = (byte) (outBuff >> 16);
				destination[destOffset + 1] = (byte) (outBuff >> 8);
				destination[destOffset + 2] = (byte) (outBuff);

				return 3;
			} catch (Exception e) {
				System.out.println("" + source[srcOffset] + ": "
						+ (DECODABET[source[srcOffset]]));
				System.out.println("" + source[srcOffset + 1] + ": "
						+ (DECODABET[source[srcOffset + 1]]));
				System.out.println("" + source[srcOffset + 2] + ": "
						+ (DECODABET[source[srcOffset + 2]]));
				System.out.println("" + source[srcOffset + 3] + ": "
						+ (DECODABET[source[srcOffset + 3]]));
				return -1;
			} // e nd catch
		}
	}

	private final static Object decodeToObject(String encodedObject) {
		// Decode and gunzip if necessary
		byte[] objBytes = decode(encodedObject);

		java.io.ByteArrayInputStream bais = null;
		java.io.ObjectInputStream ois = null;
		Object obj = null;

		try {
			bais = new java.io.ByteArrayInputStream(objBytes);
			ois = new java.io.ObjectInputStream(bais);

			obj = ois.readObject();
		} // end try
		catch (java.io.IOException e) {
			e.printStackTrace();
			obj = null;
		} // end catch
		catch (java.lang.ClassNotFoundException e) {
			e.printStackTrace();
			obj = null;
		} // end catch
		finally {
			try {
				bais.close();
			} catch (Exception e) {
			}
			try {
				ois.close();
			} catch (Exception e) {
			}
		} // end finally

		return obj;
	}

	private final static String zipDecompress(byte[] compressed) {
		if (compressed == null)
			return null;

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	}
}
