package com.github.rogeryk.charity.server.core.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogeryk.charity.server.core.fliter.AdminLoginFilter;
import com.github.rogeryk.charity.server.core.fliter.AuthenticationFilter;
import com.github.rogeryk.charity.server.core.fliter.LoginFilter;
import com.github.rogeryk.charity.server.core.service.AdminUserService;
import com.github.rogeryk.charity.server.core.service.UserService;
import com.github.rogeryk.charity.server.core.util.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminUserService adminUserService;

    @Resource(name = "authenticationManager")
    private AuthenticationManager authenticationManager;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/sign").permitAll()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/*").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/api/user/**").authenticated()
                .and()
                .formLogin().disable()
                .addFilter(userLoginFilter())
                .addFilterAfter(adminLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(basicFilter())
                .addFilter(corsFilter())
                .csrf().disable();
        http.exceptionHandling().accessDeniedHandler(getAccessDeniedHandler())
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        httpServletResponse.getOutputStream().write("{\"errCode\": 204, \"msg\":\"未登录\"}".getBytes());
                    }
                });
        super.configure(http);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public AccessDeniedHandler getAccessDeniedHandler() {
        return (request, response, ex) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ObjectMapper objectMapper = new ObjectMapper();
            OutputStream out = response.getOutputStream();
            objectMapper.writeValue(out, Response.error(204, "用户权限不足"));
        };
    }

    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }


    private LoginFilter userLoginFilter() throws Exception {
        return new LoginFilter(authenticationManager, redisTemplate);
    }

    private AdminLoginFilter adminLoginFilter() {
        return new AdminLoginFilter(authenticationManager, redisTemplate);
    }

    private AuthenticationFilter basicFilter() {
        return new AuthenticationFilter(authenticationManager, userService, adminUserService, redisTemplate);
    }
}
