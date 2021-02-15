package com.supyp.bghouse.services;

import com.supyp.bghouse.domain.entity.Authority;

import java.util.List;

public interface AuthorityService {
    // handle
    public List<Authority> findAuthorityByRoleId(int roleId); // 通过角色id找到授权对象
    public List<Authority> findAuthorityByPermissionId(int permissionId); // 通过权限id找到授权对象
    public int add(Authority authority); // 添加授权
    public int delete(Authority authority); // 解除授权
    public String validateAuthority(Authority authority); // 验证授权表单
}
