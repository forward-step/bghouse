package netty;

import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.utils.JWTUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    @Resource
    private  static JWTUtil jwtUtil;
    @Resource
    private static AccountService accountService;
    // userid --> socket id
    public static Map<Integer, Channel> socketMap = new ConcurrentHashMap<>();
    // 群发信息
    public static void sendMessageAll(Channel channel,String message){
        Collection<Channel> values = Cache.socketMap.values();
        for (Channel ch : values) {
            if(ch != channel){
                ch.writeAndFlush(
                        new TextWebSocketFrame(message)
                );
            }
        }
    }
    // 解析token
    public static Integer decodeToken(String token){
        Account account = jwtUtil.decodeJWT(token);
        if(account == null) return null;
        accountService.findAccountById(account);
        if(account.getStatus() == 0) return null;
        return account.getId();
    }
    // 添加socket
    public static void add(Channel channel,Integer userid){
        socketMap.put(userid,channel);
    }
    // 移除socket
    public static void remove(Channel channel){
        Collection<Channel> col = socketMap.values();
        while(col.contains(channel)) {
            col.remove(channel);
        }
    }
}
