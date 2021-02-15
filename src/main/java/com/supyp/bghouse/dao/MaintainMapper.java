package com.supyp.bghouse.dao;

import com.supyp.bghouse.domain.dto.ChartMaintainDto;
import com.supyp.bghouse.domain.entity.Maintain;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MaintainMapper extends Mapper<Maintain> {
    @Select("SELECT\n" +
            "\tDATE_FORMAT(any_value(endTime),'%Y-%m') as date,\n" +
            "\tsum(price) as price\n" +
            "FROM\n" +
            "\tmaintain\n" +
            "WHERE\n" +
            "\tisend = 1\n" +
            "AND date_format(endTime, '%Y-%m') >= #{arg0}\n" +
            "AND date_format(endTime, '%Y-%m') <= #{arg1}\n" +
            "GROUP BY\n" +
            "\tDATE_FORMAT(endTime,'%Y-%m')")
    public List<ChartMaintainDto> findMaintainPricesData(String startTime, String endTime);
}