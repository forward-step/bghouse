package com.supyp.bghouse.domain.dto;

import com.supyp.bghouse.domain.entity.Subscribe;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
* 后台查询预约订单返回的信息
* */
// 父类和子类都有@Data注解，要在子类加上@EqualsAndHashCode(callSuper = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SubscribeDto extends Subscribe {
    public String username;
    public String realname; // 真实姓名

    public void setSubscribe(Subscribe subscribe){
        this.setId(subscribe.getId());
        this.setUserid(subscribe.getUserid());
        this.setRoomid(subscribe.getRoomid());
        this.setCheckintime(subscribe.getCheckintime());
        this.setCreatetime(subscribe.getCreatetime());
        this.setRemark(subscribe.getRemark());
        this.setStatus(subscribe.getStatus());
    }
}
