package com.supyp.bghouse.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supyp.bghouse.domain.entity.Chat;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import javax.annotation.Resource;

/*
 * TextWebSocketFrame: 专门处理文本的对象
 * */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 处理websocket
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        // 1.将客户端发送的信息解析为JSON对象
        ObjectMapper JSON = new ObjectMapper();
        try {
            Chat chat = JSON.readValue(msg.text(), Chat.class);
            Cache.Send(ctx.channel(),chat);
            // 2.发送信息
            // Cache.Send(ctx.channel(),);
        } catch (JsonProcessingException e) {
            System.out.println("客户端发送的信息格式错误");
        }
    }
    // 连接建立成功
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    // 用户关闭连接
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        Cache.remove(ctx.channel()); // 移除socket
    }
}
