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
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bin
 * @since 2022-02-17
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

    @ApiOperation("更新用户信息昵称")
    @PutMapping("/update")
    public RespBean updateAdmin(@RequestBody Admin admin){
        if(adminService.updateById(admin)){
            return RespBean.success("更新成功");
        }else {
            return RespBean.error("更新失败");
        }

    }

    @ApiOperation("更新用户密码")
    @PutMapping("/upPassword")
    public RespBean updatePassword(String oldPassword,String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Admin admin = adminService.getAdminByUserName(minioUtil.getUsername());
        if (passwordEncoder.matches(oldPassword,admin.getPassword())){
            admin.setPassword(bCryptPasswordEncoder.encode(password));
            if (adminService.updateById(admin)){
                return RespBean.success("密码修改成功");
            }else{
                return RespBean.error("密码修改失败，未知错误");
            }
        }else {
            return RespBean.error("原密码错误");
        }
    }

    @ApiOperation("更新用户头像")
    @PostMapping("/upUserFace")
    public RespBean upFace(MultipartFile file){
        try{
            String objectName = minioUtil.getUsername() + "/" +file.getOriginalFilename();
            System.out.print(objectName);
            minioUtil.upload("userface",objectName,file);
            String url = "http://192.168.2.66:9000/userface/" + objectName;
            Admin admin = adminService.getAdminByUserName(minioUtil.getUsername());
            admin.setUserFace(url);
            adminService.updateById(admin);
            return RespBean.success("头像更新成功");
               } catch (Exception e) {
            return RespBean.success("头像更新失败",e);
             }
    }

    @ApiOperation("用户使用存储大小")
    @GetMapping("/getStorage")
    public RespBean getStorage(){
        String username=minioUtil.getUsername();
        Long sum = fileService.sumBybuck(username);
        return RespBean.success("",sum);
    }

//    @ApiOperation("遍历用户")
//    @GetMapping("/test")
//    public RespBean test(){

//    }

}

