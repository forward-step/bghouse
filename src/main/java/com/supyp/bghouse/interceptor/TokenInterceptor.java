package com.supyp.bghouse.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.services.AccountService;
import com.supyp.bghouse.utils.JWTUtil;
import com.supyp.bghouse.utils.ThreadLocalUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;

/*
* 判断是否已经登录
* */
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private ObjectMapper JSON;

    @Resource
    private AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type","text/html;charset=UTF-8");
        System.out.println("token验证");
        HashMap<String, String> res = new HashMap<>();
        res.put("type","error");
        // 1.获取token参数
        String token = request.getParameter("token");
        if(StringUtil.isEmpty(token)){
            System.out.println("请提供token令牌");
            res.put("msg","请提供token令牌");
            response.getWriter().write(JSON.writeValueAsString(res));
            return false;
        }
        // 2.解析token ，得到account
        Account token_account = JWTUtil.decodeJWT(token);
        if(token_account == null){
            // token解析失败 or 已经过期
            res.put("msg","token失效，请重新登录");
            response.getWriter().write(JSON.writeValueAsString(res));
            return false;
        }
        Account account = accountService.findAccountById(token_account);
        // 如何将查找得到的account转发到控制器
        ThreadLocalUtil.set(account);
        System.out.println(account.getUsername() + "登录校验通过");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove(); // 删除线程
    }
}
