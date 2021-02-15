package com.supyp.bghouse.services.impl;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.configuration.Config;
import com.supyp.bghouse.dao.AccountMapper;
import com.supyp.bghouse.dao.UserunreadMapper;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Userunread;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.utils.ResUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

// 用户的注册与提交
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper; // 用户表
    @Resource
    private UserunreadMapper userunreadMapper; // 未读信息

    // 根据token 解析出来的token查找用户id
    private int findIdByToken(Account token_account){
        int id = 0;
        if(token_account.getId() == null){
            List<Account> list = accountMapper.select(token_account);
            if(!list.isEmpty()){
                id = list.get(0).getId();
            }
        }else{
            id = token_account.getId();
        }
        return id;
    }

    @Override
    public int add(Account account) {
        account.setCreatetime(new Date()); // 注册时间
        int userid = accountMapper.insert(account);
        if(userid > 0){
            // 插入未读信息条数
            Userunread userunread = new Userunread();
            userunread.setUserid(userid);
            userunread.setUnread(0);
            return userunreadMapper.insert(userunread);
        }else {
            return -1;
        }
    }

    @Override
    public Account findAccountById(Account account) {
        int id = this.findIdByToken(account);
        account.setId(id);
        return accountMapper.selectByPrimaryKey(account);
    }

    @Override
    public Account selectOne(Account account) {
        return accountMapper.selectOne(account);
    }

    @Override
    public int updateStatus(int userid,int status) {
        Account temp = new Account();
        temp.setId(userid); // 用户id
        temp.setStatus(status); // 登录状态
        return accountMapper.updateByPrimaryKeySelective(temp);
    }

    @Override
    public Boolean isExistUsername(Account account) {
        // 判断用户名是否已经存在
        Account tempAccount = new Account();
        tempAccount.setUsername(account.getUsername());
        // unll -> false ;
        return accountMapper.selectOne(tempAccount) != null;
    }

    @Override
    public int edit(Account account) {
        return accountMapper.updateByPrimaryKeySelective(account);
    }

    @Override
    public int fire(Integer userid) {
        Account account = new Account();
        account.setId(userid);
        Account select = accountMapper.selectByPrimaryKey(account);
        select.setRoleid(null);
        return accountMapper.updateByPrimaryKey(select);
    }

    @Override
    public String validateBaseInfo(Account account) {
        if(StringUtil.isEmpty(account.getUsername())){
            return "用户名不能为空！";
        }
        if(!account.getUsername().matches(Config.usernameReg)){
            return Config.usernameError;
        }
        if(StringUtil.isEmpty(account.getPwd())){
            return "密码不能为空！";
        }
        if(!account.getPwd().matches(Config.pwdReg)){
            return Config.pwdError;
        }
        return "";
    }

    @Override
    public String validateCheckInInfo(Account account) {
        if(StringUtil.isEmpty(account.getPhone())){
            return "联系电话不能为空";
        }
        else if(!account.getPhone().matches(Config.phoneReg)){
            return Config.phoneError;
        }
        if(StringUtil.isEmpty(account.getIdcard())){
            return "身份证号码不能为空";
        }
        else if(!account.getIdcard().matches(Config.idCardReg)){
            return Config.idCardError;
        }
        if(StringUtil.isEmpty(account.getRealname())){
            return "真实姓名不能为空";
        }
        else if(!account.getRealname().matches(Config.realnameReg)){
            return Config.realnameError;
        }
        return "";
    }

    @Override
    public PageInfo<Account> findAll(PaginationDto paginationDto,Boolean isStaff) {
        Example example = ResUtil.getExampleByPaginationDto(paginationDto, Account.class);
        Example.Criteria criteria = example.createCriteria();
        // like
        // 查询
        if(isStaff){
            criteria.andIsNotNull("roleid");
        }else{
            criteria.andIsNull("roleid");
        }
        List<Account> accounts = accountMapper.selectByExample(example);
        return new PageInfo<>(accounts);
    }

    @Override
    public List<Account> findAll(Account account) {
        return accountMapper.select(account);
    }

    @Override
    public List<Account> findLikeUsername(String username) {
        Example example = new Example(Account.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("username","%"+username+"%");

        return accountMapper.selectByExample(example);
    }

    @Override
    public List<Account> findLikeRealName(String realname) {
        Example example = new Example(Account.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("realname","%"+realname+"%");
        return accountMapper.selectByExample(example);
    }
}
