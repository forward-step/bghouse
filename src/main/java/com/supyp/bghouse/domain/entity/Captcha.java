package com.supyp.bghouse.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "captcha")
public class Captcha {
    /**
     * 唯一的ip地址
     */
    private String ip;

    /**
     * 验证码内容
     */
    @Column(name = "checkCode")
    private String checkcode;

    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createtime;
}