package com.supyp.bghouse.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.configuration.Config;
import com.supyp.bghouse.dao.SubscribeMapper;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.dto.SubscribeDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Subscribe;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.services.SubscribeService;
import com.supyp.bghouse.utils.DateUtil;
import com.supyp.bghouse.utils.ResUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SubscribeServiceImpl implements SubscribeService {

    @Resource
    private SubscribeMapper subscribeMapper;

    @Resource
    private AccountService accountService;


    // 判断房间是否已经被预定
    public Boolean roomIsSubscribe(int roomid){
        Subscribe subscribe1 = new Subscribe();
        subscribe1.setRoomid(roomid);
        subscribe1.setStatus(Config.SubscribeStatusING); // 预定中
        List<Subscribe> select1 = subscribeMapper.select(subscribe1);
        return !select1.isEmpty();
    }

    @Override
    public Boolean roomIsCheckIn(int roomid) {
        Subscribe subscribe1 = new Subscribe();
        subscribe1.setRoomid(roomid);
        subscribe1.setStatus(Config.SubscribeStatusSuccess); // 入住中
        List<Subscribe> select1 = subscribeMapper.select(subscribe1);
        return !select1.isEmpty();
    }

    @Override
    public Subscribe findOne(int userid, int roomid,int status) {
        Subscribe subscribe = new Subscribe();
        subscribe.setUserid(userid);
        subscribe.setRoomid(roomid);
        subscribe.setStatus(status);
        return subscribeMapper.selectOne(subscribe);
    }

    @Override
    public List<Subscribe> findByExample(Example example) {
        return subscribeMapper.selectByExample(example);
    }

    @Override
    public int add(Subscribe subscribe) {
        return subscribeMapper.insertSelective(subscribe);
    }

    // 操作的公共操作
    private int operate(int subid,int status){
        Subscribe update = new Subscribe();
        update.setId(subid);
        update.setStatus(status);
        return subscribeMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public int cancel(int subid) {
        return operate(subid,Config.SubscribeStatusCancel); // 2
    }

    @Override
    public int miss(int subid) {
        return operate(subid,Config.SubscribeStatusMiss); // 3
    }

    @Override
    public int success(int subid) {
        return operate(subid,Config.SubscribeStatusSuccess); // 1
    }


    @Override
    public PageInfo<Subscribe> findAll(PaginationDto paginationDto,Integer userid) {
        // PaginationDto(current=1, pageSize=10, filters={checkintime=, createtime=, remark=, roomid=, status=0, username=}, sorterArr=[{field=createtime, order=false}])
        Example example = ResUtil.getExampleByPaginationDto(paginationDto, Subscribe.class);
        Example.Criteria criteria = example.createCriteria();
        // like
        Map<String, String> filters = paginationDto.getFilters();
        if(filters != null){
            String roomid = filters.get("roomid"); // 房间id
            String remark = filters.get("remark"); // 备注
            String checkintime = filters.get("checkintime"); // 预约入住时间
            String createtime = filters.get("createtime"); // 订单创建时间
            String status = filters.get("status"); // 状态
            String realname = filters.get("realname"); // 真实姓名
            // select * from subscribe where checkInTime > '2020-11-28'
            if (!StringUtil.isEmpty(realname)){
                // 根据用户名查找id
                List<Account> accounts = accountService.findLikeRealName(realname);
                // 设置查找条件
                if(!accounts.isEmpty()){
                    ArrayList<Integer> ids = new ArrayList<>();
                    for(Account account: accounts){
                        ids.add(account.getId());
                    }
                    criteria.andIn("userid",ids);
                }
            }
            if(!StringUtil.isEmpty(roomid)){
                criteria.andEqualTo("roomid",roomid);
            }
            if(!StringUtil.isEmpty(remark)){
                criteria.andLike("remark","%"+remark+"%");
            }
            if(!StringUtil.isEmpty(status)){
                criteria.andEqualTo("status",status);
            }
            if(!StringUtil.isEmpty(checkintime)){
                if("week".equals(checkintime)){
                    // 未来一周
                    criteria.andLessThanOrEqualTo("checkintime",DateUtil.getFetureDate(7))
                            .andGreaterThanOrEqualTo("checkintime",DateUtil.getFetureDate(0));
                }else{
                    // 今天、明天、后天
                    criteria.andEqualTo("checkintime",DateUtil.getFetureDate(Integer.valueOf(checkintime)));
                }
            }
            if(!StringUtil.isEmpty(createtime)){
                if("week".equals(createtime)){
                    // 过去一周: select * from subscribe where createTime < '2020-12-02' and createTime > '2020-11-25'
                    criteria.andGreaterThanOrEqualTo("createtime",DateUtil.getPassDate(7))
                            .andLessThanOrEqualTo("createtime",DateUtil.getPassDate(0));
                }else{
                    // 今天、昨天、前天: select * from subscribe where createTime like '2020-12-02%'
                    criteria.andLike("createtime",
                            DateUtil.getPassDate(Integer.valueOf(createtime))+"%"
                            );
                }
            }
        }

        // 查询具体用户 -- 用于home控制器
        if(userid != null){
            criteria.andEqualTo("userid",userid);
        }

        // 查询
        List<Subscribe> subscribes = subscribeMapper.selectByExample(example);
        return new PageInfo<>(subscribes);
    }

}
