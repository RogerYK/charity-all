package com.github.rogeryk.server.web.admin.service;

import com.github.rogeryk.charity.server.AdminApp;
import com.github.rogeryk.charity.server.db.domain.AdminUser;
import com.github.rogeryk.charity.server.db.repository.AdminUserRepository;

import net.bytebuddy.asm.Advice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {AdminApp.class})
@RunWith(SpringRunner.class)
public class UserAdminTest {

    @Autowired
    private AdminUserRepository adminUserRepository;

    private PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    public void addUserTest() {
        AdminUser adminUser = new AdminUser();
        adminUser.setUsername("root");
        adminUser.setPassword(encoder.encode("root"));
        adminUserRepository.save(adminUser);
    }
}
