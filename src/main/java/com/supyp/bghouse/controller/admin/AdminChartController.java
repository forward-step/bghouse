package com.supyp.bghouse.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supyp.bghouse.dao.*;
import com.supyp.bghouse.domain.dto.ChartMaintainDto;
import com.supyp.bghouse.domain.dto.ChartReportDto;
import com.supyp.bghouse.domain.dto.ChartSubscribeDto;
import com.supyp.bghouse.domain.dto.TopViewDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Maintain;
import com.supyp.bghouse.services.ContractService;
import com.supyp.bghouse.services.SubscribeService;
import com.supyp.bghouse.utils.DateUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/admin/chart")
public class AdminChartController {
    /*
    * 经营分析模块
    * 2.每个月的订单、合同数量
    * 3.每月租金收入
    * */
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MaintainMapper maintainMapper;
    @Resource
    private SubscribeMapper subscribeMapper;
    @Resource
    private ContractMapper contractMapper;
    @Resource
    private ObjectMapper JSON;

    // 请求得到topview的数据
    @PostMapping("/findTopView")
    public String findTopView() throws JsonProcessingException {
        TopViewDto res = new TopViewDto();
        // 用户
        res.setUserNum(this.getUserNum());
        res.setUserData(accountMapper.findUserLineChart());
        res.setTodayUserNum(this.getTodayUserNum());

        // 员工
        res.setStaffNum(this.getStaffNum());
        res.setStaffSalary(this.getStaffSalary());
        res.setRoleNum(roleMapper.selectAll().size());

        // 返回
        return JSON.writeValueAsString(res);
    }

    // 请求得到维修的数据
    @PostMapping("/findMaintain")
    public String findMaintain(
            String startTime,
            String endTime
    ) throws JsonProcessingException {
        // 本月维修的价格 ; select sum(price) from maintain where isend = 1 and MONTH(endTime) = MONTH(now());
        // 计算starttime - endtime之间的维修价格数据 - 列表
        /*
SELECT
	DATE_FORMAT(any_value(endTime),'%Y-%m') as date,
	sum(price) as price
FROM
	maintain
WHERE
	isend = 1
AND date_format(endTime, '%Y-%m') >= '2000-01'
AND date_format(endTime, '%Y-%m') <= '2020-12'
GROUP BY
	DATE_FORMAT(endTime,'%Y-%m')
        * */
        return JSON.writeValueAsString(maintainMapper.findMaintainPricesData(startTime,endTime));
    }

    // 请求得到预约订单的数据
    @PostMapping("/findSubscribe")
    public String findSubscribe(
            String startTime,
            String endTime
    ) throws JsonProcessingException {
        /*
SELECT
	DATE_FORMAT(any_value(createTime),'%Y-%m') as date,
	count(0) as num
FROM
	subscribe
WHERE
	`status` = 1
AND date_format(createTime, '%Y-%m') >= '2000-01'
AND date_format(createTime, '%Y-%m') <= '2020-12'
GROUP BY
	DATE_FORMAT(createTime,'%Y-%m')
        * */
        List<ChartSubscribeDto> success = subscribeMapper.findSubscribData(startTime, endTime, "1");
        List<ChartSubscribeDto> cancel = subscribeMapper.findSubscribData(startTime, endTime, "2");
        List<ChartSubscribeDto> miss = subscribeMapper.findSubscribData(startTime, endTime, "3");
        HashMap<String, List> res = new HashMap<>();
        res.put("success",success);
        res.put("cancel",cancel);
        res.put("miss",miss);
        return JSON.writeValueAsString(res);
    }

    // 请求得到合同的数据
    @PostMapping("/findContract")
    public String findContract(
            String startTime,
            String endTime
    ) throws JsonProcessingException {
/*
-- 每个月有多少单，多少钱
SELECT
	DATE_FORMAT(any_value(createTime),'%Y-%m') as date,
	count(0) as num,
	sum(price) as price
FROM
	contract
INNER JOIN
	room
ON contract.roomId = room.id
WHERE
date_format(createTime, '%Y-%m') >= '2000-01'
AND date_format(createTime, '%Y-%m') <= '2020-12'
GROUP BY
	DATE_FORMAT(createTime,'%Y-%m')
* */
        return JSON.writeValueAsString(contractMapper.findContractData(startTime,endTime));
    }

    // 利润报表 - 利润 = 收入 - 支出
    // 收入 = 租金收入
    // 支出 = 维修支出 + 员工工资支出
    @PostMapping("/report")
    public String report() throws JsonProcessingException {
        ChartReportDto res = new ChartReportDto();
        System.out.println("时间: " + DateUtil.date2String(new Date()));
        res.setIncome(contractMapper.findPriceOfMonth(DateUtil.date2String(new Date())));
        res.setStaffSalary(this.getStaffSalary());
        List<ChartMaintainDto> data = maintainMapper.findMaintainPricesData(
                DateUtil.date2StringByYearAndMonth(new Date()),
                DateUtil.date2StringByYearAndMonth(new Date())
        );
        System.out.println(DateUtil.date2StringByYearAndMonth(new Date()));
        System.out.println(data);
        if(data.size() == 0){
            res.setMaintainPrice((float) 0);
        }else {
            res.setMaintainPrice(data.get(0).getPrice());
        }

        // 利润
        res.setProfit(
                res.getIncome() - res.getStaffSalary() - res.getMaintainPrice()
        );
        return JSON.writeValueAsString(res);
    }

    // 员工的人数
    private Integer getStaffNum(){
        // select count(0) from account where roleid is not null; -- 员工人数
        Example example = new Example(Account.class);
        example.createCriteria()
                .andIsNotNull("roleid");
        List<Account> staffs = accountMapper.selectByExample(example);
        return staffs.size();
    }

    // 员工的总工资
    private Float getStaffSalary(){
        // select sum(salary) from account where roleid is not null; -- 员工总工资
        return accountMapper.findSumSalary();
    }

    // 用户的人数
    private Integer getUserNum(){
        // select count(0) from account where roleid is null;
        Example example = new Example(Account.class);
        example.createCriteria()
                .andIsNull("roleid");
        List<Account> accounts = accountMapper.selectByExample(example);
        return accounts.size();
    }
    // 今日新增用户
    private Integer getTodayUserNum(){
        Example example = new Example(Account.class);
        example.createCriteria()
                .andEqualTo("createtime",DateUtil.date2String(new Date()));
        return accountMapper.selectCountByExample(example);
    }

    // 查询指定区间内，订单的数量

    // 查询指定区间内，签订合同的数量
}
