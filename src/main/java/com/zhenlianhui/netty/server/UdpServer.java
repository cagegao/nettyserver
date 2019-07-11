/**
 * Filename: UdpServer.java
 * Description: 
 * Company: 重庆臻链汇物联网科技有限公司
 * Copyright: Copyright ©2018
 * Created by CageGao in 2018年07月09日
 * @version 1.0
 */
package com.zhenlianhui.netty.server;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UdpServer {

	private static Logger logger = LoggerFactory.getLogger(UdpServer.class);

	public static final int RECEIVE_PORT = 4606;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.sss");

	/**
	 * udp服务端，接受客户端发送的广播
	 */
	public void initServer() {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)
					.handler(new UdpServerHandler());
			Channel channel = bootstrap.bind(RECEIVE_PORT).sync().channel();
			channel.closeFuture().await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}

	private class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
		@Override
		protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
			// 因为Netty对UDP进行了封装，所以接收到的是DatagramPacket对象。
			// String req = msg.content().toString(CharsetUtil.UTF_8);
			// System.out.println("接受到的数据为:" + req + "\r");
			Channel channel = ctx.channel();
			byte[] dst = new byte[packet.content().writerIndex()];
			packet.content().readBytes(dst);
			// String[] data = this.bytesToHexStringArray(dst, dst.length);
			System.out.println(sdf.format(new Date()) + " [" + packet.sender() + "] 发送数据:");
			for (byte b : dst) {
				System.out.print(b + " ");
			}
			System.out.println();
			for (byte d : dst) {
				System.out.print((char) d);
			}
			System.out.println();
			// Byte: 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18
			// 广播报文: FF D0 00 08 00 FE 00 0B 34 01 02 03 04 0B 14 15 32 33 50
			// Lenght(b7): 0B -> 11
			// 协议版本(b8): 34 -> 00110100 -> bit7~bit4版本号,bit3~bit0修订号 -> 3.4
			// 当前模块标名组(b9~b18):
			// 01H -> ECG; 02H -> SpO2; 03H -> NIBP; 04H -> RESP; 0BH -> RP; 14H -> P1;
			// 15H -> P2; 32H -> T1; 33H -> T2; 50H -> ?
			///////////////////////////////////////////////////////////////////////////

		}

		public String[] bytesToHexStringArray(byte[] src, int length) {
			if (src == null || src.length <= 0) {
				return null;
			}
			String[] result = new String[length];
			for (int i = 0; i < length; i++) {
				int v = src[i] & 0xFF;
				String hv = Integer.toHexString(v);
				if (hv.length() < 2) {
					hv = "0" + hv;
				}
				result[i] = hv.toUpperCase();
			}
			return result;
		}
	}

	public static void main(String[] args) {
		new UdpServer().initServer();
	}
}
