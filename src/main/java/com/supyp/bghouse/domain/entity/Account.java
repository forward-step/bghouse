package com.supyp.bghouse.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String username;

    private String pwd;

    /**
     * 角色id,0表示普通用户
     */
    @Column(name = "roleId")
    private Integer roleid;

    /**
     * 0表示JWT token已经失效
     */
    private Integer status;

    /**
     * 真实姓名
     */
    @Column(name = "realName")
    private String realname;

    /**
     * 身份证号码
     */
    @Column(name = "idCard")
    private String idcard;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 工资
     */
    private Integer salary;

    private Date createtime;
}