package com.supyp.bghouse.domain.dto;

import lombok.Data;

@Data
public class ChartSubscribeDto {
    public String date; // 日期；YYYY-mm
    public Integer num; // 订单数量
}
