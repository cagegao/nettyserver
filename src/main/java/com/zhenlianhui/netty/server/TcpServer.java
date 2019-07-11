package com.zhenlianhui.netty.server;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TcpServer {

	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(20901);
		System.out.println("服务端启动,监听20901端口......");
		while (true) {
			Socket client = server.accept();
			InputStream is = client.getInputStream();
//			int i = -1, idx = 0;
			byte[] b = new byte[is.available()];
//			while ((i = is.read()) != -1) {
//				b[idx] = (byte)i;
//				idx++;
//			}
			System.out.println(b.length);
			System.out.println(client.getRemoteSocketAddress() + "于" + new Date() + " 发送数据:" + new String(b));
//			System.out.println("====================================================");
		}
	}

}
