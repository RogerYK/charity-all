package com.github.rogeryk.charity.server.core.config;

import com.github.rogeryk.charity.server.core.service.AdminUserService;
import com.github.rogeryk.charity.server.core.service.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {


    @Bean(name = "authenticationManager")
    public AuthenticationManager authenticationManager(UserService userService, AdminUserService adminUserService) {
        List<AuthenticationProvider> authenticationProviders = new ArrayList<>();
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        authenticationProviders.add(daoAuthenticationProvider);
        DaoAuthenticationProvider adminDaoAuthentication = new DaoAuthenticationProvider();
        adminDaoAuthentication.setUserDetailsService(adminUserService);
        authenticationProviders.add(adminDaoAuthentication);
        return new ProviderManager(authenticationProviders);

    }
}
