package com.supyp.bghouse.services;

import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.ContractDto;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Contract;

public interface ContractService {
    public String generateRemark(String status , int month); // 自动生成remark ; status表示续租还是退租
    public String validateInfo(Contract contract); // 验证表单信息

    // 根据合同id查找合同
    public Contract findContractById(Integer id);

    // 操作
    public int add(Contract contract); // 添加租约
    public int exit(Integer id); // 退租登记
    public int relet(Integer id,int month); // 续租登记

    // findAll
    public PageInfo<Contract> findAll(PaginationDto paginationDto,Integer userid); // 分页查找所有的角色
}
