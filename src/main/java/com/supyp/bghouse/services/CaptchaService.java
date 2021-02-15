package com.supyp.bghouse.services;

import com.supyp.bghouse.domain.entity.Captcha;

public interface CaptchaService {
    // handle
    public Captcha findByIP(String ip); // 根据ip地址寻找验证码
    public int insert(Captcha captcha); // 新增验证码
}
