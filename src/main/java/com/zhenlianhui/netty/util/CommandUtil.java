package com.zhenlianhui.netty.util;

import org.apache.commons.lang3.ArrayUtils;

public class CommandUtil {

	public static byte[] createReadCommand(byte[] command) {
		int crc16 = CRC16Util.calcCrc16(command);
		// System.out.println("crc16校验结果：" + CRC16Util.crc16ToHexString(crc16));

		int modbusCrc16 = CRC16Util.hiloReverseToInt(crc16);
		String modbusCrc16HexString = CRC16Util.crc16ToHexString(modbusCrc16);
		// System.out.println("crc16高低位交换：" + modbusCrc16HexString);

		byte[] commandCrc = HexUtil.toBytes(modbusCrc16HexString);

		byte[] result = ArrayUtils.addAll(command, commandCrc);
		return result;
	}
}
