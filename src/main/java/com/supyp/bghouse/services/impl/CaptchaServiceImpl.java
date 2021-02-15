package com.supyp.bghouse.services.impl;

import com.supyp.bghouse.dao.CaptchaMapper;
import com.supyp.bghouse.domain.entity.Captcha;
import com.supyp.bghouse.services.CaptchaService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Resource
    private CaptchaMapper captchaMapper;

    @Override
    public Captcha findByIP(String ip) {
        Captcha captcha = new Captcha();
        captcha.setIp(ip);
        return captchaMapper.selectOne(captcha);
    }

    @Override
    public int insert(Captcha captcha) {
        captcha.setCreatetime(new Date()); // 更新时间
        Captcha select = this.findByIP(captcha.getIp());
        if(select != null){
            Example example = new Example(Captcha.class);
            example.createCriteria()
                    .andEqualTo("ip",captcha.getIp());
            return captchaMapper.updateByExampleSelective(captcha,example);
        }else{
            return captchaMapper.insertSelective(captcha);
        }
    }
}
