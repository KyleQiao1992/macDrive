package com.mac.drive.macDrive.service.impl;

import com.mac.drive.macDrive.mapper.MenuMapper;
import com.mac.drive.macDrive.pojo.Admin;
import com.mac.drive.macDrive.pojo.Menu;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
public class MenuServiceImplTest {

    @Mock
    private MenuMapper menuMapper;
    @Mock
    private RedisTemplate<String, Object> redisTemplate;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private MenuServiceImpl menuService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }


    @Test
    public void testGetMenusByAdminId_WhenInCache() {
        Integer adminId = 1;
        List<Menu> cachedMenus = new ArrayList<>();
        ValueOperations<String, Object> valueOperations = mock(ValueOperations.class);

        when(authentication.getPrincipal()).thenReturn(new Admin());
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("menu_" + adminId)).thenReturn(cachedMenus);

        List<Menu> result = menuService.getMenusByAdminId();

        verify(menuMapper, never()).getMenusByAdminId(adminId);
        assertEquals(cachedMenus, result);
    }


}