package com.supyp.bghouse.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Authority;
import com.supyp.bghouse.domain.entity.Permission;
import com.supyp.bghouse.services.AuthorityService;
import com.supyp.bghouse.services.PermissionService;
import com.supyp.bghouse.utils.ThreadLocalUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
* 判断是否有权限继续执行操作
* */
public class PermissionInterceptor implements HandlerInterceptor {
    @Resource
    private PermissionService permissionService;
    @Resource
    private AuthorityService authorityService;
    @Resource
    private ObjectMapper JSON;
    /*
    * 权限校验分析
    * 一、确保loginAccount存在 -> 登录验证必须存在 -> 解决: 登录校验的范围包含权限校验
    * 二、权限验证流程
    * URL -> 权限id\父级权限(List) -> roleid(List) -> 判断登录账户的roleid是否在里面
    * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HashMap<String, String> res = new HashMap<>();
        // 1.获取登录的账户
        Account loginAccount = ThreadLocalUtil.get();
        Integer roleid = loginAccount.getRoleid();
        // 2.获取访问的url
        String url = request.getRequestURI().toString();
        System.out.println("是否具有该权限:" + url);
        /*
         * 普通用户的roleid为null，普通用户不能访问后台
         * */
        if(roleid != null){
            // 3.根据url查询权限id
            List<Permission> permissions = permissionService.findAllByUrl(url);
            // 4.根据权限id查找roleid
            for (Permission permission : permissions) {
                List<Authority> authorities = authorityService.findAuthorityByPermissionId(permission.getId());
                for(Authority authority:authorities){
                    if (roleid.equals(authority.getRoleid())) {
                        System.out.println("授权通过");
                        return true;
                    }
                }
            }
        }
        res.put("type","error");
        res.put("msg","授权不通过");
        response.getWriter().write(JSON.writeValueAsString(res));
        return false;
    }
}
