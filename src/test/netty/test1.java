package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.junit.Test;
import io.netty.channel.nio.NioEventLoopGroup;

public class test1 {
    @Test
    public void run() throws InterruptedException {
        // 主线程: 用于接收客户端的连接，但是不做任何处理
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 从线程：主线程会将任务丢给他
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            // netty服务器的创建
            ServerBootstrap serverBootstrap = new ServerBootstrap(); // 启动类
            serverBootstrap.group(bossGroup,workerGroup) // 设置主从线程组
                    .channel(NioServerSocketChannel.class) // 设置NIO的双向通道
                    .childHandler(new HelloServerInitializer()); // 拦截器,worker线程工作的东西
            // 启动server，设置端口，同步启动
            ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();
            // 监听关闭的channel
            channelFuture.channel().closeFuture().sync();
        }finally {
            // 关闭线程
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
