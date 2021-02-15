package com.supyp.bghouse.controller.admin;

import com.supyp.bghouse.job.ContractJob;
import com.supyp.bghouse.job.MissSubscribeJob;
import com.supyp.bghouse.job.ReleaseRoomJob;
import com.supyp.bghouse.job.SubscribeJob;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/test")
public class AdminTestController {

    @Resource
    private ContractJob contractJob;
    @Resource
    private SubscribeJob subscribeJob;
    @Resource
    private ReleaseRoomJob releaseRoomJob;
    @Resource
    private MissSubscribeJob missSubscribeJob;


    // 群发信息 - 给所有房客

    // 手动触发预约提醒
    @PostMapping("/fireSubscribeJob")
    public void fireSubscribeJob(){
        subscribeJob.execute();
    }

    // 手动触发合同到期提醒
    @PostMapping("/fireContractJob")
    public void fireContractJob(){
        contractJob.execute();
    }

    // 释放房间占用
    @PostMapping("/fireReleaseRoomJob")
    public void fireReleaseRoomJob(){
        releaseRoomJob.execute();
    }

    // 手动触发预约超时自动取消
    @PostMapping("/fireMissSubscribeJob")
    public void fireMissSubscribeJob(){
        missSubscribeJob.execute();
    }
}
