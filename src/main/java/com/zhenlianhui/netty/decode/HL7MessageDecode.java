package com.zhenlianhui.netty.decode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class HL7MessageDecode extends DelimiterBasedFrameDecoder {

	private static byte[] end = { 28, 13 };
	private static ByteBuf delimiter = Unpooled.copiedBuffer(end); // 毡包 分割符 \n
	private static int maxFrameLength = 1024 * 1024;

	public HL7MessageDecode() {
        super(maxFrameLength, false, delimiter);
    }

	/**
	 * 重新 自定义解码
	 */
	protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
		// 对数据 buffer 解码
		return super.decode(ctx, buffer);
	}
}
