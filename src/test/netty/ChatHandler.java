package netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*
* TextWebSocketFrame: 专门处理文本的对象
* */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    // 处理websocket
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg)
            throws Exception {
        String text = msg.text(); // 获取客户端输出过来的数据
        System.out.println("[服务器]当前在线人数为:"+ Cache.socketMap.size());
        Cache.sendMessageAll(ctx.channel(),text);
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
