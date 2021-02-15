package com.supyp.bghouse.controller.home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.dto.RoomDto;
import com.supyp.bghouse.domain.entity.Room;
import com.supyp.bghouse.services.RoomService;
import com.supyp.bghouse.utils.ResUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("/home/room")
public class HomeRoomController {
    @Resource
    private ObjectMapper JSON; // JackJSON
    @Resource
    private RoomService roomService;

    /*
    * 分页查找所有的房间信息
    * */
    @PostMapping("/findAll")
    public String showAll(
            RoomDto roomDto
    ) throws JsonProcessingException {
        System.out.println(roomDto);
        PageInfo<Room> all = roomService.findAll(roomDto);
        return JSON.writeValueAsString(all);
    }

    /*
    * 根据房间id查找某件房间
    * */
    @PostMapping("/{id}")
    public String showOne(
            @PathVariable(name = "id") int id
    ) throws JsonProcessingException {
        Room room = new Room();
        room.setId(id);
        room = roomService.find(room);
        if(room == null){
            return ResUtil.error("没有找到");
        }else{
            return ResUtil.success(JSON.writeValueAsString(room));
        }
    }
}
