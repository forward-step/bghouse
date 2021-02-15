package com.supyp.bghouse.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.ContractDto;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Contract;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.services.ContractService;
import com.supyp.bghouse.services.SubscribeService;
import com.supyp.bghouse.utils.DateUtil;
import com.supyp.bghouse.utils.ResUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/contract")
public class AdminContractController {
    @Resource
    private ContractService contractService;
    @Resource
    private SubscribeService subscribeService;
    @Resource
    private AccountService accountService;
    @Resource
    private ObjectMapper JSON;

    @PostMapping("/add")
    public String add(Contract contract) throws JsonProcessingException {
        // 1.验证表单信息
        String info = contractService.validateInfo(contract);
        if(!StringUtil.isEmpty(info)) return ResUtil.error(info);

        // 2.将预约订单status改为1
        if(contract.getId() != null){
            subscribeService.success(contract.getId()); // 这里借用id作为订单的id
        }
        contract.setId(null);

        // 2.添加表单信息
        return ResUtil.empty2error(contractService.add(contract));
    }

    // 退租 - 还要将房间设置为可以出租的状态
    @PostMapping("/exit")
    public String exit(Integer contractid) throws JsonProcessingException {
        if(contractid == null) return ResUtil.error("合同id不能为空");
        contractService.exit(contractid); // 退租动作

        Contract contract = contractService.findContractById(contractid);

        ContractDto dto = new ContractDto();
        dto.setContract(contract);
        int i = contract.getDurationtime() - DateUtil.getMonthDiff(new Date(), contract.getCheckintime());
        if(i > contract.getDurationtime()){
            dto.setRemaintime(contract.getDurationtime());
        }else{
            if(i < 0){
                dto.setRemaintime(0);
            }else{
                dto.setRemaintime(i);
            }
        }
        return ResUtil.success("");
    }

    // 续租
    @PostMapping("/relet")
    public String relet(Integer contractid,Integer month) throws JsonProcessingException {
        if(contractid == null) return ResUtil.error("合同id不能为空");
        if(month == null) return ResUtil.error("续租月份不能为空");
        return ResUtil.empty2error(contractService.relet(contractid,month));
    }

    @PostMapping("/findAll")
    public String findAll(PaginationDto paginationDto) throws JsonProcessingException {
        PageInfo<Contract> all = contractService.findAll(paginationDto,null);
        ArrayList<ContractDto> res_list = new ArrayList<>();
        PageInfo<ContractDto> res = new PageInfo<>();

        // 添加用户名、真实姓名
        // TODO() 对查询结果进行处理
        // 1.添加用户名
        for(Contract contract: all.getList()){
            // 1.查询账户
            Account temp = new Account();
            temp.setId(contract.getUserid());
            Account account = accountService.findAccountById(temp);
            // 2.添加用户名
            ContractDto contractDto = new ContractDto();
            contractDto.setUsername(account.getUsername()); // 用户名
            contractDto.setRealname(account.getRealname()); // 真实姓名
            contractDto.setContract(contract); // 合同信息
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

    // 查询用户的合同信息
    @PostMapping("/findUserContract")
    public String findAll(
            Integer userid
    ) throws JsonProcessingException {
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setCurrent(1);
        paginationDto.setPageSize(9999);
        // 根据创建时间查找
        // sorterArr=[{field=createtime, order=false}
        HashMap<String, String> sorterMap = new HashMap<>();
        sorterMap.put("field","createtime");
        sorterMap.put("order","descend");
        ArrayList<Map<String, String>> sorterArr = new ArrayList<>();
        sorterArr.add(sorterMap);
        paginationDto.setSorterArr(sorterArr);

        return JSON.writeValueAsString(contractService.findAll(paginationDto,userid));
    }
}
