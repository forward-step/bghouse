package com.supyp.bghouse.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.configuration.Config;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.dto.StaffDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Role;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.services.RoleService;
import com.supyp.bghouse.utils.ResUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/admin/staff")
public class AdminStaffController {
    @Resource
    private ObjectMapper JSON;
    @Resource
    private RoleService roleService;
    @Resource
    private AccountService accountService;

    // 找到所有的员工
    @PostMapping("/findAll")
    public String findAll(PaginationDto paginationDto) throws JsonProcessingException {
        PageInfo<Account> all = accountService.findAll(paginationDto,true);
        PageInfo<StaffDto> res = new PageInfo<>();
        res.setPageNum(all.getPageNum());
        res.setTotal(all.getTotal());
        res.setPageSize(all.getPageSize());
        res.setHasNextPage(all.isHasNextPage());
        ArrayList<StaffDto> list = new ArrayList<>();
        for (Account account: all.getList()){
            StaffDto dto = new StaffDto();
            dto.setAccount(account);
            // 查找角色名
            Role role = roleService.findByRoleId(account.getRoleid());
            dto.setRolename(role.getRolename());
            list.add(dto);
        }
        res.setList(list);
        return JSON.writeValueAsString(res);
    }

    // 添加新员工
    @PostMapping("/add")
    public String add(
            Integer userid,
            Integer salary,
            Integer roleid
    ) throws JsonProcessingException {
        // 1.验证表单信息
        if(userid == null) return ResUtil.error("用户id不能为空");
        if(roleid == null) return ResUtil.error("角色id不能为空");
        if(salary == null) return ResUtil.error("工资不能为空");
        // 2.不能添加为超级管理员
        if(roleid.equals(Config.SuperAdminRoleId)) return ResUtil.error("无法赋予超级管理员");
        // 3.完善信息
        Account temp = new Account();
        temp.setId(userid);
        String verror = accountService.validateCheckInInfo(accountService.selectOne(temp));
        if(!StringUtil.isEmpty(verror)){
            return ResUtil.error(verror);
        }
        // 4.更新数据库
        Account account = new Account();
        account.setId(userid);
        account.setRoleid(roleid);
        account.setSalary(salary);
        return ResUtil.empty2error(accountService.edit(account),"用户ID或角色ID不存在");
    }

    // 编辑工资
    @PostMapping("/edit")
    public String edit(Account account) throws JsonProcessingException {
        // 1.验证表单
        if(account.getId() == null) return ResUtil.error("用户ID不能为空");
        if(account.getSalary() == null) return ResUtil.error("工资不能为空");
        // 2.不能修改超级管理员
        Account select = accountService.findAccountById(account);
        if(select.getRoleid().equals(Config.SuperAdminRoleId)){
            return ResUtil.error("无法操作超级管理员");
        }
        // 2.更新数据库
        Account temp = new Account();
        temp.setId(account.getId());
        temp.setSalary(account.getSalary());
        return ResUtil.empty2error(accountService.edit(temp));
    }

    // 解雇员工
    @PostMapping("/delete")
    public String delete(
        @RequestParam(name = "id",required = true) Integer userid
    ) throws JsonProcessingException {
        System.out.println(userid);
        // 2.不能修改超级管理员
        Account temp = new Account();
        temp.setId(userid);
        Account select = accountService.findAccountById(temp);
        if(select.getRoleid().equals(Config.SuperAdminRoleId)){
            return ResUtil.error("无法操作超级管理员");
        }
        // 将角色id设置为null
        return ResUtil.empty2error(accountService.fire(userid));
    }
}
