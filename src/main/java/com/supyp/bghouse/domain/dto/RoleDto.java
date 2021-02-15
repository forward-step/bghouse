package com.supyp.bghouse.domain.dto;

import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDto extends Role {
    public List<String> accounts; // 分别是谁 username[]
    public void setRole(Role role) {
        this.setId(role.getId());
        this.setRolename(role.getRolename());
        this.setRemark(role.getRemark());
    }

}
