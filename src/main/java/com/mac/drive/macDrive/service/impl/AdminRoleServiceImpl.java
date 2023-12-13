package com.mac.drive.macDrive.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mac.drive.macDrive.mapper.AdminRoleMapper;
import com.mac.drive.macDrive.pojo.AdminRole;
import com.mac.drive.macDrive.service.IAdminRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bin
 * @since 2022-02-25
 */
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
