package com.supyp.bghouse.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Room;
import com.supyp.bghouse.services.RoomService;
import com.supyp.bghouse.utils.ResUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/room")
public class AdminRoomController {
    @Resource
    private RoomService roomService;
    @Resource
    private ObjectMapper JSON;


    // 添加新的房间
    @RequestMapping("/add")
    public String Upload(
            HttpServletRequest request,
            @RequestParam("upload") MultipartFile upload,
            Room room
    ) throws Exception {
        // 验证是否由上传图片
        if(upload.isEmpty()){
            return ResUtil.error("图片不能为空");
        }
        // 验证房间信息
        String msg = roomService.validateRoom(room);
        if(!StringUtil.isEmpty(msg)){
            return ResUtil.error(msg);
        }

        // 一、实现文件上传
        String path = request.getServletContext().getRealPath("/images/");
        String filename = this.UploadFile(path,upload);

        // 二、插入数据库
        room.setUrl(filename); // 设置URL
        room.setIssell(0); // 还没有出租

        return ResUtil.empty2error(roomService.add(room));
    }

    @PostMapping("/findAll")
    public String findAll(PaginationDto paginationDto) throws JsonProcessingException {
        PageInfo<Room> all = roomService.findAll(paginationDto);
        return JSON.writeValueAsString(all);
    }

    @PostMapping("/edit")
    public String edit(
            HttpServletRequest request,
            @RequestParam(name = "upload",required = false) MultipartFile upload,
            Room room
    ) throws IOException {
        // 验证房间信息
        String msg = roomService.validateRoom(room);
        if(!StringUtil.isEmpty(msg)){
            return ResUtil.error(msg);
        }

        // eg. Room(id=4, price=500.0, housetype=单间1卫, area=50, dir=东南, tags=配套齐全/精装修/拎包入住, name=平阁楼, issell=null, url=null)
        // 一、根据id查找房间对象
        Room findByid = new Room();
        findByid.setId(room.getId());
        Room select = roomService.find(findByid);
        room.setIssell(select.getIssell()); // 出否出租
        // 二、得到URL
        String url = "";
        if(upload == null){ // 没有上传新文件
            url = select.getUrl();
        }else{ // 上传新文件
            String path = request.getServletContext().getRealPath("/images/");
            url= this.UploadFile(path,upload);
        }
        room.setUrl(url);
        // 三、更新数据库
        return ResUtil.empty2error(roomService.edit(room));
    }

    @PostMapping("/delete")
    public String delete(
        @RequestParam(name = "id") Integer roomId
    ) throws JsonProcessingException {
        // 1.验证表单信息
        if(roomId == null) return ResUtil.error("roomId不能为空");
        // 2.删除房间
        return ResUtil.empty2error(roomService.delete(roomId));
    }

    // 处理上传文件的逻辑
    private String UploadFile(String path,MultipartFile upload) throws IOException {
        String filename = UUID.randomUUID().toString().replace("-", "");
        // 完成文件上传
        upload.transferTo(new File(path,filename+".jpg"));
        return filename;
    }
}
