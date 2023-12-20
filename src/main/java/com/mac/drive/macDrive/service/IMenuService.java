package com.mac.drive.macDrive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mac.drive.macDrive.pojo.Menu;

import java.util.List;

public interface IMenuService extends IService<Menu> {

    /**
     * 通过用户ID查询菜单列表
     * @return
     */
    List<Menu> getMenusByAdminId();

}
