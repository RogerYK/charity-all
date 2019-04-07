package com.github.rogeryk.charity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogeryk.charity.fliter.AuthticationFliter;
import com.github.rogeryk.charity.fliter.LoginFilter;
import com.github.rogeryk.charity.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

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
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AuthticationFliter(authenticationManager(), redisTemplate), BasicAuthenticationFilter.class)
                .addFilterBefore(corsFilter(), CorsFilter.class)
                .csrf().disable();
        http.exceptionHandling().accessDeniedHandler(getAccessDeniedHandler())
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        httpServletResponse.getOutputStream().write("{\"error\": 204, \"msg\":\"未登录\"}".getBytes());
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
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new      UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        manager.createUser(User.withDefaultPasswordEncoder().username("123").password("123").roles("USER").build());
//        return manager;
//    }


    private LoginFilter customAuthenticationFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter(authenticationManager(), redisTemplate);
        loginFilter.setFilterProcessesUrl("/api/auth/login");
        return loginFilter;
    }
}
