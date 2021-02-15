package com.supyp.bghouse.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.dao.ChatMapper;
import com.supyp.bghouse.domain.dto.FriendsDto;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Chat;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.services.ChatService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private ChatMapper chatMapper;
    @Resource
    private AccountService accountService;

    @Override
    public int add(int userid,int isSend,Chat chat) {
        // content为空 ， 不插入
        if(StringUtil.isEmpty(chat.getContent())) return 0;
        chat.setIssend(isSend);
        chat.setUserid(userid); // 房客id
        chat.setCreatetime(new Date()); // 消息发送时间
        return chatMapper.insert(chat);
    }

    @Override
    public PageInfo<Chat> findAll(Integer userid, Integer current, Integer pageSize) {
        // 1.分页
        PageHelper.startPage(current,pageSize);
        // 2.构建example
        Example example = new Example(Chat.class);
        example.and().andEqualTo("userid",userid);
        example.orderBy("createtime").desc();
        List<Chat> select = chatMapper.selectByExample(example);
        return new PageInfo<>(select);
    }

    @Override
    public List<FriendsDto> findFriedns() {
        // @return {userid: xxx,username: xxx,unread: xxx,lasttime: xxx}
        /*
select any_value(a.userId) as userid ,any_value(account.username) as username ,any_value(a.createTime) as lasttime,any_value(a.content) as lastcontent
from chat as a
inner join (select userid,MAX(createTime) as lasttime from chat group by userid) as b
on a.createTime = b.lasttime and a.userId = b.userid
inner join account
on a.userId = account.id
group by a.userId;
        * */
        return chatMapper.findFriends();
    }
}
