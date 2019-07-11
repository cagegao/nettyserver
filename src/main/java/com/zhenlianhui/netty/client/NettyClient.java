package com.zhenlianhui.netty.client;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
 * Netty实现的客户端程序
 * 
 * @author GaoJin
 * @date 2019年04月23日
 * @since JDK 1.8.0_162
 * @version 1.0
 */
public class NettyClient {

	private String host;

	private int port;

	public NettyClient(String host, int port) {
		this.host = host;
		this.port = port;
		start();
	}

	private void start() {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(eventLoopGroup)//
					.remoteAddress(new InetSocketAddress(host, port))//
					.channel(NioSocketChannel.class)//
					.option(ChannelOption.SO_KEEPALIVE, true)// 长连接
					.option(ChannelOption.TCP_NODELAY, true)//
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new NettyClientHandler());
						}
					});
			ChannelFuture f = b.connect().sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		new NettyClient("192.168.2.50", 9100);
	}

}
