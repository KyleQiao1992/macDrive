package com.mac.drive.macDrive.service.impl;

import com.mac.drive.macDrive.mapper.AdminRoleMapper;
import com.mac.drive.macDrive.pojo.AdminRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.session.DefaultMockitoSessionBuilder;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;

@Slf4j
@SpringBootTest
public class AdminRoleServiceImplTest {

    @Mock
    private AdminRoleMapper adminRoleMapper;

    @InjectMocks
    private AdminRoleServiceImpl adminRoleService;

    @Test
    public void testRegister() {
        int adminId = 1;
        int rid = 2;

        adminRoleService.register(adminId, rid);

        AdminRole expectedAdminRole = new AdminRole();
        expectedAdminRole.setAdminId(adminId);
        expectedAdminRole.setRid(rid);

        verify(adminRoleMapper).insert(expectedAdminRole);
    }
}