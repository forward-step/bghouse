package com.supyp.bghouse.services;

import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.dto.RoomDto;
import com.supyp.bghouse.domain.entity.Room;

public interface RoomService {
    // handle 房间服务
    public PageInfo<Room> findAll(PaginationDto paginationDto); // 分页查找房间
    public PageInfo<Room> findAll(RoomDto roomDto); // 分页查找房间
    public Room find(Room room); // 具体查找

    // 增删改查
    public int add(Room room);
    public int edit(Room room);
    public int delete(Integer roomId);
    public String validateRoom(Room room); // 验证表单信息
}
