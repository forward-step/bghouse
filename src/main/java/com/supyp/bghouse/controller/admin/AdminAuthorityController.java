package com.supyp.bghouse.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.configuration.Config;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Authority;
import com.supyp.bghouse.domain.entity.Permission;
import com.supyp.bghouse.services.AuthorityService;
import com.supyp.bghouse.services.PermissionService;
import com.supyp.bghouse.utils.ResUtil;
import com.supyp.bghouse.utils.ThreadLocalUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/authority")
public class AdminAuthorityController {

    @Resource
    private AuthorityService authorityService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private ObjectMapper JSON;

    @PostMapping("/add")
    public String add(
            @RequestParam(name="ids",required=true) String ids,
            @RequestParam(name="roleid",required=true) Integer roleid
    ) throws JsonProcessingException {
        System.out.println(roleid);
        System.out.println(ids);
        // 验证数据的合法性
        if(roleid == null) return ResUtil.error("角色id不能为空");
        // 一、无法更改超级管理员的权限
        if(Config.SuperAdminRoleId.equals(roleid)){
            return ResUtil.error("没有权限更新超级管理员的权限");
        }
        // 二、删除该角色下所有的权限
        Authority deleteAuthority = new Authority();
        deleteAuthority.setRoleid(roleid);
        authorityService.delete(deleteAuthority);

        // 三、增加选择的权限
        String[] idArr = ids.split(",");
        try{
            for (String permissionid : idArr){
                Authority authority = new Authority();
                authority.setRoleid(roleid);
                authority.setPermissionid(Integer.valueOf(permissionid));
                int add = authorityService.add(authority);
                if(add <= 0) return ResUtil.error("出错了,请联系管理员");
            }
        }catch (NumberFormatException e){}
        return ResUtil.success("");
    }

    @PostMapping("/findMyAuthority")
    public String findMyAuthority(Integer userid) throws JsonProcessingException {
        ArrayList<Integer> res = new ArrayList<>();
        // 根据用户id获取权限
        if(userid == null) return "";
        List<Authority> authorities = authorityService.findAuthorityByRoleId(userid);
        for(Authority authority: authorities){
            if(authority.getPermissionid() != null){
                res.add(authority.getPermissionid());
            }
        }
        return JSON.writeValueAsString(res);
    }

    // 找到我的授权菜单 -- 找到顶级菜单即可
    @PostMapping("/findTopList")
    public String findTopList() throws JsonProcessingException {
        Account loginAccount = ThreadLocalUtil.get();
        if(loginAccount.getRoleid() == null) return ResUtil.error("");

        // 1.找到所有的权限
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setCurrent(1);
        paginationDto.setPageSize(9999);
        PageInfo<Permission> pageInfo = permissionService.findAll(paginationDto);
        List<Permission> permissions = pageInfo.getList();

        // 2.找到我的授权
        List<Authority> authorities = authorityService.findAuthorityByRoleId(loginAccount.getRoleid());
        ArrayList<Integer> AuthorityIds = new ArrayList<>();
        for(Authority authority : authorities){
            AuthorityIds.add(authority.getPermissionid());
        }
        // 3.根据我的授权，拼接我的
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Permission> res = new ArrayList<>();
        // ids
        for(Permission permission : permissions){
            if(AuthorityIds.contains(permission.getId())){
                // 顶级权限
                if(permission.getParentid() == null){
                    ids.add(permission.getId());
                }
                // 二级权限
                else{
                    ids.add(permission.getParentid());
                }
            }
        }
        // res
        for(Permission permission : permissions){
            if(ids.contains(permission.getId())){
                res.add(permission);
            }
        }
        return JSON.writeValueAsString(res);
    }
}
