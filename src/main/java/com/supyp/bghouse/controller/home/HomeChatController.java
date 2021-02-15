package com.supyp.bghouse.controller.home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Chat;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.services.ChatService;
import com.supyp.bghouse.services.UserunreadService;
import com.supyp.bghouse.utils.ResUtil;
import com.supyp.bghouse.utils.ThreadLocalUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("/home/chat")
public class HomeChatController {
    @Resource
    private AccountService accountService;
    @Resource
    private ChatService chatService;
    @Resource
    private UserunreadService userunreadService;
    @Resource
    private ObjectMapper JSON;

    // 设置未读信息数量
    @PostMapping("/setup")
    public void setup(int unread) throws JsonProcessingException {
        Account loginAccount = ThreadLocalUtil.get();
        userunreadService.setup(loginAccount.getId(),unread);
    }

    // 查找未读信息数量
    @PostMapping("/findUnread")
    public String findUnread() throws JsonProcessingException {
        Account loginAccount = ThreadLocalUtil.get();
        int unreadNum = userunreadService.findUnreadNum(loginAccount.getId());
        return ResUtil.success(String.valueOf(unreadNum));
    }

    // 查找聊天记录 - 根据用户id
    @PostMapping("/findChat")
    public String findChat(
            Integer current,
            Integer pageSize
    ) throws JsonProcessingException {
        Account loginAccount = ThreadLocalUtil.get();
        Integer userid = loginAccount.getId();

        // 清空未读信息
        userunreadService.setup(userid,0);


        PageInfo<Chat> all = chatService.findAll(userid, current, pageSize);
        return JSON.writeValueAsString(all);
    }
}
