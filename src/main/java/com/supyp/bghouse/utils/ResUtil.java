package com.supyp.bghouse.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.supyp.bghouse.domain.dto.PaginationDto;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 返回信息的处理
* */
@Component
public class ResUtil {
    @Resource
    public void setJSON(ObjectMapper objectMapper){
        ResUtil.JSON = objectMapper;
    }
    private static ObjectMapper JSON; // JackJSON

    public static String error(String msg) throws JsonProcessingException {
        HashMap<String, String> res = new HashMap<>();
        res.put("type","error");
        res.put("msg",msg);
        return JSON.writeValueAsString(res);
    }
    public static String success(String msg) throws JsonProcessingException {
        HashMap<String, String> res = new HashMap<>();
        res.put("type","success");
        res.put("msg",msg);
        return JSON.writeValueAsString(res);
    }
    // msg为empty就返回success ; 否则返回error
    public static String empty2error(String msg) throws JsonProcessingException {
        if(StringUtil.isEmpty(msg)){
            return ResUtil.error(msg);
        }else{
            return ResUtil.success(msg);
        }
    }
    // 如果code <= 0，返回error
    // 如果code > 0 , 返回msg = code
    public static String empty2error(Integer code,String msg) throws JsonProcessingException {
        if(code == null || code <= 0){
            return ResUtil.error(msg);
        }else{
            return ResUtil.success(String.valueOf(code));
        }
    }
    public static String empty2error(Integer code) throws JsonProcessingException {
        return ResUtil.empty2error(code,"出错了,请联系管理员");
    }

    // 处理分页信息
    public static Example getExampleByPaginationDto(PaginationDto paginationDto,Class cls){
        // 1.分页
        PageHelper.startPage(paginationDto.getCurrent(),paginationDto.getPageSize());
        // 2.构建example
        Example example = new Example(cls);
        // 排序
        List<Map<String, String>> sorterArr = paginationDto.getSorterArr();
        try{
            if(sorterArr != null){
                for (Map<String,String> item : sorterArr){
                    if("ascend".equals(item.get("order"))){
                        example.orderBy(item.get("field")).asc();
                    }else if("descend".equals(item.get("order"))){
                        example.orderBy(item.get("field")).desc();
                    }
                }
            }
        }catch(Exception e){}
        return example;
    }
}
