package com.supyp.bghouse.configuration;

public class Config {
    // 【【【token】】】
    // token过期时间
    /*
    * 1s = 1000ms
    * 1000 -> 1s
    * * 60 -> 1min
    * * 60 -> 1hour
    * * 24 -> 1天
    * * 7  -> 7天
    * */
    public static int expireTime = 1000 * 60 * 60 * 24 * 7;
    // token加密密钥
    public static String SECRET = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545fdf>?N<:{LWPW";

    // 【【【Account】】】
    // 基本信息
    public static String usernameReg = "^[\\u4e00-\\u9fa5 | \\w]{2,11}$"; // 用户名的正则表达式
    public static String usernameError = "用户名为2到11位的字母、数字、中文、下划线、减号";
    public static String pwdReg = "^[a-z|A-Z|0-9]{6,18}$"; // 密码的正则表达式
    public static String pwdError = "密码为6到18位的字母或者数字";
    // 入住信息
    public static String realnameReg = "^[\\u4e00-\\u9fa5]{2,6}$";
    public static String realnameError = "请填写真实姓名";
    public static String phoneReg = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$"; // 手机号的正则表达式
    public static String phoneError = "手机格式错误";
    public static String idCardReg = "^[1-9]\\d{5}(18|19|20|(3\\d))\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$"; // 身份证的正则表达式
    public static String idCardError = "身份证号码格式错误";

    // 【【【Role】】】
    public static Integer SuperAdminRoleId = 1; // 超级管理员的角色ID

    // 【【【分页情况】】】
    public static int pageSize = 10; // 默认每页显示的数量

    // 【【【Subscribe】】】
    public static int SubscribeStatusING = 0; // 预定中
    public static int SubscribeStatusSuccess = 1; // 客人成功入住
    public static int SubscribeStatusCancel = 2; // 客人取消预约
    public static int SubscribeStatusMiss = 3; // 客人错误预约
}
