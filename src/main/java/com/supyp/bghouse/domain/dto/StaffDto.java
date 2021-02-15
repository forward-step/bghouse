package com.supyp.bghouse.domain.dto;

import com.supyp.bghouse.domain.entity.Account;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StaffDto extends Account {
    public String rolename; // 角色名称
    public void setAccount(Account account){
        this.setId(account.getId());
        this.setUsername(account.getUsername());
        this.setRoleid(account.getRoleid());
        this.setRealname(account.getRealname());
        this.setIdcard(account.getIdcard());
        this.setPhone(account.getPhone());
        this.setSalary(account.getSalary());
    }
}
