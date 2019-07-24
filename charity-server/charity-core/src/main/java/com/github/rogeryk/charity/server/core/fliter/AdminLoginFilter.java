package com.github.rogeryk.charity.server.core.fliter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogeryk.charity.server.core.util.Response;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminLoginFilter extends AbstractAuthenticationProcessingFilter {

    private RedisTemplate<Object, Object> redisTemplate;

    public AdminLoginFilter(AuthenticationManager authenticationManager,
            RedisTemplate<Object, Object> redisTemplate) {
        super("/api/admin/auth/login");
        this.setAuthenticationManager(authenticationManager);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        UsernamePasswordAuthenticationToken token;

        try {
            AdminLoginForm form = new ObjectMapper().readValue(httpServletRequest.getInputStream(), AdminLoginForm.class);
            log.info(form.toString());
            token = new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            token = new UsernamePasswordAuthenticationToken("", "");
        }
        token.setDetails(this.authenticationDetailsSource.buildDetails(httpServletRequest));
        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().setIfAbsent(token, ((UserDetails)authResult.getPrincipal()).getUsername());
        redisTemplate.expire(token, 2, TimeUnit.HOURS);
        Response msg = Response.ok(token);
        String res = new ObjectMapper().writeValueAsString(msg);
        response.getWriter().print(res);
//        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
        //TODO://这里其实最好使用successHandler来接受返回response
    }

    public RedisTemplate<Object, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
