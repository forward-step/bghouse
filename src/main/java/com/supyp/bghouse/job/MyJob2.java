package com.supyp.bghouse.job;


import javax.annotation.Resource;
import java.time.LocalTime;

/**
 * 此处统一执行所有任务
 */
public class MyJob2 {
    @Resource
    private ContractJob contractJob;
    @Resource
    private MissSubscribeJob missSubscribeJob;
    @Resource
    private ReleaseRoomJob releaseRoomJob;
    @Resource
    private SubscribeJob subscribeJob;
    public void execute(){
        contractJob.execute();
        missSubscribeJob.execute();
        releaseRoomJob.execute();
        subscribeJob.execute();
    }
}
