package com.supyp.bghouse.dao;

import com.supyp.bghouse.domain.entity.Account;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AccountMapper extends Mapper<Account> {
    // 查找员工总工资
    @Select("select sum(salary) from account where roleid is not null;")
    public Float findSumSalary();
    // 查找用户注册的折线图数据
    @Select("select count(createtime) as num from account where roleid is null group by createtime order by createtime asc;")
    public List<Integer> findUserLineChart();
}