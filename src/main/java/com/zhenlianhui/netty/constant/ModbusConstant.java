package com.zhenlianhui.netty.constant;

public class ModbusConstant {

	public interface Address {
		/**
		 * 氧气传感器1地址
		 */
		int OXYGEN_PRESSURE_1 = 0x01;
		/**
		 * 氧气传感器2地址
		 */
		int OXYGEN_PRESSURE_2 = 0x02;
		/**
		 * 电量仪地址
		 */
		int VOLTAGE_CURRENT = 0x03;
	}

	/**
	 * 读取数据指令
	 */
	public interface ReadCommand {
		/**
		 * 读取氧气传感器1
		 */
		byte[] READ_OXYGEN_PRESSURE_1 = new byte[] { 0x01, 0x03, 0x00, 0x01, 0x00, 0x01 };
		/**
		 * 读取氧气传感器2
		 */
		byte[] READ_OXYGEN_PRESSURE_2 = new byte[] { 0x02, 0x03, 0x00, 0x01, 0x00, 0x01 };
		/**
		 * 读取电压、电流命令
		 */
		byte[] READ_VOLTAGE_CURRENT = new byte[] { 0x03, 0x03, 0x00, 0x00, 0x00, 0x02 };

		/**
		 * 读取环境空气质量传感器(湿度、温度、PM2.5、CO2浓度、TVOC浓度、PM10)
		 */
		byte[] READ_AIR_QUALITY_SENSOR = new byte[] { 0x04, 0x03, 0x00, 0x00, 0x00, 0x0A };

		// 读取地址、波特率、奇偶校验
		// byte[] command = new byte[] { 0x03, 0x03, 0x00, 0x50, 0x00, 0x03 };

		// 读取电压量程、电流量程
		// byte[] command = new byte[] {0x03, 0x03, 0x00, 0x53, 0x00, 0x02 };
	}

	/**
	 * 写入数据指令
	 */
	public interface WriteCommand {
		/**
		 * 将电量仪的地址由0x01修改为0x03
		 */
		byte[] WRITE_VOLTAGE_CURRENT_ADDRESS_TO_0X03 = { 0x01, 0x10, 0x00, 0x50, 0x00, 0x01, 0x02, 0x00, 0x03 };

		/**
		 * 将电量仪的波特率修改为9600,注意: 厂家文档中的参数说明是错误的,实际上波特率9600对应的寄存器数据为0x01)
		 */
		byte[] WRITE_VOLTAGE_ELECTRICITY_BAUDRATE_TO_9600 = { 0x03, 0x10, 0x00, 0x51, 0x00, 0x01, 0x02, 0x00, 0x01 };

		/**
		 * 将电量仪的波特率修改为9600成功后的应答
		 */
		byte[] WRITE_VOLTAGE_ELECTRICITY_BAUDRATE_TO_9600_ANSWER = { 0x03, 0x10, 0x00, 0x51, 0x00, 0x01 };

	}
}
