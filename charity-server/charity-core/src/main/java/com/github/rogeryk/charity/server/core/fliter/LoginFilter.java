package com.github.rogeryk.charity.server.core.fliter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogeryk.charity.server.core.util.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private static Logger logger = LoggerFactory.getLogger(LoginFilter.class);


    private RedisTemplate<Object, Object> redisTemplate;

    public LoginFilter(AuthenticationManager authenticationManager,
            RedisTemplate<Object, Object> redisTemplate) {
        setFilterProcessesUrl("/api/auth/login");
        this.setAuthenticationManager(authenticationManager);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token;
        try {
            LoginForm user = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginForm.class);
            token = new UsernamePasswordAuthenticationToken(user.getPhoneNumber(), user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            token = new UsernamePasswordAuthenticationToken("", "");
        }

        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().setIfAbsent(token, ((UserDetails)authResult.getPrincipal()).getUsername());
        redisTemplate.expire(token, 2, TimeUnit.HOURS);
        Response msg = Response.ok(token);
        String res = new ObjectMapper().writeValueAsString(msg);
        response.getOutputStream().print(res);
    }
}
