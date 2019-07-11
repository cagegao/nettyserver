package com.zhenlianhui.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.zhenlianhui.netty.initializer.DeviceChannelInitializer;
import com.zhenlianhui.netty.service.ModbusDataService;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server implements ApplicationListener {

	private static Logger logger = LoggerFactory.getLogger(UdpServer.class);

	@Autowired
	@Qualifier(value = "deviceChannelInitializer")
	private DeviceChannelInitializer deviceChannelInitializer;
	
	@Autowired
    private ModbusDataService modbusDataService;

	public void run() {
		/*
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)//
					.channel(NioServerSocketChannel.class)//
					.childHandler(deviceChannelInitializer)//
					.option(ChannelOption.SO_BACKLOG, 128)//
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			logger.info("开始监听端口[20902]");
			ChannelFuture f1 = b.bind(20902).sync();
			f1.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		} */
		
		// 向数据库中添加智能电量仪的模拟数据
		while (true) {
			modbusDataService.createModbusData();
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ApplicationStartedEvent) {
			new Thread(() -> {
				try {
					this.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}
	}

}
