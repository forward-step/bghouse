package com.supyp.bghouse.job;

import com.supyp.bghouse.domain.entity.Chat;
import com.supyp.bghouse.domain.entity.Subscribe;
import com.supyp.bghouse.services.SubscribeService;
import com.supyp.bghouse.utils.DateUtil;
import com.supyp.bghouse.websocket.Cache;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

// 入住提醒 - checkintime即将到来的前一天和当天都会提醒用户
// select * from Subscribe where (checkInTime  = 明天 and status = 0) or (checkInTime  = 当天 and status = 0);
@Component
public class SubscribeJob {
    @Resource
    private SubscribeService subscribeService;

    public void execute(){
        this.findUsers(DateUtil.date2String(new Date()),"系统提醒您: 今天是您的预约入住时间");
        this.findUsers(DateUtil.getFetureDate(1),"系统提醒您: 明天是您的预约入住时间");
    }
    // 查找用户 + 通知用户
    private void findUsers(String date,String content){
        Example example = new Example(Subscribe.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("checkintime",date)
                .andEqualTo("status",0);
        List<Subscribe> list = subscribeService.findByExample(example);
        if(!list.isEmpty()){
            for(Subscribe subscribe: list){
                // TODO() 通知用户
                Chat chat = new Chat();
                chat.setUserid(subscribe.getUserid());
                chat.setContent(content);
                // 3.发送信息
                Cache.Send2User(null,chat);
            }
        }
    }
}
