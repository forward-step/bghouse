package com.supyp.bghouse.controller.home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.ContractDto;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Contract;
import com.supyp.bghouse.services.ContractService;
import com.supyp.bghouse.utils.DateUtil;
import com.supyp.bghouse.utils.ThreadLocalUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/home/contract")
public class HomeContractController {
    @Resource
    private ContractService contractService;
    @Resource
    private ObjectMapper JSON;

    @PostMapping("/findAll")
    public String findAll(PaginationDto paginationDto) throws JsonProcessingException {
        Account loginAccount = ThreadLocalUtil.get();
        PageInfo<Contract> all = contractService.findAll(paginationDto,loginAccount.getId());
        ArrayList<ContractDto> res_list = new ArrayList<>();
        PageInfo<ContractDto> res = new PageInfo<>();

        for(Contract contract: all.getList()){
            ContractDto contractDto = new ContractDto();
            // 3.计算剩余时间 - 入住时间可能比今天要大
            int i = contract.getDurationtime() - DateUtil.getMonthDiff(new Date(), contract.getCheckintime());
            if(i > contract.getDurationtime()){
                contractDto.setRemaintime(contract.getDurationtime());
            }else{
                if(i < 0){
                    contractDto.setRemaintime(0);
                }else{
                    contractDto.setRemaintime(i);
                }
            }
            contractDto.setContract(contract);
            res_list.add(contractDto);
        }

        // copt pageinfo
        res.setList(res_list);
        res.setHasNextPage(all.isHasNextPage());
        res.setPageSize(all.getPageSize());
        res.setPageNum(all.getPageNum());
        res.setTotal(all.getTotal());

        return JSON.writeValueAsString(res);
    }

}
