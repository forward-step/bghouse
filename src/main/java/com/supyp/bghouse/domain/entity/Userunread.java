package com.supyp.bghouse.domain.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "userunread")
public class Userunread {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 房客id
     */
    @Column(name = "userId")
    private Integer userid;

    /**
     * 未读信息数量
     */
    private Integer unread;
}