package com.mac.drive.macDrive.controller;

import com.mac.drive.macDrive.pojo.Admin;
import com.mac.drive.macDrive.pojo.DTO.AdminRegisterParam;
import com.mac.drive.macDrive.pojo.RespBean;
import com.mac.drive.macDrive.service.IAdminRoleService;
import com.mac.drive.macDrive.service.IAdminService;
import com.mac.drive.macDrive.utils.MailServiceUtil;
import com.mac.drive.macDrive.utils.MinioUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class RegisterController {
    @Resource
    MailServiceUtil mailServiceUtil;
    @Resource
    IAdminService adminService;
    @Resource
    IAdminRoleService adminRoleService;
    @Resource
    MinioUtil minioUtil;


    @PostMapping("/register")
    @ApiOperation(value = "Register User")
    @ResponseBody
    public RespBean registerUser(@RequestBody AdminRegisterParam adminRegisterParam) {
        int bol = mailServiceUtil.yanZheng(adminRegisterParam.getEmail(), adminRegisterParam.getEmailCode());
        if (bol == 1) {
            // Encrypt user password
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            try {
                Admin user = new Admin();
                user.setUsername(adminRegisterParam.getUsername());
                user.setPassword(bCryptPasswordEncoder.encode(adminRegisterParam.getPassword()));
                user.setEmail(adminRegisterParam.getEmail());
                user.setName("MinIO User");
                user.setEnabled(true);
                int adminId = adminService.register(user);
                System.out.print(adminId);
                adminRoleService.register(adminId, 3);
                minioUtil.createBucket(adminRegisterParam.getUsername());
                return RespBean.success("Registration successful");
            } catch (Exception e) {
                return RespBean.error("Registration failed, no reason to fail");
            }
        }
        if (bol == 2) {
            return RespBean.error("Verification code expired");
        } else {
            return RespBean.error("Verification code error");
        }
    }
}
