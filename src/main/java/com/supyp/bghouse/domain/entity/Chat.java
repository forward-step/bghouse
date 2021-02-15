package com.supyp.bghouse.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 房客id
     */
    @Column(name = "userId")
    private Integer userid;

    /**
     * 0表示房客发送，1表示客服发送
     */
    @Column(name = "isSend")
    private Integer issend;

    /**
     * 备注
     */
    private String content;

    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createtime;
}