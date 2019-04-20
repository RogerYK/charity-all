package com.github.rogeryk.charity.fliter;

import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthticationFliter extends BasicAuthenticationFilter {

    private RedisTemplate<Object, Object> redisTemplate;

    private UserService userService;

    @Autowired
    public AuthticationFliter(AuthenticationManager authenticationManager, UserService userService, RedisTemplate<Object, Object> redisTemplate) {
        super(authenticationManager);
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("access_token");

        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        String username = (String) redisTemplate.opsForValue().get(token);

        if (username != null) {
            logger.info("filter"+username);
            User user = userService.findUserByPhoneNumber(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, username, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }


}
