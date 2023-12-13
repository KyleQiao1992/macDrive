package com.mac.drive.macDrive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mac.drive.macDrive.pojo.AdminRole;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bin
 * @since 2022-03-20
 */
public interface IAdminRoleService extends IService<AdminRole> {

    void register(int adminId, int i);
}
