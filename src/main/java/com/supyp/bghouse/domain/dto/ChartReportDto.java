package com.supyp.bghouse.domain.dto;

import lombok.Data;

@Data
public class ChartReportDto {
    public Float staffSalary; // 员工工资
    public Float maintainPrice; // 维修支出
    public Float income; // 租金收入
    public Float profit; // 利润
}
