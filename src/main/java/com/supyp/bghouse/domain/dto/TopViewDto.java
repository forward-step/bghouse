package com.supyp.bghouse.domain.dto;

import lombok.Data;

import java.util.List;

/*
*  chart页面顶层信息的返回
* */
@Data
public class TopViewDto {
    public Integer userNum; // 用户人数
    public List<Integer> userData; // 用户人数变化图
    public Integer todayUserNum; // 今日新增用户数

    public Integer staffNum; // 员工人数
    public Float staffSalary; // 员工总工资
    public Integer roleNum; // 角色数量
}
