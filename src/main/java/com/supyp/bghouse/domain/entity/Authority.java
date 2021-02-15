package com.supyp.bghouse.domain.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "authority")
public class Authority {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 角色id
     */
    @Column(name = "roleId")
    private Integer roleid;

    /**
     * 权限id
     */
    @Column(name = "permissioniD")
    private Integer permissionid;
}