package com.zhenlianhui.netty.client;

import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;

import org.apache.commons.lang3.ArrayUtils;

import com.sun.istack.internal.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

//@Slf4j
public class NettyClientHandler extends ChannelHandlerAdapter {
	
	private static Logger log = Logger.getLogger(NettyClientHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive");
//		String reqMsg = "MSH|^~\\&|||||||QRY^R02|1203|P|2.3.1\r" + 
//				"QRD|20190425171950|R|I|Q895211|||||RES\r" + 
//				"QRF|MON||||0&0^1^1^1^\r" + 
//				"QRF|MON||||0&0^3^1^1^\r" + 
//				"QRF|MON||||0&0^4^1^1^\r";
//		ctx.writeAndFlush(getSendByteBuf(reqMsg));
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelInactive");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuffer = (ByteBuf) msg;
		byte[] b = new byte[byteBuffer.readableBytes()];
		byteBuffer.readBytes(b);
		log.info("[" + ctx.channel().remoteAddress() + "]上传数据," + "长度: " + b.length);
		String message = new String(b);
		log.info("内容: \r" + message);
	}

	private ByteBuf getSendByteBuf(String message) throws UnsupportedEncodingException {
		byte[] buf = message.getBytes("UTF-8");
		byte[] result = ArrayUtils.addAll(buf, (byte)28, (byte)13);
		for(byte b : result) {
			System.out.print(b + " ");
		}
		System.out.println();
		ByteBuf reqMsg = Unpooled.buffer();
		reqMsg.writeBytes(result);
		return reqMsg;
	}

}
