package com.zhenlianhui.netty.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 *
 */
public class MD5Util {
	private static final String HEX_DIGITS[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };
	private static final String PASSWORD_SALT = "23543dfggeelysdafaqj23ou89ZXcj@#$@#$#@KJdjklj";

	/**
	 * 对字符串MD5加密
	 *
	 * @param origin
	 *            要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String MD5EncodeUtf8(String origin) {
		origin = origin + PASSWORD_SALT;
		return MD5Encode(origin, "utf-8");
	}

	/**
	 * 遍历8个byte，转化为16位进制的字符，即0-F
	 *
	 * @param b
	 * @return
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	/**
	 * 单个byte，256的byte通过16拆分为d1和d2
	 *
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return HEX_DIGITS[d1] + HEX_DIGITS[d2];
	}

	/**
	 * 返回MD5字符串
	 *
	 * @param origin
	 * @param charsetName
	 * @return
	 */
	private static String MD5Encode(String origin, String charsetName) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetName == null || "".equals(charsetName)) {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			} else {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
			}
		} catch (NoSuchAlgorithmException e) {

		} catch (UnsupportedEncodingException e) {

		}
		return resultString.toUpperCase();
	}

	 public static void main(String[] args) {
		 // admin123 = 25EB72EF933B102230305A559092A1EB
		 System.out.println(MD5EncodeUtf8("admin123"));
		 System.out.println(MD5EncodeUtf8("admin123"));
	 }
}
