package com.supyp.bghouse.services.impl;

import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.dao.ContractMapper;
import com.supyp.bghouse.domain.dto.ContractDto;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Contract;
import com.supyp.bghouse.domain.entity.Subscribe;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.services.ContractService;
import com.supyp.bghouse.utils.DateUtil;
import com.supyp.bghouse.utils.ResUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ContractServiceImpl implements ContractService {
    @Resource
    private ContractMapper contractMapper;
    @Resource
    private AccountService accountService;

    @Override
    public String generateRemark(String status,int month) {
        String now = DateUtil.date2String(new Date());
        if("exit".equals(status)){
            // 退租
            return now + " 退租\n";
        }else if("relet".equals(status)){
            // 续租
            return now + " 续租" + month + "个月\n";
        }else{
            return now + "租房" + month + "个月\n";
        }
    }

    @Override
    public String validateInfo(Contract contract) {
        if(contract.getUserid() == null) return "用户id不能为空";
        if(contract.getRoomid() == null) return "房间id不能为空";
        if(contract.getCheckintime() == null) return "入住时间不能为空";
        if(contract.getDurationtime() == null) return "租约时长不能为空";
        return "";
    }

    @Override
    public Contract findContractById(Integer id) {
        Contract contract = new Contract();
        contract.setId(id);
        return contractMapper.selectByPrimaryKey(contract);
    }

    @Override
    public int add(Contract contract) {
        contract.setCreatetime(new Date()); // 签订时间
        contract.setRemark(this.generateRemark("",contract.getDurationtime()));
        return contractMapper.insertSelective(contract);
    }

    @Override
    public int exit(Integer id) {
        Contract select = this.findContractById(id);
        if(select == null) return -1;

        Contract contract = new Contract();
        contract.setId(id);
        contract.setRemark(select.getRemark() + this.generateRemark("exit",0));
        // 过去的月数
        int passMonth = DateUtil.getMonthDiff(new Date(), select.getCheckintime());
        if(passMonth < select.getDurationtime()){
            contract.setDurationtime(passMonth + 1); // 这个月就退租
        }
        return contractMapper.updateByPrimaryKeySelective(contract);
    }

    @Override
    public int relet(Integer id,int month) {
        Contract select = this.findContractById(id);
        if(select == null) return -1;

        Contract contract = new Contract();
        contract.setId(id);
        contract.setRemark(select.getRemark() + this.generateRemark("relet",month));
        contract.setDurationtime(select.getDurationtime() + month);
        return contractMapper.updateByPrimaryKeySelective(contract);
    }

    @Override
    public PageInfo<Contract> findAll(PaginationDto paginationDto,Integer userid) {
        Example example = ResUtil.getExampleByPaginationDto(paginationDto, Contract.class);
        Example.Criteria criteria = example.createCriteria();
        // like
        Map<String, String> filters = paginationDto.getFilters();
        if(filters != null){
            String roomid = filters.get("roomid"); // 房间id
            String username = filters.get("username"); // 用户名
            String realname = filters.get("realname"); // 用户名
            if(!StringUtil.isEmpty(roomid)){
                criteria.andEqualTo("roomid",roomid);
            }
            if (!StringUtil.isEmpty(username)){
                // 根据用户名查找id
                List<Account> accounts = accountService.findLikeUsername(username);
                // 设置查找条件
                if(!accounts.isEmpty()){
                    ArrayList<Integer> ids = new ArrayList<>();
                    for(Account account: accounts){
                        ids.add(account.getId());
                    }
                    criteria.andIn("userid",ids);
                }
            }
            if(!StringUtil.isEmpty(realname)){
                List<Account> accounts = accountService.findLikeRealName(username);
                // 设置查找条件
                if(!accounts.isEmpty()){
                    ArrayList<Integer> ids = new ArrayList<>();
                    for(Account account: accounts){
                        ids.add(account.getId());
                    }
                    criteria.andIn("userid",ids);
                }
            }
        }

        // 查询具体用户 -- 用于home控制器
        if(userid != null){
            criteria.andEqualTo("userid",userid);
        }
        // 查询
        List<Contract> contracts = contractMapper.selectByExample(example);
        return new PageInfo<>(contracts);
    }
}
