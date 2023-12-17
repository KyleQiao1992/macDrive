package com.mac.drive.macDrive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mac.drive.macDrive.pojo.Admin;
import com.mac.drive.macDrive.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;

public interface IAdminService extends IService<Admin> {
    /**
     * Return token after login
     *
     * @param username
     * @param password
     * @param request
     * @return
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);

    /**
     * Get user object by username
     *
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);

    /**
     * Register user
     *
     * @param user
     * @return
     */
    int register(Admin user);
}
