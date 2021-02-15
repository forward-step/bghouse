package com.supyp.bghouse.domain.dto;

import lombok.Data;

import java.util.Date;

// 客服查找聊天好友
// {userid: xxx,username: xxx,unread: xxx}
@Data
public class FriendsDto {
    public Integer userid;
    public String username;
    public Date lasttime;
    public String lastContent;
    public Integer unread; // 未读信息
    public boolean isFinishInfo; // 是否完善用户信息
}
