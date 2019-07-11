package com.zhenlianhui.netty.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhenlianhui.netty.handler.BltTcpHandler;
import com.zhenlianhui.netty.handler.ModbusDeviceRequestHandler;
import com.zhenlianhui.netty.initializer.DeviceChannelInitializer;

// @Configuration
public class ServerConfiguration implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Bean
	public DeviceChannelInitializer channelInitializer() {
		return new DeviceChannelInitializer();
	}

	@Bean
	public ModbusDeviceRequestHandler requestHandler() {
		return new ModbusDeviceRequestHandler();
	}

	@Bean
	public BltTcpHandler bltTcpHandler() {
		return new BltTcpHandler();
	}
	
	@Bean(value = "connectionContainer")
	public Map<String, String> connectionContainer() {
		return new ConcurrentHashMap<String, String>();
	}

	public <T> T getBean(Class<T> clazz) {
		return this.applicationContext.getBean(clazz);
	}
}
