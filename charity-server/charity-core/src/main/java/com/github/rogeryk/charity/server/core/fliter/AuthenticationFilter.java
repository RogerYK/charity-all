package com.github.rogeryk.charity.server.core.fliter;


import com.github.rogeryk.charity.server.core.service.AdminUserService;
import com.github.rogeryk.charity.server.db.domain.AdminUser;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.core.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthenticationFilter extends BasicAuthenticationFilter {

    private RedisTemplate<Object, Object> redisTemplate;

    private UserService userService;

    private AdminUserService adminUserService;

    @Autowired
    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UserService userService,
                                AdminUserService adminUserService,
                                RedisTemplate<Object, Object> redisTemplate) {
        super(authenticationManager);
        this.adminUserService = adminUserService;
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("access_token");

        if (token != null) {
            String username = (String) redisTemplate.opsForValue().get(token);

            if (username != null) {
                logger.info("filter"+username);
                User user = userService.findUserByPhoneNumber(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, username, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        String adminToken = request.getHeader("access_token_admin");

        if (adminToken != null) {
            String username = (String) redisTemplate.opsForValue().get(adminToken);
            if (username != null) {
                AdminUser adminUser = adminUserService.findByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        adminUser,
                        adminUser.getPassword(),
                        adminUser.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }


}
