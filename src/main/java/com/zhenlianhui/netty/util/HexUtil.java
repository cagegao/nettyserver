package com.zhenlianhui.netty.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HexUtil {
	
	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	/**
	 * 方法一： byte[] to hex string
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex1(byte[] bytes) {
		// 一个byte为8位，可用两个十六进制位标识
		char[] buf = new char[bytes.length * 2];
		int a = 0;
		int index = 0;
		for (byte b : bytes) { // 使用除与取余进行转换
			if (b < 0) {
				a = 256 + b;
			} else {
				a = b;
			}
			buf[index++] = HEX_CHAR[a / 16];
			buf[index++] = HEX_CHAR[a % 16];
		}
		return new String(buf);
	}

	/**
	 * 方法二： byte[] to hex string
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex2(byte[] bytes) {
		char[] buf = new char[bytes.length * 2];
		int index = 0;
		for (byte b : bytes) { // 利用位运算进行转换，可以看作方法一的变种
			buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
			buf[index++] = HEX_CHAR[b & 0xf];
		}
		return new String(buf);
	}

	public static String bytesToHex3(byte data) {
		Integer value = (int) data;
		String Hexcode = Integer.valueOf(value.toString(), 16).toString().length() == 1
				? "0" + Integer.valueOf(value.toString(), 16).toString()
				: Integer.valueOf(value.toString(), 16).toString();
		return Hexcode;
	}

	/**
	 * 方法三： byte[] to hex string
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex3(byte[] bytes) {
		StringBuilder buf = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) { // 使用String的format方法进行转换
			buf.append(String.format("%02x", new Integer(b & 0xff)));
		}
		return buf.toString();
	}

	/**
	 * 将16进制字符串转换为byte[]
	 * 
	 * @param str
	 * @return
	 */
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

	/**
	 * 新的将hex 转byte
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 将十六进制形式的字符串转换为ascii形式的字符串
	 * 
	 * @param hex
	 *            十六进制形式的字符串，eg: "49204c6f7665204a617661"
	 * @return ascii形式的字符串
	 */
	public static String convertHexToString(String hex) {
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {
			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);
			temp.append(decimal);
		}
		return sb.toString();
	}

	public static String convertHexToBytesString(String hex) {
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {
			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			System.out.println(ByteUtil.bytesToAscii(output.getBytes()));
		}
		return sb.toString();
	}

	/**
	 * byte 转成String
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToNunberString(byte[] bytes) {
		String hex = bytesToHex3(bytes);
		hex = convertHexToString(hex);
		return hex;
	}

	public static String littleEndianToHex(byte[] bytes) {
		String hex = bytesToHex3(bytes);
		String value = String.valueOf(CRC16Util.hiloReverseToInt(Integer.valueOf(hex, 16)));
		return value;

	}

	/**
	 * 16进制字符串转换成2进制字符串
	 * 
	 * @param hexString
	 *            16进制字符串
	 * @return 2进制字符串
	 */
	public static String hexString2binaryString(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++) {
			tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}

	public static void main(String[] args) {
		// byte[] response = new byte[] { 0x01, 0x03, 0x00, 0x01, 0x00, 0x01 };
		// int crc16 = CRC16Util.calcCrc16(response);
		// System.out.println(CRC16Util.crc16ToHexString(crc16));
		//
		// int modbusCrc16 = CRC16Util.hiloReverseToInt(crc16);
		// String modbusCrc16HexString = CRC16Util.crc16ToHexString(modbusCrc16);
		// System.out.println(modbusCrc16HexString);
		//
		// byte[] buf = toBytes(modbusCrc16HexString);
		// System.out.println(buf.length);
		// for (byte b : buf) {
		// System.out.println(b);
		// }
		// System.out.println(bytesToHex1(buf));
	}

	public static byte[] getNowTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String time = df.format(new Date());
		System.out.println(time);// new Date()为获取当前系统时间
		return time.getBytes();
	}

	public static String getHexStringNowTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String time = df.format(new Date());
		System.out.println(time);// new Date()为获取当前系统时间

		return HexUtil.bytesToHex3(time.getBytes());
	}

	/**
	 * 异或运算
	 */
	public static String xor(String content) {
		content = change(content);
		String[] b = content.split(" ");
		int a = 0;
		for (int i = 0; i < b.length; i++) {
			a = a ^ Integer.parseInt(b[i], 16);
		}
		if (a < 10) {
			StringBuffer sb = new StringBuffer();
			sb.append("0");
			sb.append(a);
			return sb.toString();
		}
		String value = Integer.toHexString(a);
		if (value.length() == 1) {
			value = 0 + value;
		}
		return value;
	}

	/**
	 * 和运算
	 */
	public static String xorAdd(String data) {
		if (data == null || data.equals("")) {
			return "";
		}
		int total = 0;
		int len = data.length();
		int num = 0;
		while (num < len) {
			String s = data.substring(num, num + 2);
			System.out.println(s);
			total += Integer.parseInt(s, 16);
			num = num + 2;
		}
		/**
		 * 用256求余最大是255，即16进制的FF
		 */
		int mod = total % 256;
		String hex = Integer.toHexString(mod);
		len = hex.length();
		// 如果不够校验位的长度，补0,这里用的是两位校验
		if (len < 2) {
			hex = "0" + hex;
		}
		return hex;
	}

	public static String change(String content) {
		String str = "";
		for (int i = 0; i < content.length(); i++) {
			if (i % 2 == 0) {
				str += " " + content.substring(i, i + 1);
			} else {
				str += content.substring(i, i + 1);
			}
		}
		return str.trim();
	}

	public static byte[] strToByteArray(String str) {
		if (str == null) {
			return null;
		}
		byte[] byteArray = str.getBytes();
		return byteArray;
	}

	public static String dec2HexString(int decNumber) {
		String hex = Integer.toHexString(decNumber);
		if (hex.length() == 1) {
			hex = "0" + hex;
		}
		return hex;
	}
}
