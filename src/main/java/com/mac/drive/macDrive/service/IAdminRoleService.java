package com.mac.drive.macDrive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mac.drive.macDrive.pojo.AdminRole;

public interface IAdminRoleService extends IService<AdminRole> {

    void register(int adminId, int i);
}
