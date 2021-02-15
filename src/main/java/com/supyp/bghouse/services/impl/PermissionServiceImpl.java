package com.supyp.bghouse.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.configuration.Config;
import com.supyp.bghouse.dao.AuthorityMapper;
import com.supyp.bghouse.dao.PermissionMapper;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Authority;
import com.supyp.bghouse.domain.entity.Permission;
import com.supyp.bghouse.services.PermissionService;
import com.supyp.bghouse.utils.ResUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper; // 权限表
    @Resource
    private AuthorityMapper authorityMapper;

    @Override
    public Permission findOone(int permissionId) {
        Permission permission = new Permission();
        permission.setId(permissionId);
        return permissionMapper.selectOne(permission);
    }

    @Override
    public List<Permission> findchild(int permissionId) {
        Permission temp = new Permission();
        temp.setParentid(permissionId);
        return permissionMapper.select(temp);
    }

    @Override
    public List<Permission> findAllByUrl(String url) {
        ArrayList<Permission> res = new ArrayList<>();
        Permission permission = new Permission();
        permission.setUrl(url);
        // 1.根据URL查询权限id
        Permission selectOne = permissionMapper.selectOne(permission);
        if(selectOne == null){ // 如果根据URL查询不到
            return res;
        }
        res.add(selectOne);
        // 2.遍历所有的父级权限
        while (selectOne.getParentid() != null){
            Permission temp = new Permission();
            temp.setId(selectOne.getParentid()); // 父级权限的id
            selectOne = permissionMapper.selectByPrimaryKey(temp);
            if(selectOne != null){
                res.add(selectOne);
            }else{
                break;
            }
        }
        return res;
    }

    @Override
    public String validatePermission(Permission permission) {
        if(StringUtil.isEmpty(permission.getName())){
            return "权限名称不能为空";
        }
        if(StringUtil.isEmpty(permission.getUrl())){
            return "URL不能为空";
        }
        // 判断URL是否重复
        Permission temp1 = new Permission();
        temp1.setUrl(permission.getUrl());
        Permission select1 = permissionMapper.selectOne(temp1);
        if(select1 != null && !select1.getId().equals(permission.getId())){
            return "URL不能重复";
        }
        // 判断权限名称是否重复
        Permission temp2 = new Permission();
        temp2.setName(permission.getName());
        Permission select2 = permissionMapper.selectOne(temp2);
        if(select2 != null && !select2.getId().equals(permission.getId())){
            return "权限名称不能重复";
        }
        return "";
    }

    @Override
    public int add(Permission permission) {
        int add = permissionMapper.insertSelective(permission);
        if(add <= 0) return -1;
        Integer id = permission.getId();// 返回自增id

        /*
         * TODO(): 添加顶级权限的时候，自动给超级管理员授权
         * */
        if(permission.getParentid() == null){
            // 表示为顶级权限，授权给超级管理员
            Authority authority = new Authority();
            authority.setRoleid(Config.SuperAdminRoleId); // 超级管理员
            authority.setPermissionid(id); // 顶级权限
            int code = authorityMapper.insert(authority);
            if(code <= 0){
                return -1;
            }
        }

        return id;
    }

    @Override
    public int edit(Permission permission) {
        return permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public int delete(Permission permission) {
        /*
        * TODO() 如果是顶级权限，同时删除子权限
        * */
        // 如果是顶级权限
        if(permission.getParentid() == null){
            // 查找所有子权限
            Permission temp = new Permission();
            temp.setParentid(permission.getId()); // 设置父级id
            List<Permission> select = permissionMapper.select(temp); // 找到所有子级权限
            for(Permission item:select){
                if(permissionMapper.deleteByPrimaryKey(item) <= 0) return -1;
            }
        }
        // 删除权限
        return permissionMapper.deleteByPrimaryKey(permission);
    }

    @Override
    public PageInfo<Permission> findAll(PaginationDto paginationDto) {
        // eg. PaginationDto(current=1, pageSize=10, filters={remark=, rolename=}, sorterArr=[{field=rolename, order=ascend}])
        Example example = ResUtil.getExampleByPaginationDto(paginationDto, Permission.class);
        Example.Criteria criteria = example.createCriteria();
        // like
        Map<String, String> filters = paginationDto.getFilters();
        if(filters != null){
            String name = filters.get("name"); // 根据权限名查找
            if(!StringUtil.isEmpty(name)){
                criteria.andLike("name","%" + name + "%");
            }
            String url = filters.get("url"); // 根据URL查找
            if(!StringUtil.isEmpty(url)){
                criteria.andLike("url","%" + url + "%");
            }
        }
        // 查询
        List<Permission> roles = permissionMapper.selectByExample(example);
        return new PageInfo<>(roles);
    }
}
