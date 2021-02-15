package com.supyp.bghouse.dao;

import com.supyp.bghouse.domain.dto.ChartSubscribeDto;
import com.supyp.bghouse.domain.entity.Subscribe;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SubscribeMapper extends Mapper<Subscribe> {
    @Select("SELECT\n" +
            "\tDATE_FORMAT(any_value(createTime),'%Y-%m') as date,\n" +
            "\tcount(0) as num\n" +
            "FROM\n" +
            "\tsubscribe\n" +
            "WHERE\n" +
            "\t`status` = #{arg2}\n" +
            "AND date_format(createTime, '%Y-%m') >= #{arg0}\n" +
            "AND date_format(createTime, '%Y-%m') <= #{arg1}\n" +
            "GROUP BY\n" +
            "\tDATE_FORMAT(createTime,'%Y-%m')")
    public List<ChartSubscribeDto> findSubscribData(String startTime, String endTime,String status);
}