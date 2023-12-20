package com.mac.drive.macDrive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mac.drive.macDrive.pojo.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户ID获取菜单
     * @param adminId
     * @return
     */
    List<Menu> getMenusByAdminId(Integer adminId);
}
