package com.supyp.bghouse.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.dto.RoleDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Role;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.services.RoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/roomer")
public class AdminRoomerController {
    @Resource
    private ObjectMapper JSON;
    @Resource
    private RoleService roleService;
    @Resource
    private AccountService accountService;

    // 找到所有的房客
    @PostMapping("/findAll")
    public String findAll(PaginationDto paginationDto) throws JsonProcessingException {
        PageInfo<Account> all = accountService.findAll(paginationDto,false);
        return JSON.writeValueAsString(all);
    }

    // 找到所有的角色
    @PostMapping("/findAllRole")
    public String findAllRole() throws JsonProcessingException {
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setCurrent(1);
        paginationDto.setPageSize(99999);
        PageInfo<Role> all = roleService.findAll(paginationDto);
        return JSON.writeValueAsString(all);
    }
}
