package com.supyp.bghouse.services;

import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Account;

import java.util.List;

public interface AccountService {
    // 增删改查
    public int add(Account account);
    public Account findAccountById(Account account); // 根据id查找账户
    // handle 用户操作
    public Account selectOne(Account account);
    public int updateStatus(int userid,int status); // 更新登录状态
    public Boolean isExistUsername(Account account); // 判断用户名是否存在
    public String validateBaseInfo(Account account); // 验证用户信息的合法性(username,pwd)
    public String validateCheckInInfo(Account account); // 验证入住信息的合法性

    // edit
    public int edit(Account account);
    public int fire(Integer userid); // 解雇

    // findAll
    public PageInfo<Account> findAll(PaginationDto paginationDto,Boolean isStaff);

    public List<Account> findAll(Account account);

    // 模糊查询用户名
    public List<Account> findLikeUsername(String username);
    public List<Account> findLikeRealName(String realname);
}
