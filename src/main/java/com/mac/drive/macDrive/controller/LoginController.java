package com.mac.drive.macDrive.controller;

import com.mac.drive.macDrive.pojo.Admin;
import com.mac.drive.macDrive.pojo.DTO.AdminLoginParam;
import com.mac.drive.macDrive.pojo.RespBean;
import com.mac.drive.macDrive.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Resource
    private IAdminService adminService;

    @PostMapping("/login")
    @ApiOperation(value = "Login")
    @ResponseBody
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request) {
        return adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword(), adminLoginParam.getCode(), request);
    }

    @GetMapping("/admin/info")
    @ApiOperation(value = "Get current logged-in user information")
    public Admin getAdminInfo(Principal principal) {
        if (null == principal) {
            return null;
        }
        Admin admin = adminService.getAdminByUserName(principal.getName());
        admin.setPassword(null);
        return admin;
    }

    @PostMapping("/logout")
    @ApiOperation(value = "Logout")
    public RespBean logout() {
        return RespBean.success("Logout successful");
    }
}
