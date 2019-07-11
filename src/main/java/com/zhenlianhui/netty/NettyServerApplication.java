package com.zhenlianhui.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.zhenlianhui.netty.server.Server;

import io.netty.handler.codec.string.StringEncoder;

@SpringBootApplication
public class NettyServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyServerApplication.class, args);
	}

	@Bean
	public Server createServer() {
		return new Server();
	}
	
	@Bean(name = "stringEncoder")
    public StringEncoder stringEncoder() {
        return new StringEncoder();
    }
}
