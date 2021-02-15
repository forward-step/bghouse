package com.supyp.bghouse.services.impl;

import com.supyp.bghouse.dao.AuthorityMapper;
import com.supyp.bghouse.domain.entity.Authority;
import com.supyp.bghouse.domain.entity.Role;
import com.supyp.bghouse.services.AuthorityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Resource
    private AuthorityMapper authorityMapper;

    @Override
    public List<Authority> findAuthorityByRoleId(int roleId) {
        Authority authority = new Authority();
        authority.setRoleid(roleId);
        return authorityMapper.select(authority);
    }

    @Override
    public List<Authority> findAuthorityByPermissionId(int permissionId) {
        Authority authority = new Authority();
        authority.setPermissionid(permissionId);
        return authorityMapper.select(authority);
    }

    @Override
    public int add(Authority authority) {
        if(authorityMapper.selectOne(authority) != null){
            return 1; // 已经存在，不用插入
        }else{
            return authorityMapper.insert(authority);
        }
    }

    @Override
    public int delete(Authority authority) {
        return authorityMapper.delete(authority);
    }

    @Override
    public String validateAuthority(Authority authority) {
        if(authority.getRoleid() == null){
            return "角色id不能为空";
        }
        if(authority.getPermissionid() == null){
            return "权限id不能为空";
        }
        return "";
    }
}
