package com.mac.drive.macDrive.controller;


import com.mac.drive.macDrive.pojo.Menu;
import com.mac.drive.macDrive.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/system/config")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @ApiOperation(value = "Query menu list by user ID")
    @GetMapping("/menu")
    public List<Menu> getMenusByAdminId(){
        return menuService.getMenusByAdminId();
    }

}
