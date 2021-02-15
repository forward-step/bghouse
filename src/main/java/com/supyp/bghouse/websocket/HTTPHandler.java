package com.supyp.bghouse.websocket;

import com.supyp.bghouse.domain.entity.Account;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;


/*
* 处理HTTP请求
* */
public class HTTPHandler extends SimpleChannelInboundHandler<FullHttpRequest > {
    private String wsUri;
    HTTPHandler(String wsUri){
        this.wsUri = wsUri;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest  request) throws Exception {
        // 如果是webSocket请求，请求地址uri等于wsUri
        String uri = request.uri();
        int index = uri.indexOf(wsUri);
        if (index != -1) {
            String token = uri.substring(index + 4);
            Account loginAccount = Cache.decodeToken(token);
            if(loginAccount != null){
                System.out.println(loginAccount.getUsername()+"连接到聊天服务器");
                // 缓存起来
                Cache.add(ctx.channel(),loginAccount);
                // 将消息发送到下一个channelHandler
                request.setUri("/ws");
                ctx.fireChannelRead(request.retain());
            }else {
                ctx.close();
            }
        }
    }
    // 出现异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
