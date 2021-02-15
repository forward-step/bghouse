package com.supyp.bghouse.controller.home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Room;
import com.supyp.bghouse.domain.entity.Subscribe;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.services.RoomService;
import com.supyp.bghouse.services.SubscribeService;
import com.supyp.bghouse.utils.DateUtil;
import com.supyp.bghouse.utils.ResUtil;
import com.supyp.bghouse.utils.ThreadLocalUtil;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashMap;

@RestController
@RequestMapping("/home/subscribe")
public class HomeSubscribeController {

    @Resource
    private AccountService accountService;
    @Resource
    private RoomService roomService;
    @Resource
    private SubscribeService subscribeService;
    @Resource
    private ObjectMapper JSON;

    @PostMapping("/add")
    public String add(
            Subscribe subscribe
    ) throws JsonProcessingException, ParseException {
        System.out.println(subscribe);
        // 1.获取登录用户
        Account loginAccount = ThreadLocalUtil.get();
        // 2.检查入住信息，不能有错
        String error = accountService.validateCheckInInfo(loginAccount);
        if(!StringUtil.isEmpty(error)){
            return ResUtil.error("请先填写入住信息");
        }
        // 3.判断预约信息是否完整
        // roomid不能为空 , checkintime不能为空
        if(subscribe.getRoomid() == null){
            return ResUtil.error("房间id没有填写");
        }
        if(subscribe.getCheckintime() == null){
            return ResUtil.error("入住预约时间没有登记");
        }
        // 5.判断房间是否已经被预约
        if(subscribeService.roomIsSubscribe(subscribe.getRoomid())){
            return ResUtil.error("房间已经被预约了");
        }
        // 6.插入数据库
        subscribe.setUserid(loginAccount.getId()); // 用户id
        subscribe.setCreatetime(DateUtil.LocalDateAndTime()); // 创建时间
        int add = subscribeService.add(subscribe);
        if(add <= 0) return ResUtil.error("出错了,请联系管理员");
        // 7.房间被预约
        Room room = new Room();
        room.setId(subscribe.getRoomid());
        room.setIssell(1);
        return ResUtil.empty2error(roomService.edit(room));
    }

    // 查询我的预约订单
    @PostMapping("/findAll")
    public String findAll(
        PaginationDto paginationDto
    ) throws JsonProcessingException {
        Account loginAccount = ThreadLocalUtil.get();


        return JSON.writeValueAsString(subscribeService.findAll(paginationDto,loginAccount.getId()));
    }


    // 记得将房间设置为可以出租的状态
    @PostMapping("/cancel")
    public String cancel(
            @RequestParam(defaultValue = "0") int roomid
    ) throws JsonProcessingException {
        HashMap<String, String> res = new HashMap<>();
        // 1.获取登录用户
        Account loginAccount = ThreadLocalUtil.get();
        // 2.判断roomid是否存在
        if(roomid == 0){
            res.put("type","error");
            res.put("msg","请填写roomid");
            return JSON.writeValueAsString(res);
        }
        // 3.查找预约订单是否存在
        Subscribe selectOne = subscribeService.findOne(loginAccount.getId(), roomid, 0);
        if(selectOne == null){
            res.put("type","error");
            res.put("msg","预约订单不存在");
        }else{
            if(subscribeService.cancel(selectOne.getId()) <= 0){
                res.put("type","error");
                res.put("msg","出错了，请联系管理员");
            }else{
                res.put("type","success");
                res.put("msg","");
            }
        }
        return JSON.writeValueAsString(res);
    }
}
