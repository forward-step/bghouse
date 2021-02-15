package com.supyp.bghouse.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.FriendsDto;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Chat;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.services.ChatService;
import com.supyp.bghouse.services.StaffunreadService;
import com.supyp.bghouse.utils.ResUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/admin/chat")
public class AdminChatController {
    @Resource
    private AccountService accountService;
    @Resource
    private ChatService chatService;
    @Resource
    private StaffunreadService staffunreadService;
    @Resource
    private ObjectMapper JSON;

    // 查找所有跟客服聊过天的好友
    @PostMapping("/findFriedns")
    public String findFriedns() throws JsonProcessingException {
        List<FriendsDto> friends = chatService.findFriedns();
        // 根据用户id，查询isread = 1的数量
        // 根据用户id，查询用户是否完善用户信息
        for(FriendsDto friendsDto: friends){
            // 根据用户id，查询isread = 1的数量
            int unread = staffunreadService.findUnreadNum(friendsDto.getUserid());
            friendsDto.setUnread(unread);
            // 根据用户id，查询用户是否完善用户信息
            Account temp = new Account();
            temp.setId(friendsDto.getUserid());
            Account account = accountService.findAccountById(temp);
            if(
                StringUtil.isEmpty(account.getRealname())
                || StringUtil.isEmpty(account.getIdcard())
                || StringUtil.isEmpty(account.getPhone())
            ){
                friendsDto.setFinishInfo(false);
            }else{
                friendsDto.setFinishInfo(true);
            }
            // 根据用户id查询房间信息 - 预约的、正在租房的
        }
        return JSON.writeValueAsString(friends);
    }
    // 设置未读信息数量
    @PostMapping("/setup")
    public void setup(int userid,int unread) throws JsonProcessingException {
        staffunreadService.setup(userid,unread);
    }

    // 查找聊天记录 - 根据用户id
    @PostMapping("/findChat")
    public String findChat(
            Integer userid,
            Integer current,
            Integer pageSize
    ) throws JsonProcessingException {
        staffunreadService.setup(userid,0); // 清空未读信息
        PageInfo<Chat> all = chatService.findAll(userid, current, pageSize);
        return JSON.writeValueAsString(all);
    }

}
