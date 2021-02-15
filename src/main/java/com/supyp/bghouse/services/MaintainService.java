package com.supyp.bghouse.services;

import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Maintain;

public interface MaintainService {
    // handle
    public int add(Maintain maintain); // 添加一个维修信息
    public int complete(Maintain maintain); // 该维修已经完成
    // 验证表单信息
    public String validateBaseInfo(Maintain maintain); // 基本信息
    public String validateCompleteInfo(Maintain maintain); // 完后信息

    // findAll
    public PageInfo<Maintain> findAll(PaginationDto paginationDto);
}
