package com.supyp.bghouse.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.dao.MaintainMapper;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Maintain;
import com.supyp.bghouse.services.MaintainService;
import com.supyp.bghouse.utils.ResUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MaintainServiceImpl implements MaintainService {
    @Resource
    private MaintainMapper maintainMapper;
    @Override
    public int add(Maintain maintain) {
        maintain.setStarttime(new Date()); // 维修开始时间
        maintain.setIsend(0); // 没有结束
        return maintainMapper.insertSelective(maintain);
    }

    @Override
    public int complete(Maintain maintain) {
        maintain.setIsend(1); // 维修结束
        maintain.setEndtime(new Date()); // 维修结束时间
        return maintainMapper.updateByPrimaryKeySelective(maintain);
    }

    @Override
    public String validateBaseInfo(Maintain maintain) {
        if(maintain.getRoomid() == null){
            return "房间id不能为空";
        }
        if(StringUtil.isEmpty(maintain.getRemark())){
            return "备注不能为空";
        }
        // 开始时间就是填表时间
        return "";
    }

    @Override
    public String validateCompleteInfo(Maintain maintain) {
        if(maintain.getId() == null){
            return "维修id不能为空";
        }
        if(maintain.getPrice() == null){
            return "维修价格不能为空";
        }
        if(StringUtil.isEmpty(maintain.getRemark())){
            return "备注不能为空";
        }
        return "";
    }

    @Override
    public PageInfo<Maintain> findAll(PaginationDto paginationDto) {
        // eg. PaginationDto(current=1, pageSize=10, filters={isend=1}, sorterArr=null)
        Example example = ResUtil.getExampleByPaginationDto(paginationDto, Maintain.class);
        Example.Criteria criteria = example.createCriteria();
        // like
        Map<String, String> filters = paginationDto.getFilters();
        if(filters != null){
            String isend = filters.get("isend");
            String roomid = filters.get("roomid");
            if(!StringUtil.isEmpty(isend)){
                criteria.andEqualTo("isend",isend);
            }
            if(!StringUtil.isEmpty(roomid)){
                criteria.andEqualTo("roomid",roomid);
            }
        }

        // 查询
        List<Maintain> maintains = maintainMapper.selectByExample(example);
        return new PageInfo<>(maintains);
    }
}
