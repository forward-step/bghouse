package com.supyp.bghouse.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.dao.RoleMapper;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.dto.RoleDto;
import com.supyp.bghouse.domain.entity.Role;
import com.supyp.bghouse.services.RoleService;
import com.supyp.bghouse.utils.ResUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public String validateRole(Role role) {
        if(StringUtil.isEmpty(role.getRolename())){
            return "角色名称不能为空";
        }
        if(StringUtil.isEmpty(role.getRemark())){
            return "角色描述不能为空";
        }
        // 角色名称不能重复
        Role temp = new Role();
        temp.setRolename(role.getRolename());
        Role select1 = roleMapper.selectOne(temp);
        if(select1 != null && !select1.getId().equals(role.getId())){
            return "角色名不能重复";
        }
        return "";
    }

    @Override
    public PageInfo<Role> findAll(PaginationDto paginationDto) {
        // eg. PaginationDto(current=1, pageSize=10, filters={remark=, rolename=}, sorterArr=[{field=rolename, order=ascend}])
        Example example = ResUtil.getExampleByPaginationDto(paginationDto, Role.class);
        Example.Criteria criteria = example.createCriteria();
        // like
        Map<String, String> filters = paginationDto.getFilters();
        if(filters != null){
            String rolename = filters.get("rolename");
            String remark = filters.get("remark");
            if(!StringUtil.isEmpty(remark)){
                criteria.andLike("remark","%" + remark + "%");
            }
            if(!StringUtil.isEmpty(rolename)){
                criteria.andLike("rolename","%" + rolename + "%");
            }
        }
        // 查询
        List<Role> roles = roleMapper.selectByExample(example);
        return new PageInfo<>(roles);
    }

    @Override
    public Role findByRoleId(Integer roleid) {
        return roleMapper.selectByPrimaryKey(roleid);
    }

    @Override
    public int add(Role role) {
        int add = roleMapper.insertSelective(role);
        if(add <= 0){return -1;}
        return role.getId();
    }

    @Override
    public int edit(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int delete(Integer roleId) {
        Role role = new Role();
        role.setId(roleId);
        return roleMapper.deleteByPrimaryKey(role);
    }
}
