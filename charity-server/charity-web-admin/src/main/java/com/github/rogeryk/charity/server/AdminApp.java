package com.github.rogeryk.charity.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
//@EnableCaching
@EnableAspectJAutoProxy
@EnableScheduling
public class AdminApp {

    public static void main(String[] args) {
        SpringApplication.run(AdminApp.class);
    }

}
