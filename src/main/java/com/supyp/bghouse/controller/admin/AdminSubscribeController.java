package com.supyp.bghouse.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.dto.SubscribeDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Room;
import com.supyp.bghouse.domain.entity.Subscribe;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.services.RoomService;
import com.supyp.bghouse.services.SubscribeService;
import com.supyp.bghouse.utils.DateUtil;
import com.supyp.bghouse.utils.ResUtil;
import com.supyp.bghouse.utils.ThreadLocalUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/subscribe")
public class AdminSubscribeController {
    @Resource
    private SubscribeService subscribeService;
    @Resource
    private AccountService accountService;
    @Resource
    private RoomService roomService;
    @Resource
    private ObjectMapper JSON;

    @PostMapping("/findAll")
    public String findAll(PaginationDto paginationDto) throws JsonProcessingException {
        PageInfo<Subscribe> all = subscribeService.findAll(paginationDto,null);
        ArrayList<SubscribeDto> res_list = new ArrayList<>();
        PageInfo<SubscribeDto> res = new PageInfo<>();

        // 1.添加用户名
        for(Subscribe subscribe: all.getList()){
            SubscribeDto subscribeDto = new SubscribeDto();
            // 1.查询账户
            Account temp = new Account();
            temp.setId(subscribe.getUserid());
            Account account = accountService.findAccountById(temp);
            // 2.添加用户名
            subscribeDto.setUsername(account.getUsername()); // 用户名
            subscribeDto.setRealname(account.getRealname()); // 真实姓名
            subscribeDto.setSubscribe(subscribe); // 预约订单
            res_list.add(subscribeDto);
        }
        // copt pageinfo
        res.setList(res_list);
        res.setHasNextPage(all.isHasNextPage());
        res.setPageSize(all.getPageSize());
        res.setPageNum(all.getPageNum());
        res.setTotal(all.getTotal());

        return JSON.writeValueAsString(res);
    }

    // 查询用户的预约信息
    @PostMapping("/findUserSubscribe")
    public String findAll(
            Integer userid
    ) throws JsonProcessingException {
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setCurrent(1);
        paginationDto.setPageSize(9999);
        // 只查找预约中的信息
        HashMap<String, String> filter = new HashMap<>();
        filter.put("status","0");
        paginationDto.setFilters(filter);
        // 根据创建时间查找
        // sorterArr=[{field=createtime, order=false}
        HashMap<String, String> sorterMap = new HashMap<>();
        sorterMap.put("field","createtime");
        sorterMap.put("order","descend");
        ArrayList<Map<String, String>> sorterArr = new ArrayList<>();
        sorterArr.add(sorterMap);
        paginationDto.setSorterArr(sorterArr);

        return JSON.writeValueAsString(subscribeService.findAll(paginationDto,userid));
    }

    // 添加预约表单
    @PostMapping("/add")
    public String add(
            Subscribe subscribe
    ) throws JsonProcessingException, ParseException {
        Account temp = new Account();;
        temp.setId(subscribe.getUserid());
        Account account = accountService.selectOne(temp);
        // 2.检查入住信息，不能有错
        String error = accountService.validateCheckInInfo(account);
        if(!StringUtil.isEmpty(error)){
            return ResUtil.error("请先完善个人信息");
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
            return ResUtil.error("房间已经被预约");
        }
        // 5.判断房间是否已经被入住
        if(subscribeService.roomIsCheckIn(subscribe.getRoomid())){
            return ResUtil.error("房间已经被入住");
        }
        // 6.插入数据库
        subscribe.setUserid(account.getId()); // 用户id
        subscribe.setCreatetime(DateUtil.LocalDateAndTime()); // 创建时间
        int add;
        try{
            add = subscribeService.add(subscribe);
        }catch(Exception e){
            return ResUtil.error("房间被占用了");
        }
        if(add <= 0) return ResUtil.error("出错了,请联系管理员");
        // 7.房间被预约
        Room room = new Room();
        room.setId(subscribe.getRoomid());
        room.setIssell(1);
        return ResUtil.empty2error(roomService.edit(room));
    }

    // 记得将房间设置为可以出租的状态
    @PostMapping("/cancel")
    public String cancel(
            int roomid, int userid
    ) throws JsonProcessingException {
        HashMap<String, String> res = new HashMap<>();
        // 1.获取登录用户
        // 2.判断roomid是否存在
        if(roomid == 0){
            res.put("type","error");
            res.put("msg","请填写roomid");
            return JSON.writeValueAsString(res);
        }
        // 3.查找预约订单是否存在
        Subscribe selectOne = subscribeService.findOne(userid, roomid, 0);
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
