package com.supyp.bghouse.dao;

import com.supyp.bghouse.domain.dto.ChartContractDto;
import com.supyp.bghouse.domain.entity.Contract;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ContractMapper extends Mapper<Contract> {
    @Select("select * from contract where TIMESTAMPDIFF(MONTH,checkInTime,NOW()) = durationTime - 1;")
    public List<Contract> findExpireContract();

    @Select("select * from contract where TIMESTAMPDIFF(MONTH,checkInTime,NOW()) > durationTime;")
    public List<Contract> findReleaseContract();

    @Select("SELECT\n" +
            "\tDATE_FORMAT(any_value(createTime),'%Y-%m') as date,\n" +
            "\tcount(0) as num,\n" +
            "\tsum(price) as price\n" +
            "FROM\n" +
            "\tcontract\n" +
            "INNER JOIN\n" +
            "\troom\n" +
            "ON contract.roomId = room.id\n" +
            "WHERE\n" +
            "date_format(createTime, '%Y-%m') >= #{arg0}\n" +
            "AND date_format(createTime, '%Y-%m') <= #{arg1}\n" +
            "GROUP BY\n" +
            "\tDATE_FORMAT(createTime,'%Y-%m')")
    public List<ChartContractDto> findContractData(String startTime,String endTime);

    // 本月租金收入
    @Select("select \n" +
            "\tsum(price) as price\n" +
            "from contract\n" +
            "inner join room\n" +
            "on contract.roomId = room.id\n" +
            "-- 找到所有还在租的合同\n" +
            "where\n" +
            "TIMESTAMPDIFF(MONTH,checkInTime,#{arg0}) < contract.durationTime\n" +
            "and #{arg0} > checkInTime;\n")
    public Float findPriceOfMonth(String time);
}