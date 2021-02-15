package com.supyp.bghouse.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WebSocketServer {

	private static class SingletionWSServer {
		static final WebSocketServer instance = new WebSocketServer();
	}

	public static WebSocketServer getInstance() {
		return SingletionWSServer.instance;
	}

	private EventLoopGroup bossGroup; // 主线程
	private EventLoopGroup workerGroup; // 工作线程
	private ServerBootstrap serverBootstrap; // 启动类
	private ChannelFuture future;

	public WebSocketServer() {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		serverBootstrap = new ServerBootstrap();
		serverBootstrap
				.group(bossGroup, workerGroup) // 关联主从线程
				.channel(NioServerSocketChannel.class) // 设置NIO的双向通道
				.childHandler(new webSocketInitializer()); // 拦截器,worker线程工作的东西
	}

	public void start() {
		this.future = serverBootstrap.bind(8090);
		System.err.println("netty websocket server 启动完毕...");
	}
	public void destory(){
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		future.channel().closeFuture().syncUninterruptibly();
	}
}