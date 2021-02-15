package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;


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
            Integer userId = Cache.decodeToken(token);
            System.out.println("token = " + token);
            if(userId != null){
                // 缓存起来
                Cache.add(ctx.channel(),userId);
                // 将消息发送到下一个channelHandler
                request.setUri("/ws");
                ctx.fireChannelRead(request.retain());
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
