package com.supyp.bghouse.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.configuration.Config;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.dto.RoleDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Role;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.services.RoleService;
import com.supyp.bghouse.utils.ResUtil;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/role")
public class AdminRoleController {

    @Resource
    private RoleService roleService;
    @Resource
    private AccountService accountService;
    @Resource
    private ObjectMapper JSON;

    @PostMapping("/add")
    public String add(Role role) throws JsonProcessingException {
        // 1.验证表单信息
        String msg = roleService.validateRole(role);
        if(!StringUtil.isEmpty(msg)){
            return ResUtil.error(msg);
        }
        // 2.添加角色
        return ResUtil.empty2error(roleService.add(role));
    };

    @PostMapping("/edit")
    public String edit(Role role) throws JsonProcessingException {
        // 1.验证表单信息
        String msg = roleService.validateRole(role);
        if(!StringUtil.isEmpty(msg)){
            return ResUtil.error(msg);
        }
        if(role.getId() == null){
            return ResUtil.error("角色id不能为空");
        }
        if(Config.SuperAdminRoleId.equals(role.getId())){
            return ResUtil.error("不能编辑超级管理员");
        }
        // 2.修改角色
        return ResUtil.empty2error(roleService.edit(role));
    }

    @PostMapping("/delete")
    public String delete(
            @RequestParam(name = "id",required = true) Integer roleId
    ) throws JsonProcessingException {
        // 1.验证表单信息
        if(roleId == null){
            return ResUtil.error("roleid不能为空");
        }
        if(Config.SuperAdminRoleId.equals(roleId)){
            return ResUtil.error("不能删除超级管理员");
        }
        // 2.删除角色
        return ResUtil.empty2error(roleService.delete(roleId));
    }

    @PostMapping("/findAll")
    public String findAll(PaginationDto paginationDto) throws JsonProcessingException {
        PageInfo<Role> all = roleService.findAll(paginationDto);
        ArrayList<RoleDto> res_list = new ArrayList<>();
        PageInfo<RoleDto> res = new PageInfo<>();

        // 拿出list进行处理
        for (Role role : all.getList()){
            // 根据角色查找所有用户
            Account temp = new Account();
            temp.setRoleid(role.getId());
            List<Account> accounts = accountService.findAll(temp);
            // 信息处理
            List<String> username = new ArrayList<>();
            for (Account account: accounts){
                username.add(account.getUsername()); // 用户名
            }
            // item
            RoleDto roleDto = new RoleDto();
            roleDto.setRole(role);
            roleDto.setAccounts(username);
            res_list.add(roleDto);
        }

        // copt pageinfo
        res.setList(res_list);
        res.setHasNextPage(all.isHasNextPage());
        res.setPageSize(all.getPageSize());
        res.setPageNum(all.getPageNum());
        res.setTotal(all.getTotal());

        return JSON.writeValueAsString(res);
    }

    @PostMapping("/findMyRoleName")
    public String findMyRoleName(Integer roleid) throws JsonProcessingException {
        // 1.验证
        if(roleid == null) return ResUtil.error("角色id不能为空");
        // 2.查询
        Role select = roleService.findByRoleId(roleid);
        return ResUtil.success(select.getRolename());
    }
}
