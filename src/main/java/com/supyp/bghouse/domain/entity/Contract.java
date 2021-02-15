package com.supyp.bghouse.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Table(name = "contract")
public class Contract {
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
     * 合同签订时间
     */
    @Column(name = "createTime")
    private Date createtime;

    /**
     * 入住时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "checkInTime")
    private Date checkintime;

    /**
     * 租约时间，单位为月份
     */
    @Column(name = "durationTime")
    private Integer durationtime;

    /**
     * 备注
     */
    private String remark;
}