package com.supyp.bghouse.services;

import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.FriendsDto;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Chat;

import java.util.List;

public interface ChatService {
    // 添加聊天信息
    // who 2 who
    // userid: 关联的房客
    // isSend: 1表示房客发的
    public int add(int userid,int isSend,Chat chat);

    // 寻找聊天记录
    public PageInfo<Chat> findAll(Integer userid,
                                  Integer current,
                                  Integer pageSize);

    /*
    * 客服寻找查找聊天好友
    * @return {userid: xxx,username: xxx,unread: xxx}
    * */
    public List<FriendsDto> findFriedns();
}
