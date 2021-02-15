package com.supyp.bghouse.domain.dto;

import lombok.Data;

import java.util.Map;

/*
* 收集前台房间列表发送的信息
* 据此查询房间列表
* */
@Data
public class RoomDto {
    public int current;
    public int pageSize;
    public Map<String,String> price;
    public Map<String,String> area;
    public String dir;
    public String searchText;
}
