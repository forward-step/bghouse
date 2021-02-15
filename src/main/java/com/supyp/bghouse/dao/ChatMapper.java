package com.supyp.bghouse.dao;

import com.supyp.bghouse.domain.dto.FriendsDto;
import com.supyp.bghouse.domain.entity.Chat;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ChatMapper extends Mapper<Chat> {
    @Select("select any_value(a.userId) as userid ,any_value(account.username) as username ,any_value(a.createTime) as lasttime,any_value(a.content) as lastcontent\n" +
            "from chat as a\n" +
            "inner join (select userid,MAX(createTime) as lasttime from chat group by userid) as b\n" +
            "on a.createTime = b.lasttime and a.userId = b.userid\n" +
            "inner join account\n" +
            "on a.userId = account.id\n" +
            "where account.roleId is NULL\n" +
            "group by a.userId\n" +
            "order by a.createTime desc;")
    public List<FriendsDto> findFriends();
}