package com.supyp.bghouse.services;

import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.dto.RoleDto;
import com.supyp.bghouse.domain.entity.Role;

public interface RoleService {
    // handle
    public String validateRole(Role role); // 检查角色表单是否有错
    public int add(Role role); // 添加一个新的角色
    public int edit(Role role); // 修改角色
    public int delete(Integer roleId); // 删除角色

    // findAll
    public PageInfo<Role> findAll(PaginationDto paginationDto); // 分页查找所有的角色
    // findone
    public Role findByRoleId(Integer roleid);
}
