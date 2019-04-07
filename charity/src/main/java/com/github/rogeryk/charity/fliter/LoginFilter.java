package com.github.rogeryk.charity.fliter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogeryk.charity.controller.form.LoginForm;
import com.github.rogeryk.charity.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private static Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    private AuthenticationManager manager;

    private RedisTemplate<Object, Object> redisTemplate;

    public LoginFilter(AuthenticationManager manager, RedisTemplate<Object, Object> redisTemplate) {
        this.manager = manager;
        this.redisTemplate = redisTemplate;
    }

//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        if (CorsUtils.isPreFlightRequest((HttpServletRequest)req)) {
//            chain.doFilter(req, res);
//        }
//        super.doFilter(req, res, chain);
//    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token;
        try {
            LoginForm user = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginForm.class);
            System.out.println(user.getPhoneNumber());
            System.out.println(user.getPassword());
            token = new UsernamePasswordAuthenticationToken(user.getPhoneNumber(), user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            token = new UsernamePasswordAuthenticationToken("", "");
        }

        return manager.authenticate(token);
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
