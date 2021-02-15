package com.supyp.bghouse.domain.dto;

import lombok.Data;

@Data
public class ChartContractDto {
    public String date;
    public Float price; // 合同的总价格
    public Integer num; // 合同的数量
}
