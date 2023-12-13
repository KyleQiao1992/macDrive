package com.mac.drive.macDrive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mac.drive.macDrive.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bin
 * @since 2022-03-20
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 通过用户ID查询菜单列表
     * @return
     */
    List<Menu> getMenusByAdminId();

}
