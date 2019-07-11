package com.zhenlianhui.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@Qualifier("tcpServerHandler")
@ChannelHandler.Sharable
public class TcpServerHandler extends SimpleChannelInboundHandler<Object> {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("[" + ctx.channel().remoteAddress() + "] 连接上线!");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		Channel incoming = ctx.channel();
		log.error("[" + incoming.remoteAddress() + "] 异常");
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("[" + ctx.channel().remoteAddress() + "] 下线");
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuffer = (ByteBuf) msg;
		byte[] b = new byte[byteBuffer.readableBytes()];
		byteBuffer.readBytes(b);
		log.info("[" + ctx.channel().remoteAddress() + "]上传数据," + "长度: " + b.length);
		String message = new String(b);
		log.info("内容: \r" + message);
		// byteBuffer.release();
	}

}
