package com.supyp.bghouse.job;

import com.supyp.bghouse.domain.entity.Subscribe;
import com.supyp.bghouse.services.SubscribeService;
import com.supyp.bghouse.utils.DateUtil;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/*
* 预约超时，自动取消任务
* select * from subscribe where status = 0 and '2020-12-05' > checkInTime;
* */
@Component
public class MissSubscribeJob {
    @Resource
    private SubscribeService subscribeService;

    public void execute() {
        Example example = new Example(Subscribe.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status",0)
                .andLessThan("checkintime", DateUtil.date2String(new Date()));

        // select * from subscribe where status = 0 and '2020-12-05' > checkInTime;
        List<Subscribe> subscribes = subscribeService.findByExample(example);
        System.out.println(subscribes);
        for(Subscribe subscribe: subscribes){
            subscribeService.miss(subscribe.getId());
        }
    }
}
