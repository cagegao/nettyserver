package com.zhenlianhui.netty.util;

import java.io.UnsupportedEncodingException;

public class ByteUtil {
	public static String bytesToAscii(byte[] bytes, int offset, int dateLen) {
		if ((bytes == null) || (bytes.length == 0) || (offset < 0) || (dateLen <= 0)) {
			return null;
		}
		if ((offset >= bytes.length) || (bytes.length - offset < dateLen)) {
			return null;
		}

		String asciiStr = null;
		byte[] data = new byte[dateLen];
		System.arraycopy(bytes, offset, data, 0, dateLen);
		try {
			asciiStr = new String(data, "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
		}
		return asciiStr;
	}

	public static String bytesToAscii(byte[] bytes, int dateLen) {
		return bytesToAscii(bytes, 0, dateLen);
	}

	public static String bytesToAscii(byte[] bytes) {
		return bytesToAscii(bytes, 0, bytes.length);
	}

	public static byte[] toBytes(String str) {
		if (str == null || str.trim().equals("")) {
			return new byte[0];
		}

		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < str.length() / 2; i++) {
			String subStr = str.substring(i * 2, i * 2 + 2);
			bytes[i] = (byte) Integer.parseInt(subStr, 16);
		}

		return bytes;
	}

	public static int ByteArrayToInt(byte[] bArr) {
		if (bArr.length != 4) {
			return -1;
		}
		return (int) ((((bArr[3] & 0xff) << 24) | ((bArr[2] & 0xff) << 16) | ((bArr[1] & 0xff) << 8)
				| ((bArr[0] & 0xff) << 0)));
	}

	public static int byteLowArrayToInt(byte[] b) {
		return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
	}

	/**
	 * 将Byte 转 String 数组
	 **/
	public static String convertByteToStringList(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			stringBuffer.append(Integer.valueOf(bytes[i]) + ",");
		}
		return stringBuffer.toString();
	}

	public static String stringToAscii(String value) {
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (i != chars.length - 1) {
				sbu.append((int) chars[i]).append(",");
			} else {
				sbu.append((int) chars[i]);
			}
		}
		return sbu.toString();
	}

	// 转成十六进制的
	public static String stringToAsciiHex(String value) {
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (i != chars.length - 1) {
				sbu.append(Integer.toHexString((int) chars[i]));
			} else {
				sbu.append(Integer.toHexString((int) chars[i]));
			}
		}
		return sbu.toString();
	}

}
