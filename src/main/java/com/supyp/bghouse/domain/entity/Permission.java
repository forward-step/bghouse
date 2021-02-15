package com.supyp.bghouse.domain.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 父级权限
     */
    @Column(name = "parentId")
    private Integer parentid;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 允许访问的URL
     */
    private String url;
}