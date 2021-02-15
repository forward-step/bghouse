package com.supyp.bghouse.domain.dto;

import com.supyp.bghouse.domain.entity.Contract;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
 * 后台查询预约订单返回的信息
 * */
// 父类和子类都有@Data注解，要在子类加上@EqualsAndHashCode(callSuper = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class ContractDto extends Contract {
    public String username; // 用户名
    public String realname; // 真实姓名
    public Integer remaintime;// 剩余时间

    public void setContract(Contract contract){
        this.setId(contract.getId());
        this.setUserid(contract.getUserid());
        this.setRoomid(contract.getRoomid());
        this.setCreatetime(contract.getCreatetime());
        this.setCheckintime(contract.getCheckintime());
        this.setDurationtime(contract.getDurationtime());
        this.setRemark(contract.getRemark());
    }
}
