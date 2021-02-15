package com.supyp.bghouse.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Table(name = "subscribe")
public class Subscribe {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 房客id
     */
    @Column(name = "userId")
    private Integer userid;

    /**
     * 房间id
     */
    @Column(name = "roomId")
    private Integer roomid;

    /**
     * 入住时间
     */
    @Column(name = "checkInTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkintime;

    /**
     * 约定时间
     */
    @Column(name = "createTime")
    private Date createtime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 预定状态,0表示还没到预定时间，1表示错过预定
     */
    private Integer status;
}