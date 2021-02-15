package com.supyp.bghouse.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Chat;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.services.ChatService;
import com.supyp.bghouse.services.StaffunreadService;
import com.supyp.bghouse.services.UserunreadService;
import com.supyp.bghouse.utils.JWTUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Cache {
    // 账户服务
    @Resource
    public void setAccountService(AccountService accountService){
        Cache.accountService = accountService;
    }
    private static AccountService accountService;

    // 聊天服务
    @Resource
    public void setAccountService(ChatService chatService){
        Cache.chatService = chatService;
    }
    private static ChatService chatService;

    // 用户未读信息服务
    @Resource
    public void setUserunreadService(UserunreadService userunreadService) {Cache.userunreadService = userunreadService;}
    private static UserunreadService userunreadService;

    // 管理员未读信息服务
    @Resource
    public void setStaffunreadService(StaffunreadService staffunreadService){Cache.staffunreadService = staffunreadService;}
    private static StaffunreadService staffunreadService;

    // JSON序列化
    @Resource
    public void setJSON(ObjectMapper objectMapper){Cache.JSON = objectMapper;}
    private static ObjectMapper JSON;

    // userid --> socket id
    // 分开存放的好处：使用方便
    public static Map<Channel, Account> UserSocketMap = new ConcurrentHashMap<>(); // 存放用户
    public static Map<Channel, Account> AdminSocketMap = new ConcurrentHashMap<>(); // 存放管理员

    // 添加socket
    public static void add(Channel channel,Account loginAccount){
        if(loginAccount.getRoleid() == null) UserSocketMap.put(channel,loginAccount);
        else AdminSocketMap.put(channel,loginAccount);
        System.out.println("用户人数:"+UserSocketMap.size());
        System.out.println("管理员人数"+AdminSocketMap.size());
    }
    // 移除socket
    public static void remove(Channel channel){
        UserSocketMap.remove(channel);
        AdminSocketMap.remove(channel);
    }

    // 解析token获取登录账户
    public static Account decodeToken(String token){
        Account account = JWTUtil.decodeJWT(token);
        if(account == null) return null;
        account = accountService.findAccountById(account); // 登录账户
        System.out.println("解析token，获取登录账户");
        System.out.println(account);
        if(account == null) return null;
        return account;
    }

    public static void Send(Channel channel,Chat chat) throws JsonProcessingException {
        System.out.println(accountService);
        System.out.println(chatService);
        System.out.println(staffunreadService);
        System.out.println(userunreadService);
        System.out.println(JSON);
        if(UserSocketMap.get(channel) != null){
            Send2Admin(channel,chat);
        }else if(AdminSocketMap.get(channel) != null){
            Send2User(channel,chat);
        }
    }

    // 客服发送
    // chat必须有userid content
    public static void Send2User(Channel channel, Chat chat){
        // 如果内容为空，直接退出
        if(StringUtil.isEmpty(chat.getContent())) return;

        Channel sendChannel = null;
        Set<Map.Entry<Channel, Account>> userEntrys = UserSocketMap.entrySet();
        for(Map.Entry<Channel, Account> entry: userEntrys){
            if(entry.getValue().getId().equals(chat.getUserid())){
                sendChannel = entry.getKey();
                break;
            }
        }

        // 1.插入到数据库
        chatService.add(chat.getUserid(),0,chat); // 1表示房客发送
        // 2,websocket发送
        if (sendChannel != null){
            sendChannel.writeAndFlush(
                    new TextWebSocketFrame(chat.getContent())
            );
        }else{
            // 用户离线，未读信息加一
            int unreadNum = userunreadService.findUnreadNum(chat.getUserid());
            userunreadService.setup(chat.getUserid(),unreadNum + 1);
        }
    }

    // 房客发送
    private static void Send2Admin(Channel channel, Chat chat) throws JsonProcessingException {
        // 如果内容为空，直接退出
        if(StringUtil.isEmpty(chat.getContent())) return;
        Account loginAccount = UserSocketMap.get(channel); // 用户的登录账户，不是管理员的

        // 发送信息
        HashMap<String, String> res = new HashMap<>();
        res.put("userid",String.valueOf(loginAccount.getId()));
        res.put("username",loginAccount.getUsername());
        res.put("content",chat.getContent());

        // 1.websocket发送 - 发送给所有管理员
        Set<Channel> channels = AdminSocketMap.keySet();
        for(Channel adminSocket:channels){
            adminSocket.writeAndFlush(
                    new TextWebSocketFrame(
                            JSON.writeValueAsString(res)
                    )
            );
        }
        // 2.插入到数据库
        chatService.add(loginAccount.getId(),1,chat); // 1表示房客发送

        // 所有管理员都不在线 - 未读信息加一
        if(channels.size() == 0){
            int unreadNum = staffunreadService.findUnreadNum(loginAccount.getId());
            staffunreadService.setup(loginAccount.getId(),unreadNum + 1);
        }
    }
}
