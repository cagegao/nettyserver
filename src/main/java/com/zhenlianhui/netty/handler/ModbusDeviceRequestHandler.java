package com.zhenlianhui.netty.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.zhenlianhui.netty.constant.ModbusConstant;
import com.zhenlianhui.netty.util.CommandUtil;
import com.zhenlianhui.netty.util.HexUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

@Component
@Qualifier("modbusDeviceRequestHandler")
@Sharable
public class ModbusDeviceRequestHandler extends ChannelHandlerAdapter {

	protected final static Logger logger = LoggerFactory.getLogger(ModbusDeviceRequestHandler.class);

	private int count = 0;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	// @Autowired
	// @Qualifier(value = "connectionContainer")
	// private Map<String, String> connectionContainer;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel ch = ctx.channel();
		System.out.println("[" + ch.remoteAddress() + "]在线\r");
		Thread t = new Thread(() -> {
			if (ch.remoteAddress().toString().indexOf("192.168.2.77") > 0) {
				int flag = 0;
				while (true) {
					// if (flag % 2 == 0) {
					ModbusDeviceRequestHandler.sendCommand(ctx,
							new byte[] { 0x01, 0x03, 0x00, 0x01, 0x00, 0x01, (byte) 0xD5, (byte) 0xCA });
					// } else {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					ModbusDeviceRequestHandler.sendCommand(ctx,
							new byte[] { 0x02, 0x03, 0x00, 0x01, 0x00, 0x01, (byte) 0xD5, (byte) 0xF9 });
					// }
					flag++;
					System.out.println("send read command successfully!");
					try {
						Thread.sleep(120000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel ch = ctx.channel();
		System.out.println("[" + ch.remoteAddress() + "]下线\r");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// System.out.println("count=" + count);
		Channel ch = ctx.channel();
		ByteBuf buf = (ByteBuf) msg;
		System.out.println(
				"[" + ch.remoteAddress() + "]上传数据-> " + sdf.format(new Date()) + "\n长度:" + buf.readableBytes());
		byte[] b = new byte[buf.readableBytes()];
		buf.readBytes(b);
		buf.release();

		/*
		 * for(byte b1 : b) { System.out.print((char)b1 + " "); } System.out.println();
		 */

		String requestMsg = HexUtil.bytesToHex2(b);
		if (requestMsg.startsWith("0000")) {
			System.out.println("心跳包:" + requestMsg + "\r");
		} else {
			System.out.println("数据:" + requestMsg + "\r");
		}

		/*
		 * String deviceNo = requestMsg.substring(0, 16); String data =
		 * requestMsg.substring(16);
		 * 
		 * if (requestMsg.startsWith("0000013800000099")) { String dataInfo = "设备【" +
		 * deviceNo + "】上传["; String dataType = data.substring(0, 2); switch (dataType)
		 * { case "01": dataInfo = dataInfo + "氧气1]数据:" + data; break; case "02":
		 * dataInfo = dataInfo + "氧气2]数据:" + data; break; case "03": dataInfo = dataInfo
		 * + "电量仪]数据:" + data; break; case "04": dataInfo = dataInfo + "环境质量]数据:" +
		 * data; break; } System.out.println(dataInfo + "\r"); } else {
		 * System.out.println(); // System.out.println("语音播放成功\r"); // sendCommand(ctx,
		 * new byte[] { 0x05, 0x06, 0x00, 0x08, 0x00, 0x01, (byte) // 0xC8, 0x4C }); }
		 */

		// if(count == 0) {
		// System.out.println("下发继电器1路开指令: 55 01 12 00 00 00 01 69\r");
		// this.sendCommand(ctx, new byte[] { 0x55, 0x01, 0x12, 0x00, 0x00, 0x00, 0x01,
		// 0x69 });
		// count++;
		// }

		// String command = HexUtil
		// .bytesToHex1(CommandUtil.createReadCommand(ModbusConstant.ReadCommand.READ_AIR_QUALITY_SENSOR));
		// this.sendCommand(ctx, command.getBytes());
		// System.out.println("下发命令: " + command);

		// String answer = HexUtil.bytesToHex1(CommandUtil
		// .createReadCommand(ModbusConstant.WriteCommand.WRITE_VOLTAGE_ELECTRICITY_BAUDRATE_TO_9600_ANSWER));
		// if (requestMsg.equalsIgnoreCase(answer)) {
		// System.out.println("电量仪波特率修改成功");
		// return;
		// }

		// if (requestMsg.startsWith("0000") && requestMsg.length() == 16) {
		// System.out.print("此次连接为心跳");
		// if (count == 0) {
		// System.out.println(",且为第一次连接");
		// System.out.println("<<<=======================================");
		// byte[] command1 =
		// CommandUtil.createReadCommand(ModbusConstant.ReadCommand.READ_VOLTAGE_CURRENT);
		// System.out.println("开始向终端设备[" + ctx.channel().remoteAddress() + "]下发指令:");
		// System.out.println(HexUtil.bytesToHex1(command1));
		//
		// Thread.sleep(1000);
		// this.sendCommand(ctx, command1);
		// System.out.println("指令发送完毕");
		// System.out.println("--------------------------------------->>>\n");
		// count++;
		// } else {
		// System.out.println();
		// }
		// }

		// if (!requestMsg.startsWith("0000") && requestMsg.startsWith("030304")) {
		// // byte[] command =
		// //
		// CommandUtil.createReadCommand(ModbusConstant.WriteCommand.WRITE_VOLTAGE_ELECTRICITY_BAUDRATE_TO_9600);
		// byte[] command =
		// CommandUtil.createReadCommand(ModbusConstant.ReadCommand.READ_VOLTAGE_CURRENT);
		// System.out.println("开始向终端设备[" + ctx.channel().remoteAddress() + "]下发指令:");
		// System.out.println(HexUtil.bytesToHex1(command));
		// Thread.sleep(1000);
		// this.sendCommand(ctx, command);
		// System.out.println("指令发送完毕");
		// System.out.println("--------------------------------------->>>\n");
		// count++;
		// }
	}

	public void copyCommandToByteBuf(ByteBuf buf, byte[] commad) {
		buf.writeBytes(commad);
	}

	public static void sendCommand(ChannelHandlerContext ctx, byte[] command) {
		ByteBuf b = ctx.alloc().directBuffer(command.length).writeBytes(command);
		ctx.pipeline().write(b);
		ctx.pipeline().flush();
		b = null;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("[" + incoming.remoteAddress() + "] 异常\n");
		cause.printStackTrace();
		ctx.close();
	}

}
