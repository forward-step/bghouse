package com.supyp.bghouse.job;


import com.supyp.bghouse.dao.ContractMapper;
import com.supyp.bghouse.domain.entity.Chat;
import com.supyp.bghouse.domain.entity.Contract;
import com.supyp.bghouse.websocket.Cache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/*
* 合同到期提醒 - 剩余时间还有一个月的
* select * from contract where TIMESTAMPDIFF(MONTH,checkInTime,NOW()) = durationTime - 1;
* */
@Component
public class ContractJob {
    @Resource
    private ContractMapper contractMapper;

    public void execute(){
        // 1. 找到需要提醒的用户
        List<Contract> contracts = contractMapper.findExpireContract();
        // 2. 发送信息 - 必须要有userid content
        for(Contract contract : contracts){
            Chat chat = new Chat();
            chat.setUserid(contract.getUserid());
            chat.setContent("你的租约即将过期");
            // 3.发送信息
            Cache.Send2User(null,chat);
        }
    }
}
