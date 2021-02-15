package com.supyp.bghouse.domain.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 月租
     */
    private Float price;

    /**
     * 户型
     */
    @Column(name = "houseType")
    private String housetype;

    /**
     * 面积
     */
    private Integer area;

    /**
     * 朝向
     */
    private String dir;

    /**
     * 标签信息，使用/分割
     */
    private String tags;

    /**
     * 房间名称
     */
    private String name;

    /**
     * 0表示没有出租，1表示已经出租
     */
    @Column(name = "isSell")
    private Integer issell;

    /**
     * 房间的图片
     */
    private String url;
}