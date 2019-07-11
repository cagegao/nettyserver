package com.zhenlianhui.netty.util;

/**
 * 字符串工具类
 */
public class StringUtil {

	/**
	 * 字典字符串
	 */
	private static final String DIC_STR = "abcdefghijklmnopqrstuvwxyz123456789";

	/**
	 * 取得一个限定最大值的随机数
	 *  
	 * @param count
	 *            最大值
	 * @return 随机数
	 */
	public static int getRandom(int count) {
		return (int) Math.round(Math.random() * count);
	}

	/**
	 * 取得指定长度随机字符串
	 *
	 * @param length
	 *            指定长度
	 * @return 随机字符串
	 */
	public static String getRandomString(int length) {
		StringBuffer sb = new StringBuffer();
		int len = DIC_STR.length();
		for (int i = 0; i < length; i++) {
			sb.append(DIC_STR.charAt(getRandom(len - 1)));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(StringUtil.getRandomString(8));
	}
}
