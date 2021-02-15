package com.supyp.bghouse.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "maintain")
public class Maintain {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 房间id
     */
    @Column(name = "roomId")
    private Integer roomid;

    /**
     * 维修的原因、描述信息
     */
    private String remark;

    /**
     * 开始维修的时间
     */
    @Column(name = "startTime")
    private Date starttime;

    /**
     * 维修是否结束
     */
    @Column(name = "isEnd")
    private Integer isend;

    /**
     * 维修费用
     */
    private Float price;

    /**
     * 结束维修的时间
     */
    @Column(name = "endTime")
    private Date endtime;
}