package com.supyp.bghouse.job;

import com.supyp.bghouse.dao.ContractMapper;
import com.supyp.bghouse.dao.RoomMapper;
import com.supyp.bghouse.dao.SubscribeMapper;
import com.supyp.bghouse.domain.entity.Contract;
import com.supyp.bghouse.domain.entity.Room;
import com.supyp.bghouse.domain.entity.Subscribe;
import com.supyp.bghouse.services.ContractService;
import com.supyp.bghouse.services.RoomService;
import com.supyp.bghouse.services.SubscribeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

/*
 * 找到所有已经出售的房间
 * select * from room where issell = 1; -- [4,29,30]
 *
 * 被预约订单占用的房间
 * select * from subscribe where status = 0; -- [30]
 *
 * 被合同占用的房间
 * select * from contract where TIMESTAMPDIFF(MONTH,checkInTime,NOW()) > durationTime; -- [4]
 *
 *
 * 综上所述：释放roomid = 29的房间
 * */
@Component
public class ReleaseRoomJob {
    @Resource
    private RoomMapper roomMapper;
    @Resource
    private SubscribeMapper subscribeMapper;
    @Resource
    private ContractMapper contractMapper;

    public void execute(){
        // 1.找到已经出售的房间 -- select * from room where issell = 1; -- [4,29,30]
        Room room = new Room();
        room.setIssell(1);
        List<Room> rooms = roomMapper.select(room);
        // 2.找到被预约表单占用的房间 -- select * from subscribe where status = 0; -- [30]
        Subscribe subscribe = new Subscribe();
        subscribe.setStatus(0);
        List<Subscribe> subscribes = subscribeMapper.select(subscribe);
        // 3.找到被合同占用的房间 -- select * from contract where TIMESTAMPDIFF(MONTH,checkInTime,NOW()) > durationTime; -- [4]
        List<Contract> contracts = contractMapper.findReleaseContract();

        // 4.释放房间的ids
        HashSet<Integer> ids = new HashSet<>();
        for(Room r : rooms){
            ids.add(r.getId()); // 添加房间id
        }
        for(Subscribe s : subscribes){
            ids.remove(s.getRoomid()); // 移除房间id
        }
        for(Contract c : contracts){
            ids.remove(c.getRoomid()); // 移除房间id
        }
        // 5.释放房间
        System.out.println("释放房间的ids");
        System.out.println(ids);// [29]
        for(Integer roomid: ids){
            Room temp = new Room();
            temp.setId(roomid); // 房间id
            temp.setIssell(0); // 释放了
            roomMapper.updateByPrimaryKeySelective(temp);
        }
    }
}
