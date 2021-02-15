package com.supyp.bghouse.services.impl;

import com.supyp.bghouse.dao.UserunreadMapper;
import com.supyp.bghouse.domain.entity.Userunread;
import com.supyp.bghouse.services.UserunreadService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service
public class UserunreadServiceImpl implements UserunreadService {
    @Resource
    private UserunreadMapper mapper;

    @Override
    public void setup(int userid, int unread) {
        Userunread userunread = new Userunread();
        userunread.setUserid(userid);
        Userunread selectOne = mapper.selectOne(userunread);
        // 如果没有就插入
        userunread.setUnread(unread);
        if(selectOne == null){
            mapper.insert(userunread);
        }else{
            userunread.setId(selectOne.getId());
            mapper.updateByPrimaryKey(userunread);
        }
    }

    @Override
    public int findUnreadNum(int userid) {
        Userunread userunread = new Userunread();
        userunread.setUserid(userid);
        Userunread selectOne = null;
        try{
            selectOne = mapper.selectOne(userunread);
        }catch (Exception e){
            return 0;
        }
        if(selectOne == null){
            return 0;
        }else{
            return selectOne.getUnread();
        }
    }
}
