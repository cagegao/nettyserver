/**
 * Filename: BltTcpHandler.java
 * Description: 
 * Company: 重庆臻链汇物联网科技有限公司
 * Copyright: Copyright ©2018
 * Created by CageGao in 2018年07月09日
 * @version 1.0
 */
package com.zhenlianhui.netty.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.zhenlianhui.netty.util.HexUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class BltTcpHandler extends ChannelInboundHandlerAdapter {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel ch = ctx.channel();
		System.out.println("[" + ch.remoteAddress() + "]上线\r");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel ch = ctx.channel();
		System.out.println("[" + ch.remoteAddress() + "]下线\r");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Channel ch = ctx.channel();
		ByteBuf buf = (ByteBuf) msg;
		System.out.println(
				"[" + ch.remoteAddress() + "]上传数据-> " + sdf.format(new Date()) + "\n长度: " + buf.readableBytes());
		byte[] b = new byte[buf.readableBytes()];
		buf.readBytes(b);
		String data = new String(b);

		// String requestMsg = HexUtil.bytesToHex1(b);
		// System.out.println("内容: " + requestMsg + "\r");

		// String[] data = this.bytesToHexStringArray(b, b.length);
		buf.release();
		System.out.print("内容:" + data);
		// for (String d : data) {
		// System.out.print(d);
		// }

		System.out.println();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("[" + incoming.remoteAddress() + "] 异常\n");
		cause.printStackTrace();
		ctx.close();
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
