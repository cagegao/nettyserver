package com.zhenlianhui.netty.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.zhenlianhui.netty.decode.HL7MessageDecode;
import com.zhenlianhui.netty.handler.ModbusDeviceRequestHandler;
import com.zhenlianhui.netty.handler.TcpServerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Qualifier("deviceChannelInitializer")
public class DeviceChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Autowired
	private ModbusDeviceRequestHandler modbusDeviceRequestHandler;

	// @Autowired
	// private BltTcpHandler bltTcpHandler;

	@Autowired
	private StringEncoder stringEncoder;

	@Autowired
	@Qualifier("tcpServerHandler")
	private TcpServerHandler tcpServerHandler;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		log.info(ch.remoteAddress() + "建立连接");
		// 处理关于20901心电的消息
		if (ch.localAddress().getPort() == 8000 || ch.localAddress().getPort() == 20901
				|| ch.localAddress().getPort() == 5510) {
			ChannelPipeline pipeline = ch.pipeline();
			pipeline.addLast(stringEncoder);
			pipeline.addLast(new HL7MessageDecode());
			pipeline.addLast("tcpServerHandler", tcpServerHandler);
		} else {
			ChannelPipeline pipeline = ch.pipeline();
			// pipeline.addLast(tcpServerHandler);
			pipeline.addLast(modbusDeviceRequestHandler);
		}
	}

}
