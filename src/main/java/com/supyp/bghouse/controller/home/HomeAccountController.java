package com.supyp.bghouse.controller.home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supyp.bghouse.configuration.Config;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Captcha;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.services.CaptchaService;
import com.supyp.bghouse.utils.JWTUtil;
import com.supyp.bghouse.utils.ResUtil;
import com.supyp.bghouse.utils.ThreadLocalUtil;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.util.HashMap;

@RestController
@RequestMapping("/home/account")
public class HomeAccountController {
    @Resource
    private AccountService accountService;
    @Resource
    private ObjectMapper JSON; // JackJSON
    @Resource
    private CaptchaService captchaService;


    // 检查验证码是否正确
    private String validate(Account account,String ip,String captcha) throws JsonProcessingException {
        // 验证基本信息
        String baseInfo = accountService.validateBaseInfo(account);
        if(!"".equals(baseInfo)){
            return baseInfo;
        }
        // 验证验证码
        if("".equals(captcha)) return "验证码不能为空";
        Captcha select = captchaService.findByIP(ip);
        if(select != null && select.getCheckcode().equals(captcha)){
            return "";
        }else{
            return "验证码错误";
        }
    }

    @PostMapping("/register")
    public String register(
            HttpServletRequest request,
            Account account,
            String captcha
    ) throws JsonProcessingException {
        HashMap<String, String> res = new HashMap<>();
        // 验证基本信息
        // 验证码
        String validate = this.validate(account, request.getRemoteAddr(), captcha);
        if(!StringUtil.isEmpty(validate)) return ResUtil.error(validate);
        // 判断用户名是否已经存在
        if(accountService.isExistUsername(account)){
            return ResUtil.error("用户名已经存在");
        }
        // 插入数据到数据库
        if(accountService.add(account) <= 0){
            return ResUtil.error("出错了,请联系管理员");
        }else {
            res.put("type","success");
            String token = JWTUtil.encodeJWT(account);
            res.put("token",token);
            return JSON.writeValueAsString(res);
        }
    }

    @PostMapping("/login")
    public String login(
            HttpServletRequest request,
            Account account,String captcha
    ) throws JsonProcessingException {
        HashMap<String, String> res = new HashMap<>();
        // 验证基本信息
        // 验证码
        String validate = this.validate(account, request.getRemoteAddr(), captcha);
        if(!StringUtil.isEmpty(validate)) return ResUtil.error(validate);
        // 查找登录账户
        Account loginAccount = accountService.selectOne(account);
        if(loginAccount == null){
            return ResUtil.error("用户名或密码错误");
        }
        // 更新登录状态
        accountService.updateStatus(loginAccount.getId(),1);

        // 登录成功
        String token = JWTUtil.encodeJWT(account);// 获取token
        res.put("type","success");
        res.put("token",token);
        res.put("msg","");
        res.put("roleid", String.valueOf(loginAccount.getRoleid()));
        return JSON.writeValueAsString(res);
    }

    // 填写入住资料 、 修改入住资料
    @PostMapping("/editCheckInInfo")
    public String CheckInInfo(Account account) throws JsonProcessingException {
        // 1.获取登录账户
        Account loginAccount = ThreadLocalUtil.get();
        // 2.验证入住信息
        String msg = accountService.validateCheckInInfo(account);
        if(!StringUtil.isEmpty((msg))){
            return ResUtil.error(msg);
        }
        // 3.修改入住信息
        account.setId(loginAccount.getId());
        return ResUtil.empty2error(accountService.edit(account));
    }

    // 获取我的入住资料
    @PostMapping("/getCheckInInfo")
    public String getCheckInInfo() throws JsonProcessingException {
        // 1.获取登录账户
        Account loginAccount = ThreadLocalUtil.get();
        HashMap<String, String> res = new HashMap<>();
        res.put("realname",loginAccount.getRealname());
        res.put("idcard",loginAccount.getIdcard());
        res.put("phone",loginAccount.getPhone());
        return JSON.writeValueAsString(res);
    }
    // 修改密码
    @PostMapping("/editPwd")
    public String editPwd(String originpwd,String newpwd) throws JsonProcessingException {
        System.out.println(originpwd);
        System.out.println(newpwd);
        // 1.获取登录账户
        Account loginAccount = ThreadLocalUtil.get();
        // 2.验证原密码
        if(!loginAccount.getPwd().equals(originpwd)){
            return ResUtil.error("原密码与登录账户的密码不一致");
        }
        // 3.验证新密码 - 不能为空，格式正确
        if(StringUtil.isEmpty(newpwd)){
            return ResUtil.error("新密码不能为空");
        }
        if(!newpwd.matches(Config.pwdReg)){
            return ResUtil.error(Config.pwdError);
        }
        // 4.修改密码
        Account account = new Account();
        account.setId(loginAccount.getId());
        account.setPwd(newpwd);
        return ResUtil.empty2error(accountService.edit(account));
    }
}
