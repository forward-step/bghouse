package com.supyp.bghouse.services;

public interface StaffunreadService {
    // 设置未读信息数
    public void setup(int userid,int unread);
    // 读取未读信息数量
    public int findUnreadNum(int userid);
}
