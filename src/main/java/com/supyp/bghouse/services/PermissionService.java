package com.supyp.bghouse.services;

import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Permission;

import java.util.List;

// Permission
// PermissionInterceptor
public interface PermissionService {
    // handle
    public Permission findOone(int permissionId); // 根据权限id查找权限
    public List<Permission> findchild(int permissionId); // 根据权限id查找权限
    public List<Permission> findAllByUrl(String url); // 根据URL查找所有的权限
    public String validatePermission(Permission permission); // 验证权限表单
    public int add(Permission permission); // 添加权限
    public int edit(Permission permission); // 修改权限
    public int delete(Permission permission); // 删除权限

    // 查找
    public PageInfo<Permission> findAll(PaginationDto paginationDto); // 分页查找所有的角色

}
