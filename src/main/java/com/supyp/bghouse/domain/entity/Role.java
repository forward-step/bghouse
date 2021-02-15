package com.supyp.bghouse.domain.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 角色名称
     */
    @Column(name = "roleName")
    private String rolename;

    /**
     * 角色的主要职责
     */
    private String remark;
}