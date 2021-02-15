package com.supyp.bghouse.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Maintain;
import com.supyp.bghouse.services.MaintainService;
import com.supyp.bghouse.utils.ResUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/maintain")
public class AdminMaintainController {

    @Resource
    private MaintainService maintainService;
    @Resource
    private ObjectMapper JSON;

    @PostMapping("/findAll")
    public String findAll(PaginationDto paginationDto) throws JsonProcessingException {
        System.out.println(paginationDto);
        PageInfo<Maintain> all = maintainService.findAll(paginationDto);
        // TODO() 对查询结果进行处理
        return JSON.writeValueAsString(all);
    }

    // 添加一个维修信息
    @PostMapping("/add")
    public String add(Maintain maintain) throws JsonProcessingException {
        // 1.验证表单信息
        String baseInfo = maintainService.validateBaseInfo(maintain);
        if(!StringUtil.isEmpty(baseInfo)){
            return ResUtil.error(baseInfo);
        }
        // 2.添加表单信息
        return ResUtil.empty2error(maintainService.add(maintain));
    }

    // 继续完成剩下的表单信息 - 一般结束时登记
    @PostMapping("/edit")
    public String complete(Maintain maintain) throws JsonProcessingException {
        // 1.验证表单信息
        String completeInfo = maintainService.validateCompleteInfo(maintain);
        if(!StringUtil.isEmpty(completeInfo)){
            return ResUtil.error(completeInfo);
        }
        // 2.更新表单信息
        return ResUtil.empty2error(maintainService.complete(maintain));
    }
}
