package com.github.rogeryk.charity.server.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*");
    }

    /*
     * hibernate could not initialize proxy - no Session错误
     *  https://blog.csdn.net/NeverSad_/article/details/86466882
     */
//    @Bean
//    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
//        return new OpenEntityManagerInViewFilter();
//    }


}
