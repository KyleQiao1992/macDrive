package com.mac.drive.macDrive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mac.drive.macDrive.config.security.JwtTokenUtil;
import com.mac.drive.macDrive.mapper.AdminMapper;
import com.mac.drive.macDrive.pojo.Admin;
import com.mac.drive.macDrive.pojo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
public class AdminServiceImplTest {
    @Mock
    private AdminMapper adminMapper;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    public void testLoginSuccess() {
        String username = "testUser";
        String password = "testPass";
        String code = "1234";
        UserDetails mockUserDetails = mock(UserDetails.class);

        when(userDetailsService.loadUserByUsername(username)).thenReturn(mockUserDetails);
        when(mockUserDetails.getPassword()).thenReturn(password);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(mockUserDetails.isEnabled()).thenReturn(true);
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        when(request.getSession().getAttribute("captcha")).thenReturn(code);

        RespBean response = adminService.login(username, password, code, request);

        assertEquals("Login successful", response.getMessage());
    }

    @Test
    public void testGetAdminByUserName() {
        String username = "testUser";
        Admin mockAdmin = new Admin();
        mockAdmin.setUsername(username);

        when(adminMapper.selectOne(any(QueryWrapper.class))).thenReturn(mockAdmin);

        Admin result = adminService.getAdminByUserName(username);

        assertEquals(username, result.getUsername());
    }

    @Test
    public void testRegister() {
        Admin newUser = new Admin();
        newUser.setUsername("newUser");
        Admin savedUser = new Admin();
        savedUser.setId(1);
        savedUser.setUsername("newUser");

        when(adminMapper.insert(any(Admin.class))).thenReturn(1);
        when(adminMapper.selectOne(any(QueryWrapper.class))).thenReturn(savedUser);

        int userId = adminService.register(newUser);

        assertEquals(1, userId);
    }
}