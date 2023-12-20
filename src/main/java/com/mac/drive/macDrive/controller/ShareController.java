package com.mac.drive.macDrive.controller;

import com.mac.drive.macDrive.pojo.DTO.AdminRegisterParam;
import com.mac.drive.macDrive.pojo.RespBean;
import com.mac.drive.macDrive.service.IFileService;
import com.mac.drive.macDrive.utils.MinioUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/share")
public class ShareController {

    @Resource
    private IFileService fileService;
    @Resource
    private MinioUtil minioUtil;

    @PostMapping("/")
    @ApiOperation(value = "分享文件")
    @ResponseBody
    public RespBean ShareFile(@RequestBody AdminRegisterParam adminRegisterParam) {


        return null;
    }
}
