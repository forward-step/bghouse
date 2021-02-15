package com.supyp.bghouse.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.supyp.bghouse.domain.entity.Captcha;
import com.supyp.bghouse.services.CaptchaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RequestMapping("/")
@RestController
public class CaptchaController {
    @Resource
    DefaultKaptcha defaultKaptcha;
    @Resource
    private CaptchaService captchaService;

    @GetMapping("/captcha")
    protected void captcha(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        // 获取验证码
        String createText = defaultKaptcha.createText();
        this.save(request.getRemoteAddr(),createText);
        // 生成图片
        BufferedImage bi = defaultKaptcha.createImage(createText);
        // 输出图片
        ImageIO.write(bi, "jpg", response.getOutputStream());
    }
    private void save(String ip,String checkCode){
        Captcha captcha = new Captcha();
        captcha.setIp(ip);
        captcha.setCheckcode(checkCode);
        captchaService.insert(captcha);
    }
}
