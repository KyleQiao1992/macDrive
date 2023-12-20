package com.mac.drive.macDrive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mac.drive.macDrive.config.security.JwtTokenUtil;
import com.mac.drive.macDrive.mapper.AdminMapper;
import com.mac.drive.macDrive.pojo.Admin;
import com.mac.drive.macDrive.pojo.RespBean;
import com.mac.drive.macDrive.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Resource
    private AdminMapper adminMapper;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * Return token after login
     *
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        // Login
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("Username or password incorrect");
        }
        if (!userDetails.isEnabled()) {
            return RespBean.error("Account disabled, please contact administrator");
        }
        // Verify captcha
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(code) || !captcha.equals(code)) {
            return RespBean.error("Incorrect captcha");
        }
        // Update login user object
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // Generate token
        String token = jwtTokenUtil.generatorToken(userDetails);
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("tokenHead", tokenHead);
        tokenMap.put("token", token);
        return RespBean.success("Login successful", tokenMap);
    }

    /**
     * Get user object by username
     *
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username));
    }

    /**
     * Register
     *
     * @return
     */
    @Override
    public int register(Admin user) {
        int bol = adminMapper.insert(user);
        System.out.print(bol);
        if (bol == 1) {
            Admin i = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", user.getUsername()));
            System.out.print(i.getId());
            return i.getId();
        } else {
            return 0;
        }
    }
}
