package com.supyp.bghouse.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/*
* 拦截器
* */
public class webSocketInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 通过channel获取对应的管道
        ChannelPipeline pipeline = channel.pipeline();

        //#region + 以下用于支持HTTP协议
        // websocket基于HTTP协议，所以要有HTTP编解码器
        pipeline.addLast(new HttpServerCodec());
        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对HttpMessage进行聚合
        pipeline.addLast(new HttpObjectAggregator(1024*64));
        //#endregion

        /*
         * 我要关注的，只有下面这三个
         * */
        pipeline.addLast(new HTTPHandler("/ws")); // 处理第一次连接
        // 指定给客户端连接和访问的路由
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new ChatHandler()); // 处理聊天
    }
}
