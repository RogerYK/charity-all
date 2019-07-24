package com.github.rogeryk.charity.server.core.service;

import com.github.rogeryk.charity.server.db.domain.AdminUser;
import com.github.rogeryk.charity.server.db.repository.AdminUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService implements UserDetailsService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    public AdminUser findByUsername(String username) {
        return adminUserRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return adminUserRepository.findByUsername(s);
    }
}
