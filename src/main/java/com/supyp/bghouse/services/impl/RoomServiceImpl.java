package com.supyp.bghouse.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.dao.RoomMapper;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.dto.RoomDto;
import com.supyp.bghouse.domain.entity.Room;
import com.supyp.bghouse.services.RoomService;
import com.supyp.bghouse.utils.ResUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RoomServiceImpl implements RoomService {

    @Resource
    private RoomMapper roomMapper;

    @Override
    public PageInfo<Room> findAll(PaginationDto paginationDto) {
        // eg. PaginationDto(current=1, pageSize=10, filters={remark=, rolename=}, sorterArr=[{field=rolename, order=ascend}])
        Example example = ResUtil.getExampleByPaginationDto(paginationDto, Room.class);
        Example.Criteria criteria = example.createCriteria();
        // like
        Map<String, String> filters = paginationDto.getFilters();
        if(filters != null){
            String price = filters.get("price");
            if(!StringUtil.isEmpty(price)){
                criteria.andLike("price","%" + price + "%");
            }
            String area = filters.get("area");
            if(!StringUtil.isEmpty(area)){
                criteria.andLike("area","%" + area + "%");
            }
            String dir = filters.get("dir");
            if(!StringUtil.isEmpty(dir)){
                criteria.andLike("dir","%" + dir + "%");
            }
            String issell = filters.get("issell");
            if(!StringUtil.isEmpty(issell)){
                criteria.andLike("issell","%" + issell + "%");
            }
            String name = filters.get("name");
            if(!StringUtil.isEmpty(name)){
                criteria.andLike("name","%" + name + "%");
            }
            String housetype = filters.get("housetype");
            if(!StringUtil.isEmpty(housetype)){
                criteria.andLike("housetype","%" + housetype + "%");
            }
        }
        // ??????
        List<Room> roles = roomMapper.selectByExample(example);
        return new PageInfo<>(roles);
    }

    @Override
    public PageInfo<Room> findAll(RoomDto roomDto) {
        // eg. RoomDto(current=1, pageSize=10, price=null, area=null, dir=null, searchText=null)
        // 1.??????
        PageHelper.startPage(roomDto.getCurrent(),roomDto.getPageSize());
        // 2.??????example
        Example example = new Example(Room.class);
        Example.Criteria criteria = example.createCriteria();
        // ????????? issell = 0 ?????????
        criteria.andEqualTo("issell",0);
        // ??????
        Map<String,String> price = roomDto.getPrice();
        if(price != null){
            // ?????? - ?????????
            // ??????
            String min = price.get("min");
            String max = price.get("max");
            if(min != null && max != null){
                criteria.andLessThanOrEqualTo("price",max)
                        .andGreaterThanOrEqualTo("price",min);
            }
            // xxx??????
            else if(min != null && max == null){
                criteria.andGreaterThanOrEqualTo("price",min);
            }
            /// xxx??????
            else if(min == null && max != null){
                criteria.andLessThanOrEqualTo("price",max);
            }
        }
        // dir
        String dir = roomDto.getDir();
        if(!StringUtil.isEmpty(dir)){
            criteria.andLike("dir",dir);
        }
        // ??????
        Map<String,String> area = roomDto.getArea();
        if(area != null){
            // ?????? - ?????????
            // ??????
            String min = area.get("min");
            String max = area.get("max");
            if(min != null && max != null){
                criteria.andLessThanOrEqualTo("area",max)
                        .andGreaterThanOrEqualTo("area",min);
            }
            // xxx??????
            else if(min != null && max == null){
                criteria.andGreaterThanOrEqualTo("area",min);
            }
            /// xxx??????
            else if(min == null && max != null){
                criteria.andLessThanOrEqualTo("area",max);
            }
        }

        // ????????????

        // 3.????????????
        List<Room> rooms = roomMapper.selectByExample(example);
        return new PageInfo<>(rooms);
    }

    @Override
    public Room find(Room room) {
        List<Room> list = roomMapper.select(room);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @Override
    public int add(Room room) {
        return roomMapper.insertSelective(room);
    }

    @Override
    public int edit(Room room) {
        return roomMapper.updateByPrimaryKeySelective(room);
    }

    @Override
    public int delete(Integer roomId) {
        Room room = new Room();
        room.setId(roomId);
        return roomMapper.deleteByPrimaryKey(room);
    }

    @Override
    public String validateRoom(Room room) {
        if(room.getPrice() == null){
            return "????????????????????????";
        }
        if(StringUtil.isEmpty(room.getHousetype())){
            return "??????????????????";
        }
        if(room.getArea() == null){
            return "??????????????????";
        }
        if(room.getDir() == null){
            return "??????????????????";
        }
        if(StringUtil.isEmpty(room.getTags())){
            return "????????????????????????";
        }
        return "";
    }
}
