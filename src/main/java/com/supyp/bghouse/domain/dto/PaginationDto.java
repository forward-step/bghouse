package com.supyp.bghouse.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/*
* 接收的分页信息
* */
@Data
public class PaginationDto {
    public int current;
    public int pageSize;
    public Map<String,String> filters;
    public List<Map<String,String>> sorterArr;
}
