package com.mac.drive.macDrive.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mac.drive.macDrive.mapper.AdminRoleMapper;
import com.mac.drive.macDrive.pojo.AdminRole;
import com.mac.drive.macDrive.service.IAdminRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements IAdminRoleService {

    @Resource
    AdminRoleMapper adminRoleMapper;

    @Override
    public void register(int adminId, int i) {
        AdminRole userRole =new AdminRole();
        userRole.setAdminId(adminId);
        userRole.setRid(i);
        adminRoleMapper.insert(userRole);
    }


}
