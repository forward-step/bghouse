package com.supyp.bghouse.services;

import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.dto.SubscribeDto;
import com.supyp.bghouse.domain.entity.Role;
import com.supyp.bghouse.domain.entity.Subscribe;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface SubscribeService {
    // handle
    public Boolean roomIsSubscribe(int roomid); // 判断房间是否已经被预约
    public Boolean roomIsCheckIn(int roomid);// 判断房间是否有人入住
    public Subscribe findOne(int userid, int roomid,int status); // 根据用户id和房间id查找预约表单
    public List<Subscribe> findByExample(Example example);
    public int add(Subscribe subscribe);// 添加一个预定订单信息

    // 操作
    public int cancel(int subid); // 网上取消预约
    public int miss(int subid); // 客人没来，错过预约，由定时任务框架完成
    public int success(int subid); //客人来了，成功入住

    // findAll
    public PageInfo<Subscribe> findAll(PaginationDto paginationDto,Integer userid); // 分页查找所有的角色
}
