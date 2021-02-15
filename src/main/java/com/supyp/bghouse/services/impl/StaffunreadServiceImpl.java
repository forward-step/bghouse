package com.supyp.bghouse.services.impl;

import com.supyp.bghouse.dao.StaffunreadMapper;
import com.supyp.bghouse.domain.entity.Staffunread;
import com.supyp.bghouse.services.StaffunreadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StaffunreadServiceImpl implements StaffunreadService {
    @Resource
    private StaffunreadMapper mapper;

    @Override
    public void setup(int userid, int unread) {
        Staffunread userunread = new Staffunread();
        userunread.setUserid(userid);
        Staffunread selectOne = mapper.selectOne(userunread);
        userunread.setUnread(unread);
        if(selectOne != null){
            // 更新
            userunread.setId(selectOne.getId());
            mapper.updateByPrimaryKey(userunread);
        }
    }

    @Override
    public int findUnreadNum(int userid) {
        Staffunread userunread = new Staffunread();
        userunread.setUserid(userid);
        Staffunread selectOne = mapper.selectOne(userunread);
        if(selectOne == null){
            return 0;
        }else{
            return selectOne.getUnread();
        }
    }
}
