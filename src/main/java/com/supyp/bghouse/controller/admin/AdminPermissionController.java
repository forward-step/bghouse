package com.supyp.bghouse.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Authority;
import com.supyp.bghouse.domain.entity.Permission;
import com.supyp.bghouse.domain.entity.Role;
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
import java.util.List;

@RestController
@RequestMapping("/admin/permission")
public class AdminPermissionController {
    @Resource
    private PermissionService permissionService;
    @Resource
    private ObjectMapper JSON;

    @PostMapping("/add")
    public String add(Permission permission) throws JsonProcessingException {
        String msg = permissionService.validatePermission(permission);
        if(!StringUtil.isEmpty(msg)){
            return ResUtil.error(msg);
        }
        return ResUtil.empty2error(permissionService.add(permission));
    }

    @PostMapping("/edit")
    public String edit(Permission permission) throws JsonProcessingException {
        String msg = permissionService.validatePermission(permission);
        if(!StringUtil.isEmpty(msg)){
            return ResUtil.error(msg);
        }
        if(permission.getId() == null){
            return ResUtil.error("权限id不能为空");
        }
        return ResUtil.empty2error(permissionService.edit(permission),"出错了，请联系管理员");
    }

    @PostMapping("/delete")
    public String delete(Permission permission) throws JsonProcessingException {
        if(permission.getId() == null){
            return ResUtil.error("权限id不能为空");
        }
        return ResUtil.empty2error(permissionService.delete(permission),"出错了，请联系管理员");
    }

    @PostMapping("/findAll")
    public String findAll(PaginationDto paginationDto) throws JsonProcessingException {
        System.out.println(paginationDto);
        PageInfo<Permission> all = permissionService.findAll(paginationDto);
        return JSON.writeValueAsString(all);
    }
}
