package com.mac.drive.macDrive.controller;

import com.mac.drive.macDrive.pojo.RespBean;
import com.mac.drive.macDrive.utils.MailServiceUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/api")
public class EmailController {

    @Resource
    private MailServiceUtil mailServiceUtils;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Send verification code, store the code in Redis
     *
     * @param toEmail the email address to send to
     */
    @PostMapping("/sendEmail")
    @ResponseBody
    public RespBean sendEmail(String toEmail) {
        // Generate a 6-digit random number
        String i = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        try {
            // Send email
            mailServiceUtils.sendMail("********@qq.com", toEmail, "Cloud Disk Verification Code", i);
            // Redis saves the verification code
            redisTemplate.opsForValue().set(toEmail, i);
        } catch (Exception e) {
            return RespBean.error("Failed to send email");
        }
        // Expires in three minutes
        redisTemplate.expire(toEmail, 60 * 3000, TimeUnit.MILLISECONDS);
        return RespBean.success("Verification code sent successfully, please fill in within three minutes");

    }
}
