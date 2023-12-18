package com.mac.drive.macDrive.controller;

import com.mac.drive.macDrive.pojo.Admin;
import com.mac.drive.macDrive.pojo.RespBean;
import com.mac.drive.macDrive.service.IAdminService;
import com.mac.drive.macDrive.service.IFileService;
import com.mac.drive.macDrive.utils.MinioUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Frontend Controller
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Resource
    private IAdminService adminService;
    @Resource
    private MinioUtil minioUtil;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private IFileService fileService;

    @ApiOperation("Update user information nickname")
    @PutMapping("/update")
    public RespBean updateAdmin(@RequestBody Admin admin) {
        if (adminService.updateById(admin)) {
            return RespBean.success("Update successful");
        } else {
            return RespBean.error("Update failed");
        }

    }

    @ApiOperation("Update user password")
    @PutMapping("/upPassword")
    public RespBean updatePassword(String oldPassword, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Admin admin = adminService.getAdminByUserName(minioUtil.getUsername());
        if (passwordEncoder.matches(oldPassword, admin.getPassword())) {
            admin.setPassword(bCryptPasswordEncoder.encode(password));
            if (adminService.updateById(admin)) {
                return RespBean.success("Password changed successfully");
            } else {
                return RespBean.error("Password change failed, unknown error");
            }
        } else {
            return RespBean.error("Original password incorrect");
        }
    }

    @ApiOperation("Update user avatar")
    @PostMapping("/upUserFace")
    public RespBean upFace(MultipartFile file) {
        try {
            String objectName = minioUtil.getUsername() + "/" + file.getOriginalFilename();
            System.out.print(objectName);
            minioUtil.upload("public", objectName, file);
            String url = "http://127.0.0.1:9000/public/" + objectName;
            Admin admin = adminService.getAdminByUserName(minioUtil.getUsername());
            admin.setUserFace(url);
            adminService.updateById(admin);
            return RespBean.success("Avatar updated successfully");
        } catch (Exception e) {
            return RespBean.success("Avatar update failed", e);
        }
    }

    @ApiOperation("User used storage size")
    @GetMapping("/getStorage")
    public RespBean getStorage() {
        String username = minioUtil.getUsername();
        Long sum = fileService.sumBybuck(username);
        return RespBean.success("", sum);
    }
}
